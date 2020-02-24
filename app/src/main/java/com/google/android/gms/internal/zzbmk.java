package com.google.android.gms.internal;

import android.content.Context;

/* compiled from: Wrappers */
public final class zzbmk {
    private static zzbmk zzb = new zzbmk();
    private zzbmj zza = null;

    private final synchronized zzbmj zzb(Context context) {
        if (this.zza == null) {
            if (context.getApplicationContext() != null) {
                context = context.getApplicationContext();
            }
            this.zza = new zzbmj(context);
        }
        return this.zza;
    }

    public static zzbmj zza(Context context) {
        return zzb.zzb(context);
    }
}
