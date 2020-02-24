package com.google.android.exoplayer2.upstream.cache;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSink;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.FileDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;

public final class CacheDataSourceFactory implements DataSource.Factory {
    private final Cache cache;
    @Nullable
    private final CacheKeyFactory cacheKeyFactory;
    private final DataSource.Factory cacheReadDataSourceFactory;
    @Nullable
    private final DataSink.Factory cacheWriteDataSinkFactory;
    @Nullable
    private final CacheDataSource.EventListener eventListener;
    private final int flags;
    private final DataSource.Factory upstreamFactory;

    public CacheDataSourceFactory(Cache cache2, DataSource.Factory upstreamFactory2) {
        this(cache2, upstreamFactory2, 0);
    }

    public CacheDataSourceFactory(Cache cache2, DataSource.Factory upstreamFactory2, int flags2) {
        this(cache2, upstreamFactory2, new FileDataSourceFactory(), new CacheDataSinkFactory(cache2, CacheDataSink.DEFAULT_FRAGMENT_SIZE), flags2, null);
    }

    public CacheDataSourceFactory(Cache cache2, DataSource.Factory upstreamFactory2, DataSource.Factory cacheReadDataSourceFactory2, @Nullable DataSink.Factory cacheWriteDataSinkFactory2, int flags2, @Nullable CacheDataSource.EventListener eventListener2) {
        this(cache2, upstreamFactory2, cacheReadDataSourceFactory2, cacheWriteDataSinkFactory2, flags2, eventListener2, null);
    }

    public CacheDataSourceFactory(Cache cache2, DataSource.Factory upstreamFactory2, DataSource.Factory cacheReadDataSourceFactory2, @Nullable DataSink.Factory cacheWriteDataSinkFactory2, int flags2, @Nullable CacheDataSource.EventListener eventListener2, @Nullable CacheKeyFactory cacheKeyFactory2) {
        this.cache = cache2;
        this.upstreamFactory = upstreamFactory2;
        this.cacheReadDataSourceFactory = cacheReadDataSourceFactory2;
        this.cacheWriteDataSinkFactory = cacheWriteDataSinkFactory2;
        this.flags = flags2;
        this.eventListener = eventListener2;
        this.cacheKeyFactory = cacheKeyFactory2;
    }

    public CacheDataSource createDataSource() {
        Cache cache2 = this.cache;
        DataSource createDataSource = this.upstreamFactory.createDataSource();
        DataSource createDataSource2 = this.cacheReadDataSourceFactory.createDataSource();
        DataSink.Factory factory = this.cacheWriteDataSinkFactory;
        return new CacheDataSource(cache2, createDataSource, createDataSource2, factory == null ? null : factory.createDataSink(), this.flags, this.eventListener, this.cacheKeyFactory);
    }
}
