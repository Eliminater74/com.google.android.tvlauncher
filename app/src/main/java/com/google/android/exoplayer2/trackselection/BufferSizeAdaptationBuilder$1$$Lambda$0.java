package com.google.android.exoplayer2.trackselection;

import com.google.android.exoplayer2.upstream.BandwidthMeter;

final /* synthetic */ class BufferSizeAdaptationBuilder$1$$Lambda$0 implements TrackSelectionUtil.AdaptiveTrackSelectionFactory {
    private final BufferSizeAdaptationBuilder.C09211 arg$1;
    private final BandwidthMeter arg$2;

    BufferSizeAdaptationBuilder$1$$Lambda$0(BufferSizeAdaptationBuilder.C09211 r1, BandwidthMeter bandwidthMeter) {
        this.arg$1 = r1;
        this.arg$2 = bandwidthMeter;
    }

    public TrackSelection createAdaptiveTrackSelection(TrackSelection.Definition definition) {
        return this.arg$1.lambda$createTrackSelections$0$BufferSizeAdaptationBuilder$1(this.arg$2, definition);
    }
}
