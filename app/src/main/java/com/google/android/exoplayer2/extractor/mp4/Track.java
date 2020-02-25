package com.google.android.exoplayer2.extractor.mp4;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.Format;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Track {
    public static final int TRANSFORMATION_CEA608_CDAT = 1;
    public static final int TRANSFORMATION_NONE = 0;
    public final long durationUs;
    @Nullable
    public final long[] editListDurations;
    @Nullable
    public final long[] editListMediaTimes;
    public final Format format;

    /* renamed from: id */
    public final int f77id;
    public final long movieTimescale;
    public final int nalUnitLengthFieldLength;
    public final int sampleTransformation;
    public final long timescale;
    public final int type;
    @Nullable
    private final TrackEncryptionBox[] sampleDescriptionEncryptionBoxes;

    public Track(int id, int type2, long timescale2, long movieTimescale2, long durationUs2, Format format2, int sampleTransformation2, @Nullable TrackEncryptionBox[] sampleDescriptionEncryptionBoxes2, int nalUnitLengthFieldLength2, @Nullable long[] editListDurations2, @Nullable long[] editListMediaTimes2) {
        this.f77id = id;
        this.type = type2;
        this.timescale = timescale2;
        this.movieTimescale = movieTimescale2;
        this.durationUs = durationUs2;
        this.format = format2;
        this.sampleTransformation = sampleTransformation2;
        this.sampleDescriptionEncryptionBoxes = sampleDescriptionEncryptionBoxes2;
        this.nalUnitLengthFieldLength = nalUnitLengthFieldLength2;
        this.editListDurations = editListDurations2;
        this.editListMediaTimes = editListMediaTimes2;
    }

    public TrackEncryptionBox getSampleDescriptionEncryptionBox(int sampleDescriptionIndex) {
        TrackEncryptionBox[] trackEncryptionBoxArr = this.sampleDescriptionEncryptionBoxes;
        if (trackEncryptionBoxArr == null) {
            return null;
        }
        return trackEncryptionBoxArr[sampleDescriptionIndex];
    }

    public Track copyWithFormat(Format format2) {
        return new Track(this.f77id, this.type, this.timescale, this.movieTimescale, this.durationUs, format2, this.sampleTransformation, this.sampleDescriptionEncryptionBoxes, this.nalUnitLengthFieldLength, this.editListDurations, this.editListMediaTimes);
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Transformation {
    }
}
