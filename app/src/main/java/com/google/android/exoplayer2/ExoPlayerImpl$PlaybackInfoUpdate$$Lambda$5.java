package com.google.android.exoplayer2;

final /* synthetic */ class ExoPlayerImpl$PlaybackInfoUpdate$$Lambda$5 implements BasePlayer.ListenerInvocation {
    static final BasePlayer.ListenerInvocation $instance = new ExoPlayerImpl$PlaybackInfoUpdate$$Lambda$5();

    private ExoPlayerImpl$PlaybackInfoUpdate$$Lambda$5() {
    }

    public void invokeListener(Player.EventListener eventListener) {
        eventListener.onSeekProcessed();
    }
}
