package com.google.android.gms.internal;

import android.os.RemoteException;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.usagereporting.UsageReporting;
import com.google.android.gms.usagereporting.UsageReportingOptInOptions;

/* compiled from: UsageReportingApiImpl */
final class zzffe extends UsageReporting.zza<Status> {
    private final /* synthetic */ UsageReportingOptInOptions zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzffe(zzffc zzffc, GoogleApiClient googleApiClient, UsageReportingOptInOptions usageReportingOptInOptions) {
        super(googleApiClient);
        this.zza = usageReportingOptInOptions;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzfez) ((zzffg) zzb).zzag()).zza(this.zza, new zzffl(this));
    }

    public final /* synthetic */ Result zza(Status status) {
        return status;
    }
}
