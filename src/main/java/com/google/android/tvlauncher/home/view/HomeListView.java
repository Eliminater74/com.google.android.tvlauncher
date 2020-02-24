package com.google.android.tvlauncher.home.view;

import android.content.Context;
import android.support.p004v7.widget.RecyclerView;
import android.util.AttributeSet;
import androidx.leanback.widget.VerticalGridView;

public class HomeListView extends VerticalGridView {
    private final RecyclerView.ItemAnimator.ItemAnimatorFinishedListener mOnAnimationsFinishedListener = new RecyclerView.ItemAnimator.ItemAnimatorFinishedListener() {
        public void onAnimationsFinished() {
            if (!HomeListView.this.isComputingLayout() && HomeListView.this.isLayoutFrozen()) {
                HomeListView.this.setLayoutFrozen(false);
                HomeListView.this.requestLayout();
            }
        }
    };

    public HomeListView(Context context) {
        super(context);
        init();
    }

    public HomeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HomeListView(Context context, AttributeSet attrs, int defStyle) {
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
