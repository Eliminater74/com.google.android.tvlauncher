package com.google.android.gms.clearcut;

import android.os.RemoteException;

import com.google.android.gms.clearcut.internal.zza;
import com.google.android.gms.clearcut.internal.zzm;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.zzdl;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: BootCountClient */
final class zzb extends zzdl<zza, Integer> {
    zzb(BootCountClient bootCountClient) {
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzm) ((zza) zzb).zzag()).zza(new zzc(this, taskCompletionSource));
    }
}
