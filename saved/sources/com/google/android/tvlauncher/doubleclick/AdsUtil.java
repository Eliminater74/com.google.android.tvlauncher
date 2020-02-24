package com.google.android.tvlauncher.doubleclick;

import android.graphics.Rect;
import android.view.View;

public class AdsUtil {
    public static final int MIN_VISIBLE_DURATION_FOR_IMPRESSION = 3000;
    private static final double MIN_VISIBLE_FRACTION_FOR_IMPRESSION = 0.5d;
    public static final int VISIBILITY_CHECK_POLL_INTERVAL_MS = 1000;

    public boolean isViewVisible(View view) {
        Rect visibleRect = new Rect();
        if (!(view.getVisibility() == 0 && view.isShown() && view.getGlobalVisibleRect(visibleRect))) {
            return false;
        }
        int totalArea = view.getWidth() * view.getHeight();
        double width = (double) (visibleRect.width() * visibleRect.height());
        double d = (double) totalArea;
        Double.isNaN(width);
        Double.isNaN(d);
        if (width / d >= MIN_VISIBLE_FRACTION_FOR_IMPRESSION) {
            return true;
        }
        return false;
    }
}
