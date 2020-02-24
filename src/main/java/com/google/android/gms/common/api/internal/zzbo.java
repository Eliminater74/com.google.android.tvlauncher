package com.google.android.gms.common.api.internal;

/* compiled from: GoogleApiManager */
final class zzbo implements zzm {
    private final /* synthetic */ zzbn zza;

    zzbo(zzbn zzbn) {
        this.zza = zzbn;
    }

    public final void zza(boolean z) {
        this.zza.zzq.sendMessage(this.zza.zzq.obtainMessage(1, Boolean.valueOf(z)));
    }
}
