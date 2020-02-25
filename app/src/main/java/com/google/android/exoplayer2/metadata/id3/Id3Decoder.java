package com.google.android.exoplayer2.metadata.id3;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataDecoder;
import com.google.android.exoplayer2.metadata.MetadataInputBuffer;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.primitives.UnsignedBytes;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public final class Id3Decoder implements MetadataDecoder {
    public static final int ID3_HEADER_LENGTH = 10;
    public static final int ID3_TAG = Util.getIntegerCodeForString("ID3");
    public static final FramePredicate NO_FRAMES_PREDICATE = Id3Decoder$$Lambda$0.$instance;
    private static final int FRAME_FLAG_V3_HAS_GROUP_IDENTIFIER = 32;
    private static final int FRAME_FLAG_V3_IS_COMPRESSED = 128;
    private static final int FRAME_FLAG_V3_IS_ENCRYPTED = 64;
    private static final int FRAME_FLAG_V4_HAS_DATA_LENGTH = 1;
    private static final int FRAME_FLAG_V4_HAS_GROUP_IDENTIFIER = 64;
    private static final int FRAME_FLAG_V4_IS_COMPRESSED = 8;
    private static final int FRAME_FLAG_V4_IS_ENCRYPTED = 4;
    private static final int FRAME_FLAG_V4_IS_UNSYNCHRONIZED = 2;
    private static final int ID3_TEXT_ENCODING_ISO_8859_1 = 0;
    private static final int ID3_TEXT_ENCODING_UTF_16 = 1;
    private static final int ID3_TEXT_ENCODING_UTF_16BE = 2;
    private static final int ID3_TEXT_ENCODING_UTF_8 = 3;
    private static final String TAG = "Id3Decoder";
    @Nullable
    private final FramePredicate framePredicate;

    public Id3Decoder() {
        this(null);
    }

    public Id3Decoder(@Nullable FramePredicate framePredicate2) {
        this.framePredicate = framePredicate2;
    }

    static final /* synthetic */ boolean lambda$static$0$Id3Decoder(int majorVersion, int id0, int id1, int id2, int id3) {
        return false;
    }

    @Nullable
    private static Id3Header decodeHeader(ParsableByteArray data) {
        if (data.bytesLeft() < 10) {
            Log.m30w(TAG, "Data too short to be an ID3 tag");
            return null;
        }
        int id = data.readUnsignedInt24();
        if (id != ID3_TAG) {
            StringBuilder sb = new StringBuilder(59);
            sb.append("Unexpected first three bytes of ID3 tag header: ");
            sb.append(id);
            Log.m30w(TAG, sb.toString());
            return null;
        }
        int majorVersion = data.readUnsignedByte();
        boolean isUnsynchronized = true;
        data.skipBytes(1);
        int flags = data.readUnsignedByte();
        int framesSize = data.readSynchSafeInt();
        if (majorVersion == 2) {
            if ((flags & 64) != 0) {
                Log.m30w(TAG, "Skipped ID3 tag with majorVersion=2 and undefined compression scheme");
                return null;
            }
        } else if (majorVersion == 3) {
            if ((flags & 64) != 0) {
                int extendedHeaderSize = data.readInt();
                data.skipBytes(extendedHeaderSize);
                framesSize -= extendedHeaderSize + 4;
            }
        } else if (majorVersion == 4) {
            if ((flags & 64) != 0) {
                int extendedHeaderSize2 = data.readSynchSafeInt();
                data.skipBytes(extendedHeaderSize2 - 4);
                framesSize -= extendedHeaderSize2;
            }
            if ((flags & 16) != 0) {
                framesSize -= 10;
            }
        } else {
            StringBuilder sb2 = new StringBuilder(57);
            sb2.append("Skipped ID3 tag with unsupported majorVersion=");
            sb2.append(majorVersion);
            Log.m30w(TAG, sb2.toString());
            return null;
        }
        if (majorVersion >= 4 || (flags & 128) == 0) {
            isUnsynchronized = false;
        }
        return new Id3Header(majorVersion, isUnsynchronized, framesSize);
    }

    private static boolean validateFrames(ParsableByteArray id3Data, int majorVersion, int frameHeaderSize, boolean unsignedIntFrameSizeHack) {
        int flags;
        long frameSize;
        int id;
        ParsableByteArray parsableByteArray = id3Data;
        int i = majorVersion;
        int startPosition = id3Data.getPosition();
        while (true) {
            try {
                boolean z = true;
                if (id3Data.bytesLeft() >= frameHeaderSize) {
                    if (i >= 3) {
                        try {
                            id = id3Data.readInt();
                            frameSize = id3Data.readUnsignedInt();
                            flags = id3Data.readUnsignedShort();
                        } catch (Throwable th) {
                            th = th;
                        }
                    } else {
                        id = id3Data.readUnsignedInt24();
                        frameSize = (long) id3Data.readUnsignedInt24();
                        flags = 0;
                    }
                    if (id == 0 && frameSize == 0 && flags == 0) {
                        parsableByteArray.setPosition(startPosition);
                        return true;
                    }
                    if (i == 4 && !unsignedIntFrameSizeHack) {
                        if ((8421504 & frameSize) != 0) {
                            parsableByteArray.setPosition(startPosition);
                            return false;
                        }
                        frameSize = (frameSize & 255) | (((frameSize >> 8) & 255) << 7) | (((frameSize >> 16) & 255) << 14) | (((frameSize >> 24) & 255) << 21);
                    }
                    boolean hasGroupIdentifier = false;
                    boolean hasDataLength = false;
                    if (i == 4) {
                        hasGroupIdentifier = (flags & 64) != 0;
                        if ((flags & 1) == 0) {
                            z = false;
                        }
                        hasDataLength = z;
                    } else if (i == 3) {
                        hasGroupIdentifier = (flags & 32) != 0;
                        if ((flags & 128) == 0) {
                            z = false;
                        }
                        hasDataLength = z;
                    }
                    int minimumFrameSize = 0;
                    if (hasGroupIdentifier) {
                        minimumFrameSize = 0 + 1;
                    }
                    if (hasDataLength) {
                        minimumFrameSize += 4;
                    }
                    if (frameSize < ((long) minimumFrameSize)) {
                        parsableByteArray.setPosition(startPosition);
                        return false;
                    } else if (((long) id3Data.bytesLeft()) < frameSize) {
                        parsableByteArray.setPosition(startPosition);
                        return false;
                    } else {
                        parsableByteArray.skipBytes((int) frameSize);
                    }
                } else {
                    parsableByteArray.setPosition(startPosition);
                    return true;
                }
            } catch (Throwable th2) {
                th = th2;
                parsableByteArray.setPosition(startPosition);
                throw th;
            }
        }
    }

    /* JADX INFO: Multiple debug info for r0v6 java.lang.String: [D('frame' com.google.android.exoplayer2.metadata.id3.Id3Frame), D('id' java.lang.String)] */
    /* JADX WARNING: Code restructure failed: missing block: B:134:0x01bf, code lost:
        if (r13 == 67) goto L_0x01c1;
     */
    @android.support.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.google.android.exoplayer2.metadata.id3.Id3Frame decodeFrame(int r23, com.google.android.exoplayer2.util.ParsableByteArray r24, boolean r25, int r26, @android.support.annotation.Nullable com.google.android.exoplayer2.metadata.id3.Id3Decoder.FramePredicate r27) {
        /*
            r7 = r23
            r8 = r24
            int r9 = r24.readUnsignedByte()
            int r10 = r24.readUnsignedByte()
            int r11 = r24.readUnsignedByte()
            r0 = 3
            if (r7 < r0) goto L_0x0018
            int r1 = r24.readUnsignedByte()
            goto L_0x0019
        L_0x0018:
            r1 = 0
        L_0x0019:
            r13 = r1
            r14 = 4
            if (r7 != r14) goto L_0x003f
            int r1 = r24.readUnsignedIntToInt()
            if (r25 != 0) goto L_0x003d
            r2 = r1 & 255(0xff, float:3.57E-43)
            int r3 = r1 >> 8
            r3 = r3 & 255(0xff, float:3.57E-43)
            int r3 = r3 << 7
            r2 = r2 | r3
            int r3 = r1 >> 16
            r3 = r3 & 255(0xff, float:3.57E-43)
            int r3 = r3 << 14
            r2 = r2 | r3
            int r3 = r1 >> 24
            r3 = r3 & 255(0xff, float:3.57E-43)
            int r3 = r3 << 21
            r1 = r2 | r3
            r15 = r1
            goto L_0x004c
        L_0x003d:
            r15 = r1
            goto L_0x004c
        L_0x003f:
            if (r7 != r0) goto L_0x0047
            int r1 = r24.readUnsignedIntToInt()
            r15 = r1
            goto L_0x004c
        L_0x0047:
            int r1 = r24.readUnsignedInt24()
            r15 = r1
        L_0x004c:
            if (r7 < r0) goto L_0x0053
            int r1 = r24.readUnsignedShort()
            goto L_0x0054
        L_0x0053:
            r1 = 0
        L_0x0054:
            r6 = r1
            r16 = 0
            if (r9 != 0) goto L_0x006b
            if (r10 != 0) goto L_0x006b
            if (r11 != 0) goto L_0x006b
            if (r13 != 0) goto L_0x006b
            if (r15 != 0) goto L_0x006b
            if (r6 != 0) goto L_0x006b
            int r0 = r24.limit()
            r8.setPosition(r0)
            return r16
        L_0x006b:
            int r1 = r24.getPosition()
            int r5 = r1 + r15
            int r1 = r24.limit()
            java.lang.String r4 = "Id3Decoder"
            if (r5 <= r1) goto L_0x0086
            java.lang.String r0 = "Frame size exceeds remaining tag data"
            com.google.android.exoplayer2.util.Log.m30w(r4, r0)
            int r0 = r24.limit()
            r8.setPosition(r0)
            return r16
        L_0x0086:
            if (r27 == 0) goto L_0x009f
            r1 = r27
            r2 = r23
            r3 = r9
            r12 = r4
            r4 = r10
            r14 = r5
            r5 = r11
            r18 = r12
            r12 = r6
            r6 = r13
            boolean r1 = r1.evaluate(r2, r3, r4, r5, r6)
            if (r1 != 0) goto L_0x00a3
            r8.setPosition(r14)
            return r16
        L_0x009f:
            r18 = r4
            r14 = r5
            r12 = r6
        L_0x00a3:
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 1
            if (r7 != r0) goto L_0x00d2
            r0 = r12 & 128(0x80, float:1.794E-43)
            if (r0 == 0) goto L_0x00b1
            r0 = 1
            goto L_0x00b2
        L_0x00b1:
            r0 = 0
        L_0x00b2:
            r1 = r0
            r0 = r12 & 64
            if (r0 == 0) goto L_0x00b9
            r0 = 1
            goto L_0x00ba
        L_0x00b9:
            r0 = 0
        L_0x00ba:
            r2 = r0
            r0 = r12 & 32
            if (r0 == 0) goto L_0x00c2
            r17 = 1
            goto L_0x00c4
        L_0x00c2:
            r17 = 0
        L_0x00c4:
            r5 = r17
            r4 = r1
            r17 = r1
            r19 = r2
            r21 = r3
            r22 = r4
            r20 = r5
            goto L_0x0115
        L_0x00d2:
            r0 = 4
            if (r7 != r0) goto L_0x010b
            r0 = r12 & 64
            if (r0 == 0) goto L_0x00db
            r0 = 1
            goto L_0x00dc
        L_0x00db:
            r0 = 0
        L_0x00dc:
            r5 = r0
            r0 = r12 & 8
            if (r0 == 0) goto L_0x00e3
            r0 = 1
            goto L_0x00e4
        L_0x00e3:
            r0 = 0
        L_0x00e4:
            r1 = r0
            r0 = r12 & 4
            if (r0 == 0) goto L_0x00eb
            r0 = 1
            goto L_0x00ec
        L_0x00eb:
            r0 = 0
        L_0x00ec:
            r2 = r0
            r0 = r12 & 2
            if (r0 == 0) goto L_0x00f3
            r0 = 1
            goto L_0x00f4
        L_0x00f3:
            r0 = 0
        L_0x00f4:
            r3 = r0
            r0 = r12 & 1
            if (r0 == 0) goto L_0x00fc
            r17 = 1
            goto L_0x00fe
        L_0x00fc:
            r17 = 0
        L_0x00fe:
            r4 = r17
            r17 = r1
            r19 = r2
            r21 = r3
            r22 = r4
            r20 = r5
            goto L_0x0115
        L_0x010b:
            r17 = r1
            r19 = r2
            r21 = r3
            r22 = r4
            r20 = r5
        L_0x0115:
            if (r17 != 0) goto L_0x026b
            if (r19 == 0) goto L_0x011d
            r2 = r18
            goto L_0x026d
        L_0x011d:
            if (r20 == 0) goto L_0x0124
            int r15 = r15 + -1
            r8.skipBytes(r6)
        L_0x0124:
            if (r22 == 0) goto L_0x012c
            int r15 = r15 + -4
            r0 = 4
            r8.skipBytes(r0)
        L_0x012c:
            if (r21 == 0) goto L_0x0133
            int r0 = removeUnsynchronization(r8, r15)
            r15 = r0
        L_0x0133:
            r0 = 84
            r1 = 2
            r2 = 88
            if (r9 != r0) goto L_0x0148
            if (r10 != r2) goto L_0x0148
            if (r11 != r2) goto L_0x0148
            if (r7 == r1) goto L_0x0142
            if (r13 != r2) goto L_0x0148
        L_0x0142:
            com.google.android.exoplayer2.metadata.id3.TextInformationFrame r0 = decodeTxxxFrame(r8, r15)     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            goto L_0x0226
        L_0x0148:
            if (r9 != r0) goto L_0x015d
            java.lang.String r0 = getFrameId(r7, r9, r10, r11, r13)     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            com.google.android.exoplayer2.metadata.id3.TextInformationFrame r1 = decodeTextInformationFrame(r8, r15, r0)     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            r0 = r1
            goto L_0x0226
        L_0x0155:
            r0 = move-exception
            goto L_0x0267
        L_0x0158:
            r0 = move-exception
            r2 = r18
            goto L_0x025d
        L_0x015d:
            r3 = 87
            if (r9 != r3) goto L_0x016f
            if (r10 != r2) goto L_0x016f
            if (r11 != r2) goto L_0x016f
            if (r7 == r1) goto L_0x0169
            if (r13 != r2) goto L_0x016f
        L_0x0169:
            com.google.android.exoplayer2.metadata.id3.UrlLinkFrame r0 = decodeWxxxFrame(r8, r15)     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            goto L_0x0226
        L_0x016f:
            r2 = 87
            if (r9 != r2) goto L_0x017e
            java.lang.String r0 = getFrameId(r7, r9, r10, r11, r13)     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            com.google.android.exoplayer2.metadata.id3.UrlLinkFrame r1 = decodeUrlLinkFrame(r8, r15, r0)     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            r0 = r1
            goto L_0x0226
        L_0x017e:
            r2 = 73
            r3 = 80
            if (r9 != r3) goto L_0x0194
            r4 = 82
            if (r10 != r4) goto L_0x0194
            if (r11 != r2) goto L_0x0194
            r4 = 86
            if (r13 != r4) goto L_0x0194
            com.google.android.exoplayer2.metadata.id3.PrivFrame r0 = decodePrivFrame(r8, r15)     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            goto L_0x0226
        L_0x0194:
            r4 = 71
            r5 = 79
            if (r9 != r4) goto L_0x01ac
            r4 = 69
            if (r10 != r4) goto L_0x01ac
            if (r11 != r5) goto L_0x01ac
            r4 = 66
            if (r13 == r4) goto L_0x01a6
            if (r7 != r1) goto L_0x01ac
        L_0x01a6:
            com.google.android.exoplayer2.metadata.id3.GeobFrame r0 = decodeGeobFrame(r8, r15)     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            goto L_0x0226
        L_0x01ac:
            r4 = 67
            if (r7 != r1) goto L_0x01b7
            if (r9 != r3) goto L_0x01c7
            if (r10 != r2) goto L_0x01c7
            if (r11 != r4) goto L_0x01c7
            goto L_0x01c1
        L_0x01b7:
            r6 = 65
            if (r9 != r6) goto L_0x01c7
            if (r10 != r3) goto L_0x01c7
            if (r11 != r2) goto L_0x01c7
            if (r13 != r4) goto L_0x01c7
        L_0x01c1:
            com.google.android.exoplayer2.metadata.id3.ApicFrame r0 = decodeApicFrame(r8, r15, r7)     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            goto L_0x0226
        L_0x01c7:
            r2 = 77
            if (r9 != r4) goto L_0x01d8
            if (r10 != r5) goto L_0x01d8
            if (r11 != r2) goto L_0x01d8
            if (r13 == r2) goto L_0x01d3
            if (r7 != r1) goto L_0x01d8
        L_0x01d3:
            com.google.android.exoplayer2.metadata.id3.CommentFrame r0 = decodeCommentFrame(r8, r15)     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            goto L_0x0226
        L_0x01d8:
            if (r9 != r4) goto L_0x01f4
            r1 = 72
            if (r10 != r1) goto L_0x01f4
            r1 = 65
            if (r11 != r1) goto L_0x01f4
            if (r13 != r3) goto L_0x01f4
            r1 = r24
            r2 = r15
            r3 = r23
            r4 = r25
            r5 = r26
            r6 = r27
            com.google.android.exoplayer2.metadata.id3.ChapterFrame r0 = decodeChapterFrame(r1, r2, r3, r4, r5, r6)     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            goto L_0x0226
        L_0x01f4:
            if (r9 != r4) goto L_0x020c
            if (r10 != r0) goto L_0x020c
            if (r11 != r5) goto L_0x020c
            if (r13 != r4) goto L_0x020c
            r1 = r24
            r2 = r15
            r3 = r23
            r4 = r25
            r5 = r26
            r6 = r27
            com.google.android.exoplayer2.metadata.id3.ChapterTocFrame r0 = decodeChapterTOCFrame(r1, r2, r3, r4, r5, r6)     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            goto L_0x0226
        L_0x020c:
            if (r9 != r2) goto L_0x021d
            r1 = 76
            if (r10 != r1) goto L_0x021d
            r1 = 76
            if (r11 != r1) goto L_0x021d
            if (r13 != r0) goto L_0x021d
            com.google.android.exoplayer2.metadata.id3.MlltFrame r0 = decodeMlltFrame(r8, r15)     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            goto L_0x0226
        L_0x021d:
            java.lang.String r0 = getFrameId(r7, r9, r10, r11, r13)     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            com.google.android.exoplayer2.metadata.id3.BinaryFrame r1 = decodeBinaryFrame(r8, r15, r0)     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            r0 = r1
        L_0x0226:
            if (r0 != 0) goto L_0x0258
            java.lang.String r1 = getFrameId(r7, r9, r10, r11, r13)     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            java.lang.String r2 = java.lang.String.valueOf(r1)     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            int r2 = r2.length()     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            int r2 = r2 + 50
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            r3.<init>(r2)     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            java.lang.String r2 = "Failed to decode frame: id="
            r3.append(r2)     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            r3.append(r1)     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            java.lang.String r1 = ", frameSize="
            r3.append(r1)     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            r3.append(r15)     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            java.lang.String r1 = r3.toString()     // Catch:{ UnsupportedEncodingException -> 0x0158 }
            r2 = r18
            com.google.android.exoplayer2.util.Log.m30w(r2, r1)     // Catch:{ UnsupportedEncodingException -> 0x0256 }
            goto L_0x0258
        L_0x0256:
            r0 = move-exception
            goto L_0x025d
        L_0x0258:
            r8.setPosition(r14)
            return r0
        L_0x025d:
            java.lang.String r1 = "Unsupported character encoding"
            com.google.android.exoplayer2.util.Log.m30w(r2, r1)     // Catch:{ all -> 0x0155 }
            r8.setPosition(r14)
            return r16
        L_0x0267:
            r8.setPosition(r14)
            throw r0
        L_0x026b:
            r2 = r18
        L_0x026d:
            java.lang.String r0 = "Skipping unsupported compressed or encrypted frame"
            com.google.android.exoplayer2.util.Log.m30w(r2, r0)
            r8.setPosition(r14)
            return r16
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.metadata.id3.Id3Decoder.decodeFrame(int, com.google.android.exoplayer2.util.ParsableByteArray, boolean, int, com.google.android.exoplayer2.metadata.id3.Id3Decoder$FramePredicate):com.google.android.exoplayer2.metadata.id3.Id3Frame");
    }

    @Nullable
    private static TextInformationFrame decodeTxxxFrame(ParsableByteArray id3Data, int frameSize) throws UnsupportedEncodingException {
        if (frameSize < 1) {
            return null;
        }
        int encoding = id3Data.readUnsignedByte();
        String charset = getCharsetName(encoding);
        byte[] data = new byte[(frameSize - 1)];
        id3Data.readBytes(data, 0, frameSize - 1);
        int descriptionEndIndex = indexOfEos(data, 0, encoding);
        String description = new String(data, 0, descriptionEndIndex, charset);
        int valueStartIndex = delimiterLength(encoding) + descriptionEndIndex;
        return new TextInformationFrame("TXXX", description, decodeStringIfValid(data, valueStartIndex, indexOfEos(data, valueStartIndex, encoding), charset));
    }

    @Nullable
    private static TextInformationFrame decodeTextInformationFrame(ParsableByteArray id3Data, int frameSize, String id) throws UnsupportedEncodingException {
        if (frameSize < 1) {
            return null;
        }
        int encoding = id3Data.readUnsignedByte();
        String charset = getCharsetName(encoding);
        byte[] data = new byte[(frameSize - 1)];
        id3Data.readBytes(data, 0, frameSize - 1);
        return new TextInformationFrame(id, null, new String(data, 0, indexOfEos(data, 0, encoding), charset));
    }

    @Nullable
    private static UrlLinkFrame decodeWxxxFrame(ParsableByteArray id3Data, int frameSize) throws UnsupportedEncodingException {
        if (frameSize < 1) {
            return null;
        }
        int encoding = id3Data.readUnsignedByte();
        String charset = getCharsetName(encoding);
        byte[] data = new byte[(frameSize - 1)];
        id3Data.readBytes(data, 0, frameSize - 1);
        int descriptionEndIndex = indexOfEos(data, 0, encoding);
        String description = new String(data, 0, descriptionEndIndex, charset);
        int urlStartIndex = delimiterLength(encoding) + descriptionEndIndex;
        return new UrlLinkFrame("WXXX", description, decodeStringIfValid(data, urlStartIndex, indexOfZeroByte(data, urlStartIndex), "ISO-8859-1"));
    }

    private static UrlLinkFrame decodeUrlLinkFrame(ParsableByteArray id3Data, int frameSize, String id) throws UnsupportedEncodingException {
        byte[] data = new byte[frameSize];
        id3Data.readBytes(data, 0, frameSize);
        return new UrlLinkFrame(id, null, new String(data, 0, indexOfZeroByte(data, 0), "ISO-8859-1"));
    }

    private static PrivFrame decodePrivFrame(ParsableByteArray id3Data, int frameSize) throws UnsupportedEncodingException {
        byte[] data = new byte[frameSize];
        id3Data.readBytes(data, 0, frameSize);
        int ownerEndIndex = indexOfZeroByte(data, 0);
        return new PrivFrame(new String(data, 0, ownerEndIndex, "ISO-8859-1"), copyOfRangeIfValid(data, ownerEndIndex + 1, data.length));
    }

    private static GeobFrame decodeGeobFrame(ParsableByteArray id3Data, int frameSize) throws UnsupportedEncodingException {
        int encoding = id3Data.readUnsignedByte();
        String charset = getCharsetName(encoding);
        byte[] data = new byte[(frameSize - 1)];
        id3Data.readBytes(data, 0, frameSize - 1);
        int mimeTypeEndIndex = indexOfZeroByte(data, 0);
        String mimeType = new String(data, 0, mimeTypeEndIndex, "ISO-8859-1");
        int filenameStartIndex = mimeTypeEndIndex + 1;
        int filenameEndIndex = indexOfEos(data, filenameStartIndex, encoding);
        String filename = decodeStringIfValid(data, filenameStartIndex, filenameEndIndex, charset);
        int descriptionStartIndex = delimiterLength(encoding) + filenameEndIndex;
        int descriptionEndIndex = indexOfEos(data, descriptionStartIndex, encoding);
        return new GeobFrame(mimeType, filename, decodeStringIfValid(data, descriptionStartIndex, descriptionEndIndex, charset), copyOfRangeIfValid(data, delimiterLength(encoding) + descriptionEndIndex, data.length));
    }

    private static ApicFrame decodeApicFrame(ParsableByteArray id3Data, int frameSize, int majorVersion) throws UnsupportedEncodingException {
        int mimeTypeEndIndex;
        String mimeType;
        int encoding = id3Data.readUnsignedByte();
        String charset = getCharsetName(encoding);
        byte[] data = new byte[(frameSize - 1)];
        id3Data.readBytes(data, 0, frameSize - 1);
        if (majorVersion == 2) {
            mimeTypeEndIndex = 2;
            String valueOf = String.valueOf(Util.toLowerInvariant(new String(data, 0, 3, "ISO-8859-1")));
            mimeType = valueOf.length() != 0 ? "image/".concat(valueOf) : new String("image/");
            if ("image/jpg".equals(mimeType)) {
                mimeType = "image/jpeg";
            }
        } else {
            mimeTypeEndIndex = indexOfZeroByte(data, 0);
            String mimeType2 = Util.toLowerInvariant(new String(data, 0, mimeTypeEndIndex, "ISO-8859-1"));
            if (mimeType2.indexOf(47) == -1) {
                String valueOf2 = String.valueOf(mimeType2);
                mimeType = valueOf2.length() != 0 ? "image/".concat(valueOf2) : new String("image/");
            } else {
                mimeType = mimeType2;
            }
        }
        int descriptionStartIndex = mimeTypeEndIndex + 2;
        int descriptionEndIndex = indexOfEos(data, descriptionStartIndex, encoding);
        return new ApicFrame(mimeType, new String(data, descriptionStartIndex, descriptionEndIndex - descriptionStartIndex, charset), data[mimeTypeEndIndex + 1] & 255, copyOfRangeIfValid(data, delimiterLength(encoding) + descriptionEndIndex, data.length));
    }

    @Nullable
    private static CommentFrame decodeCommentFrame(ParsableByteArray id3Data, int frameSize) throws UnsupportedEncodingException {
        if (frameSize < 4) {
            return null;
        }
        int encoding = id3Data.readUnsignedByte();
        String charset = getCharsetName(encoding);
        byte[] data = new byte[3];
        id3Data.readBytes(data, 0, 3);
        String language = new String(data, 0, 3);
        byte[] data2 = new byte[(frameSize - 4)];
        id3Data.readBytes(data2, 0, frameSize - 4);
        int descriptionEndIndex = indexOfEos(data2, 0, encoding);
        String description = new String(data2, 0, descriptionEndIndex, charset);
        int textStartIndex = delimiterLength(encoding) + descriptionEndIndex;
        return new CommentFrame(language, description, decodeStringIfValid(data2, textStartIndex, indexOfEos(data2, textStartIndex, encoding), charset));
    }

    /* JADX INFO: Multiple debug info for r5v2 long: [D('endOffset' long), D('startOffset' long)] */
    private static ChapterFrame decodeChapterFrame(ParsableByteArray id3Data, int frameSize, int majorVersion, boolean unsignedIntFrameSizeHack, int frameHeaderSize, @Nullable FramePredicate framePredicate2) throws UnsupportedEncodingException {
        long startOffset;
        long endOffset;
        ParsableByteArray parsableByteArray = id3Data;
        int framePosition = id3Data.getPosition();
        int chapterIdEndIndex = indexOfZeroByte(parsableByteArray.data, framePosition);
        String chapterId = new String(parsableByteArray.data, framePosition, chapterIdEndIndex - framePosition, "ISO-8859-1");
        parsableByteArray.setPosition(chapterIdEndIndex + 1);
        int startTime = id3Data.readInt();
        int endTime = id3Data.readInt();
        long startOffset2 = id3Data.readUnsignedInt();
        if (startOffset2 == 4294967295L) {
            startOffset = -1;
        } else {
            startOffset = startOffset2;
        }
        long startOffset3 = id3Data.readUnsignedInt();
        if (startOffset3 == 4294967295L) {
            endOffset = -1;
        } else {
            endOffset = startOffset3;
        }
        ArrayList<Id3Frame> subFrames = new ArrayList<>();
        int limit = framePosition + frameSize;
        while (id3Data.getPosition() < limit) {
            Id3Frame frame = decodeFrame(majorVersion, parsableByteArray, unsignedIntFrameSizeHack, frameHeaderSize, framePredicate2);
            if (frame != null) {
                subFrames.add(frame);
            }
        }
        Id3Frame[] subFrameArray = new Id3Frame[subFrames.size()];
        subFrames.toArray(subFrameArray);
        return new ChapterFrame(chapterId, startTime, endTime, startOffset, endOffset, subFrameArray);
    }

    private static ChapterTocFrame decodeChapterTOCFrame(ParsableByteArray id3Data, int frameSize, int majorVersion, boolean unsignedIntFrameSizeHack, int frameHeaderSize, @Nullable FramePredicate framePredicate2) throws UnsupportedEncodingException {
        ParsableByteArray parsableByteArray = id3Data;
        int framePosition = id3Data.getPosition();
        int elementIdEndIndex = indexOfZeroByte(parsableByteArray.data, framePosition);
        String elementId = new String(parsableByteArray.data, framePosition, elementIdEndIndex - framePosition, "ISO-8859-1");
        parsableByteArray.setPosition(elementIdEndIndex + 1);
        int ctocFlags = id3Data.readUnsignedByte();
        boolean isOrdered = false;
        boolean isRoot = (ctocFlags & 2) != 0;
        if ((ctocFlags & 1) != 0) {
            isOrdered = true;
        }
        int childCount = id3Data.readUnsignedByte();
        String[] children = new String[childCount];
        for (int i = 0; i < childCount; i++) {
            int startIndex = id3Data.getPosition();
            int endIndex = indexOfZeroByte(parsableByteArray.data, startIndex);
            children[i] = new String(parsableByteArray.data, startIndex, endIndex - startIndex, "ISO-8859-1");
            parsableByteArray.setPosition(endIndex + 1);
        }
        ArrayList arrayList = new ArrayList();
        int limit = framePosition + frameSize;
        while (id3Data.getPosition() < limit) {
            Id3Frame frame = decodeFrame(majorVersion, parsableByteArray, unsignedIntFrameSizeHack, frameHeaderSize, framePredicate2);
            if (frame != null) {
                arrayList.add(frame);
            }
        }
        Id3Frame[] subFrameArray = new Id3Frame[arrayList.size()];
        arrayList.toArray(subFrameArray);
        return new ChapterTocFrame(elementId, isRoot, isOrdered, children, subFrameArray);
    }

    private static MlltFrame decodeMlltFrame(ParsableByteArray id3Data, int frameSize) {
        int mpegFramesBetweenReference = id3Data.readUnsignedShort();
        int bytesBetweenReference = id3Data.readUnsignedInt24();
        int millisecondsBetweenReference = id3Data.readUnsignedInt24();
        int bitsForBytesDeviation = id3Data.readUnsignedByte();
        int bitsForMillisecondsDeviation = id3Data.readUnsignedByte();
        ParsableBitArray references = new ParsableBitArray();
        references.reset(id3Data);
        int referencesCount = ((frameSize - 10) * 8) / (bitsForBytesDeviation + bitsForMillisecondsDeviation);
        int[] bytesDeviations = new int[referencesCount];
        int[] millisecondsDeviations = new int[referencesCount];
        for (int i = 0; i < referencesCount; i++) {
            int bytesDeviation = references.readBits(bitsForBytesDeviation);
            int millisecondsDeviation = references.readBits(bitsForMillisecondsDeviation);
            bytesDeviations[i] = bytesDeviation;
            millisecondsDeviations[i] = millisecondsDeviation;
        }
        return new MlltFrame(mpegFramesBetweenReference, bytesBetweenReference, millisecondsBetweenReference, bytesDeviations, millisecondsDeviations);
    }

    private static BinaryFrame decodeBinaryFrame(ParsableByteArray id3Data, int frameSize, String id) {
        byte[] frame = new byte[frameSize];
        id3Data.readBytes(frame, 0, frameSize);
        return new BinaryFrame(id, frame);
    }

    private static int removeUnsynchronization(ParsableByteArray data, int length) {
        byte[] bytes = data.data;
        int startPosition = data.getPosition();
        for (int i = startPosition; i + 1 < startPosition + length; i++) {
            if ((bytes[i] & UnsignedBytes.MAX_VALUE) == 255 && bytes[i + 1] == 0) {
                System.arraycopy(bytes, i + 2, bytes, i + 1, (length - (i - startPosition)) - 2);
                length--;
            }
        }
        return length;
    }

    private static String getCharsetName(int encodingByte) {
        if (encodingByte == 1) {
            return C0841C.UTF16_NAME;
        }
        if (encodingByte == 2) {
            return "UTF-16BE";
        }
        if (encodingByte != 3) {
            return "ISO-8859-1";
        }
        return "UTF-8";
    }

    private static String getFrameId(int majorVersion, int frameId0, int frameId1, int frameId2, int frameId3) {
        if (majorVersion == 2) {
            return String.format(Locale.US, "%c%c%c", Integer.valueOf(frameId0), Integer.valueOf(frameId1), Integer.valueOf(frameId2));
        }
        return String.format(Locale.US, "%c%c%c%c", Integer.valueOf(frameId0), Integer.valueOf(frameId1), Integer.valueOf(frameId2), Integer.valueOf(frameId3));
    }

    private static int indexOfEos(byte[] data, int fromIndex, int encoding) {
        int terminationPos = indexOfZeroByte(data, fromIndex);
        if (encoding == 0 || encoding == 3) {
            return terminationPos;
        }
        while (terminationPos < data.length - 1) {
            if (terminationPos % 2 == 0 && data[terminationPos + 1] == 0) {
                return terminationPos;
            }
            terminationPos = indexOfZeroByte(data, terminationPos + 1);
        }
        return data.length;
    }

    private static int indexOfZeroByte(byte[] data, int fromIndex) {
        for (int i = fromIndex; i < data.length; i++) {
            if (data[i] == 0) {
                return i;
            }
        }
        return data.length;
    }

    private static int delimiterLength(int encodingByte) {
        return (encodingByte == 0 || encodingByte == 3) ? 1 : 2;
    }

    private static byte[] copyOfRangeIfValid(byte[] data, int from, int to) {
        if (to <= from) {
            return Util.EMPTY_BYTE_ARRAY;
        }
        return Arrays.copyOfRange(data, from, to);
    }

    private static String decodeStringIfValid(byte[] data, int from, int to, String charsetName) throws UnsupportedEncodingException {
        if (to <= from || to > data.length) {
            return "";
        }
        return new String(data, from, to - from, charsetName);
    }

    @Nullable
    public Metadata decode(MetadataInputBuffer inputBuffer) {
        ByteBuffer buffer = inputBuffer.data;
        return decode(buffer.array(), buffer.limit());
    }

    @Nullable
    public Metadata decode(byte[] data, int size) {
        List<Id3Frame> id3Frames = new ArrayList<>();
        ParsableByteArray id3Data = new ParsableByteArray(data, size);
        Id3Header id3Header = decodeHeader(id3Data);
        if (id3Header == null) {
            return null;
        }
        int startPosition = id3Data.getPosition();
        int frameHeaderSize = id3Header.majorVersion == 2 ? 6 : 10;
        int framesSize = id3Header.framesSize;
        if (id3Header.isUnsynchronized) {
            framesSize = removeUnsynchronization(id3Data, id3Header.framesSize);
        }
        id3Data.setLimit(startPosition + framesSize);
        boolean unsignedIntFrameSizeHack = false;
        if (!validateFrames(id3Data, id3Header.majorVersion, frameHeaderSize, false)) {
            if (id3Header.majorVersion != 4 || !validateFrames(id3Data, 4, frameHeaderSize, true)) {
                int access$000 = id3Header.majorVersion;
                StringBuilder sb = new StringBuilder(56);
                sb.append("Failed to validate ID3 tag with majorVersion=");
                sb.append(access$000);
                Log.m30w(TAG, sb.toString());
                return null;
            }
            unsignedIntFrameSizeHack = true;
        }
        while (id3Data.bytesLeft() >= frameHeaderSize) {
            Id3Frame frame = decodeFrame(id3Header.majorVersion, id3Data, unsignedIntFrameSizeHack, frameHeaderSize, this.framePredicate);
            if (frame != null) {
                id3Frames.add(frame);
            }
        }
        return new Metadata(id3Frames);
    }

    public interface FramePredicate {
        boolean evaluate(int i, int i2, int i3, int i4, int i5);
    }

    private static final class Id3Header {
        /* access modifiers changed from: private */
        public final int framesSize;
        /* access modifiers changed from: private */
        public final boolean isUnsynchronized;
        /* access modifiers changed from: private */
        public final int majorVersion;

        public Id3Header(int majorVersion2, boolean isUnsynchronized2, int framesSize2) {
            this.majorVersion = majorVersion2;
            this.isUnsynchronized = isUnsynchronized2;
            this.framesSize = framesSize2;
        }
    }
}
