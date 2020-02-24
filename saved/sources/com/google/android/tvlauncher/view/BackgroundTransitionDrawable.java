package com.google.android.tvlauncher.view;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.SystemClock;
import android.util.Log;

public class BackgroundTransitionDrawable extends LayerDrawable {
    private static final String TAG = "BackgroundTransition";
    private int mAlpha = 255;
    private long mAnimateDuration;
    private long mAnimateStart;
    private boolean mAnimating = false;

    public BackgroundTransitionDrawable(Drawable[] layers) {
        super(layers);
        if (layers.length != 3) {
            int length = layers.length;
            StringBuilder sb = new StringBuilder(56);
            sb.append("Wrong number of layers: ");
            sb.append(length);
            sb.append(" given, should have 3");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public void animateFadeIn(long durationMs) {
        if (this.mAnimating) {
            Log.w(TAG, "animateFadeIn called while the previous animation is still running.");
        }
        this.mAnimateStart = SystemClock.uptimeMillis();
        this.mAnimateDuration = durationMs;
        this.mAnimating = true;
        flipBuffers();
    }

    public Drawable getBackBuffer() {
        return getDrawable(0);
    }

    public void draw(Canvas canvas) {
        if (this.mAnimating) {
            long time = SystemClock.uptimeMillis() - this.mAnimateStart;
            long j = this.mAnimateDuration;
            if (time >= j) {
                this.mAnimating = false;
                this.mAlpha = 255;
            } else {
                this.mAlpha = (int) ((255 * time) / j);
            }
        }
        if (this.mAlpha != 255) {
            getDrawable(1).setAlpha(255);
            getDrawable(1).draw(canvas);
        }
        if (this.mAlpha != 0) {
            getDrawable(2).setAlpha(this.mAlpha);
            getDrawable(2).draw(canvas);
        }
        if (this.mAnimating) {
            invalidateSelf();
        }
    }

    private void flipBuffers() {
        Drawable back = getDrawable(0);
        setDrawable(0, getDrawable(1));
        setDrawable(1, getDrawable(2));
        setDrawable(2, back);
        invalidateSelf();
    }
}
