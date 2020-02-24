package com.google.android.exoplayer2.drm;

import com.google.android.exoplayer2.util.EventDispatcher;

final /* synthetic */ class DefaultDrmSession$$Lambda$5 implements EventDispatcher.Event {
    private final Exception arg$1;

    DefaultDrmSession$$Lambda$5(Exception exc) {
        this.arg$1 = exc;
    }

    public void sendTo(Object obj) {
        ((DefaultDrmSessionEventListener) obj).onDrmSessionManagerError(this.arg$1);
    }
}
