package com.google.android.exoplayer2.extractor.mp4;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Util;

import java.util.Arrays;

public final class MdtaMetadataEntry implements Metadata.Entry {
    public static final Parcelable.Creator<MdtaMetadataEntry> CREATOR = new Parcelable.Creator<MdtaMetadataEntry>() {
        public MdtaMetadataEntry createFromParcel(Parcel in) {
            return new MdtaMetadataEntry(in);
        }

        public MdtaMetadataEntry[] newArray(int size) {
            return new MdtaMetadataEntry[size];
        }
    };
    public final String key;
    public final int localeIndicator;
    public final int typeIndicator;
    public final byte[] value;

    public MdtaMetadataEntry(String key2, byte[] value2, int localeIndicator2, int typeIndicator2) {
        this.key = key2;
        this.value = value2;
        this.localeIndicator = localeIndicator2;
        this.typeIndicator = typeIndicator2;
    }

    private MdtaMetadataEntry(Parcel in) {
        this.key = (String) Util.castNonNull(in.readString());
        this.value = new byte[in.readInt()];
        in.readByteArray(this.value);
        this.localeIndicator = in.readInt();
        this.typeIndicator = in.readInt();
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MdtaMetadataEntry other = (MdtaMetadataEntry) obj;
        if (!this.key.equals(other.key) || !Arrays.equals(this.value, other.value) || this.localeIndicator != other.localeIndicator || this.typeIndicator != other.typeIndicator) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((((17 * 31) + this.key.hashCode()) * 31) + Arrays.hashCode(this.value)) * 31) + this.localeIndicator) * 31) + this.typeIndicator;
    }

    public String toString() {
        String valueOf = String.valueOf(this.key);
        return valueOf.length() != 0 ? "mdta: key=".concat(valueOf) : new String("mdta: key=");
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeInt(this.value.length);
        dest.writeByteArray(this.value);
        dest.writeInt(this.localeIndicator);
        dest.writeInt(this.typeIndicator);
    }

    public int describeContents() {
        return 0;
    }
}
