package com.google.android.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.util.Util;

public final class CommentFrame extends Id3Frame {
    public static final Parcelable.Creator<CommentFrame> CREATOR = new Parcelable.Creator<CommentFrame>() {
        public CommentFrame createFromParcel(Parcel in) {
            return new CommentFrame(in);
        }

        public CommentFrame[] newArray(int size) {
            return new CommentFrame[size];
        }
    };

    /* renamed from: ID */
    public static final String f83ID = "COMM";
    public final String description;
    public final String language;
    public final String text;

    public CommentFrame(String language2, String description2, String text2) {
        super(f83ID);
        this.language = language2;
        this.description = description2;
        this.text = text2;
    }

    CommentFrame(Parcel in) {
        super(f83ID);
        this.language = (String) Util.castNonNull(in.readString());
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
        CommentFrame other = (CommentFrame) obj;
        if (!Util.areEqual(this.description, other.description) || !Util.areEqual(this.language, other.language) || !Util.areEqual(this.text, other.text)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i = 17 * 31;
        String str = this.language;
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
        String str2 = this.language;
        String str3 = this.description;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 25 + String.valueOf(str2).length() + String.valueOf(str3).length());
        sb.append(str);
        sb.append(": language=");
        sb.append(str2);
        sb.append(", description=");
        sb.append(str3);
        return sb.toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.f85id);
        dest.writeString(this.language);
        dest.writeString(this.text);
    }
}
