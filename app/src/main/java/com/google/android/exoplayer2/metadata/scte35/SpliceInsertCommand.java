package com.google.android.exoplayer2.metadata.scte35;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SpliceInsertCommand extends SpliceCommand {
    public static final Parcelable.Creator<SpliceInsertCommand> CREATOR = new Parcelable.Creator<SpliceInsertCommand>() {
        public SpliceInsertCommand createFromParcel(Parcel in) {
            return new SpliceInsertCommand(in);
        }

        public SpliceInsertCommand[] newArray(int size) {
            return new SpliceInsertCommand[size];
        }
    };
    public final boolean autoReturn;
    public final int availNum;
    public final int availsExpected;
    public final long breakDurationUs;
    public final List<ComponentSplice> componentSpliceList;
    public final boolean outOfNetworkIndicator;
    public final boolean programSpliceFlag;
    public final long programSplicePlaybackPositionUs;
    public final long programSplicePts;
    public final boolean spliceEventCancelIndicator;
    public final long spliceEventId;
    public final boolean spliceImmediateFlag;
    public final int uniqueProgramId;

    private SpliceInsertCommand(long spliceEventId2, boolean spliceEventCancelIndicator2, boolean outOfNetworkIndicator2, boolean programSpliceFlag2, boolean spliceImmediateFlag2, long programSplicePts2, long programSplicePlaybackPositionUs2, List<ComponentSplice> componentSpliceList2, boolean autoReturn2, long breakDurationUs2, int uniqueProgramId2, int availNum2, int availsExpected2) {
        this.spliceEventId = spliceEventId2;
        this.spliceEventCancelIndicator = spliceEventCancelIndicator2;
        this.outOfNetworkIndicator = outOfNetworkIndicator2;
        this.programSpliceFlag = programSpliceFlag2;
        this.spliceImmediateFlag = spliceImmediateFlag2;
        this.programSplicePts = programSplicePts2;
        this.programSplicePlaybackPositionUs = programSplicePlaybackPositionUs2;
        this.componentSpliceList = Collections.unmodifiableList(componentSpliceList2);
        this.autoReturn = autoReturn2;
        this.breakDurationUs = breakDurationUs2;
        this.uniqueProgramId = uniqueProgramId2;
        this.availNum = availNum2;
        this.availsExpected = availsExpected2;
    }

    private SpliceInsertCommand(Parcel in) {
        this.spliceEventId = in.readLong();
        boolean z = false;
        this.spliceEventCancelIndicator = in.readByte() == 1;
        this.outOfNetworkIndicator = in.readByte() == 1;
        this.programSpliceFlag = in.readByte() == 1;
        this.spliceImmediateFlag = in.readByte() == 1;
        this.programSplicePts = in.readLong();
        this.programSplicePlaybackPositionUs = in.readLong();
        int componentSpliceListSize = in.readInt();
        List<ComponentSplice> componentSpliceList2 = new ArrayList<>(componentSpliceListSize);
        for (int i = 0; i < componentSpliceListSize; i++) {
            componentSpliceList2.add(ComponentSplice.createFromParcel(in));
        }
        this.componentSpliceList = Collections.unmodifiableList(componentSpliceList2);
        this.autoReturn = in.readByte() == 1 ? true : z;
        this.breakDurationUs = in.readLong();
        this.uniqueProgramId = in.readInt();
        this.availNum = in.readInt();
        this.availsExpected = in.readInt();
    }

    /* JADX INFO: Multiple debug info for r1v6 int: [D('firstByte' long), D('uniqueProgramId' int)] */
    /* JADX INFO: Multiple debug info for r2v7 long: [D('programSpliceFlag' boolean), D('componentSplicePts' long)] */
    static SpliceInsertCommand parseFromSection(ParsableByteArray sectionData, long ptsAdjustment, TimestampAdjuster timestampAdjuster) {
        boolean spliceImmediateFlag2;
        boolean programSpliceFlag2;
        int availsExpected2;
        int availNum2;
        int uniqueProgramId2;
        long breakDurationUs2;
        boolean autoReturn2;
        List<ComponentSplice> componentSplices;
        boolean outOfNetworkIndicator2;
        long programSplicePts2;
        boolean outOfNetworkIndicator3;
        TimestampAdjuster timestampAdjuster2 = timestampAdjuster;
        long spliceEventId2 = sectionData.readUnsignedInt();
        boolean spliceEventCancelIndicator2 = (sectionData.readUnsignedByte() & 128) != 0;
        long programSplicePts3 = C0841C.TIME_UNSET;
        List<ComponentSplice> componentSplices2 = Collections.emptyList();
        boolean autoReturn3 = false;
        long breakDurationUs3 = C0841C.TIME_UNSET;
        if (!spliceEventCancelIndicator2) {
            int headerByte = sectionData.readUnsignedByte();
            boolean outOfNetworkIndicator4 = (headerByte & 128) != 0;
            boolean programSpliceFlag3 = (headerByte & 64) != 0;
            boolean durationFlag = headerByte & true;
            boolean spliceImmediateFlag3 = (headerByte & 16) != 0;
            if (programSpliceFlag3 && !spliceImmediateFlag3) {
                programSplicePts3 = TimeSignalCommand.parseSpliceTime(sectionData, ptsAdjustment);
            }
            if (!programSpliceFlag3) {
                int componentCount = sectionData.readUnsignedByte();
                outOfNetworkIndicator3 = outOfNetworkIndicator4;
                componentSplices2 = new ArrayList<>(componentCount);
                int i = 0;
                while (i < componentCount) {
                    int componentTag = sectionData.readUnsignedByte();
                    long componentSplicePts = C0841C.TIME_UNSET;
                    if (!spliceImmediateFlag3) {
                        componentSplicePts = TimeSignalCommand.parseSpliceTime(sectionData, ptsAdjustment);
                    }
                    boolean programSpliceFlag4 = programSpliceFlag3;
                    long componentSplicePts2 = componentSplicePts;
                    componentSplices2.add(new ComponentSplice(componentTag, componentSplicePts2, timestampAdjuster2.adjustTsTimestamp(componentSplicePts2)));
                    i++;
                    programSpliceFlag3 = programSpliceFlag4;
                    componentCount = componentCount;
                    spliceImmediateFlag3 = spliceImmediateFlag3;
                }
                programSpliceFlag2 = programSpliceFlag3;
                spliceImmediateFlag2 = spliceImmediateFlag3;
            } else {
                outOfNetworkIndicator3 = outOfNetworkIndicator4;
                programSpliceFlag2 = programSpliceFlag3;
                spliceImmediateFlag2 = spliceImmediateFlag3;
            }
            if (durationFlag) {
                long firstByte = (long) sectionData.readUnsignedByte();
                autoReturn3 = (firstByte & 128) != 0;
                breakDurationUs3 = (1000 * (((firstByte & 1) << 32) | sectionData.readUnsignedInt())) / 90;
            }
            uniqueProgramId2 = sectionData.readUnsignedShort();
            availNum2 = sectionData.readUnsignedByte();
            availsExpected2 = sectionData.readUnsignedByte();
            componentSplices = componentSplices2;
            autoReturn2 = autoReturn3;
            breakDurationUs2 = breakDurationUs3;
            outOfNetworkIndicator2 = outOfNetworkIndicator3;
            programSplicePts2 = programSplicePts3;
        } else {
            outOfNetworkIndicator2 = false;
            programSpliceFlag2 = false;
            spliceImmediateFlag2 = false;
            componentSplices = componentSplices2;
            uniqueProgramId2 = 0;
            availNum2 = 0;
            availsExpected2 = 0;
            autoReturn2 = false;
            breakDurationUs2 = -9223372036854775807L;
            programSplicePts2 = -9223372036854775807L;
        }
        return new SpliceInsertCommand(spliceEventId2, spliceEventCancelIndicator2, outOfNetworkIndicator2, programSpliceFlag2, spliceImmediateFlag2, programSplicePts2, timestampAdjuster2.adjustTsTimestamp(programSplicePts2), componentSplices, autoReturn2, breakDurationUs2, uniqueProgramId2, availNum2, availsExpected2);
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.spliceEventId);
        dest.writeByte(this.spliceEventCancelIndicator ? (byte) 1 : 0);
        dest.writeByte(this.outOfNetworkIndicator ? (byte) 1 : 0);
        dest.writeByte(this.programSpliceFlag ? (byte) 1 : 0);
        dest.writeByte(this.spliceImmediateFlag ? (byte) 1 : 0);
        dest.writeLong(this.programSplicePts);
        dest.writeLong(this.programSplicePlaybackPositionUs);
        int componentSpliceListSize = this.componentSpliceList.size();
        dest.writeInt(componentSpliceListSize);
        for (int i = 0; i < componentSpliceListSize; i++) {
            this.componentSpliceList.get(i).writeToParcel(dest);
        }
        dest.writeByte((byte) this.autoReturn);
        dest.writeLong(this.breakDurationUs);
        dest.writeInt(this.uniqueProgramId);
        dest.writeInt(this.availNum);
        dest.writeInt(this.availsExpected);
    }

    public static final class ComponentSplice {
        public final long componentSplicePlaybackPositionUs;
        public final long componentSplicePts;
        public final int componentTag;

        private ComponentSplice(int componentTag2, long componentSplicePts2, long componentSplicePlaybackPositionUs2) {
            this.componentTag = componentTag2;
            this.componentSplicePts = componentSplicePts2;
            this.componentSplicePlaybackPositionUs = componentSplicePlaybackPositionUs2;
        }

        public static ComponentSplice createFromParcel(Parcel in) {
            return new ComponentSplice(in.readInt(), in.readLong(), in.readLong());
        }

        public void writeToParcel(Parcel dest) {
            dest.writeInt(this.componentTag);
            dest.writeLong(this.componentSplicePts);
            dest.writeLong(this.componentSplicePlaybackPositionUs);
        }
    }
}
