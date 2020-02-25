package com.google.android.tvlauncher.appsview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.content.ContextCompat;
import android.support.p001v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.appsview.palette.InstallingItemPaletteBitmapContainer;
import com.google.android.tvlauncher.appsview.transformation.AppBannerInstallingIconTransformation;
import com.google.android.tvlauncher.util.AddBackgroundColorTransformation;
import com.google.android.tvlauncher.util.Util;
import com.google.android.tvrecommendations.shared.util.ColorUtils;

class InstallStateOverlayHelper {
    @ColorInt
    private static final int FALLBACK_PROGRESS_COLOR = -1;
    /* access modifiers changed from: private */
    public final int mAnimationDuration;
    /* access modifiers changed from: private */
    public final FrameLayout mBannerContainer;
    /* access modifiers changed from: private */
    public final View mOverlay;
    /* access modifiers changed from: private */
    public final ImageView mBannerView = ((ImageView) this.mOverlay.findViewById(C1188R.C1191id.app_install_banner));
    /* access modifiers changed from: private */
    public final ProgressBar mProgressBar = ((ProgressBar) this.mOverlay.findViewById(C1188R.C1191id.progress_bar));
    private final int mBannerMaxHeight;
    private final int mBannerMaxWidth;
    private final ImageView mContainerImageView;
    private final int mDeterminateProgressHeight;
    private final float mDimmedFactorValue;
    private final String mDownloadingString;
    private final float mGrayScaleSaturation;
    private final int mIconBannerBackgroundColor;
    private final float mIconCornerMaxRadius;
    private final float mIconDarkenFactor;
    private final int mIconInstallingMaxSize;
    private final int mIndeterminateProgressBottomMargin;
    private final int mIndeterminateProgressHeight;
    private final String mInstallPendingString;
    private final String mInstallingString;
    private final Drawable mPlaceholderBanner;
    private final String mRestorePendingString;
    private final TextView mTitleView;
    private final String mUpdatePendingString;
    /* access modifiers changed from: private */
    public boolean mIsShowingBanner;
    private Animator mCircleRevealAnimator;
    private float mCurrentDimmingFactor;
    private Drawable mPlaceholderIcon;

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, com.google.android.tvlauncher.appsview.BannerView, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    InstallStateOverlayHelper(BannerView v) {
        Context context = v.getContext();
        this.mOverlay = LayoutInflater.from(context).inflate(C1188R.layout.install_state_overlay, (ViewGroup) v, false);
        Resources res = context.getResources();
        this.mBannerMaxWidth = res.getDimensionPixelSize(C1188R.dimen.app_banner_image_max_width);
        this.mBannerMaxHeight = res.getDimensionPixelSize(C1188R.dimen.app_banner_image_max_height);
        this.mDimmedFactorValue = Util.getFloat(res, C1188R.dimen.unfocused_channel_dimming_factor);
        this.mAnimationDuration = res.getInteger(C1188R.integer.banner_install_anim_duration);
        this.mIconBannerBackgroundColor = context.getColor(C1188R.color.app_banner_background_color_gray_scale);
        float bannerFocusedScale = res.getFraction(C1188R.fraction.home_app_banner_focused_scale, 1, 1);
        this.mIconInstallingMaxSize = res.getDimensionPixelSize(C1188R.dimen.app_banner_installing_icon_max_size);
        this.mIconCornerMaxRadius = ((float) res.getDimensionPixelSize(C1188R.dimen.card_rounded_corner_radius)) * bannerFocusedScale;
        this.mGrayScaleSaturation = Util.getFloat(res, C1188R.dimen.gray_scale_saturation);
        this.mIconDarkenFactor = Util.getFloat(res, C1188R.dimen.install_icon_darken_factor);
        this.mBannerContainer = v.getBannerContainer();
        this.mTitleView = v.getTitleView();
        this.mContainerImageView = (ImageView) this.mBannerContainer.findViewById(C1188R.C1191id.banner_image);
        this.mPlaceholderBanner = new ColorDrawable(ContextCompat.getColor(context, C1188R.color.app_banner_background_color));
        this.mPlaceholderIcon = context.getDrawable(C1188R.C1189drawable.system_default_app_icon_banner);
        this.mDownloadingString = res.getString(C1188R.string.downloading);
        this.mInstallingString = res.getString(C1188R.string.installing);
        this.mInstallPendingString = res.getString(C1188R.string.install_pending);
        this.mUpdatePendingString = res.getString(C1188R.string.update_pending);
        this.mRestorePendingString = res.getString(C1188R.string.restore_pending);
        this.mDeterminateProgressHeight = res.getDimensionPixelSize(C1188R.dimen.install_progress_bar_height);
        this.mIndeterminateProgressHeight = res.getDimensionPixelSize(C1188R.dimen.install_progress_bar_indeterminate_height);
        this.mIndeterminateProgressBottomMargin = res.getDimensionPixelSize(C1188R.dimen.install_progress_bar_indeterminate_bottom_margin);
    }

