package com.google.android.tvlauncher.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;

import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

class OemConfigurationData {
    protected static final String OEM_CONFIG_SHARED_PREFS = "oem_config";
    private static final String ALL_APPS_OUT_OF_BOX_ORDERING_PREFS_TAG = "all_apps_out_of_box_ordering";
    private static final String APPLY_STANDARD_STYLE_TO_INPUT_STATE_ICONS_PREFS_TAG = "apply_standard_style_to_input_state_icons";
    private static final String APPNAME_PACKAGE_MAP_TAG = "appname_package_map";
    private static final String APPS_VIEW_LAYOUT_OPTION_TAG = "apps_view_layout_option_tag";
    private static final String APP_CHANNEL_QUOTA_PREFS_TAG = "app_channel_quota";
    private static final String BUNDLED_TUNER_BANNER_PREFS_TAG = "bundled_tuner_banner";
    private static final String BUNDLED_TUNER_TITLE_PREFS_TAG = "bundled_tuner_title";
    private static final String CHANNELS_OUT_OF_BOX_ORDERING_PREFS_TAG = "channels_out_of_box_ordering";
    private static final String CONFIGURE_CHANNELS_APP_ORDERING_TAG = "configure_channels_app_ordering";
    private static final String CONTENT_PROVIDER_QUERY_COUNT_KEY = "content_provider_query_count";
    private static final int CONTENT_PROVIDER_QUERY_COUNT_LIMIT = 3;
    private static final String DISABLE_DISCONNECTED_INPUTS_PREFS_TAG = "disable_disconnected_inputs";
    private static final String ENABLE_INPUT_STATE_ICON_PREFS_TAG = "enable_input_state_icon";
    private static final String FAVORITE_APPS_OUT_OF_BOX_ORDERING_PREFS_TAG = "favorite_apps_out_of_box_ordering";
    private static final String FORCE_LAUNCH_PACKAGE_AFTER_BOOT_TAG = "force_launch_package_after_boot";
    private static final String GAMES_OUT_OF_BOX_ORDERING_PREFS_TAG = "games_out_of_box_ordering";
    private static final String HAS_SEEN_SPONSORED_CHANNELS_TAG = "has_seen_sponsored_channels";
    private static final String HEADSUP_NOTIFICATIONS_BACKGROUND_COLOR_PREFS_TAG = "headsup_notifications_background_color";
    private static final String HEADSUP_NOTIFICATIONS_FONT_PREFS_TAG = "headsup_notifications_font";
    private static final String HEADSUP_NOTIFICATIONS_LOCATION_PREFS_TAG = "headsup_notifications_location";
    private static final String HEADSUP_NOTIFICATIONS_TEXT_COLOR_PREFS_TAG = "headsup_notifications_text_color";
    private static final String HIDDEN_UNINSTALL_PACKAGE_LIST_PREFS_TAG = "hidden_uninstall_package_list";
    private static final String HOME_BACKGROUND_URI_PREFS_TAG = "home_background_uri";
    private static final String HOME_SCREEN_INPUTS_ORDERING_PREFS_TAG = "home_screen_inputs_ordering";
    private static final String INPUTS_LABEL_PREFS_TAG = "inputs_panel_label_option";
    private static final String LIVE_TV_CHANNEL_OUT_OF_BOX_PKG_NAME_PREFS_TAG = "live_tv_channel_out_of_box_package_name";
    private static final String LIVE_TV_CHANNEL_OUT_OF_BOX_POSITION_PREFS_TAG = "live_tv_channel_out_of_box_position";
    private static final String LIVE_TV_CHANNEL_OUT_OF_BOX_SYSTEM_CHANNEL_KEY_PREFS_TAG = "live_tv_channel_out_of_box_system_channel_key";
    private static final int LIVE_TV_OOB_PACKAGE_NO_POSITION = -1;
    private static final long LOAD_TASK_TIMEOUT = TimeUnit.SECONDS.toMillis(20);
    private static final String OEM_CONFIGURATION_PACKAGE_VERSION_TAG = "oem_config_package_ver";
    private static final String OEM_INPUTS_ICON_PREFS_TAG = "inputs_icon";
    private static final String PACKAGE_NAME_LAUNCH_AFTER_BOOT_TAG = "package_name_launch_after_boot";
    private static final String PACKAGE_NOTIFICATION_WHITELIST_PREFS_TAG = "package_notification_whitelist";
    private static final int PINNED_FAVORITE_APPS_LIMIT = 2;
    private static final String PINNED_FAVORITE_APPS_TAG = "pinned_favorite_apps";
    private static final String SEARCH_ICON_PREFS_TAG = "search_icon";
    private static final String SEARCH_ORB_FOCUSED_COLOR_TAG = "search_orb_focused_color";
    private static final String SEPARATOR = ",|,";
    private static final String SHOW_ADD_TO_WATCH_NEXT_FROM_PROGRAMS_MENU_PREFS_TAG = "show_add_to_watch_next_from_program_menu";
    private static final String SHOW_INPUTS_PREFS_TAG = "show_inputs";
    private static final String SHOW_PHYSICAL_INPUTS_SEPARATELY_PREFS_TAG = "show_physical_inputs_separately";
    private static final String SHOW_REMOVE_PROGRAM_FROM_PROGRAMS_MENU_PREFS_TAG = "show_remove_program_from_program_menu";
    private static final String SPLIT_DELIMITER = ",\\|,";
    private static final String TAG = "OemConfigurationData";
    private static final String TIME_STAMP_PREFS_TAG = "time_stamp";
    private static final String USE_CUSTOM_INPUT_LIST_PREFS_TAG = "use_custom_input_list";
    private static final String WATCH_NEXT_CHANNEL_AUTO_HIDE_ENABLED = "watch_next_channel_auto_hide_enabled_2";
    private static final String WATCH_NEXT_CHANNEL_ENABLED_BY_DEFAULT = "watch_next_channel_enabled_by_default";
    private final ArraySet<OemConfiguration.OnDataLoadedListener> mOnDataLoadedListeners = new ArraySet<>(1);
    protected Context mContext;
    private List<String> mAllAppsOutOfBoxOrdering;
    private HashMap<String, Integer> mAppChannelQuota;
    private HashMap<String, String> mAppNames;
    private boolean mApplyStandardStyleToInputStateIcons;
    private OemConfiguration.LayoutOrderOptions mAppsViewLayoutOption;
    private Uri mBundledTunerBannerUri;
    private String mBundledTunerTitle;
    private Map<String, ChannelConfigurationInfo> mChannelInfoMap;
    private List<ChannelConfigurationInfo> mChannelsOutOfBoxOrdering;
    private int mConfigurationPackageVersion;
    private List<String> mConfigureChannelsAppOrdering;
    private boolean mDisableDisconnectedInputs;
    private String mDisconnectedInputText;
    private boolean mEnableInputStateIcon;
    private List<String> mFavoriteAppsOutOfBoxOrdering;
    private boolean mForceLaunchPackageAfterBoot;
    private List<String> mGamesOutOfBoxOrdering;
    private boolean mHasSeenSponsoredChannelsForCurrentConfig;
    private boolean mHasSeenSponsoredChannelsSoFar;
    private int mHeadsupNotificationsBackgroundColor;
    private boolean mHeadsupNotificationsBackgroundColorWasSet;
    private String mHeadsupNotificationsFont;
    private String mHeadsupNotificationsLocation;
    private int mHeadsupNotificationsTextColor;
    private boolean mHeadsupNotificationsTextColorWasSet;
    private List<String> mHiddenUninstallPackageList;
    private String mHomeBackgroundUri;
    private List<String> mHomeScreenInputsOrdering;
    private String mInputsPanelLabelOption;
    private boolean mIsDataCachedInPrefs;
    private boolean mIsDataLoadingInProgress;
    private int mLatestOutOfBoxChannelPosition;
    private int mLiveTvChannelOobPosition;
    private ChannelConfigurationInfo mLiveTvOobPackageInfo;
    private Uri mOemInputsIconUri;
    private List<OemOutOfBoxApp> mOutOfBoxApps;
    private String mPackageNameLaunchAfterBoot;
    private List<String> mPackageNotificationWhitelist;
    private List<String> mPinnedFavoriteApps;
    private SharedPreferences mPrefs;
    private Uri mSearchIconUri;
    private int mSearchOrbFocusedColor;
    private boolean mSearchOrbFocusedColorWasSet;
    private boolean mShowAddToWatchNextFromProgramMenu;
    private boolean mShowInputs;
    private boolean mShowPhysicalInputsSeparately;
    private boolean mShowRemoveProgramFromProgramMenu;
    private List<ChannelConfigurationInfo> mSponsoredChannelsReadFromContentProvider;
    private boolean mUseCustomInputList;
    private boolean mWatchNextChannelAutoHideEnabled;
    private boolean mWatchNextChannelEnabledByDefault;

