package com.google.android.gms.phenotype;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.zzdl;
import com.google.android.gms.internal.zzdyr;
import com.google.android.gms.internal.zzeac;
import com.google.android.gms.phenotype.PhenotypeClient;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: PhenotypeClient */
final class zzah extends zzdl<zzeac, Void> {
    private final /* synthetic */ String zza;
    private final /* synthetic */ String zzb;

    zzah(PhenotypeClient phenotypeClient, String str, String str2) {
        this.zza = str;
        this.zzb = str2;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb2, TaskCompletionSource taskCompletionSource) throws RemoteException {
        PhenotypeClient.zza zza2 = new PhenotypeClient.zza(taskCompletionSource, null);
        String str = this.zza;
        String str2 = this.zzb;
        StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 9 + String.valueOf(str).length());
        sb.append("CURRENT:");
        sb.append(str2);
        sb.append(":");
        sb.append(str);
        ((zzdyr) ((zzeac) zzb2).zzag()).zzb(zza2, sb.toString());
    }
}
