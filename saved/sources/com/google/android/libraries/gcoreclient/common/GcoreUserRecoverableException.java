package com.google.android.libraries.gcoreclient.common;

import android.content.Intent;

public class GcoreUserRecoverableException extends Exception {
    private final Intent mIntent;

    public GcoreUserRecoverableException(String msg, Intent intent) {
        super(msg);
        this.mIntent = intent;
    }

    public GcoreUserRecoverableException(String msg, Intent intent, Throwable throwable) {
        super(msg, throwable);
        this.mIntent = intent;
    }

    public Intent getIntent() {
        return new Intent(this.mIntent);
    }
}
