package com.google.android.exoplayer2.offline;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class StreamKey implements Comparable<StreamKey>, Parcelable {
    public static final Parcelable.Creator<StreamKey> CREATOR = new Parcelable.Creator<StreamKey>() {
        public StreamKey createFromParcel(Parcel in) {
            return new StreamKey(in);
        }

        public StreamKey[] newArray(int size) {
            return new StreamKey[size];
        }
    };
    public final int groupIndex;
    public final int periodIndex;
    public final int trackIndex;

    public StreamKey(int groupIndex2, int trackIndex2) {
        this(0, groupIndex2, trackIndex2);
    }

    public StreamKey(int periodIndex2, int groupIndex2, int trackIndex2) {
        this.periodIndex = periodIndex2;
        this.groupIndex = groupIndex2;
        this.trackIndex = trackIndex2;
    }

    StreamKey(Parcel in) {
        this.periodIndex = in.readInt();
        this.groupIndex = in.readInt();
        this.trackIndex = in.readInt();
    }

    public String toString() {
        int i = this.periodIndex;
        int i2 = this.groupIndex;
        int i3 = this.trackIndex;
        StringBuilder sb = new StringBuilder(35);
        sb.append(i);
        sb.append(".");
        sb.append(i2);
        sb.append(".");
        sb.append(i3);
        return sb.toString();
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StreamKey that = (StreamKey) o;
        if (this.periodIndex == that.periodIndex && this.groupIndex == that.groupIndex && this.trackIndex == that.trackIndex) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((this.periodIndex * 31) + this.groupIndex) * 31) + this.trackIndex;
    }

    public int compareTo(@NonNull StreamKey o) {
        int result = this.periodIndex - o.periodIndex;
        if (result != 0) {
            return result;
        }
        int result2 = this.groupIndex - o.groupIndex;
        if (result2 == 0) {
            return this.trackIndex - o.trackIndex;
        }
        return result2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.periodIndex);
        dest.writeInt(this.groupIndex);
        dest.writeInt(this.trackIndex);
    }
}
