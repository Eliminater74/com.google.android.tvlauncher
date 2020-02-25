package com.google.android.libraries.performance.primes;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.google.android.libraries.performance.primes.aggregation.MetricAggregatorIdentifier;
import com.google.android.libraries.performance.primes.aggregation.impl.MetricAggregatorStore;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;

import java.util.concurrent.ScheduledExecutorService;

import logs.proto.wireless.performance.mobile.SystemHealthProto;

public final class CounterMetricService extends AbstractMetricService implements AppLifecycleListener.OnAppToBackground {
    private final AppLifecycleMonitor lifecycleMonitor;
    private final MetricAggregatorStore metricAggregatorStore = new MetricAggregatorStore();

    @VisibleForTesting
    CounterMetricService(MetricTransmitter transmitter, Application application, AppLifecycleMonitor lifecycleMonitor2, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier) {
        super(transmitter, application, metricStamperSupplier, executorServiceSupplier, MetricRecorder.RunIn.BACKGROUND_THREAD);
        this.lifecycleMonitor = lifecycleMonitor2;
        lifecycleMonitor2.register(this);
    }

    static CounterMetricService createService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier) {
        return new CounterMetricService(transmitter, application, AppLifecycleMonitor.getInstance(application), metricStamperSupplier, executorServiceSupplier);
    }

    public /* bridge */ /* synthetic */ void onShutdown() {
        super.onShutdown();
    }

    /* access modifiers changed from: package-private */
    public void shutdownService() {
        this.lifecycleMonitor.unregister(this);
    }

    public void onAppToBackground(Activity activity) {
        SystemHealthProto.SystemHealthMetric metric = this.metricAggregatorStore.flushMetrics();
        if (metric != null) {
            recordSystemHealthMetric(metric);
        }
    }

    /* access modifiers changed from: package-private */
    public void addValueToCounter(String counterName, @Nullable String componentName, boolean areNamesConstant, long value) {
        if (areNamesConstant) {
            this.metricAggregatorStore.getAggregation(MetricAggregatorIdentifier.forCustomCounter(counterName, componentName)).recordValue(value);
        }
    }

    /* access modifiers changed from: package-private */
    public Counter startCounter() {
        return new Counter();
    }

    /* access modifiers changed from: package-private */
    public void stopCounter(Counter counter, String counterName, @Nullable String componentName, boolean areNamesConstant) {
        if (areNamesConstant) {
            this.metricAggregatorStore.getAggregation(MetricAggregatorIdentifier.forCustomCounter(counterName, componentName)).recordAllFrom(counter.asSummaryAggregator());
        }
    }
}
