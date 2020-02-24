package com.google.android.libraries.performance.primes;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Debug;
import android.support.annotation.VisibleForTesting;
import com.google.android.libraries.performance.primes.AppLifecycleListener;
import com.google.android.libraries.performance.primes.MetricRecorder;
import com.google.android.libraries.performance.primes.backgroundjobs.PrimesJobScheduler;
import com.google.android.libraries.performance.primes.hprof.HprofSerializer;
import com.google.android.libraries.performance.primes.metriccapture.MemoryUsageCapture;
import com.google.android.libraries.performance.primes.metriccapture.TimeCapture;
import com.google.android.libraries.performance.primes.miniheapdump.MiniHeapDumpMemorySampler;
import com.google.android.libraries.performance.primes.miniheapdump.SerializedMiniHeapDumpFile;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import com.google.android.libraries.stitch.util.Preconditions;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.InvalidProtocolBufferException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import logs.proto.wireless.performance.mobile.PrimesHeapDumpProto;
import logs.proto.wireless.performance.mobile.SystemHealthProto;

@TargetApi(23)
final class MiniHeapDumpMetricService extends AbstractMetricService {
    private static final String DEV_HEAP_DUMP_NOSEND = "primes.dev.heapdump_nosend";
    private static final String DEV_HEAP_DUMP_NOWAIT = "primes.dev.heapdump_nowait";
    @VisibleForTesting
    static final String IS_CALIBRATED_PREFERENCE_KEY = "primes.miniheapdump.isCalibrated";
    static final int MEMORY_COLLECTION_DELAY_IN_SECONDS = 10;
    static final long MIN_HEAP_DUMP_INTERVAL_MS = TimeUnit.DAYS.toMillis(1);
    private static final String TAG = "MiniHeapDumpMetric";
    private static volatile MiniHeapDumpMetricService service;
    /* access modifiers changed from: private */
    public final AppLifecycleMonitor appLifecycleMonitor;
    /* access modifiers changed from: private */
    public final Supplier<ScheduledExecutorService> executorServiceSupplier;
    /* access modifiers changed from: private */
    public volatile ScheduledFuture<?> futureMemoryCollectionTask;
    private final ReentrantLock heapDumpLock = new ReentrantLock();
    private final HprofSerializer hprofSerializer;
    @VisibleForTesting
    final AtomicLong lastSent = new AtomicLong();
    /* access modifiers changed from: private */
    public final AppLifecycleListener.OnAppToBackground logTotalPssSampleCount = new AppLifecycleListener.OnAppToBackground() {
        public void onAppToBackground(Activity activity) {
            PrimesExecutors.handleFuture(((ScheduledExecutorService) MiniHeapDumpMetricService.this.executorServiceSupplier.get()).submit(new Runnable() {
                public void run() {
                    MiniHeapDumpMetricService.this.appLifecycleMonitor.unregister(MiniHeapDumpMetricService.this.logTotalPssSampleCount);
                    if (!MiniHeapDumpMetricService.this.preferences.getBoolean(MiniHeapDumpMetricService.IS_CALIBRATED_PREFERENCE_KEY, false)) {
                        PrimesLog.m46d(MiniHeapDumpMetricService.TAG, "Logging calibration status", new Object[0]);
                        MiniHeapDumpMetricService.this.recordStatus((SystemHealthProto.PrimesHeapDumpCalibrationStatus) SystemHealthProto.PrimesHeapDumpCalibrationStatus.newBuilder().setCurrentSampleCount(MiniHeapDumpMetricService.this.miniHeapDumpSampler.getSamples().size()).build());
                    }
                }
            }));
        }
    };
    private final double memoryUsagePercentileThreshold;
    private final Supplier<MetricStamper> metricStamperSupplier;
    /* access modifiers changed from: private */
    public final MiniHeapDumpMemorySampler miniHeapDumpSampler;
    private final AppLifecycleListener.OnAppToForeground onAppToForeground = new AppLifecycleListener.OnAppToForeground() {
        public void onAppToForeground(Activity activity) {
            MiniHeapDumpMetricService.this.cancelFutureTasksIfAny();
        }
    };
    /* access modifiers changed from: private */
    public final SharedPreferences preferences;
    private final AppLifecycleListener.OnAppToBackground takeAndLogMemorySample = new AppLifecycleListener.OnAppToBackground() {
        public void onAppToBackground(Activity activity) {
            MiniHeapDumpMetricService.this.cancelFutureTasksIfAny();
            MiniHeapDumpMetricService miniHeapDumpMetricService = MiniHeapDumpMetricService.this;
            ScheduledFuture unused = miniHeapDumpMetricService.futureMemoryCollectionTask = ((ScheduledExecutorService) miniHeapDumpMetricService.executorServiceSupplier.get()).schedule(new Runnable() {
                public void run() {
                    int totalPssKb = MemoryUsageCapture.getTotalPssKb(MiniHeapDumpMetricService.this.getApplication());
                    StringBuilder sb = new StringBuilder(36);
                    sb.append("Background total pss kb: ");
                    sb.append(totalPssKb);
                    PrimesLog.m46d(MiniHeapDumpMetricService.TAG, sb.toString(), new Object[0]);
                    MiniHeapDumpMetricService.this.addMemorySample(totalPssKb);
                    MiniHeapDumpMetricService.this.recordSamplePercentile(totalPssKb);
                }
            }, 10, TimeUnit.SECONDS);
        }
    };

