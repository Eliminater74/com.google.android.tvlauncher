package com.google.android.exoplayer2.source.ads;

import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import java.io.IOException;

final /* synthetic */ class AdsMediaSource$AdPrepareErrorListener$$Lambda$0 implements Runnable {
    private final AdsMediaSource.AdPrepareErrorListener arg$1;
    private final IOException arg$2;

    AdsMediaSource$AdPrepareErrorListener$$Lambda$0(AdsMediaSource.AdPrepareErrorListener adPrepareErrorListener, IOException iOException) {
        this.arg$1 = adPrepareErrorListener;
        this.arg$2 = iOException;
    }

    public void run() {
        this.arg$1.lambda$onPrepareError$0$AdsMediaSource$AdPrepareErrorListener(this.arg$2);
    }
}
