package com.google.android.exoplayer2.trackselection;

import com.google.android.exoplayer2.trackselection.FixedTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionUtil;

final /* synthetic */ class FixedTrackSelection$Factory$$Lambda$0 implements TrackSelectionUtil.AdaptiveTrackSelectionFactory {
    private final FixedTrackSelection.Factory arg$1;

    FixedTrackSelection$Factory$$Lambda$0(FixedTrackSelection.Factory factory) {
        this.arg$1 = factory;
    }

    public TrackSelection createAdaptiveTrackSelection(TrackSelection.Definition definition) {
        return this.arg$1.lambda$createTrackSelections$0$FixedTrackSelection$Factory(definition);
    }
}
