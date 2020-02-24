package com.google.android.exoplayer2.drm;

import com.google.android.exoplayer2.util.EventDispatcher;

final /* synthetic */ class DefaultDrmSession$$Lambda$2 implements EventDispatcher.Event {
    static final EventDispatcher.Event $instance = new DefaultDrmSession$$Lambda$2();

    private DefaultDrmSession$$Lambda$2() {
    }

    public void sendTo(Object obj) {
        ((DefaultDrmSessionEventListener) obj).onDrmKeysRestored();
    }
}
