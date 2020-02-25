package com.google.android.tvlauncher.appsview.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.VisibleForTesting;

import com.google.android.tvlauncher.util.PackageUtils;
import com.google.android.tvrecommendations.shared.util.Constants;

public class InstallingLaunchItemsDataHelper {
    private static final String LAUNCHER_VERSION_KEY = "launcher_version";
    private static final String PREFERENCE_FILE_NAME = "installing_launch_items_data";
    private static InstallingLaunchItemsDataHelper sInstallingLaunchItemsDataHelper;
    private final SharedPreferences mPrefs;

    private InstallingLaunchItemsDataHelper(Context context) {
        this.mPrefs = context.getSharedPreferences(PREFERENCE_FILE_NAME, 0);
        this.mPrefs.edit().putInt(LAUNCHER_VERSION_KEY, PackageUtils.getApplicationVersionCode(context, Constants.LAUNCHER_PACKAGE_NAME)).apply();
    }

    public static InstallingLaunchItemsDataHelper getInstance(Context context) {
        if (sInstallingLaunchItemsDataHelper == null) {
            synchronized (InstallingLaunchItemsDataHelper.class) {
                if (sInstallingLaunchItemsDataHelper == null) {
                    sInstallingLaunchItemsDataHelper = new InstallingLaunchItemsDataHelper(context.getApplicationContext());
                }
            }
        }
        return sInstallingLaunchItemsDataHelper;
    }

    @VisibleForTesting
    static void setInstance(InstallingLaunchItemsDataHelper installingLaunchItemsDataHelper) {
        sInstallingLaunchItemsDataHelper = installingLaunchItemsDataHelper;
    }

    /* access modifiers changed from: package-private */
    public void storeDataForPackage(String pkgName, boolean isGame) {
        this.mPrefs.edit().putBoolean(pkgName, isGame).apply();
    }

    /* access modifiers changed from: package-private */
    public void removeGameFlagForPackage(String pkgName) {
        this.mPrefs.edit().remove(pkgName).apply();
    }

    /* access modifiers changed from: package-private */
    public boolean getGameFlagForPackage(String packageName) {
        return this.mPrefs.getBoolean(packageName, false);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void clearForTesting() {
        this.mPrefs.edit().clear().putInt(LAUNCHER_VERSION_KEY, this.mPrefs.getInt(LAUNCHER_VERSION_KEY, -1)).apply();
    }
}
