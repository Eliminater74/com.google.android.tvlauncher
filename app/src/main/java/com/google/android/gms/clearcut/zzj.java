package com.google.android.gms.clearcut;

import android.os.RemoteException;

import com.google.android.gms.clearcut.internal.zzi;
import com.google.android.gms.clearcut.internal.zzq;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.zzdl;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: ClearcutLoggerDebugClient */
final class zzj extends zzdl<zzi, Long> {
    zzj(ClearcutLoggerDebugClient clearcutLoggerDebugClient) {
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzq) ((zzi) zzb).zzag()).zzd(new ClearcutLoggerDebugClient.zza(taskCompletionSource, null));
    }
}
