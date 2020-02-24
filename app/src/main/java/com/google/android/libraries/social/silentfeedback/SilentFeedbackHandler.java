package com.google.android.libraries.social.silentfeedback;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.google.android.libraries.stitch.util.Preconditions;
import java.lang.Thread;
import java.util.concurrent.atomic.AtomicReference;

public final class SilentFeedbackHandler {
    private static final SilentFeedbackOptions DEFAULT_OPTIONS = new SilentFeedbackOptions() {
        public boolean shouldReportException(Throwable ex, Thread thread, boolean isForegroundCrash) {
            return true;
        }

        public boolean shouldReportExceptionFullFeedback(Throwable ex, Thread thread, boolean isForegroundCrash) {
            return false;
        }

        public String getForegroundFeedbackCategory() {
            return null;
        }

        public String getBackgroundFeedbackCategory() {
            return null;
        }

        public String getForegroundFullFeedbackCategory() {
            return null;
        }

        public String getBackgroundFullFeedbackCategory() {
            return null;
        }

        public Bundle getPsdBundle(Throwable ex, Thread thread, boolean isForegroundCrash) {
            return null;
        }
    };
    private static final String TAG = "SilentFeedbackHandler";
    private final AtomicReference<Throwable> handledThrowable = new AtomicReference<>();

    public void wrapUncaughtExceptionHandlers(Context context) {
        wrapUncaughtExceptionHandlers(context, DEFAULT_OPTIONS);
    }

    public void wrapUncaughtExceptionHandlers(Context context, SilentFeedbackOptions options) {
        Preconditions.checkNotNull(options, "SilentFeedbackOptions should not be null.");
        Thread.setDefaultUncaughtExceptionHandler(new BackgroundSilentFeedbackHandler(context, Thread.getDefaultUncaughtExceptionHandler(), this.handledThrowable, options, null));
        Thread.currentThread().setUncaughtExceptionHandler(new ForegroundSilentFeedbackHandler(context, Thread.currentThread().getUncaughtExceptionHandler(), this.handledThrowable, options, null));
    }

    public void wrapUncaughtExceptionHandlersForFullFeedback(Context context, SilentFeedbackOptions options) {
        Preconditions.checkNotNull(options, "SilentFeedbackOptions should not be null.");
        UsageReportingOptIn usageReportingOptIn = new UsageReportingOptIn(context);
        usageReportingOptIn.refresh(context);
        Context context2 = context;
        SilentFeedbackOptions silentFeedbackOptions = options;
        UsageReportingOptIn usageReportingOptIn2 = usageReportingOptIn;
        Thread.setDefaultUncaughtExceptionHandler(new BackgroundSilentFeedbackHandler(context2, Thread.getDefaultUncaughtExceptionHandler(), this.handledThrowable, silentFeedbackOptions, usageReportingOptIn2));
        Thread.currentThread().setUncaughtExceptionHandler(new ForegroundSilentFeedbackHandler(context2, Thread.currentThread().getUncaughtExceptionHandler(), this.handledThrowable, silentFeedbackOptions, usageReportingOptIn2));
    }

    private static abstract class BaseSilentFeedbackHandler implements Thread.UncaughtExceptionHandler {
        final Context context;
        final AtomicReference<Throwable> handledThrowable;
        private final Thread.UncaughtExceptionHandler nextHandler;
        private final SilentFeedbackOptions options;
        private final UsageReportingOptIn usageReportingOptIn;

        /* access modifiers changed from: protected */
        public abstract boolean isForeground();

        /* access modifiers changed from: protected */
        public abstract void reportIfNeeded(Throwable th, String str, boolean z, Bundle bundle);

        BaseSilentFeedbackHandler(Context context2, Thread.UncaughtExceptionHandler nextHandler2, AtomicReference<Throwable> handledThrowable2, SilentFeedbackOptions options2, UsageReportingOptIn usageReportingOptIn2) {
            this.context = context2;
            this.nextHandler = nextHandler2;
            this.handledThrowable = handledThrowable2;
            this.options = options2;
            this.usageReportingOptIn = usageReportingOptIn2;
        }

        public void uncaughtException(Thread thread, Throwable ex) {
            String str;
            String str2;
            boolean shouldReport = false;
            String categoryTag = null;
            boolean shouldReportFull = false;
            Bundle psdBundle = null;
            boolean foreground = isForeground();
            try {
                shouldReport = this.options.shouldReportException(ex, thread, foreground);
                shouldReportFull = this.options.shouldReportExceptionFullFeedback(ex, thread, foreground);
                if (this.usageReportingOptIn == null || !this.usageReportingOptIn.isOptedIn()) {
                    shouldReportFull = false;
                }
                if (shouldReportFull) {
                    psdBundle = this.options.getPsdBundle(ex, thread, foreground);
                }
                if (shouldReportFull) {
                    if (foreground) {
                        str2 = this.options.getForegroundFullFeedbackCategory();
                    } else {
                        str2 = this.options.getBackgroundFullFeedbackCategory();
                    }
                    categoryTag = str2;
                } else {
                    if (foreground) {
                        str = this.options.getForegroundFeedbackCategory();
                    } else {
                        str = this.options.getBackgroundFeedbackCategory();
                    }
                    categoryTag = str;
                }
            } catch (Throwable t) {
                Log.e(SilentFeedbackHandler.TAG, "An error occured reading options for exception reporting, skipping silent feedback.", t);
            }
            if (shouldReport || shouldReportFull) {
                reportIfNeeded(ex, categoryTag, shouldReportFull, psdBundle);
            }
            this.nextHandler.uncaughtException(thread, ex);
        }
    }

    private static final class ForegroundSilentFeedbackHandler extends BaseSilentFeedbackHandler {
        ForegroundSilentFeedbackHandler(Context context, Thread.UncaughtExceptionHandler nextHandler, AtomicReference<Throwable> handledThrowable, SilentFeedbackOptions options, UsageReportingOptIn usageReportingOptIn) {
            super(context, nextHandler, handledThrowable, options, usageReportingOptIn);
        }

        /* access modifiers changed from: protected */
        public boolean isForeground() {
            return true;
        }

        /* access modifiers changed from: protected */
        public void reportIfNeeded(Throwable ex, String categoryTag, boolean fullFeedback, Bundle psdBundle) {
            SilentFeedback.sendSilentFeedback(this.context, ex, categoryTag, fullFeedback, psdBundle);
            this.handledThrowable.set(ex);
        }
    }

    private static final class BackgroundSilentFeedbackHandler extends BaseSilentFeedbackHandler {
        BackgroundSilentFeedbackHandler(Context context, Thread.UncaughtExceptionHandler nextHandler, AtomicReference<Throwable> handledThrowable, SilentFeedbackOptions options, UsageReportingOptIn usageReportingOptIn) {
            super(context, nextHandler, handledThrowable, options, usageReportingOptIn);
        }

        /* access modifiers changed from: protected */
        public boolean isForeground() {
            return false;
        }

        /* access modifiers changed from: protected */
        public void reportIfNeeded(Throwable ex, String categoryTag, boolean fullFeedback, Bundle psdBundle) {
            Throwable handled = (Throwable) this.handledThrowable.get();
            if (handled == null || handled != ex) {
                SilentFeedback.sendSilentFeedback(this.context, ex, categoryTag, fullFeedback, psdBundle);
            }
        }
    }
}
