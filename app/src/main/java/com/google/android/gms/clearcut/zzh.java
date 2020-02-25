package com.google.android.gms.clearcut;

import android.os.RemoteException;

import com.google.android.gms.clearcut.internal.zzi;
import com.google.android.gms.clearcut.internal.zzq;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.zzdl;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: ClearcutLoggerDebugClient */
final class zzh extends zzdl<zzi, CollectForDebugParcelable> {
    private final /* synthetic */ CollectForDebugParcelable zza;

    zzh(ClearcutLoggerDebugClient clearcutLoggerDebugClient, CollectForDebugParcelable collectForDebugParcelable) {
        this.zza = collectForDebugParcelable;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzq) ((zzi) zzb).zzag()).zza(new ClearcutLoggerDebugClient.zza(taskCompletionSource, null), this.zza);
    }
}
