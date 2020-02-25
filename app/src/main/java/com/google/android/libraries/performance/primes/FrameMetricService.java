package com.google.android.libraries.performance.primes;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.VisibleForTesting;
import android.view.FrameMetrics;
import android.view.Window;

import com.google.android.libraries.performance.primes.jank.FrameTimeMeasurement;
import com.google.android.libraries.performance.primes.jank.FrameTimeMeasurementFactory;
import com.google.android.libraries.performance.primes.metriccapture.DisplayStats;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import com.google.android.libraries.stitch.util.Preconditions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import javax.annotation.Nullable;

import logs.proto.wireless.performance.mobile.ExtensionMetric;
import logs.proto.wireless.performance.mobile.SystemHealthProto;

@TargetApi(24)
final class FrameMetricService extends AbstractMetricService implements AppLifecycleListener.OnAppToBackground, PrimesStartupListener {
    private static final int MAX_CONCURRENT_MEASUREMENTS = 25;
    private static final String TAG = "FrameMetricService";
    private final ActivityTracker activityTracker;
    private final AppLifecycleMonitor appLifecycleMonitor;
    private final FrameTimeMeasurementFactory frameTimeMeasurementFactory;
    private final int maxAcceptedFrameTimeMs;
    private final Map<String, FrameTimeMeasurement> measurements = new HashMap();
    private final JankMetricExtensionProvider metricExtensionProvider;
    private final boolean monitorActivities;

