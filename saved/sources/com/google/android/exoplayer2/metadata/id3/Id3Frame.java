package com.google.android.exoplayer2.metadata.id3;

import com.google.android.exoplayer2.metadata.Metadata;

public abstract class Id3Frame implements Metadata.Entry {

    /* renamed from: id */
    public final String f85id;

    public Id3Frame(String id) {
        this.f85id = id;
    }

    public String toString() {
        return this.f85id;
    }

    public int describeContents() {
        return 0;
    }
}
