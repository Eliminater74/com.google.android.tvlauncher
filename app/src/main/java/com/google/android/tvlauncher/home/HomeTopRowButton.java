package com.google.android.tvlauncher.home;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.tvlauncher.C1188R;

public class HomeTopRowButton extends LinearLayout {
    private final AnimatorSet mAnimateIn;
    private final AnimatorSet mAnimateOut;
    private final int mAnimationDuration;
    private View.OnFocusChangeListener mFocusChangeListener;
    /* access modifiers changed from: private */
    public ImageView mIcon;
    private final int mIconFocusedColor;
    private final int mIconUnfocusedColor;
    /* access modifiers changed from: private */
    public View mIndicator;
    /* access modifiers changed from: private */
    public final int mIndicatorFocusedColor;
    /* access modifiers changed from: private */
    public TextView mTitle;

    public HomeTopRowButton(Context context) {
        this(context, null);
    }

    public HomeTopRowButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mFocusChangeListener = new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    HomeTopRowButton.this.animateIn();
                } else {
                    HomeTopRowButton.this.animateOut();
                }
            }
        };
        Resources res = getResources();
        this.mIndicatorFocusedColor = res.getColor(C1188R.color.reference_white_100, null);
        this.mIconUnfocusedColor = res.getColor(C1188R.color.reference_white_60, null);
        this.mIconFocusedColor = ViewCompat.MEASURED_STATE_MASK;
        this.mAnimationDuration = res.getInteger(C1188R.integer.top_row_button_animation_duration_ms);
        this.mAnimateIn = new AnimatorSet();
        this.mAnimateOut = new AnimatorSet();
    }

    private void setUpAnimations() {
        ValueAnimator iconUnfocused = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(this.mIconFocusedColor), Integer.valueOf(this.mIconUnfocusedColor));
        iconUnfocused.setDuration((long) this.mAnimationDuration);
        iconUnfocused.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (HomeTopRowButton.this.mIcon != null && HomeTopRowButton.this.mIcon.getDrawable() != null) {
                    HomeTopRowButton.this.mIcon.getDrawable().setTint(((Integer) valueAnimator.getAnimatedValue()).intValue());
                }
            }
        });
        ValueAnimator iconFocused = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(this.mIconUnfocusedColor), Integer.valueOf(this.mIconFocusedColor));
        iconFocused.setDuration((long) this.mAnimationDuration);
        iconFocused.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (HomeTopRowButton.this.mIcon != null && HomeTopRowButton.this.mIcon.getDrawable() != null) {
                    HomeTopRowButton.this.mIcon.getDrawable().setTint(((Integer) valueAnimator.getAnimatedValue()).intValue());
                }
            }
        });
        ObjectAnimator textFadeIn = ObjectAnimator.ofFloat(this.mTitle, "alpha", 0.0f, 1.0f);
        textFadeIn.setDuration((long) this.mAnimationDuration);
        textFadeIn.addListener(new Animator.AnimatorListener() {
            public void onAnimationStart(Animator animation) {
                HomeTopRowButton.this.mTitle.setVisibility(0);
            }

            public void onAnimationEnd(Animator animation) {
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
        AnimatorSet expandFadeIn = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), C1188R.animator.expand_fade_in);
        expandFadeIn.setTarget(this.mIndicator);
        this.mAnimateIn.playTogether(expandFadeIn, iconFocused, textFadeIn);
        this.mAnimateIn.addListener(new Animator.AnimatorListener() {
            public void onAnimationStart(Animator animation) {
                HomeTopRowButton.this.mIndicator.setBackgroundColor(HomeTopRowButton.this.mIndicatorFocusedColor);
            }

            public void onAnimationEnd(Animator animation) {
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
        AnimatorSet shrinkFadeOut = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), C1188R.animator.shrink_fade_out);
        shrinkFadeOut.setTarget(this.mIndicator);
        this.mAnimateOut.playTogether(shrinkFadeOut, iconUnfocused);
        this.mAnimateOut.addListener(new Animator.AnimatorListener() {
            public void onAnimationStart(Animator animation) {
            }

            public void onAnimationEnd(Animator animation) {
                HomeTopRowButton.this.mIndicator.setBackgroundResource(0);
            }

            public void onAnimationCancel(Animator animation) {
                HomeTopRowButton.this.mIndicator.setBackgroundResource(0);
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mIcon = (ImageView) findViewById(C1188R.C1191id.button_icon);
        this.mTitle = (TextView) findViewById(C1188R.C1191id.button_title);
        this.mIndicator = findViewById(C1188R.C1191id.button_background);
        this.mIndicator.setOutlineProvider(new ViewOutlineProvider(this) {
            public void getOutline(View view, Outline outline) {
                outline.setOval(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            }
        });
        this.mIndicator.setClipToOutline(true);
        setUpAnimations();
        setOnFocusChangeListener(this.mFocusChangeListener);
    }

    public void setIcon(int id) {
        ImageView imageView = this.mIcon;
        if (imageView != null) {
            imageView.setImageResource(id);
            if (hasFocus()) {
                this.mIcon.getDrawable().setTint(this.mIconFocusedColor);
            }
        }
    }

    public void setIcon(@NonNull Drawable drawable) {
        ImageView imageView = this.mIcon;
        if (imageView != null) {
            imageView.setImageDrawable(drawable);
            if (hasFocus()) {
                drawable.setTint(this.mIconFocusedColor);
            }
        }
    }

    public void setText(int id) {
        TextView textView = this.mTitle;
        if (textView != null) {
            textView.setText(id);
        }
    }

    public void setText(String text) {
        TextView textView = this.mTitle;
        if (textView != null) {
            textView.setText(text);
        }
    }

    /* access modifiers changed from: private */
    public void animateIn() {
        this.mAnimateOut.cancel();
        this.mAnimateIn.start();
        this.mTitle.setSelected(true);
    }

    /* access modifiers changed from: private */
    public void animateOut() {
        this.mAnimateIn.cancel();
        this.mAnimateOut.start();
        this.mTitle.setVisibility(8);
        this.mTitle.setSelected(false);
    }

    @VisibleForTesting(otherwise = 2)
    public Drawable getIcon() {
        ImageView imageView = this.mIcon;
        if (imageView != null) {
            return imageView.getDrawable();
        }
        return null;
    }

    @VisibleForTesting(otherwise = 2)
    public String getTitle() {
        TextView textView = this.mTitle;
        if (textView != null) {
            return textView.getText().toString();
        }
        return null;
    }

    public ImageView getIconImageView() {
        return this.mIcon;
    }
}
