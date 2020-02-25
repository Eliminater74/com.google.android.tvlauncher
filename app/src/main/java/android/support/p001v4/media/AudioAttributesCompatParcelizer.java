package android.support.p001v4.media;

import android.support.annotation.RestrictTo;

import androidx.versionedparcelable.VersionedParcel;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* renamed from: android.support.v4.media.AudioAttributesCompatParcelizer */
public final class AudioAttributesCompatParcelizer extends androidx.media.AudioAttributesCompatParcelizer {
    public static AudioAttributesCompat read(VersionedParcel parcel) {
        return androidx.media.AudioAttributesCompatParcelizer.read(parcel);
    }

    public static void write(AudioAttributesCompat obj, VersionedParcel parcel) {
        androidx.media.AudioAttributesCompatParcelizer.write(obj, parcel);
    }
}
