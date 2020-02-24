package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.Map;

/* compiled from: LazyField */
final class zzgoz<K> implements Iterator<Map.Entry<K, Object>> {
    private Iterator<Map.Entry<K, Object>> zza;

    public zzgoz(Iterator<Map.Entry<K, Object>> it) {
        this.zza = it;
    }

    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    public final void remove() {
        this.zza.remove();
    }

    public final /* synthetic */ Object next() {
        Map.Entry next = this.zza.next();
        if (next.getValue() instanceof zzgow) {
            return new zzgoy(next);
        }
        return next;
    }
}
