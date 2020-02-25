package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import android.support.p001v4.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/* compiled from: DummyGoogleApiClient */
public class zzal extends GoogleApiClient {
    private final String zza;

    public zzal(String str) {
        this.zza = str;
    }

    public boolean hasConnectedApi(@NonNull Api<?> api) {
        throw new UnsupportedOperationException(this.zza);
    }

    @NonNull
    public ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        throw new UnsupportedOperationException(this.zza);
    }

    public void connect() {
        throw new UnsupportedOperationException(this.zza);
    }

    public ConnectionResult blockingConnect() {
        throw new UnsupportedOperationException(this.zza);
    }

    public ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) {
        throw new UnsupportedOperationException(this.zza);
    }

    public void disconnect() {
        throw new UnsupportedOperationException(this.zza);
    }

    public void reconnect() {
        throw new UnsupportedOperationException(this.zza);
    }

    public PendingResult<Status> clearDefaultAccountAndReconnect() {
        throw new UnsupportedOperationException(this.zza);
    }

    public void stopAutoManage(@NonNull FragmentActivity fragmentActivity) {
        throw new UnsupportedOperationException(this.zza);
    }

    public boolean isConnected() {
        throw new UnsupportedOperationException(this.zza);
    }

    public boolean isConnecting() {
        throw new UnsupportedOperationException(this.zza);
    }

    public void registerConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        throw new UnsupportedOperationException(this.zza);
    }

    public boolean isConnectionCallbacksRegistered(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        throw new UnsupportedOperationException(this.zza);
    }

    public void unregisterConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        throw new UnsupportedOperationException(this.zza);
    }

    public void registerConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        throw new UnsupportedOperationException(this.zza);
    }

    public boolean isConnectionFailedListenerRegistered(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        throw new UnsupportedOperationException(this.zza);
    }

    public void unregisterConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        throw new UnsupportedOperationException(this.zza);
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        throw new UnsupportedOperationException(this.zza);
    }
}
