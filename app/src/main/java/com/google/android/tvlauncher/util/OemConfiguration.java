package com.google.android.tvlauncher.util;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;

import com.google.android.tvlauncher.C1188R;
import com.google.android.tvrecommendations.shared.util.OemUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class OemConfiguration {
    public static final String INPUTS_PANEL_LABEL_INPUTS = "inputs";
    public static final String INPUTS_PANEL_LABEL_SOURCES = "sources";
    private static final String APPS_VIEW_LAYOUT_APPS_GAMES_OEM = "apps_games_oem";
    private static final String APPS_VIEW_LAYOUT_APPS_OEM = "apps_oem";
    private static final String APPS_VIEW_LAYOUT_APPS_OEM_GAMES = "apps_oem_games";
    private static final String APPS_VIEW_LAYOUT_GAMES_APPS_OEM = "games_apps_oem";
    private static final int DEFAULT_QUOTA = 1;
    private static final Object sLock = new Object();
    private static OemConfiguration sOemConfiguration;
    protected final OemConfigurationData mConfiguration;
    protected Context mContext;
    private List<OemConfigurationPackageChangeListener> mConfigurationPackageChangeListeners = new ArrayList(1);
    private String mPackageName;

    protected OemConfiguration(Context context, String packageName) {
        int configurationPackageVersion;
        this.mPackageName = packageName;
        if (context != null) {
            configurationPackageVersion = PackageUtils.getApplicationVersionCode(context, packageName);
        } else {
            configurationPackageVersion = -1;
        }
        this.mContext = context != null ? context.getApplicationContext() : null;
        this.mConfiguration = new OemConfigurationData(this.mContext, configurationPackageVersion);
        if (context != null) {
            registerOnDataLoadedListener(OemConfiguration$$Lambda$0.$instance);
            if (Util.isOperatorTierDevice(context)) {
                NotifyRefreshOemConfigurationDataJobService.schedule(context);
            }
        }
    }

    public static OemConfiguration get(Context context) {
        synchronized (sLock) {
            if (sOemConfiguration == null) {
                String packageName = OemUtils.getCustomizationApp(context.getPackageManager());
                if (packageName != null) {
                    sOemConfiguration = new FlavorSpecificOemConfiguration(context, packageName);
                }
                if (sOemConfiguration == null) {
                    sOemConfiguration = new FlavorSpecificOemConfiguration(null, null);
                    sOemConfiguration.mConfiguration.setDataLoaded(true);
                }
            }
        }
        return sOemConfiguration;
    }

    public static void resetIfNecessary(String packageName, boolean packageRemoved) {
        synchronized (sLock) {
            if (sOemConfiguration != null && !TextUtils.isEmpty(packageName) && packageName.equals(sOemConfiguration.getPackageName()) && packageRemoved) {
                sOemConfiguration.notifyConfigurationPackageRemoved();
            }
        }
    }

    public abstract List<OemOutOfBoxApp> getVirtualOutOfBoxApps();

    public abstract boolean isWatchNextChannelEnabledByDefault();

    /* access modifiers changed from: package-private */
    public abstract void onOemConfigurationFetched();

    public abstract boolean shouldShowAddToWatchNextFromProgramMenu();

    public abstract boolean shouldShowRemoveProgramFromProgramMenu();

    private void notifyConfigurationPackageRemoved() {
        this.mPackageName = null;
        this.mContext = null;
        for (OemConfigurationPackageChangeListener listener : this.mConfigurationPackageChangeListeners) {
            listener.onOemConfigurationPackageRemoved();
        }
    }

    public void registerOnDataLoadedListener(OnDataLoadedListener listener) {
        this.mConfiguration.registerOnDataLoadedListener(listener);
    }

    public void unregisterOnDataLoadedListener(OnDataLoadedListener listener) {
        this.mConfiguration.unregisterOnDataLoadedListener(listener);
    }

    @VisibleForTesting
    public String getPackageName() {
        return this.mPackageName;
    }

    public boolean isDataLoaded() {
        return this.mConfiguration.isDataLoaded();
    }

    public Uri getCustomSearchIconUri() {
        return this.mConfiguration.getSearchIconUri();
    }

    public int getSearchOrbFocusedColor(int defaultColor) {
        return this.mConfiguration.getSearchOrbFocusedColor(defaultColor);
    }

    public boolean shouldShowInputs() {
        return this.mConfiguration.isShowInputs();
    }

    public String getInputsPanelLabelText(@NonNull Context context) {
        if (INPUTS_PANEL_LABEL_SOURCES.equals(this.mConfiguration.getInputsPanelLabelOption())) {
            return context.getString(C1188R.string.inputs_panel_label_sources);
        }
        return context.getString(C1188R.string.inputs_panel_label_inputs);
    }

    public Uri getOemInputsIconUri() {
        return this.mConfiguration.getOemInputsIconUri();
    }

    public boolean shouldShowPhysicalTunersSeparately() {
        return this.mConfiguration.isShowPhysicalInputsSeparately();
    }

    public boolean shouldUseCustomInputList() {
        return this.mConfiguration.shouldUseCustomInputList();
    }

    public boolean shouldDisableDisconnectedInputs() {
        return this.mConfiguration.isDisableDisconnectedInputs();
    }

    public boolean getStateIconFromTVInput() {
        return this.mConfiguration.isEnableInputStateIcon();
    }

    public boolean shouldApplyStandardStyleToInputStateIcons() {
        return this.mConfiguration.shouldApplyStandardStyleToInputStateIcons();
    }

    public String getBundledTunerTitle() {
        return this.mConfiguration.getBundledTunerTitle();
    }

    public String getDisconnectedInputToastText() {
        return this.mConfiguration.getDisconnectedInputText();
    }

    public Uri getBundledTunerBannerUri() {
        return this.mConfiguration.getBundledTunerBannerUri();
    }

    public List<String> getHomeScreenInputsOrdering() {
        return this.mConfiguration.getHomeScreenInputsOrdering();
    }

    public String getAppsPromotionRowPackage() {
        return null;
    }

    public List<String> getOutOfBoxFavoriteAppsList() {
        return this.mConfiguration.getFavoriteAppsOutOfBoxOrdering();
    }

    public List<String> getOutOfBoxAllAppsList() {
        return this.mConfiguration.getAllAppsOutOfBoxOrdering();
    }

    public List<String> getOutOfBoxGamesList() {
        return this.mConfiguration.getGamesOutOfBoxOrdering();
    }

    public List<String> getConfigureChannelsAppOrdering() {
        return this.mConfiguration.getConfigureChannelsAppOrdering();
    }

    public String getAppNameByPackageName(String packageName) {
        return this.mConfiguration.getAppNameByPackageName(packageName);
    }

    public List<ChannelConfigurationInfo> getOutOfBoxChannelsList() {
        return this.mConfiguration.getChannelsOutOfBoxOrdering();
    }

    public ChannelConfigurationInfo getLiveTvOobPackageInfo() {
        return this.mConfiguration.getLiveTvOobPackageInfo();
    }

    public int getLiveTvChannelOobPosition() {
        return this.mConfiguration.getLiveTvChannelOobPosition();
    }

    public LayoutOrderOptions getAppsViewLayoutOption() {
        return this.mConfiguration.getAppsViewLayoutOption();
    }

    public boolean shouldForceLaunchPackageAfterBoot() {
        return this.mConfiguration.getForceLaunchPackageAfterBoot();
    }

    public String getPackageNameLaunchAfterBoot() {
        return this.mConfiguration.getPackageNameLaunchAfterBoot();
    }

    public List<String> getPinnedFavoriteApps() {
        return this.mConfiguration.getPinnedFavoriteApps();
    }

    @NonNull
    public List<String> getHiddenUninstallPackageList() {
        return this.mConfiguration.getHiddenUninstallPackageList();
    }

    public List<String> getNotificationWhitelist() {
        return this.mConfiguration.getPackageToPackageNotificationWhitelist();
    }

    public int getChannelQuota(String packageName) {
        int quota = this.mConfiguration.getAppChannelQuota(packageName);
        if (quota != -1) {
            return quota;
        }
        return 1;
    }

    public String getHeadsUpNotificationFont() {
        return this.mConfiguration.getHeadsupNotificationsFont();
    }

    public int getHeadsUpNotificationTextColor(int defaultColor) {
        return this.mConfiguration.getHeadsupNotificationsTextColor(defaultColor);
    }

    public int getHeadsUpNotificationBackgroundColor(int defaultColor) {
        return this.mConfiguration.getHeadsupNotificationsBackgroundColor(defaultColor);
    }

    public int getHeadsUpNotificationLocation() {
        String gravity = this.mConfiguration.getHeadsupNotificationsLocation();
        if (TextUtils.isEmpty(gravity)) {
            return 8388661;
        }
        return getGravity(gravity.replaceAll("\\s+", "").toLowerCase());
    }

    public ChannelConfigurationInfo getChannelInfo(String packageName, String systemChannelKey) {
        return this.mConfiguration.getChannelInfo(packageName, systemChannelKey);
    }

    public boolean isWatchNextAutoHideEnabled() {
        return this.mConfiguration.isWatchNextChannelAutoHideEnabled();
    }

    public String getHomeBackgroundUri() {
        return this.mConfiguration.getHomeBackgroundUri();
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    private int getGravity(String gravity) {
        char c;
        switch (gravity.hashCode()) {
            case -2128092485:
                if (gravity.equals("start|top")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1681362038:
                if (gravity.equals("bottom|end")) {
                    c = 10;
                    break;
                }
                c = 65535;
                break;
            case -1613025466:
                if (gravity.equals("bottom|center")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1606040908:
                if (gravity.equals("end|top")) {
                    c = 9;
                    break;
                }
                c = 65535;
                break;
            case -1146190628:
                if (gravity.equals("top|center")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1138690636:
                if (gravity.equals("top|end")) {
                    c = 8;
                    break;
                }
                c = 65535;
                break;
            case -868106415:
                if (gravity.equals("bottom|start")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -506054907:
                if (gravity.equals("start|bottom")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -144103316:
                if (gravity.equals("end|bottom")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -5904462:
                if (gravity.equals("center|bottom")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 948068091:
                if (gravity.equals("top|start")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1163180334:
                if (gravity.equals("center|top")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
                return 8388659;
            case 2:
            case 3:
                return 8388691;
            case 4:
            case 5:
                return 49;
            case 6:
            case 7:
                return 81;
            case 8:
            case 9:
                return 8388661;
            case 10:
            case 11:
                return 8388693;
            default:
                return 8388661;
        }
    }

    public void addConfigurationPackageChangeListener(OemConfigurationPackageChangeListener listener) {
        if (listener != null) {
            this.mConfigurationPackageChangeListeners.add(listener);
        }
    }

    public void removeConfigurationPackageChangeListener(OemConfigurationPackageChangeListener listener) {
        this.mConfigurationPackageChangeListeners.remove(listener);
    }

    public enum LayoutOrderOptions {
        APPS_OEM_GAMES(OemConfiguration.APPS_VIEW_LAYOUT_APPS_OEM_GAMES),
        APPS_GAMES_OEM(OemConfiguration.APPS_VIEW_LAYOUT_APPS_GAMES_OEM),
        GAMES_APPS_OEM(OemConfiguration.APPS_VIEW_LAYOUT_GAMES_APPS_OEM),
        APPS_OEM(OemConfiguration.APPS_VIEW_LAYOUT_APPS_OEM);

        private final String mRowOrder;

        private LayoutOrderOptions(String rowOrder) {
            this.mRowOrder = rowOrder;
        }

        public static LayoutOrderOptions getLayoutOptionForRowOrder(String rowOrder) {
            if (rowOrder == null) {
                return null;
            }
            char c = 65535;
            switch (rowOrder.hashCode()) {
                case 315532620:
                    if (rowOrder.equals(OemConfiguration.APPS_VIEW_LAYOUT_APPS_GAMES_OEM)) {
                        c = 1;
                        break;
                    }
                    break;
                case 1185685002:
                    if (rowOrder.equals(OemConfiguration.APPS_VIEW_LAYOUT_APPS_OEM)) {
                        c = 3;
                        break;
                    }
                    break;
                case 1234273356:
                    if (rowOrder.equals(OemConfiguration.APPS_VIEW_LAYOUT_APPS_OEM_GAMES)) {
                        c = 0;
                        break;
                    }
                    break;
                case 1305388488:
                    if (rowOrder.equals(OemConfiguration.APPS_VIEW_LAYOUT_GAMES_APPS_OEM)) {
                        c = 2;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                return APPS_OEM_GAMES;
            }
            if (c == 1) {
                return APPS_GAMES_OEM;
            }
            if (c == 2) {
                return GAMES_APPS_OEM;
            }
            if (c != 3) {
                return null;
            }
            return APPS_OEM;
        }

        public String getRowOrder() {
            return this.mRowOrder;
        }
    }

    public interface OemConfigurationPackageChangeListener {
        void onOemConfigurationPackageChanged();

        void onOemConfigurationPackageRemoved();
    }

    public interface OnDataLoadedListener {
        void onDataLoaded();
    }
}
