package com.google.android.exoplayer2;

final /* synthetic */ class ExoPlayerImplInternal$$Lambda$0 implements Runnable {
    private final ExoPlayerImplInternal arg$1;
    private final PlayerMessage arg$2;

    ExoPlayerImplInternal$$Lambda$0(ExoPlayerImplInternal exoPlayerImplInternal, PlayerMessage playerMessage) {
        this.arg$1 = exoPlayerImplInternal;
        this.arg$2 = playerMessage;
    }

    public void run() {
        this.arg$1.lambda$sendMessageToTargetThread$0$ExoPlayerImplInternal(this.arg$2);
    }
}
