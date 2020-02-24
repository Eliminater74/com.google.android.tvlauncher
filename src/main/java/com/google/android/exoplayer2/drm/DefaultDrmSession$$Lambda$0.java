package com.google.android.exoplayer2.drm;

import com.google.android.exoplayer2.util.EventDispatcher;

final /* synthetic */ class DefaultDrmSession$$Lambda$0 implements EventDispatcher.Event {
    static final EventDispatcher.Event $instance = new DefaultDrmSession$$Lambda$0();

    private DefaultDrmSession$$Lambda$0() {
    }

    public void sendTo(Object obj) {
        ((DefaultDrmSessionEventListener) obj).onDrmSessionReleased();
    }
}
