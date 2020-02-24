package com.google.android.tvlauncher.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class PackageUtils {
    private static final String TAG = "PackageUtils";

    public static String getApplicationVersionName(Context context, String packageName) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            if (info.versionName != null) {
                return info.versionName;
            }
            return "unknown";
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Could not retrieve application version name", e);
            return "unknown";
        }
    }

    public static int getApplicationVersionCode(Context context, String packageName) {
        try {
            return context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Could not retrieve application version code", e);
            return 0;
        }
    }

    public static boolean isPackageInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
