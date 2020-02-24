package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzdyt;
import com.google.android.gms.phenotype.PhenotypeApi;

/* compiled from: PhenotypeApiImpl */
final class zzdzk extends zzdyt.zzb<PhenotypeApi.ConfigurationsResult> {
    private final /* synthetic */ String zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ String zzd;
    private final /* synthetic */ String zze;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzdzk(zzdyt zzdyt, GoogleApiClient googleApiClient, String str, String str2, String str3, String str4) {
        super(googleApiClient);
        this.zza = str;
        this.zzb = str2;
        this.zzd = str3;
        this.zze = str4;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb2) throws RemoteException {
        ((zzdyr) ((zzeac) zzb2).zzag()).zza(new zzdzl(this), this.zza, this.zzb, this.zzd, this.zze);
    }

    public final /* synthetic */ Result zza(Status status) {
        return new zzdyt.zzc(status, null);
    }
}
