package com.google.android.gms.common.stats;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.util.zzj;
import java.util.Arrays;
import java.util.List;

/* compiled from: WakeLockTracker */
public final class zze {
    private static zze zza = new zze();
    private static Boolean zzb;
    private static boolean zzc = false;

    public static zze zza() {
        return zza;
    }

    public final void zza(Context context, Intent intent, String str, String str2, String str3, int i, String str4) {
        Context context2 = context;
        zza(context2, intent.getStringExtra("WAKE_LOCK_KEY"), 7, str, str2, null, 1, Arrays.asList(str4));
    }

    public static void zza(Context context, String str, int i, String str2, String str3, String str4, int i2, List<String> list) {
        zza(context, str, i, str2, str3, str4, i2, list, 0);
    }

    public static void zza(Context context, String str, int i, String str2, String str3, String str4, int i2, List<String> list, long j) {
        List<String> list2;
        String str5;
        int i3 = i;
        List<String> list3 = list;
        if (zzb == null) {
            zzb = false;
        }
        if (zzb.booleanValue()) {
            if (TextUtils.isEmpty(str)) {
                String valueOf = String.valueOf(str);
                Log.e("WakeLockTracker", valueOf.length() != 0 ? "missing wakeLock key. ".concat(valueOf) : new String("missing wakeLock key. "));
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            if (7 == i3 || 8 == i3 || 10 == i3 || 11 == i3) {
                if (list3 == null || list.size() != 1) {
                    list2 = list3;
                } else {
                    if ("com.google.android.gms".equals(list3.get(0))) {
                        list3 = null;
                    }
                    list2 = list3;
                }
                long elapsedRealtime = SystemClock.elapsedRealtime();
                int zza2 = zzj.zza(context);
                String packageName = context.getPackageName();
                if ("com.google.android.gms".equals(packageName)) {
                    str5 = null;
                } else {
                    str5 = packageName;
                }
                WakeLockEvent wakeLockEvent = r1;
                String str6 = "WakeLockTracker";
                WakeLockEvent wakeLockEvent2 = new WakeLockEvent(currentTimeMillis, i, str2, i2, list2, str, elapsedRealtime, zza2, str3, str5, zzj.zzb(context), j, str4);
                try {
                    context.startService(new Intent().setComponent(zzb.zza).putExtra("com.google.android.gms.common.stats.EXTRA_LOG_EVENT", wakeLockEvent));
                } catch (Exception e) {
                    Log.wtf(str6, e);
                }
            }
        }
    }
}
