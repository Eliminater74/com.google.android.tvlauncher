package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzdyt;

/* compiled from: PhenotypeApiImpl */
final class zzdyu extends zzdyt.zzb<Status> {
    private final /* synthetic */ String zza;
    private final /* synthetic */ int zzb;
    private final /* synthetic */ String[] zzd;
    private final /* synthetic */ byte[] zze;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzdyu(zzdyt zzdyt, GoogleApiClient googleApiClient, String str, int i, String[] strArr, byte[] bArr) {
        super(googleApiClient);
        this.zza = str;
        this.zzb = i;
        this.zzd = strArr;
        this.zze = bArr;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb2) throws RemoteException {
        ((zzdyr) ((zzeac) zzb2).zzag()).zza(new zzdyv(this), this.zza, this.zzb, this.zzd, this.zze);
    }

    public final /* synthetic */ Result zza(Status status) {
        return status;
    }
}
