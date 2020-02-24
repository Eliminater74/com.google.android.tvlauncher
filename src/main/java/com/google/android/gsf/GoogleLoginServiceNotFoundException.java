package com.google.android.gsf;

import android.util.AndroidException;

public class GoogleLoginServiceNotFoundException extends AndroidException {
    private int mErrorCode;

    public GoogleLoginServiceNotFoundException(int errorCode) {
        super(GoogleLoginServiceConstants.getErrorCodeMessage(errorCode));
        this.mErrorCode = errorCode;
    }

    /* access modifiers changed from: package-private */
    public int getErrorCode() {
        return this.mErrorCode;
    }
}
