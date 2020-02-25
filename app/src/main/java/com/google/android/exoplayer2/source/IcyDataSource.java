package com.google.android.exoplayer2.source;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.common.primitives.UnsignedBytes;

import java.io.IOException;
import java.util.List;
import java.util.Map;

final class IcyDataSource implements DataSource {
    private final Listener listener;
    private final int metadataIntervalBytes;
    private final byte[] metadataLengthByteHolder;
    private final DataSource upstream;
    private int bytesUntilMetadata;

    public IcyDataSource(DataSource upstream2, int metadataIntervalBytes2, Listener listener2) {
        Assertions.checkArgument(metadataIntervalBytes2 > 0);
        this.upstream = upstream2;
        this.metadataIntervalBytes = metadataIntervalBytes2;
        this.listener = listener2;
        this.metadataLengthByteHolder = new byte[1];
        this.bytesUntilMetadata = metadataIntervalBytes2;
    }

    public void addTransferListener(TransferListener transferListener) {
        this.upstream.addTransferListener(transferListener);
    }

    public long open(DataSpec dataSpec) throws IOException {
        throw new UnsupportedOperationException();
    }

    public int read(byte[] buffer, int offset, int readLength) throws IOException {
        if (this.bytesUntilMetadata == 0) {
            if (!readMetadata()) {
                return -1;
            }
            this.bytesUntilMetadata = this.metadataIntervalBytes;
        }
        int bytesRead = this.upstream.read(buffer, offset, Math.min(this.bytesUntilMetadata, readLength));
        if (bytesRead != -1) {
            this.bytesUntilMetadata -= bytesRead;
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
        throw new UnsupportedOperationException();
    }

    private boolean readMetadata() throws IOException {
        if (this.upstream.read(this.metadataLengthByteHolder, 0, 1) == -1) {
            return false;
        }
        int metadataLength = (this.metadataLengthByteHolder[0] & UnsignedBytes.MAX_VALUE) << 4;
        if (metadataLength == 0) {
            return true;
        }
        int offset = 0;
        int lengthRemaining = metadataLength;
        byte[] metadata = new byte[metadataLength];
        while (lengthRemaining > 0) {
            int bytesRead = this.upstream.read(metadata, offset, lengthRemaining);
            if (bytesRead == -1) {
                return false;
            }
            offset += bytesRead;
            lengthRemaining -= bytesRead;
        }
        while (metadataLength > 0 && metadata[metadataLength - 1] == 0) {
            metadataLength--;
        }
        if (metadataLength > 0) {
            this.listener.onIcyMetadata(new ParsableByteArray(metadata, metadataLength));
        }
        return true;
    }

    public interface Listener {
        void onIcyMetadata(ParsableByteArray parsableByteArray);
    }
}
