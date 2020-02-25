package com.google.android.gms.internal;

import android.os.RemoteException;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.phenotype.PhenotypeApi;

/* compiled from: PhenotypeApiImpl */
final class zzdzu extends zzdyt.zzb<PhenotypeApi.ExperimentTokensResult> {
    private final /* synthetic */ String zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzdzu(zzdyt zzdyt, GoogleApiClient googleApiClient, String str) {
        super(googleApiClient);
        this.zza = str;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzdyr) ((zzeac) zzb).zzag()).zza(new zzdzv(this), (String) null, this.zza);
    }

    public final /* synthetic */ Result zza(Status status) {
        return new zzdyt.zze(status, null);
    }
}
