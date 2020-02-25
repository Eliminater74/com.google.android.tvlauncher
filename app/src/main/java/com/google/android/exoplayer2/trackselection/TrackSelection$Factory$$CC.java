package com.google.android.exoplayer2.trackselection;

import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.upstream.BandwidthMeter;

public abstract /* synthetic */ class TrackSelection$Factory$$CC {
    @Deprecated
    public static TrackSelection createTrackSelection$$dflt$$(TrackSelection.Factory factory, TrackGroup group, BandwidthMeter bandwidthMeter, int... tracks) {
        throw new UnsupportedOperationException();
    }

    public static TrackSelection[] createTrackSelections$$dflt$$(TrackSelection.Factory factory, TrackSelection.Definition[] definitions, BandwidthMeter bandwidthMeter) {
        return TrackSelectionUtil.createTrackSelectionsForDefinitions(definitions, new TrackSelection$Factory$$Lambda$0(factory, bandwidthMeter));
    }
}
