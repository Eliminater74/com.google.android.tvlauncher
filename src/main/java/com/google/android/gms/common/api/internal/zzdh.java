package com.google.android.gms.common.api.internal;

import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

/* compiled from: StatusPendingResult */
public final class zzdh extends BasePendingResult<Status> {
    public zzdh(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    @Deprecated
    public zzdh(Looper looper) {
        super(looper);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zza(Status status) {
        return status;
    }
}
