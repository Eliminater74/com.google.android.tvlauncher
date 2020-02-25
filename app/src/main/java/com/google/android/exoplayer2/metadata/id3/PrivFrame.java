package com.google.android.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.util.Util;

import java.util.Arrays;

public final class PrivFrame extends Id3Frame {
    public static final Parcelable.Creator<PrivFrame> CREATOR = new Parcelable.Creator<PrivFrame>() {
        public PrivFrame createFromParcel(Parcel in) {
            return new PrivFrame(in);
        }

        public PrivFrame[] newArray(int size) {
            return new PrivFrame[size];
        }
    };

    /* renamed from: ID */
    public static final String f88ID = "PRIV";
    public final String owner;
    public final byte[] privateData;

    public PrivFrame(String owner2, byte[] privateData2) {
        super(f88ID);
        this.owner = owner2;
        this.privateData = privateData2;
    }

    PrivFrame(Parcel in) {
        super(f88ID);
        this.owner = (String) Util.castNonNull(in.readString());
        this.privateData = (byte[]) Util.castNonNull(in.createByteArray());
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PrivFrame other = (PrivFrame) obj;
        if (!Util.areEqual(this.owner, other.owner) || !Arrays.equals(this.privateData, other.privateData)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i = 17 * 31;
        String str = this.owner;
        return ((i + (str != null ? str.hashCode() : 0)) * 31) + Arrays.hashCode(this.privateData);
    }

    public String toString() {
        String str = this.f85id;
        String str2 = this.owner;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 8 + String.valueOf(str2).length());
        sb.append(str);
        sb.append(": owner=");
        sb.append(str2);
        return sb.toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.owner);
        dest.writeByteArray(this.privateData);
    }
}
