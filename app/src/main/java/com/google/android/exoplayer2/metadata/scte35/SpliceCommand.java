package com.google.android.exoplayer2.metadata.scte35;

import com.google.android.exoplayer2.metadata.Metadata;

public abstract class SpliceCommand implements Metadata.Entry {
    public String toString() {
        String valueOf = String.valueOf(getClass().getSimpleName());
        return valueOf.length() != 0 ? "SCTE-35 splice command: type=".concat(valueOf) : new String("SCTE-35 splice command: type=");
    }

    public int describeContents() {
        return 0;
    }
}
