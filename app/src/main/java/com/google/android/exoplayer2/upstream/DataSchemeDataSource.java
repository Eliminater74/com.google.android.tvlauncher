package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Base64;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;
import java.net.URLDecoder;

public final class DataSchemeDataSource extends BaseDataSource {
    public static final String SCHEME_DATA = "data";
    private int bytesRead;
    @Nullable
    private byte[] data;
    @Nullable
    private DataSpec dataSpec;

    public DataSchemeDataSource() {
        super(false);
    }

    public long open(DataSpec dataSpec2) throws IOException {
        transferInitializing(dataSpec2);
        this.dataSpec = dataSpec2;
        Uri uri = dataSpec2.uri;
        String scheme = uri.getScheme();
        if (!"data".equals(scheme)) {
            String valueOf = String.valueOf(scheme);
            throw new ParserException(valueOf.length() != 0 ? "Unsupported scheme: ".concat(valueOf) : new String("Unsupported scheme: "));
        }
        String[] uriParts = Util.split(uri.getSchemeSpecificPart(), ",");
        if (uriParts.length == 2) {
            String dataString = uriParts[1];
            if (uriParts[0].contains(";base64")) {
                try {
                    this.data = Base64.decode(dataString, 0);
                } catch (IllegalArgumentException e) {
                    String valueOf2 = String.valueOf(dataString);
                    throw new ParserException(valueOf2.length() != 0 ? "Error while parsing Base64 encoded string: ".concat(valueOf2) : new String("Error while parsing Base64 encoded string: "), e);
                }
            } else {
                this.data = Util.getUtf8Bytes(URLDecoder.decode(dataString, C0841C.ASCII_NAME));
            }
            transferStarted(dataSpec2);
            return (long) this.data.length;
        }
        String valueOf3 = String.valueOf(uri);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf3).length() + 23);
        sb.append("Unexpected URI format: ");
        sb.append(valueOf3);
        throw new ParserException(sb.toString());
    }

    public int read(byte[] buffer, int offset, int readLength) {
        if (readLength == 0) {
            return 0;
        }
        int remainingBytes = this.data.length - this.bytesRead;
        if (remainingBytes == 0) {
            return -1;
        }
        int readLength2 = Math.min(readLength, remainingBytes);
        System.arraycopy(this.data, this.bytesRead, buffer, offset, readLength2);
        this.bytesRead += readLength2;
        bytesTransferred(readLength2);
        return readLength2;
    }

    @Nullable
    public Uri getUri() {
        DataSpec dataSpec2 = this.dataSpec;
        if (dataSpec2 != null) {
            return dataSpec2.uri;
        }
        return null;
    }

    public void close() throws IOException {
        if (this.data != null) {
            this.data = null;
            transferEnded();
        }
        this.dataSpec = null;
    }
}
