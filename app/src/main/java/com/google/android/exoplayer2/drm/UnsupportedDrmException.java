package com.google.android.exoplayer2.drm;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class UnsupportedDrmException extends Exception {
    public static final int REASON_INSTANTIATION_ERROR = 2;
    public static final int REASON_UNSUPPORTED_SCHEME = 1;
    public final int reason;

    public UnsupportedDrmException(int reason2) {
        this.reason = reason2;
    }

    public UnsupportedDrmException(int reason2, Exception cause) {
        super(cause);
        this.reason = reason2;
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Reason {
    }
}
