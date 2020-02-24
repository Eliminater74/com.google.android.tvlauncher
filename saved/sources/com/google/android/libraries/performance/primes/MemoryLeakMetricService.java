package com.google.android.libraries.performance.primes;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.VisibleForTesting;
import com.google.android.libraries.performance.primes.AppLifecycleListener;
import com.google.android.libraries.performance.primes.MetricRecorder;
import com.google.android.libraries.performance.primes.hprof.PrimesHprofs;
import com.google.android.libraries.performance.primes.leak.LeakInfo;
import com.google.android.libraries.performance.primes.leak.LeakListener;
import com.google.android.libraries.performance.primes.leak.LeakWatcher;
import com.google.android.libraries.performance.primes.metriccapture.TimeCapture;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import com.google.android.libraries.stitch.util.Preconditions;
import com.google.common.base.Optional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import logs.proto.wireless.performance.mobile.SystemHealthProto;

final class MemoryLeakMetricService extends AbstractMetricService implements AppLifecycleListener.OnActivityDestroyed {
    private static final String DEV_HEAP_DUMP_NOWAIT = "primes.dev.heapdump_nowait";
    static final int DUMP_DELAY_AFTER_SCREEN_OFF = 5;
    static final long MIN_HEAP_DUMP_INTERVAL = 43200000;
    private static final String TAG = "MemoryLeakService";
    private final AppLifecycleMonitor appLifecycleMonitor;
    /* access modifiers changed from: private */
    public final Application application;
    /* access modifiers changed from: private */
    public ScheduledFuture<?> dumpFutureTask;
    /* access modifiers changed from: private */
    public final AtomicBoolean dumpScheduled = new AtomicBoolean();
    /* access modifiers changed from: private */
    public final Supplier<ScheduledExecutorService> executorServiceSupplier;
    /* access modifiers changed from: private */
    public final boolean heapDumpEligible;
    /* access modifiers changed from: private */
    public final boolean heapDumpEnabled;
    /* access modifiers changed from: private */
    public final AtomicLong lastSent = new AtomicLong();
    /* access modifiers changed from: private */
    public final boolean leakDetectionV2Enabled;
    /* access modifiers changed from: private */
    public final LeakWatcher leakWatcher;

    @VisibleForTesting
    MemoryLeakMetricService(Application application2, boolean leakDetectionV2Enabled2, boolean heapDumpEnabled2, AppLifecycleMonitor appLifecycleMonitor2, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier2, LeakWatcher leakWatcher2, MetricTransmitter metricTransmitter) {
        super(metricTransmitter, application2, metricStamperSupplier, executorServiceSupplier2, MetricRecorder.RunIn.BACKGROUND_THREAD);
        this.application = (Application) Preconditions.checkNotNull(application2);
        this.leakDetectionV2Enabled = leakDetectionV2Enabled2;
        this.heapDumpEnabled = heapDumpEnabled2;
        this.appLifecycleMonitor = (AppLifecycleMonitor) Preconditions.checkNotNull(appLifecycleMonitor2);
        this.executorServiceSupplier = (Supplier) Preconditions.checkNotNull(executorServiceSupplier2);
        this.leakWatcher = (LeakWatcher) Preconditions.checkNotNull(leakWatcher2);
        this.leakWatcher.setLeakListener(new PrimesLeakListener());
        this.heapDumpEligible = PrimesHprofs.isHeapDumpEligible(application2);
    }

    static MemoryLeakMetricService createService(MetricTransmitter metricTransmitter, Application application2, boolean leakDetectionV2Enabled2, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier2, Optional<PrimesMemoryLeakConfigurations> optionalConfigs, AppLifecycleMonitor appLifecycleMonitor2) {
        PrimesMemoryLeakConfigurations configs = optionalConfigs.mo22987or((PrimesBatteryConfigurations) new PrimesMemoryLeakConfigurations(false));
        return new MemoryLeakMetricService(application2, leakDetectionV2Enabled2, configs.isHeapDumpEnabled(), appLifecycleMonitor2, metricStamperSupplier, executorServiceSupplier2, new LeakWatcher(configs.isQuantifyLeakSizeEnabled()), metricTransmitter);
    }

