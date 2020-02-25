package com.google.android.exoplayer2.audio;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.util.Assertions;

import java.nio.ByteBuffer;
import java.util.Arrays;

final class ChannelMappingAudioProcessor extends BaseAudioProcessor {
    private boolean active;
    @Nullable
    private int[] outputChannels;
    @Nullable
    private int[] pendingOutputChannels;

    ChannelMappingAudioProcessor() {
    }

    public void setChannelMap(@Nullable int[] outputChannels2) {
        this.pendingOutputChannels = outputChannels2;
    }

    public boolean configure(int sampleRateHz, int channelCount, int encoding) throws AudioProcessor.UnhandledFormatException {
        boolean outputChannelsChanged = !Arrays.equals(this.pendingOutputChannels, this.outputChannels);
        this.outputChannels = this.pendingOutputChannels;
        int[] outputChannels2 = this.outputChannels;
        if (outputChannels2 == null) {
            this.active = false;
            return outputChannelsChanged;
        } else if (encoding != 2) {
            throw new AudioProcessor.UnhandledFormatException(sampleRateHz, channelCount, encoding);
        } else if (!outputChannelsChanged && !setInputFormat(sampleRateHz, channelCount, encoding)) {
            return false;
        } else {
            this.active = channelCount != outputChannels2.length;
            int i = 0;
            while (i < outputChannels2.length) {
                int channelIndex = outputChannels2[i];
                if (channelIndex < channelCount) {
                    this.active |= channelIndex != i;
                    i++;
                } else {
                    throw new AudioProcessor.UnhandledFormatException(sampleRateHz, channelCount, encoding);
                }
            }
            return true;
        }
    }

    public boolean isActive() {
        return this.active;
    }

    public int getOutputChannelCount() {
        int[] iArr = this.outputChannels;
        return iArr == null ? this.channelCount : iArr.length;
    }

    public void queueInput(ByteBuffer inputBuffer) {
        int[] outputChannels2 = (int[]) Assertions.checkNotNull(this.outputChannels);
        int position = inputBuffer.position();
        int limit = inputBuffer.limit();
        ByteBuffer buffer = replaceOutputBuffer(outputChannels2.length * ((limit - position) / (this.channelCount * 2)) * 2);
        while (position < limit) {
            for (int channelIndex : outputChannels2) {
                buffer.putShort(inputBuffer.getShort((channelIndex * 2) + position));
            }
            position += this.channelCount * 2;
        }
        inputBuffer.position(limit);
        buffer.flip();
    }

    /* access modifiers changed from: protected */
    public void onReset() {
        this.outputChannels = null;
        this.pendingOutputChannels = null;
        this.active = false;
    }
}
