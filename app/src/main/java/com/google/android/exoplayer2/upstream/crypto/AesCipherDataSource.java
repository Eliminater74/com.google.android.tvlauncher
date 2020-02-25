package com.google.android.exoplayer2.upstream.crypto;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.TransferListener;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class AesCipherDataSource implements DataSource {
    private final byte[] secretKey;
    private final DataSource upstream;
    @Nullable
    private AesFlushingCipher cipher;

    public AesCipherDataSource(byte[] secretKey2, DataSource upstream2) {
        this.upstream = upstream2;
        this.secretKey = secretKey2;
    }

    public void addTransferListener(TransferListener transferListener) {
        this.upstream.addTransferListener(transferListener);
    }

    public long open(DataSpec dataSpec) throws IOException {
        long dataLength = this.upstream.open(dataSpec);
        this.cipher = new AesFlushingCipher(2, this.secretKey, CryptoUtil.getFNV64Hash(dataSpec.key), dataSpec.absoluteStreamPosition);
        return dataLength;
    }

    public int read(byte[] data, int offset, int readLength) throws IOException {
        if (readLength == 0) {
            return 0;
        }
        int read = this.upstream.read(data, offset, readLength);
        if (read == -1) {
            return -1;
        }
        this.cipher.updateInPlace(data, offset, read);
        return read;
    }

    @Nullable
    public Uri getUri() {
        return this.upstream.getUri();
    }

    public Map<String, List<String>> getResponseHeaders() {
        return this.upstream.getResponseHeaders();
    }

    public void close() throws IOException {
        this.cipher = null;
        this.upstream.close();
    }
}
