package android.support.p004v7.widget;

import android.graphics.Rect;
import android.support.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* renamed from: android.support.v7.widget.FitWindowsViewGroup */
public interface FitWindowsViewGroup {

    void setOnFitSystemWindowsListener(OnFitSystemWindowsListener onFitSystemWindowsListener);

    /* renamed from: android.support.v7.widget.FitWindowsViewGroup$OnFitSystemWindowsListener */
    public interface OnFitSystemWindowsListener {
        void onFitSystemWindows(Rect rect);
    }
}
