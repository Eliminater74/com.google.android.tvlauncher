package com.google.android.gms.phenotype;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: PhenotypeClient */
final class zzv extends PhenotypeClient.zza {
    private final /* synthetic */ zzu zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzv(zzu zzu, TaskCompletionSource taskCompletionSource) {
        super(taskCompletionSource, null);
        this.zza = zzu;
    }

    public final void zza(Status status) {
        super.zza(status);
        if (status.isSuccess()) {
            synchronized (PhenotypeClient.class) {
                long unused = PhenotypeClient.zzb = this.zza.zza.getServingVersion();
            }
        }
    }
}
