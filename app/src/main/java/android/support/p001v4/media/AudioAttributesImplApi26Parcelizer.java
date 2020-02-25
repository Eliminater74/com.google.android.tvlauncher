package android.support.p001v4.media;

import android.support.annotation.RestrictTo;

import androidx.versionedparcelable.VersionedParcel;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* renamed from: android.support.v4.media.AudioAttributesImplApi26Parcelizer */
public final class AudioAttributesImplApi26Parcelizer extends androidx.media.AudioAttributesImplApi26Parcelizer {
    public static AudioAttributesImplApi26 read(VersionedParcel parcel) {
        return androidx.media.AudioAttributesImplApi26Parcelizer.read(parcel);
    }

    public static void write(AudioAttributesImplApi26 obj, VersionedParcel parcel) {
        androidx.media.AudioAttributesImplApi26Parcelizer.write(obj, parcel);
    }
}
