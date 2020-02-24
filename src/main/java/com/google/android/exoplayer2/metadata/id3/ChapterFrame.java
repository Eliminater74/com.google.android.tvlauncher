package com.google.android.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

public final class ChapterFrame extends Id3Frame {
    public static final Parcelable.Creator<ChapterFrame> CREATOR = new Parcelable.Creator<ChapterFrame>() {
        public ChapterFrame createFromParcel(Parcel in) {
            return new ChapterFrame(in);
        }

        public ChapterFrame[] newArray(int size) {
            return new ChapterFrame[size];
        }
    };

    /* renamed from: ID */
    public static final String f81ID = "CHAP";
    public final String chapterId;
    public final long endOffset;
    public final int endTimeMs;
    public final long startOffset;
    public final int startTimeMs;
    private final Id3Frame[] subFrames;

    public ChapterFrame(String chapterId2, int startTimeMs2, int endTimeMs2, long startOffset2, long endOffset2, Id3Frame[] subFrames2) {
        super(f81ID);
        this.chapterId = chapterId2;
        this.startTimeMs = startTimeMs2;
        this.endTimeMs = endTimeMs2;
        this.startOffset = startOffset2;
        this.endOffset = endOffset2;
        this.subFrames = subFrames2;
    }

    ChapterFrame(Parcel in) {
        super(f81ID);
        this.chapterId = (String) Util.castNonNull(in.readString());
        this.startTimeMs = in.readInt();
        this.endTimeMs = in.readInt();
        this.startOffset = in.readLong();
        this.endOffset = in.readLong();
        int subFrameCount = in.readInt();
        this.subFrames = new Id3Frame[subFrameCount];
        for (int i = 0; i < subFrameCount; i++) {
            this.subFrames[i] = (Id3Frame) in.readParcelable(Id3Frame.class.getClassLoader());
        }
    }

    public int getSubFrameCount() {
        return this.subFrames.length;
    }

    public Id3Frame getSubFrame(int index) {
        return this.subFrames[index];
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ChapterFrame other = (ChapterFrame) obj;
        if (this.startTimeMs == other.startTimeMs && this.endTimeMs == other.endTimeMs && this.startOffset == other.startOffset && this.endOffset == other.endOffset && Util.areEqual(this.chapterId, other.chapterId) && Arrays.equals(this.subFrames, other.subFrames)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = ((((((((17 * 31) + this.startTimeMs) * 31) + this.endTimeMs) * 31) + ((int) this.startOffset)) * 31) + ((int) this.endOffset)) * 31;
        String str = this.chapterId;
        return result + (str != null ? str.hashCode() : 0);
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.chapterId);
        dest.writeInt(this.startTimeMs);
        dest.writeInt(this.endTimeMs);
        dest.writeLong(this.startOffset);
        dest.writeLong(this.endOffset);
        dest.writeInt(this.subFrames.length);
        for (Id3Frame subFrame : this.subFrames) {
            dest.writeParcelable(subFrame, 0);
        }
    }

    public int describeContents() {
        return 0;
    }
}
