package com.google.android.exoplayer2.extractor.p007ts;

import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;

/* renamed from: com.google.android.exoplayer2.extractor.ts.SectionReader */
public final class SectionReader implements TsPayloadReader {
    private static final int DEFAULT_SECTION_BUFFER_LENGTH = 32;
    private static final int MAX_SECTION_LENGTH = 4098;
    private static final int SECTION_HEADER_LENGTH = 3;
    private final SectionPayloadReader reader;
    private final ParsableByteArray sectionData = new ParsableByteArray(32);
    private int bytesRead;
    private boolean sectionSyntaxIndicator;
    private int totalSectionLength;
    private boolean waitingForPayloadStart;

    public SectionReader(SectionPayloadReader reader2) {
        this.reader = reader2;
    }

    public void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator idGenerator) {
        this.reader.init(timestampAdjuster, extractorOutput, idGenerator);
        this.waitingForPayloadStart = true;
    }

    public void seek() {
        this.waitingForPayloadStart = true;
    }

    public void consume(ParsableByteArray data, int flags) {
        boolean payloadUnitStartIndicator = (flags & 1) != 0;
        int payloadStartPosition = -1;
        if (payloadUnitStartIndicator) {
            payloadStartPosition = data.getPosition() + data.readUnsignedByte();
        }
        if (this.waitingForPayloadStart != 0) {
            if (payloadUnitStartIndicator) {
                this.waitingForPayloadStart = false;
                data.setPosition(payloadStartPosition);
                this.bytesRead = 0;
            } else {
                return;
            }
        }
        while (data.bytesLeft() > 0) {
            int i = this.bytesRead;
            if (i < 3) {
                if (i == 0) {
                    int tableId = data.readUnsignedByte();
                    data.setPosition(data.getPosition() - 1);
                    if (tableId == 255) {
                        this.waitingForPayloadStart = true;
                        return;
                    }
                }
                int headerBytesToRead = Math.min(data.bytesLeft(), 3 - this.bytesRead);
                data.readBytes(this.sectionData.data, this.bytesRead, headerBytesToRead);
                this.bytesRead += headerBytesToRead;
                if (this.bytesRead == 3) {
                    this.sectionData.reset(3);
                    this.sectionData.skipBytes(1);
                    int secondHeaderByte = this.sectionData.readUnsignedByte();
                    int thirdHeaderByte = this.sectionData.readUnsignedByte();
                    this.sectionSyntaxIndicator = (secondHeaderByte & 128) != 0;
                    this.totalSectionLength = (((secondHeaderByte & 15) << 8) | thirdHeaderByte) + 3;
                    if (this.sectionData.capacity() < this.totalSectionLength) {
                        byte[] bytes = this.sectionData.data;
                        this.sectionData.reset(Math.min(4098, Math.max(this.totalSectionLength, bytes.length * 2)));
                        System.arraycopy(bytes, 0, this.sectionData.data, 0, 3);
                    }
                }
            } else {
                int bodyBytesToRead = Math.min(data.bytesLeft(), this.totalSectionLength - this.bytesRead);
                data.readBytes(this.sectionData.data, this.bytesRead, bodyBytesToRead);
                this.bytesRead += bodyBytesToRead;
                int i2 = this.bytesRead;
                int i3 = this.totalSectionLength;
                if (i2 != i3) {
                    continue;
                } else {
                    if (!this.sectionSyntaxIndicator) {
                        this.sectionData.reset(i3);
                    } else if (Util.crc(this.sectionData.data, 0, this.totalSectionLength, -1) != 0) {
                        this.waitingForPayloadStart = true;
                        return;
                    } else {
                        this.sectionData.reset(this.totalSectionLength - 4);
                    }
                    this.reader.consume(this.sectionData);
                    this.bytesRead = 0;
                }
            }
        }
    }
}
