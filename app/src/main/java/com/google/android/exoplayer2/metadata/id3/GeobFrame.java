package com.google.android.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.util.Util;

import java.util.Arrays;

public final class GeobFrame extends Id3Frame {
    public static final Parcelable.Creator<GeobFrame> CREATOR = new Parcelable.Creator<GeobFrame>() {
        public GeobFrame createFromParcel(Parcel in) {
            return new GeobFrame(in);
        }

        public GeobFrame[] newArray(int size) {
            return new GeobFrame[size];
        }
    };

    /* renamed from: ID */
    public static final String f84ID = "GEOB";
    public final byte[] data;
    public final String description;
    public final String filename;
    public final String mimeType;

    public GeobFrame(String mimeType2, String filename2, String description2, byte[] data2) {
        super(f84ID);
        this.mimeType = mimeType2;
        this.filename = filename2;
        this.description = description2;
        this.data = data2;
    }

    GeobFrame(Parcel in) {
        super(f84ID);
        this.mimeType = (String) Util.castNonNull(in.readString());
        this.filename = (String) Util.castNonNull(in.readString());
        this.description = (String) Util.castNonNull(in.readString());
        this.data = (byte[]) Util.castNonNull(in.createByteArray());
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        GeobFrame other = (GeobFrame) obj;
        if (!Util.areEqual(this.mimeType, other.mimeType) || !Util.areEqual(this.filename, other.filename) || !Util.areEqual(this.description, other.description) || !Arrays.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i = 17 * 31;
        String str = this.mimeType;
        int i2 = 0;
        int result = (i + (str != null ? str.hashCode() : 0)) * 31;
        String str2 = this.filename;
        int result2 = (result + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.description;
        if (str3 != null) {
            i2 = str3.hashCode();
        }
        return ((result2 + i2) * 31) + Arrays.hashCode(this.data);
    }

    public String toString() {
        String str = this.f85id;
        String str2 = this.mimeType;
        String str3 = this.filename;
        String str4 = this.description;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 36 + String.valueOf(str2).length() + String.valueOf(str3).length() + String.valueOf(str4).length());
        sb.append(str);
        sb.append(": mimeType=");
        sb.append(str2);
        sb.append(", filename=");
        sb.append(str3);
        sb.append(", description=");
        sb.append(str4);
        return sb.toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mimeType);
        dest.writeString(this.filename);
        dest.writeString(this.description);
        dest.writeByteArray(this.data);
    }
}
