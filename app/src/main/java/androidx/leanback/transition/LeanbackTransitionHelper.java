package androidx.leanback.transition;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RestrictTo;
import android.view.animation.AnimationUtils;

import androidx.leanback.C0364R;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class LeanbackTransitionHelper {
    private LeanbackTransitionHelper() {
    }

    public static Object loadTitleInTransition(Context context) {
        if (Build.VERSION.SDK_INT < 19 || Build.VERSION.SDK_INT >= 21) {
            return TransitionHelper.loadTransition(context, C0364R.C0367transition.lb_title_in);
        }
        SlideKitkat slide = new SlideKitkat();
        slide.setSlideEdge(48);
        slide.setInterpolator(AnimationUtils.loadInterpolator(context, 17432582));
        slide.addTarget(C0364R.C0366id.browse_title_group);
        return slide;
    }

    public static Object loadTitleOutTransition(Context context) {
        if (Build.VERSION.SDK_INT < 19 || Build.VERSION.SDK_INT >= 21) {
            return TransitionHelper.loadTransition(context, C0364R.C0367transition.lb_title_out);
        }
        SlideKitkat slide = new SlideKitkat();
        slide.setSlideEdge(48);
        slide.setInterpolator(AnimationUtils.loadInterpolator(context, C0364R.anim.lb_decelerator_4));
        slide.addTarget(C0364R.C0366id.browse_title_group);
        return slide;
    }
}
