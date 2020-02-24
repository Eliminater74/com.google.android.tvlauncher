package com.google.android.exoplayer2.source;

import com.google.android.exoplayer2.source.MediaSourceEventListener;

final /* synthetic */ class MediaSourceEventListener$EventDispatcher$$Lambda$8 implements Runnable {
    private final MediaSourceEventListener.EventDispatcher arg$1;
    private final MediaSourceEventListener arg$2;
    private final MediaSourceEventListener.MediaLoadData arg$3;

    MediaSourceEventListener$EventDispatcher$$Lambda$8(MediaSourceEventListener.EventDispatcher eventDispatcher, MediaSourceEventListener mediaSourceEventListener, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        this.arg$1 = eventDispatcher;
        this.arg$2 = mediaSourceEventListener;
        this.arg$3 = mediaLoadData;
    }

    public void run() {
        this.arg$1.mo14392x9a021abe(this.arg$2, this.arg$3);
    }
}
