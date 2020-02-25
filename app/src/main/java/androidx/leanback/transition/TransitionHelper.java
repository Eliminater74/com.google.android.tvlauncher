package androidx.leanback.transition;

import android.animation.TimeInterpolator;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RestrictTo;
import android.transition.AutoTransition;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public final class TransitionHelper {
    public static final int FADE_IN = 1;
    public static final int FADE_OUT = 2;

    private TransitionHelper() {
    }

    public static boolean systemSupportsEntranceTransitions() {
        return Build.VERSION.SDK_INT >= 21;
    }

    public static Object getSharedElementEnterTransition(Window window) {
        if (Build.VERSION.SDK_INT >= 21) {
            return window.getSharedElementEnterTransition();
        }
        return null;
    }

    public static void setSharedElementEnterTransition(Window window, Object transition) {
        if (Build.VERSION.SDK_INT >= 21) {
            window.setSharedElementEnterTransition((Transition) transition);
        }
    }

    public static Object getSharedElementReturnTransition(Window window) {
        if (Build.VERSION.SDK_INT >= 21) {
            return window.getSharedElementReturnTransition();
        }
        return null;
    }

    public static void setSharedElementReturnTransition(Window window, Object transition) {
        if (Build.VERSION.SDK_INT >= 21) {
            window.setSharedElementReturnTransition((Transition) transition);
        }
    }

    public static Object getSharedElementExitTransition(Window window) {
        if (Build.VERSION.SDK_INT >= 21) {
            return window.getSharedElementExitTransition();
        }
        return null;
    }

    public static Object getSharedElementReenterTransition(Window window) {
        if (Build.VERSION.SDK_INT >= 21) {
            return window.getSharedElementReenterTransition();
        }
        return null;
    }

    public static Object getEnterTransition(Window window) {
        if (Build.VERSION.SDK_INT >= 21) {
            return window.getEnterTransition();
        }
        return null;
    }

    public static void setEnterTransition(Window window, Object transition) {
        if (Build.VERSION.SDK_INT >= 21) {
            window.setEnterTransition((Transition) transition);
        }
    }

    public static Object getReturnTransition(Window window) {
        if (Build.VERSION.SDK_INT >= 21) {
            return window.getReturnTransition();
        }
        return null;
    }

    public static void setReturnTransition(Window window, Object transition) {
        if (Build.VERSION.SDK_INT >= 21) {
            window.setReturnTransition((Transition) transition);
        }
    }

    public static Object getExitTransition(Window window) {
        if (Build.VERSION.SDK_INT >= 21) {
            return window.getExitTransition();
        }
        return null;
    }

    public static Object getReenterTransition(Window window) {
        if (Build.VERSION.SDK_INT >= 21) {
            return window.getReenterTransition();
        }
        return null;
    }

    public static Object createScene(ViewGroup sceneRoot, Runnable r) {
        if (Build.VERSION.SDK_INT < 19) {
            return r;
        }
        Scene scene = new Scene(sceneRoot);
        scene.setEnterAction(r);
        return scene;
    }

    public static Object createChangeBounds(boolean reparent) {
        if (Build.VERSION.SDK_INT < 19) {
            return new TransitionStub();
        }
        CustomChangeBounds changeBounds = new CustomChangeBounds();
        changeBounds.setReparent(reparent);
        return changeBounds;
    }

    public static Object createChangeTransform() {
        if (Build.VERSION.SDK_INT >= 21) {
            return new ChangeTransform();
        }
        return new TransitionStub();
    }

    public static void setChangeBoundsStartDelay(Object changeBounds, View view, int startDelay) {
        if (Build.VERSION.SDK_INT >= 19) {
            ((CustomChangeBounds) changeBounds).setStartDelay(view, startDelay);
        }
    }

    public static void setChangeBoundsStartDelay(Object changeBounds, int viewId, int startDelay) {
        if (Build.VERSION.SDK_INT >= 19) {
            ((CustomChangeBounds) changeBounds).setStartDelay(viewId, startDelay);
        }
    }

    public static void setChangeBoundsStartDelay(Object changeBounds, String className, int startDelay) {
        if (Build.VERSION.SDK_INT >= 19) {
            ((CustomChangeBounds) changeBounds).setStartDelay(className, startDelay);
        }
    }

    public static void setChangeBoundsDefaultStartDelay(Object changeBounds, int startDelay) {
        if (Build.VERSION.SDK_INT >= 19) {
            ((CustomChangeBounds) changeBounds).setDefaultStartDelay(startDelay);
        }
    }

    public static Object createTransitionSet(boolean sequential) {
        if (Build.VERSION.SDK_INT < 19) {
            return new TransitionStub();
        }
        TransitionSet set = new TransitionSet();
        set.setOrdering(sequential);
        return set;
    }

    public static Object createSlide(int slideEdge) {
        if (Build.VERSION.SDK_INT < 19) {
            return new TransitionStub();
        }
        SlideKitkat slide = new SlideKitkat();
        slide.setSlideEdge(slideEdge);
        return slide;
    }

    public static Object createScale() {
        if (Build.VERSION.SDK_INT >= 21) {
            return new ChangeTransform();
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return new Scale();
        }
        return new TransitionStub();
    }

    public static void addTransition(Object transitionSet, Object transition) {
        if (Build.VERSION.SDK_INT >= 19) {
            ((TransitionSet) transitionSet).addTransition((Transition) transition);
        }
    }

    public static void exclude(Object transition, int targetId, boolean exclude) {
        if (Build.VERSION.SDK_INT >= 19) {
            ((Transition) transition).excludeTarget(targetId, exclude);
        }
    }

    public static void exclude(Object transition, View targetView, boolean exclude) {
        if (Build.VERSION.SDK_INT >= 19) {
            ((Transition) transition).excludeTarget(targetView, exclude);
        }
    }

    public static void excludeChildren(Object transition, int targetId, boolean exclude) {
        if (Build.VERSION.SDK_INT >= 19) {
            ((Transition) transition).excludeChildren(targetId, exclude);
        }
    }

    public static void excludeChildren(Object transition, View targetView, boolean exclude) {
        if (Build.VERSION.SDK_INT >= 19) {
            ((Transition) transition).excludeChildren(targetView, exclude);
        }
    }

    public static void include(Object transition, int targetId) {
        if (Build.VERSION.SDK_INT >= 19) {
            ((Transition) transition).addTarget(targetId);
        }
    }

    public static void include(Object transition, View targetView) {
        if (Build.VERSION.SDK_INT >= 19) {
            ((Transition) transition).addTarget(targetView);
        }
    }

    public static void setStartDelay(Object transition, long startDelay) {
        if (Build.VERSION.SDK_INT >= 19) {
            ((Transition) transition).setStartDelay(startDelay);
        }
    }

    public static void setDuration(Object transition, long duration) {
        if (Build.VERSION.SDK_INT >= 19) {
            ((Transition) transition).setDuration(duration);
        }
    }

    public static Object createAutoTransition() {
        if (Build.VERSION.SDK_INT >= 19) {
            return new AutoTransition();
        }
        return new TransitionStub();
    }

    public static Object createFadeTransition(int fadeMode) {
        if (Build.VERSION.SDK_INT >= 19) {
            return new Fade(fadeMode);
        }
        return new TransitionStub();
    }

    public static void addTransitionListener(Object transition, final TransitionListener listener) {
        if (listener != null) {
            if (Build.VERSION.SDK_INT >= 19) {
                listener.mImpl = new Transition.TransitionListener() {
                    public void onTransitionStart(Transition transition11) {
                        listener.onTransitionStart(transition11);
                    }

                    public void onTransitionResume(Transition transition11) {
                        listener.onTransitionResume(transition11);
                    }

                    public void onTransitionPause(Transition transition11) {
                        listener.onTransitionPause(transition11);
                    }

                    public void onTransitionEnd(Transition transition11) {
                        listener.onTransitionEnd(transition11);
                    }

                    public void onTransitionCancel(Transition transition11) {
                        listener.onTransitionCancel(transition11);
                    }
                };
                ((Transition) transition).addListener((Transition.TransitionListener) listener.mImpl);
                return;
            }
            TransitionStub stub = (TransitionStub) transition;
            if (stub.mTransitionListeners == null) {
                stub.mTransitionListeners = new ArrayList<>();
            }
            stub.mTransitionListeners.add(listener);
        }
    }

    public static void removeTransitionListener(Object transition, TransitionListener listener) {
        if (Build.VERSION.SDK_INT < 19) {
            TransitionStub stub = (TransitionStub) transition;
            if (stub.mTransitionListeners != null) {
                stub.mTransitionListeners.remove(listener);
            }
        } else if (listener != null && listener.mImpl != null) {
            ((Transition) transition).removeListener((Transition.TransitionListener) listener.mImpl);
            listener.mImpl = null;
        }
    }

    public static void runTransition(Object scene, Object transition) {
        if (Build.VERSION.SDK_INT >= 19) {
            TransitionManager.go((Scene) scene, (Transition) transition);
            return;
        }
        TransitionStub transitionStub = (TransitionStub) transition;
        if (!(transitionStub == null || transitionStub.mTransitionListeners == null)) {
            int size = transitionStub.mTransitionListeners.size();
            for (int i = 0; i < size; i++) {
                transitionStub.mTransitionListeners.get(i).onTransitionStart(transition);
            }
        }
        Runnable r = (Runnable) scene;
        if (r != null) {
            r.run();
        }
        if (transitionStub != null && transitionStub.mTransitionListeners != null) {
            int size2 = transitionStub.mTransitionListeners.size();
            for (int i2 = 0; i2 < size2; i2++) {
                transitionStub.mTransitionListeners.get(i2).onTransitionEnd(transition);
            }
        }
    }

    public static void setInterpolator(Object transition, Object timeInterpolator) {
        if (Build.VERSION.SDK_INT >= 19) {
            ((Transition) transition).setInterpolator((TimeInterpolator) timeInterpolator);
        }
    }

    public static void addTarget(Object transition, View view) {
        if (Build.VERSION.SDK_INT >= 19) {
            ((Transition) transition).addTarget(view);
        }
    }

    public static Object createDefaultInterpolator(Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            return AnimationUtils.loadInterpolator(context, AndroidResources.FAST_OUT_LINEAR_IN);
        }
        return null;
    }

    public static Object loadTransition(Context context, int resId) {
        if (Build.VERSION.SDK_INT >= 19) {
            return TransitionInflater.from(context).inflateTransition(resId);
        }
        return new TransitionStub();
    }

    public static void setEnterTransition(Fragment fragment, Object transition) {
        if (Build.VERSION.SDK_INT >= 21) {
            fragment.setEnterTransition((Transition) transition);
        }
    }

    public static void setExitTransition(Fragment fragment, Object transition) {
        if (Build.VERSION.SDK_INT >= 21) {
            fragment.setExitTransition((Transition) transition);
        }
    }

    public static void setSharedElementEnterTransition(Fragment fragment, Object transition) {
        if (Build.VERSION.SDK_INT >= 21) {
            fragment.setSharedElementEnterTransition((Transition) transition);
        }
    }

    public static void addSharedElement(FragmentTransaction ft, View view, String transitionName) {
        if (Build.VERSION.SDK_INT >= 21) {
            ft.addSharedElement(view, transitionName);
        }
    }

    public static Object createFadeAndShortSlide(int edge) {
        if (Build.VERSION.SDK_INT >= 21) {
            return new FadeAndShortSlide(edge);
        }
        return new TransitionStub();
    }

    public static Object createFadeAndShortSlide(int edge, float distance) {
        if (Build.VERSION.SDK_INT < 21) {
            return new TransitionStub();
        }
        FadeAndShortSlide slide = new FadeAndShortSlide(edge);
        slide.setDistance(distance);
        return slide;
    }

    public static void beginDelayedTransition(ViewGroup sceneRoot, Object transitionObject) {
        if (Build.VERSION.SDK_INT >= 21) {
            TransitionManager.beginDelayedTransition(sceneRoot, (Transition) transitionObject);
        }
    }

    public static void setTransitionGroup(ViewGroup viewGroup, boolean transitionGroup) {
        if (Build.VERSION.SDK_INT >= 21) {
            viewGroup.setTransitionGroup(transitionGroup);
        }
    }

    public static void setEpicenterCallback(Object transition, final TransitionEpicenterCallback callback) {
        if (Build.VERSION.SDK_INT < 21) {
            return;
        }
        if (callback == null) {
            ((Transition) transition).setEpicenterCallback(null);
        } else {
            ((Transition) transition).setEpicenterCallback(new Transition.EpicenterCallback() {
                public Rect onGetEpicenter(Transition transition11) {
                    return callback.onGetEpicenter(transition11);
                }
            });
        }
    }

    private static class TransitionStub {
        ArrayList<TransitionListener> mTransitionListeners;

        TransitionStub() {
        }
    }
}
