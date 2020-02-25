package androidx.leanback.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p001v4.app.Fragment;
import android.support.p001v4.view.GravityCompat;
import android.util.Property;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.leanback.C0364R;
import androidx.leanback.widget.PagingIndicator;

import java.util.ArrayList;
import java.util.List;

public abstract class OnboardingSupportFragment extends Fragment {
    private static final boolean DEBUG = false;
    private static final long DESCRIPTION_START_DELAY_MS = 33;
    private static final long HEADER_ANIMATION_DURATION_MS = 417;
    private static final long HEADER_APPEAR_DELAY_MS = 500;
    private static final TimeInterpolator HEADER_APPEAR_INTERPOLATOR = new DecelerateInterpolator();
    private static final TimeInterpolator HEADER_DISAPPEAR_INTERPOLATOR = new AccelerateInterpolator();
    private static final String KEY_CURRENT_PAGE_INDEX = "leanback.onboarding.current_page_index";
    private static final String KEY_ENTER_ANIMATION_FINISHED = "leanback.onboarding.enter_animation_finished";
    private static final String KEY_LOGO_ANIMATION_FINISHED = "leanback.onboarding.logo_animation_finished";
    private static final long LOGO_SPLASH_PAUSE_DURATION_MS = 1333;
    private static final int SLIDE_DISTANCE = 60;
    private static final String TAG = "OnboardingF";
    private static int sSlideDistance;
    int mCurrentPageIndex;
    TextView mDescriptionView;
    boolean mEnterAnimationFinished;
    boolean mIsLtr;
    boolean mLogoAnimationFinished;
    PagingIndicator mPageIndicator;
    View mStartButton;
    TextView mTitleView;
    private AnimatorSet mAnimator;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View view) {
            if (OnboardingSupportFragment.this.mLogoAnimationFinished) {
                if (OnboardingSupportFragment.this.mCurrentPageIndex == OnboardingSupportFragment.this.getPageCount() - 1) {
                    OnboardingSupportFragment.this.onFinishFragment();
                } else {
                    OnboardingSupportFragment.this.moveToNextPage();
                }
            }
        }
    };
    private final View.OnKeyListener mOnKeyListener = new View.OnKeyListener() {
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (!OnboardingSupportFragment.this.mLogoAnimationFinished) {
                if (keyCode != 4) {
                    return true;
                }
                return false;
            } else if (event.getAction() == 0) {
                return false;
            } else {
                if (keyCode != 4) {
                    if (keyCode == 21) {
                        if (OnboardingSupportFragment.this.mIsLtr) {
                            OnboardingSupportFragment.this.moveToPreviousPage();
                        } else {
                            OnboardingSupportFragment.this.moveToNextPage();
                        }
                        return true;
                    } else if (keyCode != 22) {
                        return false;
                    } else {
                        if (OnboardingSupportFragment.this.mIsLtr) {
                            OnboardingSupportFragment.this.moveToNextPage();
                        } else {
                            OnboardingSupportFragment.this.moveToPreviousPage();
                        }
                        return true;
                    }
                } else if (OnboardingSupportFragment.this.mCurrentPageIndex == 0) {
                    return false;
                } else {
                    OnboardingSupportFragment.this.moveToPreviousPage();
                    return true;
                }
            }
        }
    };
    @ColorInt
    private int mArrowBackgroundColor = 0;
    private boolean mArrowBackgroundColorSet;
    @ColorInt
    private int mArrowColor = 0;
    private boolean mArrowColorSet;
    @ColorInt
    private int mDescriptionViewTextColor = 0;
    private boolean mDescriptionViewTextColorSet;
    @ColorInt
    private int mDotBackgroundColor = 0;
    private boolean mDotBackgroundColorSet;
    private int mIconResourceId;
    private int mLogoResourceId;
    private ImageView mLogoView;
    private ImageView mMainIconView;
    private CharSequence mStartButtonText;
    private boolean mStartButtonTextSet;
    private ContextThemeWrapper mThemeWrapper;
    @ColorInt
    private int mTitleViewTextColor = 0;
    private boolean mTitleViewTextColorSet;

    /* access modifiers changed from: protected */
    public abstract int getPageCount();

    /* access modifiers changed from: protected */
    public abstract CharSequence getPageDescription(int i);

    /* access modifiers changed from: protected */
    public abstract CharSequence getPageTitle(int i);

    /* access modifiers changed from: protected */
    @Nullable
    public abstract View onCreateBackgroundView(LayoutInflater layoutInflater, ViewGroup viewGroup);

    /* access modifiers changed from: protected */
    @Nullable
    public abstract View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup);

    /* access modifiers changed from: protected */
    @Nullable
    public abstract View onCreateForegroundView(LayoutInflater layoutInflater, ViewGroup viewGroup);

    /* access modifiers changed from: protected */
    public void moveToPreviousPage() {
        int i;
        if (this.mLogoAnimationFinished && (i = this.mCurrentPageIndex) > 0) {
            this.mCurrentPageIndex = i - 1;
            onPageChangedInternal(this.mCurrentPageIndex + 1);
        }
    }

    /* access modifiers changed from: protected */
    public void moveToNextPage() {
        if (this.mLogoAnimationFinished && this.mCurrentPageIndex < getPageCount() - 1) {
            this.mCurrentPageIndex++;
            onPageChangedInternal(this.mCurrentPageIndex - 1);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        resolveTheme();
        boolean z = false;
        ViewGroup view = (ViewGroup) getThemeInflater(inflater).inflate(C0364R.layout.lb_onboarding_fragment, container, false);
        if (getResources().getConfiguration().getLayoutDirection() == 0) {
            z = true;
        }
        this.mIsLtr = z;
        this.mPageIndicator = (PagingIndicator) view.findViewById(C0364R.C0366id.page_indicator);
        this.mPageIndicator.setOnClickListener(this.mOnClickListener);
        this.mPageIndicator.setOnKeyListener(this.mOnKeyListener);
        this.mStartButton = view.findViewById(C0364R.C0366id.button_start);
        this.mStartButton.setOnClickListener(this.mOnClickListener);
        this.mStartButton.setOnKeyListener(this.mOnKeyListener);
        this.mMainIconView = (ImageView) view.findViewById(C0364R.C0366id.main_icon);
        this.mLogoView = (ImageView) view.findViewById(C0364R.C0366id.logo);
        this.mTitleView = (TextView) view.findViewById(C0364R.C0366id.title);
        this.mDescriptionView = (TextView) view.findViewById(C0364R.C0366id.description);
        if (this.mTitleViewTextColorSet) {
            this.mTitleView.setTextColor(this.mTitleViewTextColor);
        }
        if (this.mDescriptionViewTextColorSet) {
            this.mDescriptionView.setTextColor(this.mDescriptionViewTextColor);
        }
        if (this.mDotBackgroundColorSet) {
            this.mPageIndicator.setDotBackgroundColor(this.mDotBackgroundColor);
        }
        if (this.mArrowColorSet) {
            this.mPageIndicator.setArrowColor(this.mArrowColor);
        }
        if (this.mArrowBackgroundColorSet) {
            this.mPageIndicator.setDotBackgroundColor(this.mArrowBackgroundColor);
        }
        if (this.mStartButtonTextSet) {
            ((Button) this.mStartButton).setText(this.mStartButtonText);
        }
        Context context = getContext();
        if (sSlideDistance == 0) {
            sSlideDistance = (int) (context.getResources().getDisplayMetrics().scaledDensity * 60.0f);
        }
        view.requestFocus();
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            this.mCurrentPageIndex = 0;
            this.mLogoAnimationFinished = false;
            this.mEnterAnimationFinished = false;
            this.mPageIndicator.onPageSelected(0, false);
            view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    OnboardingSupportFragment.this.getView().getViewTreeObserver().removeOnPreDrawListener(this);
                    if (!OnboardingSupportFragment.this.startLogoAnimation()) {
                        OnboardingSupportFragment onboardingSupportFragment = OnboardingSupportFragment.this;
                        onboardingSupportFragment.mLogoAnimationFinished = true;
                        onboardingSupportFragment.onLogoAnimationFinished();
                    }
                    return true;
                }
            });
            return;
        }
        this.mCurrentPageIndex = savedInstanceState.getInt(KEY_CURRENT_PAGE_INDEX);
        this.mLogoAnimationFinished = savedInstanceState.getBoolean(KEY_LOGO_ANIMATION_FINISHED);
        this.mEnterAnimationFinished = savedInstanceState.getBoolean(KEY_ENTER_ANIMATION_FINISHED);
        if (this.mLogoAnimationFinished) {
            onLogoAnimationFinished();
        } else if (!startLogoAnimation()) {
            this.mLogoAnimationFinished = true;
            onLogoAnimationFinished();
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_PAGE_INDEX, this.mCurrentPageIndex);
        outState.putBoolean(KEY_LOGO_ANIMATION_FINISHED, this.mLogoAnimationFinished);
        outState.putBoolean(KEY_ENTER_ANIMATION_FINISHED, this.mEnterAnimationFinished);
    }

    @ColorInt
    public final int getTitleViewTextColor() {
        return this.mTitleViewTextColor;
    }

    public void setTitleViewTextColor(@ColorInt int color) {
        this.mTitleViewTextColor = color;
        this.mTitleViewTextColorSet = true;
        TextView textView = this.mTitleView;
        if (textView != null) {
            textView.setTextColor(color);
        }
    }

    @ColorInt
    public final int getDescriptionViewTextColor() {
        return this.mDescriptionViewTextColor;
    }

    public void setDescriptionViewTextColor(@ColorInt int color) {
        this.mDescriptionViewTextColor = color;
        this.mDescriptionViewTextColorSet = true;
        TextView textView = this.mDescriptionView;
        if (textView != null) {
            textView.setTextColor(color);
        }
    }

    @ColorInt
    public final int getDotBackgroundColor() {
        return this.mDotBackgroundColor;
    }

    public void setDotBackgroundColor(@ColorInt int color) {
        this.mDotBackgroundColor = color;
        this.mDotBackgroundColorSet = true;
        PagingIndicator pagingIndicator = this.mPageIndicator;
        if (pagingIndicator != null) {
            pagingIndicator.setDotBackgroundColor(color);
        }
    }

    @ColorInt
    public final int getArrowColor() {
        return this.mArrowColor;
    }

    public void setArrowColor(@ColorInt int color) {
        this.mArrowColor = color;
        this.mArrowColorSet = true;
        PagingIndicator pagingIndicator = this.mPageIndicator;
        if (pagingIndicator != null) {
            pagingIndicator.setArrowColor(color);
        }
    }

    @ColorInt
    public final int getArrowBackgroundColor() {
        return this.mArrowBackgroundColor;
    }

    public void setArrowBackgroundColor(@ColorInt int color) {
        this.mArrowBackgroundColor = color;
        this.mArrowBackgroundColorSet = true;
        PagingIndicator pagingIndicator = this.mPageIndicator;
        if (pagingIndicator != null) {
            pagingIndicator.setArrowBackgroundColor(color);
        }
    }

    public final CharSequence getStartButtonText() {
        return this.mStartButtonText;
    }

    public void setStartButtonText(CharSequence text) {
        this.mStartButtonText = text;
        this.mStartButtonTextSet = true;
        View view = this.mStartButton;
        if (view != null) {
            ((Button) view).setText(this.mStartButtonText);
        }
    }

    public int onProvideTheme() {
        return -1;
    }

    private void resolveTheme() {
        Context context = getContext();
        int theme = onProvideTheme();
        if (theme == -1) {
            int resId = C0364R.attr.onboardingTheme;
            TypedValue typedValue = new TypedValue();
            if (context.getTheme().resolveAttribute(resId, typedValue, true)) {
                this.mThemeWrapper = new ContextThemeWrapper(context, typedValue.resourceId);
                return;
            }
            return;
        }
        this.mThemeWrapper = new ContextThemeWrapper(context, theme);
    }

    private LayoutInflater getThemeInflater(LayoutInflater inflater) {
        ContextThemeWrapper contextThemeWrapper = this.mThemeWrapper;
        return contextThemeWrapper == null ? inflater : inflater.cloneInContext(contextThemeWrapper);
    }

    public final int getLogoResourceId() {
        return this.mLogoResourceId;
    }

    public final void setLogoResourceId(int id) {
        this.mLogoResourceId = id;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Animator onCreateLogoAnimation() {
        return null;
    }

    /* access modifiers changed from: package-private */
    public boolean startLogoAnimation() {
        Animator animator;
        final Context context = getContext();
        if (context == null) {
            return false;
        }
        if (this.mLogoResourceId != 0) {
            this.mLogoView.setVisibility(0);
            this.mLogoView.setImageResource(this.mLogoResourceId);
            Animator inAnimator = AnimatorInflater.loadAnimator(context, C0364R.animator.lb_onboarding_logo_enter);
            Animator outAnimator = AnimatorInflater.loadAnimator(context, C0364R.animator.lb_onboarding_logo_exit);
            outAnimator.setStartDelay(LOGO_SPLASH_PAUSE_DURATION_MS);
            AnimatorSet logoAnimator = new AnimatorSet();
            logoAnimator.playSequentially(inAnimator, outAnimator);
            logoAnimator.setTarget(this.mLogoView);
            animator = logoAnimator;
        } else {
            animator = onCreateLogoAnimation();
        }
        if (animator == null) {
            return false;
        }
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                if (context != null) {
                    OnboardingSupportFragment onboardingSupportFragment = OnboardingSupportFragment.this;
                    onboardingSupportFragment.mLogoAnimationFinished = true;
                    onboardingSupportFragment.onLogoAnimationFinished();
                }
            }
        });
        animator.start();
        return true;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Animator onCreateEnterAnimation() {
        return null;
    }

    /* access modifiers changed from: package-private */
    public void hideLogoView() {
        this.mLogoView.setVisibility(8);
        int i = this.mIconResourceId;
        if (i != 0) {
            this.mMainIconView.setImageResource(i);
            this.mMainIconView.setVisibility(0);
        }
        View container = getView();
        LayoutInflater inflater = getThemeInflater(LayoutInflater.from(getContext()));
        ViewGroup backgroundContainer = (ViewGroup) container.findViewById(C0364R.C0366id.background_container);
        View background = onCreateBackgroundView(inflater, backgroundContainer);
        if (background != null) {
            backgroundContainer.setVisibility(0);
            backgroundContainer.addView(background);
        }
        ViewGroup contentContainer = (ViewGroup) container.findViewById(C0364R.C0366id.content_container);
        View content = onCreateContentView(inflater, contentContainer);
        if (content != null) {
            contentContainer.setVisibility(0);
            contentContainer.addView(content);
        }
        ViewGroup foregroundContainer = (ViewGroup) container.findViewById(C0364R.C0366id.foreground_container);
        View foreground = onCreateForegroundView(inflater, foregroundContainer);
        if (foreground != null) {
            foregroundContainer.setVisibility(0);
            foregroundContainer.addView(foreground);
        }
        container.findViewById(C0364R.C0366id.page_container).setVisibility(0);
        container.findViewById(C0364R.C0366id.content_container).setVisibility(0);
        if (getPageCount() > 1) {
            this.mPageIndicator.setPageCount(getPageCount());
            this.mPageIndicator.onPageSelected(this.mCurrentPageIndex, false);
        }
        if (this.mCurrentPageIndex == getPageCount() - 1) {
            this.mStartButton.setVisibility(0);
        } else {
            this.mPageIndicator.setVisibility(0);
        }
        this.mTitleView.setText(getPageTitle(this.mCurrentPageIndex));
        this.mDescriptionView.setText(getPageDescription(this.mCurrentPageIndex));
    }

    /* access modifiers changed from: protected */
    public void onLogoAnimationFinished() {
        startEnterAnimation(false);
    }

    /* access modifiers changed from: protected */
    public final void startEnterAnimation(boolean force) {
        Context context = getContext();
        if (context != null) {
            hideLogoView();
            if (!this.mEnterAnimationFinished || force) {
                List<Animator> animators = new ArrayList<>();
                Animator animator = AnimatorInflater.loadAnimator(context, C0364R.animator.lb_onboarding_page_indicator_enter);
                animator.setTarget(getPageCount() <= 1 ? this.mStartButton : this.mPageIndicator);
                animators.add(animator);
                Animator animator2 = onCreateTitleAnimator();
                if (animator2 != null) {
                    animator2.setTarget(this.mTitleView);
                    animators.add(animator2);
                }
                Animator animator3 = onCreateDescriptionAnimator();
                if (animator3 != null) {
                    animator3.setTarget(this.mDescriptionView);
                    animators.add(animator3);
                }
                Animator customAnimator = onCreateEnterAnimation();
                if (customAnimator != null) {
                    animators.add(customAnimator);
                }
                if (!animators.isEmpty()) {
                    this.mAnimator = new AnimatorSet();
                    this.mAnimator.playTogether(animators);
                    this.mAnimator.start();
                    this.mAnimator.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animation) {
                            OnboardingSupportFragment.this.mEnterAnimationFinished = true;
                        }
                    });
                    getView().requestFocus();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public Animator onCreateDescriptionAnimator() {
        return AnimatorInflater.loadAnimator(getContext(), C0364R.animator.lb_onboarding_description_enter);
    }

    /* access modifiers changed from: protected */
    public Animator onCreateTitleAnimator() {
        return AnimatorInflater.loadAnimator(getContext(), C0364R.animator.lb_onboarding_title_enter);
    }

    /* access modifiers changed from: protected */
    public final boolean isLogoAnimationFinished() {
        return this.mLogoAnimationFinished;
    }

    /* access modifiers changed from: protected */
    public final int getCurrentPageIndex() {
        return this.mCurrentPageIndex;
    }

    /* access modifiers changed from: protected */
    public void onFinishFragment() {
    }

    private void onPageChangedInternal(int previousPage) {
        Animator fadeAnimator;
        AnimatorSet animatorSet = this.mAnimator;
        if (animatorSet != null) {
            animatorSet.end();
        }
        this.mPageIndicator.onPageSelected(this.mCurrentPageIndex, true);
        List<Animator> animators = new ArrayList<>();
        if (previousPage < getCurrentPageIndex()) {
            animators.add(createAnimator(this.mTitleView, false, GravityCompat.START, 0));
            Animator createAnimator = createAnimator(this.mDescriptionView, false, GravityCompat.START, DESCRIPTION_START_DELAY_MS);
            fadeAnimator = createAnimator;
            animators.add(createAnimator);
            animators.add(createAnimator(this.mTitleView, true, GravityCompat.END, HEADER_APPEAR_DELAY_MS));
            animators.add(createAnimator(this.mDescriptionView, true, GravityCompat.END, 533));
        } else {
            animators.add(createAnimator(this.mTitleView, false, GravityCompat.END, 0));
            Animator createAnimator2 = createAnimator(this.mDescriptionView, false, GravityCompat.END, DESCRIPTION_START_DELAY_MS);
            fadeAnimator = createAnimator2;
            animators.add(createAnimator2);
            animators.add(createAnimator(this.mTitleView, true, GravityCompat.START, HEADER_APPEAR_DELAY_MS));
            animators.add(createAnimator(this.mDescriptionView, true, GravityCompat.START, 533));
        }
        final int currentPageIndex = getCurrentPageIndex();
        fadeAnimator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                OnboardingSupportFragment.this.mTitleView.setText(OnboardingSupportFragment.this.getPageTitle(currentPageIndex));
                OnboardingSupportFragment.this.mDescriptionView.setText(OnboardingSupportFragment.this.getPageDescription(currentPageIndex));
            }
        });
        Context context = getContext();
        if (getCurrentPageIndex() == getPageCount() - 1) {
            this.mStartButton.setVisibility(0);
            Animator navigatorFadeOutAnimator = AnimatorInflater.loadAnimator(context, C0364R.animator.lb_onboarding_page_indicator_fade_out);
            navigatorFadeOutAnimator.setTarget(this.mPageIndicator);
            navigatorFadeOutAnimator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    OnboardingSupportFragment.this.mPageIndicator.setVisibility(8);
                }
            });
            animators.add(navigatorFadeOutAnimator);
            Animator buttonFadeInAnimator = AnimatorInflater.loadAnimator(context, C0364R.animator.lb_onboarding_start_button_fade_in);
            buttonFadeInAnimator.setTarget(this.mStartButton);
            animators.add(buttonFadeInAnimator);
        } else if (previousPage == getPageCount() - 1) {
            this.mPageIndicator.setVisibility(0);
            Animator navigatorFadeInAnimator = AnimatorInflater.loadAnimator(context, C0364R.animator.lb_onboarding_page_indicator_fade_in);
            navigatorFadeInAnimator.setTarget(this.mPageIndicator);
            animators.add(navigatorFadeInAnimator);
            Animator buttonFadeOutAnimator = AnimatorInflater.loadAnimator(context, C0364R.animator.lb_onboarding_start_button_fade_out);
            buttonFadeOutAnimator.setTarget(this.mStartButton);
            buttonFadeOutAnimator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    OnboardingSupportFragment.this.mStartButton.setVisibility(8);
                }
            });
            animators.add(buttonFadeOutAnimator);
        }
        this.mAnimator = new AnimatorSet();
        this.mAnimator.playTogether(animators);
        this.mAnimator.start();
        onPageChanged(this.mCurrentPageIndex, previousPage);
    }

    /* access modifiers changed from: protected */
    public void onPageChanged(int newPage, int previousPage) {
    }

    private Animator createAnimator(View view, boolean fadeIn, int slideDirection, long startDelay) {
        Animator fadeAnimator;
        Animator slideAnimator;
        boolean isLtr = getView().getLayoutDirection() == 0;
        boolean slideRight = (isLtr && slideDirection == 8388613) || (!isLtr && slideDirection == 8388611) || slideDirection == 5;
        if (fadeIn) {
            fadeAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f, 1.0f);
            Property property = View.TRANSLATION_X;
            float[] fArr = new float[2];
            int i = sSlideDistance;
            if (!slideRight) {
                i = -i;
            }
            fArr[0] = (float) i;
            fArr[1] = 0.0f;
            slideAnimator = ObjectAnimator.ofFloat(view, property, fArr);
            fadeAnimator.setInterpolator(HEADER_APPEAR_INTERPOLATOR);
            slideAnimator.setInterpolator(HEADER_APPEAR_INTERPOLATOR);
        } else {
            fadeAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 1.0f, 0.0f);
            Property property2 = View.TRANSLATION_X;
            float[] fArr2 = new float[2];
            fArr2[0] = 0.0f;
            int i2 = sSlideDistance;
            if (!slideRight) {
                i2 = -i2;
            }
            fArr2[1] = (float) i2;
            slideAnimator = ObjectAnimator.ofFloat(view, property2, fArr2);
            fadeAnimator.setInterpolator(HEADER_DISAPPEAR_INTERPOLATOR);
            slideAnimator.setInterpolator(HEADER_DISAPPEAR_INTERPOLATOR);
        }
        fadeAnimator.setDuration(HEADER_ANIMATION_DURATION_MS);
        fadeAnimator.setTarget(view);
        slideAnimator.setDuration(HEADER_ANIMATION_DURATION_MS);
        slideAnimator.setTarget(view);
        AnimatorSet animator = new AnimatorSet();
        animator.playTogether(fadeAnimator, slideAnimator);
        if (startDelay > 0) {
            animator.setStartDelay(startDelay);
        }
        return animator;
    }

    public final void setIconResouceId(int resourceId) {
        this.mIconResourceId = resourceId;
        ImageView imageView = this.mMainIconView;
        if (imageView != null) {
            imageView.setImageResource(resourceId);
            this.mMainIconView.setVisibility(0);
        }
    }

    public final int getIconResourceId() {
        return this.mIconResourceId;
    }
}
