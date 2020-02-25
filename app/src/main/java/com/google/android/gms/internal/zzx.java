package com.google.android.gms.internal;

/* compiled from: Response */
public final class zzx<T> {
    public final T zza;
    public final zzc zzb;
    public final zzae zzc;
    public boolean zzd;

    private zzx(T t, zzc zzc2) {
        this.zzd = false;
        this.zza = t;
        this.zzb = zzc2;
        this.zzc = null;
    }

    private zzx(zzae zzae) {
        this.zzd = false;
        this.zza = null;
        this.zzb = null;
        this.zzc = zzae;
    }

    public static <T> zzx<T> zza(T t, zzc zzc2) {
        return new zzx<>(t, zzc2);
    }

    public static <T> zzx<T> zza(zzae zzae) {
        return new zzx<>(zzae);
    }
}
