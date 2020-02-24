package com.google.android.gms.clearcut;

import android.os.RemoteException;
import com.google.android.gms.clearcut.ClearcutLoggerDebugClient;
import com.google.android.gms.clearcut.internal.zzq;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.zzdl;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: ClearcutLoggerDebugClient */
final class zzi extends zzdl<com.google.android.gms.clearcut.internal.zzi, Void> {
    zzi(ClearcutLoggerDebugClient clearcutLoggerDebugClient) {
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzq) ((com.google.android.gms.clearcut.internal.zzi) zzb).zzag()).zzc(new ClearcutLoggerDebugClient.zza(taskCompletionSource, null));
    }
}
