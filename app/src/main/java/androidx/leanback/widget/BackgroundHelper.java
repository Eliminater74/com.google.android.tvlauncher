package androidx.leanback.widget;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RestrictTo;
import android.view.View;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public final class BackgroundHelper {
    public static void setBackgroundPreservingAlpha(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 19) {
            if (view.getBackground() != null) {
                drawable.setAlpha(view.getBackground().getAlpha());
            }
            view.setBackground(drawable);
            return;
        }
        view.setBackground(drawable);
    }

    private BackgroundHelper() {
    }
}
