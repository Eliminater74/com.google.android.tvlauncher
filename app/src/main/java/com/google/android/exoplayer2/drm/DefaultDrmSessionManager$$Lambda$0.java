package com.google.android.exoplayer2.drm;

import com.google.android.exoplayer2.util.EventDispatcher;

final /* synthetic */ class DefaultDrmSessionManager$$Lambda$0 implements EventDispatcher.Event {
    private final DefaultDrmSessionManager.MissingSchemeDataException arg$1;

    DefaultDrmSessionManager$$Lambda$0(DefaultDrmSessionManager.MissingSchemeDataException missingSchemeDataException) {
        this.arg$1 = missingSchemeDataException;
    }

    public void sendTo(Object obj) {
        ((DefaultDrmSessionEventListener) obj).onDrmSessionManagerError(this.arg$1);
    }
}
