package com.google.android.libraries.performance.primes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.google.android.libraries.performance.primes.debug.Intents;
import com.google.android.libraries.performance.primes.trace.PrimesTrace;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import com.google.android.libraries.stitch.util.Preconditions;

import logs.proto.wireless.performance.mobile.ExtensionMetric;
import logs.proto.wireless.performance.mobile.PrimesTraceOuterClass;

public class Primes {
    private static final Primes DEFAULT_PRIMES = new Primes(new NoopPrimesApi());
    private static final String TAG = "Primes";
    private static volatile Primes primes = DEFAULT_PRIMES;
    private static volatile boolean warningNotYetLogged = true;
    /* access modifiers changed from: private */
    public final PrimesApi primesApi;

    private Primes(PrimesApi primesApi2) {
        this.primesApi = (PrimesApi) Preconditions.checkNotNull(primesApi2);
    }

    public static synchronized Primes initialize(ApiProvider apiProvider) {
        synchronized (Primes.class) {
            if (primes.isInitialized()) {
                PrimesLog.m46d(TAG, "Primes.initialize() is called more than once. This call will be ignored.", new Object[0]);
                Primes primes2 = primes;
                return primes2;
            }
            try {
                PrimesTrace.beginSection("Primes-initialize");
                PrimesForPrimesMeasurements.apiMeasurement().onPrimesInitializeStart();
                Primes primes3 = new Primes(apiProvider.getPrimesApi());
                primes = primes3;
                return primes3;
            } finally {
                PrimesForPrimesMeasurements.apiMeasurement().onPrimesInitializeEnd();
                PrimesTrace.endSection();
            }
        }
    }

    public static Primes get() {
        if (primes == DEFAULT_PRIMES && warningNotYetLogged) {
            warningNotYetLogged = false;
            PrimesLog.warning("Primes not initialized, returning default (no-op) Primes instance which will ignore all calls. Please call Primes.initialize(...) before using any Primes API.", new Object[0]);
        }
        return primes;
    }

    public static boolean startEventDebugActivity(Context context) {
        if (!primes.isInitialized()) {
            return false;
        }
        Intent intent = Intents.createPrimesEventDebugActivityIntent(context);
        if (context.getPackageManager().queryIntentActivities(intent, 65536).isEmpty()) {
            PrimesLog.m54w(TAG, "PrimesEventActivity not found: primes/debug is not included in the app.", new Object[0]);
            return false;
        }
        context.startActivity(intent);
        return true;
    }

    private static String toString(NoPiiString string) {
        if (string != null) {
            return string.toString();
        }
        return null;
    }

    public static synchronized void reset(ShutdownWhitelistToken token) {
        synchronized (Primes.class) {
            if (token == null) {
                PrimesLog.m54w(TAG, "Primes Shutdown token null, ignoring reset.", new Object[0]);
                return;
            }
            primes.primesApi.shutdown();
            primes = DEFAULT_PRIMES;
        }
    }

    static synchronized void reset() {
        synchronized (Primes.class) {
            primes = DEFAULT_PRIMES;
        }
    }

    /* access modifiers changed from: package-private */
    public void executeAfterInitialized(Runnable runnable) {
        this.primesApi.executeAfterInitialized(runnable);
    }

    public void startMemoryMonitor() {
        this.primesApi.startMemoryMonitor();
    }

    @Deprecated
    public void recordMemory(String customEventName) {
        this.primesApi.recordMemory(customEventName, false);
    }

    @Deprecated
    public synchronized void recordMemory(String customEventName, ExtensionMetric.MetricExtension metricExtension) {
        this.primesApi.recordMemory(customEventName, false, metricExtension);
    }

    public void recordMemory(NoPiiString eventName) {
        this.primesApi.recordMemory(toString(eventName), true);
    }

    public synchronized void recordMemory(NoPiiString eventName, ExtensionMetric.MetricExtension metricExtension) {
        this.primesApi.recordMemory(toString(eventName), true, metricExtension);
    }

    public void recordNetwork(NetworkEvent networkEvent) {
        this.primesApi.recordNetwork(networkEvent);
    }

    @Deprecated
    public void startGlobalTimer(String customEventName) {
        this.primesApi.startGlobalTimer(customEventName);
    }

    public void startGlobalTimer(NoPiiString eventName) {
        this.primesApi.startGlobalTimer(toString(eventName));
    }

    @Deprecated
    public void stopGlobalTimer(String customEventName) {
        this.primesApi.stopGlobalTimer(customEventName, false, TimerEvent.TimerStatus.UNKNOWN);
    }

