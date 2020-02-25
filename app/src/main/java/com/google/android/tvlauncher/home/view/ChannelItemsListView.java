package com.google.android.tvlauncher.home.view;

import android.content.Context;
import android.support.p004v7.widget.RecyclerView;
import android.util.AttributeSet;

import androidx.leanback.widget.HorizontalGridView;

public class ChannelItemsListView extends HorizontalGridView {
    private final RecyclerView.ItemAnimator.ItemAnimatorFinishedListener mOnAnimationsFinishedListener = new RecyclerView.ItemAnimator.ItemAnimatorFinishedListener() {
        public void onAnimationsFinished() {
            if (!ChannelItemsListView.this.isComputingLayout() && ChannelItemsListView.this.isLayoutFrozen()) {
                ChannelItemsListView.this.setLayoutFrozen(false);
                ChannelItemsListView.this.requestLayout();
            }
        }
    };

    public ChannelItemsListView(Context context) {
        super(context);
        init();
    }

    public ChannelItemsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChannelItemsListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setHasFixedSize(false);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        RecyclerView.ItemAnimator animator = getItemAnimator();
        if (animator != null && animator.isRunning()) {
            animator.isRunning(this.mOnAnimationsFinishedListener);
            setLayoutFrozen(true);
        } else if (isLayoutFrozen()) {
            setLayoutFrozen(false);
        }
    }
}
