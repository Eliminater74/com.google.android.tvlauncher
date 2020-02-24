package com.google.android.libraries.gcoreclient.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender;

public interface GcoreStatus extends GcoreResult {
    PendingIntent getResolution();

    int getStatusCode();

    String getStatusMessage();

    boolean isCanceled();

    boolean isInterrupted();

    boolean isSuccess();

    void startResolutionForResult(Activity activity, int i) throws IntentSender.SendIntentException;
}
