package com.google.android.gms.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzdyt;
import com.google.android.gms.phenotype.ExperimentTokens;

/* compiled from: PhenotypeApiImpl */
final class zzdzv extends zzdyt.zza {
    private final /* synthetic */ zzdzu zza;

    zzdzv(zzdzu zzdzu) {
        this.zza = zzdzu;
    }

    public final void zza(Status status, ExperimentTokens experimentTokens) {
        this.zza.zza((Result) new zzdyt.zze(status, experimentTokens));
    }
}
