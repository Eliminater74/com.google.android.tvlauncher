package com.google.android.gms.tasks;

/* compiled from: TaskCompletionSource */
final class zzs implements OnTokenCanceledListener {
    private final /* synthetic */ TaskCompletionSource zza;

    zzs(TaskCompletionSource taskCompletionSource) {
        this.zza = taskCompletionSource;
    }

    public final void onCanceled() {
        this.zza.zza.zza();
    }
}
