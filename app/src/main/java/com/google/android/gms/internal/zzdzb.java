package com.google.android.gms.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

/* compiled from: PhenotypeApiImpl */
final class zzdzb extends zzdyt.zza {
    private final /* synthetic */ zzdza zza;

    zzdzb(zzdza zzdza) {
        this.zza = zzdza;
    }

    public final void zza(Status status) {
        this.zza.zza((Result) status);
        if (status.isSuccess()) {
            synchronized (zzdyt.class) {
                long unused = zzdyt.zza = this.zza.zza.getServingVersion();
            }
        }
    }
}
