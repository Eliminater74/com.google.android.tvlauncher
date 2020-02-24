package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

@Hide
/* compiled from: PendingResultUtil */
public final class zzan {
    private static final zzat zza = new zzao();

    public static <R extends Result, T> Task<T> zza(PendingResult<R> pendingResult, zzas<R, T> zzas) {
        zzat zzat = zza;
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        pendingResult.zza(new zzap(pendingResult, taskCompletionSource, zzas, zzat));
        return taskCompletionSource.getTask();
    }

    public static <R extends Result, T extends Response<R>> Task<T> zza(PendingResult<R> pendingResult, T t) {
        return zza(pendingResult, new zzaq(t));
    }

    public static <R extends Result> Task<Void> zza(PendingResult<R> pendingResult) {
        return zza(pendingResult, new zzar());
    }
}
