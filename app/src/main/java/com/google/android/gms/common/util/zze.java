package com.google.android.gms.common.util;

import android.support.annotation.Nullable;
import android.support.p001v4.util.ArrayMap;
import android.support.p001v4.util.ArraySet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: CollectionUtils */
public final class zze {
    public static boolean zza(@Nullable Collection<?> collection) {
        if (collection == null) {
            return true;
        }
        return collection.isEmpty();
    }

    public static <T> List<T> zza(int i, T t) {
        ArrayList arrayList = new ArrayList(Math.max(1, 1));
        arrayList.add(t);
        return arrayList;
    }

    private static <T> Set<T> zza(int i, boolean z) {
        float f = z ? 0.75f : 1.0f;
        if (i <= (z ? 128 : 256)) {
            return new ArraySet(i);
        }
        return new HashSet(i, f);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.util.zze.zza(int, boolean):java.util.Set<T>
     arg types: [int, int]
     candidates:
      com.google.android.gms.common.util.zze.zza(int, java.lang.Object):java.util.List<T>
      com.google.android.gms.common.util.zze.zza(java.lang.Object[], java.lang.Object[]):java.util.Map<K, V>
      com.google.android.gms.common.util.zze.zza(int, boolean):java.util.Set<T> */
    @Deprecated
    public static <T> Set<T> zza(T t, T t2, T t3) {
        Set zza = zza(3, false);
        zza.add(t);
        zza.add(t2);
        zza.add(t3);
        return Collections.unmodifiableSet(zza);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.util.zze.zza(int, boolean):java.util.Set<T>
     arg types: [int, int]
     candidates:
      com.google.android.gms.common.util.zze.zza(int, java.lang.Object):java.util.List<T>
      com.google.android.gms.common.util.zze.zza(java.lang.Object[], java.lang.Object[]):java.util.Map<K, V>
      com.google.android.gms.common.util.zze.zza(int, boolean):java.util.Set<T> */
    @Deprecated
    public static <T> Set<T> zza(T... tArr) {
        int length = tArr.length;
        if (length == 0) {
            return Collections.emptySet();
        }
        if (length == 1) {
            return Collections.singleton(tArr[0]);
        }
        if (length == 2) {
            T t = tArr[0];
            T t2 = tArr[1];
            Set zza = zza(2, false);
            zza.add(t);
            zza.add(t2);
            return Collections.unmodifiableSet(zza);
        } else if (length == 3) {
            return zza(tArr[0], tArr[1], tArr[2]);
        } else {
            if (length != 4) {
                Set zza2 = zza(tArr.length, false);
                Collections.addAll(zza2, tArr);
                return Collections.unmodifiableSet(zza2);
            }
            T t3 = tArr[0];
            T t4 = tArr[1];
            T t5 = tArr[2];
            T t6 = tArr[3];
            Set zza3 = zza(4, false);
            zza3.add(t3);
            zza3.add(t4);
            zza3.add(t5);
            zza3.add(t6);
            return Collections.unmodifiableSet(zza3);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.util.zze.zza(int, boolean):java.util.Set<T>
     arg types: [int, int]
     candidates:
      com.google.android.gms.common.util.zze.zza(int, java.lang.Object):java.util.List<T>
      com.google.android.gms.common.util.zze.zza(java.lang.Object[], java.lang.Object[]):java.util.Map<K, V>
      com.google.android.gms.common.util.zze.zza(int, boolean):java.util.Set<T> */
    public static <T> Set<T> zza(int i) {
        if (i == 0) {
            return new ArraySet();
        }
        return zza(i, true);
    }

    private static <K, V> Map<K, V> zzb(int i, boolean z) {
        if (i <= 256) {
            return new ArrayMap(i);
        }
        return new HashMap(i, 1.0f);
    }

    public static <K, V> Map<K, V> zza(K k, V v, K k2, V v2, K k3, V v3) {
        Map zzb = zzb(3, false);
        zzb.put(k, v);
        zzb.put(k2, v2);
        zzb.put(k3, v3);
        return Collections.unmodifiableMap(zzb);
    }

    public static <K, V> Map<K, V> zza(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
        Map zzb = zzb(6, false);
        zzb.put(k, v);
        zzb.put(k2, v2);
        zzb.put(k3, v3);
        zzb.put(k4, v4);
        zzb.put(k5, v5);
        zzb.put(k6, v6);
        return Collections.unmodifiableMap(zzb);
    }

    public static <K, V> Map<K, V> zza(K[] kArr, V[] vArr) {
        if (kArr.length == vArr.length) {
            int length = kArr.length;
            if (length == 0) {
                return Collections.emptyMap();
            }
            if (length == 1) {
                return Collections.singletonMap(kArr[0], vArr[0]);
            }
            Map zzb = zzb(kArr.length, false);
            for (int i = 0; i < kArr.length; i++) {
                zzb.put(kArr[i], vArr[i]);
            }
            return Collections.unmodifiableMap(zzb);
        }
        int length2 = kArr.length;
        int length3 = vArr.length;
        StringBuilder sb = new StringBuilder(66);
        sb.append("Key and values array lengths not equal: ");
        sb.append(length2);
        sb.append(" != ");
        sb.append(length3);
        throw new IllegalArgumentException(sb.toString());
    }
}
