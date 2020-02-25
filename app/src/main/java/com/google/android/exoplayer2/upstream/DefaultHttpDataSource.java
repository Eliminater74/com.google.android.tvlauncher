package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Predicate;
import com.google.android.exoplayer2.util.Util;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.NoRouteToHostException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultHttpDataSource extends BaseDataSource implements HttpDataSource {
    public static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 8000;
    public static final int DEFAULT_READ_TIMEOUT_MILLIS = 8000;
    private static final Pattern CONTENT_RANGE_HEADER = Pattern.compile("^bytes (\\d+)-(\\d+)/(\\d+)$");
    private static final int HTTP_STATUS_PERMANENT_REDIRECT = 308;
    private static final int HTTP_STATUS_TEMPORARY_REDIRECT = 307;
    private static final long MAX_BYTES_TO_DRAIN = 2048;
    private static final int MAX_REDIRECTS = 20;
    private static final String TAG = "DefaultHttpDataSource";
    private static final AtomicReference<byte[]> skipBufferReference = new AtomicReference<>();
    private final boolean allowCrossProtocolRedirects;
    private final int connectTimeoutMillis;
    @Nullable
    private final Predicate<String> contentTypePredicate;
    @Nullable
    private final HttpDataSource.RequestProperties defaultRequestProperties;
    private final int readTimeoutMillis;
    private final HttpDataSource.RequestProperties requestProperties;
    private final String userAgent;
    private long bytesRead;
    private long bytesSkipped;
    private long bytesToRead;
    private long bytesToSkip;
    @Nullable
    private HttpURLConnection connection;
    @Nullable
    private DataSpec dataSpec;
    @Nullable
    private InputStream inputStream;
    private boolean opened;

    public DefaultHttpDataSource(String userAgent2, @Nullable Predicate<String> contentTypePredicate2) {
        this(userAgent2, contentTypePredicate2, 8000, 8000);
    }

    public DefaultHttpDataSource(String userAgent2, @Nullable Predicate<String> contentTypePredicate2, int connectTimeoutMillis2, int readTimeoutMillis2) {
        this(userAgent2, contentTypePredicate2, connectTimeoutMillis2, readTimeoutMillis2, false, null);
    }

    public DefaultHttpDataSource(String userAgent2, @Nullable Predicate<String> contentTypePredicate2, int connectTimeoutMillis2, int readTimeoutMillis2, boolean allowCrossProtocolRedirects2, @Nullable HttpDataSource.RequestProperties defaultRequestProperties2) {
        super(true);
        this.userAgent = Assertions.checkNotEmpty(userAgent2);
        this.contentTypePredicate = contentTypePredicate2;
        this.requestProperties = new HttpDataSource.RequestProperties();
        this.connectTimeoutMillis = connectTimeoutMillis2;
        this.readTimeoutMillis = readTimeoutMillis2;
        this.allowCrossProtocolRedirects = allowCrossProtocolRedirects2;
        this.defaultRequestProperties = defaultRequestProperties2;
    }

    @Deprecated
    public DefaultHttpDataSource(String userAgent2, @Nullable Predicate<String> contentTypePredicate2, @Nullable TransferListener listener) {
        this(userAgent2, contentTypePredicate2, listener, 8000, 8000);
    }

    @Deprecated
    public DefaultHttpDataSource(String userAgent2, @Nullable Predicate<String> contentTypePredicate2, @Nullable TransferListener listener, int connectTimeoutMillis2, int readTimeoutMillis2) {
        this(userAgent2, contentTypePredicate2, listener, connectTimeoutMillis2, readTimeoutMillis2, false, null);
    }

    @Deprecated
    public DefaultHttpDataSource(String userAgent2, @Nullable Predicate<String> contentTypePredicate2, @Nullable TransferListener listener, int connectTimeoutMillis2, int readTimeoutMillis2, boolean allowCrossProtocolRedirects2, @Nullable HttpDataSource.RequestProperties defaultRequestProperties2) {
        this(userAgent2, contentTypePredicate2, connectTimeoutMillis2, readTimeoutMillis2, allowCrossProtocolRedirects2, defaultRequestProperties2);
        if (listener != null) {
            addTransferListener(listener);
        }
    }

    private static URL handleRedirect(URL originalUrl, String location) throws IOException {
        if (location != null) {
            URL url = new URL(originalUrl, location);
            String protocol = url.getProtocol();
            if ("https".equals(protocol) || "http".equals(protocol)) {
                return url;
            }
            String valueOf = String.valueOf(protocol);
            throw new ProtocolException(valueOf.length() != 0 ? "Unsupported protocol redirect: ".concat(valueOf) : new String("Unsupported protocol redirect: "));
        }
        throw new ProtocolException("Null location redirect");
    }

    private static long getContentLength(HttpURLConnection connection2) {
        long contentLength = -1;
        String contentLengthHeader = connection2.getHeaderField("Content-Length");
        if (!TextUtils.isEmpty(contentLengthHeader)) {
            try {
                contentLength = Long.parseLong(contentLengthHeader);
            } catch (NumberFormatException e) {
                StringBuilder sb = new StringBuilder(String.valueOf(contentLengthHeader).length() + 28);
                sb.append("Unexpected Content-Length [");
                sb.append(contentLengthHeader);
                sb.append("]");
                Log.m26e(TAG, sb.toString());
            }
        }
        String contentRangeHeader = connection2.getHeaderField("Content-Range");
        if (TextUtils.isEmpty(contentRangeHeader)) {
            return contentLength;
        }
        Matcher matcher = CONTENT_RANGE_HEADER.matcher(contentRangeHeader);
        if (!matcher.find()) {
            return contentLength;
        }
        try {
            long contentLengthFromRange = (Long.parseLong(matcher.group(2)) - Long.parseLong(matcher.group(1))) + 1;
            if (contentLength < 0) {
                return contentLengthFromRange;
            }
            if (contentLength == contentLengthFromRange) {
                return contentLength;
            }
            StringBuilder sb2 = new StringBuilder(String.valueOf(contentLengthHeader).length() + 26 + String.valueOf(contentRangeHeader).length());
            sb2.append("Inconsistent headers [");
            sb2.append(contentLengthHeader);
            sb2.append("] [");
            sb2.append(contentRangeHeader);
            sb2.append("]");
            Log.m30w(TAG, sb2.toString());
            return Math.max(contentLength, contentLengthFromRange);
        } catch (NumberFormatException e2) {
            StringBuilder sb3 = new StringBuilder(String.valueOf(contentRangeHeader).length() + 27);
            sb3.append("Unexpected Content-Range [");
            sb3.append(contentRangeHeader);
            sb3.append("]");
            Log.m26e(TAG, sb3.toString());
            return contentLength;
        }
    }

    private static void maybeTerminateInputStream(HttpURLConnection connection2, long bytesRemaining) {
        if (Util.SDK_INT == 19 || Util.SDK_INT == 20) {
            try {
                InputStream inputStream2 = connection2.getInputStream();
                if (bytesRemaining == -1) {
                    if (inputStream2.read() == -1) {
                        return;
                    }
                } else if (bytesRemaining <= 2048) {
                    return;
                }
                String className = inputStream2.getClass().getName();
                if ("com.android.okhttp.internal.http.HttpTransport$ChunkedInputStream".equals(className) || "com.android.okhttp.internal.http.HttpTransport$FixedLengthInputStream".equals(className)) {
                    Method unexpectedEndOfInput = inputStream2.getClass().getSuperclass().getDeclaredMethod("unexpectedEndOfInput", new Class[0]);
                    unexpectedEndOfInput.setAccessible(true);
                    unexpectedEndOfInput.invoke(inputStream2, new Object[0]);
                }
            } catch (Exception e) {
            }
        }
    }

    @Nullable
    public Uri getUri() {
        HttpURLConnection httpURLConnection = this.connection;
        if (httpURLConnection == null) {
            return null;
        }
        return Uri.parse(httpURLConnection.getURL().toString());
    }

    public Map<String, List<String>> getResponseHeaders() {
        HttpURLConnection httpURLConnection = this.connection;
        return httpURLConnection == null ? Collections.emptyMap() : httpURLConnection.getHeaderFields();
    }

    public void setRequestProperty(String name, String value) {
        Assertions.checkNotNull(name);
        Assertions.checkNotNull(value);
        this.requestProperties.set(name, value);
    }

    public void clearRequestProperty(String name) {
        Assertions.checkNotNull(name);
        this.requestProperties.remove(name);
    }

    public void clearAllRequestProperties() {
        this.requestProperties.clear();
    }

    public long open(DataSpec dataSpec2) throws HttpDataSource.HttpDataSourceException {
        this.dataSpec = dataSpec2;
        long j = 0;
        this.bytesRead = 0;
        this.bytesSkipped = 0;
        transferInitializing(dataSpec2);
        try {
            this.connection = makeConnection(dataSpec2);
            try {
                int responseCode = this.connection.getResponseCode();
                String responseMessage = this.connection.getResponseMessage();
                if (responseCode < 200 || responseCode > 299) {
                    Map<String, List<String>> headers = this.connection.getHeaderFields();
                    closeConnectionQuietly();
                    HttpDataSource.InvalidResponseCodeException exception = new HttpDataSource.InvalidResponseCodeException(responseCode, responseMessage, headers, dataSpec2);
                    if (responseCode == 416) {
                        exception.initCause(new DataSourceException(0));
                    }
                    throw exception;
                }
                String contentType = this.connection.getContentType();
                Predicate<String> predicate = this.contentTypePredicate;
                if (predicate == null || predicate.evaluate(contentType)) {
                    if (responseCode == 200 && dataSpec2.position != 0) {
                        j = dataSpec2.position;
                    }
                    this.bytesToSkip = j;
                    if (!dataSpec2.isFlagSet(1)) {
                        long j2 = -1;
                        if (dataSpec2.length != -1) {
                            this.bytesToRead = dataSpec2.length;
                        } else {
                            long contentLength = getContentLength(this.connection);
                            if (contentLength != -1) {
                                j2 = contentLength - this.bytesToSkip;
                            }
                            this.bytesToRead = j2;
                        }
                    } else {
                        this.bytesToRead = dataSpec2.length;
                    }
                    try {
                        this.inputStream = this.connection.getInputStream();
                        this.opened = true;
                        transferStarted(dataSpec2);
                        return this.bytesToRead;
                    } catch (IOException e) {
                        closeConnectionQuietly();
                        throw new HttpDataSource.HttpDataSourceException(e, dataSpec2, 1);
                    }
                } else {
                    closeConnectionQuietly();
                    throw new HttpDataSource.InvalidContentTypeException(contentType, dataSpec2);
                }
            } catch (IOException e2) {
                closeConnectionQuietly();
                String valueOf = String.valueOf(dataSpec2.uri.toString());
                throw new HttpDataSource.HttpDataSourceException(valueOf.length() != 0 ? "Unable to connect to ".concat(valueOf) : new String("Unable to connect to "), e2, dataSpec2, 1);
            }
        } catch (IOException e3) {
            String valueOf2 = String.valueOf(dataSpec2.uri.toString());
            throw new HttpDataSource.HttpDataSourceException(valueOf2.length() != 0 ? "Unable to connect to ".concat(valueOf2) : new String("Unable to connect to "), e3, dataSpec2, 1);
        }
    }

    public int read(byte[] buffer, int offset, int readLength) throws HttpDataSource.HttpDataSourceException {
        try {
            skipInternal();
            return readInternal(buffer, offset, readLength);
        } catch (IOException e) {
            throw new HttpDataSource.HttpDataSourceException(e, this.dataSpec, 2);
        }
    }

    public void close() throws HttpDataSource.HttpDataSourceException {
        try {
            if (this.inputStream != null) {
                maybeTerminateInputStream(this.connection, bytesRemaining());
                this.inputStream.close();
            }
            this.inputStream = null;
            closeConnectionQuietly();
            if (this.opened) {
                this.opened = false;
                transferEnded();
            }
        } catch (IOException e) {
            throw new HttpDataSource.HttpDataSourceException(e, this.dataSpec, 3);
        } catch (Throwable th) {
            this.inputStream = null;
            closeConnectionQuietly();
            if (this.opened) {
                this.opened = false;
                transferEnded();
            }
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final HttpURLConnection getConnection() {
        return this.connection;
    }

    /* access modifiers changed from: protected */
    public final long bytesSkipped() {
        return this.bytesSkipped;
    }

    /* access modifiers changed from: protected */
    public final long bytesRead() {
        return this.bytesRead;
    }

    /* access modifiers changed from: protected */
    public final long bytesRemaining() {
        long j = this.bytesToRead;
        return j == -1 ? j : j - this.bytesRead;
    }

    private HttpURLConnection makeConnection(DataSpec dataSpec2) throws IOException {
        HttpURLConnection connection2;
        DataSpec dataSpec3 = dataSpec2;
        URL url = new URL(dataSpec3.uri.toString());
        int httpMethod = dataSpec3.httpMethod;
        byte[] httpBody = dataSpec3.httpBody;
        long position = dataSpec3.position;
        long length = dataSpec3.length;
        int i = 1;
        boolean allowGzip = dataSpec3.isFlagSet(1);
        boolean allowIcyMetadata = dataSpec3.isFlagSet(2);
        if (!this.allowCrossProtocolRedirects) {
            return makeConnection(url, httpMethod, httpBody, position, length, allowGzip, allowIcyMetadata, true);
        }
        long length2 = length;
        int redirectCount = 0;
        while (true) {
            int redirectCount2 = redirectCount + 1;
            if (redirectCount <= 20) {
                long position2 = position;
                connection2 = makeConnection(url, httpMethod, httpBody, position2, length2, allowGzip, allowIcyMetadata, false);
                int responseCode = connection2.getResponseCode();
                String location = connection2.getHeaderField("Location");
                if ((httpMethod == i || httpMethod == 3) && (responseCode == 300 || responseCode == 301 || responseCode == 302 || responseCode == 303 || responseCode == 307 || responseCode == 308)) {
                    connection2.disconnect();
                    url = handleRedirect(url, location);
                } else if (httpMethod != 2 || (responseCode != 300 && responseCode != 301 && responseCode != 302 && responseCode != 303)) {
                    return connection2;
                } else {
                    connection2.disconnect();
                    url = handleRedirect(url, location);
                    httpMethod = 1;
                    httpBody = null;
                }
                redirectCount = redirectCount2;
                position = position2;
                i = 1;
            } else {
                StringBuilder sb = new StringBuilder(31);
                sb.append("Too many redirects: ");
                sb.append(redirectCount2);
                throw new NoRouteToHostException(sb.toString());
            }
        }
        return connection2;
    }

    private HttpURLConnection makeConnection(URL url, int httpMethod, byte[] httpBody, long position, long length, boolean allowGzip, boolean allowIcyMetadata, boolean followRedirects) throws IOException {
        byte[] bArr = httpBody;
        long j = position;
        HttpURLConnection connection2 = (HttpURLConnection) url.openConnection();
        connection2.setConnectTimeout(this.connectTimeoutMillis);
        connection2.setReadTimeout(this.readTimeoutMillis);
        HttpDataSource.RequestProperties requestProperties2 = this.defaultRequestProperties;
        if (requestProperties2 != null) {
            for (Map.Entry<String, String> property : requestProperties2.getSnapshot().entrySet()) {
                connection2.setRequestProperty((String) property.getKey(), (String) property.getValue());
            }
        }
        for (Map.Entry<String, String> property2 : this.requestProperties.getSnapshot().entrySet()) {
            connection2.setRequestProperty((String) property2.getKey(), (String) property2.getValue());
        }
        if (!(j == 0 && length == -1)) {
            StringBuilder sb = new StringBuilder(27);
            sb.append("bytes=");
            sb.append(j);
            sb.append("-");
            String rangeRequest = sb.toString();
            if (length != -1) {
                String valueOf = String.valueOf(rangeRequest);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 20);
                sb2.append(valueOf);
                sb2.append((j + length) - 1);
                rangeRequest = sb2.toString();
            }
            connection2.setRequestProperty("Range", rangeRequest);
        }
        connection2.setRequestProperty("User-Agent", this.userAgent);
        if (!allowGzip) {
            connection2.setRequestProperty("Accept-Encoding", "identity");
        }
        if (allowIcyMetadata) {
            connection2.setRequestProperty(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_NAME, IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
        }
        connection2.setInstanceFollowRedirects(followRedirects);
        connection2.setDoOutput(bArr != null);
        connection2.setRequestMethod(DataSpec.getStringForHttpMethod(httpMethod));
        if (bArr != null) {
            connection2.setFixedLengthStreamingMode(bArr.length);
            connection2.connect();
            OutputStream os = connection2.getOutputStream();
            os.write(httpBody);
            os.close();
        } else {
            connection2.connect();
        }
        return connection2;
    }

    private void skipInternal() throws IOException {
        if (this.bytesSkipped != this.bytesToSkip) {
            byte[] skipBuffer = skipBufferReference.getAndSet(null);
            if (skipBuffer == null) {
                skipBuffer = new byte[4096];
            }
            while (true) {
                long j = this.bytesSkipped;
                long j2 = this.bytesToSkip;
                if (j != j2) {
                    int read = this.inputStream.read(skipBuffer, 0, (int) Math.min(j2 - j, (long) skipBuffer.length));
                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedIOException();
                    } else if (read != -1) {
                        this.bytesSkipped += (long) read;
                        bytesTransferred(read);
                    } else {
                        throw new EOFException();
                    }
                } else {
                    skipBufferReference.set(skipBuffer);
                    return;
                }
            }
        }
    }

    private int readInternal(byte[] buffer, int offset, int readLength) throws IOException {
        if (readLength == 0) {
            return 0;
        }
        long j = this.bytesToRead;
        if (j != -1) {
            long bytesRemaining = j - this.bytesRead;
            if (bytesRemaining == 0) {
                return -1;
            }
            readLength = (int) Math.min((long) readLength, bytesRemaining);
        }
        int read = this.inputStream.read(buffer, offset, readLength);
        if (read != -1) {
            this.bytesRead += (long) read;
            bytesTransferred(read);
            return read;
        } else if (this.bytesToRead == -1) {
            return -1;
        } else {
            throw new EOFException();
        }
    }

    private void closeConnectionQuietly() {
        HttpURLConnection httpURLConnection = this.connection;
        if (httpURLConnection != null) {
            try {
                httpURLConnection.disconnect();
            } catch (Exception e) {
                Log.m27e(TAG, "Unexpected error while disconnecting", e);
            }
            this.connection = null;
        }
    }
}
