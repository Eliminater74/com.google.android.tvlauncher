package com.google.android.tvlauncher.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.p004v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.leanback.widget.FacetProvider;
import androidx.leanback.widget.ItemAlignmentFacet;

import com.google.android.tvlauncher.BackHomeControllerListeners;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.TvlauncherLogEnum;
import com.google.android.tvlauncher.analytics.EventLogger;
import com.google.android.tvlauncher.analytics.LogEvent;
import com.google.android.tvlauncher.analytics.LogEventParameters;
import com.google.android.tvlauncher.data.TvDataManager;
import com.google.android.tvlauncher.data.WatchNextProgramsObserver;
import com.google.android.tvlauncher.home.util.ProgramSettings;
import com.google.android.tvlauncher.home.util.ProgramStateUtil;
import com.google.android.tvlauncher.home.util.ProgramUtil;
import com.google.android.tvlauncher.home.view.ProgramView;
import com.google.android.tvlauncher.home.view.WatchNextInfoView;
import com.google.android.tvlauncher.model.Program;
import com.google.android.tvlauncher.util.Util;
import com.google.logs.tvlauncher.config.TvLauncherConstants;

import java.util.List;

class WatchNextItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static final int TYPE_INFO = 1;
    private static final boolean DEBUG = false;
    private static final int INFO_CARD_ID = -2;
    private static final String TAG = "WatchNextItemsAdapter";
    private static final int TYPE_INFO_ADAPTER_POSITION = 1;
    private static final int TYPE_PROGRAM = 0;
    /* access modifiers changed from: private */
    public final TvDataManager mDataManager;
    private final EventLogger mEventLogger;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler();
    /* access modifiers changed from: private */
    public RecyclerViewStateProvider mHomeListStateProvider;
    /* access modifiers changed from: private */
    public int mLastUnfocusedAdapterPosition = -1;
    /* access modifiers changed from: private */
    public RecyclerViewStateProvider mListStateProvider;
    /* access modifiers changed from: private */
    public OnProgramSelectedListener mOnProgramSelectedListener;
    /* access modifiers changed from: private */
    public SharedPreferences mPreferences;
    /* access modifiers changed from: private */
    public int mProgramState = 0;
    /* access modifiers changed from: private */
    public RecyclerView mRecyclerView;
    /* access modifiers changed from: private */
    public boolean mShowInfo;
    private final WatchNextProgramsObserver mProgramsObserver = new WatchNextProgramsObserver() {
        public void onProgramsChange() {
            WatchNextItemsAdapter.this.logDataLoaded();
            int unused = WatchNextItemsAdapter.this.mLastUnfocusedAdapterPosition = -1;
            WatchNextItemsAdapter.this.notifyDataSetChanged();
        }
    };
    private BackHomeControllerListeners.OnHomeNotHandledListener mOnHomeNotHandledListener;
    private boolean mStarted;

    WatchNextItemsAdapter(Context context, EventLogger eventLogger) {
        this.mDataManager = TvDataManager.getInstance(context);
        this.mEventLogger = eventLogger;
        setHasStableIds(true);
        this.mPreferences = context.getSharedPreferences(WatchNextPrefs.WATCH_NEXT_PREF_FILE_NAME, 0);
        this.mShowInfo = !this.mPreferences.getBoolean("watch_next_info_acknowledged", false);
    }

    /* access modifiers changed from: package-private */
    public void setOnProgramSelectedListener(OnProgramSelectedListener listener) {
        this.mOnProgramSelectedListener = listener;
    }

    /* access modifiers changed from: package-private */
    public void setOnHomeNotHandledListener(BackHomeControllerListeners.OnHomeNotHandledListener onHomeNotHandledListener) {
        this.mOnHomeNotHandledListener = onHomeNotHandledListener;
    }

    /* access modifiers changed from: package-private */
    public void setListStateProvider(RecyclerViewStateProvider listStateProvider) {
        this.mListStateProvider = listStateProvider;
    }

    /* access modifiers changed from: package-private */
    public void setHomeListStateProvider(RecyclerViewStateProvider homeListStateProvider) {
        this.mHomeListStateProvider = homeListStateProvider;
    }

    /* access modifiers changed from: package-private */
    public void bind(int programState) {
        this.mProgramState = programState;
        if (!registerObserverAndUpdateDataIfNeeded()) {
            this.mLastUnfocusedAdapterPosition = -1;
            notifyDataSetChanged();
        }
        this.mStarted = true;
    }

    /* access modifiers changed from: package-private */
    public int getProgramState() {
        return this.mProgramState;
    }

    /* access modifiers changed from: package-private */
    public void setProgramState(int state) {
        setProgramState(state, true);
    }

    /* access modifiers changed from: package-private */
    public void setProgramState(int state, boolean updateVisibleItems) {
        if (this.mProgramState != state) {
            this.mProgramState = state;
            if (updateVisibleItems) {
                this.mLastUnfocusedAdapterPosition = -1;
                notifyItemRangeChanged(0, getItemCount(), "PAYLOAD_STATE");
            }
        }
    }

    private boolean registerObserverAndUpdateDataIfNeeded() {
        this.mDataManager.registerWatchNextProgramsObserver(this.mProgramsObserver);
        if (this.mDataManager.isWatchNextProgramsDataLoaded() && !this.mDataManager.isWatchNextProgramsDataStale()) {
            return false;
        }
        this.mDataManager.loadWatchNextProgramData();
        this.mDataManager.loadAllWatchNextProgramDataIntoCache();
        return true;
    }

    public void onStart() {
        if (!this.mStarted) {
            registerObserverAndUpdateDataIfNeeded();
        }
        this.mStarted = true;
    }

    public void onStop() {
        if (this.mStarted) {
            this.mDataManager.unregisterWatchNextProgramsObserver(this.mProgramsObserver);
        }
        this.mStarted = false;
    }

    /* access modifiers changed from: package-private */
    public void recycle() {
        this.mLastUnfocusedAdapterPosition = -1;
        onStop();
    }

    public int getItemCount() {
        if (!this.mDataManager.isWatchNextProgramsDataLoaded()) {
            return 0;
        }
        int programCount = this.mDataManager.getWatchNextProgramsCount();
        if (this.mShowInfo) {
            return programCount + 1;
        }
        return programCount;
    }

    public int getItemViewType(int position) {
        if (this.mDataManager.getWatchNextProgramsCount() <= 0) {
            return 1;
        }
        if (!this.mShowInfo || position != 1) {
            return 0;
        }
        return 1;
    }

    public long getItemId(int position) {
        if (getItemViewType(position) == 1) {
            return -2;
        }
        return this.mDataManager.getWatchNextProgram(getProgramIndexFromAdapterPosition(position)).getId();
    }

    /* access modifiers changed from: package-private */
    public int getProgramIndexFromAdapterPosition(int adapterPosition) {
        if (!this.mShowInfo || adapterPosition < 1) {
            return adapterPosition;
        }
        return adapterPosition - 1;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType != 0) {
            return new InfoCardViewHolder((WatchNextInfoView) LayoutInflater.from(parent.getContext()).inflate(C1188R.layout.view_watch_next_info, parent, false));
        }
        ProgramViewHolder programViewHolder = new ProgramViewHolder((ProgramView) LayoutInflater.from(parent.getContext()).inflate(C1188R.layout.view_program, parent, false), this.mEventLogger);
        programViewHolder.getProgramController().setOnHomeNotHandledListener(this.mOnHomeNotHandledListener);
        return programViewHolder;
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProgramViewHolder) {
            ((ProgramViewHolder) holder).bind(this.mDataManager.getWatchNextProgram(getProgramIndexFromAdapterPosition(position)), this.mProgramState);
        } else if (holder instanceof InfoCardViewHolder) {
            ((InfoCardViewHolder) holder).bind();
        }
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else if (holder instanceof ProgramViewHolder) {
            ProgramViewHolder vh = (ProgramViewHolder) holder;
            if (payloads.contains("PAYLOAD_STATE")) {
                vh.getProgramController().bindState(this.mProgramState);
            }
            if (payloads.contains("PAYLOAD_STATE") || payloads.contains(ProgramBindPayloads.FOCUS_CHANGED)) {
                vh.getProgramController().updateFocusedState();
            }
        } else if (holder instanceof InfoCardViewHolder) {
            InfoCardViewHolder vh2 = (InfoCardViewHolder) holder;
            if (payloads.contains("PAYLOAD_STATE")) {
                vh2.bindState();
            }
            if (payloads.contains("PAYLOAD_STATE") || payloads.contains(ProgramBindPayloads.FOCUS_CHANGED)) {
                vh2.updateFocusedState();
            }
        }
    }

    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        this.mRecyclerView = null;
    }

    /* access modifiers changed from: private */
    public void logDataLoaded() {
        LogEventParameters parameters = new LogEventParameters(TvlauncherLogEnum.TvLauncherEventCode.OPEN_HOME, LogEventParameters.WATCH_NEXT);
        parameters.getWatchNextChannel().setProgramCount(this.mShowInfo ? getItemCount() - 1 : getItemCount());
        this.mEventLogger.log(parameters);
    }

    /* access modifiers changed from: private */
    public double getCardBeforeInfoCardAspectRatio() {
        return ProgramUtil.getAspectRatio(this.mDataManager.getWatchNextProgram(0).getPreviewImageAspectRatio());
    }

    @VisibleForTesting
    class ProgramViewHolder extends RecyclerView.ViewHolder implements OnProgramViewFocusChangedListener, BackHomeControllerListeners.OnHomePressedListener, EventLogger {
        private final EventLogger mEventLogger;
        private final ProgramController mProgramController;
        private Runnable mNotifyFocusChangedRunnable = new WatchNextItemsAdapter$ProgramViewHolder$$Lambda$0(this);

        ProgramViewHolder(ProgramView v, EventLogger eventLogger) {
            super(v);
            this.mProgramController = new ProgramController(v, this, false, false);
            this.mEventLogger = eventLogger;
            this.mProgramController.setOnProgramViewFocusChangedListener(this);
            this.mProgramController.setIsWatchNextProgram(true);
            this.mProgramController.setListStateProvider(WatchNextItemsAdapter.this.mListStateProvider);
            this.mProgramController.setHomeListStateProvider(WatchNextItemsAdapter.this.mHomeListStateProvider);
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$new$0$WatchNextItemsAdapter$ProgramViewHolder() {
            WatchNextItemsAdapter.this.notifyItemChanged(getAdapterPosition(), ProgramBindPayloads.FOCUS_CHANGED);
            if (WatchNextItemsAdapter.this.mLastUnfocusedAdapterPosition != -1) {
                WatchNextItemsAdapter watchNextItemsAdapter = WatchNextItemsAdapter.this;
                watchNextItemsAdapter.notifyItemChanged(watchNextItemsAdapter.mLastUnfocusedAdapterPosition, ProgramBindPayloads.FOCUS_CHANGED);
                int unused = WatchNextItemsAdapter.this.mLastUnfocusedAdapterPosition = -1;
            }
        }

        public void onProgramViewFocusChanged(boolean hasFocus) {
            int position = getAdapterPosition();
            if (position != -1) {
                if (WatchNextItemsAdapter.this.mOnProgramSelectedListener != null && hasFocus) {
                    WatchNextItemsAdapter.this.mOnProgramSelectedListener.onProgramSelected(WatchNextItemsAdapter.this.mDataManager.getWatchNextProgram(WatchNextItemsAdapter.this.getProgramIndexFromAdapterPosition(position)), this.mProgramController);
                }
                WatchNextItemsAdapter.this.mHandler.removeCallbacks(this.mNotifyFocusChangedRunnable);
                if (WatchNextItemsAdapter.this.mProgramState == 4) {
                    if (this.mProgramController.isProgramSelected() && !hasFocus) {
                        notifyFocusChanged();
                    }
                } else if (!hasFocus) {
                    int unused = WatchNextItemsAdapter.this.mLastUnfocusedAdapterPosition = getAdapterPosition();
                } else {
                    notifyFocusChanged();
                }
            }
        }

        private void notifyFocusChanged() {
            if (WatchNextItemsAdapter.this.mRecyclerView == null || WatchNextItemsAdapter.this.mRecyclerView.isComputingLayout()) {
                Log.w(WatchNextItemsAdapter.TAG, "list is still computing layout => schedule program selection change");
                WatchNextItemsAdapter.this.mHandler.post(this.mNotifyFocusChangedRunnable);
                return;
            }
            this.mNotifyFocusChangedRunnable.run();
        }

        /* access modifiers changed from: package-private */
        public ProgramController getProgramController() {
            return this.mProgramController;
        }

        /* access modifiers changed from: package-private */
        public void bind(Program program, int programState) {
            this.mProgramController.bind(program, null, programState, false, false, false);
            if (WatchNextItemsAdapter.this.mOnProgramSelectedListener != null && this.mProgramController.isViewFocused()) {
                WatchNextItemsAdapter.this.mOnProgramSelectedListener.onProgramSelected(program, this.mProgramController);
            }
        }

        public void log(LogEvent event) {
            event.setVisualElementIndex(WatchNextItemsAdapter.this.getProgramIndexFromAdapterPosition(getAdapterPosition())).pushParentVisualElementTag(TvLauncherConstants.WATCH_NEXT_CONTAINER).getWatchNextChannel().setProgramCount(WatchNextItemsAdapter.this.mShowInfo ? WatchNextItemsAdapter.this.getItemCount() - 1 : WatchNextItemsAdapter.this.getItemCount());
            this.mEventLogger.log(event);
        }

        public void onHomePressed(Context c) {
            this.mProgramController.onHomePressed(c);
        }
    }

    private class InfoCardViewHolder extends RecyclerView.ViewHolder implements FacetProvider {
        private ItemAlignmentFacet mFacet;
        private WatchNextInfoController mInfoController;
        private ItemAlignmentFacet.ItemAlignmentDef mItemAlignmentDef;
        private Runnable mNotifyFocusChangedRunnable = new WatchNextItemsAdapter$InfoCardViewHolder$$Lambda$0(this);
        private View.OnFocusChangeListener mOnFocusChangeListener = new WatchNextItemsAdapter$InfoCardViewHolder$$Lambda$1(this);
        private ProgramSettings mProgramSettings;

        InfoCardViewHolder(WatchNextInfoView v) {
            super(v);
            this.mProgramSettings = ProgramUtil.getProgramSettings(v.getContext());
            this.mInfoController = new WatchNextInfoController(v, this.mProgramSettings);
            if (Util.areHomeScreenAnimationsEnabled(v.getContext())) {
                v.setOnFocusChangeListener(this.mOnFocusChangeListener);
            }
            v.setOnClickListener(new WatchNextItemsAdapter$InfoCardViewHolder$$Lambda$2(this));
            this.mItemAlignmentDef = new ItemAlignmentFacet.ItemAlignmentDef();
            this.mItemAlignmentDef.setItemAlignmentOffsetPercent(-1.0f);
            this.mFacet = new ItemAlignmentFacet();
            this.mFacet.setAlignmentDefs(new ItemAlignmentFacet.ItemAlignmentDef[]{this.mItemAlignmentDef});
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$new$0$WatchNextItemsAdapter$InfoCardViewHolder() {
            WatchNextItemsAdapter.this.notifyItemChanged(getAdapterPosition(), ProgramBindPayloads.FOCUS_CHANGED);
            if (WatchNextItemsAdapter.this.mLastUnfocusedAdapterPosition != -1) {
                WatchNextItemsAdapter watchNextItemsAdapter = WatchNextItemsAdapter.this;
                watchNextItemsAdapter.notifyItemChanged(watchNextItemsAdapter.mLastUnfocusedAdapterPosition, ProgramBindPayloads.FOCUS_CHANGED);
                int unused = WatchNextItemsAdapter.this.mLastUnfocusedAdapterPosition = -1;
            }
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$new$1$WatchNextItemsAdapter$InfoCardViewHolder(View view, boolean hasFocus) {
            if (getAdapterPosition() != -1) {
                if (WatchNextItemsAdapter.this.mOnProgramSelectedListener != null && hasFocus) {
                    WatchNextItemsAdapter.this.mOnProgramSelectedListener.onProgramSelected(null, null);
                }
                WatchNextItemsAdapter.this.mHandler.removeCallbacks(this.mNotifyFocusChangedRunnable);
                if (WatchNextItemsAdapter.this.mProgramState == 4) {
                    if (this.mInfoController.isSelected() && !hasFocus) {
                        notifyFocusChanged();
                    }
                } else if (!hasFocus) {
                    int unused = WatchNextItemsAdapter.this.mLastUnfocusedAdapterPosition = getAdapterPosition();
                } else {
                    notifyFocusChanged();
                }
            }
        }

        private void notifyFocusChanged() {
            if (WatchNextItemsAdapter.this.mRecyclerView == null || WatchNextItemsAdapter.this.mRecyclerView.isComputingLayout()) {
                Log.w(WatchNextItemsAdapter.TAG, "list is still computing layout => schedule card selection change");
                WatchNextItemsAdapter.this.mHandler.post(this.mNotifyFocusChangedRunnable);
                return;
            }
            this.mNotifyFocusChangedRunnable.run();
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$new$2$WatchNextItemsAdapter$InfoCardViewHolder(View view) {
            if (WatchNextItemsAdapter.this.mShowInfo) {
                boolean unused = WatchNextItemsAdapter.this.mShowInfo = false;
                WatchNextItemsAdapter.this.notifyItemRemoved(1);
                WatchNextItemsAdapter.this.mPreferences.edit().putBoolean("watch_next_info_acknowledged", true).apply();
            }
        }

        /* access modifiers changed from: package-private */
        public void bind() {
            bindState();
            updateFocusedState();
        }

        /* access modifiers changed from: package-private */
        public void bindState() {
            this.mInfoController.bindState(WatchNextItemsAdapter.this.mProgramState);
        }

        /* access modifiers changed from: package-private */
        public void updateFocusedState() {
            if (WatchNextItemsAdapter.this.mDataManager.getWatchNextProgramsCount() > 0) {
                double d = (double) this.mProgramSettings.selectedHeight;
                double access$1000 = WatchNextItemsAdapter.this.getCardBeforeInfoCardAspectRatio();
                Double.isNaN(d);
                double previousCardWidth = d * access$1000;
                double d2 = (double) (this.mProgramSettings.focusedScale - 1.0f);
                Double.isNaN(d2);
                double d3 = (double) this.mProgramSettings.defaultMarginHorizontal;
                Double.isNaN(d3);
                this.mInfoController.updateFocusedState((float) ((d2 * previousCardWidth) - d3));
                return;
            }
            this.mInfoController.updateFocusedState(0.0f);
        }

        public Object getFacet(Class<?> cls) {
            if (getAdapterPosition() == -1) {
                return null;
            }
            if (WatchNextItemsAdapter.this.mDataManager.getWatchNextProgramsCount() > 0) {
                double previousCardAspectRatio = WatchNextItemsAdapter.this.getCardBeforeInfoCardAspectRatio();
                int prevCardHeight = 0;
                int prevCardMarginEnd = 0;
                switch (WatchNextItemsAdapter.this.mProgramState) {
                    case 0:
                        prevCardHeight = this.mProgramSettings.defaultHeight;
                        prevCardMarginEnd = this.mProgramSettings.defaultMarginHorizontal;
                        break;
                    case 1:
                    case 2:
                        prevCardHeight = this.mProgramSettings.defaultTopHeight;
                        prevCardMarginEnd = this.mProgramSettings.defaultMarginHorizontal;
                        break;
                    case 3:
                    case 4:
                    case 12:
                        prevCardHeight = this.mProgramSettings.selectedHeight;
                        prevCardMarginEnd = this.mProgramSettings.defaultMarginHorizontal;
                        break;
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 10:
                        prevCardHeight = this.mProgramSettings.zoomedOutHeight;
                        prevCardMarginEnd = this.mProgramSettings.zoomedOutMarginHorizontal;
                        break;
                    case 9:
                    case 11:
                        String valueOf = String.valueOf(ProgramStateUtil.stateToString(WatchNextItemsAdapter.this.mProgramState));
                        throw new IllegalStateException(valueOf.length() != 0 ? "Unsupported Watch Next program state: ".concat(valueOf) : new String("Unsupported Watch Next program state: "));
                }
                double d = (double) prevCardHeight;
                Double.isNaN(d);
                this.mItemAlignmentDef.setItemAlignmentOffset((-((int) (d * previousCardAspectRatio))) - prevCardMarginEnd);
            } else {
                this.mItemAlignmentDef.setItemAlignmentOffset(0);
            }
            return this.mFacet;
        }
    }
}
