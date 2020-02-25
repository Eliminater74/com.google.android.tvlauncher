package com.google.android.libraries.gcoreclient.common.api.impl;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.libraries.gcoreclient.common.api.GcoreResolvableApiException;

public final class GcoreResolvableApiExceptionImpl extends GcoreResolvableApiException {
    private final ResolvableApiException resolvableApiException;

    public GcoreResolvableApiExceptionImpl(ResolvableApiException resolvableApiException2, Throwable cause) {
        super(resolvableApiException2.getMessage(), cause);
        this.resolvableApiException = resolvableApiException2;
    }

    public int getStatusCode() {
        return this.resolvableApiException.getStatusCode();
    }

    public String getStatusMessage() {
        return this.resolvableApiException.getStatusMessage();
    }

    public void startResolutionForResult(Activity activity, int requestCode) throws IntentSender.SendIntentException {
        this.resolvableApiException.startResolutionForResult(activity, requestCode);
    }

    public PendingIntent getResolution() {
        return this.resolvableApiException.getResolution();
    }
}
