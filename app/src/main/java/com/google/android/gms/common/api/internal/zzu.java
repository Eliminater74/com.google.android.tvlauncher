package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzau;

/* compiled from: ClientCallbacks */
public final class zzu implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public final Api<?> zza;
    private final boolean zzb;
    private zzv zzc;

    public zzu(Api<?> api, boolean z) {
        this.zza = api;
        this.zzb = z;
    }

    public final void onConnected(@Nullable Bundle bundle) {
        zza();
        this.zzc.onConnected(bundle);
    }

    public final void onConnectionSuspended(int i) {
        zza();
        this.zzc.onConnectionSuspended(i);
    }

    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        zza();
        this.zzc.zza(connectionResult, this.zza, this.zzb);
    }

    public final void zza(zzv zzv) {
        this.zzc = zzv;
    }

    private final void zza() {
        zzau.zza(this.zzc, "Callbacks must be attached to a ClientConnectionHelper instance before connecting the client.");
    }
}
