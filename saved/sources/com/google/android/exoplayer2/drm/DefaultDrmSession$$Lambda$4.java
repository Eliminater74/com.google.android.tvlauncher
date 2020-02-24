package com.google.android.exoplayer2.drm;

import com.google.android.exoplayer2.util.EventDispatcher;

final /* synthetic */ class DefaultDrmSession$$Lambda$4 implements EventDispatcher.Event {
    static final EventDispatcher.Event $instance = new DefaultDrmSession$$Lambda$4();

    private DefaultDrmSession$$Lambda$4() {
    }

    public void sendTo(Object obj) {
        ((DefaultDrmSessionEventListener) obj).onDrmKeysLoaded();
    }
}
