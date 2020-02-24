package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;

/* compiled from: SignInCoordinator */
final class zzdc implements Runnable {
    private final /* synthetic */ zzdb zza;

    zzdc(zzdb zzdb) {
        this.zza = zzdb;
    }

    public final void run() {
        this.zza.zzh.zza(new ConnectionResult(4));
    }
}
