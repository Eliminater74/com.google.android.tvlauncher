package com.google.android.tvrecommendations.shared.view;

import android.animation.Animator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p004v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BoundsItemAnimator extends DefaultItemAnimator {
    private static final boolean DEBUG = false;
    private static final String TAG = "BoundsItemAnimator";
    /* access modifiers changed from: protected */
    public Map<RecyclerView.ViewHolder, Animator> mChangeAnimations = new HashMap();
    private Map<RecyclerView.ViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo> mPostInfos = new HashMap();
    private Map<RecyclerView.ViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo> mPreInfos = new HashMap();

    /* access modifiers changed from: protected */
    public abstract boolean animateInPlaceChange(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2);

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

    @NonNull
    public RecyclerView.ItemAnimator.ItemHolderInfo recordPreLayoutInformation(@NonNull RecyclerView.State state, @NonNull RecyclerView.ViewHolder viewHolder, int changeFlags, @NonNull List<Object> payloads) {
        RecyclerView.ItemAnimator.ItemHolderInfo info = super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads);
        this.mPreInfos.put(viewHolder, info);
        return info;
    }

    @NonNull
    public RecyclerView.ItemAnimator.ItemHolderInfo recordPostLayoutInformation(@NonNull RecyclerView.State state, @NonNull RecyclerView.ViewHolder viewHolder) {
        RecyclerView.ItemAnimator.ItemHolderInfo info = super.recordPostLayoutInformation(state, viewHolder);
        this.mPostInfos.put(viewHolder, info);
        return info;
    }

    public boolean animateAppearance(@NonNull RecyclerView.ViewHolder viewHolder, @Nullable RecyclerView.ItemAnimator.ItemHolderInfo preLayoutInfo, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo postLayoutInfo) {
        boolean result = super.animateAppearance(viewHolder, preLayoutInfo, postLayoutInfo);
        cleanUpPostAnimation(viewHolder);
        return result;
    }

    public boolean animateDisappearance(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo preLayoutInfo, @Nullable RecyclerView.ItemAnimator.ItemHolderInfo postLayoutInfo) {
        boolean result = super.animateDisappearance(viewHolder, preLayoutInfo, postLayoutInfo);
        cleanUpPostAnimation(viewHolder);
        return result;
    }

    public boolean animatePersistence(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo preInfo, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo postInfo) {
        boolean result = super.animatePersistence(viewHolder, preInfo, postInfo);
        cleanUpPostAnimation(viewHolder);
        return result;
    }

    public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder, @NonNull RecyclerView.ViewHolder newHolder, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo preInfo, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo postInfo) {
        if (oldHolder != newHolder) {
            boolean result = super.animateChange(oldHolder, newHolder, preInfo, postInfo);
            cleanUpPostAnimation(oldHolder);
            cleanUpPostAnimation(newHolder);
            return result;
        }
        boolean result2 = animateInPlaceChange(newHolder, preInfo, postInfo);
        cleanUpPostAnimation(newHolder);
        return result2;
    }

    public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        RecyclerView.ItemAnimator.ItemHolderInfo preInfo = this.mPreInfos.remove(holder);
        RecyclerView.ItemAnimator.ItemHolderInfo postInfo = this.mPostInfos.remove(holder);
        if (preInfo != null && postInfo != null) {
            return animateChange(holder, holder, preInfo, postInfo);
        }
        boolean result = super.animateMove(holder, fromX, fromY, toX, toY);
        cleanUpPostAnimation(holder);
        return result;
    }

    private void cleanUpPostAnimation(@NonNull RecyclerView.ViewHolder item) {
        this.mPreInfos.remove(item);
        this.mPostInfos.remove(item);
    }

    /* access modifiers changed from: protected */
    public void cancelChangeAnimation(@NonNull RecyclerView.ViewHolder newHolder) {
        Animator runningAnimation = this.mChangeAnimations.remove(newHolder);
        if (runningAnimation != null) {
            runningAnimation.cancel();
        }
    }

    public boolean isRunning() {
        return super.isRunning() || !this.mChangeAnimations.isEmpty();
    }

    /* access modifiers changed from: protected */
    public void dispatchFinishedWhenDone() {
        if (!isRunning()) {
            this.mPreInfos.clear();
            this.mPostInfos.clear();
        }
        super.dispatchFinishedWhenDone();
    }

    public void endAnimation(RecyclerView.ViewHolder item) {
        cancelChangeAnimation(item);
        super.endAnimation(item);
    }

    public void endAnimations() {
        for (Animator animator : new ArrayList<>(this.mChangeAnimations.values())) {
            animator.cancel();
        }
        if (this.mChangeAnimations.size() != 0) {
            Log.w(TAG, "endAnimations: All animations canceled but collection is not empty");
        }
        this.mChangeAnimations.clear();
        this.mPreInfos.clear();
        this.mPostInfos.clear();
        super.endAnimations();
    }
}
