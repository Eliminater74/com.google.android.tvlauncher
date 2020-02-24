package com.google.android.gms.phenotype;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.zzdl;
import com.google.android.gms.internal.zzdyr;
import com.google.android.gms.internal.zzeac;
import com.google.android.gms.phenotype.PhenotypeClient;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: PhenotypeClient */
final class zzw extends zzdl<zzeac, Configurations> {
    private final /* synthetic */ String zza;
    private final /* synthetic */ int zzb;
    private final /* synthetic */ String[] zzc;
    private final /* synthetic */ byte[] zzd;
    private final /* synthetic */ String zze;
    private final /* synthetic */ String zzf;

    zzw(PhenotypeClient phenotypeClient, String str, int i, String[] strArr, byte[] bArr, String str2, String str3) {
        this.zza = str;
        this.zzb = i;
        this.zzc = strArr;
        this.zzd = bArr;
        this.zze = str2;
        this.zzf = str3;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb2, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzdyr) ((zzeac) zzb2).zzag()).zza(new PhenotypeClient.zza(taskCompletionSource, null), this.zza, this.zzb, this.zzc, this.zzd, this.zze, this.zzf);
    }
}
