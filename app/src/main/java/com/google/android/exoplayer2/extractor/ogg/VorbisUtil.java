package com.google.android.exoplayer2.extractor.ogg;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.Arrays;

final class VorbisUtil {
    private static final String TAG = "VorbisUtil";

    public static int iLog(int x) {
        int val = 0;
        while (x > 0) {
            val++;
            x >>>= 1;
        }
        return val;
    }

    public static VorbisIdHeader readVorbisIdentificationHeader(ParsableByteArray headerData) throws ParserException {
        ParsableByteArray parsableByteArray = headerData;
        verifyVorbisHeaderCapturePattern(1, parsableByteArray, false);
        long version = headerData.readLittleEndianUnsignedInt();
        int channels = headerData.readUnsignedByte();
        long sampleRate = headerData.readLittleEndianUnsignedInt();
        int bitrateMax = headerData.readLittleEndianInt();
        int bitrateNominal = headerData.readLittleEndianInt();
        int bitrateMin = headerData.readLittleEndianInt();
        int blockSize = headerData.readUnsignedByte();
        return new VorbisIdHeader(version, channels, sampleRate, bitrateMax, bitrateNominal, bitrateMin, (int) Math.pow(2.0d, (double) (blockSize & 15)), (int) Math.pow(2.0d, (double) ((blockSize & 240) >> 4)), (headerData.readUnsignedByte() & 1) > 0, Arrays.copyOf(parsableByteArray.data, headerData.limit()));
    }

    public static CommentHeader readVorbisCommentHeader(ParsableByteArray headerData) throws ParserException {
        verifyVorbisHeaderCapturePattern(3, headerData, false);
        String vendor = headerData.readString((int) headerData.readLittleEndianUnsignedInt());
        int length = 7 + 4 + vendor.length();
        long commentListLen = headerData.readLittleEndianUnsignedInt();
        String[] comments = new String[((int) commentListLen)];
        int length2 = length + 4;
        for (int i = 0; ((long) i) < commentListLen; i++) {
            comments[i] = headerData.readString((int) headerData.readLittleEndianUnsignedInt());
            length2 = length2 + 4 + comments[i].length();
        }
        if ((headerData.readUnsignedByte() & 1) != 0) {
            return new CommentHeader(vendor, comments, length2 + 1);
        }
        throw new ParserException("framing bit expected to be set");
    }

    public static boolean verifyVorbisHeaderCapturePattern(int headerType, ParsableByteArray header, boolean quiet) throws ParserException {
        if (header.bytesLeft() < 7) {
            if (quiet) {
                return false;
            }
            int bytesLeft = header.bytesLeft();
            StringBuilder sb = new StringBuilder(29);
            sb.append("too short header: ");
            sb.append(bytesLeft);
            throw new ParserException(sb.toString());
        } else if (header.readUnsignedByte() != headerType) {
            if (quiet) {
                return false;
            }
            String valueOf = String.valueOf(Integer.toHexString(headerType));
            throw new ParserException(valueOf.length() != 0 ? "expected header type ".concat(valueOf) : new String("expected header type "));
        } else if (header.readUnsignedByte() == 118 && header.readUnsignedByte() == 111 && header.readUnsignedByte() == 114 && header.readUnsignedByte() == 98 && header.readUnsignedByte() == 105 && header.readUnsignedByte() == 115) {
            return true;
        } else {
            if (quiet) {
                return false;
            }
            throw new ParserException("expected characters 'vorbis'");
        }
    }

    public static Mode[] readVorbisModes(ParsableByteArray headerData, int channels) throws ParserException {
        verifyVorbisHeaderCapturePattern(5, headerData, false);
        int numberOfBooks = headerData.readUnsignedByte() + 1;
        VorbisBitArray bitArray = new VorbisBitArray(headerData.data);
        bitArray.skipBits(headerData.getPosition() * 8);
        for (int i = 0; i < numberOfBooks; i++) {
            readBook(bitArray);
        }
        int timeCount = bitArray.readBits(6) + 1;
        int i2 = 0;
        while (i2 < timeCount) {
            if (bitArray.readBits(16) == 0) {
                i2++;
            } else {
                throw new ParserException("placeholder of time domain transforms not zeroed out");
            }
        }
        readFloors(bitArray);
        readResidues(bitArray);
        readMappings(channels, bitArray);
        Mode[] modes = readModes(bitArray);
        if (bitArray.readBit()) {
            return modes;
        }
        throw new ParserException("framing bit after modes not set as expected");
    }

    private static Mode[] readModes(VorbisBitArray bitArray) {
        int modeCount = bitArray.readBits(6) + 1;
        Mode[] modes = new Mode[modeCount];
        for (int i = 0; i < modeCount; i++) {
            modes[i] = new Mode(bitArray.readBit(), bitArray.readBits(16), bitArray.readBits(16), bitArray.readBits(8));
        }
        return modes;
    }

