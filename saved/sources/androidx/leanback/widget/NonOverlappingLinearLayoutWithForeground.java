package androidx.leanback.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

class NonOverlappingLinearLayoutWithForeground extends LinearLayout {
    private static final int VERSION_M = 23;
    private Drawable mForeground;
    private boolean mForegroundBoundsChanged;
    private final Rect mSelfBounds;

    public NonOverlappingLinearLayoutWithForeground(Context context) {
        this(context, null);
    }

    public NonOverlappingLinearLayoutWithForeground(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NonOverlappingLinearLayoutWithForeground(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mSelfBounds = new Rect();
        if (context.getApplicationInfo().targetSdkVersion < 23 || Build.VERSION.SDK_INT < 23) {
            TypedArray a = context.obtainStyledAttributes(attrs, new int[]{16843017});
            Drawable d = a.getDrawable(0);
            if (d != null) {
                setForegroundCompat(d);
            }
            a.recycle();
        }
    }

    public void setForegroundCompat(Drawable d) {
        if (Build.VERSION.SDK_INT >= 23) {
            ForegroundHelper.setForeground(this, d);
        } else if (this.mForeground != d) {
            this.mForeground = d;
            this.mForegroundBoundsChanged = true;
            setWillNotDraw(false);
            this.mForeground.setCallback(this);
            if (this.mForeground.isStateful()) {
                this.mForeground.setState(getDrawableState());
            }
        }
    }

    public Drawable getForegroundCompat() {
        if (Build.VERSION.SDK_INT >= 23) {
            return ForegroundHelper.getForeground(this);
        }
        return this.mForeground;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mForeground != null) {
            Drawable foreground = this.mForeground;
            if (this.mForegroundBoundsChanged) {
                this.mForegroundBoundsChanged = false;
                Rect selfBounds = this.mSelfBounds;
                selfBounds.set(0, 0, getRight() - getLeft(), getBottom() - getTop());
                foreground.setBounds(selfBounds);
            }
            foreground.draw(canvas);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.mForegroundBoundsChanged |= changed;
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == this.mForeground;
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mForeground;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mForeground;
        if (drawable != null && drawable.isStateful()) {
            this.mForeground.setState(getDrawableState());
        }
    }

    public boolean hasOverlappingRendering() {
        return false;
    }
}
