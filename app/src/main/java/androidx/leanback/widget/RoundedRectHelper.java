package androidx.leanback.widget;

import android.os.Build;
import android.view.View;

import androidx.leanback.C0364R;

final class RoundedRectHelper {
    private RoundedRectHelper() {
    }

    static boolean supportsRoundedCorner() {
        return Build.VERSION.SDK_INT >= 21;
    }

    static void setClipToRoundedOutline(View view, boolean clip, int radius) {
        if (Build.VERSION.SDK_INT >= 21) {
            RoundedRectHelperApi21.setClipToRoundedOutline(view, clip, radius);
        }
    }

    static void setClipToRoundedOutline(View view, boolean clip) {
        if (Build.VERSION.SDK_INT >= 21) {
            RoundedRectHelperApi21.setClipToRoundedOutline(view, clip, view.getResources().getDimensionPixelSize(C0364R.dimen.lb_rounded_rect_corner_radius));
        }
    }
}
