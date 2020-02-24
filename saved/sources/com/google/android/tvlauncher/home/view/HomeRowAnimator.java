package com.google.android.tvlauncher.home.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.p001v4.view.ViewCompat;
import android.support.p004v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import com.google.android.tvlauncher.notifications.NotificationsTrayView;
import com.google.android.tvlauncher.view.HomeTopRowView;
import com.google.android.tvlauncher.view.SearchOrb;
import com.google.android.tvlauncher.view.SearchView;
import com.google.android.tvrecommendations.shared.util.AnimUtil;
import com.google.android.tvrecommendations.shared.util.ColorUtils;
import com.google.android.tvrecommendations.shared.view.BoundsItemAnimator;
import java.util.ArrayList;
import java.util.List;

public class HomeRowAnimator extends BoundsItemAnimator {
    private static final int CHANGE_DURATION_MS = 175;
    private static final boolean DEBUG = false;
    private static final float EPS = 0.001f;
    private static final String TAG = "HomeRowAnimator";
    private final Interpolator mMainInterpolator = new DecelerateInterpolator();

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

    public HomeRowAnimator() {
        setChangeDuration(175);
    }

    /* access modifiers changed from: protected */
    public boolean animateInPlaceChange(@NonNull final RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo preLayoutInfo, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo postLayoutInfo) {
        cancelChangeAnimation(viewHolder);
        MyHolderInfo preInfo = (MyHolderInfo) preLayoutInfo;
        final MyHolderInfo postInfo = (MyHolderInfo) postLayoutInfo;
        List<Animator> animators = new ArrayList<>();
        AnimUtil.addIfNotNull(animators, AnimUtil.createBoundsAnimator(viewHolder.itemView, preInfo.bounds, postInfo.bounds, this.mMainInterpolator));
        if (viewHolder.itemView instanceof ChannelView) {
            ChannelView channelView = (ChannelView) viewHolder.itemView;
            addChannelItemsListAnimations(animators, channelView, preInfo, postInfo);
            addEmptyChannelMessageAnimations(animators, channelView, preInfo, postInfo);
            if (channelView.isSponsored()) {
                addSponsoredChannelAnimations(animators, channelView, preInfo, postInfo);
            } else {
                addChannelLogoAnimations(animators, channelView, preInfo, postInfo);
            }
            addChannelTitleAnimations(animators, channelView, preInfo, postInfo);
            addChannelMetadataAnimations(animators, channelView, preInfo, postInfo);
            addMovingChannelBackgroundAnimations(animators, channelView.getMovingChannelBackground(), preInfo, postInfo);
        } else if (viewHolder.itemView instanceof HomeTopRowView) {
            addTopRowAnimations(animators, (HomeTopRowView) viewHolder.itemView, preInfo, postInfo);
        } else if (viewHolder.itemView instanceof ConfigureChannelsRowView) {
            addBottomRowAnimations(animators, (ConfigureChannelsRowView) viewHolder.itemView, preInfo, postInfo);
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
                HomeRowAnimator.this.dispatchChangeStarting(viewHolder, true);
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationEnd(Animator animation) {
                animation.removeListener(this);
                AnimUtil.setBounds(viewHolder.itemView, postInfo.bounds);
                if (viewHolder.itemView instanceof ChannelView) {
                    ChannelView channelView = (ChannelView) viewHolder.itemView;
                    HomeRowAnimator.this.cleanUpAfterChannelItemsListAnimations(channelView, postInfo);
                    HomeRowAnimator.this.cleanUpAfterEmptyChannelMessageAnimations(channelView, postInfo);
                    if (channelView.isSponsored()) {
                        HomeRowAnimator.this.cleanUpAfterSponsoredChannelAnimations(channelView, postInfo);
                    } else {
                        HomeRowAnimator.this.cleanUpAfterChannelLogoAnimations(channelView, postInfo);
                    }
                    HomeRowAnimator.this.cleanUpAfterChannelTitleAnimations(channelView, postInfo);
                    HomeRowAnimator.this.cleanUpAfterChannelMetadataAnimations(channelView, postInfo);
                    HomeRowAnimator.this.cleanUpAfterMovingChannelBackgroundAnimations(channelView.getMovingChannelBackground(), postInfo);
                } else if (viewHolder.itemView instanceof HomeTopRowView) {
                    HomeRowAnimator.this.cleanUpAfterTopRowAnimations((HomeTopRowView) viewHolder.itemView, postInfo);
                } else if (viewHolder.itemView instanceof ConfigureChannelsRowView) {
                    HomeRowAnimator.this.cleanUpAfterBottomRowAnimations((ConfigureChannelsRowView) viewHolder.itemView, postInfo);
                }
                HomeRowAnimator.this.dispatchChangeFinished(viewHolder, true);
                HomeRowAnimator.this.mChangeAnimations.remove(viewHolder);
                HomeRowAnimator.this.dispatchFinishedWhenDone();
            }
        });
        this.mChangeAnimations.put(viewHolder, overallAnimation);
        overallAnimation.start();
        return true;
    }

    /* JADX WARN: Type inference failed for: r3v6, types: [android.view.ViewParent] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void addChannelItemsListAnimations(java.util.List<android.animation.Animator> r7, com.google.android.tvlauncher.home.view.ChannelView r8, com.google.android.tvlauncher.home.view.HomeRowAnimator.MyHolderInfo r9, com.google.android.tvlauncher.home.view.HomeRowAnimator.MyHolderInfo r10) {
        /*
            r6 = this;
            androidx.leanback.widget.HorizontalGridView r0 = r8.getItemsListView()
            int r1 = r9.emptyChannelMessageVisibility
            r2 = 4
            if (r1 != r2) goto L_0x001a
            int r1 = r10.emptyChannelMessageVisibility
            if (r1 != r2) goto L_0x001a
            android.graphics.Rect r1 = r9.itemsListBounds
            android.graphics.Rect r2 = r10.itemsListBounds
            android.view.animation.Interpolator r3 = r6.mMainInterpolator
            android.animation.Animator r1 = com.google.android.tvrecommendations.shared.util.AnimUtil.createBoundsAnimator(r0, r1, r2, r3)
            com.google.android.tvrecommendations.shared.util.AnimUtil.addIfNotNull(r7, r1)
        L_0x001a:
            android.view.ViewParent r1 = r0.getParent()
            android.view.View r1 = (android.view.View) r1
            r2 = 0
        L_0x0021:
            java.util.List<android.graphics.Rect> r3 = r10.itemsListContainersBounds
            int r3 = r3.size()
            if (r2 >= r3) goto L_0x004c
            java.util.List<android.graphics.Rect> r3 = r9.itemsListContainersBounds
            java.lang.Object r3 = r3.get(r2)
            android.graphics.Rect r3 = (android.graphics.Rect) r3
            java.util.List<android.graphics.Rect> r4 = r10.itemsListContainersBounds
            java.lang.Object r4 = r4.get(r2)
            android.graphics.Rect r4 = (android.graphics.Rect) r4
            android.view.animation.Interpolator r5 = r6.mMainInterpolator
            android.animation.Animator r3 = com.google.android.tvrecommendations.shared.util.AnimUtil.createBoundsAnimator(r1, r3, r4, r5)
            com.google.android.tvrecommendations.shared.util.AnimUtil.addIfNotNull(r7, r3)
            android.view.ViewParent r3 = r1.getParent()
            r1 = r3
            android.view.View r1 = (android.view.View) r1
            int r2 = r2 + 1
            goto L_0x0021
        L_0x004c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.tvlauncher.home.view.HomeRowAnimator.addChannelItemsListAnimations(java.util.List, com.google.android.tvlauncher.home.view.ChannelView, com.google.android.tvlauncher.home.view.HomeRowAnimator$MyHolderInfo, com.google.android.tvlauncher.home.view.HomeRowAnimator$MyHolderInfo):void");
    }

    /* JADX WARN: Type inference failed for: r3v5, types: [android.view.ViewParent] */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void cleanUpAfterChannelItemsListAnimations(com.google.android.tvlauncher.home.view.ChannelView r5, com.google.android.tvlauncher.home.view.HomeRowAnimator.MyHolderInfo r6) {
        /*
            r4 = this;
            androidx.leanback.widget.HorizontalGridView r0 = r5.getItemsListView()
            android.graphics.Rect r1 = r6.itemsListBounds
            com.google.android.tvrecommendations.shared.util.AnimUtil.setBounds(r0, r1)
            android.view.ViewParent r1 = r0.getParent()
            android.view.View r1 = (android.view.View) r1
            r2 = 0
        L_0x0010:
            java.util.List<android.graphics.Rect> r3 = r6.itemsListContainersBounds
            int r3 = r3.size()
            if (r2 >= r3) goto L_0x002d
            java.util.List<android.graphics.Rect> r3 = r6.itemsListContainersBounds
            java.lang.Object r3 = r3.get(r2)
            android.graphics.Rect r3 = (android.graphics.Rect) r3
            com.google.android.tvrecommendations.shared.util.AnimUtil.setBounds(r1, r3)
            android.view.ViewParent r3 = r1.getParent()
            r1 = r3
            android.view.View r1 = (android.view.View) r1
            int r2 = r2 + 1
            goto L_0x0010
        L_0x002d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.tvlauncher.home.view.HomeRowAnimator.cleanUpAfterChannelItemsListAnimations(com.google.android.tvlauncher.home.view.ChannelView, com.google.android.tvlauncher.home.view.HomeRowAnimator$MyHolderInfo):void");
    }

    private void addEmptyChannelMessageAnimations(List<Animator> animators, ChannelView channelView, MyHolderInfo preInfo, MyHolderInfo postInfo) {
        TextView emptyChannelMessage = channelView.getEmptyChannelMessage();
        if (preInfo.emptyChannelMessageVisibility == 0 && postInfo.emptyChannelMessageVisibility == 0) {
            AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationXYAnimator(emptyChannelMessage, preInfo.emptyChannelMessageLeft, preInfo.emptyChannelMessageTop, postInfo.emptyChannelMessageLeft, postInfo.emptyChannelMessageTop, preInfo.emptyChannelMessageTranslationX, preInfo.emptyChannelMessageTranslationY, this.mMainInterpolator));
        }
        AnimUtil.addIfNotNull(animators, AnimUtil.createVisibilityAnimator(emptyChannelMessage, preInfo.emptyChannelMessageVisibility, postInfo.emptyChannelMessageVisibility, preInfo.emptyChannelMessageAlpha, this.mMainInterpolator));
    }

    /* access modifiers changed from: private */
    public void cleanUpAfterEmptyChannelMessageAnimations(ChannelView channelView, MyHolderInfo postInfo) {
        View emptyChannelMessage = channelView.getEmptyChannelMessage();
        emptyChannelMessage.setVisibility(postInfo.emptyChannelMessageVisibility);
        emptyChannelMessage.setAlpha(1.0f);
        emptyChannelMessage.setTranslationX(0.0f);
        emptyChannelMessage.setTranslationY(0.0f);
    }

    private void addMovingChannelBackgroundAnimations(List<Animator> animators, View backgroundView, MyHolderInfo preInfo, MyHolderInfo postInfo) {
        AnimUtil.addIfNotNull(animators, AnimUtil.createVisibilityAnimator(backgroundView, preInfo.movingChannelBackgroundVisibility, postInfo.movingChannelBackgroundVisibility, preInfo.movingChannelBackgroundAlpha, this.mMainInterpolator));
    }

    /* access modifiers changed from: private */
    public void cleanUpAfterMovingChannelBackgroundAnimations(View backgroundView, MyHolderInfo postInfo) {
        backgroundView.setVisibility(postInfo.movingChannelBackgroundVisibility);
        backgroundView.setAlpha(1.0f);
    }

    private void addSponsoredChannelAnimations(List<Animator> animators, ChannelView channelView, MyHolderInfo preInfo, MyHolderInfo postInfo) {
        ImageView channelLogoImageView = channelView.getChannelLogoImageView();
        AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationXYAnimator(channelLogoImageView, preInfo.logoBounds.left, preInfo.logoBounds.top, postInfo.logoBounds.left, postInfo.logoBounds.top, preInfo.logoTranslationX, preInfo.logoTranslationY, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createScaleAnimator(channelLogoImageView, preInfo.logoBounds, postInfo.logoBounds, preInfo.logoScale, this.mMainInterpolator));
        View sponsoredBackground = channelView.getSponsoredChannelBackground();
        AnimUtil.addIfNotNull(animators, AnimUtil.createScaleYAnimator(sponsoredBackground, preInfo.sponsoredChannelBackgroundHeight, postInfo.sponsoredChannelBackgroundHeight, preInfo.sponsoredChannelBackgroundScaleY, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationYAnimator(sponsoredBackground, preInfo.sponsoredChannelBackgroundTop, postInfo.sponsoredChannelBackgroundTop, preInfo.sponsoredChannelBackgroundTranslationY, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createBackgroundColorAnimator(sponsoredBackground, preInfo.sponsoredChannelBackgroundColor, postInfo.sponsoredChannelBackgroundColor, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createVisibilityAnimator(sponsoredBackground, preInfo.sponsoredChannelBackgroundVisibility, postInfo.sponsoredChannelBackgroundVisibility, preInfo.sponsoredChannelBackgroundAlpha, this.mMainInterpolator));
    }

    /* access modifiers changed from: private */
    public void cleanUpAfterSponsoredChannelAnimations(@NonNull ChannelView channelView, @NonNull MyHolderInfo postInfo) {
        View logo = channelView.getChannelLogoImageView();
        logo.setTranslationX(0.0f);
        logo.setTranslationY(0.0f);
        logo.setScaleX(1.0f);
        logo.setScaleY(1.0f);
        View sponsoredBackground = channelView.getSponsoredChannelBackground();
        sponsoredBackground.setScaleY(1.0f);
        sponsoredBackground.setTranslationY(0.0f);
        sponsoredBackground.setBackgroundColor(postInfo.sponsoredChannelBackgroundColor);
        sponsoredBackground.setVisibility(postInfo.sponsoredChannelBackgroundVisibility);
        sponsoredBackground.setAlpha(1.0f);
    }

    private void addChannelLogoAnimations(List<Animator> animators, ChannelView channelView, MyHolderInfo preInfo, MyHolderInfo postInfo) {
        ImageView logo = channelView.getChannelLogoImageView();
        AnimUtil.addIfNotNull(animators, AnimUtil.createBoundsAnimator((View) logo.getParent(), preInfo.logoContainerBounds, postInfo.logoContainerBounds, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createScaleAnimator(logo, preInfo.logoBounds, postInfo.logoBounds, preInfo.logoScale, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationZAnimator(logo, (float) preInfo.logoElevation, (float) postInfo.logoElevation, preInfo.logoTranslationZ, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationXAnimator(logo, preInfo.logoBounds, postInfo.logoBounds, preInfo.logoTranslationX, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationYAnimator(logo, preInfo.logoBounds, postInfo.logoBounds, preInfo.logoTranslationY, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createColorFilterAnimator(logo, ViewCompat.MEASURED_STATE_MASK, preInfo.logoDimmingFactor, postInfo.logoDimmingFactor, this.mMainInterpolator));
    }

    /* access modifiers changed from: private */
    public void cleanUpAfterChannelLogoAnimations(@NonNull ChannelView channelView, @NonNull MyHolderInfo postInfo) {
        ImageView logo = channelView.getChannelLogoImageView();
        AnimUtil.setBounds((View) logo.getParent(), postInfo.logoContainerBounds);
        logo.setScaleX(1.0f);
        logo.setScaleY(1.0f);
        logo.setTranslationZ(0.0f);
        logo.setTranslationX(0.0f);
        logo.setTranslationY(0.0f);
        if (postInfo.logoDimmingFactor < EPS) {
            logo.setColorFilter((ColorFilter) null);
        } else {
            logo.setColorFilter(ColorUtils.getColorFilter(ViewCompat.MEASURED_STATE_MASK, postInfo.logoDimmingFactor));
        }
    }

    private void addChannelTitleAnimations(List<Animator> animators, ChannelView channelView, MyHolderInfo preInfo, MyHolderInfo postInfo) {
        List<Animator> list = animators;
        MyHolderInfo myHolderInfo = preInfo;
        MyHolderInfo myHolderInfo2 = postInfo;
        TextView logoTitle = channelView.getLogoTitle();
        AnimUtil.addIfNotNull(list, AnimUtil.createTranslationXYAnimator(logoTitle, myHolderInfo.logoTitleLeft, myHolderInfo.logoTitleTop, myHolderInfo2.logoTitleLeft, myHolderInfo2.logoTitleTop, myHolderInfo.logoTitleTranslationX, myHolderInfo.logoTitleTranslationY, this.mMainInterpolator));
        AnimUtil.addIfNotNull(list, AnimUtil.createFastVisibilityAnimator(logoTitle, myHolderInfo.logoTitleVisibility, myHolderInfo2.logoTitleVisibility, myHolderInfo.logoTitleAlpha));
        if (myHolderInfo.logoTitleVisibility == 0 && myHolderInfo2.logoTitleVisibility == 0) {
            AnimUtil.addIfNotNull(list, AnimUtil.createTextColorAnimator(logoTitle, myHolderInfo.logoTitleCurrentColor, postInfo.logoTitleStateColor, this.mMainInterpolator));
        }
        TextView zoomedOutLogoTitle = channelView.getZoomedOutLogoTitle();
        AnimUtil.addIfNotNull(list, AnimUtil.createTranslationXYAnimator(zoomedOutLogoTitle, myHolderInfo.zoomedOutLogoTitleLeft, myHolderInfo.zoomedOutLogoTitleTop, myHolderInfo2.zoomedOutLogoTitleLeft, myHolderInfo2.zoomedOutLogoTitleTop, myHolderInfo.zoomedOutLogoTitleTranslationX, myHolderInfo.zoomedOutLogoTitleTranslationY, this.mMainInterpolator));
        AnimUtil.addIfNotNull(list, AnimUtil.createFastVisibilityAnimator(zoomedOutLogoTitle, myHolderInfo.zoomedOutLogoTitleVisibility, myHolderInfo2.zoomedOutLogoTitleVisibility, myHolderInfo.zoomedOutLogoTitleAlpha));
        if (myHolderInfo.zoomedOutLogoTitleVisibility == 0 && myHolderInfo2.zoomedOutLogoTitleVisibility == 0) {
            AnimUtil.addIfNotNull(list, AnimUtil.createTextColorAnimator(zoomedOutLogoTitle, myHolderInfo.zoomedOutLogoTitleCurrentColor, myHolderInfo2.zoomedOutLogoTitleStateColor, this.mMainInterpolator));
        }
        TextView itemsTitle = channelView.getItemsTitle();
        AnimUtil.addIfNotNull(list, AnimUtil.createTranslationXYAnimator(itemsTitle, myHolderInfo.itemsTitleLeft, myHolderInfo.itemsTitleTop, myHolderInfo2.itemsTitleLeft, myHolderInfo2.itemsTitleTop, myHolderInfo.itemsTitleTranslationX, myHolderInfo.itemsTitleTranslationY, this.mMainInterpolator));
        AnimUtil.addIfNotNull(list, AnimUtil.createFastVisibilityAnimator(itemsTitle, myHolderInfo.itemsTitleVisibility, myHolderInfo2.itemsTitleVisibility, myHolderInfo.itemsTitleAlpha));
        if (myHolderInfo.itemsTitleVisibility == 0 && myHolderInfo2.itemsTitleVisibility == 0) {
            AnimUtil.addIfNotNull(list, AnimUtil.createTextColorAnimator(itemsTitle, myHolderInfo.itemsTitleCurrentColor, myHolderInfo2.itemsTitleStateColor, this.mMainInterpolator));
        }
        View actionsHint = channelView.getActionsHint();
        AnimUtil.addIfNotNull(list, AnimUtil.createTranslationXYAnimator(actionsHint, myHolderInfo.actionsHintLeft, myHolderInfo.actionsHintTop, myHolderInfo2.actionsHintLeft, myHolderInfo2.actionsHintTop, myHolderInfo.actionsHintTranslationX, myHolderInfo.actionsHintTranslationY, this.mMainInterpolator));
        AnimUtil.addIfNotNull(list, AnimUtil.createFastVisibilityAnimator(actionsHint, myHolderInfo.actionsHintVisibility, myHolderInfo2.actionsHintVisibility, myHolderInfo.actionsHintAlpha));
    }

    /* access modifiers changed from: private */
    public void cleanUpAfterChannelTitleAnimations(ChannelView channelView, MyHolderInfo postInfo) {
        TextView logoTitle = channelView.getLogoTitle();
        logoTitle.setTranslationX(0.0f);
        logoTitle.setTranslationY(0.0f);
        logoTitle.setVisibility(postInfo.logoTitleVisibility);
        logoTitle.setAlpha(1.0f);
        logoTitle.setTextColor(postInfo.logoTitleStateColor);
        TextView zoomedOutLogoTitle = channelView.getZoomedOutLogoTitle();
        zoomedOutLogoTitle.setTranslationX(0.0f);
        zoomedOutLogoTitle.setTranslationY(0.0f);
        zoomedOutLogoTitle.setVisibility(postInfo.zoomedOutLogoTitleVisibility);
        zoomedOutLogoTitle.setAlpha(1.0f);
        zoomedOutLogoTitle.setTextColor(postInfo.zoomedOutLogoTitleStateColor);
        TextView itemsTitle = channelView.getItemsTitle();
        itemsTitle.setTranslationX(0.0f);
        itemsTitle.setVisibility(postInfo.itemsTitleVisibility);
        itemsTitle.setAlpha(1.0f);
        itemsTitle.setTextColor(postInfo.itemsTitleStateColor);
        View actionsHint = channelView.getActionsHint();
        actionsHint.setTranslationX(0.0f);
        actionsHint.setTranslationY(0.0f);
        actionsHint.setVisibility(postInfo.actionsHintVisibility);
        actionsHint.setAlpha(1.0f);
    }

    private void addChannelMetadataAnimations(List<Animator> animators, ChannelView channelView, MyHolderInfo preInfo, MyHolderInfo postInfo) {
        View metaContainer = channelView.getItemMetadataView();
        AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationXYAnimator(metaContainer, preInfo.metaContainerLeft, preInfo.metaContainerTop, postInfo.metaContainerLeft, postInfo.metaContainerTop, preInfo.metaContainerTranslationX, preInfo.metaContainerTranslationY, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createFastVisibilityAnimator(metaContainer, preInfo.metaContainerVisibility, postInfo.metaContainerVisibility, preInfo.metaContainerAlpha));
    }

    /* access modifiers changed from: private */
    public void cleanUpAfterChannelMetadataAnimations(ChannelView channelView, MyHolderInfo postInfo) {
        View metaContainer = channelView.getItemMetadataView();
        metaContainer.setTranslationX(0.0f);
        metaContainer.setTranslationY(0.0f);
        metaContainer.setVisibility(postInfo.metaContainerVisibility);
        metaContainer.setAlpha(1.0f);
    }

    private void addTopRowAnimations(@NonNull List<Animator> animators, @NonNull HomeTopRowView topRowView, @NonNull MyHolderInfo preInfo, @NonNull MyHolderInfo postInfo) {
        ViewGroup itemsContainer = topRowView.getItemsContainer();
        AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationYAnimator(itemsContainer, preInfo.topRowItemContainerTop, postInfo.topRowItemContainerTop, preInfo.topRowItemContainerTranslationY, this.mMainInterpolator));
        int deltaX = (int) (((float) (postInfo.topRowFirstItemLeft - preInfo.topRowFirstItemLeft)) - preInfo.topRowFirstItemTranslationX);
        if (((float) deltaX) != 0.0f) {
            animators.add(ObjectAnimator.ofFloat(itemsContainer.getChildAt(0), View.TRANSLATION_X, (float) (-deltaX), 0.0f));
        }
        AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationYAnimator(topRowView.getNotificationsTray(), preInfo.topRowNotificationsTrayTop, postInfo.topRowNotificationsTrayTop, preInfo.topRowNotificationsTrayTranslationY, this.mMainInterpolator));
        SearchView searchView = topRowView.getSearchWidget();
        SearchOrb micOrb = searchView.getMicOrb();
        SearchOrb keyboardOrb = searchView.getKeyboardOrb();
        AnimUtil.addIfNotNull(animators, AnimUtil.createBackgroundColorAnimator(micOrb.getFocusIndicator(), preInfo.searchViewMicBackgroundColor, postInfo.searchViewMicBackgroundColor, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createBackgroundColorAnimator(keyboardOrb.getFocusIndicator(), preInfo.searchViewKeyboardBackgroundColor, postInfo.searchViewKeyboardBackgroundColor, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createScaleAnimator(keyboardOrb, preInfo.searchViewKeyboardOrbScale, postInfo.searchViewKeyboardOrbScale, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createScaleAnimator(micOrb, preInfo.searchViewMicOrbScale, postInfo.searchViewMicOrbScale, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createVisibilityAnimator(searchView.getHotwordDisabledIcon(), preInfo.searchViewHotwordIconVisibility, postInfo.searchViewHotwordIconVisibility, preInfo.searchViewHotwordIconAlpha, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationXAnimator(searchView.getTextSwitcher(), preInfo.searchViewTextSwitcherLeft, postInfo.searchViewTextSwitcherLeft, postInfo.searchViewTextSwitcherTranslationX, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationXAnimator(searchView.getTextSwitcherContainer(), preInfo.searchViewTextSwitcherContainerLeft, postInfo.searchViewTextSwitcherContainerLeft, postInfo.searchViewTextSwitcherContainerTranslationX, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createScaleAnimator(searchView.getKeyboardOrbContainer(), preInfo.searchViewKeyboardContainerScale, postInfo.searchViewKeyboardContainerScale, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createVisibilityAnimator(keyboardOrb, preInfo.searchViewKeyboardOrbVisibility, postInfo.searchViewKeyboardOrbVisibility, preInfo.searchViewKeyboardOrbAlpha, this.mMainInterpolator));
    }

    /* access modifiers changed from: private */
    public void cleanUpAfterTopRowAnimations(@NonNull HomeTopRowView topRowView, @NonNull MyHolderInfo postInfo) {
        ViewGroup itemContainer = topRowView.getItemsContainer();
        itemContainer.setTranslationY(0.0f);
        itemContainer.getChildAt(0).setTranslationX(0.0f);
        topRowView.getNotificationsTray().setTranslationY(0.0f);
        SearchView searchView = topRowView.getSearchWidget();
        SearchOrb micOrb = searchView.getMicOrb();
        SearchOrb keyboardOrb = searchView.getKeyboardOrb();
        micOrb.getFocusIndicator().setBackgroundColor(postInfo.searchViewMicBackgroundColor);
        keyboardOrb.getFocusIndicator().setBackgroundColor(postInfo.searchViewKeyboardBackgroundColor);
        View hotwordDisabledIcon = searchView.getHotwordDisabledIcon();
        hotwordDisabledIcon.setVisibility(postInfo.searchViewHotwordIconVisibility);
        hotwordDisabledIcon.setAlpha(1.0f);
        View keyboardOrbContainer = searchView.getKeyboardOrbContainer();
        keyboardOrbContainer.setScaleX(postInfo.searchViewKeyboardContainerScale);
        keyboardOrbContainer.setScaleY(postInfo.searchViewKeyboardContainerScale);
        keyboardOrb.setScaleX(postInfo.searchViewKeyboardOrbScale);
        keyboardOrb.setScaleY(postInfo.searchViewKeyboardOrbScale);
        keyboardOrb.setVisibility(postInfo.searchViewKeyboardOrbVisibility);
        keyboardOrb.setAlpha(1.0f);
        micOrb.setScaleX(postInfo.searchViewMicOrbScale);
        micOrb.setScaleY(postInfo.searchViewMicOrbScale);
        searchView.getTextSwitcher().setTranslationX(0.0f);
        searchView.getTextSwitcherContainer().setTranslationX(0.0f);
    }

    private void addBottomRowAnimations(@NonNull List<Animator> animators, @NonNull ConfigureChannelsRowView view, @NonNull MyHolderInfo preInfo, @NonNull MyHolderInfo postInfo) {
        for (int i = 0; i < preInfo.bottomRowItemTop.length; i++) {
            AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationXYAnimator(view.getChildAt(i), preInfo.bottomRowItemLeft[i], preInfo.bottomRowItemTop[i], postInfo.bottomRowItemLeft[i], postInfo.bottomRowItemTop[i], preInfo.bottomRowItemTranslationX[i], preInfo.bottomRowItemTranslationY[i], this.mMainInterpolator));
        }
        AnimUtil.addIfNotNull(animators, AnimUtil.createVisibilityAnimator(view.getDescriptionView(), preInfo.bottomRowDescriptionVisibility, postInfo.bottomRowDescriptionVisibility, postInfo.bottomRowDescriptionAlpha, this.mMainInterpolator));
        View button = view.getButton();
        AnimUtil.addIfNotNull(animators, AnimUtil.createScaleAnimator(button, preInfo.bottomRowButtonScale, postInfo.bottomRowButtonScale, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createBackgroundColorAnimator(button, preInfo.bottomRowButtonBackgroundColor, postInfo.bottomRowButtonBackgroundColor, this.mMainInterpolator));
    }

    /* access modifiers changed from: private */
    public void cleanUpAfterBottomRowAnimations(@NonNull ConfigureChannelsRowView view, @NonNull MyHolderInfo postInfo) {
        for (int i = 0; i < view.getChildCount(); i++) {
            view.getChildAt(i).setTranslationX(0.0f);
            view.getChildAt(i).setTranslationY(0.0f);
        }
        view.getDescriptionView().setVisibility(postInfo.bottomRowDescriptionVisibility);
        view.getDescriptionView().setAlpha(1.0f);
        View button = view.getButton();
        button.setBackgroundColor(postInfo.bottomRowButtonBackgroundColor);
        button.setScaleX(postInfo.bottomRowButtonScale);
        button.setScaleY(postInfo.bottomRowButtonScale);
    }

    @NonNull
    public RecyclerView.ItemAnimator.ItemHolderInfo obtainHolderInfo() {
        return new MyHolderInfo();
    }

    private static class MyHolderInfo extends RecyclerView.ItemAnimator.ItemHolderInfo {
        float actionsHintAlpha;
        int actionsHintLeft;
        int actionsHintTop;
        float actionsHintTranslationX;
        float actionsHintTranslationY;
        int actionsHintVisibility;
        int bottomRowButtonBackgroundColor;
        float bottomRowButtonScale;
        float bottomRowDescriptionAlpha;
        int bottomRowDescriptionVisibility;
        int[] bottomRowItemLeft;
        int[] bottomRowItemTop;
        float[] bottomRowItemTranslationX;
        float[] bottomRowItemTranslationY;
        Rect bounds;
        float emptyChannelMessageAlpha;
        int emptyChannelMessageLeft;
        int emptyChannelMessageTop;
        float emptyChannelMessageTranslationX;
        float emptyChannelMessageTranslationY;
        int emptyChannelMessageVisibility;
        Rect itemsListBounds;
        List<Rect> itemsListContainersBounds;
        float itemsTitleAlpha;
        @ColorInt
        int itemsTitleCurrentColor;
        int itemsTitleLeft;
        @ColorInt
        int itemsTitleStateColor;
        int itemsTitleTop;
        float itemsTitleTranslationX;
        float itemsTitleTranslationY;
        int itemsTitleVisibility;
        Rect logoBounds;
        Rect logoContainerBounds;
        float logoDimmingFactor;
        int logoElevation;
        float logoScale;
        float logoTitleAlpha;
        @ColorInt
        int logoTitleCurrentColor;
        int logoTitleLeft;
        /* access modifiers changed from: private */
        @ColorInt
        public int logoTitleStateColor;
        int logoTitleTop;
        float logoTitleTranslationX;
        float logoTitleTranslationY;
        int logoTitleVisibility;
        float logoTranslationX;
        float logoTranslationY;
        float logoTranslationZ;
        float metaContainerAlpha;
        int metaContainerLeft;
        int metaContainerTop;
        float metaContainerTranslationX;
        float metaContainerTranslationY;
        int metaContainerVisibility;
        float movingChannelBackgroundAlpha;
        int movingChannelBackgroundVisibility;
        float searchViewHotwordIconAlpha;
        int searchViewHotwordIconVisibility;
        @ColorInt
        int searchViewKeyboardBackgroundColor;
        float searchViewKeyboardContainerScale;
        float searchViewKeyboardOrbAlpha;
        float searchViewKeyboardOrbScale;
        int searchViewKeyboardOrbVisibility;
        @ColorInt
        int searchViewMicBackgroundColor;
        float searchViewMicOrbScale;
        int searchViewTextSwitcherContainerLeft;
        float searchViewTextSwitcherContainerTranslationX;
        int searchViewTextSwitcherLeft;
        float searchViewTextSwitcherTranslationX;
        float sponsoredChannelBackgroundAlpha;
        @ColorInt
        int sponsoredChannelBackgroundColor;
        int sponsoredChannelBackgroundHeight;
        float sponsoredChannelBackgroundScaleY;
        int sponsoredChannelBackgroundTop;
        float sponsoredChannelBackgroundTranslationY;
        int sponsoredChannelBackgroundVisibility;
        int topRowFirstItemLeft;
        float topRowFirstItemTranslationX;
        int topRowItemContainerTop;
        float topRowItemContainerTranslationY;
        int topRowNotificationsTrayTop;
        float topRowNotificationsTrayTranslationY;
        float zoomedOutLogoTitleAlpha;
        @ColorInt
        int zoomedOutLogoTitleCurrentColor;
        int zoomedOutLogoTitleLeft;
        @ColorInt
        int zoomedOutLogoTitleStateColor;
        int zoomedOutLogoTitleTop;
        float zoomedOutLogoTitleTranslationX;
        float zoomedOutLogoTitleTranslationY;
        int zoomedOutLogoTitleVisibility;

        private MyHolderInfo() {
            this.itemsListContainersBounds = new ArrayList(5);
        }

        @NonNull
        public RecyclerView.ItemAnimator.ItemHolderInfo setFrom(@NonNull RecyclerView.ViewHolder holder, int flags) {
            super.setFrom(holder, flags);
            this.bounds = AnimUtil.getBounds(holder.itemView);
            if (holder.itemView instanceof ChannelView) {
                ChannelView channelView = (ChannelView) holder.itemView;
                View itemsList = channelView.getItemsListView();
                this.itemsListBounds = AnimUtil.getBounds(itemsList);
                ViewParent parent = itemsList.getParent();
                while (true) {
                    View parent2 = (View) parent;
                    if (parent2 == holder.itemView) {
                        break;
                    }
                    this.itemsListContainersBounds.add(AnimUtil.getBounds(parent2));
                    parent = parent2.getParent();
                }
                View emptyChannelMessageView = channelView.getEmptyChannelMessage();
                this.emptyChannelMessageVisibility = channelView.getEmptyChannelMessageVisibility();
                this.emptyChannelMessageAlpha = emptyChannelMessageView.getAlpha();
                this.emptyChannelMessageLeft = emptyChannelMessageView.getLeft();
                this.emptyChannelMessageTop = emptyChannelMessageView.getTop();
                this.emptyChannelMessageTranslationX = emptyChannelMessageView.getTranslationX();
                this.emptyChannelMessageTranslationY = emptyChannelMessageView.getTranslationY();
                View logo = channelView.getChannelLogoImageView();
                this.logoBounds = AnimUtil.getBounds(logo);
                this.logoTranslationX = logo.getTranslationX();
                this.logoTranslationY = logo.getTranslationY();
                this.logoTranslationZ = logo.getTranslationZ();
                this.logoScale = logo.getScaleX();
                this.logoElevation = (int) logo.getElevation();
                this.logoContainerBounds = AnimUtil.getBounds((View) logo.getParent());
                this.logoDimmingFactor = channelView.getChannelLogoDimmingFactor();
                View sponsoredBackground = channelView.getSponsoredChannelBackground();
                this.sponsoredChannelBackgroundHeight = sponsoredBackground.getHeight();
                this.sponsoredChannelBackgroundTop = sponsoredBackground.getTop();
                this.sponsoredChannelBackgroundTranslationY = sponsoredBackground.getTranslationY();
                this.sponsoredChannelBackgroundScaleY = sponsoredBackground.getScaleY();
                this.sponsoredChannelBackgroundColor = ((ColorDrawable) sponsoredBackground.getBackground()).getColor();
                this.sponsoredChannelBackgroundVisibility = channelView.getSponsoredChannelBackgroundVisibility();
                this.sponsoredChannelBackgroundAlpha = sponsoredBackground.getAlpha();
                TextView logoTitle = channelView.getLogoTitle();
                this.logoTitleLeft = logoTitle.getLeft();
                this.logoTitleTop = logoTitle.getTop();
                this.logoTitleTranslationX = logoTitle.getTranslationX();
                this.logoTitleTranslationY = logoTitle.getTranslationY();
                this.logoTitleVisibility = channelView.getLogoTitleVisibility();
                this.logoTitleAlpha = logoTitle.getAlpha();
                this.logoTitleStateColor = channelView.getLogoTitleStateColor();
                this.logoTitleCurrentColor = logoTitle.getCurrentTextColor();
                TextView zoomedOutLogoTitle = channelView.getZoomedOutLogoTitle();
                this.zoomedOutLogoTitleLeft = zoomedOutLogoTitle.getLeft();
                this.zoomedOutLogoTitleTop = zoomedOutLogoTitle.getTop();
                this.zoomedOutLogoTitleTranslationX = zoomedOutLogoTitle.getTranslationX();
                this.zoomedOutLogoTitleTranslationY = zoomedOutLogoTitle.getTranslationY();
                this.zoomedOutLogoTitleVisibility = channelView.getZoomedOutLogoTitleVisibility();
                this.zoomedOutLogoTitleAlpha = zoomedOutLogoTitle.getAlpha();
                this.zoomedOutLogoTitleStateColor = channelView.getZoomedOutLogoTitleStateColor();
                this.zoomedOutLogoTitleCurrentColor = zoomedOutLogoTitle.getCurrentTextColor();
                TextView itemsTitle = channelView.getItemsTitle();
                this.itemsTitleLeft = itemsTitle.getLeft();
                this.itemsTitleTranslationX = itemsTitle.getTranslationX();
                this.itemsTitleTop = itemsTitle.getTop();
                this.itemsTitleTranslationY = itemsTitle.getTranslationY();
                this.itemsTitleVisibility = itemsTitle.getVisibility();
                this.itemsTitleAlpha = itemsTitle.getAlpha();
                this.itemsTitleStateColor = channelView.getItemsTitleStateColor();
                this.itemsTitleCurrentColor = itemsTitle.getCurrentTextColor();
                this.movingChannelBackgroundVisibility = channelView.getMovingChannelBackgroundVisibility();
                this.movingChannelBackgroundAlpha = channelView.getMovingChannelBackground().getAlpha();
                View actionsHint = channelView.getActionsHint();
                this.actionsHintLeft = actionsHint.getLeft();
                this.actionsHintTop = actionsHint.getTop();
                this.actionsHintTranslationX = actionsHint.getTranslationX();
                this.actionsHintTranslationY = actionsHint.getTranslationY();
                this.actionsHintVisibility = channelView.getActionsHintVisibility();
                this.actionsHintAlpha = actionsHint.getAlpha();
                View metaContainer = channelView.getItemMetadataView();
                this.metaContainerLeft = metaContainer.getLeft();
                this.metaContainerTop = metaContainer.getTop();
                this.metaContainerTranslationX = metaContainer.getTranslationX();
                this.metaContainerTranslationY = metaContainer.getTranslationY();
                this.metaContainerVisibility = channelView.getItemMetadataContainerVisibility();
                this.metaContainerAlpha = metaContainer.getAlpha();
            } else if (holder.itemView instanceof HomeTopRowView) {
                HomeTopRowView topRowView = (HomeTopRowView) holder.itemView;
                ViewGroup itemsContainer = topRowView.getItemsContainer();
                SearchView searchView = topRowView.getSearchWidget();
                this.topRowItemContainerTop = itemsContainer.getTop();
                this.topRowItemContainerTranslationY = itemsContainer.getTranslationY();
                NotificationsTrayView notificationsTrayView = topRowView.getNotificationsTray();
                this.topRowNotificationsTrayTop = notificationsTrayView.getTop();
                this.topRowNotificationsTrayTranslationY = notificationsTrayView.getTranslationY();
                this.topRowFirstItemLeft = itemsContainer.getChildAt(0).getLeft();
                this.topRowFirstItemTranslationX = itemsContainer.getChildAt(0).getTranslationX();
                SearchOrb micOrb = searchView.getMicOrb();
                SearchOrb keyboardOrb = searchView.getKeyboardOrb();
                this.searchViewMicBackgroundColor = ((ColorDrawable) micOrb.getFocusIndicator().getBackground()).getColor();
                this.searchViewMicOrbScale = micOrb.getScaleX();
                this.searchViewKeyboardBackgroundColor = ((ColorDrawable) keyboardOrb.getFocusIndicator().getBackground()).getColor();
                this.searchViewKeyboardOrbScale = keyboardOrb.getScaleX();
                this.searchViewKeyboardOrbVisibility = searchView.getKeyboardOrbVisibility();
                View keyboardContainer = searchView.getKeyboardOrbContainer();
                this.searchViewKeyboardOrbAlpha = keyboardContainer.getAlpha();
                View textSwitcherContainer = searchView.getTextSwitcherContainer();
                TextSwitcher textSwitcher = searchView.getTextSwitcher();
                View hotwordDisabled = searchView.getHotwordDisabledIcon();
                this.searchViewHotwordIconVisibility = searchView.getHotwordIconVisibility();
                this.searchViewHotwordIconAlpha = hotwordDisabled.getAlpha();
                this.searchViewTextSwitcherLeft = textSwitcher.getLeft();
                this.searchViewTextSwitcherTranslationX = textSwitcher.getTranslationX();
                this.searchViewKeyboardContainerScale = keyboardContainer.getScaleX();
                this.searchViewTextSwitcherContainerTranslationX = textSwitcherContainer.getTranslationX();
                this.searchViewTextSwitcherContainerLeft = textSwitcherContainer.getLeft();
            } else if (holder.itemView instanceof ConfigureChannelsRowView) {
                ConfigureChannelsRowView view = (ConfigureChannelsRowView) holder.itemView;
                this.bottomRowItemLeft = new int[view.getChildCount()];
                this.bottomRowItemTop = new int[view.getChildCount()];
                this.bottomRowItemTranslationX = new float[view.getChildCount()];
                this.bottomRowItemTranslationY = new float[view.getChildCount()];
                for (int i = 0; i < view.getChildCount(); i++) {
                    View child = view.getChildAt(i);
                    this.bottomRowItemLeft[i] = child.getLeft();
                    this.bottomRowItemTop[i] = child.getTop();
                    this.bottomRowItemTranslationX[i] = child.getTranslationX();
                    this.bottomRowItemTranslationY[i] = child.getTranslationY();
                }
                this.bottomRowDescriptionVisibility = view.getDescriptionVisibility();
                this.bottomRowDescriptionAlpha = view.getDescriptionView().getAlpha();
                this.bottomRowButtonBackgroundColor = ((ColorDrawable) view.getButton().getBackground()).getColor();
                this.bottomRowButtonScale = view.getButton().getScaleX();
            }
            return this;
        }

        public String toString() {
            int i = this.left;
            int i2 = this.top;
            int i3 = this.right;
            int i4 = this.bottom;
            String valueOf = String.valueOf(this.itemsListBounds);
            String valueOf2 = String.valueOf(this.itemsListContainersBounds);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 130 + String.valueOf(valueOf2).length());
            sb.append("MyHolderInfo left=");
            sb.append(i);
            sb.append(", top=");
            sb.append(i2);
            sb.append(", right=");
            sb.append(i3);
            sb.append(", bottom=");
            sb.append(i4);
            sb.append(", itemsListBounds=");
            sb.append(valueOf);
            sb.append(", itemsListContainerBounds=");
            sb.append(valueOf2);
            return sb.toString();
        }
    }
}
