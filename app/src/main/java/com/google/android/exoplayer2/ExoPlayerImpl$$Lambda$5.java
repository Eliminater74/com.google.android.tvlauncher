package com.google.android.exoplayer2;

final /* synthetic */ class ExoPlayerImpl$$Lambda$5 implements BasePlayer.ListenerInvocation {
    private final ExoPlaybackException arg$1;

    ExoPlayerImpl$$Lambda$5(ExoPlaybackException exoPlaybackException) {
        this.arg$1 = exoPlaybackException;
    }

    public void invokeListener(Player.EventListener eventListener) {
        eventListener.onPlayerError(this.arg$1);
    }
}
