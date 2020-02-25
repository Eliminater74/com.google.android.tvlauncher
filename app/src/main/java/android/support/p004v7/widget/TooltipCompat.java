package android.support.p004v7.widget;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/* renamed from: android.support.v7.widget.TooltipCompat */
public class TooltipCompat {
    private TooltipCompat() {
    }

    public static void setTooltipText(@NonNull View view, @Nullable CharSequence tooltipText) {
        if (Build.VERSION.SDK_INT >= 26) {
            view.setTooltipText(tooltipText);
        } else {
            TooltipCompatHandler.setTooltipText(view, tooltipText);
        }
    }
}
