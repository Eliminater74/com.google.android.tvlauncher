package com.google.android.gms.feedback;

import android.util.Pair;

import java.util.List;

public class BaseFeedbackProductSpecificData {
    public static final String FEEDBACK_PSD_FAILURE_VALUE_EXCEPTION = "exception";
    public static final String FEEDBACK_PSD_FAILURE_VALUE_TIMEOUT = "timeout";
    public static final String KEY_ASYNC_FEEDBACK_PSBD_COLLECTION_TIME_MS = "gms:feedback:async_feedback_psbd_collection_time_ms";
    public static final String KEY_ASYNC_FEEDBACK_PSBD_FAILURE = "gms:feedback:async_feedback_psbd_failure";
    public static final String KEY_ASYNC_FEEDBACK_PSD_COLLECTION_TIME_MS = "gms:feedback:async_feedback_psd_collection_time_ms";
    public static final String KEY_ASYNC_FEEDBACK_PSD_FAILURE = "gms:feedback:async_feedback_psd_failure";

    public List<Pair<String, String>> getAsyncFeedbackPsd() {
        return null;
    }

    public List<FileTeleporter> getAsyncFeedbackPsbd() {
        return null;
    }
}
