package com.google.android.libraries.gcoreclient.common.api.support;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.gcoreclient.common.GcoreConnectionResult;
import com.google.android.libraries.gcoreclient.common.api.GcoreApi;
import com.google.android.libraries.gcoreclient.common.api.GcoreGoogleApiClient;
import com.google.android.libraries.gcoreclient.common.api.GcorePendingResult;
import com.google.android.libraries.gcoreclient.common.api.GcoreResult;
import com.google.android.libraries.gcoreclient.common.api.GcoreScope;
import com.google.android.libraries.gcoreclient.common.api.GcoreStatus;

public class GcoreWrapper extends BaseGcoreWrapper {
    public /* bridge */ /* synthetic */ void removeConnectionCallbacks(GcoreGoogleApiClient.GcoreConnectionCallbacks gcoreConnectionCallbacks) {
        super.removeConnectionCallbacks(gcoreConnectionCallbacks);
    }

    public /* bridge */ /* synthetic */ void removeOnConnectionFailedListener(GcoreGoogleApiClient.GcoreOnConnectionFailedListener gcoreOnConnectionFailedListener) {
        super.removeOnConnectionFailedListener(gcoreOnConnectionFailedListener);
    }

    public /* bridge */ /* synthetic */ Api.ApiOptions.HasOptions unwrap(GcoreApi.GcoreApiOptions.GcoreHasOptions gcoreHasOptions) {
        return super.unwrap(gcoreHasOptions);
    }

    public /* bridge */ /* synthetic */ Api.ApiOptions.NotRequiredOptions unwrap(GcoreApi.GcoreApiOptions.GcoreNotRequiredOptions gcoreNotRequiredOptions) {
        return super.unwrap(gcoreNotRequiredOptions);
    }

    public /* bridge */ /* synthetic */ Api.ApiOptions.Optional unwrap(GcoreApi.GcoreApiOptions.GcoreOptional gcoreOptional) {
        return super.unwrap(gcoreOptional);
    }

    public /* bridge */ /* synthetic */ Api unwrap(GcoreApi gcoreApi) {
        return super.unwrap(gcoreApi);
    }

    public /* bridge */ /* synthetic */ GoogleApiClient.ConnectionCallbacks unwrap(GcoreGoogleApiClient.GcoreConnectionCallbacks gcoreConnectionCallbacks) {
        return super.unwrap(gcoreConnectionCallbacks);
    }

    public /* bridge */ /* synthetic */ GoogleApiClient.OnConnectionFailedListener unwrap(GcoreGoogleApiClient.GcoreOnConnectionFailedListener gcoreOnConnectionFailedListener) {
        return super.unwrap(gcoreOnConnectionFailedListener);
    }

    public /* bridge */ /* synthetic */ GoogleApiClient unwrap(GcoreGoogleApiClient gcoreGoogleApiClient) {
        return super.unwrap(gcoreGoogleApiClient);
    }

    public /* bridge */ /* synthetic */ Result unwrap(GcoreResult gcoreResult) {
        return super.unwrap(gcoreResult);
    }

    public /* bridge */ /* synthetic */ Scope unwrap(GcoreScope gcoreScope) {
        return super.unwrap(gcoreScope);
    }

    public /* bridge */ /* synthetic */ Status unwrap(GcoreStatus gcoreStatus) {
        return super.unwrap(gcoreStatus);
    }

    public /* bridge */ /* synthetic */ GcoreConnectionResult wrap(ConnectionResult connectionResult) {
        return super.wrap(connectionResult);
    }

    public /* bridge */ /* synthetic */ GcorePendingResult wrap(PendingResult pendingResult) {
        return super.wrap(pendingResult);
    }
}
