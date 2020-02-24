package com.google.android.gms.phenotype;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.zzdl;
import com.google.android.gms.internal.zzdyr;
import com.google.android.gms.internal.zzeac;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: PhenotypeClient */
final class zzu extends zzdl<zzeac, Void> {
    final /* synthetic */ ServingVersion zza;

    zzu(PhenotypeClient phenotypeClient, ServingVersion servingVersion) {
        this.zza = servingVersion;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzdyr) ((zzeac) zzb).zzag()).zza(new zzv(this, taskCompletionSource), this.zza.getServingVersion());
    }
}
