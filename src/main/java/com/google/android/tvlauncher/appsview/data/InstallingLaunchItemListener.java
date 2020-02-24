package com.google.android.tvlauncher.appsview.data;

import com.google.android.tvlauncher.appsview.LaunchItem;

public interface InstallingLaunchItemListener {
    void onInstallingLaunchItemAdded(LaunchItem launchItem);

    void onInstallingLaunchItemChanged(LaunchItem launchItem);

    void onInstallingLaunchItemRemoved(LaunchItem launchItem, boolean z);
}
