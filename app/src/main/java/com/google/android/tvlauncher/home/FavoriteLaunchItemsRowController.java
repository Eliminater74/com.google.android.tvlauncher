package com.google.android.tvlauncher.home;

import android.content.Context;
import android.content.res.Resources;
import android.support.p004v7.widget.DefaultItemAnimator;
import android.support.p004v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import androidx.leanback.widget.HorizontalGridView;
import com.google.android.tvlauncher.BackHomeControllerListeners;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.analytics.EventLogger;
import com.google.android.tvlauncher.appsview.AppsViewActivity;
import com.google.android.tvlauncher.home.util.ChannelUtil;
import com.google.android.tvlauncher.home.util.HomeAppStateUtil;
import com.google.android.tvlauncher.home.view.ChannelItemsAnimator;
import com.google.android.tvlauncher.home.view.ChannelView;
import com.google.android.tvlauncher.util.Util;

class FavoriteLaunchItemsRowController implements HomeRow, ChannelView.OnPerformMainActionListener, ChannelView.OnStateChangeGesturePerformedListener, BackHomeControllerListeners.OnBackPressedListener, BackHomeControllerListeners.OnHomePressedListener, BackHomeControllerListeners.OnHomeNotHandledListener, BackHomeControllerListeners.OnBackNotHandledListener, RecyclerViewStateProvider {
    private static final boolean DEBUG = false;
    private static final String TAG = "FavLaunchItemController";
    /* access modifiers changed from: private */
    public final ChannelView mChannelView;
    private final EventLogger mEventLogger;
    private RecyclerViewFastScrollingManager mFastScrollingManager;
    private FavoriteLaunchItemsRowEditModeActionCallbacks mFavoriteLaunchItemsRowEditModeActionCallbacks;
    private boolean mHomeIsFastScrolling;
    private RecyclerViewStateProvider mHomeListStateProvider;
    private FavoriteLaunchItemsAdapter mItemsAdapter;
    private final int mItemsListDefaultPaddingEnd;
    private final int mItemsListDefaultWindowAlignmentOffset;
    /* access modifiers changed from: private */
    public final HorizontalGridView mItemsListView = this.mChannelView.getItemsListView();
    private BackHomeControllerListeners.OnBackNotHandledListener mOnBackNotHandledListener;
    private BackHomeControllerListeners.OnHomeNotHandledListener mOnHomeNotHandledListener;
    private OnHomeRowSelectedListener mOnHomeRowSelectedListener;
    private OnHomeStateChangeListener mOnHomeStateChangeListener;

    FavoriteLaunchItemsRowController(ChannelView channelView, EventLogger eventLogger) {
        this.mChannelView = channelView;
        this.mEventLogger = eventLogger;
        this.mChannelView.setOnPerformMainActionListener(this);
        this.mChannelView.setOnStateChangeGesturePerformedListener(this);
        this.mChannelView.setAllowMoving(false);
        this.mChannelView.setAllowRemoving(false);
        this.mChannelView.setShowItemMeta(false);
        this.mChannelView.setShowItemsTitle(false);
        this.mChannelView.setStateSettings(ChannelUtil.getAppsRowStateSettings(channelView.getContext()));
        this.mChannelView.invalidateState();
        Context context = this.mChannelView.getContext();
        String title = context.getString(C1188R.string.action_apps_view);
        this.mChannelView.setLogoTitle(title);
        this.mChannelView.setLogoContentDescription(title);
        this.mChannelView.setZoomedOutLogoTitle(title);
        Resources resources = context.getResources();
        this.mItemsListDefaultWindowAlignmentOffset = resources.getDimensionPixelOffset(C1188R.dimen.home_app_channel_items_list_default_window_alignment_offset);
        this.mItemsListDefaultPaddingEnd = resources.getDimensionPixelOffset(C1188R.dimen.home_app_channel_items_list_default_padding_end);
        ChannelUtil.configureAppRowItemsListAlignment(this.mItemsListView);
        this.mChannelView.setItemsListWindowAlignmentOffset(this.mItemsListDefaultWindowAlignmentOffset);
        this.mChannelView.setItemsListEndPadding(this.mItemsListDefaultPaddingEnd);
        ImageView logoView = this.mChannelView.getChannelLogoImageView();
        logoView.setImageDrawable(context.getDrawable(C1188R.C1189drawable.apps_view_logo));
        logoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        this.mFavoriteLaunchItemsRowEditModeActionCallbacks = new FavoriteLaunchItemsRowEditModeActionCallbacks() {
            public void onEnterEditMode() {
                FavoriteLaunchItemsRowController.this.mChannelView.setAllowZoomOut(false);
                if (!Util.areHomeScreenAnimationsEnabled(FavoriteLaunchItemsRowController.this.mChannelView.getContext())) {
                    FavoriteLaunchItemsRowController.this.mItemsListView.setItemAnimator(new DefaultItemAnimator());
                }
            }

            public void onExitEditMode() {
                FavoriteLaunchItemsRowController.this.mChannelView.setAllowZoomOut(true);
                if (!Util.areHomeScreenAnimationsEnabled(FavoriteLaunchItemsRowController.this.mChannelView.getContext())) {
                    FavoriteLaunchItemsRowController.this.mItemsListView.setItemAnimator(null);
                }
            }
        };
    }

