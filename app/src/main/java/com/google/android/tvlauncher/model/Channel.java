package com.google.android.tvlauncher.model;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import androidx.tvprovider.media.p005tv.TvContractCompat;
import com.google.android.tvrecommendations.shared.util.Constants;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

@TargetApi(26)
public class Channel implements Comparable<Channel> {
    public static final String[] PROJECTION = {"_id", TvContractCompat.Channels.COLUMN_DISPLAY_NAME, "browsable", "system_channel_key", "configuration_display_order", "logo_content_description", "package_name", "internal_provider_data"};
    private boolean mBrowsable;
    private boolean mCanRemove = true;
    private int mConfigurationDisplayOrder;
    private String mDisplayName;
    private long mId;
    private boolean mIsEmpty;
    private boolean mIsSponsored;
    private String mLogoContentDescription;
    private String mPackageName;
    private String mSystemChannelKey;

    /* JADX INFO: Multiple debug info for r2v7 byte[]: [D('index' int), D('packageNameBlob' byte[])] */
    public static Channel fromCursor(Cursor cursor) {
        Channel channel = new Channel();
        int index = 0 + 1;
        channel.mId = cursor.getLong(0);
        int index2 = index + 1;
        channel.mDisplayName = cursor.getString(index);
        int index3 = index2 + 1;
        channel.mBrowsable = cursor.getInt(index2) == 1;
        int index4 = index3 + 1;
        channel.mSystemChannelKey = cursor.getString(index3);
        int index5 = index4 + 1;
        channel.mConfigurationDisplayOrder = cursor.getInt(index4);
        int index6 = index5 + 1;
        channel.mLogoContentDescription = cursor.getString(index5);
        int index7 = index6 + 1;
        channel.mPackageName = cursor.getString(index6);
        if (Constants.TVRECOMMENDATIONS_PACKAGE_NAME.equals(channel.mPackageName) && cursor.getBlob(index7) != null) {
            int i = index7 + 1;
            byte[] packageNameBlob = cursor.getBlob(index7);
            String packageName = new String(packageNameBlob, 0, packageNameBlob.length - 1);
            if (!Constants.SPONSORED_CHANNEL_LEGACY_PACKAGE_NAME.equals(packageName)) {
                channel.mPackageName = packageName;
            }
        }
        return channel;
    }

    public long getId() {
        return this.mId;
    }

    @VisibleForTesting
    public void setId(long id) {
        this.mId = id;
    }

    public String getDisplayName() {
        return this.mDisplayName;
    }

    @VisibleForTesting
    public void setDisplayName(String displayName) {
        this.mDisplayName = displayName;
    }

    public boolean isBrowsable() {
        return this.mBrowsable;
    }

    @VisibleForTesting
    public void setBrowsable(boolean browsable) {
        this.mBrowsable = browsable;
    }

    public String getSystemChannelKey() {
        return this.mSystemChannelKey;
    }

    public int getConfigurationDisplayOrder() {
        return this.mConfigurationDisplayOrder;
    }

    @VisibleForTesting
    public void setConfigurationDisplayOrder(int order) {
        this.mConfigurationDisplayOrder = order;
    }

    public String getLogoContentDescription() {
        return this.mLogoContentDescription;
    }

    @VisibleForTesting
    public void setLogoContentDescription(String logoContentDescription) {
        this.mLogoContentDescription = logoContentDescription;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    @VisibleForTesting
    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
    }

    public boolean isEmpty() {
        return this.mIsEmpty;
    }

    public void setIsEmpty(boolean empty) {
        this.mIsEmpty = empty;
    }

    public boolean isSponsored() {
        return this.mIsSponsored;
    }

    public void setSponsored(boolean sponsored) {
        this.mIsSponsored = sponsored;
    }

    public boolean canRemove() {
        return this.mCanRemove;
    }

    public void setCanRemove(boolean canRemove) {
        this.mCanRemove = canRemove;
    }

    public String toString() {
        long j = this.mId;
        String str = this.mDisplayName;
        boolean z = this.mBrowsable;
        String str2 = this.mPackageName;
        int i = this.mConfigurationDisplayOrder;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + ClientAnalytics.LogRequest.LogSource.CLASSROOM_VALUE + String.valueOf(str2).length());
        sb.append("Channel{mId=");
        sb.append(j);
        sb.append(", mDisplayName='");
        sb.append(str);
        sb.append('\'');
        sb.append(", mBrowsable=");
        sb.append(z);
        sb.append(", mPackageName='");
        sb.append(str2);
        sb.append('\'');
        sb.append(", mConfigurationDisplayOrder=");
        sb.append(i);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }

    public int compareTo(@NonNull Channel o) {
        if (this.mDisplayName == null && o.getDisplayName() == null) {
            return 0;
        }
        if (this.mDisplayName == null) {
            return 1;
        }
        if (o.getDisplayName() == null) {
            return -1;
        }
        return this.mDisplayName.compareToIgnoreCase(o.getDisplayName());
    }
}
