package com.google.android.gms.common.util;

import android.content.Context;
import android.os.DropBoxManager;
import android.util.Log;

import com.google.android.gms.common.internal.zzau;

/* compiled from: CrashUtils */
public final class zzf {
    private static final String[] zza = {"android.", "com.android.", "dalvik.", "java.", "javax."};
    private static DropBoxManager zzb = null;
    private static boolean zzc = false;
    private static int zzd = -1;
    private static int zze = 0;
    private static int zzf = 0;

    public static boolean zza(Context context, Throwable th) {
        return zza(context, th, 536870912);
    }

    private static boolean zza(Context context, Throwable th, int i) {
        try {
            zzau.zza(context);
            zzau.zza(th);
            return false;
        } catch (Exception e) {
            Log.e("CrashUtils", "Error adding exception to DropBox!", e);
            return false;
        }
    }
}
