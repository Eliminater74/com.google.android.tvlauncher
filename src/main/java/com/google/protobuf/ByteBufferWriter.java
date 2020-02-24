package com.google.protobuf;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

final class ByteBufferWriter {
    private static final ThreadLocal<SoftReference<byte[]>> BUFFER = new ThreadLocal<>();
    private static final float BUFFER_REALLOCATION_THRESHOLD = 0.5f;
    private static final long CHANNEL_FIELD_OFFSET = getChannelFieldOffset(FILE_OUTPUT_STREAM_CLASS);
    private static final Class<?> FILE_OUTPUT_STREAM_CLASS = safeGetClass("java.io.FileOutputStream");
    private static final int MAX_CACHED_BUFFER_SIZE = 16384;
    private static final int MIN_CACHED_BUFFER_SIZE = 1024;

    private ByteBufferWriter() {
    }

    static void clearCachedBuffer() {
        BUFFER.set(null);
    }

    static void write(ByteBuffer buffer, OutputStream output) throws IOException {
        int initialPos = buffer.position();
        try {
            if (buffer.hasArray()) {
                output.write(buffer.array(), buffer.arrayOffset() + buffer.position(), buffer.remaining());
            } else if (!writeToChannel(buffer, output)) {
                byte[] array = getOrCreateBuffer(buffer.remaining());
                while (buffer.hasRemaining()) {
                    int length = Math.min(buffer.remaining(), array.length);
                    buffer.get(array, 0, length);
                    output.write(array, 0, length);
                }
            }
        } finally {
            buffer.position(initialPos);
        }
    }

    private static byte[] getOrCreateBuffer(int requestedSize) {
        int requestedSize2 = Math.max(requestedSize, 1024);
        byte[] buffer = getBuffer();
        if (buffer == null || needToReallocate(requestedSize2, buffer.length)) {
            buffer = new byte[requestedSize2];
            if (requestedSize2 <= 16384) {
                setBuffer(buffer);
            }
        }
        return buffer;
    }

    private static boolean needToReallocate(int requestedSize, int bufferLength) {
        return bufferLength < requestedSize && ((float) bufferLength) < ((float) requestedSize) * BUFFER_REALLOCATION_THRESHOLD;
    }

    private static byte[] getBuffer() {
        SoftReference<byte[]> sr = BUFFER.get();
        if (sr == null) {
            return null;
        }
        return (byte[]) sr.get();
    }

    private static void setBuffer(byte[] value) {
        BUFFER.set(new SoftReference(value));
    }

    private static boolean writeToChannel(ByteBuffer buffer, OutputStream output) throws IOException {
        if (CHANNEL_FIELD_OFFSET < 0 || !FILE_OUTPUT_STREAM_CLASS.isInstance(output)) {
            return false;
        }
        WritableByteChannel channel = null;
        try {
            channel = (WritableByteChannel) UnsafeUtil.getObject(output, CHANNEL_FIELD_OFFSET);
        } catch (ClassCastException e) {
        }
        if (channel == null) {
            return false;
        }
        channel.write(buffer);
        return true;
    }

    private static Class<?> safeGetClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private static long getChannelFieldOffset(Class<?> clazz) {
        if (clazz == null) {
            return -1;
        }
        try {
            if (UnsafeUtil.hasUnsafeArrayOperations()) {
                return UnsafeUtil.objectFieldOffset(clazz.getDeclaredField("channel"));
            }
            return -1;
        } catch (Throwable th) {
            return -1;
        }
    }
}
