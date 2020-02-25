package androidx.leanback.widget;

import android.graphics.Outline;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewOutlineProvider;

@RequiresApi(21)
class ShadowHelperApi21 {
    static final ViewOutlineProvider sOutlineProvider = new ViewOutlineProvider() {
        public void getOutline(View view, Outline outline) {
            outline.setRect(0, 0, view.getWidth(), view.getHeight());
            outline.setAlpha(1.0f);
        }
    };

    private ShadowHelperApi21() {
    }

    public static Object addDynamicShadow(View shadowContainer, float unfocusedZ, float focusedZ, int roundedCornerRadius) {
        if (roundedCornerRadius > 0) {
            RoundedRectHelperApi21.setClipToRoundedOutline(shadowContainer, true, roundedCornerRadius);
        } else {
            shadowContainer.setOutlineProvider(sOutlineProvider);
        }
        ShadowImpl impl = new ShadowImpl();
        impl.mShadowContainer = shadowContainer;
        impl.mNormalZ = unfocusedZ;
        impl.mFocusedZ = focusedZ;
        shadowContainer.setZ(impl.mNormalZ);
        return impl;
    }

    public static void setShadowFocusLevel(Object object, float level) {
        ShadowImpl impl = (ShadowImpl) object;
        impl.mShadowContainer.setZ(impl.mNormalZ + ((impl.mFocusedZ - impl.mNormalZ) * level));
    }

    static class ShadowImpl {
        float mFocusedZ;
        float mNormalZ;
        View mShadowContainer;

        ShadowImpl() {
        }
    }
}
