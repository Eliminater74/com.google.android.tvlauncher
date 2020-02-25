package com.google.android.gms.internal;

import java.util.Iterator;

/* compiled from: UnmodifiableLazyStringList */
final class zzgri implements Iterator<String> {
    private final /* synthetic */ zzgrg zzb;
    private Iterator<String> zza = this.zzb.zza.iterator();

    zzgri(zzgrg zzgrg) {
        this.zzb = zzgrg;
    }

    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ Object next() {
        return this.zza.next();
    }
}
