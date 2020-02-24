package androidx.leanback.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import androidx.leanback.C0364R;
import java.util.ArrayList;

public class BaseCardView extends FrameLayout {
    public static final int CARD_REGION_VISIBLE_ACTIVATED = 1;
    public static final int CARD_REGION_VISIBLE_ALWAYS = 0;
    public static final int CARD_REGION_VISIBLE_SELECTED = 2;
    public static final int CARD_TYPE_INFO_OVER = 1;
    public static final int CARD_TYPE_INFO_UNDER = 2;
    public static final int CARD_TYPE_INFO_UNDER_WITH_EXTRA = 3;
    private static final int CARD_TYPE_INVALID = 4;
    public static final int CARD_TYPE_MAIN_ONLY = 0;
    private static final boolean DEBUG = false;
    private static final int[] LB_PRESSED_STATE_SET = {16842919};
    private static final String TAG = "BaseCardView";
    private final int mActivatedAnimDuration;
    private Animation mAnim;
    private final Runnable mAnimationTrigger;
    private int mCardType;
    private boolean mDelaySelectedAnim;
    ArrayList<View> mExtraViewList;
    private int mExtraVisibility;
    float mInfoAlpha;
    float mInfoOffset;
    ArrayList<View> mInfoViewList;
    float mInfoVisFraction;
    private int mInfoVisibility;
    private ArrayList<View> mMainViewList;
    private int mMeasuredHeight;
    private int mMeasuredWidth;
    private final int mSelectedAnimDuration;
    private int mSelectedAnimationDelay;

    public BaseCardView(Context context) {
        this(context, null);
    }

    public BaseCardView(Context context, AttributeSet attrs) {
        this(context, attrs, C0364R.attr.baseCardViewStyle);
    }

