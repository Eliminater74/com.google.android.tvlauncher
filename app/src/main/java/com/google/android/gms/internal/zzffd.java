package com.google.android.gms.internal;

import android.os.RemoteException;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.usagereporting.UsageReporting;
import com.google.android.gms.usagereporting.UsageReportingApi;

/* compiled from: UsageReportingApiImpl */
final class zzffd extends UsageReporting.zza<UsageReportingApi.OptInOptionsResult> {
    zzffd(zzffc zzffc, GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzfez) ((zzffg) zzb).zzag()).zza(new zzffk(this));
    }

    public final /* synthetic */ Result zza(Status status) {
        return new zzffb(status, null);
    }
}
