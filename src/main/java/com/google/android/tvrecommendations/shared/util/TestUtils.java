package com.google.android.tvrecommendations.shared.util;

import android.app.ActivityManager;
import android.os.Build;

public class TestUtils {
    public static boolean isRunningInTest() {
        return ActivityManager.isRunningInTestHarness() || isRunningRobolectric() || ActivityManager.isUserAMonkey();
    }

    private static boolean isRunningRobolectric() {
        return "robolectric".equals(Build.FINGERPRINT);
    }
}
