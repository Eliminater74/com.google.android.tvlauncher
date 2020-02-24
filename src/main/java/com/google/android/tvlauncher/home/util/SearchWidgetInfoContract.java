package com.google.android.tvlauncher.home.util;

import android.net.Uri;

public class SearchWidgetInfoContract {
    private static final String AUTHORITY = "com.google.android.katniss.search.WidgetInfoProvider";
    public static final String COLUMN_ICON = "assistant_icon";
    public static final String COLUMN_SUGGESTION = "suggestion";
    public static final Uri ICON_CONTENT_URI = Uri.parse("content://com.google.android.katniss.search.WidgetInfoProvider/state");
    private static final String PATH_SEARCH_ICON = "state";
    private static final String PATH_SUGGESTIONS = "suggestions";
    public static final Uri SUGGESTIONS_CONTENT_URI = Uri.parse("content://com.google.android.katniss.search.WidgetInfoProvider/suggestions");
}
