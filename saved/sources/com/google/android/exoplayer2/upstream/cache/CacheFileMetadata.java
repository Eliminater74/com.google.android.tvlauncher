package com.google.android.exoplayer2.upstream.cache;

final class CacheFileMetadata {
    public final long lastTouchTimestamp;
    public final long length;

    public CacheFileMetadata(long length2, long lastTouchTimestamp2) {
        this.length = length2;
        this.lastTouchTimestamp = lastTouchTimestamp2;
    }
}
