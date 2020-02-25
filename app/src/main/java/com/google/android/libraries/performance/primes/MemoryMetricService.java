package com.google.android.libraries.performance.primes;

import android.app.ActivityManager;
import android.app.Application;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.google.android.libraries.performance.primes.metriccapture.MemoryUsageCapture;
import com.google.android.libraries.performance.primes.metriccapture.ProcessStats;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicReference;

import logs.proto.wireless.performance.mobile.ExtensionMetric;
import logs.proto.wireless.performance.mobile.MemoryMetric;
import logs.proto.wireless.performance.mobile.SystemHealthProto;

final class MemoryMetricService extends AbstractMetricService {
    @VisibleForTesting
    static final int MAX_CONCURRENT_MEASUREMENTS = 10;
    private static final long MAX_SNAPSHOT_AGE = 1000;
    private static final String TAG = "MemoryMetricService";
    /* access modifiers changed from: private */
    public final boolean memorySummaryDisabled;
    /* access modifiers changed from: private */
    public final MemoryMetricExtensionProvider metricExtensionProvider;
    /* access modifiers changed from: private */
    public final boolean recordMemoryPerProcess;
    @VisibleForTesting
    final AtomicReference<MemoryEvent> lastSnapshot = new AtomicReference<>(null);
    @VisibleForTesting
    final ConcurrentHashMap<String, MemoryEvent> startSnapshots = new ConcurrentHashMap<>();
    private final boolean forceGcBeforeRecordMemory;
    private final TimeCapture timeCapture;
    private MemoryMetricMonitor metricMonitor;

    @VisibleForTesting
    MemoryMetricService(TimeCapture timeCapture2, MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier, int sampleRate, boolean recordMemoryPerProcess2, MemoryMetricExtensionProvider metricExtensionProvider2, boolean forceGcBeforeRecordMemory2, boolean memorySummaryDisabled2) {
        super(transmitter, application, metricStamperSupplier, executorServiceSupplier, MetricRecorder.RunIn.SAME_THREAD, sampleRate);
        this.timeCapture = timeCapture2;
        this.recordMemoryPerProcess = recordMemoryPerProcess2;
        this.metricExtensionProvider = metricExtensionProvider2;
        this.forceGcBeforeRecordMemory = forceGcBeforeRecordMemory2;
        this.memorySummaryDisabled = memorySummaryDisabled2;
    }

