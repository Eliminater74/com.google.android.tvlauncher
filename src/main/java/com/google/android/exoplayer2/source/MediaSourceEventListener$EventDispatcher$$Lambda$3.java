package com.google.android.exoplayer2.source;

import com.google.android.exoplayer2.source.MediaSourceEventListener;

final /* synthetic */ class MediaSourceEventListener$EventDispatcher$$Lambda$3 implements Runnable {
    private final MediaSourceEventListener.EventDispatcher arg$1;
    private final MediaSourceEventListener arg$2;
    private final MediaSourceEventListener.LoadEventInfo arg$3;
    private final MediaSourceEventListener.MediaLoadData arg$4;

    MediaSourceEventListener$EventDispatcher$$Lambda$3(MediaSourceEventListener.EventDispatcher eventDispatcher, MediaSourceEventListener mediaSourceEventListener, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        this.arg$1 = eventDispatcher;
        this.arg$2 = mediaSourceEventListener;
        this.arg$3 = loadEventInfo;
        this.arg$4 = mediaLoadData;
    }

    public void run() {
        this.arg$1.lambda$loadCompleted$3$MediaSourceEventListener$EventDispatcher(this.arg$2, this.arg$3, this.arg$4);
    }
}
