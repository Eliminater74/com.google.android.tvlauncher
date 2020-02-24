package android.support.p001v4.media;

import android.media.AudioAttributes;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.p001v4.media.AudioAttributesImpl;
import android.support.p001v4.media.AudioAttributesImplApi21;
import android.support.p001v4.media.AudioAttributesImplApi26;
import android.support.p001v4.media.AudioAttributesImplBase;
import android.util.SparseIntArray;
import androidx.versionedparcelable.VersionedParcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* renamed from: android.support.v4.media.AudioAttributesCompat */
public class AudioAttributesCompat implements VersionedParcelable {
    static final String AUDIO_ATTRIBUTES_CONTENT_TYPE = "android.support.v4.media.audio_attrs.CONTENT_TYPE";
    static final String AUDIO_ATTRIBUTES_FLAGS = "android.support.v4.media.audio_attrs.FLAGS";
    static final String AUDIO_ATTRIBUTES_FRAMEWORKS = "android.support.v4.media.audio_attrs.FRAMEWORKS";
    static final String AUDIO_ATTRIBUTES_LEGACY_STREAM_TYPE = "android.support.v4.media.audio_attrs.LEGACY_STREAM_TYPE";
    static final String AUDIO_ATTRIBUTES_USAGE = "android.support.v4.media.audio_attrs.USAGE";
    public static final int CONTENT_TYPE_MOVIE = 3;
    public static final int CONTENT_TYPE_MUSIC = 2;
    public static final int CONTENT_TYPE_SONIFICATION = 4;
    public static final int CONTENT_TYPE_SPEECH = 1;
    public static final int CONTENT_TYPE_UNKNOWN = 0;
    static final int FLAG_ALL = 1023;
    static final int FLAG_ALL_PUBLIC = 273;
    public static final int FLAG_AUDIBILITY_ENFORCED = 1;
    static final int FLAG_BEACON = 8;
    static final int FLAG_BYPASS_INTERRUPTION_POLICY = 64;
    static final int FLAG_BYPASS_MUTE = 128;
    static final int FLAG_DEEP_BUFFER = 512;
    public static final int FLAG_HW_AV_SYNC = 16;
    static final int FLAG_HW_HOTWORD = 32;
    static final int FLAG_LOW_LATENCY = 256;
    static final int FLAG_SCO = 4;
    static final int FLAG_SECURE = 2;
    static final int INVALID_STREAM_TYPE = -1;
    private static final int[] SDK_USAGES = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16};
    private static final int SUPPRESSIBLE_CALL = 2;
    private static final int SUPPRESSIBLE_NOTIFICATION = 1;
    private static final SparseIntArray SUPPRESSIBLE_USAGES = new SparseIntArray();
    private static final String TAG = "AudioAttributesCompat";
    public static final int USAGE_ALARM = 4;
    public static final int USAGE_ASSISTANCE_ACCESSIBILITY = 11;
    public static final int USAGE_ASSISTANCE_NAVIGATION_GUIDANCE = 12;
    public static final int USAGE_ASSISTANCE_SONIFICATION = 13;
    public static final int USAGE_ASSISTANT = 16;
    public static final int USAGE_GAME = 14;
    public static final int USAGE_MEDIA = 1;
    public static final int USAGE_NOTIFICATION = 5;
    public static final int USAGE_NOTIFICATION_COMMUNICATION_DELAYED = 9;
    public static final int USAGE_NOTIFICATION_COMMUNICATION_INSTANT = 8;
    public static final int USAGE_NOTIFICATION_COMMUNICATION_REQUEST = 7;
    public static final int USAGE_NOTIFICATION_EVENT = 10;
    public static final int USAGE_NOTIFICATION_RINGTONE = 6;
    public static final int USAGE_UNKNOWN = 0;
    static final int USAGE_VIRTUAL_SOURCE = 15;
    public static final int USAGE_VOICE_COMMUNICATION = 2;
    public static final int USAGE_VOICE_COMMUNICATION_SIGNALLING = 3;
    static boolean sForceLegacyBehavior;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public AudioAttributesImpl mImpl;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.media.AudioAttributesCompat$AttributeContentType */
    public @interface AttributeContentType {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.media.AudioAttributesCompat$AttributeUsage */
    public @interface AttributeUsage {
    }

    static {
        SUPPRESSIBLE_USAGES.put(5, 1);
        SUPPRESSIBLE_USAGES.put(6, 2);
        SUPPRESSIBLE_USAGES.put(7, 2);
        SUPPRESSIBLE_USAGES.put(8, 1);
        SUPPRESSIBLE_USAGES.put(9, 1);
        SUPPRESSIBLE_USAGES.put(10, 1);
    }

    AudioAttributesCompat() {
    }

    AudioAttributesCompat(AudioAttributesImpl impl) {
        this.mImpl = impl;
    }

    public int getVolumeControlStream() {
        return this.mImpl.getVolumeControlStream();
    }

    @Nullable
    public Object unwrap() {
        return this.mImpl.getAudioAttributes();
    }

    public int getLegacyStreamType() {
        return this.mImpl.getLegacyStreamType();
    }

    @Nullable
    public static AudioAttributesCompat wrap(@NonNull Object aa) {
        if (sForceLegacyBehavior) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 26) {
            return new AudioAttributesCompat(new AudioAttributesImplApi26((AudioAttributes) aa));
        }
        if (Build.VERSION.SDK_INT >= 21) {
            return new AudioAttributesCompat(new AudioAttributesImplApi21((AudioAttributes) aa));
        }
        return null;
    }

    public int getContentType() {
        return this.mImpl.getContentType();
    }

    public int getUsage() {
        return this.mImpl.getUsage();
    }

    public int getFlags() {
        return this.mImpl.getFlags();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @NonNull
    public Bundle toBundle() {
        return this.mImpl.toBundle();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static AudioAttributesCompat fromBundle(Bundle bundle) {
        AudioAttributesImpl impl;
        if (Build.VERSION.SDK_INT >= 21) {
            impl = AudioAttributesImplApi21.fromBundle(bundle);
        } else {
            impl = AudioAttributesImplBase.fromBundle(bundle);
        }
        if (impl == null) {
            return null;
        }
        return new AudioAttributesCompat(impl);
    }

    /* renamed from: android.support.v4.media.AudioAttributesCompat$Builder */
    public static class Builder {
        final AudioAttributesImpl.Builder mBuilderImpl;

        public Builder() {
            if (AudioAttributesCompat.sForceLegacyBehavior) {
                this.mBuilderImpl = new AudioAttributesImplBase.Builder();
            } else if (Build.VERSION.SDK_INT >= 26) {
                this.mBuilderImpl = new AudioAttributesImplApi26.Builder();
            } else if (Build.VERSION.SDK_INT >= 21) {
                this.mBuilderImpl = new AudioAttributesImplApi21.Builder();
            } else {
                this.mBuilderImpl = new AudioAttributesImplBase.Builder();
            }
        }

        public Builder(AudioAttributesCompat aa) {
            if (AudioAttributesCompat.sForceLegacyBehavior) {
                this.mBuilderImpl = new AudioAttributesImplBase.Builder(aa);
            } else if (Build.VERSION.SDK_INT >= 26) {
                this.mBuilderImpl = new AudioAttributesImplApi26.Builder(aa.unwrap());
            } else if (Build.VERSION.SDK_INT >= 21) {
                this.mBuilderImpl = new AudioAttributesImplApi21.Builder(aa.unwrap());
            } else {
                this.mBuilderImpl = new AudioAttributesImplBase.Builder(aa);
            }
        }

        public AudioAttributesCompat build() {
            return new AudioAttributesCompat(this.mBuilderImpl.build());
        }

        public Builder setUsage(int usage) {
            this.mBuilderImpl.setUsage(usage);
            return this;
        }

        public Builder setContentType(int contentType) {
            this.mBuilderImpl.setContentType(contentType);
            return this;
        }

        public Builder setFlags(int flags) {
            this.mBuilderImpl.setFlags(flags);
            return this;
        }

        public Builder setLegacyStreamType(int streamType) {
            this.mBuilderImpl.setLegacyStreamType(streamType);
            return this;
        }
    }

    public int hashCode() {
        return this.mImpl.hashCode();
    }

    public String toString() {
        return this.mImpl.toString();
    }

    static String usageToString(int usage) {
        switch (usage) {
            case 0:
                return "USAGE_UNKNOWN";
            case 1:
                return "USAGE_MEDIA";
            case 2:
                return "USAGE_VOICE_COMMUNICATION";
            case 3:
                return "USAGE_VOICE_COMMUNICATION_SIGNALLING";
            case 4:
                return "USAGE_ALARM";
            case 5:
                return "USAGE_NOTIFICATION";
            case 6:
                return "USAGE_NOTIFICATION_RINGTONE";
            case 7:
                return "USAGE_NOTIFICATION_COMMUNICATION_REQUEST";
            case 8:
                return "USAGE_NOTIFICATION_COMMUNICATION_INSTANT";
            case 9:
                return "USAGE_NOTIFICATION_COMMUNICATION_DELAYED";
            case 10:
                return "USAGE_NOTIFICATION_EVENT";
            case 11:
                return "USAGE_ASSISTANCE_ACCESSIBILITY";
            case 12:
                return "USAGE_ASSISTANCE_NAVIGATION_GUIDANCE";
            case 13:
                return "USAGE_ASSISTANCE_SONIFICATION";
            case 14:
                return "USAGE_GAME";
            case 15:
            default:
                return "unknown usage " + usage;
            case 16:
                return "USAGE_ASSISTANT";
        }
    }

    /* renamed from: android.support.v4.media.AudioAttributesCompat$AudioManagerHidden */
    static abstract class AudioManagerHidden {
        public static final int STREAM_ACCESSIBILITY = 10;
        public static final int STREAM_BLUETOOTH_SCO = 6;
        public static final int STREAM_SYSTEM_ENFORCED = 7;
        public static final int STREAM_TTS = 9;

        private AudioManagerHidden() {
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static void setForceLegacyBehavior(boolean force) {
        sForceLegacyBehavior = force;
    }

    /* access modifiers changed from: package-private */
    public int getRawLegacyStreamType() {
        return this.mImpl.getRawLegacyStreamType();
    }

    static int toVolumeStreamType(boolean fromGetVolumeControlStream, int flags, int usage) {
        if ((flags & 1) == 1) {
            if (fromGetVolumeControlStream) {
                return 1;
            }
            return 7;
        } else if ((flags & 4) != 4) {
            switch (usage) {
                case 0:
                    return 3;
                case 1:
                case 12:
                case 14:
                case 16:
                    return 3;
                case 2:
                    return 0;
                case 3:
                    if (fromGetVolumeControlStream) {
                        return 0;
                    }
                    return 8;
                case 4:
                    return 4;
                case 5:
                case 7:
                case 8:
                case 9:
                case 10:
                    return 5;
                case 6:
                    return 2;
                case 11:
                    return 10;
                case 13:
                    return 1;
                case 15:
                default:
                    if (!fromGetVolumeControlStream) {
                        return 3;
                    }
                    throw new IllegalArgumentException("Unknown usage value " + usage + " in audio attributes");
            }
        } else if (fromGetVolumeControlStream) {
            return 0;
        } else {
            return 6;
        }
    }

    public boolean equals(Object o) {
        if (!(o instanceof AudioAttributesCompat)) {
            return false;
        }
        AudioAttributesCompat that = (AudioAttributesCompat) o;
        AudioAttributesImpl audioAttributesImpl = this.mImpl;
        if (audioAttributesImpl != null) {
            return audioAttributesImpl.equals(that.mImpl);
        }
        if (that.mImpl == null) {
            return true;
        }
        return false;
    }
}
