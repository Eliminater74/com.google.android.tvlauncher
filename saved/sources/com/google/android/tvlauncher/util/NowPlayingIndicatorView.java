package com.google.android.tvlauncher.util;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.google.android.tvlauncher.C1188R;

public class NowPlayingIndicatorView extends View {
    private static final int[][] LEVELS = {new int[]{5, 3, 5, 7, 9, 10, 11, 12, 11, 12, 10, 8, 7, 4, 2, 4, 6, 7, 9, 11, 9, 7, 5, 3, 5, 8, 5, 3, 4}, new int[]{12, 11, 10, 11, 12, 11, 9, 7, 9, 11, 12, 10, 8, 10, 12, 11, 9, 5, 3, 5, 8, 10, 12, 10, 9, 8}, new int[]{8, 9, 10, 12, 11, 9, 7, 5, 7, 8, 9, 12, 11, 12, 9, 7, 9, 11, 12, 10, 8, 9, 7, 5, 3}};
    private static final int MAX_LEVEL = 15;
    private static final float PAUSED_LEVEL = 0.5f;
    private static final int TICK_DURATION_MS = 80;
    private final ValueAnimator mAnimator;
    private final int mBarSeparationPx;
    private final int mBarWidthPx;
    private final Rect mDrawRect = new Rect();
    private final Paint mPaint;
    /* access modifiers changed from: private */
    public float mProgress;

    /* JADX INFO: finally extract failed */
    public NowPlayingIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray customAttrs = context.obtainStyledAttributes(attrs, C1188R.styleable.NowPlayingIndicatorView, 0, C1188R.style.NowPlayingIndicatorViewStyle);
        try {
            this.mBarWidthPx = customAttrs.getDimensionPixelSize(C1188R.styleable.NowPlayingIndicatorView_bar_width, 0);
            this.mBarSeparationPx = customAttrs.getDimensionPixelSize(C1188R.styleable.NowPlayingIndicatorView_bar_separation, 0);
            customAttrs.recycle();
            this.mAnimator = new ValueAnimator();
            this.mAnimator.setInterpolator(new LinearInterpolator());
            this.mAnimator.setRepeatCount(-1);
            this.mAnimator.setDuration(100000000L);
            ValueAnimator valueAnimator = this.mAnimator;
            valueAnimator.setFloatValues(0.0f, (float) (valueAnimator.getDuration() / 80));
            this.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    float unused = NowPlayingIndicatorView.this.mProgress = ((Float) animation.getAnimatedValue()).floatValue();
                    NowPlayingIndicatorView.this.invalidate();
                }
            });
            this.mPaint = new Paint();
            this.mPaint.setColor(-1);
            setLayerType(1, null);
            setImportantForAccessibility(2);
        } catch (Throwable th) {
            customAttrs.recycle();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == 0) {
            startAnimationIfVisible();
        } else {
            stopAnimation();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = (this.mBarWidthPx * 3) + (this.mBarSeparationPx * 2) + getPaddingStart() + getPaddingEnd();
        setMeasuredDimension(width, width);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimationIfVisible();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }

    public void stopAnimation() {
        this.mAnimator.cancel();
        postInvalidate();
    }

    public void startAnimationIfVisible() {
        if (getVisibility() == 0) {
            this.mAnimator.start();
            postInvalidate();
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        drawRectangles(canvas);
    }

    private void drawRectangles(Canvas canvas) {
        for (int barIndex = 0; barIndex < 3; barIndex++) {
            this.mDrawRect.left = ((this.mBarWidthPx + this.mBarSeparationPx) * barIndex) + getPaddingStart();
            Rect rect = this.mDrawRect;
            rect.right = rect.left + this.mBarWidthPx;
            this.mDrawRect.bottom = getHeight() - getPaddingBottom();
            float value = linearlyInterpolateWithWrapping(this.mProgress, LEVELS[barIndex]);
            int barMaxHeight = (getHeight() - getPaddingTop()) - getPaddingBottom();
            this.mDrawRect.top = (int) (((float) getPaddingTop()) + (((float) barMaxHeight) * (1.0f - (value / 15.0f))));
            canvas.drawRect(this.mDrawRect, this.mPaint);
        }
    }

    private static float linearlyInterpolateWithWrapping(float position, int[] array) {
        int positionRoundedDown = (int) position;
        int beforeIndex = positionRoundedDown % array.length;
        float weight = position - ((float) positionRoundedDown);
        return (((float) array[beforeIndex]) * (1.0f - weight)) + (((float) array[(beforeIndex + 1) % array.length]) * weight);
    }
}
