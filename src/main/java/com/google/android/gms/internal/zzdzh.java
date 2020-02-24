package com.google.android.gms.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzdyt;
import com.google.android.gms.phenotype.FlagOverrides;

/* compiled from: PhenotypeApiImpl */
final class zzdzh extends zzdyt.zza {
    private final /* synthetic */ zzdzg zza;

    zzdzh(zzdzg zzdzg) {
        this.zza = zzdzg;
    }

    public final void zza(Status status, FlagOverrides flagOverrides) {
        this.zza.zza((Result) new zzdyt.zzf(status, flagOverrides));
    }
}
