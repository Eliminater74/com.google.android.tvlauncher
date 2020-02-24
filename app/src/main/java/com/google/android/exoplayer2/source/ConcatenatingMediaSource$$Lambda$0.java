package com.google.android.exoplayer2.source;

import android.os.Handler;
import android.os.Message;

final /* synthetic */ class ConcatenatingMediaSource$$Lambda$0 implements Handler.Callback {
    private final ConcatenatingMediaSource arg$1;

    ConcatenatingMediaSource$$Lambda$0(ConcatenatingMediaSource concatenatingMediaSource) {
        this.arg$1 = concatenatingMediaSource;
    }

    public boolean handleMessage(Message message) {
        return this.arg$1.bridge$lambda$0$ConcatenatingMediaSource(message);
    }
}
