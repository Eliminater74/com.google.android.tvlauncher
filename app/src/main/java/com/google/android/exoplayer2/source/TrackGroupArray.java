package com.google.android.exoplayer2.source;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.util.Arrays;

public final class TrackGroupArray implements Parcelable {
    public static final Parcelable.Creator<TrackGroupArray> CREATOR = new Parcelable.Creator<TrackGroupArray>() {
        public TrackGroupArray createFromParcel(Parcel in) {
            return new TrackGroupArray(in);
        }

        public TrackGroupArray[] newArray(int size) {
            return new TrackGroupArray[size];
        }
    };
    public static final TrackGroupArray EMPTY = new TrackGroupArray(new TrackGroup[0]);
    public final int length;
    private final TrackGroup[] trackGroups;
    private int hashCode;

    public TrackGroupArray(TrackGroup... trackGroups2) {
        this.trackGroups = trackGroups2;
        this.length = trackGroups2.length;
    }

    TrackGroupArray(Parcel in) {
        this.length = in.readInt();
        this.trackGroups = new TrackGroup[this.length];
        for (int i = 0; i < this.length; i++) {
            this.trackGroups[i] = (TrackGroup) in.readParcelable(TrackGroup.class.getClassLoader());
        }
    }

    public TrackGroup get(int index) {
        return this.trackGroups[index];
    }

    public int indexOf(TrackGroup group) {
        for (int i = 0; i < this.length; i++) {
            if (this.trackGroups[i] == group) {
                return i;
            }
        }
        return -1;
    }

    public boolean isEmpty() {
        return this.length == 0;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = Arrays.hashCode(this.trackGroups);
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
        TrackGroupArray other = (TrackGroupArray) obj;
        if (this.length != other.length || !Arrays.equals(this.trackGroups, other.trackGroups)) {
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
            dest.writeParcelable(this.trackGroups[i], 0);
        }
    }
}
