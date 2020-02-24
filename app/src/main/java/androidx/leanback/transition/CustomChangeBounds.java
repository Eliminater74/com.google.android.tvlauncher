package androidx.leanback.transition;

import android.animation.Animator;
import android.support.annotation.RequiresApi;
import android.transition.ChangeBounds;
import android.transition.TransitionValues;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import java.util.HashMap;

@RequiresApi(19)
class CustomChangeBounds extends ChangeBounds {
    final HashMap<String, Integer> mClassStartDelays = new HashMap<>();
    int mDefaultStartDelay;
    final SparseIntArray mIdStartDelays = new SparseIntArray();
    final HashMap<View, Integer> mViewStartDelays = new HashMap<>();

    CustomChangeBounds() {
    }

    private int getDelay(View view) {
        Integer delay = this.mViewStartDelays.get(view);
        if (delay != null) {
            return delay.intValue();
        }
        int idStartDelay = this.mIdStartDelays.get(view.getId(), -1);
        if (idStartDelay != -1) {
            return idStartDelay;
        }
        Integer delay2 = this.mClassStartDelays.get(view.getClass().getName());
        if (delay2 != null) {
            return delay2.intValue();
        }
        return this.mDefaultStartDelay;
    }

    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        Animator animator = super.createAnimator(sceneRoot, startValues, endValues);
        if (!(animator == null || endValues == null || endValues.view == null)) {
            animator.setStartDelay((long) getDelay(endValues.view));
        }
        return animator;
    }

    public void setStartDelay(View view, int startDelay) {
        this.mViewStartDelays.put(view, Integer.valueOf(startDelay));
    }

    public void setStartDelay(int viewId, int startDelay) {
        this.mIdStartDelays.put(viewId, startDelay);
    }

    public void setStartDelay(String className, int startDelay) {
        this.mClassStartDelays.put(className, Integer.valueOf(startDelay));
    }

    public void setDefaultStartDelay(int startDelay) {
        this.mDefaultStartDelay = startDelay;
    }
}
