package com.google.android.exoplayer2.upstream;

import android.support.annotation.NonNull;

import com.google.android.exoplayer2.util.Assertions;
import com.google.common.primitives.UnsignedBytes;

import java.io.IOException;
import java.io.InputStream;

public final class DataSourceInputStream extends InputStream {
    private final DataSource dataSource;
    private final DataSpec dataSpec;
    private final byte[] singleByteArray;
    private boolean closed = false;
    private boolean opened = false;
    private long totalBytesRead;

    public DataSourceInputStream(DataSource dataSource2, DataSpec dataSpec2) {
        this.dataSource = dataSource2;
        this.dataSpec = dataSpec2;
        this.singleByteArray = new byte[1];
    }

    public long bytesRead() {
        return this.totalBytesRead;
    }

    public void open() throws IOException {
        checkOpened();
    }

    public int read() throws IOException {
        if (read(this.singleByteArray) == -1) {
            return -1;
        }
        return this.singleByteArray[0] & UnsignedBytes.MAX_VALUE;
    }

    public int read(@NonNull byte[] buffer) throws IOException {
        return read(buffer, 0, buffer.length);
    }

    public int read(@NonNull byte[] buffer, int offset, int length) throws IOException {
        Assertions.checkState(!this.closed);
        checkOpened();
        int bytesRead = this.dataSource.read(buffer, offset, length);
        if (bytesRead == -1) {
            return -1;
        }
        this.totalBytesRead += (long) bytesRead;
        return bytesRead;
    }

    public void close() throws IOException {
        if (!this.closed) {
            this.dataSource.close();
            this.closed = true;
        }
    }

    private void checkOpened() throws IOException {
        if (!this.opened) {
            this.dataSource.open(this.dataSpec);
            this.opened = true;
        }
    }
}
