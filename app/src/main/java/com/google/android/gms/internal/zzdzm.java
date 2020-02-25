package com.google.android.gms.internal;

import android.os.RemoteException;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

/* compiled from: PhenotypeApiImpl */
final class zzdzm extends zzdyt.zzb<Status> {
    private final /* synthetic */ String zza;
    private final /* synthetic */ int zzb;
    private final /* synthetic */ String[] zzd;
    private final /* synthetic */ int[] zze;
    private final /* synthetic */ byte[] zzf;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzdzm(zzdyt zzdyt, GoogleApiClient googleApiClient, String str, int i, String[] strArr, int[] iArr, byte[] bArr) {
        super(googleApiClient);
        this.zza = str;
        this.zzb = i;
        this.zzd = strArr;
        this.zze = iArr;
        this.zzf = bArr;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb2) throws RemoteException {
        ((zzdyr) ((zzeac) zzb2).zzag()).zza(new zzdzn(this), this.zza, this.zzb, this.zzd, this.zze, this.zzf);
    }

    public final /* synthetic */ Result zza(Status status) {
        return status;
    }
}
