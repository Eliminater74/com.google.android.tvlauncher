package com.google.android.exoplayer2;

final /* synthetic */ class ExoPlayerImpl$$Lambda$4 implements BasePlayer.ListenerInvocation {
    private final PlaybackParameters arg$1;

    ExoPlayerImpl$$Lambda$4(PlaybackParameters playbackParameters) {
        this.arg$1 = playbackParameters;
    }

    public void invokeListener(Player.EventListener eventListener) {
        eventListener.onPlaybackParametersChanged(this.arg$1);
    }
}
