package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.Map;

public final class DummyDataSource implements DataSource {
    public static final DataSource.Factory FACTORY = DummyDataSource$$Lambda$0.$instance;
    public static final DummyDataSource INSTANCE = new DummyDataSource();

    private DummyDataSource() {
    }

    static final /* bridge */ /* synthetic */ DummyDataSource bridge$lambda$0$DummyDataSource() {
        return new DummyDataSource();
    }

    public Map getResponseHeaders() {
        return DataSource$$CC.getResponseHeaders$$dflt$$(this);
    }

    public void addTransferListener(TransferListener transferListener) {
    }

    public long open(DataSpec dataSpec) throws IOException {
        throw new IOException("Dummy source");
    }

    public int read(byte[] buffer, int offset, int readLength) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Nullable
    public Uri getUri() {
        return null;
    }

    public void close() throws IOException {
    }
}
