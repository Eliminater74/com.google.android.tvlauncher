package com.google.android.libraries.stitch.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public final class DisplayMetricsUtils {
    @Deprecated
    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static float getSmallestDimensionDips(Context context) {
        return getSmallestDimensionDips(getDisplayMetrics(context));
    }

    public static float getSmallestDimensionDips(DisplayMetrics displayMetrics) {
        return Math.min(((float) displayMetrics.widthPixels) / displayMetrics.density, ((float) displayMetrics.heightPixels) / displayMetrics.density);
    }

    public static float getWidthPixels(Context context) {
        return (float) getDisplayMetrics(context).widthPixels;
    }

    public static float getSmallestDimensionPixels(Context context) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        return (float) Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    @Deprecated
    public static boolean isTablet(Context context) {
        return ConfigurationUtils.isTablet(context.getResources().getConfiguration());
    }

    public static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 4) != 0;
    }
}
