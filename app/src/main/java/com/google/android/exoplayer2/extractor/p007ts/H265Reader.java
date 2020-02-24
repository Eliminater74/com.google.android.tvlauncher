package com.google.android.exoplayer2.extractor.p007ts;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.p007ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.ParsableNalUnitBitArray;
import com.google.common.primitives.UnsignedBytes;
import java.util.Collections;

/* renamed from: com.google.android.exoplayer2.extractor.ts.H265Reader */
public final class H265Reader implements ElementaryStreamReader {
    private static final int BLA_W_LP = 16;
    private static final int CRA_NUT = 21;
    private static final int PPS_NUT = 34;
    private static final int PREFIX_SEI_NUT = 39;
    private static final int RASL_R = 9;
    private static final int SPS_NUT = 33;
    private static final int SUFFIX_SEI_NUT = 40;
    private static final String TAG = "H265Reader";
    private static final int VPS_NUT = 32;
    private String formatId;
    private boolean hasOutputFormat;
    private TrackOutput output;
    private long pesTimeUs;
    private final NalUnitTargetBuffer pps = new NalUnitTargetBuffer(34, 128);
    private final boolean[] prefixFlags = new boolean[3];
    private final NalUnitTargetBuffer prefixSei = new NalUnitTargetBuffer(39, 128);
    private SampleReader sampleReader;
    private final SeiReader seiReader;
    private final ParsableByteArray seiWrapper = new ParsableByteArray();
    private final NalUnitTargetBuffer sps = new NalUnitTargetBuffer(33, 128);
    private final NalUnitTargetBuffer suffixSei = new NalUnitTargetBuffer(40, 128);
    private long totalBytesWritten;
    private final NalUnitTargetBuffer vps = new NalUnitTargetBuffer(32, 128);

    public H265Reader(SeiReader seiReader2) {
        this.seiReader = seiReader2;
    }

