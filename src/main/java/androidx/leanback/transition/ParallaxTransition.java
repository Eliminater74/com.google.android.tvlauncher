package androidx.leanback.transition;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.leanback.C0364R;
import androidx.leanback.widget.Parallax;

@RequiresApi(21)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class ParallaxTransition extends Visibility {
    static Interpolator sInterpolator = new LinearInterpolator();

    public ParallaxTransition() {
    }

    public ParallaxTransition(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /* access modifiers changed from: package-private */
    public Animator createAnimator(View view) {
        final Parallax source = (Parallax) view.getTag(C0364R.C0366id.lb_parallax_source);
        if (source == null) {
            return null;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setInterpolator(sInterpolator);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                source.updateValues();
            }
        });
        return animator;
    }

    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        if (endValues == null) {
            return null;
        }
        return createAnimator(view);
    }

    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null) {
            return null;
        }
        return createAnimator(view);
    }
}
