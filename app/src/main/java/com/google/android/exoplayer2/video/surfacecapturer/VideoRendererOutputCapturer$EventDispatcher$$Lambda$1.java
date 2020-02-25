package com.google.android.exoplayer2.video.surfacecapturer;

import android.graphics.Bitmap;

final /* synthetic */ class VideoRendererOutputCapturer$EventDispatcher$$Lambda$1 implements Runnable {
    private final VideoRendererOutputCapturer.EventDispatcher arg$1;
    private final Bitmap arg$2;

    VideoRendererOutputCapturer$EventDispatcher$$Lambda$1(VideoRendererOutputCapturer.EventDispatcher eventDispatcher, Bitmap bitmap) {
        this.arg$1 = eventDispatcher;
        this.arg$2 = bitmap;
    }

    public void run() {
        this.arg$1.mo15904x73e61860(this.arg$2);
    }
}
