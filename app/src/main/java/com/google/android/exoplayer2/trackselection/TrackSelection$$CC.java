package com.google.android.exoplayer2.trackselection;

import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import java.util.List;

public abstract /* synthetic */ class TrackSelection$$CC {
    public static void onDiscontinuity$$dflt$$(TrackSelection trackSelection) {
    }

    @Deprecated
    public static void updateSelectedTrack$$dflt$$(TrackSelection trackSelection, long playbackPositionUs, long bufferedDurationUs, long availableDurationUs) {
        throw new UnsupportedOperationException();
    }

    public static void updateSelectedTrack$$dflt$$(TrackSelection trackSelection, long playbackPositionUs, long bufferedDurationUs, long availableDurationUs, List list, MediaChunkIterator[] mediaChunkIterators) {
        trackSelection.updateSelectedTrack(playbackPositionUs, bufferedDurationUs, availableDurationUs);
    }
}
