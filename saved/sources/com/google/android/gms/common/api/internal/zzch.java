package com.google.android.gms.common.api.internal;

/* compiled from: LifecycleFragmentImpl */
final class zzch implements Runnable {
    private final /* synthetic */ LifecycleCallback zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ zzcg zzc;

    zzch(zzcg zzcg, LifecycleCallback lifecycleCallback, String str) {
        this.zzc = zzcg;
        this.zza = lifecycleCallback;
        this.zzb = str;
    }

    public final void run() {
        if (this.zzc.zzc > 0) {
            this.zza.zza(this.zzc.zzd != null ? this.zzc.zzd.getBundle(this.zzb) : null);
        }
        if (this.zzc.zzc >= 2) {
            this.zza.zzb();
        }
        if (this.zzc.zzc >= 3) {
            this.zza.zze();
        }
        if (this.zzc.zzc >= 4) {
            this.zza.zza();
        }
        if (this.zzc.zzc >= 5) {
            this.zza.zzh();
        }
    }
}