    OemConfigurationData(Context context, int configurationPackageVersion) {
        boolean forceReadFromContentProvider = true;
        init();
        this.mContext = context;
        this.mConfigurationPackageVersion = configurationPackageVersion;
        this.mIsDataCachedInPrefs = false;
        Context context2 = this.mContext;
        if (context2 != null) {
            this.mPrefs = context2.getSharedPreferences(OEM_CONFIG_SHARED_PREFS, 0);
            this.mIsDataCachedInPrefs = this.mPrefs.getLong(TIME_STAMP_PREFS_TAG, -1) != -1;
            this.mHasSeenSponsoredChannelsSoFar = this.mPrefs.getBoolean(HAS_SEEN_SPONSORED_CHANNELS_TAG, false);
            forceReadFromContentProvider = configurationPackageVersion <= this.mPrefs.getInt(OEM_CONFIGURATION_PACKAGE_VERSION_TAG, -1) ? false : forceReadFromContentProvider;
            if (this.mPrefs.getBoolean(NotifyRefreshOemConfigurationDataJobService.REFRESH_OEM_CONFIGURATION_DATA, false)) {
                this.mPrefs.edit().putBoolean(NotifyRefreshOemConfigurationDataJobService.REFRESH_OEM_CONFIGURATION_DATA, false).apply();
                forceReadFromContentProvider = true;
            }
            loadData(forceReadFromContentProvider);
        }
    }

    /* access modifiers changed from: package-private */
    public void refresh() {
        init();
        boolean z = false;
        this.mIsDataCachedInPrefs = false;
        if (this.mContext != null) {
            if (this.mPrefs.getLong(TIME_STAMP_PREFS_TAG, -1) != -1) {
                z = true;
            }
            this.mIsDataCachedInPrefs = z;
            loadData(true);
        }
    }

    private void init() {
        this.mHasSeenSponsoredChannelsForCurrentConfig = false;
        this.mSponsoredChannelsReadFromContentProvider = new ArrayList();
        this.mLatestOutOfBoxChannelPosition = -1;
        this.mHomeScreenInputsOrdering = new ArrayList(8);
        this.mPackageNotificationWhitelist = new ArrayList(5);
        this.mChannelsOutOfBoxOrdering = new ArrayList(10);
        this.mFavoriteAppsOutOfBoxOrdering = new ArrayList(8);
        this.mAllAppsOutOfBoxOrdering = new ArrayList(20);
        this.mGamesOutOfBoxOrdering = new ArrayList(10);
        this.mConfigureChannelsAppOrdering = new ArrayList(1);
        this.mPinnedFavoriteApps = new ArrayList(2);
        this.mHiddenUninstallPackageList = new ArrayList(5);
        this.mAppChannelQuota = new HashMap<>(20);
        this.mAppNames = new HashMap<>();
        this.mSearchIconUri = null;
        this.mSearchOrbFocusedColorWasSet = false;
        this.mSearchOrbFocusedColor = -1;
        this.mBundledTunerBannerUri = null;
        this.mBundledTunerTitle = null;
        this.mDisableDisconnectedInputs = true;
        this.mDisconnectedInputText = null;
        this.mEnableInputStateIcon = false;
        this.mApplyStandardStyleToInputStateIcons = true;
        this.mShowInputs = false;
        this.mInputsPanelLabelOption = OemConfiguration.INPUTS_PANEL_LABEL_INPUTS;
        this.mOemInputsIconUri = null;
        this.mShowPhysicalInputsSeparately = false;
        this.mUseCustomInputList = false;
        this.mHeadsupNotificationsFont = null;
        this.mHeadsupNotificationsTextColorWasSet = false;
        this.mHeadsupNotificationsBackgroundColorWasSet = false;
        this.mHeadsupNotificationsTextColor = -1;
        this.mHeadsupNotificationsBackgroundColor = -1;
        this.mHeadsupNotificationsLocation = null;
        this.mAppsViewLayoutOption = null;
        this.mForceLaunchPackageAfterBoot = false;
        this.mPackageNameLaunchAfterBoot = null;
        this.mLiveTvOobPackageInfo = null;
        this.mLiveTvChannelOobPosition = -1;
        this.mOutOfBoxApps = new ArrayList();
        this.mChannelInfoMap = new HashMap();
        this.mHomeBackgroundUri = null;
        this.mShowAddToWatchNextFromProgramMenu = true;
        this.mShowRemoveProgramFromProgramMenu = true;
        this.mWatchNextChannelEnabledByDefault = true;
        this.mWatchNextChannelAutoHideEnabled = true;
    }

