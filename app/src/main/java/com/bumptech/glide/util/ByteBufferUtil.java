package com.bumptech.glide.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicReference;

public final class ByteBufferUtil {
    private static final AtomicReference<byte[]> BUFFER_REF = new AtomicReference<>();
    private static final int BUFFER_SIZE = 16384;

    private ByteBufferUtil() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0051 A[SYNTHETIC, Splitter:B:25:0x0051] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0058 A[SYNTHETIC, Splitter:B:29:0x0058] */
    @android.support.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.nio.ByteBuffer fromFile(@android.support.annotation.NonNull java.io.File r10) throws java.io.IOException {
        /*
            r0 = 0
            r1 = 0
            long r2 = r10.length()     // Catch:{ all -> 0x004e }
            r4 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 > 0) goto L_0x0046
            r4 = 0
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 == 0) goto L_0x003e
            java.io.RandomAccessFile r4 = new java.io.RandomAccessFile     // Catch:{ all -> 0x004e }
            java.lang.String r5 = "r"
            r4.<init>(r10, r5)     // Catch:{ all -> 0x004e }
            r0 = r4
            java.nio.channels.FileChannel r4 = r0.getChannel()     // Catch:{ all -> 0x004e }
            java.nio.channels.FileChannel$MapMode r5 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch:{ all -> 0x003a }
            r6 = 0
            r8 = r2
            java.nio.MappedByteBuffer r1 = r4.map(r5, r6, r8)     // Catch:{ all -> 0x003a }
            java.nio.MappedByteBuffer r1 = r1.load()     // Catch:{ all -> 0x003a }
            r4.close()     // Catch:{ IOException -> 0x0032 }
            goto L_0x0033
        L_0x0032:
            r5 = move-exception
        L_0x0033:
            r0.close()     // Catch:{ IOException -> 0x0038 }
            goto L_0x0039
        L_0x0038:
            r5 = move-exception
        L_0x0039:
            return r1
        L_0x003a:
            r1 = move-exception
            r2 = r1
            r1 = r4
            goto L_0x004f
        L_0x003e:
            java.io.IOException r4 = new java.io.IOException     // Catch:{ all -> 0x004e }
            java.lang.String r5 = "File unsuitable for memory mapping"
            r4.<init>(r5)     // Catch:{ all -> 0x004e }
            throw r4     // Catch:{ all -> 0x004e }
        L_0x0046:
            java.io.IOException r4 = new java.io.IOException     // Catch:{ all -> 0x004e }
            java.lang.String r5 = "File too large to map into memory"
            r4.<init>(r5)     // Catch:{ all -> 0x004e }
            throw r4     // Catch:{ all -> 0x004e }
        L_0x004e:
            r2 = move-exception
        L_0x004f:
            if (r1 == 0) goto L_0x0056
            r1.close()     // Catch:{ IOException -> 0x0055 }
            goto L_0x0056
        L_0x0055:
            r3 = move-exception
        L_0x0056:
            if (r0 == 0) goto L_0x005d
            r0.close()     // Catch:{ IOException -> 0x005c }
            goto L_0x005d
        L_0x005c:
            r3 = move-exception
        L_0x005d:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.util.ByteBufferUtil.fromFile(java.io.File):java.nio.ByteBuffer");
    }

    public static void toFile(@NonNull ByteBuffer buffer, @NonNull File file) throws IOException {
        buffer.position(0);
        RandomAccessFile raf = null;
        FileChannel channel = null;
        try {
            raf = new RandomAccessFile(file, "rw");
            channel = raf.getChannel();
            channel.write(buffer);
            channel.force(false);
            channel.close();
            raf.close();
            try {
                channel.close();
            } catch (IOException e) {
            }
            try {
                raf.close();
            } catch (IOException e2) {
            }
        } catch (Throwable th) {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e3) {
                }
            }
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e4) {
                }
            }
            throw th;
        }
    }

    public static void toStream(@NonNull ByteBuffer byteBuffer, @NonNull OutputStream os) throws IOException {
        SafeArray safeArray = getSafeArray(byteBuffer);
        if (safeArray != null) {
            os.write(safeArray.data, safeArray.offset, safeArray.offset + safeArray.limit);
            return;
        }
        byte[] buffer = BUFFER_REF.getAndSet(null);
        if (buffer == null) {
            buffer = new byte[16384];
        }
        while (byteBuffer.remaining() > 0) {
            int toRead = Math.min(byteBuffer.remaining(), buffer.length);
            byteBuffer.get(buffer, 0, toRead);
            os.write(buffer, 0, toRead);
        }
        BUFFER_REF.set(buffer);
    }

    /* JADX INFO: Multiple debug info for r1v0 java.nio.ByteBuffer: [D('result' byte[]), D('toCopy' java.nio.ByteBuffer)] */
    /* JADX INFO: Multiple debug info for r1v1 byte[]: [D('toCopy' java.nio.ByteBuffer), D('result' byte[])] */
    @NonNull
    public static byte[] toBytes(@NonNull ByteBuffer byteBuffer) {
        SafeArray safeArray = getSafeArray(byteBuffer);
        if (safeArray != null && safeArray.offset == 0 && safeArray.limit == safeArray.data.length) {
            return byteBuffer.array();
        }
        ByteBuffer toCopy = byteBuffer.asReadOnlyBuffer();
        byte[] result = new byte[toCopy.limit()];
        toCopy.position(0);
        toCopy.get(result);
        return result;
    }

    @NonNull
    public static InputStream toStream(@NonNull ByteBuffer buffer) {
        return new ByteBufferStream(buffer);
    }

    @NonNull
    public static ByteBuffer fromStream(@NonNull InputStream stream) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream(16384);
        byte[] buffer = BUFFER_REF.getAndSet(null);
        if (buffer == null) {
            buffer = new byte[16384];
        }
        while (true) {
            int read = stream.read(buffer);
            int n = read;
            if (read >= 0) {
                outStream.write(buffer, 0, n);
            } else {
                BUFFER_REF.set(buffer);
                byte[] bytes = outStream.toByteArray();
                return (ByteBuffer) ByteBuffer.allocateDirect(bytes.length).put(bytes).position(0);
            }
        }
    }

