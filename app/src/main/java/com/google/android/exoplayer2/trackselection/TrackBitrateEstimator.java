package com.google.android.exoplayer2.trackselection;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;

import java.util.List;

public interface TrackBitrateEstimator {
    public static final TrackBitrateEstimator DEFAULT = TrackBitrateEstimator$$Lambda$0.$instance;

    int[] getBitrates(Format[] formatArr, List<? extends MediaChunk> list, MediaChunkIterator[] mediaChunkIteratorArr, @Nullable int[] iArr);
}
