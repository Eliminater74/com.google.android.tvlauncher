package com.google.android.libraries.performance.primes;

import android.app.Application;
import com.google.android.libraries.performance.primes.MetricRecorder;
import com.google.android.libraries.performance.primes.metriccapture.CpuUsageCapture;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import logs.proto.wireless.performance.mobile.SystemHealthProto;

final class CpuMetricService extends AbstractMetricService implements PrimesStartupListener {
    private static final String TAG = "CpuMetricService";
    private final int initialDelay;
    /* access modifiers changed from: private */
    public final int numSamples;
    private ScheduledFuture<?> scheduledFutureCollectCpuUsage;
    private final int timeBetweenSamples;

    static CpuMetricService createService(MetricTransmitter metricTransmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier, PrimesCpuConfigurations config) {
        return new CpuMetricService(metricTransmitter, application, metricStamperSupplier, executorServiceSupplier, config.getTimeBetweenSamples(), config.getInitialDelay(), config.getNumSamples());
    }

    CpuMetricService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier, int timeBetweenSamples2, int initialDelay2, int numSamples2) {
        super(transmitter, application, metricStamperSupplier, executorServiceSupplier, MetricRecorder.RunIn.BACKGROUND_THREAD);
        this.timeBetweenSamples = timeBetweenSamples2;
        this.initialDelay = initialDelay2;
        this.numSamples = numSamples2;
    }

    /* access modifiers changed from: package-private */
    public synchronized void startMonitoring() {
        if (this.scheduledFutureCollectCpuUsage == null && !isShutdown()) {
            this.scheduledFutureCollectCpuUsage = getScheduledExecutorService().scheduleAtFixedRate(new CpuCollectionTask(), (long) this.initialDelay, (long) this.timeBetweenSamples, TimeUnit.MILLISECONDS);
        }
    }

    private final class CpuCollectionTask implements Runnable {
        private final AtomicInteger samplesCollectedSoFar;

        private CpuCollectionTask() {
            this.samplesCollectedSoFar = new AtomicInteger(0);
        }

        public void run() {
            if (CpuMetricService.this.shouldRecord() && this.samplesCollectedSoFar.getAndIncrement() < CpuMetricService.this.numSamples) {
                CpuMetricService.this.recordStackTrace();
            }
            if (this.samplesCollectedSoFar.get() >= CpuMetricService.this.numSamples) {
                CpuMetricService.this.shutdownService(false);
            }
        }
    }

    /* access modifiers changed from: private */
    public void recordStackTrace() {
        recordSystemHealthMetric((SystemHealthProto.SystemHealthMetric) SystemHealthProto.SystemHealthMetric.newBuilder().setCpuUsageMetric(CpuUsageCapture.getCpuUsageMetric()).build());
    }

    /* access modifiers changed from: private */
    public synchronized void shutdownService(boolean mayInterruptIfRunning) {
        if (this.scheduledFutureCollectCpuUsage != null) {
            this.scheduledFutureCollectCpuUsage.cancel(mayInterruptIfRunning);
            this.scheduledFutureCollectCpuUsage = null;
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void shutdownService() {
        shutdownService(true);
    }

    public void onPrimesInitialize() {
    }

    public void onFirstActivityCreated() {
        startMonitoring();
    }
}
