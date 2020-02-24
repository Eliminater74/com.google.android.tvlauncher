package com.google.android.exoplayer2.offline;

import android.net.Uri;
import android.support.annotation.NonNull;
import com.google.android.exoplayer2.offline.Downloader;
import com.google.android.exoplayer2.offline.FilterableManifest;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheKeyFactory;
import com.google.android.exoplayer2.upstream.cache.CacheUtil;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class SegmentDownloader<M extends FilterableManifest<M>> implements Downloader {
    private static final int BUFFER_SIZE_BYTES = 131072;
    private final Cache cache;
    private final CacheKeyFactory cacheKeyFactory;
    private final CacheDataSource dataSource;
    private final AtomicBoolean isCanceled = new AtomicBoolean();
    private final DataSpec manifestDataSpec;
    private final CacheDataSource offlineDataSource;
    private final PriorityTaskManager priorityTaskManager;
    private final ArrayList<StreamKey> streamKeys;

    /* access modifiers changed from: protected */
    public abstract M getManifest(DataSource dataSource2, DataSpec dataSpec) throws IOException;

    /* access modifiers changed from: protected */
    public abstract List<Segment> getSegments(DataSource dataSource2, M m, boolean z) throws InterruptedException, IOException;

    protected static class Segment implements Comparable<Segment> {
        public final DataSpec dataSpec;
        public final long startTimeUs;

        public Segment(long startTimeUs2, DataSpec dataSpec2) {
            this.startTimeUs = startTimeUs2;
            this.dataSpec = dataSpec2;
        }

        public int compareTo(@NonNull Segment other) {
            return Util.compareLong(this.startTimeUs, other.startTimeUs);
        }
    }

    public SegmentDownloader(Uri manifestUri, List<StreamKey> streamKeys2, DownloaderConstructorHelper constructorHelper) {
        this.manifestDataSpec = getCompressibleDataSpec(manifestUri);
        this.streamKeys = new ArrayList<>(streamKeys2);
        this.cache = constructorHelper.getCache();
        this.dataSource = constructorHelper.createCacheDataSource();
        this.offlineDataSource = constructorHelper.createOfflineCacheDataSource();
        this.cacheKeyFactory = constructorHelper.getCacheKeyFactory();
        this.priorityTaskManager = constructorHelper.getPriorityTaskManager();
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: M
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:75)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    public final void download(@android.support.annotation.Nullable com.google.android.exoplayer2.offline.Downloader.ProgressListener r30) throws java.io.IOException, java.lang.InterruptedException {
        /*
            r29 = this;
            r1 = r29
            com.google.android.exoplayer2.util.PriorityTaskManager r0 = r1.priorityTaskManager
            r2 = -1000(0xfffffffffffffc18, float:NaN)
            r0.add(r2)
            com.google.android.exoplayer2.upstream.cache.CacheDataSource r0 = r1.dataSource     // Catch:{ all -> 0x00d4 }
            com.google.android.exoplayer2.upstream.DataSpec r3 = r1.manifestDataSpec     // Catch:{ all -> 0x00d4 }
            com.google.android.exoplayer2.offline.FilterableManifest r0 = r1.getManifest(r0, r3)     // Catch:{ all -> 0x00d4 }
            java.util.ArrayList<com.google.android.exoplayer2.offline.StreamKey> r3 = r1.streamKeys     // Catch:{ all -> 0x00d4 }
            boolean r3 = r3.isEmpty()     // Catch:{ all -> 0x00d4 }
            if (r3 != 0) goto L_0x0022
            java.util.ArrayList<com.google.android.exoplayer2.offline.StreamKey> r3 = r1.streamKeys     // Catch:{ all -> 0x00d4 }
            java.lang.Object r3 = r0.copy(r3)     // Catch:{ all -> 0x00d4 }
            com.google.android.exoplayer2.offline.FilterableManifest r3 = (com.google.android.exoplayer2.offline.FilterableManifest) r3     // Catch:{ all -> 0x00d4 }
            r0 = r3
        L_0x0022:
            com.google.android.exoplayer2.upstream.cache.CacheDataSource r3 = r1.dataSource     // Catch:{ all -> 0x00d4 }
            r4 = 0
            java.util.List r3 = r1.getSegments(r3, r0, r4)     // Catch:{ all -> 0x00d4 }
            int r9 = r3.size()     // Catch:{ all -> 0x00d4 }
            r5 = 0
            r6 = 0
            r10 = 0
            int r8 = r3.size()     // Catch:{ all -> 0x00d4 }
            int r8 = r8 + -1
            r17 = r5
            r13 = r6
            r15 = r10
        L_0x003c:
            if (r8 < 0) goto L_0x007a
            java.lang.Object r5 = r3.get(r8)     // Catch:{ all -> 0x00d4 }
            com.google.android.exoplayer2.offline.SegmentDownloader$Segment r5 = (com.google.android.exoplayer2.offline.SegmentDownloader.Segment) r5     // Catch:{ all -> 0x00d4 }
            com.google.android.exoplayer2.upstream.DataSpec r6 = r5.dataSpec     // Catch:{ all -> 0x00d4 }
            com.google.android.exoplayer2.upstream.cache.Cache r7 = r1.cache     // Catch:{ all -> 0x00d4 }
            com.google.android.exoplayer2.upstream.cache.CacheKeyFactory r10 = r1.cacheKeyFactory     // Catch:{ all -> 0x00d4 }
            android.util.Pair r6 = com.google.android.exoplayer2.upstream.cache.CacheUtil.getCached(r6, r7, r10)     // Catch:{ all -> 0x00d4 }
            java.lang.Object r7 = r6.first     // Catch:{ all -> 0x00d4 }
            java.lang.Long r7 = (java.lang.Long) r7     // Catch:{ all -> 0x00d4 }
            long r10 = r7.longValue()     // Catch:{ all -> 0x00d4 }
            java.lang.Object r7 = r6.second     // Catch:{ all -> 0x00d4 }
            java.lang.Long r7 = (java.lang.Long) r7     // Catch:{ all -> 0x00d4 }
            long r18 = r7.longValue()     // Catch:{ all -> 0x00d4 }
            long r15 = r15 + r18
            r20 = -1
            int r7 = (r10 > r20 ? 1 : (r10 == r20 ? 0 : -1))
            if (r7 == 0) goto L_0x0075
            int r7 = (r10 > r18 ? 1 : (r10 == r18 ? 0 : -1))
            if (r7 != 0) goto L_0x006f
            int r17 = r17 + 1
            r3.remove(r8)     // Catch:{ all -> 0x00d4 }
        L_0x006f:
            int r7 = (r13 > r20 ? 1 : (r13 == r20 ? 0 : -1))
            if (r7 == 0) goto L_0x0077
            long r13 = r13 + r10
            goto L_0x0077
        L_0x0075:
            r13 = -1
        L_0x0077:
            int r8 = r8 + -1
            goto L_0x003c
        L_0x007a:
            java.util.Collections.sort(r3)     // Catch:{ all -> 0x00d4 }
            r18 = 0
            if (r30 == 0) goto L_0x0090
            com.google.android.exoplayer2.offline.SegmentDownloader$ProgressNotifier r19 = new com.google.android.exoplayer2.offline.SegmentDownloader$ProgressNotifier     // Catch:{ all -> 0x00d4 }
            r5 = r19
            r6 = r30
            r7 = r13
            r10 = r15
            r12 = r17
            r5.<init>(r6, r7, r9, r10, r12)     // Catch:{ all -> 0x00d4 }
            r18 = r19
        L_0x0090:
            r5 = 131072(0x20000, float:1.83671E-40)
            byte[] r5 = new byte[r5]     // Catch:{ all -> 0x00d4 }
            r23 = r5
        L_0x0097:
            int r5 = r3.size()     // Catch:{ all -> 0x00d4 }
            if (r4 >= r5) goto L_0x00cd
            java.lang.Object r5 = r3.get(r4)     // Catch:{ all -> 0x00d4 }
            com.google.android.exoplayer2.offline.SegmentDownloader$Segment r5 = (com.google.android.exoplayer2.offline.SegmentDownloader.Segment) r5     // Catch:{ all -> 0x00d4 }
            com.google.android.exoplayer2.upstream.DataSpec r5 = r5.dataSpec     // Catch:{ all -> 0x00d4 }
            com.google.android.exoplayer2.upstream.cache.Cache r6 = r1.cache     // Catch:{ all -> 0x00d4 }
            com.google.android.exoplayer2.upstream.cache.CacheKeyFactory r7 = r1.cacheKeyFactory     // Catch:{ all -> 0x00d4 }
            com.google.android.exoplayer2.upstream.cache.CacheDataSource r8 = r1.dataSource     // Catch:{ all -> 0x00d4 }
            com.google.android.exoplayer2.util.PriorityTaskManager r10 = r1.priorityTaskManager     // Catch:{ all -> 0x00d4 }
            r25 = -1000(0xfffffffffffffc18, float:NaN)
            java.util.concurrent.atomic.AtomicBoolean r11 = r1.isCanceled     // Catch:{ all -> 0x00d4 }
            r28 = 1
            r19 = r5
            r20 = r6
            r21 = r7
            r22 = r8
            r24 = r10
            r26 = r18
            r27 = r11
            com.google.android.exoplayer2.upstream.cache.CacheUtil.cache(r19, r20, r21, r22, r23, r24, r25, r26, r27, r28)     // Catch:{ all -> 0x00d4 }
            if (r18 == 0) goto L_0x00ca
            r18.onSegmentDownloaded()     // Catch:{ all -> 0x00d4 }
        L_0x00ca:
            int r4 = r4 + 1
            goto L_0x0097
        L_0x00cd:
            com.google.android.exoplayer2.util.PriorityTaskManager r0 = r1.priorityTaskManager
            r0.remove(r2)
            return
        L_0x00d4:
            r0 = move-exception
            com.google.android.exoplayer2.util.PriorityTaskManager r3 = r1.priorityTaskManager
            r3.remove(r2)
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.SegmentDownloader.download(com.google.android.exoplayer2.offline.Downloader$ProgressListener):void");
    }

    public void cancel() {
        this.isCanceled.set(true);
    }

    public final void remove() throws InterruptedException {
        try {
            List<Segment> segments = getSegments(this.offlineDataSource, getManifest(this.offlineDataSource, this.manifestDataSpec), true);
            for (int i = 0; i < segments.size(); i++) {
                removeDataSpec(segments.get(i).dataSpec);
            }
        } catch (IOException e) {
        } catch (Throwable th) {
            removeDataSpec(this.manifestDataSpec);
            throw th;
        }
        removeDataSpec(this.manifestDataSpec);
    }

    private void removeDataSpec(DataSpec dataSpec) {
        CacheUtil.remove(dataSpec, this.cache, this.cacheKeyFactory);
    }

    protected static DataSpec getCompressibleDataSpec(Uri uri) {
        return new DataSpec(uri, 0, -1, null, 1);
    }

    private static final class ProgressNotifier implements CacheUtil.ProgressListener {
        private long bytesDownloaded;
        private final long contentLength;
        private final Downloader.ProgressListener progressListener;
        private int segmentsDownloaded;
        private final int totalSegments;

        public ProgressNotifier(Downloader.ProgressListener progressListener2, long contentLength2, int totalSegments2, long bytesDownloaded2, int segmentsDownloaded2) {
            this.progressListener = progressListener2;
            this.contentLength = contentLength2;
            this.totalSegments = totalSegments2;
            this.bytesDownloaded = bytesDownloaded2;
            this.segmentsDownloaded = segmentsDownloaded2;
        }

        public void onProgress(long requestLength, long bytesCached, long newBytesCached) {
            this.bytesDownloaded += newBytesCached;
            this.progressListener.onProgress(this.contentLength, this.bytesDownloaded, getPercentDownloaded());
        }

        public void onSegmentDownloaded() {
            this.segmentsDownloaded++;
            this.progressListener.onProgress(this.contentLength, this.bytesDownloaded, getPercentDownloaded());
        }

        private float getPercentDownloaded() {
            long j = this.contentLength;
            if (j != -1 && j != 0) {
                return (((float) this.bytesDownloaded) * 100.0f) / ((float) j);
            }
            int i = this.totalSegments;
            if (i != 0) {
                return (((float) this.segmentsDownloaded) * 100.0f) / ((float) i);
            }
            return -1.0f;
        }
    }
}
