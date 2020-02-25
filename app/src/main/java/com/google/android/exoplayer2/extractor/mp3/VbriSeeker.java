package com.google.android.exoplayer2.extractor.mp3;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.extractor.MpegAudioHeader;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

final class VbriSeeker implements Mp3Extractor.Seeker {
    private static final String TAG = "VbriSeeker";
    private final long dataEndPosition;
    private final long durationUs;
    private final long[] positions;
    private final long[] timesUs;

    private VbriSeeker(long[] timesUs2, long[] positions2, long durationUs2, long dataEndPosition2) {
        this.timesUs = timesUs2;
        this.positions = positions2;
        this.durationUs = durationUs2;
        this.dataEndPosition = dataEndPosition2;
    }

    @Nullable
    public static VbriSeeker create(long inputLength, long position, MpegAudioHeader mpegAudioHeader, ParsableByteArray frame) {
        int segmentSize;
        long j = inputLength;
        MpegAudioHeader mpegAudioHeader2 = mpegAudioHeader;
        ParsableByteArray parsableByteArray = frame;
        parsableByteArray.skipBytes(10);
        int numFrames = frame.readInt();
        if (numFrames <= 0) {
            return null;
        }
        int sampleRate = mpegAudioHeader2.sampleRate;
        long durationUs2 = Util.scaleLargeTimestamp((long) numFrames, 1000000 * ((long) (sampleRate >= 32000 ? 1152 : ClientAnalytics.LogRequest.LogSource.CLEARCUT_LOG_LOSS_VALUE)), (long) sampleRate);
        int entryCount = frame.readUnsignedShort();
        int scale = frame.readUnsignedShort();
        int entrySize = frame.readUnsignedShort();
        parsableByteArray.skipBytes(2);
        long minPosition = position + ((long) mpegAudioHeader2.frameSize);
        long[] timesUs2 = new long[entryCount];
        long[] positions2 = new long[entryCount];
        int index = 0;
        long position2 = position;
        while (index < entryCount) {
            long durationUs3 = durationUs2;
            timesUs2[index] = (((long) index) * durationUs2) / ((long) entryCount);
            positions2[index] = Math.max(position2, minPosition);
            if (entrySize == 1) {
                segmentSize = frame.readUnsignedByte();
            } else if (entrySize == 2) {
                segmentSize = frame.readUnsignedShort();
            } else if (entrySize == 3) {
                segmentSize = frame.readUnsignedInt24();
            } else if (entrySize != 4) {
                return null;
            } else {
                segmentSize = frame.readUnsignedIntToInt();
            }
            position2 += (long) (segmentSize * scale);
            index++;
            durationUs2 = durationUs3;
        }
        long durationUs4 = durationUs2;
        if (!(j == -1 || j == position2)) {
            StringBuilder sb = new StringBuilder(67);
            sb.append("VBRI data size mismatch: ");
            sb.append(j);
            sb.append(", ");
            sb.append(position2);
            Log.m30w(TAG, sb.toString());
        }
        return new VbriSeeker(timesUs2, positions2, durationUs4, position2);
    }

    public boolean isSeekable() {
        return true;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.util.Util.binarySearchFloor(long[], long, boolean, boolean):int
     arg types: [long[], long, int, int]
     candidates:
      com.google.android.exoplayer2.util.Util.binarySearchFloor(java.util.List, java.lang.Comparable, boolean, boolean):int
      com.google.android.exoplayer2.util.Util.binarySearchFloor(int[], int, boolean, boolean):int
      com.google.android.exoplayer2.util.Util.binarySearchFloor(long[], long, boolean, boolean):int */
    public SeekMap.SeekPoints getSeekPoints(long timeUs) {
        int tableIndex = Util.binarySearchFloor(this.timesUs, timeUs, true, true);
        SeekPoint seekPoint = new SeekPoint(this.timesUs[tableIndex], this.positions[tableIndex]);
        if (seekPoint.timeUs < timeUs) {
            long[] jArr = this.timesUs;
            if (tableIndex != jArr.length - 1) {
                return new SeekMap.SeekPoints(seekPoint, new SeekPoint(jArr[tableIndex + 1], this.positions[tableIndex + 1]));
            }
        }
        return new SeekMap.SeekPoints(seekPoint);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.util.Util.binarySearchFloor(long[], long, boolean, boolean):int
     arg types: [long[], long, int, int]
     candidates:
      com.google.android.exoplayer2.util.Util.binarySearchFloor(java.util.List, java.lang.Comparable, boolean, boolean):int
      com.google.android.exoplayer2.util.Util.binarySearchFloor(int[], int, boolean, boolean):int
      com.google.android.exoplayer2.util.Util.binarySearchFloor(long[], long, boolean, boolean):int */
    public long getTimeUs(long position) {
        return this.timesUs[Util.binarySearchFloor(this.positions, position, true, true)];
    }

    public long getDurationUs() {
        return this.durationUs;
    }

    public long getDataEndPosition() {
        return this.dataEndPosition;
    }
}
