package com.google.android.tvrecommendations.shared.util;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.annotation.Nullable;

import java.util.List;

public class OemUtils {
    @Nullable
    public static String getCustomizationApp(PackageManager pm) {
        Intent intent;
        ResolveInfo appInfo = null;
        if (isDebugMode()) {
            intent = new Intent(Constants.ACTION_OEM_CUSTOMIZATION_TEST);
        } else {
            intent = new Intent(Constants.ACTION_OEM_CUSTOMIZATION);
        }
        List<ResolveInfo> packages = pm.queryBroadcastReceivers(intent, 0);
        if (packages != null) {
            if (!isDebugMode()) {
                for (ResolveInfo info : packages) {
                    if (AppUtil.isSystemApp(info)) {
                        appInfo = info;
                    }
                }
            } else if (!packages.isEmpty()) {
                appInfo = packages.get(0);
            }
        }
        if (appInfo == null) {
            return null;
        }
        return appInfo.activityInfo.packageName;
    }

    private static boolean isDebugMode() {
        return false;
    }
}
