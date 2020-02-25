package com.google.android.exoplayer2.extractor.wav;

import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.util.Util;

final class WavHeader implements SeekMap {
    private final int averageBytesPerSecond;
    private final int bitsPerSample;
    private final int blockAlignment;
    private final int encoding;
    private final int numChannels;
    private final int sampleRateHz;
    private long dataSize;
    private long dataStartPosition;

    public WavHeader(int numChannels2, int sampleRateHz2, int averageBytesPerSecond2, int blockAlignment2, int bitsPerSample2, int encoding2) {
        this.numChannels = numChannels2;
        this.sampleRateHz = sampleRateHz2;
        this.averageBytesPerSecond = averageBytesPerSecond2;
        this.blockAlignment = blockAlignment2;
        this.bitsPerSample = bitsPerSample2;
        this.encoding = encoding2;
    }

    public void setDataBounds(long dataStartPosition2, long dataSize2) {
        this.dataStartPosition = dataStartPosition2;
        this.dataSize = dataSize2;
    }

    public long getDataLimit() {
        if (hasDataBounds()) {
            return this.dataStartPosition + this.dataSize;
        }
        return -1;
    }

    public boolean hasDataBounds() {
        return (this.dataStartPosition == 0 || this.dataSize == 0) ? false : true;
    }

    public boolean isSeekable() {
        return true;
    }

    public long getDurationUs() {
        return (1000000 * (this.dataSize / ((long) this.blockAlignment))) / ((long) this.sampleRateHz);
    }

    public SeekMap.SeekPoints getSeekPoints(long timeUs) {
        int i = this.blockAlignment;
        long positionOffset = Util.constrainValue((((((long) this.averageBytesPerSecond) * timeUs) / 1000000) / ((long) i)) * ((long) i), 0, this.dataSize - ((long) i));
        long seekPosition = this.dataStartPosition + positionOffset;
        long seekTimeUs = getTimeUs(seekPosition);
        SeekPoint seekPoint = new SeekPoint(seekTimeUs, seekPosition);
        if (seekTimeUs < timeUs) {
            long j = this.dataSize;
            int i2 = this.blockAlignment;
            if (positionOffset != j - ((long) i2)) {
                long secondSeekPosition = ((long) i2) + seekPosition;
                return new SeekMap.SeekPoints(seekPoint, new SeekPoint(getTimeUs(secondSeekPosition), secondSeekPosition));
            }
        }
        return new SeekMap.SeekPoints(seekPoint);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.Math.max(long, long):long}
     arg types: [int, long]
     candidates:
      ClspMth{java.lang.Math.max(double, double):double}
      ClspMth{java.lang.Math.max(int, int):int}
      ClspMth{java.lang.Math.max(float, float):float}
      ClspMth{java.lang.Math.max(long, long):long} */
    public long getTimeUs(long position) {
        return (1000000 * Math.max(0L, position - this.dataStartPosition)) / ((long) this.averageBytesPerSecond);
    }

    public int getBytesPerFrame() {
        return this.blockAlignment;
    }

    public int getBitrate() {
        return this.sampleRateHz * this.bitsPerSample * this.numChannels;
    }

    public int getSampleRateHz() {
        return this.sampleRateHz;
    }

    public int getNumChannels() {
        return this.numChannels;
    }

    public int getEncoding() {
        return this.encoding;
    }
}
