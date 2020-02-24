package com.google.android.libraries.stitch.util;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

public final class MemoryClass {
    @TargetApi(11)
    public static int getMemoryClass(Context context) {
        ActivityManager mgr = (ActivityManager) context.getSystemService("activity");
        if (Build.VERSION.SDK_INT >= 11) {
            try {
                if ((context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).flags & 1048576) != 0) {
                    return mgr.getLargeMemoryClass();
                }
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return mgr.getMemoryClass();
    }
}
