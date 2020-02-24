package com.google.android.libraries.stitch.util;

import android.content.res.Configuration;
import android.os.Build;

public final class ConfigurationUtils {
    private static final int TABLET_MIN_DPS = 600;

    public static boolean isTablet(Configuration configuration) {
        if (Build.VERSION.SDK_INT < 13) {
            int screenSize = configuration.screenLayout & 15;
            if (screenSize == 3 || screenSize == 4) {
                return true;
            }
            return false;
        } else if (configuration.smallestScreenWidthDp >= 600) {
            return true;
        } else {
            return false;
        }
    }

    private ConfigurationUtils() {
    }
}