    /* JADX INFO: finally extract failed */
    public BaseCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mAnimationTrigger = new Runnable() {
            public void run() {
                BaseCardView.this.animateInfoOffset(true);
            }
        };
        TypedArray a = context.obtainStyledAttributes(attrs, C0364R.styleable.lbBaseCardView, defStyleAttr, 0);
        try {
            this.mCardType = a.getInteger(C0364R.styleable.lbBaseCardView_cardType, 0);
            Drawable cardForeground = a.getDrawable(C0364R.styleable.lbBaseCardView_cardForeground);
            if (cardForeground != null) {
                setForeground(cardForeground);
            }
            Drawable cardBackground = a.getDrawable(C0364R.styleable.lbBaseCardView_cardBackground);
            if (cardBackground != null) {
                setBackground(cardBackground);
            }
            this.mInfoVisibility = a.getInteger(C0364R.styleable.lbBaseCardView_infoVisibility, 1);
            this.mExtraVisibility = a.getInteger(C0364R.styleable.lbBaseCardView_extraVisibility, 2);
            if (this.mExtraVisibility < this.mInfoVisibility) {
                this.mExtraVisibility = this.mInfoVisibility;
            }
            this.mSelectedAnimationDelay = a.getInteger(C0364R.styleable.lbBaseCardView_selectedAnimationDelay, getResources().getInteger(C0364R.integer.lb_card_selected_animation_delay));
            this.mSelectedAnimDuration = a.getInteger(C0364R.styleable.lbBaseCardView_selectedAnimationDuration, getResources().getInteger(C0364R.integer.lb_card_selected_animation_duration));
            this.mActivatedAnimDuration = a.getInteger(C0364R.styleable.lbBaseCardView_activatedAnimationDuration, getResources().getInteger(C0364R.integer.lb_card_activated_animation_duration));
            a.recycle();
            this.mDelaySelectedAnim = true;
            this.mMainViewList = new ArrayList<>();
            this.mInfoViewList = new ArrayList<>();
            this.mExtraViewList = new ArrayList<>();
            this.mInfoOffset = 0.0f;
            this.mInfoVisFraction = getFinalInfoVisFraction();
            this.mInfoAlpha = getFinalInfoAlpha();
        } catch (Throwable th) {
            a.recycle();
            throw th;
        }
    }

    public void setSelectedAnimationDelayed(boolean delay) {
        this.mDelaySelectedAnim = delay;
    }

    public boolean isSelectedAnimationDelayed() {
        return this.mDelaySelectedAnim;
    }

    public void setCardType(int type) {
        if (this.mCardType != type) {
            if (type < 0 || type >= 4) {
                Log.e(TAG, "Invalid card type specified: " + type + ". Defaulting to type CARD_TYPE_MAIN_ONLY.");
                this.mCardType = 0;
            } else {
                this.mCardType = type;
            }
            requestLayout();
        }
    }

    public int getCardType() {
        return this.mCardType;
    }

    public void setInfoVisibility(int visibility) {
        if (this.mInfoVisibility != visibility) {
            cancelAnimations();
            this.mInfoVisibility = visibility;
            this.mInfoVisFraction = getFinalInfoVisFraction();
            requestLayout();
            float newInfoAlpha = getFinalInfoAlpha();
            if (newInfoAlpha != this.mInfoAlpha) {
                this.mInfoAlpha = newInfoAlpha;
                for (int i = 0; i < this.mInfoViewList.size(); i++) {
                    this.mInfoViewList.get(i).setAlpha(this.mInfoAlpha);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final float getFinalInfoVisFraction() {
        return (this.mCardType == 2 && this.mInfoVisibility == 2 && !isSelected()) ? 0.0f : 1.0f;
    }

    /* access modifiers changed from: package-private */
    public final float getFinalInfoAlpha() {
        return (this.mCardType == 1 && this.mInfoVisibility == 2 && !isSelected()) ? 0.0f : 1.0f;
    }

    public int getInfoVisibility() {
        return this.mInfoVisibility;
    }

    @Deprecated
    public void setExtraVisibility(int visibility) {
        if (this.mExtraVisibility != visibility) {
            this.mExtraVisibility = visibility;
        }
    }

    @Deprecated
    public int getExtraVisibility() {
        return this.mExtraVisibility;
    }

    public void setActivated(boolean activated) {
        if (activated != isActivated()) {
            super.setActivated(activated);
            applyActiveState(isActivated());
        }
    }

    public void setSelected(boolean selected) {
        if (selected != isSelected()) {
            super.setSelected(selected);
            applySelectedState(isSelected());
        }
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        boolean infoAnimating = false;
        this.mMeasuredWidth = 0;
        this.mMeasuredHeight = 0;
        int state = 0;
        int mainHeight = 0;
        int infoHeight = 0;
        int extraHeight = 0;
        findChildrenViews();
        int unspecifiedSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        for (int i = 0; i < this.mMainViewList.size(); i++) {
            View mainView = this.mMainViewList.get(i);
            if (mainView.getVisibility() != 8) {
                measureChild(mainView, unspecifiedSpec, unspecifiedSpec);
                this.mMeasuredWidth = Math.max(this.mMeasuredWidth, mainView.getMeasuredWidth());
                mainHeight += mainView.getMeasuredHeight();
                state = View.combineMeasuredStates(state, mainView.getMeasuredState());
            }
        }
        setPivotX((float) (this.mMeasuredWidth / 2));
        setPivotY((float) (mainHeight / 2));
        int cardWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mMeasuredWidth, 1073741824);
        if (hasInfoRegion()) {
            for (int i2 = 0; i2 < this.mInfoViewList.size(); i2++) {
                View infoView = this.mInfoViewList.get(i2);
                if (infoView.getVisibility() != 8) {
                    measureChild(infoView, cardWidthMeasureSpec, unspecifiedSpec);
                    if (this.mCardType != 1) {
                        infoHeight += infoView.getMeasuredHeight();
                    }
                    state = View.combineMeasuredStates(state, infoView.getMeasuredState());
                }
            }
            if (hasExtraRegion() != 0) {
                for (int i3 = 0; i3 < this.mExtraViewList.size(); i3++) {
                    View extraView = this.mExtraViewList.get(i3);
                    if (extraView.getVisibility() != 8) {
                        measureChild(extraView, cardWidthMeasureSpec, unspecifiedSpec);
                        extraHeight += extraView.getMeasuredHeight();
                        state = View.combineMeasuredStates(state, extraView.getMeasuredState());
                    }
                }
            }
        }
        if (hasInfoRegion() && this.mInfoVisibility == 2) {
            infoAnimating = true;
        }
        float f = (float) mainHeight;
        float f2 = (float) infoHeight;
        if (infoAnimating) {
            f2 *= this.mInfoVisFraction;
        }
        this.mMeasuredHeight = (int) (((f + f2) + ((float) extraHeight)) - (infoAnimating ? 0.0f : this.mInfoOffset));
        setMeasuredDimension(View.resolveSizeAndState(this.mMeasuredWidth + getPaddingLeft() + getPaddingRight(), widthMeasureSpec, state), View.resolveSizeAndState(this.mMeasuredHeight + getPaddingTop() + getPaddingBottom(), heightMeasureSpec, state << 16));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        float currBottom = (float) getPaddingTop();
        for (int i = 0; i < this.mMainViewList.size(); i++) {
            View mainView = this.mMainViewList.get(i);
            if (mainView.getVisibility() != 8) {
                mainView.layout(getPaddingLeft(), (int) currBottom, this.mMeasuredWidth + getPaddingLeft(), (int) (((float) mainView.getMeasuredHeight()) + currBottom));
                currBottom += (float) mainView.getMeasuredHeight();
            }
        }
        if (hasInfoRegion() != 0) {
            float infoHeight = 0.0f;
            for (int i2 = 0; i2 < this.mInfoViewList.size(); i2++) {
                infoHeight += (float) this.mInfoViewList.get(i2).getMeasuredHeight();
            }
            int i3 = this.mCardType;
            if (i3 == 1) {
                currBottom -= infoHeight;
                if (currBottom < 0.0f) {
                    currBottom = 0.0f;
                }
            } else if (i3 != 2) {
                currBottom -= this.mInfoOffset;
            } else if (this.mInfoVisibility == 2) {
                infoHeight *= this.mInfoVisFraction;
            }
            for (int i4 = 0; i4 < this.mInfoViewList.size(); i4++) {
                View infoView = this.mInfoViewList.get(i4);
                if (infoView.getVisibility() != 8) {
                    int viewHeight = infoView.getMeasuredHeight();
                    if (((float) viewHeight) > infoHeight) {
                        viewHeight = (int) infoHeight;
                    }
                    infoView.layout(getPaddingLeft(), (int) currBottom, this.mMeasuredWidth + getPaddingLeft(), (int) (((float) viewHeight) + currBottom));
                    currBottom += (float) viewHeight;
                    infoHeight -= (float) viewHeight;
                    if (infoHeight <= 0.0f) {
                        break;
                    }
                }
            }
            if (hasExtraRegion() != 0) {
                for (int i5 = 0; i5 < this.mExtraViewList.size(); i5++) {
                    View extraView = this.mExtraViewList.get(i5);
                    if (extraView.getVisibility() != 8) {
                        extraView.layout(getPaddingLeft(), (int) currBottom, this.mMeasuredWidth + getPaddingLeft(), (int) (((float) extraView.getMeasuredHeight()) + currBottom));
                        currBottom += (float) extraView.getMeasuredHeight();
                    }
                }
            }
        }
        onSizeChanged(0, 0, right - left, bottom - top);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.mAnimationTrigger);
        cancelAnimations();
    }

    private boolean hasInfoRegion() {
        return this.mCardType != 0;
    }

    private boolean hasExtraRegion() {
        return this.mCardType == 3;
    }

    private boolean isRegionVisible(int regionVisibility) {
        if (regionVisibility == 0) {
            return true;
        }
        if (regionVisibility == 1) {
            return isActivated();
        }
        if (regionVisibility != 2) {
            return false;
        }
        return isSelected();
    }

    private boolean isCurrentRegionVisible(int regionVisibility) {
        if (regionVisibility == 0) {
            return true;
        }
        if (regionVisibility == 1) {
            return isActivated();
        }
        if (regionVisibility != 2) {
            return false;
        }
        if (this.mCardType != 2) {
            return isSelected();
        }
        if (this.mInfoVisFraction > 0.0f) {
            return true;
        }
        return false;
    }

    private void findChildrenViews() {
        this.mMainViewList.clear();
        this.mInfoViewList.clear();
        this.mExtraViewList.clear();
        int count = getChildCount();
        boolean infoVisible = hasInfoRegion() && isCurrentRegionVisible(this.mInfoVisibility);
        boolean extraVisible = hasExtraRegion() && this.mInfoOffset > 0.0f;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child != null) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                int i2 = 8;
                if (lp.viewType == 1) {
                    child.setAlpha(this.mInfoAlpha);
                    this.mInfoViewList.add(child);
                    if (infoVisible) {
                        i2 = 0;
                    }
                    child.setVisibility(i2);
                } else if (lp.viewType == 2) {
                    this.mExtraViewList.add(child);
                    if (extraVisible) {
                        i2 = 0;
                    }
                    child.setVisibility(i2);
                } else {
                    this.mMainViewList.add(child);
                    child.setVisibility(0);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int extraSpace) {
        int[] s = super.onCreateDrawableState(extraSpace);
        int N = s.length;
        boolean pressed = false;
        boolean enabled = false;
        for (int i = 0; i < N; i++) {
            if (s[i] == 16842919) {
                pressed = true;
            }
            if (s[i] == 16842910) {
                enabled = true;
            }
        }
        if (pressed && enabled) {
            return View.PRESSED_ENABLED_STATE_SET;
        }
        if (pressed) {
            return LB_PRESSED_STATE_SET;
        }
        if (enabled) {
            return View.ENABLED_STATE_SET;
        }
        return View.EMPTY_STATE_SET;
    }

    private void applyActiveState(boolean active) {
        int i;
        if (hasInfoRegion() && (i = this.mInfoVisibility) == 1) {
            setInfoViewVisibility(isRegionVisible(i));
        }
    }

    private void setInfoViewVisibility(boolean visible) {
        int i = this.mCardType;
        if (i == 3) {
            if (visible) {
                for (int i2 = 0; i2 < this.mInfoViewList.size(); i2++) {
                    this.mInfoViewList.get(i2).setVisibility(0);
                }
                return;
            }
            for (int i3 = 0; i3 < this.mInfoViewList.size(); i3++) {
                this.mInfoViewList.get(i3).setVisibility(8);
            }
            for (int i4 = 0; i4 < this.mExtraViewList.size(); i4++) {
                this.mExtraViewList.get(i4).setVisibility(8);
            }
            this.mInfoOffset = 0.0f;
        } else if (i == 2) {
            if (this.mInfoVisibility == 2) {
                animateInfoHeight(visible);
                return;
            }
            for (int i5 = 0; i5 < this.mInfoViewList.size(); i5++) {
                this.mInfoViewList.get(i5).setVisibility(visible ? 0 : 8);
            }
        } else if (i == 1) {
            animateInfoAlpha(visible);
        }
    }

    private void applySelectedState(boolean focused) {
        removeCallbacks(this.mAnimationTrigger);
        if (this.mCardType == 3) {
            if (!focused) {
                animateInfoOffset(false);
            } else if (!this.mDelaySelectedAnim) {
                post(this.mAnimationTrigger);
                this.mDelaySelectedAnim = true;
            } else {
                postDelayed(this.mAnimationTrigger, (long) this.mSelectedAnimationDelay);
            }
        } else if (this.mInfoVisibility == 2) {
            setInfoViewVisibility(focused);
        }
    }

    /* access modifiers changed from: package-private */
    public void cancelAnimations() {
        Animation animation = this.mAnim;
        if (animation != null) {
            animation.cancel();
            this.mAnim = null;
            clearAnimation();
        }
    }

    /* access modifiers changed from: package-private */
    public void animateInfoOffset(boolean shown) {
        cancelAnimations();
        int extraHeight = 0;
        if (shown) {
            int widthSpec = View.MeasureSpec.makeMeasureSpec(this.mMeasuredWidth, 1073741824);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
            for (int i = 0; i < this.mExtraViewList.size(); i++) {
                View extraView = this.mExtraViewList.get(i);
                extraView.setVisibility(0);
                extraView.measure(widthSpec, heightSpec);
                extraHeight = Math.max(extraHeight, extraView.getMeasuredHeight());
            }
        }
        this.mAnim = new InfoOffsetAnimation(this.mInfoOffset, shown ? (float) extraHeight : 0.0f);
        this.mAnim.setDuration((long) this.mSelectedAnimDuration);
        this.mAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        this.mAnim.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                if (BaseCardView.this.mInfoOffset == 0.0f) {
                    for (int i = 0; i < BaseCardView.this.mExtraViewList.size(); i++) {
                        BaseCardView.this.mExtraViewList.get(i).setVisibility(8);
                    }
                }
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        startAnimation(this.mAnim);
    }

    private void animateInfoHeight(boolean shown) {
        cancelAnimations();
        if (shown) {
            for (int i = 0; i < this.mInfoViewList.size(); i++) {
                this.mInfoViewList.get(i).setVisibility(0);
            }
        }
        float targetFraction = shown ? 1.0f : 0.0f;
        float f = this.mInfoVisFraction;
        if (f != targetFraction) {
            this.mAnim = new InfoHeightAnimation(f, targetFraction);
            this.mAnim.setDuration((long) this.mSelectedAnimDuration);
            this.mAnim.setInterpolator(new AccelerateDecelerateInterpolator());
            this.mAnim.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    if (BaseCardView.this.mInfoVisFraction == 0.0f) {
                        for (int i = 0; i < BaseCardView.this.mInfoViewList.size(); i++) {
                            BaseCardView.this.mInfoViewList.get(i).setVisibility(8);
                        }
                    }
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
            startAnimation(this.mAnim);
        }
    }

    private void animateInfoAlpha(boolean shown) {
        cancelAnimations();
        if (shown) {
            for (int i = 0; i < this.mInfoViewList.size(); i++) {
                this.mInfoViewList.get(i).setVisibility(0);
            }
        }
        int i2 = 1065353216;
        float targetAlpha = shown ? 1.0f : 0.0f;
        float f = this.mInfoAlpha;
        if (targetAlpha != f) {
            if (!shown) {
                i2 = 0;
            }
            this.mAnim = new InfoAlphaAnimation(f, i2);
            this.mAnim.setDuration((long) this.mActivatedAnimDuration);
            this.mAnim.setInterpolator(new DecelerateInterpolator());
            this.mAnim.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    if (((double) BaseCardView.this.mInfoAlpha) == 0.0d) {
                        for (int i = 0; i < BaseCardView.this.mInfoViewList.size(); i++) {
                            BaseCardView.this.mInfoViewList.get(i).setVisibility(8);
                        }
                    }
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
            startAnimation(this.mAnim);
        }
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        if (lp instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) lp);
        }
        return new LayoutParams(lp);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public static class LayoutParams extends FrameLayout.LayoutParams {
        public static final int VIEW_TYPE_EXTRA = 2;
        public static final int VIEW_TYPE_INFO = 1;
        public static final int VIEW_TYPE_MAIN = 0;
        @ViewDebug.ExportedProperty(category = TtmlNode.TAG_LAYOUT, mapping = {@ViewDebug.IntToString(from = 0, to = "MAIN"), @ViewDebug.IntToString(from = 1, to = "INFO"), @ViewDebug.IntToString(from = 2, to = "EXTRA")})
        public int viewType = 0;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, C0364R.styleable.lbBaseCardView_Layout);
            this.viewType = a.getInt(C0364R.styleable.lbBaseCardView_Layout_layout_viewType, 0);
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
        }

        public LayoutParams(LayoutParams source) {
            super((ViewGroup.MarginLayoutParams) source);
            this.viewType = source.viewType;
        }
    }

    class AnimationBase extends Animation {
        AnimationBase() {
        }

        /* access modifiers changed from: package-private */
        @VisibleForTesting
        public final void mockStart() {
            getTransformation(0, null);
        }

        /* access modifiers changed from: package-private */
        @VisibleForTesting
        public final void mockEnd() {
            applyTransformation(1.0f, null);
            BaseCardView.this.cancelAnimations();
        }
    }

    final class InfoOffsetAnimation extends AnimationBase {
        private float mDelta;
        private float mStartValue;

        public InfoOffsetAnimation(float start, float end) {
            super();
            this.mStartValue = start;
            this.mDelta = end - start;
        }

        /* access modifiers changed from: protected */
        public void applyTransformation(float interpolatedTime, Transformation t) {
            BaseCardView baseCardView = BaseCardView.this;
            baseCardView.mInfoOffset = this.mStartValue + (this.mDelta * interpolatedTime);
            baseCardView.requestLayout();
        }
    }

    final class InfoHeightAnimation extends AnimationBase {
        private float mDelta;
        private float mStartValue;

        public InfoHeightAnimation(float start, float end) {
            super();
            this.mStartValue = start;
            this.mDelta = end - start;
        }

        /* access modifiers changed from: protected */
        public void applyTransformation(float interpolatedTime, Transformation t) {
            BaseCardView baseCardView = BaseCardView.this;
            baseCardView.mInfoVisFraction = this.mStartValue + (this.mDelta * interpolatedTime);
            baseCardView.requestLayout();
        }
    }

    final class InfoAlphaAnimation extends AnimationBase {
        private float mDelta;
        private float mStartValue;

        public InfoAlphaAnimation(float start, float end) {
            super();
            this.mStartValue = start;
            this.mDelta = end - start;
        }

        /* access modifiers changed from: protected */
        public void applyTransformation(float interpolatedTime, Transformation t) {
            BaseCardView.this.mInfoAlpha = this.mStartValue + (this.mDelta * interpolatedTime);
            for (int i = 0; i < BaseCardView.this.mInfoViewList.size(); i++) {
                BaseCardView.this.mInfoViewList.get(i).setAlpha(BaseCardView.this.mInfoAlpha);
            }
        }
    }

    public String toString() {
        return super.toString();
    }
}
