package com.google.android.exoplayer2;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;

public final class PlaybackParameters {
    public static final PlaybackParameters DEFAULT = new PlaybackParameters(1.0f);
    public final float pitch;
    private final int scaledUsPerMs;
    public final boolean skipSilence;
    public final float speed;

    public PlaybackParameters(float speed2) {
        this(speed2, 1.0f, false);
    }

    public PlaybackParameters(float speed2, float pitch2) {
        this(speed2, pitch2, false);
    }

    public PlaybackParameters(float speed2, float pitch2, boolean skipSilence2) {
        boolean z = true;
        Assertions.checkArgument(speed2 > 0.0f);
        Assertions.checkArgument(pitch2 <= 0.0f ? false : z);
        this.speed = speed2;
        this.pitch = pitch2;
        this.skipSilence = skipSilence2;
        this.scaledUsPerMs = Math.round(1000.0f * speed2);
    }

    public long getMediaTimeUsForPlayoutTimeMs(long timeMs) {
        return ((long) this.scaledUsPerMs) * timeMs;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PlaybackParameters other = (PlaybackParameters) obj;
        if (this.speed == other.speed && this.pitch == other.pitch && this.skipSilence == other.skipSilence) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((17 * 31) + Float.floatToRawIntBits(this.speed)) * 31) + Float.floatToRawIntBits(this.pitch)) * 31) + (this.skipSilence ? 1 : 0);
    }
}
