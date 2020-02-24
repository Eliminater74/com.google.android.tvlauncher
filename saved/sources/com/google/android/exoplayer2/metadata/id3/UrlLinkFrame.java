package com.google.android.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.util.Util;

public final class UrlLinkFrame extends Id3Frame {
    public static final Parcelable.Creator<UrlLinkFrame> CREATOR = new Parcelable.Creator<UrlLinkFrame>() {
        public UrlLinkFrame createFromParcel(Parcel in) {
            return new UrlLinkFrame(in);
        }

        public UrlLinkFrame[] newArray(int size) {
            return new UrlLinkFrame[size];
        }
    };
    @Nullable
    public final String description;
    public final String url;

    public UrlLinkFrame(String id, @Nullable String description2, String url2) {
        super(id);
        this.description = description2;
        this.url = url2;
    }

    UrlLinkFrame(Parcel in) {
        super((String) Util.castNonNull(in.readString()));
        this.description = in.readString();
        this.url = (String) Util.castNonNull(in.readString());
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UrlLinkFrame other = (UrlLinkFrame) obj;
        if (!this.f85id.equals(other.f85id) || !Util.areEqual(this.description, other.description) || !Util.areEqual(this.url, other.url)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result = ((17 * 31) + this.f85id.hashCode()) * 31;
        String str = this.description;
        int i = 0;
        int result2 = (result + (str != null ? str.hashCode() : 0)) * 31;
        String str2 = this.url;
        if (str2 != null) {
            i = str2.hashCode();
        }
        return result2 + i;
    }

    public String toString() {
        String str = this.f85id;
        String str2 = this.url;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 6 + String.valueOf(str2).length());
        sb.append(str);
        sb.append(": url=");
        sb.append(str2);
        return sb.toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.f85id);
        dest.writeString(this.description);
        dest.writeString(this.url);
    }
}
