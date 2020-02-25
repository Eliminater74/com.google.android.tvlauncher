package com.google.android.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.util.Util;

import java.util.Arrays;

public final class BinaryFrame extends Id3Frame {
    public static final Parcelable.Creator<BinaryFrame> CREATOR = new Parcelable.Creator<BinaryFrame>() {
        public BinaryFrame createFromParcel(Parcel in) {
            return new BinaryFrame(in);
        }

        public BinaryFrame[] newArray(int size) {
            return new BinaryFrame[size];
        }
    };
    public final byte[] data;

    public BinaryFrame(String id, byte[] data2) {
        super(id);
        this.data = data2;
    }

    BinaryFrame(Parcel in) {
        super((String) Util.castNonNull(in.readString()));
        this.data = (byte[]) Util.castNonNull(in.createByteArray());
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BinaryFrame other = (BinaryFrame) obj;
        if (!this.f85id.equals(other.f85id) || !Arrays.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((17 * 31) + this.f85id.hashCode()) * 31) + Arrays.hashCode(this.data);
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.f85id);
        dest.writeByteArray(this.data);
    }
}
