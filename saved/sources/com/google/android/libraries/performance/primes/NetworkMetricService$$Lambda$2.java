package com.google.android.libraries.performance.primes;

final /* synthetic */ class NetworkMetricService$$Lambda$2 implements Runnable {
    private final NetworkMetricService arg$1;
    private final NetworkEvent[] arg$2;

    NetworkMetricService$$Lambda$2(NetworkMetricService networkMetricService, NetworkEvent[] networkEventArr) {
        this.arg$1 = networkMetricService;
        this.arg$2 = networkEventArr;
    }

    public void run() {
        this.arg$1.lambda$sendPendingEvents$1$NetworkMetricService(this.arg$2);
    }
}
