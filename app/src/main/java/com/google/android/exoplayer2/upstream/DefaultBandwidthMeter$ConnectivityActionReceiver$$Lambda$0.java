package com.google.android.exoplayer2.upstream;

final /* synthetic */ class DefaultBandwidthMeter$ConnectivityActionReceiver$$Lambda$0 implements Runnable {
    private final DefaultBandwidthMeter.ConnectivityActionReceiver arg$1;
    private final DefaultBandwidthMeter arg$2;

    DefaultBandwidthMeter$ConnectivityActionReceiver$$Lambda$0(DefaultBandwidthMeter.ConnectivityActionReceiver connectivityActionReceiver, DefaultBandwidthMeter defaultBandwidthMeter) {
        this.arg$1 = connectivityActionReceiver;
        this.arg$2 = defaultBandwidthMeter;
    }

    public void run() {
        this.arg$1.mo15526xc0d413df(this.arg$2);
    }
}
