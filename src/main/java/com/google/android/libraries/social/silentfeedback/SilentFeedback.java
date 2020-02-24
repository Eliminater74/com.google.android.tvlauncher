package com.google.android.libraries.social.silentfeedback;

import android.annotation.TargetApi;
import android.app.ApplicationErrorReport;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.util.HashSet;
import java.util.Set;

public final class SilentFeedback {
    public static final String CATEGORY_TAG_KEY = "com.google.android.libraries.social.silentfeedback.SilentFeedbackReceiver.categoryTag";
    private static final String DEFAULT_EXCEPTION_FILENAME = "Unknown Source";
    public static final String EXCEPTION_CLASS_NAME_KEY = "com.google.android.libraries.social.silentfeedback.SilentFeedbackReceiver.exceptionClass";
    public static final String EXCEPTION_MESSAGE_KEY = "com.google.android.libraries.social.silentfeedback.SilentFeedbackReceiver.exceptionMessage";
    public static final String EXCLUDE_PII_KEY = "com.google.android.libraries.social.silentfeedback.SilentFeedbackReceiver.excludePii";
    public static final String PSD_BUNDLE_KEY = "com.google.android.libraries.social.silentfeedback.SilentFeedbackReceiver.psdBundle";
    private static final String SILENT_FEEDBACK_SERVICE_REGEX = "com\\.google\\.android\\.libraries\\.social\\.silentfeedback\\.\\w*\\.SilentFeedbackReceiver";
    public static final String STACK_TRACE_KEY = "com.google.android.libraries.social.silentfeedback.SilentFeedbackReceiver.stackTrace";
    private static final String TAG = "SilentFeedback";
    public static final String THROWING_CLASS_NAME_KEY = "com.google.android.libraries.social.silentfeedback.SilentFeedbackReceiver.throwingClass";
    public static final String THROWING_FILE_NAME_KEY = "com.google.android.libraries.social.silentfeedback.SilentFeedbackReceiver.throwingFile";
    public static final String THROWING_LINE_NUMBER_KEY = "com.google.android.libraries.social.silentfeedback.SilentFeedbackReceiver.throwingLine";
    public static final String THROWING_METHOD_NAME_KEY = "com.google.android.libraries.social.silentfeedback.SilentFeedbackReceiver.throwingMethod";
    private final Context context;

    public SilentFeedback(Context context2) {
        this.context = context2;
    }

    public void sendSilentFeedback(Throwable ex, String categoryTag) {
        sendSilentFeedback(this.context, ex, categoryTag, false, null);
    }

    public static void sendSilentFeedback(Context context2, Throwable ex, String categoryTag, boolean fullFeedback, Bundle psdBundle) {
        Intent feedbackIntent = buildFeedbackIntent(context2, ex, categoryTag, fullFeedback, psdBundle);
        if (feedbackIntent != null) {
            context2.sendBroadcast(feedbackIntent);
        }
    }

    public static boolean verifySilentFeedbackIsEnabled(Context context2) {
        return findReceiverName(context2) != null;
    }

    private static void printCleanedThrowable(Throwable ex, StringBuilder builder) {
        printCleanedThrowableHelper(ex, builder, new HashSet(), null);
    }

    @TargetApi(19)
    private static void printCleanedThrowableHelper(Throwable ex, StringBuilder builder, Set<Throwable> seen, String prefix) {
        if (ex != null && !seen.contains(ex)) {
            seen.add(ex);
            if (prefix != null) {
                builder.append(prefix);
            }
            builder.append(ex.getClass().getName());
            builder.append(':');
            for (StackTraceElement element : ex.getStackTrace()) {
                builder.append("\n\tat ");
                builder.append(element);
            }
            if (Build.VERSION.SDK_INT >= 19) {
                for (Throwable suppressed : ThrowableExtension.getSuppressed(ex)) {
                    printCleanedThrowableHelper(suppressed, builder, seen, "\nSuppressed: ");
                }
            }
            if (ex.getCause() != null) {
                printCleanedThrowableHelper(ex.getCause(), builder, seen, "\nCaused by: ");
            }
        }
    }

    private static String findReceiverName(Context context2) {
        try {
            PackageInfo packageInfo = context2.getPackageManager().getPackageInfo(context2.getApplicationContext().getPackageName(), 2);
            if (packageInfo == null || packageInfo.receivers == null) {
                return null;
            }
            String receiverName = null;
            ActivityInfo[] activityInfoArr = packageInfo.receivers;
            int length = activityInfoArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                ActivityInfo receiver = activityInfoArr[i];
                if (receiver.name.matches(SILENT_FEEDBACK_SERVICE_REGEX)) {
                    receiverName = receiver.name;
                    break;
                }
                i++;
            }
            if (receiverName != null) {
                return receiverName;
            }
            Log.e(TAG, "Could not find SilentFeedbackReceiver, not sending crash info.");
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Could not find our own package. This should never happen. Not sending crash info.", e);
            return null;
        }
    }

    private static Intent buildFeedbackIntent(Context context2, Throwable ex, String categoryTag, boolean fullFeedback, Bundle psdBundle) {
        String receiverName;
        String exceptionMessage;
        String stackTrace;
        StackTraceElement[] stackTraceElements = ex.getStackTrace();
        if (ex.getStackTrace().length == 0 || (receiverName = findReceiverName(context2)) == null) {
            return null;
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(context2.getApplicationContext(), receiverName));
        intent.setPackage(context2.getApplicationContext().getPackageName());
        if (fullFeedback) {
            ApplicationErrorReport.CrashInfo crashInfo = new ApplicationErrorReport.CrashInfo(ex);
            exceptionMessage = crashInfo.exceptionMessage;
            stackTrace = crashInfo.stackTrace;
        } else {
            exceptionMessage = null;
            StringBuilder stackTraceBuilder = new StringBuilder();
            printCleanedThrowable(ex, stackTraceBuilder);
            stackTrace = stackTraceBuilder.toString();
        }
        StackTraceElement thrownBy = stackTraceElements[0];
        String fileName = thrownBy.getFileName() != null ? thrownBy.getFileName() : DEFAULT_EXCEPTION_FILENAME;
        intent.putExtra(EXCLUDE_PII_KEY, !fullFeedback);
        if (fullFeedback && exceptionMessage != null) {
            intent.putExtra(EXCEPTION_MESSAGE_KEY, exceptionMessage);
        }
        intent.putExtra(EXCEPTION_CLASS_NAME_KEY, ex.getClass().getName());
        intent.putExtra(STACK_TRACE_KEY, stackTrace);
        intent.putExtra(THROWING_CLASS_NAME_KEY, thrownBy.getClassName());
        intent.putExtra(THROWING_FILE_NAME_KEY, fileName);
        intent.putExtra(THROWING_LINE_NUMBER_KEY, thrownBy.getLineNumber());
        intent.putExtra(THROWING_METHOD_NAME_KEY, thrownBy.getMethodName());
        if (categoryTag != null) {
            intent.putExtra(CATEGORY_TAG_KEY, categoryTag);
        }
        if (fullFeedback && psdBundle != null) {
            intent.putExtra(PSD_BUNDLE_KEY, psdBundle);
        }
        return intent;
    }
}
