package com.google.android.exoplayer2.decoder;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SimpleOutputBuffer extends OutputBuffer {
    public ByteBuffer data;
    private final SimpleDecoder<?, SimpleOutputBuffer, ?> owner;

    public SimpleOutputBuffer(SimpleDecoder<?, SimpleOutputBuffer, ?> owner2) {
        this.owner = owner2;
    }

    public ByteBuffer init(long timeUs, int size) {
        this.timeUs = timeUs;
        ByteBuffer byteBuffer = this.data;
        if (byteBuffer == null || byteBuffer.capacity() < size) {
            this.data = ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
        }
        this.data.position(0);
        this.data.limit(size);
        return this.data;
    }

    public void clear() {
        super.clear();
        ByteBuffer byteBuffer = this.data;
        if (byteBuffer != null) {
            byteBuffer.clear();
        }
    }

    public void release() {
        this.owner.releaseOutputBuffer(this);
    }
}
