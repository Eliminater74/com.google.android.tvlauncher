package com.google.android.gms.internal;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;

/* compiled from: SmallSortedMap */
class zzgqx extends AbstractSet<Map.Entry<K, V>> {
    private final /* synthetic */ zzgqo zza;

    private zzgqx(zzgqo zzgqo) {
        this.zza = zzgqo;
    }

    public Iterator<Map.Entry<K, V>> iterator() {
        return new zzgqw(this.zza, null);
    }

    public int size() {
        return this.zza.size();
    }

    public boolean contains(Object obj) {
        Map.Entry entry = (Map.Entry) obj;
        Object obj2 = this.zza.get(entry.getKey());
        Object value = entry.getValue();
        if (obj2 != value) {
            return obj2 != null && obj2.equals(value);
        }
        return true;
    }

    public boolean remove(Object obj) {
        Map.Entry entry = (Map.Entry) obj;
        if (!contains(entry)) {
            return false;
        }
        this.zza.remove(entry.getKey());
        return true;
    }

    public void clear() {
        this.zza.clear();
    }

    public /* synthetic */ boolean add(Object obj) {
        Map.Entry entry = (Map.Entry) obj;
        if (contains(entry)) {
            return false;
        }
        this.zza.put((Comparable) entry.getKey(), entry.getValue());
        return true;
    }

    /* synthetic */ zzgqx(zzgqo zzgqo, zzgqp zzgqp) {
        this(zzgqo);
    }
}
