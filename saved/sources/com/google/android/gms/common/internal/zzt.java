package com.google.android.gms.common.internal;

import android.util.Log;

/* compiled from: GmsLogger */
public final class zzt {
    private static final int zza = 15;
    private static final String zzb = null;
    private final String zzc;
    private final String zzd;

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
     arg types: [java.lang.String, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T */
    public zzt(String str, String str2) {
        zzau.zza((Object) str, (Object) "log tag cannot be null");
        zzau.zzb(str.length() <= 23, "tag \"%s\" is longer than the %d character maximum", str, 23);
        this.zzc = str;
        if (str2 == null || str2.length() <= 0) {
            this.zzd = null;
        } else {
            this.zzd = str2;
        }
    }

    public zzt(String str) {
        this(str, null);
    }

    private final boolean zza(int i) {
        return Log.isLoggable(this.zzc, i);
    }

    public final void zza(String str, String str2, Throwable th) {
        if (zza(4)) {
            Log.i(str, zza(str2), th);
        }
    }

    public final void zza(String str, String str2) {
        if (zza(5)) {
            Log.w(str, zza(str2));
        }
    }

    public final void zzb(String str, String str2, Throwable th) {
        if (zza(5)) {
            Log.w(str, zza(str2), th);
        }
    }

    public final void zza(String str, String str2, Object... objArr) {
        if (zza(5)) {
            Log.w(this.zzc, zza(str2, objArr));
        }
    }

    public final void zzb(String str, String str2) {
        if (zza(6)) {
            Log.e(str, zza(str2));
        }
    }

    public final void zzc(String str, String str2, Throwable th) {
        if (zza(6)) {
            Log.e(str, zza(str2), th);
        }
    }

    public final void zzb(String str, String str2, Object... objArr) {
        if (zza(6)) {
            Log.e(str, zza(str2, objArr));
        }
    }

    private final String zza(String str) {
        String str2 = this.zzd;
        if (str2 == null) {
            return str;
        }
        return str2.concat(str);
    }

    private final String zza(String str, Object... objArr) {
        String format = String.format(str, objArr);
        String str2 = this.zzd;
        if (str2 == null) {
            return format;
        }
        return str2.concat(format);
    }
}
