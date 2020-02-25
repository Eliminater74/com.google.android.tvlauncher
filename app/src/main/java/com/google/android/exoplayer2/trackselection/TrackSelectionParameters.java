package com.google.android.exoplayer2.trackselection;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.android.exoplayer2.util.Util;

public class TrackSelectionParameters implements Parcelable {
    public static final Parcelable.Creator<TrackSelectionParameters> CREATOR = new Parcelable.Creator<TrackSelectionParameters>() {
        public TrackSelectionParameters createFromParcel(Parcel in) {
            return new TrackSelectionParameters(in);
        }

        public TrackSelectionParameters[] newArray(int size) {
            return new TrackSelectionParameters[size];
        }
    };
    public static final TrackSelectionParameters DEFAULT = new TrackSelectionParameters();
    public final int disabledTextTrackSelectionFlags;
    @Nullable
    public final String preferredAudioLanguage;
    @Nullable
    public final String preferredTextLanguage;
    public final boolean selectUndeterminedTextLanguage;

    TrackSelectionParameters() {
        this(null, null, false, 0);
    }

    TrackSelectionParameters(@Nullable String preferredAudioLanguage2, @Nullable String preferredTextLanguage2, boolean selectUndeterminedTextLanguage2, int disabledTextTrackSelectionFlags2) {
        this.preferredAudioLanguage = Util.normalizeLanguageCode(preferredAudioLanguage2);
        this.preferredTextLanguage = Util.normalizeLanguageCode(preferredTextLanguage2);
        this.selectUndeterminedTextLanguage = selectUndeterminedTextLanguage2;
        this.disabledTextTrackSelectionFlags = disabledTextTrackSelectionFlags2;
    }

    TrackSelectionParameters(Parcel in) {
        this.preferredAudioLanguage = in.readString();
        this.preferredTextLanguage = in.readString();
        this.selectUndeterminedTextLanguage = Util.readBoolean(in);
        this.disabledTextTrackSelectionFlags = in.readInt();
    }

    public Builder buildUpon() {
        return new Builder(this);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TrackSelectionParameters other = (TrackSelectionParameters) obj;
        if (!TextUtils.equals(this.preferredAudioLanguage, other.preferredAudioLanguage) || !TextUtils.equals(this.preferredTextLanguage, other.preferredTextLanguage) || this.selectUndeterminedTextLanguage != other.selectUndeterminedTextLanguage || this.disabledTextTrackSelectionFlags != other.disabledTextTrackSelectionFlags) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i = 1 * 31;
        String str = this.preferredAudioLanguage;
        int i2 = 0;
        int result = (i + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.preferredTextLanguage;
        if (str2 != null) {
            i2 = str2.hashCode();
        }
        return ((((result + i2) * 31) + (this.selectUndeterminedTextLanguage ? 1 : 0)) * 31) + this.disabledTextTrackSelectionFlags;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.preferredAudioLanguage);
        dest.writeString(this.preferredTextLanguage);
        Util.writeBoolean(dest, this.selectUndeterminedTextLanguage);
        dest.writeInt(this.disabledTextTrackSelectionFlags);
    }

    public static class Builder {
        int disabledTextTrackSelectionFlags;
        @Nullable
        String preferredAudioLanguage;
        @Nullable
        String preferredTextLanguage;
        boolean selectUndeterminedTextLanguage;

        public Builder() {
            this(TrackSelectionParameters.DEFAULT);
        }

        Builder(TrackSelectionParameters initialValues) {
            this.preferredAudioLanguage = initialValues.preferredAudioLanguage;
            this.preferredTextLanguage = initialValues.preferredTextLanguage;
            this.selectUndeterminedTextLanguage = initialValues.selectUndeterminedTextLanguage;
            this.disabledTextTrackSelectionFlags = initialValues.disabledTextTrackSelectionFlags;
        }

        public Builder setPreferredAudioLanguage(@Nullable String preferredAudioLanguage2) {
            this.preferredAudioLanguage = preferredAudioLanguage2;
            return this;
        }

        public Builder setPreferredTextLanguage(@Nullable String preferredTextLanguage2) {
            this.preferredTextLanguage = preferredTextLanguage2;
            return this;
        }

        public Builder setSelectUndeterminedTextLanguage(boolean selectUndeterminedTextLanguage2) {
            this.selectUndeterminedTextLanguage = selectUndeterminedTextLanguage2;
            return this;
        }

        public Builder setDisabledTextTrackSelectionFlags(int disabledTextTrackSelectionFlags2) {
            this.disabledTextTrackSelectionFlags = disabledTextTrackSelectionFlags2;
            return this;
        }

        public TrackSelectionParameters build() {
            return new TrackSelectionParameters(this.preferredAudioLanguage, this.preferredTextLanguage, this.selectUndeterminedTextLanguage, this.disabledTextTrackSelectionFlags);
        }
    }
}