    public void setOnHomeStateChangeListener(OnHomeStateChangeListener listener) {
        this.mOnHomeStateChangeListener = listener;
    }

    public void setOnHomeRowSelectedListener(OnHomeRowSelectedListener listener) {
        this.mOnHomeRowSelectedListener = listener;
    }

    public void setOnHomeRowRemovedListener(OnHomeRowRemovedListener listener) {
    }

    public void setHomeIsFastScrolling(boolean homeIsFastScrolling) {
        if (this.mHomeIsFastScrolling != homeIsFastScrolling) {
            this.mHomeIsFastScrolling = homeIsFastScrolling;
            updateStateForHomeFastScrolling();
        }
    }

    public View getView() {
        return this.mChannelView;
    }

    /* access modifiers changed from: package-private */
    public void setHomeListStateProvider(RecyclerViewStateProvider homeListStateProvider) {
        this.mHomeListStateProvider = homeListStateProvider;
    }

    /* access modifiers changed from: package-private */
    public void setSelectedItemPosition(int position) {
        if (this.mItemsListView.getAdapter() == null || position < 0 || position >= this.mItemsListView.getAdapter().getItemCount()) {
            return;
        }
        if (Util.areHomeScreenAnimationsEnabled(this.mChannelView.getContext())) {
            this.mItemsListView.setSelectedPosition(position);
        } else {
            this.mItemsListView.setSelectedPositionSmooth(position);
        }
    }

    /* access modifiers changed from: package-private */
    public void setOnBackNotHandledListener(BackHomeControllerListeners.OnBackNotHandledListener listener) {
        this.mOnBackNotHandledListener = listener;
    }

    /* access modifiers changed from: package-private */
    public void setOnHomeNotHandledListener(BackHomeControllerListeners.OnHomeNotHandledListener listener) {
        this.mOnHomeNotHandledListener = listener;
    }

    private void ensureItemListIsSetUp() {
        if (this.mItemsAdapter == null) {
            this.mItemsAdapter = new FavoriteLaunchItemsAdapter(this.mChannelView.getContext(), this.mEventLogger);
            this.mItemsAdapter.setAppsRowEditModeActionCallbacks(this.mFavoriteLaunchItemsRowEditModeActionCallbacks);
            this.mItemsAdapter.setOnHomeNotHandledListener(this);
            this.mItemsAdapter.setOnBackNotHandledListener(this);
            this.mItemsAdapter.setListStateProvider(this);
            this.mItemsAdapter.setHomeListStateProvider(this.mHomeListStateProvider);
            this.mItemsListView.setAdapter(this.mItemsAdapter);
            this.mFastScrollingManager = new RecyclerViewFastScrollingManager(this.mItemsListView, new ChannelItemsAnimator());
            updateStateForHomeFastScrolling();
        }
    }

    private void updateStateForHomeFastScrolling() {
        this.mFastScrollingManager.setAnimatorEnabled(!this.mHomeIsFastScrolling);
        this.mFastScrollingManager.setScrollEnabled(false);
    }

    /* access modifiers changed from: package-private */
    public void bind(int channelViewState) {
        ensureItemListIsSetUp();
        this.mChannelView.setState(channelViewState);
        int oldAppState = this.mItemsAdapter.getAppState();
        int newAppState = getAppState(channelViewState);
        this.mItemsAdapter.setAppState(newAppState);
        updateItemsListPosition(newAppState, oldAppState);
    }

    private void updateItemsListPosition(int newState, int oldState) {
        if (newState != oldState && HomeAppStateUtil.isZoomedOutState(newState) && this.mItemsAdapter.getItemCount() > 1 && this.mItemsListView.getSelectedPosition() != 0) {
            this.mItemsListView.scrollToPosition(0);
        }
    }

    private int getAppState(int channelViewState) {
        switch (channelViewState) {
            case 0:
                return 2;
            case 1:
                return 0;
            case 2:
            case 15:
                return 1;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 11:
            case 13:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
                String valueOf = String.valueOf(ChannelView.stateToString(channelViewState));
                throw new IllegalStateException(valueOf.length() != 0 ? "Unsupported Apps row state ".concat(valueOf) : new String("Unsupported Apps row state "));
            case 8:
                return 4;
            case 9:
                return 3;
            case 10:
                return 5;
            case 12:
                return 6;
            case 14:
                return 7;
            default:
                return 0;
        }
    }

    public void onPerformMainAction(ChannelView v) {
        AppsViewActivity.startAppsViewActivity(null, v.getContext());
    }

