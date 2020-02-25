package android.support.p001v4.media;

import android.support.annotation.RestrictTo;

import androidx.versionedparcelable.VersionedParcel;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* renamed from: android.support.v4.media.AudioAttributesImplBaseParcelizer */
public final class AudioAttributesImplBaseParcelizer extends androidx.media.AudioAttributesImplBaseParcelizer {
    public static AudioAttributesImplBase read(VersionedParcel parcel) {
        return androidx.media.AudioAttributesImplBaseParcelizer.read(parcel);
    }

    public static void write(AudioAttributesImplBase obj, VersionedParcel parcel) {
        androidx.media.AudioAttributesImplBaseParcelizer.write(obj, parcel);
    }
}