    /* access modifiers changed from: package-private */
    public void shutdownService() {
        this.appLifecycleMonitor.unregister(this);
        this.leakWatcher.stop();
        cancelDumpTaskIfAny();
    }

    public void startMonitoring() {
        synchronized (this) {
            this.leakWatcher.start();
            this.appLifecycleMonitor.register(this);
        }
    }

    public void watchForMemoryLeak(Object object) {
        Preconditions.checkNotNull(object);
        this.leakWatcher.watch(object, object.getClass().getName());
    }

    public void onActivityDestroyed(Activity activity) {
        if (!isShutdown()) {
            this.leakWatcher.watch(activity, activity.getClass().getName());
        }
    }

    private final class PrimesLeakListener implements LeakListener {
        private final Map<String, LeakCounter> stats;

        private PrimesLeakListener() {
            this.stats = new HashMap();
        }

        public void onReleased(String name) {
            LeakCounter leakCounter = this.stats.get(name);
            if (leakCounter == null) {
                leakCounter = new LeakCounter();
                this.stats.put(name, leakCounter);
            }
            LeakCounter.access$208(leakCounter);
        }

        public void onLeaked(String name) {
            LeakCounter leakCounter = this.stats.get(name);
            if (leakCounter == null) {
                leakCounter = new LeakCounter();
                this.stats.put(name, leakCounter);
            }
            LeakCounter.access$308(leakCounter);
        }

        public void onBatchComplete(boolean leakFound) {
            SystemHealthProto.MemoryLeakMetric.Builder memoryLeakMetric = SystemHealthProto.MemoryLeakMetric.newBuilder();
            for (Map.Entry<String, LeakCounter> entry : this.stats.entrySet()) {
                String name = (String) entry.getKey();
                LeakCounter leakCounter = (LeakCounter) entry.getValue();
                if (leakCounter.leaked > 0 || leakCounter.released > 0) {
                    memoryLeakMetric.addObjectInfo(SystemHealthProto.ObjectInfo.newBuilder().setClassName(name).setLeakedCount(leakCounter.leaked).setReleasedCount(leakCounter.released));
                    int unused = leakCounter.leaked = 0;
                    int unused2 = leakCounter.released = 0;
                }
            }
            if (memoryLeakMetric.getObjectInfoCount() != 0) {
                SystemHealthProto.SystemHealthMetric metric = (SystemHealthProto.SystemHealthMetric) SystemHealthProto.SystemHealthMetric.newBuilder().setMemoryLeakMetric(memoryLeakMetric).build();
                if (MemoryLeakMetricService.this.shouldRecord()) {
                    MemoryLeakMetricService.this.recordSystemHealthMetric(metric);
                }
            }
            if (leakFound && satisfyDumpingRequirements()) {
                scheduleHprofDump();
            }
        }

        private boolean satisfyDumpingRequirements() {
            if (!MemoryLeakMetricService.this.heapDumpEligible || MemoryLeakMetricService.this.isShutdown() || (!MemoryLeakMetricService.this.heapDumpEnabled && !MemoryLeakMetricService.this.leakDetectionV2Enabled)) {
                return false;
            }
            long lastSentTime = MemoryLeakMetricService.this.lastSent.get();
            if (lastSentTime == 0 || MemoryLeakMetricService.MIN_HEAP_DUMP_INTERVAL + lastSentTime <= TimeCapture.getTime()) {
                return true;
            }
            return false;
        }

