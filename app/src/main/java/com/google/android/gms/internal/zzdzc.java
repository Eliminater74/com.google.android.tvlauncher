package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzdyt;
import com.google.android.gms.phenotype.PhenotypeApi;

/* compiled from: PhenotypeApiImpl */
final class zzdzc extends zzdyt.zzb<PhenotypeApi.ConfigurationsResult> {
    private final /* synthetic */ String zza;
    private final /* synthetic */ int zzb;
    private final /* synthetic */ String[] zzd;
    private final /* synthetic */ byte[] zze;
    private final /* synthetic */ String zzf;
    private final /* synthetic */ String zzg;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzdzc(zzdyt zzdyt, GoogleApiClient googleApiClient, String str, int i, String[] strArr, byte[] bArr, String str2, String str3) {
        super(googleApiClient);
        this.zza = str;
        this.zzb = i;
        this.zzd = strArr;
        this.zze = bArr;
        this.zzf = str2;
        this.zzg = str3;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb2) throws RemoteException {
        ((zzdyr) ((zzeac) zzb2).zzag()).zza(new zzdzd(this), this.zza, this.zzb, this.zzd, this.zze, this.zzf, this.zzg);
    }

    public final /* synthetic */ Result zza(Status status) {
        return new zzdyt.zzc(status, null);
    }
}
