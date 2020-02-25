package com.google.android.exoplayer2.video;

final /* synthetic */ class VideoRendererEventListener$EventDispatcher$$Lambda$3 implements Runnable {
    private final VideoRendererEventListener.EventDispatcher arg$1;
    private final int arg$2;
    private final long arg$3;

    VideoRendererEventListener$EventDispatcher$$Lambda$3(VideoRendererEventListener.EventDispatcher eventDispatcher, int i, long j) {
        this.arg$1 = eventDispatcher;
        this.arg$2 = i;
        this.arg$3 = j;
    }

    public void run() {
        this.arg$1.mo15872xf7e95759(this.arg$2, this.arg$3);
    }
}
