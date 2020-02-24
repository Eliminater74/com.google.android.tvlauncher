package com.google.android.exoplayer2.source.ads;

import com.google.android.exoplayer2.source.ads.AdsMediaSource;

final /* synthetic */ class AdsMediaSource$$Lambda$0 implements Runnable {
    private final AdsMediaSource arg$1;
    private final AdsMediaSource.ComponentListener arg$2;

    AdsMediaSource$$Lambda$0(AdsMediaSource adsMediaSource, AdsMediaSource.ComponentListener componentListener) {
        this.arg$1 = adsMediaSource;
        this.arg$2 = componentListener;
    }

    public void run() {
        this.arg$1.lambda$prepareSourceInternal$0$AdsMediaSource(this.arg$2);
    }
}
