package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

/* compiled from: ConnectionlessInProgressCalls */
final class zzag implements PendingResult.zza {
    private final /* synthetic */ BasePendingResult zza;
    private final /* synthetic */ zzaf zzb;

    zzag(zzaf zzaf, BasePendingResult basePendingResult) {
        this.zzb = zzaf;
        this.zza = basePendingResult;
    }

    public final void zza(Status status) {
        this.zzb.zza.remove(this.zza);
    }
}
