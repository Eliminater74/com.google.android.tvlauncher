package com.google.android.exoplayer2.util;

import com.google.common.base.Ascii;

import java.nio.ByteBuffer;
import java.util.Arrays;

public final class NalUnitUtil {
    public static final float[] ASPECT_RATIO_IDC_VALUES = {1.0f, 1.0f, 1.0909091f, 0.90909094f, 1.4545455f, 1.2121212f, 2.1818182f, 1.8181819f, 2.909091f, 2.4242425f, 1.6363636f, 1.3636364f, 1.939394f, 1.6161616f, 1.3333334f, 1.5f, 2.0f};
    public static final int EXTENDED_SAR = 255;
    public static final byte[] NAL_START_CODE = {0, 0, 0, 1};
    private static final int H264_NAL_UNIT_TYPE_SEI = 6;
    private static final int H264_NAL_UNIT_TYPE_SPS = 7;
    private static final int H265_NAL_UNIT_TYPE_PREFIX_SEI = 39;
    private static final String TAG = "NalUnitUtil";
    private static final Object scratchEscapePositionsLock = new Object();
    private static int[] scratchEscapePositions = new int[10];

    private NalUnitUtil() {
    }

    /* JADX INFO: Multiple debug info for r6v2 int: [D('i' int), D('remainingLength' int)] */
    public static int unescapeStream(byte[] data, int limit) {
        int unescapedLength;
        synchronized (scratchEscapePositionsLock) {
            int position = 0;
            int scratchEscapeCount = 0;
            while (position < limit) {
                try {
                    position = findNextUnescapeIndex(data, position, limit);
                    if (position < limit) {
                        if (scratchEscapePositions.length <= scratchEscapeCount) {
                            scratchEscapePositions = Arrays.copyOf(scratchEscapePositions, scratchEscapePositions.length * 2);
                        }
                        scratchEscapePositions[scratchEscapeCount] = position;
                        position += 3;
                        scratchEscapeCount++;
                    }
                } finally {
                }
            }
            unescapedLength = limit - scratchEscapeCount;
            int escapedPosition = 0;
            int unescapedPosition = 0;
            for (int i = 0; i < scratchEscapeCount; i++) {
                int copyLength = scratchEscapePositions[i] - escapedPosition;
                System.arraycopy(data, escapedPosition, data, unescapedPosition, copyLength);
                int unescapedPosition2 = unescapedPosition + copyLength;
                int unescapedPosition3 = unescapedPosition2 + 1;
                data[unescapedPosition2] = 0;
                unescapedPosition = unescapedPosition3 + 1;
                data[unescapedPosition3] = 0;
                escapedPosition += copyLength + 3;
            }
            System.arraycopy(data, escapedPosition, data, unescapedPosition, unescapedLength - unescapedPosition);
        }
        return unescapedLength;
    }

    public static void discardToSps(ByteBuffer data) {
        int length = data.position();
        int consecutiveZeros = 0;
        for (int offset = 0; offset + 1 < length; offset++) {
            int value = data.get(offset) & 255;
            if (consecutiveZeros == 3) {
                if (value == 1 && (data.get(offset + 1) & Ascii.f159US) == 7) {
                    ByteBuffer offsetData = data.duplicate();
                    offsetData.position(offset - 3);
                    offsetData.limit(length);
                    data.position(0);
                    data.put(offsetData);
                    return;
                }
            } else if (value == 0) {
                consecutiveZeros++;
            }
            if (value != 0) {
                consecutiveZeros = 0;
            }
        }
        data.clear();
    }

    public static boolean isNalUnitSei(String mimeType, byte nalUnitHeaderFirstByte) {
        if (MimeTypes.VIDEO_H264.equals(mimeType) && (nalUnitHeaderFirstByte & Ascii.f159US) == 6) {
            return true;
        }
        if (!MimeTypes.VIDEO_H265.equals(mimeType) || ((nalUnitHeaderFirstByte & 126) >> 1) != 39) {
            return false;
        }
        return true;
    }

    public static int getNalUnitType(byte[] data, int offset) {
        return data[offset + 3] & Ascii.f159US;
    }

    public static int getH265NalUnitType(byte[] data, int offset) {
        return (data[offset + 3] & 126) >> 1;
    }

