package com.google.android.gms.clearcut;

import com.google.android.gms.clearcut.internal.zzl;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: BootCountClient */
final class zzc extends zzl {
    private final /* synthetic */ TaskCompletionSource zza;

    zzc(zzb zzb, TaskCompletionSource taskCompletionSource) {
        this.zza = taskCompletionSource;
    }

    public final void zza(Status status, int i) {
        this.zza.setResult(Integer.valueOf(i));
    }
}
