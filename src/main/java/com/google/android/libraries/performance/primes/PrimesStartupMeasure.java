package com.google.android.libraries.performance.primes;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.VisibleForTesting;
import android.view.View;
import android.view.ViewTreeObserver;
import com.google.android.libraries.performance.primes.PrimesStartupMeasureListener;
import com.google.android.libraries.stitch.util.ThreadUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PrimesStartupMeasure {
    private static final int MAX_STARTUP_ACTIVITIES = 3;
    private static final List<PrimesStartupMeasureListener.OnActivityInit> ON_ACTIVITY_INIT_EMPTY_LIST = Collections.emptyList();
    private static final List<PrimesStartupMeasureListener.OnDraw> ON_DRAW_EMPTY_LIST = Collections.emptyList();
    private static final String TAG = "PrimesStartupMeasure";
    private static volatile PrimesStartupMeasure instance = new PrimesStartupMeasure();
    private volatile long appClassLoadedAt;
    private volatile long appOnCreateAt;
    private volatile long firstAppInteractiveAt;
    /* access modifiers changed from: private */
    public volatile long firstDrawnAt;
    /* access modifiers changed from: private */
    public volatile long firstOnActivityCreatedAt;
    private volatile long firstOnActivityInitAt;
    /* access modifiers changed from: private */
    public volatile long firstOnActivityResumedAt;
    /* access modifiers changed from: private */
    public volatile long firstOnActivityStartedAt;
    private final Object onActivityInitListenerLock = new Object();
    private volatile List<PrimesStartupMeasureListener.OnActivityInit> onActivityInitListeners = ON_ACTIVITY_INIT_EMPTY_LIST;
    private final Object onDrawListenerLock = new Object();
    private volatile List<PrimesStartupMeasureListener.OnDraw> onDrawListeners = ON_DRAW_EMPTY_LIST;
    /* access modifiers changed from: private */
    public volatile boolean startedByUser;
    private final List<StartupActivityInfo> startupActivityInfos = new ArrayList();
    private volatile NoPiiString startupType;

    static final class StartupActivityInfo {
        volatile String activityName;
        volatile long onActivityCreatedAt;

        StartupActivityInfo() {
        }
    }

    @VisibleForTesting
    PrimesStartupMeasure() {
    }

    public static PrimesStartupMeasure get() {
        return instance;
    }

    public void onAppClassLoaded() {
        if (this.appClassLoadedAt == 0) {
            this.appClassLoadedAt = SystemClock.elapsedRealtime();
        }
    }

    public void logActivityInitTime(TikTokWhitelistToken token, long activityInstantiatedAt) {
        if (token == null) {
            throw new NullPointerException("token must not be null");
        } else if (ThreadUtil.isMainThread() && this.appClassLoadedAt > 0 && activityInstantiatedAt <= SystemClock.elapsedRealtime()) {
            if ((activityInstantiatedAt <= this.firstOnActivityCreatedAt || this.firstOnActivityCreatedAt == 0) && this.firstOnActivityInitAt == 0) {
                this.firstOnActivityInitAt = activityInstantiatedAt;
            }
        }
    }

    public void onActivityInit() {
        if (ThreadUtil.isMainThread() && this.appClassLoadedAt > 0 && this.firstOnActivityInitAt == 0 && this.firstOnActivityCreatedAt == 0) {
            this.firstOnActivityInitAt = SystemClock.elapsedRealtime();
            runOnActivityInitListeners();
        }
    }

    public void onAppCreate(TikTokWhitelistToken token, Application app, long appClassLoadedAt2) {
        if (token == null) {
            throw new NullPointerException("token must not be null");
        } else if (appClassLoadedAt2 <= SystemClock.elapsedRealtime()) {
            this.appClassLoadedAt = appClassLoadedAt2;
            onAppCreate(app);
        }
    }

    public void onAppCreate(Application app) {
        if (ThreadUtil.isMainThread() && this.appClassLoadedAt > 0 && this.appOnCreateAt == 0 && app != null) {
            this.appOnCreateAt = SystemClock.elapsedRealtime();
            ThreadUtil.postOnUiThread(new Runnable() {
                public void run() {
                    PrimesStartupMeasure primesStartupMeasure = PrimesStartupMeasure.this;
                    boolean unused = primesStartupMeasure.startedByUser = primesStartupMeasure.firstOnActivityCreatedAt != 0;
                }
            });
            app.registerActivityLifecycleCallbacks(new StartupCallbacks(app));
        }
    }

    @Deprecated
    public void onAppInteractive() {
        onAppInteractive(null);
    }

    public void onAppInteractive(Activity activity) {
        if (ThreadUtil.isMainThread() && this.firstAppInteractiveAt == 0) {
            this.firstAppInteractiveAt = SystemClock.elapsedRealtime();
            if (Build.VERSION.SDK_INT >= 21 && activity != null) {
                try {
                    activity.reportFullyDrawn();
                } catch (RuntimeException e) {
                    PrimesLog.m45d(TAG, "Failed to report App usable time.", e, new Object[0]);
                }
            }
        }
    }

    public void setStartupType(NoPiiString startupType2) {
        if (this.startupType == null) {
            this.startupType = startupType2;
        }
    }

    /* access modifiers changed from: private */
    public void addToOrReplaceStartupActivityInfos(StartupActivityInfo currentActivityInfo) {
        synchronized (this.startupActivityInfos) {
            if (this.startupActivityInfos.size() == 3) {
                this.startupActivityInfos.set(2, currentActivityInfo);
            } else {
                this.startupActivityInfos.add(currentActivityInfo);
            }
        }
    }

    private final class StartupCallbacks implements Application.ActivityLifecycleCallbacks {
        /* access modifiers changed from: private */
        public final Application app;

        StartupCallbacks(Application app2) {
            this.app = app2;
        }

        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (PrimesStartupMeasure.this.firstOnActivityCreatedAt == 0) {
                long unused = PrimesStartupMeasure.this.firstOnActivityCreatedAt = elapsedRealtime;
            }
            StartupActivityInfo currentActivityInfo = new StartupActivityInfo();
            currentActivityInfo.activityName = activity.getClass().getSimpleName();
            currentActivityInfo.onActivityCreatedAt = elapsedRealtime;
            PrimesStartupMeasure.this.addToOrReplaceStartupActivityInfos(currentActivityInfo);
        }

        public void onActivityStarted(Activity activity) {
            if (PrimesStartupMeasure.this.firstOnActivityStartedAt == 0) {
                long unused = PrimesStartupMeasure.this.firstOnActivityStartedAt = SystemClock.elapsedRealtime();
            }
        }

        public void onActivityResumed(Activity activity) {
            if (PrimesStartupMeasure.this.firstOnActivityResumedAt == 0) {
                long unused = PrimesStartupMeasure.this.firstOnActivityResumedAt = SystemClock.elapsedRealtime();
            }
            try {
                View rootView = activity.findViewById(16908290);
                rootView.getViewTreeObserver().addOnPreDrawListener(new MyOnPreDrawListener(rootView));
            } catch (RuntimeException e) {
                PrimesLog.m45d(PrimesStartupMeasure.TAG, "Error handling PrimesStartupMeasure's onActivityResume", e, new Object[0]);
            }
        }

        public void onActivityPaused(Activity activity) {
        }

        public void onActivityStopped(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        public void onActivityDestroyed(Activity activity) {
        }

        private final class MyOnPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
            private View view;

            private MyOnPreDrawListener(View view2) {
                this.view = view2;
            }

            public boolean onPreDraw() {
                try {
                    if (this.view != null) {
                        StartupCallbacks.this.app.unregisterActivityLifecycleCallbacks(StartupCallbacks.this);
                        this.view.getViewTreeObserver().removeOnPreDrawListener(this);
                        ThreadUtil.postOnUiThread(new C1109xee2b9e1f(this));
                    }
                } catch (RuntimeException e) {
                    PrimesLog.m45d(PrimesStartupMeasure.TAG, "Error handling PrimesStartupMeasure's onPreDraw", e, new Object[0]);
                } catch (Throwable th) {
                    this.view = null;
                    throw th;
                }
                this.view = null;
                return true;
            }

            /* access modifiers changed from: package-private */
            /* renamed from: lambda$onPreDraw$0$PrimesStartupMeasure$StartupCallbacks$MyOnPreDrawListener */
            public final /* synthetic */ void mo19799xad37085f() {
                if (PrimesStartupMeasure.this.firstDrawnAt == 0) {
                    long unused = PrimesStartupMeasure.this.firstDrawnAt = SystemClock.elapsedRealtime();
                    PrimesStartupMeasure.this.runOnDrawListeners();
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void registerOrRunOnActivityInitListener(PrimesStartupMeasureListener.OnActivityInit listener) {
        synchronized (this.onActivityInitListenerLock) {
            if (this.firstOnActivityInitAt > 0) {
                runOnActivityInit(listener);
            } else {
                if (this.onActivityInitListeners == ON_ACTIVITY_INIT_EMPTY_LIST) {
                    this.onActivityInitListeners = new ArrayList();
                }
                this.onActivityInitListeners.add(listener);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void registerOrRunOnDrawListener(PrimesStartupMeasureListener.OnDraw listener) {
        synchronized (this.onDrawListenerLock) {
            if (this.firstDrawnAt > 0) {
                runOnDraw(listener);
            } else {
                if (this.onDrawListeners == ON_DRAW_EMPTY_LIST) {
                    this.onDrawListeners = new ArrayList();
                }
                this.onDrawListeners.add(listener);
            }
        }
    }

    private void runOnActivityInitListeners() {
        synchronized (this.onActivityInitListenerLock) {
            for (PrimesStartupMeasureListener.OnActivityInit listener : this.onActivityInitListeners) {
                runOnActivityInit(listener);
            }
            this.onActivityInitListeners = Collections.emptyList();
        }
    }

    private static void runOnActivityInit(PrimesStartupMeasureListener.OnActivityInit listener) {
        try {
            listener.onActivityInit();
        } catch (RuntimeException e) {
            PrimesLog.m45d(TAG, "Error running onActivityInit listener", e, new Object[0]);
        }
    }

    /* access modifiers changed from: private */
    public void runOnDrawListeners() {
        synchronized (this.onDrawListenerLock) {
            for (PrimesStartupMeasureListener.OnDraw listener : this.onDrawListeners) {
                runOnDraw(listener);
            }
            this.onDrawListeners = Collections.emptyList();
        }
    }

    private static void runOnDraw(PrimesStartupMeasureListener.OnDraw listener) {
        try {
            listener.onDraw();
        } catch (RuntimeException e) {
            PrimesLog.m45d(TAG, "Error running onDraw listener", e, new Object[0]);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isColdStartup() {
        return this.startedByUser;
    }

    /* access modifiers changed from: package-private */
    public long getAppClassLoadedAt() {
        return this.appClassLoadedAt;
    }

    /* access modifiers changed from: package-private */
    public long getAppOnCreateAt() {
        return this.appOnCreateAt;
    }

    /* access modifiers changed from: package-private */
    public long getFirstOnActivityInitAt() {
        return this.firstOnActivityInitAt;
    }

    /* access modifiers changed from: package-private */
    public StartupActivityInfo[] getStartupActivityInfos() {
        StartupActivityInfo[] startupActivityInfoArr;
        synchronized (this.startupActivityInfos) {
            startupActivityInfoArr = (StartupActivityInfo[]) this.startupActivityInfos.toArray(new StartupActivityInfo[this.startupActivityInfos.size()]);
        }
        return startupActivityInfoArr;
    }

    /* access modifiers changed from: package-private */
    public long getFirstOnActivityStartedAt() {
        return this.firstOnActivityStartedAt;
    }

    /* access modifiers changed from: package-private */
    public long getFirstOnActivityResumedAt() {
        return this.firstOnActivityResumedAt;
    }

    /* access modifiers changed from: package-private */
    public long getFirstDrawnAt() {
        return this.firstDrawnAt;
    }

    /* access modifiers changed from: package-private */
    public long getFirstAppInteractiveAt() {
        return this.firstAppInteractiveAt;
    }

    /* access modifiers changed from: package-private */
    public NoPiiString getStartupType() {
        return this.startupType;
    }

    @VisibleForTesting(otherwise = 5)
    static void reset() {
        instance = new PrimesStartupMeasure();
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public List<PrimesStartupMeasureListener.OnActivityInit> getOnActivityInitListeners() {
        return this.onActivityInitListeners;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public List<PrimesStartupMeasureListener.OnDraw> getOnDrawListeners() {
        return this.onDrawListeners;
    }
}
