package com.google.android.exoplayer2.p008ui.spherical;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.os.SystemClock;
import android.support.annotation.AnyThread;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.google.android.exoplayer2.util.Assertions;

/* renamed from: com.google.android.exoplayer2.ui.spherical.GlViewGroup */
public final class GlViewGroup extends FrameLayout {
    private final CanvasRenderer canvasRenderer = new CanvasRenderer();

    public GlViewGroup(Context context, int layoutId) {
        super(context);
        LayoutInflater.from(context).inflate(layoutId, this);
        measure(-2, -2);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        Assertions.checkState(width > 0 && height > 0);
        this.canvasRenderer.setSize(width, height);
        setLayoutParams(new FrameLayout.LayoutParams(width, height));
    }

    @UiThread
    public boolean isVisible() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i).getVisibility() == 0) {
                return true;
            }
        }
        return false;
    }

    public void dispatchDraw(Canvas notUsed) {
        Canvas glCanvas = this.canvasRenderer.lockCanvas();
        if (glCanvas == null) {
            postInvalidate();
            return;
        }
        glCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        super.dispatchDraw(glCanvas);
        this.canvasRenderer.unlockCanvasAndPost(glCanvas);
    }

    @UiThread
    public boolean simulateClick(int action, float yaw, float pitch) {
        PointF point;
        if (!isVisible() || (point = this.canvasRenderer.translateClick(yaw, pitch)) == null) {
            return false;
        }
        long now = SystemClock.uptimeMillis();
        dispatchTouchEvent(MotionEvent.obtain(now, now, action, point.x, point.y, 1));
        return true;
    }

    @AnyThread
    public CanvasRenderer getRenderer() {
        return this.canvasRenderer;
    }
}
