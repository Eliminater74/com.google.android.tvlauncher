package com.google.android.exoplayer2.trackselection;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.upstream.BandwidthMeter;

import java.util.List;

public final class FixedTrackSelection extends BaseTrackSelection {
    @Nullable
    private final Object data;
    private final int reason;

    public FixedTrackSelection(TrackGroup group, int track) {
        this(group, track, 0, null);
    }

    public FixedTrackSelection(TrackGroup group, int track, int reason2, @Nullable Object data2) {
        super(group, track);
        this.reason = reason2;
        this.data = data2;
    }

    public void updateSelectedTrack(long playbackPositionUs, long bufferedDurationUs, long availableDurationUs, List<? extends MediaChunk> list, MediaChunkIterator[] mediaChunkIterators) {
    }

    public int getSelectedIndex() {
        return 0;
    }

    public int getSelectionReason() {
        return this.reason;
    }

    @Nullable
    public Object getSelectionData() {
        return this.data;
    }

    @Deprecated
    public static final class Factory implements TrackSelection.Factory {
        @Nullable
        private final Object data;
        private final int reason;

        public Factory() {
            this.reason = 0;
            this.data = null;
        }

        public Factory(int reason2, @Nullable Object data2) {
            this.reason = reason2;
            this.data = data2;
        }

        public TrackSelection createTrackSelection(TrackGroup trackGroup, BandwidthMeter bandwidthMeter, int... iArr) {
            return TrackSelection$Factory$$CC.createTrackSelection$$dflt$$(this, trackGroup, bandwidthMeter, iArr);
        }

        public TrackSelection[] createTrackSelections(TrackSelection.Definition[] definitions, BandwidthMeter bandwidthMeter) {
            return TrackSelectionUtil.createTrackSelectionsForDefinitions(definitions, new FixedTrackSelection$Factory$$Lambda$0(this));
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ TrackSelection lambda$createTrackSelections$0$FixedTrackSelection$Factory(TrackSelection.Definition definition) {
            return new FixedTrackSelection(definition.group, definition.tracks[0], this.reason, this.data);
        }
    }
}
