package com.google.android.exoplayer2.extractor.p007ts;

import android.util.Pair;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;

import java.util.Arrays;
import java.util.Collections;

/* renamed from: com.google.android.exoplayer2.extractor.ts.H262Reader */
public final class H262Reader implements ElementaryStreamReader {
    private static final double[] FRAME_RATE_VALUES = {23.976023976023978d, 24.0d, 25.0d, 29.97002997002997d, 30.0d, 50.0d, 59.94005994005994d, 60.0d};
    private static final int START_EXTENSION = 181;
    private static final int START_GROUP = 184;
    private static final int START_PICTURE = 0;
    private static final int START_SEQUENCE_HEADER = 179;
    private static final int START_USER_DATA = 178;
    private final CsdBuffer csdBuffer;
    private final boolean[] prefixFlags;
    private final NalUnitTargetBuffer userData;
    private final ParsableByteArray userDataParsable;
    private final UserDataReader userDataReader;
    private String formatId;
    private long frameDurationUs;
    private boolean hasOutputFormat;
    private TrackOutput output;
    private long pesTimeUs;
    private boolean sampleHasPicture;
    private boolean sampleIsKeyframe;
    private long samplePosition;
    private long sampleTimeUs;
    private boolean startedFirstSample;
    private long totalBytesWritten;

    public H262Reader() {
        this(null);
    }

    public H262Reader(UserDataReader userDataReader2) {
        this.userDataReader = userDataReader2;
        this.prefixFlags = new boolean[4];
        this.csdBuffer = new CsdBuffer(128);
        if (userDataReader2 != null) {
            this.userData = new NalUnitTargetBuffer(178, 128);
            this.userDataParsable = new ParsableByteArray();
            return;
        }
        this.userData = null;
        this.userDataParsable = null;
    }

    private static Pair<Format, Long> parseCsdBuffer(CsdBuffer csdBuffer2, String formatId2) {
        float pixelWidthHeightRatio;
        CsdBuffer csdBuffer3 = csdBuffer2;
        byte[] csdData = Arrays.copyOf(csdBuffer3.data, csdBuffer3.length);
        byte b = csdData[4] & UnsignedBytes.MAX_VALUE;
        int secondByte = csdData[5] & 255;
        int width = (b << 4) | (secondByte >> 4);
        int height = ((secondByte & 15) << 8) | (csdData[6] & 255);
        int aspectRatioCode = (csdData[7] & 240) >> 4;
        if (aspectRatioCode == 2) {
            pixelWidthHeightRatio = ((float) (height * 4)) / ((float) (width * 3));
        } else if (aspectRatioCode == 3) {
            pixelWidthHeightRatio = ((float) (height * 16)) / ((float) (width * 9));
        } else if (aspectRatioCode != 4) {
            pixelWidthHeightRatio = 1.0f;
        } else {
            pixelWidthHeightRatio = ((float) (height * 121)) / ((float) (width * 100));
        }
        Format format = Format.createVideoSampleFormat(formatId2, MimeTypes.VIDEO_MPEG2, null, -1, -1, width, height, -1.0f, Collections.singletonList(csdData), -1, pixelWidthHeightRatio, null);
        long frameDurationUs2 = 0;
        int frameRateCodeMinusOne = (csdData[7] & Ascii.f156SI) - 1;
        if (frameRateCodeMinusOne >= 0) {
            double[] dArr = FRAME_RATE_VALUES;
            if (frameRateCodeMinusOne < dArr.length) {
                double frameRate = dArr[frameRateCodeMinusOne];
                int sequenceExtensionPosition = csdBuffer3.sequenceExtensionPosition;
                int frameRateExtensionN = (csdData[sequenceExtensionPosition + 9] & 96) >> 5;
                int frameRateExtensionD = csdData[sequenceExtensionPosition + 9] & 31;
                if (frameRateExtensionN != frameRateExtensionD) {
                    double d = (double) frameRateExtensionN;
                    Double.isNaN(d);
                    double d2 = (double) (frameRateExtensionD + 1);
                    Double.isNaN(d2);
                    frameRate *= (d + 1.0d) / d2;
                }
                frameDurationUs2 = (long) (1000000.0d / frameRate);
                return Pair.create(format, Long.valueOf(frameDurationUs2));
            }
        }
        return Pair.create(format, Long.valueOf(frameDurationUs2));
    }

