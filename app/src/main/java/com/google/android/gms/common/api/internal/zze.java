package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: ApiCallRunner */
public final class zze extends zzc<Void> {
    private final zzcv<Api.zzb, ?> zzb;
    private final zzdv<Api.zzb, ?> zzc;

    public zze(zzcw zzcw, TaskCompletionSource<Void> taskCompletionSource) {
        super(3, taskCompletionSource);
        this.zzb = zzcw.zza;
        this.zzc = zzcw.zzb;
    }

    public final void zzb(zzbp<?> zzbp) throws RemoteException {
        this.zzb.zza(zzbp.zzb(), this.zza);
        if (this.zzb.zza() != null) {
            zzbp.zzc().put(this.zzb.zza(), new zzcw(this.zzb, this.zzc));
        }
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
