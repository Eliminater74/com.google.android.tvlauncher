package com.google.android.tvlauncher.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.tv.TvContract;
import android.media.tv.TvInputManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Log;
import android.util.LongSparseArray;
import com.google.android.tvlauncher.application.TvLauncherApplicationBase;
import com.google.android.tvlauncher.data.DataLoadingBackgroundTask;
import com.google.android.tvlauncher.data.DataSourceObserver;
import com.google.android.tvlauncher.home.WatchNextPrefs;
import com.google.android.tvlauncher.model.Channel;
import com.google.android.tvlauncher.model.ChannelPackage;
import com.google.android.tvlauncher.model.HomeChannel;
import com.google.android.tvlauncher.model.Program;
import com.google.android.tvlauncher.util.ChannelConfigurationInfo;
import com.google.android.tvlauncher.util.Executors;
import com.google.android.tvlauncher.util.GoogleConfiguration;
import com.google.android.tvlauncher.util.GoogleConfigurationManager;
import com.google.android.tvlauncher.util.OemConfiguration;
import com.google.android.tvrecommendations.shared.util.Constants;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class TvDataManager implements DataLoadingBackgroundTask.Callbacks {
    private static final int CHANNEL_PROGRAM_COUNT_BATCH_SIZE = 1000;
    private static final int COLUMN_INDEX_CONTENT_ID = 0;
    private static final int COLUMN_INDEX_PACKAGE_NAME = 1;
    private static final boolean DEBUG = false;
    private static final LongSparseArray<ChannelConfigurationInfo> EMPTY_PINNED_CHANNEL_CONFIG = new LongSparseArray<>(0);
    public static final String ENABLE_PREVIEW_AUDIO_KEY = "enable_preview_audio_key";
    public static final String ENABLE_PREVIEW_VIDEO_KEY = "show_preview_video_key";
    private static final int FIRST_PROGRAMS_CHANNELS_ALWAYS_CACHED = 5;
    private static final String HOME_CHANNELS_SELECTION = "browsable=1 AND type='TYPE_PREVIEW'";
    private static final String KEY_BOOT_COUNT = "boot_count";
    private static final String KEY_LIVE_TV_CHANNEL_ID = "live_tv_channel_id";
    private static final String KEY_NON_EMPTY_OLD_HOME_CHANNEL_IDS = "non_empty_old_home_channel_ids";
    private static final long LIVE_TV_CHANNEL_NO_ID = -1;
    private static final String LIVE_TV_CHANNEL_PREF_FILE_NAME = "com.google.android.tvlauncher.data.TvDataManager.LIVE_TV_CHANNEL_PREF";
    private static final int MAX_PROGRAMS_CHANNELS_CACHED = 15;
    private static final int MAX_WATCH_NEXT_PROGRAMS = 1000;
    private static final String OLD_CHANNEL_IDS_SEPARATOR = ",";
    private static final String PACKAGE_CHANNELS_SELECTION = "type='TYPE_PREVIEW'";
    public static final String PREVIEW_MEDIA_PREF_FILE_NAME = "com.google.android.tvlauncher.data.TvDataManager.PREVIEW_VIDEO_PREF_FILE_NAME";
    private static final String PROGRAMS_BY_CHANNEL_ID_SELECTION = "channel_id=? AND browsable=1";
    private static final String PROMO_CHANNEL_SELECTION = "package_name=?";
    public static Provider PROVIDER = TvDataManager$$Lambda$2.$instance;
    private static final long SPONSORED_GOOGLE_CHANNEL_NO_ID = -1;
    private static final int SPONSORED_GOOGLE_CHANNEL_NO_OOB_POSITION = -1;
    private static final String TAG = "TvDataManager";
    private static final int TASK_HOME_CHANNELS = 0;
    private static final int TASK_HOME_CHANNEL_PROGRAMS = 1;
    private static final int TASK_PACKAGE_CHANNELS = 2;
    private static final int TASK_PROMO_CHANNEL = 3;
    private static final int TASK_WATCH_NEXT_CACHE = 5;
    private static final int TASK_WATCH_NEXT_PROGRAMS = 4;
    private static final String TV_DATA_MANAGER_PREF_FILE_NAME = "com.google.android.tvlauncher.data.TvDataManager.PREF";
    private static final String[] WATCH_NEXT_CACHE_PROJECTION = {"content_id", "package_name"};
    public static final long WATCH_NEXT_REQUERY_INTERVAL_MILLIS = 600000;
    private static final String WATCH_NEXT_SELECTION = "browsable=1 AND last_engagement_time_utc_millis<=?";
    private static int sChannelProgramCountBatchSize = 1000;
    @SuppressLint({"StaticFieldLeak"})
    private static TvDataManager sInstance;
    /* access modifiers changed from: private */
    @SuppressLint({"UseSparseArrays"})
    public Map<Long, HomeChannel> mBrowsableChannels = new HashMap();
    private Queue<Long> mCachedProgramsChannelOrder = new ArrayDeque();
    /* access modifiers changed from: private */
    @SuppressLint({"UseSparseArrays"})
    public Map<Long, Uri> mChannelLogoUris = new HashMap();
    /* access modifiers changed from: private */
    public ChannelOrderManager mChannelOrderManager;
    /* access modifiers changed from: private */
    @SuppressLint({"UseSparseArrays"})
    public Map<Long, ProgramsDataBuffer> mChannelPrograms = new HashMap();
    /* access modifiers changed from: private */
    @SuppressLint({"UseSparseArrays"})
    public Map<Long, List<ChannelProgramsObserver>> mChannelProgramsObservers = new HashMap();
    /* access modifiers changed from: private */
    public final Context mContext;
    private final DataSourceObserver mDataSourceObserver;
    private final GoogleConfigurationManager mGoogleConfigurationManager;
    private final Handler mHandler = new Handler();
    /* access modifiers changed from: private */
    public HashSet<Long> mHomeChannelIds = new HashSet<>();
    @SuppressLint({"UseSparseArrays"})
    private Map<Long, DataLoadingBackgroundTask> mHomeChannelProgramsBackgroundTasks = new HashMap();
    /* access modifiers changed from: private */
    public List<HomeChannel> mHomeChannels;
    private DataLoadingBackgroundTask mHomeChannelsBackgroundTask;
    /* access modifiers changed from: private */
    public List<HomeChannelsObserver> mHomeChannelsObservers = new LinkedList();
    /* access modifiers changed from: private */
    public boolean mHomeChannelsStale = true;
    private boolean mIsFirstLaunchAfterBoot = true;
    private long mLiveTvChannelId;
    private SharedPreferences mLiveTvChannelPref;
    /* access modifiers changed from: private */
    public Set<Long> mNonEmptyHomeChannelIds;
    private Map<String, List<Channel>> mPackageChannels;
    private DataLoadingBackgroundTask mPackageChannelsBackgroundTask;
    private List<PackageChannelsObserver> mPackageChannelsObservers = new LinkedList();
    private List<ChannelPackage> mPackagesWithChannels;
    private List<PackagesWithChannelsObserver> mPackagesWithChannelsObservers = new LinkedList();
    private PinnedChannelOrderManager mPinnedChannelOrderManager = new PinnedChannelOrderManager();
    /* access modifiers changed from: private */
    @SuppressLint({"UseSparseArrays"})
    public Map<Long, Long> mProgramChannelIds = Collections.synchronizedMap(new HashMap());
    private Channel mPromoChannel;
    private DataLoadingBackgroundTask mPromoChannelBackgroundTask;
    private boolean mPromoChannelLoaded;
    /* access modifiers changed from: private */
    public List<PromoChannelObserver> mPromoChannelObservers = new LinkedList();
    /* access modifiers changed from: private */
    public Set<Long> mStaleProgramsChannels = new HashSet();
    private SharedPreferences mTvDataManagerPref;
    private DataLoadingBackgroundTask mWatchNextCacheBackgroundTask;
    private Set<String> mWatchNextContentIdsCache = new HashSet();
    private final SharedPreferences.OnSharedPreferenceChangeListener mWatchNextPrefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.startsWith(WatchNextPrefs.WATCH_NEXT_PACKAGE_KEY_PREFIX)) {
                TvDataManager tvDataManager = TvDataManager.this;
                String unused = tvDataManager.mWatchNextSelection = tvDataManager.buildWatchNextSelection(sharedPreferences);
                TvDataManager.this.loadWatchNextProgramData();
            }
        }
    };
    private WatchNextProgramsDataBuffer mWatchNextPrograms;
    private DataLoadingBackgroundTask mWatchNextProgramsBackgroundTask;
    /* access modifiers changed from: private */
    public List<WatchNextProgramsObserver> mWatchNextProgramsObservers = new LinkedList();
    /* access modifiers changed from: private */
    public boolean mWatchNextProgramsStale = true;
    /* access modifiers changed from: private */
    public String mWatchNextSelection;

    public interface Provider {
        TvDataManager get(Context context);
    }

    @VisibleForTesting
    static void setChannelProgramCountBatchSize(int channelProgramCountBatchSize) {
        sChannelProgramCountBatchSize = channelProgramCountBatchSize;
    }

    public static TvDataManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TvDataManager(context, ((TvLauncherApplicationBase) context.getApplicationContext()).getGoogleConfigurationManager());
        }
        return sInstance;
    }

    @VisibleForTesting
    TvDataManager(Context context, GoogleConfigurationManager googleConfigurationManager) {
        this.mContext = context.getApplicationContext();
        this.mGoogleConfigurationManager = googleConfigurationManager;
        this.mDataSourceObserver = new DataSourceObserver(this.mContext, new DataSourceObserverCallbacks());
        SharedPreferences watchNextPreferences = context.getSharedPreferences(WatchNextPrefs.WATCH_NEXT_PREF_FILE_NAME, 0);
        watchNextPreferences.registerOnSharedPreferenceChangeListener(this.mWatchNextPrefListener);
        this.mWatchNextSelection = buildWatchNextSelection(watchNextPreferences);
        loadAllWatchNextProgramDataIntoCache();
        this.mLiveTvChannelPref = this.mContext.getSharedPreferences(LIVE_TV_CHANNEL_PREF_FILE_NAME, 0);
        this.mLiveTvChannelId = this.mLiveTvChannelPref.getLong(KEY_LIVE_TV_CHANNEL_ID, -1);
        this.mTvDataManagerPref = this.mContext.getSharedPreferences(TV_DATA_MANAGER_PREF_FILE_NAME, 0);
        String allOldHomeChannelIds = this.mTvDataManagerPref.getString(KEY_NON_EMPTY_OLD_HOME_CHANNEL_IDS, null);
        if (allOldHomeChannelIds != null) {
            for (String homeChannelIdStr : allOldHomeChannelIds.split(OLD_CHANNEL_IDS_SEPARATOR)) {
                try {
                    this.mHomeChannelIds.add(Long.valueOf(Long.parseLong(homeChannelIdStr)));
                } catch (NumberFormatException e) {
                    StringBuilder sb = new StringBuilder(String.valueOf(homeChannelIdStr).length() + 61 + String.valueOf(allOldHomeChannelIds).length());
                    sb.append("Invalid channel ID: [");
                    sb.append(homeChannelIdStr);
                    sb.append("] in old home channel ids shared pref [");
                    sb.append(allOldHomeChannelIds);
                    sb.append("]");
                    Log.e(TAG, sb.toString());
                }
            }
        }
        this.mNonEmptyHomeChannelIds = (Set) this.mHomeChannelIds.clone();
    }

    /* access modifiers changed from: private */
    public void saveNonEmptyHomeChannelIdsToSharedPref() {
        SharedPreferences.Editor editor = this.mTvDataManagerPref.edit();
        if (this.mNonEmptyHomeChannelIds.size() > 0) {
            StringBuilder idsString = new StringBuilder(128);
            for (Long homeChannelId : this.mNonEmptyHomeChannelIds) {
                idsString.append(homeChannelId);
                idsString.append(OLD_CHANNEL_IDS_SEPARATOR);
            }
            editor.putString(KEY_NON_EMPTY_OLD_HOME_CHANNEL_IDS, idsString.toString());
        } else {
            editor.putString(KEY_NON_EMPTY_OLD_HOME_CHANNEL_IDS, "");
        }
        editor.apply();
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void setChannelOrderManager(ChannelOrderManager channelOrderManager) {
        this.mChannelOrderManager = channelOrderManager;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void setPinnedChannelOrderManager(PinnedChannelOrderManager pinnedChannelOrderManager) {
        this.mPinnedChannelOrderManager = pinnedChannelOrderManager;
    }

    private void logObserversState() {
        int channelsObserved = this.mChannelProgramsObservers.size();
        int channelObservers = 0;
        for (List<ChannelProgramsObserver> observers : this.mChannelProgramsObservers.values()) {
            channelObservers += observers.size();
        }
        int size = this.mHomeChannelsObservers.size();
        int size2 = this.mPackagesWithChannelsObservers.size();
        int size3 = this.mPackageChannelsObservers.size();
        int size4 = this.mPromoChannelObservers.size();
        int size5 = this.mWatchNextProgramsObservers.size();
        StringBuilder sb = new StringBuilder((int) ClientAnalytics.LogRequest.LogSource.CRONET_FIREBALL_VALUE);
        sb.append("Observers: \nmHomeChannelsObservers.size()=");
        sb.append(size);
        sb.append("\nmChannelProgramsObservers.size()=");
        sb.append(channelsObserved);
        sb.append(" (total: ");
        sb.append(channelObservers);
        sb.append(")\nmPackagesWithChannelsObservers.size()=");
        sb.append(size2);
        sb.append("\nmPackageChannelsObservers.size()=");
        sb.append(size3);
        sb.append("\nmPromoChannelObservers.size()=");
        sb.append(size4);
        sb.append("\nmWatchNextProgramsObservers.size()=");
        sb.append(size5);
        Log.d(TAG, sb.toString());
    }

    private void logCachesState() {
        List<HomeChannel> list = this.mHomeChannels;
        Object obj = "null";
        String valueOf = String.valueOf(list != null ? Integer.valueOf(list.size()) : obj);
        Map<Long, ProgramsDataBuffer> map = this.mChannelPrograms;
        String valueOf2 = String.valueOf(map != null ? Integer.valueOf(map.size()) : obj);
        List<ChannelPackage> list2 = this.mPackagesWithChannels;
        String valueOf3 = String.valueOf(list2 != null ? Integer.valueOf(list2.size()) : obj);
        Map<String, List<Channel>> map2 = this.mPackageChannels;
        String valueOf4 = String.valueOf(map2 != null ? Integer.valueOf(map2.size()) : obj);
        String valueOf5 = String.valueOf(this.mCachedProgramsChannelOrder);
        String valueOf6 = String.valueOf(this.mStaleProgramsChannels);
        WatchNextProgramsDataBuffer watchNextProgramsDataBuffer = this.mWatchNextPrograms;
        if (watchNextProgramsDataBuffer != null) {
            obj = Integer.valueOf(watchNextProgramsDataBuffer.getCount());
        }
        String valueOf7 = String.valueOf(obj);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + ClientAnalytics.LogRequest.LogSource.YT_MUSIC_ANDROID_PRIMES_VALUE + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length() + String.valueOf(valueOf4).length() + String.valueOf(valueOf5).length() + String.valueOf(valueOf6).length() + String.valueOf(valueOf7).length());
        sb.append("Cache: \nmHomeChannels=");
        sb.append(valueOf);
        sb.append("\nmChannelPrograms=");
        sb.append(valueOf2);
        sb.append("\nmPackagesWithChannels=");
        sb.append(valueOf3);
        sb.append("\nmPackageChannels=");
        sb.append(valueOf4);
        sb.append("\nmCachedProgramsChannelOrder: ");
        sb.append(valueOf5);
        sb.append("\nmStaleProgramsChannels: ");
        sb.append(valueOf6);
        sb.append("\nmWatchNextPrograms: ");
        sb.append(valueOf7);
        Log.d(TAG, sb.toString());
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public DataSourceObserver getDataSourceObserver() {
        return this.mDataSourceObserver;
    }

    private void registerDataSourceObserver() {
        this.mDataSourceObserver.register();
    }

    private void unregisterDataSourceObserverIfNoObservers() {
        if (this.mHomeChannelsObservers.size() == 0 && this.mChannelProgramsObservers.size() == 0 && this.mPackagesWithChannelsObservers.size() == 0 && this.mPackageChannelsObservers.size() == 0 && this.mPromoChannelObservers.size() == 0 && this.mWatchNextProgramsObservers.size() == 0) {
            this.mHomeChannelsStale = true;
            this.mChannelLogoUris.clear();
            this.mStaleProgramsChannels.addAll(this.mChannelPrograms.keySet());
            this.mWatchNextProgramsStale = true;
            this.mDataSourceObserver.unregister();
        }
    }

    public void registerHomeChannelsObserver(HomeChannelsObserver observer) {
        if (!this.mHomeChannelsObservers.contains(observer)) {
            this.mHomeChannelsObservers.add(observer);
        }
        registerDataSourceObserver();
    }

    public void unregisterHomeChannelsObserver(HomeChannelsObserver observer) {
        DataLoadingBackgroundTask dataLoadingBackgroundTask = this.mHomeChannelsBackgroundTask;
        if (dataLoadingBackgroundTask != null) {
            dataLoadingBackgroundTask.cancel();
            this.mHomeChannelsBackgroundTask = null;
        }
        this.mHomeChannelsObservers.remove(observer);
        unregisterDataSourceObserverIfNoObservers();
    }

    public void registerChannelProgramsObserver(long channelId, ChannelProgramsObserver observer) {
        addToMultimap(this.mChannelProgramsObservers, Long.valueOf(channelId), observer);
        registerDataSourceObserver();
    }

    public void unregisterChannelProgramsObserver(long channelId, ChannelProgramsObserver observer) {
        removeFromMultimap(this.mChannelProgramsObservers, Long.valueOf(channelId), observer);
        unregisterDataSourceObserverIfNoObservers();
    }

    public void registerPackagesWithChannelsObserver(PackagesWithChannelsObserver observer) {
        if (!this.mPackagesWithChannelsObservers.contains(observer)) {
            this.mPackagesWithChannelsObservers.add(observer);
        }
        registerDataSourceObserver();
    }

    public void unregisterPackagesWithChannelsObserver(PackagesWithChannelsObserver observer) {
        this.mPackagesWithChannelsObservers.remove(observer);
        clearPackageChannelsCacheAndCancelTasksIfNoObservers();
        unregisterDataSourceObserverIfNoObservers();
    }

    public void registerPackageChannelsObserver(PackageChannelsObserver observer) {
        if (!this.mPackageChannelsObservers.contains(observer)) {
            this.mPackageChannelsObservers.add(observer);
        }
        registerDataSourceObserver();
    }

    public void unregisterPackageChannelsObserver(PackageChannelsObserver observer) {
        this.mPackageChannelsObservers.remove(observer);
        clearPackageChannelsCacheAndCancelTasksIfNoObservers();
        unregisterDataSourceObserverIfNoObservers();
    }

    private void clearPackageChannelsCacheAndCancelTasksIfNoObservers() {
        if (this.mPackagesWithChannelsObservers.size() == 0 && this.mPackageChannelsObservers.size() == 0) {
            DataLoadingBackgroundTask dataLoadingBackgroundTask = this.mPackageChannelsBackgroundTask;
            if (dataLoadingBackgroundTask != null) {
                dataLoadingBackgroundTask.cancel();
                this.mPackageChannelsBackgroundTask = null;
            }
            this.mPackagesWithChannels = null;
            this.mPackageChannels = null;
        }
    }

    public void registerPromoChannelObserver(PromoChannelObserver observer) {
        if (!this.mPromoChannelObservers.contains(observer)) {
            this.mPromoChannelObservers.add(observer);
        }
        registerDataSourceObserver();
    }

    public void unregisterPromoChannelObserver(PromoChannelObserver observer) {
        DataLoadingBackgroundTask dataLoadingBackgroundTask = this.mPromoChannelBackgroundTask;
        if (dataLoadingBackgroundTask != null) {
            dataLoadingBackgroundTask.cancel();
            this.mPromoChannelBackgroundTask = null;
        }
        this.mPromoChannelObservers.remove(observer);
        if (this.mPromoChannelObservers.size() == 0) {
            this.mPromoChannel = null;
            this.mPromoChannelLoaded = false;
        }
        unregisterDataSourceObserverIfNoObservers();
    }

    public void registerWatchNextProgramsObserver(WatchNextProgramsObserver observer) {
        if (!this.mWatchNextProgramsObservers.contains(observer)) {
            this.mWatchNextProgramsObservers.add(observer);
        }
        registerDataSourceObserver();
    }

    public void unregisterWatchNextProgramsObserver(WatchNextProgramsObserver observer) {
        DataLoadingBackgroundTask dataLoadingBackgroundTask = this.mWatchNextProgramsBackgroundTask;
        if (dataLoadingBackgroundTask != null) {
            dataLoadingBackgroundTask.cancel();
            this.mWatchNextProgramsBackgroundTask = null;
        }
        this.mWatchNextProgramsObservers.remove(observer);
        unregisterDataSourceObserverIfNoObservers();
    }

    public boolean isInWatchNext(String contentId, String packageName) {
        if (contentId == null || packageName == null) {
            return false;
        }
        return this.mWatchNextContentIdsCache.contains(createKeyFor(contentId, packageName));
    }

    private String createKeyFor(String contentId, String packageName) {
        StringBuilder sb = new StringBuilder(String.valueOf(contentId).length() + 1 + String.valueOf(packageName).length());
        sb.append(contentId);
        sb.append("-");
        sb.append(packageName);
        return sb.toString();
    }

    private HashSet<String> extractWatchNextCache(Cursor cursor) {
        HashSet<String> contentIds = new HashSet<>();
        if (cursor == null || cursor.getCount() <= 0) {
            return contentIds;
        }
        cursor.moveToFirst();
        do {
            contentIds.add(createKeyFor(cursor.getString(0), cursor.getString(1)));
        } while (cursor.moveToNext());
        return contentIds;
    }

    public void refreshWatchNextOffset() {
        WatchNextProgramsDataBuffer watchNextProgramsDataBuffer = this.mWatchNextPrograms;
        if (watchNextProgramsDataBuffer != null && watchNextProgramsDataBuffer.refresh()) {
            notifyWatchNextProgramsChange();
        }
    }

    public void removeProgramFromWatchNextCache(String contentId, String packageName) {
        if (contentId != null && packageName != null) {
            this.mWatchNextContentIdsCache.remove(createKeyFor(contentId, packageName));
        }
    }

    public void addProgramToWatchNextCache(String contentId, String packageName) {
        if (contentId != null && packageName != null) {
            this.mWatchNextContentIdsCache.add(createKeyFor(contentId, packageName));
        }
    }

    private static <K, V> void addToMultimap(Map<K, List<V>> multimap, K key, V item) {
        List<V> list = multimap.get(key);
        if (list == null) {
            List<V> list2 = new LinkedList<>();
            list2.add(item);
            multimap.put(key, list2);
        } else if (!list.contains(item)) {
            list.add(item);
        }
    }

    private static <K, V> void removeFromMultimap(Map<K, List<V>> multimap, K key, V item) {
        List<V> list = multimap.get(key);
        if (list != null) {
            list.remove(item);
            if (list.size() == 0) {
                multimap.remove(key);
            }
        }
    }

    /* access modifiers changed from: private */
    public void notifyHomeChannelsChange() {
        for (HomeChannelsObserver observer : this.mHomeChannelsObservers) {
            observer.onChannelsChange();
        }
    }

    private void notifyEmptyHomeChannelRemoved(int channelIndex) {
        for (HomeChannelsObserver observer : this.mHomeChannelsObservers) {
            observer.onEmptyChannelRemove(channelIndex);
        }
    }

    /* access modifiers changed from: private */
    public void notifyHomeChannelEmptyStatusChanged(int channelIndex) {
        for (HomeChannelsObserver observer : this.mHomeChannelsObservers) {
            observer.onChannelEmptyStatusChange(channelIndex);
        }
    }

    private void notifyChannelProgramsChange(long channelId) {
        List<ChannelProgramsObserver> observers = this.mChannelProgramsObservers.get(Long.valueOf(channelId));
        if (observers != null) {
            for (ChannelProgramsObserver observer : observers) {
                observer.onProgramsChange(channelId);
            }
        }
    }

    private void notifyPackagesWithChannelsChange() {
        for (PackagesWithChannelsObserver observer : this.mPackagesWithChannelsObservers) {
            observer.onPackagesChange();
        }
    }

    private void notifyPackageChannelsChange() {
        for (PackageChannelsObserver observer : this.mPackageChannelsObservers) {
            observer.onChannelsChange();
        }
    }

    private void notifyPromoChannelChange() {
        for (PromoChannelObserver observer : this.mPromoChannelObservers) {
            observer.onChannelChange();
        }
    }

    private void notifyWatchNextProgramsChange() {
        for (WatchNextProgramsObserver observer : this.mWatchNextProgramsObservers) {
            observer.onProgramsChange();
        }
    }

    public boolean isHomeChannelDataLoaded() {
        return this.mHomeChannels != null;
    }

    public boolean isHomeChannelDataStale() {
        return this.mHomeChannelsStale;
    }

    public int getHomeChannelCount() {
        List<HomeChannel> list = this.mHomeChannels;
        if (list != null) {
            return list.size();
        }
        return -1;
    }

    public HomeChannel getHomeChannel(int position) {
        return this.mHomeChannels.get(position);
    }

    public boolean isChannelEmpty(long channelId) {
        return !this.mNonEmptyHomeChannelIds.contains(Long.valueOf(channelId));
    }

    public boolean isHomeChannel(long channelId) {
        return this.mHomeChannelIds.contains(Long.valueOf(channelId));
    }

    public void loadHomeChannelData() {
        loadHomeChannelDataInternal();
    }

    public void removeHomeChannel(long channelId) {
        Integer position = this.mChannelOrderManager.getChannelPosition(channelId);
        if (position != null) {
            this.mHomeChannels.remove(position.intValue());
            this.mHomeChannelIds.remove(Long.valueOf(channelId));
            this.mBrowsableChannels.remove(Long.valueOf(channelId));
        }
        setChannelBrowsable(channelId, false, false);
    }

    public void hideEmptyChannel(long channelId) {
        Integer position = this.mChannelOrderManager.getChannelPosition(channelId);
        if (position != null) {
            this.mHomeChannelIds.remove(Long.valueOf(channelId));
            this.mChannelOrderManager.onEmptyChannelHidden(this.mHomeChannels.remove(position.intValue()));
            notifyEmptyHomeChannelRemoved(position.intValue());
        }
    }

    public Uri getChannelLogoUri(Long channelId) {
        Uri uri = this.mChannelLogoUris.get(channelId);
        if (uri != null) {
            return uri;
        }
        Uri uri2 = TvContract.buildChannelLogoUri(channelId.longValue()).buildUpon().appendQueryParameter("t", String.valueOf(System.currentTimeMillis())).build();
        this.mChannelLogoUris.put(channelId, uri2);
        return uri2;
    }

    public boolean isProgramDataLoaded(long channelId) {
        return this.mChannelPrograms.containsKey(Long.valueOf(channelId));
    }

    public boolean isProgramDataStale(long channelId) {
        return this.mStaleProgramsChannels.contains(Long.valueOf(channelId));
    }

    public int getProgramCount(long channelId) {
        if (this.mChannelPrograms.containsKey(Long.valueOf(channelId))) {
            return this.mChannelPrograms.get(Long.valueOf(channelId)).getCount();
        }
        return -1;
    }

    public Program getProgram(long channelId, int position) {
        if (position >= 0) {
            ProgramsDataBuffer programs = this.mChannelPrograms.get(Long.valueOf(channelId));
            if (programs != null && position >= programs.getCount()) {
                StringBuilder sb = new StringBuilder(56);
                sb.append("Position [");
                sb.append(position);
                sb.append("] is out of bounds [0, ");
                sb.append(programs.getCount() - 1);
                sb.append("]");
                throw new IllegalArgumentException(sb.toString());
            } else if (programs != null) {
                return programs.get(position);
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException("Position must be positive");
        }
    }

    public void loadProgramData(long channelId) {
        loadHomeChannelProgramData(channelId);
    }

    public void pruneChannelProgramsCache() {
        Iterator<Map.Entry<Long, ProgramsDataBuffer>> it = this.mChannelPrograms.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Long, ProgramsDataBuffer> entry = it.next();
            Long channelId = (Long) entry.getKey();
            if (!this.mChannelProgramsObservers.containsKey(channelId) && !areChannelProgramsAlwaysCached(channelId)) {
                it.remove();
                cleanupAfterChannelProgramsRemovalFromCache((ProgramsDataBuffer) entry.getValue(), channelId);
            }
        }
    }

    private void cleanupAfterChannelProgramsRemovalFromCache(ProgramsDataBuffer buffer, Long channelId) {
        if (buffer != null) {
            buffer.release();
        }
        this.mStaleProgramsChannels.remove(channelId);
        this.mCachedProgramsChannelOrder.remove(channelId);
    }

    /* access modifiers changed from: private */
    public boolean areChannelProgramsAlwaysCached(Long channelId) {
        Integer position;
        ChannelOrderManager channelOrderManager = this.mChannelOrderManager;
        if (channelOrderManager == null || (position = channelOrderManager.getChannelPosition(channelId.longValue())) == null || position.intValue() >= 5) {
            return false;
        }
        return true;
    }

    public boolean isPackagesWithChannelsDataLoaded() {
        return this.mPackagesWithChannels != null;
    }

    public List<ChannelPackage> getPackagesWithChannels() {
        return this.mPackagesWithChannels;
    }

    public void loadPackagesWithChannelsData() {
        loadPackageChannelsData();
    }

    public boolean isPackageChannelDataLoaded(String packageName) {
        Map<String, List<Channel>> map = this.mPackageChannels;
        return map != null && map.containsKey(packageName);
    }

    public List<Channel> getPackageChannels(String packageName) {
        return this.mPackageChannels.get(packageName);
    }

    public void loadPackageChannelsData(String packageName) {
        loadPackageChannelsData();
    }

    private boolean isPromoChannelLoaded() {
        return this.mPromoChannelLoaded;
    }

    public Channel getPromoChannel() {
        return this.mPromoChannel;
    }

    public void loadPromoChannel() {
        loadPromoChannelData();
    }

    public boolean isWatchNextProgramsDataLoaded() {
        return this.mWatchNextPrograms != null;
    }

    public boolean isWatchNextProgramsDataStale() {
        return this.mWatchNextProgramsStale;
    }

    public Program getWatchNextProgram(int position) {
        if (position >= 0) {
            WatchNextProgramsDataBuffer watchNextProgramsDataBuffer = this.mWatchNextPrograms;
            if (watchNextProgramsDataBuffer == null || position < watchNextProgramsDataBuffer.getCount()) {
                WatchNextProgramsDataBuffer watchNextProgramsDataBuffer2 = this.mWatchNextPrograms;
                if (watchNextProgramsDataBuffer2 != null) {
                    return watchNextProgramsDataBuffer2.get(position);
                }
                return null;
            }
            StringBuilder sb = new StringBuilder(56);
            sb.append("Position [");
            sb.append(position);
            sb.append("] is out of bounds [0, ");
            sb.append(this.mWatchNextPrograms.getCount() - 1);
            sb.append("]");
            throw new IllegalArgumentException(sb.toString());
        }
        throw new IllegalArgumentException("Position must be positive");
    }

    public int getWatchNextProgramsCount() {
        WatchNextProgramsDataBuffer watchNextProgramsDataBuffer = this.mWatchNextPrograms;
        if (watchNextProgramsDataBuffer != null) {
            return watchNextProgramsDataBuffer.getCount();
        }
        return 0;
    }

    public void loadWatchNextProgramData() {
        loadWatchNextProgramDataInternal();
    }

    /* access modifiers changed from: private */
    public void loadHomeChannelDataInternal() {
        if (this.mHomeChannelsBackgroundTask == null) {
            DataLoadingBackgroundTask task = DataLoadingBackgroundTask.obtain(this.mContext).setUri(TvContract.Channels.CONTENT_URI).setProjection(HomeChannel.PROJECTION).setSelection(HOME_CHANNELS_SELECTION).setTag(0).setCallbacks(this);
            startTrackingTask(task);
            task.execute();
        }
    }

    private void removeOneChannelProgramsIfCacheTooBig() {
        if (this.mChannelPrograms.size() >= 15) {
            for (Long cachedChannelId : this.mCachedProgramsChannelOrder) {
                if (!this.mChannelProgramsObservers.containsKey(cachedChannelId) && !areChannelProgramsAlwaysCached(cachedChannelId)) {
                    cleanupAfterChannelProgramsRemovalFromCache(this.mChannelPrograms.remove(cachedChannelId), cachedChannelId);
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void loadHomeChannelProgramData(long channelId) {
        if (!this.mHomeChannelProgramsBackgroundTasks.containsKey(Long.valueOf(channelId))) {
            if (!this.mChannelPrograms.containsKey(Long.valueOf(channelId))) {
                removeOneChannelProgramsIfCacheTooBig();
            }
            DataLoadingBackgroundTask task = DataLoadingBackgroundTask.obtain(this.mContext).setUri(TvContract.PreviewPrograms.CONTENT_URI).setProjection(Program.PROJECTION).setSelection(PROGRAMS_BY_CHANNEL_ID_SELECTION).setSelectionArg(String.valueOf(channelId)).setSortOrder("weight DESC").setExtraParam(Long.valueOf(channelId)).setTag(1).setCallbacks(this);
            startTrackingTask(task);
            task.execute();
        }
    }

    private void loadPackageChannelsData() {
        if (this.mPackageChannelsBackgroundTask == null) {
            DataLoadingBackgroundTask task = DataLoadingBackgroundTask.obtain(this.mContext).setUri(TvContract.Channels.CONTENT_URI).setProjection(Channel.PROJECTION).setSelection(PACKAGE_CHANNELS_SELECTION).setTag(2).setCallbacks(this);
            startTrackingTask(task);
            task.execute();
        }
    }

    /* access modifiers changed from: private */
    public void loadPackageChannelsDataIfNeeded() {
        if (this.mPackagesWithChannelsObservers.size() > 0 || this.mPackageChannelsObservers.size() > 0) {
            loadPackageChannelsData();
        }
    }

    /* access modifiers changed from: private */
    public void loadPromoChannelData() {
        if (this.mPromoChannelBackgroundTask == null) {
            String promotionRowPackage = OemConfiguration.get(this.mContext).getAppsPromotionRowPackage();
            if (promotionRowPackage == null) {
                Log.e(TAG, "Promotion row package is not configured");
                this.mHandler.post(new TvDataManager$$Lambda$0(this));
                return;
            }
            DataLoadingBackgroundTask task = DataLoadingBackgroundTask.obtain(this.mContext).setUri(TvContract.Channels.CONTENT_URI).setProjection(Channel.PROJECTION).setSelection(PROMO_CHANNEL_SELECTION).setSelectionArg(promotionRowPackage).setTag(3).setCallbacks(this);
            startTrackingTask(task);
            task.execute();
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$loadPromoChannelData$0$TvDataManager() {
        setPromoChannelAndNotify(null);
    }

    /* access modifiers changed from: private */
    public void loadWatchNextProgramDataInternal() {
        if (this.mWatchNextProgramsBackgroundTask == null) {
            DataLoadingBackgroundTask task = DataLoadingBackgroundTask.obtain(this.mContext).setUri(TvContract.WatchNextPrograms.CONTENT_URI).setProjection(Program.WATCH_NEXT_PROJECTION).setSelection(this.mWatchNextSelection).setSelectionArg(String.valueOf(System.currentTimeMillis() + WATCH_NEXT_REQUERY_INTERVAL_MILLIS)).setSortOrder("last_engagement_time_utc_millis DESC LIMIT 1000").setTag(4).setCallbacks(this);
            startTrackingTask(task);
            task.execute();
        }
    }

    public void loadAllWatchNextProgramDataIntoCache() {
        if (this.mWatchNextCacheBackgroundTask == null) {
            DataLoadingBackgroundTask task = DataLoadingBackgroundTask.obtain(this.mContext).setUri(TvContract.WatchNextPrograms.CONTENT_URI).setProjection(WATCH_NEXT_CACHE_PROJECTION).setSelection("browsable=1").setTag(5).setCallbacks(this);
            startTrackingTask(task);
            task.execute();
        }
    }

    /* access modifiers changed from: private */
    public String buildWatchNextSelection(SharedPreferences prefs) {
        StringBuilder sb = new StringBuilder(" NOT IN (");
        boolean hasPackages = false;
        for (String key : prefs.getAll().keySet()) {
            if (key.startsWith(WatchNextPrefs.WATCH_NEXT_PACKAGE_KEY_PREFIX)) {
                sb.append("'");
                sb.append(key.substring(WatchNextPrefs.WATCH_NEXT_PACKAGE_KEY_PREFIX.length()));
                sb.append("',");
                hasPackages = true;
            }
        }
        if (!hasPackages) {
            return WATCH_NEXT_SELECTION;
        }
        sb.setLength(sb.length() - 1);
        sb.append(")");
        String notInClause = sb.toString();
        String valueOf = String.valueOf("browsable=1 AND last_engagement_time_utc_millis<=? AND package_name");
        String valueOf2 = String.valueOf(notInClause);
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    private ChannelConfigurationInfo getChannelConfigInfo(GoogleConfiguration googleConfiguration, OemConfiguration oemConfiguration, String packageName, String systemChannelKey) {
        ChannelConfigurationInfo channelConfig = null;
        if (googleConfiguration != null) {
            channelConfig = googleConfiguration.getChannelInfo(packageName, systemChannelKey);
        }
        if (channelConfig == null) {
            return oemConfiguration.getChannelInfo(packageName, systemChannelKey);
        }
        return channelConfig;
    }

    private boolean isChannelSponsored(String packageName, String systemChannelKey, GoogleConfiguration googleConfig, ChannelConfigurationInfo channelConfig) {
        return (googleConfig != null && googleConfig.isSponsored(packageName, systemChannelKey)) || (channelConfig != null && channelConfig.isSponsored());
    }

    private void startTrackingTask(DataLoadingBackgroundTask task) {
        if (task.getTag() == 0) {
            this.mHomeChannelsBackgroundTask = task;
        } else if (task.getTag() == 1) {
            this.mHomeChannelProgramsBackgroundTasks.put((Long) task.getExtraParam(), task);
        } else if (task.getTag() == 2) {
            this.mPackageChannelsBackgroundTask = task;
        } else if (task.getTag() == 3) {
            this.mPromoChannelBackgroundTask = task;
        } else if (task.getTag() == 4) {
            this.mWatchNextProgramsBackgroundTask = task;
        } else if (task.getTag() == 5) {
            this.mWatchNextCacheBackgroundTask = task;
        }
    }

    private void stopTrackingTask(DataLoadingBackgroundTask task) {
        if (task.getTag() == 0) {
            this.mHomeChannelsBackgroundTask = null;
        } else if (task.getTag() == 1) {
            this.mHomeChannelProgramsBackgroundTasks.remove((Long) task.getExtraParam());
        } else if (task.getTag() == 2) {
            this.mPackageChannelsBackgroundTask = null;
        } else if (task.getTag() == 3) {
            this.mPromoChannelBackgroundTask = null;
        } else if (task.getTag() == 4) {
            this.mWatchNextProgramsBackgroundTask = null;
        } else if (task.getTag() == 5) {
            this.mWatchNextCacheBackgroundTask = null;
        }
    }

    @WorkerThread
    public void onTaskPostProcess(DataLoadingBackgroundTask task) {
        Throwable th;
        Throwable th2;
        Map<String, List<Channel>> packageChannels;
        List<ChannelPackage> packagesWithChannels;
        Throwable th3;
        int sponsoredGoogleChannelOobPosition;
        long sponsoredGoogleChannelId;
        List<HomeChannel> channels;
        List<HomeChannel> pinnedChannels;
        LongSparseArray<ChannelConfigurationInfo> pinnedChannelsConfig;
        Throwable th4;
        DataLoadingBackgroundTask dataLoadingBackgroundTask = task;
        if (task.getTag() == 0) {
            long sponsoredGoogleChannelId2 = -1;
            int sponsoredGoogleChannelOobPosition2 = -1;
            List<HomeChannel> pinnedChannels2 = null;
            LongSparseArray<ChannelConfigurationInfo> pinnedChannelsConfig2 = null;
            Cursor cursor = task.getResult();
            if (cursor != null) {
                try {
                    channels = new ArrayList<>(cursor.getCount());
                    GoogleConfiguration googleConfiguration = this.mGoogleConfigurationManager.getChannelConfigs();
                    OemConfiguration oemConfiguration = OemConfiguration.get(this.mContext);
                    while (cursor.moveToNext()) {
                        HomeChannel homeChannel = HomeChannel.fromCursor(cursor);
                        channels.add(homeChannel);
                        ChannelConfigurationInfo channelConfig = getChannelConfigInfo(googleConfiguration, oemConfiguration, homeChannel.getPackageName(), homeChannel.getSystemChannelKey());
                        homeChannel.setSponsored(isChannelSponsored(homeChannel.getPackageName(), homeChannel.getSystemChannelKey(), googleConfiguration, channelConfig));
                        if (channelConfig != null) {
                            if (channelConfig.isSponsored() && channelConfig.isGoogleConfig()) {
                                sponsoredGoogleChannelId2 = homeChannel.getId();
                                sponsoredGoogleChannelOobPosition2 = channelConfig.getChannelPosition();
                            }
                            homeChannel.setCanMove(channelConfig.canMove());
                            homeChannel.setCanRemove(channelConfig.canHide());
                            if (!homeChannel.canMove() && !homeChannel.canRemove()) {
                                if (pinnedChannelsConfig2 == null) {
                                    pinnedChannelsConfig2 = new LongSparseArray<>();
                                    pinnedChannels2 = new ArrayList<>();
                                }
                                pinnedChannels2.add(homeChannel);
                                pinnedChannelsConfig2.put(homeChannel.getId(), channelConfig);
                            }
                        }
                    }
                    sponsoredGoogleChannelOobPosition = sponsoredGoogleChannelOobPosition2;
                    sponsoredGoogleChannelId = sponsoredGoogleChannelId2;
                } catch (Throwable th5) {
                    Throwable th6 = th5;
                    if (cursor != null) {
                        $closeResource(th4, cursor);
                    }
                    throw th6;
                }
            } else {
                channels = Collections.emptyList();
                Log.e(TAG, "error loading home channels, cursor is null");
                sponsoredGoogleChannelOobPosition = -1;
                sponsoredGoogleChannelId = -1;
            }
            if (cursor != null) {
                $closeResource(null, cursor);
            }
            if (pinnedChannels2 == null) {
                pinnedChannels = Collections.emptyList();
            } else {
                pinnedChannels = pinnedChannels2;
            }
            if (pinnedChannelsConfig2 == null) {
                pinnedChannelsConfig = EMPTY_PINNED_CHANNEL_CONFIG;
            } else {
                pinnedChannelsConfig = pinnedChannelsConfig2;
            }
            HomeChannelsBackgroundTaskResults homeChannelsBackgroundTaskResults = r1;
            HomeChannelsBackgroundTaskResults homeChannelsBackgroundTaskResults2 = new HomeChannelsBackgroundTaskResults(this, channels, sponsoredGoogleChannelId, sponsoredGoogleChannelOobPosition, pinnedChannels, pinnedChannelsConfig);
            dataLoadingBackgroundTask.setExtraResult(homeChannelsBackgroundTaskResults);
        } else if (task.getTag() == 1) {
            Cursor cursor2 = task.getResult();
            if (cursor2 != null) {
                while (cursor2.moveToNext()) {
                    this.mProgramChannelIds.put(Long.valueOf(cursor2.getLong(0)), Long.valueOf(cursor2.getLong(1)));
                }
                return;
            }
            String valueOf = String.valueOf(task.getExtraParam());
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 51);
            sb.append("error loading programs for channel ");
            sb.append(valueOf);
            sb.append(", cursor is null");
            Log.e(TAG, sb.toString());
        } else if (task.getTag() == 2) {
            Set<Long> packageChannelIds = new HashSet<>(100);
            Cursor cursor3 = task.getResult();
            if (cursor3 != null) {
                try {
                    packagesWithChannels = new ArrayList<>(cursor3.getCount());
                    packageChannels = new HashMap<>(cursor3.getCount());
                    GoogleConfiguration googleConfiguration2 = this.mGoogleConfigurationManager.getChannelConfigs();
                    OemConfiguration oemConfiguration2 = OemConfiguration.get(this.mContext);
                    while (cursor3.moveToNext()) {
                        Channel channel = Channel.fromCursor(cursor3);
                        packageChannelIds.add(Long.valueOf(channel.getId()));
                        ChannelConfigurationInfo channelConfig2 = getChannelConfigInfo(googleConfiguration2, oemConfiguration2, channel.getPackageName(), channel.getSystemChannelKey());
                        channel.setSponsored(isChannelSponsored(channel.getPackageName(), channel.getSystemChannelKey(), googleConfiguration2, channelConfig2));
                        String packageName = channel.getPackageName();
                        if (channel.isSponsored()) {
                            packageName = Constants.SPONSORED_CHANNEL_LEGACY_PACKAGE_NAME;
                        }
                        if (channelConfig2 != null) {
                            channel.setCanRemove(channelConfig2.canHide());
                        }
                        addToMultimap(packageChannels, packageName, channel);
                    }
                    for (Map.Entry<String, List<Channel>> entry : packageChannels.entrySet()) {
                        packagesWithChannels.add(new ChannelPackage((String) entry.getKey(), ((List) entry.getValue()).size()));
                    }
                } catch (Throwable th7) {
                    Throwable th8 = th7;
                    if (cursor3 != null) {
                        $closeResource(th3, cursor3);
                    }
                    throw th8;
                }
            } else {
                packagesWithChannels = Collections.emptyList();
                Map<String, List<Channel>> packageChannels2 = Collections.emptyMap();
                Log.e(TAG, "error loading package channels, cursor is null");
                packageChannels = packageChannels2;
            }
            if (cursor3 != null) {
                $closeResource(null, cursor3);
            }
            Set<Long> nonEmptyChannelIds = getNonEmptyChannelIds(packageChannelIds);
            for (ChannelPackage channelPackage : packagesWithChannels) {
                List<Channel> channels2 = packageChannels.get(channelPackage.getPackageName());
                for (Channel channel2 : channels2) {
                    channel2.setIsEmpty(!nonEmptyChannelIds.contains(Long.valueOf(channel2.getId())));
                }
                if (channels2.size() == 1) {
                    channelPackage.setOnlyChannelAttributes((Channel) channels2.get(0));
                }
            }
            dataLoadingBackgroundTask.setExtraResult(new PackageChannelsBackgroundTaskResults(this, packagesWithChannels, packageChannels));
        } else if (task.getTag() == 3) {
            Channel channel3 = null;
            Cursor cursor4 = task.getResult();
            if (cursor4 != null) {
                try {
                    if (cursor4.moveToFirst()) {
                        channel3 = Channel.fromCursor(cursor4);
                    }
                } catch (Throwable th9) {
                    Throwable th10 = th9;
                    if (cursor4 != null) {
                        $closeResource(th2, cursor4);
                    }
                    throw th10;
                }
            } else {
                Log.e(TAG, "error loading promo channel, cursor is null");
            }
            if (cursor4 != null) {
                $closeResource(null, cursor4);
            }
            dataLoadingBackgroundTask.setExtraResult(channel3);
        } else if (task.getTag() == 5) {
            Cursor cursor5 = task.getResult();
            if (cursor5 != null) {
                try {
                    dataLoadingBackgroundTask.setExtraResult(extractWatchNextCache(cursor5));
                } catch (Throwable th11) {
                    Throwable th12 = th11;
                    if (cursor5 != null) {
                        $closeResource(th, cursor5);
                    }
                    throw th12;
                }
            } else {
                Log.e(TAG, "error loading watch next data into cache, cursor is null");
            }
            if (cursor5 != null) {
                $closeResource(null, cursor5);
            }
        }
    }

    private static /* synthetic */ void $closeResource(Throwable x0, Cursor x1) {
        if (x0 != null) {
            try {
                x1.close();
            } catch (Throwable th) {
                ThrowableExtension.addSuppressed(x0, th);
            }
        } else {
            x1.close();
        }
    }

    @NonNull
    private String buildCountProgramForChannelsSelection(List<Long> channelsToCount) {
        StringBuilder sb = new StringBuilder(channelsToCount.size() * 5);
        sb.append("channel_id IN (");
        for (Long programId : channelsToCount) {
            sb.append(programId.toString());
            sb.append(',');
        }
        sb.setLength(sb.length() - 1);
        sb.append(')');
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    @VisibleForTesting
    public Set<Long> getNonEmptyChannelIds(Set<Long> channelIds) {
        int channelIdsCount = channelIds.size();
        Set<Long> nonEmptyChannelIds = new HashSet<>(channelIds.size());
        List<Long> channelIdsList = new ArrayList<>(channelIds);
        for (int i = 0; i < channelIdsCount; i += sChannelProgramCountBatchSize) {
            List<Long> subChannelIdList = channelIdsList.subList(i, Math.min(sChannelProgramCountBatchSize + i, channelIdsCount));
            String buildCountProgramForChannelsSelection = buildCountProgramForChannelsSelection(subChannelIdList);
            StringBuilder sb = new StringBuilder(String.valueOf(buildCountProgramForChannelsSelection).length() + 38);
            sb.append("browsable=1 AND ");
            sb.append(buildCountProgramForChannelsSelection);
            sb.append(") GROUP BY (");
            sb.append("channel_id");
            Cursor cursor = this.mContext.getContentResolver().query(TvContract.PreviewPrograms.CONTENT_URI, new String[]{"channel_id"}, sb.toString(), null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    try {
                        nonEmptyChannelIds.add(Long.valueOf(cursor.getLong(0)));
                    } catch (Throwable th) {
                        if (cursor != null) {
                            $closeResource(th, cursor);
                        }
                        throw th;
                    }
                }
            } else {
                int size = channelIds.size();
                StringBuilder sb2 = new StringBuilder(70);
                sb2.append("Program count failed for ");
                sb2.append(size);
                sb2.append(" channels. Returned cursor is null");
                Log.e(TAG, sb2.toString());
            }
            if (cursor != null) {
                $closeResource(null, cursor);
            }
        }
        return nonEmptyChannelIds;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    @MainThread
    public void invalidateProgramsCountForBrowsableChannelsInternal(Set<Long> channelIds) {
        if (!channelIds.isEmpty()) {
            final Set<Long> channelIdsCopy = new HashSet<>(channelIds);
            new AsyncTask<Object, Void, Set<Long>>() {
                /* access modifiers changed from: protected */
                public /* bridge */ /* synthetic */ void onPostExecute(Object obj) {
                    onPostExecute((Set<Long>) ((Set) obj));
                }

                /* access modifiers changed from: protected */
                public Set<Long> doInBackground(Object... params) {
                    return TvDataManager.this.getNonEmptyChannelIds(channelIdsCopy);
                }

                /* access modifiers changed from: protected */
                public void onPostExecute(Set<Long> nonEmptyChannelIds) {
                    boolean homeChannelsAdded = false;
                    Set<Long> emptyStatusChangedHomeChannelIds = new HashSet<>();
                    for (Long countedChannelId : channelIdsCopy) {
                        if (TvDataManager.this.mBrowsableChannels.containsKey(countedChannelId)) {
                            if (TvDataManager.this.mHomeChannelIds.contains(countedChannelId)) {
                                boolean havePrograms = nonEmptyChannelIds.contains(countedChannelId);
                                if (havePrograms != TvDataManager.this.mNonEmptyHomeChannelIds.contains(countedChannelId)) {
                                    if (havePrograms) {
                                        TvDataManager.this.mNonEmptyHomeChannelIds.add(countedChannelId);
                                    } else {
                                        TvDataManager.this.mNonEmptyHomeChannelIds.remove(countedChannelId);
                                    }
                                    emptyStatusChangedHomeChannelIds.add(countedChannelId);
                                }
                            } else if (nonEmptyChannelIds.contains(countedChannelId)) {
                                TvDataManager.this.mHomeChannels.add((HomeChannel) TvDataManager.this.mBrowsableChannels.get(countedChannelId));
                                TvDataManager.this.mHomeChannelIds.add(countedChannelId);
                                TvDataManager.this.mNonEmptyHomeChannelIds.add(countedChannelId);
                                homeChannelsAdded = true;
                                TvDataManager.this.mChannelOrderManager.onNewChannelAdded(countedChannelId.longValue());
                            }
                        }
                    }
                    if (homeChannelsAdded || emptyStatusChangedHomeChannelIds.size() > 0) {
                        TvDataManager.this.saveNonEmptyHomeChannelIdsToSharedPref();
                        if (homeChannelsAdded) {
                            TvDataManager.this.mChannelOrderManager.applyOrder(TvDataManager.this.mHomeChannels);
                        }
                        if (emptyStatusChangedHomeChannelIds.size() > 0) {
                            for (Long longValue : emptyStatusChangedHomeChannelIds) {
                                Integer position = TvDataManager.this.mChannelOrderManager.getChannelPosition(longValue.longValue());
                                if (position != null) {
                                    TvDataManager.this.notifyHomeChannelEmptyStatusChanged(position.intValue());
                                }
                            }
                        }
                        if (homeChannelsAdded) {
                            TvDataManager.this.notifyHomeChannelsChange();
                        }
                    }
                }
            }.executeOnExecutor(Executors.getThreadPoolExecutor(), new Object[0]);
        }
    }

    private boolean isFirstLaunchAfterBoot() {
        int bootCount;
        if (!this.mIsFirstLaunchAfterBoot || this.mTvDataManagerPref.getInt(KEY_BOOT_COUNT, -1) >= (bootCount = getBootCount())) {
            return false;
        }
        this.mTvDataManagerPref.edit().putInt(KEY_BOOT_COUNT, bootCount).apply();
        this.mIsFirstLaunchAfterBoot = false;
        return true;
    }

    private int getBootCount() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), KEY_BOOT_COUNT, 0);
    }

    @MainThread
    public void onTaskCompleted(DataLoadingBackgroundTask task) {
        boolean z = false;
        if (task.getTag() == 0) {
            boolean needToCountProgramsForAllBrowsableChannels = isHomeChannelDataStale();
            HomeChannelsBackgroundTaskResults results = (HomeChannelsBackgroundTaskResults) task.getExtraResult();
            List<HomeChannel> list = this.mHomeChannels;
            if (list == null) {
                this.mHomeChannels = new ArrayList();
            } else {
                list.clear();
            }
            HashSet<Long> newHomeChannelIds = new HashSet<>(this.mHomeChannelIds.size());
            boolean firstLaunchAfterBoot = isFirstLaunchAfterBoot();
            Map<Long, HomeChannel> newBrowsableChannels = new HashMap<>(results.channels.size());
            Set<Long> addedBrowsableChannelIds = new HashSet<>();
            for (HomeChannel channel : results.channels) {
                long channelId = channel.getId();
                if (this.mLiveTvChannelId == -1 && isLiveTvChannel(channel.getPackageName(), channel.getSystemChannelKey())) {
                    saveLiveTvChannelId(channelId);
                }
                if (!needToCountProgramsForAllBrowsableChannels && !this.mBrowsableChannels.containsKey(Long.valueOf(channelId))) {
                    addedBrowsableChannelIds.add(Long.valueOf(channelId));
                }
                newBrowsableChannels.put(Long.valueOf(channelId), channel);
                if (this.mHomeChannelIds.contains(Long.valueOf(channelId)) && (!firstLaunchAfterBoot || !channel.isLegacy())) {
                    this.mHomeChannels.add(channel);
                    newHomeChannelIds.add(Long.valueOf(channelId));
                }
            }
            this.mBrowsableChannels = newBrowsableChannels;
            this.mHomeChannelIds = newHomeChannelIds;
            this.mNonEmptyHomeChannelIds.removeIf(new TvDataManager$$Lambda$1(this));
            saveNonEmptyHomeChannelIdsToSharedPref();
            this.mHomeChannelsStale = false;
            this.mPinnedChannelOrderManager.setPinnedChannels(results.pinnedChannels, results.pinnedChannelsConfig);
            if (this.mChannelOrderManager == null) {
                Context context = this.mContext;
                this.mChannelOrderManager = new ChannelOrderManager(context, OemConfiguration.get(context).getOutOfBoxChannelsList(), OemConfiguration.get(this.mContext).getLiveTvChannelOobPosition());
                this.mChannelOrderManager.setChannelsObservers(this.mHomeChannelsObservers);
                this.mChannelOrderManager.setPinnedChannelOrderManager(this.mPinnedChannelOrderManager);
            }
            this.mChannelOrderManager.setLiveTvChannelId(this.mLiveTvChannelId);
            this.mChannelOrderManager.setSponsoredGoogleChannelId(results.sponsoredGoogleChannelId);
            this.mChannelOrderManager.setSponsoredGoogleChannelOobPosition(results.sponsoredGoogleChannelOobPosition);
            this.mChannelOrderManager.applyOrder(this.mHomeChannels);
            notifyHomeChannelsChange();
            if (needToCountProgramsForAllBrowsableChannels) {
                invalidateProgramsCountForBrowsableChannelsInternal(this.mBrowsableChannels.keySet());
            } else {
                invalidateProgramsCountForBrowsableChannelsInternal(addedBrowsableChannelIds);
            }
        } else if (task.getTag() == 1) {
            if (task.getResult() != null) {
                Long channelId2 = (Long) task.getExtraParam();
                cleanupAfterChannelProgramsRemovalFromCache(this.mChannelPrograms.remove(channelId2), channelId2);
                this.mChannelPrograms.put(channelId2, new ProgramsDataBuffer(task.getResult()));
                this.mCachedProgramsChannelOrder.add(channelId2);
                notifyChannelProgramsChange(channelId2.longValue());
                if (task.getResult().getCount() != 0) {
                    z = true;
                }
                boolean havePrograms = z;
                if (this.mNonEmptyHomeChannelIds.contains(channelId2) != havePrograms) {
                    if (havePrograms) {
                        this.mNonEmptyHomeChannelIds.add(channelId2);
                    } else {
                        this.mNonEmptyHomeChannelIds.remove(channelId2);
                    }
                    saveNonEmptyHomeChannelIdsToSharedPref();
                    Integer position = this.mChannelOrderManager.getChannelPosition(channelId2.longValue());
                    if (position != null) {
                        notifyHomeChannelEmptyStatusChanged(position.intValue());
                    }
                }
            }
        } else if (task.getTag() == 2) {
            PackageChannelsBackgroundTaskResults results2 = (PackageChannelsBackgroundTaskResults) task.getExtraResult();
            this.mPackagesWithChannels = results2.packagesWithChannels;
            this.mPackageChannels = results2.packageChannels;
            for (Map.Entry<String, List<Channel>> entry : this.mPackageChannels.entrySet()) {
                for (Channel channel2 : (List) entry.getValue()) {
                    if (this.mLiveTvChannelId == -1 && isLiveTvChannel(channel2.getPackageName(), channel2.getSystemChannelKey())) {
                        saveLiveTvChannelId(channel2.getId());
                    }
                }
            }
            notifyPackagesWithChannelsChange();
            notifyPackageChannelsChange();
        } else if (task.getTag() == 3) {
            setPromoChannelAndNotify((Channel) task.getExtraResult());
        } else if (task.getTag() == 4) {
            if (task.getResult() != null) {
                WatchNextProgramsDataBuffer watchNextProgramsDataBuffer = this.mWatchNextPrograms;
                if (watchNextProgramsDataBuffer != null) {
                    watchNextProgramsDataBuffer.release();
                }
                this.mWatchNextPrograms = new WatchNextProgramsDataBuffer(task.getResult());
                this.mWatchNextProgramsStale = false;
                notifyWatchNextProgramsChange();
            } else {
                Log.e(TAG, "error loading watch next programs, cursor is null");
            }
        } else if (task.getTag() == 5) {
            this.mWatchNextContentIdsCache = (HashSet) task.getExtraResult();
        }
        stopTrackingTask(task);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ boolean lambda$onTaskCompleted$1$TvDataManager(Long channelId) {
        return !this.mHomeChannelIds.contains(channelId);
    }

    private void setPromoChannelAndNotify(Channel promoChannel) {
        this.mPromoChannel = promoChannel;
        this.mPromoChannelLoaded = true;
        notifyPromoChannelChange();
    }

    @MainThread
    public void onTaskCanceled(DataLoadingBackgroundTask task) {
        stopTrackingTask(task);
    }

    @MainThread
    public void onTaskFailed(DataLoadingBackgroundTask task, Throwable throwable) {
        String valueOf = String.valueOf(task);
        String valueOf2 = String.valueOf(throwable);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 27 + String.valueOf(valueOf2).length());
        sb.append("onTaskFailed: ");
        sb.append(valueOf);
        sb.append(", throwable: ");
        sb.append(valueOf2);
        Log.e(TAG, sb.toString());
        stopTrackingTask(task);
    }

    private boolean isLiveTvChannel(String packageName, String systemChannelKey) {
        ChannelConfigurationInfo liveChannelConfigurationInfo = OemConfiguration.get(this.mContext).getLiveTvOobPackageInfo();
        if (liveChannelConfigurationInfo == null || !liveChannelConfigurationInfo.getPackageName().equals(packageName)) {
            return false;
        }
        if (TextUtils.isEmpty(liveChannelConfigurationInfo.getSystemChannelKey()) || TextUtils.equals(liveChannelConfigurationInfo.getSystemChannelKey(), systemChannelKey)) {
            return true;
        }
        return false;
    }

    private void saveLiveTvChannelId(long liveChannelId) {
        this.mLiveTvChannelId = liveChannelId;
        this.mLiveTvChannelPref.edit().putLong(KEY_LIVE_TV_CHANNEL_ID, this.mLiveTvChannelId).apply();
    }

    public void setChannelBrowsable(long channelId, boolean browsable, boolean reloadImmediately) {
        ChannelOrderManager channelOrderManager = this.mChannelOrderManager;
        if (channelOrderManager != null) {
            if (!browsable) {
                channelOrderManager.onChannelRemoved(channelId);
            }
            this.mChannelOrderManager.notifyUserHasManagedChannels();
        }
        final boolean z = browsable;
        final long j = channelId;
        final boolean z2 = reloadImmediately;
        new AsyncTask<Object, Void, Integer>() {
            /* access modifiers changed from: protected */
            public Integer doInBackground(Object... params) {
                ContentValues values = new ContentValues();
                values.put("browsable", Boolean.valueOf(z));
                return Integer.valueOf(TvDataManager.this.mContext.getContentResolver().update(TvContract.buildChannelUri(j), values, null, null));
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(Integer numRow) {
                if (numRow.intValue() == 1 && z2) {
                    TvDataManager.this.loadHomeChannelDataInternal();
                }
            }
        }.executeOnExecutor(Executors.getThreadPoolExecutor(), Boolean.valueOf(browsable), Long.valueOf(channelId));
    }

    public void removePreviewProgram(long programId, long channelId, String packageName) {
        final long j = programId;
        final String str = packageName;
        final long j2 = channelId;
        new AsyncTask<Object, Void, Integer>() {
            /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
             method: ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Boolean):void}
             arg types: [java.lang.String, int]
             candidates:
              ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Byte):void}
              ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Float):void}
              ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.String):void}
              ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Integer):void}
              ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Long):void}
              ClspMth{android.content.ContentValues.put(java.lang.String, byte[]):void}
              ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Double):void}
              ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Short):void}
              ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Boolean):void} */
            /* access modifiers changed from: protected */
            public Integer doInBackground(Object... params) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("browsable", (Boolean) false);
                int numRowAffected = TvDataManager.this.mContext.getContentResolver().update(TvContract.buildPreviewProgramUri(j), contentValues, null, null);
                if (numRowAffected == 1) {
                    ((TvInputManager) TvDataManager.this.mContext.getSystemService("tv_input")).notifyPreviewProgramBrowsableDisabled(str, j);
                }
                return Integer.valueOf(numRowAffected);
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(Integer numRowAffected) {
                if (numRowAffected.intValue() == 1) {
                    TvDataManager.this.loadHomeChannelProgramData(j2);
                }
            }
        }.executeOnExecutor(Executors.getThreadPoolExecutor(), new Object[0]);
    }

    public void removeProgramFromWatchlist(final long programId, final String packageName) {
        new AsyncTask<Object, Void, Integer>() {
            /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
             method: ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Boolean):void}
             arg types: [java.lang.String, int]
             candidates:
              ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Byte):void}
              ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Float):void}
              ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.String):void}
              ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Integer):void}
              ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Long):void}
              ClspMth{android.content.ContentValues.put(java.lang.String, byte[]):void}
              ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Double):void}
              ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Short):void}
              ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Boolean):void} */
            /* access modifiers changed from: protected */
            public Integer doInBackground(Object... params) {
                Uri watchNextProgramUri = TvContract.buildWatchNextProgramUri(programId);
                ContentValues contentValues = new ContentValues();
                contentValues.put("browsable", (Boolean) false);
                int numRowAffected = TvDataManager.this.mContext.getContentResolver().update(watchNextProgramUri, contentValues, null, null);
                if (numRowAffected == 1) {
                    ((TvInputManager) TvDataManager.this.mContext.getSystemService("tv_input")).notifyWatchNextProgramBrowsableDisabled(packageName, programId);
                }
                return Integer.valueOf(numRowAffected);
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(Integer numRowAffected) {
                if (numRowAffected.intValue() == 1) {
                    TvDataManager.this.loadWatchNextProgramDataInternal();
                }
            }
        }.executeOnExecutor(Executors.getThreadPoolExecutor(), new Object[0]);
    }

    public void addProgramToWatchlist(final long programId, final String packageName) {
        new AsyncTask<Object, Void, Boolean>() {
            /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
             method: ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Integer):void}
             arg types: [java.lang.String, int]
             candidates:
              ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Byte):void}
              ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Float):void}
              ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.String):void}
              ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Long):void}
              ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Boolean):void}
              ClspMth{android.content.ContentValues.put(java.lang.String, byte[]):void}
              ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Double):void}
              ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Short):void}
              ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Integer):void} */
            /* access modifiers changed from: protected */
            /* JADX WARNING: Removed duplicated region for block: B:10:0x0055 A[Catch:{ all -> 0x0422, all -> 0x004e }] */
            /* JADX WARNING: Removed duplicated region for block: B:40:0x0427 A[Catch:{ all -> 0x0422, all -> 0x004e }] */
            /* JADX WARNING: Removed duplicated region for block: B:43:0x042f  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public java.lang.Boolean doInBackground(java.lang.Object... r24) {
                /*
                    r23 = this;
                    r1 = r23
                    java.lang.String r0 = "long_description"
                    java.lang.String r2 = "short_description"
                    java.lang.String r3 = "canonical_genre"
                    java.lang.String r4 = "episode_title"
                    java.lang.String r5 = "episode_display_number"
                    java.lang.String r6 = "season_title"
                    java.lang.String r7 = "season_display_number"
                    java.lang.String r8 = "title"
                    java.lang.String r9 = "preview_audio_uri"
                    java.lang.String r10 = "end_time_utc_millis"
                    java.lang.String r11 = "start_time_utc_millis"
                    java.lang.String r12 = "genre"
                    java.lang.String r13 = "logo_content_description"
                    java.lang.String r14 = "tv_series_item_type"
                    com.google.android.tvlauncher.data.TvDataManager r15 = com.google.android.tvlauncher.data.TvDataManager.this
                    android.content.Context r15 = r15.mContext
                    android.content.ContentResolver r16 = r15.getContentResolver()
                    r15 = r9
                    r22 = r10
                    long r9 = r4
                    android.net.Uri r17 = android.media.tv.TvContract.buildPreviewProgramUri(r9)
                    r18 = 0
                    r19 = 0
                    r20 = 0
                    r21 = 0
                    android.database.Cursor r9 = r16.query(r17, r18, r19, r20, r21)
                    if (r9 == 0) goto L_0x0052
                    boolean r10 = r9.moveToFirst()     // Catch:{ all -> 0x004e }
                    if (r10 == 0) goto L_0x0052
                    r10 = 1
                    goto L_0x0053
                L_0x004e:
                    r0 = move-exception
                L_0x004f:
                    r2 = r0
                    goto L_0x0433
                L_0x0052:
                    r10 = 0
                L_0x0053:
                    if (r10 == 0) goto L_0x0427
                    android.content.ContentValues r16 = new android.content.ContentValues     // Catch:{ all -> 0x004e }
                    r16.<init>()     // Catch:{ all -> 0x004e }
                    r17 = r16
                    r16 = r15
                    java.lang.String r15 = "package_name"
                    r18 = r10
                    java.lang.String r10 = r6     // Catch:{ all -> 0x004e }
                    r1 = r17
                    r1.put(r15, r10)     // Catch:{ all -> 0x0422 }
                    java.lang.String r10 = "watch_next_type"
                    r15 = 3
                    java.lang.Integer r15 = java.lang.Integer.valueOf(r15)     // Catch:{ all -> 0x0422 }
                    r1.put(r10, r15)     // Catch:{ all -> 0x0422 }
                    java.lang.String r10 = "last_engagement_time_utc_millis"
                    long r19 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0422 }
                    java.lang.Long r15 = java.lang.Long.valueOf(r19)     // Catch:{ all -> 0x0422 }
                    r1.put(r10, r15)     // Catch:{ all -> 0x0422 }
                    int r10 = r9.getColumnIndex(r8)     // Catch:{ all -> 0x0422 }
                    java.lang.String r10 = r9.getString(r10)     // Catch:{ all -> 0x0422 }
                    r1.put(r8, r10)     // Catch:{ all -> 0x0422 }
                    int r8 = r9.getColumnIndex(r7)     // Catch:{ all -> 0x0422 }
                    java.lang.String r8 = r9.getString(r8)     // Catch:{ all -> 0x0422 }
                    r1.put(r7, r8)     // Catch:{ all -> 0x0422 }
                    int r7 = r9.getColumnIndex(r14)     // Catch:{ all -> 0x0422 }
                    r8 = -1
                    if (r7 == r8) goto L_0x00b0
                    int r7 = r9.getColumnIndex(r14)     // Catch:{ all -> 0x0422 }
                    int r7 = r9.getInt(r7)     // Catch:{ all -> 0x0422 }
                    java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x0422 }
                    r1.put(r14, r7)     // Catch:{ all -> 0x0422 }
                L_0x00b0:
                    int r7 = r9.getColumnIndex(r6)     // Catch:{ all -> 0x0422 }
                    java.lang.String r7 = r9.getString(r7)     // Catch:{ all -> 0x0422 }
                    r1.put(r6, r7)     // Catch:{ all -> 0x0422 }
                    int r6 = r9.getColumnIndex(r5)     // Catch:{ all -> 0x0422 }
                    java.lang.String r6 = r9.getString(r6)     // Catch:{ all -> 0x0422 }
                    r1.put(r5, r6)     // Catch:{ all -> 0x0422 }
                    int r5 = r9.getColumnIndex(r4)     // Catch:{ all -> 0x0422 }
                    java.lang.String r5 = r9.getString(r5)     // Catch:{ all -> 0x0422 }
                    r1.put(r4, r5)     // Catch:{ all -> 0x0422 }
                    int r4 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r4 = r9.getString(r4)     // Catch:{ all -> 0x0422 }
                    r1.put(r3, r4)     // Catch:{ all -> 0x0422 }
                    int r3 = r9.getColumnIndex(r2)     // Catch:{ all -> 0x0422 }
                    java.lang.String r3 = r9.getString(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    int r2 = r9.getColumnIndex(r0)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = r9.getString(r2)     // Catch:{ all -> 0x0422 }
                    r1.put(r0, r2)     // Catch:{ all -> 0x0422 }
                    java.lang.String r0 = "video_width"
                    java.lang.String r2 = "video_width"
                    int r2 = r9.getColumnIndex(r2)     // Catch:{ all -> 0x0422 }
                    int r2 = r9.getInt(r2)     // Catch:{ all -> 0x0422 }
                    java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ all -> 0x0422 }
                    r1.put(r0, r2)     // Catch:{ all -> 0x0422 }
                    java.lang.String r0 = "video_height"
                    java.lang.String r2 = "video_height"
                    int r2 = r9.getColumnIndex(r2)     // Catch:{ all -> 0x0422 }
                    int r2 = r9.getInt(r2)     // Catch:{ all -> 0x0422 }
                    java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ all -> 0x0422 }
                    r1.put(r0, r2)     // Catch:{ all -> 0x0422 }
                    java.lang.String r0 = "audio_language"
                    java.lang.String r2 = "audio_language"
                    int r2 = r9.getColumnIndex(r2)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = r9.getString(r2)     // Catch:{ all -> 0x0422 }
                    r1.put(r0, r2)     // Catch:{ all -> 0x0422 }
                    java.lang.String r0 = "content_rating"
                    java.lang.String r2 = "content_rating"
                    int r2 = r9.getColumnIndex(r2)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = r9.getString(r2)     // Catch:{ all -> 0x0422 }
                    r1.put(r0, r2)     // Catch:{ all -> 0x0422 }
                    java.lang.String r0 = "poster_art_uri"
                    java.lang.String r2 = "poster_art_uri"
                    int r2 = r9.getColumnIndex(r2)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = r9.getString(r2)     // Catch:{ all -> 0x0422 }
                    r1.put(r0, r2)     // Catch:{ all -> 0x0422 }
                    java.lang.String r0 = "thumbnail_uri"
                    java.lang.String r2 = "thumbnail_uri"
                    int r2 = r9.getColumnIndex(r2)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = r9.getString(r2)     // Catch:{ all -> 0x0422 }
                    r1.put(r0, r2)     // Catch:{ all -> 0x0422 }
                    java.lang.String r0 = "searchable"
                    java.lang.String r2 = "searchable"
                    int r2 = r9.getColumnIndex(r2)     // Catch:{ all -> 0x0422 }
                    int r2 = r9.getInt(r2)     // Catch:{ all -> 0x0422 }
                    java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ all -> 0x0422 }
                    r1.put(r0, r2)     // Catch:{ all -> 0x0422 }
                    java.lang.String r0 = "internal_provider_data"
                    int r0 = r9.getColumnIndex(r0)     // Catch:{ all -> 0x0422 }
                    int r2 = r9.getType(r0)     // Catch:{ all -> 0x0422 }
                    r3 = 4
                    if (r2 != r3) goto L_0x018d
                    java.lang.String r2 = "internal_provider_data"
                    byte[] r3 = r9.getBlob(r0)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                L_0x018d:
                    java.lang.String r2 = "internal_provider_flag1"
                    java.lang.String r3 = "internal_provider_flag1"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    int r3 = r9.getInt(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "internal_provider_flag2"
                    java.lang.String r3 = "internal_provider_flag2"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    int r3 = r9.getInt(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "internal_provider_flag3"
                    java.lang.String r3 = "internal_provider_flag3"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    int r3 = r9.getInt(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "internal_provider_flag4"
                    java.lang.String r3 = "internal_provider_flag4"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    int r3 = r9.getInt(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "version_number"
                    java.lang.String r3 = "version_number"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    int r3 = r9.getInt(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "review_rating_style"
                    java.lang.String r3 = "review_rating_style"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    int r3 = r9.getInt(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "review_rating"
                    java.lang.String r3 = "review_rating"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r3 = r9.getString(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "type"
                    java.lang.String r3 = "type"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    int r3 = r9.getInt(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "poster_art_aspect_ratio"
                    java.lang.String r3 = "poster_art_aspect_ratio"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    int r3 = r9.getInt(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "poster_thumbnail_aspect_ratio"
                    java.lang.String r3 = "poster_thumbnail_aspect_ratio"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    int r3 = r9.getInt(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "logo_uri"
                    java.lang.String r3 = "logo_uri"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r3 = r9.getString(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    int r2 = r9.getColumnIndex(r13)     // Catch:{ all -> 0x0422 }
                    if (r2 == r8) goto L_0x0274
                    int r2 = r9.getColumnIndex(r13)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = r9.getString(r2)     // Catch:{ all -> 0x0422 }
                    r1.put(r13, r2)     // Catch:{ all -> 0x0422 }
                L_0x0274:
                    int r2 = r9.getColumnIndex(r12)     // Catch:{ all -> 0x0422 }
                    if (r2 == r8) goto L_0x0286
                    int r2 = r9.getColumnIndex(r12)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = r9.getString(r2)     // Catch:{ all -> 0x0422 }
                    r1.put(r12, r2)     // Catch:{ all -> 0x0422 }
                L_0x0286:
                    int r2 = r9.getColumnIndex(r11)     // Catch:{ all -> 0x0422 }
                    if (r2 == r8) goto L_0x029c
                    int r2 = r9.getColumnIndex(r11)     // Catch:{ all -> 0x0422 }
                    long r2 = r9.getLong(r2)     // Catch:{ all -> 0x0422 }
                    java.lang.Long r2 = java.lang.Long.valueOf(r2)     // Catch:{ all -> 0x0422 }
                    r1.put(r11, r2)     // Catch:{ all -> 0x0422 }
                L_0x029c:
                    r2 = r22
                    int r3 = r9.getColumnIndex(r2)     // Catch:{ all -> 0x0422 }
                    if (r3 == r8) goto L_0x02b4
                    int r3 = r9.getColumnIndex(r2)     // Catch:{ all -> 0x0422 }
                    long r3 = r9.getLong(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.Long r3 = java.lang.Long.valueOf(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                L_0x02b4:
                    java.lang.String r2 = "availability"
                    java.lang.String r3 = "availability"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    int r3 = r9.getInt(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "starting_price"
                    java.lang.String r3 = "starting_price"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r3 = r9.getString(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "offer_price"
                    java.lang.String r3 = "offer_price"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r3 = r9.getString(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "release_date"
                    java.lang.String r3 = "release_date"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r3 = r9.getString(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "item_count"
                    java.lang.String r3 = "item_count"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    int r3 = r9.getInt(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "live"
                    java.lang.String r3 = "live"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    int r3 = r9.getInt(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "internal_provider_id"
                    java.lang.String r3 = "internal_provider_id"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r3 = r9.getString(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "preview_video_uri"
                    java.lang.String r3 = "preview_video_uri"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r3 = r9.getString(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    r2 = r16
                    int r3 = r9.getColumnIndex(r2)     // Catch:{ all -> 0x0422 }
                    if (r3 == r8) goto L_0x0352
                    int r3 = r9.getColumnIndex(r2)     // Catch:{ all -> 0x0422 }
                    java.lang.String r3 = r9.getString(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                L_0x0352:
                    java.lang.String r2 = "last_playback_position_millis"
                    java.lang.String r3 = "last_playback_position_millis"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    int r3 = r9.getInt(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "duration_millis"
                    java.lang.String r3 = "duration_millis"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    int r3 = r9.getInt(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "intent_uri"
                    java.lang.String r3 = "intent_uri"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r3 = r9.getString(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "transient"
                    java.lang.String r3 = "transient"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    int r3 = r9.getInt(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "interaction_type"
                    java.lang.String r3 = "interaction_type"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    int r3 = r9.getInt(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "interaction_count"
                    java.lang.String r3 = "interaction_count"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    long r3 = r9.getLong(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.Long r3 = java.lang.Long.valueOf(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "author"
                    java.lang.String r3 = "author"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r3 = r9.getString(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "browsable"
                    java.lang.String r3 = "browsable"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    int r3 = r9.getInt(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r2 = "content_id"
                    java.lang.String r3 = "content_id"
                    int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x0422 }
                    java.lang.String r3 = r9.getString(r3)     // Catch:{ all -> 0x0422 }
                    r1.put(r2, r3)     // Catch:{ all -> 0x0422 }
                    r2 = r1
                    r1 = r23
                    com.google.android.tvlauncher.data.TvDataManager r3 = com.google.android.tvlauncher.data.TvDataManager.this     // Catch:{ all -> 0x004e }
                    android.content.Context r3 = r3.mContext     // Catch:{ all -> 0x004e }
                    android.content.ContentResolver r3 = r3.getContentResolver()     // Catch:{ all -> 0x004e }
                    android.net.Uri r4 = android.media.tv.TvContract.WatchNextPrograms.CONTENT_URI     // Catch:{ all -> 0x004e }
                    android.net.Uri r3 = r3.insert(r4, r2)     // Catch:{ all -> 0x004e }
                    long r14 = android.content.ContentUris.parseId(r3)     // Catch:{ all -> 0x004e }
                    com.google.android.tvlauncher.data.TvDataManager r4 = com.google.android.tvlauncher.data.TvDataManager.this     // Catch:{ all -> 0x004e }
                    android.content.Context r4 = r4.mContext     // Catch:{ all -> 0x004e }
                    java.lang.String r5 = "tv_input"
                    java.lang.Object r4 = r4.getSystemService(r5)     // Catch:{ all -> 0x004e }
                    r10 = r4
                    android.media.tv.TvInputManager r10 = (android.media.tv.TvInputManager) r10     // Catch:{ all -> 0x004e }
                    java.lang.String r11 = r6     // Catch:{ all -> 0x004e }
                    long r12 = r4     // Catch:{ all -> 0x004e }
                    r10.notifyPreviewProgramAddedToWatchNext(r11, r12, r14)     // Catch:{ all -> 0x004e }
                    goto L_0x0429
                L_0x0422:
                    r0 = move-exception
                    r1 = r23
                    goto L_0x004f
                L_0x0427:
                    r18 = r10
                L_0x0429:
                    java.lang.Boolean r0 = java.lang.Boolean.valueOf(r18)     // Catch:{ all -> 0x004e }
                    if (r9 == 0) goto L_0x0432
                    r9.close()
                L_0x0432:
                    return r0
                L_0x0433:
                    throw r2     // Catch:{ all -> 0x0434 }
                L_0x0434:
                    r0 = move-exception
                    r3 = r0
                    if (r9 == 0) goto L_0x0441
                    r9.close()     // Catch:{ all -> 0x043c }
                    goto L_0x0441
                L_0x043c:
                    r0 = move-exception
                    r4 = r0
                    com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r2, r4)
                L_0x0441:
                    throw r3
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.tvlauncher.data.TvDataManager.C12516.doInBackground(java.lang.Object[]):java.lang.Boolean");
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(Boolean hasProgram) {
                if (hasProgram.booleanValue()) {
                    TvDataManager.this.loadWatchNextProgramDataInternal();
                }
            }
        }.executeOnExecutor(Executors.getThreadPoolExecutor(), new Object[0]);
    }

    public ChannelOrderManager getChannelOrderManager() {
        if (isHomeChannelDataLoaded()) {
            return this.mChannelOrderManager;
        }
        throw new IllegalStateException("Home channel data not loaded yet");
    }

    private class DataSourceObserverCallbacks implements DataSourceObserver.Callbacks {
        private DataSourceObserverCallbacks() {
        }

        public void invalidateAllChannels() {
            if (TvDataManager.this.mHomeChannelsObservers.size() > 0) {
                TvDataManager.this.loadHomeChannelDataInternal();
            } else {
                boolean unused = TvDataManager.this.mHomeChannelsStale = true;
            }
            TvDataManager.this.loadPackageChannelsDataIfNeeded();
            if (TvDataManager.this.mPromoChannelObservers.size() > 0 && OemConfiguration.get(TvDataManager.this.mContext).getAppsPromotionRowPackage() != null) {
                TvDataManager.this.loadPromoChannelData();
            }
        }

        public void invalidateChannelLogo(long channelId) {
            TvDataManager.this.mChannelLogoUris.remove(Long.valueOf(channelId));
        }

        public void invalidatePackageChannels() {
            TvDataManager.this.loadPackageChannelsDataIfNeeded();
        }

        private void reloadProgramsOrMarkStale(Long channelId) {
            if (TvDataManager.this.mChannelProgramsObservers.containsKey(channelId) || TvDataManager.this.areChannelProgramsAlwaysCached(channelId)) {
                TvDataManager.this.loadHomeChannelProgramData(channelId.longValue());
            } else {
                TvDataManager.this.mStaleProgramsChannels.add(channelId);
            }
        }

        public void invalidateAllPrograms() {
            for (Long channelId : TvDataManager.this.mChannelPrograms.keySet()) {
                reloadProgramsOrMarkStale(channelId);
            }
            Set<Long> nonObservedBrowsableChannelIds = new HashSet<>(TvDataManager.this.mBrowsableChannels.keySet());
            nonObservedBrowsableChannelIds.removeAll(TvDataManager.this.mChannelProgramsObservers.keySet());
            TvDataManager.this.invalidateProgramsCountForBrowsableChannelsInternal(nonObservedBrowsableChannelIds);
        }

        public void invalidateProgramsCountForBrowsableChannels(Set<Long> channelIds) {
            TvDataManager.this.invalidateProgramsCountForBrowsableChannelsInternal(channelIds);
        }

        public void invalidateHomeChannelPrograms(long channelId) {
            if (TvDataManager.this.mChannelPrograms.containsKey(Long.valueOf(channelId))) {
                reloadProgramsOrMarkStale(Long.valueOf(channelId));
            }
        }

        public void invalidateWatchNextPrograms() {
            if (TvDataManager.this.mWatchNextProgramsObservers.size() > 0) {
                TvDataManager.this.loadWatchNextProgramDataInternal();
                TvDataManager.this.loadAllWatchNextProgramDataIntoCache();
                return;
            }
            boolean unused = TvDataManager.this.mWatchNextProgramsStale = true;
        }

        public boolean isChannelBrowsable(long channelId) {
            return TvDataManager.this.mBrowsableChannels.containsKey(Long.valueOf(channelId));
        }

        public Long findCachedChannelId(long programId) {
            return (Long) TvDataManager.this.mProgramChannelIds.get(Long.valueOf(programId));
        }

        public boolean areProgramsCached(long channelId) {
            return TvDataManager.this.mChannelPrograms.containsKey(Long.valueOf(channelId));
        }

        public Set<Long> getAllHomeChannelIds() {
            return TvDataManager.this.mHomeChannelIds;
        }
    }

    private class HomeChannelsBackgroundTaskResults {
        final List<HomeChannel> channels;
        final List<HomeChannel> pinnedChannels;
        final LongSparseArray<ChannelConfigurationInfo> pinnedChannelsConfig;
        final long sponsoredGoogleChannelId;
        final int sponsoredGoogleChannelOobPosition;

        HomeChannelsBackgroundTaskResults(TvDataManager tvDataManager, List<HomeChannel> channels2, long sponsoredGoogleChannelId2, int sponsoredGoogleChannelOobPosition2, List<HomeChannel> pinnedChannels2, LongSparseArray<ChannelConfigurationInfo> pinnedChannelsConfig2) {
            this.channels = channels2;
            this.sponsoredGoogleChannelId = sponsoredGoogleChannelId2;
            this.sponsoredGoogleChannelOobPosition = sponsoredGoogleChannelOobPosition2;
            this.pinnedChannels = pinnedChannels2;
            this.pinnedChannelsConfig = pinnedChannelsConfig2;
        }
    }

    private class PackageChannelsBackgroundTaskResults {
        final Map<String, List<Channel>> packageChannels;
        final List<ChannelPackage> packagesWithChannels;

        PackageChannelsBackgroundTaskResults(TvDataManager tvDataManager, List<ChannelPackage> packagesWithChannels2, Map<String, List<Channel>> packageChannels2) {
            this.packagesWithChannels = packagesWithChannels2;
            this.packageChannels = packageChannels2;
        }
    }
}
