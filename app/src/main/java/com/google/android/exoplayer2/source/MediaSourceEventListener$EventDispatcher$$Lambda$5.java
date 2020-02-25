package com.google.android.exoplayer2.source;

import java.io.IOException;

final /* synthetic */ class MediaSourceEventListener$EventDispatcher$$Lambda$5 implements Runnable {
    private final MediaSourceEventListener.EventDispatcher arg$1;
    private final MediaSourceEventListener arg$2;
    private final MediaSourceEventListener.LoadEventInfo arg$3;
    private final MediaSourceEventListener.MediaLoadData arg$4;
    private final IOException arg$5;
    private final boolean arg$6;

    MediaSourceEventListener$EventDispatcher$$Lambda$5(MediaSourceEventListener.EventDispatcher eventDispatcher, MediaSourceEventListener mediaSourceEventListener, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData, IOException iOException, boolean z) {
        this.arg$1 = eventDispatcher;
        this.arg$2 = mediaSourceEventListener;
        this.arg$3 = loadEventInfo;
        this.arg$4 = mediaLoadData;
        this.arg$5 = iOException;
        this.arg$6 = z;
    }

    public void run() {
        this.arg$1.lambda$loadError$5$MediaSourceEventListener$EventDispatcher(this.arg$2, this.arg$3, this.arg$4, this.arg$5, this.arg$6);
    }
}
