package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;

final /* synthetic */ class AudioRendererEventListener$EventDispatcher$$Lambda$2 implements Runnable {
    private final AudioRendererEventListener.EventDispatcher arg$1;
    private final Format arg$2;

    AudioRendererEventListener$EventDispatcher$$Lambda$2(AudioRendererEventListener.EventDispatcher eventDispatcher, Format format) {
        this.arg$1 = eventDispatcher;
        this.arg$2 = format;
    }

    public void run() {
        this.arg$1.mo13437x2eadf638(this.arg$2);
    }
}
