package androidx.leanback.widget;

import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.RestrictTo;
import android.util.TypedValue;

import androidx.leanback.C0364R;
import androidx.leanback.graphics.CompositeDrawable;
import androidx.leanback.graphics.FitWidthBitmapDrawable;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public class DetailsParallaxDrawable extends CompositeDrawable {
    private Drawable mBottomDrawable;

    public DetailsParallaxDrawable(Context context, DetailsParallax parallax, Drawable coverDrawable, ParallaxTarget coverDrawableParallaxTarget) {
        init(context, parallax, coverDrawable, new ColorDrawable(), coverDrawableParallaxTarget);
    }

    public DetailsParallaxDrawable(Context context, DetailsParallax parallax, Drawable coverDrawable, Drawable bottomDrawable, ParallaxTarget coverDrawableParallaxTarget) {
        init(context, parallax, coverDrawable, bottomDrawable, coverDrawableParallaxTarget);
    }

    public DetailsParallaxDrawable(Context context, DetailsParallax parallax) {
        FitWidthBitmapDrawable fitWidthBitmapDrawable = new FitWidthBitmapDrawable();
        Context context2 = context;
        DetailsParallax detailsParallax = parallax;
        FitWidthBitmapDrawable fitWidthBitmapDrawable2 = fitWidthBitmapDrawable;
        init(context2, detailsParallax, fitWidthBitmapDrawable2, new ColorDrawable(), new ParallaxTarget.PropertyValuesHolderTarget(fitWidthBitmapDrawable, PropertyValuesHolder.ofInt("verticalOffset", 0, -context.getResources().getDimensionPixelSize(C0364R.dimen.lb_details_cover_drawable_parallax_movement))));
    }

    private static int getDefaultBackgroundColor(Context context) {
        TypedValue outValue = new TypedValue();
        if (context.getTheme().resolveAttribute(C0364R.attr.defaultBrandColorDark, outValue, true)) {
            return context.getResources().getColor(outValue.resourceId);
        }
        return context.getResources().getColor(C0364R.color.lb_default_brand_color_dark);
    }

    /* access modifiers changed from: package-private */
    public void init(Context context, DetailsParallax parallax, Drawable coverDrawable, Drawable bottomDrawable, ParallaxTarget coverDrawableParallaxTarget) {
        if (bottomDrawable instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) bottomDrawable;
            if (colorDrawable.getColor() == 0) {
                colorDrawable.setColor(getDefaultBackgroundColor(context));
            }
        }
        addChildDrawable(coverDrawable);
        this.mBottomDrawable = bottomDrawable;
        addChildDrawable(bottomDrawable);
        connect(context, parallax, coverDrawableParallaxTarget);
    }

    public Drawable getCoverDrawable() {
        return getChildAt(0).getDrawable();
    }

    public Drawable getBottomDrawable() {
        return this.mBottomDrawable;
    }

    @ColorInt
    public int getSolidColor() {
        return ((ColorDrawable) this.mBottomDrawable).getColor();
    }

    public void setSolidColor(@ColorInt int color) {
        ((ColorDrawable) this.mBottomDrawable).setColor(color);
    }

    /* access modifiers changed from: package-private */
    public void connect(Context context, DetailsParallax parallax, ParallaxTarget coverDrawableParallaxTarget) {
        Parallax.IntProperty frameTop = parallax.getOverviewRowTop();
        Parallax.IntProperty frameBottom = parallax.getOverviewRowBottom();
        parallax.addEffect(frameTop.atAbsolute(context.getResources().getDimensionPixelSize(C0364R.dimen.lb_details_v2_align_pos_for_actions)), frameTop.atAbsolute(context.getResources().getDimensionPixelSize(C0364R.dimen.lb_details_v2_align_pos_for_description))).target(coverDrawableParallaxTarget);
        parallax.addEffect(frameBottom.atMax(), frameBottom.atMin()).target(getChildAt(1), CompositeDrawable.ChildDrawable.TOP_ABSOLUTE);
        parallax.addEffect(frameTop.atMax(), frameTop.atMin()).target(getChildAt(0), CompositeDrawable.ChildDrawable.BOTTOM_ABSOLUTE);
    }
}
