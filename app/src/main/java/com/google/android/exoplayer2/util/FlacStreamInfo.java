package com.google.android.exoplayer2.util;

import android.support.p001v4.media.session.PlaybackStateCompat;

public final class FlacStreamInfo {
    public final int bitsPerSample;
    public final int channels;
    public final int maxBlockSize;
    public final int maxFrameSize;
    public final int minBlockSize;
    public final int minFrameSize;
    public final int sampleRate;
    public final long totalSamples;

    public FlacStreamInfo(byte[] data, int offset) {
        ParsableBitArray scratch = new ParsableBitArray(data);
        scratch.setPosition(offset * 8);
        this.minBlockSize = scratch.readBits(16);
        this.maxBlockSize = scratch.readBits(16);
        this.minFrameSize = scratch.readBits(24);
        this.maxFrameSize = scratch.readBits(24);
        this.sampleRate = scratch.readBits(20);
        this.channels = scratch.readBits(3) + 1;
        this.bitsPerSample = scratch.readBits(5) + 1;
        this.totalSamples = ((((long) scratch.readBits(4)) & 15) << 32) | (((long) scratch.readBits(32)) & 4294967295L);
    }

    public FlacStreamInfo(int minBlockSize2, int maxBlockSize2, int minFrameSize2, int maxFrameSize2, int sampleRate2, int channels2, int bitsPerSample2, long totalSamples2) {
        this.minBlockSize = minBlockSize2;
        this.maxBlockSize = maxBlockSize2;
        this.minFrameSize = minFrameSize2;
        this.maxFrameSize = maxFrameSize2;
        this.sampleRate = sampleRate2;
        this.channels = channels2;
        this.bitsPerSample = bitsPerSample2;
        this.totalSamples = totalSamples2;
    }

    public int maxDecodedFrameSize() {
        return this.maxBlockSize * this.channels * (this.bitsPerSample / 8);
    }

    public int bitRate() {
        return this.bitsPerSample * this.sampleRate;
    }

    public long durationUs() {
        return (this.totalSamples * 1000000) / ((long) this.sampleRate);
    }

    public long getSampleIndex(long timeUs) {
        return Util.constrainValue((((long) this.sampleRate) * timeUs) / 1000000, 0, this.totalSamples - 1);
    }

    /* JADX INFO: Multiple debug info for r0v3 long: [D('blockSize' long), D('approxBytesPerFrame' long)] */
    public long getApproxBytesPerFrame() {
        int i = this.maxFrameSize;
        if (i > 0) {
            return ((((long) i) + ((long) this.minFrameSize)) / 2) + 1;
        }
        int i2 = this.minBlockSize;
        return (((((long) this.channels) * ((i2 != this.maxBlockSize || i2 <= 0) ? PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM : (long) i2)) * ((long) this.bitsPerSample)) / 8) + 64;
    }
}
