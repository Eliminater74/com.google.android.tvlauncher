package com.google.android.exoplayer2;

final /* synthetic */ class ExoPlayerImpl$PlaybackInfoUpdate$$Lambda$1 implements BasePlayer.ListenerInvocation {
    private final ExoPlayerImpl.PlaybackInfoUpdate arg$1;

    ExoPlayerImpl$PlaybackInfoUpdate$$Lambda$1(ExoPlayerImpl.PlaybackInfoUpdate playbackInfoUpdate) {
        this.arg$1 = playbackInfoUpdate;
    }

    public void invokeListener(Player.EventListener eventListener) {
        this.arg$1.lambda$run$1$ExoPlayerImpl$PlaybackInfoUpdate(eventListener);
    }
}
