package androidx.leanback.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import androidx.leanback.C0364R;

public final class ShadowOverlayHelper {
    public static final int SHADOW_DYNAMIC = 3;
    public static final int SHADOW_NONE = 1;
    public static final int SHADOW_STATIC = 2;
    float mFocusedZ;
    boolean mNeedsOverlay;
    boolean mNeedsRoundedCorner;
    boolean mNeedsShadow;
    boolean mNeedsWrapper;
    int mRoundedCornerRadius;
    int mShadowType = 1;
    float mUnfocusedZ;

    ShadowOverlayHelper() {
    }

    public static boolean supportsShadow() {
        return StaticShadowHelper.supportsShadow();
    }

    public static boolean supportsDynamicShadow() {
        return ShadowHelper.supportsDynamicShadow();
    }

    public static boolean supportsRoundedCorner() {
        return RoundedRectHelper.supportsRoundedCorner();
    }

    public static boolean supportsForeground() {
        return ForegroundHelper.supportsForeground();
    }

    public static void setNoneWrapperOverlayColor(View view, int color) {
        Drawable d = ForegroundHelper.getForeground(view);
        if (d instanceof ColorDrawable) {
            ((ColorDrawable) d).setColor(color);
        } else {
            ForegroundHelper.setForeground(view, new ColorDrawable(color));
        }
    }

    public static void setNoneWrapperShadowFocusLevel(View view, float level) {
        setShadowFocusLevel(getNoneWrapperDynamicShadowImpl(view), 3, level);
    }

    static Object getNoneWrapperDynamicShadowImpl(View view) {
        return view.getTag(C0364R.C0366id.lb_shadow_impl);
    }

    static void setShadowFocusLevel(Object impl, int shadowType, float level) {
        if (impl != null) {
            if (level < 0.0f) {
                level = 0.0f;
            } else if (level > 1.0f) {
                level = 1.0f;
            }
            if (shadowType == 2) {
                StaticShadowHelper.setShadowFocusLevel(impl, level);
            } else if (shadowType == 3) {
                ShadowHelper.setShadowFocusLevel(impl, level);
            }
        }
    }

    public void prepareParentForShadow(ViewGroup parent) {
        if (this.mShadowType == 2) {
            StaticShadowHelper.prepareParent(parent);
        }
    }

    public int getShadowType() {
        return this.mShadowType;
    }

    public boolean needsOverlay() {
        return this.mNeedsOverlay;
    }

    public boolean needsRoundedCorner() {
        return this.mNeedsRoundedCorner;
    }

    public boolean needsWrapper() {
        return this.mNeedsWrapper;
    }

    public ShadowOverlayContainer createShadowOverlayContainer(Context context) {
        if (needsWrapper()) {
            return new ShadowOverlayContainer(context, this.mShadowType, this.mNeedsOverlay, this.mUnfocusedZ, this.mFocusedZ, this.mRoundedCornerRadius);
        }
        throw new IllegalArgumentException();
    }

    public void setOverlayColor(View view, int color) {
        if (needsWrapper()) {
            ((ShadowOverlayContainer) view).setOverlayColor(color);
        } else {
            setNoneWrapperOverlayColor(view, color);
        }
    }

    public void onViewCreated(View view) {
        if (needsWrapper()) {
            return;
        }
        if (!this.mNeedsShadow) {
            if (this.mNeedsRoundedCorner) {
                RoundedRectHelper.setClipToRoundedOutline(view, true, this.mRoundedCornerRadius);
            }
        } else if (this.mShadowType == 3) {
            view.setTag(C0364R.C0366id.lb_shadow_impl, ShadowHelper.addDynamicShadow(view, this.mUnfocusedZ, this.mFocusedZ, this.mRoundedCornerRadius));
        } else if (this.mNeedsRoundedCorner) {
            RoundedRectHelper.setClipToRoundedOutline(view, true, this.mRoundedCornerRadius);
        }
    }

    public void setShadowFocusLevel(View view, float level) {
        if (needsWrapper()) {
            ((ShadowOverlayContainer) view).setShadowFocusLevel(level);
        } else {
            setShadowFocusLevel(getNoneWrapperDynamicShadowImpl(view), 3, level);
        }
    }

