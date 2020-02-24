package com.google.android.libraries.performance.primes;

import android.support.annotation.Nullable;
import com.google.android.libraries.performance.primes.TimerEvent;
import com.google.android.libraries.performance.primes.scenario.ScenarioEvent;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import java.lang.Thread;
import java.util.concurrent.ScheduledExecutorService;
import logs.proto.wireless.performance.mobile.ExtensionMetric;
import logs.proto.wireless.performance.mobile.PrimesTraceOuterClass;

interface PrimesApi {
    void addScenarioEvent(ScenarioEvent scenarioEvent);

    void addValueToCounter(String str, @Nullable String str2, boolean z, long j);

    void cancelBatteryDiffMeasurement(String str);

    void cancelGlobalTimer(String str);

    void cancelJankRecorder(String str);

    void cancelMemoryDiffMeasurement(String str);

    void cancelScenarioSampling(String str);

    void executeAfterInitialized(Runnable runnable);

    Supplier<ScheduledExecutorService> getExecutorServiceSupplier();

    MetricTransmitter getTransmitter();

    boolean isNetworkEnabled();

    void recordBatterySnapshot(String str, boolean z);

    void recordBatterySnapshotOnForegroundServiceStart();

    void recordBatterySnapshotOnForegroundServiceStop();

    void recordDuration(WhitelistToken whitelistToken, String str, boolean z, long j, long j2, ExtensionMetric.MetricExtension metricExtension);

    void recordMemory(String str, boolean z);

    void recordMemory(String str, boolean z, ExtensionMetric.MetricExtension metricExtension);

    void recordNetwork(NetworkEvent networkEvent);

    void recordPackageStats();

    void recordScenario(PrimesTraceOuterClass.PrimesTrace primesTrace);

    void recordScenarioSampledMetrics(String str);

    void recordTrace(TikTokWhitelistToken tikTokWhitelistToken, PrimesTraceOuterClass.PrimesTrace primesTrace, @Nullable ExtensionMetric.MetricExtension metricExtension);

    boolean registerShutdownListener(ShutdownListener shutdownListener);

    void sendCustomLaunchedEvent();

    void sendPendingNetworkEvents();

    void shutdown();

    void startBatteryDiffMeasurement(String str, boolean z);

    Counter startCounter();

    void startCrashMonitor();

    void startGlobalTimer(String str);

    void startJankRecorder(String str);

    void startMemoryDiffMeasurement(String str);

    void startMemoryMonitor();

    void startScenarioSampling(String str);

    TimerEvent startTimer();

    void stopBatteryDiffMeasurement(String str, boolean z, ExtensionMetric.MetricExtension metricExtension);

    void stopCounter(Counter counter, String str, @Nullable String str2, boolean z);

    void stopGlobalTimer(String str, String str2, boolean z, TimerEvent.TimerStatus timerStatus);

    void stopGlobalTimer(String str, String str2, boolean z, ExtensionMetric.MetricExtension metricExtension, TimerEvent.TimerStatus timerStatus);

    void stopGlobalTimer(String str, boolean z, TimerEvent.TimerStatus timerStatus);

    void stopJankRecorder(String str, boolean z, @Nullable ExtensionMetric.MetricExtension metricExtension);

    void stopMemoryDiffMeasurement(String str, boolean z, @Nullable ExtensionMetric.MetricExtension metricExtension);

    void stopTimer(@Nullable TimerEvent timerEvent, String str, boolean z, TimerEvent.TimerStatus timerStatus);

    void stopTimer(@Nullable TimerEvent timerEvent, String str, boolean z, ExtensionMetric.MetricExtension metricExtension, TimerEvent.TimerStatus timerStatus);

    void watchForMemoryLeak(Object obj);

    Thread.UncaughtExceptionHandler wrapCrashReportingIntoUncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler);
}
