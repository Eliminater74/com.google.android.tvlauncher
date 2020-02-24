package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

/* compiled from: GoogleApiClientImpl */
final class zzbe implements GoogleApiClient.OnConnectionFailedListener {
    private final /* synthetic */ zzdh zza;

    zzbe(zzbb zzbb, zzdh zzdh) {
        this.zza = zzdh;
    }

    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zza.zza((Result) new Status(8));
    }
}
