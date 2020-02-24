package com.google.android.exoplayer2.extractor.p007ts;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import java.io.IOException;

/* renamed from: com.google.android.exoplayer2.extractor.ts.PsDurationReader */
final class PsDurationReader {
    private static final int TIMESTAMP_SEARCH_BYTES = 20000;
    private long durationUs = C0841C.TIME_UNSET;
    private long firstScrValue = C0841C.TIME_UNSET;
    private boolean isDurationRead;
    private boolean isFirstScrValueRead;
    private boolean isLastScrValueRead;
    private long lastScrValue = C0841C.TIME_UNSET;
    private final ParsableByteArray packetBuffer = new ParsableByteArray();
    private final TimestampAdjuster scrTimestampAdjuster = new TimestampAdjuster(0);

    PsDurationReader() {
    }

    public boolean isDurationReadFinished() {
        return this.isDurationRead;
    }

    public TimestampAdjuster getScrTimestampAdjuster() {
        return this.scrTimestampAdjuster;
    }

    public int readDuration(ExtractorInput input, PositionHolder seekPositionHolder) throws IOException, InterruptedException {
        if (!this.isLastScrValueRead) {
            return readLastScrValue(input, seekPositionHolder);
        }
        if (this.lastScrValue == C0841C.TIME_UNSET) {
            return finishReadDuration(input);
        }
        if (!this.isFirstScrValueRead) {
            return readFirstScrValue(input, seekPositionHolder);
        }
        long j = this.firstScrValue;
        if (j == C0841C.TIME_UNSET) {
            return finishReadDuration(input);
        }
        this.durationUs = this.scrTimestampAdjuster.adjustTsTimestamp(this.lastScrValue) - this.scrTimestampAdjuster.adjustTsTimestamp(j);
        return finishReadDuration(input);
    }

    public long getDurationUs() {
        return this.durationUs;
    }

    public static long readScrValueFromPack(ParsableByteArray packetBuffer2) {
        int originalPosition = packetBuffer2.getPosition();
        if (packetBuffer2.bytesLeft() < 9) {
            return C0841C.TIME_UNSET;
        }
        byte[] scrBytes = new byte[9];
        packetBuffer2.readBytes(scrBytes, 0, scrBytes.length);
        packetBuffer2.setPosition(originalPosition);
        if (!checkMarkerBits(scrBytes)) {
            return C0841C.TIME_UNSET;
        }
        return readScrValueFromPackHeader(scrBytes);
    }

    private int finishReadDuration(ExtractorInput input) {
        this.packetBuffer.reset(Util.EMPTY_BYTE_ARRAY);
        this.isDurationRead = true;
        input.resetPeekPosition();
        return 0;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.Math.min(long, long):long}
     arg types: [int, long]
     candidates:
      ClspMth{java.lang.Math.min(double, double):double}
      ClspMth{java.lang.Math.min(float, float):float}
      ClspMth{java.lang.Math.min(int, int):int}
      ClspMth{java.lang.Math.min(long, long):long} */
    private int readFirstScrValue(ExtractorInput input, PositionHolder seekPositionHolder) throws IOException, InterruptedException {
        int bytesToSearch = (int) Math.min(20000L, input.getLength());
        if (input.getPosition() != ((long) 0)) {
            seekPositionHolder.position = (long) 0;
            return 1;
        }
        this.packetBuffer.reset(bytesToSearch);
        input.resetPeekPosition();
        input.peekFully(this.packetBuffer.data, 0, bytesToSearch);
        this.firstScrValue = readFirstScrValueFromBuffer(this.packetBuffer);
        this.isFirstScrValueRead = true;
        return 0;
    }

    private long readFirstScrValueFromBuffer(ParsableByteArray packetBuffer2) {
        int searchStartPosition = packetBuffer2.getPosition();
        int searchEndPosition = packetBuffer2.limit();
        for (int searchPosition = searchStartPosition; searchPosition < searchEndPosition - 3; searchPosition++) {
            if (peekIntAtPosition(packetBuffer2.data, searchPosition) == 442) {
                packetBuffer2.setPosition(searchPosition + 4);
                long scrValue = readScrValueFromPack(packetBuffer2);
                if (scrValue != C0841C.TIME_UNSET) {
                    return scrValue;
                }
            }
        }
        return C0841C.TIME_UNSET;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.Math.min(long, long):long}
     arg types: [int, long]
     candidates:
      ClspMth{java.lang.Math.min(double, double):double}
      ClspMth{java.lang.Math.min(float, float):float}
      ClspMth{java.lang.Math.min(int, int):int}
      ClspMth{java.lang.Math.min(long, long):long} */
    private int readLastScrValue(ExtractorInput input, PositionHolder seekPositionHolder) throws IOException, InterruptedException {
        long inputLength = input.getLength();
        int bytesToSearch = (int) Math.min(20000L, inputLength);
        long searchStartPosition = inputLength - ((long) bytesToSearch);
        if (input.getPosition() != searchStartPosition) {
            seekPositionHolder.position = searchStartPosition;
            return 1;
        }
        this.packetBuffer.reset(bytesToSearch);
        input.resetPeekPosition();
        input.peekFully(this.packetBuffer.data, 0, bytesToSearch);
        this.lastScrValue = readLastScrValueFromBuffer(this.packetBuffer);
        this.isLastScrValueRead = true;
        return 0;
    }

    private long readLastScrValueFromBuffer(ParsableByteArray packetBuffer2) {
        int searchStartPosition = packetBuffer2.getPosition();
        for (int searchPosition = packetBuffer2.limit() - 4; searchPosition >= searchStartPosition; searchPosition--) {
            if (peekIntAtPosition(packetBuffer2.data, searchPosition) == 442) {
                packetBuffer2.setPosition(searchPosition + 4);
                long scrValue = readScrValueFromPack(packetBuffer2);
                if (scrValue != C0841C.TIME_UNSET) {
                    return scrValue;
                }
            }
        }
        return C0841C.TIME_UNSET;
    }

    private int peekIntAtPosition(byte[] data, int position) {
        return ((data[position] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | ((data[position + 1] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((data[position + 2] & UnsignedBytes.MAX_VALUE) << 8) | (data[position + 3] & UnsignedBytes.MAX_VALUE);
    }

    private static boolean checkMarkerBits(byte[] scrBytes) {
        if ((scrBytes[0] & 196) == 68 && (scrBytes[2] & 4) == 4 && (scrBytes[4] & 4) == 4 && (scrBytes[5] & 1) == 1 && (scrBytes[8] & 3) == 3) {
            return true;
        }
        return false;
    }

    private static long readScrValueFromPackHeader(byte[] scrBytes) {
        return (((((long) scrBytes[0]) & 56) >> 3) << 30) | ((((long) scrBytes[0]) & 3) << 28) | ((((long) scrBytes[1]) & 255) << 20) | (((((long) scrBytes[2]) & 248) >> 3) << 15) | ((((long) scrBytes[2]) & 3) << 13) | ((((long) scrBytes[3]) & 255) << 5) | ((((long) scrBytes[4]) & 248) >> 3);
    }
}
