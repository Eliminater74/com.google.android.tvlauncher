package com.google.android.gms.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.phenotype.ConfigurationContentLoader;

/* compiled from: PhenotypeApiImpl */
final class zzdzt extends zzdyt.zza {
    private final /* synthetic */ zzdzs zza;

    zzdzt(zzdzs zzdzs) {
        this.zza = zzdzs;
    }

    public final void zzf(Status status) {
        if (status.isSuccess()) {
            ConfigurationContentLoader.invalidateAllCaches();
        }
        this.zza.zza((Result) status);
    }
}
