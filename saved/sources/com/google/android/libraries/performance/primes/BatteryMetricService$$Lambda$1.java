package com.google.android.libraries.performance.primes;

import android.os.SystemClock;
import com.google.android.libraries.performance.primes.battery.BatteryCapture;

final /* synthetic */ class BatteryMetricService$$Lambda$1 implements BatteryCapture.TimeCapture {
    static final BatteryCapture.TimeCapture $instance = new BatteryMetricService$$Lambda$1();

    private BatteryMetricService$$Lambda$1() {
    }

    public long getTime() {
        return SystemClock.elapsedRealtime();
    }
}
