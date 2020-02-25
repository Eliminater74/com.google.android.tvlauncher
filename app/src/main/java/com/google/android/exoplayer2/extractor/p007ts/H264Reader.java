package com.google.android.exoplayer2.extractor.p007ts;

import android.util.SparseArray;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.ParsableNalUnitBitArray;

import java.util.ArrayList;
import java.util.Arrays;

/* renamed from: com.google.android.exoplayer2.extractor.ts.H264Reader */
public final class H264Reader implements ElementaryStreamReader {
    private static final int NAL_UNIT_TYPE_PPS = 8;
    private static final int NAL_UNIT_TYPE_SEI = 6;
    private static final int NAL_UNIT_TYPE_SPS = 7;
    private final boolean allowNonIdrKeyframes;
    private final boolean detectAccessUnits;
    private final NalUnitTargetBuffer pps = new NalUnitTargetBuffer(8, 128);
    private final boolean[] prefixFlags = new boolean[3];
    private final NalUnitTargetBuffer sei = new NalUnitTargetBuffer(6, 128);
    private final SeiReader seiReader;
    private final ParsableByteArray seiWrapper = new ParsableByteArray();
    private final NalUnitTargetBuffer sps = new NalUnitTargetBuffer(7, 128);
    private String formatId;
    private boolean hasOutputFormat;
    private TrackOutput output;
    private long pesTimeUs;
    private boolean randomAccessIndicator;
    private SampleReader sampleReader;
    private long totalBytesWritten;

    public H264Reader(SeiReader seiReader2, boolean allowNonIdrKeyframes2, boolean detectAccessUnits2) {
        this.seiReader = seiReader2;
        this.allowNonIdrKeyframes = allowNonIdrKeyframes2;
        this.detectAccessUnits = detectAccessUnits2;
    }

