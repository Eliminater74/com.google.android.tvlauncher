package com.google.android.exoplayer2.source.chunk;

import com.google.android.exoplayer2.upstream.DataSpec;
import java.util.List;

public final class MediaChunkListIterator extends BaseMediaChunkIterator {
    private final List<? extends MediaChunk> chunks;
    private final boolean reverseOrder;

    public MediaChunkListIterator(List<? extends MediaChunk> chunks2, boolean reverseOrder2) {
        super(0, (long) (chunks2.size() - 1));
        this.chunks = chunks2;
        this.reverseOrder = reverseOrder2;
    }

    public DataSpec getDataSpec() {
        return getCurrentChunk().dataSpec;
    }

    public long getChunkStartTimeUs() {
        return getCurrentChunk().startTimeUs;
    }

    public long getChunkEndTimeUs() {
        return getCurrentChunk().endTimeUs;
    }

    private MediaChunk getCurrentChunk() {
        int index = (int) super.getCurrentIndex();
        if (this.reverseOrder) {
            index = (this.chunks.size() - 1) - index;
        }
        return (MediaChunk) this.chunks.get(index);
    }
}
