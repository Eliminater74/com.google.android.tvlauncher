package com.google.android.libraries.performance.primes;

import android.support.annotation.Nullable;

import com.google.android.libraries.performance.primes.sampling.PrimesSampling;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import com.google.android.libraries.stitch.util.Preconditions;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

import logs.proto.wireless.performance.mobile.ExtensionMetric;
import logs.proto.wireless.performance.mobile.SystemHealthProto;

class MetricRecorder {
    private static final String TAG = "MetricRecorder";
    private final Supplier<ScheduledExecutorService> executorServiceSupplier;
    private final PrimesSampling instrumentationSampling;
    private final Supplier<MetricStamper> metricStamperSupplier;
    private final MetricTransmitter metricTransmitter;
    private final RunIn whereToRun;

    MetricRecorder(MetricTransmitter metricTransmitter2, Supplier<MetricStamper> metricStamperSupplier2, Supplier<ScheduledExecutorService> executorServiceSupplier2, RunIn whereToRun2, int sampleRatePerSecond) {
        this.metricTransmitter = (MetricTransmitter) Preconditions.checkNotNull(metricTransmitter2);
        this.metricStamperSupplier = (Supplier) Preconditions.checkNotNull(metricStamperSupplier2);
        this.executorServiceSupplier = executorServiceSupplier2;
        this.whereToRun = whereToRun2;
        this.instrumentationSampling = new PrimesSampling(sampleRatePerSecond);
    }

    /* access modifiers changed from: package-private */
    public boolean shouldRecord() {
        return !this.instrumentationSampling.isSampleRateExceeded();
    }

    /* access modifiers changed from: package-private */
    public void record(SystemHealthProto.SystemHealthMetric message) {
        record(null, false, message, null, null);
    }

    /* access modifiers changed from: package-private */
    public void record(@Nullable String customEventName, boolean isEventNameConstant, SystemHealthProto.SystemHealthMetric message, @Nullable ExtensionMetric.MetricExtension metricExtension, @Nullable String accountableComponentName) {
        record(this.whereToRun, customEventName, isEventNameConstant, message, metricExtension, accountableComponentName);
    }

    private void record(RunIn where, @Nullable String customEventName, boolean isEventNameConstant, SystemHealthProto.SystemHealthMetric message, @Nullable ExtensionMetric.MetricExtension metricExtension, @Nullable String accountableComponentName) {
        if (where == RunIn.SAME_THREAD) {
            recordInternal(customEventName, isEventNameConstant, message, metricExtension, accountableComponentName);
        } else {
            recordInBackground(customEventName, isEventNameConstant, message, metricExtension, accountableComponentName);
        }
    }

    private void recordInBackground(@Nullable String customEventName, boolean isEventNameConstant, SystemHealthProto.SystemHealthMetric message, @Nullable ExtensionMetric.MetricExtension metricExtension, @Nullable String accountableComponentName) {
        final String str = customEventName;
        final boolean z = isEventNameConstant;
        final SystemHealthProto.SystemHealthMetric systemHealthMetric = message;
        final ExtensionMetric.MetricExtension metricExtension2 = metricExtension;
        final String str2 = accountableComponentName;
        Future<?> submit = this.executorServiceSupplier.get().submit(new Runnable() {
            public void run() {
                MetricRecorder.this.recordInternal(str, z, systemHealthMetric, metricExtension2, str2);
            }
        });
    }

    /* access modifiers changed from: private */
    public void recordInternal(@Nullable String customEventName, boolean isEventNameConstant, SystemHealthProto.SystemHealthMetric metric, @Nullable ExtensionMetric.MetricExtension metricExtension, @Nullable String accountableComponentName) {
        if (metric == null) {
            String valueOf = String.valueOf(customEventName);
            PrimesLog.m54w(TAG, valueOf.length() != 0 ? "metric is null, skipping recorded metric for event: ".concat(valueOf) : new String("metric is null, skipping recorded metric for event: "), new Object[0]);
            return;
        }
        SystemHealthProto.SystemHealthMetric.Builder metricBuilder = (SystemHealthProto.SystemHealthMetric.Builder) this.metricStamperSupplier.get().stamp(metric).toBuilder();
        if (isEventNameConstant) {
            if (customEventName != null) {
                metricBuilder.setConstantEventName(customEventName);
            } else {
                metricBuilder.clearConstantEventName();
            }
        } else if (customEventName != null) {
            metricBuilder.setCustomEventName(customEventName);
        } else {
            metricBuilder.clearCustomEventName();
        }
        if (metricExtension != null) {
            metricBuilder.setMetricExtension(metricExtension);
        }
        if (accountableComponentName != null) {
            metricBuilder.setAccountableComponent(SystemHealthProto.AccountableComponent.newBuilder().setCustomName(accountableComponentName));
        }
        this.metricTransmitter.send((SystemHealthProto.SystemHealthMetric) metricBuilder.build());
        this.instrumentationSampling.incrementSampleCount();
    }

    public enum RunIn {
        SAME_THREAD,
        BACKGROUND_THREAD
    }
}
