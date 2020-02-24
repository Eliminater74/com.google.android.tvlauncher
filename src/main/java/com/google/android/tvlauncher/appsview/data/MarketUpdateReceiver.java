package com.google.android.tvlauncher.appsview.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.tvlauncher.appsview.LaunchItem;

public class MarketUpdateReceiver extends BroadcastReceiver {
    private static final String ACTION_PACKAGE_DEQUEUED = "com.android.launcher.action.ACTION_PACKAGE_DEQUEUED";
    private static final String ACTION_PACKAGE_DOWNLOADING = "com.android.launcher.action.ACTION_PACKAGE_DOWNLOADING";
    private static final String ACTION_PACKAGE_ENQUEUED = "com.android.launcher.action.ACTION_PACKAGE_ENQUEUED";
    private static final String ACTION_PACKAGE_INSTALLING = "com.android.launcher.action.ACTION_PACKAGE_INSTALLING";
    private static final boolean DEBUG = false;
    private static final String EXTRA_KEY_APP_BANNER = "app_banner";
    private static final String EXTRA_KEY_APP_DETAIL_INTENT = "app_detailIntent";
    private static final String EXTRA_KEY_APP_ICON = "app_icon";
    private static final String EXTRA_KEY_APP_IS_GAME = "app_is_game";
    private static final String EXTRA_KEY_APP_NAME = "app_name";
    private static final String EXTRA_KEY_INSTALL_COMPLETED = "com.android.launcher.action.INSTALL_COMPLETED";
    private static final String EXTRA_KEY_PROGRESS = "progress";
    private static final String EXTRA_KEY_REASON = "reason";
    private static final String EXTRA_USER_INITIATED = "user_initiated";
    private static final String EXTRA_VALUE_REASON_INSTALL = "install";
    private static final String EXTRA_VALUE_REASON_RESTORE = "restore";
    private static final String EXTRA_VALUE_REASON_UPDATE = "update";
    private static final String TAG = "MarketUpdateReceiver";

    public void onReceive(Context context, Intent intent) {
        Uri uri = intent.getData();
        if (uri != null) {
            LaunchItemsManager launchItemsManager = LaunchItemsManagerProvider.getInstance(context);
            String pkgName = uri.getSchemeSpecificPart();
            if (!TextUtils.isEmpty(pkgName)) {
                String action = intent.getAction();
                LaunchItem installLaunchItem = launchItemsManager.getInstallingLaunchItem(pkgName);
                if (installLaunchItem != null || intent.getBooleanExtra(EXTRA_USER_INITIATED, false)) {
                    boolean wasAdded = false;
                    if (isLeanbackApp(context, pkgName)) {
                        if (installLaunchItem == null) {
                            if (!ACTION_PACKAGE_DEQUEUED.equals(action)) {
                                wasAdded = true;
                                installLaunchItem = createInstallLaunchItem(context, pkgName, intent);
                            } else {
                                return;
                            }
                        }
                        boolean success = false;
                        if (ACTION_PACKAGE_ENQUEUED.equals(action)) {
                            installLaunchItem.setInstallProgressPercent(-1);
                            String reason = EXTRA_VALUE_REASON_INSTALL;
                            if (intent.hasExtra(EXTRA_KEY_REASON)) {
                                reason = intent.getStringExtra(EXTRA_KEY_REASON);
                            }
                            if (EXTRA_VALUE_REASON_INSTALL.equals(reason)) {
                                installLaunchItem.setInstallState(LaunchItem.InstallState.INSTALL_PENDING);
                            } else if (EXTRA_VALUE_REASON_UPDATE.equals(reason)) {
                                installLaunchItem.setInstallState(LaunchItem.InstallState.UPDATE_PENDING);
                            } else if (EXTRA_VALUE_REASON_RESTORE.equals(reason)) {
                                installLaunchItem.setInstallState(LaunchItem.InstallState.RESTORE_PENDING);
                            } else {
                                installLaunchItem.setInstallState(LaunchItem.InstallState.UNKNOWN);
                            }
                            intent.getBooleanExtra(EXTRA_USER_INITIATED, false);
                        } else if (ACTION_PACKAGE_DOWNLOADING.equals(action)) {
                            installLaunchItem.setInstallProgressPercent(intent.getIntExtra("progress", 0));
                            installLaunchItem.setInstallState(LaunchItem.InstallState.DOWNLOADING);
                        } else if (ACTION_PACKAGE_INSTALLING.equals(action)) {
                            installLaunchItem.setInstallProgressPercent(-1);
                            installLaunchItem.setInstallState(LaunchItem.InstallState.INSTALLING);
                        } else if (ACTION_PACKAGE_DEQUEUED.equals(action)) {
                            installLaunchItem.setInstallProgressPercent(-1);
                            installLaunchItem.setInstallState(LaunchItem.InstallState.UNKNOWN);
                            success = intent.getBooleanExtra(EXTRA_KEY_INSTALL_COMPLETED, false);
                        } else {
                            installLaunchItem.setInstallProgressPercent(-1);
                            installLaunchItem.setInstallState(LaunchItem.InstallState.UNKNOWN);
                        }
                        if (ACTION_PACKAGE_DEQUEUED.equals(action)) {
                            launchItemsManager.onInstallingLaunchItemRemoved(installLaunchItem, success);
                        } else if (wasAdded) {
                            launchItemsManager.onInstallingLaunchItemAdded(installLaunchItem);
                        } else {
                            launchItemsManager.onInstallingLaunchItemChanged(installLaunchItem);
                        }
                    } else if (installLaunchItem != null && ACTION_PACKAGE_DEQUEUED.equals(action) && !intent.getBooleanExtra(EXTRA_KEY_INSTALL_COMPLETED, false)) {
                        installLaunchItem.setInstallState(LaunchItem.InstallState.UNKNOWN);
                        installLaunchItem.setInstallProgressPercent(-1);
                        launchItemsManager.onInstallingLaunchItemRemoved(installLaunchItem, false);
                    }
                }
            }
        }
    }

    private boolean isLeanbackApp(Context context, String pkgName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(pkgName, 1);
            if (pm.getLeanbackLaunchIntentForPackage(pkgName) != null) {
                return true;
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            return true;
        }
    }

    private LaunchItem createInstallLaunchItem(Context context, String pkgName, Intent intent) {
        String appName = intent.getStringExtra(EXTRA_KEY_APP_NAME);
        String appIconUri = intent.getStringExtra(EXTRA_KEY_APP_ICON);
        String appBannerUri = intent.getStringExtra(EXTRA_KEY_APP_BANNER);
        boolean appIsGame = intent.getBooleanExtra(EXTRA_KEY_APP_IS_GAME, false);
        LaunchItem item = new LaunchItem();
        item.setLabel(appName);
        item.setPackageName(pkgName);
        item.setIsGame(appIsGame);
        item.setIntent(getMarketIntentForInstallingItem(context, pkgName));
        item.setIconUri(appIconUri);
        item.setBannerUri(appBannerUri);
        item.setInitialInstall(true);
        return item;
    }

    private Intent getMarketIntentForInstallingItem(Context context, String packageName) {
        return new Intent("android.intent.action.VIEW").setData(new Uri.Builder().scheme("market").authority("details").appendQueryParameter(TtmlNode.ATTR_ID, packageName).build()).putExtra("android.intent.extra.REFERRER", new Uri.Builder().scheme("android-app").authority(context.getPackageName()).build());
    }
}
