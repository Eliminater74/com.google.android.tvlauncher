package com.google.android.exoplayer2.source;

final /* synthetic */ class MediaSourceEventListener$EventDispatcher$$Lambda$6 implements Runnable {
    private final MediaSourceEventListener.EventDispatcher arg$1;
    private final MediaSourceEventListener arg$2;
    private final MediaSource.MediaPeriodId arg$3;

    MediaSourceEventListener$EventDispatcher$$Lambda$6(MediaSourceEventListener.EventDispatcher eventDispatcher, MediaSourceEventListener mediaSourceEventListener, MediaSource.MediaPeriodId mediaPeriodId) {
        this.arg$1 = eventDispatcher;
        this.arg$2 = mediaSourceEventListener;
        this.arg$3 = mediaPeriodId;
    }

    public void run() {
        this.arg$1.lambda$readingStarted$6$MediaSourceEventListener$EventDispatcher(this.arg$2, this.arg$3);
    }
}
