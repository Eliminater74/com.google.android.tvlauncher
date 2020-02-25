package com.google.android.libraries.performance.primes;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.google.android.libraries.performance.primes.battery.BatteryCapture;
import com.google.android.libraries.performance.primes.battery.StatsStorage;
import com.google.android.libraries.performance.primes.battery.SystemHealthCapture;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import com.google.android.libraries.stitch.util.ThreadUtil;
import com.google.common.base.Optional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import logs.proto.wireless.performance.mobile.BatteryMetric;
import logs.proto.wireless.performance.mobile.ExtensionMetric;
import logs.proto.wireless.performance.mobile.SystemHealthProto;

final class BatteryMetricService extends AbstractMetricService implements PrimesStartupListener, AppLifecycleListener.OnAppToForeground, AppLifecycleListener.OnAppToBackground {
    static final int MAX_CONCURRENT_MEASUREMENTS = 10;
    static final String TAG = "BatteryMetricService";
    @VisibleForTesting
    final AtomicBoolean inForeground = new AtomicBoolean();
    @VisibleForTesting
    final ConcurrentHashMap<String, PrimesBatterySnapshot> startSnapshots = new ConcurrentHashMap<>();
    private final BatteryCapture batteryCapture;
    private final List<Future<BatteryCapture.Snapshot>> batteryCaptures;
    private final boolean logDeferred;
    private final Object monitorMutex = new Object();
    private final StatsStorage storage;
    private volatile boolean monitoring = false;

