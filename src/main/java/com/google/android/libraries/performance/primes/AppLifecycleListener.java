package com.google.android.libraries.performance.primes;

import android.app.Activity;
import android.os.Bundle;

public interface AppLifecycleListener {

    public interface OnActivityCreated extends AppLifecycleListener {
        void onActivityCreated(Activity activity, Bundle bundle);
    }

    public interface OnActivityDestroyed extends AppLifecycleListener {
        void onActivityDestroyed(Activity activity);
    }

    public interface OnActivityPaused extends AppLifecycleListener {
        void onActivityPaused(Activity activity);
    }

    public interface OnActivityResumed extends AppLifecycleListener {
        void onActivityResumed(Activity activity);
    }

    public interface OnActivitySaveInstanceState extends AppLifecycleListener {
        void onActivitySaveInstanceState(Activity activity, Bundle bundle);
    }

    public interface OnActivityStarted extends AppLifecycleListener {
        void onActivityStarted(Activity activity);
    }

    public interface OnActivityStopped extends AppLifecycleListener {
        void onActivityStopped(Activity activity);
    }

    public interface OnAppToBackground extends AppLifecycleListener {
        void onAppToBackground(Activity activity);
    }

    public interface OnAppToForeground extends AppLifecycleListener {
        void onAppToForeground(Activity activity);
    }

    public interface OnTrimMemory extends AppLifecycleListener {
        void onTrimMemory(int i);
    }
}
