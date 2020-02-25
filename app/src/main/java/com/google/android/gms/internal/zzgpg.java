package com.google.android.gms.internal;

import java.util.List;

/* compiled from: ListFieldSchema */
final class zzgpg extends zzgpd {
    private zzgpg() {
        super();
    }

    private static <E> zzgos<E> zzc(Object obj, long j) {
        return (zzgos) zzgrj.zzf(obj, j);
    }

    /* access modifiers changed from: package-private */
    public final <L> List<L> zza(Object obj, long j) {
        zzgos zzc = zzc(obj, j);
        if (zzc.zza()) {
            return zzc;
        }
        int size = zzc.size();
        zzgos zza = zzc.zza(size == 0 ? 10 : size << 1);
        zzgrj.zza(obj, j, zza);
        return zza;
    }

    /* access modifiers changed from: package-private */
    public final void zzb(Object obj, long j) {
        zzc(obj, j).zzb();
    }

    /* access modifiers changed from: package-private */
    public final <E> void zza(Object obj, Object obj2, long j) {
        zzgos zzc = zzc(obj, j);
        zzgos zzc2 = zzc(obj2, j);
        int size = zzc.size();
        int size2 = zzc2.size();
        if (size > 0 && size2 > 0) {
            if (!zzc.zza()) {
                zzc = zzc.zza(size2 + size);
            }
            zzc.addAll(zzc2);
        }
        if (size > 0) {
            zzc2 = zzc;
        }
        zzgrj.zza(obj, j, zzc2);
    }
}
