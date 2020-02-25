package com.google.android.tvlauncher.util;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GoogleConfiguration {
    private final List<ChannelConfigurationInfo> mChannelConfigs;
    private final Set<String> mSponsoredChannels;
    private Map<String, ChannelConfigurationInfo> mChannelInfoMap = new HashMap();

    public GoogleConfiguration(List<ChannelConfigurationInfo> channelConfigs, Set<String> sponsoredChannels) {
        this.mChannelConfigs = channelConfigs;
        this.mSponsoredChannels = sponsoredChannels;
        for (ChannelConfigurationInfo channelInfo : this.mChannelConfigs) {
            addChannelToChannelInfoMap(channelInfo);
        }
    }

    public List<ChannelConfigurationInfo> getChannelConfigs() {
        return this.mChannelConfigs;
    }

    public ChannelConfigurationInfo getChannelInfo(String packageName, String systemChannelKey) {
        return this.mChannelInfoMap.get(ChannelConfigurationInfo.getUniqueKey(packageName, systemChannelKey));
    }

    public boolean isSponsored(String packageName, String systemChannelKey) {
        return this.mSponsoredChannels.contains(ChannelConfigurationInfo.getUniqueKey(packageName, systemChannelKey));
    }

    private void addChannelToChannelInfoMap(@NonNull ChannelConfigurationInfo channelConfigurationInfo) {
        this.mChannelInfoMap.put(ChannelConfigurationInfo.getUniqueKey(channelConfigurationInfo.getPackageName(), channelConfigurationInfo.getSystemChannelKey()), channelConfigurationInfo);
    }
}
