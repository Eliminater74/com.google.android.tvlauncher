package com.google.android.exoplayer2.drm;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

public interface DrmSession<T extends ExoMediaCrypto> {
    public static final int STATE_ERROR = 1;
    public static final int STATE_OPENED = 3;
    public static final int STATE_OPENED_WITH_KEYS = 4;
    public static final int STATE_OPENING = 2;
    public static final int STATE_RELEASED = 0;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    @Nullable
    DrmSessionException getError();

    @Nullable
    T getMediaCrypto();

    @Nullable
    byte[] getOfflineLicenseKeySetId();

    int getState();

    @Nullable
    Map<String, String> queryKeyStatus();

    public static class DrmSessionException extends Exception {
        public DrmSessionException(Throwable cause) {
            super(cause);
        }
    }
}
