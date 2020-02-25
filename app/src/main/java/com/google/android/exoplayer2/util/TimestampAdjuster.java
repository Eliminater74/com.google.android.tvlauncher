package com.google.android.exoplayer2.util;

import com.google.android.exoplayer2.C0841C;

public final class TimestampAdjuster {
    public static final long DO_NOT_OFFSET = Long.MAX_VALUE;
    private static final long MAX_PTS_PLUS_ONE = 8589934592L;
    private long firstSampleTimestampUs;
    private volatile long lastSampleTimestampUs = C0841C.TIME_UNSET;
    private long timestampOffsetUs;

    public TimestampAdjuster(long firstSampleTimestampUs2) {
        setFirstSampleTimestampUs(firstSampleTimestampUs2);
    }

    public static long ptsToUs(long pts) {
        return (1000000 * pts) / 90000;
    }

    public static long usToPts(long us) {
        return (90000 * us) / 1000000;
    }

    public long getFirstSampleTimestampUs() {
        return this.firstSampleTimestampUs;
    }

    public synchronized void setFirstSampleTimestampUs(long firstSampleTimestampUs2) {
        Assertions.checkState(this.lastSampleTimestampUs == C0841C.TIME_UNSET);
        this.firstSampleTimestampUs = firstSampleTimestampUs2;
    }

    public long getLastAdjustedTimestampUs() {
        if (this.lastSampleTimestampUs != C0841C.TIME_UNSET) {
            return this.timestampOffsetUs + this.lastSampleTimestampUs;
        }
        long j = this.firstSampleTimestampUs;
        if (j != Long.MAX_VALUE) {
            return j;
        }
        return C0841C.TIME_UNSET;
    }

    public long getTimestampOffsetUs() {
        if (this.firstSampleTimestampUs == Long.MAX_VALUE) {
            return 0;
        }
        if (this.lastSampleTimestampUs == C0841C.TIME_UNSET) {
            return C0841C.TIME_UNSET;
        }
        return this.timestampOffsetUs;
    }

    public void reset() {
        this.lastSampleTimestampUs = C0841C.TIME_UNSET;
    }

    public long adjustTsTimestamp(long pts90Khz) {
        long j;
        if (pts90Khz == C0841C.TIME_UNSET) {
            return C0841C.TIME_UNSET;
        }
        if (this.lastSampleTimestampUs != C0841C.TIME_UNSET) {
            long lastPts = usToPts(this.lastSampleTimestampUs);
            long closestWrapCount = (4294967296L + lastPts) / MAX_PTS_PLUS_ONE;
            long ptsWrapBelow = ((closestWrapCount - 1) * MAX_PTS_PLUS_ONE) + pts90Khz;
            long ptsWrapAbove = (MAX_PTS_PLUS_ONE * closestWrapCount) + pts90Khz;
            if (Math.abs(ptsWrapBelow - lastPts) < Math.abs(ptsWrapAbove - lastPts)) {
                j = ptsWrapBelow;
            } else {
                j = ptsWrapAbove;
            }
            pts90Khz = j;
        }
        return adjustSampleTimestamp(ptsToUs(pts90Khz));
    }

    public long adjustSampleTimestamp(long timeUs) {
        if (timeUs == C0841C.TIME_UNSET) {
            return C0841C.TIME_UNSET;
        }
        if (this.lastSampleTimestampUs != C0841C.TIME_UNSET) {
            this.lastSampleTimestampUs = timeUs;
        } else {
            long j = this.firstSampleTimestampUs;
            if (j != Long.MAX_VALUE) {
                this.timestampOffsetUs = j - timeUs;
            }
            synchronized (this) {
                this.lastSampleTimestampUs = timeUs;
                notifyAll();
            }
        }
        return this.timestampOffsetUs + timeUs;
    }

    public synchronized void waitUntilInitialized() throws InterruptedException {
        while (this.lastSampleTimestampUs == C0841C.TIME_UNSET) {
            wait();
        }
    }
}
