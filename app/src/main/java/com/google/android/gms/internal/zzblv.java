package com.google.android.gms.internal;

import com.google.android.gms.common.internal.Hide;

@Hide
/* compiled from: PooledExecutorsProvider */
public final class zzblv {
    private static zzblx zza;

    @Hide
    public static synchronized zzblx zza() {
        zzblx zzblx;
        synchronized (zzblv.class) {
            if (zza == null) {
                zza = new zzblw();
            }
            zzblx = zza;
        }
        return zzblx;
    }
}