    @Deprecated
    public void stopGlobalTimer(String customEventName, String newEventName) {
        this.primesApi.stopGlobalTimer(customEventName, newEventName, false, TimerEvent.TimerStatus.UNKNOWN);
    }

    @Deprecated
    public synchronized void stopGlobalTimer(String customEventName, String newEventName, ExtensionMetric.MetricExtension metricExtension) {
        this.primesApi.stopGlobalTimer(customEventName, newEventName, false, metricExtension, TimerEvent.TimerStatus.UNKNOWN);
    }

    public void stopGlobalTimer(NoPiiString eventName) {
        this.primesApi.stopGlobalTimer(toString(eventName), true, TimerEvent.TimerStatus.UNKNOWN);
    }

    public void stopGlobalTimer(NoPiiString eventName, TimerEvent.TimerStatus timerStatus) {
        this.primesApi.stopGlobalTimer(toString(eventName), true, timerStatus);
    }

    public void stopGlobalTimer(NoPiiString eventName, NoPiiString newEventName) {
        this.primesApi.stopGlobalTimer(toString(eventName), toString(newEventName), true, TimerEvent.TimerStatus.UNKNOWN);
    }

    public void stopGlobalTimer(NoPiiString eventName, NoPiiString newEventName, TimerEvent.TimerStatus timerStatus) {
        this.primesApi.stopGlobalTimer(toString(eventName), toString(newEventName), true, timerStatus);
    }

    public synchronized void stopGlobalTimer(NoPiiString eventName, NoPiiString newEventName, ExtensionMetric.MetricExtension metricExtension) {
        this.primesApi.stopGlobalTimer(toString(eventName), toString(newEventName), true, metricExtension, TimerEvent.TimerStatus.UNKNOWN);
    }

    public synchronized void stopGlobalTimer(NoPiiString eventName, NoPiiString newEventName, ExtensionMetric.MetricExtension metricExtension, TimerEvent.TimerStatus timerStatus) {
        this.primesApi.stopGlobalTimer(toString(eventName), toString(newEventName), true, metricExtension, timerStatus);
    }

    public void cancelGlobalTimer(NoPiiString eventName) {
        this.primesApi.cancelGlobalTimer(toString(eventName));
    }

    @Deprecated
    public void recordDuration(WhitelistToken whitelistToken, String customEventName, long startMs, long endMs) {
        this.primesApi.recordDuration(whitelistToken, customEventName, true, startMs, endMs, null);
    }

    public void recordDuration(WhitelistToken whitelistToken, NoPiiString noPiiEventName, long startMs, long endMs) {
        this.primesApi.recordDuration(whitelistToken, NoPiiString.safeToString(noPiiEventName), true, startMs, endMs, null);
    }

    @Deprecated
    public void recordDuration(WhitelistToken whitelistToken, String customEventName, long startMs, long endMs, ExtensionMetric.MetricExtension metricExtension) {
        this.primesApi.recordDuration(whitelistToken, customEventName, true, startMs, endMs, metricExtension);
    }

    public void recordDuration(WhitelistToken whitelistToken, NoPiiString noPiiEventName, long startMs, long endMs, ExtensionMetric.MetricExtension metricExtension) {
        this.primesApi.recordDuration(whitelistToken, NoPiiString.safeToString(noPiiEventName), true, startMs, endMs, metricExtension);
    }

    public TimerEvent startTimer() {
        return this.primesApi.startTimer();
    }

    @Deprecated
    public void stopTimer(@Nullable TimerEvent event, String customEventName) {
        this.primesApi.stopTimer(event, customEventName, false, TimerEvent.TimerStatus.UNKNOWN);
    }

    @Deprecated
    public synchronized void stopTimer(@Nullable TimerEvent event, String customEventName, ExtensionMetric.MetricExtension metricExtension) {
        this.primesApi.stopTimer(event, customEventName, false, metricExtension, TimerEvent.TimerStatus.UNKNOWN);
    }

    public void stopTimer(@Nullable TimerEvent event, NoPiiString eventName) {
        stopTimer(event, eventName, TimerEvent.TimerStatus.UNKNOWN);
    }

    public void stopTimer(@Nullable TimerEvent event, NoPiiString eventName, TimerEvent.TimerStatus timerStatus) {
        this.primesApi.stopTimer(event, toString(eventName), true, timerStatus);
    }

    public synchronized void stopTimer(@Nullable TimerEvent event, NoPiiString eventName, ExtensionMetric.MetricExtension metricExtension, TimerEvent.TimerStatus timerStatus) {
        this.primesApi.stopTimer(event, toString(eventName), true, metricExtension, timerStatus);
    }

