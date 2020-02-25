package com.bumptech.glide.manager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.util.Preconditions;

final class DefaultConnectivityMonitor implements ConnectivityMonitor {
    private static final String TAG = "ConnectivityMonitor";
    final ConnectivityMonitor.ConnectivityListener listener;
    private final Context context;
    boolean isConnected;
    private final BroadcastReceiver connectivityReceiver = new BroadcastReceiver() {
        public void onReceive(@NonNull Context context, Intent intent) {
            boolean wasConnected = DefaultConnectivityMonitor.this.isConnected;
            DefaultConnectivityMonitor defaultConnectivityMonitor = DefaultConnectivityMonitor.this;
            defaultConnectivityMonitor.isConnected = defaultConnectivityMonitor.isConnected(context);
            if (wasConnected != DefaultConnectivityMonitor.this.isConnected) {
                if (Log.isLoggable(DefaultConnectivityMonitor.TAG, 3)) {
                    boolean z = DefaultConnectivityMonitor.this.isConnected;
                    StringBuilder sb = new StringBuilder(40);
                    sb.append("connectivity changed, isConnected: ");
                    sb.append(z);
                    Log.d(DefaultConnectivityMonitor.TAG, sb.toString());
                }
                DefaultConnectivityMonitor.this.listener.onConnectivityChanged(DefaultConnectivityMonitor.this.isConnected);
            }
        }
    };
    private boolean isRegistered;

    DefaultConnectivityMonitor(@NonNull Context context2, @NonNull ConnectivityMonitor.ConnectivityListener listener2) {
        this.context = context2.getApplicationContext();
        this.listener = listener2;
    }

    private void register() {
        if (!this.isRegistered) {
            this.isConnected = isConnected(this.context);
            try {
                this.context.registerReceiver(this.connectivityReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                this.isRegistered = true;
            } catch (SecurityException e) {
                if (Log.isLoggable(TAG, 5)) {
                    Log.w(TAG, "Failed to register", e);
                }
            }
        }
    }

    private void unregister() {
        if (this.isRegistered) {
            this.context.unregisterReceiver(this.connectivityReceiver);
            this.isRegistered = false;
        }
    }

    /* access modifiers changed from: package-private */
    @SuppressLint({"MissingPermission"})
    public boolean isConnected(@NonNull Context context2) {
        try {
            NetworkInfo networkInfo = ((ConnectivityManager) Preconditions.checkNotNull((ConnectivityManager) context2.getSystemService("connectivity"))).getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected()) {
                return false;
            }
            return true;
        } catch (RuntimeException e) {
            if (Log.isLoggable(TAG, 5)) {
                Log.w(TAG, "Failed to determine connectivity status when connectivity changed", e);
            }
            return true;
        }
    }

    public void onStart() {
        register();
    }

    public void onStop() {
        unregister();
    }

    public void onDestroy() {
    }
}
