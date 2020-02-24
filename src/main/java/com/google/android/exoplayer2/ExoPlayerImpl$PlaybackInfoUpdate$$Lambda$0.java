package com.google.android.exoplayer2;

import com.google.android.exoplayer2.BasePlayer;
import com.google.android.exoplayer2.ExoPlayerImpl;
import com.google.android.exoplayer2.Player;

final /* synthetic */ class ExoPlayerImpl$PlaybackInfoUpdate$$Lambda$0 implements BasePlayer.ListenerInvocation {
    private final ExoPlayerImpl.PlaybackInfoUpdate arg$1;

    ExoPlayerImpl$PlaybackInfoUpdate$$Lambda$0(ExoPlayerImpl.PlaybackInfoUpdate playbackInfoUpdate) {
        this.arg$1 = playbackInfoUpdate;
    }

    public void invokeListener(Player.EventListener eventListener) {
        this.arg$1.lambda$run$0$ExoPlayerImpl$PlaybackInfoUpdate(eventListener);
    }
}
