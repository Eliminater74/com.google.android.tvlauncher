package com.google.android.libraries.gcoreclient.common.api.impl;

import android.accounts.Account;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.libraries.gcoreclient.common.GcoreConnectionResult;
import com.google.android.libraries.gcoreclient.common.api.GcoreApi;
import com.google.android.libraries.gcoreclient.common.api.GcoreGoogleApiClient;
import com.google.android.libraries.gcoreclient.common.api.GcoreScope;
import com.google.android.libraries.gcoreclient.common.api.support.GcoreWrapper;
import com.google.android.libraries.gcoreclient.common.api.support.GoogleApiClientWrapper;
import java.util.concurrent.TimeUnit;

public abstract class BaseGcoreGoogleApiClientImpl implements GcoreGoogleApiClient, GoogleApiClientWrapper {
    GoogleApiClient client;
    final Context context;
    GcoreWrapper wrapper;

    public static abstract class Builder implements GcoreGoogleApiClient.Builder {
        GoogleApiClient.Builder builder;
        final Context context;
        GcoreWrapper wrapper;

        public Builder(Context context2) {
            this(context2, new GcoreWrapper());
        }

        public Builder(Context context2, GcoreWrapper wrapper2) {
            this.context = context2;
            this.builder = new GoogleApiClient.Builder(context2);
            this.wrapper = wrapper2;
        }

        public GcoreGoogleApiClient.Builder addApi(GcoreApi<? extends GcoreApi.GcoreApiOptions.GcoreHasOptions> api, GcoreApi.GcoreApiOptions.GcoreHasOptions options) {
            this.builder.addApi(this.wrapper.unwrap(api), this.wrapper.unwrap(options));
            return this;
        }

        public GcoreGoogleApiClient.Builder addApi(GcoreApi<? extends GcoreApi.GcoreApiOptions.GcoreNotRequiredOptions> api) {
            this.builder.addApi(this.wrapper.unwrap(api));
            return this;
        }

        public GcoreGoogleApiClient.Builder addConnectionCallbacks(GcoreGoogleApiClient.GcoreConnectionCallbacks listener) {
            this.builder.addConnectionCallbacks(this.wrapper.unwrap(listener));
            return this;
        }

        public GcoreGoogleApiClient.Builder addOnConnectionFailedListener(GcoreGoogleApiClient.GcoreOnConnectionFailedListener listener) {
            this.builder.addOnConnectionFailedListener(this.wrapper.unwrap(listener));
            return this;
        }

        public GcoreGoogleApiClient.Builder addScope(GcoreScope scope) {
            this.builder.addScope(this.wrapper.unwrap(scope));
            return this;
        }

        public GcoreGoogleApiClient.Builder setAccount(Account account) {
            throw new UnsupportedOperationException("setAccount is not supported till orla.");
        }

        public GcoreGoogleApiClient.Builder setAccountName(String accountName) {
            this.builder.setAccountName(accountName);
            return this;
        }

        public GcoreGoogleApiClient.Builder setGravityForPopups(int gravityForPopups) {
            this.builder.setGravityForPopups(gravityForPopups);
            return this;
        }

        public GcoreGoogleApiClient.Builder setHandler(Handler handler) {
            this.builder.setHandler(handler);
            return this;
        }

        public GcoreGoogleApiClient.Builder setViewForPopups(View viewForPopups) {
            this.builder.setViewForPopups(viewForPopups);
            return this;
        }

        public GcoreGoogleApiClient.Builder useDefaultAccount() {
            this.builder.useDefaultAccount();
            return this;
        }
    }

    protected BaseGcoreGoogleApiClientImpl(Context context2, GoogleApiClient client2, GcoreWrapper wrapper2) {
        this.context = context2;
        this.client = client2;
        this.wrapper = wrapper2;
    }

    public GcoreConnectionResult blockingConnect() {
        return this.wrapper.wrap(this.client.blockingConnect());
    }

    public GcoreConnectionResult blockingConnect(long timeout, TimeUnit unit) {
        return this.wrapper.wrap(this.client.blockingConnect(timeout, unit));
    }

    public void connect() {
        this.client.connect();
    }

    public void disconnect() {
        this.client.disconnect();
    }

    public boolean isConnected() {
        return this.client.isConnected();
    }

    public boolean isConnecting() {
        return this.client.isConnecting();
    }

    public boolean isConnectionCallbacksRegistered(GcoreGoogleApiClient.GcoreConnectionCallbacks listener) {
        return this.client.isConnectionCallbacksRegistered(this.wrapper.unwrap(listener));
    }

    public boolean isConnectionFailedListenerRegistered(GcoreGoogleApiClient.GcoreOnConnectionFailedListener listener) {
        return this.client.isConnectionFailedListenerRegistered(this.wrapper.unwrap(listener));
    }

    public void reconnect() {
        this.client.reconnect();
    }

    public void registerConnectionCallbacks(GcoreGoogleApiClient.GcoreConnectionCallbacks listener) {
        this.client.registerConnectionCallbacks(this.wrapper.unwrap(listener));
    }

    public void registerConnectionFailedListener(GcoreGoogleApiClient.GcoreOnConnectionFailedListener listener) {
        this.client.registerConnectionFailedListener(this.wrapper.unwrap(listener));
    }

    public void unregisterConnectionCallbacks(GcoreGoogleApiClient.GcoreConnectionCallbacks listener) {
        this.client.unregisterConnectionCallbacks(this.wrapper.unwrap(listener));
        this.wrapper.removeConnectionCallbacks(listener);
    }

    public void unregisterConnectionFailedListener(GcoreGoogleApiClient.GcoreOnConnectionFailedListener listener) {
        this.client.unregisterConnectionFailedListener(this.wrapper.unwrap(listener));
        this.wrapper.removeOnConnectionFailedListener(listener);
    }

    public Context getContext() {
        return this.context;
    }

    public GoogleApiClient unwrap() {
        return this.client;
    }
}
