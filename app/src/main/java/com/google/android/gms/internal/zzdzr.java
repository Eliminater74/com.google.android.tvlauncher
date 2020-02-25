package com.google.android.gms.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.phenotype.Configurations;

/* compiled from: PhenotypeApiImpl */
final class zzdzr extends zzdyt.zza {
    private final /* synthetic */ zzdzq zza;

    zzdzr(zzdzq zzdzq) {
        this.zza = zzdzq;
    }

    public final void zza(Status status, Configurations configurations) {
        this.zza.zza((Result) new zzdyt.zzc(status, configurations));
    }
}
