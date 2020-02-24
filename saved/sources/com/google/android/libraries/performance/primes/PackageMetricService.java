package com.google.android.libraries.performance.primes;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageStats;
import android.support.annotation.VisibleForTesting;
import com.google.android.libraries.performance.primes.AppLifecycleListener;
import com.google.android.libraries.performance.primes.MetricRecorder;
import com.google.android.libraries.performance.primes.metriccapture.DirStatsCapture;
import com.google.android.libraries.performance.primes.metriccapture.PackageStatsCapture;
import com.google.android.libraries.performance.primes.sampling.SamplingUtil;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import com.google.android.libraries.stitch.util.Preconditions;
import com.google.common.base.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import logs.proto.wireless.performance.mobile.SystemHealthProto;

final class PackageMetricService extends AbstractMetricService implements PrimesStartupListener, AppLifecycleListener.OnAppToBackground {
    private static final long CONSIDER_RECENT_DURATION_MS = TimeUnit.HOURS.toMillis(12);
    private static final String LAST_SEND_KEY = "primes.packageMetric.lastSendTime";
    private static final String TAG = "PackageMetricService";
    private final AppLifecycleMonitor appLifecycleMonitor;
    private final boolean captureDirStats;
    private final Pattern[] listFilesPatterns;
    private final int maxFolderDepth;
    /* access modifiers changed from: private */
    public final SharedPreferences sharedPrefs;

    static PackageMetricService createService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier, SharedPreferences sharedPrefs2, Optional<PrimesDirStatsConfigurations> dirStatsConfigs) {
        if (!dirStatsConfigs.isPresent()) {
            return new PackageMetricService(transmitter, application, metricStamperSupplier, executorServiceSupplier, sharedPrefs2);
        }
        return new PackageMetricService(transmitter, application, metricStamperSupplier, executorServiceSupplier, sharedPrefs2, dirStatsConfigs.get().isEnabled(), dirStatsConfigs.get().getMaxFolderDepth(), dirStatsConfigs.get().getListFilesPatterns());
    }

    static boolean skipPackageMetric(SharedPreferences sharedPrefs2) {
        return SamplingUtil.hasRecentTimeStamp(sharedPrefs2, LAST_SEND_KEY, CONSIDER_RECENT_DURATION_MS);
    }

    static long readSendTime(SharedPreferences sharedPrefs2) {
        return SamplingUtil.readTimeStamp(sharedPrefs2, LAST_SEND_KEY);
    }

    private static boolean writeSendTime(SharedPreferences sharedPrefs2) {
        return SamplingUtil.writeTimeStamp(sharedPrefs2, LAST_SEND_KEY);
    }

    @VisibleForTesting
    static SystemHealthProto.PackageMetric statsToProto(PackageStats src) {
        Preconditions.checkNotNull(src);
        return (SystemHealthProto.PackageMetric) SystemHealthProto.PackageMetric.newBuilder().setCacheSize(src.cacheSize).setCodeSize(src.codeSize).setDataSize(src.dataSize).setExternalCacheSize(src.externalCacheSize).setExternalCodeSize(src.externalCodeSize).setExternalDataSize(src.externalDataSize).setExternalMediaSize(src.externalMediaSize).setExternalObbSize(src.externalObbSize).build();
    }

    PackageMetricService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorSupplier, SharedPreferences sharedPrefs2) {
        this(transmitter, application, metricStamperSupplier, executorSupplier, sharedPrefs2, false, -1, new Pattern[0]);
    }

    PackageMetricService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorSupplier, SharedPreferences sharedPrefs2, boolean captureDirStats2, int maxFolderDepth2, Pattern... listFilesPatterns2) {
        super(transmitter, application, metricStamperSupplier, executorSupplier, MetricRecorder.RunIn.SAME_THREAD);
        this.sharedPrefs = sharedPrefs2;
        this.captureDirStats = captureDirStats2;
        this.maxFolderDepth = maxFolderDepth2;
        this.listFilesPatterns = listFilesPatterns2;
        this.appLifecycleMonitor = AppLifecycleMonitor.getInstance(application);
    }

    public void onPrimesInitialize() {
        this.appLifecycleMonitor.register(this);
    }

    public void onFirstActivityCreated() {
    }

    public void onAppToBackground(Activity activity) {
        this.appLifecycleMonitor.unregister(this);
        PrimesExecutors.handleFuture(sendInBackground());
    }

    /* access modifiers changed from: package-private */
    public Future<?> sendInBackground() {
        return getScheduledExecutorService().submit(new Runnable() {
            public void run() {
                if (!PackageMetricService.skipPackageMetric(PackageMetricService.this.sharedPrefs)) {
                    PackageMetricService.this.send();
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void send() {
        PackageStats packageStats = PackageStatsCapture.getPackageStats(getApplication());
        if (packageStats != null) {
            SystemHealthProto.SystemHealthMetric.Builder metric = SystemHealthProto.SystemHealthMetric.newBuilder();
            SystemHealthProto.PackageMetric.Builder packageMetric = (SystemHealthProto.PackageMetric.Builder) statsToProto(packageStats).toBuilder();
            if (this.captureDirStats) {
                packageMetric.clearDirStats().addAllDirStats(DirStatsCapture.getDirStats(getApplication(), this.maxFolderDepth, this.listFilesPatterns));
            }
            metric.setPackageMetric(packageMetric);
            recordSystemHealthMetric((SystemHealthProto.SystemHealthMetric) metric.build());
            if (!writeSendTime(this.sharedPrefs)) {
                PrimesLog.m46d(TAG, "Failure storing timestamp persistently", new Object[0]);
                return;
            }
            return;
        }
        PrimesLog.m54w(TAG, "PackageStats capture failed.", new Object[0]);
    }

    /* access modifiers changed from: package-private */
    public void shutdownService() {
        this.appLifecycleMonitor.unregister(this);
    }
}
