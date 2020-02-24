package com.google.android.libraries.performance.primes;

import android.app.Activity;
import android.app.Application;
import com.google.android.libraries.performance.primes.AppLifecycleListener;
import com.google.android.libraries.performance.primes.MetricRecorder;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import logs.proto.wireless.performance.mobile.SystemHealthProto;

final class MagicEyeLogService extends AbstractMetricService implements PrimesStartupListener, AppLifecycleListener.OnAppToForeground, AppLifecycleListener.OnAppToBackground {
    private static final String TAG = "MagicEyeLogService";
    private boolean monitoring;

    static MagicEyeLogService createService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier) {
        return new MagicEyeLogService(transmitter, application, metricStamperSupplier, executorServiceSupplier);
    }

    MagicEyeLogService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier) {
        super(transmitter, application, metricStamperSupplier, executorServiceSupplier, MetricRecorder.RunIn.SAME_THREAD);
    }

    public void onPrimesInitialize() {
        startMonitoring();
    }

    public void onFirstActivityCreated() {
    }

    public void onAppToBackground(Activity activity) {
        sendInBackground(SystemHealthProto.MagicEyeMetric.AppStateEvent.APP_TO_BACKGROUND);
        PrimesLog.m46d(TAG, "Logging APP_TO_BACKGROUND", new Object[0]);
    }

    public void onAppToForeground(Activity activity) {
        sendInBackground(SystemHealthProto.MagicEyeMetric.AppStateEvent.APP_TO_FOREGROUND);
        PrimesLog.m46d(TAG, "Logging APP_TO_FOREGROUND", new Object[0]);
    }

    /* access modifiers changed from: package-private */
    public synchronized void startMonitoring() {
        if (!this.monitoring && !isShutdown()) {
            AppLifecycleMonitor.getInstance(getApplication()).register(this);
            this.monitoring = true;
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void stopMonitoring() {
        if (this.monitoring) {
            AppLifecycleMonitor.getInstance(getApplication()).unregister(this);
            this.monitoring = false;
        }
    }

    private void sendInBackground(final SystemHealthProto.MagicEyeMetric.AppStateEvent appStateEvent) {
        Future<?> submit = getScheduledExecutorService().submit(new Runnable() {
            public void run() {
                MagicEyeLogService.this.recordSystemHealthMetric((SystemHealthProto.SystemHealthMetric) SystemHealthProto.SystemHealthMetric.newBuilder().setMagicEyeMetric(SystemHealthProto.MagicEyeMetric.newBuilder().setAppStateEvent(appStateEvent)).build());
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void shutdownService() {
        stopMonitoring();
    }
}
