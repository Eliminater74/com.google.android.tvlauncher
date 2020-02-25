package com.google.android.gms.internal;

import android.os.RemoteException;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

/* compiled from: PhenotypeApiImpl */
final class zzdze extends zzdyt.zzb<Status> {
    private final /* synthetic */ String zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ String zzd;
    private final /* synthetic */ int zze;
    private final /* synthetic */ int zzf;
    private final /* synthetic */ String zzg;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzdze(zzdyt zzdyt, GoogleApiClient googleApiClient, String str, String str2, String str3, int i, int i2, String str4) {
        super(googleApiClient);
        this.zza = str;
        this.zzb = str2;
        this.zzd = str3;
        this.zze = i;
        this.zzf = i2;
        this.zzg = str4;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb2) throws RemoteException {
        ((zzdyr) ((zzeac) zzb2).zzag()).zza(new zzdzf(this), this.zza, this.zzb, this.zzd, this.zze, this.zzf, this.zzg);
    }

    public final /* synthetic */ Result zza(Status status) {
        return status;
    }
}
