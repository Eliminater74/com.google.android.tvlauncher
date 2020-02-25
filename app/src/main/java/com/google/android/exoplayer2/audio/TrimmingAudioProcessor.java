package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.util.Util;

import java.nio.ByteBuffer;

final class TrimmingAudioProcessor extends BaseAudioProcessor {
    private static final int OUTPUT_ENCODING = 2;
    private int bytesPerFrame;
    private byte[] endBuffer = Util.EMPTY_BYTE_ARRAY;
    private int endBufferSize;
    private boolean isActive;
    private int pendingTrimStartBytes;
    private boolean receivedInputSinceConfigure;
    private int trimEndFrames;
    private int trimStartFrames;
    private long trimmedFrameCount;

    public void setTrimFrameCount(int trimStartFrames2, int trimEndFrames2) {
        this.trimStartFrames = trimStartFrames2;
        this.trimEndFrames = trimEndFrames2;
    }

    public void resetTrimmedFrameCount() {
        this.trimmedFrameCount = 0;
    }

    public long getTrimmedFrameCount() {
        return this.trimmedFrameCount;
    }

    public boolean configure(int sampleRateHz, int channelCount, int encoding) throws AudioProcessor.UnhandledFormatException {
        if (encoding == 2) {
            int i = this.endBufferSize;
            if (i > 0) {
                this.trimmedFrameCount += (long) (i / this.bytesPerFrame);
            }
            this.bytesPerFrame = Util.getPcmFrameSize(2, channelCount);
            int i2 = this.trimEndFrames;
            int i3 = this.bytesPerFrame;
            this.endBuffer = new byte[(i2 * i3)];
            this.endBufferSize = 0;
            int i4 = this.trimStartFrames;
            this.pendingTrimStartBytes = i3 * i4;
            boolean wasActive = this.isActive;
            this.isActive = (i4 == 0 && i2 == 0) ? false : true;
            this.receivedInputSinceConfigure = false;
            setInputFormat(sampleRateHz, channelCount, encoding);
            if (wasActive != this.isActive) {
                return true;
            }
            return false;
        }
        throw new AudioProcessor.UnhandledFormatException(sampleRateHz, channelCount, encoding);
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void queueInput(ByteBuffer inputBuffer) {
        int position = inputBuffer.position();
        int limit = inputBuffer.limit();
        int remaining = limit - position;
        if (remaining != 0) {
            this.receivedInputSinceConfigure = true;
            int trimBytes = Math.min(remaining, this.pendingTrimStartBytes);
            this.trimmedFrameCount += (long) (trimBytes / this.bytesPerFrame);
            this.pendingTrimStartBytes -= trimBytes;
            inputBuffer.position(position + trimBytes);
            if (this.pendingTrimStartBytes <= 0) {
                int remaining2 = remaining - trimBytes;
                int remainingBytesToOutput = (this.endBufferSize + remaining2) - this.endBuffer.length;
                ByteBuffer buffer = replaceOutputBuffer(remainingBytesToOutput);
                int endBufferBytesToOutput = Util.constrainValue(remainingBytesToOutput, 0, this.endBufferSize);
                buffer.put(this.endBuffer, 0, endBufferBytesToOutput);
                int inputBufferBytesToOutput = Util.constrainValue(remainingBytesToOutput - endBufferBytesToOutput, 0, remaining2);
                inputBuffer.limit(inputBuffer.position() + inputBufferBytesToOutput);
                buffer.put(inputBuffer);
                inputBuffer.limit(limit);
                int remaining3 = remaining2 - inputBufferBytesToOutput;
                this.endBufferSize -= endBufferBytesToOutput;
                byte[] bArr = this.endBuffer;
                System.arraycopy(bArr, endBufferBytesToOutput, bArr, 0, this.endBufferSize);
                inputBuffer.get(this.endBuffer, this.endBufferSize, remaining3);
                this.endBufferSize += remaining3;
                buffer.flip();
            }
        }
    }

    public ByteBuffer getOutput() {
        int i;
        if (super.isEnded() && (i = this.endBufferSize) > 0) {
            replaceOutputBuffer(i).put(this.endBuffer, 0, this.endBufferSize).flip();
            this.endBufferSize = 0;
        }
        return super.getOutput();
    }

    public boolean isEnded() {
        return super.isEnded() && this.endBufferSize == 0;
    }

    /* access modifiers changed from: protected */
    public void onFlush() {
        if (this.receivedInputSinceConfigure) {
            this.pendingTrimStartBytes = 0;
        }
        this.endBufferSize = 0;
    }

    /* access modifiers changed from: protected */
    public void onReset() {
        this.endBuffer = Util.EMPTY_BYTE_ARRAY;
    }
}
