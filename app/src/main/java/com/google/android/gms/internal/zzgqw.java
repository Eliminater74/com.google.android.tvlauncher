package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.Map;

/* compiled from: SmallSortedMap */
final class zzgqw implements Iterator<Map.Entry<K, V>> {
    private final /* synthetic */ zzgqo zzd;
    private int zza;
    private boolean zzb;
    private Iterator<Map.Entry<K, V>> zzc;

    private zzgqw(zzgqo zzgqo) {
        this.zzd = zzgqo;
        this.zza = -1;
    }

    /* synthetic */ zzgqw(zzgqo zzgqo, zzgqp zzgqp) {
        this(zzgqo);
    }

    public final boolean hasNext() {
        if (this.zza + 1 < this.zzd.zzb.size() || (!this.zzd.zzc.isEmpty() && zza().hasNext())) {
            return true;
        }
        return false;
    }

    public final void remove() {
        if (this.zzb) {
            this.zzb = false;
            this.zzd.zzf();
            if (this.zza < this.zzd.zzb.size()) {
                zzgqo zzgqo = this.zzd;
                int i = this.zza;
                this.zza = i - 1;
                Object unused = zzgqo.zzc(i);
                return;
            }
            zza().remove();
            return;
        }
        throw new IllegalStateException("remove() was called before next()");
    }

    private final Iterator<Map.Entry<K, V>> zza() {
        if (this.zzc == null) {
            this.zzc = this.zzd.zzc.entrySet().iterator();
        }
        return this.zzc;
    }

    public final /* synthetic */ Object next() {
        this.zzb = true;
        int i = this.zza + 1;
        this.zza = i;
        if (i < this.zzd.zzb.size()) {
            return (Map.Entry) this.zzd.zzb.get(this.zza);
        }
        return (Map.Entry) zza().next();
    }
}
