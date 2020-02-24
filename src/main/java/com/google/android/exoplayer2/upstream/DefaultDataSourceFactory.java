package com.google.android.exoplayer2.upstream;

import android.content.Context;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSource;

public final class DefaultDataSourceFactory implements DataSource.Factory {
    private final DataSource.Factory baseDataSourceFactory;
    private final Context context;
    @Nullable
    private final TransferListener listener;

    public DefaultDataSourceFactory(Context context2, String userAgent) {
        this(context2, userAgent, (TransferListener) null);
    }

    public DefaultDataSourceFactory(Context context2, String userAgent, @Nullable TransferListener listener2) {
        this(context2, listener2, new DefaultHttpDataSourceFactory(userAgent, listener2));
    }

    public DefaultDataSourceFactory(Context context2, DataSource.Factory baseDataSourceFactory2) {
        this(context2, (TransferListener) null, baseDataSourceFactory2);
    }

    public DefaultDataSourceFactory(Context context2, @Nullable TransferListener listener2, DataSource.Factory baseDataSourceFactory2) {
        this.context = context2.getApplicationContext();
        this.listener = listener2;
        this.baseDataSourceFactory = baseDataSourceFactory2;
    }

    public DefaultDataSource createDataSource() {
        DefaultDataSource dataSource = new DefaultDataSource(this.context, this.baseDataSourceFactory.createDataSource());
        TransferListener transferListener = this.listener;
        if (transferListener != null) {
            dataSource.addTransferListener(transferListener);
        }
        return dataSource;
    }
}
