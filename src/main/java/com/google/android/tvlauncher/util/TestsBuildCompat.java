package com.google.android.tvlauncher.util;

import android.os.Build;

public class TestsBuildCompat {
    public static boolean isAtLeastO() {
        return !Build.TYPE.equals("unknown") && Build.VERSION.SDK_INT >= 26;
    }
}
