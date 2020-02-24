package com.google.android.tvlauncher.home.operatorbackground;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p001v4.view.ViewCompat;

class BitmapBackgroundTransitionDrawable extends Drawable {
    private int mAlpha = 255;
    private long mAnimateDuration;
    private long mAnimateStart;
    private boolean mAnimating = false;
    private Bitmap mBitmap;
    private Paint mPaint;

    BitmapBackgroundTransitionDrawable() {
    }

    /* access modifiers changed from: package-private */
    public void animateFadeIn(Bitmap bitmap, long durationMs) {
        this.mBitmap = bitmap;
        this.mAnimateStart = SystemClock.uptimeMillis();
        this.mAnimateDuration = durationMs;
        this.mAnimating = true;
        this.mPaint = new Paint();
    }

    public void draw(@NonNull Canvas canvas) {
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
        if (this.mAlpha != 255 || this.mBitmap == null) {
            canvas.drawColor((int) ViewCompat.MEASURED_STATE_MASK);
        }
        int i = this.mAlpha;
        if (!(i == 0 || this.mBitmap == null)) {
            this.mPaint.setAlpha(i);
            canvas.drawBitmap(this.mBitmap, 0.0f, 0.0f, this.mPaint);
        }
        if (this.mAnimating) {
            invalidateSelf();
        }
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(@Nullable ColorFilter colorFilter) {
    }

    public int getOpacity() {
        return -1;
    }
}
