package com.google.android.exoplayer2.source.chunk;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Assertions;

public abstract class MediaChunk extends Chunk {
    public final long chunkIndex;

    public MediaChunk(DataSource dataSource, DataSpec dataSpec, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long startTimeUs, long endTimeUs, long chunkIndex2) {
        super(dataSource, dataSpec, 1, trackFormat, trackSelectionReason, trackSelectionData, startTimeUs, endTimeUs);
        Assertions.checkNotNull(trackFormat);
        this.chunkIndex = chunkIndex2;
    }

    public abstract boolean isLoadCompleted();

    public long getNextChunkIndex() {
        long j = this.chunkIndex;
        if (j != -1) {
            return 1 + j;
        }
        return -1;
    }
}
