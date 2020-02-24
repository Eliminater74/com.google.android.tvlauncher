package com.google.android.exoplayer2.drm;

import com.google.android.exoplayer2.util.EventDispatcher;

final /* synthetic */ class DefaultDrmSession$$Lambda$1 implements EventDispatcher.Event {
    static final EventDispatcher.Event $instance = new DefaultDrmSession$$Lambda$1();

    private DefaultDrmSession$$Lambda$1() {
    }

    public void sendTo(Object obj) {
        ((DefaultDrmSessionEventListener) obj).onDrmSessionAcquired();
    }
}
