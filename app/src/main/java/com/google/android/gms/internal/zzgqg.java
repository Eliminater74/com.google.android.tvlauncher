package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.List;

/* compiled from: ProtobufArrayList */
final class zzgqg<E> extends zzgms<E> {
    private static final zzgqg<Object> zza;

    static {
        zzgqg<Object> zzgqg = new zzgqg<>();
        zza = zzgqg;
        zzgqg.zzb();
    }

    private final List<E> zzb;

    zzgqg() {
        this(new ArrayList(10));
    }

    private zzgqg(List<E> list) {
        this.zzb = list;
    }

    public static <E> zzgqg<E> zzd() {
        return zza;
    }

    public final void add(int i, E e) {
        zzc();
        this.zzb.add(i, e);
        this.modCount++;
    }

    public final E get(int i) {
        return this.zzb.get(i);
    }

    public final E remove(int i) {
        zzc();
        E remove = this.zzb.remove(i);
        this.modCount++;
        return remove;
    }

    public final E set(int i, E e) {
        zzc();
        E e2 = this.zzb.set(i, e);
        this.modCount++;
        return e2;
    }

    public final int size() {
        return this.zzb.size();
    }

    public final /* synthetic */ zzgos zza(int i) {
        if (i >= size()) {
            ArrayList arrayList = new ArrayList(i);
            arrayList.addAll(this.zzb);
            return new zzgqg(arrayList);
        }
        throw new IllegalArgumentException();
    }
}
