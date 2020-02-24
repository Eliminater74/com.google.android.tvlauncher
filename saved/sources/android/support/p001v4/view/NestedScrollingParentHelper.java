package android.support.p001v4.view;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

/* renamed from: android.support.v4.view.NestedScrollingParentHelper */
public class NestedScrollingParentHelper {
    private int mNestedScrollAxesNonTouch;
    private int mNestedScrollAxesTouch;

    public NestedScrollingParentHelper(@NonNull ViewGroup viewGroup) {
    }

    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes) {
        onNestedScrollAccepted(child, target, axes, 0);
    }

    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        if (type == 1) {
            this.mNestedScrollAxesNonTouch = axes;
        } else {
            this.mNestedScrollAxesTouch = axes;
        }
    }

    public int getNestedScrollAxes() {
        return this.mNestedScrollAxesTouch | this.mNestedScrollAxesNonTouch;
    }

    public void onStopNestedScroll(@NonNull View target) {
        onStopNestedScroll(target, 0);
    }

    public void onStopNestedScroll(@NonNull View target, int type) {
        if (type == 1) {
            this.mNestedScrollAxesNonTouch = 0;
        } else {
            this.mNestedScrollAxesTouch = 0;
        }
    }
}