        private void scheduleHprofDump() {
            if (MemoryLeakMetricService.this.dumpScheduled.compareAndSet(false, true)) {
                PrimesLog.m46d(MemoryLeakMetricService.TAG, "Scheduling heap dump %d seconds after the next screen off.", 5);
                IntentFilter filter = new IntentFilter("android.intent.action.SCREEN_OFF");
                filter.addAction("android.intent.action.SCREEN_ON");
                MemoryLeakMetricService.this.application.registerReceiver(new ScreenOnOffReceiver(), filter);
            }
        }

        public void onHeapDumpResult(List<LeakInfo> leaks) {
            SystemHealthProto.MemoryLeakMetric.Builder memoryLeakMetric = SystemHealthProto.MemoryLeakMetric.newBuilder();
            for (LeakInfo leakInfo : leaks) {
                String leak = leakInfo.getPath();
                int newlineIndex = leak.indexOf(10);
                memoryLeakMetric.addObjectInfo(SystemHealthProto.ObjectInfo.newBuilder().setClassName(newlineIndex < 0 ? leak : leak.substring(0, newlineIndex)).setLeakPath(leak).setRetainedHeapBytes(leakInfo.getRetainedHeapSizeBytes()).setLeakedCount(1));
            }
            if (memoryLeakMetric.getObjectInfoCount() != 0) {
                SystemHealthProto.SystemHealthMetric metric = (SystemHealthProto.SystemHealthMetric) SystemHealthProto.SystemHealthMetric.newBuilder().setMemoryLeakMetric(memoryLeakMetric).build();
                if (MemoryLeakMetricService.this.shouldRecord()) {
                    MemoryLeakMetricService.this.recordSystemHealthMetric(metric);
                }
            }
            if (!leaks.isEmpty()) {
                PrimesLog.m52v(MemoryLeakMetricService.TAG, "Primes found %d leak(s): %s", Integer.valueOf(leaks.size()), leaks);
            }
        }
    }

    private static class LeakCounter {
        /* access modifiers changed from: private */
        public int leaked;
        /* access modifiers changed from: private */
        public int released;

        private LeakCounter() {
        }

        static /* synthetic */ int access$208(LeakCounter x0) {
            int i = x0.released;
            x0.released = i + 1;
            return i;
        }

        static /* synthetic */ int access$308(LeakCounter x0) {
            int i = x0.leaked;
            x0.leaked = i + 1;
            return i;
        }
    }

    /* access modifiers changed from: private */
    public void cancelDumpTaskIfAny() {
        ScheduledFuture<?> scheduledFuture = this.dumpFutureTask;
        if (scheduledFuture != null) {
            if (!scheduledFuture.isDone()) {
                this.dumpFutureTask.cancel(true);
            }
            this.dumpFutureTask = null;
        }
    }

    @VisibleForTesting
    final class ScreenOnOffReceiver extends BroadcastReceiver {
        ScreenOnOffReceiver() {
        }

        public void onReceive(final Context context, Intent intent) {
            if (!"android.intent.action.SCREEN_ON".equals(intent.getAction())) {
                MemoryLeakMetricService.this.cancelDumpTaskIfAny();
                MemoryLeakMetricService memoryLeakMetricService = MemoryLeakMetricService.this;
                ScheduledFuture unused = memoryLeakMetricService.dumpFutureTask = ((ScheduledExecutorService) memoryLeakMetricService.executorServiceSupplier.get()).schedule(new Runnable() {
                    public void run() {
                        if (MemoryLeakMetricService.this.dumpScheduled.compareAndSet(true, false)) {
                            context.unregisterReceiver(ScreenOnOffReceiver.this);
                            MemoryLeakMetricService.this.lastSent.set(TimeCapture.getTime());
                            MemoryLeakMetricService.this.leakWatcher.scheduleHeapDumpAndAnalysis(PrimesHprofFile.getHprofFile(context));
                        }
                    }
                }, 5, TimeUnit.SECONDS);
            } else if (MemoryLeakMetricService.this.dumpScheduled.get()) {
                MemoryLeakMetricService.this.cancelDumpTaskIfAny();
            }
        }
    }
}
