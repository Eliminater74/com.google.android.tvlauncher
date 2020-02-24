package com.google.android.libraries.performance.primes;

import java.util.concurrent.Callable;
import logs.proto.wireless.performance.mobile.BatteryMetric;

final /* synthetic */ class BatteryMetricService$$Lambda$2 implements Callable {
    private final BatteryMetricService arg$1;
    private final BatteryMetric.BatteryStatsDiff.SampleInfo arg$2;
    private final String arg$3;
    private final boolean arg$4;

    BatteryMetricService$$Lambda$2(BatteryMetricService batteryMetricService, BatteryMetric.BatteryStatsDiff.SampleInfo sampleInfo, String str, boolean z) {
        this.arg$1 = batteryMetricService;
        this.arg$2 = sampleInfo;
        this.arg$3 = str;
        this.arg$4 = z;
    }

    public Object call() {
        return this.arg$1.lambda$captureCustomDiffSnapshot$0$BatteryMetricService(this.arg$2, this.arg$3, this.arg$4);
    }
}
