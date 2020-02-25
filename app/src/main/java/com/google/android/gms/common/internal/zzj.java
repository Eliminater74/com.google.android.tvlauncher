package com.google.android.gms.common.internal;

import android.content.Intent;

import com.google.android.gms.common.api.internal.zzcf;

/* compiled from: DialogRedirect */
final class zzj extends zzg {
    private final /* synthetic */ Intent zza;
    private final /* synthetic */ zzcf zzb;
    private final /* synthetic */ int zzc;

    zzj(Intent intent, zzcf zzcf, int i) {
        this.zza = intent;
        this.zzb = zzcf;
        this.zzc = i;
    }

    public final void zza() {
        Intent intent = this.zza;
        if (intent != null) {
            this.zzb.startActivityForResult(intent, this.zzc);
        }
    }
}
