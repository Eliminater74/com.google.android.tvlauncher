package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.BaseGmsClient;

/* compiled from: GmsClient */
final class zzm implements BaseGmsClient.BaseConnectionCallbacks {
    private final /* synthetic */ GoogleApiClient.ConnectionCallbacks zza;

    zzm(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zza = connectionCallbacks;
    }

    public final void onConnected(@Nullable Bundle bundle) {
        this.zza.onConnected(bundle);
    }

    public final void onConnectionSuspended(int i) {
        this.zza.onConnectionSuspended(i);
    }
}
