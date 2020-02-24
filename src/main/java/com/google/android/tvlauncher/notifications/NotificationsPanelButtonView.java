package com.google.android.tvlauncher.notifications;

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
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.tvlauncher.C1188R;

public class NotificationsPanelButtonView extends LinearLayout {
    private final int mAllNotifsSeenUnfocusedColor;
    /* access modifiers changed from: private */
    public AnimatorSet mAnimateIn;
    /* access modifiers changed from: private */
    public AnimatorSet mAnimateOut;
    private final int mAnimationDuration;
    private ImageView mCircle;
    private float mDefaultTextSize;
    /* access modifiers changed from: private */
    public View mFocusIndicator;
    private final String mGreaterThanNineText;
    private float mGreaterThanNineTextSize;
    /* access modifiers changed from: private */
    public final int mIndicatorFocusedColor;
    /* access modifiers changed from: private */
    public TextView mNotifCountTextView;
    /* access modifiers changed from: private */
    public TextView mNotificationButtonTitle;
    private final int mNotifsFocusedBackgroundColor;
    private final String mNumberFormat;
    /* access modifiers changed from: private */
    public boolean mSeen;
    /* access modifiers changed from: private */
    public Drawable mSeenBackground;
    /* access modifiers changed from: private */
    public ValueAnimator mSeenIconColorFocusedAnimator;
    /* access modifiers changed from: private */
    public ValueAnimator mSeenIconColorUnfocusedAnimator;
    /* access modifiers changed from: private */
    public ObjectAnimator mTextFadeInAnimator;
    private Drawable mUnseenBackground;
    /* access modifiers changed from: private */
    public ObjectAnimator mUnseenIconColorFocusedAnimator;
    /* access modifiers changed from: private */
    public ObjectAnimator mUnseenIconColorUnfocusedAnimator;
    private int mUnseenNotifsUnfocusedBackgroundColor;
    /* access modifiers changed from: private */
    public ObjectAnimator mUnseenTextColorFocusedAnimator;
    /* access modifiers changed from: private */
    public ObjectAnimator mUnseenTextColorUnfocusedAnimator;
    private int mUnseenTextUnfocusedColor;

