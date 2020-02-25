package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DataSource {

    void addTransferListener(TransferListener transferListener);

    void close() throws IOException;

    Map<String, List<String>> getResponseHeaders();

    @Nullable
    Uri getUri();

    long open(DataSpec dataSpec) throws IOException;

    int read(byte[] bArr, int i, int i2) throws IOException;

    public interface Factory {
        DataSource createDataSource();
    }
}
