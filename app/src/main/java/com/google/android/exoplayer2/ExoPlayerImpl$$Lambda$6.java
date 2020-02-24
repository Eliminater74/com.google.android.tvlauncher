package com.google.android.exoplayer2;

import com.google.android.exoplayer2.BasePlayer;
import java.util.concurrent.CopyOnWriteArrayList;

final /* synthetic */ class ExoPlayerImpl$$Lambda$6 implements Runnable {
    private final CopyOnWriteArrayList arg$1;
    private final BasePlayer.ListenerInvocation arg$2;

    ExoPlayerImpl$$Lambda$6(CopyOnWriteArrayList copyOnWriteArrayList, BasePlayer.ListenerInvocation listenerInvocation) {
        this.arg$1 = copyOnWriteArrayList;
        this.arg$2 = listenerInvocation;
    }

    public void run() {
        ExoPlayerImpl.invokeAll(this.arg$1, this.arg$2);
    }
}
