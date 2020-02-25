package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.util.Assertions;

import java.io.IOException;

public final class ByteArrayDataSource extends BaseDataSource {
    private final byte[] data;
    private int bytesRemaining;
    private boolean opened;
    private int readPosition;
    @Nullable
    private Uri uri;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ByteArrayDataSource(byte[] data2) {
        super(false);
        boolean z = false;
        Assertions.checkNotNull(data2);
        Assertions.checkArgument(data2.length > 0 ? true : z);
        this.data = data2;
    }

    public long open(DataSpec dataSpec) throws IOException {
        this.uri = dataSpec.uri;
        transferInitializing(dataSpec);
        this.readPosition = (int) dataSpec.position;
        this.bytesRemaining = (int) (dataSpec.length == -1 ? ((long) this.data.length) - dataSpec.position : dataSpec.length);
        int i = this.bytesRemaining;
        if (i <= 0 || this.readPosition + i > this.data.length) {
            int i2 = this.readPosition;
            long j = dataSpec.length;
            int length = this.data.length;
            StringBuilder sb = new StringBuilder(77);
            sb.append("Unsatisfiable range: [");
            sb.append(i2);
            sb.append(", ");
            sb.append(j);
            sb.append("], length: ");
            sb.append(length);
            throw new IOException(sb.toString());
        }
        this.opened = true;
        transferStarted(dataSpec);
        return (long) this.bytesRemaining;
    }

    public int read(byte[] buffer, int offset, int readLength) throws IOException {
        if (readLength == 0) {
            return 0;
        }
        int i = this.bytesRemaining;
        if (i == 0) {
            return -1;
        }
        int readLength2 = Math.min(readLength, i);
        System.arraycopy(this.data, this.readPosition, buffer, offset, readLength2);
        this.readPosition += readLength2;
        this.bytesRemaining -= readLength2;
        bytesTransferred(readLength2);
        return readLength2;
    }

    @Nullable
    public Uri getUri() {
        return this.uri;
    }

    public void close() throws IOException {
        if (this.opened) {
            this.opened = false;
            transferEnded();
        }
        this.uri = null;
    }
}
