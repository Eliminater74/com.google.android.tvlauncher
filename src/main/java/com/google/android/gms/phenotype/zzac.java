package com.google.android.gms.phenotype;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.zzdl;
import com.google.android.gms.internal.zzdyr;
import com.google.android.gms.internal.zzeac;
import com.google.android.gms.phenotype.PhenotypeClient;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: PhenotypeClient */
final class zzac extends zzdl<zzeac, Void> {
    private final /* synthetic */ RegistrationInfo[] zza;

    zzac(PhenotypeClient phenotypeClient, RegistrationInfo[] registrationInfoArr) {
        this.zza = registrationInfoArr;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzdyr) ((zzeac) zzb).zzag()).zza(new PhenotypeClient.zza(taskCompletionSource, null), this.zza);
    }
}
