package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;

/* compiled from: DeviceProperties */
public final class zzi {
    private static Boolean zza;
    private static Boolean zzb;
    private static Boolean zzc;
    private static Boolean zzd;
    private static Boolean zze;

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003d, code lost:
        if (com.google.android.gms.common.util.zzi.zzb.booleanValue() != false) goto L_0x003f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean zza(android.content.res.Resources r4) {
        /*
            r0 = 0
            if (r4 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.lang.Boolean r1 = com.google.android.gms.common.util.zzi.zza
            if (r1 != 0) goto L_0x0046
            android.content.res.Configuration r1 = r4.getConfiguration()
            int r1 = r1.screenLayout
            r1 = r1 & 15
            r2 = 3
            r3 = 1
            if (r1 <= r2) goto L_0x0017
            r1 = 1
            goto L_0x0018
        L_0x0017:
            r1 = 0
        L_0x0018:
            if (r1 != 0) goto L_0x003f
            java.lang.Boolean r1 = com.google.android.gms.common.util.zzi.zzb
            if (r1 != 0) goto L_0x0037
            android.content.res.Configuration r4 = r4.getConfiguration()
            int r1 = r4.screenLayout
            r1 = r1 & 15
            if (r1 > r2) goto L_0x0030
            int r4 = r4.smallestScreenWidthDp
            r1 = 600(0x258, float:8.41E-43)
            if (r4 < r1) goto L_0x0030
            r4 = 1
            goto L_0x0031
        L_0x0030:
            r4 = 0
        L_0x0031:
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)
            com.google.android.gms.common.util.zzi.zzb = r4
        L_0x0037:
            java.lang.Boolean r4 = com.google.android.gms.common.util.zzi.zzb
            boolean r4 = r4.booleanValue()
            if (r4 == 0) goto L_0x0040
        L_0x003f:
            r0 = 1
        L_0x0040:
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r0)
            com.google.android.gms.common.util.zzi.zza = r4
        L_0x0046:
            java.lang.Boolean r4 = com.google.android.gms.common.util.zzi.zza
            boolean r4 = r4.booleanValue()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.zzi.zza(android.content.res.Resources):boolean");
    }

    @TargetApi(20)
    public static boolean zza(Context context) {
        if (zzc == null) {
            zzc = Boolean.valueOf(zzp.zzf() && context.getPackageManager().hasSystemFeature("android.hardware.type.watch"));
        }
        return zzc.booleanValue();
    }

    @TargetApi(24)
    public static boolean zzb(Context context) {
        return (!zzp.zzj() || zzc(context)) && zza(context);
    }

    @TargetApi(21)
    public static boolean zzc(Context context) {
        if (zzd == null) {
            zzd = Boolean.valueOf(zzp.zzg() && context.getPackageManager().hasSystemFeature("cn.google"));
        }
        return zzd.booleanValue();
    }

    public static boolean zzd(Context context) {
        if (zze == null) {
            zze = Boolean.valueOf(context.getPackageManager().hasSystemFeature("android.hardware.type.iot") || context.getPackageManager().hasSystemFeature("android.hardware.type.embedded"));
        }
        return zze.booleanValue();
    }
}
