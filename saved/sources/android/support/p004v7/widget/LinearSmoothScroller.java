package android.support.p004v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.support.p004v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/* renamed from: android.support.v7.widget.LinearSmoothScroller */
public class LinearSmoothScroller extends RecyclerView.SmoothScroller {
    private static final boolean DEBUG = false;
    private static final float MILLISECONDS_PER_INCH = 25.0f;
    public static final int SNAP_TO_ANY = 0;
    public static final int SNAP_TO_END = 1;
    public static final int SNAP_TO_START = -1;
    private static final float TARGET_SEEK_EXTRA_SCROLL_RATIO = 1.2f;
    private static final int TARGET_SEEK_SCROLL_DISTANCE_PX = 10000;
    protected final DecelerateInterpolator mDecelerateInterpolator = new DecelerateInterpolator();
    private final DisplayMetrics mDisplayMetrics;
    private boolean mHasCalculatedMillisPerPixel = false;
    protected int mInterimTargetDx = 0;
    protected int mInterimTargetDy = 0;
    protected final LinearInterpolator mLinearInterpolator = new LinearInterpolator();
    private float mMillisPerPixel;
    protected PointF mTargetVector;

    public LinearSmoothScroller(Context context) {
        this.mDisplayMetrics = context.getResources().getDisplayMetrics();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
    }

    /* access modifiers changed from: protected */
    public void onTargetFound(View targetView, RecyclerView.State state, RecyclerView.SmoothScroller.Action action) {
        int dx = calculateDxToMakeVisible(targetView, getHorizontalSnapPreference());
        int dy = calculateDyToMakeVisible(targetView, getVerticalSnapPreference());
        int time = calculateTimeForDeceleration((int) Math.sqrt((double) ((dx * dx) + (dy * dy))));
        if (time > 0) {
            action.update(-dx, -dy, time, this.mDecelerateInterpolator);
        }
    }

    /* access modifiers changed from: protected */
    public void onSeekTargetStep(int dx, int dy, RecyclerView.State state, RecyclerView.SmoothScroller.Action action) {
        if (getChildCount() == 0) {
            stop();
            return;
        }
        this.mInterimTargetDx = clampApplyScroll(this.mInterimTargetDx, dx);
        this.mInterimTargetDy = clampApplyScroll(this.mInterimTargetDy, dy);
        if (this.mInterimTargetDx == 0 && this.mInterimTargetDy == 0) {
            updateActionForInterimTarget(action);
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        this.mInterimTargetDy = 0;
        this.mInterimTargetDx = 0;
        this.mTargetVector = null;
    }

    /* access modifiers changed from: protected */
    public float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
        return MILLISECONDS_PER_INCH / ((float) displayMetrics.densityDpi);
    }

    private float getSpeedPerPixel() {
        if (!this.mHasCalculatedMillisPerPixel) {
            this.mMillisPerPixel = calculateSpeedPerPixel(this.mDisplayMetrics);
            this.mHasCalculatedMillisPerPixel = true;
        }
        return this.mMillisPerPixel;
    }

    /* access modifiers changed from: protected */
    public int calculateTimeForDeceleration(int dx) {
        double calculateTimeForScrolling = (double) calculateTimeForScrolling(dx);
        Double.isNaN(calculateTimeForScrolling);
        return (int) Math.ceil(calculateTimeForScrolling / 0.3356d);
    }

    /* access modifiers changed from: protected */
    public int calculateTimeForScrolling(int dx) {
        return (int) Math.ceil((double) (((float) Math.abs(dx)) * getSpeedPerPixel()));
    }

    /* access modifiers changed from: protected */
    public int getHorizontalSnapPreference() {
        PointF pointF = this.mTargetVector;
        if (pointF == null || pointF.x == 0.0f) {
            return 0;
        }
        return this.mTargetVector.x > 0.0f ? 1 : -1;
    }

    /* access modifiers changed from: protected */
    public int getVerticalSnapPreference() {
        PointF pointF = this.mTargetVector;
        if (pointF == null || pointF.y == 0.0f) {
            return 0;
        }
        return this.mTargetVector.y > 0.0f ? 1 : -1;
    }

    /* JADX INFO: Multiple debug info for r1v0 int: [D('time' int), D('target' int)] */
    /* access modifiers changed from: protected */
    public void updateActionForInterimTarget(RecyclerView.SmoothScroller.Action action) {
        PointF scrollVector = computeScrollVectorForPosition(getTargetPosition());
        if (scrollVector == null || (scrollVector.x == 0.0f && scrollVector.y == 0.0f)) {
            action.jumpTo(getTargetPosition());
            stop();
            return;
        }
        normalize(scrollVector);
        this.mTargetVector = scrollVector;
        this.mInterimTargetDx = (int) (scrollVector.x * 10000.0f);
        this.mInterimTargetDy = (int) (scrollVector.y * 10000.0f);
        action.update((int) (((float) this.mInterimTargetDx) * TARGET_SEEK_EXTRA_SCROLL_RATIO), (int) (((float) this.mInterimTargetDy) * TARGET_SEEK_EXTRA_SCROLL_RATIO), (int) (((float) calculateTimeForScrolling(10000)) * TARGET_SEEK_EXTRA_SCROLL_RATIO), this.mLinearInterpolator);
    }

    private int clampApplyScroll(int tmpDt, int dt) {
        int before = tmpDt;
        int tmpDt2 = tmpDt - dt;
        if (before * tmpDt2 <= 0) {
            return 0;
        }
        return tmpDt2;
    }

    public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
        if (snapPreference == -1) {
            return boxStart - viewStart;
        }
        if (snapPreference == 0) {
            int dtStart = boxStart - viewStart;
            if (dtStart > 0) {
                return dtStart;
            }
            int dtEnd = boxEnd - viewEnd;
            if (dtEnd < 0) {
                return dtEnd;
            }
            return 0;
        } else if (snapPreference == 1) {
            return boxEnd - viewEnd;
        } else {
            throw new IllegalArgumentException("snap preference should be one of the constants defined in SmoothScroller, starting with SNAP_");
        }
    }

    public int calculateDyToMakeVisible(View view, int snapPreference) {
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager == null || !layoutManager.canScrollVertically()) {
            return 0;
        }
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        return calculateDtToFit(layoutManager.getDecoratedTop(view) - params.topMargin, layoutManager.getDecoratedBottom(view) + params.bottomMargin, layoutManager.getPaddingTop(), layoutManager.getHeight() - layoutManager.getPaddingBottom(), snapPreference);
    }

    public int calculateDxToMakeVisible(View view, int snapPreference) {
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager == null || !layoutManager.canScrollHorizontally()) {
            return 0;
        }
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        return calculateDtToFit(layoutManager.getDecoratedLeft(view) - params.leftMargin, layoutManager.getDecoratedRight(view) + params.rightMargin, layoutManager.getPaddingLeft(), layoutManager.getWidth() - layoutManager.getPaddingRight(), snapPreference);
    }
}
