package com.google.android.exoplayer2.source.ads;

final /* synthetic */ class AdsMediaSource$$Lambda$1 implements Runnable {
    private final AdsLoader arg$1;

    private AdsMediaSource$$Lambda$1(AdsLoader adsLoader) {
        this.arg$1 = adsLoader;
    }

    static Runnable get$Lambda(AdsLoader adsLoader) {
        return new AdsMediaSource$$Lambda$1(adsLoader);
    }

    public void run() {
        this.arg$1.stop();
    }
}
