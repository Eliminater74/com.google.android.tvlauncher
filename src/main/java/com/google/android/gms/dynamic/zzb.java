package com.google.android.gms.dynamic;

import android.os.Bundle;
import java.util.Iterator;

/* compiled from: DeferredLifecycleHelper */
final class zzb implements zzo<T> {
    private final /* synthetic */ zza zza;

    zzb(zza zza2) {
        this.zza = zza2;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.dynamic.zza.zza(com.google.android.gms.dynamic.zza, com.google.android.gms.dynamic.LifecycleDelegate):com.google.android.gms.dynamic.LifecycleDelegate
     arg types: [com.google.android.gms.dynamic.zza, T]
     candidates:
      com.google.android.gms.dynamic.zza.zza(com.google.android.gms.dynamic.zza, android.os.Bundle):android.os.Bundle
      com.google.android.gms.dynamic.zza.zza(android.os.Bundle, com.google.android.gms.dynamic.zzi):void
      com.google.android.gms.dynamic.zza.zza(com.google.android.gms.dynamic.zza, com.google.android.gms.dynamic.LifecycleDelegate):com.google.android.gms.dynamic.LifecycleDelegate */
    public final void zza(T t) {
        LifecycleDelegate unused = this.zza.zza = (LifecycleDelegate) t;
        Iterator it = this.zza.zzc.iterator();
        while (it.hasNext()) {
            ((zzi) it.next()).zza(this.zza.zza);
        }
        this.zza.zzc.clear();
        Bundle unused2 = this.zza.zzb = null;
    }
}
