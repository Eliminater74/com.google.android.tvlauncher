package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;

final /* synthetic */ class AudioRendererEventListener$EventDispatcher$$Lambda$4 implements Runnable {
    private final AudioRendererEventListener.EventDispatcher arg$1;
    private final DecoderCounters arg$2;

    AudioRendererEventListener$EventDispatcher$$Lambda$4(AudioRendererEventListener.EventDispatcher eventDispatcher, DecoderCounters decoderCounters) {
        this.arg$1 = eventDispatcher;
        this.arg$2 = decoderCounters;
    }

    public void run() {
        this.arg$1.lambda$disabled$4$AudioRendererEventListener$EventDispatcher(this.arg$2);
    }
}
