package com.google.android.libraries.performance.primes;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.VisibleForTesting;
import com.google.android.libraries.performance.primes.AppLifecycleListener;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import logs.proto.wireless.performance.mobile.MemoryMetric;

final class MemoryMetricMonitor {
    @VisibleForTesting
    static final int MEMORY_LOG_DELAY_IN_SECONDS = 10;
    private static final String TAG = "MemoryMetricMonitor";
    private final AppLifecycleMonitor appLifecycleMonitor;
    /* access modifiers changed from: private */
    public final Callback callback;
    /* access modifiers changed from: private */
    public final Supplier<ScheduledExecutorService> executorServiceSupplier;
    /* access modifiers changed from: private */
    public ScheduledFuture<?> futureMemoryBackgroundTask;
    /* access modifiers changed from: private */
    public ScheduledFuture<?> futureMemoryForegroundTask;
    private final AtomicBoolean hasMemoryMonitorStarted;
    private final AppLifecycleListener.OnAppToBackground onAppToBackground;
    private final AppLifecycleListener.OnAppToForeground onAppToForeground;

    interface Callback {
        void onEvent(MemoryMetric.MemoryUsageMetric.MemoryEventCode memoryEventCode, String str);
    }

    MemoryMetricMonitor(Callback callback2, Application appToMonitor, Supplier<ScheduledExecutorService> executorServiceSupplier2) {
        this(callback2, appToMonitor, executorServiceSupplier2, AppLifecycleMonitor.getInstance(appToMonitor));
    }

    @VisibleForTesting
    MemoryMetricMonitor(Callback callback2, Application appToMonitor, Supplier<ScheduledExecutorService> executorServiceSupplier2, AppLifecycleMonitor appLifecycleMonitor2) {
        this.hasMemoryMonitorStarted = new AtomicBoolean(false);
        this.onAppToBackground = new AppLifecycleListener.OnAppToBackground() {
            public void onAppToBackground(Activity activity) {
                final String activityName = activity.getClass().getSimpleName();
                MemoryMetricMonitor.this.callback.onEvent(MemoryMetric.MemoryUsageMetric.MemoryEventCode.APP_TO_BACKGROUND, activityName);
                MemoryMetricMonitor.this.cancelFutureTasksIfAny();
                MemoryMetricMonitor memoryMetricMonitor = MemoryMetricMonitor.this;
                ScheduledFuture unused = memoryMetricMonitor.futureMemoryBackgroundTask = ((ScheduledExecutorService) memoryMetricMonitor.executorServiceSupplier.get()).schedule(new Runnable() {
                    public void run() {
                        MemoryMetricMonitor.this.callback.onEvent(MemoryMetric.MemoryUsageMetric.MemoryEventCode.APP_IN_BACKGROUND_FOR_SECONDS, activityName);
                    }
                }, 10, TimeUnit.SECONDS);
            }
        };
        this.onAppToForeground = new AppLifecycleListener.OnAppToForeground() {
            public void onAppToForeground(Activity activity) {
                final String activityName = activity.getClass().getSimpleName();
                MemoryMetricMonitor.this.callback.onEvent(MemoryMetric.MemoryUsageMetric.MemoryEventCode.APP_TO_FOREGROUND, activityName);
                MemoryMetricMonitor.this.cancelFutureTasksIfAny();
                MemoryMetricMonitor memoryMetricMonitor = MemoryMetricMonitor.this;
                ScheduledFuture unused = memoryMetricMonitor.futureMemoryForegroundTask = ((ScheduledExecutorService) memoryMetricMonitor.executorServiceSupplier.get()).schedule(new Runnable() {
                    public void run() {
                        MemoryMetricMonitor.this.callback.onEvent(MemoryMetric.MemoryUsageMetric.MemoryEventCode.APP_IN_FOREGROUND_FOR_SECONDS, activityName);
                    }
                }, 10, TimeUnit.SECONDS);
            }
        };
        this.callback = callback2;
        this.executorServiceSupplier = executorServiceSupplier2;
        this.appLifecycleMonitor = appLifecycleMonitor2;
    }

    /* access modifiers changed from: package-private */
    public void start() {
        if (this.hasMemoryMonitorStarted.getAndSet(true)) {
            PrimesLog.m54w(TAG, "Memory Monitor has already started. This MemoryMetricMonitor.start() is ignored.", new Object[0]);
            return;
        }
        this.appLifecycleMonitor.register(this.onAppToBackground);
        this.appLifecycleMonitor.register(this.onAppToForeground);
    }

    /* access modifiers changed from: private */
    public void cancelFutureTasksIfAny() {
        ScheduledFuture<?> scheduledFuture = this.futureMemoryForegroundTask;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            this.futureMemoryForegroundTask = null;
        }
        ScheduledFuture<?> scheduledFuture2 = this.futureMemoryBackgroundTask;
        if (scheduledFuture2 != null) {
            scheduledFuture2.cancel(true);
            this.futureMemoryBackgroundTask = null;
        }
    }

    /* access modifiers changed from: package-private */
    public void stop() {
        this.appLifecycleMonitor.unregister(this.onAppToBackground);
        this.appLifecycleMonitor.unregister(this.onAppToForeground);
    }
}
