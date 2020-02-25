package com.google.android.tvrecommendations.shared.util;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.support.annotation.ColorInt;

import androidx.leanback.graphics.ColorFilterCache;

public class ColorUtils {
    @ColorInt
    public static int darkenColor(@ColorInt int color, float factor) {
        float[] hsv = new float[3];
        Color.RGBToHSV(Color.red(color), Color.green(color), Color.blue(color), hsv);
        hsv[2] = hsv[2] * factor;
        return Color.HSVToColor(hsv);
    }

    public static ColorFilter getColorFilter(@ColorInt int color, float alpha) {
        return ColorFilterCache.getColorFilterCache(color).getFilterForLevel(alpha);
    }
}
