package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: ApiCallRunner */
abstract class zzc<T> extends zzb {
    protected final TaskCompletionSource<T> zza;

    public zzc(int i, TaskCompletionSource<T> taskCompletionSource) {
        super(i);
        this.zza = taskCompletionSource;
    }

    /* access modifiers changed from: protected */
    public abstract void zzb(zzbp<?> zzbp) throws RemoteException;

    public void zza(@NonNull Status status) {
        this.zza.trySetException(new ApiException(status));
    }

    public void zza(@NonNull RuntimeException runtimeException) {
        this.zza.trySetException(runtimeException);
    }

    public void zza(@NonNull zzaf zzaf, boolean z) {
    }

    public final void zza(zzbp<?> zzbp) throws DeadObjectException {
        try {
            zzb(zzbp);
        } catch (DeadObjectException e) {
            zza(zzb.zzb(e));
            throw e;
        } catch (RemoteException e2) {
            zza(zzb.zzb(e2));
        } catch (RuntimeException e3) {
            zza(e3);
        }
    }
}
