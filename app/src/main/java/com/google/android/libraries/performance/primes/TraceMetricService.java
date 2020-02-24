package com.google.android.libraries.performance.primes;

import android.app.Application;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.libraries.performance.primes.MetricRecorder;
import com.google.android.libraries.performance.primes.sampling.ProbabilitySampler;
import com.google.android.libraries.performance.primes.tracing.TraceData;
import com.google.android.libraries.performance.primes.tracing.Tracer;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import com.google.common.base.Optional;
import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import logs.proto.wireless.performance.mobile.ExtensionMetric;
import logs.proto.wireless.performance.mobile.PrimesTraceOuterClass;
import logs.proto.wireless.performance.mobile.SystemHealthProto;

class TraceMetricService extends AbstractMetricService {
    private static final int DEFAULT_SAMPLING_RATE_PER_SECOND = 10;
    private static final String TAG = "TraceMetricService";
    private final int maxTracingBufferSize;
    private final int minSpanDurationMs;
    private final ProbabilitySampler probabilitySampler;

    static synchronized TraceMetricService createServiceWithTikTokTracing(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier, Optional<PrimesTikTokTraceConfigurations> optionalTiktokConfig) {
        TraceMetricService traceMetricService;
        synchronized (TraceMetricService.class) {
            MetricTransmitter metricTransmitter = transmitter;
            Application application2 = application;
            Supplier<MetricStamper> supplier = metricStamperSupplier;
            Supplier<ScheduledExecutorService> supplier2 = executorServiceSupplier;
            traceMetricService = new TraceMetricService(metricTransmitter, application2, supplier, supplier2, optionalTiktokConfig.mo22987or((PrimesBatteryConfigurations) PrimesTikTokTraceConfigurations.newBuilder().build()).getSampleRatePerSecond(), 1.0f, 0, 0);
        }
        return traceMetricService;
    }

    static synchronized TraceMetricService createServiceWithPrimesTracing(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier, Optional<PrimesTraceConfigurations> optionalConfig) {
        TraceMetricService traceMetricService;
        synchronized (TraceMetricService.class) {
            PrimesTraceConfigurations config = optionalConfig.mo22987or((PrimesBatteryConfigurations) PrimesTraceConfigurations.newBuilder().build());
            traceMetricService = new TraceMetricService(transmitter, application, metricStamperSupplier, executorServiceSupplier, 10, config.getSamplingPropability(), config.getMinSpanDurationMs(), config.getMaxTracingBufferSize());
        }
        return traceMetricService;
    }

    TraceMetricService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier, int samplingRatePerSecond, float samplingProbability, int minSpanDurationMs2, int maxTracingBufferSize2) {
        super(transmitter, application, metricStamperSupplier, executorServiceSupplier, MetricRecorder.RunIn.BACKGROUND_THREAD, samplingRatePerSecond);
        this.probabilitySampler = new ProbabilitySampler(samplingProbability);
        this.minSpanDurationMs = minSpanDurationMs2;
        this.maxTracingBufferSize = maxTracingBufferSize2;
    }

    private boolean isTraceWithinSamplingRate() {
        return this.probabilitySampler.isSampleAllowed();
    }

    /* access modifiers changed from: package-private */
    public boolean beginTracingIfNotStarted(String eventName) {
        if (!isTraceWithinSamplingRate()) {
            return false;
        }
        return Tracer.start(PrimesToken.PRIMES_TOKEN, eventName, this.minSpanDurationMs, this.maxTracingBufferSize);
    }

    /* access modifiers changed from: package-private */
    public void sideLoadSpan(String eventName, long startMs, long durationMs) {
        if (!TextUtils.isEmpty(eventName) && durationMs > 0) {
            Tracer.sideLoadSpan(PrimesToken.PRIMES_TOKEN, eventName, startMs, durationMs);
        }
    }

    /* access modifiers changed from: package-private */
    public void endTracingIfStarted(String customEventName, @Nullable String newEventName) {
        TraceData traceToFlush = stopAndGetTraceData(customEventName, newEventName);
        if (traceToFlush != null) {
            flushTrace(traceToFlush, null);
        }
    }

    /* access modifiers changed from: package-private */
    public TraceData stopAndGetTraceData(String customEventName, String newEventName) {
        return Tracer.stop(PrimesToken.PRIMES_TOKEN, !TextUtils.isEmpty(newEventName) ? newEventName : customEventName);
    }

    /* access modifiers changed from: package-private */
    public void flushTrace(final TraceData traceToFlush, String accountableComponentName) {
        if (traceToFlush != null) {
            Future<?> submit = getScheduledExecutorService().submit(new Runnable() {
                public void run() {
                    TraceMetricService.this.record(Tracer.flush(PrimesToken.PRIMES_TOKEN, traceToFlush));
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void record(PrimesTraceOuterClass.Span[] spans) {
        if (spans != null && spans.length > 0) {
            PrimesTraceOuterClass.PrimesTrace trace = (PrimesTraceOuterClass.PrimesTrace) PrimesTraceOuterClass.PrimesTrace.newBuilder().setTraceId(UUID.randomUUID().getLeastSignificantBits()).addAllSpans(Arrays.asList(spans)).build();
            SystemHealthProto.SystemHealthMetric metric = (SystemHealthProto.SystemHealthMetric) SystemHealthProto.SystemHealthMetric.newBuilder().setPrimesTrace(trace).build();
            Serializable[] serializableArr = new Serializable[2];
            serializableArr[0] = trace.hasTraceId() ? Long.valueOf(trace.getTraceId()) : null;
            serializableArr[1] = trace.getSpans(0).getConstantName();
            PrimesLog.m46d(TAG, "Recording trace %d: %s", serializableArr);
            recordSystemHealthMetric(metric);
        }
    }

    /* access modifiers changed from: package-private */
    public void record(PrimesTraceOuterClass.PrimesTrace trace, @Nullable ExtensionMetric.MetricExtension metricExtension) {
        if (shouldRecord()) {
            SystemHealthProto.SystemHealthMetric metric = (SystemHealthProto.SystemHealthMetric) SystemHealthProto.SystemHealthMetric.newBuilder().setPrimesTrace(trace).build();
            Serializable[] serializableArr = new Serializable[2];
            serializableArr[0] = trace.hasTraceId() ? Long.valueOf(trace.getTraceId()) : null;
            serializableArr[1] = trace.getSpans(0).getConstantName();
            PrimesLog.m46d(TAG, "Recording trace %d: %s", serializableArr);
            recordSystemHealthMetric(metric, metricExtension);
        }
    }

    /* access modifiers changed from: package-private */
    public void cancelTracingIfActive() {
        Tracer.cancel(PrimesToken.PRIMES_TOKEN);
    }

    /* access modifiers changed from: package-private */
    public void shutdownService() {
        Tracer.shutdown(PrimesToken.PRIMES_TOKEN);
    }
}
