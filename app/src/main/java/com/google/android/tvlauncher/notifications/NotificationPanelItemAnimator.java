package com.google.android.tvlauncher.notifications;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.p004v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.google.android.tvrecommendations.shared.util.AnimUtil;
import com.google.android.tvrecommendations.shared.view.BoundsItemAnimator;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.util.ArrayList;
import java.util.List;

public class NotificationPanelItemAnimator extends BoundsItemAnimator {
    private static final int CHANGE_DURATION_MS = 175;
    private static final boolean DEBUG = false;
    private static final String TAG = "NotifPanelItemAnimator";
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

    public NotificationPanelItemAnimator() {
        setChangeDuration(175);
    }

    /* access modifiers changed from: protected */
    public boolean animateInPlaceChange(@NonNull final RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo preLayoutInfo, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo postLayoutInfo) {
        cancelChangeAnimation(viewHolder);
        NotifItemHolderInfo preInfo = (NotifItemHolderInfo) preLayoutInfo;
        final NotifItemHolderInfo postInfo = (NotifItemHolderInfo) postLayoutInfo;
        NotificationPanelItemView view = (NotificationPanelItemView) viewHolder.itemView;
        View itemContainer = view.getItemContainer();
        View title = view.getTitleView();
        View text = view.getTextView();
        List<Animator> animators = new ArrayList<>();
        AnimUtil.addIfNotNull(animators, AnimUtil.createBoundsAnimator(viewHolder.itemView, preInfo.bounds, postInfo.bounds, this.mMainInterpolator));
        if (preInfo.itemContainerAlpha != postInfo.itemContainerAlpha) {
            AnimUtil.addIfNotNull(animators, ObjectAnimator.ofFloat(itemContainer, View.ALPHA, preInfo.itemContainerAlpha, postInfo.itemContainerAlpha));
        }
        AnimUtil.addIfNotNull(animators, AnimUtil.createTranslationXAnimator(itemContainer, preInfo.itemContainerLeft, postInfo.itemContainerLeft, preInfo.itemsContainerTranslationX, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createBoundsAnimator(title, preInfo.titleBounds, postInfo.titleBounds, this.mMainInterpolator));
        AnimUtil.addIfNotNull(animators, AnimUtil.createBoundsAnimator(text, preInfo.textBounds, postInfo.textBounds, this.mMainInterpolator));
        AnimatorSet overallAnimation = new AnimatorSet();
        overallAnimation.playTogether(animators);
        overallAnimation.setDuration(getChangeDuration());
        overallAnimation.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                NotificationPanelItemAnimator.this.dispatchChangeStarting(viewHolder, true);
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationEnd(Animator animation) {
                animation.removeListener(this);
                AnimUtil.setBounds(viewHolder.itemView, postInfo.bounds);
                NotificationPanelItemAnimator.this.cleanUpAfterAnimations((NotificationPanelItemView) viewHolder.itemView, postInfo);
                NotificationPanelItemAnimator.this.dispatchChangeFinished(viewHolder, true);
                NotificationPanelItemAnimator.this.mChangeAnimations.remove(viewHolder);
                NotificationPanelItemAnimator.this.dispatchFinishedWhenDone();
            }
        });
        this.mChangeAnimations.put(viewHolder, overallAnimation);
        overallAnimation.start();
        return true;
    }

    /* access modifiers changed from: private */
    public void cleanUpAfterAnimations(@NonNull NotificationPanelItemView view, @NonNull NotifItemHolderInfo postInfo) {
        view.getItemContainer().setTranslationX(0.0f);
        view.getItemContainer().setAlpha(postInfo.itemContainerAlpha);
    }

    public RecyclerView.ItemAnimator.ItemHolderInfo obtainHolderInfo() {
        return new NotifItemHolderInfo();
    }

    private static class NotifItemHolderInfo extends RecyclerView.ItemAnimator.ItemHolderInfo {
        Rect bounds;
        float itemContainerAlpha;
        int itemContainerLeft;
        float itemsContainerTranslationX;
        Rect textBounds;
        Rect titleBounds;

        private NotifItemHolderInfo() {
        }

        public RecyclerView.ItemAnimator.ItemHolderInfo setFrom(RecyclerView.ViewHolder holder, int flags) {
            super.setFrom(holder, flags);
            this.bounds = AnimUtil.getBounds(holder.itemView);
            NotificationPanelItemView itemView = (NotificationPanelItemView) holder.itemView;
            View itemContainer = itemView.getItemContainer();
            this.itemContainerLeft = itemContainer.getLeft();
            this.itemsContainerTranslationX = itemContainer.getTranslationX();
            this.itemContainerAlpha = itemContainer.getAlpha();
            this.titleBounds = AnimUtil.getBounds(itemView.getTitleView());
            this.textBounds = AnimUtil.getBounds(itemView.getTextView());
            return this;
        }

        public String toString() {
            int i = this.left;
            int i2 = this.top;
            int i3 = this.right;
            int i4 = this.bottom;
            String valueOf = String.valueOf(this.bounds);
            int i5 = this.itemContainerLeft;
            float f = this.itemsContainerTranslationX;
            float f2 = this.itemContainerAlpha;
            String valueOf2 = String.valueOf(this.titleBounds);
            String valueOf3 = String.valueOf(this.textBounds);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + ClientAnalytics.LogRequest.LogSource.SOCIETY_ANDROID_PRIMES_VALUE + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length());
            sb.append("NotifItemHolderInfo left=");
            sb.append(i);
            sb.append(", top=");
            sb.append(i2);
            sb.append(", right=");
            sb.append(i3);
            sb.append(", bottom=");
            sb.append(i4);
            sb.append(", bounds=");
            sb.append(valueOf);
            sb.append(", itemContainerLeft=");
            sb.append(i5);
            sb.append(", itemContainerTranslationX=");
            sb.append(f);
            sb.append(", itemContainerAlpha=");
            sb.append(f2);
            sb.append(", titleBounds=");
            sb.append(valueOf2);
            sb.append(", textBounds=");
            sb.append(valueOf3);
            return sb.toString();
        }
    }
}
