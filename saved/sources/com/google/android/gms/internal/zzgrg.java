package com.google.android.gms.internal;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* compiled from: UnmodifiableLazyStringList */
public final class zzgrg extends AbstractList<String> implements zzgpc, RandomAccess {
    /* access modifiers changed from: private */
    public final zzgpc zza;

    public zzgrg(zzgpc zzgpc) {
        this.zza = zzgpc;
    }

    public final Object zzb(int i) {
        return this.zza.zzb(i);
    }

    public final int size() {
        return this.zza.size();
    }

    public final void zza(zzgnb zzgnb) {
        throw new UnsupportedOperationException();
    }

    public final ListIterator<String> listIterator(int i) {
        return new zzgrh(this, i);
    }

    public final Iterator<String> iterator() {
        return new zzgri(this);
    }

    public final List<?> zzd() {
        return this.zza.zzd();
    }

    public final zzgpc zze() {
        return this;
    }

    public final /* synthetic */ Object get(int i) {
        return (String) this.zza.get(i);
    }
}
