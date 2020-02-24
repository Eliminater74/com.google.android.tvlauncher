package com.google.android.exoplayer2.drm;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Map;

public final class ErrorStateDrmSession<T extends ExoMediaCrypto> implements DrmSession<T> {
    private final DrmSession.DrmSessionException error;

    public ErrorStateDrmSession(DrmSession.DrmSessionException error2) {
        this.error = (DrmSession.DrmSessionException) Assertions.checkNotNull(error2);
    }

    public int getState() {
        return 1;
    }

    @Nullable
    public DrmSession.DrmSessionException getError() {
        return this.error;
    }

    @Nullable
    public T getMediaCrypto() {
        return null;
    }

    @Nullable
    public Map<String, String> queryKeyStatus() {
        return null;
    }

    @Nullable
    public byte[] getOfflineLicenseKeySetId() {
        return null;
    }
}