    @VisibleForTesting
    MiniHeapDumpMetricService(MetricTransmitter metricTransmitter, Application application, AppLifecycleMonitor appLifecycleMonitor2, double memoryUsagePercentileThreshold2, MiniHeapDumpMemorySampler miniHeapDumpSampler2, HprofSerializer hprofSerializer2, Supplier<MetricStamper> metricStamperSupplier2, Supplier<ScheduledExecutorService> executorServiceSupplier2, SharedPreferences preferences2) {
        super(metricTransmitter, application, metricStamperSupplier2, executorServiceSupplier2, MetricRecorder.RunIn.BACKGROUND_THREAD);
        this.appLifecycleMonitor = appLifecycleMonitor2;
        this.memoryUsagePercentileThreshold = memoryUsagePercentileThreshold2;
        this.miniHeapDumpSampler = (MiniHeapDumpMemorySampler) Preconditions.checkNotNull(miniHeapDumpSampler2);
        this.hprofSerializer = (HprofSerializer) Preconditions.checkNotNull(hprofSerializer2);
        this.metricStamperSupplier = (Supplier) Preconditions.checkNotNull(metricStamperSupplier2);
        this.executorServiceSupplier = (Supplier) Preconditions.checkNotNull(executorServiceSupplier2);
        this.preferences = preferences2;
    }

    static synchronized MiniHeapDumpMetricService createService(MetricTransmitter metricTransmitter, Application application, Supplier<MetricStamper> metricStamperSupplier2, Supplier<ScheduledExecutorService> executorServiceSupplier2, SharedPreferences sharedPreferences, double miniHeapDumpPercentileThreshold) {
        MiniHeapDumpMetricService miniHeapDumpMetricService;
        synchronized (MiniHeapDumpMetricService.class) {
            if (!PrimesJobScheduler.isFileUploadScheduled(application)) {
                SerializedMiniHeapDumpFile.deleteSerializedObjectGraphFileIfExists(application);
            }
            if (service == null) {
                String versionName = metricStamperSupplier2.get().getVersionName();
                service = new MiniHeapDumpMetricService(metricTransmitter, application, AppLifecycleMonitor.getInstance(application), miniHeapDumpPercentileThreshold, MiniHeapDumpMemorySampler.newInstance(sharedPreferences, versionName != null ? versionName.hashCode() : 0), new HprofSerializer(), metricStamperSupplier2, executorServiceSupplier2, sharedPreferences);
            }
            miniHeapDumpMetricService = service;
        }
        return miniHeapDumpMetricService;
    }

    static PrimesHeapDumpProto.HeapDumpContext createHeapDumpContextForMemorySample(int totalPssKb) {
        return (PrimesHeapDumpProto.HeapDumpContext) PrimesHeapDumpProto.HeapDumpContext.newBuilder().setTriggerType(PrimesHeapDumpProto.HeapDumpContext.TriggerType.BACKGROUND_MEMORY_SAMPLE_THRESHOLD).setTotalPssKb(totalPssKb).build();
    }

    static SystemHealthProto.PrimesHeapDumpEvent createPrimesHeapDumpEventForMemorySamples(List<Integer> memorySamples) {
        return (SystemHealthProto.PrimesHeapDumpEvent) SystemHealthProto.PrimesHeapDumpEvent.newBuilder().addAllTotalPssKbSamples(memorySamples).build();
    }

    static boolean isFileUploadEnabled(Application application) {
        return PrimesJobScheduler.isJobEnabled(application, PrimesJobScheduler.LOGGER_JOB_NAME);
    }

    public void startMonitoring() {
        if (!isShutdown()) {
            this.appLifecycleMonitor.register(this.logTotalPssSampleCount);
            this.appLifecycleMonitor.register(this.takeAndLogMemorySample);
            this.appLifecycleMonitor.register(this.onAppToForeground);
        }
    }

    /* access modifiers changed from: private */
    public void cancelFutureTasksIfAny() {
        if (this.futureMemoryCollectionTask != null) {
            this.futureMemoryCollectionTask.cancel(true);
            this.futureMemoryCollectionTask = null;
        }
    }

