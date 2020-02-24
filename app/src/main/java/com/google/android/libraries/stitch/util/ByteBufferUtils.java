package com.google.android.libraries.stitch.util;

import com.google.common.primitives.UnsignedBytes;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public final class ByteBufferUtils {
    public static ByteBuffer fromFile(File f, boolean touchFile) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(f.getPath(), "rw");
        try {
            FileChannel file = raf.getChannel();
            ByteBuffer buffer = file.map(FileChannel.MapMode.READ_ONLY, 0, file.size());
            if (touchFile) {
                f.setLastModified(System.currentTimeMillis());
            }
            return (ByteBuffer) buffer.position(0);
        } finally {
            raf.close();
        }
    }

    public static ByteBuffer fromByteArray(byte[] b) {
        return (ByteBuffer) ByteBuffer.allocateDirect(b.length).put(b).position(0);
    }

    public static ByteBuffer fromByteArrayJavaOnly(byte[] b) {
        return ByteBuffer.wrap(b);
    }

    public static byte[] toByteArray(ByteBuffer b) {
        if (b == null) {
            return new byte[0];
        }
        if (b.hasArray()) {
            int arrayOffset = b.arrayOffset();
            if (arrayOffset == 0 && b.array().length == b.limit()) {
                return b.array();
            }
            return Arrays.copyOfRange(b.array(), arrayOffset, b.limit() + arrayOffset);
        }
        byte[] ba = new byte[b.limit()];
        int oldPosition = b.position();
        b.position(0);
        b.get(ba);
        b.position(oldPosition);
        return ba;
    }

    @Deprecated
    public static InputStream toInputStream(ByteBuffer b) {
        b.position(0);
        return new ByteBufferInputStream(b);
    }

    public static void toFile(ByteBuffer b, String fileName) throws IOException {
        FileChannel file = new RandomAccessFile(fileName, "rw").getChannel();
        try {
            file.write(b);
            b.position(0);
        } finally {
            file.close();
        }
    }

    private static final class ByteBufferInputStream extends InputStream {
        private final ByteBuffer mBuffer;
        private int markedPos = 0;

        public ByteBufferInputStream(ByteBuffer buffer) {
            this.mBuffer = buffer;
        }

        public int read() throws IOException {
            if (!this.mBuffer.hasRemaining()) {
                return -1;
            }
            return this.mBuffer.get() & UnsignedBytes.MAX_VALUE;
        }

        public void mark(int readlimit) {
            this.markedPos = this.mBuffer.position();
        }

        public boolean markSupported() {
            return true;
        }

        public int available() throws IOException {
            return this.mBuffer.remaining();
        }

        public long skip(long byteCount) throws IOException {
            int skipped = (int) Math.min(byteCount, (long) this.mBuffer.remaining());
            ByteBuffer byteBuffer = this.mBuffer;
            byteBuffer.position(byteBuffer.position() + skipped);
            return (long) skipped;
        }

        public void reset() throws IOException {
            this.mBuffer.position(this.markedPos);
        }

        public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
            if (!this.mBuffer.hasRemaining()) {
                return -1;
            }
            int result = Math.min(this.mBuffer.remaining(), byteCount);
            this.mBuffer.get(buffer, byteOffset, result);
            return result;
        }
    }
}
