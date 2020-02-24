package com.google.android.gms.common.api.internal;

/* compiled from: CompositeGoogleApiClient */
final class zzx implements Runnable {
    private final /* synthetic */ zzw zza;

    zzx(zzw zzw) {
        this.zza = zzw;
    }

    public final void run() {
        this.zza.zzm.lock();
        try {
            this.zza.zzh();
        } finally {
            this.zza.zzm.unlock();
        }
    }
}