    /* access modifiers changed from: package-private */
    public void updateOverlay(LaunchItem item) {
        Context context = this.mBannerContainer.getContext();
        int progressPercent = item.getInstallProgressPercent();
        switch (item.getInstallState()) {
            case UNKNOWN:
                this.mProgressBar.setIndeterminate(true);
                this.mProgressBar.setProgress(0);
                this.mProgressBar.setRotation(0.0f);
                this.mProgressBar.setLayoutParams(getProgressLayoutParams());
                break;
            case INSTALL_PENDING:
                this.mProgressBar.setIndeterminate(true);
                this.mProgressBar.setProgress(0);
                this.mProgressBar.setRotation(0.0f);
                this.mProgressBar.setLayoutParams(getProgressLayoutParams());
                this.mTitleView.setText(this.mInstallPendingString);
                break;
            case UPDATE_PENDING:
                this.mProgressBar.setIndeterminate(true);
                this.mProgressBar.setProgress(0);
                this.mProgressBar.setRotation(0.0f);
                this.mProgressBar.setLayoutParams(getProgressLayoutParams());
                this.mTitleView.setText(this.mUpdatePendingString);
                break;
            case RESTORE_PENDING:
                this.mProgressBar.setIndeterminate(true);
                this.mProgressBar.setProgress(0);
                this.mProgressBar.setRotation(0.0f);
                this.mProgressBar.setLayoutParams(getProgressLayoutParams());
                this.mTitleView.setText(this.mRestorePendingString);
                break;
            case INSTALLING:
                this.mProgressBar.setIndeterminate(true);
                this.mProgressBar.setProgress(0);
                this.mProgressBar.setRotation(180.0f);
                this.mProgressBar.setLayoutParams(getProgressLayoutParams());
                this.mTitleView.setText(this.mInstallingString);
                break;
            case DOWNLOADING:
                this.mProgressBar.setIndeterminate(false);
                this.mProgressBar.setProgress(progressPercent);
                this.mProgressBar.setRotation(0.0f);
                this.mProgressBar.setLayoutParams(getProgressLayoutParams());
                this.mTitleView.setText(this.mDownloadingString);
                break;
            default:
                throw new IllegalStateException("Invalid install state.");
        }
        if (this.mOverlay.getParent() == null) {
            addOverlay();
            setInstallingGraphic(item, context);
        }
    }