    @VisibleForTesting
    BatteryMetricService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier, SharedPreferences sharedPreferences, BatteryCapture batteryCapture2, boolean logDeferred2) {
        super(transmitter, application, metricStamperSupplier, executorServiceSupplier, MetricRecorder.RunIn.SAME_THREAD);
        this.storage = new StatsStorage(sharedPreferences);
        this.batteryCapture = batteryCapture2;
        this.logDeferred = logDeferred2;
        this.batteryCaptures = logDeferred2 ? new ArrayList() : null;
    }

    static BatteryMetricService createService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier, SharedPreferences sharedPreferences, Optional<PrimesBatteryConfigurations> optionalConfigs) {
        PrimesBatteryConfigurations configs = optionalConfigs.mo22987or(PrimesBatteryConfigurations.newBuilder().build());
        return new BatteryMetricService(transmitter, application, metricStamperSupplier, executorServiceSupplier, sharedPreferences, new BatteryCapture(metricStamperSupplier, new SystemHealthCapture(application), BatteryMetricService$$Lambda$0.$instance, BatteryMetricService$$Lambda$1.$instance, configs.getMetricExtensionProvider()), configs.isDeferredLogging());
    }

    public void onPrimesInitialize() {
    }

    public void onFirstActivityCreated() {
        if (!this.inForeground.get()) {
            onAppToForeground(null);
        }
        startMonitoring();
    }

    public void onAppToForeground(Activity activity) {
        PrimesExecutors.handleFuture(onAppToForeground());
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public Future<?> onAppToForeground() {
        if (!this.inForeground.getAndSet(true)) {
            return scheduleCapture(BatteryMetric.BatteryStatsDiff.SampleInfo.BACKGROUND_TO_FOREGROUND);
        }
        PrimesLog.m54w(TAG, "unexpected state onAppToForeground", new Object[0]);
        return null;
    }

    public void onAppToBackground(Activity activity) {
        PrimesExecutors.handleFuture(onAppToBackground());
    }

    /* access modifiers changed from: package-private */
    public Future<?> onAppToBackground() {
        if (this.inForeground.getAndSet(false)) {
            return scheduleCapture(BatteryMetric.BatteryStatsDiff.SampleInfo.FOREGROUND_TO_BACKGROUND);
        }
        PrimesLog.m54w(TAG, "unexpected state onAppToBackground", new Object[0]);
        return null;
    }

    /* access modifiers changed from: package-private */
    public Future<?> onForegroundServiceStarted() {
        return scheduleCapture(BatteryMetric.BatteryStatsDiff.SampleInfo.FOREGROUND_SERVICE_START);
    }

    /* access modifiers changed from: package-private */
    public Future<?> onForegroundServiceStopped() {
        return scheduleCapture(BatteryMetric.BatteryStatsDiff.SampleInfo.FOREGROUND_SERVICE_STOP);
    }

    /* access modifiers changed from: package-private */
    public Future<?> scheduleManualCapture(@Nullable String customEventName, boolean isEventNameConstant) {
        return scheduleCapture(BatteryMetric.BatteryStatsDiff.SampleInfo.UNKNOWN, customEventName, isEventNameConstant);
    }

    /* access modifiers changed from: package-private */
    public void startBatteryDiffMeasurement(String customEventName, boolean isEventNameConstant) {
        if (this.startSnapshots.size() < 10) {
            this.startSnapshots.put(customEventName, new PrimesBatterySnapshot(captureCustomDiffSnapshot(BatteryMetric.BatteryStatsDiff.SampleInfo.CUSTOM_MEASURE_START, customEventName, isEventNameConstant)));
        }
    }

    /* access modifiers changed from: package-private */
    public void stopBatteryDiffMeasurement(String customEventName, boolean isEventNameConstant, ExtensionMetric.MetricExtension metricExtension) {
        PrimesBatterySnapshot startSnapshot = this.startSnapshots.remove(customEventName);
        if (startSnapshot != null) {
            recordBatteryStatsDiff(startSnapshot, new PrimesBatterySnapshot(captureCustomDiffSnapshot(BatteryMetric.BatteryStatsDiff.SampleInfo.CUSTOM_MEASURE_STOP, customEventName, isEventNameConstant)), customEventName, isEventNameConstant, metricExtension);
            return;
        }
        String valueOf = String.valueOf(customEventName);
        PrimesLog.m54w(TAG, valueOf.length() != 0 ? "startBatteryDiffMeasurement() failed for customEventName ".concat(valueOf) : new String("startBatteryDiffMeasurement() failed for customEventName "), new Object[0]);
    }

    /* access modifiers changed from: package-private */
    public void cancelBatteryDiffMeasurement(String customEventName) {
        if (this.startSnapshots.remove(customEventName) != null) {
            String valueOf = String.valueOf(customEventName);
            PrimesLog.m54w(TAG, valueOf.length() != 0 ? "Cancel battery diff measurement for customEventName ".concat(valueOf) : new String("Cancel battery diff measurement for customEventName "), new Object[0]);
        }
    }

    private Future<BatteryCapture.Snapshot> captureCustomDiffSnapshot(BatteryMetric.BatteryStatsDiff.SampleInfo sampleInfo, @Nullable String customEventName, boolean isEventNameConstant) {
        return getScheduledExecutorService().submit(new BatteryMetricService$$Lambda$2(this, sampleInfo, customEventName, isEventNameConstant));
    }

    /* access modifiers changed from: package-private */
    public void recordBatteryStatsDiff(PrimesBatterySnapshot startSnapshot, PrimesBatterySnapshot endSnapshot, String customEventName, boolean isEventNameConstant, ExtensionMetric.MetricExtension metricExtension) {
        if (!startSnapshot.isEmptySnapshot() && !endSnapshot.isEmptySnapshot()) {
            PrimesExecutors.handleFuture(getScheduledExecutorService().submit(new BatteryMetricService$$Lambda$3(this, startSnapshot, endSnapshot, customEventName, isEventNameConstant, metricExtension)));
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$recordBatteryStatsDiff$1$BatteryMetricService(PrimesBatterySnapshot startSnapshot, PrimesBatterySnapshot endSnapshot, String customEventName, boolean isEventNameConstant, ExtensionMetric.MetricExtension metricExtension) {
        if (startSnapshot.getBatterySnapshot() != null && endSnapshot.getBatterySnapshot() != null) {
            SystemHealthProto.SystemHealthMetric metric = this.batteryCapture.createBatteryMetric(startSnapshot.getBatterySnapshot().toStatsRecord(), endSnapshot.getBatterySnapshot().toStatsRecord());
            if (metric == null || !metric.hasBatteryUsageMetric()) {
                PrimesLog.m54w(TAG, "at least one battery snapshot failed", new Object[0]);
            } else {
                recordSystemHealthMetric(customEventName, isEventNameConstant, metric, metricExtension);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void startMonitoring() {
        synchronized (this.monitorMutex) {
            if (!this.monitoring) {
                AppLifecycleMonitor.getInstance(getApplication()).register(this);
                this.monitoring = true;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void stopMonitoring() {
        synchronized (this.monitorMutex) {
            if (this.monitoring) {
                AppLifecycleMonitor.getInstance(getApplication()).unregister(this);
                this.monitoring = false;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void shutdownService() {
        stopMonitoring();
        synchronized (this.storage) {
            this.storage.clear();
        }
    }

    /* access modifiers changed from: package-private */
    public StatsStorage.StatsRecord fromStorage() {
        StatsStorage.StatsRecord readStatsRecord;
        ThreadUtil.ensureBackgroundThread();
        synchronized (this.storage) {
            readStatsRecord = this.storage.readStatsRecord();
        }
        return readStatsRecord;
    }

    /* access modifiers changed from: package-private */
    public boolean toStorage(StatsStorage.StatsRecord statsRecord) {
        boolean writeStatsRecord;
        ThreadUtil.ensureBackgroundThread();
        synchronized (this.storage) {
            writeStatsRecord = this.storage.writeStatsRecord(statsRecord);
        }
        return writeStatsRecord;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: captureBattery */
    public BatteryCapture.Snapshot lambda$captureForDeferredLogging$2$BatteryMetricService(BatteryMetric.BatteryStatsDiff.SampleInfo sampleInfo, @Nullable String customEventName, boolean isEventNameConstant) {
        return this.batteryCapture.takeSnapshot(sampleInfo, customEventName, isEventNameConstant);
    }

    /* access modifiers changed from: package-private */
    public void log(StatsStorage.StatsRecord start, StatsStorage.StatsRecord end) {
        PrimesLog.m52v(TAG, "log start: %s\nend: %s", start, end);
        SystemHealthProto.SystemHealthMetric metric = this.batteryCapture.createBatteryMetric(start, end);
        if (metric != null) {
            recordSystemHealthMetric(end.getCustomEventName(), end.isEventNameConstant().booleanValue(), metric, end.getMetricExtension());
        }
    }

    /* access modifiers changed from: package-private */
    public Future<?> scheduleCapture(BatteryMetric.BatteryStatsDiff.SampleInfo sampleInfo) {
        return scheduleCapture(sampleInfo, null, true);
    }

    private Future<?> scheduleCapture(BatteryMetric.BatteryStatsDiff.SampleInfo sampleInfo, @Nullable String customEventName, boolean isEventNameConstant) {
        if (this.logDeferred) {
            return captureForDeferredLogging(sampleInfo, customEventName, isEventNameConstant);
        }
        return captureAndLogInstantaneous(sampleInfo, customEventName, isEventNameConstant);
    }

    private Future<?> captureForDeferredLogging(BatteryMetric.BatteryStatsDiff.SampleInfo sampleInfo, @Nullable String customEventName, boolean isEventNameConstant) {
        Future<BatteryCapture.Snapshot> future = getScheduledExecutorService().submit(new BatteryMetricService$$Lambda$4(this, sampleInfo, customEventName, isEventNameConstant));
        PrimesLog.m46d(TAG, "adding future BatteryCapture", new Object[0]);
        synchronized (this.batteryCaptures) {
            this.batteryCaptures.add(future);
            if (this.inForeground.get()) {
                return future;
            }
            Future<?> logDeferredData = logDeferredData();
            return logDeferredData;
        }
    }

    private Future<?> logDeferredData() {
        List<Future<BatteryCapture.Snapshot>> captures;
        synchronized (this.batteryCaptures) {
            captures = new ArrayList<>(this.batteryCaptures);
            this.batteryCaptures.clear();
        }
        PrimesLog.m50i(TAG, "Logging captures: %d", Integer.valueOf(captures.size()));
        return getScheduledExecutorService().submit(new BatteryMetricService$$Lambda$5(this, captures));
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$logDeferredData$3$BatteryMetricService(List captures) {
        StatsStorage.StatsRecord endRecord = fromStorage();
        Iterator it = captures.iterator();
        while (it.hasNext()) {
            try {
                StatsStorage.StatsRecord startRecord = endRecord;
                endRecord = ((BatteryCapture.Snapshot) ((Future) it.next()).get()).toStatsRecord();
                if (startRecord != null) {
                    log(startRecord, endRecord);
                }
            } catch (Exception e) {
                PrimesLog.m47e(TAG, "unpexpected failure", e, new Object[0]);
            }
        }
        toStorage(endRecord);
    }

    private Future<?> captureAndLogInstantaneous(BatteryMetric.BatteryStatsDiff.SampleInfo sampleInfo, @Nullable String customEventName, boolean isEventNameConstant) {
        return getScheduledExecutorService().submit(new BatteryMetricService$$Lambda$6(this, sampleInfo, customEventName, isEventNameConstant));
    }

    /* access modifiers changed from: private */
    /* renamed from: captureAndLogLocked */
    public void lambda$captureAndLogInstantaneous$4$BatteryMetricService(BatteryMetric.BatteryStatsDiff.SampleInfo sampleInfo, @Nullable String customEventName, boolean isEventNameConstant) {
        ThreadUtil.ensureBackgroundThread();
        if (!isShutdown()) {
            synchronized (this.storage) {
                StatsStorage.StatsRecord end = lambda$captureForDeferredLogging$2$BatteryMetricService(sampleInfo, customEventName, isEventNameConstant).toStatsRecord();
                StatsStorage.StatsRecord start = fromStorage();
                if (toStorage(end)) {
                    log(start, end);
                } else {
                    shutdownService();
                    PrimesLog.m54w(TAG, "Failure storing persistent snapshot and helper data", new Object[0]);
                }
            }
            return;
        }
        PrimesLog.m46d(TAG, "shutdown - skipping capture", new Object[0]);
    }
}
