package com.google.android.tvlauncher.wallpaper;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.tvlauncher.C1188R;

import java.io.IOException;

public class WallpaperInstaller {
    private static final String ANDROID_BUILD_FINGERPRINT_PROPERTY = "android_build_fingerprint";
    private static final String TAG = "WallpaperInstaller";
    private static final int WALLPAPER_VERSION = 2;
    private static final String WALLPAPER_VERSION_PROPERTY = "wallpaper_version";
    @SuppressLint({"StaticFieldLeak"})
    private static volatile WallpaperInstaller sInstance;
    /* access modifiers changed from: private */
    public boolean mInstallingWallpaper;

    private WallpaperInstaller() {
    }

    public static WallpaperInstaller getInstance() {
        if (sInstance == null) {
            synchronized (WallpaperInstaller.class) {
                if (sInstance == null) {
                    sInstance = new WallpaperInstaller();
                }
            }
        }
        return sInstance;
    }

    @SuppressLint({"StaticFieldLeak"})
    public void installWallpaperIfNeeded(Context context) {
        final Context appContext = context.getApplicationContext();
        if (shouldInstallWallpaper(appContext)) {
            this.mInstallingWallpaper = true;
            new AsyncTask<Void, Void, Void>() {
                /* access modifiers changed from: protected */
                public Void doInBackground(Void... params) {
                    WallpaperInstaller.this.installWallpaper(appContext);
                    return null;
                }

                /* access modifiers changed from: protected */
                public void onCancelled() {
                    super.onCancelled();
                    boolean unused = WallpaperInstaller.this.mInstallingWallpaper = false;
                }
            }.execute(new Void[0]);
        }
    }

    private Bitmap getWallpaperBitmap(Context context) {
        Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        new Canvas(bitmap).drawColor(context.getResources().getColor(C1188R.color.wallpaper_color, null));
        return bitmap;
    }

    private boolean shouldInstallWallpaper(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int installedWallpaperVersion = prefs.getInt(WALLPAPER_VERSION_PROPERTY, 0);
        boolean osUpgraded = !Build.FINGERPRINT.equals(prefs.getString(ANDROID_BUILD_FINGERPRINT_PROPERTY, ""));
        if (this.mInstallingWallpaper) {
            return false;
        }
        if (osUpgraded || installedWallpaperVersion < 2) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void installWallpaper(Context context) {
        try {
            Log.v(TAG, "Installing wallpaper Ver. 2");
            WallpaperManager wallpaperManager = (WallpaperManager) context.getSystemService("wallpaper");
            Bitmap bitmap = getWallpaperBitmap(context);
            wallpaperManager.suggestDesiredDimensions(bitmap.getWidth(), bitmap.getHeight());
            wallpaperManager.setBitmap(bitmap);
            PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(WALLPAPER_VERSION_PROPERTY, 2).putString(ANDROID_BUILD_FINGERPRINT_PROPERTY, Build.FINGERPRINT).apply();
        } catch (IOException | OutOfMemoryError e) {
            Log.e(TAG, "Cannot install wallpaper", e);
        }
        this.mInstallingWallpaper = false;
    }
}
