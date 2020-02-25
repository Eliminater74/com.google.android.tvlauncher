package com.google.android.exoplayer2.audio;

import android.annotation.TargetApi;
import android.support.annotation.Nullable;

public final class AudioAttributes {
    public static final AudioAttributes DEFAULT = new Builder().build();
    public final int contentType;
    public final int flags;
    public final int usage;
    @Nullable
    private android.media.AudioAttributes audioAttributesV21;

    private AudioAttributes(int contentType2, int flags2, int usage2) {
        this.contentType = contentType2;
        this.flags = flags2;
        this.usage = usage2;
    }

    @TargetApi(21)
    public android.media.AudioAttributes getAudioAttributesV21() {
        if (this.audioAttributesV21 == null) {
            this.audioAttributesV21 = new AudioAttributes.Builder().setContentType(this.contentType).setFlags(this.flags).setUsage(this.usage).build();
        }
        return this.audioAttributesV21;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AudioAttributes other = (AudioAttributes) obj;
        if (this.contentType == other.contentType && this.flags == other.flags && this.usage == other.usage) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((17 * 31) + this.contentType) * 31) + this.flags) * 31) + this.usage;
    }

    public static final class Builder {
        private int contentType = 0;
        private int flags = 0;
        private int usage = 1;

        public Builder setContentType(int contentType2) {
            this.contentType = contentType2;
            return this;
        }

        public Builder setFlags(int flags2) {
            this.flags = flags2;
            return this;
        }

        public Builder setUsage(int usage2) {
            this.usage = usage2;
            return this;
        }

        public AudioAttributes build() {
            return new AudioAttributes(this.contentType, this.flags, this.usage);
        }
    }
}
