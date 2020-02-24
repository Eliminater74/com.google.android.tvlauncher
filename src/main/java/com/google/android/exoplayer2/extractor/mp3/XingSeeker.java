package com.google.android.exoplayer2.extractor.mp3;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.extractor.MpegAudioHeader;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;

final class XingSeeker implements Mp3Extractor.Seeker {
    private static final String TAG = "XingSeeker";
    private final long dataEndPosition;
    private final long dataSize;
    private final long dataStartPosition;
    private final long durationUs;
    @Nullable
    private final long[] tableOfContents;
    private final int xingFrameSize;

    @Nullable
    public static XingSeeker create(long inputLength, long position, MpegAudioHeader mpegAudioHeader, ParsableByteArray frame) {
        long j = inputLength;
        MpegAudioHeader mpegAudioHeader2 = mpegAudioHeader;
        int samplesPerFrame = mpegAudioHeader2.samplesPerFrame;
        int sampleRate = mpegAudioHeader2.sampleRate;
        int flags = frame.readInt();
        if ((flags & 1) != 1) {
            return null;
        }
        int readUnsignedIntToInt = frame.readUnsignedIntToInt();
        int frameCount = readUnsignedIntToInt;
        if (readUnsignedIntToInt == 0) {
            return null;
        }
        long durationUs2 = Util.scaleLargeTimestamp((long) frameCount, ((long) samplesPerFrame) * 1000000, (long) sampleRate);
        if ((flags & 6) != 6) {
            return new XingSeeker(position, mpegAudioHeader2.frameSize, durationUs2);
        }
        long dataSize2 = (long) frame.readUnsignedIntToInt();
        long[] tableOfContents2 = new long[100];
        for (int i = 0; i < 100; i++) {
            tableOfContents2[i] = (long) frame.readUnsignedByte();
        }
        if (!(j == -1 || j == position + dataSize2)) {
            StringBuilder sb = new StringBuilder(67);
            sb.append("XING data size mismatch: ");
            sb.append(j);
            sb.append(", ");
            sb.append(position + dataSize2);
            Log.m30w(TAG, sb.toString());
        }
        return new XingSeeker(position, mpegAudioHeader2.frameSize, durationUs2, dataSize2, tableOfContents2);
    }

    private XingSeeker(long dataStartPosition2, int xingFrameSize2, long durationUs2) {
        this(dataStartPosition2, xingFrameSize2, durationUs2, -1, null);
    }

    private XingSeeker(long dataStartPosition2, int xingFrameSize2, long durationUs2, long dataSize2, @Nullable long[] tableOfContents2) {
        this.dataStartPosition = dataStartPosition2;
        this.xingFrameSize = xingFrameSize2;
        this.durationUs = durationUs2;
        this.tableOfContents = tableOfContents2;
        this.dataSize = dataSize2;
        this.dataEndPosition = dataSize2 != -1 ? dataStartPosition2 + dataSize2 : -1;
    }

    public boolean isSeekable() {
        return this.tableOfContents != null;
    }

    /* JADX INFO: Multiple debug info for r5v2 int: [D('scaledPosition' double), D('prevTableIndex' int)] */
    public SeekMap.SeekPoints getSeekPoints(long timeUs) {
        double scaledPosition;
        if (!isSeekable()) {
            return new SeekMap.SeekPoints(new SeekPoint(0, this.dataStartPosition + ((long) this.xingFrameSize)));
        }
        long timeUs2 = Util.constrainValue(timeUs, 0, this.durationUs);
        double d = (double) timeUs2;
        Double.isNaN(d);
        double d2 = (double) this.durationUs;
        Double.isNaN(d2);
        double percent = (d * 100.0d) / d2;
        if (percent <= 0.0d) {
            scaledPosition = 0.0d;
        } else if (percent >= 100.0d) {
            scaledPosition = 256.0d;
        } else {
            int prevTableIndex = (int) percent;
            long[] tableOfContents2 = (long[]) Assertions.checkNotNull(this.tableOfContents);
            double prevScaledPosition = (double) tableOfContents2[prevTableIndex];
            double nextScaledPosition = prevTableIndex == 99 ? 256.0d : (double) tableOfContents2[prevTableIndex + 1];
            double d3 = (double) prevTableIndex;
            Double.isNaN(d3);
            Double.isNaN(prevScaledPosition);
            Double.isNaN(prevScaledPosition);
            scaledPosition = prevScaledPosition + ((nextScaledPosition - prevScaledPosition) * (percent - d3));
        }
        double d4 = (double) this.dataSize;
        Double.isNaN(d4);
        return new SeekMap.SeekPoints(new SeekPoint(timeUs2, this.dataStartPosition + Util.constrainValue(Math.round((scaledPosition / 256.0d) * d4), (long) this.xingFrameSize, this.dataSize - 1)));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.util.Util.binarySearchFloor(long[], long, boolean, boolean):int
     arg types: [long[], long, int, int]
     candidates:
      com.google.android.exoplayer2.util.Util.binarySearchFloor(java.util.List, java.lang.Comparable, boolean, boolean):int
      com.google.android.exoplayer2.util.Util.binarySearchFloor(int[], int, boolean, boolean):int
      com.google.android.exoplayer2.util.Util.binarySearchFloor(long[], long, boolean, boolean):int */
    public long getTimeUs(long position) {
        double interpolateFraction;
        long positionOffset = position - this.dataStartPosition;
        if (!isSeekable()) {
            return 0;
        }
        if (positionOffset <= ((long) this.xingFrameSize)) {
            return 0;
        }
        long[] tableOfContents2 = (long[]) Assertions.checkNotNull(this.tableOfContents);
        double d = (double) positionOffset;
        Double.isNaN(d);
        double d2 = (double) this.dataSize;
        Double.isNaN(d2);
        double scaledPosition = (d * 256.0d) / d2;
        int prevTableIndex = Util.binarySearchFloor(tableOfContents2, (long) scaledPosition, true, true);
        long prevTimeUs = getTimeUsForTableIndex(prevTableIndex);
        long prevScaledPosition = tableOfContents2[prevTableIndex];
        long nextTimeUs = getTimeUsForTableIndex(prevTableIndex + 1);
        long nextScaledPosition = prevTableIndex == 99 ? 256 : tableOfContents2[prevTableIndex + 1];
        if (prevScaledPosition == nextScaledPosition) {
            interpolateFraction = 0.0d;
        } else {
            double d3 = (double) prevScaledPosition;
            Double.isNaN(d3);
            double d4 = (double) (nextScaledPosition - prevScaledPosition);
            Double.isNaN(d4);
            interpolateFraction = (scaledPosition - d3) / d4;
        }
        double d5 = (double) (nextTimeUs - prevTimeUs);
        Double.isNaN(d5);
        return Math.round(d5 * interpolateFraction) + prevTimeUs;
    }

    public long getDurationUs() {
        return this.durationUs;
    }

    public long getDataEndPosition() {
        return this.dataEndPosition;
    }

    private long getTimeUsForTableIndex(int tableIndex) {
        return (this.durationUs * ((long) tableIndex)) / 100;
    }
}
