package com.google.android.tvlauncher.home;

import android.content.Context;
import android.support.p004v7.widget.RecyclerView;
import android.view.View;
import androidx.leanback.widget.HorizontalGridView;
import com.google.android.tvlauncher.BackHomeControllerListeners;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.analytics.EventLogger;
import com.google.android.tvlauncher.data.TvDataManager;
import com.google.android.tvlauncher.home.RecyclerViewFastScrollingManager;
import com.google.android.tvlauncher.home.util.ChannelUtil;
import com.google.android.tvlauncher.home.util.ProgramStateUtil;
import com.google.android.tvlauncher.home.view.ChannelItemsAnimator;
import com.google.android.tvlauncher.home.view.ChannelView;
import com.google.android.tvlauncher.util.Util;

class WatchNextRowController implements HomeRow, ChannelView.OnStateChangeGesturePerformedListener, BackHomeControllerListeners.OnBackPressedListener, BackHomeControllerListeners.OnHomePressedListener, BackHomeControllerListeners.OnHomeNotHandledListener, RecyclerViewStateProvider, RecyclerViewFastScrollingManager.OnFastScrollingChangedListener {
    private static final boolean DEBUG = false;
    private static final int EXIT_FAST_SCROLLING_DELAY_MS = 550;
    private static final String TAG = "WatchNextRowController";
    private final ChannelView mChannelView;
    private final EventLogger mEventLogger;
    private RecyclerViewFastScrollingManager mFastScrollingManager;
    private boolean mHomeIsFastScrolling;
    private RecyclerViewStateProvider mHomeListStateProvider;
    private WatchNextItemsAdapter mItemsAdapter;
    private final HorizontalGridView mItemsListView = this.mChannelView.getItemsListView();
    private WatchNextItemMetadataController mMetadataController;
    private BackHomeControllerListeners.OnBackNotHandledListener mOnBackNotHandledListener;
    private BackHomeControllerListeners.OnHomeNotHandledListener mOnHomeNotHandledListener;
    private OnHomeRowSelectedListener mOnHomeRowSelectedListener;
    private OnHomeStateChangeListener mOnHomeStateChangeListener;
    private OnProgramSelectedListener mOnProgramSelectedListener;

