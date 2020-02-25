package com.google.android.tvlauncher.analytics;

import android.app.Activity;

import com.google.android.libraries.social.analytics.visualelement.VisualElementTag;

public abstract class LoggingActivity extends Activity implements EventLoggerProvider {
    protected final ActivityEventLogger mActivityEventLogger;

    public LoggingActivity(String name) {
        this.mActivityEventLogger = new ActivityEventLogger(this, name);
    }

    public LoggingActivity(String name, VisualElementTag visualElementTag) {
        this.mActivityEventLogger = new ActivityEventLogger(this, name, visualElementTag);
    }

    public EventLogger getEventLogger() {
        return this.mActivityEventLogger;
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.mActivityEventLogger.onResume();
    }
}
