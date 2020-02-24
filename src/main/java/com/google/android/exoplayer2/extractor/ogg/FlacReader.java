package com.google.android.exoplayer2.extractor.ogg;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.extractor.ogg.StreamReader;
import com.google.android.exoplayer2.util.FlacStreamInfo;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

final class FlacReader extends StreamReader {
    private static final byte AUDIO_PACKET_TYPE = -1;
    private static final int FRAME_HEADER_SAMPLE_NUMBER_OFFSET = 4;
    private static final byte SEEKTABLE_PACKET_TYPE = 3;
    private FlacOggSeeker flacOggSeeker;
    /* access modifiers changed from: private */
    public FlacStreamInfo streamInfo;

    FlacReader() {
    }

    public static boolean verifyBitstreamType(ParsableByteArray data) {
        return data.bytesLeft() >= 5 && data.readUnsignedByte() == 127 && data.readUnsignedInt() == 1179402563;
    }

    /* access modifiers changed from: protected */
    public void reset(boolean headerData) {
        super.reset(headerData);
        if (headerData) {
            this.streamInfo = null;
            this.flacOggSeeker = null;
        }
    }

    private static boolean isAudioPacket(byte[] data) {
        return data[0] == -1;
    }

    /* access modifiers changed from: protected */
    public long preparePayload(ParsableByteArray packet) {
        if (!isAudioPacket(packet.data)) {
            return -1;
        }
        return (long) getFlacFrameBlockSize(packet);
    }

    /* access modifiers changed from: protected */
    public boolean readHeaders(ParsableByteArray packet, long position, StreamReader.SetupData setupData) throws IOException, InterruptedException {
        ParsableByteArray parsableByteArray = packet;
        StreamReader.SetupData setupData2 = setupData;
        byte[] data = parsableByteArray.data;
        if (this.streamInfo == null) {
            this.streamInfo = new FlacStreamInfo(data, 17);
            byte[] metadata = Arrays.copyOfRange(data, 9, packet.limit());
            metadata[4] = UnsignedBytes.MAX_POWER_OF_TWO;
            setupData2.format = Format.createAudioSampleFormat(null, MimeTypes.AUDIO_FLAC, null, -1, this.streamInfo.bitRate(), this.streamInfo.channels, this.streamInfo.sampleRate, Collections.singletonList(metadata), null, 0, null);
            return true;
        } else if ((data[0] & Ascii.DEL) == 3) {
            this.flacOggSeeker = new FlacOggSeeker();
            this.flacOggSeeker.parseSeekTable(parsableByteArray);
            return true;
        } else if (!isAudioPacket(data)) {
            return true;
        } else {
            FlacOggSeeker flacOggSeeker2 = this.flacOggSeeker;
            if (flacOggSeeker2 != null) {
                flacOggSeeker2.setFirstFrameOffset(position);
                setupData2.oggSeeker = this.flacOggSeeker;
            }
            return false;
        }
    }

    private int getFlacFrameBlockSize(ParsableByteArray packet) {
        int blockSizeCode = (packet.data[2] & 255) >> 4;
        switch (blockSizeCode) {
            case 1:
                return 192;
            case 2:
            case 3:
            case 4:
            case 5:
                return ClientAnalytics.LogRequest.LogSource.CLEARCUT_LOG_LOSS_VALUE << (blockSizeCode - 2);
            case 6:
            case 7:
                packet.skipBytes(4);
                packet.readUtf8EncodedLong();
                int value = blockSizeCode == 6 ? packet.readUnsignedByte() : packet.readUnsignedShort();
                packet.setPosition(0);
                return value + 1;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                return 256 << (blockSizeCode - 8);
            default:
                return -1;
        }
    }

    private class FlacOggSeeker implements OggSeeker, SeekMap {
        private static final int METADATA_LENGTH_OFFSET = 1;
        private static final int SEEK_POINT_SIZE = 18;
        private long firstFrameOffset = -1;
        private long pendingSeekGranule = -1;
        private long[] seekPointGranules;
        private long[] seekPointOffsets;

        public FlacOggSeeker() {
        }

        public void setFirstFrameOffset(long firstFrameOffset2) {
            this.firstFrameOffset = firstFrameOffset2;
        }

        public void parseSeekTable(ParsableByteArray data) {
            data.skipBytes(1);
            int numberOfSeekPoints = data.readUnsignedInt24() / 18;
            this.seekPointGranules = new long[numberOfSeekPoints];
            this.seekPointOffsets = new long[numberOfSeekPoints];
            for (int i = 0; i < numberOfSeekPoints; i++) {
                this.seekPointGranules[i] = data.readLong();
                this.seekPointOffsets[i] = data.readLong();
                data.skipBytes(2);
            }
        }

        public long read(ExtractorInput input) throws IOException, InterruptedException {
            long j = this.pendingSeekGranule;
            if (j < 0) {
                return -1;
            }
            long result = -(j + 2);
            this.pendingSeekGranule = -1;
            return result;
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.android.exoplayer2.util.Util.binarySearchFloor(long[], long, boolean, boolean):int
         arg types: [long[], long, int, int]
         candidates:
          com.google.android.exoplayer2.util.Util.binarySearchFloor(java.util.List, java.lang.Comparable, boolean, boolean):int
          com.google.android.exoplayer2.util.Util.binarySearchFloor(int[], int, boolean, boolean):int
          com.google.android.exoplayer2.util.Util.binarySearchFloor(long[], long, boolean, boolean):int */
        public long startSeek(long timeUs) {
            long granule = FlacReader.this.convertTimeToGranule(timeUs);
            this.pendingSeekGranule = this.seekPointGranules[Util.binarySearchFloor(this.seekPointGranules, granule, true, true)];
            return granule;
        }

        public SeekMap createSeekMap() {
            return this;
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
            long j = timeUs;
            int index = Util.binarySearchFloor(this.seekPointGranules, FlacReader.this.convertTimeToGranule(j), true, true);
            long seekTimeUs = FlacReader.this.convertGranuleToTime(this.seekPointGranules[index]);
            SeekPoint seekPoint = new SeekPoint(seekTimeUs, this.firstFrameOffset + this.seekPointOffsets[index]);
            if (seekTimeUs < j) {
                long[] jArr = this.seekPointGranules;
                if (index != jArr.length - 1) {
                    return new SeekMap.SeekPoints(seekPoint, new SeekPoint(FlacReader.this.convertGranuleToTime(jArr[index + 1]), this.firstFrameOffset + this.seekPointOffsets[index + 1]));
                }
            }
            return new SeekMap.SeekPoints(seekPoint);
        }

        public long getDurationUs() {
            return FlacReader.this.streamInfo.durationUs();
        }
    }
}
