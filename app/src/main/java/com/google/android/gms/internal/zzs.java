package com.google.android.gms.internal;

/* compiled from: Request */
final class zzs implements Runnable {
    private final /* synthetic */ String zza;
    private final /* synthetic */ long zzb;
    private final /* synthetic */ zzr zzc;

    zzs(zzr zzr, String str, long j) {
        this.zzc = zzr;
        this.zza = str;
        this.zzb = j;
    }

    public final void run() {
        this.zzc.zza.zza(this.zza, this.zzb);
        this.zzc.zza.zza(toString());
    }
}
