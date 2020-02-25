package com.google.android.gms.internal;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzau;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Hide
/* compiled from: PeopleUtils */
public final class zzdyi {
    public static final Map<String, Integer> zza;
    public static final String[] zzb = new String[0];
    public static final Pattern zzc = Pattern.compile("\\,");
    public static final Pattern zzd = Pattern.compile(Pattern.quote("\u0001"));
    public static final String zze = "\u0001";
    private static final Handler zzf = new Handler(Looper.getMainLooper());
    private static final Pattern zzg = Pattern.compile("[     ᠎             　\t\u000b\f\u001c\u001d\u001e\u001f\n\r]+");
    private static final Pattern zzh = Pattern.compile(Pattern.quote("\u0002"));
    private static final String zzi = "\u0002";
    @SuppressLint({"TrulyRandom"})
    private static final SecureRandom zzj = new SecureRandom();

    static {
        HashMap hashMap = new HashMap();
        zza = hashMap;
        hashMap.put("circle", -1);
        zza.put("extendedCircles", 4);
        zza.put("myCircles", 3);
        zza.put("domain", 2);
        zza.put("public", 1);
        zza.put(null, -2);
    }

    public static String zza(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        return str;
    }

    public static String[] zzb(String str) {
        if (TextUtils.isEmpty(str)) {
            return zzb;
        }
        return zzc.split(str, 0);
    }

    public static String zzc(String str) {
        if (str == null || !str.startsWith("g:")) {
            return null;
        }
        return str.substring(2);
    }

    public static String zzd(String str) {
        zzau.zza((Object) str);
        String valueOf = String.valueOf("g:");
        String valueOf2 = String.valueOf(str);
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    public static String zze(String str) {
        if (str == null || !str.startsWith("e:")) {
            return null;
        }
        return str.substring(2);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
     arg types: [java.lang.String, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String */
    public static void zza(String str, String str2) {
        zzau.zza(str, (Object) str2);
        zzau.zzb(str.startsWith("g:") || str.startsWith("e:"), String.valueOf(str2).concat(": Expecting qualified-id, not gaia-id"));
    }

    public static String zzf(String str) {
        zzau.zza(str);
        String valueOf = String.valueOf("e:");
        String valueOf2 = String.valueOf(str);
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    public static boolean zzg(String str) {
        return str != null && str.startsWith("e:");
    }

    public static boolean zzh(String str) {
        return str != null && str.startsWith("g:");
    }

    public static boolean zzi(String str) {
        return zzg(str) || zzh(str);
    }

    public static String zzj(String str) {
        int i = 0;
        while (i < str.length() && str.charAt(i) == '0') {
            i++;
        }
        return str.substring(i);
    }
}
