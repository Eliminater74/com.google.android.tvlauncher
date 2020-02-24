package com.google.android.exoplayer2;

import com.google.android.exoplayer2.BasePlayer;
import com.google.android.exoplayer2.Player;

final /* synthetic */ class ExoPlayerImpl$$Lambda$1 implements BasePlayer.ListenerInvocation {
    private final int arg$1;

    ExoPlayerImpl$$Lambda$1(int i) {
        this.arg$1 = i;
    }

    public void invokeListener(Player.EventListener eventListener) {
        eventListener.onRepeatModeChanged(this.arg$1);
    }
}
