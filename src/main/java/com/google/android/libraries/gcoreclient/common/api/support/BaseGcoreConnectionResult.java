package com.google.android.libraries.gcoreclient.common.api.support;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.libraries.gcoreclient.common.GcoreConnectionResult;

final class BaseGcoreConnectionResult implements GcoreConnectionResult {
    private final ConnectionResult mResult;

    public BaseGcoreConnectionResult(ConnectionResult result) {
        if (result != null) {
            this.mResult = result;
            return;
        }
        throw new IllegalArgumentException("null connectionResult");
    }

    public BaseGcoreConnectionResult(int statusCode, PendingIntent pendingIntent) {
        this.mResult = new ConnectionResult(statusCode, pendingIntent);
    }

    public void startResolutionForResult(Activity activity, int requestCode) throws IntentSender.SendIntentException {
        this.mResult.startResolutionForResult(activity, requestCode);
    }

    public boolean hasResolution() {
        return this.mResult.hasResolution();
    }

    public boolean isSuccess() {
        return this.mResult.isSuccess();
    }

    public int getErrorCode() {
        return this.mResult.getErrorCode();
    }

    public PendingIntent getResolution() {
        return this.mResult.getResolution();
    }

    public String toString() {
        return this.mResult.toString();
    }
}
