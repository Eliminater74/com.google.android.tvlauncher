package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: ApiCallRunner */
public final class zzg extends zzc<Boolean> {
    private final zzcl<?> zzb;

    public zzg(zzcl<?> zzcl, TaskCompletionSource<Boolean> taskCompletionSource) {
        super(4, taskCompletionSource);
        this.zzb = zzcl;
    }

    public final void zzb(zzbp<?> zzbp) throws RemoteException {
        zzcw remove = zzbp.zzc().remove(this.zzb);
        if (remove != null) {
            remove.zzb.zza(zzbp.zzb(), this.zza);
            remove.zza.zzb();
            return;
        }
        this.zza.trySetResult(false);
    }

    public final /* bridge */ /* synthetic */ void zza(@NonNull zzaf zzaf, boolean z) {
    }

    public final /* bridge */ /* synthetic */ void zza(@NonNull RuntimeException runtimeException) {
        super.zza(runtimeException);
    }

    public final /* bridge */ /* synthetic */ void zza(@NonNull Status status) {
        super.zza(status);
    }
}
