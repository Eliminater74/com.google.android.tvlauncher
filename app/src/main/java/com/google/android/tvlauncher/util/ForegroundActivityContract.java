package com.google.android.tvlauncher.util;

import android.net.Uri;

public final class ForegroundActivityContract {
    static final String AUTHORITY = "com.google.android.tvrecommendations.ForegroundActivityContentProvider";
    public static final String COLUMN_PACKAGE_NAME = "package_name";
    public static final Uri FOREGROUND_ACTIVITY_URI = Uri.parse("content://com.google.android.tvrecommendations.ForegroundActivityContentProvider/foreground_activity");
    static final String PATH_FOREGROUND_ACTIVITY = "foreground_activity";
}
