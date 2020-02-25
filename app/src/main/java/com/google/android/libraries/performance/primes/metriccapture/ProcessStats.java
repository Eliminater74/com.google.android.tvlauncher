package com.google.android.libraries.performance.primes.metriccapture;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.PowerManager;
import android.os.Process;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ProcessStats {
    private static final String TAG = "ProcessStats";
    static volatile boolean callingProcessForegroundNotionEnabled;
    private static volatile ActivityManager activityManager = null;

    public static ActivityManager getActivityManager(Context appContext) {
        if (activityManager == null) {
            synchronized (ProcessStats.class) {
                if (activityManager == null) {
                    activityManager = (ActivityManager) appContext.getSystemService("activity");
                }
            }
        }
        return activityManager;
    }

    public static boolean isAppInForeground(Context context) {
        if (callingProcessForegroundNotionEnabled) {
            return isMyProcessInForeground(context);
        }
        return isAnyAppProcessInForeground(context);
    }

    public static boolean isAnyAppProcessInForeground(Context appContext) {
        List<ActivityManager.RunningAppProcessInfo> appProcesses = ((ActivityManager) appContext.getSystemService("activity")).getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == 100 && appProcess.processName.contains(appContext.getPackageName())) {
                boolean isAppInForeground = true;
                if (Build.VERSION.SDK_INT < 23) {
                    isAppInForeground = isScreenOn(appContext);
                }
                if (isAppInForeground) {
                    return true;
                }
            }
        }
        return false;
    }

    @TargetApi(20)
    public static boolean isScreenOn(Context appContext) {
        PowerManager powerManager = (PowerManager) appContext.getSystemService("power");
        if (Build.VERSION.SDK_INT >= 20) {
            return powerManager.isInteractive();
        }
        return powerManager.isScreenOn();
    }

    public static boolean isMyProcessInForeground(Context appContext) {
        List<ActivityManager.RunningAppProcessInfo> appProcesses = getActivityManager(appContext).getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.pid == Process.myPid()) {
                if (appProcess.importance == 100) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public static String getShortProcessName(Context appContext) {
        return getShortProcessName(appContext.getPackageName(), getCurrentProcessName());
    }

    public static String getShortProcessName(String applicationPackage, String processName) {
        if (processName == null || applicationPackage == null || !processName.startsWith(applicationPackage)) {
            return processName;
        }
        int packageNameLength = applicationPackage.length();
        if (processName.length() == packageNameLength) {
            return null;
        }
        return processName.substring(packageNameLength + 1);
    }

    public static String getCurrentProcessName() {
        return getProcessNameFromProcFile(Process.myPid());
    }

    private static String getProcessNameFromProcFile(int pid) {
        if (pid <= 0) {
            return null;
        }
        BufferedReader reader = null;
        String processName = null;
        try {
            StringBuilder sb = new StringBuilder(25);
            sb.append("/proc/");
            sb.append(pid);
            sb.append("/cmdline");
            BufferedReader reader2 = new BufferedReader(new FileReader(sb.toString()));
            processName = reader2.readLine().trim();
            try {
                reader2.close();
            } catch (IOException e) {
            }
        } catch (IOException e2) {
            if (reader != null) {
                reader.close();
            }
        } catch (Throwable th) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e3) {
                }
            }
            throw th;
        }
        return processName;
    }
}
