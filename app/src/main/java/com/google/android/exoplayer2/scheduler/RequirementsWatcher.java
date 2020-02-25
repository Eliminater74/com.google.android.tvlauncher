package com.google.android.exoplayer2.scheduler;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Handler;
import android.support.annotation.RequiresApi;

import com.google.android.exoplayer2.util.Util;

public final class RequirementsWatcher {
    private static final String TAG = "RequirementsWatcher";
    /* access modifiers changed from: private */
    public final Handler handler = new Handler(Util.getLooper());
    private final Context context;
    private final Listener listener;
    private final Requirements requirements;
    /* access modifiers changed from: private */
    public CapabilityValidatedCallback networkCallback;
    private int notMetRequirements;
    private DeviceStatusChangeReceiver receiver;

    public RequirementsWatcher(Context context2, Listener listener2, Requirements requirements2) {
        this.context = context2.getApplicationContext();
        this.listener = listener2;
        this.requirements = requirements2;
        String valueOf = String.valueOf(this);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 8);
        sb.append(valueOf);
        sb.append(" created");
        logd(sb.toString());
    }

    /* access modifiers changed from: private */
    public static void logd(String message) {
    }

    public int start() {
        this.notMetRequirements = this.requirements.getNotMetRequirements(this.context);
        IntentFilter filter = new IntentFilter();
        if (this.requirements.isNetworkRequired()) {
            if (Util.SDK_INT >= 23) {
                registerNetworkCallbackV23();
            } else {
                filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            }
        }
        if (this.requirements.isChargingRequired()) {
            filter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
            filter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        }
        if (this.requirements.isIdleRequired()) {
            if (Util.SDK_INT >= 23) {
                filter.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
            } else {
                filter.addAction("android.intent.action.SCREEN_ON");
                filter.addAction("android.intent.action.SCREEN_OFF");
            }
        }
        this.receiver = new DeviceStatusChangeReceiver();
        this.context.registerReceiver(this.receiver, filter, null, this.handler);
        String valueOf = String.valueOf(this);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 8);
        sb.append(valueOf);
        sb.append(" started");
        logd(sb.toString());
        return this.notMetRequirements;
    }

    public void stop() {
        this.context.unregisterReceiver(this.receiver);
        this.receiver = null;
        if (this.networkCallback != null) {
            unregisterNetworkCallback();
        }
        String valueOf = String.valueOf(this);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 8);
        sb.append(valueOf);
        sb.append(" stopped");
        logd(sb.toString());
    }

    public Requirements getRequirements() {
        return this.requirements;
    }

    public String toString() {
        return super.toString();
    }

    @TargetApi(23)
    private void registerNetworkCallbackV23() {
        NetworkRequest request = new NetworkRequest.Builder().addCapability(16).build();
        this.networkCallback = new CapabilityValidatedCallback();
        ((ConnectivityManager) this.context.getSystemService("connectivity")).registerNetworkCallback(request, this.networkCallback);
    }

    private void unregisterNetworkCallback() {
        if (Util.SDK_INT >= 21) {
            ((ConnectivityManager) this.context.getSystemService("connectivity")).unregisterNetworkCallback(this.networkCallback);
            this.networkCallback = null;
        }
    }

    /* access modifiers changed from: private */
    public void checkRequirements() {
        int notMetRequirements2 = this.requirements.getNotMetRequirements(this.context);
        if (this.notMetRequirements != notMetRequirements2) {
            this.notMetRequirements = notMetRequirements2;
            StringBuilder sb = new StringBuilder(43);
            sb.append("notMetRequirements has changed: ");
            sb.append(notMetRequirements2);
            logd(sb.toString());
            this.listener.onRequirementsStateChanged(this, notMetRequirements2);
        }
    }

    public interface Listener {
        void onRequirementsStateChanged(RequirementsWatcher requirementsWatcher, int i);
    }

    private class DeviceStatusChangeReceiver extends BroadcastReceiver {
        private DeviceStatusChangeReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (!isInitialStickyBroadcast()) {
                String valueOf = String.valueOf(RequirementsWatcher.this);
                String action = intent.getAction();
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 10 + String.valueOf(action).length());
                sb.append(valueOf);
                sb.append(" received ");
                sb.append(action);
                RequirementsWatcher.logd(sb.toString());
                RequirementsWatcher.this.checkRequirements();
            }
        }
    }

    @RequiresApi(api = 21)
    private final class CapabilityValidatedCallback extends ConnectivityManager.NetworkCallback {
        private CapabilityValidatedCallback() {
        }

        public void onAvailable(Network network) {
            onNetworkCallback();
        }

        public void onLost(Network network) {
            onNetworkCallback();
        }

        private void onNetworkCallback() {
            RequirementsWatcher.this.handler.post(new RequirementsWatcher$CapabilityValidatedCallback$$Lambda$0(this));
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$onNetworkCallback$0$RequirementsWatcher$CapabilityValidatedCallback */
        public final /* synthetic */ void mo14270x8a9259a2() {
            if (RequirementsWatcher.this.networkCallback != null) {
                String valueOf = String.valueOf(RequirementsWatcher.this);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 16);
                sb.append(valueOf);
                sb.append(" NetworkCallback");
                RequirementsWatcher.logd(sb.toString());
                RequirementsWatcher.this.checkRequirements();
            }
        }
    }
}
