package com.google.android.libraries.gcoreclient.common.api.support;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.gcoreclient.common.api.GcoreStatus;

public class GcoreStatusImpl implements GcoreStatus {
    public static final ResultWrapper<GcoreStatus, Status> STATUS_RESULT_WRAPPER = new ResultWrapper<GcoreStatus, Status>() {
        public GcoreStatus wrap(Status status) {
            return new GcoreStatusImpl(status);
        }
    };
    private final Status status;

    public GcoreStatusImpl(Status status2) {
        this.status = status2;
    }

    public GcoreStatus getStatus() {
        return this;
    }

    public boolean isSuccess() {
        return this.status.isSuccess();
    }

    public boolean isInterrupted() {
        return this.status.isInterrupted();
    }

    public boolean isCanceled() {
        return this.status.isCanceled();
    }

    public int getStatusCode() {
        return this.status.getStatusCode();
    }

    public String getStatusMessage() {
        return this.status.getStatusMessage();
    }

    public PendingIntent getResolution() {
        return this.status.getResolution();
    }

    public void startResolutionForResult(Activity activity, int requestCode) throws IntentSender.SendIntentException {
        this.status.startResolutionForResult(activity, requestCode);
    }

    public String toString() {
        return this.status.toString();
    }

    public boolean equals(Object o) {
        if (!(o instanceof GcoreStatusImpl)) {
            return false;
        }
        return this.status.equals(((GcoreStatusImpl) o).status);
    }

    public int hashCode() {
        return this.status.hashCode();
    }

    public Status unwrap() {
        return this.status;
    }
}
