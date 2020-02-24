package com.google.android.exoplayer2.trackselection;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import java.util.List;

public final class WindowedTrackBitrateEstimator implements TrackBitrateEstimator {
    private final long maxFutureDurationUs;
    private final long maxPastDurationUs;
    private final boolean useFormatBitrateAsLowerBound;

    public WindowedTrackBitrateEstimator(long maxPastDurationMs, long maxFutureDurationMs, boolean useFormatBitrateAsLowerBound2) {
        this.maxPastDurationUs = C0841C.msToUs(maxPastDurationMs);
        this.maxFutureDurationUs = C0841C.msToUs(maxFutureDurationMs);
        this.useFormatBitrateAsLowerBound = useFormatBitrateAsLowerBound2;
    }

    public int[] getBitrates(Format[] formats, List<? extends MediaChunk> queue, MediaChunkIterator[] iterators, @Nullable int[] bitrates) {
        if (this.maxFutureDurationUs <= 0 && this.maxPastDurationUs <= 0) {
            return TrackSelectionUtil.getFormatBitrates(formats, bitrates);
        }
        return TrackSelectionUtil.getBitratesUsingPastAndFutureInfo(formats, queue, this.maxPastDurationUs, iterators, this.maxFutureDurationUs, this.useFormatBitrateAsLowerBound, bitrates);
    }
}
