package com.google.android.exoplayer2.video.surfacecapturer;

final /* synthetic */ class VideoRendererOutputCapturer$EventDispatcher$$Lambda$0 implements Runnable {
    private final VideoRendererOutputCapturer.EventDispatcher arg$1;
    private final int arg$2;
    private final int arg$3;

    VideoRendererOutputCapturer$EventDispatcher$$Lambda$0(VideoRendererOutputCapturer.EventDispatcher eventDispatcher, int i, int i2) {
        this.arg$1 = eventDispatcher;
        this.arg$2 = i;
        this.arg$3 = i2;
    }

    public void run() {
        this.arg$1.mo15902xf0df60d4(this.arg$2, this.arg$3);
    }
}
