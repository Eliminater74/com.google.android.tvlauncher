package com.google.android.gms.internal;

import com.google.android.gms.common.internal.Hide;

@Hide
/* compiled from: Flag */
public abstract class zzcmt<T> {
    private final int zza;
    private final String zzb;
    private final T zzc;

    private zzcmt(int i, String str, T t) {
        this.zza = i;
        this.zzb = str;
        this.zzc = t;
        zzcne.zza().zza(this);
    }

    /* access modifiers changed from: protected */
    @Hide
    public abstract T zza(zzcnb zzcnb);

    public final String zza() {
        return this.zzb;
    }

    public final T zzb() {
        return this.zzc;
    }

    @Hide
    public static zzcmv zza(int i, String str, Boolean bool) {
        return new zzcmv(0, str, bool);
    }

    @Hide
    public static zzcmw zza(int i, String str, int i2) {
        return new zzcmw(0, str, Integer.valueOf(i2));
    }

    @Hide
    public static zzcmx zza(int i, String str, long j) {
        return new zzcmx(0, str, Long.valueOf(j));
    }

    @Hide
    public final int zzc() {
        return this.zza;
    }
}
