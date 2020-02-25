package com.google.android.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.util.Util;

import java.util.Arrays;

public final class MlltFrame extends Id3Frame {
    public static final Parcelable.Creator<MlltFrame> CREATOR = new Parcelable.Creator<MlltFrame>() {
        public MlltFrame createFromParcel(Parcel in) {
            return new MlltFrame(in);
        }

        public MlltFrame[] newArray(int size) {
            return new MlltFrame[size];
        }
    };

    /* renamed from: ID */
    public static final String f87ID = "MLLT";
    public final int bytesBetweenReference;
    public final int[] bytesDeviations;
    public final int millisecondsBetweenReference;
    public final int[] millisecondsDeviations;
    public final int mpegFramesBetweenReference;

    public MlltFrame(int mpegFramesBetweenReference2, int bytesBetweenReference2, int millisecondsBetweenReference2, int[] bytesDeviations2, int[] millisecondsDeviations2) {
        super(f87ID);
        this.mpegFramesBetweenReference = mpegFramesBetweenReference2;
        this.bytesBetweenReference = bytesBetweenReference2;
        this.millisecondsBetweenReference = millisecondsBetweenReference2;
        this.bytesDeviations = bytesDeviations2;
        this.millisecondsDeviations = millisecondsDeviations2;
    }

    MlltFrame(Parcel in) {
        super(f87ID);
        this.mpegFramesBetweenReference = in.readInt();
        this.bytesBetweenReference = in.readInt();
        this.millisecondsBetweenReference = in.readInt();
        this.bytesDeviations = (int[]) Util.castNonNull(in.createIntArray());
        this.millisecondsDeviations = (int[]) Util.castNonNull(in.createIntArray());
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MlltFrame other = (MlltFrame) obj;
        if (this.mpegFramesBetweenReference == other.mpegFramesBetweenReference && this.bytesBetweenReference == other.bytesBetweenReference && this.millisecondsBetweenReference == other.millisecondsBetweenReference && Arrays.equals(this.bytesDeviations, other.bytesDeviations) && Arrays.equals(this.millisecondsDeviations, other.millisecondsDeviations)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((((((17 * 31) + this.mpegFramesBetweenReference) * 31) + this.bytesBetweenReference) * 31) + this.millisecondsBetweenReference) * 31) + Arrays.hashCode(this.bytesDeviations)) * 31) + Arrays.hashCode(this.millisecondsDeviations);
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mpegFramesBetweenReference);
        dest.writeInt(this.bytesBetweenReference);
        dest.writeInt(this.millisecondsBetweenReference);
        dest.writeIntArray(this.bytesDeviations);
        dest.writeIntArray(this.millisecondsDeviations);
    }

    public int describeContents() {
        return 0;
    }
}
