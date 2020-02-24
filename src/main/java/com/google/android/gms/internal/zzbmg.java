package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzau;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: NumberedThreadFactory */
public final class zzbmg implements ThreadFactory {
    private final String zza;
    private final int zzb;
    private final AtomicInteger zzc;
    private final ThreadFactory zzd;

    public zzbmg(String str) {
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
    private zzbmg(String str, int i) {
        this.zzc = new AtomicInteger();
        this.zzd = Executors.defaultThreadFactory();
        this.zza = (String) zzau.zza((Object) str, (Object) "Name must not be null");
        this.zzb = 0;
    }

    public final Thread newThread(Runnable runnable) {
        Thread newThread = this.zzd.newThread(new zzbmh(runnable, 0));
        String str = this.zza;
        int andIncrement = this.zzc.getAndIncrement();
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 13);
        sb.append(str);
        sb.append("[");
        sb.append(andIncrement);
        sb.append("]");
        newThread.setName(sb.toString());
        return newThread;
    }
}
