package com.google.android.exoplayer2.upstream;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.util.Assertions;

public final class DefaultHttpDataSourceFactory extends HttpDataSource.BaseFactory {
    private final boolean allowCrossProtocolRedirects;
    private final int connectTimeoutMillis;
    @Nullable
    private final TransferListener listener;
    private final int readTimeoutMillis;
    private final String userAgent;

    public DefaultHttpDataSourceFactory(String userAgent2) {
        this(userAgent2, null);
    }

    public DefaultHttpDataSourceFactory(String userAgent2, @Nullable TransferListener listener2) {
        this(userAgent2, listener2, 8000, 8000, false);
    }

    public DefaultHttpDataSourceFactory(String userAgent2, int connectTimeoutMillis2, int readTimeoutMillis2, boolean allowCrossProtocolRedirects2) {
        this(userAgent2, null, connectTimeoutMillis2, readTimeoutMillis2, allowCrossProtocolRedirects2);
    }

    public DefaultHttpDataSourceFactory(String userAgent2, @Nullable TransferListener listener2, int connectTimeoutMillis2, int readTimeoutMillis2, boolean allowCrossProtocolRedirects2) {
        this.userAgent = Assertions.checkNotEmpty(userAgent2);
        this.listener = listener2;
        this.connectTimeoutMillis = connectTimeoutMillis2;
        this.readTimeoutMillis = readTimeoutMillis2;
        this.allowCrossProtocolRedirects = allowCrossProtocolRedirects2;
    }

    /* access modifiers changed from: protected */
    public DefaultHttpDataSource createDataSourceInternal(HttpDataSource.RequestProperties defaultRequestProperties) {
        DefaultHttpDataSource dataSource = new DefaultHttpDataSource(this.userAgent, null, this.connectTimeoutMillis, this.readTimeoutMillis, this.allowCrossProtocolRedirects, defaultRequestProperties);
        TransferListener transferListener = this.listener;
        if (transferListener != null) {
            dataSource.addTransferListener(transferListener);
        }
        return dataSource;
    }
}
