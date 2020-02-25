package com.google.android.gms.clearcut;

import android.content.Context;
import android.os.Looper;

import com.google.android.gms.clearcut.internal.zzi;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;

/* compiled from: ClearcutLogger */
final class zzd extends Api.zza<zzi, Api.ApiOptions.NoOptions> {
    zzd() {
    }

    public final /* synthetic */ Api.Client zza(Context context, Looper looper, ClientSettings clientSettings, Object obj, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return new zzi(context, looper, clientSettings, connectionCallbacks, onConnectionFailedListener);
    }
}
