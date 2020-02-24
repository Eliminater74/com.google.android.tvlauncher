package androidx.leanback.graphics;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import androidx.leanback.C0364R;

public final class ColorOverlayDimmer {
    private final float mActiveLevel;
    private int mAlpha;
    private float mAlphaFloat;
    private final float mDimmedLevel;
    private final Paint mPaint;

    public static ColorOverlayDimmer createDefault(Context context) {
        TypedArray a = context.obtainStyledAttributes(C0364R.styleable.LeanbackTheme);
        int dimColor = a.getColor(C0364R.styleable.LeanbackTheme_overlayDimMaskColor, context.getResources().getColor(C0364R.color.lb_view_dim_mask_color));
        float activeLevel = a.getFraction(C0364R.styleable.LeanbackTheme_overlayDimActiveLevel, 1, 1, context.getResources().getFraction(C0364R.fraction.lb_view_active_level, 1, 0));
        float dimmedLevel = a.getFraction(C0364R.styleable.LeanbackTheme_overlayDimDimmedLevel, 1, 1, context.getResources().getFraction(C0364R.fraction.lb_view_dimmed_level, 1, 1));
        a.recycle();
        return new ColorOverlayDimmer(dimColor, activeLevel, dimmedLevel);
    }

    public static ColorOverlayDimmer createColorOverlayDimmer(int dimColor, float activeLevel, float dimmedLevel) {
        return new ColorOverlayDimmer(dimColor, activeLevel, dimmedLevel);
    }

    private ColorOverlayDimmer(int dimColor, float activeLevel, float dimmedLevel) {
        activeLevel = activeLevel > 1.0f ? 1.0f : activeLevel;
        activeLevel = activeLevel < 0.0f ? 0.0f : activeLevel;
        dimmedLevel = dimmedLevel > 1.0f ? 1.0f : dimmedLevel;
        dimmedLevel = dimmedLevel < 0.0f ? 0.0f : dimmedLevel;
        this.mPaint = new Paint();
        this.mPaint.setColor(Color.rgb(Color.red(dimColor), Color.green(dimColor), Color.blue(dimColor)));
        this.mActiveLevel = activeLevel;
        this.mDimmedLevel = dimmedLevel;
        setActiveLevel(1.0f);
    }

    public void setActiveLevel(float level) {
        float f = this.mDimmedLevel;
        this.mAlphaFloat = f + ((this.mActiveLevel - f) * level);
        this.mAlpha = (int) (this.mAlphaFloat * 255.0f);
        this.mPaint.setAlpha(this.mAlpha);
    }

    public boolean needsDraw() {
        return this.mAlpha != 0;
    }

    public int getAlpha() {
        return this.mAlpha;
    }

    public float getAlphaFloat() {
        return this.mAlphaFloat;
    }

    public Paint getPaint() {
        return this.mPaint;
    }

    public int applyToColor(int color) {
        float f = 1.0f - this.mAlphaFloat;
        return Color.argb(Color.alpha(color), (int) (((float) Color.red(color)) * f), (int) (((float) Color.green(color)) * f), (int) (((float) Color.blue(color)) * f));
    }

    public void drawColorOverlay(Canvas c, View v, boolean includePadding) {
        c.save();
        float dx = ((float) v.getLeft()) + v.getTranslationX();
        float dy = ((float) v.getTop()) + v.getTranslationY();
        c.translate(dx, dy);
        c.concat(v.getMatrix());
        c.translate(-dx, -dy);
        if (includePadding) {
            c.drawRect((float) v.getLeft(), (float) v.getTop(), (float) v.getRight(), (float) v.getBottom(), this.mPaint);
        } else {
            c.drawRect((float) (v.getLeft() + v.getPaddingLeft()), (float) (v.getTop() + v.getPaddingTop()), (float) (v.getRight() - v.getPaddingRight()), (float) (v.getBottom() - v.getPaddingBottom()), this.mPaint);
        }
        c.restore();
    }
}