    public void seek() {
        NalUnitUtil.clearPrefixFlags(this.prefixFlags);
        this.vps.reset();
        this.sps.reset();
        this.pps.reset();
        this.prefixSei.reset();
        this.suffixSei.reset();
        this.sampleReader.reset();
        this.totalBytesWritten = 0;
    }

    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator idGenerator) {
        idGenerator.generateNewId();
        this.formatId = idGenerator.getFormatId();
        this.output = extractorOutput.track(idGenerator.getTrackId(), 2);
        this.sampleReader = new SampleReader(this.output);
        this.seiReader.createTracks(extractorOutput, idGenerator);
    }

    public void packetStarted(long pesTimeUs2, int flags) {
        this.pesTimeUs = pesTimeUs2;
    }

    public void consume(ParsableByteArray data) {
        ParsableByteArray parsableByteArray = data;
        while (data.bytesLeft() > 0) {
            int offset = data.getPosition();
            int limit = data.limit();
            byte[] dataArray = parsableByteArray.data;
            this.totalBytesWritten += (long) data.bytesLeft();
            this.output.sampleData(parsableByteArray, data.bytesLeft());
            int offset2 = offset;
            while (true) {
                if (offset2 < limit) {
                    int nalUnitOffset = NalUnitUtil.findNalUnit(dataArray, offset2, limit, this.prefixFlags);
                    if (nalUnitOffset == limit) {
                        nalUnitData(dataArray, offset2, limit);
                        return;
                    }
                    int nalUnitType = NalUnitUtil.getH265NalUnitType(dataArray, nalUnitOffset);
                    int lengthToNalUnit = nalUnitOffset - offset2;
                    if (lengthToNalUnit > 0) {
                        nalUnitData(dataArray, offset2, nalUnitOffset);
                    }
                    int bytesWrittenPastPosition = limit - nalUnitOffset;
                    long j = this.totalBytesWritten - ((long) bytesWrittenPastPosition);
                    int i = bytesWrittenPastPosition;
                    endNalUnit(j, i, lengthToNalUnit < 0 ? -lengthToNalUnit : 0, this.pesTimeUs);
                    startNalUnit(j, i, nalUnitType, this.pesTimeUs);
                    offset2 = nalUnitOffset + 3;
                }
            }
        }
    }

    public void packetFinished() {
    }

    private void startNalUnit(long position, int offset, int nalUnitType, long pesTimeUs2) {
        if (this.hasOutputFormat) {
            this.sampleReader.startNalUnit(position, offset, nalUnitType, pesTimeUs2);
        } else {
            this.vps.startNalUnit(nalUnitType);
            this.sps.startNalUnit(nalUnitType);
            this.pps.startNalUnit(nalUnitType);
        }
        this.prefixSei.startNalUnit(nalUnitType);
        this.suffixSei.startNalUnit(nalUnitType);
    }

    private void nalUnitData(byte[] dataArray, int offset, int limit) {
        if (this.hasOutputFormat) {
            this.sampleReader.readNalUnitData(dataArray, offset, limit);
        } else {
            this.vps.appendToNalUnit(dataArray, offset, limit);
            this.sps.appendToNalUnit(dataArray, offset, limit);
            this.pps.appendToNalUnit(dataArray, offset, limit);
        }
        this.prefixSei.appendToNalUnit(dataArray, offset, limit);
        this.suffixSei.appendToNalUnit(dataArray, offset, limit);
    }

    private void endNalUnit(long position, int offset, int discardPadding, long pesTimeUs2) {
        if (this.hasOutputFormat) {
            this.sampleReader.endNalUnit(position, offset);
        } else {
            this.vps.endNalUnit(discardPadding);
            this.sps.endNalUnit(discardPadding);
            this.pps.endNalUnit(discardPadding);
            if (this.vps.isCompleted() && this.sps.isCompleted() && this.pps.isCompleted()) {
                this.output.format(parseMediaFormat(this.formatId, this.vps, this.sps, this.pps));
                this.hasOutputFormat = true;
            }
        }
        if (this.prefixSei.endNalUnit(discardPadding)) {
            this.seiWrapper.reset(this.prefixSei.nalData, NalUnitUtil.unescapeStream(this.prefixSei.nalData, this.prefixSei.nalLength));
            this.seiWrapper.skipBytes(5);
            this.seiReader.consume(pesTimeUs2, this.seiWrapper);
        }
        if (this.suffixSei.endNalUnit(discardPadding)) {
            this.seiWrapper.reset(this.suffixSei.nalData, NalUnitUtil.unescapeStream(this.suffixSei.nalData, this.suffixSei.nalLength));
            this.seiWrapper.skipBytes(5);
            this.seiReader.consume(pesTimeUs2, this.seiWrapper);
        }
    }

    private static Format parseMediaFormat(String formatId2, NalUnitTargetBuffer vps2, NalUnitTargetBuffer sps2, NalUnitTargetBuffer pps2) {
        NalUnitTargetBuffer nalUnitTargetBuffer = vps2;
        NalUnitTargetBuffer nalUnitTargetBuffer2 = sps2;
        NalUnitTargetBuffer nalUnitTargetBuffer3 = pps2;
        byte[] csd = new byte[(nalUnitTargetBuffer.nalLength + nalUnitTargetBuffer2.nalLength + nalUnitTargetBuffer3.nalLength)];
        System.arraycopy(nalUnitTargetBuffer.nalData, 0, csd, 0, nalUnitTargetBuffer.nalLength);
        System.arraycopy(nalUnitTargetBuffer2.nalData, 0, csd, nalUnitTargetBuffer.nalLength, nalUnitTargetBuffer2.nalLength);
        System.arraycopy(nalUnitTargetBuffer3.nalData, 0, csd, nalUnitTargetBuffer.nalLength + nalUnitTargetBuffer2.nalLength, nalUnitTargetBuffer3.nalLength);
        ParsableNalUnitBitArray bitArray = new ParsableNalUnitBitArray(nalUnitTargetBuffer2.nalData, 0, nalUnitTargetBuffer2.nalLength);
        bitArray.skipBits(44);
        int maxSubLayersMinus1 = bitArray.readBits(3);
        bitArray.skipBit();
        bitArray.skipBits(88);
        bitArray.skipBits(8);
        int toSkip = 0;
        for (int i = 0; i < maxSubLayersMinus1; i++) {
            if (bitArray.readBit()) {
                toSkip += 89;
            }
            if (bitArray.readBit()) {
                toSkip += 8;
            }
        }
        bitArray.skipBits(toSkip);
        if (maxSubLayersMinus1 > 0) {
            bitArray.skipBits((8 - maxSubLayersMinus1) * 2);
        }
        bitArray.readUnsignedExpGolombCodedInt();
        int chromaFormatIdc = bitArray.readUnsignedExpGolombCodedInt();
        if (chromaFormatIdc == 3) {
            bitArray.skipBit();
        }
        int picWidthInLumaSamples = bitArray.readUnsignedExpGolombCodedInt();
        int picHeightInLumaSamples = bitArray.readUnsignedExpGolombCodedInt();
        if (bitArray.readBit()) {
            int confWinLeftOffset = bitArray.readUnsignedExpGolombCodedInt();
            int confWinRightOffset = bitArray.readUnsignedExpGolombCodedInt();
            int confWinTopOffset = bitArray.readUnsignedExpGolombCodedInt();
            int confWinBottomOffset = bitArray.readUnsignedExpGolombCodedInt();
            int subHeightC = 1;
            int subWidthC = (chromaFormatIdc == 1 || chromaFormatIdc == 2) ? 2 : 1;
            if (chromaFormatIdc == 1) {
                subHeightC = 2;
            }
            picWidthInLumaSamples -= (confWinLeftOffset + confWinRightOffset) * subWidthC;
            picHeightInLumaSamples -= (confWinTopOffset + confWinBottomOffset) * subHeightC;
        }
        bitArray.readUnsignedExpGolombCodedInt();
        bitArray.readUnsignedExpGolombCodedInt();
        int log2MaxPicOrderCntLsbMinus4 = bitArray.readUnsignedExpGolombCodedInt();
        for (int i2 = bitArray.readBit() ? 0 : maxSubLayersMinus1; i2 <= maxSubLayersMinus1; i2++) {
            bitArray.readUnsignedExpGolombCodedInt();
            bitArray.readUnsignedExpGolombCodedInt();
            bitArray.readUnsignedExpGolombCodedInt();
        }
        bitArray.readUnsignedExpGolombCodedInt();
        bitArray.readUnsignedExpGolombCodedInt();
        bitArray.readUnsignedExpGolombCodedInt();
        bitArray.readUnsignedExpGolombCodedInt();
        bitArray.readUnsignedExpGolombCodedInt();
        bitArray.readUnsignedExpGolombCodedInt();
        if (bitArray.readBit() && bitArray.readBit()) {
            skipScalingList(bitArray);
        }
        bitArray.skipBits(2);
        if (bitArray.readBit()) {
            bitArray.skipBits(8);
            bitArray.readUnsignedExpGolombCodedInt();
            bitArray.readUnsignedExpGolombCodedInt();
            bitArray.skipBit();
        }
        skipShortTermRefPicSets(bitArray);
        if (bitArray.readBit()) {
            for (int i3 = 0; i3 < bitArray.readUnsignedExpGolombCodedInt(); i3++) {
                bitArray.skipBits(log2MaxPicOrderCntLsbMinus4 + 4 + 1);
            }
        }
        bitArray.skipBits(2);
        float pixelWidthHeightRatio = 1.0f;
        if (bitArray.readBit() && bitArray.readBit()) {
            int aspectRatioIdc = bitArray.readBits(8);
            if (aspectRatioIdc == 255) {
                int sarWidth = bitArray.readBits(16);
                int sarHeight = bitArray.readBits(16);
                if (!(sarWidth == 0 || sarHeight == 0)) {
                    pixelWidthHeightRatio = ((float) sarWidth) / ((float) sarHeight);
                }
            } else if (aspectRatioIdc < NalUnitUtil.ASPECT_RATIO_IDC_VALUES.length) {
                pixelWidthHeightRatio = NalUnitUtil.ASPECT_RATIO_IDC_VALUES[aspectRatioIdc];
            } else {
                StringBuilder sb = new StringBuilder(46);
                sb.append("Unexpected aspect_ratio_idc value: ");
                sb.append(aspectRatioIdc);
                Log.m30w(TAG, sb.toString());
            }
        }
        return Format.createVideoSampleFormat(formatId2, MimeTypes.VIDEO_H265, null, -1, -1, picWidthInLumaSamples, picHeightInLumaSamples, -1.0f, Collections.singletonList(csd), -1, pixelWidthHeightRatio, null);
    }

    private static void skipScalingList(ParsableNalUnitBitArray bitArray) {
        for (int sizeId = 0; sizeId < 4; sizeId++) {
            int matrixId = 0;
            while (matrixId < 6) {
                if (!bitArray.readBit()) {
                    bitArray.readUnsignedExpGolombCodedInt();
                } else {
                    int coefNum = Math.min(64, 1 << ((sizeId << 1) + 4));
                    if (sizeId > 1) {
                        bitArray.readSignedExpGolombCodedInt();
                    }
                    for (int i = 0; i < coefNum; i++) {
                        bitArray.readSignedExpGolombCodedInt();
                    }
                }
                int coefNum2 = 3;
                if (sizeId != 3) {
                    coefNum2 = 1;
                }
                matrixId += coefNum2;
            }
        }
    }

    private static void skipShortTermRefPicSets(ParsableNalUnitBitArray bitArray) {
        int numShortTermRefPicSets = bitArray.readUnsignedExpGolombCodedInt();
        boolean interRefPicSetPredictionFlag = false;
        int previousNumDeltaPocs = 0;
        for (int stRpsIdx = 0; stRpsIdx < numShortTermRefPicSets; stRpsIdx++) {
            if (stRpsIdx != 0) {
                interRefPicSetPredictionFlag = bitArray.readBit();
            }
            if (interRefPicSetPredictionFlag) {
                bitArray.skipBit();
                bitArray.readUnsignedExpGolombCodedInt();
                for (int j = 0; j <= previousNumDeltaPocs; j++) {
                    if (bitArray.readBit()) {
                        bitArray.skipBit();
                    }
                }
            } else {
                int numNegativePics = bitArray.readUnsignedExpGolombCodedInt();
                int numPositivePics = bitArray.readUnsignedExpGolombCodedInt();
                previousNumDeltaPocs = numNegativePics + numPositivePics;
                for (int i = 0; i < numNegativePics; i++) {
                    bitArray.readUnsignedExpGolombCodedInt();
                    bitArray.skipBit();
                }
                for (int i2 = 0; i2 < numPositivePics; i2++) {
                    bitArray.readUnsignedExpGolombCodedInt();
                    bitArray.skipBit();
                }
            }
        }
    }

    /* renamed from: com.google.android.exoplayer2.extractor.ts.H265Reader$SampleReader */
    private static final class SampleReader {
        private static final int FIRST_SLICE_FLAG_OFFSET = 2;
        private boolean isFirstParameterSet;
        private boolean isFirstSlice;
        private boolean lookingForFirstSliceFlag;
        private int nalUnitBytesRead;
        private boolean nalUnitHasKeyframeData;
        private long nalUnitStartPosition;
        private long nalUnitTimeUs;
        private final TrackOutput output;
        private boolean readingSample;
        private boolean sampleIsKeyframe;
        private long samplePosition;
        private long sampleTimeUs;
        private boolean writingParameterSets;

        public SampleReader(TrackOutput output2) {
            this.output = output2;
        }

        public void reset() {
            this.lookingForFirstSliceFlag = false;
            this.isFirstSlice = false;
            this.isFirstParameterSet = false;
            this.readingSample = false;
            this.writingParameterSets = false;
        }

        public void startNalUnit(long position, int offset, int nalUnitType, long pesTimeUs) {
            boolean z = false;
            this.isFirstSlice = false;
            this.isFirstParameterSet = false;
            this.nalUnitTimeUs = pesTimeUs;
            this.nalUnitBytesRead = 0;
            this.nalUnitStartPosition = position;
            if (nalUnitType >= 32) {
                if (!this.writingParameterSets && this.readingSample) {
                    outputSample(offset);
                    this.readingSample = false;
                }
                if (nalUnitType <= 34) {
                    this.isFirstParameterSet = !this.writingParameterSets;
                    this.writingParameterSets = true;
                }
            }
            this.nalUnitHasKeyframeData = nalUnitType >= 16 && nalUnitType <= 21;
            if (this.nalUnitHasKeyframeData || nalUnitType <= 9) {
                z = true;
            }
            this.lookingForFirstSliceFlag = z;
        }

        public void readNalUnitData(byte[] data, int offset, int limit) {
            if (this.lookingForFirstSliceFlag) {
                int i = this.nalUnitBytesRead;
                int headerOffset = (offset + 2) - i;
                if (headerOffset < limit) {
                    this.isFirstSlice = (data[headerOffset] & UnsignedBytes.MAX_POWER_OF_TWO) != 0;
                    this.lookingForFirstSliceFlag = false;
                    return;
                }
                this.nalUnitBytesRead = i + (limit - offset);
            }
        }

        public void endNalUnit(long position, int offset) {
            if (this.writingParameterSets && this.isFirstSlice) {
                this.sampleIsKeyframe = this.nalUnitHasKeyframeData;
                this.writingParameterSets = false;
            } else if (this.isFirstParameterSet || this.isFirstSlice) {
                if (this.readingSample) {
                    outputSample(offset + ((int) (position - this.nalUnitStartPosition)));
                }
                this.samplePosition = this.nalUnitStartPosition;
                this.sampleTimeUs = this.nalUnitTimeUs;
                this.readingSample = true;
                this.sampleIsKeyframe = this.nalUnitHasKeyframeData;
            }
        }

        private void outputSample(int offset) {
            int flags = this.sampleIsKeyframe;
            int flags2 = (int) flags;
            this.output.sampleMetadata(this.sampleTimeUs, flags2, (int) (this.nalUnitStartPosition - this.samplePosition), offset, null);
        }
    }
}
