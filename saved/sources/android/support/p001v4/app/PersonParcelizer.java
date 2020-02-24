package android.support.p001v4.app;

import android.support.annotation.RestrictTo;
import androidx.versionedparcelable.VersionedParcel;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* renamed from: android.support.v4.app.PersonParcelizer */
public final class PersonParcelizer extends androidx.core.app.PersonParcelizer {
    public static Person read(VersionedParcel parcel) {
        return androidx.core.app.PersonParcelizer.read(parcel);
    }

    public static void write(Person obj, VersionedParcel parcel) {
        androidx.core.app.PersonParcelizer.write(obj, parcel);
    }
}
