package com.google.android.gms.common.api.internal;

/* compiled from: UnconsumedApiCalls */
final class zzds implements zzdu {
    private final /* synthetic */ zzdr zza;

    zzds(zzdr zzdr) {
        this.zza = zzdr;
    }

    public final void zza(BasePendingResult<?> basePendingResult) {
        this.zza.zzb.remove(basePendingResult);
        if (basePendingResult.zzb() != null && this.zza.zze != null) {
            this.zza.zze.remove(basePendingResult.zzb().intValue());
        }
    }
}
