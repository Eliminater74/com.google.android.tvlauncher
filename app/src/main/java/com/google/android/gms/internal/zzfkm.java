package com.google.android.gms.internal;

import java.util.List;

/* compiled from: ImmutableList */
final class zzfkm extends zzfkj<E> {
    private final transient int zza;
    private final transient int zzb;
    private final /* synthetic */ zzfkj zzc;

    zzfkm(zzfkj zzfkj, int i, int i2) {
        this.zzc = zzfkj;
        this.zza = i;
        this.zzb = i2;
    }

    public final int size() {
        return this.zzb;
    }

    public final E get(int i) {
        zzfkb.zza(i, this.zzb);
        return this.zzc.get(i + this.zza);
    }

    public final zzfkj<E> zza(int i, int i2) {
        zzfkb.zza(i, i2, this.zzb);
        zzfkj zzfkj = this.zzc;
        int i3 = this.zza;
        return (zzfkj) zzfkj.subList(i + i3, i2 + i3);
    }

    /* access modifiers changed from: package-private */
    public final boolean zzc() {
        return true;
    }

    public final /* synthetic */ List subList(int i, int i2) {
        return subList(i, i2);
    }
}
