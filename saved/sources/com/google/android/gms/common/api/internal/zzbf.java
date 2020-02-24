package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.auth.api.signin.internal.zzaa;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/* compiled from: GoogleApiClientImpl */
final class zzbf implements ResultCallback<Status> {
    private final /* synthetic */ zzdh zza;
    private final /* synthetic */ boolean zzb;
    private final /* synthetic */ GoogleApiClient zzc;
    private final /* synthetic */ zzbb zzd;

    zzbf(zzbb zzbb, zzdh zzdh, boolean z, GoogleApiClient googleApiClient) {
        this.zzd = zzbb;
        this.zza = zzdh;
        this.zzb = z;
        this.zzc = googleApiClient;
    }

    public final /* synthetic */ void onResult(@NonNull Result result) {
        Status status = (Status) result;
        zzaa.zza(this.zzd.zzk).zzc();
        if (status.isSuccess() && this.zzd.isConnected()) {
            this.zzd.reconnect();
        }
        this.zza.zza((Result) status);
        if (this.zzb) {
            this.zzc.disconnect();
        }
    }
}
