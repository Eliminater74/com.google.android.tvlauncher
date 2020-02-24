package com.google.android.gms.internal;

/* compiled from: NewInstanceSchemas */
final class zzgqd {
    private static final zzgqb zza = zzc();
    private static final zzgqb zzb = new zzgqc();

    static zzgqb zza() {
        return zza;
    }

    static zzgqb zzb() {
        return zzb;
    }

    private static zzgqb zzc() {
        try {
            return (zzgqb) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }
}
