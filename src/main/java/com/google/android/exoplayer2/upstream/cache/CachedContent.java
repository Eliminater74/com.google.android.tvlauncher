package com.google.android.exoplayer2.upstream.cache;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import java.io.File;
import java.util.TreeSet;

final class CachedContent {
    private static final String TAG = "CachedContent";
    private final TreeSet<SimpleCacheSpan> cachedSpans;

    /* renamed from: id */
    public final int f105id;
    public final String key;
    private boolean locked;
    private DefaultContentMetadata metadata;

    public CachedContent(int id, String key2) {
        this(id, key2, DefaultContentMetadata.EMPTY);
    }

    public CachedContent(int id, String key2, DefaultContentMetadata metadata2) {
        this.f105id = id;
        this.key = key2;
        this.metadata = metadata2;
        this.cachedSpans = new TreeSet<>();
    }

    public DefaultContentMetadata getMetadata() {
        return this.metadata;
    }

    public boolean applyMetadataMutations(ContentMetadataMutations mutations) {
        DefaultContentMetadata oldMetadata = this.metadata;
        this.metadata = this.metadata.copyWithMutationsApplied(mutations);
        return !this.metadata.equals(oldMetadata);
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(boolean locked2) {
        this.locked = locked2;
    }

    public void addSpan(SimpleCacheSpan span) {
        this.cachedSpans.add(span);
    }

    public TreeSet<SimpleCacheSpan> getSpans() {
        return this.cachedSpans;
    }

    public SimpleCacheSpan getSpan(long position) {
        SimpleCacheSpan lookupSpan = SimpleCacheSpan.createLookup(this.key, position);
        SimpleCacheSpan floorSpan = this.cachedSpans.floor(lookupSpan);
        if (floorSpan != null && floorSpan.position + floorSpan.length > position) {
            return floorSpan;
        }
        SimpleCacheSpan ceilSpan = this.cachedSpans.ceiling(lookupSpan);
        if (ceilSpan == null) {
            return SimpleCacheSpan.createOpenHole(this.key, position);
        }
        return SimpleCacheSpan.createClosedHole(this.key, position, ceilSpan.position - position);
    }

    public long getCachedBytesLength(long position, long length) {
        SimpleCacheSpan span = getSpan(position);
        if (span.isHoleSpan()) {
            return -Math.min(span.isOpenEnded() ? Long.MAX_VALUE : span.length, length);
        }
        long queryEndPosition = position + length;
        long currentEndPosition = span.position + span.length;
        if (currentEndPosition < queryEndPosition) {
            for (SimpleCacheSpan next : this.cachedSpans.tailSet(span, false)) {
                if (next.position <= currentEndPosition) {
                    currentEndPosition = Math.max(currentEndPosition, next.position + next.length);
                    if (currentEndPosition >= queryEndPosition) {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        return Math.min(currentEndPosition - position, length);
    }

    public SimpleCacheSpan setLastTouchTimestamp(SimpleCacheSpan cacheSpan, long lastTouchTimestamp, boolean updateFile) {
        Assertions.checkState(this.cachedSpans.remove(cacheSpan));
        File file = cacheSpan.file;
        if (updateFile) {
            File directory = file.getParentFile();
            File file2 = directory;
            File newFile = SimpleCacheSpan.getCacheFile(file2, this.f105id, cacheSpan.position, lastTouchTimestamp);
            if (file.renameTo(newFile)) {
                file = newFile;
            } else {
                String valueOf = String.valueOf(file);
                String valueOf2 = String.valueOf(newFile);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 21 + String.valueOf(valueOf2).length());
                sb.append("Failed to rename ");
                sb.append(valueOf);
                sb.append(" to ");
                sb.append(valueOf2);
                Log.m30w(TAG, sb.toString());
            }
        }
        SimpleCacheSpan newCacheSpan = cacheSpan.copyWithFileAndLastTouchTimestamp(file, lastTouchTimestamp);
        this.cachedSpans.add(newCacheSpan);
        return newCacheSpan;
    }

    public boolean isEmpty() {
        return this.cachedSpans.isEmpty();
    }

    public boolean removeSpan(CacheSpan span) {
        if (!this.cachedSpans.remove(span)) {
            return false;
        }
        span.file.delete();
        return true;
    }

    public int hashCode() {
        return (((this.f105id * 31) + this.key.hashCode()) * 31) + this.metadata.hashCode();
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CachedContent that = (CachedContent) o;
        if (this.f105id != that.f105id || !this.key.equals(that.key) || !this.cachedSpans.equals(that.cachedSpans) || !this.metadata.equals(that.metadata)) {
            return false;
        }
        return true;
    }
}
