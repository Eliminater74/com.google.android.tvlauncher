package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.concurrent.TimeUnit;

/* compiled from: PendingResultUtil */
final class zzap implements PendingResult.zza {
    private final /* synthetic */ PendingResult zza;
    private final /* synthetic */ TaskCompletionSource zzb;
    private final /* synthetic */ zzas zzc;
    private final /* synthetic */ zzat zzd;

    zzap(PendingResult pendingResult, TaskCompletionSource taskCompletionSource, zzas zzas, zzat zzat) {
        this.zza = pendingResult;
        this.zzb = taskCompletionSource;
        this.zzc = zzas;
        this.zzd = zzat;
    }

    public final void zza(Status status) {
        if (status.isSuccess()) {
            this.zzb.setResult(this.zzc.zza(this.zza.await(0, TimeUnit.MILLISECONDS)));
            return;
        }
        this.zzb.setException(this.zzd.zza(status));
    }
}
