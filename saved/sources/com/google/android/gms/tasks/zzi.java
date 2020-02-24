package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

/* compiled from: OnCompleteCompletionListener */
final class zzi<TResult> implements zzq<TResult> {
    private final Executor zza;
    /* access modifiers changed from: private */
    public final Object zzb = new Object();
    /* access modifiers changed from: private */
    public OnCompleteListener<TResult> zzc;

    public zzi(@NonNull Executor executor, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        this.zza = executor;
        this.zzc = onCompleteListener;
    }

    public final void zza(@NonNull Task<TResult> task) {
        synchronized (this.zzb) {
            if (this.zzc != null) {
                this.zza.execute(new zzj(this, task));
            }
        }
    }

    public final void zza() {
        synchronized (this.zzb) {
            this.zzc = null;
        }
    }
}
