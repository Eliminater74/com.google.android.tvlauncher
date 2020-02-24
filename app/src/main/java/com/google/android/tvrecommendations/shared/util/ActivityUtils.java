package com.google.android.tvrecommendations.shared.util;

import android.app.Activity;
import android.os.Build;

public class ActivityUtils {
    private ActivityUtils() {
    }

    public static void forceLandscapeOrientation(Activity activity) {
        if (activity.getApplicationInfo().targetSdkVersion < 27 || Build.VERSION.SDK_INT != 26) {
            activity.setRequestedOrientation(0);
        }
    }
}
