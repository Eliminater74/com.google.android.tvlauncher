package com.google.android.tvlauncher.appsview.data;

import android.content.Context;

import com.google.android.tvlauncher.appsview.LaunchItem;

class InstallingLaunchItemsManager implements InstallingLaunchItemListener {
    private static InstallingLaunchItemsManager sInstallingLaunchItemsManager;
    private final Context mContext;
    private InstallingLaunchItemListener mInstallingLaunchItemListener;

    private InstallingLaunchItemsManager(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static InstallingLaunchItemsManager getInstance(Context context) {
        if (sInstallingLaunchItemsManager == null) {
            synchronized (InstallingLaunchItemsManager.class) {
                if (sInstallingLaunchItemsManager == null) {
                    sInstallingLaunchItemsManager = new InstallingLaunchItemsManager(context);
                }
            }
        }
        return sInstallingLaunchItemsManager;
    }

    public void onInstallingLaunchItemAdded(LaunchItem launchItem) {
    }

    public void onInstallingLaunchItemChanged(LaunchItem launchItem) {
    }

    public void onInstallingLaunchItemRemoved(LaunchItem launchItem, boolean success) {
    }

    public void setInstallingLaunchItemListener(InstallingLaunchItemListener listener) {
        this.mInstallingLaunchItemListener = listener;
    }
}
