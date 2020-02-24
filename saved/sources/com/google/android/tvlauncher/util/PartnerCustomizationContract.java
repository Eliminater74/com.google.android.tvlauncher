package com.google.android.tvlauncher.util;

import android.net.Uri;

public final class PartnerCustomizationContract {
    public static final String COLUMN_WIDGET_ICON = "icon";
    public static final String COLUMN_WIDGET_INTENT_URI = "action";
    public static final String COLUMN_WIDGET_TITLE = "title";
    public static final String OEM_APP_RECS_AUTHORITY = "tvlauncher.apprecs";
    public static final Uri OEM_APP_RECS_URI = Uri.parse("content://tvlauncher.apprecs/app_recommendations");
    public static final Uri OEM_CONFIGURATION_URI = Uri.parse("content://tvlauncher.config/configuration");
    private static final String OEM_CONFIG_AUTHORITY = "tvlauncher.config";
    private static final String OEM_WIDGET_AUTHORITY = "tvlauncher.widget";
    public static final Uri WIDGET_CONTENT_URI = Uri.parse("content://tvlauncher.widget/widget");
}
