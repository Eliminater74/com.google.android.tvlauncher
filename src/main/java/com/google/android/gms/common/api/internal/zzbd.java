package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: GoogleApiClientImpl */
final class zzbd implements GoogleApiClient.ConnectionCallbacks {
    private final /* synthetic */ AtomicReference zza;
    private final /* synthetic */ zzdh zzb;
    private final /* synthetic */ zzbb zzc;

    zzbd(zzbb zzbb, AtomicReference atomicReference, zzdh zzdh) {
        this.zzc = zzbb;
        this.zza = atomicReference;
        this.zzb = zzdh;
    }

    public final void onConnected(Bundle bundle) {
        this.zzc.zza((GoogleApiClient) this.zza.get(), this.zzb, true);
    }

    public final void onConnectionSuspended(int i) {
    }
}
