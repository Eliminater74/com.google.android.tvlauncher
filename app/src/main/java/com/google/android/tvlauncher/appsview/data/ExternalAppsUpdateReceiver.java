package com.google.android.tvlauncher.appsview.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

class ExternalAppsUpdateReceiver extends BroadcastReceiver {
    ExternalAppsUpdateReceiver() {
    }

    public void onReceive(Context context, Intent intent) {
        LaunchItemsManagerProvider.getInstance(context).refreshLaunchItems();
    }

    public static IntentFilter getIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE");
        filter.addAction("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
        return filter;
    }
}
