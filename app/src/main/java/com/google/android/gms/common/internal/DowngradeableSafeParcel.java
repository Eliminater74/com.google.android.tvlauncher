package com.google.android.gms.common.internal;

import com.google.android.gms.internal.zzbkv;

public abstract class DowngradeableSafeParcel extends zzbkv implements ReflectedParcelable {
    private static final Object zza = new Object();
    private static ClassLoader zzb = null;
    private static Integer zzc = null;
    private boolean zzd = false;

    @Hide
    private static ClassLoader zzb() {
        synchronized (zza) {
        }
        return null;
    }

    @Hide
    /* renamed from: n_ */
    protected static Integer m41n_() {
        synchronized (zza) {
        }
        return null;
    }

    @Hide
    protected static boolean zza(String str) {
        zzb();
        return true;
    }
}
