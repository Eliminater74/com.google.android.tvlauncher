package com.google.android.tvlauncher.appsview;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Outline;
import android.support.annotation.Nullable;
import android.support.p001v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.libraries.social.analytics.visualelement.VisualElementTag;
import com.google.android.tvlauncher.C1188R;

public class StoreRowButtonView extends LinearLayout implements View.OnClickListener, View.OnFocusChangeListener {
    private final int mAnimDuration;
    /* access modifiers changed from: private */
    public final int mCornerRadius;
    private final int mElevation;
    private final int mFocusedFillColor;
    private final float mFocusedScale;
    private OnAppsViewActionListener mOnAppsViewActionListener;
    private ImageView mStoreIconView;
    private LaunchItem mStoreItem;
    private TextView mStoreTitleView;
    private final int mUnfocusedFillColor;
    private VisualElementTag mVisualElementTag;

    public StoreRowButtonView(Context context) {
        this(context, null);
    }

    public StoreRowButtonView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StoreRowButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public StoreRowButtonView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mFocusedFillColor = ContextCompat.getColor(getContext(), C1188R.color.store_button_focused_fill);
        this.mUnfocusedFillColor = ContextCompat.getColor(getContext(), C1188R.color.store_button_unfocused_fill);
        Resources res = getResources();
        this.mFocusedScale = res.getFraction(C1188R.fraction.store_button_focused_scale, 1, 1);
        this.mAnimDuration = res.getInteger(C1188R.integer.banner_scale_anim_duration);
        this.mCornerRadius = res.getDimensionPixelSize(C1188R.dimen.store_button_rounded_corner_radius);
        this.mElevation = res.getDimensionPixelSize(C1188R.dimen.store_button_z);
        setOutlineProvider(new ViewOutlineProvider() {
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, StoreRowButtonView.this.getWidth(), StoreRowButtonView.this.getHeight(), (float) StoreRowButtonView.this.mCornerRadius);
            }
        });
        setOnClickListener(this);
        setClipToOutline(true);
        setOnFocusChangeListener(this);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mStoreIconView = (ImageView) findViewById(C1188R.C1191id.store_icon);
        this.mStoreTitleView = (TextView) findViewById(C1188R.C1191id.store_title);
    }

    public ImageView getStoreIconView() {
        return this.mStoreIconView;
    }

    public void setStoreItem(LaunchItem item, OnAppsViewActionListener listener) {
        this.mStoreItem = item;
        this.mOnAppsViewActionListener = listener;
    }

    public void setStoreTitle(String title) {
        if (!TextUtils.equals(title, this.mStoreTitleView.getText())) {
            this.mStoreTitleView.setText(title);
        }
    }

    public void onClick(View view) {
        this.mOnAppsViewActionListener.onStoreLaunch(this.mStoreItem.getIntent(), this.mVisualElementTag, view);
    }

    public void onFocusChange(View v, boolean hasFocus) {
        float scale = hasFocus ? this.mFocusedScale : 1.0f;
        int colorFrom = hasFocus ? this.mUnfocusedFillColor : this.mFocusedFillColor;
        int colorTo = hasFocus ? this.mFocusedFillColor : this.mUnfocusedFillColor;
        int elevationTo = hasFocus ? this.mElevation : 0;
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(colorFrom), Integer.valueOf(colorTo));
        colorAnimation.setDuration((long) this.mAnimDuration);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animator) {
                StoreRowButtonView.this.setBackgroundColor(((Integer) animator.getAnimatedValue()).intValue());
            }
        });
        colorAnimation.start();
        v.animate().scaleX(scale).scaleY(scale).translationZ((float) elevationTo).setDuration((long) this.mAnimDuration);
        this.mStoreTitleView.setSelected(hasFocus);
    }

    public void setVisualElementTag(VisualElementTag visualElementTag) {
        this.mVisualElementTag = visualElementTag;
    }
}
