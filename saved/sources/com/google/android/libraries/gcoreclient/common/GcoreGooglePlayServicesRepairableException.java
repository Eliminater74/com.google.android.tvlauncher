package com.google.android.libraries.gcoreclient.common;

import android.content.Intent;

public class GcoreGooglePlayServicesRepairableException extends GcoreUserRecoverableException {
    private final int mConnectionStatusCode;

    public GcoreGooglePlayServicesRepairableException(int connectionStatusCode, String msg, Intent intent) {
        super(msg, intent);
        this.mConnectionStatusCode = connectionStatusCode;
    }

    public GcoreGooglePlayServicesRepairableException(int connectionStatusCode, String msg, Intent intent, Throwable throwable) {
        super(msg, intent, throwable);
        this.mConnectionStatusCode = connectionStatusCode;
    }

    public int getConnectionStatusCode() {
        return this.mConnectionStatusCode;
    }
}
