package com.google.android.gms.common.api.internal;

import android.app.Dialog;

/* compiled from: BaseLifecycleHelper */
final class zzs extends zzbz {
    private final /* synthetic */ Dialog zza;
    private final /* synthetic */ zzr zzb;

    zzs(zzr zzr, Dialog dialog) {
        this.zzb = zzr;
        this.zza = dialog;
    }

    public final void zza() {
        this.zzb.zza.zzd();
        if (this.zza.isShowing()) {
            this.zza.dismiss();
        }
    }
}
