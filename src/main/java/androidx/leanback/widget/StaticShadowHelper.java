package androidx.leanback.widget;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.leanback.C0364R;

final class StaticShadowHelper {
    private StaticShadowHelper() {
    }

    static boolean supportsShadow() {
        return Build.VERSION.SDK_INT >= 21;
    }

    static void prepareParent(ViewGroup parent) {
        if (Build.VERSION.SDK_INT >= 21) {
            parent.setLayoutMode(1);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    static Object addStaticShadow(ViewGroup shadowContainer) {
        if (Build.VERSION.SDK_INT < 21) {
            return null;
        }
        shadowContainer.setLayoutMode(1);
        LayoutInflater.from(shadowContainer.getContext()).inflate(C0364R.layout.lb_shadow, shadowContainer, true);
        ShadowImpl impl = new ShadowImpl();
        impl.mNormalShadow = shadowContainer.findViewById(C0364R.C0366id.lb_shadow_normal);
        impl.mFocusShadow = shadowContainer.findViewById(C0364R.C0366id.lb_shadow_focused);
        return impl;
    }

    static void setShadowFocusLevel(Object impl, float level) {
        if (Build.VERSION.SDK_INT >= 21) {
            ShadowImpl shadowImpl = (ShadowImpl) impl;
            shadowImpl.mNormalShadow.setAlpha(1.0f - level);
            shadowImpl.mFocusShadow.setAlpha(level);
        }
    }

    static class ShadowImpl {
        View mFocusShadow;
        View mNormalShadow;

        ShadowImpl() {
        }
    }
}
