package com.google.android.gms.internal;

import com.google.android.gms.common.internal.Hide;

@Hide
/* compiled from: Singletons */
public final class zzcne {
    private static zzcne zza;

    static {
        zzcne zzcne = new zzcne();
        synchronized (zzcne.class) {
            zza = zzcne;
        }
    }

    private final zzcmz zzb = new zzcmz();
    private final zzcna zzc = new zzcna();

    private zzcne() {
    }

    private static zzcne zzc() {
        zzcne zzcne;
        synchronized (zzcne.class) {
            zzcne = zza;
        }
        return zzcne;
    }

    public static zzcmz zza() {
        return zzc().zzb;
    }

    public static zzcna zzb() {
        return zzc().zzc;
    }
}
