package com.google.android.exoplayer2.metadata;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.util.Util;

import java.util.Arrays;
import java.util.List;

public final class Metadata implements Parcelable {
    public static final Parcelable.Creator<Metadata> CREATOR = new Parcelable.Creator<Metadata>() {
        public Metadata createFromParcel(Parcel in) {
            return new Metadata(in);
        }

        public Metadata[] newArray(int size) {
            return new Metadata[size];
        }
    };
    private final Entry[] entries;

    public Metadata(Entry... entries2) {
        this.entries = entries2 == null ? new Entry[0] : entries2;
    }

    public Metadata(List<? extends Entry> entries2) {
        if (entries2 != null) {
            this.entries = new Entry[entries2.size()];
            entries2.toArray(this.entries);
            return;
        }
        this.entries = new Entry[0];
    }

    Metadata(Parcel in) {
        this.entries = new Entry[in.readInt()];
        int i = 0;
        while (true) {
            Entry[] entryArr = this.entries;
            if (i < entryArr.length) {
                entryArr[i] = (Entry) in.readParcelable(Entry.class.getClassLoader());
                i++;
            } else {
                return;
            }
        }
    }

    public int length() {
        return this.entries.length;
    }

    public Entry get(int index) {
        return this.entries[index];
    }

    public Metadata copyWithAppendedEntriesFrom(@Nullable Metadata other) {
        if (other == null) {
            return this;
        }
        return copyWithAppendedEntries(other.entries);
    }

    public Metadata copyWithAppendedEntries(Entry... entriesToAppend) {
        Entry[] entryArr = this.entries;
        Entry[] merged = (Entry[]) Arrays.copyOf(entryArr, entryArr.length + entriesToAppend.length);
        System.arraycopy(entriesToAppend, 0, merged, this.entries.length, entriesToAppend.length);
        return new Metadata((Entry[]) Util.castNonNullTypeArray(merged));
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Arrays.equals(this.entries, ((Metadata) obj).entries);
    }

    public int hashCode() {
        return Arrays.hashCode(this.entries);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.entries.length);
        for (Entry entry : this.entries) {
            dest.writeParcelable(entry, 0);
        }
    }

    public interface Entry extends Parcelable {
    }
}
