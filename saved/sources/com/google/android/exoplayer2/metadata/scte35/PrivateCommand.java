package com.google.android.exoplayer2.metadata.scte35;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.util.ParsableByteArray;

public final class PrivateCommand extends SpliceCommand {
    public static final Parcelable.Creator<PrivateCommand> CREATOR = new Parcelable.Creator<PrivateCommand>() {
        public PrivateCommand createFromParcel(Parcel in) {
            return new PrivateCommand(in);
        }

        public PrivateCommand[] newArray(int size) {
            return new PrivateCommand[size];
        }
    };
    public final byte[] commandBytes;
    public final long identifier;
    public final long ptsAdjustment;

    private PrivateCommand(long identifier2, byte[] commandBytes2, long ptsAdjustment2) {
        this.ptsAdjustment = ptsAdjustment2;
        this.identifier = identifier2;
        this.commandBytes = commandBytes2;
    }

    private PrivateCommand(Parcel in) {
        this.ptsAdjustment = in.readLong();
        this.identifier = in.readLong();
        this.commandBytes = new byte[in.readInt()];
        in.readByteArray(this.commandBytes);
    }

    static PrivateCommand parseFromSection(ParsableByteArray sectionData, int commandLength, long ptsAdjustment2) {
        long identifier2 = sectionData.readUnsignedInt();
        byte[] privateBytes = new byte[(commandLength - 4)];
        sectionData.readBytes(privateBytes, 0, privateBytes.length);
        return new PrivateCommand(identifier2, privateBytes, ptsAdjustment2);
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.ptsAdjustment);
        dest.writeLong(this.identifier);
        dest.writeInt(this.commandBytes.length);
        dest.writeByteArray(this.commandBytes);
    }
}
