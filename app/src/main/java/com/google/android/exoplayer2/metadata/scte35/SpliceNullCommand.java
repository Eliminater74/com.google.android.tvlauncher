package com.google.android.exoplayer2.metadata.scte35;

import android.os.Parcel;
import android.os.Parcelable;

public final class SpliceNullCommand extends SpliceCommand {
    public static final Parcelable.Creator<SpliceNullCommand> CREATOR = new Parcelable.Creator<SpliceNullCommand>() {
        public SpliceNullCommand createFromParcel(Parcel in) {
            return new SpliceNullCommand();
        }

        public SpliceNullCommand[] newArray(int size) {
            return new SpliceNullCommand[size];
        }
    };

    public void writeToParcel(Parcel dest, int flags) {
    }
}
