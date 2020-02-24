package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

/* compiled from: CommonApiImpl */
final class zzblg extends zzblj {
    zzblg(zzblf zzblf, GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbln) ((zzblk) zzb).zzag()).zza(new zzblh(this));
    }
}
