package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.util.Util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;

public final class SilenceSkippingAudioProcessor extends BaseAudioProcessor {
    private static final long MINIMUM_SILENCE_DURATION_US = 150000;
    private static final long PADDING_SILENCE_US = 20000;
    private static final short SILENCE_THRESHOLD_LEVEL = 1024;
    private static final byte SILENCE_THRESHOLD_LEVEL_MSB = 4;
    private static final int STATE_MAYBE_SILENT = 1;
    private static final int STATE_NOISY = 0;
    private static final int STATE_SILENT = 2;
    private int bytesPerFrame;
    private boolean enabled;
    private boolean hasOutputNoise;
    private byte[] maybeSilenceBuffer = Util.EMPTY_BYTE_ARRAY;
    private int maybeSilenceBufferSize;
    private byte[] paddingBuffer = Util.EMPTY_BYTE_ARRAY;
    private int paddingSize;
    private long skippedFrames;
    private int state;

    public void setEnabled(boolean enabled2) {
        this.enabled = enabled2;
        flush();
    }

    public long getSkippedFrames() {
        return this.skippedFrames;
    }

    public boolean configure(int sampleRateHz, int channelCount, int encoding) throws AudioProcessor.UnhandledFormatException {
        if (encoding == 2) {
            this.bytesPerFrame = channelCount * 2;
            return setInputFormat(sampleRateHz, channelCount, encoding);
        }
        throw new AudioProcessor.UnhandledFormatException(sampleRateHz, channelCount, encoding);
    }

    public boolean isActive() {
        return super.isActive() && this.enabled;
    }