    /* access modifiers changed from: private */
    public void addMemorySample(int totalPssKb) {
        this.miniHeapDumpSampler.addSample(totalPssKb);
        if (satisfyDumpingRequirements() && this.miniHeapDumpSampler.isSampleAtLeastPercentile(totalPssKb, this.memoryUsagePercentileThreshold)) {
            takeHeapDump(createHeapDumpContextForMemorySample(totalPssKb), createPrimesHeapDumpEventForMemorySamples(this.miniHeapDumpSampler.getSamples()));
        }
    }

    private void takeHeapDump(PrimesHeapDumpProto.HeapDumpContext heapDumpContext, SystemHealthProto.PrimesHeapDumpEvent heapDumpEvent) {
        if (this.heapDumpLock.tryLock()) {
            this.lastSent.set(TimeCapture.getTime());
            try {
                Debug.dumpHprofData(PrimesHprofFile.getMiniHeapDumpHprofFile(getApplication()).getAbsolutePath());
                PrimesLog.m46d(TAG, "Hprof data dumped", new Object[0]);
                processHeapDump(PrimesHprofFile.getMiniHeapDumpHprofFile(getApplication()), SerializedMiniHeapDumpFile.getSerializedObjectGraphFile(getApplication()), heapDumpContext, heapDumpEvent);
            } catch (IOException e) {
                PrimesLog.m45d(TAG, "Failed to dump hprof data", e, new Object[0]);
            } catch (Throwable th) {
                PrimesHprofFile.deleteMiniHeapDumpHprofIfExists(getApplication());
                this.heapDumpLock.unlock();
                throw th;
            }
            PrimesHprofFile.deleteMiniHeapDumpHprofIfExists(getApplication());
            this.heapDumpLock.unlock();
        }
    }

