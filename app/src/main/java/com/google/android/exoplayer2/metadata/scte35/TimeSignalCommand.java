package com.google.android.exoplayer2.metadata.scte35;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;

public final class TimeSignalCommand extends SpliceCommand {
    public static final Parcelable.Creator<TimeSignalCommand> CREATOR = new Parcelable.Creator<TimeSignalCommand>() {
        public TimeSignalCommand createFromParcel(Parcel in) {
            return new TimeSignalCommand(in.readLong(), in.readLong());
        }

        public TimeSignalCommand[] newArray(int size) {
            return new TimeSignalCommand[size];
        }
    };
    public final long playbackPositionUs;
    public final long ptsTime;

    private TimeSignalCommand(long ptsTime2, long playbackPositionUs2) {
        this.ptsTime = ptsTime2;
        this.playbackPositionUs = playbackPositionUs2;
    }

    static TimeSignalCommand parseFromSection(ParsableByteArray sectionData, long ptsAdjustment, TimestampAdjuster timestampAdjuster) {
        long ptsTime2 = parseSpliceTime(sectionData, ptsAdjustment);
        return new TimeSignalCommand(ptsTime2, timestampAdjuster.adjustTsTimestamp(ptsTime2));
    }

    static long parseSpliceTime(ParsableByteArray sectionData, long ptsAdjustment) {
        long firstByte = (long) sectionData.readUnsignedByte();
        if ((128 & firstByte) != 0) {
            return ((((1 & firstByte) << 32) | sectionData.readUnsignedInt()) + ptsAdjustment) & 8589934591L;
        }
        return C0841C.TIME_UNSET;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.ptsTime);
        dest.writeLong(this.playbackPositionUs);
    }
}
