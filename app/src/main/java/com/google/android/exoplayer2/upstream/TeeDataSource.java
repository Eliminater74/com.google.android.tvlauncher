package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.util.Assertions;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class TeeDataSource implements DataSource {
    private final DataSink dataSink;
    private final DataSource upstream;
    private long bytesRemaining;
    private boolean dataSinkNeedsClosing;

    public TeeDataSource(DataSource upstream2, DataSink dataSink2) {
        this.upstream = (DataSource) Assertions.checkNotNull(upstream2);
        this.dataSink = (DataSink) Assertions.checkNotNull(dataSink2);
    }

    public void addTransferListener(TransferListener transferListener) {
        this.upstream.addTransferListener(transferListener);
    }

    public long open(DataSpec dataSpec) throws IOException {
        this.bytesRemaining = this.upstream.open(dataSpec);
        if (this.bytesRemaining == 0) {
            return 0;
        }
        if (dataSpec.length == -1) {
            long j = this.bytesRemaining;
            if (j != -1) {
                dataSpec = dataSpec.subrange(0, j);
            }
        }
        this.dataSinkNeedsClosing = true;
        this.dataSink.open(dataSpec);
        return this.bytesRemaining;
    }

    public int read(byte[] buffer, int offset, int max) throws IOException {
        if (this.bytesRemaining == 0) {
            return -1;
        }
        int bytesRead = this.upstream.read(buffer, offset, max);
        if (bytesRead > 0) {
            this.dataSink.write(buffer, offset, bytesRead);
            long j = this.bytesRemaining;
            if (j != -1) {
                this.bytesRemaining = j - ((long) bytesRead);
            }
        }
        return bytesRead;
    }

    @Nullable
    public Uri getUri() {
        return this.upstream.getUri();
    }

    public Map<String, List<String>> getResponseHeaders() {
        return this.upstream.getResponseHeaders();
    }

    public void close() throws IOException {
        try {
            this.upstream.close();
        } finally {
            if (this.dataSinkNeedsClosing) {
                this.dataSinkNeedsClosing = false;
                this.dataSink.close();
            }
        }
    }
}
