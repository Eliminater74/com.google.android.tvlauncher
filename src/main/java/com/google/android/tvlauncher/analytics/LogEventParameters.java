package com.google.android.tvlauncher.analytics;

import com.google.android.tvlauncher.TvlauncherLogEnum;

public class LogEventParameters extends LogEvent {
    public static final String APP_COUNT = "app_count";
    public static final String NOTIFICATION_INDICATOR_TOTAL = "notification_indicator_total";
    public static final String SHOWN_CHANNEL_COUNT = "shown_channel_count";
    public static final String TRAY_NOTIFICATION_COUNT = "tray_notification_count";
    public static final String WATCH_NEXT = "watch_next";
    private final String mParameterName;

    public LogEventParameters(TvlauncherLogEnum.TvLauncherEventCode eventCode, String parameterName) {
        super(eventCode);
        this.mParameterName = parameterName;
    }

    /* access modifiers changed from: package-private */
    public String getParameterName() {
        return this.mParameterName;
    }
}
