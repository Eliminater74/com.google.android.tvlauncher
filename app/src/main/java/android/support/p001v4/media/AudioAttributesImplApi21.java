package android.support.p001v4.media;

import android.annotation.SuppressLint;
import android.media.AudioAttributes;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.p001v4.media.AudioAttributesImpl;

@RequiresApi(21)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* renamed from: android.support.v4.media.AudioAttributesImplApi21 */
public class AudioAttributesImplApi21 implements AudioAttributesImpl {
    private static final String TAG = "AudioAttributesCompat21";
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public AudioAttributes mAudioAttributes;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int mLegacyStreamType;

    AudioAttributesImplApi21() {
        this.mLegacyStreamType = -1;
    }

    AudioAttributesImplApi21(AudioAttributes audioAttributes) {
        this(audioAttributes, -1);
    }

    AudioAttributesImplApi21(AudioAttributes audioAttributes, int explicitLegacyStream) {
        this.mLegacyStreamType = -1;
        this.mAudioAttributes = audioAttributes;
        this.mLegacyStreamType = explicitLegacyStream;
    }

    public Object getAudioAttributes() {
        return this.mAudioAttributes;
    }

    @SuppressLint({"NewApi"})
    public int getVolumeControlStream() {
        return AudioAttributesCompat.toVolumeStreamType(true, getFlags(), getUsage());
    }

    public int getLegacyStreamType() {
        int i = this.mLegacyStreamType;
        if (i != -1) {
            return i;
        }
        return AudioAttributesCompat.toVolumeStreamType(false, getFlags(), getUsage());
    }

    public int getRawLegacyStreamType() {
        return this.mLegacyStreamType;
    }

    public int getContentType() {
        return this.mAudioAttributes.getContentType();
    }

    public int getUsage() {
        return this.mAudioAttributes.getUsage();
    }

    public int getFlags() {
        return this.mAudioAttributes.getFlags();
    }

    @NonNull
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("android.support.v4.media.audio_attrs.FRAMEWORKS", this.mAudioAttributes);
        int i = this.mLegacyStreamType;
        if (i != -1) {
            bundle.putInt("android.support.v4.media.audio_attrs.LEGACY_STREAM_TYPE", i);
        }
        return bundle;
    }

    public int hashCode() {
        return this.mAudioAttributes.hashCode();
    }

    public boolean equals(Object o) {
        if (!(o instanceof AudioAttributesImplApi21)) {
            return false;
        }
        return this.mAudioAttributes.equals(((AudioAttributesImplApi21) o).mAudioAttributes);
    }

    public String toString() {
        return "AudioAttributesCompat: audioattributes=" + this.mAudioAttributes;
    }

    public static AudioAttributesImpl fromBundle(Bundle bundle) {
        AudioAttributes frameworkAttrs;
        if (bundle == null || (frameworkAttrs = (AudioAttributes) bundle.getParcelable("android.support.v4.media.audio_attrs.FRAMEWORKS")) == null) {
            return null;
        }
        return new AudioAttributesImplApi21(frameworkAttrs, bundle.getInt("android.support.v4.media.audio_attrs.LEGACY_STREAM_TYPE", -1));
    }

    /* renamed from: android.support.v4.media.AudioAttributesImplApi21$Builder */
    static class Builder implements AudioAttributesImpl.Builder {
        final AudioAttributes.Builder mFwkBuilder;

        Builder() {
            this.mFwkBuilder = new AudioAttributes.Builder();
        }

        Builder(Object aa) {
            this.mFwkBuilder = new AudioAttributes.Builder((AudioAttributes) aa);
        }

        public AudioAttributesImpl build() {
            return new AudioAttributesImplApi21(this.mFwkBuilder.build());
        }

        public Builder setUsage(int usage) {
            if (usage == 16) {
                usage = 12;
            }
            this.mFwkBuilder.setUsage(usage);
            return this;
        }

        public Builder setContentType(int contentType) {
            this.mFwkBuilder.setContentType(contentType);
            return this;
        }

        public Builder setFlags(int flags) {
            this.mFwkBuilder.setFlags(flags);
            return this;
        }

        public Builder setLegacyStreamType(int streamType) {
            this.mFwkBuilder.setLegacyStreamType(streamType);
            return this;
        }
    }
}
