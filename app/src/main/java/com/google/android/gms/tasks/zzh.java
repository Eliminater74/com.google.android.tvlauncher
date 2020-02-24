package com.google.android.gms.tasks;

/* compiled from: OnCanceledCompletionListener */
final class zzh implements Runnable {
    private final /* synthetic */ zzg zza;

    zzh(zzg zzg) {
        this.zza = zzg;
    }

    public final void run() {
        synchronized (this.zza.zzb) {
            if (this.zza.zzc != null) {
                this.zza.zzc.onCanceled();
            }
        }
    }
}