    public void seek() {
        NalUnitUtil.clearPrefixFlags(this.prefixFlags);
        this.csdBuffer.reset();
        if (this.userDataReader != null) {
            this.userData.reset();
        }
        this.totalBytesWritten = 0;
        this.startedFirstSample = false;
    }

    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator idGenerator) {
        idGenerator.generateNewId();
        this.formatId = idGenerator.getFormatId();
        this.output = extractorOutput.track(idGenerator.getTrackId(), 2);
        UserDataReader userDataReader2 = this.userDataReader;
        if (userDataReader2 != null) {
            userDataReader2.createTracks(extractorOutput, idGenerator);
        }
    }

    public void packetStarted(long pesTimeUs2, int flags) {
        this.pesTimeUs = pesTimeUs2;
    }

    public void consume(ParsableByteArray data) {
        boolean z;
        ParsableByteArray parsableByteArray = data;
        int offset = data.getPosition();
        int limit = data.limit();
        byte[] dataArray = parsableByteArray.data;
        this.totalBytesWritten += (long) data.bytesLeft();
        this.output.sampleData(parsableByteArray, data.bytesLeft());
        while (true) {
            int startCodeOffset = NalUnitUtil.findNalUnit(dataArray, offset, limit, this.prefixFlags);
            if (startCodeOffset == limit) {
                break;
            }
            int startCodeValue = parsableByteArray.data[startCodeOffset + 3] & 255;
            int lengthToStartCode = startCodeOffset - offset;
            if (!this.hasOutputFormat) {
                if (lengthToStartCode > 0) {
                    this.csdBuffer.onData(dataArray, offset, startCodeOffset);
                }
                if (this.csdBuffer.onStartCode(startCodeValue, lengthToStartCode < 0 ? -lengthToStartCode : 0)) {
                    Pair<Format, Long> result = parseCsdBuffer(this.csdBuffer, this.formatId);
                    this.output.format((Format) result.first);
                    this.frameDurationUs = ((Long) result.second).longValue();
                    this.hasOutputFormat = true;
                }
            }
            if (this.userDataReader != null) {
                int bytesAlreadyPassed = 0;
                if (lengthToStartCode > 0) {
                    this.userData.appendToNalUnit(dataArray, offset, startCodeOffset);
                } else {
                    bytesAlreadyPassed = -lengthToStartCode;
                }
                if (this.userData.endNalUnit(bytesAlreadyPassed)) {
                    this.userDataParsable.reset(this.userData.nalData, NalUnitUtil.unescapeStream(this.userData.nalData, this.userData.nalLength));
                    this.userDataReader.consume(this.sampleTimeUs, this.userDataParsable);
                }
                if (startCodeValue == 178 && parsableByteArray.data[startCodeOffset + 2] == 1) {
                    this.userData.startNalUnit(startCodeValue);
                }
            }
            if (startCodeValue == 0 || startCodeValue == 179) {
                int bytesWrittenPastStartCode = limit - startCodeOffset;
                if (this.startedFirstSample && this.sampleHasPicture && this.hasOutputFormat) {
                    this.output.sampleMetadata(this.sampleTimeUs, (int) this.sampleIsKeyframe, ((int) (this.totalBytesWritten - this.samplePosition)) - bytesWrittenPastStartCode, bytesWrittenPastStartCode, null);
                }
                if (this.startedFirstSample == 0 || this.sampleHasPicture) {
                    this.samplePosition = this.totalBytesWritten - ((long) bytesWrittenPastStartCode);
                    long j = this.pesTimeUs;
                    if (j == C0841C.TIME_UNSET) {
                        j = this.startedFirstSample ? this.sampleTimeUs + this.frameDurationUs : 0;
                    }
                    this.sampleTimeUs = j;
                    z = false;
                    this.sampleIsKeyframe = false;
                    this.pesTimeUs = C0841C.TIME_UNSET;
                    this.startedFirstSample = true;
                } else {
                    z = false;
                }
                if (startCodeValue == 0) {
                    z = true;
                }
                this.sampleHasPicture = z;
            } else if (startCodeValue == 184) {
                this.sampleIsKeyframe = true;
            }
            offset = startCodeOffset + 3;
        }
        if (!this.hasOutputFormat) {
            this.csdBuffer.onData(dataArray, offset, limit);
        }
        if (this.userDataReader != null) {
            this.userData.appendToNalUnit(dataArray, offset, limit);
        }
    }

    public void packetFinished() {
    }

    /* renamed from: com.google.android.exoplayer2.extractor.ts.H262Reader$CsdBuffer */
    private static final class CsdBuffer {
        private static final byte[] START_CODE = {0, 0, 1};
        public byte[] data;
        public int length;
        public int sequenceExtensionPosition;
        private boolean isFilling;

        public CsdBuffer(int initialCapacity) {
            this.data = new byte[initialCapacity];
        }

        public void reset() {
            this.isFilling = false;
            this.length = 0;
            this.sequenceExtensionPosition = 0;
        }

        public boolean onStartCode(int startCodeValue, int bytesAlreadyPassed) {
            if (this.isFilling) {
                this.length -= bytesAlreadyPassed;
                if (this.sequenceExtensionPosition == 0 && startCodeValue == 181) {
                    this.sequenceExtensionPosition = this.length;
                } else {
                    this.isFilling = false;
                    return true;
                }
            } else if (startCodeValue == 179) {
                this.isFilling = true;
            }
            byte[] bArr = START_CODE;
            onData(bArr, 0, bArr.length);
            return false;
        }

        public void onData(byte[] newData, int offset, int limit) {
            if (this.isFilling) {
                int readLength = limit - offset;
                byte[] bArr = this.data;
                int length2 = bArr.length;
                int i = this.length;
                if (length2 < i + readLength) {
                    this.data = Arrays.copyOf(bArr, (i + readLength) * 2);
                }
                System.arraycopy(newData, offset, this.data, this.length, readLength);
                this.length += readLength;
            }
        }
    }
}
