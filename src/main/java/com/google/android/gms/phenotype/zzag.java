package com.google.android.gms.phenotype;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.zzdl;
import com.google.android.gms.internal.zzdyr;
import com.google.android.gms.internal.zzeac;
import com.google.android.gms.phenotype.PhenotypeClient;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: PhenotypeClient */
final class zzag extends zzdl<zzeac, Void> {
    private final /* synthetic */ String zza;

    zzag(PhenotypeClient phenotypeClient, String str) {
        this.zza = str;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzdyr) ((zzeac) zzb).zzag()).zzb(new PhenotypeClient.zza(taskCompletionSource, null), this.zza);
    }
}
