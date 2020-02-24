package com.google.android.exoplayer2.source.hls;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.exoplayer2.metadata.Metadata;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class HlsTrackMetadataEntry implements Metadata.Entry {
    public static final Parcelable.Creator<HlsTrackMetadataEntry> CREATOR = new Parcelable.Creator<HlsTrackMetadataEntry>() {
        public HlsTrackMetadataEntry createFromParcel(Parcel in) {
            return new HlsTrackMetadataEntry(in);
        }

        public HlsTrackMetadataEntry[] newArray(int size) {
            return new HlsTrackMetadataEntry[size];
        }
    };
    @Nullable
    public final String groupId;
    @Nullable
    public final String name;
    public final List<VariantInfo> variantInfos;

    public static final class VariantInfo implements Parcelable {
        public static final Parcelable.Creator<VariantInfo> CREATOR = new Parcelable.Creator<VariantInfo>() {
            public VariantInfo createFromParcel(Parcel in) {
                return new VariantInfo(in);
            }

            public VariantInfo[] newArray(int size) {
                return new VariantInfo[size];
            }
        };
        @Nullable
        public final String audioGroupId;
        public final long bitrate;
        @Nullable
        public final String captionGroupId;
        @Nullable
        public final String subtitleGroupId;
        @Nullable
        public final String videoGroupId;

        public VariantInfo(long bitrate2, @Nullable String videoGroupId2, @Nullable String audioGroupId2, @Nullable String subtitleGroupId2, @Nullable String captionGroupId2) {
            this.bitrate = bitrate2;
            this.videoGroupId = videoGroupId2;
            this.audioGroupId = audioGroupId2;
            this.subtitleGroupId = subtitleGroupId2;
            this.captionGroupId = captionGroupId2;
        }

        VariantInfo(Parcel in) {
            this.bitrate = in.readLong();
            this.videoGroupId = in.readString();
            this.audioGroupId = in.readString();
            this.subtitleGroupId = in.readString();
            this.captionGroupId = in.readString();
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            VariantInfo that = (VariantInfo) other;
            if (this.bitrate != that.bitrate || !TextUtils.equals(this.videoGroupId, that.videoGroupId) || !TextUtils.equals(this.audioGroupId, that.audioGroupId) || !TextUtils.equals(this.subtitleGroupId, that.subtitleGroupId) || !TextUtils.equals(this.captionGroupId, that.captionGroupId)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            long j = this.bitrate;
            int i = ((int) (j ^ (j >>> 32))) * 31;
            String str = this.videoGroupId;
            int i2 = 0;
            int result = (i + (str != null ? str.hashCode() : 0)) * 31;
            String str2 = this.audioGroupId;
            int result2 = (result + (str2 != null ? str2.hashCode() : 0)) * 31;
            String str3 = this.subtitleGroupId;
            int result3 = (result2 + (str3 != null ? str3.hashCode() : 0)) * 31;
            String str4 = this.captionGroupId;
            if (str4 != null) {
                i2 = str4.hashCode();
            }
            return result3 + i2;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.bitrate);
            dest.writeString(this.videoGroupId);
            dest.writeString(this.audioGroupId);
            dest.writeString(this.subtitleGroupId);
            dest.writeString(this.captionGroupId);
        }
    }

    public HlsTrackMetadataEntry(@Nullable String groupId2, @Nullable String name2, List<VariantInfo> variantInfos2) {
        this.groupId = groupId2;
        this.name = name2;
        this.variantInfos = Collections.unmodifiableList(new ArrayList(variantInfos2));
    }

    HlsTrackMetadataEntry(Parcel in) {
        this.groupId = in.readString();
        this.name = in.readString();
        int variantInfoSize = in.readInt();
        ArrayList<VariantInfo> variantInfos2 = new ArrayList<>(variantInfoSize);
        for (int i = 0; i < variantInfoSize; i++) {
            variantInfos2.add((VariantInfo) in.readParcelable(VariantInfo.class.getClassLoader()));
        }
        this.variantInfos = Collections.unmodifiableList(variantInfos2);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        HlsTrackMetadataEntry that = (HlsTrackMetadataEntry) other;
        if (!TextUtils.equals(this.groupId, that.groupId) || !TextUtils.equals(this.name, that.name) || !this.variantInfos.equals(that.variantInfos)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        String str = this.groupId;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.name;
        if (str2 != null) {
            i = str2.hashCode();
        }
        return ((hashCode + i) * 31) + this.variantInfos.hashCode();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.groupId);
        dest.writeString(this.name);
        int variantInfosSize = this.variantInfos.size();
        dest.writeInt(variantInfosSize);
        for (int i = 0; i < variantInfosSize; i++) {
            dest.writeParcelable(this.variantInfos.get(i), 0);
        }
    }
}
