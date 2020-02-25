package com.google.android.gms.common.api;

import android.os.Looper;

import com.google.android.gms.common.api.internal.zzdg;
import com.google.android.gms.common.api.internal.zzh;
import com.google.android.gms.common.internal.zzau;

/* compiled from: GoogleApi */
public final class zzd {
    private zzdg zza;
    private Looper zzb;

    public final zzd zza(zzdg zzdg) {
        zzau.zza(zzdg, "StatusExceptionMapper must not be null.");
        this.zza = zzdg;
        return this;
    }

    public final zzd zza(Looper looper) {
        zzau.zza(looper, "Looper must not be null.");
        this.zzb = looper;
        return this;
    }

    public final GoogleApi.zza zza() {
        if (this.zza == null) {
            this.zza = new zzh();
        }
        if (this.zzb == null) {
            this.zzb = Looper.getMainLooper();
        }
        return new GoogleApi.zza(this.zza, this.zzb);
    }
}