    @VisibleForTesting
    FrameMetricService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier, boolean monitorActivities2, int maxSampleRate, FrameTimeMeasurementFactory frameTimeMeasurementFactory2, final JankMetricExtensionProvider metricExtensionProvider2) {
        super(transmitter, application, metricStamperSupplier, executorServiceSupplier, MetricRecorder.RunIn.BACKGROUND_THREAD, maxSampleRate);
        this.appLifecycleMonitor = AppLifecycleMonitor.getInstance(application);
        this.monitorActivities = monitorActivities2;
        this.frameTimeMeasurementFactory = (FrameTimeMeasurementFactory) Preconditions.checkNotNull(frameTimeMeasurementFactory2);
        this.metricExtensionProvider = metricExtensionProvider2;
        this.maxAcceptedFrameTimeMs = DisplayStats.maxAcceptedFrameRenderTimeMs(application);
        this.activityTracker = new ActivityTracker(new FrameMetricCallback() {
            public void activityResumed(String activity) {
                FrameMetricService.this.startMeasurement(activity);
            }

            public void activityPaused(String activity) {
                FrameMetricService.this.stopMeasurement(activity, true, metricExtensionProvider2.getMetricExtension());
            }

            public void frameRendered(int renderTimeMs) {
                FrameMetricService.this.recordMeasurement(renderTimeMs);
            }
        }, monitorActivities2);
        this.appLifecycleMonitor.register(this.activityTracker);
    }

    static FrameMetricService createService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier, FrameTimeMeasurementFactory frameTimeMeasurementFactory2, PrimesJankConfigurations configuration) {
        Preconditions.checkState(Build.VERSION.SDK_INT >= 24);
        return new FrameMetricService(transmitter, application, metricStamperSupplier, executorServiceSupplier, configuration.isMonitorActivities(), configuration.getSampleRatePerSecond(), frameTimeMeasurementFactory2, configuration.getMetricExtensionProvider());
    }

    public void onPrimesInitialize() {
    }

    public void onFirstActivityCreated() {
    }

    public void onAppToBackground(Activity activity) {
        synchronized (this.measurements) {
            this.measurements.clear();
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0057, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void startMeasurement(java.lang.String r6) {
        /*
            r5 = this;
            java.util.Map<java.lang.String, com.google.android.libraries.performance.primes.jank.FrameTimeMeasurement> r0 = r5.measurements
            monitor-enter(r0)
            java.util.Map<java.lang.String, com.google.android.libraries.performance.primes.jank.FrameTimeMeasurement> r1 = r5.measurements     // Catch:{ all -> 0x0058 }
            boolean r1 = r1.containsKey(r6)     // Catch:{ all -> 0x0058 }
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L_0x001a
            java.lang.String r1 = "FrameMetricService"
            java.lang.String r4 = "measurement already started: %s"
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ all -> 0x0058 }
            r3[r2] = r6     // Catch:{ all -> 0x0058 }
            com.google.android.libraries.performance.primes.PrimesLog.m54w(r1, r4, r3)     // Catch:{ all -> 0x0058 }
            monitor-exit(r0)     // Catch:{ all -> 0x0058 }
            return
        L_0x001a:
            java.util.Map<java.lang.String, com.google.android.libraries.performance.primes.jank.FrameTimeMeasurement> r1 = r5.measurements     // Catch:{ all -> 0x0058 }
            int r1 = r1.size()     // Catch:{ all -> 0x0058 }
            r4 = 25
            if (r1 < r4) goto L_0x0031
            java.lang.String r1 = "FrameMetricService"
            java.lang.String r4 = "Too many concurrent measurements, ignoring %s"
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ all -> 0x0058 }
            r3[r2] = r6     // Catch:{ all -> 0x0058 }
            com.google.android.libraries.performance.primes.PrimesLog.m54w(r1, r4, r3)     // Catch:{ all -> 0x0058 }
            monitor-exit(r0)     // Catch:{ all -> 0x0058 }
            return
        L_0x0031:
            java.util.Map<java.lang.String, com.google.android.libraries.performance.primes.jank.FrameTimeMeasurement> r1 = r5.measurements     // Catch:{ all -> 0x0058 }
            com.google.android.libraries.performance.primes.jank.FrameTimeMeasurementFactory r4 = r5.frameTimeMeasurementFactory     // Catch:{ all -> 0x0058 }
            com.google.android.libraries.performance.primes.jank.FrameTimeMeasurement r4 = r4.newMeasurement(r6)     // Catch:{ all -> 0x0058 }
            r1.put(r6, r4)     // Catch:{ all -> 0x0058 }
            java.util.Map<java.lang.String, com.google.android.libraries.performance.primes.jank.FrameTimeMeasurement> r1 = r5.measurements     // Catch:{ all -> 0x0058 }
            int r1 = r1.size()     // Catch:{ all -> 0x0058 }
            if (r1 != r3) goto L_0x0056
            boolean r1 = r5.monitorActivities     // Catch:{ all -> 0x0058 }
            if (r1 != 0) goto L_0x0056
            java.lang.String r1 = "FrameMetricService"
            java.lang.String r3 = "measuring start"
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0058 }
            com.google.android.libraries.performance.primes.PrimesLog.m46d(r1, r3, r2)     // Catch:{ all -> 0x0058 }
            com.google.android.libraries.performance.primes.FrameMetricService$ActivityTracker r1 = r5.activityTracker     // Catch:{ all -> 0x0058 }
            r1.startCollecting()     // Catch:{ all -> 0x0058 }
        L_0x0056:
            monitor-exit(r0)     // Catch:{ all -> 0x0058 }
            return
        L_0x0058:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0058 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.performance.primes.FrameMetricService.startMeasurement(java.lang.String):void");
    }

    /* access modifiers changed from: package-private */
    public void stopMeasurement(String eventName, boolean isConstEventName) {
        stopMeasurement(eventName, isConstEventName, null);
    }

    /* access modifiers changed from: package-private */
    public void stopMeasurement(String eventName, boolean isConstEventName, @Nullable ExtensionMetric.MetricExtension metricExtension) {
        FrameTimeMeasurement measurement;
        JankMetricExtensionProvider jankMetricExtensionProvider;
        synchronized (this.measurements) {
            measurement = this.measurements.remove(eventName);
            if (this.measurements.isEmpty() && !this.monitorActivities) {
                this.activityTracker.stopCollecting();
            }
        }
        if (measurement == null) {
            PrimesLog.m54w(TAG, "Measurement not found: %s", eventName);
        } else if (measurement.isMetricReadyToBeSent()) {
            SystemHealthProto.SystemHealthMetric.Builder systemHealthMetric = SystemHealthProto.SystemHealthMetric.newBuilder().setJankMetric(((SystemHealthProto.JankMetric.Builder) measurement.getMetric().toBuilder()).setDeviceRefreshRate(DisplayStats.getRefreshRate(getApplication())));
            if (metricExtension == null && (jankMetricExtensionProvider = this.metricExtensionProvider) != null) {
                try {
                    ExtensionMetric.MetricExtension metricExtension1 = jankMetricExtensionProvider.getMetricExtension();
                    if (metricExtension1 != null) {
                        systemHealthMetric.setMetricExtension(metricExtension1);
                    }
                } catch (Exception e) {
                    PrimesLog.m53w(TAG, "Exception while getting jank metric extension!", e, new Object[0]);
                }
            } else if (metricExtension != null) {
                systemHealthMetric.setMetricExtension(metricExtension);
            }
            recordSystemHealthMetric(eventName, isConstEventName, (SystemHealthProto.SystemHealthMetric) systemHealthMetric.build());
        }
    }

    /* access modifiers changed from: package-private */
    public void cancelMeasurement(String eventName) {
        synchronized (this.measurements) {
            this.measurements.remove(eventName);
        }
    }

    /* access modifiers changed from: private */
    public void recordMeasurement(int renderTimeMs) {
        synchronized (this.measurements) {
            for (FrameTimeMeasurement measurement : this.measurements.values()) {
                measurement.addFrame(renderTimeMs, this.maxAcceptedFrameTimeMs);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void shutdownService() {
        this.appLifecycleMonitor.unregister(this.activityTracker);
        this.activityTracker.shutdown();
        synchronized (this.measurements) {
            this.measurements.clear();
        }
    }

    @VisibleForTesting
    interface FrameMetricCallback {
        void activityPaused(String str);

        void activityResumed(String str);

        void frameRendered(int i);
    }

    @VisibleForTesting
    static class ActivityTracker implements AppLifecycleListener.OnActivityResumed, AppLifecycleListener.OnActivityPaused, Window.OnFrameMetricsAvailableListener {
        private final FrameMetricCallback callback;
        private final boolean monitorActivities;
        private Activity currentActivity;
        @Nullable
        private Handler handler;
        @Nullable
        private HandlerThread handlerThread;
        private boolean measuring;

        ActivityTracker(FrameMetricCallback callback2, boolean monitorActivities2) {
            this.callback = callback2;
            this.monitorActivities = monitorActivities2;
            if (monitorActivities2) {
                this.measuring = true;
            }
        }

        private static String generateAccountableName(Activity activity) {
            if (activity instanceof WithAccountableName) {
                return NoPiiString.safeToString(((WithAccountableName) activity).getAccountableName());
            }
            return activity.getClass().getName();
        }

        private Handler getHandler() {
            if (this.handler == null) {
                this.handlerThread = new HandlerThread("Primes-Jank");
                this.handlerThread.start();
                this.handler = new Handler(this.handlerThread.getLooper());
            }
            return this.handler;
        }

        private void attachToCurrentActivity() {
            Activity activity = this.currentActivity;
            if (activity != null) {
                activity.getWindow().addOnFrameMetricsAvailableListener(this, getHandler());
            }
        }

        private void detachFromCurrentActivity() {
            Activity activity = this.currentActivity;
            if (activity != null) {
                try {
                    activity.getWindow().removeOnFrameMetricsAvailableListener(this);
                } catch (RuntimeException ex) {
                    PrimesLog.m45d(FrameMetricService.TAG, "remove frame metrics listener failed", ex, new Object[0]);
                }
            }
        }

        public void onActivityResumed(Activity activity) {
            if (this.monitorActivities) {
                this.callback.activityResumed(generateAccountableName(activity));
            }
            synchronized (this) {
                this.currentActivity = activity;
                if (this.measuring) {
                    attachToCurrentActivity();
                }
            }
        }

        public void onActivityPaused(Activity activity) {
            synchronized (this) {
                if (this.measuring) {
                    detachFromCurrentActivity();
                }
                this.currentActivity = null;
            }
            if (this.monitorActivities) {
                this.callback.activityPaused(generateAccountableName(activity));
            }
        }

        /* access modifiers changed from: package-private */
        public void startCollecting() {
            synchronized (this) {
                this.measuring = true;
                if (this.currentActivity != null) {
                    attachToCurrentActivity();
                } else {
                    PrimesLog.m46d(FrameMetricService.TAG, "No activity", new Object[0]);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void stopCollecting() {
            synchronized (this) {
                this.measuring = false;
                detachFromCurrentActivity();
            }
        }

        /* access modifiers changed from: package-private */
        public void shutdown() {
            synchronized (this) {
                stopCollecting();
                if (this.handler != null) {
                    this.handlerThread.quitSafely();
                    this.handlerThread = null;
                    this.handler = null;
                }
            }
        }

        public void onFrameMetricsAvailable(Window window, FrameMetrics frameMetrics, int dropCountSinceLastInvocation) {
            double metric = (double) frameMetrics.getMetric(8);
            Double.isNaN(metric);
            this.callback.frameRendered((int) (metric / 1000000.0d));
        }
    }
}
