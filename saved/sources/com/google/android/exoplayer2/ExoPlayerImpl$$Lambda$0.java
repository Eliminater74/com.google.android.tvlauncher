package com.google.android.exoplayer2;

import com.google.android.exoplayer2.BasePlayer;
import com.google.android.exoplayer2.Player;

final /* synthetic */ class ExoPlayerImpl$$Lambda$0 implements BasePlayer.ListenerInvocation {
    private final boolean arg$1;
    private final int arg$2;

    ExoPlayerImpl$$Lambda$0(boolean z, int i) {
        this.arg$1 = z;
        this.arg$2 = i;
    }

    public void invokeListener(Player.EventListener eventListener) {
        eventListener.onPlayerStateChanged(this.arg$1, this.arg$2);
    }
}
