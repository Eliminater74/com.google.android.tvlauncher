package com.google.android.gms.common.api.internal;

/* compiled from: GoogleApiManager */
final class zzbu implements Runnable {
    private final /* synthetic */ zzbt zza;

    zzbu(zzbt zzbt) {
        this.zza = zzbt;
    }

    public final void run() {
        this.zza.zza.zzc.disconnect();
    }
}
