package com.google.android.libraries.performance.primes.aggregation.impl;

import com.google.android.libraries.performance.primes.PrimesLog;
import com.google.android.libraries.performance.primes.Supplier;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import logs.proto.wireless.performance.mobile.AggregatedMetricProto;

public class MetricSampler {
    private static final String TAG = "MetricSampler";
    private final Map<String, SummaryAggregator> activeAggregations = new HashMap();
    private final Supplier<ScheduledExecutorService> executorServiceSupplier;
    private final Object lock = new Object();
    private final AggregatedMetricProto.AggregatedMetric.Identifier.Metric metricType;
    private ScheduledFuture<?> recordMetricAction;
    /* access modifiers changed from: private */
    public final Supplier<Long> sampledMetric;
    private final long samplingIntervalMs;

    public MetricSampler(Supplier<ScheduledExecutorService> executorServiceSupplier2, AggregatedMetricProto.AggregatedMetric.Identifier.Metric metricType2, long samplingIntervalMs2, Supplier<Long> sampledMetric2) {
        this.executorServiceSupplier = executorServiceSupplier2;
        this.metricType = metricType2;
        this.sampledMetric = sampledMetric2;
        this.samplingIntervalMs = samplingIntervalMs2;
    }

    public void registerScenario(String scenarioName) {
        synchronized (this.lock) {
            if (this.activeAggregations.containsKey(scenarioName)) {
                PrimesLog.m46d(TAG, "Replacing scenario: %s", scenarioName);
            }
            this.activeAggregations.put(scenarioName, new SummaryAggregator());
            maybeStartMetricSampling();
        }
    }

    public AggregatedMetricProto.AggregatedMetric unregisterScenario(String scenarioName) {
        AggregatedMetricProto.AggregatedMetric constructAggregatedMetric;
        synchronized (this.lock) {
            maybeCancelMetricSampling();
            constructAggregatedMetric = constructAggregatedMetric(this.activeAggregations.remove(scenarioName), this.metricType);
        }
        return constructAggregatedMetric;
    }

    public void cancel() {
        synchronized (this.lock) {
            this.activeAggregations.clear();
            maybeCancelMetricSampling();
        }
    }

    private void maybeStartMetricSampling() {
        if (this.recordMetricAction == null) {
            this.recordMetricAction = this.executorServiceSupplier.get().scheduleWithFixedDelay(new Runnable() {
                public void run() {
                    MetricSampler metricSampler = MetricSampler.this;
                    metricSampler.recordSample(((Long) metricSampler.sampledMetric.get()).longValue());
                }
            }, 0, this.samplingIntervalMs, TimeUnit.MILLISECONDS);
        }
    }

    private void maybeCancelMetricSampling() {
        if (this.recordMetricAction != null && this.activeAggregations.isEmpty()) {
            this.recordMetricAction.cancel(true);
            this.recordMetricAction = null;
        }
    }

    /* access modifiers changed from: private */
    public void recordSample(long value) {
        synchronized (this.lock) {
            for (String scenarioName : this.activeAggregations.keySet()) {
                this.activeAggregations.get(scenarioName).recordValue(value);
            }
        }
    }

    private static AggregatedMetricProto.AggregatedMetric constructAggregatedMetric(SummaryAggregator aggregation, AggregatedMetricProto.AggregatedMetric.Identifier.Metric metricType2) {
        if (aggregation == null) {
            return null;
        }
        return (AggregatedMetricProto.AggregatedMetric) AggregatedMetricProto.AggregatedMetric.newBuilder().setAggregatedData(aggregation.toProto()).setIdentifier(AggregatedMetricProto.AggregatedMetric.Identifier.newBuilder().setMetric(metricType2)).build();
    }
}
