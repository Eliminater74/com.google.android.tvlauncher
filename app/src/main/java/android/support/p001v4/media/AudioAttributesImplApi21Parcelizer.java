package android.support.p001v4.media;

import android.support.annotation.RestrictTo;
import androidx.versionedparcelable.VersionedParcel;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* renamed from: android.support.v4.media.AudioAttributesImplApi21Parcelizer */
public final class AudioAttributesImplApi21Parcelizer extends androidx.media.AudioAttributesImplApi21Parcelizer {
    public static AudioAttributesImplApi21 read(VersionedParcel parcel) {
        return androidx.media.AudioAttributesImplApi21Parcelizer.read(parcel);
    }

    public static void write(AudioAttributesImplApi21 obj, VersionedParcel parcel) {
        androidx.media.AudioAttributesImplApi21Parcelizer.write(obj, parcel);
    }
}
