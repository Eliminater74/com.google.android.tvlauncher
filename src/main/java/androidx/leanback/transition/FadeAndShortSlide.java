package androidx.leanback.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.p001v4.view.GravityCompat;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import androidx.leanback.C0364R;

@RequiresApi(21)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class FadeAndShortSlide extends Visibility {
    private static final String PROPNAME_SCREEN_POSITION = "android:fadeAndShortSlideTransition:screenPosition";
    static final CalculateSlide sCalculateBottom = new CalculateSlide() {
        public float getGoneY(FadeAndShortSlide t, ViewGroup sceneRoot, View view, int[] position) {
            return view.getTranslationY() + t.getVerticalDistance(sceneRoot);
        }
    };
    static final CalculateSlide sCalculateEnd = new CalculateSlide() {
        public float getGoneX(FadeAndShortSlide t, ViewGroup sceneRoot, View view, int[] position) {
            boolean isRtl = true;
            if (sceneRoot.getLayoutDirection() != 1) {
                isRtl = false;
            }
            if (isRtl) {
                return view.getTranslationX() - t.getHorizontalDistance(sceneRoot);
            }
            return view.getTranslationX() + t.getHorizontalDistance(sceneRoot);
        }
    };
    static final CalculateSlide sCalculateStart = new CalculateSlide() {
        public float getGoneX(FadeAndShortSlide t, ViewGroup sceneRoot, View view, int[] position) {
            boolean isRtl = true;
            if (sceneRoot.getLayoutDirection() != 1) {
                isRtl = false;
            }
            if (isRtl) {
                return view.getTranslationX() + t.getHorizontalDistance(sceneRoot);
            }
            return view.getTranslationX() - t.getHorizontalDistance(sceneRoot);
        }
    };
    static final CalculateSlide sCalculateStartEnd = new CalculateSlide() {
        public float getGoneX(FadeAndShortSlide t, ViewGroup sceneRoot, View view, int[] position) {
            int sceneRootCenter;
            int viewCenter = position[0] + (view.getWidth() / 2);
            sceneRoot.getLocationOnScreen(position);
            Rect center = t.getEpicenter();
            if (center == null) {
                sceneRootCenter = position[0] + (sceneRoot.getWidth() / 2);
            } else {
                sceneRootCenter = center.centerX();
            }
            if (viewCenter < sceneRootCenter) {
                return view.getTranslationX() - t.getHorizontalDistance(sceneRoot);
            }
            return view.getTranslationX() + t.getHorizontalDistance(sceneRoot);
        }
    };
    static final CalculateSlide sCalculateTop = new CalculateSlide() {
        public float getGoneY(FadeAndShortSlide t, ViewGroup sceneRoot, View view, int[] position) {
            return view.getTranslationY() - t.getVerticalDistance(sceneRoot);
        }
    };
    private static final TimeInterpolator sDecelerate = new DecelerateInterpolator();
    private float mDistance;
    private Visibility mFade;
    private CalculateSlide mSlideCalculator;
    final CalculateSlide sCalculateTopBottom;

    private static abstract class CalculateSlide {
        CalculateSlide() {
        }

        /* access modifiers changed from: package-private */
        public float getGoneX(FadeAndShortSlide t, ViewGroup sceneRoot, View view, int[] position) {
            return view.getTranslationX();
        }

        /* access modifiers changed from: package-private */
        public float getGoneY(FadeAndShortSlide t, ViewGroup sceneRoot, View view, int[] position) {
            return view.getTranslationY();
        }
    }

    /* access modifiers changed from: package-private */
    public float getHorizontalDistance(ViewGroup sceneRoot) {
        float f = this.mDistance;
        return f >= 0.0f ? f : (float) (sceneRoot.getWidth() / 4);
    }

    /* access modifiers changed from: package-private */
    public float getVerticalDistance(ViewGroup sceneRoot) {
        float f = this.mDistance;
        return f >= 0.0f ? f : (float) (sceneRoot.getHeight() / 4);
    }

    public FadeAndShortSlide() {
        this(GravityCompat.START);
    }

    public FadeAndShortSlide(int slideEdge) {
        this.mFade = new Fade();
        this.mDistance = -1.0f;
        this.sCalculateTopBottom = new CalculateSlide() {
            public float getGoneY(FadeAndShortSlide t, ViewGroup sceneRoot, View view, int[] position) {
                int sceneRootCenter;
                int viewCenter = position[1] + (view.getHeight() / 2);
                sceneRoot.getLocationOnScreen(position);
                Rect center = FadeAndShortSlide.this.getEpicenter();
                if (center == null) {
                    sceneRootCenter = position[1] + (sceneRoot.getHeight() / 2);
                } else {
                    sceneRootCenter = center.centerY();
                }
                if (viewCenter < sceneRootCenter) {
                    return view.getTranslationY() - t.getVerticalDistance(sceneRoot);
                }
                return view.getTranslationY() + t.getVerticalDistance(sceneRoot);
            }
        };
        setSlideEdge(slideEdge);
    }

    public FadeAndShortSlide(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mFade = new Fade();
        this.mDistance = -1.0f;
        this.sCalculateTopBottom = new CalculateSlide() {
            public float getGoneY(FadeAndShortSlide t, ViewGroup sceneRoot, View view, int[] position) {
                int sceneRootCenter;
                int viewCenter = position[1] + (view.getHeight() / 2);
                sceneRoot.getLocationOnScreen(position);
                Rect center = FadeAndShortSlide.this.getEpicenter();
                if (center == null) {
                    sceneRootCenter = position[1] + (sceneRoot.getHeight() / 2);
                } else {
                    sceneRootCenter = center.centerY();
                }
                if (viewCenter < sceneRootCenter) {
                    return view.getTranslationY() - t.getVerticalDistance(sceneRoot);
                }
                return view.getTranslationY() + t.getVerticalDistance(sceneRoot);
            }
        };
        TypedArray a = context.obtainStyledAttributes(attrs, C0364R.styleable.lbSlide);
        setSlideEdge(a.getInt(C0364R.styleable.lbSlide_lb_slideEdge, GravityCompat.START));
        a.recycle();
    }

    public void setEpicenterCallback(Transition.EpicenterCallback epicenterCallback) {
        this.mFade.setEpicenterCallback(epicenterCallback);
        super.setEpicenterCallback(epicenterCallback);
    }

    private void captureValues(TransitionValues transitionValues) {
        int[] position = new int[2];
        transitionValues.view.getLocationOnScreen(position);
        transitionValues.values.put(PROPNAME_SCREEN_POSITION, position);
    }

    public void captureStartValues(TransitionValues transitionValues) {
        this.mFade.captureStartValues(transitionValues);
        super.captureStartValues(transitionValues);
        captureValues(transitionValues);
    }

    public void captureEndValues(TransitionValues transitionValues) {
        this.mFade.captureEndValues(transitionValues);
        super.captureEndValues(transitionValues);
        captureValues(transitionValues);
    }

    public void setSlideEdge(int slideEdge) {
        if (slideEdge == 48) {
            this.mSlideCalculator = sCalculateTop;
        } else if (slideEdge == 80) {
            this.mSlideCalculator = sCalculateBottom;
        } else if (slideEdge == 112) {
            this.mSlideCalculator = this.sCalculateTopBottom;
        } else if (slideEdge == 8388611) {
            this.mSlideCalculator = sCalculateStart;
        } else if (slideEdge == 8388613) {
            this.mSlideCalculator = sCalculateEnd;
        } else if (slideEdge == 8388615) {
            this.mSlideCalculator = sCalculateStartEnd;
        } else {
            throw new IllegalArgumentException("Invalid slide direction");
        }
    }

    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        ViewGroup viewGroup = sceneRoot;
        ViewGroup viewGroup2 = view;
        TransitionValues transitionValues = endValues;
        if (transitionValues == null || viewGroup == viewGroup2) {
            return null;
        }
        int[] position = (int[]) transitionValues.values.get(PROPNAME_SCREEN_POSITION);
        int left = position[0];
        int top = position[1];
        float endX = view.getTranslationX();
        float startX = this.mSlideCalculator.getGoneX(this, viewGroup, viewGroup2, position);
        float endY = view.getTranslationY();
        Animator slideAnimator = TranslationAnimationCreator.createAnimation(view, endValues, left, top, startX, this.mSlideCalculator.getGoneY(this, viewGroup, viewGroup2, position), endX, endY, sDecelerate, this);
        Animator fadeAnimator = this.mFade.onAppear(viewGroup, viewGroup2, startValues, transitionValues);
        if (slideAnimator == null) {
            return fadeAnimator;
        }
        if (fadeAnimator == null) {
            return slideAnimator;
        }
        AnimatorSet set = new AnimatorSet();
        set.play(slideAnimator).with(fadeAnimator);
        return set;
    }

    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        ViewGroup viewGroup = sceneRoot;
        ViewGroup viewGroup2 = view;
        TransitionValues transitionValues = startValues;
        if (transitionValues == null || viewGroup == viewGroup2) {
            return null;
        }
        int[] position = (int[]) transitionValues.values.get(PROPNAME_SCREEN_POSITION);
        int left = position[0];
        int top = position[1];
        float startX = view.getTranslationX();
        float endX = this.mSlideCalculator.getGoneX(this, viewGroup, viewGroup2, position);
        float startY = view.getTranslationY();
        Animator slideAnimator = TranslationAnimationCreator.createAnimation(view, startValues, left, top, startX, startY, endX, this.mSlideCalculator.getGoneY(this, viewGroup, viewGroup2, position), sDecelerate, this);
        Animator fadeAnimator = this.mFade.onDisappear(viewGroup, viewGroup2, transitionValues, endValues);
        if (slideAnimator == null) {
            return fadeAnimator;
        }
        if (fadeAnimator == null) {
            return slideAnimator;
        }
        AnimatorSet set = new AnimatorSet();
        set.play(slideAnimator).with(fadeAnimator);
        return set;
    }

    public Transition addListener(Transition.TransitionListener listener) {
        this.mFade.addListener(listener);
        return super.addListener(listener);
    }

    public Transition removeListener(Transition.TransitionListener listener) {
        this.mFade.removeListener(listener);
        return super.removeListener(listener);
    }

    public float getDistance() {
        return this.mDistance;
    }

    public void setDistance(float distance) {
        this.mDistance = distance;
    }

    public Transition clone() {
        FadeAndShortSlide clone = (FadeAndShortSlide) super.clone();
        clone.mFade = (Visibility) this.mFade.clone();
        return clone;
    }
}
