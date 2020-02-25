package com.google.android.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.util.Util;

public final class InternalFrame extends Id3Frame {
    public static final Parcelable.Creator<InternalFrame> CREATOR = new Parcelable.Creator<InternalFrame>() {
        public InternalFrame createFromParcel(Parcel in) {
            return new InternalFrame(in);
        }

        public InternalFrame[] newArray(int size) {
            return new InternalFrame[size];
        }
    };

    /* renamed from: ID */
    public static final String f86ID = "----";
    public final String description;
    public final String domain;
    public final String text;

    public InternalFrame(String domain2, String description2, String text2) {
        super(f86ID);
        this.domain = domain2;
        this.description = description2;
        this.text = text2;
    }

    InternalFrame(Parcel in) {
        super(f86ID);
        this.domain = (String) Util.castNonNull(in.readString());
        this.description = (String) Util.castNonNull(in.readString());
        this.text = (String) Util.castNonNull(in.readString());
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        InternalFrame other = (InternalFrame) obj;
        if (!Util.areEqual(this.description, other.description) || !Util.areEqual(this.domain, other.domain) || !Util.areEqual(this.text, other.text)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i = 17 * 31;
        String str = this.domain;
        int i2 = 0;
        int result = (i + (str != null ? str.hashCode() : 0)) * 31;
        String str2 = this.description;
        int result2 = (result + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.text;
        if (str3 != null) {
            i2 = str3.hashCode();
        }
        return result2 + i2;
    }

    public String toString() {
        String str = this.f85id;
        String str2 = this.domain;
        String str3 = this.description;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 23 + String.valueOf(str2).length() + String.valueOf(str3).length());
        sb.append(str);
        sb.append(": domain=");
        sb.append(str2);
        sb.append(", description=");
        sb.append(str3);
        return sb.toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.f85id);
        dest.writeString(this.domain);
        dest.writeString(this.text);
    }
}
