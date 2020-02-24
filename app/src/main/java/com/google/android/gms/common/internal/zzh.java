package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.Intent;

/* compiled from: DialogRedirect */
final class zzh extends zzg {
    private final /* synthetic */ Intent zza;
    private final /* synthetic */ Activity zzb;
    private final /* synthetic */ int zzc;

    zzh(Intent intent, Activity activity, int i) {
        this.zza = intent;
        this.zzb = activity;
        this.zzc = i;
    }

    public final void zza() {
        Intent intent = this.zza;
        if (intent != null) {
            this.zzb.startActivityForResult(intent, this.zzc);
        }
    }
}
