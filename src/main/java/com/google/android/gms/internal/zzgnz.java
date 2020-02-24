package com.google.android.gms.internal;

/* compiled from: ExtensionSchemas */
final class zzgnz {
    private static final zzgnw<?> zza = new zzgnx();
    private static final zzgnw<?> zzb = zzc();

    private static zzgnw<?> zzc() {
        try {
            return (zzgnw) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }

    static zzgnw<?> zza() {
        return zza;
    }

    static zzgnw<?> zzb() {
        zzgnw<?> zzgnw = zzb;
        if (zzgnw != null) {
            return zzgnw;
        }
        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
    }
}
