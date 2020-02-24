package com.google.android.exoplayer2.extractor.mkv;

import com.google.android.exoplayer2.extractor.ExtractorInput;
import java.io.IOException;

final class VarintReader {
    private static final int STATE_BEGIN_READING = 0;
    private static final int STATE_READ_CONTENTS = 1;
    private static final long[] VARINT_LENGTH_MASKS = {128, 64, 32, 16, 8, 4, 2, 1};
    private int length;
    private final byte[] scratch = new byte[8];
    private int state;

    public void reset() {
        this.state = 0;
        this.length = 0;
    }

    public long readUnsignedVarint(ExtractorInput input, boolean allowEndOfInput, boolean removeLengthMask, int maximumAllowedLength) throws IOException, InterruptedException {
        if (this.state == 0) {
            if (!input.readFully(this.scratch, 0, 1, allowEndOfInput)) {
                return -1;
            }
            this.length = parseUnsignedVarintLength(this.scratch[0] & 255);
            if (this.length != -1) {
                this.state = 1;
            } else {
                throw new IllegalStateException("No valid varint length mask found");
            }
        }
        int firstByte = this.length;
        if (firstByte > maximumAllowedLength) {
            this.state = 0;
            return -2;
        }
        if (firstByte != 1) {
            input.readFully(this.scratch, 1, firstByte - 1);
        }
        this.state = 0;
        return assembleVarint(this.scratch, this.length, removeLengthMask);
    }

    public int getLastLength() {
        return this.length;
    }

    public static int parseUnsignedVarintLength(int firstByte) {
        int i = 0;
        while (true) {
            long[] jArr = VARINT_LENGTH_MASKS;
            if (i >= jArr.length) {
                return -1;
            }
            if ((jArr[i] & ((long) firstByte)) != 0) {
                return i + 1;
            }
            i++;
        }
    }

    public static long assembleVarint(byte[] varintBytes, int varintLength, boolean removeLengthMask) {
        long varint = ((long) varintBytes[0]) & 255;
        if (removeLengthMask) {
            varint &= VARINT_LENGTH_MASKS[varintLength - 1] ^ -1;
        }
        for (int i = 1; i < varintLength; i++) {
            varint = (varint << 8) | (((long) varintBytes[i]) & 255);
        }
        return varint;
    }
}
