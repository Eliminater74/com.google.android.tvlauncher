package com.google.android.gms.internal;

import android.os.RemoteException;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzo;

/* compiled from: UsageReportingClientImpl */
final class zzffl extends zzffi {
    private final zzo<Status> zza;

    public zzffl(zzo<Status> zzo) {
        super();
        this.zza = zzo;
    }

    public final void zza(Status status) throws RemoteException {
        this.zza.zza(status);
    }
}
