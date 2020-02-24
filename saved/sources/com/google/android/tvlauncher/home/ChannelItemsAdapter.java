package com.google.android.tvlauncher.home;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.p004v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.google.android.tvlauncher.BackHomeControllerListeners;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.analytics.EventLogger;
import com.google.android.tvlauncher.analytics.LogEvent;
import com.google.android.tvlauncher.data.ChannelProgramsObserver;
import com.google.android.tvlauncher.data.TvDataManager;
import com.google.android.tvlauncher.home.view.ProgramView;
import com.google.android.tvlauncher.model.Program;
import com.google.protos.logs.proto.wireless.android.tvlauncher.TvlauncherClientLog;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChannelItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final boolean DEBUG = false;
    private static final int NO_CHANNEL_ID = -1;
    private static final String PAYLOAD_LIVE_PROGRESS_UPDATE = "PAYLOAD_LIVE_PROGRESS_UPDATE";
    private static final String TAG = "ChannelItemsAdapter";
    private Set<ProgramController> mActiveProgramControllers = new HashSet();
    /* access modifiers changed from: private */
    public boolean mCanAddToWatchNext;
    /* access modifiers changed from: private */
    public boolean mCanRemoveProgram;
    /* access modifiers changed from: private */
    public long mChannelId = -1;
    /* access modifiers changed from: private */
    public final TvDataManager mDataManager;
    private final EventLogger mEventLogger;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler();
    /* access modifiers changed from: private */
    public RecyclerViewStateProvider mHomeListStateProvider;
    /* access modifiers changed from: private */
    public boolean mIsLegacy;
    /* access modifiers changed from: private */
    public boolean mIsSponsored;
    /* access modifiers changed from: private */
    public boolean mIsSponsoredBranded;
    /* access modifiers changed from: private */
    public int mLastUnfocusedAdapterPosition = -1;
    /* access modifiers changed from: private */
    public RecyclerViewStateProvider mListStateProvider;
    private BackHomeControllerListeners.OnHomeNotHandledListener mOnHomeNotHandledListener;
    /* access modifiers changed from: private */
    public OnProgramSelectedListener mOnProgramSelectedListener;
    /* access modifiers changed from: private */
    public String mPackageName;
    /* access modifiers changed from: private */
    public int mProgramState = 0;
    private final ChannelProgramsObserver mProgramsObserver = new ChannelProgramsObserver() {
        public void onProgramsChange(long channelId) {
            int unused = ChannelItemsAdapter.this.mLastUnfocusedAdapterPosition = -1;
            ChannelItemsAdapter.this.notifyDataSetChanged();
        }
    };
    /* access modifiers changed from: private */
    public RecyclerView mRecyclerView;
    private boolean mStarted;

    ChannelItemsAdapter(Context context, EventLogger eventLogger) {
        this.mDataManager = TvDataManager.getInstance(context);
        this.mEventLogger = eventLogger;
        setHasStableIds(true);
    }

    /* access modifiers changed from: package-private */
    public void setIsSponsored(boolean isSponsored, boolean isSponsoredBranded) {
        this.mIsSponsored = isSponsored;
        this.mIsSponsoredBranded = isSponsoredBranded;
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
    public void setOnProgramSelectedListener(OnProgramSelectedListener listener) {
        this.mOnProgramSelectedListener = listener;
    }

    /* access modifiers changed from: package-private */
    public void setOnHomeNotHandledListener(BackHomeControllerListeners.OnHomeNotHandledListener onHomeNotHandledListener) {
        this.mOnHomeNotHandledListener = onHomeNotHandledListener;
    }

    /* access modifiers changed from: package-private */
    public void bind(long channelId, String packageName, int programState, boolean canAddToWatchNext, boolean canRemoveProgram, boolean isLegacy) {
        long j = channelId;
        int i = programState;
        this.mPackageName = packageName;
        this.mCanAddToWatchNext = canAddToWatchNext;
        this.mCanRemoveProgram = canRemoveProgram;
        this.mIsLegacy = isLegacy;
        int oldProgramState = this.mProgramState;
        this.mProgramState = i;
        long j2 = this.mChannelId;
        if (j != j2) {
            if (j2 != -1) {
                this.mDataManager.unregisterChannelProgramsObserver(j2, this.mProgramsObserver);
            }
            this.mChannelId = j;
            if (this.mChannelId != -1) {
                if (!registerObserverAndUpdateDataIfNeeded()) {
                    this.mLastUnfocusedAdapterPosition = -1;
                    notifyDataSetChanged();
                }
                this.mStarted = true;
                return;
            }
            this.mStarted = false;
        } else if (oldProgramState != i) {
            this.mLastUnfocusedAdapterPosition = -1;
            notifyItemRangeChanged(0, getItemCount(), "PAYLOAD_STATE");
        }
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

    /* access modifiers changed from: package-private */
    public void updateProgramFocusState(int position) {
        notifyItemChanged(position, ProgramBindPayloads.FOCUS_CHANGED);
    }

    private boolean registerObserverAndUpdateDataIfNeeded() {
        this.mDataManager.registerChannelProgramsObserver(this.mChannelId, this.mProgramsObserver);
        if (this.mDataManager.isProgramDataLoaded(this.mChannelId) && !this.mDataManager.isProgramDataStale(this.mChannelId)) {
            return false;
        }
        this.mDataManager.loadProgramData(this.mChannelId);
        return true;
    }

    public void onStart() {
        if (!this.mStarted && this.mChannelId != -1 && !registerObserverAndUpdateDataIfNeeded()) {
            notifyItemRangeChanged(0, getItemCount(), PAYLOAD_LIVE_PROGRESS_UPDATE);
        }
        this.mStarted = true;
    }

    public void onStop() {
        if (this.mStarted) {
            long j = this.mChannelId;
            if (j != -1) {
                this.mDataManager.unregisterChannelProgramsObserver(j, this.mProgramsObserver);
                for (ProgramController controller : this.mActiveProgramControllers) {
                    controller.onStop();
                }
            }
        }
        this.mStarted = false;
    }

    /* access modifiers changed from: package-private */
    public void recycle() {
        bind(-1, null, this.mProgramState, false, false, false);
        this.mStarted = false;
        this.mLastUnfocusedAdapterPosition = -1;
        notifyDataSetChanged();
    }

    public int getItemCount() {
        if (this.mDataManager.isProgramDataLoaded(this.mChannelId)) {
            return this.mDataManager.getProgramCount(this.mChannelId);
        }
        return 0;
    }

    public long getItemId(int position) {
        return this.mDataManager.getProgram(this.mChannelId, position).getId();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProgramViewHolder programViewHolder = new ProgramViewHolder((ProgramView) LayoutInflater.from(parent.getContext()).inflate(C1188R.layout.view_program, parent, false), this.mEventLogger);
        programViewHolder.getProgramController().setOnHomeNotHandledListener(this.mOnHomeNotHandledListener);
        return programViewHolder;
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProgramViewHolder vh = (ProgramViewHolder) holder;
        vh.bind(this.mDataManager.getProgram(this.mChannelId, position));
        this.mActiveProgramControllers.add(vh.getProgramController());
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
            return;
        }
        ProgramViewHolder vh = (ProgramViewHolder) holder;
        if (payloads.contains("PAYLOAD_STATE")) {
            vh.getProgramController().bindState(this.mProgramState);
        }
        if (payloads.contains("PAYLOAD_STATE") || payloads.contains(ProgramBindPayloads.FOCUS_CHANGED)) {
            vh.getProgramController().updateFocusedState();
        }
        if (payloads.contains(PAYLOAD_LIVE_PROGRESS_UPDATE)) {
            vh.getProgramController().updateProgressState(this.mDataManager.getProgram(this.mChannelId, position));
        }
    }

    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof ProgramViewHolder) {
            ProgramViewHolder vh = (ProgramViewHolder) holder;
            vh.recycle();
            this.mActiveProgramControllers.remove(vh.getProgramController());
        }
    }

    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        this.mRecyclerView = null;
    }

    public class ProgramViewHolder extends RecyclerView.ViewHolder implements OnProgramViewFocusChangedListener, ProgramViewLiveProgressUpdateCallback, BackHomeControllerListeners.OnHomePressedListener, EventLogger {
        private final EventLogger mEventLogger;
        private Runnable mNotifyFocusChangedRunnable = new ChannelItemsAdapter$ProgramViewHolder$$Lambda$0(this);
        private Runnable mNotifyLiveProgressUpdateRunnable = new ChannelItemsAdapter$ProgramViewHolder$$Lambda$1(this);
        final ProgramController mProgramController;

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$new$0$ChannelItemsAdapter$ProgramViewHolder() {
            ChannelItemsAdapter.this.notifyItemChanged(getAdapterPosition(), ProgramBindPayloads.FOCUS_CHANGED);
            if (ChannelItemsAdapter.this.mLastUnfocusedAdapterPosition != -1) {
                ChannelItemsAdapter channelItemsAdapter = ChannelItemsAdapter.this;
                channelItemsAdapter.notifyItemChanged(channelItemsAdapter.mLastUnfocusedAdapterPosition, ProgramBindPayloads.FOCUS_CHANGED);
                int unused = ChannelItemsAdapter.this.mLastUnfocusedAdapterPosition = -1;
            }
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$new$1$ChannelItemsAdapter$ProgramViewHolder() {
            ChannelItemsAdapter.this.notifyItemChanged(getAdapterPosition(), ChannelItemsAdapter.PAYLOAD_LIVE_PROGRESS_UPDATE);
        }

        ProgramViewHolder(ProgramView v, EventLogger eventLogger) {
            super(v);
            this.mEventLogger = eventLogger;
            this.mProgramController = new ProgramController(v, this, ChannelItemsAdapter.this.mIsSponsored, ChannelItemsAdapter.this.mIsSponsoredBranded);
            this.mProgramController.setOnProgramViewFocusChangedListener(this);
            this.mProgramController.setIsWatchNextProgram(false);
            this.mProgramController.setProgramViewLiveProgressUpdateCallback(this);
        }

        public void onProgramViewFocusChanged(boolean hasFocus) {
            int position = getAdapterPosition();
            if (position != -1) {
                if (ChannelItemsAdapter.this.mOnProgramSelectedListener != null && hasFocus) {
                    ChannelItemsAdapter.this.mOnProgramSelectedListener.onProgramSelected(ChannelItemsAdapter.this.mDataManager.getProgram(ChannelItemsAdapter.this.mChannelId, position), this.mProgramController);
                }
                ChannelItemsAdapter.this.mHandler.removeCallbacks(this.mNotifyFocusChangedRunnable);
                if (ChannelItemsAdapter.this.mProgramState == 4) {
                    if (this.mProgramController.isProgramSelected() && !hasFocus) {
                        notifyFocusChanged();
                    }
                } else if (!hasFocus) {
                    int unused = ChannelItemsAdapter.this.mLastUnfocusedAdapterPosition = getAdapterPosition();
                } else {
                    notifyFocusChanged();
                }
            }
        }

        private void notifyFocusChanged() {
            if (ChannelItemsAdapter.this.mRecyclerView == null || ChannelItemsAdapter.this.mRecyclerView.isComputingLayout()) {
                Log.w(ChannelItemsAdapter.TAG, "list is still computing layout => schedule program selection change");
                ChannelItemsAdapter.this.mHandler.post(this.mNotifyFocusChangedRunnable);
                return;
            }
            this.mNotifyFocusChangedRunnable.run();
        }

        public void updateProgramViewLiveProgress() {
            ChannelItemsAdapter.this.mHandler.removeCallbacks(this.mNotifyLiveProgressUpdateRunnable);
            if (ChannelItemsAdapter.this.mRecyclerView == null || ChannelItemsAdapter.this.mRecyclerView.isComputingLayout()) {
                Log.w(ChannelItemsAdapter.TAG, "list is still computing layout => schedule live progress change");
                ChannelItemsAdapter.this.mHandler.post(this.mNotifyLiveProgressUpdateRunnable);
                return;
            }
            this.mNotifyLiveProgressUpdateRunnable.run();
        }

        public void log(LogEvent event) {
            event.setVisualElementIndex(getAdapterPosition());
            TvlauncherClientLog.Channel.Builder channel = event.getChannel();
            channel.setPackageName(ChannelItemsAdapter.this.mPackageName);
            channel.setProgramCount(ChannelItemsAdapter.this.getItemCount());
            this.mEventLogger.log(event);
        }

        public ProgramController getProgramController() {
            return this.mProgramController;
        }

        /* access modifiers changed from: package-private */
        public void bind(Program program) {
            this.mProgramController.bind(program, ChannelItemsAdapter.this.mPackageName, ChannelItemsAdapter.this.mProgramState, ChannelItemsAdapter.this.mCanAddToWatchNext, ChannelItemsAdapter.this.mCanRemoveProgram, ChannelItemsAdapter.this.mIsLegacy);
            if (ChannelItemsAdapter.this.mOnProgramSelectedListener != null && this.mProgramController.isViewFocused()) {
                ChannelItemsAdapter.this.mOnProgramSelectedListener.onProgramSelected(program, this.mProgramController);
            }
            this.mProgramController.setListStateProvider(ChannelItemsAdapter.this.mListStateProvider);
            this.mProgramController.setHomeListStateProvider(ChannelItemsAdapter.this.mHomeListStateProvider);
        }

        /* access modifiers changed from: package-private */
        public void recycle() {
            this.mProgramController.setListStateProvider(null);
            this.mProgramController.setHomeListStateProvider(null);
            this.mProgramController.recycle();
            ChannelItemsAdapter.this.mHandler.removeCallbacks(this.mNotifyLiveProgressUpdateRunnable);
        }

        public void onHomePressed(Context c) {
            this.mProgramController.onHomePressed(c);
        }
    }
}
