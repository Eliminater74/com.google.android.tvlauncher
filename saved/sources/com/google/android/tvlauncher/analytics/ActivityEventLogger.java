package com.google.android.tvlauncher.analytics;

import android.app.Activity;
import com.google.android.libraries.social.analytics.visualelement.VisualElementTag;

class ActivityEventLogger implements EventLogger {
    private final Activity mActivity;
    private final String mName;
    private final VisualElementTag mVisualElementTag;

    ActivityEventLogger(Activity activity, String name) {
        this(activity, name, null);
    }

    ActivityEventLogger(Activity activity, String name, VisualElementTag visualElementTag) {
        this.mActivity = activity;
        this.mName = name;
        this.mVisualElementTag = visualElementTag;
    }

    public void log(LogEvent event) {
        VisualElementTag visualElementTag = this.mVisualElementTag;
        if (visualElementTag != null) {
            event.pushParentVisualElementTag(visualElementTag);
        }
        AppEventLogger logger = AppEventLogger.getInstance();
        if (logger != null) {
            logger.log(event);
        }
    }

    public void onResume() {
        AppEventLogger.getInstance().setName(this.mActivity, this.mName);
    }
}
