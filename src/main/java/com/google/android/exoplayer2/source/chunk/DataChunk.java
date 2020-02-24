package com.google.android.exoplayer2.source.chunk;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.Arrays;

public abstract class DataChunk extends Chunk {
    private static final int READ_GRANULARITY = 16384;
    private byte[] data;
    private volatile boolean loadCanceled;

    /* access modifiers changed from: protected */
    public abstract void consume(byte[] bArr, int i) throws IOException;

    public DataChunk(DataSource dataSource, DataSpec dataSpec, int type, Format trackFormat, int trackSelectionReason, Object trackSelectionData, byte[] data2) {
        super(dataSource, dataSpec, type, trackFormat, trackSelectionReason, trackSelectionData, C0841C.TIME_UNSET, C0841C.TIME_UNSET);
        this.data = data2;
    }

    public byte[] getDataHolder() {
        return this.data;
    }

    public final void cancelLoad() {
        this.loadCanceled = true;
    }

    public final void load() throws IOException, InterruptedException {
        try {
            this.dataSource.open(this.dataSpec);
            int limit = 0;
            int bytesRead = 0;
            while (bytesRead != -1 && !this.loadCanceled) {
                maybeExpandData(limit);
                bytesRead = this.dataSource.read(this.data, limit, 16384);
                if (bytesRead != -1) {
                    limit += bytesRead;
                }
            }
            if (!this.loadCanceled) {
                consume(this.data, limit);
            }
        } finally {
            Util.closeQuietly(this.dataSource);
        }
    }

    private void maybeExpandData(int limit) {
        byte[] bArr = this.data;
        if (bArr == null) {
            this.data = new byte[16384];
        } else if (bArr.length < limit + 16384) {
            this.data = Arrays.copyOf(bArr, bArr.length + 16384);
        }
    }
}
