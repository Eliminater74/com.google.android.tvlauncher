package com.google.android.exoplayer2.drm;

import com.google.android.exoplayer2.util.Assertions;

import java.io.IOException;
import java.util.UUID;

public final class LocalMediaDrmCallback implements MediaDrmCallback {
    private final byte[] keyResponse;

    public LocalMediaDrmCallback(byte[] keyResponse2) {
        this.keyResponse = (byte[]) Assertions.checkNotNull(keyResponse2);
    }

    public byte[] executeProvisionRequest(UUID uuid, ExoMediaDrm.ProvisionRequest request) throws IOException {
        throw new UnsupportedOperationException();
    }

    public byte[] executeKeyRequest(UUID uuid, ExoMediaDrm.KeyRequest request) throws Exception {
        return this.keyResponse;
    }
}
