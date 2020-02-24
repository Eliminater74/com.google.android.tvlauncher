package android.support.p001v4.media;

import android.media.AudioAttributes;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.p001v4.media.AudioAttributesImplApi21;

@RequiresApi(26)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* renamed from: android.support.v4.media.AudioAttributesImplApi26 */
public class AudioAttributesImplApi26 extends AudioAttributesImplApi21 {
    private static final String TAG = "AudioAttributesCompat26";

    AudioAttributesImplApi26() {
    }

    AudioAttributesImplApi26(AudioAttributes audioAttributes) {
        super(audioAttributes, -1);
    }

    public int getVolumeControlStream() {
        return this.mAudioAttributes.getVolumeControlStream();
    }

    /* renamed from: android.support.v4.media.AudioAttributesImplApi26$Builder */
    static class Builder extends AudioAttributesImplApi21.Builder {
        Builder() {
        }

        Builder(Object aa) {
            super(aa);
        }

        public AudioAttributesImpl build() {
            return new AudioAttributesImplApi26(this.mFwkBuilder.build());
        }

        public Builder setUsage(int usage) {
            this.mFwkBuilder.setUsage(usage);
            return this;
        }
    }
}
