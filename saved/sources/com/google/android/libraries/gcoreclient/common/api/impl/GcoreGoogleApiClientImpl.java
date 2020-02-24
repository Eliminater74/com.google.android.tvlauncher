package com.google.android.libraries.gcoreclient.common.api.impl;

import android.accounts.Account;
import android.content.Context;
import android.support.p001v4.app.FragmentActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.libraries.gcoreclient.common.api.GcoreGoogleApiClient;
import com.google.android.libraries.gcoreclient.common.api.impl.BaseGcoreGoogleApiClientImpl;
import com.google.android.libraries.gcoreclient.common.api.support.GcoreWrapper;

public class GcoreGoogleApiClientImpl extends BaseGcoreGoogleApiClientImpl {

    public static class Builder extends BaseGcoreGoogleApiClientImpl.Builder {
        public Builder(Context context) {
            super(context);
        }

        public Builder(Context context, GcoreWrapper wrapper) {
            super(context, wrapper);
        }

        public GcoreGoogleApiClient build() {
            return new GcoreGoogleApiClientImpl(this.context, this.builder.build(), this.wrapper);
        }

        public GcoreGoogleApiClient.Builder enableAutoManage(FragmentActivity fragmentActivity, int clientId, GcoreGoogleApiClient.GcoreOnConnectionFailedListener listener) {
            this.builder.enableAutoManage(fragmentActivity, clientId, this.wrapper.unwrap(listener));
            return this;
        }

        public GcoreGoogleApiClient.Builder enableSignInClientDisconnectFix() {
            return this;
        }

        public GcoreGoogleApiClient.Builder setAccount(Account account) {
            this.builder.setAccount(account);
            return this;
        }
    }

    public static class BuilderFactory implements GcoreGoogleApiClient.BuilderFactory {
        public GcoreGoogleApiClient.Builder newBuilder(Context context) {
            return new Builder(context);
        }
    }

    public void stopAutoManage(FragmentActivity lifecycleActivity) {
        this.client.stopAutoManage(lifecycleActivity);
    }

    public GcoreGoogleApiClientImpl(Context context, GoogleApiClient client, GcoreWrapper wrapper) {
        super(context, client, wrapper);
    }
}
