package com.google.android.gms.internal;

/* compiled from: RegularImmutableList */
final class zzfkn<E> extends zzfkj<E> {
    static final zzfkj<Object> zza = new zzfkn(new Object[0], 0);
    private final transient Object[] zzb;
    private final transient int zzc;

    zzfkn(Object[] objArr, int i) {
        this.zzb = objArr;
        this.zzc = i;
    }

    public final int size() {
        return this.zzc;
    }

    /* access modifiers changed from: package-private */
    public final boolean zzc() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public final int zza(Object[] objArr, int i) {
        System.arraycopy(this.zzb, 0, objArr, 0, this.zzc);
        return this.zzc + 0;
    }

    public final E get(int i) {
        zzfkb.zza(i, this.zzc);
        return this.zzb[i];
    }
}
