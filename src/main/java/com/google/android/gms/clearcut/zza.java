package com.google.android.gms.clearcut;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;

/* compiled from: BootCountClient */
final class zza extends Api.zza<com.google.android.gms.clearcut.internal.zza, Api.ApiOptions.NoOptions> {
    zza() {
    }

    public final /* synthetic */ Api.Client zza(Context context, Looper looper, ClientSettings clientSettings, Object obj, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return new com.google.android.gms.clearcut.internal.zza(context, looper, clientSettings, connectionCallbacks, onConnectionFailedListener);
    }
}
