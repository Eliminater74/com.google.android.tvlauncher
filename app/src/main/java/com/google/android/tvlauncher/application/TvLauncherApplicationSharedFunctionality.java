package com.google.android.tvlauncher.application;

import android.app.Application;
import android.os.Build;
import com.google.android.tvlauncher.appsview.data.LaunchItemsManagerProvider;
import com.google.android.tvlauncher.wallpaper.WallpaperInstaller;

public class TvLauncherApplicationSharedFunctionality {
    public static void onCreate(Application application) {
        if (!Build.TYPE.equals("unknown")) {
            LaunchItemsManagerProvider.getInstance(application).registerUpdateListeners();
            WallpaperInstaller.getInstance().installWallpaperIfNeeded(application);
        }
    }
}
