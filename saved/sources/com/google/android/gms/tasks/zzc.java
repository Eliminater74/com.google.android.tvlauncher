package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

/* compiled from: ContinueWithCompletionListener */
final class zzc<TResult, TContinuationResult> implements zzq<TResult> {
    private final Executor zza;
    /* access modifiers changed from: private */
    public final Continuation<TResult, TContinuationResult> zzb;
    /* access modifiers changed from: private */
    public final zzu<TContinuationResult> zzc;

    public zzc(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation, @NonNull zzu<TContinuationResult> zzu) {
        this.zza = executor;
        this.zzb = continuation;
        this.zzc = zzu;
    }

    public final void zza(@NonNull Task<TResult> task) {
        this.zza.execute(new zzd(this, task));
    }

    public final void zza() {
        throw new UnsupportedOperationException();
    }
}