    @Nullable
    private static SafeArray getSafeArray(@NonNull ByteBuffer byteBuffer) {
        if (byteBuffer.isReadOnly() || !byteBuffer.hasArray()) {
            return null;
        }
        return new SafeArray(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.limit());
    }

    static final class SafeArray {
        final byte[] data;
        final int limit;
        final int offset;

        SafeArray(@NonNull byte[] data2, int offset2, int limit2) {
            this.data = data2;
            this.offset = offset2;
            this.limit = limit2;
        }
    }

    private static class ByteBufferStream extends InputStream {
        private static final int UNSET = -1;
        @NonNull
        private final ByteBuffer byteBuffer;
        private int markPos = -1;

        ByteBufferStream(@NonNull ByteBuffer byteBuffer2) {
            this.byteBuffer = byteBuffer2;
        }

        public int available() {
            return this.byteBuffer.remaining();
        }

        public int read() {
            if (!this.byteBuffer.hasRemaining()) {
                return -1;
            }
            return this.byteBuffer.get();
        }

        public synchronized void mark(int readLimit) {
            this.markPos = this.byteBuffer.position();
        }

        public boolean markSupported() {
            return true;
        }

        public int read(@NonNull byte[] buffer, int byteOffset, int byteCount) throws IOException {
            if (!this.byteBuffer.hasRemaining()) {
                return -1;
            }
            int toRead = Math.min(byteCount, available());
            this.byteBuffer.get(buffer, byteOffset, toRead);
            return toRead;
        }

        public synchronized void reset() throws IOException {
            if (this.markPos != -1) {
                this.byteBuffer.position(this.markPos);
            } else {
                throw new IOException("Cannot reset to unset mark position");
            }
        }

        public long skip(long byteCount) throws IOException {
            if (!this.byteBuffer.hasRemaining()) {
                return -1;
            }
            long toSkip = Math.min(byteCount, (long) available());
            ByteBuffer byteBuffer2 = this.byteBuffer;
            byteBuffer2.position((int) (((long) byteBuffer2.position()) + toSkip));
            return toSkip;
        }
    }
}
