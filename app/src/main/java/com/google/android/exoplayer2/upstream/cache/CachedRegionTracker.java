package com.google.android.exoplayer2.upstream.cache;

import android.support.annotation.NonNull;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;

public final class CachedRegionTracker implements Cache.Listener {
    public static final int CACHED_TO_END = -2;
    public static final int NOT_CACHED = -1;
    private static final String TAG = "CachedRegionTracker";
    private final Cache cache;
    private final String cacheKey;
    private final ChunkIndex chunkIndex;
    private final Region lookupRegion = new Region(0, 0);
    private final TreeSet<Region> regions = new TreeSet<>();

    public CachedRegionTracker(Cache cache2, String cacheKey2, ChunkIndex chunkIndex2) {
        this.cache = cache2;
        this.cacheKey = cacheKey2;
        this.chunkIndex = chunkIndex2;
        synchronized (this) {
            Iterator<CacheSpan> spanIterator = cache2.addListener(cacheKey2, this).descendingIterator();
            while (spanIterator.hasNext()) {
                mergeSpan(spanIterator.next());
            }
        }
    }

    public void release() {
        this.cache.removeListener(this.cacheKey, this);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0066, code lost:
        return -1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized int getRegionEndTimeMs(long r10) {
        /*
            r9 = this;
            monitor-enter(r9)
            com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$Region r0 = r9.lookupRegion     // Catch:{ all -> 0x0067 }
            r0.startOffset = r10     // Catch:{ all -> 0x0067 }
            java.util.TreeSet<com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$Region> r0 = r9.regions     // Catch:{ all -> 0x0067 }
            com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$Region r1 = r9.lookupRegion     // Catch:{ all -> 0x0067 }
            java.lang.Object r0 = r0.floor(r1)     // Catch:{ all -> 0x0067 }
            com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$Region r0 = (com.google.android.exoplayer2.upstream.cache.CachedRegionTracker.Region) r0     // Catch:{ all -> 0x0067 }
            r1 = -1
            if (r0 == 0) goto L_0x0065
            long r2 = r0.endOffset     // Catch:{ all -> 0x0067 }
            int r4 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r4 > 0) goto L_0x0065
            int r2 = r0.endOffsetIndex     // Catch:{ all -> 0x0067 }
            if (r2 != r1) goto L_0x001d
            goto L_0x0065
        L_0x001d:
            int r1 = r0.endOffsetIndex     // Catch:{ all -> 0x0067 }
            com.google.android.exoplayer2.extractor.ChunkIndex r2 = r9.chunkIndex     // Catch:{ all -> 0x0067 }
            int r2 = r2.length     // Catch:{ all -> 0x0067 }
            int r2 = r2 + -1
            if (r1 != r2) goto L_0x003e
            long r2 = r0.endOffset     // Catch:{ all -> 0x0067 }
            com.google.android.exoplayer2.extractor.ChunkIndex r4 = r9.chunkIndex     // Catch:{ all -> 0x0067 }
            long[] r4 = r4.offsets     // Catch:{ all -> 0x0067 }
            r5 = r4[r1]     // Catch:{ all -> 0x0067 }
            com.google.android.exoplayer2.extractor.ChunkIndex r4 = r9.chunkIndex     // Catch:{ all -> 0x0067 }
            int[] r4 = r4.sizes     // Catch:{ all -> 0x0067 }
            r4 = r4[r1]     // Catch:{ all -> 0x0067 }
            long r7 = (long) r4
            long r5 = r5 + r7
            int r4 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r4 != 0) goto L_0x003e
            r2 = -2
            monitor-exit(r9)
            return r2
        L_0x003e:
            com.google.android.exoplayer2.extractor.ChunkIndex r2 = r9.chunkIndex     // Catch:{ all -> 0x0067 }
            long[] r2 = r2.durationsUs     // Catch:{ all -> 0x0067 }
            r3 = r2[r1]     // Catch:{ all -> 0x0067 }
            long r5 = r0.endOffset     // Catch:{ all -> 0x0067 }
            com.google.android.exoplayer2.extractor.ChunkIndex r2 = r9.chunkIndex     // Catch:{ all -> 0x0067 }
            long[] r2 = r2.offsets     // Catch:{ all -> 0x0067 }
            r7 = r2[r1]     // Catch:{ all -> 0x0067 }
            long r5 = r5 - r7
            long r3 = r3 * r5
            com.google.android.exoplayer2.extractor.ChunkIndex r2 = r9.chunkIndex     // Catch:{ all -> 0x0067 }
            int[] r2 = r2.sizes     // Catch:{ all -> 0x0067 }
            r2 = r2[r1]     // Catch:{ all -> 0x0067 }
            long r5 = (long) r2     // Catch:{ all -> 0x0067 }
            long r3 = r3 / r5
            r2 = r3
            com.google.android.exoplayer2.extractor.ChunkIndex r4 = r9.chunkIndex     // Catch:{ all -> 0x0067 }
            long[] r4 = r4.timesUs     // Catch:{ all -> 0x0067 }
            r5 = r4[r1]     // Catch:{ all -> 0x0067 }
            long r5 = r5 + r2
            r7 = 1000(0x3e8, double:4.94E-321)
            long r5 = r5 / r7
            int r4 = (int) r5
            monitor-exit(r9)
            return r4
        L_0x0065:
            monitor-exit(r9)
            return r1
        L_0x0067:
            r10 = move-exception
            monitor-exit(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CachedRegionTracker.getRegionEndTimeMs(long):int");
    }

    public synchronized void onSpanAdded(Cache cache2, CacheSpan span) {
        mergeSpan(span);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x006c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void onSpanRemoved(com.google.android.exoplayer2.upstream.cache.Cache r8, com.google.android.exoplayer2.upstream.cache.CacheSpan r9) {
        /*
            r7 = this;
            monitor-enter(r7)
            com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$Region r0 = new com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$Region     // Catch:{ all -> 0x006d }
            long r1 = r9.position     // Catch:{ all -> 0x006d }
            long r3 = r9.position     // Catch:{ all -> 0x006d }
            long r5 = r9.length     // Catch:{ all -> 0x006d }
            long r3 = r3 + r5
            r0.<init>(r1, r3)     // Catch:{ all -> 0x006d }
            java.util.TreeSet<com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$Region> r1 = r7.regions     // Catch:{ all -> 0x006d }
            java.lang.Object r1 = r1.floor(r0)     // Catch:{ all -> 0x006d }
            com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$Region r1 = (com.google.android.exoplayer2.upstream.cache.CachedRegionTracker.Region) r1     // Catch:{ all -> 0x006d }
            if (r1 != 0) goto L_0x0020
            java.lang.String r2 = "CachedRegionTracker"
            java.lang.String r3 = "Removed a span we were not aware of"
            com.google.android.exoplayer2.util.Log.m26e(r2, r3)     // Catch:{ all -> 0x006d }
            monitor-exit(r7)
            return
        L_0x0020:
            java.util.TreeSet<com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$Region> r2 = r7.regions     // Catch:{ all -> 0x006d }
            r2.remove(r1)     // Catch:{ all -> 0x006d }
            long r2 = r1.startOffset     // Catch:{ all -> 0x006d }
            long r4 = r0.startOffset     // Catch:{ all -> 0x006d }
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 >= 0) goto L_0x004e
            com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$Region r2 = new com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$Region     // Catch:{ all -> 0x006d }
            long r3 = r1.startOffset     // Catch:{ all -> 0x006d }
            long r5 = r0.startOffset     // Catch:{ all -> 0x006d }
            r2.<init>(r3, r5)     // Catch:{ all -> 0x006d }
            com.google.android.exoplayer2.extractor.ChunkIndex r3 = r7.chunkIndex     // Catch:{ all -> 0x006d }
            long[] r3 = r3.offsets     // Catch:{ all -> 0x006d }
            long r4 = r2.endOffset     // Catch:{ all -> 0x006d }
            int r3 = java.util.Arrays.binarySearch(r3, r4)     // Catch:{ all -> 0x006d }
            if (r3 >= 0) goto L_0x0046
            int r4 = -r3
            int r4 = r4 + -2
            goto L_0x0047
        L_0x0046:
            r4 = r3
        L_0x0047:
            r2.endOffsetIndex = r4     // Catch:{ all -> 0x006d }
            java.util.TreeSet<com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$Region> r4 = r7.regions     // Catch:{ all -> 0x006d }
            r4.add(r2)     // Catch:{ all -> 0x006d }
        L_0x004e:
            long r2 = r1.endOffset     // Catch:{ all -> 0x006d }
            long r4 = r0.endOffset     // Catch:{ all -> 0x006d }
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 <= 0) goto L_0x006b
            com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$Region r2 = new com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$Region     // Catch:{ all -> 0x006d }
            long r3 = r0.endOffset     // Catch:{ all -> 0x006d }
            r5 = 1
            long r3 = r3 + r5
            long r5 = r1.endOffset     // Catch:{ all -> 0x006d }
            r2.<init>(r3, r5)     // Catch:{ all -> 0x006d }
            int r3 = r1.endOffsetIndex     // Catch:{ all -> 0x006d }
            r2.endOffsetIndex = r3     // Catch:{ all -> 0x006d }
            java.util.TreeSet<com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$Region> r3 = r7.regions     // Catch:{ all -> 0x006d }
            r3.add(r2)     // Catch:{ all -> 0x006d }
        L_0x006b:
            monitor-exit(r7)
            return
        L_0x006d:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CachedRegionTracker.onSpanRemoved(com.google.android.exoplayer2.upstream.cache.Cache, com.google.android.exoplayer2.upstream.cache.CacheSpan):void");
    }

    public void onSpanTouched(Cache cache2, CacheSpan oldSpan, CacheSpan newSpan) {
    }

    private void mergeSpan(CacheSpan span) {
        Region newRegion = new Region(span.position, span.position + span.length);
        Region floorRegion = this.regions.floor(newRegion);
        Region ceilingRegion = this.regions.ceiling(newRegion);
        boolean floorConnects = regionsConnect(floorRegion, newRegion);
        if (regionsConnect(newRegion, ceilingRegion)) {
            if (floorConnects) {
                floorRegion.endOffset = ceilingRegion.endOffset;
                floorRegion.endOffsetIndex = ceilingRegion.endOffsetIndex;
            } else {
                newRegion.endOffset = ceilingRegion.endOffset;
                newRegion.endOffsetIndex = ceilingRegion.endOffsetIndex;
                this.regions.add(newRegion);
            }
            this.regions.remove(ceilingRegion);
        } else if (floorConnects) {
            floorRegion.endOffset = newRegion.endOffset;
            int index = floorRegion.endOffsetIndex;
            while (index < this.chunkIndex.length - 1 && this.chunkIndex.offsets[index + 1] <= floorRegion.endOffset) {
                index++;
            }
            floorRegion.endOffsetIndex = index;
        } else {
            int index2 = Arrays.binarySearch(this.chunkIndex.offsets, newRegion.endOffset);
            newRegion.endOffsetIndex = index2 < 0 ? (-index2) - 2 : index2;
            this.regions.add(newRegion);
        }
    }

    private boolean regionsConnect(Region lower, Region upper) {
        return (lower == null || upper == null || lower.endOffset != upper.startOffset) ? false : true;
    }

    private static class Region implements Comparable<Region> {
        public long endOffset;
        public int endOffsetIndex;
        public long startOffset;

        public Region(long position, long endOffset2) {
            this.startOffset = position;
            this.endOffset = endOffset2;
        }

        public int compareTo(@NonNull Region another) {
            return Util.compareLong(this.startOffset, another.startOffset);
        }
    }
}
