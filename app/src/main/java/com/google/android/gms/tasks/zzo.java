package com.google.android.gms.tasks;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/* compiled from: OnSuccessTaskCompletionListener */
final class zzo<TResult, TContinuationResult> implements OnCanceledListener, OnFailureListener, OnSuccessListener<TContinuationResult>, zzq<TResult> {
    /* access modifiers changed from: private */
    public final SuccessContinuation<TResult, TContinuationResult> zzb;
    private final Executor zza;
    private final zzu<TContinuationResult> zzc;

    public zzo(@NonNull Executor executor, @NonNull SuccessContinuation<TResult, TContinuationResult> successContinuation, @NonNull zzu<TContinuationResult> zzu) {
        this.zza = executor;
        this.zzb = successContinuation;
        this.zzc = zzu;
    }

    public final void zza(@NonNull Task<TResult> task) {
        this.zza.execute(new zzp(this, task));
    }

    public final void zza() {
        throw new UnsupportedOperationException();
    }

    public final void onSuccess(TContinuationResult tcontinuationresult) {
        this.zzc.zza((Object) tcontinuationresult);
    }

    public final void onFailure(@NonNull Exception exc) {
        this.zzc.zza(exc);
    }

    public final void onCanceled() {
        this.zzc.zza();
    }
}
