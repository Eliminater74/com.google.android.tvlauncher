package com.google.android.tvlauncher.data;

import android.support.annotation.NonNull;
import android.util.LongSparseArray;
import com.google.android.tvlauncher.model.HomeChannel;
import com.google.android.tvlauncher.util.ChannelConfigurationInfo;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

class PinnedChannelOrderManager {
    private PinnedChannelsComparator mPinnedChannelsComparator = new PinnedChannelsComparator();
    private LongSparseArray<ChannelConfigurationInfo> mPinnedChannelsConfig;
    private List<HomeChannel> mSortedPinnedChannels;
    private HashSet<Long> mTempFilteredOutPinnedChannelIds = new HashSet<>();

    PinnedChannelOrderManager() {
    }

    /* access modifiers changed from: package-private */
    public void setPinnedChannels(@NonNull List<HomeChannel> pinnedChannels, @NonNull LongSparseArray<ChannelConfigurationInfo> pinnedChannelsConfig) {
        this.mPinnedChannelsConfig = pinnedChannelsConfig;
        this.mPinnedChannelsComparator.setPinnedChannelsConfig(pinnedChannelsConfig);
        pinnedChannels.sort(this.mPinnedChannelsComparator);
        this.mSortedPinnedChannels = pinnedChannels;
    }

    /* access modifiers changed from: package-private */
    public void filterOutPinnedChannels(List<HomeChannel> channels) {
        if (this.mSortedPinnedChannels.size() != 0) {
            List<HomeChannel> filteredChannels = new ArrayList<>(channels.size());
            boolean shouldUpdate = false;
            for (HomeChannel channel : channels) {
                if (this.mPinnedChannelsConfig.get(channel.getId()) == null) {
                    filteredChannels.add(channel);
                } else {
                    shouldUpdate = true;
                    this.mTempFilteredOutPinnedChannelIds.add(Long.valueOf(channel.getId()));
                }
            }
            if (shouldUpdate) {
                channels.clear();
                channels.addAll(filteredChannels);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void applyPinnedChannelOrder(List<HomeChannel> channels) {
        for (HomeChannel pinnedChannel : this.mSortedPinnedChannels) {
            if (this.mTempFilteredOutPinnedChannelIds.contains(Long.valueOf(pinnedChannel.getId()))) {
                int pinnedChannelPosition = this.mPinnedChannelsConfig.get(pinnedChannel.getId()).getChannelPosition();
                if (pinnedChannelPosition > channels.size()) {
                    pinnedChannelPosition = channels.size();
                }
                channels.add(pinnedChannelPosition, pinnedChannel);
            }
        }
        this.mTempFilteredOutPinnedChannelIds.clear();
    }

    /* access modifiers changed from: package-private */
    public boolean isPinnedChannel(long channelId) {
        return this.mPinnedChannelsConfig.get(channelId) != null;
    }

    private static class PinnedChannelsComparator implements Comparator<HomeChannel> {
        private LongSparseArray<ChannelConfigurationInfo> mPinnedChannelsConfig;

        private PinnedChannelsComparator() {
        }

        /* access modifiers changed from: package-private */
        public void setPinnedChannelsConfig(LongSparseArray<ChannelConfigurationInfo> pinnedChannelsConfig) {
            this.mPinnedChannelsConfig = pinnedChannelsConfig;
        }

        public int compare(HomeChannel channel1, HomeChannel channel2) {
            int leftChannelPosition = this.mPinnedChannelsConfig.get(channel1.getId()).getChannelPosition();
            int rightChannelPosition = this.mPinnedChannelsConfig.get(channel2.getId()).getChannelPosition();
            if (leftChannelPosition == rightChannelPosition) {
                if (this.mPinnedChannelsConfig.get(channel1.getId()).isGoogleConfig()) {
                    return 1;
                }
                if (this.mPinnedChannelsConfig.get(channel2.getId()).isGoogleConfig()) {
                    return -1;
                }
            }
            return Integer.compare(leftChannelPosition, rightChannelPosition);
        }
    }
}
