package com.google.android.libraries.performance.primes;

import com.google.android.libraries.performance.primes.battery.BatteryCapture;

final /* synthetic */ class BatteryMetricService$$Lambda$0 implements BatteryCapture.TimeCapture {
    static final BatteryCapture.TimeCapture $instance = new BatteryMetricService$$Lambda$0();

    private BatteryMetricService$$Lambda$0() {
    }

    public long getTime() {
        return System.currentTimeMillis();
    }
}
