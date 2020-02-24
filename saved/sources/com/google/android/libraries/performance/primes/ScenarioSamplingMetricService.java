package com.google.android.libraries.performance.primes;

import android.app.Application;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.media.session.PlaybackStateCompat;
import com.google.android.libraries.performance.primes.MetricRecorder;
import com.google.android.libraries.performance.primes.aggregation.impl.MetricSampler;
import com.google.android.libraries.performance.primes.metriccapture.MemoryUsageCapture;
import com.google.android.libraries.performance.primes.scenario.PrimesScenarioConfigurations;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import logs.proto.wireless.performance.mobile.AggregatedMetricProto;
import logs.proto.wireless.performance.mobile.SystemHealthProto;

class ScenarioSamplingMetricService extends AbstractMetricService {
    private static final long ALLOCATED_BYTES_RECORD_INTERVAL_MS = TimeUnit.SECONDS.toMillis(1);
    private static final String TAG = "ScenarioSamplingMetricService";
    private static final long TOTAL_PSS_RECORD_INTERVAL_MS = TimeUnit.SECONDS.toMillis(2);
    private final Object lock = new Object();
    private final List<MetricSampler> scenarioMetrics;

    static ScenarioSamplingMetricService createService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier, PrimesScenarioConfigurations config) {
        List<MetricSampler> scenarioMetrics2 = new ArrayList<>(2);
        if (config.isTotalPssCaptureEnabled()) {
            scenarioMetrics2.add(newTotalPssSampler(application, executorServiceSupplier));
        }
        scenarioMetrics2.add(newAllocatedBytesSampler(executorServiceSupplier));
        return new ScenarioSamplingMetricService(application, transmitter, metricStamperSupplier, executorServiceSupplier, scenarioMetrics2);
    }

    private static MetricSampler newTotalPssSampler(final Application application, Supplier<ScheduledExecutorService> executorServiceSupplier) {
        return new MetricSampler(executorServiceSupplier, AggregatedMetricProto.AggregatedMetric.Identifier.Metric.MEMORY_TOTAL_PSS_KB, TOTAL_PSS_RECORD_INTERVAL_MS, new Supplier<Long>() {
            public Long get() {
                PrimesLog.m46d(ScenarioSamplingMetricService.TAG, "Measuring total pss", new Object[0]);
                return Long.valueOf((long) MemoryUsageCapture.getTotalPssKb(application));
            }
        });
    }

    private static MetricSampler newAllocatedBytesSampler(Supplier<ScheduledExecutorService> executorServiceSupplier) {
        return new MetricSampler(executorServiceSupplier, AggregatedMetricProto.AggregatedMetric.Identifier.Metric.MEMORY_ALLOCATED_KB, ALLOCATED_BYTES_RECORD_INTERVAL_MS, new Supplier<Long>() {
            public Long get() {
                PrimesLog.m46d(ScenarioSamplingMetricService.TAG, "Measuring total pss", new Object[0]);
                return Long.valueOf(MemoryUsageCapture.getAllocatedBytes() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);
            }
        });
    }

    @VisibleForTesting
    ScenarioSamplingMetricService(Application application, MetricTransmitter transmitter, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier, List<MetricSampler> scenarioMetrics2) {
        super(transmitter, application, metricStamperSupplier, executorServiceSupplier, MetricRecorder.RunIn.BACKGROUND_THREAD);
        this.scenarioMetrics = scenarioMetrics2;
    }

    /* access modifiers changed from: package-private */
    public void startScenarioSampling(String scenarioName) {
        synchronized (this.lock) {
            for (MetricSampler sampler : this.scenarioMetrics) {
                sampler.registerScenario(scenarioName);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void recordScenarioSampledMetrics(String scenarioName) {
        synchronized (this.lock) {
            for (MetricSampler sampler : this.scenarioMetrics) {
                recordSystemHealthMetric(constructSystemHealthMetric(sampler.unregisterScenario(scenarioName)));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void cancelScenarioSampling(String scenarioName) {
        synchronized (this.lock) {
            for (MetricSampler sampler : this.scenarioMetrics) {
                sampler.unregisterScenario(scenarioName);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void shutdownService() {
        for (MetricSampler sampler : this.scenarioMetrics) {
            sampler.cancel();
        }
    }

    private static SystemHealthProto.SystemHealthMetric constructSystemHealthMetric(AggregatedMetricProto.AggregatedMetric aggregatedMetric) {
        if (aggregatedMetric == null) {
            return null;
        }
        return (SystemHealthProto.SystemHealthMetric) SystemHealthProto.SystemHealthMetric.newBuilder().addAggregatedMetrics(aggregatedMetric).build();
    }
}
