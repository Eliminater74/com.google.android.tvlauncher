package com.google.android.libraries.performance.primes;

import android.app.Application;
import android.support.annotation.Nullable;
import com.google.android.libraries.performance.primes.MetricRecorder;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import com.google.android.libraries.stitch.util.Preconditions;
import java.util.concurrent.ScheduledExecutorService;
import logs.proto.wireless.performance.mobile.ExtensionMetric;
import logs.proto.wireless.performance.mobile.SystemHealthProto;

abstract class AbstractMetricService implements ShutdownListener {
    private final Application application;
    private final Supplier<ScheduledExecutorService> executorServiceSupplier;
    private final MetricRecorder metricRecorder;
    private volatile boolean shutdown;

    /* access modifiers changed from: package-private */
    public abstract void shutdownService();

    protected AbstractMetricService(MetricTransmitter transmitter, Application application2, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier2, MetricRecorder.RunIn whereToRun) {
        this(transmitter, application2, metricStamperSupplier, executorServiceSupplier2, whereToRun, Integer.MAX_VALUE);
    }

    protected AbstractMetricService(MetricTransmitter transmitter, Application application2, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier2, MetricRecorder.RunIn whereToRun, int sampleRate) {
        Preconditions.checkNotNull(transmitter);
        Preconditions.checkNotNull(application2);
        this.application = application2;
        this.executorServiceSupplier = executorServiceSupplier2;
        this.metricRecorder = new MetricRecorder(transmitter, metricStamperSupplier, executorServiceSupplier2, whereToRun, sampleRate);
    }

    public void onShutdown() {
        this.shutdown = true;
        shutdownService();
    }

    /* access modifiers changed from: protected */
    public boolean isShutdown() {
        return this.shutdown;
    }

    /* access modifiers changed from: protected */
    public final Application getApplication() {
        return this.application;
    }

    /* access modifiers changed from: protected */
    public final boolean shouldRecord() {
        return this.metricRecorder.shouldRecord();
    }

    /* access modifiers changed from: protected */
    public final void recordSystemHealthMetric(@Nullable String customEventName, boolean isEventNameConstant, SystemHealthProto.SystemHealthMetric metric, @Nullable ExtensionMetric.MetricExtension metricExtension) {
        recordSystemHealthMetric(customEventName, isEventNameConstant, metric, metricExtension, null);
    }

    /* access modifiers changed from: protected */
    public final void recordSystemHealthMetric(SystemHealthProto.SystemHealthMetric metric) {
        recordSystemHealthMetric(null, true, metric, null, null);
    }

    /* access modifiers changed from: protected */
    public final void recordSystemHealthMetric(String customEventName, boolean isEventNameConstant, SystemHealthProto.SystemHealthMetric metric) {
        recordSystemHealthMetric(customEventName, isEventNameConstant, metric, null, null);
    }

    /* access modifiers changed from: protected */
    public final void recordSystemHealthMetric(SystemHealthProto.SystemHealthMetric metric, @Nullable ExtensionMetric.MetricExtension metricExtension) {
        recordSystemHealthMetric(null, true, metric, metricExtension, null);
    }

    /* access modifiers changed from: protected */
    public final void recordSystemHealthMetric(SystemHealthProto.SystemHealthMetric metric, @Nullable ExtensionMetric.MetricExtension metricExtension, @Nullable String accountableComponentName) {
        recordSystemHealthMetric(null, true, metric, metricExtension, accountableComponentName);
    }

    private void recordSystemHealthMetric(@Nullable String customEventName, boolean isEventNameConstant, SystemHealthProto.SystemHealthMetric metric, @Nullable ExtensionMetric.MetricExtension metricExtension, @Nullable String accountableComponentName) {
        if (!isShutdown()) {
            this.metricRecorder.record(customEventName, isEventNameConstant, metric, metricExtension, accountableComponentName);
        }
    }

    /* access modifiers changed from: package-private */
    public final ScheduledExecutorService getScheduledExecutorService() {
        return this.executorServiceSupplier.get();
    }

    /* access modifiers changed from: package-private */
    public final Supplier<ScheduledExecutorService> getScheduledExecutorSupplier() {
        return this.executorServiceSupplier;
    }
}
