package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule;

/* compiled from: DynamiteModule */
final class zzf implements DynamiteModule.zzd {
    zzf() {
    }

    public final zzj zza(Context context, String str, zzi zzi) throws DynamiteModule.zzc {
        zzj zzj = new zzj();
        zzj.zza = zzi.zza(context, str);
        zzj.zzb = zzi.zza(context, str, true);
        if (zzj.zza == 0 && zzj.zzb == 0) {
            zzj.zzc = 0;
        } else if (zzj.zzb >= zzj.zza) {
            zzj.zzc = 1;
        } else {
            zzj.zzc = -1;
        }
        return zzj;
    }
}
