package com.google.android.exoplayer2.video;

import com.google.android.exoplayer2.Format;

final /* synthetic */ class VideoRendererEventListener$EventDispatcher$$Lambda$2 implements Runnable {
    private final VideoRendererEventListener.EventDispatcher arg$1;
    private final Format arg$2;

    VideoRendererEventListener$EventDispatcher$$Lambda$2(VideoRendererEventListener.EventDispatcher eventDispatcher, Format format) {
        this.arg$1 = eventDispatcher;
        this.arg$2 = format;
    }

    public void run() {
        this.arg$1.mo15874xe7570b3(this.arg$2);
    }
}
