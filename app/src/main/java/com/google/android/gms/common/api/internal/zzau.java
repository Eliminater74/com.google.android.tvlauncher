package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.BaseGmsClient;

/* compiled from: GoogleApiClientConnecting */
final class zzau extends zzbk {
    private final /* synthetic */ BaseGmsClient.ConnectionProgressReportCallbacks zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzau(zzas zzas, zzbi zzbi, BaseGmsClient.ConnectionProgressReportCallbacks connectionProgressReportCallbacks) {
        super(zzbi);
        this.zza = connectionProgressReportCallbacks;
    }

    public final void zza() {
        this.zza.onReportServiceBinding(new ConnectionResult(16, null));
    }
}
