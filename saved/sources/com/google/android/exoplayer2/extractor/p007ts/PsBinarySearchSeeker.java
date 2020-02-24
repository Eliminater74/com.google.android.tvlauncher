package com.google.android.exoplayer2.extractor.p007ts;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.extractor.BinarySearchSeeker;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import java.io.IOException;

/* renamed from: com.google.android.exoplayer2.extractor.ts.PsBinarySearchSeeker */
final class PsBinarySearchSeeker extends BinarySearchSeeker {
    private static final int MINIMUM_SEARCH_RANGE_BYTES = 1000;
    private static final long SEEK_TOLERANCE_US = 100000;
    private static final int TIMESTAMP_SEARCH_BYTES = 20000;

    public PsBinarySearchSeeker(TimestampAdjuster scrTimestampAdjuster, long streamDurationUs, long inputLength) {
        super(new BinarySearchSeeker.DefaultSeekTimestampConverter(), new PsScrSeeker(scrTimestampAdjuster), streamDurationUs, 0, streamDurationUs + 1, 0, inputLength, 188, 1000);
    }

    /* renamed from: com.google.android.exoplayer2.extractor.ts.PsBinarySearchSeeker$PsScrSeeker */
    private static final class PsScrSeeker implements BinarySearchSeeker.TimestampSeeker {
        private final ParsableByteArray packetBuffer;
        private final TimestampAdjuster scrTimestampAdjuster;

        private PsScrSeeker(TimestampAdjuster scrTimestampAdjuster2) {
            this.scrTimestampAdjuster = scrTimestampAdjuster2;
            this.packetBuffer = new ParsableByteArray();
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{java.lang.Math.min(long, long):long}
         arg types: [int, long]
         candidates:
          ClspMth{java.lang.Math.min(double, double):double}
          ClspMth{java.lang.Math.min(float, float):float}
          ClspMth{java.lang.Math.min(int, int):int}
          ClspMth{java.lang.Math.min(long, long):long} */
        public BinarySearchSeeker.TimestampSearchResult searchForTimestamp(ExtractorInput input, long targetTimestamp, BinarySearchSeeker.OutputFrameHolder outputFrameHolder) throws IOException, InterruptedException {
            long inputPosition = input.getPosition();
            int bytesToSearch = (int) Math.min(20000L, input.getLength() - inputPosition);
            this.packetBuffer.reset(bytesToSearch);
            input.peekFully(this.packetBuffer.data, 0, bytesToSearch);
            return searchForScrValueInBuffer(this.packetBuffer, targetTimestamp, inputPosition);
        }

        public void onSeekFinished() {
            this.packetBuffer.reset(Util.EMPTY_BYTE_ARRAY);
        }

        private BinarySearchSeeker.TimestampSearchResult searchForScrValueInBuffer(ParsableByteArray packetBuffer2, long targetScrTimeUs, long bufferStartOffset) {
            ParsableByteArray parsableByteArray = packetBuffer2;
            long j = bufferStartOffset;
            int startOfLastPacketPosition = -1;
            int endOfLastPacketPosition = -1;
            long lastScrTimeUsInRange = C0841C.TIME_UNSET;
            while (packetBuffer2.bytesLeft() >= 4) {
                if (PsBinarySearchSeeker.peekIntAtPosition(parsableByteArray.data, packetBuffer2.getPosition()) != 442) {
                    parsableByteArray.skipBytes(1);
                } else {
                    parsableByteArray.skipBytes(4);
                    long scrValue = PsDurationReader.readScrValueFromPack(packetBuffer2);
                    if (scrValue != C0841C.TIME_UNSET) {
                        long scrTimeUs = this.scrTimestampAdjuster.adjustTsTimestamp(scrValue);
                        if (scrTimeUs > targetScrTimeUs) {
                            if (lastScrTimeUsInRange == C0841C.TIME_UNSET) {
                                return BinarySearchSeeker.TimestampSearchResult.overestimatedResult(scrTimeUs, j);
                            }
                            return BinarySearchSeeker.TimestampSearchResult.targetFoundResult(((long) startOfLastPacketPosition) + j);
                        } else if (PsBinarySearchSeeker.SEEK_TOLERANCE_US + scrTimeUs > targetScrTimeUs) {
                            return BinarySearchSeeker.TimestampSearchResult.targetFoundResult(((long) packetBuffer2.getPosition()) + j);
                        } else {
                            lastScrTimeUsInRange = scrTimeUs;
                            startOfLastPacketPosition = packetBuffer2.getPosition();
                        }
                    }
                    skipToEndOfCurrentPack(packetBuffer2);
                    endOfLastPacketPosition = packetBuffer2.getPosition();
                }
            }
            if (lastScrTimeUsInRange != C0841C.TIME_UNSET) {
                return BinarySearchSeeker.TimestampSearchResult.underestimatedResult(lastScrTimeUsInRange, ((long) endOfLastPacketPosition) + j);
            }
            return BinarySearchSeeker.TimestampSearchResult.NO_TIMESTAMP_IN_RANGE_RESULT;
        }

        private static void skipToEndOfCurrentPack(ParsableByteArray packetBuffer2) {
            int limit = packetBuffer2.limit();
            if (packetBuffer2.bytesLeft() < 10) {
                packetBuffer2.setPosition(limit);
                return;
            }
            packetBuffer2.skipBytes(9);
            int packStuffingLength = packetBuffer2.readUnsignedByte() & 7;
            if (packetBuffer2.bytesLeft() < packStuffingLength) {
                packetBuffer2.setPosition(limit);
                return;
            }
            packetBuffer2.skipBytes(packStuffingLength);
            if (packetBuffer2.bytesLeft() < 4) {
                packetBuffer2.setPosition(limit);
                return;
            }
            if (PsBinarySearchSeeker.peekIntAtPosition(packetBuffer2.data, packetBuffer2.getPosition()) == 443) {
                packetBuffer2.skipBytes(4);
                int systemHeaderLength = packetBuffer2.readUnsignedShort();
                if (packetBuffer2.bytesLeft() < systemHeaderLength) {
                    packetBuffer2.setPosition(limit);
                    return;
                }
                packetBuffer2.skipBytes(systemHeaderLength);
            }
            while (packetBuffer2.bytesLeft() >= 4 && (nextStartCode = PsBinarySearchSeeker.peekIntAtPosition(packetBuffer2.data, packetBuffer2.getPosition())) != 442 && nextStartCode != 441 && (nextStartCode >>> 8) == 1) {
                packetBuffer2.skipBytes(4);
                if (packetBuffer2.bytesLeft() < 2) {
                    packetBuffer2.setPosition(limit);
                    return;
                }
                packetBuffer2.setPosition(Math.min(packetBuffer2.limit(), packetBuffer2.getPosition() + packetBuffer2.readUnsignedShort()));
            }
        }
    }

    /* access modifiers changed from: private */
    public static int peekIntAtPosition(byte[] data, int position) {
        return ((data[position] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | ((data[position + 1] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((data[position + 2] & UnsignedBytes.MAX_VALUE) << 8) | (data[position + 3] & UnsignedBytes.MAX_VALUE);
    }
}
