package com.google.android.tvlauncher.home;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.leanback.widget.VerticalGridView;

import com.bumptech.glide.Glide;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.MainBackHomeController;
import com.google.android.tvlauncher.TvlauncherLogEnum;
import com.google.android.tvlauncher.analytics.FragmentEventLogger;
import com.google.android.tvlauncher.analytics.LogEvent;
import com.google.android.tvlauncher.analytics.LogEventParameters;
import com.google.android.tvlauncher.appsview.data.LaunchItemsManager;
import com.google.android.tvlauncher.appsview.data.LaunchItemsManagerProvider;
import com.google.android.tvlauncher.data.FarfieldMicStatusLoader;
import com.google.android.tvlauncher.data.HotwordEnabledLoader;
import com.google.android.tvlauncher.data.PartnerWidgetInfoLoader;
import com.google.android.tvlauncher.data.TvDataManager;
import com.google.android.tvlauncher.data.TvSearchIconLoader;
import com.google.android.tvlauncher.data.TvSearchSuggestionsLoader;
import com.google.android.tvlauncher.home.operatorbackground.BitmapHomeBackgroundController;
import com.google.android.tvlauncher.inputs.InputsManagerUtil;
import com.google.android.tvlauncher.instantvideo.preload.InstantVideoPreloadManager;
import com.google.android.tvlauncher.instantvideo.preload.impl.ExoPlayerPreloaderManager;
import com.google.android.tvlauncher.instantvideo.preload.impl.TvPlayerPreloaderManager;
import com.google.android.tvlauncher.notifications.NotificationsContract;
import com.google.android.tvlauncher.notifications.NotificationsUtils;
import com.google.android.tvlauncher.notifications.TvNotification;
import com.google.android.tvlauncher.util.ExtendableTimer;
import com.google.android.tvlauncher.util.OemConfiguration;
import com.google.android.tvlauncher.util.Util;
import com.google.android.tvlauncher.widget.PartnerWidgetInfo;

import java.lang.ref.WeakReference;

