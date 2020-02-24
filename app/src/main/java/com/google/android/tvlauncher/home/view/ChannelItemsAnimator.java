package com.google.android.tvlauncher.home.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p001v4.view.ViewCompat;
import android.support.p004v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.tvlauncher.appsview.BannerView;
import com.google.android.tvlauncher.home.ChannelItemsAdapter;
import com.google.android.tvlauncher.home.ProgramBindPayloads;
import com.google.android.tvlauncher.util.Util;
import com.google.android.tvrecommendations.shared.util.AnimUtil;
import com.google.android.tvrecommendations.shared.util.ColorUtils;
import com.google.android.tvrecommendations.shared.view.BoundsItemAnimator;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChannelItemsAnimator extends BoundsItemAnimator {
    private static final int CHANGE_DURATION_MS = 175;
    private static final boolean DEBUG = false;
    private static final Interpolator DELAY_WATCH_NEXT_INFO_CARD_APPEARANCE_INTERPOLATOR = new AccelerateInterpolator(3.0f);
    private static final float EPS = 0.001f;
    private static final Interpolator FAST_WATCH_NEXT_INFO_CARD_DISAPPEARANCE_INTERPOLATOR = new DecelerateInterpolator(3.0f);
    private static final String TAG = "ChannelItemsAnimator";
    /* access modifiers changed from: private */
    public Set<Animator> mCanceledAnimations = new HashSet();
    private final Interpolator mMainInterpolator;

    public /* bridge */ /* synthetic */ boolean animateAdd(RecyclerView.ViewHolder viewHolder) {
        return super.animateAdd(viewHolder);
    }

    public /* bridge */ /* synthetic */ boolean animateChange(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2, int i, int i2, int i3, int i4) {
        return super.animateChange(viewHolder, viewHolder2, i, i2, i3, i4);
    }

    public /* bridge */ /* synthetic */ boolean animateRemove(RecyclerView.ViewHolder viewHolder) {
        return super.animateRemove(viewHolder);
    }

    public /* bridge */ /* synthetic */ boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull List list) {
        return super.canReuseUpdatedViewHolder(viewHolder, list);
    }

    public /* bridge */ /* synthetic */ void runPendingAnimations() {
        super.runPendingAnimations();
    }

    public ChannelItemsAnimator() {
        setChangeDuration(175);
        this.mMainInterpolator = new DecelerateInterpolator();
    }

    @NonNull
    public RecyclerView.ItemAnimator.ItemHolderInfo recordPreLayoutInformation(@NonNull RecyclerView.State state, @NonNull RecyclerView.ViewHolder viewHolder, int changeFlags, @NonNull List<Object> payloads) {
        RecyclerView.ItemAnimator.ItemHolderInfo info = super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads);
        ((MyHolderInfo) info).payloads = new ArrayList(payloads);
        return info;
    }

    /* access modifiers changed from: protected */
    public boolean animateInPlaceChange(@NonNull final RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo preLayoutInfo, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo postLayoutInfo) {
        Animator runningAnimation = (Animator) this.mChangeAnimations.get(viewHolder);
        if (runningAnimation != null) {
            this.mCanceledAnimations.add(runningAnimation);
        }
        cancelChangeAnimation(viewHolder);
        final MyHolderInfo preInfo = (MyHolderInfo) preLayoutInfo;
        final MyHolderInfo postInfo = (MyHolderInfo) postLayoutInfo;
        List<Animator> animators = new ArrayList<>();
        if (viewHolder.itemView instanceof ProgramView) {
            ProgramView programView = (ProgramView) viewHolder.itemView;
            AnimUtil.addIfNotNull(animators, AnimUtil.createBoundsAnimator(programView, preInfo.bounds, postInfo.bounds, this.mMainInterpolator));
            addMainContainerAnimations(animators, viewHolder.itemView, preInfo, postInfo);
            addPreviewImageBackgroundAnimations(animators, programView, preInfo, postInfo);
            addPreviewImageAnimations(animators, programView, preInfo, postInfo);
            addPreviewDelayAnimations(animators, programView, preInfo, postInfo);
            addPlaybackProgressAnimations(animators, programView, preInfo, postInfo);
            addLogoAnimations(animators, programView, preInfo, postInfo);
            addBadgeAnimations(animators, programView, preInfo, postInfo);
        } else if (viewHolder.itemView instanceof FavoriteLaunchItemView) {
            addFavoriteAppsRowViewAnimations(animators, (FavoriteLaunchItemView) viewHolder.itemView, preInfo, postInfo);
        } else if (viewHolder.itemView instanceof WatchNextInfoView) {
            addWatchNextInfoViewAnimations(animators, (WatchNextInfoView) viewHolder.itemView, preInfo, postInfo);
        }
        if (animators.isEmpty()) {
            dispatchChangeFinished(viewHolder, true);
            return false;
        }
        AnimatorSet overallAnimation = new AnimatorSet();
        overallAnimation.playTogether(animators);
        overallAnimation.setDuration(getChangeDuration());
        overallAnimation.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                ChannelItemsAnimator.this.dispatchChangeStarting(viewHolder, true);
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationEnd(Animator animation) {
                animation.removeListener(this);
                boolean wasCanceled = ChannelItemsAnimator.this.mCanceledAnimations.remove(animation);
                if (viewHolder.itemView instanceof ProgramView) {
                    ProgramView programView = (ProgramView) viewHolder.itemView;
                    AnimUtil.setBounds(programView, postInfo.bounds);
                    ChannelItemsAnimator.this.cleanUpAfterMainContainerAnimations(programView, postInfo);
                    ChannelItemsAnimator.this.cleanUpAfterPreviewImageBackgroundAnimations(programView);
                    ChannelItemsAnimator.this.cleanUpAfterPreviewImageAnimations(programView, postInfo);
                    ChannelItemsAnimator.this.cleanUpAfterPreviewDelayAnimations(programView);
                    ChannelItemsAnimator.this.cleanupAfterPlaybackProgressAnimations(programView);
                    ChannelItemsAnimator.this.cleanupAfterLogoAnimations(programView, postInfo);
                    ChannelItemsAnimator.this.cleanupAfterBadgeAnimations(programView, postInfo);
                    if (!wasCanceled && !viewHolder.itemView.isFocused() && preInfo.payloads != null && preInfo.payloads.contains(ProgramBindPayloads.FOCUS_CHANGED)) {
                        RecyclerView.ViewHolder viewHolder = viewHolder;
                        if (viewHolder instanceof ChannelItemsAdapter.ProgramViewHolder) {
                            ((ChannelItemsAdapter.ProgramViewHolder) viewHolder).getProgramController().clearPreviewImageBackgroundIfPossible();
                        }
                    }
                } else if (viewHolder.itemView instanceof FavoriteLaunchItemView) {
                    ChannelItemsAnimator.this.cleanUpAfterFavoriteAppsRowViewViewAnimations((FavoriteLaunchItemView) viewHolder.itemView, postInfo);
                } else if (viewHolder.itemView instanceof WatchNextInfoView) {
                    ChannelItemsAnimator.this.cleanUpAfterWatchNextInfoViewAnimations((WatchNextInfoView) viewHolder.itemView, postInfo);
                }
                ChannelItemsAnimator.this.dispatchChangeFinished(viewHolder, true);
                ChannelItemsAnimator.this.mChangeAnimations.remove(viewHolder);
                ChannelItemsAnimator.this.dispatchFinishedWhenDone();
            }
        });
        this.mChangeAnimations.put(viewHolder, overallAnimation);
        overallAnimation.start();
        return true;
    }

    private void addMainContainerAnimations(List<Animator> animators, @NonNull View view, MyHolderInfo preInfo, MyHolderInfo postInfo) {
        Animator scaleAnimator = AnimUtil.createScaleAnimator(view, preInfo.scale, postInfo.scale, this.mMainInterpolator);
        if (scaleAnimator != null) {
            view.setPivotY(Math.max(preInfo.pivotY, postInfo.pivotY));
            animators.add(scaleAnimator);
        }
        AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationZAnimator(view, preInfo.elevation, postInfo.elevation, preInfo.translationZ, this.mMainInterpolator));
    }

    /* access modifiers changed from: private */
    public void cleanUpAfterMainContainerAnimations(View view, MyHolderInfo postInfo) {
        view.setPivotY(postInfo.pivotY);
        view.setScaleX(postInfo.scale);
        view.setScaleY(postInfo.scale);
        view.setTranslationZ(0.0f);
    }

    private void addPreviewImageBackgroundAnimations(List<Animator> animators, ProgramView view, MyHolderInfo preInfo, MyHolderInfo postInfo) {
        ImageView previewImageBackground = view.getPreviewImageBackground();
        AnimUtil.addIfNotNull(animators, AnimUtil.createScaleAnimator(previewImageBackground, preInfo.previewImageBackgroundBounds, postInfo.previewImageBackgroundBounds, preInfo.previewImageBackgroundScale, 0.0f, (float) (previewImageBackground.getHeight() / 2), this.mMainInterpolator));
        int mainContainerPreHeight = preInfo.bounds.height();
        int mainContainerPostHeight = postInfo.bounds.height();
        if (mainContainerPreHeight != mainContainerPostHeight) {
            animators.add(ObjectAnimator.ofFloat(previewImageBackground, View.TRANSLATION_Y, (float) ((mainContainerPreHeight - mainContainerPostHeight) / 2), 0.0f));
        }
    }

    /* access modifiers changed from: private */
    public void cleanUpAfterPreviewImageBackgroundAnimations(ProgramView view) {
        View previewImageBackground = view.getPreviewImageBackground();
        previewImageBackground.setScaleX(1.0f);
        previewImageBackground.setScaleY(1.0f);
        previewImageBackground.setTranslationY(0.0f);
    }

    private void addPreviewImageAnimations(List<Animator> animators, ProgramView view, MyHolderInfo preInfo, MyHolderInfo postInfo) {
        AnimUtil.addIfNotNull(animators, AnimUtil.createBoundsAnimator(view.getPreviewImageContainer(), preInfo.previewImageContainerBounds, postInfo.previewImageContainerBounds, this.mMainInterpolator));
        ImageView previewImage = view.getPreviewImage();
        AnimUtil.addIfNotNull(animators, AnimUtil.createScaleAnimator(previewImage, preInfo.previewImageBounds, postInfo.previewImageBounds, preInfo.previewImageScale, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationXYAnimator(previewImage, preInfo.previewImageBounds.left, preInfo.previewImageBounds.top, postInfo.previewImageBounds.left, postInfo.previewImageBounds.top, preInfo.previewImageTranslationX, preInfo.previewImageTranslationY, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createColorFilterAnimator(previewImage, ViewCompat.MEASURED_STATE_MASK, preInfo.dimmingFactor, postInfo.dimmingFactor, this.mMainInterpolator));
    }

    /* access modifiers changed from: private */
    public void cleanUpAfterPreviewImageAnimations(ProgramView view, MyHolderInfo postInfo) {
        AnimUtil.setBounds(view.getPreviewImageContainer(), postInfo.previewImageContainerBounds);
        ImageView previewImage = view.getPreviewImage();
        previewImage.setScaleX(1.0f);
        previewImage.setScaleY(1.0f);
        previewImage.setTranslationX(0.0f);
        previewImage.setTranslationY(0.0f);
        if (postInfo.dimmingFactor < EPS) {
            previewImage.setColorFilter((ColorFilter) null);
        } else {
            previewImage.setColorFilter(ColorUtils.getColorFilter(ViewCompat.MEASURED_STATE_MASK, postInfo.dimmingFactor));
        }
    }

    private void addPreviewDelayAnimations(List<Animator> animators, ProgramView programView, MyHolderInfo preInfo, MyHolderInfo postInfo) {
        AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationXYAnimator(programView.getPreviewDelayProgress(), preInfo.previewDelayProgressLeft, preInfo.previewDelayProgressTop, postInfo.previewDelayProgressLeft, postInfo.previewDelayProgressTop, preInfo.previewDelayProgressTranslationX, postInfo.previewDelayProgressTranslationY, this.mMainInterpolator));
    }

    /* access modifiers changed from: private */
    public void cleanUpAfterPreviewDelayAnimations(ProgramView programView) {
        View previewDelayProgress = programView.getPreviewDelayProgress();
        previewDelayProgress.setTranslationX(0.0f);
        previewDelayProgress.setTranslationY(0.0f);
    }

    private void addPlaybackProgressAnimations(List<Animator> animators, ProgramView programView, MyHolderInfo preInfo, MyHolderInfo postInfo) {
        View playbackProgressDimmer = programView.getPlaybackProgressDimmer();
        AnimUtil.addIfNotNull(animators, AnimUtil.createScaleXAnimator(playbackProgressDimmer, preInfo.playbackProgressDimmerWidth, postInfo.playbackProgressDimmerWidth, preInfo.playbackProgressDimmerScaleX, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationYAnimator(playbackProgressDimmer, preInfo.playbackProgressDimmerTop, postInfo.playbackProgressDimmerTop, preInfo.playbackProgressDimmerTranslationY, this.mMainInterpolator));
        View playbackProgress = programView.getPlaybackProgress();
        AnimUtil.addIfNotNull(animators, AnimUtil.createScaleXAnimator(playbackProgress, preInfo.playbackProgressWidth, postInfo.playbackProgressWidth, preInfo.playbackProgressScaleX, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationYAnimator(playbackProgress, preInfo.playbackProgressTop, postInfo.playbackProgressTop, preInfo.playbackProgressTranslationY, this.mMainInterpolator));
    }

    /* access modifiers changed from: private */
    public void cleanupAfterPlaybackProgressAnimations(ProgramView programView) {
        View playbackProgressDimmer = programView.getPlaybackProgressDimmer();
        playbackProgressDimmer.setScaleX(1.0f);
        playbackProgressDimmer.setTranslationY(0.0f);
        View playbackProgress = programView.getPlaybackProgress();
        playbackProgress.setScaleX(1.0f);
        playbackProgress.setTranslationY(0.0f);
    }

    private void addLogoAnimations(List<Animator> animators, ProgramView programView, MyHolderInfo preInfo, MyHolderInfo postInfo) {
        AnimUtil.addIfNotNull(animators, AnimUtil.createBoundsAnimator(programView.getLogoAndBadgesContainer(), preInfo.logoAndBadgesContainerBounds, postInfo.logoAndBadgesContainerBounds, this.mMainInterpolator));
        View logoDimmer = programView.getLogoDimmer();
        AnimUtil.addIfNotNull(animators, AnimUtil.createScaleXAnimator(logoDimmer, preInfo.logoDimmerWidth, postInfo.logoDimmerWidth, preInfo.logoDimmerScaleX, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationYAnimator(logoDimmer, preInfo.logoDimmerTop, postInfo.logoDimmerTop, preInfo.logoDimmerTranslationY, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createVisibilityAnimator(logoDimmer, preInfo.logoDimmerVisibility, postInfo.logoDimmerVisibility, preInfo.logoDimmerAlpha, this.mMainInterpolator));
        View logo = programView.getLogo();
        AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationYAnimator(logo, preInfo.logoTop, postInfo.logoTop, preInfo.logoTranslationY, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createFastVisibilityAnimator(logo, preInfo.logoVisibility, postInfo.logoVisibility, preInfo.logoAlpha));
    }

    /* access modifiers changed from: private */
    public void cleanupAfterLogoAnimations(ProgramView programView, MyHolderInfo postInfo) {
        AnimUtil.setBounds(programView.getLogoAndBadgesContainer(), postInfo.logoAndBadgesContainerBounds);
        View logoDimmer = programView.getLogoDimmer();
        logoDimmer.setScaleX(1.0f);
        logoDimmer.setTranslationY(0.0f);
        logoDimmer.setVisibility(postInfo.logoDimmerVisibility);
        logoDimmer.setAlpha(1.0f);
        View logo = programView.getLogo();
        logo.setTranslationY(0.0f);
        logo.setVisibility(postInfo.logoVisibility);
        logo.setAlpha(1.0f);
    }

    private void addBadgeAnimations(List<Animator> animators, ProgramView programView, MyHolderInfo preInfo, MyHolderInfo postInfo) {
        List<Animator> list = animators;
        MyHolderInfo myHolderInfo = preInfo;
        MyHolderInfo myHolderInfo2 = postInfo;
        ImageView liveIcon = programView.getLiveIcon();
        AnimUtil.addIfNotNull(list, AnimUtil.createTranslationXYAnimator(liveIcon, myHolderInfo.liveIconLeft, myHolderInfo.liveIconTop, myHolderInfo2.liveIconLeft, myHolderInfo2.liveIconTop, myHolderInfo.liveIconTranslationX, myHolderInfo.liveIconTranslationY, this.mMainInterpolator));
        AnimUtil.addIfNotNull(list, AnimUtil.createFastVisibilityAnimator(liveIcon, myHolderInfo.liveIconVisibility, myHolderInfo2.liveIconVisibility, myHolderInfo.liveIconAlpha));
        AnimUtil.addIfNotNull(list, AnimUtil.createTranslationXYAnimator((View) programView.getLiveBadge().getParent(), myHolderInfo.badgesContainerLeft, myHolderInfo.badgesContainerTop, myHolderInfo2.badgesContainerLeft, myHolderInfo2.badgesContainerTop, myHolderInfo.badgesContainerTranslationX, myHolderInfo.badgesContainerTranslationY, this.mMainInterpolator));
        AnimUtil.addIfNotNull(list, AnimUtil.createFastVisibilityAnimator(programView.getLiveBadge(), myHolderInfo.liveBadgeVisibility, myHolderInfo2.liveBadgeVisibility, myHolderInfo.liveBadgeAlpha));
        AnimUtil.addIfNotNull(list, AnimUtil.createFastVisibilityAnimator(programView.getDurationBadge(), myHolderInfo.durationBadgeVisibility, myHolderInfo2.durationBadgeVisibility, myHolderInfo.durationBadgeAlpha));
    }

    /* access modifiers changed from: private */
    public void cleanupAfterBadgeAnimations(ProgramView programView, MyHolderInfo postInfo) {
        View liveIcon = programView.getLiveIcon();
        liveIcon.setTranslationX(0.0f);
        liveIcon.setTranslationY(0.0f);
        liveIcon.setVisibility(postInfo.liveIconVisibility);
        liveIcon.setAlpha(1.0f);
        View badgesContainer = (View) programView.getLiveBadge().getParent();
        badgesContainer.setTranslationX(0.0f);
        badgesContainer.setTranslationY(0.0f);
        View liveBadge = programView.getLiveBadge();
        liveBadge.setVisibility(postInfo.liveBadgeVisibility);
        liveBadge.setAlpha(1.0f);
        View durationBadge = programView.getDurationBadge();
        durationBadge.setVisibility(postInfo.durationBadgeVisibility);
        durationBadge.setAlpha(1.0f);
    }

    private void addFavoriteAppsRowViewAnimations(List<Animator> animators, @NonNull FavoriteLaunchItemView favoriteLaunchItemView, MyHolderInfo preInfo, MyHolderInfo postInfo) {
        ImageView overlayBannerView;
        View view = (View) favoriteLaunchItemView;
        AnimUtil.addIfNotNull(animators, AnimUtil.createScaleAnimator(view, preInfo.scale, postInfo.scale, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createPivotXAnimator(view, preInfo.pivotX, postInfo.pivotX, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationZAnimator(view, preInfo.elevation, postInfo.elevation, preInfo.translationZ, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationXYAnimator(view, preInfo.left, preInfo.top, postInfo.left, postInfo.top, preInfo.translationX, preInfo.translationY, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createColorFilterAnimator(favoriteLaunchItemView.getBannerImage(), ViewCompat.MEASURED_STATE_MASK, preInfo.dimmingFactor, postInfo.dimmingFactor, this.mMainInterpolator));
        if ((favoriteLaunchItemView instanceof BannerView) && (overlayBannerView = ((BannerView) favoriteLaunchItemView).getOverlayBannerView()) != null) {
            AnimUtil.addIfNotNull(animators, AnimUtil.createColorFilterAnimator(overlayBannerView, ViewCompat.MEASURED_STATE_MASK, preInfo.overlayBannerViewDimmingFactor, postInfo.overlayBannerViewDimmingFactor, this.mMainInterpolator));
        }
        AnimUtil.addIfNotNull(animators, AnimUtil.createFastVisibilityAnimator(favoriteLaunchItemView.getTitleView(), preInfo.titleViewVisibility, postInfo.titleViewVisibility, preInfo.titleViewAlpha));
    }

    /* JADX INFO: Multiple debug info for r1v7 android.widget.TextView: [D('bannerView' com.google.android.tvlauncher.appsview.BannerView), D('titleView' android.view.View)] */
    /* access modifiers changed from: private */
    public void cleanUpAfterFavoriteAppsRowViewViewAnimations(FavoriteLaunchItemView favoriteLaunchItemView, MyHolderInfo postInfo) {
        ImageView overlayBannerView;
        View view = (View) favoriteLaunchItemView;
        view.setPivotX(postInfo.pivotX);
        view.setScaleX(postInfo.scale);
        view.setScaleY(postInfo.scale);
        view.setTranslationX(0.0f);
        view.setTranslationY(0.0f);
        view.setTranslationZ(0.0f);
        if (postInfo.dimmingFactor < EPS) {
            favoriteLaunchItemView.getBannerImage().setColorFilter((ColorFilter) null);
        } else {
            favoriteLaunchItemView.getBannerImage().setColorFilter(ColorUtils.getColorFilter(ViewCompat.MEASURED_STATE_MASK, postInfo.dimmingFactor));
        }
        if ((favoriteLaunchItemView instanceof BannerView) && (overlayBannerView = ((BannerView) favoriteLaunchItemView).getOverlayBannerView()) != null) {
            if (postInfo.overlayBannerViewDimmingFactor < EPS) {
                overlayBannerView.setColorFilter((ColorFilter) null);
            } else {
                overlayBannerView.setColorFilter(ColorUtils.getColorFilter(ViewCompat.MEASURED_STATE_MASK, postInfo.overlayBannerViewDimmingFactor));
            }
        }
        View titleView = favoriteLaunchItemView.getTitleView();
        titleView.setVisibility(postInfo.titleViewVisibility);
        titleView.setAlpha(1.0f);
    }

    private void addWatchNextInfoViewAnimations(List<Animator> animators, WatchNextInfoView view, MyHolderInfo preInfo, MyHolderInfo postInfo) {
        AnimUtil.addIfNotNull(animators, AnimUtil.createBoundsAnimator(view, preInfo.bounds, postInfo.bounds, this.mMainInterpolator));
        View container = view.getContainer();
        if (Math.abs(preInfo.watchNextInfoContainerAlpha - 1.0f) < EPS && postInfo.watchNextInfoContainerAlpha < EPS) {
            AnimUtil.addIfNotNull(animators, AnimUtil.createAlphaAnimator(container, preInfo.watchNextInfoContainerAlpha, postInfo.watchNextInfoContainerAlpha, FAST_WATCH_NEXT_INFO_CARD_DISAPPEARANCE_INTERPOLATOR));
        } else if (preInfo.watchNextInfoContainerAlpha < EPS && Math.abs(postInfo.watchNextInfoContainerAlpha - 1.0f) < EPS) {
            AnimUtil.addIfNotNull(animators, AnimUtil.createAlphaAnimator(container, preInfo.watchNextInfoContainerAlpha, postInfo.watchNextInfoContainerAlpha, DELAY_WATCH_NEXT_INFO_CARD_APPEARANCE_INTERPOLATOR));
        }
        AnimUtil.addIfNotNull(animators, AnimUtil.createBoundsAnimator(container, preInfo.watchNextInfoContainerBounds, postInfo.watchNextInfoContainerBounds, this.mMainInterpolator));
        container.setPivotY((float) (postInfo.watchNextInfoContainerBounds.height() / 2));
        if (Util.isRtl(view.getContext())) {
            container.setPivotX((float) postInfo.watchNextInfoContainerBounds.width());
        } else {
            container.setPivotX(0.0f);
        }
        AnimUtil.addIfNotNull(animators, AnimUtil.createScaleAnimator(container, preInfo.watchNextInfoContainerScale, postInfo.watchNextInfoContainerScale, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createBoundsAnimator(view.getIconTitleContainer(), preInfo.watchNextInfoIconTitleContainerBounds, postInfo.watchNextInfoIconTitleContainerBounds, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createScaleAnimator(view.getIcon(), preInfo.watchNextInfoIconBounds, postInfo.watchNextInfoIconBounds, preInfo.watchNextInfoIconScale, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationXYAnimator(view.getIcon(), preInfo.watchNextInfoIconBounds.left, preInfo.watchNextInfoIconBounds.top, postInfo.watchNextInfoIconBounds.left, postInfo.watchNextInfoIconBounds.top, preInfo.watchNextInfoIconTranslationX, preInfo.watchNextInfoIconTranslationY, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createAlphaAnimator(view.getIcon(), preInfo.watchNextInfoIconAlpha, postInfo.watchNextInfoIconAlpha, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, createScaleAnimatorBasedOnHeight(view.getTitle(), preInfo.watchNextInfoTitleBounds.height(), postInfo.watchNextInfoTitleBounds.height(), preInfo.watchNextInfoTitleScale, this.mMainInterpolator));
        if (Util.isRtl(view.getContext())) {
            AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationXAnimator(view.getTitle(), preInfo.watchNextInfoTitleBounds.right, postInfo.watchNextInfoTitleBounds.right, preInfo.watchNextInfoTitleTranslationX, this.mMainInterpolator));
        } else {
            AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationXAnimator(view.getTitle(), preInfo.watchNextInfoTitleBounds.left, postInfo.watchNextInfoTitleBounds.left, preInfo.watchNextInfoTitleTranslationX, this.mMainInterpolator));
        }
        AnimUtil.addIfNotNull(animators, AnimUtil.createTextColorAnimator(view.getTitle(), preInfo.watchNextInfoMessageTextColor, postInfo.watchNextInfoMessageTextColor, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createScaleAnimator(view.getMessage(), preInfo.watchNextInfoMessageScale, postInfo.watchNextInfoMessageScale, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationXYAnimator(view.getMessage(), preInfo.watchNextInfoMessageViewBounds.left, preInfo.watchNextInfoMessageViewBounds.top, postInfo.watchNextInfoMessageViewBounds.left, postInfo.watchNextInfoMessageViewBounds.top, preInfo.watchNextInfoMessageTranslationX, preInfo.watchNextInfoMessageTranslationY, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createTextColorAnimator(view.getMessage(), preInfo.watchNextInfoMessageTextColor, postInfo.watchNextInfoMessageTextColor, this.mMainInterpolator));
    }

    /* access modifiers changed from: private */
    public void cleanUpAfterWatchNextInfoViewAnimations(WatchNextInfoView view, MyHolderInfo postInfo) {
        AnimUtil.setBounds(view, postInfo.bounds);
        View container = view.getContainer();
        AnimUtil.setBounds(container, postInfo.watchNextInfoContainerBounds);
        container.setScaleX(postInfo.watchNextInfoContainerScale);
        container.setScaleY(postInfo.watchNextInfoContainerScale);
        container.setAlpha(postInfo.watchNextInfoContainerAlpha);
        AnimUtil.setBounds(view.getIconTitleContainer(), postInfo.watchNextInfoIconTitleContainerBounds);
        View iconView = view.getIcon();
        iconView.setScaleX(1.0f);
        iconView.setScaleY(1.0f);
        iconView.setTranslationY(0.0f);
        iconView.setAlpha(postInfo.watchNextInfoIconAlpha);
        TextView titleView = view.getTitle();
        titleView.setScaleX(1.0f);
        titleView.setScaleY(1.0f);
        titleView.setTranslationX(0.0f);
        titleView.setTextColor(postInfo.watchNextInfoTitleTextColor);
        TextView messageView = view.getMessage();
        messageView.setScaleX(postInfo.watchNextInfoMessageScale);
        messageView.setScaleY(postInfo.watchNextInfoMessageScale);
        messageView.setTranslationX(0.0f);
        messageView.setTranslationY(0.0f);
        messageView.setTextColor(postInfo.watchNextInfoMessageTextColor);
    }

    @Nullable
    private Animator createScaleAnimatorBasedOnHeight(@NonNull View view, int from, int to, float current, @Nullable Interpolator interpolator) {
        float fromHeight = ((float) from) * current;
        if (from <= 0 || to <= 0 || Math.abs(fromHeight - ((float) to)) <= EPS) {
            return null;
        }
        Animator animator = ObjectAnimator.ofFloat(view, AnimUtil.SCALE_PROPERTY, fromHeight / ((float) to), 1.0f);
        animator.setInterpolator(interpolator);
        return animator;
    }

    @NonNull
    public RecyclerView.ItemAnimator.ItemHolderInfo obtainHolderInfo() {
        return new MyHolderInfo();
    }

    private static class MyHolderInfo extends RecyclerView.ItemAnimator.ItemHolderInfo {
        int badgesContainerLeft;
        int badgesContainerTop;
        float badgesContainerTranslationX;
        float badgesContainerTranslationY;
        Rect bounds;
        float dimmingFactor;
        float durationBadgeAlpha;
        int durationBadgeVisibility;
        float elevation;
        float liveBadgeAlpha;
        int liveBadgeVisibility;
        float liveIconAlpha;
        int liveIconLeft;
        int liveIconTop;
        float liveIconTranslationX;
        float liveIconTranslationY;
        int liveIconVisibility;
        float logoAlpha;
        Rect logoAndBadgesContainerBounds;
        float logoDimmerAlpha;
        float logoDimmerScaleX;
        int logoDimmerTop;
        float logoDimmerTranslationY;
        int logoDimmerVisibility;
        int logoDimmerWidth;
        int logoTop;
        float logoTranslationY;
        int logoVisibility;
        float overlayBannerViewDimmingFactor;
        List<Object> payloads;
        float pivotX;
        float pivotY;
        float playbackProgressDimmerScaleX;
        int playbackProgressDimmerTop;
        float playbackProgressDimmerTranslationY;
        int playbackProgressDimmerWidth;
        float playbackProgressScaleX;
        int playbackProgressTop;
        float playbackProgressTranslationY;
        int playbackProgressWidth;
        int previewDelayProgressLeft;
        int previewDelayProgressTop;
        float previewDelayProgressTranslationX;
        float previewDelayProgressTranslationY;
        Rect previewImageBackgroundBounds;
        float previewImageBackgroundScale;
        Rect previewImageBounds;
        Rect previewImageContainerBounds;
        float previewImageScale;
        float previewImageTranslationX;
        float previewImageTranslationY;
        float scale;
        float titleViewAlpha;
        int titleViewVisibility;
        float translationX;
        float translationY;
        float translationZ;
        float watchNextInfoContainerAlpha;
        Rect watchNextInfoContainerBounds;
        float watchNextInfoContainerScale;
        float watchNextInfoIconAlpha;
        Rect watchNextInfoIconBounds;
        float watchNextInfoIconScale;
        Rect watchNextInfoIconTitleContainerBounds;
        float watchNextInfoIconTranslationX;
        float watchNextInfoIconTranslationY;
        float watchNextInfoMessageScale;
        @ColorInt
        int watchNextInfoMessageTextColor;
        float watchNextInfoMessageTranslationX;
        float watchNextInfoMessageTranslationY;
        Rect watchNextInfoMessageViewBounds;
        Rect watchNextInfoTitleBounds;
        float watchNextInfoTitleScale;
        @ColorInt
        int watchNextInfoTitleTextColor;
        float watchNextInfoTitleTranslationX;

        private MyHolderInfo() {
        }

        @NonNull
        public RecyclerView.ItemAnimator.ItemHolderInfo setFrom(@NonNull RecyclerView.ViewHolder holder, int flags) {
            super.setFrom(holder, flags);
            this.bounds = AnimUtil.getBounds(holder.itemView);
            this.scale = holder.itemView.getScaleX();
            this.pivotX = holder.itemView.getPivotX();
            this.pivotY = holder.itemView.getPivotY();
            this.elevation = holder.itemView.getElevation();
            this.translationX = holder.itemView.getTranslationX();
            this.translationY = holder.itemView.getTranslationY();
            this.translationZ = holder.itemView.getTranslationZ();
            if (holder.itemView instanceof ProgramView) {
                ProgramView programView = (ProgramView) holder.itemView;
                this.previewImageContainerBounds = AnimUtil.getBounds(programView.getPreviewImageContainer());
                View previewImageBackground = programView.getPreviewImageBackground();
                this.previewImageBackgroundBounds = AnimUtil.getBounds(previewImageBackground);
                this.previewImageBackgroundScale = previewImageBackground.getScaleX();
                View previewImage = programView.getPreviewImage();
                this.previewImageBounds = AnimUtil.getBounds(previewImage);
                this.previewImageScale = previewImage.getScaleX();
                this.previewImageTranslationX = previewImage.getTranslationX();
                this.previewImageTranslationY = previewImage.getTranslationY();
                this.dimmingFactor = programView.getPreviewImageDimmingFactor();
                View previewDelayProgress = programView.getPreviewDelayProgress();
                this.previewDelayProgressLeft = previewDelayProgress.getLeft();
                this.previewDelayProgressTranslationX = previewDelayProgress.getTranslationX();
                this.previewDelayProgressTop = previewDelayProgress.getTop();
                this.previewDelayProgressTranslationY = previewDelayProgress.getTranslationY();
                View playbackProgressDimmer = programView.getPlaybackProgressDimmer();
                this.playbackProgressDimmerWidth = playbackProgressDimmer.getWidth();
                this.playbackProgressDimmerScaleX = playbackProgressDimmer.getScaleX();
                this.playbackProgressDimmerTop = playbackProgressDimmer.getTop();
                this.playbackProgressDimmerTranslationY = playbackProgressDimmer.getTranslationY();
                View playbackProgress = programView.getPlaybackProgress();
                this.playbackProgressWidth = playbackProgress.getWidth();
                this.playbackProgressScaleX = playbackProgress.getScaleX();
                this.playbackProgressTop = playbackProgress.getTop();
                this.playbackProgressTranslationY = playbackProgress.getTranslationY();
                this.logoAndBadgesContainerBounds = AnimUtil.getBounds(programView.getLogoAndBadgesContainer());
                View logoDimmer = programView.getLogoDimmer();
                this.logoDimmerWidth = logoDimmer.getWidth();
                this.logoDimmerScaleX = logoDimmer.getScaleX();
                this.logoDimmerTop = logoDimmer.getTop();
                this.logoDimmerTranslationY = logoDimmer.getTranslationY();
                this.logoDimmerVisibility = programView.getLogoDimmerVisibility();
                this.logoDimmerAlpha = logoDimmer.getAlpha();
                View logo = programView.getLogo();
                this.logoTop = logo.getTop();
                this.logoTranslationY = logo.getTranslationY();
                this.logoVisibility = programView.getLogoVisibility();
                this.logoAlpha = logo.getAlpha();
                View liveIcon = programView.getLiveIcon();
                this.liveIconLeft = liveIcon.getLeft();
                this.liveIconTranslationX = liveIcon.getTranslationX();
                this.liveIconTop = liveIcon.getTop();
                this.liveIconTranslationY = liveIcon.getTranslationY();
                this.liveIconVisibility = programView.getLiveIconVisibility();
                this.liveIconAlpha = liveIcon.getAlpha();
                View badgesContainer = (View) programView.getLiveBadge().getParent();
                this.badgesContainerLeft = badgesContainer.getLeft();
                this.badgesContainerTranslationX = badgesContainer.getTranslationX();
                this.badgesContainerTop = badgesContainer.getTop();
                this.badgesContainerTranslationY = badgesContainer.getTranslationY();
                this.liveBadgeVisibility = programView.getLiveBadgeVisibility();
                this.liveBadgeAlpha = programView.getLiveBadge().getAlpha();
                this.durationBadgeVisibility = programView.getDurationBadgeVisibility();
                this.durationBadgeAlpha = programView.getDurationBadge().getAlpha();
            } else if (holder.itemView instanceof FavoriteLaunchItemView) {
                FavoriteLaunchItemView favoriteLaunchItemView = (FavoriteLaunchItemView) holder.itemView;
                this.titleViewAlpha = favoriteLaunchItemView.getTitleView().getAlpha();
                this.titleViewVisibility = favoriteLaunchItemView.getTitleVisibility();
                this.dimmingFactor = favoriteLaunchItemView.getBannerImageDimmingFactor();
                if (holder.itemView instanceof BannerView) {
                    BannerView bannerView = (BannerView) holder.itemView;
                    if (bannerView.getOverlayBannerView() != null) {
                        this.overlayBannerViewDimmingFactor = bannerView.getOverlayBannerViewDimmingFactor();
                    }
                }
            } else if (holder.itemView instanceof WatchNextInfoView) {
                WatchNextInfoView infoView = (WatchNextInfoView) holder.itemView;
                View container = infoView.getContainer();
                this.watchNextInfoContainerBounds = AnimUtil.getBounds(container);
                this.watchNextInfoContainerScale = container.getScaleX();
                this.watchNextInfoContainerAlpha = container.getAlpha();
                this.watchNextInfoIconTitleContainerBounds = AnimUtil.getBounds(infoView.getIconTitleContainer());
                View iconView = infoView.getIcon();
                this.watchNextInfoIconBounds = AnimUtil.getBounds(iconView);
                this.watchNextInfoIconScale = iconView.getScaleX();
                this.watchNextInfoIconTranslationX = iconView.getTranslationX();
                this.watchNextInfoIconTranslationY = iconView.getTranslationY();
                this.watchNextInfoIconAlpha = iconView.getAlpha();
                TextView titleView = infoView.getTitle();
                this.watchNextInfoTitleBounds = AnimUtil.getBounds(titleView);
                this.watchNextInfoTitleScale = titleView.getScaleY();
                this.watchNextInfoTitleTranslationX = titleView.getTranslationX();
                this.watchNextInfoTitleTextColor = titleView.getCurrentTextColor();
                TextView messageView = infoView.getMessage();
                this.watchNextInfoMessageViewBounds = AnimUtil.getBounds(messageView);
                this.watchNextInfoMessageScale = messageView.getScaleX();
                this.watchNextInfoMessageTranslationX = messageView.getTranslationX();
                this.watchNextInfoMessageTranslationY = messageView.getTranslationY();
                this.watchNextInfoMessageTextColor = messageView.getCurrentTextColor();
            }
            return this;
        }

        public String toString() {
            String hexString = Integer.toHexString(hashCode());
            String valueOf = String.valueOf(this.bounds);
            String valueOf2 = String.valueOf(this.payloads);
            float f = this.scale;
            float f2 = this.pivotY;
            String valueOf3 = String.valueOf(this.previewImageContainerBounds);
            String valueOf4 = String.valueOf(this.previewImageBackgroundBounds);
            float f3 = this.previewImageBackgroundScale;
            String valueOf5 = String.valueOf(this.previewImageBounds);
            float f4 = this.previewImageScale;
            StringBuilder sb = new StringBuilder(String.valueOf(hexString).length() + ClientAnalytics.LogRequest.LogSource.BOQ_WEB_VALUE + String.valueOf(valueOf).length() + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length() + String.valueOf(valueOf4).length() + String.valueOf(valueOf5).length());
            sb.append("MyHolderInfo ");
            sb.append(hexString);
            sb.append(" bounds=");
            sb.append(valueOf);
            sb.append(", payloads=");
            sb.append(valueOf2);
            sb.append(", scale=");
            sb.append(f);
            sb.append(", pivotY=");
            sb.append(f2);
            sb.append(", previewImageContainerBounds=");
            sb.append(valueOf3);
            sb.append(", previewImageBackgroundBounds=");
            sb.append(valueOf4);
            sb.append(", previewImageBackgroundScale=");
            sb.append(f3);
            sb.append(", previewImageBounds=");
            sb.append(valueOf5);
            sb.append(", previewImageScale=");
            sb.append(f4);
            return sb.toString();
        }
    }
}
