package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzdyt;
import com.google.android.gms.phenotype.PhenotypeApi;

/* compiled from: PhenotypeApiImpl */
final class zzdzy extends zzdyt.zzb<PhenotypeApi.DogfoodsTokenResult> {
    zzdzy(zzdyt zzdyt, GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzdyr) ((zzeac) zzb).zzag()).zza(new zzdzz(this));
    }

    public final /* synthetic */ Result zza(Status status) {
        return new zzdyt.zzd(status, null);
    }
}
