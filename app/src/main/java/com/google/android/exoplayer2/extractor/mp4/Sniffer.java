package com.google.android.exoplayer2.extractor.mp4;

import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;

final class Sniffer {
    private static final int[] COMPATIBLE_BRANDS = {Util.getIntegerCodeForString("isom"), Util.getIntegerCodeForString("iso2"), Util.getIntegerCodeForString("iso3"), Util.getIntegerCodeForString("iso4"), Util.getIntegerCodeForString("iso5"), Util.getIntegerCodeForString("iso6"), Util.getIntegerCodeForString("avc1"), Util.getIntegerCodeForString("hvc1"), Util.getIntegerCodeForString("hev1"), Util.getIntegerCodeForString("av01"), Util.getIntegerCodeForString("mp41"), Util.getIntegerCodeForString("mp42"), Util.getIntegerCodeForString("3g2a"), Util.getIntegerCodeForString("3g2b"), Util.getIntegerCodeForString("3gr6"), Util.getIntegerCodeForString("3gs6"), Util.getIntegerCodeForString("3ge6"), Util.getIntegerCodeForString("3gg6"), Util.getIntegerCodeForString("M4V "), Util.getIntegerCodeForString("M4A "), Util.getIntegerCodeForString("f4v "), Util.getIntegerCodeForString("kddi"), Util.getIntegerCodeForString("M4VP"), Util.getIntegerCodeForString("qt  "), Util.getIntegerCodeForString("MSNV"), Util.getIntegerCodeForString("dby1")};
    private static final int SEARCH_LENGTH = 4096;

    private Sniffer() {
    }

    public static boolean sniffFragmented(ExtractorInput input) throws IOException, InterruptedException {
        return sniffInternal(input, true);
    }

