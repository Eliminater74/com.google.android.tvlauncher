package com.google.android.exoplayer2.source.hls;

import com.google.android.exoplayer2.upstream.DataSource;

public final class DefaultHlsDataSourceFactory implements HlsDataSourceFactory {
    private final DataSource.Factory dataSourceFactory;

    public DefaultHlsDataSourceFactory(DataSource.Factory dataSourceFactory2) {
        this.dataSourceFactory = dataSourceFactory2;
    }

    public DataSource createDataSource(int dataType) {
        return this.dataSourceFactory.createDataSource();
    }
}