    public void seek() {
        NalUnitUtil.clearPrefixFlags(this.prefixFlags);
        this.sps.reset();
        this.pps.reset();
        this.sei.reset();
        this.sampleReader.reset();
        this.totalBytesWritten = 0;
        this.randomAccessIndicator = false;
    }

    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator idGenerator) {
        idGenerator.generateNewId();
        this.formatId = idGenerator.getFormatId();
        this.output = extractorOutput.track(idGenerator.getTrackId(), 2);
        this.sampleReader = new SampleReader(this.output, this.allowNonIdrKeyframes, this.detectAccessUnits);
        this.seiReader.createTracks(extractorOutput, idGenerator);
    }

    public void packetStarted(long pesTimeUs2, int flags) {
        this.pesTimeUs = pesTimeUs2;
        this.randomAccessIndicator |= (flags & 2) != 0;
    }

    public void consume(ParsableByteArray data) {
        ParsableByteArray parsableByteArray = data;
        int offset = data.getPosition();
        int limit = data.limit();
        byte[] dataArray = parsableByteArray.data;
        this.totalBytesWritten += (long) data.bytesLeft();
        this.output.sampleData(parsableByteArray, data.bytesLeft());
        int offset2 = offset;
        while (true) {
            int nalUnitOffset = NalUnitUtil.findNalUnit(dataArray, offset2, limit, this.prefixFlags);
            if (nalUnitOffset == limit) {
                nalUnitData(dataArray, offset2, limit);
                return;
            }
            int nalUnitType = NalUnitUtil.getNalUnitType(dataArray, nalUnitOffset);
            int lengthToNalUnit = nalUnitOffset - offset2;
            if (lengthToNalUnit > 0) {
                nalUnitData(dataArray, offset2, nalUnitOffset);
            }
            int bytesWrittenPastPosition = limit - nalUnitOffset;
            long j = this.totalBytesWritten - ((long) bytesWrittenPastPosition);
            endNalUnit(j, bytesWrittenPastPosition, lengthToNalUnit < 0 ? -lengthToNalUnit : 0, this.pesTimeUs);
            startNalUnit(j, nalUnitType, this.pesTimeUs);
            offset2 = nalUnitOffset + 3;
        }
    }

    public void packetFinished() {
    }

    private void startNalUnit(long position, int nalUnitType, long pesTimeUs2) {
        if (!this.hasOutputFormat || this.sampleReader.needsSpsPps()) {
            this.sps.startNalUnit(nalUnitType);
            this.pps.startNalUnit(nalUnitType);
        }
        this.sei.startNalUnit(nalUnitType);
        this.sampleReader.startNalUnit(position, nalUnitType, pesTimeUs2);
    }

    private void nalUnitData(byte[] dataArray, int offset, int limit) {
        if (!this.hasOutputFormat || this.sampleReader.needsSpsPps()) {
            this.sps.appendToNalUnit(dataArray, offset, limit);
            this.pps.appendToNalUnit(dataArray, offset, limit);
        }
        this.sei.appendToNalUnit(dataArray, offset, limit);
        this.sampleReader.appendToNalUnit(dataArray, offset, limit);
    }

    /* JADX INFO: Multiple debug info for r2v25 com.google.android.exoplayer2.util.NalUnitUtil$SpsData: [D('spsData' com.google.android.exoplayer2.util.NalUnitUtil$SpsData), D('initializationData' java.util.List<byte[]>)] */
    private void endNalUnit(long position, int offset, int discardPadding, long pesTimeUs2) {
        int i = discardPadding;
        if (!this.hasOutputFormat || this.sampleReader.needsSpsPps()) {
            this.sps.endNalUnit(i);
            this.pps.endNalUnit(i);
            if (!this.hasOutputFormat) {
                if (this.sps.isCompleted() && this.pps.isCompleted()) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(Arrays.copyOf(this.sps.nalData, this.sps.nalLength));
                    arrayList.add(Arrays.copyOf(this.pps.nalData, this.pps.nalLength));
                    NalUnitUtil.SpsData spsData = NalUnitUtil.parseSpsNalUnit(this.sps.nalData, 3, this.sps.nalLength);
                    NalUnitUtil.PpsData ppsData = NalUnitUtil.parsePpsNalUnit(this.pps.nalData, 3, this.pps.nalLength);
                    TrackOutput trackOutput = this.output;
                    TrackOutput trackOutput2 = trackOutput;
                    trackOutput2.format(Format.createVideoSampleFormat(this.formatId, MimeTypes.VIDEO_H264, CodecSpecificDataUtil.buildAvcCodecString(spsData.profileIdc, spsData.constraintsFlagsAndReservedZero2Bits, spsData.levelIdc), -1, -1, spsData.width, spsData.height, -1.0f, arrayList, -1, spsData.pixelWidthAspectRatio, null));
                    this.hasOutputFormat = true;
                    this.sampleReader.putSps(spsData);
                    this.sampleReader.putPps(ppsData);
                    this.sps.reset();
                    this.pps.reset();
                }
            } else if (this.sps.isCompleted()) {
                this.sampleReader.putSps(NalUnitUtil.parseSpsNalUnit(this.sps.nalData, 3, this.sps.nalLength));
                this.sps.reset();
            } else if (this.pps.isCompleted()) {
                this.sampleReader.putPps(NalUnitUtil.parsePpsNalUnit(this.pps.nalData, 3, this.pps.nalLength));
                this.pps.reset();
            }
        }
        if (this.sei.endNalUnit(i)) {
            this.seiWrapper.reset(this.sei.nalData, NalUnitUtil.unescapeStream(this.sei.nalData, this.sei.nalLength));
            this.seiWrapper.setPosition(4);
            this.seiReader.consume(pesTimeUs2, this.seiWrapper);
        }
        if (this.sampleReader.endNalUnit(position, offset, this.hasOutputFormat, this.randomAccessIndicator)) {
            this.randomAccessIndicator = false;
        }
    }

    /* renamed from: com.google.android.exoplayer2.extractor.ts.H264Reader$SampleReader */
    private static final class SampleReader {
        private static final int DEFAULT_BUFFER_SIZE = 128;
        private static final int NAL_UNIT_TYPE_AUD = 9;
        private static final int NAL_UNIT_TYPE_IDR = 5;
        private static final int NAL_UNIT_TYPE_NON_IDR = 1;
        private static final int NAL_UNIT_TYPE_PARTITION_A = 2;
        private final boolean allowNonIdrKeyframes;
        private final boolean detectAccessUnits;
        private final TrackOutput output;
        private final SparseArray<NalUnitUtil.PpsData> pps = new SparseArray<>();
        private final SparseArray<NalUnitUtil.SpsData> sps = new SparseArray<>();
        private byte[] buffer = new byte[128];
        private final ParsableNalUnitBitArray bitArray = new ParsableNalUnitBitArray(this.buffer, 0, 0);
        private int bufferLength;
        private boolean isFilling;
        private long nalUnitStartPosition;
        private long nalUnitTimeUs;
        private int nalUnitType;
        private SliceHeaderData previousSliceHeader = new SliceHeaderData();
        private boolean readingSample;
        private boolean sampleIsKeyframe;
        private long samplePosition;
        private long sampleTimeUs;
        private SliceHeaderData sliceHeader = new SliceHeaderData();

        public SampleReader(TrackOutput output2, boolean allowNonIdrKeyframes2, boolean detectAccessUnits2) {
            this.output = output2;
            this.allowNonIdrKeyframes = allowNonIdrKeyframes2;
            this.detectAccessUnits = detectAccessUnits2;
            reset();
        }

        public boolean needsSpsPps() {
            return this.detectAccessUnits;
        }

        public void putSps(NalUnitUtil.SpsData spsData) {
            this.sps.append(spsData.seqParameterSetId, spsData);
        }

        public void putPps(NalUnitUtil.PpsData ppsData) {
            this.pps.append(ppsData.picParameterSetId, ppsData);
        }

        public void reset() {
            this.isFilling = false;
            this.readingSample = false;
            this.sliceHeader.clear();
        }

        public void startNalUnit(long position, int type, long pesTimeUs) {
            this.nalUnitType = type;
            this.nalUnitTimeUs = pesTimeUs;
            this.nalUnitStartPosition = position;
            if (!this.allowNonIdrKeyframes || this.nalUnitType != 1) {
                if (this.detectAccessUnits) {
                    int i = this.nalUnitType;
                    if (!(i == 5 || i == 1 || i == 2)) {
                        return;
                    }
                } else {
                    return;
                }
            }
            SliceHeaderData newSliceHeader = this.previousSliceHeader;
            this.previousSliceHeader = this.sliceHeader;
            this.sliceHeader = newSliceHeader;
            this.sliceHeader.clear();
            this.bufferLength = 0;
            this.isFilling = true;
        }

        public void appendToNalUnit(byte[] data, int offset, int limit) {
            boolean bottomFieldFlag;
            boolean bottomFieldFlagPresent;
            int idrPicId;
            int deltaPicOrderCnt1;
            int deltaPicOrderCnt0;
            int deltaPicOrderCntBottom;
            int picOrderCntLsb;
            int i = offset;
            if (this.isFilling) {
                int readLength = limit - i;
                byte[] bArr = this.buffer;
                int length = bArr.length;
                int i2 = this.bufferLength;
                if (length < i2 + readLength) {
                    this.buffer = Arrays.copyOf(bArr, (i2 + readLength) * 2);
                }
                System.arraycopy(data, i, this.buffer, this.bufferLength, readLength);
                this.bufferLength += readLength;
                this.bitArray.reset(this.buffer, 0, this.bufferLength);
                if (this.bitArray.canReadBits(8)) {
                    this.bitArray.skipBit();
                    int nalRefIdc = this.bitArray.readBits(2);
                    this.bitArray.skipBits(5);
                    if (this.bitArray.canReadExpGolombCodedNum()) {
                        this.bitArray.readUnsignedExpGolombCodedInt();
                        if (this.bitArray.canReadExpGolombCodedNum()) {
                            int sliceType = this.bitArray.readUnsignedExpGolombCodedInt();
                            if (!this.detectAccessUnits) {
                                this.isFilling = false;
                                this.sliceHeader.setSliceType(sliceType);
                            } else if (this.bitArray.canReadExpGolombCodedNum()) {
                                int picParameterSetId = this.bitArray.readUnsignedExpGolombCodedInt();
                                if (this.pps.indexOfKey(picParameterSetId) < 0) {
                                    this.isFilling = false;
                                    return;
                                }
                                NalUnitUtil.PpsData ppsData = this.pps.get(picParameterSetId);
                                NalUnitUtil.SpsData spsData = this.sps.get(ppsData.seqParameterSetId);
                                if (spsData.separateColorPlaneFlag) {
                                    if (this.bitArray.canReadBits(2)) {
                                        this.bitArray.skipBits(2);
                                    } else {
                                        return;
                                    }
                                }
                                if (this.bitArray.canReadBits(spsData.frameNumLength)) {
                                    boolean fieldPicFlag = false;
                                    int frameNum = this.bitArray.readBits(spsData.frameNumLength);
                                    if (spsData.frameMbsOnlyFlag) {
                                        bottomFieldFlagPresent = false;
                                        bottomFieldFlag = false;
                                    } else if (this.bitArray.canReadBits(1)) {
                                        fieldPicFlag = this.bitArray.readBit();
                                        if (!fieldPicFlag) {
                                            bottomFieldFlagPresent = false;
                                            bottomFieldFlag = false;
                                        } else if (this.bitArray.canReadBits(1)) {
                                            bottomFieldFlagPresent = true;
                                            bottomFieldFlag = this.bitArray.readBit();
                                        } else {
                                            return;
                                        }
                                    } else {
                                        return;
                                    }
                                    boolean idrPicFlag = this.nalUnitType == 5;
                                    if (!idrPicFlag) {
                                        idrPicId = 0;
                                    } else if (this.bitArray.canReadExpGolombCodedNum()) {
                                        idrPicId = this.bitArray.readUnsignedExpGolombCodedInt();
                                    } else {
                                        return;
                                    }
                                    if (spsData.picOrderCountType == 0) {
                                        if (this.bitArray.canReadBits(spsData.picOrderCntLsbLength)) {
                                            picOrderCntLsb = this.bitArray.readBits(spsData.picOrderCntLsbLength);
                                            if (ppsData.bottomFieldPicOrderInFramePresentFlag == 0 || fieldPicFlag) {
                                                deltaPicOrderCntBottom = 0;
                                                deltaPicOrderCnt0 = 0;
                                                deltaPicOrderCnt1 = 0;
                                            } else if (this.bitArray.canReadExpGolombCodedNum()) {
                                                deltaPicOrderCntBottom = this.bitArray.readSignedExpGolombCodedInt();
                                                deltaPicOrderCnt0 = 0;
                                                deltaPicOrderCnt1 = 0;
                                            } else {
                                                return;
                                            }
                                        } else {
                                            return;
                                        }
                                    } else if (spsData.picOrderCountType != 1 || spsData.deltaPicOrderAlwaysZeroFlag) {
                                        picOrderCntLsb = 0;
                                        deltaPicOrderCntBottom = 0;
                                        deltaPicOrderCnt0 = 0;
                                        deltaPicOrderCnt1 = 0;
                                    } else if (this.bitArray.canReadExpGolombCodedNum()) {
                                        int deltaPicOrderCnt02 = this.bitArray.readSignedExpGolombCodedInt();
                                        if (ppsData.bottomFieldPicOrderInFramePresentFlag == 0 || fieldPicFlag) {
                                            deltaPicOrderCnt0 = deltaPicOrderCnt02;
                                            picOrderCntLsb = 0;
                                            deltaPicOrderCntBottom = 0;
                                            deltaPicOrderCnt1 = 0;
                                        } else if (this.bitArray.canReadExpGolombCodedNum()) {
                                            deltaPicOrderCnt0 = deltaPicOrderCnt02;
                                            picOrderCntLsb = 0;
                                            deltaPicOrderCntBottom = 0;
                                            deltaPicOrderCnt1 = this.bitArray.readSignedExpGolombCodedInt();
                                        } else {
                                            return;
                                        }
                                    } else {
                                        return;
                                    }
                                    this.sliceHeader.setAll(spsData, nalRefIdc, sliceType, frameNum, picParameterSetId, fieldPicFlag, bottomFieldFlagPresent, bottomFieldFlag, idrPicFlag, idrPicId, picOrderCntLsb, deltaPicOrderCntBottom, deltaPicOrderCnt0, deltaPicOrderCnt1);
                                    this.isFilling = false;
                                }
                            }
                        }
                    }
                }
            }
        }

        public boolean endNalUnit(long position, int offset, boolean hasOutputFormat, boolean randomAccessIndicator) {
            boolean z = false;
            if (this.nalUnitType == 9 || (this.detectAccessUnits && this.sliceHeader.isFirstVclNalUnitOfPicture(this.previousSliceHeader))) {
                if (hasOutputFormat && this.readingSample) {
                    outputSample(offset + ((int) (position - this.nalUnitStartPosition)));
                }
                this.samplePosition = this.nalUnitStartPosition;
                this.sampleTimeUs = this.nalUnitTimeUs;
                this.sampleIsKeyframe = false;
                this.readingSample = true;
            }
            boolean treatIFrameAsKeyframe = this.allowNonIdrKeyframes ? this.sliceHeader.isISlice() : randomAccessIndicator;
            boolean z2 = this.sampleIsKeyframe;
            int i = this.nalUnitType;
            if (i == 5 || (treatIFrameAsKeyframe && i == 1)) {
                z = true;
            }
            this.sampleIsKeyframe = z | z2;
            return this.sampleIsKeyframe;
        }

        private void outputSample(int offset) {
            int flags = this.sampleIsKeyframe;
            int flags2 = (int) flags;
            this.output.sampleMetadata(this.sampleTimeUs, flags2, (int) (this.nalUnitStartPosition - this.samplePosition), offset, null);
        }

        /* renamed from: com.google.android.exoplayer2.extractor.ts.H264Reader$SampleReader$SliceHeaderData */
        private static final class SliceHeaderData {
            private static final int SLICE_TYPE_ALL_I = 7;
            private static final int SLICE_TYPE_I = 2;
            private boolean bottomFieldFlag;
            private boolean bottomFieldFlagPresent;
            private int deltaPicOrderCnt0;
            private int deltaPicOrderCnt1;
            private int deltaPicOrderCntBottom;
            private boolean fieldPicFlag;
            private int frameNum;
            private boolean hasSliceType;
            private boolean idrPicFlag;
            private int idrPicId;
            private boolean isComplete;
            private int nalRefIdc;
            private int picOrderCntLsb;
            private int picParameterSetId;
            private int sliceType;
            private NalUnitUtil.SpsData spsData;

            private SliceHeaderData() {
            }

            public void clear() {
                this.hasSliceType = false;
                this.isComplete = false;
            }

            public void setSliceType(int sliceType2) {
                this.sliceType = sliceType2;
                this.hasSliceType = true;
            }

            public void setAll(NalUnitUtil.SpsData spsData2, int nalRefIdc2, int sliceType2, int frameNum2, int picParameterSetId2, boolean fieldPicFlag2, boolean bottomFieldFlagPresent2, boolean bottomFieldFlag2, boolean idrPicFlag2, int idrPicId2, int picOrderCntLsb2, int deltaPicOrderCntBottom2, int deltaPicOrderCnt02, int deltaPicOrderCnt12) {
                this.spsData = spsData2;
                this.nalRefIdc = nalRefIdc2;
                this.sliceType = sliceType2;
                this.frameNum = frameNum2;
                this.picParameterSetId = picParameterSetId2;
                this.fieldPicFlag = fieldPicFlag2;
                this.bottomFieldFlagPresent = bottomFieldFlagPresent2;
                this.bottomFieldFlag = bottomFieldFlag2;
                this.idrPicFlag = idrPicFlag2;
                this.idrPicId = idrPicId2;
                this.picOrderCntLsb = picOrderCntLsb2;
                this.deltaPicOrderCntBottom = deltaPicOrderCntBottom2;
                this.deltaPicOrderCnt0 = deltaPicOrderCnt02;
                this.deltaPicOrderCnt1 = deltaPicOrderCnt12;
                this.isComplete = true;
                this.hasSliceType = true;
            }

            public boolean isISlice() {
                int i;
                return this.hasSliceType && ((i = this.sliceType) == 7 || i == 2);
            }

            /* access modifiers changed from: private */
            public boolean isFirstVclNalUnitOfPicture(SliceHeaderData other) {
                boolean z;
                boolean z2;
                if (this.isComplete) {
                    if (!other.isComplete || this.frameNum != other.frameNum || this.picParameterSetId != other.picParameterSetId || this.fieldPicFlag != other.fieldPicFlag) {
                        return true;
                    }
                    if (this.bottomFieldFlagPresent && other.bottomFieldFlagPresent && this.bottomFieldFlag != other.bottomFieldFlag) {
                        return true;
                    }
                    int i = this.nalRefIdc;
                    int i2 = other.nalRefIdc;
                    if (i != i2 && (i == 0 || i2 == 0)) {
                        return true;
                    }
                    if (this.spsData.picOrderCountType == 0 && other.spsData.picOrderCountType == 0 && (this.picOrderCntLsb != other.picOrderCntLsb || this.deltaPicOrderCntBottom != other.deltaPicOrderCntBottom)) {
                        return true;
                    }
                    if ((this.spsData.picOrderCountType != 1 || other.spsData.picOrderCountType != 1 || (this.deltaPicOrderCnt0 == other.deltaPicOrderCnt0 && this.deltaPicOrderCnt1 == other.deltaPicOrderCnt1)) && (z = this.idrPicFlag) == (z2 = other.idrPicFlag)) {
                        return z && z2 && this.idrPicId != other.idrPicId;
                    }
                    return true;
                }
            }
        }
    }
}
