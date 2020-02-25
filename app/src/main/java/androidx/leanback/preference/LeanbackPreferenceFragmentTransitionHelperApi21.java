package androidx.leanback.preference;

import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.p001v4.view.GravityCompat;
import android.transition.Transition;

import androidx.leanback.transition.FadeAndShortSlide;

@RequiresApi(21)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class LeanbackPreferenceFragmentTransitionHelperApi21 {
    private LeanbackPreferenceFragmentTransitionHelperApi21() {
    }

    public static void addTransitions(Fragment f) {
        Transition transitionStartEdge = new FadeAndShortSlide(GravityCompat.START);
        Transition transitionEndEdge = new FadeAndShortSlide(GravityCompat.END);
        f.setEnterTransition(transitionEndEdge);
        f.setExitTransition(transitionStartEdge);
        f.setReenterTransition(transitionStartEdge);
        f.setReturnTransition(transitionEndEdge);
    }

    static void addTransitions(android.support.p001v4.app.Fragment f) {
        Transition transitionStartEdge = new FadeAndShortSlide(GravityCompat.START);
        Transition transitionEndEdge = new FadeAndShortSlide(GravityCompat.END);
        f.setEnterTransition(transitionEndEdge);
        f.setExitTransition(transitionStartEdge);
        f.setReenterTransition(transitionStartEdge);
        f.setReturnTransition(transitionEndEdge);
    }
}
