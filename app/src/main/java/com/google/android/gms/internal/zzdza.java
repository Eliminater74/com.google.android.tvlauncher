package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzdyt;
import com.google.android.gms.phenotype.ServingVersion;

/* compiled from: PhenotypeApiImpl */
final class zzdza extends zzdyt.zzb<Status> {
    final /* synthetic */ ServingVersion zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzdza(zzdyt zzdyt, GoogleApiClient googleApiClient, ServingVersion servingVersion) {
        super(googleApiClient);
        this.zza = servingVersion;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzdyr) ((zzeac) zzb).zzag()).zza(new zzdzb(this), this.zza.getServingVersion());
    }

    public final /* synthetic */ Result zza(Status status) {
        return status;
    }
}
