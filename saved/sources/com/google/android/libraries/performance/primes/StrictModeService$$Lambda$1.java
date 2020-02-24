package com.google.android.libraries.performance.primes;

import android.os.StrictMode;

final /* synthetic */ class StrictModeService$$Lambda$1 implements Runnable {
    static final Runnable $instance = new StrictModeService$$Lambda$1();

    private StrictModeService$$Lambda$1() {
    }

    public void run() {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
    }
}
