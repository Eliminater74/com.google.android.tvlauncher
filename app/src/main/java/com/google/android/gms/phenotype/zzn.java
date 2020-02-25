package com.google.android.gms.phenotype;

import android.content.Context;
import android.os.Looper;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.internal.zzeac;

/* compiled from: Phenotype */
final class zzn extends Api.zza<zzeac, Api.ApiOptions.NoOptions> {
    zzn() {
    }

    public final /* synthetic */ Api.Client zza(Context context, Looper looper, ClientSettings clientSettings, Object obj, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return new zzeac(context, looper, clientSettings, connectionCallbacks, onConnectionFailedListener);
    }
}
