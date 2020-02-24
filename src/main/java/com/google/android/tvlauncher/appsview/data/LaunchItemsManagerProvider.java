package com.google.android.tvlauncher.appsview.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

public class LaunchItemsManagerProvider {
    @SuppressLint({"StaticFieldLeak"})
    private static LaunchItemsManager sLaunchItemsManager;

    public static LaunchItemsManager getInstance(Context context) {
        if (sLaunchItemsManager == null) {
            sLaunchItemsManager = new FlavorSpecificLaunchItemsManager(context.getApplicationContext());
        }
        return sLaunchItemsManager;
    }

    @VisibleForTesting
    public static void setInstance(LaunchItemsManager launchItemsManager) {
        sLaunchItemsManager = launchItemsManager;
    }
}
