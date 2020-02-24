package androidx.core.app;

import android.support.annotation.RestrictTo;
import android.support.p001v4.app.Person;
import android.support.p001v4.graphics.drawable.IconCompat;
import androidx.versionedparcelable.VersionedParcel;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public class PersonParcelizer {
    public static Person read(VersionedParcel parcel) {
        Person obj = new Person();
        obj.mName = parcel.readCharSequence(obj.mName, 1);
        obj.mIcon = (IconCompat) parcel.readVersionedParcelable(obj.mIcon, 2);
        obj.mUri = parcel.readString(obj.mUri, 3);
        obj.mKey = parcel.readString(obj.mKey, 4);
        obj.mIsBot = parcel.readBoolean(obj.mIsBot, 5);
        obj.mIsImportant = parcel.readBoolean(obj.mIsImportant, 6);
        return obj;
    }

    public static void write(Person obj, VersionedParcel parcel) {
        parcel.setSerializationFlags(false, false);
        parcel.writeCharSequence(obj.mName, 1);
        parcel.writeVersionedParcelable(obj.mIcon, 2);
        parcel.writeString(obj.mUri, 3);
        parcel.writeString(obj.mKey, 4);
        parcel.writeBoolean(obj.mIsBot, 5);
        parcel.writeBoolean(obj.mIsImportant, 6);
    }
}
