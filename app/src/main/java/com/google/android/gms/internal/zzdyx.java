package com.google.android.gms.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzdyt;
import com.google.android.gms.phenotype.Flag;

/* compiled from: PhenotypeApiImpl */
final class zzdyx extends zzdyt.zza {
    private final /* synthetic */ zzdyw zza;

    zzdyx(zzdyw zzdyw) {
        this.zza = zzdyw;
    }

    public final void zza(Status status, Flag flag) {
        this.zza.zza((Result) new zzdyt.zzg(status, flag));
    }
}
