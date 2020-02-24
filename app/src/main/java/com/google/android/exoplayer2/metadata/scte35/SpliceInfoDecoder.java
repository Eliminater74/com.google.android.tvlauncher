package com.google.android.exoplayer2.metadata.scte35;

import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataDecoder;
import com.google.android.exoplayer2.metadata.MetadataInputBuffer;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import java.nio.ByteBuffer;

public final class SpliceInfoDecoder implements MetadataDecoder {
    private static final int TYPE_PRIVATE_COMMAND = 255;
    private static final int TYPE_SPLICE_INSERT = 5;
    private static final int TYPE_SPLICE_NULL = 0;
    private static final int TYPE_SPLICE_SCHEDULE = 4;
    private static final int TYPE_TIME_SIGNAL = 6;
    private final ParsableByteArray sectionData = new ParsableByteArray();
    private final ParsableBitArray sectionHeader = new ParsableBitArray();
    private TimestampAdjuster timestampAdjuster;

    public Metadata decode(MetadataInputBuffer inputBuffer) {
        if (this.timestampAdjuster == null || inputBuffer.subsampleOffsetUs != this.timestampAdjuster.getTimestampOffsetUs()) {
            this.timestampAdjuster = new TimestampAdjuster(inputBuffer.timeUs);
            this.timestampAdjuster.adjustSampleTimestamp(inputBuffer.timeUs - inputBuffer.subsampleOffsetUs);
        }
        ByteBuffer buffer = inputBuffer.data;
        byte[] data = buffer.array();
        int size = buffer.limit();
        this.sectionData.reset(data, size);
        this.sectionHeader.reset(data, size);
        this.sectionHeader.skipBits(39);
        long ptsAdjustment = (((long) this.sectionHeader.readBits(1)) << 32) | ((long) this.sectionHeader.readBits(32));
        this.sectionHeader.skipBits(20);
        int spliceCommandLength = this.sectionHeader.readBits(12);
        int spliceCommandType = this.sectionHeader.readBits(8);
        SpliceCommand command = null;
        this.sectionData.skipBytes(14);
        if (spliceCommandType == 0) {
            command = new SpliceNullCommand();
        } else if (spliceCommandType == 255) {
            command = PrivateCommand.parseFromSection(this.sectionData, spliceCommandLength, ptsAdjustment);
        } else if (spliceCommandType == 4) {
            command = SpliceScheduleCommand.parseFromSection(this.sectionData);
        } else if (spliceCommandType == 5) {
            command = SpliceInsertCommand.parseFromSection(this.sectionData, ptsAdjustment, this.timestampAdjuster);
        } else if (spliceCommandType == 6) {
            command = TimeSignalCommand.parseFromSection(this.sectionData, ptsAdjustment, this.timestampAdjuster);
        }
        if (command == null) {
            return new Metadata(new Metadata.Entry[0]);
        }
        return new Metadata(command);
    }
}
