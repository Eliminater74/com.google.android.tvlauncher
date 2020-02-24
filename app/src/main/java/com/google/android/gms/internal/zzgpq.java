package com.google.android.gms.internal;

/* compiled from: MapFieldSchemas */
final class zzgpq {
    private static final zzgpo zza = zzc();
    private static final zzgpo zzb = new zzgpp();

    static zzgpo zza() {
        return zza;
    }

    static zzgpo zzb() {
        return zzb;
    }

    private static zzgpo zzc() {
        try {
            return (zzgpo) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }
}
