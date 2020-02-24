package com.google.android.gms.tasks;

/* compiled from: CancellationTokenImpl */
final class zzb implements OnSuccessListener<Void> {
    private final /* synthetic */ OnTokenCanceledListener zza;

    zzb(zza zza2, OnTokenCanceledListener onTokenCanceledListener) {
        this.zza = onTokenCanceledListener;
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        this.zza.onCanceled();
    }
}
