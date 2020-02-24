package com.google.android.exoplayer2.extractor.flv;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.flv.TagPayloadReader;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.video.AvcConfig;

final class VideoTagPayloadReader extends TagPayloadReader {
    private static final int AVC_PACKET_TYPE_AVC_NALU = 1;
    private static final int AVC_PACKET_TYPE_SEQUENCE_HEADER = 0;
    private static final int VIDEO_CODEC_AVC = 7;
    private static final int VIDEO_FRAME_KEYFRAME = 1;
    private static final int VIDEO_FRAME_VIDEO_INFO = 5;
    private int frameType;
    private boolean hasOutputFormat;
    private final ParsableByteArray nalLength = new ParsableByteArray(4);
    private final ParsableByteArray nalStartCode = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
    private int nalUnitLengthFieldLength;

    public VideoTagPayloadReader(TrackOutput output) {
        super(output);
    }

    public void seek() {
    }

    /* access modifiers changed from: protected */
    public boolean parseHeader(ParsableByteArray data) throws TagPayloadReader.UnsupportedFormatException {
        int header = data.readUnsignedByte();
        int frameType2 = (header >> 4) & 15;
        int videoCodec = header & 15;
        if (videoCodec == 7) {
            this.frameType = frameType2;
            return frameType2 != 5;
        }
        StringBuilder sb = new StringBuilder(39);
        sb.append("Video format not supported: ");
        sb.append(videoCodec);
        throw new TagPayloadReader.UnsupportedFormatException(sb.toString());
    }

    /* access modifiers changed from: protected */
    public void parsePayload(ParsableByteArray data, long timeUs) throws ParserException {
        ParsableByteArray parsableByteArray = data;
        int packetType = data.readUnsignedByte();
        long timeUs2 = timeUs + (((long) data.readInt24()) * 1000);
        if (packetType == 0 && !this.hasOutputFormat) {
            ParsableByteArray videoSequence = new ParsableByteArray(new byte[data.bytesLeft()]);
            parsableByteArray.readBytes(videoSequence.data, 0, data.bytesLeft());
            AvcConfig avcConfig = AvcConfig.parse(videoSequence);
            this.nalUnitLengthFieldLength = avcConfig.nalUnitLengthFieldLength;
            this.output.format(Format.createVideoSampleFormat(null, MimeTypes.VIDEO_H264, null, -1, -1, avcConfig.width, avcConfig.height, -1.0f, avcConfig.initializationData, -1, avcConfig.pixelWidthAspectRatio, null));
            this.hasOutputFormat = true;
        } else if (packetType == 1 && this.hasOutputFormat) {
            byte[] nalLengthData = this.nalLength.data;
            nalLengthData[0] = 0;
            nalLengthData[1] = 0;
            nalLengthData[2] = 0;
            int nalUnitLengthFieldLengthDiff = 4 - this.nalUnitLengthFieldLength;
            int bytesWritten = 0;
            while (data.bytesLeft() > 0) {
                parsableByteArray.readBytes(this.nalLength.data, nalUnitLengthFieldLengthDiff, this.nalUnitLengthFieldLength);
                this.nalLength.setPosition(0);
                int bytesToWrite = this.nalLength.readUnsignedIntToInt();
                this.nalStartCode.setPosition(0);
                this.output.sampleData(this.nalStartCode, 4);
                this.output.sampleData(parsableByteArray, bytesToWrite);
                bytesWritten = bytesWritten + 4 + bytesToWrite;
            }
            TrackOutput trackOutput = this.output;
            int i = 1;
            if (this.frameType != 1) {
                i = 0;
            }
            trackOutput.sampleMetadata(timeUs2, i, bytesWritten, 0, null);
        }
    }
}
