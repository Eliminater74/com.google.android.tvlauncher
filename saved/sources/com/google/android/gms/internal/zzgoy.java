package com.google.android.gms.internal;

import java.util.Map;

/* compiled from: LazyField */
final class zzgoy<K> implements Map.Entry<K, Object> {
    private Map.Entry<K, zzgow> zza;

    private zzgoy(Map.Entry<K, zzgow> entry) {
        this.zza = entry;
    }

    public final K getKey() {
        return this.zza.getKey();
    }

    public final Object getValue() {
        if (this.zza.getValue() == null) {
            return null;
        }
        return zzgow.zza();
    }

    public final zzgow zza() {
        return this.zza.getValue();
    }

    public final Object setValue(Object obj) {
        if (obj instanceof zzgpt) {
            return this.zza.getValue().zza((zzgpt) obj);
        }
        throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
    }
}
