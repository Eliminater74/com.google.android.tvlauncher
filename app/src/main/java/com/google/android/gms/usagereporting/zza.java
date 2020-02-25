package com.google.android.gms.usagereporting;

import android.content.Context;
import android.os.Looper;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.internal.zzffg;

/* compiled from: UsageReporting */
final class zza extends Api.zza<zzffg, Api.ApiOptions.NoOptions> {
    zza() {
    }

    public final /* synthetic */ Api.Client zza(Context context, Looper looper, ClientSettings clientSettings, Object obj, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return new zzffg(context, looper, clientSettings, connectionCallbacks, onConnectionFailedListener);
    }
}
