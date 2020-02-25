package com.google.android.gms.common.api.internal;

import android.os.RemoteException;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: UnregisterListenerMethod */
public abstract class zzdv<A extends Api.zzb, L> {
    private final zzcl<L> zza;

    protected zzdv(zzcl<L> zzcl) {
        this.zza = zzcl;
    }

    /* access modifiers changed from: protected */
    @Hide
    public abstract void zza(A a, TaskCompletionSource<Boolean> taskCompletionSource) throws RemoteException;

    @Hide
    public final zzcl<L> zza() {
        return this.zza;
    }
}
