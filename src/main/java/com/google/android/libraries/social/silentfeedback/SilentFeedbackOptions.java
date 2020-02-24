package com.google.android.libraries.social.silentfeedback;

import android.os.Bundle;

public interface SilentFeedbackOptions {
    String getBackgroundFeedbackCategory();

    String getBackgroundFullFeedbackCategory();

    String getForegroundFeedbackCategory();

    String getForegroundFullFeedbackCategory();

    Bundle getPsdBundle(Throwable th, Thread thread, boolean z);

    boolean shouldReportException(Throwable th, Thread thread, boolean z);

    boolean shouldReportExceptionFullFeedback(Throwable th, Thread thread, boolean z);
}
