package com.google.android.gms.common.api.internal;

import android.os.RemoteException;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: TaskApiCall */
public abstract class zzdl<A extends Api.zzb, TResult> {
    /* access modifiers changed from: protected */
    @Hide
    public abstract void zza(A a, TaskCompletionSource<TResult> taskCompletionSource) throws RemoteException;
}
