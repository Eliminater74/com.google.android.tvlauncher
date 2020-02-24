package com.google.android.tvlauncher.appsview;

import android.content.Intent;
import android.view.View;
import com.google.android.libraries.social.analytics.visualelement.VisualElementTag;

public interface OnAppsViewActionListener {
    void onLaunchApp(Intent intent, View view);

    void onShowAppInfo(String str);

    void onShowEditModeView(int i, int i2);

    void onStoreLaunch(Intent intent, VisualElementTag visualElementTag, View view);

    void onToggleFavorite(LaunchItem launchItem);

    void onUninstallApp(String str);
}
