package com.google.android.gms.tasks;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/* compiled from: OnSuccessCompletionListener */
final class zzm<TResult> implements zzq<TResult> {
    /* access modifiers changed from: private */
    public final Object zzb = new Object();
    private final Executor zza;
    /* access modifiers changed from: private */
    public OnSuccessListener<? super TResult> zzc;

    public zzm(@NonNull Executor executor, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        this.zza = executor;
        this.zzc = onSuccessListener;
    }

    public final void zza(@NonNull Task<TResult> task) {
        if (task.isSuccessful()) {
            synchronized (this.zzb) {
                if (this.zzc != null) {
                    this.zza.execute(new zzn(this, task));
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
