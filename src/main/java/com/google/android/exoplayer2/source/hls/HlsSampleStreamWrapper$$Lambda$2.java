package com.google.android.exoplayer2.source.hls;

import com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper;

final /* synthetic */ class HlsSampleStreamWrapper$$Lambda$2 implements Runnable {
    private final HlsSampleStreamWrapper.Callback arg$1;

    private HlsSampleStreamWrapper$$Lambda$2(HlsSampleStreamWrapper.Callback callback) {
        this.arg$1 = callback;
    }

    static Runnable get$Lambda(HlsSampleStreamWrapper.Callback callback) {
        return new HlsSampleStreamWrapper$$Lambda$2(callback);
    }

    public void run() {
        this.arg$1.onPrepared();
    }
}
