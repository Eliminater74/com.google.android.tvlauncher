package com.google.android.exoplayer2.source;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Arrays;

public final class TrackGroup implements Parcelable {
    public static final Parcelable.Creator<TrackGroup> CREATOR = new Parcelable.Creator<TrackGroup>() {
        public TrackGroup createFromParcel(Parcel in) {
            return new TrackGroup(in);
        }

        public TrackGroup[] newArray(int size) {
            return new TrackGroup[size];
        }
    };
    private final Format[] formats;
    private int hashCode;
    public final int length;

    public TrackGroup(Format... formats2) {
        Assertions.checkState(formats2.length > 0);
        this.formats = formats2;
        this.length = formats2.length;
    }

    TrackGroup(Parcel in) {
        this.length = in.readInt();
        this.formats = new Format[this.length];
        for (int i = 0; i < this.length; i++) {
            this.formats[i] = (Format) in.readParcelable(Format.class.getClassLoader());
        }
    }

    public Format getFormat(int index) {
        return this.formats[index];
    }

    public int indexOf(Format format) {
        int i = 0;
        while (true) {
            Format[] formatArr = this.formats;
            if (i >= formatArr.length) {
                return -1;
            }
            if (format == formatArr[i]) {
                return i;
            }
            i++;
        }
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = (17 * 31) + Arrays.hashCode(this.formats);
        }
        return this.hashCode;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TrackGroup other = (TrackGroup) obj;
        if (this.length != other.length || !Arrays.equals(this.formats, other.formats)) {
            return false;
        }
        return true;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.length);
        for (int i = 0; i < this.length; i++) {
            dest.writeParcelable(this.formats[i], 0);
        }
    }
}
