package androidx.media;

import android.media.AudioAttributes;
import android.support.annotation.RestrictTo;
import android.support.p001v4.media.AudioAttributesImplApi26;

import androidx.versionedparcelable.VersionedParcel;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public class AudioAttributesImplApi26Parcelizer {
    public static AudioAttributesImplApi26 read(VersionedParcel parcel) {
        AudioAttributesImplApi26 obj = new AudioAttributesImplApi26();
        obj.mAudioAttributes = (AudioAttributes) parcel.readParcelable(obj.mAudioAttributes, 1);
        obj.mLegacyStreamType = parcel.readInt(obj.mLegacyStreamType, 2);
        return obj;
    }

    public static void write(AudioAttributesImplApi26 obj, VersionedParcel parcel) {
        parcel.setSerializationFlags(false, false);
        parcel.writeParcelable(obj.mAudioAttributes, 1);
        parcel.writeInt(obj.mLegacyStreamType, 2);
    }
}
