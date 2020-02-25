package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: SmallSortedMap */
final class zzgqq implements Iterator<Map.Entry<K, V>> {
    private final /* synthetic */ zzgqo zzc;
    private int zza;
    private Iterator<Map.Entry<K, V>> zzb;

    private zzgqq(zzgqo zzgqo) {
        this.zzc = zzgqo;
        this.zza = this.zzc.zzb.size();
    }

    /* synthetic */ zzgqq(zzgqo zzgqo, zzgqp zzgqp) {
        this(zzgqo);
    }

    public final boolean hasNext() {
        int i = this.zza;
        return (i > 0 && i <= this.zzc.zzb.size()) || zza().hasNext();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }

    private final Iterator<Map.Entry<K, V>> zza() {
        if (this.zzb == null) {
            this.zzb = this.zzc.zzf.entrySet().iterator();
        }
        return this.zzb;
    }

    public final /* synthetic */ Object next() {
        if (zza().hasNext()) {
            return (Map.Entry) zza().next();
        }
        List zzb2 = this.zzc.zzb;
        int i = this.zza - 1;
        this.zza = i;
        return (Map.Entry) zzb2.get(i);
    }
}
