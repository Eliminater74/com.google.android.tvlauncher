package com.google.android.exoplayer2;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.ColorInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Format implements Parcelable {
    public static final Parcelable.Creator<Format> CREATOR = new Parcelable.Creator<Format>() {
        public Format createFromParcel(Parcel in) {
            return new Format(in);
        }

        public Format[] newArray(int size) {
            return new Format[size];
        }
    };
    public static final int NO_VALUE = -1;
    public static final long OFFSET_SAMPLE_RELATIVE = Long.MAX_VALUE;
    public final int accessibilityChannel;
    public final int bitrate;
    public final int channelCount;
    @Nullable
    public final String codecs;
    @Nullable
    public final ColorInfo colorInfo;
    @Nullable
    public final String containerMimeType;
    @Nullable
    public final DrmInitData drmInitData;
    public final int encoderDelay;
    public final int encoderPadding;
    public final float frameRate;
    private int hashCode;
    public final int height;
    @Nullable

    /* renamed from: id */
    public final String f72id;
    public final List<byte[]> initializationData;
    @Nullable
    public final String label;
    @Nullable
    public final String language;
    public final int maxInputSize;
    @Nullable
    public final Metadata metadata;
    public final int pcmEncoding;
    public final float pixelWidthHeightRatio;
    @Nullable
    public final byte[] projectionData;
    public final int roleFlags;
    public final int rotationDegrees;
    @Nullable
    public final String sampleMimeType;
    public final int sampleRate;
    public final int selectionFlags;
    public final int stereoMode;
    public final long subsampleOffsetUs;
    public final int width;

    @Deprecated
    public static Format createVideoContainerFormat(@Nullable String id, @Nullable String containerMimeType2, String sampleMimeType2, String codecs2, int bitrate2, int width2, int height2, float frameRate2, @Nullable List<byte[]> initializationData2, int selectionFlags2) {
        return createVideoContainerFormat(id, null, containerMimeType2, sampleMimeType2, codecs2, bitrate2, width2, height2, frameRate2, initializationData2, selectionFlags2, 0);
    }

    public static Format createVideoContainerFormat(@Nullable String id, @Nullable String label2, @Nullable String containerMimeType2, String sampleMimeType2, String codecs2, int bitrate2, int width2, int height2, float frameRate2, @Nullable List<byte[]> initializationData2, int selectionFlags2, int roleFlags2) {
        return new Format(id, label2, selectionFlags2, roleFlags2, bitrate2, codecs2, null, containerMimeType2, sampleMimeType2, -1, initializationData2, null, Long.MAX_VALUE, width2, height2, frameRate2, -1, -1.0f, null, -1, null, -1, -1, -1, -1, -1, null, -1);
    }

    public static Format createVideoSampleFormat(@Nullable String id, @Nullable String sampleMimeType2, @Nullable String codecs2, int bitrate2, int maxInputSize2, int width2, int height2, float frameRate2, @Nullable List<byte[]> initializationData2, @Nullable DrmInitData drmInitData2) {
        return createVideoSampleFormat(id, sampleMimeType2, codecs2, bitrate2, maxInputSize2, width2, height2, frameRate2, initializationData2, -1, -1.0f, drmInitData2);
    }

    public static Format createVideoSampleFormat(@Nullable String id, @Nullable String sampleMimeType2, @Nullable String codecs2, int bitrate2, int maxInputSize2, int width2, int height2, float frameRate2, @Nullable List<byte[]> initializationData2, int rotationDegrees2, float pixelWidthHeightRatio2, @Nullable DrmInitData drmInitData2) {
        return createVideoSampleFormat(id, sampleMimeType2, codecs2, bitrate2, maxInputSize2, width2, height2, frameRate2, initializationData2, rotationDegrees2, pixelWidthHeightRatio2, null, -1, null, drmInitData2);
    }

    public static Format createVideoSampleFormat(@Nullable String id, @Nullable String sampleMimeType2, @Nullable String codecs2, int bitrate2, int maxInputSize2, int width2, int height2, float frameRate2, @Nullable List<byte[]> initializationData2, int rotationDegrees2, float pixelWidthHeightRatio2, byte[] projectionData2, int stereoMode2, @Nullable ColorInfo colorInfo2, @Nullable DrmInitData drmInitData2) {
        return new Format(id, null, 0, 0, bitrate2, codecs2, null, null, sampleMimeType2, maxInputSize2, initializationData2, drmInitData2, Long.MAX_VALUE, width2, height2, frameRate2, rotationDegrees2, pixelWidthHeightRatio2, projectionData2, stereoMode2, colorInfo2, -1, -1, -1, -1, -1, null, -1);
    }

    @Deprecated
    public static Format createAudioContainerFormat(@Nullable String id, @Nullable String containerMimeType2, @Nullable String sampleMimeType2, @Nullable String codecs2, int bitrate2, int channelCount2, int sampleRate2, @Nullable List<byte[]> initializationData2, int selectionFlags2, @Nullable String language2) {
        return createAudioContainerFormat(id, null, containerMimeType2, sampleMimeType2, codecs2, bitrate2, channelCount2, sampleRate2, initializationData2, selectionFlags2, 0, language2);
    }

    public static Format createAudioContainerFormat(@Nullable String id, @Nullable String label2, @Nullable String containerMimeType2, @Nullable String sampleMimeType2, @Nullable String codecs2, int bitrate2, int channelCount2, int sampleRate2, @Nullable List<byte[]> initializationData2, int selectionFlags2, int roleFlags2, @Nullable String language2) {
        return new Format(id, label2, selectionFlags2, roleFlags2, bitrate2, codecs2, null, containerMimeType2, sampleMimeType2, -1, initializationData2, null, Long.MAX_VALUE, -1, -1, -1.0f, -1, -1.0f, null, -1, null, channelCount2, sampleRate2, -1, -1, -1, language2, -1);
    }

    public static Format createAudioSampleFormat(@Nullable String id, @Nullable String sampleMimeType2, @Nullable String codecs2, int bitrate2, int maxInputSize2, int channelCount2, int sampleRate2, @Nullable List<byte[]> initializationData2, @Nullable DrmInitData drmInitData2, int selectionFlags2, @Nullable String language2) {
        return createAudioSampleFormat(id, sampleMimeType2, codecs2, bitrate2, maxInputSize2, channelCount2, sampleRate2, -1, initializationData2, drmInitData2, selectionFlags2, language2);
    }

    public static Format createAudioSampleFormat(@Nullable String id, @Nullable String sampleMimeType2, @Nullable String codecs2, int bitrate2, int maxInputSize2, int channelCount2, int sampleRate2, int pcmEncoding2, @Nullable List<byte[]> initializationData2, @Nullable DrmInitData drmInitData2, int selectionFlags2, @Nullable String language2) {
        return createAudioSampleFormat(id, sampleMimeType2, codecs2, bitrate2, maxInputSize2, channelCount2, sampleRate2, pcmEncoding2, -1, -1, initializationData2, drmInitData2, selectionFlags2, language2, null);
    }

    public static Format createAudioSampleFormat(@Nullable String id, @Nullable String sampleMimeType2, @Nullable String codecs2, int bitrate2, int maxInputSize2, int channelCount2, int sampleRate2, int pcmEncoding2, int encoderDelay2, int encoderPadding2, @Nullable List<byte[]> initializationData2, @Nullable DrmInitData drmInitData2, int selectionFlags2, @Nullable String language2, @Nullable Metadata metadata2) {
        return new Format(id, null, selectionFlags2, 0, bitrate2, codecs2, metadata2, null, sampleMimeType2, maxInputSize2, initializationData2, drmInitData2, Long.MAX_VALUE, -1, -1, -1.0f, -1, -1.0f, null, -1, null, channelCount2, sampleRate2, pcmEncoding2, encoderDelay2, encoderPadding2, language2, -1);
    }

    public static Format createTextContainerFormat(@Nullable String id, @Nullable String label2, @Nullable String containerMimeType2, @Nullable String sampleMimeType2, @Nullable String codecs2, int bitrate2, int selectionFlags2, int roleFlags2, @Nullable String language2) {
        return createTextContainerFormat(id, label2, containerMimeType2, sampleMimeType2, codecs2, bitrate2, selectionFlags2, roleFlags2, language2, -1);
    }

    public static Format createTextContainerFormat(@Nullable String id, @Nullable String label2, @Nullable String containerMimeType2, @Nullable String sampleMimeType2, @Nullable String codecs2, int bitrate2, int selectionFlags2, int roleFlags2, @Nullable String language2, int accessibilityChannel2) {
        return new Format(id, label2, selectionFlags2, roleFlags2, bitrate2, codecs2, null, containerMimeType2, sampleMimeType2, -1, null, null, Long.MAX_VALUE, -1, -1, -1.0f, -1, -1.0f, null, -1, null, -1, -1, -1, -1, -1, language2, accessibilityChannel2);
    }

    public static Format createTextSampleFormat(@Nullable String id, String sampleMimeType2, int selectionFlags2, @Nullable String language2) {
        return createTextSampleFormat(id, sampleMimeType2, selectionFlags2, language2, null);
    }

    public static Format createTextSampleFormat(@Nullable String id, String sampleMimeType2, int selectionFlags2, @Nullable String language2, @Nullable DrmInitData drmInitData2) {
        return createTextSampleFormat(id, sampleMimeType2, null, -1, selectionFlags2, language2, -1, drmInitData2, Long.MAX_VALUE, Collections.emptyList());
    }

    public static Format createTextSampleFormat(@Nullable String id, @Nullable String sampleMimeType2, @Nullable String codecs2, int bitrate2, int selectionFlags2, @Nullable String language2, int accessibilityChannel2, @Nullable DrmInitData drmInitData2) {
        return createTextSampleFormat(id, sampleMimeType2, codecs2, bitrate2, selectionFlags2, language2, accessibilityChannel2, drmInitData2, Long.MAX_VALUE, Collections.emptyList());
    }

    public static Format createTextSampleFormat(@Nullable String id, @Nullable String sampleMimeType2, @Nullable String codecs2, int bitrate2, int selectionFlags2, @Nullable String language2, @Nullable DrmInitData drmInitData2, long subsampleOffsetUs2) {
        return createTextSampleFormat(id, sampleMimeType2, codecs2, bitrate2, selectionFlags2, language2, -1, drmInitData2, subsampleOffsetUs2, Collections.emptyList());
    }

    public static Format createTextSampleFormat(@Nullable String id, @Nullable String sampleMimeType2, @Nullable String codecs2, int bitrate2, int selectionFlags2, @Nullable String language2, int accessibilityChannel2, @Nullable DrmInitData drmInitData2, long subsampleOffsetUs2, List<byte[]> initializationData2) {
        return new Format(id, null, selectionFlags2, 0, bitrate2, codecs2, null, null, sampleMimeType2, -1, initializationData2, drmInitData2, subsampleOffsetUs2, -1, -1, -1.0f, -1, -1.0f, null, -1, null, -1, -1, -1, -1, -1, language2, accessibilityChannel2);
    }

    public static Format createImageSampleFormat(@Nullable String id, @Nullable String sampleMimeType2, @Nullable String codecs2, int bitrate2, int selectionFlags2, @Nullable List<byte[]> initializationData2, @Nullable String language2, @Nullable DrmInitData drmInitData2) {
        return new Format(id, null, selectionFlags2, 0, bitrate2, codecs2, null, null, sampleMimeType2, -1, initializationData2, drmInitData2, Long.MAX_VALUE, -1, -1, -1.0f, -1, -1.0f, null, -1, null, -1, -1, -1, -1, -1, language2, -1);
    }

    @Deprecated
    public static Format createContainerFormat(@Nullable String id, @Nullable String containerMimeType2, @Nullable String sampleMimeType2, @Nullable String codecs2, int bitrate2, int selectionFlags2, @Nullable String language2) {
        return createContainerFormat(id, null, containerMimeType2, sampleMimeType2, codecs2, bitrate2, selectionFlags2, 0, language2);
    }

    public static Format createContainerFormat(@Nullable String id, @Nullable String label2, @Nullable String containerMimeType2, @Nullable String sampleMimeType2, @Nullable String codecs2, int bitrate2, int selectionFlags2, int roleFlags2, @Nullable String language2) {
        return new Format(id, label2, selectionFlags2, roleFlags2, bitrate2, codecs2, null, containerMimeType2, sampleMimeType2, -1, null, null, Long.MAX_VALUE, -1, -1, -1.0f, -1, -1.0f, null, -1, null, -1, -1, -1, -1, -1, language2, -1);
    }

    public static Format createSampleFormat(@Nullable String id, @Nullable String sampleMimeType2, long subsampleOffsetUs2) {
        return new Format(id, null, 0, 0, -1, null, null, null, sampleMimeType2, -1, null, null, subsampleOffsetUs2, -1, -1, -1.0f, -1, -1.0f, null, -1, null, -1, -1, -1, -1, -1, null, -1);
    }

    public static Format createSampleFormat(@Nullable String id, @Nullable String sampleMimeType2, @Nullable String codecs2, int bitrate2, @Nullable DrmInitData drmInitData2) {
        return new Format(id, null, 0, 0, bitrate2, codecs2, null, null, sampleMimeType2, -1, null, drmInitData2, Long.MAX_VALUE, -1, -1, -1.0f, -1, -1.0f, null, -1, null, -1, -1, -1, -1, -1, null, -1);
    }

    Format(@Nullable String id, @Nullable String label2, int selectionFlags2, int roleFlags2, int bitrate2, @Nullable String codecs2, @Nullable Metadata metadata2, @Nullable String containerMimeType2, @Nullable String sampleMimeType2, int maxInputSize2, @Nullable List<byte[]> initializationData2, @Nullable DrmInitData drmInitData2, long subsampleOffsetUs2, int width2, int height2, float frameRate2, int rotationDegrees2, float pixelWidthHeightRatio2, @Nullable byte[] projectionData2, int stereoMode2, @Nullable ColorInfo colorInfo2, int channelCount2, int sampleRate2, int pcmEncoding2, int encoderDelay2, int encoderPadding2, @Nullable String language2, int accessibilityChannel2) {
        this.f72id = id;
        this.label = label2;
        this.selectionFlags = selectionFlags2;
        this.roleFlags = roleFlags2;
        this.bitrate = bitrate2;
        this.codecs = codecs2;
        this.metadata = metadata2;
        this.containerMimeType = containerMimeType2;
        this.sampleMimeType = sampleMimeType2;
        this.maxInputSize = maxInputSize2;
        this.initializationData = initializationData2 == null ? Collections.emptyList() : initializationData2;
        this.drmInitData = drmInitData2;
        this.subsampleOffsetUs = subsampleOffsetUs2;
        this.width = width2;
        this.height = height2;
        this.frameRate = frameRate2;
        int i = rotationDegrees2;
        this.rotationDegrees = i == -1 ? 0 : i;
        this.pixelWidthHeightRatio = pixelWidthHeightRatio2 == -1.0f ? 1.0f : pixelWidthHeightRatio2;
        this.projectionData = projectionData2;
        this.stereoMode = stereoMode2;
        this.colorInfo = colorInfo2;
        this.channelCount = channelCount2;
        this.sampleRate = sampleRate2;
        this.pcmEncoding = pcmEncoding2;
        int i2 = encoderDelay2;
        this.encoderDelay = i2 == -1 ? 0 : i2;
        int i3 = encoderPadding2;
        this.encoderPadding = i3 == -1 ? 0 : i3;
        this.language = Util.normalizeLanguageCode(language2);
        this.accessibilityChannel = accessibilityChannel2;
    }

    Format(Parcel in) {
        this.f72id = in.readString();
        this.label = in.readString();
        this.selectionFlags = in.readInt();
        this.roleFlags = in.readInt();
        this.bitrate = in.readInt();
        this.codecs = in.readString();
        this.metadata = (Metadata) in.readParcelable(Metadata.class.getClassLoader());
        this.containerMimeType = in.readString();
        this.sampleMimeType = in.readString();
        this.maxInputSize = in.readInt();
        int initializationDataSize = in.readInt();
        this.initializationData = new ArrayList(initializationDataSize);
        for (int i = 0; i < initializationDataSize; i++) {
            this.initializationData.add(in.createByteArray());
        }
        this.drmInitData = (DrmInitData) in.readParcelable(DrmInitData.class.getClassLoader());
        this.subsampleOffsetUs = in.readLong();
        this.width = in.readInt();
        this.height = in.readInt();
        this.frameRate = in.readFloat();
        this.rotationDegrees = in.readInt();
        this.pixelWidthHeightRatio = in.readFloat();
        this.projectionData = Util.readBoolean(in) ? in.createByteArray() : null;
        this.stereoMode = in.readInt();
        this.colorInfo = (ColorInfo) in.readParcelable(ColorInfo.class.getClassLoader());
        this.channelCount = in.readInt();
        this.sampleRate = in.readInt();
        this.pcmEncoding = in.readInt();
        this.encoderDelay = in.readInt();
        this.encoderPadding = in.readInt();
        this.language = in.readString();
        this.accessibilityChannel = in.readInt();
    }

    public Format copyWithMaxInputSize(int maxInputSize2) {
        return new Format(this.f72id, this.label, this.selectionFlags, this.roleFlags, this.bitrate, this.codecs, this.metadata, this.containerMimeType, this.sampleMimeType, maxInputSize2, this.initializationData, this.drmInitData, this.subsampleOffsetUs, this.width, this.height, this.frameRate, this.rotationDegrees, this.pixelWidthHeightRatio, this.projectionData, this.stereoMode, this.colorInfo, this.channelCount, this.sampleRate, this.pcmEncoding, this.encoderDelay, this.encoderPadding, this.language, this.accessibilityChannel);
    }

    public Format copyWithSubsampleOffsetUs(long subsampleOffsetUs2) {
        return new Format(this.f72id, this.label, this.selectionFlags, this.roleFlags, this.bitrate, this.codecs, this.metadata, this.containerMimeType, this.sampleMimeType, this.maxInputSize, this.initializationData, this.drmInitData, subsampleOffsetUs2, this.width, this.height, this.frameRate, this.rotationDegrees, this.pixelWidthHeightRatio, this.projectionData, this.stereoMode, this.colorInfo, this.channelCount, this.sampleRate, this.pcmEncoding, this.encoderDelay, this.encoderPadding, this.language, this.accessibilityChannel);
    }

    public Format copyWithContainerInfo(@Nullable String id, @Nullable String label2, @Nullable String sampleMimeType2, @Nullable String codecs2, @Nullable Metadata metadata2, int bitrate2, int width2, int height2, int channelCount2, int selectionFlags2, @Nullable String language2) {
        Metadata metadata3;
        Metadata metadata4 = this.metadata;
        if (metadata4 != null) {
            metadata3 = metadata4.copyWithAppendedEntriesFrom(metadata2);
        } else {
            metadata3 = metadata2;
        }
        return new Format(id, label2, selectionFlags2, this.roleFlags, bitrate2, codecs2, metadata3, this.containerMimeType, sampleMimeType2, this.maxInputSize, this.initializationData, this.drmInitData, this.subsampleOffsetUs, width2, height2, this.frameRate, this.rotationDegrees, this.pixelWidthHeightRatio, this.projectionData, this.stereoMode, this.colorInfo, channelCount2, this.sampleRate, this.pcmEncoding, this.encoderDelay, this.encoderPadding, language2, this.accessibilityChannel);
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0051  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.exoplayer2.Format copyWithManifestFormatInfo(com.google.android.exoplayer2.Format r42) {
        /*
            r41 = this;
            r0 = r41
            r1 = r42
            if (r0 != r1) goto L_0x0007
            return r0
        L_0x0007:
            java.lang.String r2 = r0.sampleMimeType
            int r2 = com.google.android.exoplayer2.util.MimeTypes.getTrackType(r2)
            java.lang.String r9 = r1.f72id
            java.lang.String r3 = r1.label
            if (r3 == 0) goto L_0x0014
            goto L_0x0016
        L_0x0014:
            java.lang.String r3 = r0.label
        L_0x0016:
            r5 = r3
            java.lang.String r3 = r0.language
            r4 = 3
            r6 = 1
            if (r2 == r4) goto L_0x001f
            if (r2 != r6) goto L_0x0028
        L_0x001f:
            java.lang.String r4 = r1.language
            if (r4 == 0) goto L_0x0028
            java.lang.String r3 = r1.language
            r33 = r3
            goto L_0x002a
        L_0x0028:
            r33 = r3
        L_0x002a:
            int r3 = r0.bitrate
            r4 = -1
            if (r3 != r4) goto L_0x0031
            int r3 = r1.bitrate
        L_0x0031:
            r8 = r3
            java.lang.String r3 = r0.codecs
            if (r3 != 0) goto L_0x0047
            java.lang.String r4 = r1.codecs
            java.lang.String r4 = com.google.android.exoplayer2.util.Util.getCodecsOfType(r4, r2)
            java.lang.String[] r7 = com.google.android.exoplayer2.util.Util.splitCodecs(r4)
            int r7 = r7.length
            if (r7 != r6) goto L_0x0047
            r3 = r4
            r34 = r3
            goto L_0x0049
        L_0x0047:
            r34 = r3
        L_0x0049:
            com.google.android.exoplayer2.metadata.Metadata r3 = r0.metadata
            if (r3 != 0) goto L_0x0051
            com.google.android.exoplayer2.metadata.Metadata r3 = r1.metadata
            r10 = r3
            goto L_0x0058
        L_0x0051:
            com.google.android.exoplayer2.metadata.Metadata r4 = r1.metadata
            com.google.android.exoplayer2.metadata.Metadata r3 = r3.copyWithAppendedEntriesFrom(r4)
            r10 = r3
        L_0x0058:
            float r3 = r0.frameRate
            r4 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r4 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r4 != 0) goto L_0x0069
            r4 = 2
            if (r2 != r4) goto L_0x0069
            float r3 = r1.frameRate
            r35 = r3
            goto L_0x006b
        L_0x0069:
            r35 = r3
        L_0x006b:
            int r3 = r0.selectionFlags
            int r4 = r1.selectionFlags
            r36 = r3 | r4
            r6 = r36
            int r3 = r0.roleFlags
            int r4 = r1.roleFlags
            r37 = r3 | r4
            r7 = r37
            com.google.android.exoplayer2.drm.DrmInitData r3 = r1.drmInitData
            com.google.android.exoplayer2.drm.DrmInitData r4 = r0.drmInitData
            com.google.android.exoplayer2.drm.DrmInitData r38 = com.google.android.exoplayer2.drm.DrmInitData.createSessionCreationData(r3, r4)
            r15 = r38
            com.google.android.exoplayer2.Format r39 = new com.google.android.exoplayer2.Format
            r3 = r39
            java.lang.String r11 = r0.containerMimeType
            java.lang.String r12 = r0.sampleMimeType
            int r13 = r0.maxInputSize
            java.util.List<byte[]> r14 = r0.initializationData
            r40 = r2
            long r1 = r0.subsampleOffsetUs
            r16 = r1
            int r1 = r0.width
            r18 = r1
            int r1 = r0.height
            r19 = r1
            int r1 = r0.rotationDegrees
            r21 = r1
            float r1 = r0.pixelWidthHeightRatio
            r22 = r1
            byte[] r1 = r0.projectionData
            r23 = r1
            int r1 = r0.stereoMode
            r24 = r1
            com.google.android.exoplayer2.video.ColorInfo r1 = r0.colorInfo
            r25 = r1
            int r1 = r0.channelCount
            r26 = r1
            int r1 = r0.sampleRate
            r27 = r1
            int r1 = r0.pcmEncoding
            r28 = r1
            int r1 = r0.encoderDelay
            r29 = r1
            int r1 = r0.encoderPadding
            r30 = r1
            int r1 = r0.accessibilityChannel
            r32 = r1
            r4 = r9
            r1 = r9
            r9 = r34
            r20 = r35
            r31 = r33
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32)
            return r39
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.Format.copyWithManifestFormatInfo(com.google.android.exoplayer2.Format):com.google.android.exoplayer2.Format");
    }

    public Format copyWithGaplessInfo(int encoderDelay2, int encoderPadding2) {
        return new Format(this.f72id, this.label, this.selectionFlags, this.roleFlags, this.bitrate, this.codecs, this.metadata, this.containerMimeType, this.sampleMimeType, this.maxInputSize, this.initializationData, this.drmInitData, this.subsampleOffsetUs, this.width, this.height, this.frameRate, this.rotationDegrees, this.pixelWidthHeightRatio, this.projectionData, this.stereoMode, this.colorInfo, this.channelCount, this.sampleRate, this.pcmEncoding, encoderDelay2, encoderPadding2, this.language, this.accessibilityChannel);
    }

    public Format copyWithFrameRate(float frameRate2) {
        return new Format(this.f72id, this.label, this.selectionFlags, this.roleFlags, this.bitrate, this.codecs, this.metadata, this.containerMimeType, this.sampleMimeType, this.maxInputSize, this.initializationData, this.drmInitData, this.subsampleOffsetUs, this.width, this.height, frameRate2, this.rotationDegrees, this.pixelWidthHeightRatio, this.projectionData, this.stereoMode, this.colorInfo, this.channelCount, this.sampleRate, this.pcmEncoding, this.encoderDelay, this.encoderPadding, this.language, this.accessibilityChannel);
    }

    public Format copyWithDrmInitData(@Nullable DrmInitData drmInitData2) {
        return new Format(this.f72id, this.label, this.selectionFlags, this.roleFlags, this.bitrate, this.codecs, this.metadata, this.containerMimeType, this.sampleMimeType, this.maxInputSize, this.initializationData, drmInitData2, this.subsampleOffsetUs, this.width, this.height, this.frameRate, this.rotationDegrees, this.pixelWidthHeightRatio, this.projectionData, this.stereoMode, this.colorInfo, this.channelCount, this.sampleRate, this.pcmEncoding, this.encoderDelay, this.encoderPadding, this.language, this.accessibilityChannel);
    }

    public Format copyWithMetadata(@Nullable Metadata metadata2) {
        return new Format(this.f72id, this.label, this.selectionFlags, this.roleFlags, this.bitrate, this.codecs, metadata2, this.containerMimeType, this.sampleMimeType, this.maxInputSize, this.initializationData, this.drmInitData, this.subsampleOffsetUs, this.width, this.height, this.frameRate, this.rotationDegrees, this.pixelWidthHeightRatio, this.projectionData, this.stereoMode, this.colorInfo, this.channelCount, this.sampleRate, this.pcmEncoding, this.encoderDelay, this.encoderPadding, this.language, this.accessibilityChannel);
    }

    public Format copyWithRotationDegrees(int rotationDegrees2) {
        return new Format(this.f72id, this.label, this.selectionFlags, this.roleFlags, this.bitrate, this.codecs, this.metadata, this.containerMimeType, this.sampleMimeType, this.maxInputSize, this.initializationData, this.drmInitData, this.subsampleOffsetUs, this.width, this.height, this.frameRate, rotationDegrees2, this.pixelWidthHeightRatio, this.projectionData, this.stereoMode, this.colorInfo, this.channelCount, this.sampleRate, this.pcmEncoding, this.encoderDelay, this.encoderPadding, this.language, this.accessibilityChannel);
    }

    public Format copyWithBitrate(int bitrate2) {
        return new Format(this.f72id, this.label, this.selectionFlags, this.roleFlags, bitrate2, this.codecs, this.metadata, this.containerMimeType, this.sampleMimeType, this.maxInputSize, this.initializationData, this.drmInitData, this.subsampleOffsetUs, this.width, this.height, this.frameRate, this.rotationDegrees, this.pixelWidthHeightRatio, this.projectionData, this.stereoMode, this.colorInfo, this.channelCount, this.sampleRate, this.pcmEncoding, this.encoderDelay, this.encoderPadding, this.language, this.accessibilityChannel);
    }

    public int getPixelCount() {
        int i;
        int i2 = this.width;
        if (i2 == -1 || (i = this.height) == -1) {
            return -1;
        }
        return i2 * i;
    }

    public String toString() {
        String str = this.f72id;
        String str2 = this.label;
        String str3 = this.containerMimeType;
        String str4 = this.sampleMimeType;
        String str5 = this.codecs;
        int i = this.bitrate;
        String str6 = this.language;
        int i2 = this.width;
        int i3 = this.height;
        float f = this.frameRate;
        int i4 = this.channelCount;
        int i5 = this.sampleRate;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 104 + String.valueOf(str2).length() + String.valueOf(str3).length() + String.valueOf(str4).length() + String.valueOf(str5).length() + String.valueOf(str6).length());
        sb.append("Format(");
        sb.append(str);
        sb.append(", ");
        sb.append(str2);
        sb.append(", ");
        sb.append(str3);
        sb.append(", ");
        sb.append(str4);
        sb.append(", ");
        sb.append(str5);
        sb.append(", ");
        sb.append(i);
        sb.append(", ");
        sb.append(str6);
        sb.append(", [");
        sb.append(i2);
        sb.append(", ");
        sb.append(i3);
        sb.append(", ");
        sb.append(f);
        sb.append("], [");
        sb.append(i4);
        sb.append(", ");
        sb.append(i5);
        sb.append("])");
        return sb.toString();
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            int i = 17 * 31;
            String str = this.f72id;
            int i2 = 0;
            int result = (i + (str == null ? 0 : str.hashCode())) * 31;
            String str2 = this.label;
            int result2 = (((((((result + (str2 != null ? str2.hashCode() : 0)) * 31) + this.selectionFlags) * 31) + this.roleFlags) * 31) + this.bitrate) * 31;
            String str3 = this.codecs;
            int result3 = (result2 + (str3 == null ? 0 : str3.hashCode())) * 31;
            Metadata metadata2 = this.metadata;
            int result4 = (result3 + (metadata2 == null ? 0 : metadata2.hashCode())) * 31;
            String str4 = this.containerMimeType;
            int result5 = (result4 + (str4 == null ? 0 : str4.hashCode())) * 31;
            String str5 = this.sampleMimeType;
            int result6 = (((((((((((((((((((((((((((result5 + (str5 == null ? 0 : str5.hashCode())) * 31) + this.maxInputSize) * 31) + ((int) this.subsampleOffsetUs)) * 31) + this.width) * 31) + this.height) * 31) + Float.floatToIntBits(this.frameRate)) * 31) + this.rotationDegrees) * 31) + Float.floatToIntBits(this.pixelWidthHeightRatio)) * 31) + this.stereoMode) * 31) + this.channelCount) * 31) + this.sampleRate) * 31) + this.pcmEncoding) * 31) + this.encoderDelay) * 31) + this.encoderPadding) * 31;
            String str6 = this.language;
            if (str6 != null) {
                i2 = str6.hashCode();
            }
            this.hashCode = ((result6 + i2) * 31) + this.accessibilityChannel;
        }
        return this.hashCode;
    }

    public boolean equals(@Nullable Object obj) {
        int i;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Format other = (Format) obj;
        int i2 = this.hashCode;
        if ((i2 == 0 || (i = other.hashCode) == 0 || i2 == i) && this.selectionFlags == other.selectionFlags && this.roleFlags == other.roleFlags && this.bitrate == other.bitrate && this.maxInputSize == other.maxInputSize && this.subsampleOffsetUs == other.subsampleOffsetUs && this.width == other.width && this.height == other.height && this.rotationDegrees == other.rotationDegrees && this.stereoMode == other.stereoMode && this.channelCount == other.channelCount && this.sampleRate == other.sampleRate && this.pcmEncoding == other.pcmEncoding && this.encoderDelay == other.encoderDelay && this.encoderPadding == other.encoderPadding && this.accessibilityChannel == other.accessibilityChannel && Float.compare(this.frameRate, other.frameRate) == 0 && Float.compare(this.pixelWidthHeightRatio, other.pixelWidthHeightRatio) == 0 && Util.areEqual(this.f72id, other.f72id) && Util.areEqual(this.label, other.label) && Util.areEqual(this.codecs, other.codecs) && Util.areEqual(this.containerMimeType, other.containerMimeType) && Util.areEqual(this.sampleMimeType, other.sampleMimeType) && Util.areEqual(this.language, other.language) && Arrays.equals(this.projectionData, other.projectionData) && Util.areEqual(this.metadata, other.metadata) && Util.areEqual(this.colorInfo, other.colorInfo) && Util.areEqual(this.drmInitData, other.drmInitData) && initializationDataEquals(other)) {
            return true;
        }
        return false;
    }

    public boolean initializationDataEquals(Format other) {
        if (this.initializationData.size() != other.initializationData.size()) {
            return false;
        }
        for (int i = 0; i < this.initializationData.size(); i++) {
            if (!Arrays.equals(this.initializationData.get(i), other.initializationData.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static String toLogString(@Nullable Format format) {
        if (format == null) {
            return "null";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("id=");
        builder.append(format.f72id);
        builder.append(", mimeType=");
        builder.append(format.sampleMimeType);
        if (format.bitrate != -1) {
            builder.append(", bitrate=");
            builder.append(format.bitrate);
        }
        if (format.codecs != null) {
            builder.append(", codecs=");
            builder.append(format.codecs);
        }
        if (!(format.width == -1 || format.height == -1)) {
            builder.append(", res=");
            builder.append(format.width);
            builder.append("x");
            builder.append(format.height);
        }
        if (format.frameRate != -1.0f) {
            builder.append(", fps=");
            builder.append(format.frameRate);
        }
        if (format.channelCount != -1) {
            builder.append(", channels=");
            builder.append(format.channelCount);
        }
        if (format.sampleRate != -1) {
            builder.append(", sample_rate=");
            builder.append(format.sampleRate);
        }
        if (format.language != null) {
            builder.append(", language=");
            builder.append(format.language);
        }
        if (format.label != null) {
            builder.append(", label=");
            builder.append(format.label);
        }
        return builder.toString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.f72id);
        dest.writeString(this.label);
        dest.writeInt(this.selectionFlags);
        dest.writeInt(this.roleFlags);
        dest.writeInt(this.bitrate);
        dest.writeString(this.codecs);
        boolean z = false;
        dest.writeParcelable(this.metadata, 0);
        dest.writeString(this.containerMimeType);
        dest.writeString(this.sampleMimeType);
        dest.writeInt(this.maxInputSize);
        int initializationDataSize = this.initializationData.size();
        dest.writeInt(initializationDataSize);
        for (int i = 0; i < initializationDataSize; i++) {
            dest.writeByteArray(this.initializationData.get(i));
        }
        dest.writeParcelable(this.drmInitData, 0);
        dest.writeLong(this.subsampleOffsetUs);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeFloat(this.frameRate);
        dest.writeInt(this.rotationDegrees);
        dest.writeFloat(this.pixelWidthHeightRatio);
        if (this.projectionData != null) {
            z = true;
        }
        Util.writeBoolean(dest, z);
        byte[] bArr = this.projectionData;
        if (bArr != null) {
            dest.writeByteArray(bArr);
        }
        dest.writeInt(this.stereoMode);
        dest.writeParcelable(this.colorInfo, flags);
        dest.writeInt(this.channelCount);
        dest.writeInt(this.sampleRate);
        dest.writeInt(this.pcmEncoding);
        dest.writeInt(this.encoderDelay);
        dest.writeInt(this.encoderPadding);
        dest.writeString(this.language);
        dest.writeInt(this.accessibilityChannel);
    }
}
