package android.support.p001v4.p003os;

import android.os.Parcel;
import android.support.annotation.NonNull;

/* renamed from: android.support.v4.os.ParcelCompat */
public final class ParcelCompat {
    public static boolean readBoolean(@NonNull Parcel in) {
        return in.readInt() != 0;
    }

    public static void writeBoolean(@NonNull Parcel out, boolean value) {
        out.writeInt(value);
    }

    private ParcelCompat() {
    }
}