    public static SpsData parseSpsNalUnit(byte[] nalData, int nalOffset, int nalLimit) {
        boolean separateColorPlaneFlag;
        int chromaFormatIdc;
        boolean deltaPicOrderAlwaysZeroFlag;
        int picOrderCntLsbLength;
        int frameHeight;
        int cropUnitX;
        float pixelWidthHeightRatio;
        int cropUnitX2;
        int cropUnitY;
        int picOrderCntLsbLength2;
        ParsableNalUnitBitArray data = new ParsableNalUnitBitArray(nalData, nalOffset, nalLimit);
        data.skipBits(8);
        int profileIdc = data.readBits(8);
        int constraintsFlagsAndReservedZero2Bits = data.readBits(8);
        int levelIdc = data.readBits(8);
        int seqParameterSetId = data.readUnsignedExpGolombCodedInt();
        boolean separateColorPlaneFlag2 = false;
        if (profileIdc == 100 || profileIdc == 110 || profileIdc == 122 || profileIdc == 244 || profileIdc == 44 || profileIdc == 83 || profileIdc == 86 || profileIdc == 118 || profileIdc == 128 || profileIdc == 138) {
            int chromaFormatIdc2 = data.readUnsignedExpGolombCodedInt();
            if (chromaFormatIdc2 == 3) {
                separateColorPlaneFlag2 = data.readBit();
            }
            data.readUnsignedExpGolombCodedInt();
            data.readUnsignedExpGolombCodedInt();
            data.skipBit();
            if (data.readBit()) {
                int limit = chromaFormatIdc2 != 3 ? 8 : 12;
                int i = 0;
                while (i < limit) {
                    if (data.readBit()) {
                        skipScalingList(data, i < 6 ? 16 : 64);
                    }
                    i++;
                }
            }
            chromaFormatIdc = chromaFormatIdc2;
            separateColorPlaneFlag = separateColorPlaneFlag2;
        } else {
            chromaFormatIdc = 1;
            separateColorPlaneFlag = false;
        }
        int frameNumLength = data.readUnsignedExpGolombCodedInt() + 4;
        int picOrderCntType = data.readUnsignedExpGolombCodedInt();
        int picOrderCntLsbLength3 = 0;
        int subHeightC = 1;
        if (picOrderCntType == 0) {
            deltaPicOrderAlwaysZeroFlag = false;
            picOrderCntLsbLength = data.readUnsignedExpGolombCodedInt() + 4;
        } else if (picOrderCntType == 1) {
            boolean deltaPicOrderAlwaysZeroFlag2 = data.readBit();
            data.readSignedExpGolombCodedInt();
            data.readSignedExpGolombCodedInt();
            long numRefFramesInPicOrderCntCycle = (long) data.readUnsignedExpGolombCodedInt();
            int i2 = 0;
            while (true) {
                picOrderCntLsbLength2 = picOrderCntLsbLength3;
                if (((long) i2) >= numRefFramesInPicOrderCntCycle) {
                    break;
                }
                data.readUnsignedExpGolombCodedInt();
                i2++;
                picOrderCntLsbLength3 = picOrderCntLsbLength2;
            }
            deltaPicOrderAlwaysZeroFlag = deltaPicOrderAlwaysZeroFlag2;
            picOrderCntLsbLength = picOrderCntLsbLength2;
        } else {
            deltaPicOrderAlwaysZeroFlag = false;
            picOrderCntLsbLength = 0;
        }
        data.readUnsignedExpGolombCodedInt();
        data.skipBit();
        int picWidthInMbs = data.readUnsignedExpGolombCodedInt() + 1;
        boolean frameMbsOnlyFlag = data.readBit();
        int frameHeightInMbs = (true - (frameMbsOnlyFlag)) * (data.readUnsignedExpGolombCodedInt() + 1);
        if (!frameMbsOnlyFlag) {
            data.skipBit();
        }
        data.skipBit();
        int frameWidth = picWidthInMbs * 16;
        int frameHeight2 = frameHeightInMbs * 16;
        if (data.readBit()) {
            int frameCropLeftOffset = data.readUnsignedExpGolombCodedInt();
            int frameCropRightOffset = data.readUnsignedExpGolombCodedInt();
            int frameCropTopOffset = data.readUnsignedExpGolombCodedInt();
            int frameCropBottomOffset = data.readUnsignedExpGolombCodedInt();
            if (chromaFormatIdc == 0) {
                cropUnitY = true - frameMbsOnlyFlag;
                cropUnitX2 = 1;
            } else {
                int subWidthC = chromaFormatIdc == 3 ? 1 : 2;
                if (chromaFormatIdc == 1) {
                    subHeightC = 2;
                }
                cropUnitX2 = subWidthC;
                cropUnitY = (true - frameMbsOnlyFlag) * subHeightC;
            }
            cropUnitX = frameWidth - ((frameCropLeftOffset + frameCropRightOffset) * cropUnitX2);
            frameHeight = frameHeight2 - ((frameCropTopOffset + frameCropBottomOffset) * cropUnitY);
        } else {
            cropUnitX = frameWidth;
            frameHeight = frameHeight2;
        }
        float pixelWidthHeightRatio2 = 1.0f;
        if (data.readBit() && data.readBit()) {
            int aspectRatioIdc = data.readBits(8);
            if (aspectRatioIdc == 255) {
                int sarWidth = data.readBits(16);
                int sarHeight = data.readBits(16);
                if (!(sarWidth == 0 || sarHeight == 0)) {
                    pixelWidthHeightRatio2 = ((float) sarWidth) / ((float) sarHeight);
                }
                pixelWidthHeightRatio = pixelWidthHeightRatio2;
            } else {
                float[] fArr = ASPECT_RATIO_IDC_VALUES;
                if (aspectRatioIdc < fArr.length) {
                    pixelWidthHeightRatio = fArr[aspectRatioIdc];
                } else {
                    StringBuilder sb = new StringBuilder(46);
                    sb.append("Unexpected aspect_ratio_idc value: ");
                    sb.append(aspectRatioIdc);
                    Log.m30w(TAG, sb.toString());
                }
            }
            return new SpsData(profileIdc, constraintsFlagsAndReservedZero2Bits, levelIdc, seqParameterSetId, cropUnitX, frameHeight, pixelWidthHeightRatio, separateColorPlaneFlag, frameMbsOnlyFlag, frameNumLength, picOrderCntType, picOrderCntLsbLength, deltaPicOrderAlwaysZeroFlag);
        }
        pixelWidthHeightRatio = 1.0f;
        return new SpsData(profileIdc, constraintsFlagsAndReservedZero2Bits, levelIdc, seqParameterSetId, cropUnitX, frameHeight, pixelWidthHeightRatio, separateColorPlaneFlag, frameMbsOnlyFlag, frameNumLength, picOrderCntType, picOrderCntLsbLength, deltaPicOrderAlwaysZeroFlag);
    }

