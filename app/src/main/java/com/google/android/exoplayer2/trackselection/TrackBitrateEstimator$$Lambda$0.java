package com.google.android.exoplayer2.trackselection;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import java.util.List;

final /* synthetic */ class TrackBitrateEstimator$$Lambda$0 implements TrackBitrateEstimator {
    static final TrackBitrateEstimator $instance = new TrackBitrateEstimator$$Lambda$0();

    private TrackBitrateEstimator$$Lambda$0() {
    }

    public int[] getBitrates(Format[] formatArr, List list, MediaChunkIterator[] mediaChunkIteratorArr, int[] iArr) {
        return TrackSelectionUtil.getFormatBitrates(formatArr, iArr);
    }
}