    private FrameLayout.LayoutParams getProgressLayoutParams() {
        if (this.mProgressBar.isIndeterminate()) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, this.mIndeterminateProgressHeight);
            layoutParams.gravity = 80;
            layoutParams.setMargins(0, 0, 0, this.mIndeterminateProgressBottomMargin);
            return layoutParams;
        }
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, this.mDeterminateProgressHeight);
        layoutParams2.gravity = 80;
        return layoutParams2;
    }

    private void setInstallingGraphic(LaunchItem item, Context context) {
        if (item.getBannerUri() != null) {
            this.mIsShowingBanner = true;
            Glide.with(context).mo11716as(InstallingItemPaletteBitmapContainer.class).error(createIconBannerBuilder(item, context)).load(item.getBannerUri()).apply((BaseRequestOptions<?>) ((RequestOptions) ((RequestOptions) ((RequestOptions) new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)).placeholder(this.mPlaceholderBanner)).transform(new AddBackgroundColorTransformation(context.getColor(C1188R.color.app_banner_background_color_gray_scale), true)))).into(new ImageViewTarget<InstallingItemPaletteBitmapContainer>(this.mBannerView) {
                /* access modifiers changed from: protected */
                public void setResource(@Nullable InstallingItemPaletteBitmapContainer resource) {
                    if (resource != null) {
                        InstallStateOverlayHelper.this.mBannerView.setImageBitmap(resource.getBitmap());
                        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                        alphaAnimation.setDuration((long) InstallStateOverlayHelper.this.mAnimationDuration);
                        alphaAnimation.setFillAfter(true);
                        int vibrantColor = resource.getPalette().getVibrantColor(-1);
                        InstallStateOverlayHelper.this.mProgressBar.getProgressDrawable().setTint(vibrantColor);
                        InstallStateOverlayHelper.this.mProgressBar.getIndeterminateDrawable().setTint(vibrantColor);
                        InstallStateOverlayHelper installStateOverlayHelper = InstallStateOverlayHelper.this;
                        installStateOverlayHelper.cancelCurrentAnimationAndStartAnimation(installStateOverlayHelper.mBannerView, alphaAnimation);
                    }
                }

                public void onLoadFailed(@Nullable Drawable resource) {
                    super.onLoadFailed(resource);
                    boolean unused = InstallStateOverlayHelper.this.mIsShowingBanner = false;
                }
            });
            return;
        }
        this.mIsShowingBanner = false;
        createIconBannerBuilder(item, context).into(new ImageViewTarget<InstallingItemPaletteBitmapContainer>(this.mBannerView) {
            /* access modifiers changed from: protected */
            public void setResource(@Nullable InstallingItemPaletteBitmapContainer resource) {
                if (resource != null) {
                    AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                    alphaAnimation.setDuration((long) InstallStateOverlayHelper.this.mAnimationDuration);
                    alphaAnimation.setFillAfter(true);
                    InstallStateOverlayHelper.this.mBannerView.setImageBitmap(resource.getBitmap());
                    InstallStateOverlayHelper installStateOverlayHelper = InstallStateOverlayHelper.this;
                    installStateOverlayHelper.cancelCurrentAnimationAndStartAnimation(installStateOverlayHelper.mBannerView, alphaAnimation);
                }
            }
        });
    }

    private RequestBuilder<InstallingItemPaletteBitmapContainer> createIconBannerBuilder(LaunchItem item, Context context) {
        RequestOptions requestOptions = new RequestOptions();
        int i = this.mIconInstallingMaxSize;
        return Glide.with(context).mo11716as(InstallingItemPaletteBitmapContainer.class).load(item.getIconUri()).apply((BaseRequestOptions<?>) ((RequestOptions) ((RequestOptions) ((RequestOptions) ((RequestOptions) requestOptions.override(i, i)).fallback(this.mPlaceholderIcon)).diskCacheStrategy(DiskCacheStrategy.NONE)).transform(new AppBannerInstallingIconTransformation(this.mIconBannerBackgroundColor, this.mIconDarkenFactor, this.mGrayScaleSaturation, this.mBannerMaxWidth, this.mBannerMaxHeight, this.mIconCornerMaxRadius))));
    }

    /* access modifiers changed from: package-private */
    public void removeOverlay() {
        if (this.mOverlay.getParent() == null) {
            return;
        }
        if (this.mIsShowingBanner && this.mContainerImageView.isAttachedToWindow()) {
            Animator animator = this.mCircleRevealAnimator;
            if (animator != null && animator.isStarted()) {
                this.mCircleRevealAnimator.end();
            }
            int cx = (this.mContainerImageView.getWidth() * 3) / 4;
            int cy = (this.mContainerImageView.getHeight() * 3) / 4;
            this.mCircleRevealAnimator = ViewAnimationUtils.createCircularReveal(this.mContainerImageView, cx, cy, 0.0f, (float) Math.hypot((double) cx, (double) cy));
            this.mCircleRevealAnimator.setDuration((long) this.mAnimationDuration);
            this.mCircleRevealAnimator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    InstallStateOverlayHelper.this.mBannerContainer.removeView(InstallStateOverlayHelper.this.mOverlay);
                }
            });
            this.mContainerImageView.setVisibility(0);
            if (this.mContainerImageView.getAnimation() != null) {
                this.mContainerImageView.getAnimation().cancel();
            }
            this.mCircleRevealAnimator.start();
        } else if (this.mIsShowingBanner || !this.mContainerImageView.isAttachedToWindow()) {
            this.mBannerContainer.removeView(this.mOverlay);
        } else {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration((long) this.mAnimationDuration);
            alphaAnimation.setFillAfter(true);
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    InstallStateOverlayHelper.this.mBannerContainer.removeView(InstallStateOverlayHelper.this.mOverlay);
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
            cancelCurrentAnimationAndStartAnimation(this.mOverlay, alphaAnimation);
        }
    }

    public void setDimmed(boolean dimmed) {
        if (dimmed) {
            float f = this.mDimmedFactorValue;
            this.mCurrentDimmingFactor = f;
            this.mBannerView.setColorFilter(ColorUtils.getColorFilter(ViewCompat.MEASURED_STATE_MASK, f));
            return;
        }
        this.mCurrentDimmingFactor = 0.0f;
        this.mBannerView.setColorFilter((ColorFilter) null);
    }

    /* access modifiers changed from: package-private */
    public ImageView getBannerView() {
        return this.mBannerView;
    }

    /* access modifiers changed from: package-private */
    public float getDimmingFactor() {
        return this.mCurrentDimmingFactor;
    }

    /* access modifiers changed from: package-private */
    public void recycle() {
        if (this.mOverlay.getAnimation() != null) {
            this.mOverlay.getAnimation().cancel();
        }
        if (this.mBannerView.getAnimation() != null) {
            this.mBannerView.getAnimation().cancel();
        }
        if (this.mContainerImageView.getAnimation() != null) {
            this.mContainerImageView.getAnimation().cancel();
        }
        Animator animator = this.mCircleRevealAnimator;
        if (animator != null) {
            animator.end();
        }
    }

    /* access modifiers changed from: private */
    public void cancelCurrentAnimationAndStartAnimation(View v, Animation animation) {
        if (v.getAnimation() != null) {
            v.getAnimation().cancel();
        }
        v.startAnimation(animation);
    }

    private void addOverlay() {
        this.mBannerContainer.addView(this.mOverlay, 0);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration((long) this.mAnimationDuration);
        alphaAnimation.setFillAfter(true);
        cancelCurrentAnimationAndStartAnimation(this.mOverlay, alphaAnimation);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean isOverlayOnBannerContainer() {
        FrameLayout frameLayout = this.mBannerContainer;
        return frameLayout != null && frameLayout.indexOfChild(this.mOverlay) >= 0;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean isProgressBarIndeterminate() {
        return this.mProgressBar.isIndeterminate();
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public int getPercentage() {
        return this.mProgressBar.getProgress();
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public CharSequence getTitle() {
        return this.mTitleView.getText();
    }
}
