package com.google.android.gms.tasks;

import android.support.annotation.NonNull;

public class TaskCompletionSource<TResult> {
    /* access modifiers changed from: private */
    public final zzu<TResult> zza;
    private final CancellationToken zzb;

    public TaskCompletionSource() {
        this.zza = new zzu<>();
        this.zzb = null;
    }

    public TaskCompletionSource(@NonNull CancellationToken cancellationToken) {
        this.zza = new zzu<>();
        this.zzb = cancellationToken.onCanceledRequested(new zzs(this));
    }

    public void setResult(TResult tresult) {
        this.zza.zza((Object) tresult);
    }

    public boolean trySetResult(TResult tresult) {
        return this.zza.zzb((Object) tresult);
    }

    public void setException(@NonNull Exception exc) {
        this.zza.zza(exc);
    }

    public boolean trySetException(@NonNull Exception exc) {
        return this.zza.zzb(exc);
    }

    @NonNull
    public Task<TResult> getTask() {
        return this.zza;
    }
}
