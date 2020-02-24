package com.google.android.exoplayer2;

import com.google.android.exoplayer2.BasePlayer;
import com.google.android.exoplayer2.Player;

final /* synthetic */ class ExoPlayerImpl$$Lambda$3 implements BasePlayer.ListenerInvocation {
    static final BasePlayer.ListenerInvocation $instance = new ExoPlayerImpl$$Lambda$3();

    private ExoPlayerImpl$$Lambda$3() {
    }

    public void invokeListener(Player.EventListener eventListener) {
        eventListener.onPositionDiscontinuity(1);
    }
}