    public static PpsData parsePpsNalUnit(byte[] nalData, int nalOffset, int nalLimit) {
        ParsableNalUnitBitArray data = new ParsableNalUnitBitArray(nalData, nalOffset, nalLimit);
        data.skipBits(8);
        int picParameterSetId = data.readUnsignedExpGolombCodedInt();
        int seqParameterSetId = data.readUnsignedExpGolombCodedInt();
        data.skipBit();
        return new PpsData(picParameterSetId, seqParameterSetId, data.readBit());
    }

    public static int findNalUnit(byte[] data, int startOffset, int endOffset, boolean[] prefixFlags) {
        boolean z;
        boolean z2;
        int length = endOffset - startOffset;
        boolean z3 = false;
        Assertions.checkState(length >= 0);
        if (length == 0) {
            return endOffset;
        }
        if (prefixFlags != null) {
            if (prefixFlags[0]) {
                clearPrefixFlags(prefixFlags);
                return startOffset - 3;
            } else if (length > 1 && prefixFlags[1] && data[startOffset] == 1) {
                clearPrefixFlags(prefixFlags);
                return startOffset - 2;
            } else if (length > 2 && prefixFlags[2] && data[startOffset] == 0 && data[startOffset + 1] == 1) {
                clearPrefixFlags(prefixFlags);
                return startOffset - 1;
            }
        }
        int limit = endOffset - 1;
        int i = startOffset + 2;
        while (i < limit) {
            if ((data[i] & 254) == 0) {
                if (data[i - 2] == 0 && data[i - 1] == 0 && data[i] == 1) {
                    if (prefixFlags != null) {
                        clearPrefixFlags(prefixFlags);
                    }
                    return i - 2;
                }
                i -= 2;
            }
            i += 3;
        }
        if (prefixFlags != null) {
            if (length > 2) {
                z = data[endOffset + -3] == 0 && data[endOffset + -2] == 0 && data[endOffset + -1] == 1;
            } else if (length == 2) {
                z = prefixFlags[2] && data[endOffset + -2] == 0 && data[endOffset + -1] == 1;
            } else {
                z = prefixFlags[1] && data[endOffset + -1] == 1;
            }
            prefixFlags[0] = z;
            if (length > 1) {
                z2 = data[endOffset + -2] == 0 && data[endOffset + -1] == 0;
            } else {
                z2 = prefixFlags[2] && data[endOffset + -1] == 0;
            }
            prefixFlags[1] = z2;
            if (data[endOffset - 1] == 0) {
                z3 = true;
            }
            prefixFlags[2] = z3;
        }
        return endOffset;
    }

