package android.support.p001v4.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewParent;

/* renamed from: android.support.v4.view.NestedScrollingChildHelper */
public class NestedScrollingChildHelper {
    private final View mView;
    private boolean mIsNestedScrollingEnabled;
    private ViewParent mNestedScrollingParentNonTouch;
    private ViewParent mNestedScrollingParentTouch;
    private int[] mTempNestedScrollConsumed;

    public NestedScrollingChildHelper(@NonNull View view) {
        this.mView = view;
    }

    public boolean isNestedScrollingEnabled() {
        return this.mIsNestedScrollingEnabled;
    }

    public void setNestedScrollingEnabled(boolean enabled) {
        if (this.mIsNestedScrollingEnabled) {
            ViewCompat.stopNestedScroll(this.mView);
        }
        this.mIsNestedScrollingEnabled = enabled;
    }

    public boolean hasNestedScrollingParent() {
        return hasNestedScrollingParent(0);
    }

    public boolean hasNestedScrollingParent(int type) {
        return getNestedScrollingParentForType(type) != null;
    }

    public boolean startNestedScroll(int axes) {
        return startNestedScroll(axes, 0);
    }

    public boolean startNestedScroll(int axes, int type) {
        if (hasNestedScrollingParent(type)) {
            return true;
        }
        if (!isNestedScrollingEnabled()) {
            return false;
        }
        View child = this.mView;
        for (ViewParent p = this.mView.getParent(); p != null; p = p.getParent()) {
            if (ViewParentCompat.onStartNestedScroll(p, child, this.mView, axes, type)) {
                setNestedScrollingParentForType(type, p);
                ViewParentCompat.onNestedScrollAccepted(p, child, this.mView, axes, type);
                return true;
            }
            if (p instanceof View) {
                child = (View) p;
            }
        }
        return false;
    }

    public void stopNestedScroll() {
        stopNestedScroll(0);
    }

    public void stopNestedScroll(int type) {
        ViewParent parent = getNestedScrollingParentForType(type);
        if (parent != null) {
            ViewParentCompat.onStopNestedScroll(parent, this.mView, type);
            setNestedScrollingParentForType(type, null);
        }
    }

    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow) {
        return dispatchNestedScrollInternal(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, 0, null);
    }

    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type) {
        return dispatchNestedScrollInternal(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type, null);
    }

    public void dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type, @Nullable int[] consumed) {
        dispatchNestedScrollInternal(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type, consumed);
    }

    private boolean dispatchNestedScrollInternal(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type, @Nullable int[] consumed) {
        int startY;
        int startX;
        int[] consumed2;
        int[] iArr = offsetInWindow;
        if (isNestedScrollingEnabled()) {
            ViewParent parent = getNestedScrollingParentForType(type);
            if (parent == null) {
                return false;
            }
            if (dxConsumed != 0 || dyConsumed != 0 || dxUnconsumed != 0 || dyUnconsumed != 0) {
                if (iArr != null) {
                    this.mView.getLocationInWindow(iArr);
                    startX = iArr[0];
                    startY = iArr[1];
                } else {
                    startX = 0;
                    startY = 0;
                }
                if (consumed == null) {
                    int[] consumed3 = getTempNestedScrollConsumed();
                    consumed3[0] = 0;
                    consumed3[1] = 0;
                    consumed2 = consumed3;
                } else {
                    consumed2 = consumed;
                }
                ViewParentCompat.onNestedScroll(parent, this.mView, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed2);
                if (iArr != null) {
                    this.mView.getLocationInWindow(iArr);
                    iArr[0] = iArr[0] - startX;
                    iArr[1] = iArr[1] - startY;
                }
                return true;
            } else if (iArr != null) {
                iArr[0] = 0;
                iArr[1] = 0;
            }
        }
        return false;
    }

    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow) {
        return dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, 0);
    }

    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow, int type) {
        int startY;
        int startX;
        int[] consumed2;
        int[] iArr = offsetInWindow;
        if (isNestedScrollingEnabled()) {
            ViewParent parent = getNestedScrollingParentForType(type);
            if (parent == null) {
                return false;
            }
            if (dx != 0 || dy != 0) {
                if (iArr != null) {
                    this.mView.getLocationInWindow(iArr);
                    startX = iArr[0];
                    startY = iArr[1];
                } else {
                    startX = 0;
                    startY = 0;
                }
                if (consumed == null) {
                    consumed2 = getTempNestedScrollConsumed();
                } else {
                    consumed2 = consumed;
                }
                consumed2[0] = 0;
                consumed2[1] = 0;
                ViewParentCompat.onNestedPreScroll(parent, this.mView, dx, dy, consumed2, type);
                if (iArr != null) {
                    this.mView.getLocationInWindow(iArr);
                    iArr[0] = iArr[0] - startX;
                    iArr[1] = iArr[1] - startY;
                }
                if (consumed2[0] == 0 && consumed2[1] == 0) {
                    return false;
                }
                return true;
            } else if (iArr != null) {
                iArr[0] = 0;
                iArr[1] = 0;
            }
        }
        return false;
    }

    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        ViewParent parent;
        if (!isNestedScrollingEnabled() || (parent = getNestedScrollingParentForType(0)) == null) {
            return false;
        }
        return ViewParentCompat.onNestedFling(parent, this.mView, velocityX, velocityY, consumed);
    }

    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        ViewParent parent;
        if (!isNestedScrollingEnabled() || (parent = getNestedScrollingParentForType(0)) == null) {
            return false;
        }
        return ViewParentCompat.onNestedPreFling(parent, this.mView, velocityX, velocityY);
    }

    public void onDetachedFromWindow() {
        ViewCompat.stopNestedScroll(this.mView);
    }

    public void onStopNestedScroll(@NonNull View child) {
        ViewCompat.stopNestedScroll(this.mView);
    }

    private ViewParent getNestedScrollingParentForType(int type) {
        if (type == 0) {
            return this.mNestedScrollingParentTouch;
        }
        if (type != 1) {
            return null;
        }
        return this.mNestedScrollingParentNonTouch;
    }

    private void setNestedScrollingParentForType(int type, ViewParent p) {
        if (type == 0) {
            this.mNestedScrollingParentTouch = p;
        } else if (type == 1) {
            this.mNestedScrollingParentNonTouch = p;
        }
    }

    private int[] getTempNestedScrollConsumed() {
        if (this.mTempNestedScrollConsumed == null) {
            this.mTempNestedScrollConsumed = new int[2];
        }
        return this.mTempNestedScrollConsumed;
    }
}
