package com.google.android.gms.phenotype;

import android.util.Log;
import com.google.android.gms.phenotype.PhenotypeFlagCommitter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.concurrent.Executor;

final /* synthetic */ class zzau implements OnCompleteListener {
    private final PhenotypeFlagCommitter zza;
    private final PhenotypeFlagCommitter.Callback zzb;
    private final Executor zzc;
    private final int zzd;
    private final String zze;

    zzau(PhenotypeFlagCommitter phenotypeFlagCommitter, PhenotypeFlagCommitter.Callback callback, Executor executor, int i, String str) {
        this.zza = phenotypeFlagCommitter;
        this.zzb = callback;
        this.zzc = executor;
        this.zzd = i;
        this.zze = str;
    }

    public final void onComplete(Task task) {
        PhenotypeFlagCommitter phenotypeFlagCommitter = this.zza;
        PhenotypeFlagCommitter.Callback callback = this.zzb;
        Executor executor = this.zzc;
        int i = this.zzd;
        String str = this.zze;
        if (!task.isSuccessful()) {
            String str2 = phenotypeFlagCommitter.packageName;
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 31);
            sb.append("Retrieving snapshot for ");
            sb.append(str2);
            sb.append(" failed");
            Log.e("PhenotypeFlagCommitter", sb.toString());
            if (callback != null) {
                callback.onFinish(false);
                return;
            }
            return;
        }
        phenotypeFlagCommitter.handleConfigurations((Configurations) task.getResult());
        phenotypeFlagCommitter.client.commitToConfiguration(((Configurations) task.getResult()).snapshotToken).addOnCompleteListener(executor, new zzav(phenotypeFlagCommitter, i, str, executor, callback));
    }
}
