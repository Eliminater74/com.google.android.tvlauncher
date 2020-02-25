package com.google.android.libraries.performance.primes.transmitter.impl;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutApi;
import com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLogger;
import com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLoggerFactory;
import com.google.android.libraries.gcoreclient.common.api.GcoreGoogleApiClient;
import com.google.android.libraries.gcoreclient.common.api.GcoreResultCallback;
import com.google.android.libraries.gcoreclient.common.api.GcoreStatus;
import com.google.android.libraries.performance.primes.PrimesLog;
import com.google.android.libraries.performance.primes.transmitter.AccountProvider;
import com.google.android.libraries.stitch.util.Preconditions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import logs.proto.wireless.performance.mobile.SystemHealthProto;

public final class ClearcutMetricTransmitter extends HashedNamesTransmitter {
    private static final String DEV_DISABLE_CLEARCUT = "primes.dev.disable_clearcut";
    private static final String DUMP_TAG = "PrimesClearcutBinaryLog";
    private static final String TAG = "ClearcutTransmitter";
    @VisibleForTesting
    final GcoreResultCallback<GcoreStatus> resultCallback;
    private final AccountProvider accountProvider;
    private final boolean anonymous;
    private final GcoreClearcutLoggerFactory clearcutLoggerFactory;
    private final Map<String, GcoreClearcutLogger> clearcutLoggers;
    private final Context context;
    private final Object lock;
    private final String logSource;

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.libraries.performance.primes.transmitter.impl.ClearcutMetricTransmitter.<init>(android.content.Context, com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLoggerFactory, java.lang.String, com.google.android.libraries.performance.primes.transmitter.AccountProvider, boolean):void
     arg types: [android.content.Context, com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLoggerFactory, java.lang.String, com.google.android.libraries.performance.primes.transmitter.AccountProvider, int]
     candidates:
      com.google.android.libraries.performance.primes.transmitter.impl.ClearcutMetricTransmitter.<init>(android.content.Context, com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLoggerFactory, com.google.android.libraries.gcoreclient.common.api.GcoreGoogleApiClient$Builder, com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutApi$Builder, java.lang.String):void
      com.google.android.libraries.performance.primes.transmitter.impl.ClearcutMetricTransmitter.<init>(android.content.Context, com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLoggerFactory, com.google.android.libraries.gcoreclient.common.api.GcoreGoogleApiClient$BuilderFactory, com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutApi$Builder, java.lang.String):void
      com.google.android.libraries.performance.primes.transmitter.impl.ClearcutMetricTransmitter.<init>(android.content.Context, com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLoggerFactory, java.lang.String, com.google.android.libraries.performance.primes.transmitter.AccountProvider, boolean):void */
    @Deprecated
    public ClearcutMetricTransmitter(Context context2, GcoreClearcutLoggerFactory clearcutLoggerFactory2, GcoreGoogleApiClient.Builder apiClientBuilder, GcoreClearcutApi.Builder clearcutApiBuilder, String logSource2) {
        this(context2, clearcutLoggerFactory2, logSource2, AccountProvider.NOOP_PROVIDER, false);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.libraries.performance.primes.transmitter.impl.ClearcutMetricTransmitter.<init>(android.content.Context, com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLoggerFactory, java.lang.String, com.google.android.libraries.performance.primes.transmitter.AccountProvider, boolean):void
     arg types: [android.content.Context, com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLoggerFactory, java.lang.String, com.google.android.libraries.performance.primes.transmitter.AccountProvider, int]
     candidates:
      com.google.android.libraries.performance.primes.transmitter.impl.ClearcutMetricTransmitter.<init>(android.content.Context, com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLoggerFactory, com.google.android.libraries.gcoreclient.common.api.GcoreGoogleApiClient$Builder, com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutApi$Builder, java.lang.String):void
      com.google.android.libraries.performance.primes.transmitter.impl.ClearcutMetricTransmitter.<init>(android.content.Context, com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLoggerFactory, com.google.android.libraries.gcoreclient.common.api.GcoreGoogleApiClient$BuilderFactory, com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutApi$Builder, java.lang.String):void
      com.google.android.libraries.performance.primes.transmitter.impl.ClearcutMetricTransmitter.<init>(android.content.Context, com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLoggerFactory, java.lang.String, com.google.android.libraries.performance.primes.transmitter.AccountProvider, boolean):void */
    @Deprecated
    public ClearcutMetricTransmitter(Context context2, GcoreClearcutLoggerFactory clearcutLoggerFactory2, GcoreGoogleApiClient.BuilderFactory apiClientBuilderFactory, GcoreClearcutApi.Builder clearcutApiBuilder, String logSource2) {
        this(context2, clearcutLoggerFactory2, logSource2, AccountProvider.NOOP_PROVIDER, false);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.libraries.performance.primes.transmitter.impl.ClearcutMetricTransmitter.<init>(android.content.Context, com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLoggerFactory, java.lang.String, com.google.android.libraries.performance.primes.transmitter.AccountProvider, boolean):void
     arg types: [android.content.Context, com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLoggerFactory, java.lang.String, com.google.android.libraries.performance.primes.transmitter.AccountProvider, int]
     candidates:
      com.google.android.libraries.performance.primes.transmitter.impl.ClearcutMetricTransmitter.<init>(android.content.Context, com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLoggerFactory, com.google.android.libraries.gcoreclient.common.api.GcoreGoogleApiClient$Builder, com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutApi$Builder, java.lang.String):void
      com.google.android.libraries.performance.primes.transmitter.impl.ClearcutMetricTransmitter.<init>(android.content.Context, com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLoggerFactory, com.google.android.libraries.gcoreclient.common.api.GcoreGoogleApiClient$BuilderFactory, com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutApi$Builder, java.lang.String):void
      com.google.android.libraries.performance.primes.transmitter.impl.ClearcutMetricTransmitter.<init>(android.content.Context, com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLoggerFactory, java.lang.String, com.google.android.libraries.performance.primes.transmitter.AccountProvider, boolean):void */
    @Deprecated
    public ClearcutMetricTransmitter(Context context2, GcoreClearcutLoggerFactory clearcutLoggerFactory2, GcoreGoogleApiClient.BuilderFactory apiClientBuilderFactory, GcoreClearcutApi.Builder clearcutApiBuilder, String logSource2, AccountProvider accountProvider2) {
        this(context2, clearcutLoggerFactory2, logSource2, accountProvider2, false);
    }

    private ClearcutMetricTransmitter(Context context2, GcoreClearcutLoggerFactory clearcutLoggerFactory2, String logSource2, AccountProvider accountProvider2, boolean anonymous2) {
        this.lock = new Object();
        this.clearcutLoggers = Collections.synchronizedMap(new HashMap());
        this.resultCallback = new GcoreResultCallback<GcoreStatus>() {
            public void onResult(GcoreStatus gcoreStatus) {
                ClearcutMetricTransmitter.this.handleResult(gcoreStatus);
            }
        };
        this.context = context2.getApplicationContext();
        this.clearcutLoggerFactory = (GcoreClearcutLoggerFactory) Preconditions.checkNotNull(clearcutLoggerFactory2);
        this.logSource = (String) Preconditions.checkNotNull(logSource2);
        this.accountProvider = (AccountProvider) Preconditions.checkNotNull(accountProvider2);
        this.anonymous = anonymous2;
    }

    private static void logSystemHealthMetric(SystemHealthProto.SystemHealthMetric msg) {
        if (PrimesLog.vLoggable(TAG)) {
            PrimesLog.m52v(TAG, msg.toString(), new Object[0]);
        } else if (PrimesLog.dLoggable(TAG)) {
            String metricString = null;
            if (msg.hasPrimesStats()) {
                metricString = "primes stats";
            }
            if (msg.hasNetworkUsageMetric()) {
                metricString = "network metric";
            }
            if (msg.hasTimerMetric()) {
                metricString = "timer metric";
            }
            if (msg.hasMemoryUsageMetric()) {
                metricString = "memory metric";
            }
            if (msg.hasBatteryUsageMetric()) {
                metricString = "battery metric";
            }
            if (msg.hasCrashMetric()) {
                metricString = "crash metric";
            }
            if (msg.hasJankMetric()) {
                metricString = "jank metric";
            }
            if (msg.hasMemoryLeakMetric()) {
                metricString = "leak metric";
            }
            if (msg.hasPackageMetric()) {
                metricString = "package metric";
            }
            if (msg.hasMagicEyeMetric()) {
                metricString = "magic_eye log";
            }
            if (msg.hasPrimesTrace()) {
                metricString = "trace";
            }
            if (metricString == null) {
                String valueOf = String.valueOf(msg);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 9);
                sb.append("unknown: ");
                sb.append(valueOf);
                metricString = sb.toString();
            }
            PrimesLog.m46d(TAG, "Sending Primes %s", metricString);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /* access modifiers changed from: protected */
    public void sendHashedEvent(SystemHealthProto.SystemHealthMetric message) {
        logSystemHealthMetric(message);
        send(message.toByteArray(), this.logSource);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0067, code lost:
        if (r7.anonymous != false) goto L_0x002a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x006a, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0025, code lost:
        if (r7.anonymous == false) goto L_0x0027;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0027, code lost:
        r1.setUploadAccountName(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x002a, code lost:
        r1.logAsync().setResultCallback(r7.resultCallback);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void send(byte[] r8, java.lang.String r9) {
        /*
            r7 = this;
            java.lang.String r0 = "PrimesClearcutBinaryLog"
            boolean r1 = com.google.android.libraries.performance.primes.PrimesLog.vLoggable(r0)
            r2 = 0
            if (r1 == 0) goto L_0x0013
            r1 = 2
            java.lang.String r1 = android.util.Base64.encodeToString(r8, r1)
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.google.android.libraries.performance.primes.PrimesLog.m52v(r0, r1, r3)
        L_0x0013:
            r0 = 0
            com.google.android.libraries.performance.primes.transmitter.AccountProvider r1 = r7.accountProvider     // Catch:{ Exception -> 0x0036 }
            java.lang.String r1 = r1.getAccountName()     // Catch:{ Exception -> 0x0036 }
            r0 = r1
            com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLogger r1 = r7.getClearcutLogger(r9)
            com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLogEventBuilder r1 = r1.newEvent(r8)
            boolean r2 = r7.anonymous
            if (r2 != 0) goto L_0x002a
        L_0x0027:
            r1.setUploadAccountName(r0)
        L_0x002a:
            com.google.android.libraries.gcoreclient.common.api.GcorePendingResult r2 = r1.logAsync()
            com.google.android.libraries.gcoreclient.common.api.GcoreResultCallback<com.google.android.libraries.gcoreclient.common.api.GcoreStatus> r3 = r7.resultCallback
            r2.setResultCallback(r3)
            goto L_0x006a
        L_0x0034:
            r1 = move-exception
            goto L_0x006b
        L_0x0036:
            r1 = move-exception
            java.lang.String r3 = "ClearcutTransmitter"
            java.lang.String r4 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x0034 }
            java.lang.String r5 = java.lang.String.valueOf(r4)     // Catch:{ all -> 0x0034 }
            int r5 = r5.length()     // Catch:{ all -> 0x0034 }
            int r5 = r5 + 73
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0034 }
            r6.<init>(r5)     // Catch:{ all -> 0x0034 }
            java.lang.String r5 = "Failed to get Account Name, falling back to Zwieback logging, exception: "
            r6.append(r5)     // Catch:{ all -> 0x0034 }
            r6.append(r4)     // Catch:{ all -> 0x0034 }
            java.lang.String r4 = r6.toString()     // Catch:{ all -> 0x0034 }
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0034 }
            com.google.android.libraries.performance.primes.PrimesLog.m46d(r3, r4, r2)     // Catch:{ all -> 0x0034 }
            com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLogger r1 = r7.getClearcutLogger(r9)
            com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLogEventBuilder r1 = r1.newEvent(r8)
            boolean r2 = r7.anonymous
            if (r2 != 0) goto L_0x002a
            goto L_0x0027
        L_0x006a:
            return
        L_0x006b:
            com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLogger r2 = r7.getClearcutLogger(r9)
            com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLogEventBuilder r2 = r2.newEvent(r8)
            boolean r3 = r7.anonymous
            if (r3 != 0) goto L_0x007a
            r2.setUploadAccountName(r0)
        L_0x007a:
            com.google.android.libraries.gcoreclient.common.api.GcorePendingResult r3 = r2.logAsync()
            com.google.android.libraries.gcoreclient.common.api.GcoreResultCallback<com.google.android.libraries.gcoreclient.common.api.GcoreStatus> r4 = r7.resultCallback
            r3.setResultCallback(r4)
            throw r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.performance.primes.transmitter.impl.ClearcutMetricTransmitter.send(byte[], java.lang.String):void");
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public GcoreClearcutLogger getClearcutLogger(String logSource2) {
        GcoreClearcutLogger gcoreClearcutLogger;
        GcoreClearcutLogger logger;
        synchronized (this.lock) {
            if (this.clearcutLoggers.get(logSource2) == null) {
                if (this.anonymous) {
                    logger = this.clearcutLoggerFactory.getAnonymousGcoreClearcutLogger(this.context, logSource2);
                } else {
                    logger = this.clearcutLoggerFactory.getGcoreClearcutLogger(this.context, logSource2, null);
                }
                this.clearcutLoggers.put(logSource2, logger);
            }
            gcoreClearcutLogger = this.clearcutLoggers.get(logSource2);
        }
        return gcoreClearcutLogger;
    }

    /* access modifiers changed from: private */
    public void handleResult(GcoreStatus gcoreStatus) {
        PrimesLog.m52v(TAG, "handleResult, success: %b", Boolean.valueOf(gcoreStatus.isSuccess()));
        if (!gcoreStatus.isSuccess()) {
            PrimesLog.m46d(TAG, "Clearcut logging failed", new Object[0]);
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public Map<String, GcoreClearcutLogger> getLoggers() {
        return this.clearcutLoggers;
    }

    public static final class Builder {
        private AccountProvider accountProvider = AccountProvider.NOOP_PROVIDER;
        private boolean anonymous;
        @Nullable
        private GcoreClearcutLoggerFactory clearcutLoggerFactory;
        @Nullable
        private Context context;
        @Nullable
        private String logSource;

        public Builder setContext(Context context2) {
            this.context = context2;
            return this;
        }

        public Builder setClearcutLoggerFactory(GcoreClearcutLoggerFactory clearcutLoggerFactory2) {
            this.clearcutLoggerFactory = clearcutLoggerFactory2;
            return this;
        }

        public Builder setLogSource(String logSource2) {
            this.logSource = logSource2;
            return this;
        }

        public Builder setAccountProvider(AccountProvider accountProvider2) {
            this.accountProvider = accountProvider2;
            return this;
        }

        public Builder setAnonymous(boolean anonymous2) {
            this.anonymous = anonymous2;
            return this;
        }

        public ClearcutMetricTransmitter build() {
            return new ClearcutMetricTransmitter((Context) Preconditions.checkNotNull(this.context), (GcoreClearcutLoggerFactory) Preconditions.checkNotNull(this.clearcutLoggerFactory), (String) Preconditions.checkNotNull(this.logSource), this.accountProvider, this.anonymous);
        }
    }
}