    public static void clearPrefixFlags(boolean[] prefixFlags) {
        prefixFlags[0] = false;
        prefixFlags[1] = false;
        prefixFlags[2] = false;
    }

    private static int findNextUnescapeIndex(byte[] bytes, int offset, int limit) {
        for (int i = offset; i < limit - 2; i++) {
            if (bytes[i] == 0 && bytes[i + 1] == 0 && bytes[i + 2] == 3) {
                return i;
            }
        }
        return limit;
    }

    private static void skipScalingList(ParsableNalUnitBitArray bitArray, int size) {
        int lastScale = 8;
        int nextScale = 8;
        for (int i = 0; i < size; i++) {
            if (nextScale != 0) {
                nextScale = ((lastScale + bitArray.readSignedExpGolombCodedInt()) + 256) % 256;
            }
            lastScale = nextScale == 0 ? lastScale : nextScale;
        }
    }

    public static final class SpsData {
        public final int constraintsFlagsAndReservedZero2Bits;
        public final boolean deltaPicOrderAlwaysZeroFlag;
        public final boolean frameMbsOnlyFlag;
        public final int frameNumLength;
        public final int height;
        public final int levelIdc;
        public final int picOrderCntLsbLength;
        public final int picOrderCountType;
        public final float pixelWidthAspectRatio;
        public final int profileIdc;
        public final boolean separateColorPlaneFlag;
        public final int seqParameterSetId;
        public final int width;

        public SpsData(int profileIdc2, int constraintsFlagsAndReservedZero2Bits2, int levelIdc2, int seqParameterSetId2, int width2, int height2, float pixelWidthAspectRatio2, boolean separateColorPlaneFlag2, boolean frameMbsOnlyFlag2, int frameNumLength2, int picOrderCountType2, int picOrderCntLsbLength2, boolean deltaPicOrderAlwaysZeroFlag2) {
            this.profileIdc = profileIdc2;
            this.constraintsFlagsAndReservedZero2Bits = constraintsFlagsAndReservedZero2Bits2;
            this.levelIdc = levelIdc2;
            this.seqParameterSetId = seqParameterSetId2;
            this.width = width2;
            this.height = height2;
            this.pixelWidthAspectRatio = pixelWidthAspectRatio2;
            this.separateColorPlaneFlag = separateColorPlaneFlag2;
            this.frameMbsOnlyFlag = frameMbsOnlyFlag2;
            this.frameNumLength = frameNumLength2;
            this.picOrderCountType = picOrderCountType2;
            this.picOrderCntLsbLength = picOrderCntLsbLength2;
            this.deltaPicOrderAlwaysZeroFlag = deltaPicOrderAlwaysZeroFlag2;
        }
    }

    public static final class PpsData {
        public final boolean bottomFieldPicOrderInFramePresentFlag;
        public final int picParameterSetId;
        public final int seqParameterSetId;

        public PpsData(int picParameterSetId2, int seqParameterSetId2, boolean bottomFieldPicOrderInFramePresentFlag2) {
            this.picParameterSetId = picParameterSetId2;
            this.seqParameterSetId = seqParameterSetId2;
            this.bottomFieldPicOrderInFramePresentFlag = bottomFieldPicOrderInFramePresentFlag2;
        }
    }
}
