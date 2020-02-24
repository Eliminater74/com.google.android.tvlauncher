package com.google.android.gms.common;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzae;
import com.google.android.gms.common.internal.zzaf;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamite.DynamiteModule;

@Hide
/* compiled from: GoogleCertificates */
final class zze {
    private static volatile zzae zza;
    private static final Object zzb = new Object();
    private static Context zzc;

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0019, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static synchronized void zza(android.content.Context r2) {
        /*
            java.lang.Class<com.google.android.gms.common.zze> r0 = com.google.android.gms.common.zze.class
            monitor-enter(r0)
            android.content.Context r1 = com.google.android.gms.common.zze.zzc     // Catch:{ all -> 0x001a }
            if (r1 != 0) goto L_0x0011
            if (r2 == 0) goto L_0x0018
            android.content.Context r2 = r2.getApplicationContext()     // Catch:{ all -> 0x001a }
            com.google.android.gms.common.zze.zzc = r2     // Catch:{ all -> 0x001a }
            monitor-exit(r0)
            return
        L_0x0011:
            java.lang.String r2 = "GoogleCertificates"
            java.lang.String r1 = "GoogleCertificates has been initialized already"
            android.util.Log.w(r2, r1)     // Catch:{ all -> 0x001a }
        L_0x0018:
            monitor-exit(r0)
            return
        L_0x001a:
            r2 = move-exception
            monitor-exit(r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.zze.zza(android.content.Context):void");
    }

    static zzn zza(String str, zzf zzf, boolean z) {
        try {
            if (zza == null) {
                zzau.zza(zzc);
                synchronized (zzb) {
                    if (zza == null) {
                        zza = zzaf.zza(DynamiteModule.zza(zzc, DynamiteModule.zzc, "com.google.android.gms.googlecertificates").zza("com.google.android.gms.common.GoogleCertificatesImpl"));
                    }
                }
            }
            zzau.zza(zzc);
            try {
                if (zza.zza(new zzl(str, zzf, z), zzn.zza(zzc.getPackageManager()))) {
                    return zzn.zza();
                }
                boolean z2 = true;
                if (z || !zza(str, zzf, true).zza) {
                    z2 = false;
                }
                return zzn.zza(str, zzf, z, z2);
            } catch (RemoteException e) {
                Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
                return zzn.zza("module call", e);
            }
        } catch (DynamiteModule.zzc e2) {
            return zzn.zza("module init", e2);
        }
    }
}
