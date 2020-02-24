package com.google.android.tvlauncher.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import androidx.leanback.preference.LeanbackPreferenceFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.TvlauncherLogEnum;
import com.google.android.tvlauncher.analytics.ClickEvent;
import com.google.android.tvlauncher.analytics.FragmentEventLogger;
import com.google.android.tvlauncher.analytics.LogEvent;
import com.google.android.tvlauncher.data.PackageChannelsObserver;
import com.google.android.tvlauncher.data.TvDataManager;
import com.google.android.tvlauncher.home.WatchNextPrefs;
import com.google.android.tvlauncher.model.Channel;
import com.google.android.tvrecommendations.shared.util.Constants;
import com.google.logs.tvlauncher.config.TvLauncherConstants;
import java.util.Comparator;
import java.util.List;

public class ConfigureChannelsAppDetailsFragment extends LeanbackPreferenceFragment implements Preference.OnPreferenceChangeListener {
    static final String APP_NAME_KEY = "app_name";
    static final String CHANNEL_APP_KEY = "channel_app";
    private static final Comparator<Channel> CHANNEL_COMPARATOR = ConfigureChannelsAppDetailsFragment$$Lambda$0.$instance;
    private String mAppName;
    private String mChannelAppKey;
    private final PackageChannelsObserver mChannelsObserver = new PackageChannelsObserver() {
        public void onChannelsChange() {
            ConfigureChannelsAppDetailsFragment.this.onChannelsLoaded();
        }
    };
    private final FragmentEventLogger mEventLogger = new FragmentEventLogger(this);
    private boolean mLoggedOpenEvent;
    private boolean mStarted;
    private TvDataManager mTvDataManager;
    private TvDataManager.Provider mTvDataManagerProvider = TvDataManager.PROVIDER;

    static final /* synthetic */ int lambda$static$0$ConfigureChannelsAppDetailsFragment(Channel o1, Channel o2) {
        if (o1 == null) {
            if (o2 != null) {
                return 1;
            }
            return 0;
        } else if (o2 == null) {
            return -1;
        } else {
            int displayOrder1 = o1.getConfigurationDisplayOrder();
            int displayOrder2 = o2.getConfigurationDisplayOrder();
            if (displayOrder1 != displayOrder2) {
                if (displayOrder1 == 0) {
                    return 1;
                }
                if (displayOrder2 == 0) {
                    return -1;
                }
                return Integer.compare(displayOrder1, displayOrder2);
            } else if (o1.getDisplayName() == null) {
                if (o2.getDisplayName() != null) {
                    return 1;
                }
                return 0;
            } else if (o2.getDisplayName() == null) {
                return -1;
            } else {
                return o1.getDisplayName().compareToIgnoreCase(o2.getDisplayName());
            }
        }
    }

    public void onCreatePreferences(Bundle instanceStateBundle, String s) {
        Context preferenceContext = getPreferenceManager().getContext();
        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(preferenceContext);
        Bundle args = getArguments();
        if (instanceStateBundle != null) {
            this.mChannelAppKey = instanceStateBundle.getString(CHANNEL_APP_KEY);
            this.mAppName = instanceStateBundle.getString(APP_NAME_KEY);
        } else if (args != null) {
            this.mChannelAppKey = args.getString(CHANNEL_APP_KEY);
            this.mAppName = args.getString(APP_NAME_KEY);
        }
        if (Constants.SPONSORED_CHANNEL_LEGACY_PACKAGE_NAME.equals(this.mChannelAppKey)) {
            screen.setTitle(getString(C1188R.string.promotional_channel_setting_panel_title));
        } else {
            screen.setTitle(getString(C1188R.string.select_channels_title_with_app_name, new Object[]{this.mAppName}));
        }
        setPreferenceScreen(screen);
        this.mTvDataManager = this.mTvDataManagerProvider.get(preferenceContext);
        registerObserverAndUpdateDataIfNeeded();
        this.mStarted = true;
    }

    private void registerObserverAndUpdateDataIfNeeded() {
        this.mTvDataManager.registerPackageChannelsObserver(this.mChannelsObserver);
        if (this.mChannelAppKey != null) {
            loadChannels();
        }
    }

    public void onStart() {
        super.onStart();
        if (!this.mStarted) {
            registerObserverAndUpdateDataIfNeeded();
            this.mStarted = true;
        }
    }

    public void onStop() {
        super.onStop();
        if (this.mStarted) {
            this.mTvDataManager.unregisterPackageChannelsObserver(this.mChannelsObserver);
            this.mStarted = false;
        }
    }

    public void onSaveInstanceState(Bundle instanceStateBundle) {
        super.onSaveInstanceState(instanceStateBundle);
        String str = this.mChannelAppKey;
        if (str != null) {
            instanceStateBundle.putString(CHANNEL_APP_KEY, str);
        }
        String str2 = this.mAppName;
        if (str2 != null) {
            instanceStateBundle.putString(APP_NAME_KEY, str2);
        }
    }

