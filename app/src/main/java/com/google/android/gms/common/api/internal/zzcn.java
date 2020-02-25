package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.android.gms.common.internal.zzau;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/* compiled from: ListenerHolders */
public final class zzcn {
    private final Set<zzcj<?>> zza = Collections.newSetFromMap(new WeakHashMap());

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
     arg types: [L, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
     arg types: [java.lang.String, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T */
    public static <L> zzcj<L> zzb(@NonNull L l, @NonNull Looper looper, @NonNull String str) {
        zzau.zza((Object) l, (Object) "Listener must not be null");
        zzau.zza(looper, "Looper must not be null");
        zzau.zza((Object) str, (Object) "Listener type must not be null");
        return new zzcj<>(looper, l, str);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
     arg types: [L, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
     arg types: [java.lang.String, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
     arg types: [java.lang.String, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String */
    public static <L> zzcl<L> zza(@NonNull L l, @NonNull String str) {
        zzau.zza((Object) l, (Object) "Listener must not be null");
        zzau.zza((Object) str, (Object) "Listener type must not be null");
        zzau.zza(str, (Object) "Listener type must not be empty");
        return new zzcl<>(l, str);
    }

    public final <L> zzcj<L> zza(@NonNull L l, @NonNull Looper looper, @NonNull String str) {
        zzcj<L> zzb = zzb(l, looper, str);
        this.zza.add(zzb);
        return zzb;
    }

    public final void zza() {
        for (zzcj<?> zzb : this.zza) {
            zzb.zzb();
        }
        this.zza.clear();
    }
}
