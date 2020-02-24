package com.google.android.gms.internal;

/* compiled from: ImmutableList */
final class zzfkl<E> extends zzfke<E> {
    private final zzfkj<E> zza;

    zzfkl(zzfkj<E> zzfkj, int i) {
        super(zzfkj.size(), i);
        this.zza = zzfkj;
    }

    /* access modifiers changed from: protected */
    public final E zza(int i) {
        return this.zza.get(i);
    }
}
