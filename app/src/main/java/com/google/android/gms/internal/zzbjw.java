package com.google.android.gms.internal;

import android.util.Log;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gsf.Gservices;

@Hide
/* compiled from: GservicesValue */
public class zzbjw<T> {
    private static final Object zza = new Object();
    private static zzbkc zzb = null;
    private static int zzc = 0;
    private static String zzd = Gservices.PERMISSION_READ_GSERVICES;
    private final String zze;
    private final T zzf;
    private T zzg = null;

    protected zzbjw(String str, T t) {
        this.zze = str;
        this.zzf = t;
    }

    private static boolean zzb() {
        synchronized (zza) {
        }
        return false;
    }

    public static zzbjw<Boolean> zza(String str, boolean z) {
        return new zzbjx(str, Boolean.valueOf(z));
    }

    public static zzbjw<Long> zza(String str, Long l) {
        return new zzbjy(str, l);
    }

    public static zzbjw<Integer> zza(String str, Integer num) {
        return new zzbjz(str, num);
    }

    public static zzbjw<Float> zza(String str, Float f) {
        return new zzbka(str, f);
    }

    public static zzbjw<String> zza(String str, String str2) {
        return new zzbkb(str, str2);
    }

    public final void zza(T t) {
        Log.w("GservicesValue", "GservicesValue.override(): test should probably call initForTests() first");
        this.zzg = t;
        synchronized (zza) {
            zzb();
        }
    }

    public final void zza() {
        this.zzg = null;
    }
}
