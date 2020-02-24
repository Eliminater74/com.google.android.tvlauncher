package com.google.android.exoplayer2.scheduler;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.PowerManager;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Requirements {
    public static final int DEVICE_CHARGING = 8;
    public static final int DEVICE_IDLE = 4;
    public static final int NETWORK = 1;
    public static final int NETWORK_UNMETERED = 2;
    private static final String TAG = "Requirements";
    private final int requirements;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface RequirementFlags {
    }

    public Requirements(int requirements2) {
        this.requirements = (requirements2 & 2) != 0 ? requirements2 | 1 : requirements2;
    }

    public int getRequirements() {
        return this.requirements;
    }

    public boolean isNetworkRequired() {
        return (this.requirements & 1) != 0;
    }

    public boolean isUnmeteredNetworkRequired() {
        return (this.requirements & 2) != 0;
    }

    public boolean isChargingRequired() {
        return (this.requirements & 8) != 0;
    }

    public boolean isIdleRequired() {
        return (this.requirements & 4) != 0;
    }

    public boolean checkRequirements(Context context) {
        return getNotMetRequirements(context) == 0;
    }

    public int getNotMetRequirements(Context context) {
        int notMetRequirements = getNotMetNetworkRequirements(context);
        if (isChargingRequired() && !isDeviceCharging(context)) {
            notMetRequirements |= 8;
        }
        if (!isIdleRequired() || isDeviceIdle(context)) {
            return notMetRequirements;
        }
        return notMetRequirements | 4;
    }

    private int getNotMetNetworkRequirements(Context context) {
        if (!isNetworkRequired()) {
            return 0;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() || !isInternetConnectivityValidated(connectivityManager)) {
            logd("No network info, connection or connectivity.");
            return this.requirements & 3;
        } else if (!isUnmeteredNetworkRequired() || !connectivityManager.isActiveNetworkMetered()) {
            return 0;
        } else {
            return 2;
        }
    }

    private boolean isDeviceCharging(Context context) {
        Intent batteryStatus = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (batteryStatus == null) {
            return false;
        }
        int status = batteryStatus.getIntExtra("status", -1);
        if (status == 2 || status == 5) {
            return true;
        }
        return false;
    }

    private boolean isDeviceIdle(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        if (Util.SDK_INT >= 23) {
            return powerManager.isDeviceIdleMode();
        }
        if (Util.SDK_INT >= 20) {
            if (!powerManager.isInteractive()) {
                return true;
            }
        } else if (!powerManager.isScreenOn()) {
            return true;
        }
        return false;
    }

    private static boolean isInternetConnectivityValidated(ConnectivityManager connectivityManager) {
        if (Util.SDK_INT < 23) {
            return true;
        }
        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork == null) {
            logd("No active network.");
            return false;
        }
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
        boolean validated = networkCapabilities == null || !networkCapabilities.hasCapability(16);
        StringBuilder sb = new StringBuilder(35);
        sb.append("Network capability validated: ");
        sb.append(validated);
        logd(sb.toString());
        if (!validated) {
            return true;
        }
        return false;
    }

    private static void logd(String message) {
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && getClass() == o.getClass() && this.requirements == ((Requirements) o).requirements) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.requirements;
    }
}
