package com.google.android.libraries.gcoreclient.common.api.support;

import android.os.Bundle;
import android.support.p001v4.util.ArrayMap;

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

import java.util.Map;

import javax.annotation.concurrent.GuardedBy;

abstract class BaseGcoreWrapper {
    @GuardedBy("mapLock")
    private final Map<GcoreGoogleApiClient.GcoreConnectionCallbacks, GoogleApiClient.ConnectionCallbacks> connectionCallbacksMap = new ArrayMap();
    @GuardedBy("mapLock")
    private final Map<GcoreGoogleApiClient.GcoreOnConnectionFailedListener, GoogleApiClient.OnConnectionFailedListener> failedListenerMap = new ArrayMap();
    private final Object mapLock = new Object();

    BaseGcoreWrapper() {
    }

    public <O extends GcoreApi.GcoreApiOptions> Api unwrap(GcoreApi gcoreApi) {
        if (gcoreApi instanceof BaseGcoreApi) {
            return ((BaseGcoreApi) gcoreApi).getApi();
        }
        return null;
    }

    public <O extends GcoreApi.GcoreApiOptions.GcoreHasOptions> Api.ApiOptions.HasOptions unwrap(GcoreApi.GcoreApiOptions.GcoreHasOptions gcoreHasOptions) {
        if (gcoreHasOptions instanceof BaseGcoreApi.BaseGcoreApiOptions.BaseGcoreHasOptions) {
            return ((BaseGcoreApi.BaseGcoreApiOptions.BaseGcoreHasOptions) gcoreHasOptions).getApiOptions();
        }
        return null;
    }

    public <O extends GcoreApi.GcoreApiOptions.GcoreNotRequiredOptions> Api.ApiOptions.NotRequiredOptions unwrap(GcoreApi.GcoreApiOptions.GcoreNotRequiredOptions gcoreNotRequiredOptions) {
        if (gcoreNotRequiredOptions instanceof BaseGcoreApi.BaseGcoreApiOptions.BaseGcoreNotRequiredOptions) {
            return ((BaseGcoreApi.BaseGcoreApiOptions.BaseGcoreNotRequiredOptions) gcoreNotRequiredOptions).getApiOptions();
        }
        return null;
    }

    public <O extends GcoreApi.GcoreApiOptions.GcoreOptional> Api.ApiOptions.Optional unwrap(GcoreApi.GcoreApiOptions.GcoreOptional gcoreOptional) {
        if (gcoreOptional instanceof BaseGcoreApi.BaseGcoreApiOptions.BaseGcoreOptional) {
            return ((BaseGcoreApi.BaseGcoreApiOptions.BaseGcoreOptional) gcoreOptional).getApiOptions();
        }
        return null;
    }

    public GoogleApiClient.ConnectionCallbacks unwrap(final GcoreGoogleApiClient.GcoreConnectionCallbacks listener) {
        synchronized (this.mapLock) {
            if (this.connectionCallbacksMap.containsKey(listener)) {
                GoogleApiClient.ConnectionCallbacks connectionCallbacks = this.connectionCallbacksMap.get(listener);
                return connectionCallbacks;
            }
            GoogleApiClient.ConnectionCallbacks connectionCallbacks2 = new GoogleApiClient.ConnectionCallbacks(this) {
                public void onConnected(Bundle connectionHint) {
                    listener.onConnected(connectionHint);
                }

                public void onConnectionSuspended(int cause) {
                    listener.onConnectionSuspended(cause);
                }
            };
            this.connectionCallbacksMap.put(listener, connectionCallbacks2);
            return connectionCallbacks2;
        }
    }

    public GoogleApiClient.OnConnectionFailedListener unwrap(final GcoreGoogleApiClient.GcoreOnConnectionFailedListener listener) {
        synchronized (this.mapLock) {
            if (this.failedListenerMap.containsKey(listener)) {
                GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = this.failedListenerMap.get(listener);
                return onConnectionFailedListener;
            }
            GoogleApiClient.OnConnectionFailedListener failedListener = new GoogleApiClient.OnConnectionFailedListener() {
                public void onConnectionFailed(ConnectionResult result) {
                    listener.onConnectionFailed(BaseGcoreWrapper.this.wrap(result));
                }
            };
            this.failedListenerMap.put(listener, failedListener);
            return failedListener;
        }
    }

    public GoogleApiClient unwrap(GcoreGoogleApiClient client) {
        if (client instanceof GoogleApiClientWrapper) {
            return ((GoogleApiClientWrapper) client).unwrap();
        }
        return null;
    }

    public Scope unwrap(GcoreScope scope) {
        if (scope instanceof GcoreScopeImpl) {
            return new Scope(((GcoreScopeImpl) scope).getScopeUri());
        }
        return null;
    }

    public GcoreConnectionResult wrap(ConnectionResult result) {
        return new BaseGcoreConnectionResult(result);
    }

    public GcorePendingResult<GcoreStatus> wrap(PendingResult<Status> result) {
        return new GcorePendingResultImpl(result, GcoreStatusImpl.STATUS_RESULT_WRAPPER);
    }

    public void removeConnectionCallbacks(GcoreGoogleApiClient.GcoreConnectionCallbacks listener) {
        synchronized (this.mapLock) {
            this.connectionCallbacksMap.remove(listener);
        }
    }

    public void removeOnConnectionFailedListener(GcoreGoogleApiClient.GcoreOnConnectionFailedListener listener) {
        synchronized (this.mapLock) {
            this.failedListenerMap.remove(listener);
        }
    }

    public Status unwrap(GcoreStatus status) {
        if (status instanceof GcoreStatusImpl) {
            return ((GcoreStatusImpl) status).unwrap();
        }
        return null;
    }

    public Result unwrap(GcoreResult result) {
        if (result instanceof GcoreResultImpl) {
            return ((GcoreResultImpl) result).unwrap();
        }
        return null;
    }
}