    public void onChannelsLoaded() {
        if (isAdded()) {
            PreferenceScreen screen = getPreferenceScreen();
            screen.removeAll();
            List<Channel> channels = this.mTvDataManager.getPackageChannels(this.mChannelAppKey);
            if (channels != null && channels.size() > 0) {
                channels.sort(CHANNEL_COMPARATOR);
                for (Channel channel : channels) {
                    CustomSwitchPreference switchPreference = new CustomSwitchPreference(getPreferenceManager().getContext());
                    switchPreference.setLayoutResource(C1188R.layout.appchannel_channel_banner);
                    switchPreference.setKey(Long.toString(channel.getId()));
                    switchPreference.setTitle(channel.getDisplayName());
                    switchPreference.setChecked(channel.isBrowsable());
                    switchPreference.setShowToggle(channel.canRemove());
                    if (channel.isSponsored()) {
                        switchPreference.setSummary(channel.getLogoContentDescription());
                        switchPreference.setDimSummary(true);
                    } else if (channel.isEmpty()) {
                        switchPreference.setSummary(C1188R.string.empty_channel_message);
                    }
                    switchPreference.setPersistent(false);
                    switchPreference.setOnPreferenceChangeListener(this);
                    screen.addPreference(switchPreference);
                }
            }
            if (!this.mLoggedOpenEvent) {
                logDataLoadedEvent();
                this.mLoggedOpenEvent = true;
            }
        }
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference.getKey() != null && preference.getKey().startsWith(WatchNextPrefs.WATCH_NEXT_PACKAGE_KEY_PREFIX)) {
            SharedPreferences prefs = getContext().getSharedPreferences(WatchNextPrefs.WATCH_NEXT_PREF_FILE_NAME, 0);
            if (((Boolean) newValue).booleanValue()) {
                prefs.edit().remove(preference.getKey()).apply();
            } else {
                prefs.edit().putBoolean(preference.getKey(), false).apply();
            }
            return true;
        } else if (!this.mTvDataManager.isPackageChannelDataLoaded(this.mChannelAppKey)) {
            return false;
        } else {
            Boolean browsable = (Boolean) newValue;
            if (browsable != null) {
                int channelId = Integer.parseInt(preference.getKey());
                if (browsable.booleanValue()) {
                    logToggleChannelVisible(channelId, TvlauncherLogEnum.TvLauncherEventCode.ADD_CHANNEL, getBrowsableChannelCount() + 1);
                } else {
                    logToggleChannelVisible(channelId, TvlauncherLogEnum.TvLauncherEventCode.REMOVE_CHANNEL, getBrowsableChannelCount() - 1);
                }
                this.mTvDataManager.setChannelBrowsable((long) channelId, browsable.booleanValue(), true);
            }
            return true;
        }
    }

    private void loadChannels() {
        if (this.mTvDataManager.isPackageChannelDataLoaded(this.mChannelAppKey)) {
            onChannelsLoaded();
        } else {
            this.mTvDataManager.loadPackageChannelsData(this.mChannelAppKey);
        }
    }

    private void logDataLoadedEvent() {
        LogEvent event = new LogEvent().setVisualElementTag(TvLauncherConstants.CUSTOMIZE_APP_CHANNELS_CONTAINER);
        event.getApplication().setPackageName(this.mChannelAppKey).setChannelCount(getTotalChannelCount()).setBrowsableChannelCount(getBrowsableChannelCount());
        this.mEventLogger.log(event);
    }

    private void logToggleChannelVisible(int channelId, TvlauncherLogEnum.TvLauncherEventCode eventCode, int browsableChannelCount) {
        int channelIndex = 0;
        String channelTitle = null;
        List<Channel> channels = this.mTvDataManager.getPackageChannels(this.mChannelAppKey);
        if (channels != null) {
            for (int i = 0; i < channels.size(); i++) {
                Channel channel = channels.get(i);
                if (channel.getId() == ((long) channelId)) {
                    channelIndex = i;
                    channelTitle = channel.getDisplayName();
                }
            }
        }
        LogEvent event = new ClickEvent(eventCode).setVisualElementTag(TvLauncherConstants.CHANNEL_BROWSABLE_TOGGLE).setVisualElementIndex(channelIndex).pushParentVisualElementTag(TvLauncherConstants.CUSTOMIZE_APP_CHANNELS_CONTAINER);
        if (channelTitle != null) {
            event.getChannel().setTitle(channelTitle);
        }
        event.getApplication().setPackageName(this.mChannelAppKey).setChannelCount(getTotalChannelCount()).setBrowsableChannelCount(browsableChannelCount);
        this.mEventLogger.log(event);
    }

    private int getTotalChannelCount() {
        List<Channel> channels = this.mTvDataManager.getPackageChannels(this.mChannelAppKey);
        if (channels != null) {
            return channels.size();
        }
        return 0;
    }

    private int getBrowsableChannelCount() {
        List<Channel> channels = this.mTvDataManager.getPackageChannels(this.mChannelAppKey);
        int browsableChannels = 0;
        if (channels != null) {
            for (Channel channel : channels) {
                if (channel.isBrowsable()) {
                    browsableChannels++;
                }
            }
        }
        return browsableChannels;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting(otherwise = 5)
    public void setTvDataManagerProvider(TvDataManager.Provider tvDataManagerProvider) {
        this.mTvDataManagerProvider = tvDataManagerProvider;
    }
}
