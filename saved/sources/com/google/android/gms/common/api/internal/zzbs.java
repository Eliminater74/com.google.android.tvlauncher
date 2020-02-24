package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;

/* compiled from: GoogleApiManager */
final class zzbs implements Runnable {
    private final /* synthetic */ ConnectionResult zza;
    private final /* synthetic */ zzbp zzb;

    zzbs(zzbp zzbp, ConnectionResult connectionResult) {
        this.zzb = zzbp;
        this.zza = connectionResult;
    }

    public final void run() {
        this.zzb.onConnectionFailed(this.zza);
    }
}