    public void onStateChangeGesturePerformed(ChannelView v, int newState) {
        switch (newState) {
            case 0:
                OnHomeRowSelectedListener onHomeRowSelectedListener = this.mOnHomeRowSelectedListener;
                if (onHomeRowSelectedListener != null) {
                    onHomeRowSelectedListener.onHomeRowSelected(this);
                }
                OnHomeStateChangeListener onHomeStateChangeListener = this.mOnHomeStateChangeListener;
                if (onHomeStateChangeListener != null) {
                    onHomeStateChangeListener.onHomeStateChange(0);
                    return;
                }
                return;
            case 1:
                OnHomeStateChangeListener onHomeStateChangeListener2 = this.mOnHomeStateChangeListener;
                if (onHomeStateChangeListener2 != null) {
                    onHomeStateChangeListener2.onHomeStateChange(0);
                    return;
                }
                return;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
                String valueOf = String.valueOf(ChannelView.stateToString(newState));
                throw new IllegalStateException(valueOf.length() != 0 ? "Unsupported ChannelView state change gesture: ".concat(valueOf) : new String("Unsupported ChannelView state change gesture: "));
            case 8:
                OnHomeRowSelectedListener onHomeRowSelectedListener2 = this.mOnHomeRowSelectedListener;
                if (onHomeRowSelectedListener2 != null) {
                    onHomeRowSelectedListener2.onHomeRowSelected(this);
                }
                OnHomeStateChangeListener onHomeStateChangeListener3 = this.mOnHomeStateChangeListener;
                if (onHomeStateChangeListener3 != null) {
                    onHomeStateChangeListener3.onHomeStateChange(1);
                    return;
                }
                return;
            case 9:
                OnHomeStateChangeListener onHomeStateChangeListener4 = this.mOnHomeStateChangeListener;
                if (onHomeStateChangeListener4 != null) {
                    onHomeStateChangeListener4.onHomeStateChange(1);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void onBackPressed(Context c) {
        if (this.mItemsListView.getAdapter() != null) {
            if (this.mChannelView.getState() == 0 && this.mItemsListView.getAdapter().getItemCount() > 0) {
                HorizontalGridView horizontalGridView = this.mItemsListView;
                RecyclerView.ViewHolder selectedViewHolder = horizontalGridView.findViewHolderForAdapterPosition(horizontalGridView.getSelectedPosition());
                if (selectedViewHolder instanceof BackHomeControllerListeners.OnBackPressedListener) {
                    ((BackHomeControllerListeners.OnBackPressedListener) selectedViewHolder).onBackPressed(c);
                    return;
                }
            }
            onBackNotHandled(c);
        }
    }

    public void onHomePressed(Context c) {
        if (this.mItemsListView.getAdapter() != null) {
            if (this.mChannelView.getState() == 0 && this.mItemsListView.getAdapter().getItemCount() > 0) {
                HorizontalGridView horizontalGridView = this.mItemsListView;
                RecyclerView.ViewHolder selectedViewHolder = horizontalGridView.findViewHolderForAdapterPosition(horizontalGridView.getSelectedPosition());
                if (selectedViewHolder instanceof BackHomeControllerListeners.OnHomePressedListener) {
                    ((BackHomeControllerListeners.OnHomePressedListener) selectedViewHolder).onHomePressed(c);
                    return;
                }
            }
            onHomeNotHandled(c);
        }
    }

    private boolean selectFirstItemIfNeeded() {
        if (this.mItemsListView.getAdapter() == null || this.mChannelView.getState() != 0 || this.mItemsListView.getAdapter().getItemCount() <= 0 || this.mItemsListView.getSelectedPosition() == 0) {
            return false;
        }
        setSelectedItemPosition(0);
        return true;
    }

    public void onHomeNotHandled(Context c) {
        BackHomeControllerListeners.OnHomeNotHandledListener onHomeNotHandledListener;
        if (!selectFirstItemIfNeeded() && (onHomeNotHandledListener = this.mOnHomeNotHandledListener) != null) {
            onHomeNotHandledListener.onHomeNotHandled(c);
        }
    }

    public void onBackNotHandled(Context c) {
        BackHomeControllerListeners.OnBackNotHandledListener onBackNotHandledListener;
        if (!selectFirstItemIfNeeded() && (onBackNotHandledListener = this.mOnBackNotHandledListener) != null) {
            onBackNotHandledListener.onBackNotHandled(c);
        }
    }

    public boolean isAnimating() {
        return this.mItemsListView.getItemAnimator() != null && this.mItemsListView.getItemAnimator().isRunning();
    }

    public boolean isAnimating(RecyclerView.ItemAnimator.ItemAnimatorFinishedListener listener) {
        HorizontalGridView horizontalGridView = this.mItemsListView;
        if (horizontalGridView != null && horizontalGridView.getItemAnimator() != null) {
            return this.mItemsListView.getItemAnimator().isRunning(listener);
        }
        if (listener == null) {
            return false;
        }
        listener.onAnimationsFinished();
        return false;
    }
}
