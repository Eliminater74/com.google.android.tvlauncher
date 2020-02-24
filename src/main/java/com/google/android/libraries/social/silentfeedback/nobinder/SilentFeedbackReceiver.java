package com.google.android.libraries.social.silentfeedback.nobinder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.feedback.Feedback;
import com.google.android.gms.feedback.FeedbackOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.social.silentfeedback.SilentFeedback;

public final class SilentFeedbackReceiver extends BroadcastReceiver {
    private static final String TAG = "SilentFeedbackReceiver";

    private FeedbackOptions buildCrashOptions(Intent intent) {
        FeedbackOptions.CrashBuilder builder = new FeedbackOptions.CrashBuilder();
        if (intent == null) {
            return builder.build();
        }
        if (Log.isLoggable(TAG, 3)) {
            boolean booleanExtra = intent.getBooleanExtra(SilentFeedback.EXCLUDE_PII_KEY, true);
            String stringExtra = intent.getStringExtra(SilentFeedback.EXCEPTION_MESSAGE_KEY);
            StringBuilder sb = new StringBuilder(String.valueOf(stringExtra).length() + 53);
            sb.append("buildCrashOptions excludePii=");
            sb.append(booleanExtra);
            sb.append(", exceptionMessage=");
            sb.append(stringExtra);
            Log.d(TAG, sb.toString());
        }
        builder.setDescription(" ");
        builder.setExcludePii(intent.getBooleanExtra(SilentFeedback.EXCLUDE_PII_KEY, true));
        if (intent.hasExtra(SilentFeedback.EXCEPTION_MESSAGE_KEY)) {
            builder.setExceptionMessage(intent.getStringExtra(SilentFeedback.EXCEPTION_MESSAGE_KEY));
        }
        if (intent.hasExtra(SilentFeedback.EXCEPTION_CLASS_NAME_KEY)) {
            builder.setExceptionClassName(intent.getStringExtra(SilentFeedback.EXCEPTION_CLASS_NAME_KEY));
        }
        if (intent.hasExtra(SilentFeedback.STACK_TRACE_KEY)) {
            builder.setStackTrace(intent.getStringExtra(SilentFeedback.STACK_TRACE_KEY));
        }
        if (intent.hasExtra(SilentFeedback.THROWING_CLASS_NAME_KEY)) {
            builder.setThrowClassName(intent.getStringExtra(SilentFeedback.THROWING_CLASS_NAME_KEY));
        }
        if (intent.hasExtra(SilentFeedback.THROWING_FILE_NAME_KEY)) {
            builder.setThrowFileName(intent.getStringExtra(SilentFeedback.THROWING_FILE_NAME_KEY));
        }
        if (intent.hasExtra(SilentFeedback.THROWING_LINE_NUMBER_KEY)) {
            builder.setThrowLineNumber(intent.getIntExtra(SilentFeedback.THROWING_LINE_NUMBER_KEY, -1));
        }
        if (intent.hasExtra(SilentFeedback.THROWING_METHOD_NAME_KEY)) {
            builder.setThrowMethodName(intent.getStringExtra(SilentFeedback.THROWING_METHOD_NAME_KEY));
        }
        if (intent.hasExtra(SilentFeedback.CATEGORY_TAG_KEY)) {
            builder.setCategoryTag(intent.getStringExtra(SilentFeedback.CATEGORY_TAG_KEY));
        }
        if (intent.hasExtra(SilentFeedback.PSD_BUNDLE_KEY)) {
            builder.addPsdBundle(intent.getBundleExtra(SilentFeedback.PSD_BUNDLE_KEY));
        }
        return builder.build();
    }

    public void onReceive(Context context, Intent intent) {
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "Starting silent feedback service.");
        }
        final BroadcastReceiver.PendingResult finished = goAsync();
        Feedback.getClient(context).silentSendFeedback(buildCrashOptions(intent)).addOnCompleteListener(new OnCompleteListener<Void>(this) {
            public void onComplete(@NonNull Task<Void> task) {
                finished.finish();
            }
        }).addOnFailureListener(new OnFailureListener(this) {
            public void onFailure(@NonNull Exception e) {
                String valueOf = String.valueOf(e);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 54);
                sb.append("FeedbackClient silent feedback failed with exception: ");
                sb.append(valueOf);
                Log.e(SilentFeedbackReceiver.TAG, sb.toString());
            }
        });
    }
}
