package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.GoogleApiAvailabilityLight;

/* compiled from: GoogleApiClientConnecting */
final class zzaq implements Runnable {
    private final /* synthetic */ zzap zza;

    zzaq(zzap zzap) {
        this.zza = zzap;
    }

    public final void run() {
        GoogleApiAvailabilityLight.zzc(this.zza.zzc);
    }
}
