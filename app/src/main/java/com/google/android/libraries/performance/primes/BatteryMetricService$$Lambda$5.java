package com.google.android.libraries.performance.primes;

import java.util.List;

final /* synthetic */ class BatteryMetricService$$Lambda$5 implements Runnable {
    private final BatteryMetricService arg$1;
    private final List arg$2;

    BatteryMetricService$$Lambda$5(BatteryMetricService batteryMetricService, List list) {
        this.arg$1 = batteryMetricService;
        this.arg$2 = list;
    }

    public void run() {
        this.arg$1.lambda$logDeferredData$3$BatteryMetricService(this.arg$2);
    }
}
