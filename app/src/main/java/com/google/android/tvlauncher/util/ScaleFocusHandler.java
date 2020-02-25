package com.google.android.tvlauncher.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ScaleFocusHandler implements View.OnFocusChangeListener {
    public static final int FOCUS_DELAY_MILLIS = 60;
    public static final int PIVOT_CENTER = 0;
    public static final int PIVOT_START = 1;
    private final int mAnimationDuration;
    private final float mFocusedElevation;
    /* access modifiers changed from: private */
    public View mView;
    private AnimatorSet mAnimator;
    private Animator.AnimatorListener mAnimatorListener;
    private Runnable mDelayedFocusRunnable;
    private Runnable mDelayedUnfocusRunnable;
    private float mFocusedScale;
    private View.OnFocusChangeListener mOnFocusChangeListener;
    private int mPivot;
    private PivotProvider mPivotProvider;
    private int mPivotVerticalShift;

    public ScaleFocusHandler(int animationDuration, float scale, float elevation) {
        this(animationDuration, scale, elevation, 0);
    }

    public ScaleFocusHandler(int animationDuration, float scale, float elevation, int pivot) {
        this.mPivot = 0;
        this.mDelayedFocusRunnable = new Runnable() {
            public void run() {
                if (ScaleFocusHandler.this.mView.isFocused()) {
                    ScaleFocusHandler.this.animateFocusedState(true);
                }
            }
        };
        this.mDelayedUnfocusRunnable = new Runnable() {
            public void run() {
                if (!ScaleFocusHandler.this.mView.isFocused()) {
                    ScaleFocusHandler.this.animateFocusedState(false);
                }
            }
        };
        this.mAnimationDuration = animationDuration;
        this.mFocusedScale = scale;
        this.mFocusedElevation = elevation;
        this.mPivot = pivot;
    }

    public ScaleFocusHandler(ScaleFocusHandler handler) {
        this(handler.mAnimationDuration, handler.mFocusedScale, handler.mFocusedElevation, handler.mPivot);
    }

    public void setView(View view) {
        this.mView = view;
        this.mView.setOnFocusChangeListener(this);
    }

    public void setFocusedScale(float focusedScale) {
        this.mFocusedScale = focusedScale;
    }

    public void setPivot(int pivot) {
        this.mPivot = pivot;
    }

    public void setPivotVerticalShift(int pivotVerticalShift) {
        this.mPivotVerticalShift = pivotVerticalShift;
    }

    public void setOnFocusChangeListener(View.OnFocusChangeListener onFocusChangeListener) {
        this.mOnFocusChangeListener = onFocusChangeListener;
    }

    public void setPivotProvider(PivotProvider pivotProvider) {
        this.mPivotProvider = pivotProvider;
    }

    public void resetFocusedState() {
        releaseAnimator();
        float scale = this.mView.isFocused() ? this.mFocusedScale : 1.0f;
        float elevation = this.mView.isFocused() ? this.mFocusedElevation : 0.0f;
        applyPivot();
        this.mView.setScaleX(scale);
        this.mView.setScaleY(scale);
        this.mView.setTranslationZ(elevation);
    }

    public int getAnimationDuration() {
        return this.mAnimationDuration;
    }

    private void cancelAnimation() {
        AnimatorSet animatorSet = this.mAnimator;
        if (animatorSet != null) {
            animatorSet.cancel();
            releaseAnimator();
        }
    }

    /* access modifiers changed from: private */
    public void releaseAnimator() {
        AnimatorSet animatorSet = this.mAnimator;
        if (animatorSet != null) {
            Animator.AnimatorListener animatorListener = this.mAnimatorListener;
            if (animatorListener != null) {
                animatorSet.removeListener(animatorListener);
            }
            this.mAnimator = null;
        }
        this.mAnimatorListener = null;
    }

    public void onFocusChange(View v, boolean hasFocus) {
        v.removeCallbacks(this.mDelayedFocusRunnable);
        v.removeCallbacks(this.mDelayedUnfocusRunnable);
        v.postDelayed(hasFocus ? this.mDelayedFocusRunnable : this.mDelayedUnfocusRunnable, 60);
        View.OnFocusChangeListener onFocusChangeListener = this.mOnFocusChangeListener;
        if (onFocusChangeListener != null) {
            onFocusChangeListener.onFocusChange(v, hasFocus);
        }
    }

    public void animateFocusedState(boolean hasFocus) {
        cancelAnimation();
        float beforePivotX = this.mView.getPivotX();
        applyPivot();
        boolean animatePivot = false;
        PivotProvider pivotProvider = this.mPivotProvider;
        if (pivotProvider != null) {
            animatePivot = pivotProvider.shouldAnimate();
        }
        ObjectAnimator pivotAnimator = null;
        if (animatePivot) {
            View view = this.mView;
            pivotAnimator = ObjectAnimator.ofFloat(view, "pivotX", beforePivotX, view.getPivotX());
        }
        float scale = hasFocus ? this.mFocusedScale : 1.0f;
        ObjectAnimator elevationAnimator = ObjectAnimator.ofFloat(this.mView, View.TRANSLATION_Z, hasFocus ? this.mFocusedElevation : 0.0f);
        elevationAnimator.setDuration((long) this.mAnimationDuration);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(this.mView, View.SCALE_X, scale);
        scaleXAnimator.setDuration((long) this.mAnimationDuration);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(this.mView, View.SCALE_Y, scale);
        scaleYAnimator.setDuration((long) this.mAnimationDuration);
        this.mAnimator = new AnimatorSet();
        if (pivotAnimator != null) {
            this.mAnimator.playTogether(elevationAnimator, scaleXAnimator, scaleYAnimator, pivotAnimator);
        } else {
            this.mAnimator.playTogether(elevationAnimator, scaleXAnimator, scaleYAnimator);
        }
        this.mAnimatorListener = new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                ScaleFocusHandler.this.releaseAnimator();
            }
        };
        this.mAnimator.addListener(this.mAnimatorListener);
        this.mAnimator.start();
    }

    private void applyPivot() {
        int width = this.mView.getLayoutParams().width;
        int height = this.mView.getLayoutParams().height;
        int pivotX = 0;
        if (width <= 0 || height <= 0) {
            width = this.mView.getWidth();
            height = this.mView.getHeight();
            if (width <= 0 || height <= 0) {
                return;
            }
        }
        PivotProvider pivotProvider = this.mPivotProvider;
        if (pivotProvider != null) {
            this.mPivot = pivotProvider.getPivot();
        }
        int i = this.mPivot;
        if (i == 0) {
            pivotX = width / 2;
        } else if (i == 1) {
            if (this.mView.getLayoutDirection() == 1) {
                pivotX = width;
            } else {
                pivotX = 0;
            }
        }
        this.mView.setPivotX((float) pivotX);
        this.mView.setPivotY((float) ((height / 2) + this.mPivotVerticalShift));
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface Pivot {
    }

    public interface PivotProvider {
        int getPivot();

        boolean shouldAnimate();
    }
}
