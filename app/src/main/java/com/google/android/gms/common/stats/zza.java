package com.google.android.gms.common.stats;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;

import java.util.Collections;
import java.util.List;

/* compiled from: ConnectionTracker */
public final class zza {
    private static final Object zza = new Object();
    private static volatile zza zzb;
    private static boolean zzc = false;
    private final List<String> zzd = Collections.EMPTY_LIST;
    private final List<String> zze = Collections.EMPTY_LIST;
    private final List<String> zzf = Collections.EMPTY_LIST;
    private final List<String> zzg = Collections.EMPTY_LIST;

    private zza() {
    }

    public static zza zza() {
        if (zzb == null) {
            synchronized (zza) {
                if (zzb == null) {
                    zzb = new zza();
                }
            }
        }
        return zzb;
    }

    public final boolean zza(Context context, String str, Intent intent, ServiceConnection serviceConnection, int i) {
        boolean z;
        ComponentName component = intent.getComponent();
        if (component == null) {
            z = false;
        } else {
            z = zzd.zzb(context, component.getPackageName());
        }
        if (!z) {
            return context.bindService(intent, serviceConnection, i);
        }
        Log.w("ConnectionTracker", "Attempted to bind to a service in a STOPPED package.");
        return false;
    }

    public final boolean zza(Context context, Intent intent, ServiceConnection serviceConnection, int i) {
        return zza(context, context.getClass().getName(), intent, serviceConnection, i);
    }
}
