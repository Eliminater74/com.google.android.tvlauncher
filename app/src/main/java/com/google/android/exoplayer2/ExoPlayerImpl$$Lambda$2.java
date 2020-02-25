package com.google.android.exoplayer2;

final /* synthetic */ class ExoPlayerImpl$$Lambda$2 implements BasePlayer.ListenerInvocation {
    private final boolean arg$1;

    ExoPlayerImpl$$Lambda$2(boolean z) {
        this.arg$1 = z;
    }

    public void invokeListener(Player.EventListener eventListener) {
        eventListener.onShuffleModeEnabledChanged(this.arg$1);
    }
}
