package com.google.android.gms.phenotype;

import android.os.RemoteException;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.zzdl;
import com.google.android.gms.internal.zzdyr;
import com.google.android.gms.internal.zzeac;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: PhenotypeClient */
final class zzaf extends zzdl<zzeac, Configurations> {
    private final /* synthetic */ String zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ String zzc;

    zzaf(PhenotypeClient phenotypeClient, String str, String str2, String str3) {
        this.zza = str;
        this.zzb = str2;
        this.zzc = str3;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb2, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzdyr) ((zzeac) zzb2).zzag()).zza(new PhenotypeClient.zza(taskCompletionSource, null), this.zza, this.zzb, this.zzc);
    }
}
