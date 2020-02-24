package com.google.android.tvlauncher.analytics;

import android.content.ComponentName;
import android.content.Intent;

public final class LogUtils {
    public static String getPackage(Intent intent) {
        ComponentName componentName = intent.getComponent();
        if (componentName != null) {
            return componentName.getPackageName();
        }
        return intent.getPackage();
    }

    private LogUtils() {
    }
}
