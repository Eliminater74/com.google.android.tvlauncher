package com.google.android.exoplayer2.audio;

import android.support.annotation.CallSuper;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class BaseAudioProcessor implements AudioProcessor {
    private ByteBuffer buffer = EMPTY_BUFFER;
    protected int channelCount = -1;
    protected int encoding = -1;
    private boolean inputEnded;
    private ByteBuffer outputBuffer = EMPTY_BUFFER;
    protected int sampleRateHz = -1;

    public boolean isActive() {
        return this.sampleRateHz != -1;
    }

    public int getOutputChannelCount() {
        return this.channelCount;
    }

    public int getOutputEncoding() {
        return this.encoding;
    }

    public int getOutputSampleRateHz() {
        return this.sampleRateHz;
    }

    public final void queueEndOfStream() {
        this.inputEnded = true;
        onQueueEndOfStream();
    }

    @CallSuper
    public ByteBuffer getOutput() {
        ByteBuffer outputBuffer2 = this.outputBuffer;
        this.outputBuffer = EMPTY_BUFFER;
        return outputBuffer2;
    }

    @CallSuper
    public boolean isEnded() {
        return this.inputEnded && this.outputBuffer == EMPTY_BUFFER;
    }

    public final void flush() {
        this.outputBuffer = EMPTY_BUFFER;
        this.inputEnded = false;
        onFlush();
    }

    public final void reset() {
        flush();
        this.buffer = EMPTY_BUFFER;
        this.sampleRateHz = -1;
        this.channelCount = -1;
        this.encoding = -1;
        onReset();
    }

    /* access modifiers changed from: protected */
    public final boolean setInputFormat(int sampleRateHz2, int channelCount2, int encoding2) {
        if (sampleRateHz2 == this.sampleRateHz && channelCount2 == this.channelCount && encoding2 == this.encoding) {
            return false;
        }
        this.sampleRateHz = sampleRateHz2;
        this.channelCount = channelCount2;
        this.encoding = encoding2;
        return true;
    }

    /* access modifiers changed from: protected */
    public final ByteBuffer replaceOutputBuffer(int count) {
        if (this.buffer.capacity() < count) {
            this.buffer = ByteBuffer.allocateDirect(count).order(ByteOrder.nativeOrder());
        } else {
            this.buffer.clear();
        }
        ByteBuffer byteBuffer = this.buffer;
        this.outputBuffer = byteBuffer;
        return byteBuffer;
    }

    /* access modifiers changed from: protected */
    public final boolean hasPendingOutput() {
        return this.outputBuffer.hasRemaining();
    }

    /* access modifiers changed from: protected */
    public void onQueueEndOfStream() {
    }

    /* access modifiers changed from: protected */
    public void onFlush() {
    }

    /* access modifiers changed from: protected */
    public void onReset() {
    }
}
