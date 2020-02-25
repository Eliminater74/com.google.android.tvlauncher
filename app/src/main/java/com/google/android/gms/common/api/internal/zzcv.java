package com.google.android.gms.common.api.internal;

import android.os.RemoteException;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: RegisterListenerMethod */
public abstract class zzcv<A extends Api.zzb, L> {
    private final zzcj<L> zza;

    protected zzcv(zzcj<L> zzcj) {
        this.zza = zzcj;
    }

    /* access modifiers changed from: protected */
    @Hide
    public abstract void zza(A a, TaskCompletionSource<Void> taskCompletionSource) throws RemoteException;

    @Hide
    public final zzcl<L> zza() {
        return this.zza.zzc();
    }

    public final void zzb() {
        this.zza.zzb();
    }
}
