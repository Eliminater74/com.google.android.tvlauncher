package com.google.android.tvlauncher.application;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.android.gsf.Gservices;
import com.google.android.libraries.social.silentfeedback.SilentFeedbackHandler;
import com.google.android.libraries.social.silentfeedback.SilentFeedbackOptions;
import com.google.android.tvlauncher.util.PackageUtils;
import com.google.android.tvlauncher.util.TestUtils;
import com.google.android.tvrecommendations.shared.util.Constants;

class SilentFeedbackConfiguration {
    private static final boolean DEBUG = false;
    private static final boolean SILENT_FULL_FEEDBACK_ENABLED_DEFAULT = false;
    private static final String SILENT_FULL_FEEDBACK_ENABLED_KEY = "tvlauncher:silent_full_feedback_enabled";
    private static final String TAG = "SilentFeedbackConfiguration";
    private static final String TEST_EXCEPTION_MESSAGE_PREFIX = "Unable to start receiver com.google.android.tvlauncher.util.TestCrashReceiver";

    SilentFeedbackConfiguration() {
    }

    static void init(@NonNull final Context context) {
        DefaultUncaughtExceptionHandlerVerifier.assertHandlerClass("com.android.internal.os.RuntimeInit$KillApplicationHandler");
        new SilentFeedbackHandler().wrapUncaughtExceptionHandlersForFullFeedback(context, new SilentFeedbackOptions() {
            private boolean shouldReportException(Throwable throwable) {
                String exceptionMessage = throwable.getMessage();
                if (exceptionMessage == null) {
                    exceptionMessage = "";
                }
                if (!TestUtils.isRunningInTest() || exceptionMessage.startsWith(SilentFeedbackConfiguration.TEST_EXCEPTION_MESSAGE_PREFIX)) {
                    return true;
                }
                return false;
            }

            public boolean shouldReportException(Throwable throwable, Thread thread, boolean isForegroundCrash) {
                return false;
            }

            public boolean shouldReportExceptionFullFeedback(Throwable throwable, Thread thread, boolean isForegroundCrash) {
                if (!shouldReportException(throwable)) {
                    return false;
                }
                return Gservices.getBoolean(context.getContentResolver(), SilentFeedbackConfiguration.SILENT_FULL_FEEDBACK_ENABLED_KEY, false);
            }

            public String getForegroundFeedbackCategory() {
                return null;
            }

            public String getBackgroundFeedbackCategory() {
                return null;
            }

            public String getForegroundFullFeedbackCategory() {
                return "com.google.android.tvlauncher.SILENT_REPORT_FULL_MAIN_THREAD";
            }

            public String getBackgroundFullFeedbackCategory() {
                return "com.google.android.tvlauncher.SILENT_REPORT_FULL_BACKGROUND_THREAD";
            }

            public Bundle getPsdBundle(Throwable ex, Thread thread, boolean isForegroundCrash) {
                Bundle bundle = new Bundle(2);
                bundle.putString("com.google.android.tvrecommendations:version-code", String.valueOf(PackageUtils.getApplicationVersionCode(context, Constants.TVRECOMMENDATIONS_PACKAGE_NAME)));
                bundle.putString("com.google.android.tvrecommendations:version-name", PackageUtils.getApplicationVersionName(context, Constants.TVRECOMMENDATIONS_PACKAGE_NAME));
                return bundle;
            }
        });
    }
}
