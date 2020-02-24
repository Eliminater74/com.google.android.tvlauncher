package com.google.android.tvlauncher.appsview;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.annotation.NonNull;
import android.text.TextUtils;

public class LaunchItem implements Comparable<LaunchItem> {
    public static final int PROGRESS_UNKNOWN = -1;
    private static final String TAG = "LaunchItem";
    private String mBannerUri;
    private String mDataUri;
    private String mIconUri;
    private int mInstallProgressPercent = -1;
    private InstallState mInstallState = InstallState.UNKNOWN;
    private Intent mIntent;
    private boolean mIsAppLink;
    private boolean mIsGame;
    private boolean mIsInitialInstall;
    private CharSequence mLabel;
    private long mLastUpdateTime;
    private String mPkgName;
    private ResolveInfo mResolveInfo;

    public enum InstallState {
        UNKNOWN,
        INSTALL_PENDING,
        UPDATE_PENDING,
        RESTORE_PENDING,
        DOWNLOADING,
        INSTALLING
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    public String getPackageName() {
        return this.mPkgName;
    }

    public String getIconUri() {
        return this.mIconUri;
    }

    public String getBannerUri() {
        return this.mBannerUri;
    }

    public String getDataUri() {
        return this.mDataUri;
    }

    public ResolveInfo getResolveInfo() {
        return this.mResolveInfo;
    }

    public int getInstallProgressPercent() {
        return this.mInstallProgressPercent;
    }

    public boolean isInitialInstall() {
        return this.mIsInitialInstall;
    }

    public boolean isInstalling() {
        return this.mInstallState != InstallState.UNKNOWN;
    }

    public InstallState getInstallState() {
        return this.mInstallState;
    }

    public boolean isGame() {
        return this.mIsGame;
    }

    public boolean isAppLink() {
        return this.mIsAppLink;
    }

    public long getLastUpdateTime() {
        return this.mLastUpdateTime;
    }

    public int compareTo(@NonNull LaunchItem another) {
        CharSequence o1Label = getLabel();
        CharSequence o2Label = another.getLabel();
        if (o1Label == null && o2Label == null) {
            return 0;
        }
        if (o1Label == null) {
            return 1;
        }
        if (o2Label == null) {
            return -1;
        }
        return o1Label.toString().compareToIgnoreCase(o2Label.toString());
    }

    public int hashCode() {
        return this.mPkgName.hashCode();
    }

    public String toString() {
        String valueOf = String.valueOf(this.mLabel);
        String packageName = getPackageName();
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 4 + String.valueOf(packageName).length());
        sb.append(valueOf);
        sb.append(" -- ");
        sb.append(packageName);
        return sb.toString();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LaunchItem)) {
            return false;
        }
        return TextUtils.equals(this.mPkgName, ((LaunchItem) other).getPackageName());
    }

    public boolean hasSamePackageName(ResolveInfo info) {
        return TextUtils.equals(this.mPkgName, info.activityInfo.packageName);
    }

    public boolean hasSamePackageName(String packageName) {
        return TextUtils.equals(this.mPkgName, packageName);
    }

    public void setInitialInstall(boolean initialInstall) {
        this.mIsInitialInstall = initialInstall;
    }

    public void setInstallProgressPercent(int progressPercent) {
        this.mInstallProgressPercent = progressPercent;
    }

    public void setIntent(Intent intent) {
        this.mIntent = intent;
    }

    public void setLabel(CharSequence label) {
        this.mLabel = label;
    }

    public void setPackageName(String pkgName) {
        this.mPkgName = pkgName;
    }

    public void setIconUri(String iconUri) {
        this.mIconUri = iconUri;
    }

    public void setBannerUri(String bannerUri) {
        this.mBannerUri = bannerUri;
    }

    public void setDataUri(String dataUri) {
        this.mDataUri = dataUri;
    }

    public void setResolveInfo(ResolveInfo resolveInfo) {
        this.mResolveInfo = resolveInfo;
    }

    public void setIsGame(boolean isGame) {
        this.mIsGame = isGame;
    }

    public void setIsAppLink(boolean isAppLink) {
        this.mIsAppLink = isAppLink;
    }

    public void setInstallState(InstallState state) {
        this.mInstallState = state;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.mLastUpdateTime = lastUpdateTime;
    }

    public void recycle() {
        this.mInstallProgressPercent = -1;
        this.mInstallState = InstallState.UNKNOWN;
        this.mIntent = null;
        this.mLabel = null;
        this.mPkgName = null;
        this.mIsInitialInstall = false;
        this.mIsGame = false;
        this.mResolveInfo = null;
        this.mIconUri = null;
        this.mBannerUri = null;
        this.mIsAppLink = false;
        this.mDataUri = null;
    }
}
