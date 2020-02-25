package com.google.android.exoplayer2.decoder;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;

public class DecoderInputBuffer extends Buffer {
    public static final int BUFFER_REPLACEMENT_MODE_DIRECT = 2;
    public static final int BUFFER_REPLACEMENT_MODE_DISABLED = 0;
    public static final int BUFFER_REPLACEMENT_MODE_NORMAL = 1;
    public final CryptoInfo cryptoInfo = new CryptoInfo();
    private final int bufferReplacementMode;
    public ByteBuffer data;
    public long timeUs;

    public DecoderInputBuffer(int bufferReplacementMode2) {
        this.bufferReplacementMode = bufferReplacementMode2;
    }

    public static DecoderInputBuffer newFlagsOnlyInstance() {
        return new DecoderInputBuffer(0);
    }

    public void ensureSpaceForWrite(int length) {
        ByteBuffer byteBuffer = this.data;
        if (byteBuffer == null) {
            this.data = createReplacementByteBuffer(length);
            return;
        }
        int capacity = byteBuffer.capacity();
        int position = this.data.position();
        int requiredCapacity = position + length;
        if (capacity < requiredCapacity) {
            ByteBuffer newData = createReplacementByteBuffer(requiredCapacity);
            if (position > 0) {
                this.data.position(0);
                this.data.limit(position);
                newData.put(this.data);
            }
            this.data = newData;
        }
    }

    public final boolean isFlagsOnly() {
        return this.data == null && this.bufferReplacementMode == 0;
    }

    public final boolean isEncrypted() {
        return getFlag(1073741824);
    }

    public final void flip() {
        this.data.flip();
    }

    public void clear() {
        super.clear();
        ByteBuffer byteBuffer = this.data;
        if (byteBuffer != null) {
            byteBuffer.clear();
        }
    }

    private ByteBuffer createReplacementByteBuffer(int requiredCapacity) {
        int i = this.bufferReplacementMode;
        if (i == 1) {
            return ByteBuffer.allocate(requiredCapacity);
        }
        if (i == 2) {
            return ByteBuffer.allocateDirect(requiredCapacity);
        }
        ByteBuffer byteBuffer = this.data;
        int currentCapacity = byteBuffer == null ? 0 : byteBuffer.capacity();
        StringBuilder sb = new StringBuilder(44);
        sb.append("Buffer too small (");
        sb.append(currentCapacity);
        sb.append(" < ");
        sb.append(requiredCapacity);
        sb.append(")");
        throw new IllegalStateException(sb.toString());
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface BufferReplacementMode {
    }
}
