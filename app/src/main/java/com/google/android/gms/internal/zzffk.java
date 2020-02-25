package com.google.android.gms.internal;

import android.os.RemoteException;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzo;
import com.google.android.gms.usagereporting.UsageReportingApi;
import com.google.android.gms.usagereporting.UsageReportingOptInOptions;

/* compiled from: UsageReportingClientImpl */
final class zzffk extends zzffi {
    private final zzo<UsageReportingApi.OptInOptionsResult> zza;

    public zzffk(zzo<UsageReportingApi.OptInOptionsResult> zzo) {
        super();
        this.zza = zzo;
    }

    public final void zza(Status status, UsageReportingOptInOptions usageReportingOptInOptions) throws RemoteException {
        if (!status.isSuccess()) {
            this.zza.zza(new zzffb(status, null));
        } else {
            this.zza.zza(new zzffb(Status.zza, usageReportingOptInOptions));
        }
    }
}
