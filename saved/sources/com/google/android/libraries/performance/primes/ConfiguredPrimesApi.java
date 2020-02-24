package com.google.android.libraries.performance.primes;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.google.android.libraries.performance.primes.TimerEvent;
import com.google.android.libraries.performance.primes.scenario.ScenarioEvent;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import com.google.android.libraries.stitch.util.Preconditions;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import logs.proto.wireless.performance.mobile.ExtensionMetric;
import logs.proto.wireless.performance.mobile.PrimesTraceOuterClass;

final class ConfiguredPrimesApi implements PrimesApi {
    private static final String TAG = "Primes";
    @VisibleForTesting
    final LazyMetricServices lazyServices;
    private final String packageName;

    ConfiguredPrimesApi(LazyMetricServices lazyServices2, String packageName2) {
        this.lazyServices = lazyServices2;
        this.packageName = packageName2;
    }

    /* access modifiers changed from: package-private */
    public List<PrimesStartupListener> initAndGetServices() {
        List<PrimesStartupListener> listeners = startMetricServices();
        if (this.lazyServices.memoryLeakMetricEnabled()) {
            this.lazyServices.memoryLeakMetricService().startMonitoring();
        } else {
            logDebug("Memory Leak metric disabled", new Object[0]);
        }
        if (this.lazyServices.miniHeapDumpMetricEnabled()) {
            this.lazyServices.miniHeapDumpMetricService().startMonitoring();
        } else {
            logDebug("Mini heap dump disabled", new Object[0]);
        }
        return listeners;
    }

    private List<PrimesStartupListener> startMetricServices() {
        List<PrimesStartupListener> startupListeners = new ArrayList<>();
        if (this.lazyServices.crashMetricEnabled()) {
            startupListeners.add(this.lazyServices.crashMetricService());
        } else {
            logDebug("Crash metric disabled - not registering for startup notifications.", new Object[0]);
        }
        if (this.lazyServices.strictModeEnabled()) {
            startupListeners.add(this.lazyServices.strictModeService());
        } else {
            logDebug("Strict mode disabled", new Object[0]);
        }
        if (this.lazyServices.autoPackageMetricEnabled()) {
            startupListeners.add(this.lazyServices.packageMetricService());
            logDebug("Package metric: registered for startup notifications", new Object[0]);
        }
        if (this.lazyServices.batteryMetricEnabled()) {
            startupListeners.add(this.lazyServices.batteryMetricService());
            logDebug("Battery metrics enabled", new Object[0]);
        } else {
            logDebug("Battery metric disabled", new Object[0]);
        }
        if (this.lazyServices.magicEyeLogEnabled()) {
            startupListeners.add(this.lazyServices.magicEyeLogService());
        } else {
            logDebug("MagicEye logging metric disabled", new Object[0]);
        }
        if (this.lazyServices.frameMetricEnabled()) {
            startupListeners.add(this.lazyServices.frameMetricService());
        }
        if (this.lazyServices.cpuMetricEnabled()) {
            startupListeners.add(this.lazyServices.cpuMetricService());
        } else {
            logDebug("Cpu metric disabled - not registering for startup notifications.", new Object[0]);
        }
        if (this.lazyServices.cpuProfilingEnabled()) {
            startupListeners.add(this.lazyServices.cpuProfilingService());
        } else {
            logDebug("Cpu profiling disabled", new Object[0]);
        }
        if (this.lazyServices.startupMetricEnabled()) {
            this.lazyServices.startupMetricHandler();
        } else {
            logDebug("Startup metric disabled", new Object[0]);
        }
        return startupListeners;
    }

