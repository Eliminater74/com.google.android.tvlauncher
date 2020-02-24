package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.common.api.Status;

/* compiled from: UsageReportingClientImpl */
final class zzffj extends zzffi {
    private zzffj() {
        super();
    }

    public final void zzc(Status status) {
        if (!status.isSuccess()) {
            String valueOf = String.valueOf(status);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 52);
            sb.append("disconnect(): Could not unregister listener: status=");
            sb.append(valueOf);
            Log.e("UsageReportingClientImpl", sb.toString());
        }
    }
}
