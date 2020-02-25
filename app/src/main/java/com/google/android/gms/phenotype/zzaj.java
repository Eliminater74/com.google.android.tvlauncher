package com.google.android.gms.phenotype;

final /* synthetic */ class zzaj implements PhenotypeFlag.zza {
    private final PhenotypeFlag zza;
    private final ConfigurationContentLoader zzb;

    zzaj(PhenotypeFlag phenotypeFlag, ConfigurationContentLoader configurationContentLoader) {
        this.zza = phenotypeFlag;
        this.zzb = configurationContentLoader;
    }

    public final Object zza() {
        return this.zzb.getFlags().get(this.zza.zza);
    }
}
