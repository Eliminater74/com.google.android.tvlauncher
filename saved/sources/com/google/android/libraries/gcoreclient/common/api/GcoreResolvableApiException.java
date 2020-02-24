package com.google.android.libraries.gcoreclient.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender;

public abstract class GcoreResolvableApiException extends GcoreApiException {
    public abstract PendingIntent getResolution();

    public abstract void startResolutionForResult(Activity activity, int i) throws IntentSender.SendIntentException;

    protected GcoreResolvableApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
