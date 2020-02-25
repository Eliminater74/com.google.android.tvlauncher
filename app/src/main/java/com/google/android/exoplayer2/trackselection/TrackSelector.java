package com.google.android.exoplayer2.trackselection;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;

public abstract class TrackSelector {
    @Nullable
    private BandwidthMeter bandwidthMeter;
    @Nullable
    private InvalidationListener listener;

    public abstract void onSelectionActivated(Object obj);

    public abstract TrackSelectorResult selectTracks(RendererCapabilities[] rendererCapabilitiesArr, TrackGroupArray trackGroupArray, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline) throws ExoPlaybackException;

    public final void init(InvalidationListener listener2, BandwidthMeter bandwidthMeter2) {
        this.listener = listener2;
        this.bandwidthMeter = bandwidthMeter2;
    }

    /* access modifiers changed from: protected */
    public final void invalidate() {
        InvalidationListener invalidationListener = this.listener;
        if (invalidationListener != null) {
            invalidationListener.onTrackSelectionsInvalidated();
        }
    }

    /* access modifiers changed from: protected */
    public final BandwidthMeter getBandwidthMeter() {
        return (BandwidthMeter) Assertions.checkNotNull(this.bandwidthMeter);
    }

    public interface InvalidationListener {
        void onTrackSelectionsInvalidated();
    }
}
