package com.google.android.tvlauncher.home;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.p004v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import androidx.leanback.widget.FacetProvider;
import androidx.leanback.widget.ItemAlignmentFacet;
import androidx.leanback.widget.VerticalGridView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.libraries.social.analytics.visualelement.VisualElementTag;
import com.google.android.tvlauncher.BackHomeControllerListeners;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.TvlauncherLogEnum;
import com.google.android.tvlauncher.analytics.ClickEvent;
import com.google.android.tvlauncher.analytics.EventLogger;
import com.google.android.tvlauncher.analytics.LogEvent;
import com.google.android.tvlauncher.analytics.LogEventParameters;
import com.google.android.tvlauncher.analytics.UserActionEvent;
import com.google.android.tvlauncher.application.TvLauncherApplicationBase;
import com.google.android.tvlauncher.appsview.LaunchItem;
import com.google.android.tvlauncher.appsview.data.LaunchItemsManager;
import com.google.android.tvlauncher.appsview.data.LaunchItemsManagerProvider;
import com.google.android.tvlauncher.data.ChannelOrderManager;
import com.google.android.tvlauncher.data.HomeChannelsObserver;
import com.google.android.tvlauncher.data.TvDataManager;
import com.google.android.tvlauncher.data.WatchNextProgramsObserver;
import com.google.android.tvlauncher.home.view.ChannelView;
import com.google.android.tvlauncher.home.view.ConfigureChannelsRowView;
import com.google.android.tvlauncher.home.view.HomeRowAnimator;
import com.google.android.tvlauncher.inputs.InputsManagerUtil;
import com.google.android.tvlauncher.model.HomeChannel;
import com.google.android.tvlauncher.model.Program;
import com.google.android.tvlauncher.notifications.NotificationsPanelController;
import com.google.android.tvlauncher.notifications.NotificationsTrayAdapter;
import com.google.android.tvlauncher.notifications.NotificationsUtils;
import com.google.android.tvlauncher.util.GservicesUtils;
import com.google.android.tvlauncher.util.IntentLaunchDispatcher;
import com.google.android.tvlauncher.util.KeylineUtil;
import com.google.android.tvlauncher.util.OemConfiguration;
import com.google.android.tvlauncher.util.Util;
import com.google.android.tvlauncher.util.palette.PaletteUtil;
import com.google.android.tvlauncher.view.HomeTopRowView;
import com.google.android.tvlauncher.view.SearchView;
import com.google.android.tvlauncher.widget.PartnerWidgetInfo;
import com.google.logs.tvlauncher.config.TvLauncherConstants;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class HomeController extends RecyclerView.Adapter<HomeRowViewHolder> implements HomeTopRowView.OnActionListener, BackHomeControllerListeners.OnBackPressedListener, BackHomeControllerListeners.OnBackNotHandledListener, BackHomeControllerListeners.OnHomePressedListener, BackHomeControllerListeners.OnHomeNotHandledListener, AccessibilityManager.AccessibilityStateChangeListener, LaunchItemsManager.AppsViewChangeListener, HomeTopRowView.OnHomeTopRowFocusChangedListener {
    static final int STATE_CHANNEL_ACTIONS = 2;
    static final int STATE_DEFAULT = 0;
    static final int STATE_MOVE_CHANNEL = 3;
    static final int STATE_ZOOMED_OUT = 1;
    private static final int ACCESSIBILITY_APP_SELECT_DELAY_MILLIS = 50;
    private static final int BACKGROUND_UPDATE_DELAY_MILLIS = 2000;
    private static final boolean DEBUG = false;
    private static final int EXIT_FAST_SCROLLING_DELAY_MILLIS = 600;
    private static final int MAX_SMOOTH_SCROLL_DISTANCE = 6;
    private static final int NOTIFY_IDLE_STATE_CHANGED_DELAY_MILLIS = 500;
    private static final int NUMBER_OF_ROWS_ABOVE_CHANNELS_WITHOUT_WATCH_NEXT = 2;
    private static final int NUMBER_OF_ROWS_ABOVE_CHANNELS_WITH_WATCH_NEXT = 3;
    private static final int NUMBER_OF_ROWS_BELOW_CHANNELS = 1;
    private static final String PAYLOAD_ASSISTANT_ICON = "PAYLOAD_ASSISTANT_ICON";
    private static final String PAYLOAD_ASSISTANT_SUGGESTIONS = "PAYLOAD_ASSISTANT_SUGGESTIONS";
    private static final String PAYLOAD_CHANNEL_ITEM_METADATA = "PAYLOAD_CHANNEL_ITEM_METADATA";
    private static final String PAYLOAD_CHANNEL_LOGO_TITLE = "PAYLOAD_CHANNEL_LOGO_TITLE";
    private static final String PAYLOAD_CHANNEL_MOVE_ACTION = "PAYLOAD_CHANNEL_MOVE_ACTION";
    private static final String PAYLOAD_FAST_SCROLLING = "PAYLOAD_FAST_SCROLLING";
    private static final String PAYLOAD_HOTWORD_STATUS = "PAYLOAD_HOTWORD_STATUS";
    private static final String PAYLOAD_IDLE_STATE = "PAYLOAD_IDLE_STATE";
    private static final String PAYLOAD_INPUT_ICON_VISIBILITY = "PAYLOAD_INPUT_ICON_VISIBILITY";
    private static final String PAYLOAD_MIC_STATUS = "PAYLOAD_MIC_STATUS";
    private static final String PAYLOAD_NOTIF_COUNT_CURSOR = "PAYLOAD_NOTIF_COUNT_CURSOR";
    private static final String PAYLOAD_NOTIF_TRAY_CURSOR = "PAYLOAD_NOTIF_TRAY_CURSOR";
    private static final String PAYLOAD_PARTNER_WIDGET_INFO = "PAYLOAD_PARTNER_WIDGET_INFO";
    private static final String PAYLOAD_STATE = "PAYLOAD_STATE";
    private static final String PAYLOAD_WATCH_NEXT_CARD_SELECTION_CHANGED = "PAYLOAD_WATCH_NEXT_CARD_SELECTION_CHANGED";
    private static final String PAYLOAD_WATCH_NEXT_DATA_CHANGED = "PAYLOAD_WATCH_NEXT_DATA_CHANGED";
    private static final long REFRESH_WATCH_NEXT_OFFSET_INTERVAL_MILLIS = 600000;
    private static final int ROW_TYPE_APPS = 1;
    private static final int ROW_TYPE_APPS_POSITION = 1;
    private static final int ROW_TYPE_BRANDED_SPONSORED_CHANNEL = 4;
    private static final int ROW_TYPE_CHANNEL = 3;
    private static final int ROW_TYPE_CONFIGURE_CHANNELS = 6;
    private static final int ROW_TYPE_TOP = 0;
    private static final int ROW_TYPE_TOP_POSITION = 0;
    private static final int ROW_TYPE_UNBRANDED_SPONSORED_CHANNEL = 5;
    private static final int ROW_TYPE_WATCH_NEXT = 2;
    private static final int ROW_TYPE_WATCH_NEXT_POSITION = 2;
    private static final String TAG = "HomeController";
    private static final int THRESHOLD_HOME_PRESS_PAUSE_INTERVAL_MILLIS = 200;
    /* access modifiers changed from: private */
    public final Context mContext;
    /* access modifiers changed from: private */
    public final TvDataManager mDataManager;
    /* access modifiers changed from: private */
    public final EventLogger mEventLogger;
    /* access modifiers changed from: private */
    public final Runnable mNotifyChannelItemMetadataChangedRunnable = new HomeController$$Lambda$3(this);
    /* access modifiers changed from: private */
    public final Runnable mNotifyWatchNextCardSelectionChangedRunnable = new HomeController$$Lambda$4(this);
    private final Runnable mNotifyIdleStateChangedRunnable = new HomeController$$Lambda$5(this);
    private final Runnable mNotifySelectionChangedRunnable = new HomeController$$Lambda$1(this);
    private final Runnable mNotifyStateChangedRunnable = new HomeController$$Lambda$2(this);
    private final Runnable mNotifyTopRowStateChangedRunnable = new HomeController$$Lambda$0(this);
    /* access modifiers changed from: private */
    public ItemAlignmentFacet mAppsRowDefaultWithTrayFacet;
    /* access modifiers changed from: private */
    public ItemAlignmentFacet mAppsRowZoomedOutWithTrayFacet;
    /* access modifiers changed from: private */
    public HomeBackgroundController mBackgroundController;
    /* access modifiers changed from: private */
    public ItemAlignmentFacet mConfigureChannelsFacet;
    /* access modifiers changed from: private */
    public EmptyChannelsHelper mEmptyChannelsHelper;
    /* access modifiers changed from: private */
    public RecyclerViewFastScrollingManager mFastScrollingManager;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler();
    /* access modifiers changed from: private */
    public ProgramController mLastSelectedProgramController;
    /* access modifiers changed from: private */
    public final Runnable mUpdateBackgroundForProgramAfterDelayRunnable = new Runnable() {
        public void run() {
            if (!HomeController.this.mFastScrollingManager.isFastScrollingEnabled() && HomeController.this.mBackgroundController != null && HomeController.this.mLastSelectedProgramController != null && HomeController.this.mLastSelectedProgramController.isViewFocused() && HomeController.this.mLastSelectedProgramController.getPreviewImagePalette() != null) {
                HomeController.this.mBackgroundController.updateBackground(HomeController.this.mLastSelectedProgramController.getPreviewImagePalette());
            }
        }
    };
    /* access modifiers changed from: private */
    public VerticalGridView mList;
    private final RecyclerViewStateProvider mHomeListStateProvider = new RecyclerViewStateProvider() {
        public boolean isAnimating() {
            return HomeController.this.mList != null && HomeController.this.mList.isAnimating();
        }

        public boolean isAnimating(RecyclerView.ItemAnimator.ItemAnimatorFinishedListener listener) {
            if (HomeController.this.mList != null && HomeController.this.mList.getItemAnimator() != null) {
                return HomeController.this.mList.getItemAnimator().isRunning(listener);
            }
            if (listener == null) {
                return false;
            }
            listener.onAnimationsFinished();
            return false;
        }
    };
    /* access modifiers changed from: private */
    public Cursor mNotifTrayCursor = null;
    /* access modifiers changed from: private */
    public ItemAlignmentFacet mSecondToLastRowZoomedOutFacet;
    /* access modifiers changed from: private */
    public boolean mSelectFirstAppWhenRowSelected;
    /* access modifiers changed from: private */
    public long mSelectedId;
    /* access modifiers changed from: private */
    public int mSelectedPosition = 1;
    private final Runnable mUpdateBackgroundForTopRowsAfterDelayRunnable = new Runnable() {
        public void run() {
            if (HomeController.this.mBackgroundController != null && HomeController.this.mSelectedPosition <= 1) {
                HomeController.this.mBackgroundController.enterDarkMode();
            }
        }
    };
    /* access modifiers changed from: private */
    public ItemAlignmentFacet mSponsoredChannelFacet;
    /* access modifiers changed from: private */
    public int mState = 0;
    /* access modifiers changed from: private */
    public ItemAlignmentFacet mThirdToLastRowZoomedOutFacet;
    /* access modifiers changed from: private */
    public boolean mWatchNextEnabled;
    /* access modifiers changed from: private */
    public Handler mWatchNextHandler = new Handler();
    /* access modifiers changed from: private */
    public Runnable mRequeryWatchNext = new Runnable() {
        public void run() {
            if (HomeController.this.mWatchNextEnabled) {
                HomeController.this.mDataManager.loadWatchNextProgramData();
            }
            HomeController.this.mWatchNextHandler.postDelayed(HomeController.this.mRequeryWatchNext, 600000);
        }
    };
    /* access modifiers changed from: private */
    public boolean mWatchNextVisible;
    private final OnProgramSelectedListener mOnProgramSelectedListener = new OnProgramSelectedListener() {
        public void onProgramSelected(Program program, ProgramController programController) {
            ProgramController unused = HomeController.this.mLastSelectedProgramController = programController;
            if (HomeController.this.mState == 0 && HomeController.this.mFastScrollingManager.isFastScrollingEnabled()) {
                HomeController homeController = HomeController.this;
                if (homeController.hasFastScrollingMode(homeController.mSelectedPosition)) {
                    return;
                }
            }
            if (HomeController.this.mBackgroundController != null) {
                HomeController.this.mHandler.removeCallbacks(HomeController.this.mUpdateBackgroundForProgramAfterDelayRunnable);
                HomeController.this.mHandler.postDelayed(HomeController.this.mUpdateBackgroundForProgramAfterDelayRunnable, AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS);
            }
            HomeController homeController2 = HomeController.this;
            int rowType = homeController2.getItemViewType(homeController2.mSelectedPosition);
            if (rowType != 4 && rowType != 5) {
                HomeController.this.mHandler.removeCallbacks(HomeController.this.mNotifyChannelItemMetadataChangedRunnable);
                if (HomeController.this.mList == null || HomeController.this.mList.isComputingLayout()) {
                    Log.w(HomeController.TAG, "list is still computing layout => schedule item metadata change");
                    HomeController.this.mHandler.post(HomeController.this.mNotifyChannelItemMetadataChangedRunnable);
                } else {
                    HomeController.this.mNotifyChannelItemMetadataChangedRunnable.run();
                }
                HomeController homeController3 = HomeController.this;
                if (homeController3.getItemViewType(homeController3.mSelectedPosition) == 2) {
                    HomeController.this.mHandler.removeCallbacks(HomeController.this.mNotifyWatchNextCardSelectionChangedRunnable);
                    if (HomeController.this.mList == null || HomeController.this.mList.isComputingLayout()) {
                        Log.w(HomeController.TAG, "list is still computing layout => schedule watch next card selection changed");
                        HomeController.this.mHandler.post(HomeController.this.mNotifyWatchNextCardSelectionChangedRunnable);
                        return;
                    }
                    HomeController.this.mNotifyWatchNextCardSelectionChangedRunnable.run();
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public Runnable mRefreshWatchNextOffset = new Runnable() {
        public void run() {
            if (HomeController.this.mWatchNextEnabled) {
                HomeController.this.mDataManager.refreshWatchNextOffset();
                HomeController.this.updateWatchNextVisibility();
            }
            HomeController.this.mWatchNextHandler.postDelayed(HomeController.this.mRefreshWatchNextOffset, 600000);
        }
    };
    private final BroadcastReceiver mTimeSetReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            HomeController.this.mWatchNextHandler.removeCallbacks(HomeController.this.mRefreshWatchNextOffset);
            HomeController.this.mWatchNextHandler.removeCallbacks(HomeController.this.mRequeryWatchNext);
            HomeController.this.mWatchNextHandler.post(HomeController.this.mRefreshWatchNextOffset);
            HomeController.this.mWatchNextHandler.post(HomeController.this.mRequeryWatchNext);
        }
    };
    /* access modifiers changed from: private */
    public final WatchNextProgramsObserver mWatchNextProgramsObserver = new WatchNextProgramsObserver() {
        public void onProgramsChange() {
            if (HomeController.this.mWatchNextEnabled) {
                if (HomeController.this.mWatchNextVisible) {
                    HomeController homeController = HomeController.this;
                    if (homeController.getItemViewType(homeController.mSelectedPosition) == 2) {
                        HomeController homeController2 = HomeController.this;
                        homeController2.notifyItemChanged(homeController2.mSelectedPosition, HomeController.PAYLOAD_WATCH_NEXT_DATA_CHANGED);
                    }
                }
                HomeController.this.updateWatchNextVisibility();
            }
        }
    };
    private Set<ChannelRowController> mActiveChannelRowControllers = new HashSet();
    private Set<WatchNextRowController> mActiveWatchNextRowControllers = new HashSet();
    private Set<String> mAddToWatchNextPackagesBlacklist;
    private Drawable mAssistantIcon;
    private String[] mAssistantSuggestions;
    private int mChannelKeyline;
    private RequestManager mChannelLogoRequestManager;
    private boolean mHotwordEnabled;
    private boolean mInputIconVisible;
    private IntentLaunchDispatcher mIntentLauncher;
    private boolean mIsIdle = false;
    private long mLastPausedTime;
    private int mMicStatus;
    private Cursor mNotifCountCursor = null;
    private BackHomeControllerListeners.OnBackNotHandledListener mOnBackNotHandledListener;
    private PartnerWidgetInfo mPartnerWidgetInfo;
    private HashSet<Integer> mPrevSelectedPositions = new HashSet<>();
    private Set<String> mRemoveProgramPackagesBlacklist;
    private Runnable mSelectFirstAppRunnable = new Runnable() {
        public void run() {
            if (HomeController.this.mSelectFirstAppWhenRowSelected && HomeController.this.mSelectedPosition == 1) {
                HomeRow appsRow = HomeController.this.getHomeRow(1);
                if (appsRow instanceof FavoriteLaunchItemsRowController) {
                    ((FavoriteLaunchItemsRowController) appsRow).setSelectedItemPosition(0);
                    boolean unused = HomeController.this.mSelectFirstAppWhenRowSelected = false;
                }
            }
        }
    };
    private final HomeChannelsObserver mChannelsObserver = new HomeChannelsObserver() {
        public void onChannelsChange() {
            HomeController.this.mEmptyChannelsHelper.onChannelsChange();
            int count = HomeController.this.mDataManager.getHomeChannelCount();
            if (count >= 0) {
                LogEvent event = new LogEventParameters(TvlauncherLogEnum.TvLauncherEventCode.OPEN_HOME, LogEventParameters.SHOWN_CHANNEL_COUNT);
                event.getChannelCollection().setBrowsableCount(count);
                HomeController.this.mEventLogger.log(event);
            }
            long oldSelectedId = HomeController.this.mSelectedId;
            if (HomeController.this.mSelectedPosition >= HomeController.this.getItemCount()) {
                HomeController homeController = HomeController.this;
                homeController.setSelectedPosition(homeController.getItemCount() - 1, false);
            }
            HomeController.this.updateSelectedId();
            if (HomeController.this.mState == 3 || HomeController.this.mState == 2) {
                if (HomeController.this.mDataManager.isChannelEmpty(HomeController.this.mSelectedId)) {
                    int unused = HomeController.this.mState = 1;
                } else if (oldSelectedId != HomeController.this.mSelectedId) {
                    int unused2 = HomeController.this.mState = 1;
                }
            }
            if (HomeController.this.mState == 1 && Util.isAccessibilityEnabled(HomeController.this.mContext)) {
                int unused3 = HomeController.this.mState = 0;
            }
            HomeController.this.notifyDataSetChanged();
        }

        public void onChannelMove(int fromIndex, int toIndex) {
            int fromPosition = HomeController.this.getAdapterPositionForChannelIndex(fromIndex);
            int toPosition = HomeController.this.getAdapterPositionForChannelIndex(toIndex);
            if (HomeController.this.mSelectedPosition == fromPosition) {
                int unused = HomeController.this.mSelectedPosition = toPosition;
                HomeController.this.updateSelectedId();
            }
            int numChannels = HomeController.this.getChannelCount();
            if (fromIndex == 0 || toIndex == 0 || fromIndex == numChannels - 1 || toIndex == numChannels - 1) {
                HomeController.this.notifyItemChanged(fromPosition, HomeController.PAYLOAD_CHANNEL_MOVE_ACTION);
                HomeController.this.notifyItemChanged(toPosition, HomeController.PAYLOAD_CHANNEL_MOVE_ACTION);
            }
            HomeController.this.notifyItemMoved(fromPosition, toPosition);
        }

        public void onEmptyChannelRemove(int channelIndex) {
            HomeController homeController = HomeController.this;
            homeController.onItemRemoved(homeController.getAdapterPositionForChannelIndex(channelIndex));
        }

        public void onChannelEmptyStatusChange(int channelIndex) {
            int adapterPosition = HomeController.this.getAdapterPositionForChannelIndex(channelIndex);
            long channelId = HomeController.this.mDataManager.getHomeChannel(channelIndex).getId();
            if (HomeController.this.mSelectedPosition != adapterPosition || (!(HomeController.this.mState == 2 || HomeController.this.mState == 3) || !HomeController.this.mDataManager.isChannelEmpty(channelId))) {
                HomeController.this.notifyItemChanged(adapterPosition, HomeController.PAYLOAD_STATE);
            } else {
                if (Util.isAccessibilityEnabled(HomeController.this.mContext)) {
                    int unused = HomeController.this.mState = 0;
                } else {
                    int unused2 = HomeController.this.mState = 1;
                }
                HomeController homeController = HomeController.this;
                homeController.notifyItemRangeChanged(0, homeController.getItemCount(), HomeController.PAYLOAD_STATE);
            }
            HomeController.this.mEmptyChannelsHelper.onChannelEmptyStatusChange(channelId);
        }
    };
    private boolean mStarted;
    private IntentFilter mTimeFilter = new IntentFilter();
    private SharedPreferences.OnSharedPreferenceChangeListener mWatchNextPrefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            boolean watchNextEnabled;
            if (WatchNextPrefs.SHOW_WATCH_NEXT_ROW_KEY.equals(key) && HomeController.this.mWatchNextEnabled != (watchNextEnabled = sharedPreferences.getBoolean(WatchNextPrefs.SHOW_WATCH_NEXT_ROW_KEY, OemConfiguration.get(HomeController.this.mContext).isWatchNextChannelEnabledByDefault()))) {
                boolean unused = HomeController.this.mWatchNextEnabled = watchNextEnabled;
                if (HomeController.this.mWatchNextEnabled) {
                    HomeController.this.registerObserverAndUpdateWatchNextDataIfNeeded();
                    return;
                }
                HomeController.this.setWatchNextVisibility(false);
                HomeController.this.mDataManager.unregisterWatchNextProgramsObserver(HomeController.this.mWatchNextProgramsObserver);
            }
        }
    };

    HomeController(Context context, EventLogger eventLogger, TvDataManager dataManager, EmptyChannelsHelper emptyChannelsHelper) {
        this.mContext = context;
        this.mEventLogger = eventLogger;
        this.mDataManager = dataManager;
        this.mEmptyChannelsHelper = emptyChannelsHelper;
        this.mIntentLauncher = ((TvLauncherApplicationBase) context.getApplicationContext()).getIntentLauncher();
        this.mTimeFilter.addAction("android.intent.action.TIME_SET");
        this.mTimeFilter.addAction("android.intent.action.DATE_CHANGED");
        this.mTimeFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        this.mChannelKeyline = this.mContext.getResources().getDimensionPixelSize(C1188R.dimen.channel_items_list_keyline);
        this.mAppsRowZoomedOutWithTrayFacet = createChannelItemAlignmentFacet(C1188R.dimen.channel_items_list_apps_row_zoomed_out_notif_tray_keyline_offset);
        this.mAppsRowDefaultWithTrayFacet = createChannelItemAlignmentFacet(C1188R.dimen.channel_items_list_apps_row_notif_tray_keyline_offset);
        this.mSponsoredChannelFacet = createChannelItemAlignmentFacet(C1188R.dimen.channel_items_list_sponsored_channel_keyline_offset);
        this.mConfigureChannelsFacet = KeylineUtil.createItemAlignmentFacet(this.mContext.getResources().getDimensionPixelSize(C1188R.dimen.home_configure_channels_keyline_offset), Integer.valueOf(C1188R.C1191id.button));
        this.mSecondToLastRowZoomedOutFacet = createChannelItemAlignmentFacet(C1188R.dimen.channel_items_list_second_to_last_row_zoomed_out_keyline_offset);
        this.mThirdToLastRowZoomedOutFacet = createChannelItemAlignmentFacet(C1188R.dimen.channel_items_list_third_to_last_row_zoomed_out_keyline_offset);
        setHasStableIds(true);
        SharedPreferences watchNextPreferences = context.getSharedPreferences(WatchNextPrefs.WATCH_NEXT_PREF_FILE_NAME, 0);
        this.mWatchNextEnabled = watchNextPreferences.getBoolean(WatchNextPrefs.SHOW_WATCH_NEXT_ROW_KEY, OemConfiguration.get(this.mContext).isWatchNextChannelEnabledByDefault());
        watchNextPreferences.registerOnSharedPreferenceChangeListener(this.mWatchNextPrefListener);
        PaletteUtil.registerGlidePaletteTranscoder(this.mContext);
        this.mAddToWatchNextPackagesBlacklist = GservicesUtils.retrievePackagesBlacklistedForWatchNext(this.mContext.getContentResolver());
        this.mRemoveProgramPackagesBlacklist = GservicesUtils.retrievePackagesBlacklistedForProgramRemoval(this.mContext.getContentResolver());
        registerChannelsObserverAndLoadDataIfNeeded();
        if (this.mWatchNextEnabled) {
            registerObserverAndUpdateWatchNextDataIfNeeded();
        }
        this.mStarted = true;
        updateSelectedId();
    }

    private static String stateToString(int state) {
        String stateString;
        if (state == 0) {
            stateString = "STATE_DEFAULT";
        } else if (state == 1) {
            stateString = "STATE_ZOOMED_OUT";
        } else if (state == 2) {
            stateString = "STATE_CHANNEL_ACTIONS";
        } else if (state != 3) {
            stateString = "STATE_UNKNOWN";
        } else {
            stateString = "STATE_MOVE_CHANNEL";
        }
        StringBuilder sb = new StringBuilder(String.valueOf(stateString).length() + 14);
        sb.append(stateString);
        sb.append(" (");
        sb.append(state);
        sb.append(")");
        return sb.toString();
    }

    public /* bridge */ /* synthetic */ void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i, @NonNull List list) {
        onBindViewHolder((HomeRowViewHolder) viewHolder, i, (List<Object>) list);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public HomeChannelsObserver getChannelsObserver() {
        return this.mChannelsObserver;
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$new$0$HomeController() {
        notifyItemChanged(0, PAYLOAD_STATE);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$new$1$HomeController() {
        int i;
        int i2;
        Set<Integer> positionsToUpdate = new HashSet<>(this.mPrevSelectedPositions.size() + 5);
        int i3 = this.mState;
        if (i3 == 0 || i3 == 1) {
            positionsToUpdate.addAll(this.mPrevSelectedPositions);
            this.mPrevSelectedPositions.clear();
            positionsToUpdate.add(Integer.valueOf(this.mSelectedPosition));
            positionsToUpdate.add(Integer.valueOf(this.mSelectedPosition - 1));
            positionsToUpdate.add(Integer.valueOf(this.mSelectedPosition + 1));
        }
        if (this.mState == 0 && ((i2 = this.mSelectedPosition) == 0 || i2 == 1)) {
            positionsToUpdate.add(Integer.valueOf(this.mSelectedPosition + 2));
            positionsToUpdate.add(Integer.valueOf(this.mSelectedPosition + 3));
        }
        if (this.mState == 1 && ((i = this.mSelectedPosition) == 0 || i == 1)) {
            positionsToUpdate.add(Integer.valueOf(this.mSelectedPosition + 2));
            positionsToUpdate.add(Integer.valueOf(this.mSelectedPosition + 3));
            positionsToUpdate.add(Integer.valueOf(this.mSelectedPosition + 4));
        }
        int itemCount = getItemCount();
        for (Integer position : positionsToUpdate) {
            if (position.intValue() >= 0 && position.intValue() < itemCount) {
                notifyItemChanged(position.intValue(), PAYLOAD_STATE);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$new$2$HomeController() {
        notifyItemRangeChanged(0, getItemCount(), PAYLOAD_STATE);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$new$3$HomeController() {
        notifyItemChanged(this.mSelectedPosition, PAYLOAD_CHANNEL_ITEM_METADATA);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$new$4$HomeController() {
        notifyItemChanged(this.mSelectedPosition, PAYLOAD_WATCH_NEXT_CARD_SELECTION_CHANGED);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$new$5$HomeController() {
        notifyItemChanged(0, PAYLOAD_IDLE_STATE);
    }

    public void setList(VerticalGridView list) {
        this.mList = list;
        if (this.mList.getLayoutManager() != null) {
            this.mList.getLayoutManager().setItemPrefetchEnabled(false);
        }
        this.mList.setItemAlignmentViewId(C1188R.C1191id.items_list);
        this.mList.setWindowAlignment(1);
        this.mList.setWindowAlignmentOffsetPercent(0.0f);
        this.mList.setWindowAlignmentOffset(this.mChannelKeyline);
        this.mList.setItemViewCacheSize(5);
        this.mList.setSelectedPosition(this.mSelectedPosition);
        this.mFastScrollingManager = new RecyclerViewFastScrollingManager(this.mList, new HomeRowAnimator());
        this.mFastScrollingManager.setOnFastScrollingChangedListener(new HomeController$$Lambda$6(this));
        this.mFastScrollingManager.setAnimatorEnabled(true);
        this.mFastScrollingManager.setScrollEnabled(false);
        this.mFastScrollingManager.setExitFastScrollingDelayMs(600);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$setList$6$HomeController(boolean fastScrolling) {
        notifyItemRangeChanged(0, getItemCount(), PAYLOAD_FAST_SCROLLING);
        if (!fastScrolling) {
            notifyItemChanged(this.mSelectedPosition, PAYLOAD_CHANNEL_ITEM_METADATA);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isWatchNextVisible() {
        return this.mWatchNextVisible;
    }

    /* access modifiers changed from: package-private */
    public void setBackgroundController(HomeBackgroundController backgroundController) {
        this.mBackgroundController = backgroundController;
    }

    /* access modifiers changed from: package-private */
    public void setOnBackNotHandledListener(BackHomeControllerListeners.OnBackNotHandledListener onBackNotHandledListener) {
        this.mOnBackNotHandledListener = onBackNotHandledListener;
    }

    /* access modifiers changed from: package-private */
    public void updateWatchNextVisibility() {
        setWatchNextVisibility(shouldShowWatchNextChannel());
    }

    /* access modifiers changed from: private */
    public void setWatchNextVisibility(boolean watchNextVisible) {
        if (this.mWatchNextVisible != watchNextVisible) {
            this.mWatchNextVisible = watchNextVisible;
            if (this.mWatchNextVisible) {
                int i = this.mSelectedPosition;
                if (i >= 2) {
                    this.mSelectedPosition = i + 1;
                }
                notifyItemInserted(2);
                notifyItemRangeChanged(0, getItemCount(), PAYLOAD_STATE);
            } else {
                int i2 = this.mSelectedPosition;
                if (i2 > 2) {
                    this.mSelectedPosition = i2 - 1;
                }
                notifyItemRemoved(2);
                notifyItemRangeChanged(0, getItemCount(), PAYLOAD_STATE);
            }
            updateSelectedId();
        }
    }

    /* access modifiers changed from: private */
    public void registerObserverAndUpdateWatchNextDataIfNeeded() {
        this.mDataManager.registerWatchNextProgramsObserver(this.mWatchNextProgramsObserver);
        if (!this.mDataManager.isWatchNextProgramsDataLoaded() || this.mDataManager.isWatchNextProgramsDataStale()) {
            this.mDataManager.loadWatchNextProgramData();
            this.mDataManager.loadAllWatchNextProgramDataIntoCache();
            return;
        }
        updateWatchNextVisibility();
    }

    private void registerChannelsObserverAndLoadDataIfNeeded() {
        this.mDataManager.registerHomeChannelsObserver(this.mChannelsObserver);
        if (!this.mDataManager.isHomeChannelDataLoaded() || this.mDataManager.isHomeChannelDataStale()) {
            this.mDataManager.loadHomeChannelData();
        }
    }

    private boolean shouldShowWatchNextChannel() {
        return this.mDataManager.getWatchNextProgramsCount() > 0;
    }

    /* access modifiers changed from: private */
    public int getChannelCount() {
        if (this.mDataManager.isHomeChannelDataLoaded()) {
            return this.mDataManager.getHomeChannelCount();
        }
        return 0;
    }

    /* access modifiers changed from: package-private */
    public void onStart() {
        ActiveMediaSessionManager.getInstance(this.mContext).start();
        this.mContext.registerReceiver(this.mTimeSetReceiver, this.mTimeFilter);
        LaunchItemsManagerProvider.getInstance(this.mContext).registerAppsViewChangeListener(this);
        for (WatchNextRowController controller : this.mActiveWatchNextRowControllers) {
            controller.onStart();
        }
        for (ChannelRowController controller2 : this.mActiveChannelRowControllers) {
            controller2.onStart();
        }
        AccessibilityManager accessibilityManager = (AccessibilityManager) this.mContext.getSystemService("accessibility");
        if (accessibilityManager != null) {
            accessibilityManager.addAccessibilityStateChangeListener(this);
        }
        LaunchItemsManager itemsManager = LaunchItemsManagerProvider.getInstance(this.mContext);
        if (!itemsManager.areItemsLoaded() || itemsManager.hasPendingLoadRequest()) {
            itemsManager.refreshLaunchItems();
        }
        if (!this.mStarted) {
            this.mEmptyChannelsHelper.onStart();
            registerChannelsObserverAndLoadDataIfNeeded();
            if (this.mWatchNextEnabled) {
                registerObserverAndUpdateWatchNextDataIfNeeded();
            }
            this.mStarted = true;
        }
    }

    /* access modifiers changed from: package-private */
    public void onStop() {
        ActiveMediaSessionManager.getInstance(this.mContext).stop();
        if (NotificationsUtils.hasNowPlaying(this.mNotifTrayCursor)) {
            updateNotifications(null);
            HomeTopRowView topRowView = getHomeTopRowView();
            if (topRowView != null) {
                topRowView.hideNotificationsTray();
            }
        }
        this.mContext.unregisterReceiver(this.mTimeSetReceiver);
        this.mEmptyChannelsHelper.onStop();
        this.mDataManager.unregisterHomeChannelsObserver(this.mChannelsObserver);
        this.mDataManager.unregisterWatchNextProgramsObserver(this.mWatchNextProgramsObserver);
        this.mDataManager.pruneChannelProgramsCache();
        LaunchItemsManagerProvider.getInstance(this.mContext).unregisterAppsViewChangeListener(this);
        for (WatchNextRowController controller : this.mActiveWatchNextRowControllers) {
            controller.onStop();
        }
        for (ChannelRowController controller2 : this.mActiveChannelRowControllers) {
            controller2.onStop();
        }
        AccessibilityManager manager = (AccessibilityManager) this.mContext.getSystemService("accessibility");
        if (manager != null) {
            manager.removeAccessibilityStateChangeListener(this);
        }
        this.mStarted = false;
    }

    /* access modifiers changed from: package-private */
    public void onResume() {
        this.mWatchNextHandler.postDelayed(this.mRefreshWatchNextOffset, 600000);
        this.mWatchNextHandler.postDelayed(this.mRequeryWatchNext, 600000);
        if (this.mDataManager.isHomeChannelDataLoaded()) {
            LogEventParameters eventParams = new LogEventParameters(TvlauncherLogEnum.TvLauncherEventCode.OPEN_HOME, LogEventParameters.SHOWN_CHANNEL_COUNT);
            eventParams.getChannelCollection().setBrowsableCount(this.mDataManager.getHomeChannelCount());
            this.mEventLogger.log(eventParams);
        }
        if (isWatchNextVisible() && this.mDataManager.isWatchNextProgramsDataLoaded()) {
            LogEventParameters eventParams2 = new LogEventParameters(TvlauncherLogEnum.TvLauncherEventCode.OPEN_HOME, LogEventParameters.WATCH_NEXT);
            eventParams2.getWatchNextChannel().setProgramCount(this.mDataManager.getWatchNextProgramsCount());
            this.mEventLogger.log(eventParams2);
        }
    }

    /* access modifiers changed from: package-private */
    public void onPause() {
        ProgramController programController = this.mLastSelectedProgramController;
        if (programController != null) {
            programController.stopPreviewMedia();
        }
        this.mWatchNextHandler.removeCallbacks(this.mRefreshWatchNextOffset);
        this.mWatchNextHandler.removeCallbacks(this.mRequeryWatchNext);
        this.mLastPausedTime = SystemClock.elapsedRealtime();
    }

    /* access modifiers changed from: private */
    @Nullable
    public HomeRow getHomeRow(int position) {
        HomeRowViewHolder holder = (HomeRowViewHolder) this.mList.findViewHolderForAdapterPosition(position);
        if (holder != null) {
            return holder.homeRow;
        }
        return null;
    }

    @Nullable
    private HomeRow getSelectedHomeRow() {
        return getHomeRow(this.mSelectedPosition);
    }

    @Nullable
    private HomeTopRowView getHomeTopRowView() {
        HomeRow row = getHomeRow(0);
        if (row != null) {
            return (HomeTopRowView) row.getView();
        }
        return null;
    }

    private void scrollToAppsRow() {
        this.mSelectFirstAppWhenRowSelected = true;
        if (this.mList.getSelectedPosition() > 6 || Util.areHomeScreenAnimationsEnabled(this.mContext)) {
            this.mList.setLayoutFrozen(true);
            this.mList.setSelectedPosition(1);
            setSelectedPosition(1, true);
            this.mList.setLayoutFrozen(false);
            return;
        }
        this.mList.setSelectedPositionSmooth(1);
    }

    public void onAccessibilityStateChanged(boolean enabled) {
        notifyItemChanged(0, PAYLOAD_NOTIF_TRAY_CURSOR);
        if (enabled && this.mState != 0) {
            this.mState = 0;
            notifyItemRangeChanged(0, getItemCount(), PAYLOAD_STATE);
        }
    }

    public void onBackPressed(Context c) {
        HomeRow homeRow = getSelectedHomeRow();
        if (homeRow instanceof BackHomeControllerListeners.OnBackPressedListener) {
            ((BackHomeControllerListeners.OnBackPressedListener) homeRow).onBackPressed(c);
        } else {
            onBackNotHandled(c);
        }
    }

    public void onBackNotHandled(Context c) {
        int i;
        if (this.mList.getSelectedPosition() == 1 || !((i = this.mState) == 0 || i == 1)) {
            BackHomeControllerListeners.OnBackNotHandledListener onBackNotHandledListener = this.mOnBackNotHandledListener;
            if (onBackNotHandledListener != null) {
                onBackNotHandledListener.onBackNotHandled(c);
                return;
            }
            return;
        }
        scrollToAppsRow();
    }

    public void onHomePressed(Context c) {
        if (SystemClock.elapsedRealtime() - this.mLastPausedTime <= 200) {
            HomeRow homeRow = getSelectedHomeRow();
            if (homeRow instanceof BackHomeControllerListeners.OnHomePressedListener) {
                ((BackHomeControllerListeners.OnHomePressedListener) homeRow).onHomePressed(c);
            } else {
                onHomeNotHandled(c);
            }
        }
    }

    public void onHomeNotHandled(Context c) {
        if (this.mList.getSelectedPosition() != 1) {
            int i = this.mState;
            if (i == 0 || i == 1) {
                scrollToAppsRow();
            }
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public WatchNextProgramsObserver getWatchNextProgramsObserver() {
        return this.mWatchNextProgramsObserver;
    }

    /* access modifiers changed from: package-private */
    public void updateInputIconVisibility(boolean visible) {
        if (this.mInputIconVisible != visible) {
            this.mInputIconVisible = visible;
            notifyItemChanged(0, PAYLOAD_INPUT_ICON_VISIBILITY);
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean isInputIconVisible() {
        return this.mInputIconVisible;
    }

    /* access modifiers changed from: package-private */
    public void updateNotifications(Cursor cursor) {
        Cursor oldCursor = this.mNotifTrayCursor;
        this.mNotifTrayCursor = cursor;
        if (oldCursor != null) {
            oldCursor.close();
        }
        notifyItemChanged(0, PAYLOAD_NOTIF_TRAY_CURSOR);
    }

    /* access modifiers changed from: package-private */
    public void updatePanelNotificationsCount(Cursor cursor) {
        this.mNotifCountCursor = cursor;
        notifyItemChanged(0, PAYLOAD_NOTIF_COUNT_CURSOR);
    }

    /* access modifiers changed from: package-private */
    public void onIdleStateChange(boolean isIdle) {
        this.mIsIdle = isIdle;
        this.mHandler.removeCallbacks(this.mNotifyIdleStateChangedRunnable);
        this.mHandler.postDelayed(this.mNotifyIdleStateChangedRunnable, 500);
    }

    /* access modifiers changed from: package-private */
    public void onSearchIconUpdate(Drawable assistantIcon) {
        this.mAssistantIcon = assistantIcon;
        notifyItemChanged(0, PAYLOAD_ASSISTANT_ICON);
    }

    /* access modifiers changed from: package-private */
    public void onSearchSuggestionsUpdate(String[] assistantSuggestions) {
        this.mAssistantSuggestions = assistantSuggestions;
        notifyItemChanged(0, PAYLOAD_ASSISTANT_SUGGESTIONS);
    }

    /* access modifiers changed from: package-private */
    public void onPartnerWidgetUpdate(PartnerWidgetInfo info) {
        this.mPartnerWidgetInfo = info;
        notifyItemChanged(0, PAYLOAD_PARTNER_WIDGET_INFO);
    }

    /* access modifiers changed from: package-private */
    public void onMicStatusUpdated(Integer status) {
        this.mMicStatus = status == null ? 0 : status.intValue();
        notifyItemChanged(0, PAYLOAD_MIC_STATUS);
    }

    /* access modifiers changed from: package-private */
    public void onHotwordEnabledUpdated(Boolean enabled) {
        this.mHotwordEnabled = enabled == null ? false : enabled.booleanValue();
        notifyItemChanged(0, PAYLOAD_HOTWORD_STATUS);
    }

    /* access modifiers changed from: package-private */
    public void setChannelLogoRequestManager(RequestManager requestManager) {
        this.mChannelLogoRequestManager = requestManager;
        this.mChannelLogoRequestManager.setDefaultRequestOptions((RequestOptions) new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE));
    }

    private ItemAlignmentFacet createChannelItemAlignmentFacet(@DimenRes int dimenId) {
        return KeylineUtil.createItemAlignmentFacet(this.mContext.getResources().getDimensionPixelSize(dimenId), Integer.valueOf(C1188R.C1191id.items_list));
    }

    private int getNumberOfRowsAboveChannels() {
        if (this.mWatchNextVisible) {
            return 3;
        }
        return 2;
    }

    /* access modifiers changed from: private */
    public boolean hasFastScrollingMode(int position) {
        return position > 3 && position < getItemCount() - 3;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public int getState() {
        return this.mState;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void setState(int state) {
        boolean z = true;
        if (Util.isAccessibilityEnabled(this.mContext) && (state == 1 || state == 2 || state == 3)) {
            state = 0;
        }
        int i = this.mState;
        if (i != state) {
            if (i != 0) {
                if (i == 1) {
                    this.mEventLogger.log(new UserActionEvent(TvlauncherLogEnum.TvLauncherEventCode.EXIT_ZOOMED_OUT_MODE).setVisualElementTag(TvLauncherConstants.CHANNEL_ROW));
                } else if (i == 2) {
                    this.mEventLogger.log(new UserActionEvent(TvlauncherLogEnum.TvLauncherEventCode.EXIT_CHANNEL_ACTIONS_MODE).setVisualElementTag(TvLauncherConstants.CHANNEL_ROW));
                } else if (i == 3) {
                    this.mEventLogger.log(new ClickEvent(TvlauncherLogEnum.TvLauncherEventCode.EXIT_MOVE_CHANNEL_MODE).setVisualElementTag(TvLauncherConstants.CHANNEL_ROW));
                }
            }
            this.mState = state;
            int i2 = this.mState;
            if (i2 != 0) {
                if (i2 == 1) {
                    this.mEventLogger.log(new UserActionEvent(TvlauncherLogEnum.TvLauncherEventCode.ENTER_ZOOMED_OUT_MODE).setVisualElementTag(TvLauncherConstants.CHANNEL_ROW));
                } else if (i2 == 2) {
                    this.mEventLogger.log(new UserActionEvent(TvlauncherLogEnum.TvLauncherEventCode.ENTER_CHANNEL_ACTIONS_MODE).setVisualElementTag(TvLauncherConstants.CHANNEL_ROW));
                } else if (i2 == 3) {
                    this.mEventLogger.log(new ClickEvent(TvlauncherLogEnum.TvLauncherEventCode.ENTER_MOVE_CHANNEL_MODE).setVisualElementTag(TvLauncherConstants.MOVE_CHANNEL_BUTTON).pushParentVisualElementTag(TvLauncherConstants.CHANNEL_ROW));
                }
            }
            RecyclerViewFastScrollingManager recyclerViewFastScrollingManager = this.mFastScrollingManager;
            if (this.mState == 3) {
                z = false;
            }
            recyclerViewFastScrollingManager.setScrollAllowedDuringFastScrolling(z);
            if (this.mList.isComputingLayout()) {
                this.mHandler.post(this.mNotifyStateChangedRunnable);
            } else {
                this.mNotifyStateChangedRunnable.run();
            }
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public int getSelectedPosition() {
        return this.mSelectedPosition;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void setSelectedPosition(int selectedPosition) {
        setSelectedPosition(selectedPosition, true);
    }

    /* access modifiers changed from: private */
    public void setSelectedPosition(int selectedPosition, boolean notifyChange) {
        if (this.mSelectedPosition != selectedPosition) {
            if (this.mState != 0 || !this.mFastScrollingManager.isFastScrollingEnabled() || !hasFastScrollingMode(selectedPosition)) {
                if (this.mSelectedPosition >= getNumberOfRowsAboveChannels() && this.mSelectedPosition < getItemCount() - 1 && this.mDataManager.isChannelEmpty(this.mSelectedId)) {
                    this.mHandler.post(new HomeController$$Lambda$7(this, this.mSelectedId));
                }
                int i = this.mState;
                if ((i == 0 || i == 1) && notifyChange) {
                    this.mPrevSelectedPositions.add(Integer.valueOf(this.mSelectedPosition));
                    this.mSelectedPosition = selectedPosition;
                    updateSelectedId();
                    this.mHandler.removeCallbacks(this.mNotifySelectionChangedRunnable);
                    if (this.mList.isComputingLayout()) {
                        Log.w(TAG, "setSelectedPosition: still computing layout => scheduling");
                        this.mHandler.post(this.mNotifySelectionChangedRunnable);
                    } else {
                        this.mNotifySelectionChangedRunnable.run();
                    }
                } else {
                    this.mSelectedPosition = selectedPosition;
                    updateSelectedId();
                }
                if (this.mSelectedPosition > 1 || this.mBackgroundController == null) {
                    this.mHandler.removeCallbacks(this.mUpdateBackgroundForTopRowsAfterDelayRunnable);
                } else {
                    this.mHandler.postDelayed(this.mUpdateBackgroundForTopRowsAfterDelayRunnable, AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS);
                }
            } else {
                this.mSelectedPosition = selectedPosition;
                updateSelectedId();
            }
            if (this.mSelectFirstAppWhenRowSelected && selectedPosition == 1) {
                this.mHandler.removeCallbacks(this.mSelectFirstAppRunnable);
                if (Util.isAccessibilityEnabled(this.mContext)) {
                    this.mHandler.postDelayed(this.mSelectFirstAppRunnable, 50);
                } else {
                    this.mSelectFirstAppRunnable.run();
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$setSelectedPosition$7$HomeController(long copiedSelectedId) {
        this.mDataManager.hideEmptyChannel(copiedSelectedId);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public long getSelectedId() {
        return this.mSelectedId;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void updateSelectedId() {
        this.mSelectedId = getItemId(Math.min(this.mSelectedPosition, getItemCount() - 1));
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void onItemRemoved(int position) {
        int i;
        this.mEmptyChannelsHelper.onChannelsChange();
        boolean isFirstChannel = true;
        boolean isLastChannel = position == getItemCount() - 1;
        int i2 = this.mSelectedPosition;
        if (i2 > position) {
            this.mSelectedPosition = i2 - 1;
        }
        long oldSelectedId = this.mSelectedId;
        updateSelectedId();
        if (oldSelectedId != this.mSelectedId && ((i = this.mState) == 3 || i == 2)) {
            this.mState = 1;
        }
        if (this.mState == 1 && Util.isAccessibilityEnabled(this.mContext)) {
            this.mState = 0;
        }
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, getItemCount(), PAYLOAD_STATE);
        if (position != getNumberOfRowsAboveChannels()) {
            isFirstChannel = false;
        }
        if (isFirstChannel && !isLastChannel) {
            notifyItemChanged(position, PAYLOAD_CHANNEL_MOVE_ACTION);
        }
        if (isLastChannel && !isFirstChannel) {
            notifyItemChanged(position - 1, PAYLOAD_CHANNEL_MOVE_ACTION);
        }
    }

    /* access modifiers changed from: private */
    public int getAdapterPositionForChannelIndex(int channelIndex) {
        return getNumberOfRowsAboveChannels() + channelIndex;
    }

    /* access modifiers changed from: private */
    public int getChannelIndexForAdapterPosition(int position) {
        return position - getNumberOfRowsAboveChannels();
    }

    public int getItemCount() {
        return getChannelCount() + getNumberOfRowsAboveChannels() + 1;
    }

    public long getItemId(int position) {
        switch (getItemViewType(position)) {
            case 0:
                return -2;
            case 1:
                return -3;
            case 2:
                return -4;
            case 3:
            case 4:
            case 5:
                return this.mDataManager.getHomeChannel(getChannelIndexForAdapterPosition(position)).getId();
            case 6:
                return -5;
            default:
                return super.getItemId(position);
        }
    }

    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return 6;
        }
        if (position == 0) {
            return 0;
        }
        if (position == 1) {
            return 1;
        }
        if (position == 2 && this.mWatchNextVisible) {
            return 2;
        }
        HomeChannel homeChannel = this.mDataManager.getHomeChannel(getChannelIndexForAdapterPosition(position));
        if (homeChannel.isSponsored()) {
            return homeChannel.getSubtype() == 2 ? 5 : 4;
        }
        return 3;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    @NonNull
    public HomeRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup viewGroup = parent;
        int i = viewType;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        boolean z = false;
        if (i == 0) {
            HomeTopRowView v = (HomeTopRowView) inflater.inflate(C1188R.layout.home_top_row, viewGroup, false);
            v.setOnActionListener(this);
            v.setEventLogger(this.mEventLogger);
            v.setFocusChangeListener(this);
            NotificationsPanelController controller = new NotificationsPanelController(parent.getContext(), this.mEventLogger);
            v.setNotificationsTrayAdapter(new NotificationsTrayAdapter(parent.getContext(), this.mEventLogger, this.mNotifTrayCursor));
            v.setNotificationsPanelController(controller);
            return new HomeRowViewHolder(v);
        } else if (i == 1) {
            ChannelView channelView = (ChannelView) inflater.inflate(C1188R.layout.home_channel_row, viewGroup, false);
            channelView.setId(C1188R.C1191id.apps_row);
            FavoriteLaunchItemsRowController controller2 = new FavoriteLaunchItemsRowController(channelView, this.mEventLogger);
            controller2.setOnBackNotHandledListener(this);
            controller2.setOnHomeNotHandledListener(this);
            controller2.setHomeListStateProvider(this.mHomeListStateProvider);
            return new HomeRowViewHolder(controller2);
        } else if (i == 2) {
            WatchNextRowController controller3 = new WatchNextRowController((ChannelView) inflater.inflate(C1188R.layout.home_channel_row, viewGroup, false), this.mEventLogger);
            controller3.setOnProgramSelectedListener(this.mOnProgramSelectedListener);
            controller3.setOnBackNotHandledListener(this);
            controller3.setOnHomeNotHandledListener(this);
            controller3.setHomeListStateProvider(this.mHomeListStateProvider);
            return new HomeRowViewHolder(controller3);
        } else if (i == 6) {
            return new HomeRowViewHolder(new ConfigureChannelsRowController((ConfigureChannelsRowView) inflater.inflate(C1188R.layout.home_configure_channels_row, viewGroup, false)));
        } else {
            ChannelView channelView2 = (ChannelView) inflater.inflate(C1188R.layout.home_channel_row, viewGroup, false);
            ChannelEventLogger logger = new ChannelEventLogger();
            RequestManager requestManager = this.mChannelLogoRequestManager;
            ChannelOrderManager channelOrderManager = TvDataManager.getInstance(this.mContext).getChannelOrderManager();
            ChannelItemMetadataController channelItemMetadataController = new ChannelItemMetadataController(channelView2.getItemMetadataView());
            IntentLaunchDispatcher intentLaunchDispatcher = this.mIntentLauncher;
            boolean z2 = i == 4 || i == 5;
            if (i == 4) {
                z = true;
            }
            ChannelRowController controller4 = new ChannelRowController(channelView2, requestManager, logger, channelOrderManager, channelItemMetadataController, intentLaunchDispatcher, z2, z);
            controller4.setOnProgramSelectedListener(this.mOnProgramSelectedListener);
            controller4.setOnBackNotHandledListener(this);
            controller4.setOnHomeNotHandledListener(this);
            controller4.setHomeListStateProvider(this.mHomeListStateProvider);
            HomeRowViewHolder viewHolder = new HomeRowViewHolder(controller4);
            HomeRowViewHolder unused = logger.viewHolder = viewHolder;
            return viewHolder;
        }
    }

    public void onBindViewHolder(@NonNull HomeRowViewHolder holder, int position, @NonNull List<Object> payloads) {
        HomeRowViewHolder homeRowViewHolder = holder;
        int i = position;
        List<Object> list = payloads;
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
            return;
        }
        int rowType = getItemViewType(i);
        if (list.contains(PAYLOAD_CHANNEL_ITEM_METADATA)) {
            if (rowType == 3) {
                ((ChannelRowController) homeRowViewHolder.homeRow).bindItemMetadata();
            } else if (rowType == 2) {
                ((WatchNextRowController) homeRowViewHolder.homeRow).bindItemMetadata();
            }
        }
        if (rowType == 2 && (list.contains(PAYLOAD_WATCH_NEXT_CARD_SELECTION_CHANGED) || list.contains(PAYLOAD_WATCH_NEXT_DATA_CHANGED))) {
            ((WatchNextRowController) homeRowViewHolder.homeRow).bindInfoAcknowledgedButton();
        }
        if (list.contains(PAYLOAD_STATE)) {
            if (rowType == 2) {
                WatchNextRowController controller = (WatchNextRowController) homeRowViewHolder.homeRow;
                controller.setState(getNonEmptyChannelState(i));
                this.mActiveWatchNextRowControllers.add(controller);
            } else if (rowType == 3 || rowType == 4 || rowType == 5) {
                ChannelRowController controller2 = (ChannelRowController) homeRowViewHolder.homeRow;
                long channelId = this.mDataManager.getHomeChannel(getChannelIndexForAdapterPosition(i)).getId();
                boolean isEmpty = this.mDataManager.isChannelEmpty(channelId);
                controller2.setState(getChannelState(i, isEmpty));
                if (isEmpty) {
                    this.mEmptyChannelsHelper.setChannelSelected(channelId, this.mSelectedPosition == i);
                }
                this.mActiveChannelRowControllers.add(controller2);
            } else if (rowType == 0) {
                ((HomeTopRowView) homeRowViewHolder.homeRow.getView()).setState(this.mSelectedPosition == i);
            } else {
                onBindViewHolder(holder, position);
            }
        }
        if (list.contains(PAYLOAD_FAST_SCROLLING)) {
            if (rowType == 1 || rowType == 2 || rowType == 3 || rowType == 4 || rowType == 5) {
                homeRowViewHolder.homeRow.setHomeIsFastScrolling(this.mFastScrollingManager.isFastScrollingEnabled());
            }
            if (rowType == 3 || rowType == 4 || rowType == 5) {
                ((ChannelRowController) homeRowViewHolder.homeRow).setState(getChannelState(i, this.mDataManager.isChannelEmpty(this.mDataManager.getHomeChannel(getChannelIndexForAdapterPosition(i)).getId())));
            }
        }
        if (list.contains(PAYLOAD_CHANNEL_MOVE_ACTION) && (rowType == 3 || rowType == 4 || rowType == 5)) {
            ((ChannelRowController) homeRowViewHolder.homeRow).bindChannelMoveAction();
        }
        if (list.contains(PAYLOAD_CHANNEL_LOGO_TITLE) && rowType == 3) {
            ((ChannelRowController) homeRowViewHolder.homeRow).bindChannelLogoTitle();
        }
        if (rowType == 0) {
            HomeTopRowView v = (HomeTopRowView) homeRowViewHolder.homeRow.getView();
            SearchView searchView = v.getSearchWidget();
            if (searchView != null) {
                if (list.contains(PAYLOAD_IDLE_STATE)) {
                    searchView.setIdleState(this.mIsIdle);
                }
                if (list.contains(PAYLOAD_ASSISTANT_ICON)) {
                    searchView.updateAssistantIcon(this.mAssistantIcon);
                }
                if (list.contains(PAYLOAD_ASSISTANT_SUGGESTIONS)) {
                    searchView.updateSearchSuggestions(this.mAssistantSuggestions);
                }
                if (list.contains(PAYLOAD_MIC_STATUS)) {
                    searchView.updateMicStatus(this.mMicStatus);
                }
                if (list.contains(PAYLOAD_HOTWORD_STATUS)) {
                    searchView.updateHotwordEnabled(this.mHotwordEnabled);
                }
            }
            if (list.contains(PAYLOAD_PARTNER_WIDGET_INFO)) {
                v.onPartnerWidgetUpdate(this.mPartnerWidgetInfo);
            }
            if (list.contains(PAYLOAD_NOTIF_COUNT_CURSOR)) {
                v.getNotificationsPanelController().updateNotificationsCount(this.mNotifCountCursor);
            }
            if (list.contains(PAYLOAD_NOTIF_TRAY_CURSOR)) {
                v.getNotificationsTrayAdapter().changeCursor(this.mNotifTrayCursor);
                v.updateNotificationsTrayVisibility();
            }
            if (list.contains(PAYLOAD_INPUT_ICON_VISIBILITY)) {
                v.updateInputIconVisibility(this.mInputIconVisible);
            }
        }
    }

    public void onBindViewHolder(@NonNull HomeRowViewHolder viewHolder, int position) {
        int rowType = getItemViewType(position);
        boolean z = false;
        boolean rowSelected = this.mSelectedPosition == position;
        if (rowType == 2) {
            WatchNextRowController controller = (WatchNextRowController) viewHolder.homeRow;
            int channelState = getNonEmptyChannelState(position);
            controller.bind(channelState);
            if (channelState == 0) {
                controller.bindItemMetadata();
            }
            controller.setHomeIsFastScrolling(this.mFastScrollingManager.isFastScrollingEnabled());
            this.mActiveWatchNextRowControllers.add(controller);
        } else if (rowType == 1) {
            FavoriteLaunchItemsRowController controller2 = (FavoriteLaunchItemsRowController) viewHolder.homeRow;
            controller2.bind(getNonEmptyChannelState(position));
            controller2.setHomeIsFastScrolling(this.mFastScrollingManager.isFastScrollingEnabled());
            if (this.mSelectFirstAppWhenRowSelected && position == this.mSelectedPosition) {
                controller2.setSelectedItemPosition(0);
                this.mSelectFirstAppWhenRowSelected = false;
            }
        } else if (rowType == 6) {
            ConfigureChannelsRowController controller3 = (ConfigureChannelsRowController) viewHolder.homeRow;
            int i = this.mState;
            if (getItemViewType(position - 1) == 1) {
                z = true;
            }
            controller3.bind(i, rowSelected, z);
        } else if (rowType == 3 || rowType == 4 || rowType == 5) {
            onBindChannel((ChannelRowController) viewHolder.homeRow, getChannelIndexForAdapterPosition(position), position);
        } else if (rowType == 0) {
            HomeTopRowView v = (HomeTopRowView) viewHolder.homeRow.getView();
            v.getNotificationsTrayAdapter().changeCursor(this.mNotifTrayCursor);
            v.getNotificationsPanelController().updateNotificationsCount(this.mNotifCountCursor);
            v.updateNotificationsTrayVisibility();
            v.setState(rowSelected);
            SearchView searchView = v.getSearchWidget();
            searchView.setIdleState(this.mIsIdle);
            searchView.updateAssistantIcon(this.mAssistantIcon);
            searchView.updateSearchSuggestions(this.mAssistantSuggestions);
            searchView.updateMicStatus(this.mMicStatus);
            searchView.updateHotwordEnabled(this.mHotwordEnabled);
            v.onPartnerWidgetUpdate(this.mPartnerWidgetInfo);
            v.updateInputIconVisibility(this.mInputIconVisible);
        }
    }

    private void onBindChannel(ChannelRowController controller, int channelIndex, int position) {
        HomeChannel channel = this.mDataManager.getHomeChannel(channelIndex);
        long channelId = channel.getId();
        boolean isEmpty = this.mDataManager.isChannelEmpty(channelId);
        int channelState = getChannelState(position, isEmpty);
        boolean z = true;
        controller.bind(channel, channelState, !this.mAddToWatchNextPackagesBlacklist.contains(channel.getPackageName()), !this.mRemoveProgramPackagesBlacklist.contains(channel.getPackageName()));
        if (channelState == 0) {
            controller.bindItemMetadata();
        }
        if (isEmpty) {
            EmptyChannelsHelper emptyChannelsHelper = this.mEmptyChannelsHelper;
            if (this.mSelectedPosition != position) {
                z = false;
            }
            emptyChannelsHelper.setChannelSelected(channelId, z);
        }
        controller.setHomeIsFastScrolling(this.mFastScrollingManager.isFastScrollingEnabled());
        this.mActiveChannelRowControllers.add(controller);
    }

    private int getChannelState(int position, boolean isEmpty) {
        int nonEmptyState = getNonEmptyChannelState(position);
        if (isEmpty) {
            return this.mEmptyChannelsHelper.getEmptyChannelState(nonEmptyState);
        }
        return nonEmptyState;
    }

    private int getNonEmptyChannelState(int position) {
        int channelViewState;
        int channelViewState2;
        boolean channelSelected = this.mSelectedPosition == position;
        int i = this.mState;
        if (i == 0) {
            int channelViewState3 = 6;
            if (this.mFastScrollingManager.isFastScrollingEnabled() && hasFastScrollingMode(position)) {
                if (!channelSelected) {
                    channelViewState3 = 7;
                }
                return channelViewState3;
            } else if (channelSelected) {
                return 0;
            } else {
                if (this.mSelectedPosition == 0 && ((position == 2 || position == 3) && getItemViewType(position) != 6)) {
                    return 4;
                }
                if (this.mSelectedPosition == 1 && ((position == 2 || position == 3) && getItemViewType(position) != 6)) {
                    return 5;
                }
                if (this.mSelectedPosition == getItemCount() - 1 && position == getItemCount() - 2) {
                    return 15;
                }
                int i2 = this.mSelectedPosition;
                if (position == i2 - 1 || (i2 == getItemCount() - 1 && position == getItemCount() - 3 && getItemViewType(position) != 0)) {
                    return 2;
                }
                if (position != this.mSelectedPosition + 1 || position <= 2) {
                    return 1;
                }
                return 3;
            }
        } else if (i != 1) {
            if (i == 2) {
                if (channelSelected) {
                    channelViewState = 11;
                } else {
                    channelViewState = 12;
                }
                return channelViewState;
            } else if (i != 3) {
                return 1;
            } else {
                if (channelSelected) {
                    channelViewState2 = 13;
                } else {
                    channelViewState2 = 14;
                }
                return channelViewState2;
            }
        } else if (channelSelected) {
            return 8;
        } else {
            if (this.mSelectedPosition != 0 || position <= 0 || position > 4) {
                return 9;
            }
            return 10;
        }
    }

    public void onViewRecycled(@NonNull HomeRowViewHolder holder) {
        super.onViewRecycled((RecyclerView.ViewHolder) holder);
        if (holder.homeRow instanceof ChannelRowController) {
            ChannelRowController channelRowController = (ChannelRowController) holder.homeRow;
            channelRowController.recycle();
            this.mActiveChannelRowControllers.remove(channelRowController);
        } else if (holder.homeRow instanceof WatchNextRowController) {
            WatchNextRowController watchNextRowController = (WatchNextRowController) holder.homeRow;
            watchNextRowController.recycle();
            this.mActiveWatchNextRowControllers.remove(watchNextRowController);
        } else if (holder.homeRow instanceof HomeTopRowView) {
            HomeTopRowView homeTopRowView = (HomeTopRowView) holder.homeRow;
            NotificationsPanelController notificationsPanelController = homeTopRowView.getNotificationsPanelController();
            homeTopRowView.getNotificationsTrayAdapter().changeCursor(null);
        }
    }

    public boolean onFailedToRecycleView(@NonNull HomeRowViewHolder holder) {
        String valueOf = String.valueOf(holder);
        String valueOf2 = String.valueOf(holder.homeRow);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 29 + String.valueOf(valueOf2).length());
        sb.append("onFailedToRecycleView: h=");
        sb.append(valueOf);
        sb.append(", r=");
        sb.append(valueOf2);
        Log.w(TAG, sb.toString());
        return super.onFailedToRecycleView((RecyclerView.ViewHolder) holder);
    }

    public void onShowInputs() {
        logClick(TvLauncherConstants.INPUTS_LAUNCH_BUTTON);
        InputsManagerUtil.launchInputsActivity(this.mContext);
    }

    public void onStartSettings() {
        logClick(TvLauncherConstants.SETTINGS_LAUNCH_BUTTON);
        try {
            this.mContext.startActivity(new Intent("android.settings.SETTINGS"));
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "Exception starting settings", e);
            Context context = this.mContext;
            Toast.makeText(context, context.getString(C1188R.string.app_unavailable), 0).show();
        }
    }

    private void logClick(VisualElementTag visualElementTag) {
        this.mEventLogger.log(new ClickEvent(null).setVisualElementTag(visualElementTag));
    }

    public void onLaunchItemsLoaded() {
        notifyItemRangeChanged(getNumberOfRowsAboveChannels(), getItemCount(), PAYLOAD_CHANNEL_LOGO_TITLE);
    }

    public void onLaunchItemsAddedOrUpdated(ArrayList<LaunchItem> arrayList) {
        notifyItemRangeChanged(getNumberOfRowsAboveChannels(), getItemCount(), PAYLOAD_CHANNEL_LOGO_TITLE);
    }

    public void onLaunchItemsRemoved(ArrayList<LaunchItem> arrayList) {
    }

    public void onEditModeItemOrderChange(ArrayList<LaunchItem> arrayList, boolean isGameItems, Pair<Integer, Integer> pair) {
    }

    public void onHomeTopRowFocusChanged() {
        this.mHandler.removeCallbacks(this.mNotifyTopRowStateChangedRunnable);
        if (this.mList.isComputingLayout()) {
            Log.w(TAG, "onHomeTopRowFocusChanged: still computing layout => scheduling");
            this.mHandler.post(this.mNotifyTopRowStateChangedRunnable);
            return;
        }
        this.mNotifyTopRowStateChangedRunnable.run();
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface RowType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    class HomeRowViewHolder extends RecyclerView.ViewHolder implements OnHomeStateChangeListener, OnHomeRowSelectedListener, OnHomeRowRemovedListener, FacetProvider {
        HomeRow homeRow;

        HomeRowViewHolder(HomeRow homeRow2) {
            super(homeRow2.getView());
            this.homeRow = homeRow2;
            homeRow2.setOnHomeStateChangeListener(this);
            homeRow2.setOnHomeRowSelectedListener(this);
            homeRow2.setOnHomeRowRemovedListener(this);
        }

        public void onHomeStateChange(int state) {
            HomeController.this.setState(state);
        }

        public void onHomeRowSelected(HomeRow homeRow2) {
            int newSelection = getAdapterPosition();
            if (newSelection != -1) {
                HomeController.this.setSelectedPosition(newSelection);
            }
        }

        public void onHomeRowRemoved(HomeRow homeRow2) {
            HomeController.this.onItemRemoved(getAdapterPosition());
        }

        public Object getFacet(Class<?> cls) {
            int itemCount = HomeController.this.getItemCount();
            int position = getAdapterPosition();
            if (position == -1) {
                return null;
            }
            int type = getItemViewType();
            if (type == 1) {
                if (HomeController.this.mNotifTrayCursor == null || HomeController.this.mNotifTrayCursor.getCount() <= 0) {
                    return null;
                }
                if (HomeController.this.mState == 0) {
                    return HomeController.this.mAppsRowDefaultWithTrayFacet;
                }
                return HomeController.this.mAppsRowZoomedOutWithTrayFacet;
            } else if (position == itemCount - 1 && type == 6) {
                return HomeController.this.mConfigureChannelsFacet;
            } else {
                if (position == itemCount - 2 && HomeController.this.mState != 0 && (type == 3 || type == 2 || type == 4 || type == 5)) {
                    return HomeController.this.mSecondToLastRowZoomedOutFacet;
                }
                if (position == itemCount - 3 && HomeController.this.mState != 0) {
                    return HomeController.this.mThirdToLastRowZoomedOutFacet;
                }
                if (type == 4 || type == 5) {
                    return HomeController.this.mSponsoredChannelFacet;
                }
                return null;
            }
        }
    }

    private class ChannelEventLogger implements EventLogger {
        /* access modifiers changed from: private */
        public HomeRowViewHolder viewHolder;

        private ChannelEventLogger() {
        }

        public void log(LogEvent event) {
            event.pushParentVisualElementTag(TvLauncherConstants.CHANNEL_ROW);
            event.setVisualElementRowIndex(HomeController.this.getChannelIndexForAdapterPosition(this.viewHolder.getAdapterPosition()));
            HomeController.this.mEventLogger.log(event);
        }
    }
}
