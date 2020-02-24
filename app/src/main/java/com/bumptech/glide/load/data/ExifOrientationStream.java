package com.bumptech.glide.load.data;

import android.support.annotation.NonNull;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class ExifOrientationStream extends FilterInputStream {
    private static final byte[] EXIF_SEGMENT = {-1, -31, 0, Ascii.f150FS, 69, 120, 105, 102, 0, 0, 77, 77, 0, 0, 0, 0, 0, 8, 0, 1, 1, Ascii.DC2, 0, 2, 0, 0, 0, 1, 0};
    private static final int ORIENTATION_POSITION = (SEGMENT_LENGTH + 2);
    private static final int SEGMENT_LENGTH = EXIF_SEGMENT.length;
    private static final int SEGMENT_START_POSITION = 2;
    private final byte orientation;
    private int position;

    public ExifOrientationStream(InputStream in, int orientation2) {
        super(in);
        if (orientation2 < -1 || orientation2 > 8) {
            StringBuilder sb = new StringBuilder(43);
            sb.append("Cannot add invalid orientation: ");
            sb.append(orientation2);
            throw new IllegalArgumentException(sb.toString());
        }
        this.orientation = (byte) orientation2;
    }

    public boolean markSupported() {
        return false;
    }

    public void mark(int readLimit) {
        throw new UnsupportedOperationException();
    }

    public int read() throws IOException {
        int result;
        int i;
        int i2 = this.position;
        if (i2 < 2 || i2 > (i = ORIENTATION_POSITION)) {
            result = super.read();
        } else if (i2 == i) {
            result = this.orientation;
        } else {
            result = EXIF_SEGMENT[i2 - 2] & UnsignedBytes.MAX_VALUE;
        }
        if (result != -1) {
            this.position++;
        }
        return result;
    }

    public int read(@NonNull byte[] buffer, int byteOffset, int byteCount) throws IOException {
        int read;
        int read2 = this.position;
        int i = ORIENTATION_POSITION;
        if (read2 > i) {
            read = super.read(buffer, byteOffset, byteCount);
        } else if (read2 == i) {
            buffer[byteOffset] = this.orientation;
            read = 1;
        } else if (read2 < 2) {
            read = super.read(buffer, byteOffset, 2 - read2);
        } else {
            read = Math.min(i - read2, byteCount);
            System.arraycopy(EXIF_SEGMENT, this.position - 2, buffer, byteOffset, read);
        }
        if (read > 0) {
            this.position += read;
        }
        return read;
    }

    public long skip(long byteCount) throws IOException {
        long skipped = super.skip(byteCount);
        if (skipped > 0) {
            this.position = (int) (((long) this.position) + skipped);
        }
        return skipped;
    }

    public void reset() throws IOException {
        throw new UnsupportedOperationException();
    }
}
