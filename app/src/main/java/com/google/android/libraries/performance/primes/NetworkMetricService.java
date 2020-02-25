package com.google.android.libraries.performance.primes;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.GuardedBy;
import android.support.annotation.VisibleForTesting;

import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import com.google.common.base.Optional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

final class NetworkMetricService extends AbstractMetricService implements AppLifecycleListener.OnAppToBackground {
    private static final String TAG = "NetworkMetricService";
    private final int batchSize;
    @GuardedBy("lock")
    private final List<NetworkEvent> batchedMetric;
    private final Object lock = new Object();
    private final NetworkMetricCollector metricCollector;
    private final AtomicInteger pendingRecords;

    @VisibleForTesting
    NetworkMetricService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier, MetricRecorder.RunIn runIn, int sampleRate, int batchSize2, boolean enableAutoSanitization, UrlSanitizer urlSanitizer, Optional<NetworkMetricExtensionProvider> metricExtensionProvider) {
        super(transmitter, application, metricStamperSupplier, executorServiceSupplier, runIn, sampleRate);
        this.batchSize = batchSize2;
        this.batchedMetric = new ArrayList(batchSize2);
        this.metricCollector = new NetworkMetricCollector(enableAutoSanitization, urlSanitizer, metricExtensionProvider);
        this.pendingRecords = new AtomicInteger();
        AppLifecycleMonitor.getInstance(getApplication()).register(this);
    }

    static NetworkMetricService createService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier, Supplier<Optional<ScenarioMetricService>> scenarioServiceSupplier, PrimesNetworkConfigurations networkConfigurations, boolean enableAutoSanitization) {
        return createService(transmitter, application, metricStamperSupplier, executorServiceSupplier, scenarioServiceSupplier, networkConfigurations, enableAutoSanitization, MetricRecorder.RunIn.SAME_THREAD);
    }

    static NetworkMetricService createService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier, Supplier<Optional<ScenarioMetricService>> supplier, PrimesNetworkConfigurations configs, boolean enableAutoSanitization, MetricRecorder.RunIn runIn) {
        return new NetworkMetricService(transmitter, application, metricStamperSupplier, executorServiceSupplier, runIn, Integer.MAX_VALUE, configs.batchSize(), enableAutoSanitization, configs.getUrlSanitizer(), configs.getMetricExtensionProvider());
    }

    public void onAppToBackground(Activity activity) {
        sendPendingEvents();
    }

    /* access modifiers changed from: package-private */
    public void recordEvent(NetworkEvent event) {
        if (!shouldRecord()) {
            return;
        }
        if (!event.isReadyToRecord()) {
            PrimesLog.m54w(TAG, "skip logging NetworkEvent due to empty bandwidth/latency data", new Object[0]);
            return;
        }
        this.pendingRecords.incrementAndGet();
        PrimesExecutors.handleFuture(getScheduledExecutorService().submit(new NetworkMetricService$$Lambda$0(this, event)));
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$recordEvent$0$NetworkMetricService(NetworkEvent event) {
        try {
            doRecordNetwork(event);
        } finally {
            this.pendingRecords.decrementAndGet();
        }
    }

    private void doRecordNetwork(NetworkEvent event) {
        event.onRecord(getApplication());
        NetworkEvent[] batchToRecord = null;
        synchronized (this.lock) {
            this.batchedMetric.add(event);
            if (this.batchedMetric.size() >= this.batchSize) {
                batchToRecord = (NetworkEvent[]) this.batchedMetric.toArray(new NetworkEvent[this.batchedMetric.size()]);
                this.batchedMetric.clear();
            }
        }
        if (batchToRecord != null) {
            recordSystemHealthMetric(this.metricCollector.getMetric(batchToRecord));
        }
    }

    /* access modifiers changed from: package-private */
    public void sendPendingEvents() {
        if (this.pendingRecords.get() > 0) {
            PrimesExecutors.handleFuture(getScheduledExecutorService().schedule(new NetworkMetricService$$Lambda$1(this), 1, TimeUnit.SECONDS));
            return;
        }
        NetworkEvent[] batchToRecord = null;
        synchronized (this.lock) {
            if (!this.batchedMetric.isEmpty()) {
                batchToRecord = (NetworkEvent[]) this.batchedMetric.toArray(new NetworkEvent[this.batchedMetric.size()]);
                this.batchedMetric.clear();
            }
        }
        if (batchToRecord != null) {
            PrimesExecutors.handleFuture(getScheduledExecutorService().submit(new NetworkMetricService$$Lambda$2(this, batchToRecord)));
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$sendPendingEvents$1$NetworkMetricService(NetworkEvent[] batch) {
        recordSystemHealthMetric(this.metricCollector.getMetric(batch));
    }

    /* access modifiers changed from: package-private */
    public void shutdownService() {
        AppLifecycleMonitor.getInstance(getApplication()).unregister(this);
        synchronized (this.lock) {
            this.batchedMetric.clear();
        }
    }
}
