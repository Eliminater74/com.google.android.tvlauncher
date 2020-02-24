package com.google.android.exoplayer2.extractor.ogg;

import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;
import java.util.Arrays;

final class OggPacket {
    private int currentSegmentIndex = -1;
    private final ParsableByteArray packetArray = new ParsableByteArray(new byte[OggPageHeader.MAX_PAGE_PAYLOAD], 0);
    private final OggPageHeader pageHeader = new OggPageHeader();
    private boolean populated;
    private int segmentCount;

    OggPacket() {
    }

    public void reset() {
        this.pageHeader.reset();
        this.packetArray.reset();
        this.currentSegmentIndex = -1;
        this.populated = false;
    }

    public boolean populate(ExtractorInput input) throws IOException, InterruptedException {
        int i;
        Assertions.checkState(input != null);
        if (this.populated) {
            this.populated = false;
            this.packetArray.reset();
        }
        while (!this.populated) {
            if (this.currentSegmentIndex < 0) {
                if (!this.pageHeader.populate(input, true)) {
                    return false;
                }
                int segmentIndex = 0;
                int bytesToSkip = this.pageHeader.headerSize;
                if ((this.pageHeader.type & 1) == 1 && this.packetArray.limit() == 0) {
                    bytesToSkip += calculatePacketSize(0);
                    segmentIndex = 0 + this.segmentCount;
                }
                input.skipFully(bytesToSkip);
                this.currentSegmentIndex = segmentIndex;
            }
            int size = calculatePacketSize(this.currentSegmentIndex);
            int segmentIndex2 = this.currentSegmentIndex + this.segmentCount;
            if (size > 0) {
                if (this.packetArray.capacity() < this.packetArray.limit() + size) {
                    ParsableByteArray parsableByteArray = this.packetArray;
                    parsableByteArray.data = Arrays.copyOf(parsableByteArray.data, this.packetArray.limit() + size);
                }
                input.readFully(this.packetArray.data, this.packetArray.limit(), size);
                ParsableByteArray parsableByteArray2 = this.packetArray;
                parsableByteArray2.setLimit(parsableByteArray2.limit() + size);
                this.populated = this.pageHeader.laces[segmentIndex2 + -1] != 255;
            }
            if (segmentIndex2 == this.pageHeader.pageSegmentCount) {
                i = -1;
            } else {
                i = segmentIndex2;
            }
            this.currentSegmentIndex = i;
        }
        return true;
    }

    public OggPageHeader getPageHeader() {
        return this.pageHeader;
    }

    public ParsableByteArray getPayload() {
        return this.packetArray;
    }

    public void trimPayload() {
        if (this.packetArray.data.length != 65025) {
            ParsableByteArray parsableByteArray = this.packetArray;
            parsableByteArray.data = Arrays.copyOf(parsableByteArray.data, Math.max((int) OggPageHeader.MAX_PAGE_PAYLOAD, this.packetArray.limit()));
        }
    }

    private int calculatePacketSize(int startSegmentIndex) {
        this.segmentCount = 0;
        int size = 0;
        while (this.segmentCount + startSegmentIndex < this.pageHeader.pageSegmentCount) {
            int[] iArr = this.pageHeader.laces;
            int i = this.segmentCount;
            this.segmentCount = i + 1;
            int segmentLength = iArr[i + startSegmentIndex];
            size += segmentLength;
            if (segmentLength != 255) {
                break;
            }
        }
        return size;
    }
}
