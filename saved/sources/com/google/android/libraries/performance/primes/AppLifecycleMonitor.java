package com.google.android.libraries.performance.primes;

import android.app.Application;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.google.android.libraries.performance.primes.AppLifecycleTracker;

public class AppLifecycleMonitor {
    private static volatile AppLifecycleMonitor instance;
    final AppLifecycleTracker tracker = new AppLifecycleTracker();

    public static AppLifecycleMonitor getInstance(Application application) {
        if (instance == null) {
            synchronized (AppLifecycleMonitor.class) {
                if (instance == null) {
                    instance = newInstance(application);
                }
            }
        }
        return instance;
    }

    static void shutdownInstance(Application application) {
        synchronized (AppLifecycleMonitor.class) {
            if (instance != null) {
                instance.detachFromApp(application);
                instance = null;
            }
        }
    }

    @VisibleForTesting
    static AppLifecycleMonitor newInstance(Application application) {
        AppLifecycleMonitor monitor = new AppLifecycleMonitor();
        monitor.attachToApp(application);
        return monitor;
    }

    private AppLifecycleMonitor() {
    }

    private void attachToApp(Application application) {
        this.tracker.attachToApp(application);
    }

    private void detachFromApp(Application application) {
        this.tracker.detachFromApp(application);
    }

    public void register(AppLifecycleListener listener) {
        this.tracker.register(listener);
    }

    public void unregister(AppLifecycleListener listener) {
        this.tracker.unregister(listener);
    }

    public AppLifecycleTracker.AppForegroundState getAppForegroundState() {
        return this.tracker.getAppForegroundState();
    }

    @Nullable
    public String getForegroundActivityName() {
        return this.tracker.getForegroundActivityName();
    }

    public int getActivityCreatedCount() {
        return this.tracker.getActivityCreatedCount();
    }

    public int getActivityResumedCount() {
        return this.tracker.getActivityResumedCount();
    }

    public int getActivityStartedCount() {
        return this.tracker.getActivityStartedCount();
    }

    public int getActivityPausedCount() {
        return this.tracker.getActivityPausedCount();
    }

    public int getActivityStoppedCount() {
        return this.tracker.getActivityStoppedCount();
    }

    public int getActivityDestroyedCount() {
        return this.tracker.getActivityDestroyedCount();
    }
}