public class HomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, LaunchItemsManager.SearchPackageChangeListener, OemConfiguration.OemConfigurationPackageChangeListener {
    private static final int CACHE_SIZE_PER_VIDEO = 2000000;
    private static final boolean DEBUG = false;
    private static final long DELAY_BETWEEN_USER_INTERACTION_LOGS = 14400000;
    private static final int DISK_CACHE_SIZE_MB = 100;
    private static final long INPUTS_REFRESH_DELAY_MS = 20000;
    private static final int LOADER_ID_FARFIELD_MIC_STATUS = 5;
    private static final int LOADER_ID_HOTWORD_ENABLED = 6;
    private static final int LOADER_ID_NOTIFICATION_COUNT = 1;
    private static final int LOADER_ID_PARTNER_WIDGET = 4;
    private static final int LOADER_ID_SEARCH_ICON = 2;
    private static final int LOADER_ID_SEARCH_SUGGESTIONS = 3;
    private static final int LOADER_ID_TRAY_NOTIFS = 0;
    private static final int NOTIFICATION_FORCE_RELOAD_MAX_TIMEOUT_MILLIS = 4000;
    private static final int NOTIFICATION_UPDATE_DELAY_MILLIS = 3000;
    private static final String TAG = "HomeFragment";
    private static final int USER_IDLE_MSG = 1;
    private static final int USER_INTERACTION_MSG = 2;
    private final FragmentEventLogger eventLogger = new FragmentEventLogger(this);
    private final Handler inputHandler = new Handler();
    private final ExtendableTimer loadNotifsTimeout = ExtendableTimer.obtain();
    private final InteractionHandler userInteractionHandler = new InteractionHandler();
    /* access modifiers changed from: private */
    public HomeController homeController;
    private final LoaderManager.LoaderCallbacks<Boolean> hotwordEnabledLoaderCallbacks = new LoaderManager.LoaderCallbacks<Boolean>() {
        public /* bridge */ /* synthetic */ void onLoadFinished(Loader loader, Object obj) {
            onLoadFinished((Loader<Boolean>) loader, (Boolean) obj);
        }

        public Loader<Boolean> onCreateLoader(int id, Bundle args) {
            return new HotwordEnabledLoader(HomeFragment.this.getContext());
        }

        public void onLoadFinished(Loader<Boolean> loader, Boolean data) {
            HomeFragment.this.homeController.onHotwordEnabledUpdated(data);
        }

        public void onLoaderReset(Loader<Boolean> loader) {
            HomeFragment.this.homeController.onHotwordEnabledUpdated(null);
        }
    };
    private final LoaderManager.LoaderCallbacks<Integer> micStatusLoaderCallbacks = new LoaderManager.LoaderCallbacks<Integer>() {
        public /* bridge */ /* synthetic */ void onLoadFinished(Loader loader, Object obj) {
            onLoadFinished((Loader<Integer>) loader, (Integer) obj);
        }

        public Loader<Integer> onCreateLoader(int id, Bundle args) {
            return new FarfieldMicStatusLoader(HomeFragment.this.getContext());
        }

        public void onLoadFinished(Loader<Integer> loader, Integer data) {
            HomeFragment.this.homeController.onMicStatusUpdated(data);
        }

        public void onLoaderReset(Loader<Integer> loader) {
            HomeFragment.this.homeController.onMicStatusUpdated(null);
        }
    };
    private final LoaderManager.LoaderCallbacks<PartnerWidgetInfo> partnerWidgetInfoLoaderCallbacks = new LoaderManager.LoaderCallbacks<PartnerWidgetInfo>() {
        public /* bridge */ /* synthetic */ void onLoadFinished(Loader loader, Object obj) {
            onLoadFinished((Loader<PartnerWidgetInfo>) loader, (PartnerWidgetInfo) obj);
        }

        public Loader<PartnerWidgetInfo> onCreateLoader(int id, Bundle args) {
            return new PartnerWidgetInfoLoader(HomeFragment.this.getContext());
        }

        public void onLoadFinished(Loader<PartnerWidgetInfo> loader, PartnerWidgetInfo data) {
            HomeFragment.this.homeController.onPartnerWidgetUpdate(data);
        }

        public void onLoaderReset(Loader<PartnerWidgetInfo> loader) {
            HomeFragment.this.homeController.onPartnerWidgetUpdate(null);
        }
    };
    private final LoaderManager.LoaderCallbacks<Drawable> searchIconCallbacks = new LoaderManager.LoaderCallbacks<Drawable>() {
        public /* bridge */ /* synthetic */ void onLoadFinished(Loader loader, Object obj) {
            onLoadFinished((Loader<Drawable>) loader, (Drawable) obj);
        }

        public Loader<Drawable> onCreateLoader(int id, Bundle args) {
            return new TvSearchIconLoader(HomeFragment.this.getContext());
        }

        public void onLoadFinished(Loader<Drawable> loader, Drawable data) {
            HomeFragment.this.homeController.onSearchIconUpdate(data);
        }

        public void onLoaderReset(Loader<Drawable> loader) {
            HomeFragment.this.homeController.onSearchIconUpdate(null);
        }
    };
    private final LoaderManager.LoaderCallbacks<String[]> searchSuggestionsCallbacks = new LoaderManager.LoaderCallbacks<String[]>() {
        public /* bridge */ /* synthetic */ void onLoadFinished(Loader loader, Object obj) {
            onLoadFinished((Loader<String[]>) loader, (String[]) obj);
        }

        public Loader<String[]> onCreateLoader(int id, Bundle args) {
            return new TvSearchSuggestionsLoader(HomeFragment.this.getContext());
        }

        public void onLoadFinished(Loader<String[]> loader, String[] data) {
            HomeFragment.this.homeController.onSearchSuggestionsUpdate(data);
        }

        public void onLoaderReset(Loader<String[]> loader) {
            HomeFragment.this.homeController.onSearchSuggestionsUpdate(null);
        }
    };
    /* access modifiers changed from: private */
    public boolean isIdle;
    private boolean delayNotifTrayUpdate = true;
    private ExoPlayerPreloaderManager exoPlayerPreloaderManager;
    private int idlePeriod;
    private InstantVideoPreloadManager instantVideoPreloadManager;
    private TvPlayerPreloaderManager tvPlayerPreloaderManager;
    private long userInteractionLoggedTimestamp;

    public /* bridge */ /* synthetic */ void onLoadFinished(Loader loader, Object obj) {
        onLoadFinished((Loader<Cursor>) loader, (Cursor) obj);
    }

