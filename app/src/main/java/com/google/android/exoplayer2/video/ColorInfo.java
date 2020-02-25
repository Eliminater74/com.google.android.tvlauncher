package com.google.android.exoplayer2.video;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.util.Util;

import java.util.Arrays;

public final class ColorInfo implements Parcelable {
    public static final Parcelable.Creator<ColorInfo> CREATOR = new Parcelable.Creator<ColorInfo>() {
        public ColorInfo createFromParcel(Parcel in) {
            return new ColorInfo(in);
        }

        public ColorInfo[] newArray(int size) {
            return new ColorInfo[size];
        }
    };
    public final int colorRange;
    public final int colorSpace;
    public final int colorTransfer;
    @Nullable
    public final byte[] hdrStaticInfo;
    private int hashCode;

    public ColorInfo(int colorSpace2, int colorRange2, int colorTransfer2, @Nullable byte[] hdrStaticInfo2) {
        this.colorSpace = colorSpace2;
        this.colorRange = colorRange2;
        this.colorTransfer = colorTransfer2;
        this.hdrStaticInfo = hdrStaticInfo2;
    }

    ColorInfo(Parcel in) {
        this.colorSpace = in.readInt();
        this.colorRange = in.readInt();
        this.colorTransfer = in.readInt();
        this.hdrStaticInfo = Util.readBoolean(in) ? in.createByteArray() : null;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ColorInfo other = (ColorInfo) obj;
        if (this.colorSpace == other.colorSpace && this.colorRange == other.colorRange && this.colorTransfer == other.colorTransfer && Arrays.equals(this.hdrStaticInfo, other.hdrStaticInfo)) {
            return true;
        }
        return false;
    }

    public String toString() {
        int i = this.colorSpace;
        int i2 = this.colorRange;
        int i3 = this.colorTransfer;
        boolean z = this.hdrStaticInfo != null;
        StringBuilder sb = new StringBuilder(55);
        sb.append("ColorInfo(");
        sb.append(i);
        sb.append(", ");
        sb.append(i2);
        sb.append(", ");
        sb.append(i3);
        sb.append(", ");
        sb.append(z);
        sb.append(")");
        return sb.toString();
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = (((((((17 * 31) + this.colorSpace) * 31) + this.colorRange) * 31) + this.colorTransfer) * 31) + Arrays.hashCode(this.hdrStaticInfo);
        }
        return this.hashCode;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.colorSpace);
        dest.writeInt(this.colorRange);
        dest.writeInt(this.colorTransfer);
        Util.writeBoolean(dest, this.hdrStaticInfo != null);
        byte[] bArr = this.hdrStaticInfo;
        if (bArr != null) {
            dest.writeByteArray(bArr);
        }
    }
}
