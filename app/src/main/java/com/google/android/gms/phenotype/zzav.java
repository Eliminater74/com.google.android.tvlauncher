package com.google.android.gms.phenotype;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.Executor;

final /* synthetic */ class zzav implements OnCompleteListener {
    private final PhenotypeFlagCommitter zza;
    private final int zzb;
    private final String zzc;
    private final Executor zzd;
    private final PhenotypeFlagCommitter.Callback zze;

    zzav(PhenotypeFlagCommitter phenotypeFlagCommitter, int i, String str, Executor executor, PhenotypeFlagCommitter.Callback callback) {
        this.zza = phenotypeFlagCommitter;
        this.zzb = i;
        this.zzc = str;
        this.zzd = executor;
        this.zze = callback;
    }

    public final void onComplete(Task task) {
        this.zza.zza(this.zzb, this.zzc, this.zzd, this.zze, task);
    }
}