    private void logDebug(String msg, Object... args) {
        if (PrimesLog.dLoggable(TAG)) {
            String str = this.packageName;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 2 + String.valueOf(msg).length());
            sb.append(str);
            sb.append(": ");
            sb.append(msg);
            PrimesLog.m46d(TAG, sb.toString(), args);
        }
    }

    public Supplier<ScheduledExecutorService> getExecutorServiceSupplier() {
        return this.lazyServices.getExecutorServiceSupplier();
    }

    public void shutdown() {
        this.lazyServices.getShutdown().shutdown();
    }

    public boolean registerShutdownListener(ShutdownListener shutdownListener) {
        return this.lazyServices.getShutdown().registerShutdownListener(shutdownListener);
    }

    public MetricTransmitter getTransmitter() {
        return this.lazyServices.metricTransmitter();
    }

    public void executeAfterInitialized(Runnable runnable) {
        ((Runnable) Preconditions.checkNotNull(runnable)).run();
    }

    public void startMemoryMonitor() {
        if (this.lazyServices.memoryMetricEnabled()) {
            this.lazyServices.memoryMetricService().startMonitoring();
        }
    }

    public void recordMemory(String customEventName, boolean isEventNameConstant) {
        recordMemory(customEventName, isEventNameConstant, null);
    }

    public void recordMemory(String customEventName, boolean isEventNameConstant, @Nullable ExtensionMetric.MetricExtension metricExtension) {
        if (this.lazyServices.memoryMetricEnabled()) {
            this.lazyServices.memoryMetricService().recordEvent(customEventName, isEventNameConstant, metricExtension);
        }
    }

    public void startMemoryDiffMeasurement(String customEventName) {
        if (this.lazyServices.memoryMetricEnabled()) {
            this.lazyServices.memoryMetricService().startMemoryDiffMeasurement(customEventName);
        }
    }

    public void stopMemoryDiffMeasurement(String customEventName, boolean isEventNameConstant, @Nullable ExtensionMetric.MetricExtension metricExtension) {
        if (this.lazyServices.memoryMetricEnabled()) {
            this.lazyServices.memoryMetricService().stopMemoryDiffMeasurement(customEventName, isEventNameConstant, metricExtension);
        }
    }

    public void cancelMemoryDiffMeasurement(String customEventName) {
        if (this.lazyServices.memoryMetricEnabled()) {
            this.lazyServices.memoryMetricService().cancelMemoryDiffMeasurement(customEventName);
        }
    }

    public void recordNetwork(NetworkEvent networkEvent) {
        if (networkEvent != null && this.lazyServices.networkMetricEnabled()) {
            this.lazyServices.networkMetricService().recordEvent(networkEvent);
        }
    }

    public void sendPendingNetworkEvents() {
        if (this.lazyServices.networkMetricEnabled()) {
            this.lazyServices.networkMetricService().sendPendingEvents();
        }
    }

    public void recordDuration(WhitelistToken whitelistToken, String customEventName, boolean isEventNameConstant, long startMs, long endMs, ExtensionMetric.MetricExtension metricExtension) {
        Preconditions.checkNotNull(whitelistToken);
        if (this.lazyServices.timerMetricEnabled()) {
            this.lazyServices.timerMetricService().recordEvent(customEventName, isEventNameConstant, startMs, endMs, metricExtension);
        }
    }

    public void startGlobalTimer(String customEventName) {
        if (this.lazyServices.timerMetricEnabled()) {
            this.lazyServices.timerMetricService().startGlobal(customEventName);
        }
    }

    public void stopGlobalTimer(String customEventName, boolean isEventNameConstant, TimerEvent.TimerStatus timerStatus) {
        stopGlobalTimer(customEventName, null, isEventNameConstant, null, timerStatus);
    }

    public void stopGlobalTimer(String customEventName, @Nullable String newEventName, boolean isEventNameConstant, TimerEvent.TimerStatus timerStatus) {
        stopGlobalTimer(customEventName, newEventName, isEventNameConstant, null, timerStatus);
    }

    public void stopGlobalTimer(String customEventName, @Nullable String newEventName, boolean isEventNameConstant, @Nullable ExtensionMetric.MetricExtension metricExtension, TimerEvent.TimerStatus timerStatus) {
        if (this.lazyServices.timerMetricEnabled()) {
            this.lazyServices.timerMetricService().stopGlobal(customEventName, newEventName, isEventNameConstant, metricExtension, timerStatus);
        }
    }

    /* access modifiers changed from: package-private */
    public void recordGlobalTimer(TimerEvent timerEvent, String eventName, @Nullable String newEventName, boolean isEventNameConstant, @Nullable ExtensionMetric.MetricExtension metricExtension, @Nullable TimerEvent.TimerStatus timerStatus) {
        if (this.lazyServices.timerMetricEnabled()) {
            this.lazyServices.timerMetricService().recordGlobalTimer(timerEvent, eventName, newEventName, isEventNameConstant, metricExtension, timerStatus);
        }
    }

    public void cancelGlobalTimer(String customEventName) {
        if (this.lazyServices.timerMetricEnabled()) {
            this.lazyServices.timerMetricService().cancelGlobal(customEventName);
        }
    }

    public TimerEvent startTimer() {
        if (!this.lazyServices.timerMetricEnabled()) {
            return TimerEvent.EMPTY_TIMER;
        }
        return this.lazyServices.timerMetricService().start();
    }

    public void stopTimer(@Nullable TimerEvent event, String customEventName, boolean isEventNameConstant, TimerEvent.TimerStatus timerStatus) {
        stopTimer(event, customEventName, isEventNameConstant, null, timerStatus);
    }

    public void stopTimer(@Nullable TimerEvent event, String customEventName, boolean isEventNameConstant, ExtensionMetric.MetricExtension metricExtension, TimerEvent.TimerStatus timerStatus) {
        if (!TimerEvent.isEmpty(event) && this.lazyServices.timerMetricEnabled()) {
            event.stop();
            event.setTimerStatus(timerStatus);
            this.lazyServices.timerMetricService().recordTimer(event, customEventName, isEventNameConstant, metricExtension);
        }
    }

    public void startCrashMonitor() {
        if (this.lazyServices.crashMetricEnabled()) {
            this.lazyServices.crashMetricService().setPrimesExceptionHandlerAsDefaultHandler();
        } else {
            logDebug("Primes crash monitoring is not enabled, yet crash monitoring was requested.", new Object[0]);
        }
    }

    public Thread.UncaughtExceptionHandler wrapCrashReportingIntoUncaughtExceptionHandler(Thread.UncaughtExceptionHandler handler) {
        if (this.lazyServices.crashMetricEnabled()) {
            return this.lazyServices.crashMetricService().wrapUncaughtExceptionHandlerWithPrimesHandler(handler);
        }
        logDebug("Primes crash monitoring is not enabled, yet a UncaughtExceptionHandler withcrash monitoring was requested.", new Object[0]);
        return handler;
    }

    public void startJankRecorder(String eventName) {
        if (this.lazyServices.frameMetricEnabled()) {
            this.lazyServices.frameMetricService().startMeasurement(eventName);
        } else if (this.lazyServices.jankMetricEnabled()) {
            this.lazyServices.jankMetricService().start(eventName);
        }
    }

    public void stopJankRecorder(String eventName, boolean isEventNameConstant, @Nullable ExtensionMetric.MetricExtension metricExtension) {
        if (this.lazyServices.frameMetricEnabled()) {
            this.lazyServices.frameMetricService().stopMeasurement(eventName, isEventNameConstant, metricExtension);
        } else if (this.lazyServices.jankMetricEnabled()) {
            this.lazyServices.jankMetricService().stop(eventName, isEventNameConstant, metricExtension);
        }
    }

    public void cancelJankRecorder(String eventName) {
        if (this.lazyServices.frameMetricEnabled()) {
            this.lazyServices.frameMetricService().cancelMeasurement(eventName);
        } else if (this.lazyServices.jankMetricEnabled()) {
            this.lazyServices.jankMetricService().cancel(eventName);
        }
    }

    public boolean isNetworkEnabled() {
        return this.lazyServices.networkMetricEnabled();
    }

    public void watchForMemoryLeak(Object object) {
        if (object != null && this.lazyServices.memoryLeakMetricEnabled()) {
            this.lazyServices.memoryLeakMetricService().watchForMemoryLeak(object);
        }
    }

    public void recordScenario(PrimesTraceOuterClass.PrimesTrace primesTrace) {
        if (primesTrace == null || primesTrace.getSpansCount() == 0) {
            PrimesLog.m54w(TAG, "Invalid traces were logged.", new Object[0]);
        } else {
            this.lazyServices.traceMetricRecordingService().record(primesTrace, null, null);
        }
    }

    public void recordTrace(TikTokWhitelistToken token, PrimesTraceOuterClass.PrimesTrace primesTrace, @Nullable ExtensionMetric.MetricExtension metricExtension) {
        Preconditions.checkNotNull(token);
        if (primesTrace == null || primesTrace.getSpansCount() == 0) {
            PrimesLog.m54w(TAG, "Invalid traces were logged.", new Object[0]);
        } else if (this.lazyServices.tiktokTraceEnabled()) {
            this.lazyServices.traceMetricService().record(primesTrace, metricExtension);
        }
    }

    public void recordPackageStats() {
        recordPackageStatsImpl();
    }

    /* access modifiers changed from: package-private */
    public boolean recordPackageStatsImpl() {
        if (!this.lazyServices.manualPackageMetricEnabled()) {
            return false;
        }
        PrimesExecutors.handleFuture(this.lazyServices.getExecutorServiceSupplier().get().submit(new Runnable() {
            public void run() {
                if (!ConfiguredPrimesApi.this.lazyServices.skipPackageMetric()) {
                    PrimesExecutors.handleFuture(ConfiguredPrimesApi.this.lazyServices.packageMetricService().sendInBackground());
                }
            }
        }));
        return true;
    }

    public void recordBatterySnapshot(String customEventName, boolean isEventNameConstant) {
        if (this.lazyServices.batteryMetricEnabled()) {
            PrimesExecutors.handleFuture(this.lazyServices.batteryMetricService().scheduleManualCapture(customEventName, isEventNameConstant));
        }
    }

    public void recordBatterySnapshotOnForegroundServiceStart() {
        if (this.lazyServices.batteryMetricEnabled()) {
            PrimesExecutors.handleFuture(this.lazyServices.batteryMetricService().onForegroundServiceStarted());
        }
    }

    public void recordBatterySnapshotOnForegroundServiceStop() {
        if (this.lazyServices.batteryMetricEnabled()) {
            PrimesExecutors.handleFuture(this.lazyServices.batteryMetricService().onForegroundServiceStopped());
        }
    }

    public void startBatteryDiffMeasurement(String customEventName, boolean isEventNameConstant) {
        if (this.lazyServices.batteryMetricEnabled()) {
            this.lazyServices.batteryMetricService().startBatteryDiffMeasurement(customEventName, isEventNameConstant);
        }
    }

    public void stopBatteryDiffMeasurement(String customEventName, boolean isEventNameConstant, ExtensionMetric.MetricExtension metricExtension) {
        if (this.lazyServices.batteryMetricEnabled()) {
            this.lazyServices.batteryMetricService().stopBatteryDiffMeasurement(customEventName, isEventNameConstant, metricExtension);
        }
    }

    public void cancelBatteryDiffMeasurement(String customEventName) {
        if (this.lazyServices.batteryMetricEnabled()) {
            this.lazyServices.batteryMetricService().cancelBatteryDiffMeasurement(customEventName);
        }
    }

    public void addValueToCounter(String counterName, @Nullable String componentName, boolean areNamesConstant, long value) {
        if (this.lazyServices.counterMetricEnabled()) {
            this.lazyServices.counterMetricService().addValueToCounter(counterName, componentName, areNamesConstant, value);
        }
    }

    public Counter startCounter() {
        if (this.lazyServices.counterMetricEnabled()) {
            return this.lazyServices.counterMetricService().startCounter();
        }
        return Counter.EMPTY_COUNTER;
    }

    public void stopCounter(Counter counter, String counterName, @Nullable String componentName, boolean areNamesConstant) {
        if (this.lazyServices.counterMetricEnabled()) {
            this.lazyServices.counterMetricService().stopCounter(counter, counterName, componentName, areNamesConstant);
        }
    }

    public void addScenarioEvent(ScenarioEvent scenarioEvent) {
        Preconditions.checkNotNull(scenarioEvent);
        if (this.lazyServices.scenarioMetricServiceOptional().isPresent()) {
            this.lazyServices.scenarioMetricServiceOptional().get().addScenarioEvent(scenarioEvent);
        }
    }

    public void startScenarioSampling(String scenarioName) {
        if (this.lazyServices.scenarioSamplingMetricServiceOptional().isPresent()) {
            this.lazyServices.scenarioSamplingMetricServiceOptional().get().startScenarioSampling(scenarioName);
        }
    }

    public void recordScenarioSampledMetrics(String scenarioName) {
        if (this.lazyServices.scenarioSamplingMetricServiceOptional().isPresent()) {
            this.lazyServices.scenarioSamplingMetricServiceOptional().get().recordScenarioSampledMetrics(scenarioName);
        }
    }

    public void cancelScenarioSampling(String scenarioName) {
        if (this.lazyServices.scenarioSamplingMetricServiceOptional().isPresent()) {
            this.lazyServices.scenarioSamplingMetricServiceOptional().get().cancelScenarioSampling(scenarioName);
        }
    }

    public void sendCustomLaunchedEvent() {
        if (this.lazyServices.crashMetricEnabled()) {
            this.lazyServices.crashMetricService().sendCustomLaunchedEvent();
        }
    }
}
