package com.google.android.gms.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzdyt;
import com.google.android.gms.phenotype.Configurations;

/* compiled from: PhenotypeApiImpl */
final class zzdyz extends zzdyt.zza {
    private final /* synthetic */ zzdyy zza;

    zzdyz(zzdyy zzdyy) {
        this.zza = zzdyy;
    }

    public final void zzb(Status status, Configurations configurations) {
        this.zza.zza((Result) new zzdyt.zzc(status, configurations));
    }
}
