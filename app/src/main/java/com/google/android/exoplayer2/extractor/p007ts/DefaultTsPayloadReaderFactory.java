package com.google.android.exoplayer2.extractor.p007ts;

import android.util.SparseArray;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.text.cea.Cea708InitializationData;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.common.primitives.SignedBytes;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* renamed from: com.google.android.exoplayer2.extractor.ts.DefaultTsPayloadReaderFactory */
public final class DefaultTsPayloadReaderFactory implements TsPayloadReader.Factory {
    public static final int FLAG_ALLOW_NON_IDR_KEYFRAMES = 1;
    public static final int FLAG_DETECT_ACCESS_UNITS = 8;
    public static final int FLAG_ENABLE_HDMV_DTS_AUDIO_STREAMS = 64;
    public static final int FLAG_IGNORE_AAC_STREAM = 2;
    public static final int FLAG_IGNORE_H264_STREAM = 4;
    public static final int FLAG_IGNORE_SPLICE_INFO_STREAM = 16;
    public static final int FLAG_OVERRIDE_CAPTION_DESCRIPTORS = 32;
    private static final int DESCRIPTOR_TAG_CAPTION_SERVICE = 134;
    private final List<Format> closedCaptionFormats;
    private final int flags;

    public DefaultTsPayloadReaderFactory() {
        this(0);
    }

    public DefaultTsPayloadReaderFactory(int flags2) {
        this(flags2, Collections.singletonList(Format.createTextSampleFormat(null, MimeTypes.APPLICATION_CEA608, 0, null)));
    }

    public DefaultTsPayloadReaderFactory(int flags2, List<Format> closedCaptionFormats2) {
        this.flags = flags2;
        this.closedCaptionFormats = closedCaptionFormats2;
    }

    public SparseArray<TsPayloadReader> createInitialPayloadReaders() {
        return new SparseArray<>();
    }

    public TsPayloadReader createPayloadReader(int streamType, TsPayloadReader.EsInfo esInfo) {
        if (streamType == 2) {
            return new PesReader(new H262Reader(buildUserDataReader(esInfo)));
        }
        if (streamType == 3 || streamType == 4) {
            return new PesReader(new MpegAudioReader(esInfo.language));
        }
        if (streamType != 15) {
            if (streamType != 17) {
                if (streamType == 21) {
                    return new PesReader(new Id3Reader());
                }
                if (streamType != 27) {
                    if (streamType == 36) {
                        return new PesReader(new H265Reader(buildSeiReader(esInfo)));
                    }
                    if (streamType == 89) {
                        return new PesReader(new DvbSubtitleReader(esInfo.dvbSubtitleInfos));
                    }
                    if (streamType != 138) {
                        if (streamType == 172) {
                            return new PesReader(new Ac4Reader(esInfo.language));
                        }
                        if (streamType != 129) {
                            if (streamType != 130) {
                                if (streamType != 134) {
                                    if (streamType != 135) {
                                        return null;
                                    }
                                } else if (isSet(16)) {
                                    return null;
                                } else {
                                    return new SectionReader(new SpliceInfoSectionReader());
                                }
                            } else if (!isSet(64)) {
                                return null;
                            }
                        }
                        return new PesReader(new Ac3Reader(esInfo.language));
                    }
                    return new PesReader(new DtsReader(esInfo.language));
                } else if (isSet(4)) {
                    return null;
                } else {
                    return new PesReader(new H264Reader(buildSeiReader(esInfo), isSet(1), isSet(8)));
                }
            } else if (isSet(2)) {
                return null;
            } else {
                return new PesReader(new LatmReader(esInfo.language));
            }
        } else if (isSet(2)) {
            return null;
        } else {
            return new PesReader(new AdtsReader(false, esInfo.language));
        }
    }

    private SeiReader buildSeiReader(TsPayloadReader.EsInfo esInfo) {
        return new SeiReader(getClosedCaptionFormats(esInfo));
    }

    private UserDataReader buildUserDataReader(TsPayloadReader.EsInfo esInfo) {
        return new UserDataReader(getClosedCaptionFormats(esInfo));
    }

    private List<Format> getClosedCaptionFormats(TsPayloadReader.EsInfo esInfo) {
        int accessibilityChannel;
        String mimeType;
        if (isSet(32)) {
            return this.closedCaptionFormats;
        }
        ParsableByteArray scratchDescriptorData = new ParsableByteArray(esInfo.descriptorBytes);
        List<Format> closedCaptionFormats2 = this.closedCaptionFormats;
        while (scratchDescriptorData.bytesLeft() > 0) {
            int descriptorTag = scratchDescriptorData.readUnsignedByte();
            int nextDescriptorPosition = scratchDescriptorData.getPosition() + scratchDescriptorData.readUnsignedByte();
            if (descriptorTag == 134) {
                closedCaptionFormats2 = new ArrayList<>();
                int numberOfServices = scratchDescriptorData.readUnsignedByte() & 31;
                for (int i = 0; i < numberOfServices; i++) {
                    String language = scratchDescriptorData.readString(3);
                    int captionTypeByte = scratchDescriptorData.readUnsignedByte();
                    boolean isWideAspectRatio = false;
                    boolean isDigital = (captionTypeByte & 128) != 0;
                    if (isDigital) {
                        mimeType = MimeTypes.APPLICATION_CEA708;
                        accessibilityChannel = captionTypeByte & 63;
                    } else {
                        mimeType = MimeTypes.APPLICATION_CEA608;
                        accessibilityChannel = 1;
                    }
                    byte flags2 = (byte) scratchDescriptorData.readUnsignedByte();
                    scratchDescriptorData.skipBytes(1);
                    List<byte[]> initializationData = null;
                    if (isDigital) {
                        if ((flags2 & SignedBytes.MAX_POWER_OF_TWO) != 0) {
                            isWideAspectRatio = true;
                        }
                        initializationData = Cea708InitializationData.buildData(isWideAspectRatio);
                    }
                    closedCaptionFormats2.add(Format.createTextSampleFormat(null, mimeType, null, -1, 0, language, accessibilityChannel, null, Long.MAX_VALUE, initializationData));
                }
            }
            scratchDescriptorData.setPosition(nextDescriptorPosition);
        }
        return closedCaptionFormats2;
    }

    private boolean isSet(int flag) {
        return (this.flags & flag) != 0;
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: com.google.android.exoplayer2.extractor.ts.DefaultTsPayloadReaderFactory$Flags */
    public @interface Flags {
    }
}