    private boolean satisfyDumpingRequirements() {
        if (isShutdown() || PrimesHprofFile.getMiniHeapDumpHprofFile(getApplication()).exists() || SerializedMiniHeapDumpFile.getSerializedObjectGraphFile(getApplication()).exists()) {
            return false;
        }
        long lastSentTime = this.lastSent.get();
        if (lastSentTime == 0 || MIN_HEAP_DUMP_INTERVAL_MS + lastSentTime <= TimeCapture.getTime()) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void shutdownService() {
        cancelFutureTasksIfAny();
        this.appLifecycleMonitor.unregister(this.takeAndLogMemorySample);
        this.appLifecycleMonitor.unregister(this.onAppToForeground);
        PrimesHprofFile.deleteMiniHeapDumpHprofIfExists(getApplication());
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void processHeapDump(File hprofFile, File mhdFile, PrimesHeapDumpProto.HeapDumpContext heapDumpContext, SystemHealthProto.PrimesHeapDumpEvent headDumpTriggeredEvent) {
        mergeAndRecordHeapDumpEvents(headDumpTriggeredEvent, new HeapDumpProcessor(this.hprofSerializer, this.metricStamperSupplier.get()).process(hprofFile, heapDumpContext, mhdFile));
        if (!mhdFile.exists()) {
            PrimesLog.m46d(TAG, "Failed to serialize to file.", new Object[0]);
            return;
        }
        PrimesLog.m46d(TAG, "Scheduling heap dump upload", new Object[0]);
        PrimesJobScheduler.scheduleFileUpload(getApplication(), mhdFile.getAbsolutePath(), "PRIMES_INTERNAL_ANDROID_PRIMES");
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.protobuf.AbstractMessageLite.Builder.mergeFrom(byte[], com.google.protobuf.ExtensionRegistryLite):BuilderType
     arg types: [byte[], com.google.protobuf.ExtensionRegistryLite]
     candidates:
      com.google.protobuf.GeneratedMessageLite.Builder.mergeFrom(com.google.protobuf.CodedInputStream, com.google.protobuf.ExtensionRegistryLite):com.google.protobuf.AbstractMessageLite$Builder
      com.google.protobuf.GeneratedMessageLite.Builder.mergeFrom(com.google.protobuf.CodedInputStream, com.google.protobuf.ExtensionRegistryLite):BuilderType
      com.google.protobuf.GeneratedMessageLite.Builder.mergeFrom(com.google.protobuf.CodedInputStream, com.google.protobuf.ExtensionRegistryLite):com.google.protobuf.MessageLite$Builder
      com.google.protobuf.AbstractMessageLite.Builder.mergeFrom(com.google.protobuf.ByteString, com.google.protobuf.ExtensionRegistryLite):BuilderType
      com.google.protobuf.AbstractMessageLite.Builder.mergeFrom(com.google.protobuf.CodedInputStream, com.google.protobuf.ExtensionRegistryLite):BuilderType
      com.google.protobuf.AbstractMessageLite.Builder.mergeFrom(java.io.InputStream, com.google.protobuf.ExtensionRegistryLite):BuilderType
      com.google.protobuf.AbstractMessageLite.Builder.mergeFrom(com.google.protobuf.ByteString, com.google.protobuf.ExtensionRegistryLite):com.google.protobuf.MessageLite$Builder
      com.google.protobuf.AbstractMessageLite.Builder.mergeFrom(com.google.protobuf.CodedInputStream, com.google.protobuf.ExtensionRegistryLite):com.google.protobuf.MessageLite$Builder
      com.google.protobuf.AbstractMessageLite.Builder.mergeFrom(java.io.InputStream, com.google.protobuf.ExtensionRegistryLite):com.google.protobuf.MessageLite$Builder
      com.google.protobuf.AbstractMessageLite.Builder.mergeFrom(byte[], com.google.protobuf.ExtensionRegistryLite):com.google.protobuf.MessageLite$Builder
      com.google.protobuf.MessageLite.Builder.mergeFrom(com.google.protobuf.ByteString, com.google.protobuf.ExtensionRegistryLite):com.google.protobuf.MessageLite$Builder
      com.google.protobuf.MessageLite.Builder.mergeFrom(com.google.protobuf.CodedInputStream, com.google.protobuf.ExtensionRegistryLite):com.google.protobuf.MessageLite$Builder
      com.google.protobuf.MessageLite.Builder.mergeFrom(java.io.InputStream, com.google.protobuf.ExtensionRegistryLite):com.google.protobuf.MessageLite$Builder
      com.google.protobuf.MessageLite.Builder.mergeFrom(byte[], com.google.protobuf.ExtensionRegistryLite):com.google.protobuf.MessageLite$Builder
      com.google.protobuf.AbstractMessageLite.Builder.mergeFrom(byte[], com.google.protobuf.ExtensionRegistryLite):BuilderType */
    private void mergeAndRecordHeapDumpEvents(SystemHealthProto.PrimesHeapDumpEvent heapDumpEventTemplate, List<SystemHealthProto.PrimesHeapDumpEvent> heapDumpProcessedEvents) {
        for (SystemHealthProto.PrimesHeapDumpEvent processedEvent : heapDumpProcessedEvents) {
            try {
                recordHeapDumpEvent((SystemHealthProto.PrimesHeapDumpEvent) ((SystemHealthProto.PrimesHeapDumpEvent.Builder) ((SystemHealthProto.PrimesHeapDumpEvent.Builder) heapDumpEventTemplate.toBuilder()).mergeFrom(processedEvent.toByteArray(), ExtensionRegistryLite.getGeneratedRegistry())).build());
            } catch (InvalidProtocolBufferException e) {
                PrimesLog.m45d(TAG, "Failed to merge protos: ", e, new Object[0]);
            }
        }
    }

    private void recordHeapDumpEvent(SystemHealthProto.PrimesHeapDumpEvent event) {
        SystemHealthProto.SystemHealthMetric.Builder metric = SystemHealthProto.SystemHealthMetric.newBuilder();
        metric.setPrimesStats(SystemHealthProto.PrimesStats.newBuilder().setPrimesDebugMessage((SystemHealthProto.PrimesStats.PrimesDebugMessage) SystemHealthProto.PrimesStats.PrimesDebugMessage.newBuilder().setPrimesHeapDumpEvent(event).build()));
        recordSystemHealthMetric((SystemHealthProto.SystemHealthMetric) metric.build());
    }

    /* access modifiers changed from: private */
    public void recordStatus(SystemHealthProto.PrimesHeapDumpCalibrationStatus status) {
        SystemHealthProto.SystemHealthMetric.Builder metric = SystemHealthProto.SystemHealthMetric.newBuilder();
        metric.setPrimesStats(SystemHealthProto.PrimesStats.newBuilder().setPrimesDebugMessage((SystemHealthProto.PrimesStats.PrimesDebugMessage) SystemHealthProto.PrimesStats.PrimesDebugMessage.newBuilder().setPrimesHeapDumpCalibrationStatus(status).build()));
        recordSystemHealthMetric((SystemHealthProto.SystemHealthMetric) metric.build());
    }

    /* access modifiers changed from: private */
    public void recordSamplePercentile(int totalPssKb) {
        if (this.miniHeapDumpSampler.canComputePercentile()) {
            this.preferences.edit().putBoolean(IS_CALIBRATED_PREFERENCE_KEY, true).apply();
            recordStatus((SystemHealthProto.PrimesHeapDumpCalibrationStatus) SystemHealthProto.PrimesHeapDumpCalibrationStatus.newBuilder().setNewSamplePercentile((float) this.miniHeapDumpSampler.calculateQuantile(totalPssKb)).build());
        }
    }
}
