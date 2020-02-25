package com.google.android.gms.clearcut.internal;

import android.os.RemoteException;

import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.Hide;

/* compiled from: ClearcutLoggerApiImpl */
final class zze extends zzn<Status, zzi> {
    zze(GoogleApiClient googleApiClient) {
        super(ClearcutLogger.API, googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzq) ((zzi) zzb).zzag()).zza(new zzf(this));
    }

    @Hide
    public final /* bridge */ /* synthetic */ void zza(Object obj) {
        super.zza((Result) ((Status) obj));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zza(Status status) {
        return status;
    }
}
