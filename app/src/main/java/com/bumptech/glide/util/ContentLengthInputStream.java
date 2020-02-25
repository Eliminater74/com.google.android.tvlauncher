package com.bumptech.glide.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class ContentLengthInputStream extends FilterInputStream {
    private static final String TAG = "ContentLengthStream";
    private static final int UNKNOWN = -1;
    private final long contentLength;
    private int readSoFar;

    private ContentLengthInputStream(@NonNull InputStream in, long contentLength2) {
        super(in);
        this.contentLength = contentLength2;
    }

    @NonNull
    public static InputStream obtain(@NonNull InputStream other, @Nullable String contentLengthHeader) {
        return obtain(other, (long) parseContentLength(contentLengthHeader));
    }

    @NonNull
    public static InputStream obtain(@NonNull InputStream other, long contentLength2) {
        return new ContentLengthInputStream(other, contentLength2);
    }

    private static int parseContentLength(@Nullable String contentLengthHeader) {
        if (TextUtils.isEmpty(contentLengthHeader)) {
            return -1;
        }
        try {
            return Integer.parseInt(contentLengthHeader);
        } catch (NumberFormatException e) {
            if (!Log.isLoggable(TAG, 3)) {
                return -1;
            }
            String valueOf = String.valueOf(contentLengthHeader);
            Log.d(TAG, valueOf.length() != 0 ? "failed to parse content length header: ".concat(valueOf) : new String("failed to parse content length header: "), e);
            return -1;
        }
    }

    public synchronized int available() throws IOException {
        return (int) Math.max(this.contentLength - ((long) this.readSoFar), (long) this.in.available());
    }

    public synchronized int read() throws IOException {
        int value;
        value = super.read();
        checkReadSoFarOrThrow(value >= 0 ? 1 : -1);
        return value;
    }

    public int read(byte[] buffer) throws IOException {
        return read(buffer, 0, buffer.length);
    }

    public synchronized int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
        return checkReadSoFarOrThrow(super.read(buffer, byteOffset, byteCount));
    }

    private int checkReadSoFarOrThrow(int read) throws IOException {
        if (read >= 0) {
            this.readSoFar += read;
        } else {
            long j = this.contentLength;
            int i = this.readSoFar;
            if (j - ((long) i) > 0) {
                StringBuilder sb = new StringBuilder(87);
                sb.append("Failed to read all expected data, expected: ");
                sb.append(j);
                sb.append(", but read: ");
                sb.append(i);
                throw new IOException(sb.toString());
            }
        }
        return read;
    }
}
