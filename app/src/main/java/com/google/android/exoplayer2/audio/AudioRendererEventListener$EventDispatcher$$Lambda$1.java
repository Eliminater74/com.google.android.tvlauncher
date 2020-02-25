package com.google.android.exoplayer2.audio;

final /* synthetic */ class AudioRendererEventListener$EventDispatcher$$Lambda$1 implements Runnable {
    private final AudioRendererEventListener.EventDispatcher arg$1;
    private final String arg$2;
    private final long arg$3;
    private final long arg$4;

    AudioRendererEventListener$EventDispatcher$$Lambda$1(AudioRendererEventListener.EventDispatcher eventDispatcher, String str, long j, long j2) {
        this.arg$1 = eventDispatcher;
        this.arg$2 = str;
        this.arg$3 = j;
        this.arg$4 = j2;
    }

    public void run() {
        this.arg$1.mo13434xba417f1c(this.arg$2, this.arg$3, this.arg$4);
    }
}
