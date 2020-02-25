package com.google.android.tvlauncher.model;

import android.database.Cursor;
import android.support.annotation.VisibleForTesting;

import androidx.tvprovider.media.p005tv.TvContractCompat;

import com.google.android.tvrecommendations.shared.util.Constants;

public class HomeChannel {
    public static final String[] PROJECTION = {"_id", TvContractCompat.Channels.COLUMN_DISPLAY_NAME, TvContractCompat.Channels.COLUMN_APP_LINK_INTENT_URI, "system_channel_key", "logo_content_description", "package_name", Constants.COLUMN_SUBTYPE, "internal_provider_data"};
    private boolean mCanMove;
    private boolean mCanRemove;
    private String mDisplayName;
    private long mId;
    private boolean mIsSponsored;
    private String mLaunchUri;
    private boolean mLegacy;
    private String mLogoContentDescription;
    private String mPackageName;
    private int mSubtype;
    private String mSystemChannelKey;

    private HomeChannel() {
        this.mCanMove = true;
        this.mCanRemove = true;
        this.mLegacy = false;
    }

    @VisibleForTesting
    public HomeChannel(long id, String packageName, String systemChannelKey) {
        this.mCanMove = true;
        this.mCanRemove = true;
        this.mLegacy = false;
        this.mId = id;
        this.mPackageName = packageName;
        this.mSystemChannelKey = systemChannelKey;
    }

    @VisibleForTesting
    public HomeChannel(long id, String packageName) {
        this(id, packageName, null);
    }

    /* JADX INFO: Multiple debug info for r2v7 byte[]: [D('index' int), D('packageNameBlob' byte[])] */
    public static HomeChannel fromCursor(Cursor cursor) {
        HomeChannel channel = new HomeChannel();
        int index = 0 + 1;
        channel.mId = cursor.getLong(0);
        int index2 = index + 1;
        channel.mDisplayName = cursor.getString(index);
        int index3 = index2 + 1;
        channel.mLaunchUri = cursor.getString(index2);
        int index4 = index3 + 1;
        channel.mSystemChannelKey = cursor.getString(index3);
        int index5 = index4 + 1;
        channel.mLogoContentDescription = cursor.getString(index4);
        int index6 = index5 + 1;
        channel.mPackageName = cursor.getString(index5);
        int index7 = index6 + 1;
        channel.mSubtype = cursor.getInt(index6);
        if (Constants.TVRECOMMENDATIONS_PACKAGE_NAME.equals(channel.mPackageName)) {
            int i = index7 + 1;
            byte[] packageNameBlob = cursor.getBlob(index7);
            if (packageNameBlob != null) {
                String packageName = new String(packageNameBlob, 0, packageNameBlob.length - 1);
                if (!Constants.SPONSORED_CHANNEL_LEGACY_PACKAGE_NAME.equals(packageName)) {
                    channel.mPackageName = packageName;
                    channel.mLegacy = true;
                }
            }
        }
        return channel;
    }

    public long getId() {
        return this.mId;
    }

    public String getDisplayName() {
        return this.mDisplayName;
    }

    @VisibleForTesting
    public void setDisplayName(String displayName) {
        this.mDisplayName = displayName;
    }

    public String getLaunchUri() {
        return this.mLaunchUri;
    }

    @VisibleForTesting
    public void setLaunchUri(String launchUri) {
        this.mLaunchUri = launchUri;
    }

    public String getLogoContentDescription() {
        return this.mLogoContentDescription;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getSystemChannelKey() {
        return this.mSystemChannelKey;
    }

    public boolean isLegacy() {
        return this.mLegacy;
    }

    public boolean isSponsored() {
        return this.mIsSponsored;
    }

    public void setSponsored(boolean sponsored) {
        this.mIsSponsored = sponsored;
    }

    public int getSubtype() {
        return this.mSubtype;
    }

    public boolean canMove() {
        return this.mCanMove;
    }

    public void setCanMove(boolean canMove) {
        this.mCanMove = canMove;
    }

    public boolean canRemove() {
        return this.mCanRemove;
    }

    public void setCanRemove(boolean canRemove) {
        this.mCanRemove = canRemove;
    }

    public boolean equals(Object obj) {
        return (obj instanceof HomeChannel) && this.mId == ((HomeChannel) obj).getId();
    }

    public int hashCode() {
        return (int) this.mId;
    }

    public String toString() {
        long j = this.mId;
        String str = this.mDisplayName;
        String str2 = this.mPackageName;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 71 + String.valueOf(str2).length());
        sb.append("HomeChannel{mId=");
        sb.append(j);
        sb.append(", mDisplayName='");
        sb.append(str);
        sb.append('\'');
        sb.append(", mPackageName='");
        sb.append(str2);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
