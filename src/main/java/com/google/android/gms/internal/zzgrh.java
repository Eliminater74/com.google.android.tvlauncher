package com.google.android.gms.internal;

import java.util.ListIterator;

/* compiled from: UnmodifiableLazyStringList */
final class zzgrh implements ListIterator<String> {
    private ListIterator<String> zza = this.zzc.zza.listIterator(this.zzb);
    private final /* synthetic */ int zzb;
    private final /* synthetic */ zzgrg zzc;

    zzgrh(zzgrg zzgrg, int i) {
        this.zzc = zzgrg;
        this.zzb = i;
    }

    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    public final boolean hasPrevious() {
        return this.zza.hasPrevious();
    }

    public final int nextIndex() {
        return this.zza.nextIndex();
    }

    public final int previousIndex() {
        return this.zza.previousIndex();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ void add(Object obj) {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ void set(Object obj) {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ Object previous() {
        return this.zza.previous();
    }

    public final /* synthetic */ Object next() {
        return this.zza.next();
    }
}