    /* access modifiers changed from: private */
    /* renamed from: loadInputList */
    public void bridge$lambda$0$HomeFragment() {
        InputsManagerUtil.getInputsManager(getContext()).loadInputsIfNeeded();
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context ctx = getContext();
        this.instantVideoPreloadManager = InstantVideoPreloadManager.getInstance(getContext());
        this.exoPlayerPreloaderManager = new ExoPlayerPreloaderManager(ctx, 100, 2000000);
        this.instantVideoPreloadManager.registerPreloaderManager(this.exoPlayerPreloaderManager);
        this.tvPlayerPreloaderManager = new TvPlayerPreloaderManager(ctx);
        this.instantVideoPreloadManager.registerPreloaderManager(this.tvPlayerPreloaderManager);
        this.idlePeriod = getResources().getInteger(C1188R.integer.idle_period);
        LaunchItemsManagerProvider.getInstance(getContext()).addSearchPackageChangeListener(this);
        OemConfiguration.get(getContext()).addConfigurationPackageChangeListener(this);
        this.loadNotifsTimeout.setListener(new HomeFragment$$Lambda$0(this));
        this.loadNotifsTimeout.setTimeout(3000);
        this.loadNotifsTimeout.setMaximumTimeout(4000);
        this.inputHandler.postDelayed(new HomeFragment$$Lambda$1(this), INPUTS_REFRESH_DELAY_MS);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$onCreate$0$HomeFragment(ExtendableTimer timer) {
        this.delayNotifTrayUpdate = false;
        Loader<Cursor> trayLoader = getLoaderManager().getLoader(0);
        if (trayLoader != null && trayLoader.isStarted()) {
            getLoaderManager().getLoader(0).forceLoad();
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C1188R.layout.fragment_home, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VerticalGridView homeRowList = (VerticalGridView) view.findViewById(C1188R.C1191id.home_row_list);
        TvDataManager tvDataManager = TvDataManager.getInstance(getActivity());
        this.homeController = new HomeController(getActivity(), this.eventLogger, tvDataManager, new EmptyChannelsHelper(tvDataManager));
        this.homeController.setChannelLogoRequestManager(Glide.with(this));
        this.homeController.setList(homeRowList);
        String backgroundUri = OemConfiguration.get(getContext()).getHomeBackgroundUri();
        if (!Util.isOperatorTierDevice(view.getContext()) || backgroundUri == null) {
            this.homeController.setBackgroundController(new HomeBackgroundController(homeRowList));
        } else {
            new BitmapHomeBackgroundController(backgroundUri, homeRowList);
        }
        homeRowList.setAdapter(this.homeController);
        MainBackHomeController.getInstance().setOnBackPressedListener(this.homeController);
        MainBackHomeController.getInstance().setOnHomePressedListener(this.homeController);
        this.homeController.setOnBackNotHandledListener(MainBackHomeController.getInstance());
        getLoaderManager().initLoader(0, null, this);
        getLoaderManager().initLoader(1, null, this);
        getLoaderManager().initLoader(4, null, this.partnerWidgetInfoLoaderCallbacks);
        getLoaderManager().initLoader(5, null, this.micStatusLoaderCallbacks);
        getLoaderManager().initLoader(6, null, this.hotwordEnabledLoaderCallbacks);
        onSearchPackageChanged();
        this.homeController.updateInputIconVisibility(OemConfiguration.get(getContext()).shouldShowInputs());
    }

    public void onStart() {
        super.onStart();
        this.homeController.onStart();
        this.delayNotifTrayUpdate = true;
        this.loadNotifsTimeout.start();
    }

    public void onStop() {
        super.onStop();
        this.homeController.onStop();
        this.loadNotifsTimeout.cancel();
        this.delayNotifTrayUpdate = true;
    }

    public void onDestroy() {
        super.onDestroy();
        this.instantVideoPreloadManager.unregisterPreloaderManager(this.exoPlayerPreloaderManager);
        this.instantVideoPreloadManager.unregisterPreloaderManager(this.tvPlayerPreloaderManager);
        LaunchItemsManagerProvider.getInstance(getContext()).removeSearchPackageChangeListener(this);
        OemConfiguration.get(getContext()).removeConfigurationPackageChangeListener(this);
        this.inputHandler.removeCallbacksAndMessages(null);
    }

    public void onResume() {
        super.onResume();
        logHomeImpression();
        this.homeController.onResume();
        removeInteractionHandlerMessages();
        this.userInteractionHandler.sendEmptyMessageDelayed(1, (long) this.idlePeriod);
    }

    public void onPause() {
        super.onPause();
        this.homeController.onPause();
        removeInteractionHandlerMessages();
        this.homeController.onIdleStateChange(false);
    }

    public void onUserInteraction() {
        removeInteractionHandlerMessages();
        if (this.isIdle) {
            this.userInteractionHandler.sendEmptyMessage(2);
        }
        this.userInteractionHandler.sendEmptyMessageDelayed(1, (long) this.idlePeriod);
        long now = System.currentTimeMillis();
        if (now - this.userInteractionLoggedTimestamp > DELAY_BETWEEN_USER_INTERACTION_LOGS) {
            this.eventLogger.log(new LogEvent(TvlauncherLogEnum.TvLauncherEventCode.USER_ACTION).bypassUsageReportingOptOut());
            this.userInteractionLoggedTimestamp = now;
        }
    }

    private void removeInteractionHandlerMessages() {
        this.userInteractionHandler.removeMessages(1);
        this.userInteractionHandler.removeMessages(2);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == 1) {
            return new CursorLoader(getContext(), NotificationsContract.NOTIF_COUNT_CONTENT_URI, null, null, null, null);
        }
        return new CursorLoader(getContext(), NotificationsContract.TRAY_CONTENT_URI, TvNotification.PROJECTION, null, null, null);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int id = loader.getId();
        if (id != 0) {
            if (id == 1) {
                this.homeController.updatePanelNotificationsCount(data);
            }
        } else if (!this.delayNotifTrayUpdate || !NotificationsUtils.hasNowPlaying(data)) {
            this.delayNotifTrayUpdate = false;
            if (this.loadNotifsTimeout.isStarted()) {
                this.loadNotifsTimeout.cancel();
            }
            this.homeController.updateNotifications(NotificationsUtils.copyCursor(data));
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        int id = loader.getId();
        if (id == 0) {
            this.homeController.updateNotifications(null);
        } else if (id == 1) {
            this.homeController.updatePanelNotificationsCount(null);
        }
    }

    private void logHomeImpression() {
        LogEvent logEvent = new LogEvent(TvlauncherLogEnum.TvLauncherEventCode.OPEN_HOME);
        if (this.homeController.isWatchNextVisible()) {
            logEvent.expectParameters(LogEventParameters.SHOWN_CHANNEL_COUNT, LogEventParameters.NOTIFICATION_INDICATOR_TOTAL, LogEventParameters.TRAY_NOTIFICATION_COUNT, LogEventParameters.WATCH_NEXT);
        } else {
            logEvent.expectParameters(LogEventParameters.SHOWN_CHANNEL_COUNT, LogEventParameters.NOTIFICATION_INDICATOR_TOTAL, LogEventParameters.TRAY_NOTIFICATION_COUNT);
        }
        this.eventLogger.log(logEvent);
    }

    public void onSearchPackageChanged() {
        getLoaderManager().restartLoader(2, null, this.searchIconCallbacks);
        getLoaderManager().restartLoader(3, null, this.searchSuggestionsCallbacks);
    }

    public void onOemConfigurationPackageChanged() {
        getLoaderManager().restartLoader(4, null, this.partnerWidgetInfoLoaderCallbacks);
    }

    public void onOemConfigurationPackageRemoved() {
        this.homeController.onPartnerWidgetUpdate(null);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public HomeController getHomeController() {
        return this.homeController;
    }

    private static class InteractionHandler extends Handler {
        private final WeakReference<HomeFragment> homeFragment;

        private InteractionHandler(HomeFragment homeFragment2) {
            this.homeFragment = new WeakReference<>(homeFragment2);
        }

        public void handleMessage(Message msg) {
            HomeFragment fragment = this.homeFragment.get();
            if (fragment != null) {
                boolean z = true;
                if (msg.what != 1) {
                    z = false;
                }
                boolean unused = fragment.isIdle = z;
                fragment.homeController.onIdleStateChange(fragment.isIdle);
            }
        }
    }
}
