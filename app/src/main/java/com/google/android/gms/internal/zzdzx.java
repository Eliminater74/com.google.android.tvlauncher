package com.google.android.gms.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.phenotype.ExperimentTokens;

/* compiled from: PhenotypeApiImpl */
final class zzdzx extends zzdyt.zza {
    private final /* synthetic */ zzdzw zza;

    zzdzx(zzdzw zzdzw) {
        this.zza = zzdzw;
    }

    public final void zza(Status status, ExperimentTokens experimentTokens) {
        this.zza.zza((Result) new zzdyt.zze(status, experimentTokens));
    }
}
