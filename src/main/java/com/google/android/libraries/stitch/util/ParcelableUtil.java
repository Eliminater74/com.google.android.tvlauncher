package com.google.android.libraries.stitch.util;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

public final class ParcelableUtil {
    private ParcelableUtil() {
    }

    public static byte[] serialize(Parcelable p) {
        Parcel parcel = Parcel.obtain();
        parcel.writeParcelable(p, 0);
        byte[] bytes = parcel.marshall();
        parcel.recycle();
        return bytes;
    }

    public static int getParcelSize(Parcelable p) {
        Parcel parcel = Parcel.obtain();
        parcel.writeParcelable(p, 0);
        int size = parcel.dataSize();
        parcel.recycle();
        return size;
    }

    public static <T extends Parcelable> T deserialize(byte[] data, ClassLoader loader) throws BadParcelableException {
        if (data == null) {
            return null;
        }
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(data, 0, data.length);
        parcel.setDataPosition(0);
        T parcelable = parcel.readParcelable(loader);
        parcel.recycle();
        return parcelable;
    }
}
