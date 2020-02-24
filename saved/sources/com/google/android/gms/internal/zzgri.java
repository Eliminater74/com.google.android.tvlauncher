package com.google.android.gms.internal;

import java.util.Iterator;

/* compiled from: UnmodifiableLazyStringList */
final class zzgri implements Iterator<String> {
    private Iterator<String> zza = this.zzb.zza.iterator();
    private final /* synthetic */ zzgrg zzb;

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
