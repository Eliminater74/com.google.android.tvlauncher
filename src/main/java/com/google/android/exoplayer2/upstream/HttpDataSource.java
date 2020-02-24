package com.google.android.exoplayer2.upstream;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.util.Predicate;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface HttpDataSource extends DataSource {
    public static final Predicate<String> REJECT_PAYWALL_TYPES = HttpDataSource$$Lambda$0.$instance;

    public interface Factory extends DataSource.Factory {
        @Deprecated
        void clearAllDefaultRequestProperties();

        @Deprecated
        void clearDefaultRequestProperty(String str);

        HttpDataSource createDataSource();

        RequestProperties getDefaultRequestProperties();

        @Deprecated
        void setDefaultRequestProperty(String str, String str2);
    }

    void clearAllRequestProperties();

    void clearRequestProperty(String str);

    void close() throws HttpDataSourceException;

    Map<String, List<String>> getResponseHeaders();

    long open(DataSpec dataSpec) throws HttpDataSourceException;

    int read(byte[] bArr, int i, int i2) throws HttpDataSourceException;

    void setRequestProperty(String str, String str2);

    public static final class RequestProperties {
        private final Map<String, String> requestProperties = new HashMap();
        private Map<String, String> requestPropertiesSnapshot;

        public synchronized void set(String name, String value) {
            this.requestPropertiesSnapshot = null;
            this.requestProperties.put(name, value);
        }

        public synchronized void set(Map<String, String> properties) {
            this.requestPropertiesSnapshot = null;
            this.requestProperties.putAll(properties);
        }

        public synchronized void clearAndSet(Map<String, String> properties) {
            this.requestPropertiesSnapshot = null;
            this.requestProperties.clear();
            this.requestProperties.putAll(properties);
        }

        public synchronized void remove(String name) {
            this.requestPropertiesSnapshot = null;
            this.requestProperties.remove(name);
        }

        public synchronized void clear() {
            this.requestPropertiesSnapshot = null;
            this.requestProperties.clear();
        }

        public synchronized Map<String, String> getSnapshot() {
            if (this.requestPropertiesSnapshot == null) {
                this.requestPropertiesSnapshot = Collections.unmodifiableMap(new HashMap(this.requestProperties));
            }
            return this.requestPropertiesSnapshot;
        }
    }

    public static abstract class BaseFactory implements Factory {
        private final RequestProperties defaultRequestProperties = new RequestProperties();

        /* access modifiers changed from: protected */
        public abstract HttpDataSource createDataSourceInternal(RequestProperties requestProperties);

        public final HttpDataSource createDataSource() {
            return createDataSourceInternal(this.defaultRequestProperties);
        }

        public final RequestProperties getDefaultRequestProperties() {
            return this.defaultRequestProperties;
        }

        @Deprecated
        public final void setDefaultRequestProperty(String name, String value) {
            this.defaultRequestProperties.set(name, value);
        }

        @Deprecated
        public final void clearDefaultRequestProperty(String name) {
            this.defaultRequestProperties.remove(name);
        }

        @Deprecated
        public final void clearAllDefaultRequestProperties() {
            this.defaultRequestProperties.clear();
        }
    }

    public static class HttpDataSourceException extends IOException {
        public static final int TYPE_CLOSE = 3;
        public static final int TYPE_OPEN = 1;
        public static final int TYPE_READ = 2;
        public final DataSpec dataSpec;
        public final int type;

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface Type {
        }

        public HttpDataSourceException(DataSpec dataSpec2, int type2) {
            this.dataSpec = dataSpec2;
            this.type = type2;
        }

        public HttpDataSourceException(String message, DataSpec dataSpec2, int type2) {
            super(message);
            this.dataSpec = dataSpec2;
            this.type = type2;
        }

        public HttpDataSourceException(IOException cause, DataSpec dataSpec2, int type2) {
            super(cause);
            this.dataSpec = dataSpec2;
            this.type = type2;
        }

        public HttpDataSourceException(String message, IOException cause, DataSpec dataSpec2, int type2) {
            super(message, cause);
            this.dataSpec = dataSpec2;
            this.type = type2;
        }
    }

    public static final class InvalidContentTypeException extends HttpDataSourceException {
        public final String contentType;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public InvalidContentTypeException(java.lang.String r4, com.google.android.exoplayer2.upstream.DataSpec r5) {
            /*
                r3 = this;
                java.lang.String r0 = java.lang.String.valueOf(r4)
                int r1 = r0.length()
                java.lang.String r2 = "Invalid content type: "
                if (r1 == 0) goto L_0x0011
                java.lang.String r0 = r2.concat(r0)
                goto L_0x0016
            L_0x0011:
                java.lang.String r0 = new java.lang.String
                r0.<init>(r2)
            L_0x0016:
                r1 = 1
                r3.<init>(r0, r5, r1)
                r3.contentType = r4
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.HttpDataSource.InvalidContentTypeException.<init>(java.lang.String, com.google.android.exoplayer2.upstream.DataSpec):void");
        }
    }

    public static final class InvalidResponseCodeException extends HttpDataSourceException {
        public final Map<String, List<String>> headerFields;
        public final int responseCode;
        @Nullable
        public final String responseMessage;

        @Deprecated
        public InvalidResponseCodeException(int responseCode2, Map<String, List<String>> headerFields2, DataSpec dataSpec) {
            this(responseCode2, null, headerFields2, dataSpec);
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public InvalidResponseCodeException(int r3, @android.support.annotation.Nullable java.lang.String r4, java.util.Map<java.lang.String, java.util.List<java.lang.String>> r5, com.google.android.exoplayer2.upstream.DataSpec r6) {
            /*
                r2 = this;
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r1 = 26
                r0.<init>(r1)
                java.lang.String r1 = "Response code: "
                r0.append(r1)
                r0.append(r3)
                java.lang.String r0 = r0.toString()
                r1 = 1
                r2.<init>(r0, r6, r1)
                r2.responseCode = r3
                r2.responseMessage = r4
                r2.headerFields = r5
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.HttpDataSource.InvalidResponseCodeException.<init>(int, java.lang.String, java.util.Map, com.google.android.exoplayer2.upstream.DataSpec):void");
        }
    }
}
