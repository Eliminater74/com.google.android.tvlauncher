package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: ConnectionlessInProgressCalls */
final class zzah implements OnCompleteListener<TResult> {
    private final /* synthetic */ TaskCompletionSource zza;
    private final /* synthetic */ zzaf zzb;

    zzah(zzaf zzaf, TaskCompletionSource taskCompletionSource) {
        this.zzb = zzaf;
        this.zza = taskCompletionSource;
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        this.zzb.zzb.remove(this.zza);
    }
}