    public NotificationsPanelButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Resources resources = context.getResources();
        this.mAllNotifsSeenUnfocusedColor = resources.getColor(C1188R.color.white_60, null);
        this.mNotifsFocusedBackgroundColor = resources.getColor(C1188R.color.notification_panel_icon_focused_color, null);
        this.mUnseenNotifsUnfocusedBackgroundColor = resources.getColor(C1188R.color.notification_panel_icon_unseen_color, null);
        this.mIndicatorFocusedColor = resources.getColor(C1188R.color.reference_white_100, null);
        this.mUnseenTextUnfocusedColor = resources.getColor(C1188R.color.notification_panel_icon_text_unfocused_color, null);
        this.mAnimationDuration = resources.getInteger(C1188R.integer.top_row_button_animation_duration_ms);
        this.mNumberFormat = resources.getString(C1188R.string.number_format);
        this.mGreaterThanNineText = resources.getString(C1188R.string.greater_than_nine_notifs_text);
        this.mDefaultTextSize = resources.getDimension(C1188R.dimen.text_size_h4);
        this.mGreaterThanNineTextSize = resources.getDimension(C1188R.dimen.text_size_h5);
        this.mSeenBackground = resources.getDrawable(C1188R.C1189drawable.hollow_circle_background, null);
        this.mSeenBackground.setTint(this.mAllNotifsSeenUnfocusedColor);
        this.mUnseenBackground = resources.getDrawable(C1188R.C1189drawable.full_circle_background, null);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mFocusIndicator = findViewById(C1188R.C1191id.button_background);
        this.mCircle = (ImageView) findViewById(C1188R.C1191id.notification_panel_background_circle);
        this.mCircle.setImageDrawable(this.mSeenBackground);
        this.mNotifCountTextView = (TextView) findViewById(C1188R.C1191id.notification_panel_count);
        this.mNotificationButtonTitle = (TextView) findViewById(C1188R.C1191id.button_title);
        this.mNotificationButtonTitle.setText(C1188R.string.notifications_panel_icon_title);
        this.mFocusIndicator.setOutlineProvider(new ViewOutlineProvider(this) {
            public void getOutline(View view, Outline outline) {
                outline.setOval(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            }
        });
        this.mFocusIndicator.setClipToOutline(true);
        setUpAnimations();
        setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean focused) {
                if (focused) {
                    NotificationsPanelButtonView.this.mAnimateOut.cancel();
                    if (NotificationsPanelButtonView.this.mSeen) {
                        NotificationsPanelButtonView.this.mSeenIconColorFocusedAnimator.start();
                    } else {
                        NotificationsPanelButtonView.this.mUnseenIconColorFocusedAnimator.start();
                        NotificationsPanelButtonView.this.mUnseenTextColorFocusedAnimator.start();
                    }
                    NotificationsPanelButtonView.this.mTextFadeInAnimator.start();
                    NotificationsPanelButtonView.this.mAnimateIn.start();
                } else {
                    NotificationsPanelButtonView.this.mAnimateIn.cancel();
                    if (NotificationsPanelButtonView.this.mSeen) {
                        NotificationsPanelButtonView.this.mSeenIconColorUnfocusedAnimator.start();
                    } else {
                        NotificationsPanelButtonView.this.mUnseenIconColorUnfocusedAnimator.start();
                        NotificationsPanelButtonView.this.mUnseenTextColorUnfocusedAnimator.start();
                    }
                    NotificationsPanelButtonView.this.mAnimateOut.start();
                    NotificationsPanelButtonView.this.mNotificationButtonTitle.setVisibility(8);
                }
                NotificationsPanelButtonView.this.mNotificationButtonTitle.setSelected(focused);
            }
        });
    }

    private void setUpAnimations() {
        this.mTextFadeInAnimator = ObjectAnimator.ofFloat(this.mNotificationButtonTitle, "alpha", 0.0f, 1.0f);
        this.mTextFadeInAnimator.setDuration((long) this.mAnimationDuration);
        this.mTextFadeInAnimator.addListener(new Animator.AnimatorListener() {
            public void onAnimationStart(Animator animation) {
                NotificationsPanelButtonView.this.mNotificationButtonTitle.setVisibility(0);
            }

            public void onAnimationEnd(Animator animation) {
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
        this.mSeenIconColorFocusedAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(this.mAllNotifsSeenUnfocusedColor), Integer.valueOf(this.mNotifsFocusedBackgroundColor));
        this.mSeenIconColorFocusedAnimator.setDuration((long) this.mAnimationDuration);
        this.mSeenIconColorFocusedAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                NotificationsPanelButtonView.this.mNotifCountTextView.setTextColor(value);
                if (NotificationsPanelButtonView.this.mSeenBackground != null) {
                    NotificationsPanelButtonView.this.mSeenBackground.setTint(value);
                }
            }
        });
        this.mSeenIconColorUnfocusedAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(this.mNotifsFocusedBackgroundColor), Integer.valueOf(this.mAllNotifsSeenUnfocusedColor));
        this.mSeenIconColorUnfocusedAnimator.setDuration((long) this.mAnimationDuration);
        this.mSeenIconColorUnfocusedAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                NotificationsPanelButtonView.this.mNotifCountTextView.setTextColor(value);
                if (NotificationsPanelButtonView.this.mSeenBackground != null) {
                    NotificationsPanelButtonView.this.mSeenBackground.setTint(value);
                }
            }
        });
        this.mUnseenIconColorFocusedAnimator = ObjectAnimator.ofArgb(this.mUnseenBackground, "tint", this.mUnseenNotifsUnfocusedBackgroundColor, this.mNotifsFocusedBackgroundColor);
        this.mUnseenIconColorFocusedAnimator.setDuration((long) this.mAnimationDuration);
        this.mUnseenIconColorUnfocusedAnimator = ObjectAnimator.ofArgb(this.mUnseenBackground, "tint", this.mNotifsFocusedBackgroundColor, this.mUnseenNotifsUnfocusedBackgroundColor);
        this.mUnseenIconColorUnfocusedAnimator.setDuration((long) this.mAnimationDuration);
        this.mUnseenTextColorFocusedAnimator = ObjectAnimator.ofArgb(this.mNotifCountTextView, "textColor", this.mUnseenTextUnfocusedColor, this.mIndicatorFocusedColor);
        this.mUnseenTextColorFocusedAnimator.setDuration((long) this.mAnimationDuration);
        this.mUnseenTextColorUnfocusedAnimator = ObjectAnimator.ofArgb(this.mNotifCountTextView, "textColor", this.mIndicatorFocusedColor, this.mUnseenTextUnfocusedColor);
        this.mUnseenTextColorUnfocusedAnimator.setDuration((long) this.mAnimationDuration);
        this.mAnimateIn = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), C1188R.animator.expand_fade_in);
        this.mAnimateIn.setTarget(this.mFocusIndicator);
        this.mAnimateIn.addListener(new Animator.AnimatorListener() {
            public void onAnimationStart(Animator animation) {
                NotificationsPanelButtonView.this.mFocusIndicator.setBackgroundColor(NotificationsPanelButtonView.this.mIndicatorFocusedColor);
            }

            public void onAnimationEnd(Animator animation) {
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
        this.mAnimateOut = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), C1188R.animator.shrink_fade_out);
        this.mAnimateOut.setTarget(this.mFocusIndicator);
        this.mAnimateOut.addListener(new Animator.AnimatorListener() {
            public void onAnimationStart(Animator animation) {
            }

            public void onAnimationEnd(Animator animation) {
                NotificationsPanelButtonView.this.mFocusIndicator.setBackgroundResource(0);
            }

            public void onAnimationCancel(Animator animation) {
                NotificationsPanelButtonView.this.mFocusIndicator.setBackgroundResource(0);
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    public void setCount(int count) {
        if (count > 9) {
            this.mNotifCountTextView.setTextSize(0, this.mGreaterThanNineTextSize);
            this.mNotifCountTextView.setText(this.mGreaterThanNineText);
        } else {
            this.mNotifCountTextView.setTextSize(0, this.mDefaultTextSize);
            this.mNotifCountTextView.setText(String.format(this.mNumberFormat, Integer.valueOf(count)));
        }
        setContentDescription(getResources().getQuantityString(C1188R.plurals.notification_panel_icon_accessibility_description, count, Integer.valueOf(count)));
    }

    public void setSeenState(boolean seen) {
        int i;
        this.mSeen = seen;
        if (this.mSeen) {
            this.mSeenBackground.setTint(hasFocus() ? this.mNotifsFocusedBackgroundColor : this.mAllNotifsSeenUnfocusedColor);
            this.mCircle.setImageDrawable(this.mSeenBackground);
            this.mNotifCountTextView.setTextColor(hasFocus() ? this.mNotifsFocusedBackgroundColor : this.mAllNotifsSeenUnfocusedColor);
            return;
        }
        Drawable drawable = this.mUnseenBackground;
        if (hasFocus()) {
            i = this.mNotifsFocusedBackgroundColor;
        } else {
            i = this.mUnseenNotifsUnfocusedBackgroundColor;
        }
        drawable.setTint(i);
        this.mCircle.setImageDrawable(this.mUnseenBackground);
        this.mNotifCountTextView.setTextColor(hasFocus() ? this.mIndicatorFocusedColor : this.mUnseenTextUnfocusedColor);
    }

    @VisibleForTesting
    public Drawable getUnseenBackground() {
        return this.mUnseenBackground;
    }

    @VisibleForTesting
    public Drawable getSeenBackground() {
        return this.mSeenBackground;
    }
}