    WatchNextRowController(ChannelView channelView, EventLogger eventLogger) {
        this.mChannelView = channelView;
        this.mEventLogger = eventLogger;
        this.mChannelView.setOnStateChangeGesturePerformedListener(this);
        this.mChannelView.setAllowMoving(false);
        this.mChannelView.setAllowRemoving(false);
        this.mChannelView.setStateSettings(ChannelUtil.getDefaultChannelStateSettings(channelView.getContext()));
        String title = this.mChannelView.getContext().getString(C1188R.string.play_next_channel_title);
        this.mChannelView.setLogoTitle(title);
        this.mChannelView.setLogoContentDescription(title);
        this.mChannelView.setZoomedOutLogoTitle(title);
        ChannelUtil.setWatchNextLogo(this.mChannelView.getChannelLogoImageView());
        ChannelUtil.configureItemsListAlignment(this.mItemsListView);
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

    public void onFastScrollingChanged(boolean fastScrolling) {
        if (fastScrolling) {
            this.mItemsAdapter.setProgramState(4, false);
        } else {
            this.mItemsAdapter.setProgramState(getProgramState(this.mChannelView.getState()), true);
        }
        this.mChannelView.setIsFastScrolling(fastScrolling);
    }

    public View getView() {
        return this.mChannelView;
    }

    /* access modifiers changed from: package-private */
    public void setOnProgramSelectedListener(OnProgramSelectedListener listener) {
        this.mOnProgramSelectedListener = listener;
    }

    /* access modifiers changed from: package-private */
    public void setOnBackNotHandledListener(BackHomeControllerListeners.OnBackNotHandledListener listener) {
        this.mOnBackNotHandledListener = listener;
    }

    /* access modifiers changed from: package-private */
    public void setOnHomeNotHandledListener(BackHomeControllerListeners.OnHomeNotHandledListener listener) {
        this.mOnHomeNotHandledListener = listener;
    }

    /* access modifiers changed from: package-private */
    public void setHomeListStateProvider(RecyclerViewStateProvider homeListStateProvider) {
        this.mHomeListStateProvider = homeListStateProvider;
    }

    private void ensureItemListIsSetUp() {
        if (this.mItemsAdapter == null) {
            this.mItemsAdapter = new WatchNextItemsAdapter(this.mChannelView.getContext(), this.mEventLogger);
            this.mItemsAdapter.setOnProgramSelectedListener(this.mOnProgramSelectedListener);
            this.mItemsAdapter.setOnHomeNotHandledListener(this);
            this.mItemsAdapter.setListStateProvider(this);
            this.mItemsAdapter.setHomeListStateProvider(this.mHomeListStateProvider);
            this.mItemsListView.setAdapter(this.mItemsAdapter);
            this.mFastScrollingManager = new RecyclerViewFastScrollingManager(this.mItemsListView, new ChannelItemsAnimator());
            this.mFastScrollingManager.setExitFastScrollingDelayMs(550);
            this.mFastScrollingManager.setOnFastScrollingChangedListener(this);
            updateStateForHomeFastScrolling();
            this.mMetadataController = new WatchNextItemMetadataController(this.mChannelView.getItemMetadataView());
        }
    }

    private void updateStateForHomeFastScrolling() {
        this.mFastScrollingManager.setAnimatorEnabled(!this.mHomeIsFastScrolling);
        this.mFastScrollingManager.setScrollEnabled(false);
    }

    /* access modifiers changed from: package-private */
    public void onStart() {
        this.mItemsAdapter.onStart();
    }

    /* access modifiers changed from: package-private */
    public void onStop() {
        this.mItemsAdapter.onStop();
    }

    /* access modifiers changed from: package-private */
    public void recycle() {
        this.mItemsAdapter.recycle();
        this.mMetadataController.clear();
    }

    /* access modifiers changed from: package-private */
    public void bind(int channelViewState) {
        ensureItemListIsSetUp();
        this.mChannelView.setState(channelViewState);
        int oldProgramState = this.mItemsAdapter.getProgramState();
        int newProgramState = getProgramState(channelViewState);
        this.mItemsAdapter.bind(newProgramState);
        updateItemsListPosition(newProgramState, oldProgramState);
    }

    public void setState(int channelViewState) {
        if (this.mChannelView.getState() != channelViewState && this.mFastScrollingManager.isFastScrollingEnabled() && this.mItemsListView.getSelectedPosition() == 0) {
            this.mFastScrollingManager.setAnimatorEnabled(!this.mHomeIsFastScrolling);
        }
        this.mChannelView.setState(channelViewState);
        int oldProgramState = this.mItemsAdapter.getProgramState();
        int newProgramState = getProgramState(channelViewState);
        this.mItemsAdapter.setProgramState(newProgramState);
        updateItemsListPosition(newProgramState, oldProgramState);
    }

    private void updateItemsListPosition(int newState, int oldState) {
        if (newState != oldState && ProgramStateUtil.isZoomedOutState(newState) && this.mItemsAdapter.getItemCount() > 1 && this.mItemsListView.getSelectedPosition() != 0) {
            this.mItemsListView.scrollToPosition(0);
        }
    }

    private int getProgramState(int channelViewState) {
        switch (channelViewState) {
            case 0:
                return 3;
            case 1:
            case 2:
            case 3:
                return 0;
            case 4:
                return 1;
            case 5:
                return 2;
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
                throw new IllegalStateException(valueOf.length() != 0 ? "Unsupported Watch Next row state ".concat(valueOf) : new String("Unsupported Watch Next row state "));
            case 8:
                return 6;
            case 9:
                return 5;
            case 10:
                return 7;
            case 12:
                return 8;
            case 14:
                return 10;
            case 15:
                return 12;
            default:
                return 0;
        }
    }

    /* access modifiers changed from: package-private */
    public void bindItemMetadata() {
        int position = this.mItemsListView.getSelectedPosition();
        TvDataManager dataManager = TvDataManager.getInstance(this.mItemsListView.getContext());
        if (position == -1 || this.mItemsAdapter.getItemCount() == 0) {
            this.mMetadataController.clear();
        } else if (this.mItemsAdapter.getItemCount() <= 0 || this.mItemsAdapter.getItemViewType(position) != 1) {
            int programIndex = this.mItemsAdapter.getProgramIndexFromAdapterPosition(position);
            if (programIndex >= 0 && programIndex < dataManager.getWatchNextProgramsCount()) {
                this.mMetadataController.bindView(dataManager.getWatchNextProgram(programIndex));
            }
        } else {
            this.mMetadataController.clear();
        }
    }

    /* access modifiers changed from: package-private */
    public void bindInfoAcknowledgedButton() {
        int position = this.mItemsListView.getSelectedPosition();
        boolean isInfoCardSelected = true;
        if (this.mItemsAdapter.getItemCount() <= 0 || this.mItemsAdapter.getItemViewType(position) != 1) {
            isInfoCardSelected = false;
        }
        this.mChannelView.bindWatchNextInfoAcknowledgedButton(isInfoCardSelected);
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

    private void setSelectedItemPosition(int position) {
        if (Util.areHomeScreenAnimationsEnabled(this.mChannelView.getContext())) {
            this.mItemsListView.setSelectedPosition(position);
        } else {
            this.mItemsListView.setSelectedPositionSmooth(position);
        }
    }

    public void onBackPressed(Context c) {
        if (this.mItemsListView.getAdapter() != null) {
            if (this.mChannelView.getState() != 0 || this.mItemsListView.getSelectedPosition() == 0 || this.mItemsListView.getAdapter().getItemCount() <= 0) {
                BackHomeControllerListeners.OnBackNotHandledListener onBackNotHandledListener = this.mOnBackNotHandledListener;
                if (onBackNotHandledListener != null) {
                    onBackNotHandledListener.onBackNotHandled(c);
                    return;
                }
                return;
            }
            setSelectedItemPosition(0);
        }
    }

    public void onHomePressed(Context c) {
        if (this.mChannelView.getState() == 0) {
            HorizontalGridView horizontalGridView = this.mItemsListView;
            RecyclerView.ViewHolder selectedViewHolder = horizontalGridView.findViewHolderForAdapterPosition(horizontalGridView.getSelectedPosition());
            if (selectedViewHolder instanceof BackHomeControllerListeners.OnHomePressedListener) {
                ((BackHomeControllerListeners.OnHomePressedListener) selectedViewHolder).onHomePressed(c);
                return;
            }
        }
        onHomeNotHandled(c);
    }

    public void onHomeNotHandled(Context c) {
        BackHomeControllerListeners.OnHomeNotHandledListener onHomeNotHandledListener = this.mOnHomeNotHandledListener;
        if (onHomeNotHandledListener != null) {
            onHomeNotHandledListener.onHomeNotHandled(c);
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
