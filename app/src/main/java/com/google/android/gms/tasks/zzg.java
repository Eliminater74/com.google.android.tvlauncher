package com.google.android.gms.tasks;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/* compiled from: OnCanceledCompletionListener */
final class zzg<TResult> implements zzq<TResult> {
    /* access modifiers changed from: private */
    public final Object zzb = new Object();
    private final Executor zza;
    /* access modifiers changed from: private */
    public OnCanceledListener zzc;

    public zzg(@NonNull Executor executor, @NonNull OnCanceledListener onCanceledListener) {
        this.zza = executor;
        this.zzc = onCanceledListener;
    }

    public final void zza(@NonNull Task task) {
        if (task.isCanceled()) {
            synchronized (this.zzb) {
                if (this.zzc != null) {
                    this.zza.execute(new zzh(this));
                }
            }
        }
    }

    public final void zza() {
        synchronized (this.zzb) {
            this.zzc = null;
        }
    }
}
