package com.google.android.exoplayer2.video;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;

import java.util.ArrayList;
import java.util.List;

public final class AvcConfig {
    public final int height;
    public final List<byte[]> initializationData;
    public final int nalUnitLengthFieldLength;
    public final float pixelWidthAspectRatio;
    public final int width;

    private AvcConfig(List<byte[]> initializationData2, int nalUnitLengthFieldLength2, int width2, int height2, float pixelWidthAspectRatio2) {
        this.initializationData = initializationData2;
        this.nalUnitLengthFieldLength = nalUnitLengthFieldLength2;
        this.width = width2;
        this.height = height2;
        this.pixelWidthAspectRatio = pixelWidthAspectRatio2;
    }

    public static AvcConfig parse(ParsableByteArray data) throws ParserException {
        float pixelWidthAspectRatio2;
        int height2;
        int width2;
        try {
            data.skipBytes(4);
            int nalUnitLengthFieldLength2 = (data.readUnsignedByte() & 3) + 1;
            if (nalUnitLengthFieldLength2 != 3) {
                List<byte[]> initializationData2 = new ArrayList<>();
                int numSequenceParameterSets = data.readUnsignedByte() & 31;
                for (int j = 0; j < numSequenceParameterSets; j++) {
                    initializationData2.add(buildNalUnitForChild(data));
                }
                int numPictureParameterSets = data.readUnsignedByte();
                for (int j2 = 0; j2 < numPictureParameterSets; j2++) {
                    initializationData2.add(buildNalUnitForChild(data));
                }
                if (numSequenceParameterSets > 0) {
                    NalUnitUtil.SpsData spsData = NalUnitUtil.parseSpsNalUnit((byte[]) initializationData2.get(0), nalUnitLengthFieldLength2, ((byte[]) initializationData2.get(0)).length);
                    width2 = spsData.width;
                    height2 = spsData.height;
                    pixelWidthAspectRatio2 = spsData.pixelWidthAspectRatio;
                } else {
                    width2 = -1;
                    height2 = -1;
                    pixelWidthAspectRatio2 = 1.0f;
                }
                return new AvcConfig(initializationData2, nalUnitLengthFieldLength2, width2, height2, pixelWidthAspectRatio2);
            }
            throw new IllegalStateException();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParserException("Error parsing AVC config", e);
        }
    }

    private static byte[] buildNalUnitForChild(ParsableByteArray data) {
        int length = data.readUnsignedShort();
        int offset = data.getPosition();
        data.skipBytes(length);
        return CodecSpecificDataUtil.buildNalUnit(data.data, offset, length);
    }
}
