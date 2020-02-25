package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: ApiCallRunner */
public final class zzf<TResult> extends zzb {
    private final zzdl<Api.zzb, TResult> zza;
    private final TaskCompletionSource<TResult> zzb;
    private final zzdg zzc;

    public zzf(int i, zzdl<Api.zzb, TResult> zzdl, TaskCompletionSource<TResult> taskCompletionSource, zzdg zzdg) {
        super(i);
        this.zzb = taskCompletionSource;
        this.zza = zzdl;
        this.zzc = zzdg;
    }

    public final void zza(zzbp<?> zzbp) throws DeadObjectException {
        try {
            this.zza.zza(zzbp.zzb(), this.zzb);
        } catch (DeadObjectException e) {
            throw e;
        } catch (RemoteException e2) {
            zza(zzb.zzb(e2));
        } catch (RuntimeException e3) {
            this.zzb.trySetException(e3);
        }
    }

    public final void zza(@NonNull Status status) {
        this.zzb.trySetException(this.zzc.zza(status));
    }

    public final void zza(@NonNull zzaf zzaf, boolean z) {
        zzaf.zza(this.zzb, z);
    }
}