    public void queueInput(ByteBuffer inputBuffer) {
        while (inputBuffer.hasRemaining() && !hasPendingOutput()) {
            int i = this.state;
            if (i == 0) {
                processNoisy(inputBuffer);
            } else if (i == 1) {
                processMaybeSilence(inputBuffer);
            } else if (i == 2) {
                processSilence(inputBuffer);
            } else {
                throw new IllegalStateException();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onQueueEndOfStream() {
        int i = this.maybeSilenceBufferSize;
        if (i > 0) {
            output(this.maybeSilenceBuffer, i);
        }
        if (!this.hasOutputNoise) {
            this.skippedFrames += (long) (this.paddingSize / this.bytesPerFrame);
        }
    }

    /* access modifiers changed from: protected */
    public void onFlush() {
        if (isActive()) {
            int maybeSilenceBufferSize2 = durationUsToFrames(MINIMUM_SILENCE_DURATION_US) * this.bytesPerFrame;
            if (this.maybeSilenceBuffer.length != maybeSilenceBufferSize2) {
                this.maybeSilenceBuffer = new byte[maybeSilenceBufferSize2];
            }
            this.paddingSize = durationUsToFrames(PADDING_SILENCE_US) * this.bytesPerFrame;
            int length = this.paddingBuffer.length;
            int i = this.paddingSize;
            if (length != i) {
                this.paddingBuffer = new byte[i];
            }
        }
        this.state = 0;
        this.skippedFrames = 0;
        this.maybeSilenceBufferSize = 0;
        this.hasOutputNoise = false;
    }

    /* access modifiers changed from: protected */
    public void onReset() {
        this.enabled = false;
        this.paddingSize = 0;
        this.maybeSilenceBuffer = Util.EMPTY_BYTE_ARRAY;
        this.paddingBuffer = Util.EMPTY_BYTE_ARRAY;
    }

    private void processNoisy(ByteBuffer inputBuffer) {
        int limit = inputBuffer.limit();
        inputBuffer.limit(Math.min(limit, inputBuffer.position() + this.maybeSilenceBuffer.length));
        int noiseLimit = findNoiseLimit(inputBuffer);
        if (noiseLimit == inputBuffer.position()) {
            this.state = 1;
        } else {
            inputBuffer.limit(noiseLimit);
            output(inputBuffer);
        }
        inputBuffer.limit(limit);
    }

    private void processMaybeSilence(ByteBuffer inputBuffer) {
        int limit = inputBuffer.limit();
        int noisePosition = findNoisePosition(inputBuffer);
        int maybeSilenceInputSize = noisePosition - inputBuffer.position();
        byte[] bArr = this.maybeSilenceBuffer;
        int length = bArr.length;
        int i = this.maybeSilenceBufferSize;
        int maybeSilenceBufferRemaining = length - i;
        if (noisePosition >= limit || maybeSilenceInputSize >= maybeSilenceBufferRemaining) {
            int bytesToWrite = Math.min(maybeSilenceInputSize, maybeSilenceBufferRemaining);
            inputBuffer.limit(inputBuffer.position() + bytesToWrite);
            inputBuffer.get(this.maybeSilenceBuffer, this.maybeSilenceBufferSize, bytesToWrite);
            this.maybeSilenceBufferSize += bytesToWrite;
            int i2 = this.maybeSilenceBufferSize;
            byte[] bArr2 = this.maybeSilenceBuffer;
            if (i2 == bArr2.length) {
                if (this.hasOutputNoise) {
                    output(bArr2, this.paddingSize);
                    this.skippedFrames += (long) ((this.maybeSilenceBufferSize - (this.paddingSize * 2)) / this.bytesPerFrame);
                } else {
                    this.skippedFrames += (long) ((i2 - this.paddingSize) / this.bytesPerFrame);
                }
                updatePaddingBuffer(inputBuffer, this.maybeSilenceBuffer, this.maybeSilenceBufferSize);
                this.maybeSilenceBufferSize = 0;
                this.state = 2;
            }
            inputBuffer.limit(limit);
            return;
        }
        output(bArr, i);
        this.maybeSilenceBufferSize = 0;
        this.state = 0;
    }

    private void processSilence(ByteBuffer inputBuffer) {
        int limit = inputBuffer.limit();
        int noisyPosition = findNoisePosition(inputBuffer);
        inputBuffer.limit(noisyPosition);
        this.skippedFrames += (long) (inputBuffer.remaining() / this.bytesPerFrame);
        updatePaddingBuffer(inputBuffer, this.paddingBuffer, this.paddingSize);
        if (noisyPosition < limit) {
            output(this.paddingBuffer, this.paddingSize);
            this.state = 0;
            inputBuffer.limit(limit);
        }
    }

    private void output(byte[] data, int length) {
        replaceOutputBuffer(length).put(data, 0, length).flip();
        if (length > 0) {
            this.hasOutputNoise = true;
        }
    }

    private void output(ByteBuffer data) {
        int length = data.remaining();
        replaceOutputBuffer(length).put(data).flip();
        if (length > 0) {
            this.hasOutputNoise = true;
        }
    }

    private void updatePaddingBuffer(ByteBuffer input, byte[] buffer, int size) {
        int fromInputSize = Math.min(input.remaining(), this.paddingSize);
        int fromBufferSize = this.paddingSize - fromInputSize;
        System.arraycopy(buffer, size - fromBufferSize, this.paddingBuffer, 0, fromBufferSize);
        input.position(input.limit() - fromInputSize);
        input.get(this.paddingBuffer, fromBufferSize, fromInputSize);
    }

    private int durationUsToFrames(long durationUs) {
        return (int) ((((long) this.sampleRateHz) * durationUs) / 1000000);
    }

    private int findNoisePosition(ByteBuffer buffer) {
        for (int i = buffer.position() + 1; i < buffer.limit(); i += 2) {
            if (Math.abs((int) buffer.get(i)) > 4) {
                int i2 = this.bytesPerFrame;
                return i2 * (i / i2);
            }
        }
        return buffer.limit();
    }

    private int findNoiseLimit(ByteBuffer buffer) {
        for (int i = buffer.limit() - 1; i >= buffer.position(); i -= 2) {
            if (Math.abs((int) buffer.get(i)) > 4) {
                int i2 = this.bytesPerFrame;
                return ((i / i2) * i2) + i2;
            }
        }
        return buffer.position();
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    private @interface State {
    }
}
