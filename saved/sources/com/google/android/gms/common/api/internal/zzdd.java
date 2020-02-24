package com.google.android.gms.common.api.internal;

import com.google.android.gms.internal.zzemf;

/* compiled from: SignInCoordinator */
final class zzdd implements Runnable {
    private final /* synthetic */ zzemf zza;
    private final /* synthetic */ zzdb zzb;

    zzdd(zzdb zzdb, zzemf zzemf) {
        this.zzb = zzdb;
        this.zza = zzemf;
    }

    public final void run() {
        this.zzb.zzb(this.zza);
    }
}
