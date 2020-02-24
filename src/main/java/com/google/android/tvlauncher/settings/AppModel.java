package com.google.android.tvlauncher.settings;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.content.IntentCompat;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.data.PackagesWithChannelsObserver;
import com.google.android.tvlauncher.data.TvDataManager;
import com.google.android.tvlauncher.model.ChannelPackage;
import com.google.android.tvrecommendations.shared.util.Constants;
import java.util.ArrayList;
import java.util.List;

class AppModel {
    private Context mContext;
    private LoadAppsCallback mLoadAppsCallback;
    @VisibleForTesting
    final PackagesWithChannelsObserver mPackagesObserver = new PackagesWithChannelsObserver() {
        public void onPackagesChange() {
            AppModel.this.onPackagesDataLoaded();
        }
    };
    private TvDataManager mTvDataManager;

    interface LoadAppsCallback {
        void onAppsChanged();

        void onAppsLoaded(List<AppInfo> list);
    }

    static class AppInfo implements Comparable<AppInfo> {
        ChannelPackage mChannelPackage;
        final String mPackageName;
        ResolveInfo mResolveInfo;
        CharSequence mTitle;

        AppInfo(String packageName, ChannelPackage channelPackage, ApplicationInfo applicationInfo, PackageManager packageManager) {
            this(packageName, channelPackage, packageManager.getApplicationLabel(applicationInfo), AppModel.getResolveInfo(packageName, packageManager));
        }

        AppInfo(String packageName, ChannelPackage channelPackage, CharSequence title, ResolveInfo resolveInfo) {
            this.mPackageName = packageName;
            this.mChannelPackage = channelPackage;
            this.mTitle = title;
            this.mResolveInfo = resolveInfo;
        }

        public int compareTo(@NonNull AppInfo o) {
            CharSequence charSequence = this.mTitle;
            if (charSequence == null) {
                return o.mTitle != null ? 1 : 0;
            }
            return charSequence.toString().compareToIgnoreCase(o.mTitle.toString());
        }
    }

    AppModel(Context context) {
        this.mContext = context;
        this.mTvDataManager = TvDataManager.getInstance(context);
        this.mTvDataManager.registerPackagesWithChannelsObserver(this.mPackagesObserver);
    }

    @VisibleForTesting
    AppModel(Context context, TvDataManager tvDataManager) {
        this.mContext = context;
        this.mTvDataManager = tvDataManager;
    }

    /* access modifiers changed from: package-private */
    public void onPause() {
        this.mLoadAppsCallback = null;
        this.mTvDataManager.unregisterPackagesWithChannelsObserver(this.mPackagesObserver);
    }

    /* access modifiers changed from: package-private */
    public void loadApps(LoadAppsCallback callback) {
        this.mLoadAppsCallback = callback;
        this.mTvDataManager.registerPackagesWithChannelsObserver(this.mPackagesObserver);
        if (this.mTvDataManager.isPackagesWithChannelsDataLoaded()) {
            onPackagesDataLoaded();
        } else {
            this.mTvDataManager.loadPackagesWithChannelsData();
        }
    }

    /* access modifiers changed from: private */
    public void onPackagesDataLoaded() {
        if (this.mLoadAppsCallback != null) {
            List<ChannelPackage> channelPackages = this.mTvDataManager.getPackagesWithChannels();
            List<AppInfo> applicationInfos = new ArrayList<>(channelPackages.size());
            PackageManager packageManager = this.mContext.getPackageManager();
            for (ChannelPackage channelPackage : channelPackages) {
                String packageName = channelPackage.getPackageName();
                if (Constants.SPONSORED_CHANNEL_LEGACY_PACKAGE_NAME.equals(packageName)) {
                    applicationInfos.add(new AppInfo(packageName, channelPackage, this.mContext.getString(C1188R.string.promotional_channel_setting_panel_title), (ResolveInfo) null));
                } else {
                    ApplicationInfo applicationInfo = getApplicationInfo(packageName, packageManager);
                    if (applicationInfo != null) {
                        applicationInfos.add(new AppInfo(packageName, channelPackage, applicationInfo, packageManager));
                    }
                }
            }
            this.mLoadAppsCallback.onAppsLoaded(applicationInfos);
        }
    }

    private ApplicationInfo getApplicationInfo(String packageName, PackageManager packageManager) {
        try {
            return packageManager.getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static ResolveInfo getResolveInfo(String packageName, PackageManager packageManager) {
        Intent mainIntent = new Intent("android.intent.action.MAIN");
        mainIntent.setPackage(packageName).addCategory(IntentCompat.CATEGORY_LEANBACK_LAUNCHER);
        List<ResolveInfo> infos = packageManager.queryIntentActivities(mainIntent, 1);
        if (infos == null || infos.size() <= 0) {
            return null;
        }
        return infos.get(0);
    }
}
