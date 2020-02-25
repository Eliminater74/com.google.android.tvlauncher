package com.google.android.tvlauncher.model;

import android.text.TextUtils;

import java.util.Objects;

public class ChannelPackage {
    private int mChannelCount;
    private boolean mOnlyChannelBrowsable;
    private String mOnlyChannelDisplayName;
    private boolean mOnlyChannelEmpty;
    private long mOnlyChannelId;
    private boolean mOnlyChannelRemovable = true;
    private String mPackageName;

    public ChannelPackage(String packageName, int channelCount) {
        this.mPackageName = packageName;
        this.mChannelCount = channelCount;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public int getChannelCount() {
        return this.mChannelCount;
    }

    public void setOnlyChannelAttributes(Channel onlyChannel) {
        this.mOnlyChannelDisplayName = onlyChannel.getDisplayName();
        this.mOnlyChannelId = onlyChannel.getId();
        this.mOnlyChannelBrowsable = onlyChannel.isBrowsable();
        this.mOnlyChannelEmpty = onlyChannel.isEmpty();
        this.mOnlyChannelRemovable = onlyChannel.canRemove();
    }

    public String getOnlyChannelDisplayName() {
        return this.mOnlyChannelDisplayName;
    }

    public long getOnlyChannelId() {
        return this.mOnlyChannelId;
    }

    public boolean isOnlyChannelBrowsable() {
        return this.mOnlyChannelBrowsable;
    }

    public boolean isOnlyChannelEmpty() {
        return this.mOnlyChannelEmpty;
    }

    public boolean isOnlyChannelRemovable() {
        return this.mOnlyChannelRemovable;
    }

    public String toString() {
        String str = this.mPackageName;
        int i = this.mChannelCount;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 58);
        sb.append("ChannelPackage{mPackageName='");
        sb.append(str);
        sb.append('\'');
        sb.append(", mChannelCount=");
        sb.append(i);
        sb.append('}');
        return sb.toString();
    }

    public boolean equals(Object obj) {
        return (obj instanceof ChannelPackage) && TextUtils.equals(this.mPackageName, ((ChannelPackage) obj).getPackageName());
    }

    public int hashCode() {
        return Objects.hashCode(this.mPackageName);
    }
}
