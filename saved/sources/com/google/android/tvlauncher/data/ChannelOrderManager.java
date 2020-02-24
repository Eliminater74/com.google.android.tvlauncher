package com.google.android.tvlauncher.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.util.Log;
import android.util.LongSparseArray;
import com.google.android.tvlauncher.model.HomeChannel;
import com.google.android.tvlauncher.util.ChannelConfigurationInfo;
import com.google.android.tvrecommendations.shared.util.Constants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ChannelOrderManager {
    private static final boolean DEBUG = false;
    private static final int DIRECTION_DOWN = 1;
    private static final int DIRECTION_UP = -1;
    private static final LongSparseArray<Integer> EMPTY_OOB_CHANNEL_POSITIONS = new LongSparseArray<>(0);
    private static final String KEY_ALL_CHANNELS_POSITIONS = "ALL_CHANNELS_POSITIONS";
    private static final String KEY_FIRST_START_TIMESTAMP = "FIRST_START_TIMESTAMP";
    private static final String KEY_LIVE_TV_CHANNEL_LAST_POSITION = "LIVE_TV_CHANNEL_LAST_POSITION";
    private static final String KEY_LOGGED_CHANNEL_ID_ORDER = "LOGGED_CHANNEL_ID_ORDER";
    private static final String KEY_ORDERED_CHANNEL_IDS = "ORDERED_CHANNEL_IDS";
    private static final String KEY_SPONSORED_GOOGLE_CHANNEL_LAST_OOB_POSITION = "SPONSORED_GOOGLE_CHANNEL_LAST_OOB_POSITION";
    private static final String KEY_SPONSORED_GOOGLE_CHANNEL_LAST_POSITION = "SPONSORED_GOOGLE_CHANNEL_LAST_POSITION";
    private static final String KEY_USER_HAS_MANAGED_CHANNELS = "USER_HAS_MANAGED_CHANNELS";
    private static final long LIVE_TV_CHANNEL_NO_ID = -1;
    private static final int LIVE_TV_CHANNEL_NO_LAST_POSITION = -1;
    private static final int LIVE_TV_CHANNEL_NO_OOB_POSITION = -1;
    private static final int MAX_OOB_CHANNEL_PACKAGES = 20;
    private static final long MAX_TIME_OOB_ORDER_HONORED = 172800000;
    private static final int NO_POSITION = -1;
    private static final String PREF_CHANNEL_ORDER_MANAGER = "CHANNEL_ORDER_MANAGER";
    private static final long SPONSORED_GOOGLE_CHANNEL_NO_ID = -1;
    private static final int SPONSORED_GOOGLE_CHANNEL_NO_LAST_POSITION = -1;
    private static final int SPONSORED_GOOGLE_CHANNEL_NO_OOB_POSITION = -1;
    private static final String TAG = "ChannelOrderManager";
    private LongSparseArray<Integer> mAllChannelPositions;
    private ChannelComparator mChannelComparator;
    private LongSparseArray<Integer> mChannelPositions;
    private List<HomeChannel> mChannels;
    private List<HomeChannelsObserver> mChannelsObservers;
    private final Context mContext;
    private long mFirstStartTimestamp;
    private boolean mIsNewChannelAdded;
    private long mLiveTvChannelId;
    private int mLiveTvChannelLastPosition;
    private int mLiveTvChannelOobPosition;
    private Map<ChannelConfigurationInfo, List<Integer>> mOobPackageKeyPositions;
    private final List<ChannelConfigurationInfo> mOutOfBoxPackages;
    private PinnedChannelOrderManager mPinnedChannelOrderManager;
    private long mSponsoredGoogleChannelId;
    private int mSponsoredGoogleChannelLastOobPosition;
    private int mSponsoredGoogleChannelLastPosition;
    private int mSponsoredGoogleChannelOobPosition;
    private boolean mUserHasManagedChannels;

    @Retention(RetentionPolicy.SOURCE)
    private @interface Direction {
    }

    @VisibleForTesting
    ChannelOrderManager(Context context, List<ChannelConfigurationInfo> outOfBoxPackages) {
        this(context, outOfBoxPackages, -1);
    }

    ChannelOrderManager(Context context, List<ChannelConfigurationInfo> outOfBoxPackages, int liveTvChannelOobPosition) {
        this.mChannelComparator = new ChannelComparator();
        this.mLiveTvChannelId = -1;
        this.mLiveTvChannelLastPosition = -1;
        this.mLiveTvChannelOobPosition = -1;
        this.mSponsoredGoogleChannelId = -1;
        this.mSponsoredGoogleChannelLastPosition = -1;
        this.mSponsoredGoogleChannelLastOobPosition = -1;
        this.mSponsoredGoogleChannelOobPosition = -1;
        this.mContext = context.getApplicationContext();
        this.mOutOfBoxPackages = outOfBoxPackages;
        this.mLiveTvChannelOobPosition = liveTvChannelOobPosition;
        List<ChannelConfigurationInfo> list = this.mOutOfBoxPackages;
        if (list != null && list.size() > 0) {
            this.mOobPackageKeyPositions = new HashMap();
            int i = 0;
            while (i < this.mOutOfBoxPackages.size() && i < 20) {
                ChannelConfigurationInfo key = this.mOutOfBoxPackages.get(i);
                if (this.mOobPackageKeyPositions.containsKey(key)) {
                    this.mOobPackageKeyPositions.get(key).add(Integer.valueOf(i));
                } else {
                    List<Integer> newList = new ArrayList<>();
                    newList.add(Integer.valueOf(i));
                    this.mOobPackageKeyPositions.put(key, newList);
                }
                i++;
            }
        }
        readStateFromStorage();
    }

    @VisibleForTesting(otherwise = 3)
    public void setChannels(List<HomeChannel> channels) {
        this.mChannels = channels;
    }

    /* access modifiers changed from: package-private */
    public void setChannelsObservers(List<HomeChannelsObserver> channelsObservers) {
        this.mChannelsObservers = channelsObservers;
    }

    /* access modifiers changed from: package-private */
    public void setPinnedChannelOrderManager(PinnedChannelOrderManager pinnedChannelOrderManager) {
        this.mPinnedChannelOrderManager = pinnedChannelOrderManager;
    }

    /* access modifiers changed from: package-private */
    public void setLiveTvChannelId(long liveTvChannelId) {
        this.mLiveTvChannelId = liveTvChannelId;
    }

    /* access modifiers changed from: package-private */
    public void setSponsoredGoogleChannelId(long sponsoredGoogleChannelId) {
        this.mSponsoredGoogleChannelId = sponsoredGoogleChannelId;
    }

    /* access modifiers changed from: package-private */
    public void setSponsoredGoogleChannelOobPosition(int sponsoredGoogleChannelOobPosition) {
        this.mSponsoredGoogleChannelOobPosition = sponsoredGoogleChannelOobPosition;
    }

    private void readStateFromStorage() {
        int position;
        char c = 0;
        SharedPreferences pref = this.mContext.getSharedPreferences(PREF_CHANNEL_ORDER_MANAGER, 0);
        String[] channelIds = TextUtils.split(pref.getString(KEY_ORDERED_CHANNEL_IDS, ""), ",");
        this.mChannelPositions = new LongSparseArray<>(channelIds.length);
        int position2 = 0;
        for (String channelId : channelIds) {
            try {
                position = position2 + 1;
                try {
                    this.mChannelPositions.put(Long.parseLong(channelId), Integer.valueOf(position2));
                } catch (NumberFormatException e) {
                }
            } catch (NumberFormatException e2) {
                position = position2;
                StringBuilder sb = new StringBuilder(String.valueOf(channelId).length() + 44);
                sb.append("Invalid channel ID: ");
                sb.append(channelId);
                sb.append(" at position ");
                sb.append(position);
                Log.e(TAG, sb.toString());
                position2 = position;
            }
            position2 = position;
        }
        String allChannelsString = pref.getString(KEY_ALL_CHANNELS_POSITIONS, "");
        if (allChannelsString.isEmpty()) {
            this.mAllChannelPositions = this.mChannelPositions.clone();
        } else {
            String[] channelStrings = TextUtils.split(allChannelsString, ",");
            this.mAllChannelPositions = new LongSparseArray<>(channelStrings.length);
            int length = channelStrings.length;
            int i = 0;
            while (i < length) {
                String channelString = channelStrings[i];
                try {
                    String[] channelPosition = TextUtils.split(channelString, "=");
                    if (channelPosition.length == 2) {
                        this.mAllChannelPositions.put(Long.parseLong(channelPosition[c]), Integer.valueOf(Integer.parseInt(channelPosition[1])));
                    } else {
                        StringBuilder sb2 = new StringBuilder(String.valueOf(channelString).length() + 44 + String.valueOf(allChannelsString).length());
                        sb2.append("Error parsing all channel positions ");
                        sb2.append(channelString);
                        sb2.append(" within ");
                        sb2.append(allChannelsString);
                        Log.e(TAG, sb2.toString());
                    }
                } catch (NumberFormatException e3) {
                    String valueOf = String.valueOf(allChannelsString);
                    Log.e(TAG, valueOf.length() != 0 ? "Invalid info in all channel positions ".concat(valueOf) : new String("Invalid info in all channel positions "));
                }
                i++;
                c = 0;
            }
        }
        this.mUserHasManagedChannels = pref.getBoolean(KEY_USER_HAS_MANAGED_CHANNELS, false);
        this.mFirstStartTimestamp = pref.getLong(KEY_FIRST_START_TIMESTAMP, -1);
        if (this.mFirstStartTimestamp == -1) {
            this.mFirstStartTimestamp = System.currentTimeMillis();
            pref.edit().putLong(KEY_FIRST_START_TIMESTAMP, this.mFirstStartTimestamp).apply();
        }
        this.mLiveTvChannelLastPosition = pref.getInt(KEY_LIVE_TV_CHANNEL_LAST_POSITION, -1);
        this.mSponsoredGoogleChannelLastPosition = pref.getInt(KEY_SPONSORED_GOOGLE_CHANNEL_LAST_POSITION, -1);
        this.mSponsoredGoogleChannelLastOobPosition = pref.getInt(KEY_SPONSORED_GOOGLE_CHANNEL_LAST_OOB_POSITION, -1);
    }

    private void saveChannelOrderToStorage() {
        SharedPreferences.Editor editor = this.mContext.getSharedPreferences(PREF_CHANNEL_ORDER_MANAGER, 0).edit();
        String channelOrder = "";
        if (this.mChannelPositions.size() > 0) {
            StringBuilder sb = new StringBuilder(this.mChannels.size() * 12);
            for (HomeChannel channel : this.mChannels) {
                sb.append(channel.getId());
                sb.append(',');
            }
            sb.setLength(sb.length() - 1);
            channelOrder = sb.toString();
        }
        String allChannelOrder = "";
        if (this.mAllChannelPositions.size() > 0) {
            StringBuilder sb2 = new StringBuilder(this.mAllChannelPositions.size() * 15);
            for (int i = 0; i < this.mAllChannelPositions.size(); i++) {
                sb2.append(this.mAllChannelPositions.keyAt(i));
                sb2.append('=');
                sb2.append(this.mAllChannelPositions.valueAt(i));
                sb2.append(',');
            }
            sb2.setLength(sb2.length() - 1);
            allChannelOrder = sb2.toString();
        }
        editor.putString(KEY_ORDERED_CHANNEL_IDS, channelOrder).putString(KEY_ALL_CHANNELS_POSITIONS, allChannelOrder).putInt(KEY_LIVE_TV_CHANNEL_LAST_POSITION, this.mLiveTvChannelLastPosition).putInt(KEY_SPONSORED_GOOGLE_CHANNEL_LAST_POSITION, this.mSponsoredGoogleChannelLastPosition).putInt(KEY_SPONSORED_GOOGLE_CHANNEL_LAST_OOB_POSITION, this.mSponsoredGoogleChannelLastOobPosition).apply();
        sendChannelOrderChangeLogEventIfChanged(channelOrder);
    }

    private void updateSponsoredGoogleChannelLastOobPosition() {
        SharedPreferences.Editor editor = this.mContext.getSharedPreferences(PREF_CHANNEL_ORDER_MANAGER, 0).edit();
        this.mSponsoredGoogleChannelLastOobPosition = this.mSponsoredGoogleChannelOobPosition;
        editor.putInt(KEY_SPONSORED_GOOGLE_CHANNEL_LAST_OOB_POSITION, this.mSponsoredGoogleChannelLastOobPosition).apply();
    }

    /* access modifiers changed from: package-private */
    public void notifyUserHasManagedChannels() {
        this.mUserHasManagedChannels = true;
        this.mContext.getSharedPreferences(PREF_CHANNEL_ORDER_MANAGER, 0).edit().putBoolean(KEY_USER_HAS_MANAGED_CHANNELS, true).apply();
    }

    private void refreshChannelPositions() {
        List<HomeChannel> list = this.mChannels;
        if (list != null) {
            LongSparseArray<Integer> newChannelPositions = new LongSparseArray<>(list.size());
            int position = 0;
            for (HomeChannel channel : this.mChannels) {
                newChannelPositions.put(channel.getId(), Integer.valueOf(position));
                position++;
            }
            this.mChannelPositions = newChannelPositions;
            if (this.mIsNewChannelAdded) {
                this.mAllChannelPositions = this.mChannelPositions.clone();
                this.mIsNewChannelAdded = false;
            }
            updateLiveTvChannelLastPosition();
            updateSponsoredGoogleChannelLastPosition();
            saveChannelOrderToStorage();
            return;
        }
        throw new IllegalStateException("Channels must be set");
    }

    /* access modifiers changed from: package-private */
    public void onNewChannelAdded(long addedChannelId) {
        if (!this.mIsNewChannelAdded && this.mAllChannelPositions.get(addedChannelId) == null) {
            this.mIsNewChannelAdded = true;
        }
    }

    /* access modifiers changed from: package-private */
    public void onChannelRemoved(long channelId) {
        this.mAllChannelPositions.remove(channelId);
        refreshChannelPositions();
    }

    /* access modifiers changed from: package-private */
    public void onEmptyChannelHidden(HomeChannel channel) {
        if (!channel.isLegacy()) {
            this.mAllChannelPositions.remove(channel.getId());
        }
        refreshChannelPositions();
    }

    /* access modifiers changed from: package-private */
    public void applyOrder(List<HomeChannel> channels) {
        int i;
        LongSparseArray<Integer> outOfBoxChannelPositions = getOutOfBoxChannelPositions(channels);
        boolean shouldAddBackPinnedChannels = false;
        if (outOfBoxChannelPositions == EMPTY_OOB_CHANNEL_POSITIONS) {
            this.mPinnedChannelOrderManager.filterOutPinnedChannels(channels);
            shouldAddBackPinnedChannels = true;
        }
        if (outOfBoxChannelPositions == EMPTY_OOB_CHANNEL_POSITIONS) {
            long j = this.mLiveTvChannelId;
            if (j != -1 && this.mLiveTvChannelLastPosition == -1) {
                this.mAllChannelPositions.put(j, Integer.valueOf(this.mLiveTvChannelOobPosition));
            }
            long j2 = this.mSponsoredGoogleChannelId;
            if (j2 != -1) {
                if (this.mSponsoredGoogleChannelLastPosition == -1) {
                    this.mAllChannelPositions.put(j2, Integer.valueOf(this.mSponsoredGoogleChannelOobPosition));
                } else if (this.mSponsoredGoogleChannelOobPosition != this.mSponsoredGoogleChannelLastOobPosition) {
                    updateSponsoredGoogleChannelLastOobPosition();
                    if (this.mAllChannelPositions.get(this.mSponsoredGoogleChannelId) == null || (i = this.mSponsoredGoogleChannelOobPosition) <= this.mSponsoredGoogleChannelLastPosition) {
                        this.mAllChannelPositions.put(this.mSponsoredGoogleChannelId, Integer.valueOf(this.mSponsoredGoogleChannelOobPosition));
                    } else {
                        this.mAllChannelPositions.put(this.mSponsoredGoogleChannelId, Integer.valueOf(i + 1));
                    }
                }
            }
        }
        this.mChannelComparator.setLiveTvChannelId(this.mLiveTvChannelId);
        this.mChannelComparator.setSponsoredGoogleChannelId(this.mSponsoredGoogleChannelId);
        this.mChannelComparator.setChannelPositions(this.mAllChannelPositions);
        this.mChannelComparator.setOutOfBoxChannelPositions(outOfBoxChannelPositions);
        channels.sort(this.mChannelComparator);
        if (shouldAddBackPinnedChannels) {
            this.mPinnedChannelOrderManager.applyPinnedChannelOrder(channels);
        }
        setChannels(channels);
        refreshChannelPositions();
    }

    @NonNull
    private LongSparseArray<Integer> getOutOfBoxChannelPositions(List<HomeChannel> channels) {
        int i;
        int i2;
        long timePassedSinceStart = System.currentTimeMillis() - this.mFirstStartTimestamp;
        if (this.mUserHasManagedChannels || timePassedSinceStart > MAX_TIME_OOB_ORDER_HONORED || this.mOobPackageKeyPositions == null) {
            return EMPTY_OOB_CHANNEL_POSITIONS;
        }
        Map<ChannelConfigurationInfo, Iterator<Integer>> oobPackageKeyPositionIterators = new HashMap<>();
        for (ChannelConfigurationInfo key : this.mOobPackageKeyPositions.keySet()) {
            oobPackageKeyPositionIterators.put(key, this.mOobPackageKeyPositions.get(key).iterator());
        }
        LongSparseArray<Integer> oobChannelPositions = new LongSparseArray<>(Math.min(this.mOutOfBoxPackages.size(), 20) + 1);
        for (int i3 = 0; i3 < channels.size() && oobPackageKeyPositionIterators.size() != 0; i3++) {
            HomeChannel channel = channels.get(i3);
            ChannelConfigurationInfo key2 = new ChannelConfigurationInfo(channel.getPackageName(), channel.getSystemChannelKey());
            if (!oobPackageKeyPositionIterators.containsKey(key2)) {
                key2 = new ChannelConfigurationInfo(channel.getPackageName(), (String) null);
            }
            Iterator<Integer> positionValues = oobPackageKeyPositionIterators.get(key2);
            if (positionValues != null) {
                oobChannelPositions.put(channel.getId(), (Integer) positionValues.next());
                if (!positionValues.hasNext()) {
                    oobPackageKeyPositionIterators.remove(key2);
                }
            }
        }
        long j = this.mLiveTvChannelId;
        if (!(j == -1 || (i2 = this.mLiveTvChannelOobPosition) == -1)) {
            oobChannelPositions.put(j, Integer.valueOf(i2));
        }
        long j2 = this.mSponsoredGoogleChannelId;
        if (!(j2 == -1 || (i = this.mSponsoredGoogleChannelOobPosition) == -1)) {
            oobChannelPositions.put(j2, Integer.valueOf(i));
        }
        return oobChannelPositions;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Integer getChannelPosition(long channelId) {
        return this.mChannelPositions.get(channelId);
    }

    private int getNextChannelPositionToMoveTo(long channelId, int direction) {
        Integer position = this.mChannelPositions.get(channelId);
        if (position == null || this.mPinnedChannelOrderManager.isPinnedChannel(channelId)) {
            return -1;
        }
        int i = position.intValue() + direction;
        while (i >= 0 && i < this.mChannels.size()) {
            if (!this.mPinnedChannelOrderManager.isPinnedChannel(this.mChannels.get(i).getId())) {
                return i;
            }
            i += direction;
        }
        return -1;
    }

    public boolean canMoveChannelUp(long channelId) {
        return getNextChannelPositionToMoveTo(channelId, -1) != -1;
    }

    public boolean canMoveChannelDown(long channelId) {
        return getNextChannelPositionToMoveTo(channelId, 1) != -1;
    }

    public int moveChannelUp(long channelId) {
        int replacementPosition = getNextChannelPositionToMoveTo(channelId, -1);
        if (replacementPosition != -1) {
            return swapChannels(this.mChannelPositions.get(channelId).intValue(), replacementPosition);
        }
        StringBuilder sb = new StringBuilder(42);
        sb.append("Can't move channel ");
        sb.append(channelId);
        sb.append(" up");
        throw new IllegalArgumentException(sb.toString());
    }

    public int moveChannelDown(long channelId) {
        int replacementPosition = getNextChannelPositionToMoveTo(channelId, 1);
        if (replacementPosition != -1) {
            return swapChannels(this.mChannelPositions.get(channelId).intValue(), replacementPosition);
        }
        StringBuilder sb = new StringBuilder(44);
        sb.append("Can't move channel ");
        sb.append(channelId);
        sb.append(" down");
        throw new IllegalArgumentException(sb.toString());
    }

    private int swapChannels(int position, int replacementPosition) {
        notifyUserHasManagedChannels();
        HomeChannel channel = this.mChannels.get(position);
        HomeChannel replacementChannel = this.mChannels.get(replacementPosition);
        this.mChannelPositions.put(channel.getId(), Integer.valueOf(replacementPosition));
        this.mChannelPositions.put(replacementChannel.getId(), Integer.valueOf(position));
        this.mChannels.set(replacementPosition, channel);
        this.mChannels.set(position, replacementChannel);
        updateLiveTvChannelLastPosition();
        updateSponsoredGoogleChannelLastPosition();
        notifyChannelMoved(position, replacementPosition);
        this.mAllChannelPositions = this.mChannelPositions.clone();
        saveChannelOrderToStorage();
        return replacementPosition;
    }

    private void updateLiveTvChannelLastPosition() {
        Integer liveTvChannelLastPosition = this.mChannelPositions.get(this.mLiveTvChannelId);
        if (liveTvChannelLastPosition != null) {
            this.mLiveTvChannelLastPosition = liveTvChannelLastPosition.intValue();
        }
    }

    private void updateSponsoredGoogleChannelLastPosition() {
        Integer sponsoredGoogleChannelLastPosition = this.mChannelPositions.get(this.mSponsoredGoogleChannelId);
        if (sponsoredGoogleChannelLastPosition != null) {
            this.mSponsoredGoogleChannelLastPosition = sponsoredGoogleChannelLastPosition.intValue();
        }
    }

    private void notifyChannelMoved(int currentPosition, int newPosition) {
        List<HomeChannelsObserver> list = this.mChannelsObservers;
        if (list != null) {
            for (HomeChannelsObserver observer : list) {
                observer.onChannelMove(currentPosition, newPosition);
            }
        }
    }

    private void sendChannelOrderChangeLogEventIfChanged(@NonNull String channelOrder) {
        SharedPreferences prefs = this.mContext.getSharedPreferences(PREF_CHANNEL_ORDER_MANAGER, 0);
        String loggedChannelIds = prefs.getString(KEY_LOGGED_CHANNEL_ID_ORDER, null);
        Intent intent = new Intent(Constants.ACTION_CHANNEL_ORDER_CHANGE_LOG_EVENT).putExtra(Constants.EXTRA_CHANNEL_IDS, channelOrder);
        intent.setPackage(Constants.TVRECOMMENDATIONS_PACKAGE_NAME);
        if (loggedChannelIds == null) {
            List<ResolveInfo> receivers = this.mContext.getPackageManager().queryBroadcastReceivers(intent, 0);
            if (receivers != null && !receivers.isEmpty()) {
                this.mContext.sendBroadcast(intent);
                prefs.edit().putString(KEY_LOGGED_CHANNEL_ID_ORDER, channelOrder).apply();
            }
        } else if (!TextUtils.equals(channelOrder, loggedChannelIds)) {
            this.mContext.sendBroadcast(intent);
            prefs.edit().putString(KEY_LOGGED_CHANNEL_ID_ORDER, channelOrder).apply();
        }
    }

    private static class ChannelComparator implements Comparator<HomeChannel> {
        private LongSparseArray<Integer> mChannelPositions;
        private long mLiveTvChannelId;
        private LongSparseArray<Integer> mOobChannelPositions;
        private long mSponsoredGoogleChannelId;

        private ChannelComparator() {
            this.mLiveTvChannelId = -1;
            this.mSponsoredGoogleChannelId = -1;
        }

        /* access modifiers changed from: package-private */
        public void setLiveTvChannelId(long oobLiveTvChannelId) {
            this.mLiveTvChannelId = oobLiveTvChannelId;
        }

        /* access modifiers changed from: package-private */
        public void setSponsoredGoogleChannelId(long sponsoredGoogleChannelId) {
            this.mSponsoredGoogleChannelId = sponsoredGoogleChannelId;
        }

        /* access modifiers changed from: package-private */
        public void setChannelPositions(LongSparseArray<Integer> channelPositions) {
            this.mChannelPositions = channelPositions;
        }

        /* access modifiers changed from: package-private */
        public void setOutOfBoxChannelPositions(LongSparseArray<Integer> channelPositions) {
            this.mOobChannelPositions = channelPositions;
        }

        private int compareChannel(long leftChannelId, long rightChannelId, int positionLeft, int positionRight) {
            if (positionLeft != positionRight) {
                return Integer.compare(positionLeft, positionRight);
            }
            long j = this.mSponsoredGoogleChannelId;
            if (leftChannelId == j) {
                return -1;
            }
            if (rightChannelId == j) {
                return 1;
            }
            long j2 = this.mLiveTvChannelId;
            if (leftChannelId == j2) {
                return -1;
            }
            if (rightChannelId == j2) {
                return 1;
            }
            return 0;
        }

        public int compare(@NonNull HomeChannel channelLeft, @NonNull HomeChannel channelRight) {
            if (channelLeft == channelRight) {
                return 0;
            }
            Integer positionLeft = this.mOobChannelPositions.get(channelLeft.getId());
            Integer positionRight = this.mOobChannelPositions.get(channelRight.getId());
            if (positionLeft != null && positionRight != null) {
                return compareChannel(channelLeft.getId(), channelRight.getId(), positionLeft.intValue(), positionRight.intValue());
            }
            if (positionRight != null) {
                return 1;
            }
            if (positionLeft != null) {
                return -1;
            }
            Integer positionLeft2 = this.mChannelPositions.get(channelLeft.getId());
            Integer positionRight2 = this.mChannelPositions.get(channelRight.getId());
            if (positionLeft2 != null && positionRight2 != null) {
                return compareChannel(channelLeft.getId(), channelRight.getId(), positionLeft2.intValue(), positionRight2.intValue());
            }
            if (positionRight2 != null) {
                return 1;
            }
            if (positionLeft2 != null) {
                return -1;
            }
            return (channelLeft.getId() > channelRight.getId() ? 1 : (channelLeft.getId() == channelRight.getId() ? 0 : -1));
        }
    }
}
