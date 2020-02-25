package com.google.android.exoplayer2.video.surfacecapturer;

final /* synthetic */ class VideoRendererOutputCapturer$EventDispatcher$$Lambda$2 implements Runnable {
    private final VideoRendererOutputCapturer.EventDispatcher arg$1;
    private final Exception arg$2;

    VideoRendererOutputCapturer$EventDispatcher$$Lambda$2(VideoRendererOutputCapturer.EventDispatcher eventDispatcher, Exception exc) {
        this.arg$1 = eventDispatcher;
        this.arg$2 = exc;
    }

    public void run() {
        this.arg$1.mo15903xcbe77e5(this.arg$2);
    }
}
