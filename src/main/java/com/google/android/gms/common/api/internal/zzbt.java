package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.internal.BaseGmsClient;

/* compiled from: GoogleApiManager */
final class zzbt implements BaseGmsClient.SignOutCallbacks {
    final /* synthetic */ zzbp zza;

    zzbt(zzbp zzbp) {
        this.zza = zzbp;
    }

    public final void onSignOutComplete() {
        this.zza.zza.zzq.post(new zzbu(this));
    }
}
