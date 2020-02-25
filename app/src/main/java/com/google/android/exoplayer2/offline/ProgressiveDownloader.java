package com.google.android.exoplayer2.offline;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheKeyFactory;
import com.google.android.exoplayer2.upstream.cache.CacheUtil;
import com.google.android.exoplayer2.util.PriorityTaskManager;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ProgressiveDownloader implements Downloader {
    private static final int BUFFER_SIZE_BYTES = 131072;
    private final Cache cache;
    private final CacheKeyFactory cacheKeyFactory;
    private final CacheDataSource dataSource;
    private final DataSpec dataSpec;
    private final AtomicBoolean isCanceled = new AtomicBoolean();
    private final PriorityTaskManager priorityTaskManager;

    public ProgressiveDownloader(Uri uri, @Nullable String customCacheKey, DownloaderConstructorHelper constructorHelper) {
        this.dataSpec = new DataSpec(uri, 0, -1, customCacheKey, 16);
        this.cache = constructorHelper.getCache();
        this.dataSource = constructorHelper.createCacheDataSource();
        this.cacheKeyFactory = constructorHelper.getCacheKeyFactory();
        this.priorityTaskManager = constructorHelper.getPriorityTaskManager();
    }

    public void download(@Nullable Downloader.ProgressListener progressListener) throws InterruptedException, IOException {
        this.priorityTaskManager.add(-1000);
        try {
            CacheUtil.cache(this.dataSpec, this.cache, this.cacheKeyFactory, this.dataSource, new byte[131072], this.priorityTaskManager, -1000, progressListener == null ? null : new ProgressForwarder(progressListener), this.isCanceled, true);
        } finally {
            this.priorityTaskManager.remove(-1000);
        }
    }

    public void cancel() {
        this.isCanceled.set(true);
    }

    public void remove() {
        CacheUtil.remove(this.dataSpec, this.cache, this.cacheKeyFactory);
    }

    private static final class ProgressForwarder implements CacheUtil.ProgressListener {
        private final Downloader.ProgressListener progessListener;

        public ProgressForwarder(Downloader.ProgressListener progressListener) {
            this.progessListener = progressListener;
        }

        public void onProgress(long contentLength, long bytesCached, long newBytesCached) {
            float percentDownloaded;
            if (contentLength == -1 || contentLength == 0) {
                percentDownloaded = -1.0f;
            } else {
                percentDownloaded = (((float) bytesCached) * 100.0f) / ((float) contentLength);
            }
            this.progessListener.onProgress(contentLength, bytesCached, percentDownloaded);
        }
    }
}
