package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/* compiled from: GoogleApiClientConnecting */
final class zzay implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private final /* synthetic */ zzap zza;

    private zzay(zzap zzap) {
        this.zza = zzap;
    }

    /* synthetic */ zzay(zzap zzap, zzaq zzaq) {
        this(zzap);
    }

    public final void onConnected(Bundle bundle) {
        this.zza.zzk.zza(new zzaw(this.zza));
    }

    public final void onConnectionSuspended(int i) {
    }

    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zza.zzb.lock();
        try {
            if (this.zza.zza(connectionResult)) {
                this.zza.zzg();
                this.zza.zze();
            } else {
                this.zza.zzb(connectionResult);
            }
        } finally {
            this.zza.zzb.unlock();
        }
    }
}
