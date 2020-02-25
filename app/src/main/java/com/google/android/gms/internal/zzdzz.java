package com.google.android.gms.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.phenotype.DogfoodsToken;

/* compiled from: PhenotypeApiImpl */
final class zzdzz extends zzdyt.zza {
    private final /* synthetic */ zzdzy zza;

    zzdzz(zzdzy zzdzy) {
        this.zza = zzdzy;
    }

    public final void zza(Status status, DogfoodsToken dogfoodsToken) {
        this.zza.zza((Result) new zzdyt.zzd(status, dogfoodsToken));
    }
}
