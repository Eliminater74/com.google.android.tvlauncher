package androidx.leanback.transition;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.annotation.RequiresApi;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;

@RequiresApi(19)
class Scale extends Transition {
    private static final String PROPNAME_SCALE = "android:leanback:scale";

    private void captureValues(TransitionValues values) {
        values.values.put(PROPNAME_SCALE, Float.valueOf(values.view.getScaleX()));
    }

    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        float startScale = ((Float) startValues.values.get(PROPNAME_SCALE)).floatValue();
        float endScale = ((Float) endValues.values.get(PROPNAME_SCALE)).floatValue();
        final View view = startValues.view;
        view.setScaleX(startScale);
        view.setScaleY(startScale);
        ValueAnimator animator = ValueAnimator.ofFloat(startScale, endScale);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = ((Float) animation.getAnimatedValue()).floatValue();
                view.setScaleX(scale);
                view.setScaleY(scale);
            }
        });
        return animator;
    }
}
