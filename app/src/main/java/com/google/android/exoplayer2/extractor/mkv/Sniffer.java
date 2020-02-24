package com.google.android.exoplayer2.extractor.mkv;

import android.support.p001v4.media.session.PlaybackStateCompat;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.common.primitives.UnsignedBytes;
import java.io.IOException;

final class Sniffer {
    private static final int ID_EBML = 440786851;
    private static final int SEARCH_LENGTH = 1024;
    private int peekLength;
    private final ParsableByteArray scratch = new ParsableByteArray(8);

    public boolean sniff(ExtractorInput input) throws IOException, InterruptedException {
        ExtractorInput extractorInput = input;
        long inputLength = input.getLength();
        long j = PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        if (inputLength != -1 && inputLength <= PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            j = inputLength;
        }
        int bytesToSearch = (int) j;
        boolean z = false;
        extractorInput.peekFully(this.scratch.data, 0, 4);
        this.peekLength = 4;
        for (long tag = this.scratch.readUnsignedInt(); tag != 440786851; tag = ((tag << 8) & -256) | ((long) (this.scratch.data[0] & UnsignedBytes.MAX_VALUE))) {
            int i = this.peekLength + 1;
            this.peekLength = i;
            if (i == bytesToSearch) {
                return false;
            }
            extractorInput.peekFully(this.scratch.data, 0, 1);
        }
        long headerSize = readUint(input);
        long headerStart = (long) this.peekLength;
        if (headerSize == Long.MIN_VALUE) {
            return false;
        }
        if (inputLength != -1 && headerStart + headerSize >= inputLength) {
            return false;
        }
        while (true) {
            int i2 = this.peekLength;
            if (((long) i2) >= headerStart + headerSize) {
                return ((long) i2) == headerStart + headerSize;
            }
            if (readUint(input) == Long.MIN_VALUE) {
                return z;
            }
            int bytesToSearch2 = bytesToSearch;
            long size = readUint(input);
            if (size < 0 || size > 2147483647L) {
                return false;
            }
            if (size != 0) {
                int sizeInt = (int) size;
                extractorInput.advancePeekPosition(sizeInt);
                this.peekLength += sizeInt;
            }
            extractorInput = input;
            bytesToSearch = bytesToSearch2;
            z = false;
        }
    }

    private long readUint(ExtractorInput input) throws IOException, InterruptedException {
        input.peekFully(this.scratch.data, 0, 1);
        int value = this.scratch.data[0] & 255;
        if (value == 0) {
            return Long.MIN_VALUE;
        }
        int mask = 128;
        int length = 0;
        while ((value & mask) == 0) {
            mask >>= 1;
            length++;
        }
        int value2 = value & (mask ^ -1);
        input.peekFully(this.scratch.data, 1, length);
        for (int i = 0; i < length; i++) {
            value2 = (value2 << 8) + (this.scratch.data[i + 1] & UnsignedBytes.MAX_VALUE);
        }
        this.peekLength += length + 1;
        return (long) value2;
    }
}
