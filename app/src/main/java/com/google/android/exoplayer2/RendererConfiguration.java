package com.google.android.exoplayer2;

import android.support.annotation.Nullable;

public final class RendererConfiguration {
    public static final RendererConfiguration DEFAULT = new RendererConfiguration(0);
    public final int tunnelingAudioSessionId;

    public RendererConfiguration(int tunnelingAudioSessionId2) {
        this.tunnelingAudioSessionId = tunnelingAudioSessionId2;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass() && this.tunnelingAudioSessionId == ((RendererConfiguration) obj).tunnelingAudioSessionId) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.tunnelingAudioSessionId;
    }
}
