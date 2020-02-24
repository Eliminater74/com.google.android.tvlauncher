package com.google.android.gms.internal;

import java.io.IOException;

/* compiled from: MapEntryLite */
public final class zzgpl<K, V> {
    static <K, V> void zza(zzgnp zzgnp, zzgpm<K, V> zzgpm, K k, V v) throws IOException {
        zzgoa.zza(zzgnp, zzgpm.zza, 1, k);
        zzgoa.zza(zzgnp, zzgpm.zzc, 2, v);
    }

    static <K, V> int zza(zzgpm<K, V> zzgpm, K k, V v) {
        return zzgoa.zza(zzgpm.zza, 1, k) + zzgoa.zza(zzgpm.zzc, 2, v);
    }
}
