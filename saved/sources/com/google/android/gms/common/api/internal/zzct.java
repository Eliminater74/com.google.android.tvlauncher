package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;

/* compiled from: PendingResultFacade */
final class zzct extends ResultTransform<A, T> {
    private final /* synthetic */ ResultTransform zza;
    private final /* synthetic */ zzcq zzb;

    zzct(zzcq zzcq, ResultTransform resultTransform) {
        this.zzb = zzcq;
        this.zza = resultTransform;
    }

    public final PendingResult<T> onSuccess(A a) {
        return this.zza.onSuccess(this.zzb.zza((Result) a));
    }

    public final Status onFailure(Status status) {
        return this.zza.onFailure(status);
    }
}
