package com.google.android.gms.common.internal;

import android.content.Intent;
import android.support.p001v4.app.Fragment;

/* compiled from: DialogRedirect */
final class zzi extends zzg {
    private final /* synthetic */ Intent zza;
    private final /* synthetic */ Fragment zzb;
    private final /* synthetic */ int zzc;

    zzi(Intent intent, Fragment fragment, int i) {
        this.zza = intent;
        this.zzb = fragment;
        this.zzc = i;
    }

    public final void zza() {
        Intent intent = this.zza;
        if (intent != null) {
            this.zzb.startActivityForResult(intent, this.zzc);
        }
    }
}
