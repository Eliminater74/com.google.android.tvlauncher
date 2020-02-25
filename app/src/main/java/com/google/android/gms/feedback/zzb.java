package com.google.android.gms.feedback;

import android.content.Context;
import android.os.Looper;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.internal.zzckq;

/* compiled from: Feedback */
final class zzb extends Api.zza<zzckq, Api.ApiOptions.NoOptions> {
    zzb() {
    }

    public final /* synthetic */ Api.Client zza(Context context, Looper looper, ClientSettings clientSettings, Object obj, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return new zzckq(context, looper, connectionCallbacks, onConnectionFailedListener, clientSettings);
    }
}
