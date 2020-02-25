package com.google.android.libraries.gcoreclient.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender;

public abstract class GcoreResolvableApiException extends GcoreApiException {
    protected GcoreResolvableApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract PendingIntent getResolution();

    public abstract void startResolutionForResult(Activity activity, int i) throws IntentSender.SendIntentException;
}
