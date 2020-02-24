package com.google.android.gms.internal;

/* compiled from: ManifestSchemaFactory */
final class zzgpk implements zzgps {
    private zzgps[] zza;

    zzgpk(zzgps... zzgpsArr) {
        this.zza = zzgpsArr;
    }

    public final boolean zza(Class<?> cls) {
        for (zzgps zza2 : this.zza) {
            if (zza2.zza(cls)) {
                return true;
            }
        }
        return false;
    }

    public final zzgpr zzb(Class<?> cls) {
        for (zzgps zzgps : this.zza) {
            if (zzgps.zza(cls)) {
                return zzgps.zzb(cls);
            }
        }
        String valueOf = String.valueOf(cls.getName());
        throw new UnsupportedOperationException(valueOf.length() != 0 ? "No factory is available for message type: ".concat(valueOf) : new String("No factory is available for message type: "));
    }
}
