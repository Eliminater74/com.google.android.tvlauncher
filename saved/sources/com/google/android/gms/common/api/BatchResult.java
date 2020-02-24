package com.google.android.gms.common.api;

import com.google.android.gms.common.internal.zzau;
import java.util.concurrent.TimeUnit;

public final class BatchResult implements Result {
    private final Status zza;
    private final PendingResult<?>[] zzb;

    BatchResult(Status status, PendingResult<?>[] pendingResultArr) {
        this.zza = status;
        this.zzb = pendingResultArr;
    }

    public final Status getStatus() {
        return this.zza;
    }

    public final <R extends Result> R take(BatchResultToken<R> batchResultToken) {
        zzau.zzb(batchResultToken.mId < this.zzb.length, "The result token does not belong to this batch");
        return this.zzb[batchResultToken.mId].await(0, TimeUnit.MILLISECONDS);
    }
}
