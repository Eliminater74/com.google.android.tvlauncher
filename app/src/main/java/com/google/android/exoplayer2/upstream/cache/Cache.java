package com.google.android.exoplayer2.upstream.cache;

import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.NavigableSet;
import java.util.Set;

public interface Cache {
    public static final long UID_UNSET = -1;

    NavigableSet<CacheSpan> addListener(String str, Listener listener);

    void applyContentMetadataMutations(String str, ContentMetadataMutations contentMetadataMutations) throws CacheException;

    void commitFile(File file, long j) throws CacheException;

    long getCacheSpace();

    long getCachedLength(String str, long j, long j2);

    NavigableSet<CacheSpan> getCachedSpans(String str);

    ContentMetadata getContentMetadata(String str);

    Set<String> getKeys();

    long getUid();

    boolean isCached(String str, long j, long j2);

    void release();

    void releaseHoleSpan(CacheSpan cacheSpan);

    void removeListener(String str, Listener listener);

    void removeSpan(CacheSpan cacheSpan) throws CacheException;

    File startFile(String str, long j, long j2) throws CacheException;

    CacheSpan startReadWrite(String str, long j) throws InterruptedException, CacheException;

    @Nullable
    CacheSpan startReadWriteNonBlocking(String str, long j) throws CacheException;

    public interface Listener {
        void onSpanAdded(Cache cache, CacheSpan cacheSpan);

        void onSpanRemoved(Cache cache, CacheSpan cacheSpan);

        void onSpanTouched(Cache cache, CacheSpan cacheSpan, CacheSpan cacheSpan2);
    }

    public static class CacheException extends IOException {
        public CacheException(String message) {
            super(message);
        }

        public CacheException(Throwable cause) {
            super(cause);
        }

        public CacheException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
