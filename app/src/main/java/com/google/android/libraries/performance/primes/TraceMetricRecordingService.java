package com.google.android.libraries.performance.primes;

import android.app.Application;
import android.support.annotation.Nullable;

import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;

import java.io.Serializable;
import java.util.concurrent.ScheduledExecutorService;

import logs.proto.wireless.performance.mobile.ExtensionMetric;
import logs.proto.wireless.performance.mobile.PrimesTraceOuterClass;
import logs.proto.wireless.performance.mobile.SystemHealthProto;

class TraceMetricRecordingService extends AbstractMetricService {
    private static final String TAG = "BaseTraceMetricService";

    TraceMetricRecordingService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier) {
        super(transmitter, application, metricStamperSupplier, executorServiceSupplier, MetricRecorder.RunIn.BACKGROUND_THREAD);
    }

    static synchronized TraceMetricRecordingService createService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier) {
        TraceMetricRecordingService traceMetricRecordingService;
        synchronized (TraceMetricRecordingService.class) {
            traceMetricRecordingService = new TraceMetricRecordingService(transmitter, application, metricStamperSupplier, executorServiceSupplier);
        }
        return traceMetricRecordingService;
    }

    /* access modifiers changed from: package-private */
    public void record(PrimesTraceOuterClass.PrimesTrace trace, @Nullable ExtensionMetric.MetricExtension metricExtension, @Nullable String accountableComponentName) {
        SystemHealthProto.SystemHealthMetric message = (SystemHealthProto.SystemHealthMetric) SystemHealthProto.SystemHealthMetric.newBuilder().setPrimesTrace(trace).build();
        Serializable[] serializableArr = new Serializable[2];
        serializableArr[0] = trace.hasTraceId() ? Long.valueOf(trace.getTraceId()) : null;
        serializableArr[1] = trace.getSpans(0).getConstantName();
        PrimesLog.m46d(TAG, "Recording trace %d: %s", serializableArr);
        recordSystemHealthMetric(message, metricExtension, accountableComponentName);
    }

    /* access modifiers changed from: package-private */
    public void shutdownService() {
    }
}
