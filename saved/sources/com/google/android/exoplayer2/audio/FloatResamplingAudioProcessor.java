package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import java.nio.ByteBuffer;

final class FloatResamplingAudioProcessor extends BaseAudioProcessor {
    private static final int FLOAT_NAN_AS_INT = Float.floatToIntBits(Float.NaN);
    private static final double PCM_32_BIT_INT_TO_PCM_32_BIT_FLOAT_FACTOR = 4.656612875245797E-10d;

    FloatResamplingAudioProcessor() {
    }

    public boolean configure(int sampleRateHz, int channelCount, int encoding) throws AudioProcessor.UnhandledFormatException {
        if (Util.isEncodingHighResolutionIntegerPcm(encoding)) {
            return setInputFormat(sampleRateHz, channelCount, encoding);
        }
        throw new AudioProcessor.UnhandledFormatException(sampleRateHz, channelCount, encoding);
    }

    public boolean isActive() {
        return Util.isEncodingHighResolutionIntegerPcm(this.encoding);
    }

    public int getOutputEncoding() {
        return 4;
    }

    public void queueInput(ByteBuffer inputBuffer) {
        boolean isInput32Bit = this.encoding == 1073741824;
        int position = inputBuffer.position();
        int limit = inputBuffer.limit();
        int size = limit - position;
        ByteBuffer buffer = replaceOutputBuffer(isInput32Bit ? size : (size / 3) * 4);
        if (isInput32Bit) {
            for (int i = position; i < limit; i += 4) {
                writePcm32BitFloat((inputBuffer.get(i) & 255) | ((inputBuffer.get(i + 1) & UnsignedBytes.MAX_VALUE) << 8) | ((inputBuffer.get(i + 2) & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((inputBuffer.get(i + 3) & UnsignedBytes.MAX_VALUE) << Ascii.CAN), buffer);
            }
        } else {
            for (int i2 = position; i2 < limit; i2 += 3) {
                writePcm32BitFloat(((inputBuffer.get(i2) & UnsignedBytes.MAX_VALUE) << 8) | ((inputBuffer.get(i2 + 1) & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((inputBuffer.get(i2 + 2) & UnsignedBytes.MAX_VALUE) << Ascii.CAN), buffer);
            }
        }
        inputBuffer.position(inputBuffer.limit());
        buffer.flip();
    }

    private static void writePcm32BitFloat(int pcm32BitInt, ByteBuffer buffer) {
        double d = (double) pcm32BitInt;
        Double.isNaN(d);
        int floatBits = Float.floatToIntBits((float) (d * PCM_32_BIT_INT_TO_PCM_32_BIT_FLOAT_FACTOR));
        if (floatBits == FLOAT_NAN_AS_INT) {
            floatBits = Float.floatToIntBits(0.0f);
        }
        buffer.putInt(floatBits);
    }
}
