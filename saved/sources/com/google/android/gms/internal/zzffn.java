package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.zzcj;
import com.google.android.gms.usagereporting.UsageReportingApi;

/* compiled from: UsageReportingClientImpl */
final class zzffn extends zzfey {
    private final zzcj<UsageReportingApi.OptInOptionsChangedListener> zza;

    public zzffn(zzcj<UsageReportingApi.OptInOptionsChangedListener> zzcj) {
        this.zza = zzcj;
    }

    public final void zza() throws RemoteException {
        this.zza.zza(new zzffo(this));
    }
}
