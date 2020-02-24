package com.google.android.libraries.performance.primes;

import logs.proto.wireless.performance.mobile.ExtensionMetric;

final /* synthetic */ class BatteryMetricService$$Lambda$3 implements Runnable {
    private final BatteryMetricService arg$1;
    private final PrimesBatterySnapshot arg$2;
    private final PrimesBatterySnapshot arg$3;
    private final String arg$4;
    private final boolean arg$5;
    private final ExtensionMetric.MetricExtension arg$6;

    BatteryMetricService$$Lambda$3(BatteryMetricService batteryMetricService, PrimesBatterySnapshot primesBatterySnapshot, PrimesBatterySnapshot primesBatterySnapshot2, String str, boolean z, ExtensionMetric.MetricExtension metricExtension) {
        this.arg$1 = batteryMetricService;
        this.arg$2 = primesBatterySnapshot;
        this.arg$3 = primesBatterySnapshot2;
        this.arg$4 = str;
        this.arg$5 = z;
        this.arg$6 = metricExtension;
    }

    public void run() {
        this.arg$1.lambda$recordBatteryStatsDiff$1$BatteryMetricService(this.arg$2, this.arg$3, this.arg$4, this.arg$5, this.arg$6);
    }
}
