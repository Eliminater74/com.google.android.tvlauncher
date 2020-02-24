package com.google.android.gms.internal;

/* compiled from: GeneratedMessageInfoFactory */
final class zzgoi implements zzgps {
    private static final zzgoi zza = new zzgoi();

    private zzgoi() {
    }

    public static zzgoi zza() {
        return zza;
    }

    public final boolean zza(Class<?> cls) {
        return zzgoj.class.isAssignableFrom(cls);
    }

    public final zzgpr zzb(Class<?> cls) {
        if (!zzgoj.class.isAssignableFrom(cls)) {
            String valueOf = String.valueOf(cls.getName());
            throw new IllegalArgumentException(valueOf.length() != 0 ? "Unsupported message type: ".concat(valueOf) : new String("Unsupported message type: "));
        }
        try {
            return (zzgpr) zzgoj.zza((Class) cls.asSubclass(zzgoj.class)).zzb();
        } catch (Exception e) {
            String valueOf2 = String.valueOf(cls.getName());
            throw new RuntimeException(valueOf2.length() != 0 ? "Unable to get message info for ".concat(valueOf2) : new String("Unable to get message info for "), e);
        }
    }
}
