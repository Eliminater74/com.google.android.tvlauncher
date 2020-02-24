package com.google.android.exoplayer2.source.dash.manifest;

import com.google.android.exoplayer2.source.dash.DashSegmentIndex;

final class SingleSegmentIndex implements DashSegmentIndex {
    private final RangedUri uri;

    public SingleSegmentIndex(RangedUri uri2) {
        this.uri = uri2;
    }

    public long getSegmentNum(long timeUs, long periodDurationUs) {
        return 0;
    }

    public long getTimeUs(long segmentNum) {
        return 0;
    }

    public long getDurationUs(long segmentNum, long periodDurationUs) {
        return periodDurationUs;
    }

    public RangedUri getSegmentUrl(long segmentNum) {
        return this.uri;
    }

    public long getFirstSegmentNum() {
        return 0;
    }

    public int getSegmentCount(long periodDurationUs) {
        return 1;
    }

    public boolean isExplicit() {
        return true;
    }
}