    /* access modifiers changed from: package-private */
    public void setupDynamicShadowZ(Options options, Context context) {
        if (options.getDynamicShadowUnfocusedZ() < 0.0f) {
            Resources res = context.getResources();
            this.mFocusedZ = res.getDimension(C0364R.dimen.lb_material_shadow_focused_z);
            this.mUnfocusedZ = res.getDimension(C0364R.dimen.lb_material_shadow_normal_z);
            return;
        }
        this.mFocusedZ = options.getDynamicShadowFocusedZ();
        this.mUnfocusedZ = options.getDynamicShadowUnfocusedZ();
    }

    /* access modifiers changed from: package-private */
    public void setupRoundedCornerRadius(Options options, Context context) {
        if (options.getRoundedCornerRadius() == 0) {
            this.mRoundedCornerRadius = context.getResources().getDimensionPixelSize(C0364R.dimen.lb_rounded_rect_corner_radius);
        } else {
            this.mRoundedCornerRadius = options.getRoundedCornerRadius();
        }
    }

    public static final class Builder {
        private boolean keepForegroundDrawable;
        private boolean needsOverlay;
        private boolean needsRoundedCorner;
        private boolean needsShadow;
        private Options options = Options.DEFAULT;
        private boolean preferZOrder = true;

        public Builder needsOverlay(boolean needsOverlay2) {
            this.needsOverlay = needsOverlay2;
            return this;
        }

        public Builder needsShadow(boolean needsShadow2) {
            this.needsShadow = needsShadow2;
            return this;
        }

        public Builder needsRoundedCorner(boolean needsRoundedCorner2) {
            this.needsRoundedCorner = needsRoundedCorner2;
            return this;
        }

        public Builder preferZOrder(boolean preferZOrder2) {
            this.preferZOrder = preferZOrder2;
            return this;
        }

        public Builder keepForegroundDrawable(boolean keepForegroundDrawable2) {
            this.keepForegroundDrawable = keepForegroundDrawable2;
            return this;
        }

        public Builder options(Options options2) {
            this.options = options2;
            return this;
        }

        public ShadowOverlayHelper build(Context context) {
            ShadowOverlayHelper helper = new ShadowOverlayHelper();
            helper.mNeedsOverlay = this.needsOverlay;
            boolean z = false;
            helper.mNeedsRoundedCorner = this.needsRoundedCorner && ShadowOverlayHelper.supportsRoundedCorner();
            helper.mNeedsShadow = this.needsShadow && ShadowOverlayHelper.supportsShadow();
            if (helper.mNeedsRoundedCorner) {
                helper.setupRoundedCornerRadius(this.options, context);
            }
            if (!helper.mNeedsShadow) {
                helper.mShadowType = 1;
                if ((!ShadowOverlayHelper.supportsForeground() || this.keepForegroundDrawable) && helper.mNeedsOverlay) {
                    z = true;
                }
                helper.mNeedsWrapper = z;
            } else if (!this.preferZOrder || !ShadowOverlayHelper.supportsDynamicShadow()) {
                helper.mShadowType = 2;
                helper.mNeedsWrapper = true;
            } else {
                helper.mShadowType = 3;
                helper.setupDynamicShadowZ(this.options, context);
                if ((!ShadowOverlayHelper.supportsForeground() || this.keepForegroundDrawable) && helper.mNeedsOverlay) {
                    z = true;
                }
                helper.mNeedsWrapper = z;
            }
            return helper;
        }
    }

    public static final class Options {
        public static final Options DEFAULT = new Options();
        private float dynamicShadowFocusedZ = -1.0f;
        private float dynamicShadowUnfocusedZ = -1.0f;
        private int roundedCornerRadius = 0;

        public Options roundedCornerRadius(int roundedCornerRadius2) {
            this.roundedCornerRadius = roundedCornerRadius2;
            return this;
        }

        public Options dynamicShadowZ(float unfocusedZ, float focusedZ) {
            this.dynamicShadowUnfocusedZ = unfocusedZ;
            this.dynamicShadowFocusedZ = focusedZ;
            return this;
        }

        public final int getRoundedCornerRadius() {
            return this.roundedCornerRadius;
        }

        public final float getDynamicShadowUnfocusedZ() {
            return this.dynamicShadowUnfocusedZ;
        }

        public final float getDynamicShadowFocusedZ() {
            return this.dynamicShadowFocusedZ;
        }
    }
}
