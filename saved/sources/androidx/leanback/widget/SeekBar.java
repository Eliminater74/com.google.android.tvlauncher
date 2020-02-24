package androidx.leanback.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.RestrictTo;
import android.support.p001v4.internal.view.SupportMenu;
import android.util.AttributeSet;
import android.view.View;
import androidx.leanback.C0364R;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public final class SeekBar extends View {
    private AccessibilitySeekListener mAccessibilitySeekListener;
    private int mActiveBarHeight;
    private int mActiveRadius;
    private final Paint mBackgroundPaint = new Paint(1);
    private final RectF mBackgroundRect = new RectF();
    private int mBarHeight;
    private final Paint mKnobPaint = new Paint(1);
    private int mKnobx;
    private int mMax;
    private int mProgress;
    private final Paint mProgressPaint = new Paint(1);
    private final RectF mProgressRect = new RectF();
    private int mSecondProgress;
    private final Paint mSecondProgressPaint = new Paint(1);
    private final RectF mSecondProgressRect = new RectF();

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static abstract class AccessibilitySeekListener {
        public abstract boolean onAccessibilitySeekBackward();

        public abstract boolean onAccessibilitySeekForward();
    }

    public SeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        this.mBackgroundPaint.setColor(-7829368);
        this.mSecondProgressPaint.setColor(-3355444);
        this.mProgressPaint.setColor((int) SupportMenu.CATEGORY_MASK);
        this.mKnobPaint.setColor(-1);
        this.mBarHeight = context.getResources().getDimensionPixelSize(C0364R.dimen.lb_playback_transport_progressbar_bar_height);
        this.mActiveBarHeight = context.getResources().getDimensionPixelSize(C0364R.dimen.lb_playback_transport_progressbar_active_bar_height);
        this.mActiveRadius = context.getResources().getDimensionPixelSize(C0364R.dimen.lb_playback_transport_progressbar_active_radius);
    }

    public void setActiveRadius(int radius) {
        this.mActiveRadius = radius;
        calculate();
    }

    public void setBarHeight(int barHeight) {
        this.mBarHeight = barHeight;
        calculate();
    }

    public void setActiveBarHeight(int activeBarHeight) {
        this.mActiveBarHeight = activeBarHeight;
        calculate();
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        calculate();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calculate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int radius = isFocused() ? this.mActiveRadius : this.mBarHeight / 2;
        canvas.drawRoundRect(this.mBackgroundRect, (float) radius, (float) radius, this.mBackgroundPaint);
        if (this.mSecondProgressRect.right > this.mSecondProgressRect.left) {
            canvas.drawRoundRect(this.mSecondProgressRect, (float) radius, (float) radius, this.mSecondProgressPaint);
        }
        canvas.drawRoundRect(this.mProgressRect, (float) radius, (float) radius, this.mProgressPaint);
        canvas.drawCircle((float) this.mKnobx, (float) (getHeight() / 2), (float) radius, this.mKnobPaint);
    }

    public void setProgress(int progress) {
        if (progress > this.mMax) {
            progress = this.mMax;
        } else if (progress < 0) {
            progress = 0;
        }
        this.mProgress = progress;
        calculate();
    }

    public void setSecondaryProgress(int progress) {
        if (progress > this.mMax) {
            progress = this.mMax;
        } else if (progress < 0) {
            progress = 0;
        }
        this.mSecondProgress = progress;
        calculate();
    }

    public int getProgress() {
        return this.mProgress;
    }

    public int getSecondProgress() {
        return this.mSecondProgress;
    }

    public int getMax() {
        return this.mMax;
    }

    public void setMax(int max) {
        this.mMax = max;
        calculate();
    }

    public void setProgressColor(int color) {
        this.mProgressPaint.setColor(color);
    }

    public void setSecondaryProgressColor(int color) {
        this.mSecondProgressPaint.setColor(color);
    }

    public int getSecondaryProgressColor() {
        return this.mSecondProgressPaint.getColor();
    }

    private void calculate() {
        int barHeight = isFocused() ? this.mActiveBarHeight : this.mBarHeight;
        int width = getWidth();
        int height = getHeight();
        int verticalPadding = (height - barHeight) / 2;
        RectF rectF = this.mBackgroundRect;
        int i = this.mBarHeight;
        rectF.set((float) (i / 2), (float) verticalPadding, (float) (width - (i / 2)), (float) (height - verticalPadding));
        int radius = isFocused() ? this.mActiveRadius : this.mBarHeight / 2;
        int progressWidth = width - (radius * 2);
        float progressPixels = (((float) this.mProgress) / ((float) this.mMax)) * ((float) progressWidth);
        RectF rectF2 = this.mProgressRect;
        int i2 = this.mBarHeight;
        rectF2.set((float) (i2 / 2), (float) verticalPadding, ((float) (i2 / 2)) + progressPixels, (float) (height - verticalPadding));
        this.mSecondProgressRect.set(this.mProgressRect.right, (float) verticalPadding, ((float) (this.mBarHeight / 2)) + ((((float) this.mSecondProgress) / ((float) this.mMax)) * ((float) progressWidth)), (float) (height - verticalPadding));
        this.mKnobx = ((int) progressPixels) + radius;
        invalidate();
    }

    public CharSequence getAccessibilityClassName() {
        return android.widget.SeekBar.class.getName();
    }

    public void setAccessibilitySeekListener(AccessibilitySeekListener listener) {
        this.mAccessibilitySeekListener = listener;
    }

    public boolean performAccessibilityAction(int action, Bundle arguments) {
        AccessibilitySeekListener accessibilitySeekListener = this.mAccessibilitySeekListener;
        if (accessibilitySeekListener != null) {
            if (action == 4096) {
                return accessibilitySeekListener.onAccessibilitySeekForward();
            }
            if (action == 8192) {
                return accessibilitySeekListener.onAccessibilitySeekBackward();
            }
        }
        return super.performAccessibilityAction(action, arguments);
    }
}
