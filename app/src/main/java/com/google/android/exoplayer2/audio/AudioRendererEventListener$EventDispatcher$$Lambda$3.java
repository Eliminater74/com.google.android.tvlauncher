package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.audio.AudioRendererEventListener;

final /* synthetic */ class AudioRendererEventListener$EventDispatcher$$Lambda$3 implements Runnable {
    private final AudioRendererEventListener.EventDispatcher arg$1;
    private final int arg$2;
    private final long arg$3;
    private final long arg$4;

    AudioRendererEventListener$EventDispatcher$$Lambda$3(AudioRendererEventListener.EventDispatcher eventDispatcher, int i, long j, long j2) {
        this.arg$1 = eventDispatcher;
        this.arg$2 = i;
        this.arg$3 = j;
        this.arg$4 = j2;
    }

    public void run() {
        this.arg$1.mo13433xe45e91e2(this.arg$2, this.arg$3, this.arg$4);
    }
}
