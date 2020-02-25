package com.google.android.tvlauncher.appsview;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.support.annotation.Nullable;
import android.support.p001v4.content.ContextCompat;
import android.support.p001v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.home.view.FavoriteLaunchItemView;
import com.google.android.tvlauncher.util.Util;
import com.google.android.tvrecommendations.shared.util.ColorUtils;

public class BannerView extends LinearLayout implements FavoriteLaunchItemView {
    /* access modifiers changed from: private */
    public final int mCornerRadius;
    /* access modifiers changed from: private */
    public int mBannerImageHeight;
    /* access modifiers changed from: private */
    public TextView mTitleView;
    private ObjectAnimator mAnimBlink;
    private FrameLayout mBannerContainer;
    private ImageView mBannerImage;
    private float mBannerImageCurrentDimmingFactor;
    private float mBannerImageDimmedFactorValue;
    private boolean mDefaultScaleAnimationsEnabled;
    private float mFocusedScale;
    private float mFocusedZDelta;
    private View mFrame;
    private boolean mIsBeingEdited;
    private LaunchItem mItem;
    private OnEditModeFocusSearchCallback mOnEditModeFocusSearchCallback;
    private OnWindowVisibilityChangedListener mOnWindowVisibilityChangedListener;
    private InstallStateOverlayHelper mOverlayHelper;
    private int mScaleAnimDuration;
    private int mTitleVisibility;
    private int mUnselectedTint;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mDefaultScaleAnimationsEnabled = true;
        Resources res = getResources();
        this.mCornerRadius = res.getDimensionPixelSize(C1188R.dimen.card_rounded_corner_radius);
        this.mUnselectedTint = ContextCompat.getColor(getContext(), C1188R.color.app_banner_image_unselected_tint);
        this.mFocusedZDelta = res.getDimension(C1188R.dimen.app_banner_selected_item_z_delta);
        this.mFocusedScale = res.getFraction(C1188R.fraction.app_banner_focused_scale, 1, 1);
        this.mScaleAnimDuration = res.getInteger(C1188R.integer.banner_scale_anim_duration);
        this.mBannerImageDimmedFactorValue = Util.getFloat(getResources(), C1188R.dimen.unfocused_channel_dimming_factor);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mBannerImage = (ImageView) findViewById(C1188R.C1191id.banner_image);
        this.mBannerContainer = (FrameLayout) findViewById(C1188R.C1191id.banner_container);
        this.mTitleView = (TextView) findViewById(C1188R.C1191id.app_title);
        this.mTitleVisibility = this.mTitleView.getVisibility();
        this.mBannerImageHeight = this.mBannerContainer.getLayoutParams().height;
        this.mBannerContainer.setOutlineProvider(new ViewOutlineProvider() {
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), (float) BannerView.this.mCornerRadius);
            }
        });
        this.mBannerContainer.setClipToOutline(true);
        setOutlineProvider(new ViewOutlineProvider() {
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), BannerView.this.mBannerImageHeight, (float) BannerView.this.mCornerRadius);
            }
        });
        this.mFrame = findViewById(C1188R.C1191id.edit_focused_frame);
        this.mAnimBlink = (ObjectAnimator) AnimatorInflater.loadAnimator(getContext(), C1188R.animator.edit_focused_frame_blink);
        this.mAnimBlink.setTarget(this.mFrame);
    }

    /* access modifiers changed from: protected */
    public void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        OnWindowVisibilityChangedListener onWindowVisibilityChangedListener = this.mOnWindowVisibilityChangedListener;
        if (onWindowVisibilityChangedListener != null) {
            onWindowVisibilityChangedListener.onWindowVisibilityChanged(visibility);
        }
    }

    public View focusSearch(int direction) {
        OnEditModeFocusSearchCallback onEditModeFocusSearchCallback = this.mOnEditModeFocusSearchCallback;
        if (onEditModeFocusSearchCallback == null) {
            return super.focusSearch(direction);
        }
        return onEditModeFocusSearchCallback.onEditModeFocusSearch(direction, super.focusSearch(direction));
    }

    public void setLaunchItem(LaunchItem item) {
        this.mItem = item;
        CharSequence title = item.getLabel();
        if (!TextUtils.equals(title, this.mTitleView.getText())) {
            this.mTitleView.setText(title);
        }
        this.mBannerImage.setContentDescription(this.mItem.getLabel());
        if (this.mItem.isInstalling()) {
            if (this.mOverlayHelper == null) {
                this.mOverlayHelper = new InstallStateOverlayHelper(this);
            }
            this.mOverlayHelper.updateOverlay(this.mItem);
            this.mBannerImage.setVisibility(4);
            this.mFrame.bringToFront();
            return;
        }
        InstallStateOverlayHelper installStateOverlayHelper = this.mOverlayHelper;
        if (installStateOverlayHelper != null) {
            installStateOverlayHelper.removeOverlay();
            this.mBannerImage.setVisibility(0);
        }
    }

    public void setTitle(CharSequence title) {
        if (!TextUtils.equals(title, this.mTitleView.getText())) {
            this.mTitleView.setText(title);
        }
    }

    public void setDimmingEnabled(boolean dimmingEnabled) {
        if (dimmingEnabled) {
            this.mBannerImage.setColorFilter(this.mUnselectedTint);
        } else {
            this.mBannerImage.clearColorFilter();
        }
    }

    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (this.mDefaultScaleAnimationsEnabled) {
            float scale = selected ? this.mFocusedScale : 1.0f;
            float elevation = selected ? this.mFocusedZDelta : 0.0f;
            setDimmingEnabled(!selected);
            animate().z(elevation).scaleX(scale).scaleY(scale).setDuration((long) this.mScaleAnimDuration);
        }
    }

    public void setFocusedState(boolean isFocused) {
        if (this.mDefaultScaleAnimationsEnabled) {
            float destinationAlpha = 1.0f;
            float scale = isFocused ? this.mFocusedScale : 1.0f;
            float elevation = isFocused ? this.mFocusedZDelta : 0.0f;
            if (!isFocused) {
                destinationAlpha = 0.0f;
            }
            this.mTitleView.setSelected(isFocused);
            ObjectAnimator bannerX = ObjectAnimator.ofFloat(this, View.SCALE_X, scale);
            ObjectAnimator bannerY = ObjectAnimator.ofFloat(this, View.SCALE_Y, scale);
            ObjectAnimator bannerZ = ObjectAnimator.ofFloat(this, View.TRANSLATION_Z, elevation);
            ObjectAnimator titleAlpha = ObjectAnimator.ofFloat(this.mTitleView, View.ALPHA, destinationAlpha);
            AnimatorSet animSet = new AnimatorSet();
            animSet.playTogether(bannerX, bannerY, bannerZ, titleAlpha);
            animSet.setDuration((long) this.mScaleAnimDuration);
            animSet.addListener(new AnimatorListenerAdapter() {
                public void onAnimationStart(Animator animation) {
                    BannerView.this.mTitleView.setVisibility(0);
                }

                public void onAnimationEnd(Animator animation) {
                    if (BannerView.this.mTitleView.getAlpha() == 0.0f) {
                        BannerView.this.mTitleView.setVisibility(4);
                    }
                }
            });
            animSet.start();
        }
    }

    public void setOnEditModeFocusSearchCallback(OnEditModeFocusSearchCallback listener) {
        this.mOnEditModeFocusSearchCallback = listener;
    }

    public void setOnWindowVisibilityChangedListener(OnWindowVisibilityChangedListener listener) {
        this.mOnWindowVisibilityChangedListener = listener;
    }

    public void setIsBeingEdited(boolean isBeingEdited) {
        if (isBeingEdited) {
            this.mAnimBlink.start();
            this.mFrame.setVisibility(0);
        } else {
            this.mAnimBlink.cancel();
            this.mFrame.setVisibility(8);
        }
        this.mIsBeingEdited = isBeingEdited;
    }

    public void setDefaultScaleAnimationsEnabled(boolean enable) {
        this.mDefaultScaleAnimationsEnabled = enable;
    }

    public boolean isBeingEdited() {
        return this.mIsBeingEdited;
    }

    public LaunchItem getItem() {
        return this.mItem;
    }

    public ImageView getBannerImage() {
        return this.mBannerImage;
    }

    public FrameLayout getBannerContainer() {
        return this.mBannerContainer;
    }

    public void setBannerImageDimmed(boolean dimmed) {
        if (this.mOverlayHelper != null && this.mItem.isInstalling()) {
            this.mOverlayHelper.setDimmed(dimmed);
        }
        if (dimmed) {
            float f = this.mBannerImageDimmedFactorValue;
            this.mBannerImageCurrentDimmingFactor = f;
            this.mBannerImage.setColorFilter(ColorUtils.getColorFilter(ViewCompat.MEASURED_STATE_MASK, f));
            return;
        }
        this.mBannerImageCurrentDimmingFactor = 0.0f;
        this.mBannerImage.setColorFilter((ColorFilter) null);
    }

    public float getBannerImageDimmingFactor() {
        return this.mBannerImageCurrentDimmingFactor;
    }

    @Nullable
    public ImageView getOverlayBannerView() {
        if (this.mOverlayHelper == null || !this.mItem.isInstalling()) {
            return null;
        }
        return this.mOverlayHelper.getBannerView();
    }

    public float getOverlayBannerViewDimmingFactor() {
        return this.mOverlayHelper.getDimmingFactor();
    }

    public int getCornerRadius() {
        return this.mCornerRadius;
    }

    public void resetAnimations(boolean isFocused) {
        float destinationAlpha = 1.0f;
        float scale = isFocused ? this.mFocusedScale : 1.0f;
        float elevation = isFocused ? this.mFocusedZDelta : 0.0f;
        if (!isFocused) {
            destinationAlpha = 0.0f;
        }
        setTranslationZ(elevation);
        setScaleX(scale);
        setScaleY(scale);
        this.mTitleView.setSelected(isFocused);
        this.mTitleView.setAlpha(destinationAlpha);
    }

    public TextView getTitleView() {
        return this.mTitleView;
    }

    public int getTitleVisibility() {
        return this.mTitleVisibility;
    }

    public void setTitleVisibility(int titleVisibility) {
        this.mTitleVisibility = titleVisibility;
        this.mTitleView.setVisibility(titleVisibility);
    }

    public void recycle() {
        InstallStateOverlayHelper installStateOverlayHelper = this.mOverlayHelper;
        if (installStateOverlayHelper != null) {
            installStateOverlayHelper.recycle();
            this.mOverlayHelper.removeOverlay();
        }
        if (this.mBannerImage.getAnimation() != null) {
            this.mBannerImage.getAnimation().cancel();
        }
    }

    public interface OnWindowVisibilityChangedListener {
        void onWindowVisibilityChanged(int i);
    }
}