    private static void readMappings(int channels, VorbisBitArray bitArray) throws ParserException {
        int submaps;
        int mappingsCount = bitArray.readBits(6) + 1;
        for (int i = 0; i < mappingsCount; i++) {
            int mappingType = bitArray.readBits(16);
            if (mappingType != 0) {
                StringBuilder sb = new StringBuilder(52);
                sb.append("mapping type other than 0 not supported: ");
                sb.append(mappingType);
                Log.m26e(TAG, sb.toString());
            } else {
                if (bitArray.readBit()) {
                    submaps = bitArray.readBits(4) + 1;
                } else {
                    submaps = 1;
                }
                if (bitArray.readBit()) {
                    int couplingSteps = bitArray.readBits(8) + 1;
                    for (int j = 0; j < couplingSteps; j++) {
                        bitArray.skipBits(iLog(channels - 1));
                        bitArray.skipBits(iLog(channels - 1));
                    }
                }
                if (bitArray.readBits(2) == 0) {
                    if (submaps > 1) {
                        for (int j2 = 0; j2 < channels; j2++) {
                            bitArray.skipBits(4);
                        }
                    }
                    for (int j3 = 0; j3 < submaps; j3++) {
                        bitArray.skipBits(8);
                        bitArray.skipBits(8);
                        bitArray.skipBits(8);
                    }
                } else {
                    throw new ParserException("to reserved bits must be zero after mapping coupling steps");
                }
            }
        }
    }

    private static void readResidues(VorbisBitArray bitArray) throws ParserException {
        int residueCount = bitArray.readBits(6) + 1;
        int i = 0;
        while (i < residueCount) {
            if (bitArray.readBits(16) <= 2) {
                bitArray.skipBits(24);
                bitArray.skipBits(24);
                bitArray.skipBits(24);
                int classifications = bitArray.readBits(6) + 1;
                bitArray.skipBits(8);
                int[] cascade = new int[classifications];
                for (int j = 0; j < classifications; j++) {
                    int highBits = 0;
                    int lowBits = bitArray.readBits(3);
                    if (bitArray.readBit()) {
                        highBits = bitArray.readBits(5);
                    }
                    cascade[j] = (highBits * 8) + lowBits;
                }
                for (int j2 = 0; j2 < classifications; j2++) {
                    for (int k = 0; k < 8; k++) {
                        if ((cascade[j2] & (1 << k)) != 0) {
                            bitArray.skipBits(8);
                        }
                    }
                }
                i++;
            } else {
                throw new ParserException("residueType greater than 2 is not decodable");
            }
        }
    }

    private static void readFloors(VorbisBitArray bitArray) throws ParserException {
        int floorCount = bitArray.readBits(6) + 1;
        for (int i = 0; i < floorCount; i++) {
            int floorType = bitArray.readBits(16);
            if (floorType == 0) {
                bitArray.skipBits(8);
                bitArray.skipBits(16);
                bitArray.skipBits(16);
                bitArray.skipBits(6);
                bitArray.skipBits(8);
                int floorNumberOfBooks = bitArray.readBits(4) + 1;
                for (int j = 0; j < floorNumberOfBooks; j++) {
                    bitArray.skipBits(8);
                }
            } else if (floorType == 1) {
                int partitions = bitArray.readBits(5);
                int maximumClass = -1;
                int[] partitionClassList = new int[partitions];
                for (int j2 = 0; j2 < partitions; j2++) {
                    partitionClassList[j2] = bitArray.readBits(4);
                    if (partitionClassList[j2] > maximumClass) {
                        maximumClass = partitionClassList[j2];
                    }
                }
                int[] classDimensions = new int[(maximumClass + 1)];
                for (int j3 = 0; j3 < classDimensions.length; j3++) {
                    classDimensions[j3] = bitArray.readBits(3) + 1;
                    int classSubclasses = bitArray.readBits(2);
                    if (classSubclasses > 0) {
                        bitArray.skipBits(8);
                    }
                    for (int k = 0; k < (1 << classSubclasses); k++) {
                        bitArray.skipBits(8);
                    }
                }
                bitArray.skipBits(2);
                int rangeBits = bitArray.readBits(4);
                int count = 0;
                int k2 = 0;
                for (int j4 = 0; j4 < partitions; j4++) {
                    count += classDimensions[partitionClassList[j4]];
                    while (k2 < count) {
                        bitArray.skipBits(rangeBits);
                        k2++;
                    }
                }
            } else {
                StringBuilder sb = new StringBuilder(52);
                sb.append("floor type greater than 1 not decodable: ");
                sb.append(floorType);
                throw new ParserException(sb.toString());
            }
        }
    }

