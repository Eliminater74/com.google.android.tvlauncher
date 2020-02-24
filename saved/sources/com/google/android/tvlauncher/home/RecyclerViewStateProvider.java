package com.google.android.tvlauncher.home;

import android.support.p004v7.widget.RecyclerView;

public interface RecyclerViewStateProvider {
    boolean isAnimating();

    boolean isAnimating(RecyclerView.ItemAnimator.ItemAnimatorFinishedListener itemAnimatorFinishedListener);
}
