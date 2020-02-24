package com.google.android.tvlauncher.application;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.libraries.gcoreclient.clearcut.impl.GcoreClearcutApiImpl;
import com.google.android.libraries.gcoreclient.clearcut.impl.GcoreClearcutLoggerFactoryImpl;
import com.google.android.libraries.gcoreclient.common.api.impl.GcoreGoogleApiClientImpl;
import com.google.android.libraries.performance.primes.Primes;
import com.google.android.libraries.performance.primes.PrimesApiProvider;
import com.google.android.libraries.performance.primes.PrimesConfigurations;
import com.google.android.libraries.performance.primes.PrimesConfigurationsProvider;
import com.google.android.libraries.performance.primes.PrimesCrashConfigurations;
import com.google.android.libraries.performance.primes.PrimesMemoryConfigurations;
import com.google.android.libraries.performance.primes.PrimesPackageConfigurations;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import com.google.android.libraries.performance.primes.transmitter.impl.ClearcutMetricTransmitter;
import com.google.android.tvlauncher.analytics.ClearcutAppEventLogger;
import com.google.android.tvlauncher.analytics.ClearcutEventLoggerEngine;
import com.google.android.tvlauncher.doubleclick.AdsManager;
import com.google.android.tvlauncher.doubleclick.AdsUtil;
import com.google.android.tvlauncher.doubleclick.DirectAdConfigSerializer;
import com.google.android.tvlauncher.doubleclick.DoubleClickAdConfigSerializer;
import com.google.android.tvlauncher.doubleclick.DoubleClickAdServer;
import com.google.android.tvlauncher.doubleclick.OutstreamVideoAdFactory;
import com.google.android.tvlauncher.util.TestUtils;

public class TvLauncherApplication extends TvLauncherApplicationBase {
    private static final String TAG = "TvLauncherApplication";
    private AdsManager mAdsManager;
    private final Object mAdsManagerLock = new Object();
    private AdsUtil mAdsUtil;
    private final Object mAdsUtilLock = new Object();
    private DirectAdConfigSerializer mDirectAdConfigSerializer;
    private final Object mDirectAdConfigSerializerLock = new Object();
    private DoubleClickAdConfigSerializer mDoubleClickAdConfigSerializer;
    private final Object mDoubleClickAdConfigSerializerLock = new Object();
    private DoubleClickAdServer mDoubleClickAdServer;
    private final Object mDoubleClickAdServerLock = new Object();
    private OutstreamVideoAdFactory mOutstreamVideoAdFactory;
    private final Object mOutstreamVideoAdFactoryLock = new Object();

    public void onCreate() {
        super.onCreate();
        if (!TestUtils.isRunningInTest()) {
            if (!isRemoteYoutubePlayerProcess()) {
                ClearcutAppEventLogger.init(this, new ClearcutEventLoggerEngine(this));
            }
            SilentFeedbackConfiguration.init(this);
            initPrimes();
        }
    }

    private void initPrimes() {
        DefaultUncaughtExceptionHandlerVerifier.assertHandlerClass("com.google.android.libraries.social.silentfeedback.SilentFeedbackHandler$BackgroundSilentFeedbackHandler");
        final PrimesSettings primesSettings = new PrimesSettings(this);
        if (primesSettings.isPrimesEnabled()) {
            Primes primes = Primes.initialize(PrimesApiProvider.newInstance(this, new PrimesConfigurationsProvider() {
                public PrimesConfigurations get() {
                    return PrimesConfigurations.newBuilder().setMetricTransmitter(TvLauncherApplication.this.getPrimesMetricTransmitter()).setPackageConfigurations(new PrimesPackageConfigurations(primesSettings.isPackageStatsMetricEnabled())).setMemoryConfigurations(new PrimesMemoryConfigurations(primesSettings.isMemoryMetricEnabled())).setCrashConfigurations(new PrimesCrashConfigurations(primesSettings.isCrashMetricEnabled())).build();
                }
            }));
            primes.startMemoryMonitor();
            primes.startCrashMonitor();
            return;
        }
        Log.e(TAG, "PRIMES not enabled");
    }

    /* access modifiers changed from: private */
    @NonNull
    public MetricTransmitter getPrimesMetricTransmitter() {
        return new ClearcutMetricTransmitter(this, new GcoreClearcutLoggerFactoryImpl(), new GcoreGoogleApiClientImpl.BuilderFactory(), new GcoreClearcutApiImpl.Builder(), "TV_LAUNCHER_ANDROID_PRIMES");
    }

    public DoubleClickAdServer getDoubleClickAdServer() {
        if (this.mDoubleClickAdServer == null) {
            synchronized (this.mDoubleClickAdServerLock) {
                if (this.mDoubleClickAdServer == null) {
                    this.mDoubleClickAdServer = new DoubleClickAdServer(this);
                }
            }
        }
        return this.mDoubleClickAdServer;
    }

    public AdsManager getAdsManager() {
        if (this.mAdsManager == null) {
            synchronized (this.mAdsManagerLock) {
                if (this.mAdsManager == null) {
                    this.mAdsManager = new AdsManager(this);
                }
            }
        }
        return this.mAdsManager;
    }

    public DoubleClickAdConfigSerializer getDoubleClickAdConfigSerializer() {
        if (this.mDoubleClickAdConfigSerializer == null) {
            synchronized (this.mDoubleClickAdConfigSerializerLock) {
                if (this.mDoubleClickAdConfigSerializer == null) {
                    this.mDoubleClickAdConfigSerializer = new DoubleClickAdConfigSerializer();
                }
            }
        }
        return this.mDoubleClickAdConfigSerializer;
    }

    public DirectAdConfigSerializer getDirectAdConfigSerializer() {
        if (this.mDirectAdConfigSerializer == null) {
            synchronized (this.mDirectAdConfigSerializerLock) {
                if (this.mDirectAdConfigSerializer == null) {
                    this.mDirectAdConfigSerializer = new DirectAdConfigSerializer();
                }
            }
        }
        return this.mDirectAdConfigSerializer;
    }

    public OutstreamVideoAdFactory getOutstreamVideoAdFactory() {
        if (this.mOutstreamVideoAdFactory == null) {
            synchronized (this.mOutstreamVideoAdFactoryLock) {
                if (this.mOutstreamVideoAdFactory == null) {
                    this.mOutstreamVideoAdFactory = new OutstreamVideoAdFactory();
                }
            }
        }
        return this.mOutstreamVideoAdFactory;
    }

    public AdsUtil getAdsUtil() {
        if (this.mAdsUtil == null) {
            synchronized (this.mAdsUtilLock) {
                if (this.mAdsUtil == null) {
                    this.mAdsUtil = new AdsUtil();
                }
            }
        }
        return this.mAdsUtil;
    }
}
