package com.google.android.libraries.directboot;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Process;
import android.os.UserManager;
import android.support.annotation.GuardedBy;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import java.util.List;

public class DirectBootUtils {
    private static final boolean DEBUG = false;
    private static final String TAG = "DirectBootUtils";
    private static volatile boolean isUserUnlocked = (!supportsDirectBoot());
    @GuardedBy("DirectBootUtils.class")
    private static boolean useNewUserUnlocked = false;
    @GuardedBy("DirectBootUtils.class")
    private static UserManager userManager;

    private DirectBootUtils() {
    }

    public static boolean supportsDirectBoot() {
        return Build.VERSION.SDK_INT >= 24;
    }

    public static boolean isDirectBoot(Context context) {
        return supportsDirectBoot() && !checkUserUnlocked(context);
    }

    public static boolean isUserUnlocked(Context context) {
        return !supportsDirectBoot() || checkUserUnlocked(context);
    }

    public static synchronized void useNewUserUnlocked() {
        synchronized (DirectBootUtils.class) {
            useNewUserUnlocked = true;
        }
    }

    @GuardedBy("DirectBootUtils.class")
    private static boolean computeUserUnlockedWithPackageManager(Context context) {
        List<ResolveInfo> availableServices = context.getPackageManager().queryIntentServices(new Intent().setClassName(context, DirectBootHelperService.class.getName()), 512);
        return availableServices != null && !availableServices.isEmpty();
    }

    @TargetApi(24)
    @RequiresApi(24)
    @GuardedBy("DirectBootUtils.class")
    private static boolean computeUserUnlockedWithUserManager(Context context) {
        boolean isUserUnlocked2 = false;
        int attempt = 1;
        while (true) {
            if (attempt > 2) {
                break;
            }
            UserManager userManager2 = getUserManager(context);
            boolean z = true;
            if (userManager2 == null) {
                return true;
            }
            try {
                if (!userManager2.isUserUnlocked() && userManager2.isUserRunning(Process.myUserHandle())) {
                    z = false;
                }
                isUserUnlocked2 = z;
            } catch (NullPointerException e) {
                Log.w(TAG, "Failed to check if user is unlocked.", e);
                userManager = null;
                attempt++;
            }
        }
        if (isUserUnlocked2) {
            userManager = null;
        }
        return isUserUnlocked2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0023, code lost:
        return r0;
     */
    @android.annotation.TargetApi(24)
    @android.support.annotation.RequiresApi(24)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean checkUserUnlocked(android.content.Context r4) {
        /*
            boolean r0 = com.google.android.libraries.directboot.DirectBootUtils.isUserUnlocked
            r1 = 1
            if (r0 == 0) goto L_0x0006
            return r1
        L_0x0006:
            java.lang.Class<com.google.android.libraries.directboot.DirectBootUtils> r2 = com.google.android.libraries.directboot.DirectBootUtils.class
            monitor-enter(r2)
            boolean r3 = com.google.android.libraries.directboot.DirectBootUtils.isUserUnlocked     // Catch:{ all -> 0x0024 }
            r0 = r3
            if (r0 == 0) goto L_0x0010
            monitor-exit(r2)     // Catch:{ all -> 0x0024 }
            return r1
        L_0x0010:
            boolean r1 = com.google.android.libraries.directboot.DirectBootUtils.useNewUserUnlocked     // Catch:{ all -> 0x0024 }
            if (r1 == 0) goto L_0x0019
            boolean r1 = computeUserUnlockedWithPackageManager(r4)     // Catch:{ all -> 0x0024 }
            goto L_0x001d
        L_0x0019:
            boolean r1 = computeUserUnlockedWithUserManager(r4)     // Catch:{ all -> 0x0024 }
        L_0x001d:
            r0 = r1
            if (r0 == 0) goto L_0x0022
            com.google.android.libraries.directboot.DirectBootUtils.isUserUnlocked = r0     // Catch:{ all -> 0x0024 }
        L_0x0022:
            monitor-exit(r2)     // Catch:{ all -> 0x0024 }
            return r0
        L_0x0024:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0024 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.directboot.DirectBootUtils.checkUserUnlocked(android.content.Context):boolean");
    }

    @VisibleForTesting
    @RequiresApi(24)
    @TargetApi(24)
    @GuardedBy("DirectBootUtils.class")
    static UserManager getUserManager(Context context) {
        if (userManager == null) {
            userManager = (UserManager) context.getSystemService(UserManager.class);
        }
        return userManager;
    }

    @VisibleForTesting(otherwise = 5)
    public static synchronized void resetForTest() {
        synchronized (DirectBootUtils.class) {
            userManager = null;
            isUserUnlocked = !supportsDirectBoot();
        }
    }

    @TargetApi(24)
    @RequiresApi(24)
    public static Context getDeviceProtectedStorageContext(Context context) {
        if (context.isDeviceProtectedStorage()) {
            return context;
        }
        return context.createDeviceProtectedStorageContext();
    }

    public static Context getDeviceProtectedStorageContextOrFallback(Context context) {
        if (supportsDirectBoot()) {
            return getDeviceProtectedStorageContext(context);
        }
        return context;
    }
}
