package com.google.android.exoplayer2.trackselection;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.upstream.BandwidthMeter;

import java.util.List;

public interface TrackSelection {

    boolean blacklist(int i, long j);

    void disable();

    void enable();

    int evaluateQueueSize(long j, List<? extends MediaChunk> list);

    Format getFormat(int i);

    int getIndexInTrackGroup(int i);

    Format getSelectedFormat();

    int getSelectedIndex();

    int getSelectedIndexInTrackGroup();

    @Nullable
    Object getSelectionData();

    int getSelectionReason();

    TrackGroup getTrackGroup();

    int indexOf(int i);

    int indexOf(Format format);

    int length();

    void onDiscontinuity();

    void onPlaybackSpeed(float f);

    @Deprecated
    void updateSelectedTrack(long j, long j2, long j3);

    void updateSelectedTrack(long j, long j2, long j3, List<? extends MediaChunk> list, MediaChunkIterator[] mediaChunkIteratorArr);

    public interface Factory {
        @Deprecated
        TrackSelection createTrackSelection(TrackGroup trackGroup, BandwidthMeter bandwidthMeter, int... iArr);

        TrackSelection[] createTrackSelections(Definition[] definitionArr, BandwidthMeter bandwidthMeter);
    }

    public static final class Definition {
        @Nullable
        public final Object data;
        public final TrackGroup group;
        public final int reason;
        public final int[] tracks;

        public Definition(TrackGroup group2, int... tracks2) {
            this(group2, tracks2, 0, null);
        }

        public Definition(TrackGroup group2, int[] tracks2, int reason2, @Nullable Object data2) {
            this.group = group2;
            this.tracks = tracks2;
            this.reason = reason2;
            this.data = data2;
        }
    }
}