    public synchronized void stopTimer(@Nullable TimerEvent event, NoPiiString eventName, ExtensionMetric.MetricExtension metricExtension) {
        stopTimer(event, eventName, metricExtension, TimerEvent.TimerStatus.UNKNOWN);
    }

    public void startCrashMonitor() {
        this.primesApi.startCrashMonitor();
    }

    public Thread.UncaughtExceptionHandler wrapCrashReportingIntoUncaughtExceptionHandler(Thread.UncaughtExceptionHandler handler) {
        return this.primesApi.wrapCrashReportingIntoUncaughtExceptionHandler(handler);
    }

    @Deprecated
    public void startJankRecorder(String eventName) {
        this.primesApi.startJankRecorder(eventName);
    }

    public void startJankRecorder(NoPiiString eventName) {
        this.primesApi.startJankRecorder(toString(eventName));
    }

    @Deprecated
    public void stopJankRecorder(String eventName) {
        this.primesApi.stopJankRecorder(eventName, false, null);
    }

    public void stopJankRecorder(NoPiiString eventName) {
        this.primesApi.stopJankRecorder(toString(eventName), true, null);
    }

    public void stopJankRecorder(NoPiiString eventName, @Nullable ExtensionMetric.MetricExtension metricExtension) {
        this.primesApi.stopJankRecorder(toString(eventName), true, metricExtension);
    }

    public void cancelJankRecorder(NoPiiString eventName) {
        this.primesApi.cancelJankRecorder(toString(eventName));
    }

    public void watchForMemoryLeak(Object object) {
        this.primesApi.watchForMemoryLeak(object);
    }

    public boolean isInitialized() {
        return this != DEFAULT_PRIMES;
    }

    @Deprecated
    public boolean startPrimesEventDebugActivity(Context context) {
        return startEventDebugActivity(context);
    }

    public void recordPackageStats() {
        this.primesApi.recordPackageStats();
    }

    public void recordTrace(TikTokWhitelistToken token, PrimesTraceOuterClass.PrimesTrace primesTrace, @Nullable ExtensionMetric.MetricExtension metricExtension) {
        this.primesApi.recordTrace(token, primesTrace, metricExtension);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting(otherwise = 5)
    public MetricTransmitter getTransmitter() {
        return this.primesApi.getTransmitter();
    }

    /* access modifiers changed from: package-private */
    public boolean isNetworkEnabled() {
        return this.primesApi.isNetworkEnabled();
    }

    public void recordBatterySnapshot(NoPiiString eventName) {
        this.primesApi.recordBatterySnapshot(toString(eventName), true);
    }

    public void recordBatterySnapshotOnForegroundServiceStart() {
        this.primesApi.recordBatterySnapshotOnForegroundServiceStart();
    }

    public void recordBatterySnapshotOnForegroundServiceStop() {
        this.primesApi.recordBatterySnapshotOnForegroundServiceStop();
    }

    public void startBatteryDiffMeasurement(NoPiiString customEventName) {
        this.primesApi.startBatteryDiffMeasurement(NoPiiString.safeToString(customEventName), true);
    }

    public void stopBatteryDiffMeasurement(NoPiiString customEventName) {
        this.primesApi.stopBatteryDiffMeasurement(NoPiiString.safeToString(customEventName), true, null);
    }

    public void stopBatteryDiffMeasurement(NoPiiString customEventName, ExtensionMetric.MetricExtension metricExtension) {
        this.primesApi.stopBatteryDiffMeasurement(NoPiiString.safeToString(customEventName), true, metricExtension);
    }

    public void cancelBatteryDiffMeasurement(NoPiiString customEventName) {
        this.primesApi.cancelBatteryDiffMeasurement(NoPiiString.safeToString(customEventName));
    }

    public void shutdown(ShutdownWhitelistToken token) {
        if (token == null) {
            PrimesLog.m54w(TAG, "Primes Shutdown token null, ignoring Shutdown.", new Object[0]);
        } else {
            this.primesApi.executeAfterInitialized(new Runnable() {
                public void run() {
                    Primes.this.primesApi.shutdown();
                }
            });
        }
    }

    public void sendCustomLaunchedEvent(LaunchCountWhitelistToken token) {
        if (token == null) {
            PrimesLog.m54w(TAG, "Primes Lifecycle token null, ignoring logFirstActivityLaunched.", new Object[0]);
        } else {
            this.primesApi.sendCustomLaunchedEvent();
        }
    }

    /* access modifiers changed from: package-private */
    public PrimesApi getPrimesApi() {
        return this.primesApi;
    }
}
