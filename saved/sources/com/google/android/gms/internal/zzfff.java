package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzcj;
import com.google.android.gms.usagereporting.UsageReporting;

/* compiled from: UsageReportingApiImpl */
final class zzfff extends UsageReporting.zza<Status> {
    private final /* synthetic */ zzcj zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzfff(zzffc zzffc, GoogleApiClient googleApiClient, zzcj zzcj) {
        super(googleApiClient);
        this.zza = zzcj;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzffg) zzb).zza(this.zza, this);
    }

    public final /* synthetic */ Result zza(Status status) {
        return status;
    }
}
