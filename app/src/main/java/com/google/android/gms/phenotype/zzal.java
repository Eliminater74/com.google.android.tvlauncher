package com.google.android.gms.phenotype;

import com.google.android.gms.internal.zzfij;

final /* synthetic */ class zzal implements PhenotypeFlag.zza {
    private final String zza;
    private final boolean zzb = false;

    zzal(String str, boolean z) {
        this.zza = str;
    }

    public final Object zza() {
        return Boolean.valueOf(zzfij.zza(PhenotypeFlag.zzc.getContentResolver(), this.zza, this.zzb));
    }
}
