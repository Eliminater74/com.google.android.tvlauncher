package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.common.primitives.UnsignedBytes;
import java.nio.ByteBuffer;

final class ResamplingAudioProcessor extends BaseAudioProcessor {
    ResamplingAudioProcessor() {
    }

    public boolean configure(int sampleRateHz, int channelCount, int encoding) throws AudioProcessor.UnhandledFormatException {
        if (encoding == 3 || encoding == 2 || encoding == Integer.MIN_VALUE || encoding == 1073741824) {
            return setInputFormat(sampleRateHz, channelCount, encoding);
        }
        throw new AudioProcessor.UnhandledFormatException(sampleRateHz, channelCount, encoding);
    }

    public boolean isActive() {
        return (this.encoding == 0 || this.encoding == 2) ? false : true;
    }

    public int getOutputEncoding() {
        return 2;
    }

    public void queueInput(ByteBuffer inputBuffer) {
        int resampledSize;
        int position = inputBuffer.position();
        int limit = inputBuffer.limit();
        int size = limit - position;
        int i = this.encoding;
        if (i == Integer.MIN_VALUE) {
            resampledSize = (size / 3) * 2;
        } else if (i == 3) {
            resampledSize = size * 2;
        } else if (i == 1073741824) {
            resampledSize = size / 2;
        } else {
            throw new IllegalStateException();
        }
        ByteBuffer buffer = replaceOutputBuffer(resampledSize);
        int i2 = this.encoding;
        if (i2 == Integer.MIN_VALUE) {
            for (int i3 = position; i3 < limit; i3 += 3) {
                buffer.put(inputBuffer.get(i3 + 1));
                buffer.put(inputBuffer.get(i3 + 2));
            }
        } else if (i2 == 3) {
            for (int i4 = position; i4 < limit; i4++) {
                buffer.put((byte) 0);
                buffer.put((byte) ((inputBuffer.get(i4) & UnsignedBytes.MAX_VALUE) + UnsignedBytes.MAX_POWER_OF_TWO));
            }
        } else if (i2 == 1073741824) {
            for (int i5 = position; i5 < limit; i5 += 4) {
                buffer.put(inputBuffer.get(i5 + 2));
                buffer.put(inputBuffer.get(i5 + 3));
            }
        } else {
            throw new IllegalStateException();
        }
        inputBuffer.position(inputBuffer.limit());
        buffer.flip();
    }
}
