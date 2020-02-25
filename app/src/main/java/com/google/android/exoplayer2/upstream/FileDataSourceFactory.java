package com.google.android.exoplayer2.upstream;

import android.support.annotation.Nullable;

public final class FileDataSourceFactory implements DataSource.Factory {
    @Nullable
    private final TransferListener listener;

    public FileDataSourceFactory() {
        this(null);
    }

    public FileDataSourceFactory(@Nullable TransferListener listener2) {
        this.listener = listener2;
    }

    public DataSource createDataSource() {
        FileDataSource dataSource = new FileDataSource();
        TransferListener transferListener = this.listener;
        if (transferListener != null) {
            dataSource.addTransferListener(transferListener);
        }
        return dataSource;
    }
}
