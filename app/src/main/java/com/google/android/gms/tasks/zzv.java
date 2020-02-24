package com.google.android.gms.tasks;

import java.util.concurrent.Callable;

/* compiled from: Tasks */
final class zzv implements Runnable {
    private final /* synthetic */ zzu zza;
    private final /* synthetic */ Callable zzb;

    zzv(zzu zzu, Callable callable) {
        this.zza = zzu;
        this.zzb = callable;
    }

    public final void run() {
        try {
            this.zza.zza(this.zzb.call());
        } catch (Exception e) {
            this.zza.zza(e);
        }
    }
}
