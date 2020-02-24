package com.google.android.tvlauncher.analytics;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.tvlauncher.TvlauncherLogEnum;
import com.google.protos.logs.proto.wireless.android.tvlauncher.TvlauncherClientLog;

public class ClearcutEventLoggerEngine {
    private static final boolean DEBUG = false;
    private static final String LOG_SOURCE_NAME = "TV_LAUNCHER";
    private static final String PREF_LOGGING_ENABLED_BY_USER = "logging_enabled_by_user";
    private static final String TAG = "ClearcutEvent";
    private final ClearcutLogger mClearcutLogger;
    private boolean mEnabled = this.mPreferences.getBoolean(PREF_LOGGING_ENABLED_BY_USER, false);
    private final SharedPreferences mPreferences;

    public ClearcutEventLoggerEngine(Context context) {
        this.mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.mClearcutLogger = new ClearcutLogger(context, LOG_SOURCE_NAME, null);
    }

    public void setEnabled(boolean enabled) {
        this.mEnabled = enabled;
        this.mPreferences.edit().putBoolean(PREF_LOGGING_ENABLED_BY_USER, enabled).apply();
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    /* access modifiers changed from: package-private */
    public void logEvent(TvlauncherLogEnum.TvLauncherEventCode eventCode, TvlauncherClientLog.TvLauncherClientExtension clientLogEntry) {
        if (this.mEnabled) {
            logEventUnconditionally(eventCode, clientLogEntry);
        }
    }

    /* access modifiers changed from: package-private */
    public void logEventUnconditionally(TvlauncherLogEnum.TvLauncherEventCode eventCode, TvlauncherClientLog.TvLauncherClientExtension clientLogEntry) {
        ClearcutLogger.LogEventBuilder event = this.mClearcutLogger.newEvent(clientLogEntry.toByteArray());
        if (eventCode != null) {
            event.setEventCode(eventCode.getNumber());
        }
        event.log();
    }
}