    public static boolean sniffUnfragmented(ExtractorInput input) throws IOException, InterruptedException {
        return sniffInternal(input, false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00e7, code lost:
        r9 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean sniffInternal(com.google.android.exoplayer2.extractor.ExtractorInput r23, boolean r24) throws java.io.IOException, java.lang.InterruptedException {
        /*
            r0 = r23
            long r1 = r23.getLength()
            r3 = 4096(0x1000, double:2.0237E-320)
            r5 = -1
            int r7 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r7 == 0) goto L_0x0015
            int r7 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r7 <= 0) goto L_0x0013
            goto L_0x0015
        L_0x0013:
            r3 = r1
            goto L_0x0016
        L_0x0015:
        L_0x0016:
            int r4 = (int) r3
            com.google.android.exoplayer2.util.ParsableByteArray r3 = new com.google.android.exoplayer2.util.ParsableByteArray
            r7 = 64
            r3.<init>(r7)
            r7 = 0
            r8 = 0
            r9 = 0
        L_0x0021:
            r11 = 0
            if (r7 >= r4) goto L_0x00e9
            r12 = 8
            r3.reset(r12)
            byte[] r13 = r3.data
            r0.peekFully(r13, r11, r12)
            long r13 = r3.readUnsignedInt()
            int r15 = r3.readInt()
            r16 = 1
            r10 = 8
            int r19 = (r13 > r16 ? 1 : (r13 == r16 ? 0 : -1))
            if (r19 != 0) goto L_0x004f
            r12 = 16
            byte[] r11 = r3.data
            r0.peekFully(r11, r10, r10)
            r11 = 16
            r3.setLimit(r11)
            long r13 = r3.readLong()
            goto L_0x0066
        L_0x004f:
            r19 = 0
            int r11 = (r13 > r19 ? 1 : (r13 == r19 ? 0 : -1))
            if (r11 != 0) goto L_0x0066
            long r19 = r23.getLength()
            int r11 = (r19 > r5 ? 1 : (r19 == r5 ? 0 : -1))
            if (r11 == 0) goto L_0x0066
            long r21 = r23.getPeekPosition()
            long r21 = r19 - r21
            long r10 = (long) r12
            long r13 = r21 + r10
        L_0x0066:
            int r10 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r10 == 0) goto L_0x0072
            long r10 = (long) r7
            long r10 = r10 + r13
            int r19 = (r10 > r1 ? 1 : (r10 == r1 ? 0 : -1))
            if (r19 <= 0) goto L_0x0072
            r10 = 0
            return r10
        L_0x0072:
            r10 = 0
            long r5 = (long) r12
            int r11 = (r13 > r5 ? 1 : (r13 == r5 ? 0 : -1))
            if (r11 >= 0) goto L_0x0079
            return r10
        L_0x0079:
            int r7 = r7 + r12
            int r5 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_moov
            if (r15 != r5) goto L_0x008d
            int r5 = (int) r13
            int r4 = r4 + r5
            r5 = -1
            int r10 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r10 == 0) goto L_0x0021
            long r10 = (long) r4
            int r16 = (r10 > r1 ? 1 : (r10 == r1 ? 0 : -1))
            if (r16 <= 0) goto L_0x0021
            int r4 = (int) r1
            goto L_0x0021
        L_0x008d:
            r5 = -1
            int r10 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_moof
            if (r15 == r10) goto L_0x00e6
            int r10 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_mvex
            if (r15 != r10) goto L_0x0099
            r10 = 0
            goto L_0x00e7
        L_0x0099:
            long r10 = (long) r7
            long r10 = r10 + r13
            long r5 = (long) r12
            long r10 = r10 - r5
            long r5 = (long) r4
            int r21 = (r10 > r5 ? 1 : (r10 == r5 ? 0 : -1))
            if (r21 < 0) goto L_0x00a4
            r10 = 0
            goto L_0x00ea
        L_0x00a4:
            long r5 = (long) r12
            long r5 = r13 - r5
            int r6 = (int) r5
            int r7 = r7 + r6
            int r5 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_ftyp
            if (r15 != r5) goto L_0x00dd
            r5 = 8
            if (r6 >= r5) goto L_0x00b3
            r5 = 0
            return r5
        L_0x00b3:
            r5 = 0
            r3.reset(r6)
            byte[] r10 = r3.data
            r0.peekFully(r10, r5, r6)
            int r5 = r6 / 4
            r10 = 0
        L_0x00bf:
            if (r10 >= r5) goto L_0x00d8
            r11 = 1
            if (r10 != r11) goto L_0x00c9
            r11 = 4
            r3.skipBytes(r11)
            goto L_0x00d5
        L_0x00c9:
            int r11 = r3.readInt()
            boolean r11 = isCompatibleBrand(r11)
            if (r11 == 0) goto L_0x00d5
            r8 = 1
            goto L_0x00d8
        L_0x00d5:
            int r10 = r10 + 1
            goto L_0x00bf
        L_0x00d8:
            if (r8 != 0) goto L_0x00dc
            r10 = 0
            return r10
        L_0x00dc:
            goto L_0x00e2
        L_0x00dd:
            if (r6 == 0) goto L_0x00dc
            r0.advancePeekPosition(r6)
        L_0x00e2:
            r5 = -1
            goto L_0x0021
        L_0x00e6:
            r10 = 0
        L_0x00e7:
            r9 = 1
            goto L_0x00ea
        L_0x00e9:
            r10 = 0
        L_0x00ea:
            if (r8 == 0) goto L_0x00f3
            r5 = r24
            if (r5 != r9) goto L_0x00f5
            r18 = 1
            goto L_0x00f7
        L_0x00f3:
            r5 = r24
        L_0x00f5:
            r18 = 0
        L_0x00f7:
            return r18
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp4.Sniffer.sniffInternal(com.google.android.exoplayer2.extractor.ExtractorInput, boolean):boolean");
    }

    private static boolean isCompatibleBrand(int brand) {
        if ((brand >>> 8) == Util.getIntegerCodeForString("3gp")) {
            return true;
        }
        for (int compatibleBrand : COMPATIBLE_BRANDS) {
            if (compatibleBrand == brand) {
                return true;
            }
        }
        return false;
    }
}