    private void loadData(boolean forceReadFromContentProvider) {
        if (!this.mIsDataCachedInPrefs || forceReadFromContentProvider) {
            if (this.mIsDataCachedInPrefs) {
                readFromSharedPrefs(true);
            }
            OemConfigurationDataLoadingTask task = new OemConfigurationDataLoadingTask(this, this.mContext);
            task.executeOnExecutor(Executors.getThreadPoolExecutor(), new Void[0]);
            this.mIsDataLoadingInProgress = true;
            new Handler().postDelayed(new OemConfigurationData$$Lambda$0(this, task), LOAD_TASK_TIMEOUT);
            return;
        }
        readFromSharedPrefs(false);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$loadData$0$OemConfigurationData(OemConfigurationDataLoadingTask task) {
        if (task.getStatus() == AsyncTask.Status.RUNNING) {
            task.cancel(true);
            saveToSharedPrefs();
            onDataLoaded();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isDataLoaded() {
        return this.mIsDataCachedInPrefs && !this.mIsDataLoadingInProgress;
    }

    /* access modifiers changed from: package-private */
    public void setDataLoaded(boolean loaded) {
        this.mIsDataCachedInPrefs = loaded;
    }

    /* access modifiers changed from: package-private */
    public void registerOnDataLoadedListener(OemConfiguration.OnDataLoadedListener listener) {
        this.mOnDataLoadedListeners.add(listener);
    }

    /* access modifiers changed from: package-private */
    public void unregisterOnDataLoadedListener(OemConfiguration.OnDataLoadedListener listener) {
        this.mOnDataLoadedListeners.remove(listener);
    }

    /* access modifiers changed from: private */
    public void onDataLoaded() {
        this.mIsDataCachedInPrefs = true;
        this.mIsDataLoadingInProgress = false;
        resetContentProviderQueryCount();
        for (OemConfiguration.OnDataLoadedListener listener : new ArraySet<>((ArraySet) this.mOnDataLoadedListeners)) {
            listener.onDataLoaded();
        }
    }

    private void resetContentProviderQueryCount() {
        this.mPrefs.edit().putInt(CONTENT_PROVIDER_QUERY_COUNT_KEY, 0).apply();
    }

    /* access modifiers changed from: package-private */
    public Uri getSearchIconUri() {
        return this.mSearchIconUri;
    }

    /* access modifiers changed from: package-private */
    public void setSearchIconUri(Uri searchIconUri) {
        this.mSearchIconUri = searchIconUri;
    }

    /* access modifiers changed from: package-private */
    public int getSearchOrbFocusedColor(int defaultColor) {
        return this.mSearchOrbFocusedColorWasSet ? this.mSearchOrbFocusedColor : defaultColor;
    }

    /* access modifiers changed from: package-private */
    public void setSearchOrbFocusedColor(int focusedColor) {
        this.mSearchOrbFocusedColor = focusedColor;
        this.mSearchOrbFocusedColorWasSet = true;
    }

    /* access modifiers changed from: package-private */
    public Uri getBundledTunerBannerUri() {
        return this.mBundledTunerBannerUri;
    }

    /* access modifiers changed from: package-private */
    public void setBundledTunerBannerUri(Uri bundledTunnerBannerUri) {
        this.mBundledTunerBannerUri = bundledTunnerBannerUri;
    }

    /* access modifiers changed from: package-private */
    public String getBundledTunerTitle() {
        return this.mBundledTunerTitle;
    }

    /* access modifiers changed from: package-private */
    public void setBundledTunerTitle(String bundledTunnerTitle) {
        this.mBundledTunerTitle = bundledTunnerTitle;
    }

    /* access modifiers changed from: package-private */
    public boolean isDisableDisconnectedInputs() {
        return this.mDisableDisconnectedInputs;
    }

    /* access modifiers changed from: package-private */
    public void setDisableDisconnectedInputs(boolean disableDisconnectedInputs) {
        this.mDisableDisconnectedInputs = disableDisconnectedInputs;
    }

    /* access modifiers changed from: package-private */
    public String getDisconnectedInputText() {
        return this.mDisconnectedInputText;
    }

    /* access modifiers changed from: package-private */
    public void setDisconnectedInputText(String disconnectedInputText) {
        this.mDisconnectedInputText = disconnectedInputText;
    }

    /* access modifiers changed from: package-private */
    public boolean isEnableInputStateIcon() {
        return this.mEnableInputStateIcon;
    }

    /* access modifiers changed from: package-private */
    public void setEnableInputStateIcon(boolean enableInputStateIcon) {
        this.mEnableInputStateIcon = enableInputStateIcon;
    }

    /* access modifiers changed from: package-private */
    public Uri getOemInputsIconUri() {
        return this.mOemInputsIconUri;
    }

    /* access modifiers changed from: package-private */
    public void setOemInputsIconUri(Uri oemInputsIconUri) {
        this.mOemInputsIconUri = oemInputsIconUri;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldApplyStandardStyleToInputStateIcons() {
        return this.mApplyStandardStyleToInputStateIcons;
    }

    /* access modifiers changed from: package-private */
    public void setApplyStandardStyleToInputStateIcons(boolean applyStandardStyleToInputStateIcons) {
        this.mApplyStandardStyleToInputStateIcons = applyStandardStyleToInputStateIcons;
    }

    /* access modifiers changed from: package-private */
    public boolean isShowInputs() {
        return this.mShowInputs;
    }

    /* access modifiers changed from: package-private */
    public void setShowInputs(boolean showInputs) {
        this.mShowInputs = showInputs;
    }

    /* access modifiers changed from: package-private */
    public String getInputsPanelLabelOption() {
        return this.mInputsPanelLabelOption;
    }

    /* access modifiers changed from: package-private */
    public void setInputsPanelLabelOption(String inputsPanelLabelOption) {
        this.mInputsPanelLabelOption = inputsPanelLabelOption;
    }

    /* access modifiers changed from: package-private */
    public boolean isShowPhysicalInputsSeparately() {
        return this.mShowPhysicalInputsSeparately;
    }

    /* access modifiers changed from: package-private */
    public void setShowPhysicalInputsSeparately(boolean showPhysicalInputsSeparately) {
        this.mShowPhysicalInputsSeparately = showPhysicalInputsSeparately;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldUseCustomInputList() {
        return this.mUseCustomInputList;
    }

    /* access modifiers changed from: package-private */
    public void setUseCustomInputList(boolean useCustomInputList) {
        this.mUseCustomInputList = useCustomInputList;
    }

    /* access modifiers changed from: package-private */
    public String getHomeBackgroundUri() {
        return this.mHomeBackgroundUri;
    }

    /* access modifiers changed from: package-private */
    public void setHomeBackgroundUri(String homeBackgroundUri) {
        this.mHomeBackgroundUri = homeBackgroundUri;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldShowAddToWatchNextFromProgramMenu() {
        return this.mShowAddToWatchNextFromProgramMenu;
    }

    /* access modifiers changed from: package-private */
    public void setShowAddToWatchNextFromProgramMenu(boolean showAddToWatchNextFromProgramMenu) {
        this.mShowAddToWatchNextFromProgramMenu = showAddToWatchNextFromProgramMenu;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldShowRemoveProgramFromProgramMenu() {
        return this.mShowRemoveProgramFromProgramMenu;
    }

    /* access modifiers changed from: package-private */
    public void setShowRemoveProgramFromProgramMenu(boolean showRemoveProgramFromProgramMenu) {
        this.mShowRemoveProgramFromProgramMenu = showRemoveProgramFromProgramMenu;
    }

    /* access modifiers changed from: package-private */
    public boolean isWatchNextChannelEnabledByDefault() {
        return this.mWatchNextChannelEnabledByDefault;
    }

    /* access modifiers changed from: package-private */
    public void setWatchNextChannelEnabledByDefault(boolean watchNextChannelEnabledByDefault) {
        this.mWatchNextChannelEnabledByDefault = watchNextChannelEnabledByDefault;
    }

    /* access modifiers changed from: package-private */
    public boolean isWatchNextChannelAutoHideEnabled() {
        return this.mWatchNextChannelAutoHideEnabled;
    }

    /* access modifiers changed from: package-private */
    public void setWatchNextChannelAutoHideEnabled(boolean watchNextChannelAutoHideEnabled) {
        this.mWatchNextChannelAutoHideEnabled = watchNextChannelAutoHideEnabled;
    }

    /* access modifiers changed from: package-private */
    public void addInputToHomeScreenInputsOrdering(String input) {
        if (!this.mHomeScreenInputsOrdering.contains(input)) {
            this.mHomeScreenInputsOrdering.add(input);
        }
    }

    /* access modifiers changed from: package-private */
    public List<String> getHomeScreenInputsOrdering() {
        return this.mHomeScreenInputsOrdering;
    }

    /* access modifiers changed from: package-private */
    public void addAppToVirtualAppsOutOfBoxOrdering(OemOutOfBoxApp app) {
        addAppToVirtualAppsOutOfBoxOrdering(app, true);
    }

    private void addAppToVirtualAppsOutOfBoxOrdering(OemOutOfBoxApp app, boolean readFromConfigFile) {
        if ((!readFromConfigFile || !this.mIsDataCachedInPrefs) && app.isVirtualApp() && !TextUtils.isEmpty(app.getAppName()) && !TextUtils.isEmpty(app.getPackageName()) && !TextUtils.isEmpty(app.getBannerUri()) && !TextUtils.isEmpty(app.getDataUri())) {
            this.mOutOfBoxApps.add(app);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean addAppToFavoriteAppsOutOfBoxOrdering(String appName, String packageName) {
        return addAppToFavoriteAppsOutOfBoxOrdering(appName, packageName, true);
    }

    private boolean addAppToFavoriteAppsOutOfBoxOrdering(String appName, String packageName, boolean readFromConfigFile) {
        if ((readFromConfigFile && this.mIsDataCachedInPrefs) || this.mFavoriteAppsOutOfBoxOrdering.contains(packageName) || this.mPinnedFavoriteApps.contains(packageName)) {
            return false;
        }
        this.mFavoriteAppsOutOfBoxOrdering.add(packageName);
        this.mAppNames.put(packageName, appName);
        return true;
    }

    /* access modifiers changed from: package-private */
    public List<String> getFavoriteAppsOutOfBoxOrdering() {
        return this.mFavoriteAppsOutOfBoxOrdering;
    }

    /* access modifiers changed from: package-private */
    public void addAppToAllAppsOutOfBoxOrdering(String appName, String packageName) {
        addAppToAllAppsOutOfBoxOrdering(appName, packageName, true);
    }

    private void addAppToAllAppsOutOfBoxOrdering(String appName, String packageName, boolean readFromConfigFile) {
        if ((!readFromConfigFile || !this.mIsDataCachedInPrefs) && !this.mAllAppsOutOfBoxOrdering.contains(packageName)) {
            this.mAllAppsOutOfBoxOrdering.add(packageName);
            this.mAppNames.put(packageName, appName);
        }
    }

    /* access modifiers changed from: package-private */
    public List<String> getAllAppsOutOfBoxOrdering() {
        return this.mAllAppsOutOfBoxOrdering;
    }

    /* access modifiers changed from: package-private */
    public void addAppToGamesOutOfBoxOrdering(String appName, String packageName) {
        addAppToGamesOutOfBoxOrdering(appName, packageName, true);
    }

    private void addAppToGamesOutOfBoxOrdering(String appName, String packageName, boolean readFromConfigFile) {
        if ((!readFromConfigFile || !this.mIsDataCachedInPrefs) && !this.mGamesOutOfBoxOrdering.contains(packageName)) {
            this.mGamesOutOfBoxOrdering.add(packageName);
            this.mAppNames.put(packageName, appName);
        }
    }

    /* access modifiers changed from: package-private */
    public List<String> getGamesOutOfBoxOrdering() {
        return this.mGamesOutOfBoxOrdering;
    }

    /* access modifiers changed from: package-private */
    public void addAppToAppChannelQuota(String packageName, int quota) {
        this.mAppChannelQuota.put(packageName, Integer.valueOf(quota));
    }

    /* access modifiers changed from: package-private */
    public int getAppChannelQuota(String packageName) {
        if (this.mAppChannelQuota.containsKey(packageName)) {
            return this.mAppChannelQuota.get(packageName).intValue();
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public void addAppToConfigureChannelsAppOrdering(String packageName) {
        addAppToConfigureChannelsAppOrdering(packageName, true);
    }

    private void addAppToConfigureChannelsAppOrdering(String packageName, boolean readFromConfigFile) {
        if ((!readFromConfigFile || !this.mIsDataCachedInPrefs) && !this.mConfigureChannelsAppOrdering.contains(packageName)) {
            this.mConfigureChannelsAppOrdering.add(packageName);
        }
    }

    /* access modifiers changed from: package-private */
    public List<String> getConfigureChannelsAppOrdering() {
        return this.mConfigureChannelsAppOrdering;
    }

    /* access modifiers changed from: package-private */
    public void addChannelToOutOfBoxOrdering(@NonNull ChannelConfigurationInfo.Builder channelConfigurationInfoBuilder) {
        addChannelToOutOfBoxOrdering(channelConfigurationInfoBuilder, true);
    }

    private void addChannelToOutOfBoxOrdering(@NonNull ChannelConfigurationInfo.Builder channelConfigurationInfoBuilder, boolean readFromConfigFile) {
        boolean isRereadFromConfigFile = readFromConfigFile && this.mIsDataCachedInPrefs;
        if (!isRereadFromConfigFile || !this.mHasSeenSponsoredChannelsSoFar) {
            if (channelConfigurationInfoBuilder.isSponsored()) {
                this.mHasSeenSponsoredChannelsForCurrentConfig = true;
            }
            if (isRereadFromConfigFile) {
                reReadOutOfBoxChannelFromConfigFile(channelConfigurationInfoBuilder);
            } else if (TextUtils.isEmpty(channelConfigurationInfoBuilder.getSystemChannelKey()) || !this.mChannelInfoMap.containsKey(ChannelConfigurationInfo.getUniqueKey(channelConfigurationInfoBuilder.getPackageName(), channelConfigurationInfoBuilder.getSystemChannelKey()))) {
                ChannelConfigurationInfo channelConfigurationInfo = this.mLiveTvOobPackageInfo;
                if (channelConfigurationInfo == null || !TextUtils.equals(channelConfigurationInfo.getPackageName(), channelConfigurationInfoBuilder.getPackageName())) {
                    int i = this.mLatestOutOfBoxChannelPosition + 1;
                    this.mLatestOutOfBoxChannelPosition = i;
                    channelConfigurationInfoBuilder.setChannelPosition(i);
                    ChannelConfigurationInfo channelConfigurationInfo2 = channelConfigurationInfoBuilder.build();
                    this.mChannelsOutOfBoxOrdering.add(channelConfigurationInfo2);
                    if (channelConfigurationInfo2.isSponsored()) {
                        this.mSponsoredChannelsReadFromContentProvider.add(channelConfigurationInfo2);
                    }
                    addChannelToChannelInfoMap(channelConfigurationInfo2);
                    return;
                }
                Log.e(TAG, "Live TV OOB channel order has already been defined.");
            } else {
                String packageName = channelConfigurationInfoBuilder.getPackageName();
                String systemChannelKey = channelConfigurationInfoBuilder.getSystemChannelKey();
                StringBuilder sb = new StringBuilder(String.valueOf(packageName).length() + 92 + String.valueOf(systemChannelKey).length());
                sb.append("channel with package = ");
                sb.append(packageName);
                sb.append(" with system_channel_key = ");
                sb.append(systemChannelKey);
                sb.append(" has already appeared in out-of-box order.");
                Log.e(TAG, sb.toString());
            }
        }
    }

    private void reReadOutOfBoxChannelFromConfigFile(@NonNull ChannelConfigurationInfo.Builder channelConfigurationInfoBuilder) {
        int channelPos;
        if (channelConfigurationInfoBuilder.isSponsored()) {
            if (this.mChannelInfoMap.containsKey(ChannelConfigurationInfo.getUniqueKey(channelConfigurationInfoBuilder.getPackageName(), channelConfigurationInfoBuilder.getSystemChannelKey()))) {
                channelPos = this.mChannelsOutOfBoxOrdering.indexOf(channelConfigurationInfoBuilder.build());
            } else if (this.mChannelInfoMap.containsKey(ChannelConfigurationInfo.getUniqueKey(channelConfigurationInfoBuilder.getPackageName(), null))) {
                channelPos = this.mChannelsOutOfBoxOrdering.indexOf(new ChannelConfigurationInfo.Builder().setPackageName(channelConfigurationInfoBuilder.getPackageName()).setSystemChannelKey(null).build());
            } else {
                String packageName = channelConfigurationInfoBuilder.getPackageName();
                String systemChannelKey = channelConfigurationInfoBuilder.getSystemChannelKey();
                StringBuilder sb = new StringBuilder(String.valueOf(packageName).length() + ClientAnalytics.LogRequest.LogSource.FLYDROID_COUNTERS_VALUE + String.valueOf(systemChannelKey).length());
                sb.append("The sponsored channel is skipped because it did not exist before in the previous configuration with the same package name: ");
                sb.append(packageName);
                sb.append(" and system_channel_key: ");
                sb.append(systemChannelKey);
                Log.e(TAG, sb.toString());
                return;
            }
            if (channelPos != -1) {
                ChannelConfigurationInfo channelConfigurationInfo = channelConfigurationInfoBuilder.setChannelPosition(channelPos).build();
                this.mChannelsOutOfBoxOrdering.set(channelPos, channelConfigurationInfo);
                addChannelToChannelInfoMap(channelConfigurationInfo);
                this.mSponsoredChannelsReadFromContentProvider.add(channelConfigurationInfo);
                return;
            }
            String packageName2 = channelConfigurationInfoBuilder.getPackageName();
            String systemChannelKey2 = channelConfigurationInfoBuilder.getSystemChannelKey();
            StringBuilder sb2 = new StringBuilder(String.valueOf(packageName2).length() + 105 + String.valueOf(systemChannelKey2).length());
            sb2.append("The sponsored channel with package name: ");
            sb2.append(packageName2);
            sb2.append(" and system_channel_key: ");
            sb2.append(systemChannelKey2);
            sb2.append(" does not exist in out of box ordering.");
            throw new IllegalStateException(sb2.toString());
        }
    }

    /* access modifiers changed from: package-private */
    public List<ChannelConfigurationInfo> getSponsoredChannelsReadFromContentProvider() {
        return this.mSponsoredChannelsReadFromContentProvider;
    }

    /* access modifiers changed from: package-private */
    public List<ChannelConfigurationInfo> getChannelsOutOfBoxOrdering() {
        return this.mChannelsOutOfBoxOrdering;
    }

    private void addChannelToChannelInfoMap(@NonNull ChannelConfigurationInfo channelConfigurationInfo) {
        this.mChannelInfoMap.put(ChannelConfigurationInfo.getUniqueKey(channelConfigurationInfo.getPackageName(), channelConfigurationInfo.getSystemChannelKey()), channelConfigurationInfo);
    }

    /* access modifiers changed from: package-private */
    public ChannelConfigurationInfo getChannelInfo(@NonNull String packageName, String systemChannelKey) {
        return this.mChannelInfoMap.get(ChannelConfigurationInfo.getUniqueKey(packageName, systemChannelKey));
    }

    /* access modifiers changed from: package-private */
    public void addPackageToPackageNotificationWhitelist(String packageName) {
        if (!this.mPackageNotificationWhitelist.contains(packageName)) {
            this.mPackageNotificationWhitelist.add(packageName);
        }
    }

    /* access modifiers changed from: package-private */
    public List<String> getPackageToPackageNotificationWhitelist() {
        return this.mPackageNotificationWhitelist;
    }

    /* access modifiers changed from: package-private */
    public String getHeadsupNotificationsLocation() {
        return this.mHeadsupNotificationsLocation;
    }

    /* access modifiers changed from: package-private */
    public void setHeadsupNotificationsLocation(String headsupNotificationsLocation) {
        this.mHeadsupNotificationsLocation = headsupNotificationsLocation;
    }

    /* access modifiers changed from: package-private */
    public String getHeadsupNotificationsFont() {
        return this.mHeadsupNotificationsFont;
    }

    /* access modifiers changed from: package-private */
    public void setHeadsupNotificationsFont(String headsupNotificationsFont) {
        this.mHeadsupNotificationsFont = headsupNotificationsFont;
    }

    /* access modifiers changed from: package-private */
    public int getHeadsupNotificationsTextColor(int defaultColor) {
        if (this.mHeadsupNotificationsTextColorWasSet) {
            return this.mHeadsupNotificationsTextColor;
        }
        return defaultColor;
    }

    /* access modifiers changed from: package-private */
    public void setHeadsupNotificationsTextColor(int headsupNotificationsTextColor) {
        this.mHeadsupNotificationsTextColor = headsupNotificationsTextColor;
        this.mHeadsupNotificationsTextColorWasSet = true;
    }

    /* access modifiers changed from: package-private */
    public int getHeadsupNotificationsBackgroundColor(int defaultColor) {
        if (this.mHeadsupNotificationsBackgroundColorWasSet) {
            return this.mHeadsupNotificationsBackgroundColor;
        }
        return defaultColor;
    }

    /* access modifiers changed from: package-private */
    public void setHeadsupNotificationsBackgroundColor(int headsupNotificationsBackgroundColor) {
        this.mHeadsupNotificationsBackgroundColor = headsupNotificationsBackgroundColor;
        this.mHeadsupNotificationsBackgroundColorWasSet = true;
    }

    /* access modifiers changed from: package-private */
    public String getAppNameByPackageName(String packageName) {
        return this.mAppNames.get(packageName);
    }

    /* access modifiers changed from: package-private */
    public OemConfiguration.LayoutOrderOptions getAppsViewLayoutOption() {
        return this.mAppsViewLayoutOption;
    }

    /* access modifiers changed from: package-private */
    public void setAppsViewLayoutOption(OemConfiguration.LayoutOrderOptions option) {
        setAppsViewLayoutOption(option, true);
    }

    private void setAppsViewLayoutOption(OemConfiguration.LayoutOrderOptions option, boolean readFromConfigFile) {
        if (!readFromConfigFile || !this.mIsDataCachedInPrefs) {
            this.mAppsViewLayoutOption = option;
        }
    }

    /* access modifiers changed from: package-private */
    public String getPackageNameLaunchAfterBoot() {
        return this.mPackageNameLaunchAfterBoot;
    }

    /* access modifiers changed from: package-private */
    public void setPackageNameLaunchAfterBoot(String packageName) {
        this.mPackageNameLaunchAfterBoot = packageName;
    }

    /* access modifiers changed from: package-private */
    public void setLiveTvPackageInfo(String packageName, String systemChannelKey) {
        setLiveTvPackageInfo(packageName, systemChannelKey, true);
    }

    private void setLiveTvPackageInfo(String packageName, String systemChannelKey, boolean readFromConfigFile) {
        if (!readFromConfigFile || !this.mIsDataCachedInPrefs) {
            for (ChannelConfigurationInfo oobPackageInfo : this.mChannelsOutOfBoxOrdering) {
                if (oobPackageInfo.getPackageName().equals(packageName)) {
                    Log.e(TAG, "Live TV Package cannot be declared in both live channel out-of-box ordering and channels out-of-box ordering.");
                    return;
                }
            }
            this.mLiveTvOobPackageInfo = new ChannelConfigurationInfo.Builder().setPackageName(packageName).setSystemChannelKey(systemChannelKey).build();
        }
    }

    /* access modifiers changed from: package-private */
    public ChannelConfigurationInfo getLiveTvOobPackageInfo() {
        return this.mLiveTvOobPackageInfo;
    }

    private void setLiveTvChannelOobPosition(int position, boolean readFromConfigFile) {
        if (!readFromConfigFile || !this.mIsDataCachedInPrefs) {
            this.mLiveTvChannelOobPosition = position;
        }
    }

    /* access modifiers changed from: package-private */
    public int getLiveTvChannelOobPosition() {
        return this.mLiveTvChannelOobPosition;
    }

    /* access modifiers changed from: package-private */
    public void setLiveTvChannelOobPosition(int position) {
        setLiveTvChannelOobPosition(position, true);
    }

    /* access modifiers changed from: package-private */
    public boolean getForceLaunchPackageAfterBoot() {
        return this.mForceLaunchPackageAfterBoot;
    }

    /* access modifiers changed from: package-private */
    public void setForceLaunchPackageAfterBoot(boolean force) {
        this.mForceLaunchPackageAfterBoot = force;
    }

    public List<OemOutOfBoxApp> getVirtualOutOfBoxApps() {
        return this.mOutOfBoxApps;
    }

    /* access modifiers changed from: package-private */
    public boolean addAppToPinnedFavoriteApps(String appName, String packageName) {
        if (this.mPinnedFavoriteApps.contains(packageName) || this.mFavoriteAppsOutOfBoxOrdering.contains(packageName) || this.mPinnedFavoriteApps.size() >= 2) {
            return false;
        }
        this.mPinnedFavoriteApps.add(packageName);
        this.mAppNames.put(packageName, appName);
        return true;
    }

    /* access modifiers changed from: package-private */
    public List<String> getPinnedFavoriteApps() {
        return this.mPinnedFavoriteApps;
    }

    /* access modifiers changed from: package-private */
    public void addPackageToHiddenUninstallList(String packageName) {
        if (!this.mHiddenUninstallPackageList.contains(packageName)) {
            this.mHiddenUninstallPackageList.add(packageName);
        }
    }

    /* access modifiers changed from: package-private */
    public List<String> getHiddenUninstallPackageList() {
        return this.mHiddenUninstallPackageList;
    }

    /* JADX INFO: Multiple debug info for r0v56 java.lang.String: [D('packageName' java.lang.String), D('pinnedFavoriteApps' java.lang.String)] */
    /* JADX INFO: Multiple debug info for r2v54 java.lang.String: [D('packageName' java.lang.String), D('whitelist' java.lang.String)] */
    /* JADX INFO: Multiple debug info for r3v25 java.lang.String: [D('input' java.lang.String), D('liveTvChannelPackageName' java.lang.String)] */
    private void readFromSharedPrefs(boolean readOnlyImmutableFields) {
        int i;
        String quotas;
        String liveTvChannelPackageName = this.mPrefs.getString(LIVE_TV_CHANNEL_OUT_OF_BOX_PKG_NAME_PREFS_TAG, null);
        if (liveTvChannelPackageName != null) {
            setLiveTvPackageInfo(liveTvChannelPackageName, this.mPrefs.getString(LIVE_TV_CHANNEL_OUT_OF_BOX_SYSTEM_CHANNEL_KEY_PREFS_TAG, null), false);
            setLiveTvChannelOobPosition(this.mPrefs.getInt(LIVE_TV_CHANNEL_OUT_OF_BOX_POSITION_PREFS_TAG, -1), false);
        }
        String channelOrdering = this.mPrefs.getString(CHANNELS_OUT_OF_BOX_ORDERING_PREFS_TAG, null);
        if (!TextUtils.isEmpty(channelOrdering)) {
            List<ChannelConfigurationInfo.Builder> channelConfigurationInfoBuilderList = ChannelConfigurationInfo.fromJsonArrayString(channelOrdering);
            if (channelConfigurationInfoBuilderList.size() > 0) {
                for (ChannelConfigurationInfo.Builder channelConfigurationInfoBuilder : channelConfigurationInfoBuilderList) {
                    addChannelToOutOfBoxOrdering(channelConfigurationInfoBuilder, false);
                }
            } else {
                upgradeChannelOrderingFromCathode(channelOrdering);
            }
        }
        Map<String, String> packageToAppName = new HashMap<>();
        String namesMap = this.mPrefs.getString(APPNAME_PACKAGE_MAP_TAG, null);
        if (namesMap != null) {
            String[] namesMapArray = namesMap.split(SPLIT_DELIMITER);
            if (namesMapArray.length > 1) {
                for (int i2 = 0; i2 < namesMapArray.length; i2 += 2) {
                    packageToAppName.put(namesMapArray[i2], namesMapArray[i2 + 1]);
                }
            }
        }
        String favoritesOrdering = this.mPrefs.getString(FAVORITE_APPS_OUT_OF_BOX_ORDERING_PREFS_TAG, null);
        if (favoritesOrdering != null) {
            for (String packageName : favoritesOrdering.split(SPLIT_DELIMITER)) {
                String appName = (String) packageToAppName.get(packageName);
                if (appName != null) {
                    addAppToFavoriteAppsOutOfBoxOrdering(appName, packageName, false);
                }
            }
        }
        String allAppsOrdering = this.mPrefs.getString(ALL_APPS_OUT_OF_BOX_ORDERING_PREFS_TAG, null);
        if (allAppsOrdering != null) {
            for (String packageName2 : allAppsOrdering.split(SPLIT_DELIMITER)) {
                String appName2 = (String) packageToAppName.get(packageName2);
                if (appName2 != null) {
                    addAppToAllAppsOutOfBoxOrdering(appName2, packageName2, false);
                }
            }
        }
        String gamesOrdering = this.mPrefs.getString(GAMES_OUT_OF_BOX_ORDERING_PREFS_TAG, null);
        if (gamesOrdering != null) {
            for (String packageName3 : gamesOrdering.split(SPLIT_DELIMITER)) {
                String appName3 = (String) packageToAppName.get(packageName3);
                if (appName3 != null) {
                    addAppToGamesOutOfBoxOrdering(appName3, packageName3, false);
                }
            }
        }
        String configureChannelsAppOrdering = this.mPrefs.getString(CONFIGURE_CHANNELS_APP_ORDERING_TAG, null);
        if (configureChannelsAppOrdering != null) {
            for (String packageName4 : configureChannelsAppOrdering.split(SPLIT_DELIMITER)) {
                addAppToConfigureChannelsAppOrdering(packageName4, false);
            }
        }
        setAppsViewLayoutOption(OemConfiguration.LayoutOrderOptions.getLayoutOptionForRowOrder(this.mPrefs.getString(APPS_VIEW_LAYOUT_OPTION_TAG, null)), false);
        if (!readOnlyImmutableFields) {
            String searchIconUri = this.mPrefs.getString(SEARCH_ICON_PREFS_TAG, null);
            setSearchIconUri(searchIconUri != null ? Uri.parse(searchIconUri) : null);
            if (this.mPrefs.contains(SEARCH_ORB_FOCUSED_COLOR_TAG)) {
                setSearchOrbFocusedColor(this.mPrefs.getInt(SEARCH_ORB_FOCUSED_COLOR_TAG, 0));
            }
            String inputsIconUri = this.mPrefs.getString(OEM_INPUTS_ICON_PREFS_TAG, null);
            setOemInputsIconUri(inputsIconUri != null ? Uri.parse(inputsIconUri) : null);
            String tunerBannerUri = this.mPrefs.getString(BUNDLED_TUNER_BANNER_PREFS_TAG, null);
            setBundledTunerBannerUri(tunerBannerUri != null ? Uri.parse(tunerBannerUri) : null);
            setBundledTunerTitle(this.mPrefs.getString(BUNDLED_TUNER_TITLE_PREFS_TAG, null));
            if (this.mPrefs.contains(DISABLE_DISCONNECTED_INPUTS_PREFS_TAG)) {
                setDisableDisconnectedInputs(this.mPrefs.getBoolean(DISABLE_DISCONNECTED_INPUTS_PREFS_TAG, true));
            }
            if (this.mPrefs.contains(ENABLE_INPUT_STATE_ICON_PREFS_TAG)) {
                setEnableInputStateIcon(this.mPrefs.getBoolean(ENABLE_INPUT_STATE_ICON_PREFS_TAG, false));
            }
            if (this.mPrefs.contains(APPLY_STANDARD_STYLE_TO_INPUT_STATE_ICONS_PREFS_TAG)) {
                setApplyStandardStyleToInputStateIcons(this.mPrefs.getBoolean(APPLY_STANDARD_STYLE_TO_INPUT_STATE_ICONS_PREFS_TAG, true));
            }
            if (this.mPrefs.contains(SHOW_INPUTS_PREFS_TAG)) {
                setShowInputs(this.mPrefs.getBoolean(SHOW_INPUTS_PREFS_TAG, false));
            }
            if (this.mPrefs.contains(INPUTS_LABEL_PREFS_TAG)) {
                setInputsPanelLabelOption(this.mPrefs.getString(INPUTS_LABEL_PREFS_TAG, null));
            }
            if (this.mPrefs.contains(SHOW_PHYSICAL_INPUTS_SEPARATELY_PREFS_TAG)) {
                setShowPhysicalInputsSeparately(this.mPrefs.getBoolean(SHOW_PHYSICAL_INPUTS_SEPARATELY_PREFS_TAG, false));
            }
            if (this.mPrefs.contains(USE_CUSTOM_INPUT_LIST_PREFS_TAG)) {
                setUseCustomInputList(this.mPrefs.getBoolean(USE_CUSTOM_INPUT_LIST_PREFS_TAG, false));
            }
            String inputOrdering = this.mPrefs.getString(HOME_SCREEN_INPUTS_ORDERING_PREFS_TAG, null);
            if (inputOrdering != null) {
                String[] inputOrderingArray = inputOrdering.split(SPLIT_DELIMITER);
                int length = inputOrderingArray.length;
                int i3 = 0;
                while (i3 < length) {
                    addInputToHomeScreenInputsOrdering(inputOrderingArray[i3]);
                    i3++;
                    liveTvChannelPackageName = liveTvChannelPackageName;
                }
            }
            String whitelist = this.mPrefs.getString(PACKAGE_NOTIFICATION_WHITELIST_PREFS_TAG, null);
            if (whitelist != null) {
                String[] whitelistArray = whitelist.split(SPLIT_DELIMITER);
                int length2 = whitelistArray.length;
                int i4 = 0;
                while (i4 < length2) {
                    addPackageToPackageNotificationWhitelist(whitelistArray[i4]);
                    i4++;
                    whitelist = whitelist;
                }
            }
            String quotas2 = this.mPrefs.getString(APP_CHANNEL_QUOTA_PREFS_TAG, null);
            if (quotas2 != null) {
                String[] quotasArray = quotas2.split(SPLIT_DELIMITER);
                if (quotasArray.length > 1) {
                    int i5 = 0;
                    while (i5 < quotasArray.length) {
                        try {
                            quotas = quotas2;
                            try {
                                addAppToAppChannelQuota(quotasArray[i5], Integer.decode(quotasArray[i5 + 1]).intValue());
                            } catch (NumberFormatException e) {
                                e = e;
                            }
                        } catch (NumberFormatException e2) {
                            e = e2;
                            quotas = quotas2;
                            String valueOf = String.valueOf(quotasArray[i5 + 1]);
                            Log.e(TAG, valueOf.length() != 0 ? "Bad quota number: ".concat(valueOf) : new String("Bad quota number: "));
                            i5 += 2;
                            quotas2 = quotas;
                        }
                        i5 += 2;
                        quotas2 = quotas;
                    }
                }
            }
            setHeadsupNotificationsFont(this.mPrefs.getString(HEADSUP_NOTIFICATIONS_FONT_PREFS_TAG, null));
            if (this.mPrefs.contains(HEADSUP_NOTIFICATIONS_TEXT_COLOR_PREFS_TAG)) {
                setHeadsupNotificationsTextColor(this.mPrefs.getInt(HEADSUP_NOTIFICATIONS_TEXT_COLOR_PREFS_TAG, 0));
            }
            if (this.mPrefs.contains(HEADSUP_NOTIFICATIONS_BACKGROUND_COLOR_PREFS_TAG)) {
                setHeadsupNotificationsBackgroundColor(this.mPrefs.getInt(HEADSUP_NOTIFICATIONS_BACKGROUND_COLOR_PREFS_TAG, 0));
            }
            setHeadsupNotificationsLocation(this.mPrefs.getString(HEADSUP_NOTIFICATIONS_LOCATION_PREFS_TAG, null));
            if (this.mPrefs.contains(FORCE_LAUNCH_PACKAGE_AFTER_BOOT_TAG)) {
                i = 0;
                setForceLaunchPackageAfterBoot(this.mPrefs.getBoolean(FORCE_LAUNCH_PACKAGE_AFTER_BOOT_TAG, false));
            } else {
                i = 0;
            }
            setPackageNameLaunchAfterBoot(this.mPrefs.getString(PACKAGE_NAME_LAUNCH_AFTER_BOOT_TAG, null));
            String pinnedFavoriteApps = this.mPrefs.getString(PINNED_FAVORITE_APPS_TAG, null);
            if (pinnedFavoriteApps != null) {
                String[] pinnedFavoriteAppsArray = pinnedFavoriteApps.split(SPLIT_DELIMITER);
                int length3 = pinnedFavoriteAppsArray.length;
                while (i < length3) {
                    String pinnedFavoriteApps2 = pinnedFavoriteApps;
                    String pinnedFavoriteApps3 = pinnedFavoriteAppsArray[i];
                    String[] pinnedFavoriteAppsArray2 = pinnedFavoriteAppsArray;
                    String appName4 = (String) packageToAppName.get(pinnedFavoriteApps3);
                    if (appName4 != null) {
                        addAppToPinnedFavoriteApps(appName4, pinnedFavoriteApps3);
                    }
                    i++;
                    pinnedFavoriteApps = pinnedFavoriteApps2;
                    pinnedFavoriteAppsArray = pinnedFavoriteAppsArray2;
                }
            }
            String hiddenUninstallList = this.mPrefs.getString(HIDDEN_UNINSTALL_PACKAGE_LIST_PREFS_TAG, null);
            if (hiddenUninstallList != null) {
                for (String packageName5 : hiddenUninstallList.split(SPLIT_DELIMITER)) {
                    addPackageToHiddenUninstallList(packageName5);
                }
            }
            if (this.mPrefs.contains(HOME_BACKGROUND_URI_PREFS_TAG)) {
                this.mHomeBackgroundUri = this.mPrefs.getString(HOME_BACKGROUND_URI_PREFS_TAG, null);
            }
            if (this.mPrefs.contains(SHOW_ADD_TO_WATCH_NEXT_FROM_PROGRAMS_MENU_PREFS_TAG)) {
                this.mShowAddToWatchNextFromProgramMenu = this.mPrefs.getBoolean(SHOW_ADD_TO_WATCH_NEXT_FROM_PROGRAMS_MENU_PREFS_TAG, true);
            }
            if (this.mPrefs.contains(SHOW_REMOVE_PROGRAM_FROM_PROGRAMS_MENU_PREFS_TAG)) {
                this.mShowRemoveProgramFromProgramMenu = this.mPrefs.getBoolean(SHOW_REMOVE_PROGRAM_FROM_PROGRAMS_MENU_PREFS_TAG, true);
            }
            if (this.mPrefs.contains(WATCH_NEXT_CHANNEL_ENABLED_BY_DEFAULT)) {
                this.mWatchNextChannelEnabledByDefault = this.mPrefs.getBoolean(WATCH_NEXT_CHANNEL_ENABLED_BY_DEFAULT, true);
            }
            if (this.mPrefs.contains(WATCH_NEXT_CHANNEL_AUTO_HIDE_ENABLED)) {
                this.mWatchNextChannelAutoHideEnabled = this.mPrefs.getBoolean(WATCH_NEXT_CHANNEL_AUTO_HIDE_ENABLED, true);
            }
        }
    }

    private void upgradeChannelOrderingFromCathode(String channelOrdering) {
        String systemChannelKey;
        String packageName;
        for (String channelInfo : channelOrdering.split(SPLIT_DELIMITER)) {
            int colonIndex = channelInfo.indexOf(":");
            if (colonIndex != -1) {
                packageName = channelInfo.substring(0, colonIndex);
                systemChannelKey = channelInfo.substring(colonIndex + 1);
            } else {
                packageName = channelInfo;
                systemChannelKey = null;
            }
            addChannelToOutOfBoxOrdering(new ChannelConfigurationInfo.Builder().setPackageName(packageName).setSystemChannelKey(systemChannelKey), false);
        }
        this.mPrefs.edit().putString(CHANNELS_OUT_OF_BOX_ORDERING_PREFS_TAG, ChannelConfigurationInfo.toJsonArray(this.mChannelsOutOfBoxOrdering).toString()).apply();
    }

    /* access modifiers changed from: private */
    public void saveToSharedPrefs() {
        SharedPreferences.Editor editor = this.mPrefs.edit();
        editor.clear();
        editor.putLong(TIME_STAMP_PREFS_TAG, System.currentTimeMillis());
        Uri uri = this.mSearchIconUri;
        if (uri != null) {
            editor.putString(SEARCH_ICON_PREFS_TAG, uri.toString());
        }
        if (this.mSearchOrbFocusedColorWasSet) {
            editor.putInt(SEARCH_ORB_FOCUSED_COLOR_TAG, this.mSearchOrbFocusedColor);
        }
        Uri uri2 = this.mOemInputsIconUri;
        if (uri2 != null) {
            editor.putString(OEM_INPUTS_ICON_PREFS_TAG, uri2.toString());
        }
        Uri uri3 = this.mBundledTunerBannerUri;
        if (uri3 != null) {
            editor.putString(BUNDLED_TUNER_BANNER_PREFS_TAG, uri3.toString());
        }
        editor.putString(BUNDLED_TUNER_TITLE_PREFS_TAG, this.mBundledTunerTitle);
        editor.putBoolean(DISABLE_DISCONNECTED_INPUTS_PREFS_TAG, this.mDisableDisconnectedInputs);
        editor.putBoolean(ENABLE_INPUT_STATE_ICON_PREFS_TAG, this.mEnableInputStateIcon);
        editor.putBoolean(SHOW_INPUTS_PREFS_TAG, this.mShowInputs);
        editor.putString(INPUTS_LABEL_PREFS_TAG, this.mInputsPanelLabelOption);
        editor.putBoolean(SHOW_PHYSICAL_INPUTS_SEPARATELY_PREFS_TAG, this.mShowPhysicalInputsSeparately);
        editor.putBoolean(USE_CUSTOM_INPUT_LIST_PREFS_TAG, this.mUseCustomInputList);
        storeListInSharedPrefs(this.mHomeScreenInputsOrdering, HOME_SCREEN_INPUTS_ORDERING_PREFS_TAG, editor);
        storeListInSharedPrefs(this.mPackageNotificationWhitelist, PACKAGE_NOTIFICATION_WHITELIST_PREFS_TAG, editor);
        ChannelConfigurationInfo channelConfigurationInfo = this.mLiveTvOobPackageInfo;
        if (channelConfigurationInfo != null) {
            editor.putString(LIVE_TV_CHANNEL_OUT_OF_BOX_PKG_NAME_PREFS_TAG, channelConfigurationInfo.getPackageName());
            editor.putString(LIVE_TV_CHANNEL_OUT_OF_BOX_SYSTEM_CHANNEL_KEY_PREFS_TAG, this.mLiveTvOobPackageInfo.getSystemChannelKey());
            editor.putInt(LIVE_TV_CHANNEL_OUT_OF_BOX_POSITION_PREFS_TAG, this.mLiveTvChannelOobPosition);
        }
        editor.putString(CHANNELS_OUT_OF_BOX_ORDERING_PREFS_TAG, ChannelConfigurationInfo.toJsonArray(this.mChannelsOutOfBoxOrdering).toString());
        this.mHasSeenSponsoredChannelsSoFar = this.mHasSeenSponsoredChannelsSoFar || this.mHasSeenSponsoredChannelsForCurrentConfig;
        editor.putBoolean(HAS_SEEN_SPONSORED_CHANNELS_TAG, this.mHasSeenSponsoredChannelsSoFar);
        if (this.mAppNames.size() > 0) {
            StringBuilder appsString = new StringBuilder(128);
            for (Map.Entry<String, String> entry : this.mAppNames.entrySet()) {
                appsString.append((String) entry.getKey());
                appsString.append(SEPARATOR);
                appsString.append((String) entry.getValue());
                appsString.append(SEPARATOR);
            }
            editor.putString(APPNAME_PACKAGE_MAP_TAG, appsString.toString());
        }
        storeListInSharedPrefs(this.mFavoriteAppsOutOfBoxOrdering, FAVORITE_APPS_OUT_OF_BOX_ORDERING_PREFS_TAG, editor);
        storeListInSharedPrefs(this.mAllAppsOutOfBoxOrdering, ALL_APPS_OUT_OF_BOX_ORDERING_PREFS_TAG, editor);
        storeListInSharedPrefs(this.mGamesOutOfBoxOrdering, GAMES_OUT_OF_BOX_ORDERING_PREFS_TAG, editor);
        storeListInSharedPrefs(this.mConfigureChannelsAppOrdering, CONFIGURE_CHANNELS_APP_ORDERING_TAG, editor);
        storeListInSharedPrefs(this.mPinnedFavoriteApps, PINNED_FAVORITE_APPS_TAG, editor);
        storeListInSharedPrefs(this.mHiddenUninstallPackageList, HIDDEN_UNINSTALL_PACKAGE_LIST_PREFS_TAG, editor);
        if (this.mAppChannelQuota.size() > 0) {
            StringBuilder quotasString = new StringBuilder(128);
            for (Map.Entry<String, Integer> entry2 : this.mAppChannelQuota.entrySet()) {
                quotasString.append((String) entry2.getKey());
                quotasString.append(SEPARATOR);
                quotasString.append(entry2.getValue());
                quotasString.append(SEPARATOR);
            }
            editor.putString(APP_CHANNEL_QUOTA_PREFS_TAG, quotasString.toString());
        }
        editor.putString(HEADSUP_NOTIFICATIONS_FONT_PREFS_TAG, this.mHeadsupNotificationsFont);
        if (this.mHeadsupNotificationsTextColorWasSet) {
            editor.putInt(HEADSUP_NOTIFICATIONS_TEXT_COLOR_PREFS_TAG, this.mHeadsupNotificationsTextColor);
        }
        if (this.mHeadsupNotificationsBackgroundColorWasSet) {
            editor.putInt(HEADSUP_NOTIFICATIONS_BACKGROUND_COLOR_PREFS_TAG, this.mHeadsupNotificationsBackgroundColor);
        }
        editor.putString(HEADSUP_NOTIFICATIONS_LOCATION_PREFS_TAG, this.mHeadsupNotificationsLocation);
        OemConfiguration.LayoutOrderOptions layoutOrderOptions = this.mAppsViewLayoutOption;
        if (layoutOrderOptions != null) {
            editor.putString(APPS_VIEW_LAYOUT_OPTION_TAG, layoutOrderOptions.getRowOrder());
        }
        if (this.mForceLaunchPackageAfterBoot) {
            editor.putBoolean(FORCE_LAUNCH_PACKAGE_AFTER_BOOT_TAG, true);
        }
        String str = this.mPackageNameLaunchAfterBoot;
        if (str != null) {
            editor.putString(PACKAGE_NAME_LAUNCH_AFTER_BOOT_TAG, str);
        }
        editor.putString(HOME_BACKGROUND_URI_PREFS_TAG, this.mHomeBackgroundUri);
        editor.putBoolean(SHOW_ADD_TO_WATCH_NEXT_FROM_PROGRAMS_MENU_PREFS_TAG, this.mShowAddToWatchNextFromProgramMenu);
        editor.putBoolean(SHOW_REMOVE_PROGRAM_FROM_PROGRAMS_MENU_PREFS_TAG, this.mShowRemoveProgramFromProgramMenu);
        editor.putBoolean(WATCH_NEXT_CHANNEL_ENABLED_BY_DEFAULT, this.mWatchNextChannelEnabledByDefault);
        editor.putBoolean(WATCH_NEXT_CHANNEL_AUTO_HIDE_ENABLED, this.mWatchNextChannelAutoHideEnabled);
        editor.putInt(OEM_CONFIGURATION_PACKAGE_VERSION_TAG, this.mConfigurationPackageVersion);
        editor.apply();
    }

    private void storeListInSharedPrefs(Collection<String> data, String tag, SharedPreferences.Editor editor) {
        if (data.size() > 0) {
            StringBuilder dataString = new StringBuilder(128);
            for (String item : data) {
                dataString.append(item);
                dataString.append(SEPARATOR);
            }
            editor.putString(tag, dataString.toString());
        }
    }

    private static class OemConfigurationDataLoadingTask extends AsyncTask<Void, Void, InputStream> {
        @SuppressLint({"StaticFieldLeak"})
        private final Context mContext;
        private final OemConfigurationData mOemConfigurationData;

        OemConfigurationDataLoadingTask(@NonNull OemConfigurationData oemConfigurationData, @NonNull Context context) {
            this.mOemConfigurationData = oemConfigurationData;
            this.mContext = context.getApplicationContext();
        }

        /* access modifiers changed from: protected */
        @SuppressLint({"ApplySharedPref"})
        public InputStream doInBackground(Void... params) {
            SharedPreferences prefs = this.mContext.getSharedPreferences(OemConfigurationData.OEM_CONFIG_SHARED_PREFS, 0);
            int queryCount = prefs.getInt(OemConfigurationData.CONTENT_PROVIDER_QUERY_COUNT_KEY, 0);
            if (queryCount < 3) {
                if (TestUtils.isRunningInTest()) {
                    prefs.edit().putInt(OemConfigurationData.CONTENT_PROVIDER_QUERY_COUNT_KEY, queryCount + 1).apply();
                } else {
                    prefs.edit().putInt(OemConfigurationData.CONTENT_PROVIDER_QUERY_COUNT_KEY, queryCount + 1).commit();
                }
                try {
                    return this.mContext.getContentResolver().openInputStream(PartnerCustomizationContract.OEM_CONFIGURATION_URI);
                } catch (Exception e) {
                    String valueOf = String.valueOf(PartnerCustomizationContract.OEM_CONFIGURATION_URI);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 14);
                    sb.append("Error reading ");
                    sb.append(valueOf);
                    Log.e(OemConfigurationData.TAG, sb.toString(), e);
                    return null;
                }
            } else {
                String valueOf2 = String.valueOf(PartnerCustomizationContract.OEM_CONFIGURATION_URI);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 68);
                sb2.append("Error reading ");
                sb2.append(valueOf2);
                sb2.append(". Queried the content provider unsuccessfully ");
                sb2.append(3);
                sb2.append(" times.");
                Log.e(OemConfigurationData.TAG, sb2.toString());
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(InputStream configurationFile) {
            super.onPostExecute((Object) configurationFile);
            if (configurationFile != null) {
                if (new OemConfigurationXmlParser(configurationFile, this.mOemConfigurationData, Util.isOperatorTierDevice(this.mContext), Util.isDynamicConfigDevice(this.mContext)).parse()) {
                    this.mOemConfigurationData.saveToSharedPrefs();
                }
                try {
                    configurationFile.close();
                } catch (IOException e) {
                    String valueOf = String.valueOf(PartnerCustomizationContract.OEM_CONFIGURATION_URI);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 14);
                    sb.append("Error closing ");
                    sb.append(valueOf);
                    Log.e(OemConfigurationData.TAG, sb.toString(), e);
                }
            } else {
                String valueOf2 = String.valueOf(PartnerCustomizationContract.OEM_CONFIGURATION_URI);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 14);
                sb2.append("Error reading ");
                sb2.append(valueOf2);
                Log.e(OemConfigurationData.TAG, sb2.toString());
                this.mOemConfigurationData.saveToSharedPrefs();
            }
            this.mOemConfigurationData.onDataLoaded();
        }
    }
}
