package com.google.android.exoplayer2.drm;

public class DecryptionException extends Exception {
    public final int errorCode;

    public DecryptionException(int errorCode2, String message) {
        super(message);
        this.errorCode = errorCode2;
    }
}
