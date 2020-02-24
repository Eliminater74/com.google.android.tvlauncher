package com.google.android.tvlauncher.home;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.p004v7.widget.RecyclerView;
import android.view.KeyEvent;
import androidx.leanback.widget.BaseGridView;
import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.VerticalGridView;
import com.google.android.tvlauncher.util.Util;

class RecyclerViewFastScrollingManager {
    private static final boolean DEBUG = false;
    private static final int DEFAULT_EXIT_FAST_SCROLLING_DELAY_MS = 450;
    private static final int DPAD_EVENTS_COUNT_FOR_FAST_SCROLLING = 1;
    private static final String TAG = "RVFastScrollingHelper";
    private boolean mAnimatorEnabled;
    private boolean mAnimatorInitialized;
    /* access modifiers changed from: private */
    public int mExitFastScrollingDelayMs = 450;
    /* access modifiers changed from: private */
    public final Runnable mExitFastScrollingRunnable = new RecyclerViewFastScrollingManager$$Lambda$0(this);
    /* access modifiers changed from: private */
    public boolean mFastScrollingEnabled;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler();
    private RecyclerView.ItemAnimator mItemAnimator;
    /* access modifiers changed from: private */
    public int mKeyCodeNext;
    /* access modifiers changed from: private */
    public int mKeyCodePrevious;
    /* access modifiers changed from: private */
    public BaseGridView mList;
    private OnFastScrollingChangedListener mListener;
    private final BaseGridView.OnUnhandledKeyListener mOnUnhandledKeyListener = new BaseGridView.OnUnhandledKeyListener() {
        public boolean onUnhandledKey(KeyEvent event) {
            if (event.getKeyCode() != RecyclerViewFastScrollingManager.this.mKeyCodePrevious && event.getKeyCode() != RecyclerViewFastScrollingManager.this.mKeyCodeNext) {
                return false;
            }
            if (!RecyclerViewFastScrollingManager.this.mFastScrollingEnabled && event.getAction() == 0 && event.getRepeatCount() >= 1 && !Util.isAccessibilityEnabled(RecyclerViewFastScrollingManager.this.mList.getContext())) {
                RecyclerViewFastScrollingManager.this.mHandler.removeCallbacks(RecyclerViewFastScrollingManager.this.mExitFastScrollingRunnable);
                RecyclerViewFastScrollingManager.this.setFastScrollingEnabled(true);
                return false;
            } else if (!RecyclerViewFastScrollingManager.this.mFastScrollingEnabled || event.getAction() != 1) {
                return false;
            } else {
                RecyclerViewFastScrollingManager.this.mHandler.removeCallbacks(RecyclerViewFastScrollingManager.this.mExitFastScrollingRunnable);
                RecyclerViewFastScrollingManager.this.mHandler.postDelayed(RecyclerViewFastScrollingManager.this.mExitFastScrollingRunnable, (long) RecyclerViewFastScrollingManager.this.mExitFastScrollingDelayMs);
                return false;
            }
        }
    };
    private boolean mScrollAllowedDuringFastScrolling = true;
    private boolean mScrollEnabled;
    private boolean mScrollInitialized;

    public interface OnFastScrollingChangedListener {
        void onFastScrollingChanged(boolean z);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$new$0$RecyclerViewFastScrollingManager() {
        setFastScrollingEnabled(false);
    }

    RecyclerViewFastScrollingManager(@NonNull BaseGridView list, @NonNull RecyclerView.ItemAnimator itemAnimator) {
        this.mItemAnimator = itemAnimator;
        this.mList = list;
        if (list instanceof HorizontalGridView) {
            this.mKeyCodePrevious = 21;
            this.mKeyCodeNext = 22;
        } else if (list instanceof VerticalGridView) {
            this.mKeyCodePrevious = 20;
            this.mKeyCodeNext = 19;
        } else {
            throw new IllegalArgumentException("Provided list must be a HorizontalGridView or a VerticalGridView");
        }
        this.mList.setOnUnhandledKeyListener(this.mOnUnhandledKeyListener);
    }

    /* access modifiers changed from: package-private */
    public void setOnFastScrollingChangedListener(OnFastScrollingChangedListener listener) {
        this.mListener = listener;
    }

    /* access modifiers changed from: package-private */
    public void setExitFastScrollingDelayMs(int delayMs) {
        this.mExitFastScrollingDelayMs = delayMs;
    }

    /* access modifiers changed from: package-private */
    public boolean isFastScrollingEnabled() {
        return this.mFastScrollingEnabled;
    }

    /* access modifiers changed from: private */
    public void setFastScrollingEnabled(boolean enabled) {
        this.mFastScrollingEnabled = enabled;
        boolean z = true;
        setAnimatorEnabled(!this.mFastScrollingEnabled);
        if (!this.mScrollAllowedDuringFastScrolling || !this.mFastScrollingEnabled) {
            z = false;
        }
        setScrollEnabled(z);
        OnFastScrollingChangedListener onFastScrollingChangedListener = this.mListener;
        if (onFastScrollingChangedListener != null) {
            onFastScrollingChangedListener.onFastScrollingChanged(this.mFastScrollingEnabled);
        }
    }

    public boolean isScrollAllowedDuringFastScrolling() {
        return this.mScrollAllowedDuringFastScrolling;
    }

    /* access modifiers changed from: package-private */
    public void setScrollAllowedDuringFastScrolling(boolean allowed) {
        this.mScrollAllowedDuringFastScrolling = allowed;
    }

    /* access modifiers changed from: package-private */
    public void setAnimatorEnabled(boolean enabled) {
        if (!Util.areHomeScreenAnimationsEnabled(this.mList.getContext())) {
            enabled = false;
        }
        if (this.mAnimatorEnabled != enabled || !this.mAnimatorInitialized) {
            this.mAnimatorEnabled = enabled;
            this.mAnimatorInitialized = true;
            if (this.mAnimatorEnabled) {
                this.mList.setItemAnimator(this.mItemAnimator);
            } else {
                this.mList.setItemAnimator(null);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setScrollEnabled(boolean enabled) {
        if (!Util.areHomeScreenAnimationsEnabled(this.mList.getContext())) {
            enabled = true;
        }
        if (this.mScrollEnabled != enabled || !this.mScrollInitialized) {
            this.mScrollEnabled = enabled;
            this.mScrollInitialized = true;
            this.mList.setScrollEnabled(this.mScrollEnabled);
        }
    }
}
