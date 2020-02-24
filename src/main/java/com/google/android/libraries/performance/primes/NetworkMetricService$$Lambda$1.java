package com.google.android.libraries.performance.primes;

final /* synthetic */ class NetworkMetricService$$Lambda$1 implements Runnable {
    private final NetworkMetricService arg$1;

    NetworkMetricService$$Lambda$1(NetworkMetricService networkMetricService) {
        this.arg$1 = networkMetricService;
    }

    public void run() {
        this.arg$1.sendPendingEvents();
    }
}
