package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.util.zzp;

/* compiled from: InstantApps */
public final class zzbmi {
    private static Context zza;
    private static Boolean zzb;

    public static synchronized boolean zza(Context context) {
        synchronized (zzbmi.class) {
            Context applicationContext = context.getApplicationContext();
            if (zza == null || zzb == null || zza != applicationContext) {
                zzb = null;
                if (zzp.zzk()) {
                    zzb = Boolean.valueOf(applicationContext.getPackageManager().isInstantApp());
                } else {
                    try {
                        context.getClassLoader().loadClass("com.google.android.instantapps.supervisor.InstantAppsRuntime");
                        zzb = true;
                    } catch (ClassNotFoundException e) {
                        zzb = false;
                    }
                }
                zza = applicationContext;
                boolean booleanValue = zzb.booleanValue();
                return booleanValue;
            }
            boolean booleanValue2 = zzb.booleanValue();
            return booleanValue2;
        }
    }
}