    static MemoryMetricService createService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier, PrimesMemoryConfigurations configs, boolean memorySummaryDisabled2) {
        return new MemoryMetricService(new TimeCapture() {
            public long getTime() {
                return SystemClock.elapsedRealtime();
            }
        }, transmitter, application, metricStamperSupplier, executorServiceSupplier, configs.getSampleRatePerSecond(), configs.recordMetricPerProcess(), configs.getMetricExtensionProvider().orNull(), configs.getForceGcBeforeRecordMemory(), memorySummaryDisabled2);
    }

    /* access modifiers changed from: package-private */
    public synchronized void startMonitoring() {
        if (!isShutdown() && this.metricMonitor == null) {
            this.metricMonitor = new MemoryMetricMonitor(new MemoryMetricMonitor.Callback() {
                public void onEvent(MemoryMetric.MemoryUsageMetric.MemoryEventCode eventCode, String activityName) {
                    MemoryMetricService.this.recordEvent(null, false, eventCode, activityName, null);
                }
            }, getApplication(), getScheduledExecutorSupplier());
            this.metricMonitor.start();
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void shutdownService() {
        if (this.metricMonitor != null) {
            this.metricMonitor.stop();
            this.metricMonitor = null;
        }
    }

    /* access modifiers changed from: package-private */
    public Future<MemoryMetric.MemoryUsageMetric> scheduleSnapshot() {
        return getScheduledExecutorService().submit(new Callable<MemoryMetric.MemoryUsageMetric>() {
            public MemoryMetric.MemoryUsageMetric call() throws Exception {
                return MemoryUsageCapture.getMemoryUsageMetric(MemoryMetric.MemoryUsageMetric.MemoryEventCode.UNKNOWN, MemoryMetricService.this.getApplication(), null, MemoryMetricService.this.memorySummaryDisabled);
            }
        });
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean isRecent(MemoryEvent snapshot) {
        return this.timeCapture.getTime() - snapshot.getCreateTimestamp() <= 1000;
    }

    /* access modifiers changed from: package-private */
    public MemoryEvent getMemorySnapshot() {
        MemoryEvent memoryEvent;
        MemoryEvent prevSnapshot = this.lastSnapshot.get();
        if (prevSnapshot != null && prevSnapshot != MemoryEvent.EMPTY_SNAPSHOT && isRecent(prevSnapshot)) {
            return prevSnapshot;
        }
        synchronized (this.lastSnapshot) {
            if (this.lastSnapshot.get() == prevSnapshot) {
                this.lastSnapshot.set(new MemoryEvent(this.timeCapture.getTime(), scheduleSnapshot()));
            }
            memoryEvent = this.lastSnapshot.get();
        }
        return memoryEvent;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void recordMemoryDiff(MemoryEvent startSnapshot, MemoryEvent endSnapshot, String customEventName, boolean isEventNameConstant, @Nullable ExtensionMetric.MetricExtension metricExtension) {
        if (!startSnapshot.isEmptySnapshot() && !endSnapshot.isEmptySnapshot()) {
            final MemoryEvent memoryEvent = startSnapshot;
            final MemoryEvent memoryEvent2 = endSnapshot;
            final String str = customEventName;
            final boolean z = isEventNameConstant;
            final ExtensionMetric.MetricExtension metricExtension2 = metricExtension;
            PrimesExecutors.handleFuture(getScheduledExecutorService().submit(new Runnable() {
                public void run() {
                    if (memoryEvent.getMemoryUsageMetric() != null) {
                        memoryEvent2.getMemoryUsageMetric();
                    }
                    MemoryMetric.MemoryUsageMetric.Builder memoryUsageMetric = MemoryEvent.diff(memoryEvent.getMemoryUsageMetric(), memoryEvent2.getMemoryUsageMetric());
                    if (memoryUsageMetric != null) {
                        memoryUsageMetric.setMemoryEventCode(MemoryMetric.MemoryUsageMetric.MemoryEventCode.DELTA_OF_MEMORY);
                        MemoryMetricService.this.recordSystemHealthMetric(str, z, (SystemHealthProto.SystemHealthMetric) SystemHealthProto.SystemHealthMetric.newBuilder().setMemoryUsageMetric(memoryUsageMetric).build(), metricExtension2);
                        return;
                    }
                    PrimesLog.m54w(MemoryMetricService.TAG, "at least one memory snapshot failed", new Object[0]);
                }
            }));
        }
    }

    /* access modifiers changed from: package-private */
    public void startMemoryDiffMeasurement(String customEventName) {
        if (this.startSnapshots.size() < 10) {
            this.startSnapshots.put(customEventName, getMemorySnapshot());
            PrimesLog.m46d(TAG, "Starting memory diff measurement for custom event %s", customEventName);
            return;
        }
        PrimesLog.m54w(TAG, "Memory diff measurement for custom event %s not started", customEventName);
    }

    /* access modifiers changed from: package-private */
    public void stopMemoryDiffMeasurement(String customEventName, boolean isEventNameConstant, @Nullable ExtensionMetric.MetricExtension metricExtension) {
        MemoryEvent start = this.startSnapshots.remove(customEventName);
        if (start == null || start == MemoryEvent.EMPTY_SNAPSHOT) {
            PrimesLog.m54w(TAG, "Could not find the start memory diff measurement for custom event %s", customEventName);
            return;
        }
        recordMemoryDiff(start, getMemorySnapshot(), customEventName, isEventNameConstant, metricExtension);
        PrimesLog.m46d(TAG, "Stopping memory diff measurement for custom event %s", customEventName);
    }

    /* access modifiers changed from: package-private */
    public void cancelMemoryDiffMeasurement(String customEventName) {
        if (this.startSnapshots.remove(customEventName) != null) {
            PrimesLog.m54w(TAG, "Cancel memory diff measurement for custom event %s", customEventName);
        }
    }

    /* access modifiers changed from: package-private */
    public void recordEvent(String customEventName, boolean isEventNameConstant, @Nullable ExtensionMetric.MetricExtension metricExtension) {
        recordEvent(customEventName, isEventNameConstant, MemoryMetric.MemoryUsageMetric.MemoryEventCode.UNKNOWN, null, metricExtension);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void recordEvent(String customEventName, boolean isEventNameConstant, MemoryMetric.MemoryUsageMetric.MemoryEventCode eventCode, String activityName, @Nullable ExtensionMetric.MetricExtension metricExtension) {
        if (shouldRecord()) {
            final ExtensionMetric.MetricExtension metricExtension2 = metricExtension;
            final String str = customEventName;
            final MemoryMetric.MemoryUsageMetric.MemoryEventCode memoryEventCode = eventCode;
            final boolean z = isEventNameConstant;
            final String str2 = activityName;
            PrimesExecutors.handleFuture(getScheduledExecutorService().submit(new Runnable() {
                public void run() {
                    MemoryMetricService.this.forceGcBeforeRecordMemoryIfConfigured();
                    ExtensionMetric.MetricExtension recordedExtension = metricExtension2;
                    if (metricExtension2 == null && MemoryMetricService.this.metricExtensionProvider != null) {
                        try {
                            recordedExtension = MemoryMetricService.this.metricExtensionProvider.getMetricExtension(str, memoryEventCode);
                        } catch (RuntimeException e) {
                            PrimesLog.m48e(MemoryMetricService.TAG, "Metric extension provider failed.", new Object[0]);
                        }
                    }
                    if (MemoryMetricService.this.recordMemoryPerProcess) {
                        MemoryMetricService.this.recordMemoryPerProcess(str, z, memoryEventCode, str2, recordedExtension);
                    } else {
                        MemoryMetricService.this.recordMemory(str, z, memoryEventCode, str2, recordedExtension);
                    }
                }
            }));
        }
    }

    /* access modifiers changed from: private */
    public void forceGcBeforeRecordMemoryIfConfigured() {
        if (this.forceGcBeforeRecordMemory) {
            System.gc();
            System.runFinalization();
            System.gc();
        }
    }

    /* access modifiers changed from: private */
    public void recordMemory(String customEventName, boolean isEventNameConstant, MemoryMetric.MemoryUsageMetric.MemoryEventCode eventCode, String activityName, @Nullable ExtensionMetric.MetricExtension metricExtension) {
        SystemHealthProto.SystemHealthMetric.Builder metric = SystemHealthProto.SystemHealthMetric.newBuilder();
        metric.setMemoryUsageMetric(MemoryUsageCapture.getMemoryUsageMetric(eventCode, getApplication(), activityName, this.memorySummaryDisabled));
        recordSystemHealthMetric(customEventName, isEventNameConstant, (SystemHealthProto.SystemHealthMetric) metric.build(), metricExtension);
    }

    /* access modifiers changed from: private */
    public void recordMemoryPerProcess(String customEventName, boolean isEventNameConstant, MemoryMetric.MemoryUsageMetric.MemoryEventCode eventCode, String activityName, @Nullable ExtensionMetric.MetricExtension metricExtension) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ProcessStats.getActivityManager(getApplication()).getRunningAppProcesses();
        if (runningAppProcesses != null) {
            String packageName = getApplication().getPackageName();
            for (ActivityManager.RunningAppProcessInfo info : runningAppProcesses) {
                if (Build.VERSION.SDK_INT > 22 || info.processName.contains(packageName)) {
                    SystemHealthProto.SystemHealthMetric.Builder metric = SystemHealthProto.SystemHealthMetric.newBuilder();
                    metric.setMemoryUsageMetric(MemoryUsageCapture.getMemoryUsageMetric(eventCode, info.pid, info.processName, getApplication(), activityName, this.memorySummaryDisabled));
                    recordSystemHealthMetric(customEventName, isEventNameConstant, (SystemHealthProto.SystemHealthMetric) metric.build(), metricExtension);
                }
            }
        }
    }

    public interface TimeCapture {
        long getTime();
    }
}
