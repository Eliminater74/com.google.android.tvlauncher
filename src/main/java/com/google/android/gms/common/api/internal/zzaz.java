package com.google.android.gms.common.api.internal;

import android.support.annotation.WorkerThread;

/* compiled from: GoogleApiClientConnecting */
abstract class zzaz implements Runnable {
    private final /* synthetic */ zzap zza;

    private zzaz(zzap zzap) {
        this.zza = zzap;
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public abstract void zza();

    @WorkerThread
    public void run() {
        this.zza.zzb.lock();
        try {
            if (!Thread.interrupted()) {
                zza();
                this.zza.zzb.unlock();
            }
        } catch (RuntimeException e) {
            this.zza.zza.zza(e);
        } finally {
            this.zza.zzb.unlock();
        }
    }

    /* synthetic */ zzaz(zzap zzap, zzaq zzaq) {
        this(zzap);
    }
}
