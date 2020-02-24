package androidx.leanback.graphics;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.view.View;
import androidx.leanback.C0364R;

public final class ColorFilterDimmer {
    private final float mActiveLevel;
    private final ColorFilterCache mColorDimmer;
    private final float mDimmedLevel;
    private ColorFilter mFilter;
    private final Paint mPaint;

    public static ColorFilterDimmer createDefault(Context context) {
        TypedArray a = context.obtainStyledAttributes(C0364R.styleable.LeanbackTheme);
        int dimColor = a.getColor(C0364R.styleable.LeanbackTheme_overlayDimMaskColor, context.getResources().getColor(C0364R.color.lb_view_dim_mask_color));
        float activeLevel = a.getFraction(C0364R.styleable.LeanbackTheme_overlayDimActiveLevel, 1, 1, context.getResources().getFraction(C0364R.fraction.lb_view_active_level, 1, 0));
        float dimmedLevel = a.getFraction(C0364R.styleable.LeanbackTheme_overlayDimDimmedLevel, 1, 1, context.getResources().getFraction(C0364R.fraction.lb_view_dimmed_level, 1, 1));
        a.recycle();
        return new ColorFilterDimmer(ColorFilterCache.getColorFilterCache(dimColor), activeLevel, dimmedLevel);
    }

    public static ColorFilterDimmer create(ColorFilterCache dimmer, float activeLevel, float dimmedLevel) {
        return new ColorFilterDimmer(dimmer, activeLevel, dimmedLevel);
    }

    private ColorFilterDimmer(ColorFilterCache dimmer, float activeLevel, float dimmedLevel) {
        this.mColorDimmer = dimmer;
        activeLevel = activeLevel > 1.0f ? 1.0f : activeLevel;
        activeLevel = activeLevel < 0.0f ? 0.0f : activeLevel;
        dimmedLevel = dimmedLevel > 1.0f ? 1.0f : dimmedLevel;
        dimmedLevel = dimmedLevel < 0.0f ? 0.0f : dimmedLevel;
        this.mActiveLevel = activeLevel;
        this.mDimmedLevel = dimmedLevel;
        this.mPaint = new Paint();
    }

    public void applyFilterToView(View view) {
        if (this.mFilter != null) {
            view.setLayerType(2, this.mPaint);
        } else {
            view.setLayerType(0, null);
        }
        view.invalidate();
    }

    public void setActiveLevel(float level) {
        if (level < 0.0f) {
            level = 0.0f;
        }
        if (level > 1.0f) {
            level = 1.0f;
        }
        ColorFilterCache colorFilterCache = this.mColorDimmer;
        float f = this.mDimmedLevel;
        this.mFilter = colorFilterCache.getFilterForLevel(f + ((this.mActiveLevel - f) * level));
        this.mPaint.setColorFilter(this.mFilter);
    }

    public ColorFilter getColorFilter() {
        return this.mFilter;
    }

    public Paint getPaint() {
        return this.mPaint;
    }
}
