package com.google.android.exoplayer2.upstream.cache;

import java.util.Comparator;
import java.util.TreeSet;

public final class LeastRecentlyUsedCacheEvictor implements CacheEvictor, Comparator<CacheSpan> {
    private final TreeSet<CacheSpan> leastRecentlyUsed = new TreeSet<>(this);
    private final long maxBytes;
    private long currentSize;

    public LeastRecentlyUsedCacheEvictor(long maxBytes2) {
        this.maxBytes = maxBytes2;
    }

    public boolean requiresCacheSpanTouches() {
        return true;
    }

    public void onCacheInitialized() {
    }

    public void onStartFile(Cache cache, String key, long position, long length) {
        if (length != -1) {
            evictCache(cache, length);
        }
    }

    public void onSpanAdded(Cache cache, CacheSpan span) {
        this.leastRecentlyUsed.add(span);
        this.currentSize += span.length;
        evictCache(cache, 0);
    }

    public void onSpanRemoved(Cache cache, CacheSpan span) {
        this.leastRecentlyUsed.remove(span);
        this.currentSize -= span.length;
    }

    public void onSpanTouched(Cache cache, CacheSpan oldSpan, CacheSpan newSpan) {
        onSpanRemoved(cache, oldSpan);
        onSpanAdded(cache, newSpan);
    }

    public int compare(CacheSpan lhs, CacheSpan rhs) {
        if (lhs.lastTouchTimestamp - rhs.lastTouchTimestamp == 0) {
            return lhs.compareTo(rhs);
        }
        return lhs.lastTouchTimestamp < rhs.lastTouchTimestamp ? -1 : 1;
    }

    private void evictCache(Cache cache, long requiredSpace) {
        while (this.currentSize + requiredSpace > this.maxBytes && !this.leastRecentlyUsed.isEmpty()) {
            try {
                cache.removeSpan(this.leastRecentlyUsed.first());
            } catch (Cache.CacheException e) {
            }
        }
    }
}
