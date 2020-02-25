package com.google.android.gms.dynamite;

import android.content.Context;

/* compiled from: DynamiteModule */
final class zzb implements DynamiteModule.zzd {
    zzb() {
    }

    public final zzj zza(Context context, String str, zzi zzi) throws DynamiteModule.zzc {
        zzj zzj = new zzj();
        zzj.zzb = zzi.zza(context, str, true);
        if (zzj.zzb != 0) {
            zzj.zzc = 1;
        } else {
            zzj.zza = zzi.zza(context, str);
            if (zzj.zza != 0) {
                zzj.zzc = -1;
            }
        }
        return zzj;
    }
}
