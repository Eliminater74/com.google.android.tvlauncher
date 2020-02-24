package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzau;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/* compiled from: NamedThreadFactory */
public final class zzbmf implements ThreadFactory {
    private final String zza;
    private final int zzb;
    private final ThreadFactory zzc;

    public zzbmf(String str) {
        this(str, 0);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
     arg types: [java.lang.String, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T */
    private zzbmf(String str, int i) {
        this.zzc = Executors.defaultThreadFactory();
        this.zza = (String) zzau.zza((Object) str, (Object) "Name must not be null");
        this.zzb = 0;
    }

    public final Thread newThread(Runnable runnable) {
        Thread newThread = this.zzc.newThread(new zzbmh(runnable, 0));
        newThread.setName(this.zza);
        return newThread;
    }
}
