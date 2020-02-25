package com.google.android.exoplayer2.source.chunk;

import java.util.NoSuchElementException;

public abstract class BaseMediaChunkIterator implements MediaChunkIterator {
    private final long fromIndex;
    private final long toIndex;
    private long currentIndex;

    public BaseMediaChunkIterator(long fromIndex2, long toIndex2) {
        this.fromIndex = fromIndex2;
        this.toIndex = toIndex2;
        reset();
    }

    public boolean isEnded() {
        return this.currentIndex > this.toIndex;
    }

    public boolean next() {
        this.currentIndex++;
        return !isEnded();
    }

    public void reset() {
        this.currentIndex = this.fromIndex - 1;
    }

    /* access modifiers changed from: protected */
    public final void checkInBounds() {
        long j = this.currentIndex;
        if (j < this.fromIndex || j > this.toIndex) {
            throw new NoSuchElementException();
        }
    }

    /* access modifiers changed from: protected */
    public final long getCurrentIndex() {
        return this.currentIndex;
    }
}