    private static CodeBook readBook(VorbisBitArray bitArray) throws ParserException {
        long lookupValuesCount;
        if (bitArray.readBits(24) == 5653314) {
            int dimensions = bitArray.readBits(16);
            int entries = bitArray.readBits(24);
            long[] lengthMap = new long[entries];
            boolean isOrdered = bitArray.readBit();
            if (!isOrdered) {
                boolean isSparse = bitArray.readBit();
                for (int i = 0; i < lengthMap.length; i++) {
                    if (!isSparse) {
                        lengthMap[i] = (long) (bitArray.readBits(5) + 1);
                    } else if (bitArray.readBit()) {
                        lengthMap[i] = (long) (bitArray.readBits(5) + 1);
                    } else {
                        lengthMap[i] = 0;
                    }
                }
            } else {
                int length = bitArray.readBits(5) + 1;
                int i2 = 0;
                while (i2 < lengthMap.length) {
                    int num = bitArray.readBits(iLog(entries - i2));
                    for (int j = 0; j < num && i2 < lengthMap.length; j++) {
                        lengthMap[i2] = (long) length;
                        i2++;
                    }
                    length++;
                }
            }
            int lookupType = bitArray.readBits(4);
            if (lookupType <= 2) {
                if (lookupType == 1 || lookupType == 2) {
                    bitArray.skipBits(32);
                    bitArray.skipBits(32);
                    int valueBits = bitArray.readBits(4) + 1;
                    bitArray.skipBits(1);
                    if (lookupType != 1) {
                        lookupValuesCount = ((long) entries) * ((long) dimensions);
                    } else if (dimensions != 0) {
                        lookupValuesCount = mapType1QuantValues((long) entries, (long) dimensions);
                    } else {
                        lookupValuesCount = 0;
                    }
                    bitArray.skipBits((int) (((long) valueBits) * lookupValuesCount));
                }
                return new CodeBook(dimensions, entries, lengthMap, lookupType, isOrdered);
            }
            StringBuilder sb = new StringBuilder(53);
            sb.append("lookup type greater than 2 not decodable: ");
            sb.append(lookupType);
            throw new ParserException(sb.toString());
        }
        int position = bitArray.getPosition();
        StringBuilder sb2 = new StringBuilder(66);
        sb2.append("expected code book to start with [0x56, 0x43, 0x42] at ");
        sb2.append(position);
        throw new ParserException(sb2.toString());
    }

    private static long mapType1QuantValues(long entries, long dimension) {
        double d = (double) dimension;
        Double.isNaN(d);
        return (long) Math.floor(Math.pow((double) entries, 1.0d / d));
    }

    private VorbisUtil() {
    }

    public static final class CodeBook {
        public final int dimensions;
        public final int entries;
        public final boolean isOrdered;
        public final long[] lengthMap;
        public final int lookupType;

        public CodeBook(int dimensions2, int entries2, long[] lengthMap2, int lookupType2, boolean isOrdered2) {
            this.dimensions = dimensions2;
            this.entries = entries2;
            this.lengthMap = lengthMap2;
            this.lookupType = lookupType2;
            this.isOrdered = isOrdered2;
        }
    }

    public static final class CommentHeader {
        public final String[] comments;
        public final int length;
        public final String vendor;

        public CommentHeader(String vendor2, String[] comments2, int length2) {
            this.vendor = vendor2;
            this.comments = comments2;
            this.length = length2;
        }
    }

    public static final class VorbisIdHeader {
        public final int bitrateMax;
        public final int bitrateMin;
        public final int bitrateNominal;
        public final int blockSize0;
        public final int blockSize1;
        public final int channels;
        public final byte[] data;
        public final boolean framingFlag;
        public final long sampleRate;
        public final long version;

        public VorbisIdHeader(long version2, int channels2, long sampleRate2, int bitrateMax2, int bitrateNominal2, int bitrateMin2, int blockSize02, int blockSize12, boolean framingFlag2, byte[] data2) {
            this.version = version2;
            this.channels = channels2;
            this.sampleRate = sampleRate2;
            this.bitrateMax = bitrateMax2;
            this.bitrateNominal = bitrateNominal2;
            this.bitrateMin = bitrateMin2;
            this.blockSize0 = blockSize02;
            this.blockSize1 = blockSize12;
            this.framingFlag = framingFlag2;
            this.data = data2;
        }

        public int getApproximateBitrate() {
            int i = this.bitrateNominal;
            return i == 0 ? (this.bitrateMin + this.bitrateMax) / 2 : i;
        }
    }

    public static final class Mode {
        public final boolean blockFlag;
        public final int mapping;
        public final int transformType;
        public final int windowType;

        public Mode(boolean blockFlag2, int windowType2, int transformType2, int mapping2) {
            this.blockFlag = blockFlag2;
            this.windowType = windowType2;
            this.transformType = transformType2;
            this.mapping = mapping2;
        }
    }
}
