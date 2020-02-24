package com.google.android.tvrecommendations.shared.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

public class AppUtil {
    private AppUtil() {
    }

    public static boolean isSystemApp(ResolveInfo info) {
        return info.activityInfo != null && hasSystemFlags(info.activityInfo.applicationInfo);
    }

    public static boolean isSystemApp(Context context, String packageName) {
        try {
            return hasSystemFlags(context.getPackageManager().getApplicationInfo(packageName, 0));
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private static boolean hasSystemFlags(ApplicationInfo info) {
        return (info == null || (info.flags & 129) == 0) ? false : true;
    }
}
