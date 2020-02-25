package com.google.android.exoplayer2.source.hls;

import android.util.SparseArray;

import com.google.android.exoplayer2.util.TimestampAdjuster;

public final class TimestampAdjusterProvider {
    private final SparseArray<TimestampAdjuster> timestampAdjusters = new SparseArray<>();

    public TimestampAdjuster getAdjuster(int discontinuitySequence) {
        TimestampAdjuster adjuster = this.timestampAdjusters.get(discontinuitySequence);
        if (adjuster != null) {
            return adjuster;
        }
        TimestampAdjuster adjuster2 = new TimestampAdjuster(Long.MAX_VALUE);
        this.timestampAdjusters.put(discontinuitySequence, adjuster2);
        return adjuster2;
    }

    public void reset() {
        this.timestampAdjusters.clear();
    }
}
