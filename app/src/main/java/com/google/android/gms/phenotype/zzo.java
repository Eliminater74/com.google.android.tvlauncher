package com.google.android.gms.phenotype;

import android.os.RemoteException;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.zzdl;
import com.google.android.gms.internal.zzdyr;
import com.google.android.gms.internal.zzeac;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: PhenotypeClient */
final class zzo extends zzdl<zzeac, Void> {
    private final /* synthetic */ String zza;
    private final /* synthetic */ int zzb;
    private final /* synthetic */ String[] zzc;
    private final /* synthetic */ byte[] zzd;

    zzo(PhenotypeClient phenotypeClient, String str, int i, String[] strArr, byte[] bArr) {
        this.zza = str;
        this.zzb = i;
        this.zzc = strArr;
        this.zzd = bArr;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb2, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzdyr) ((zzeac) zzb2).zzag()).zza(new PhenotypeClient.zza(taskCompletionSource, null), this.zza, this.zzb, this.zzc, this.zzd);
    }
}
