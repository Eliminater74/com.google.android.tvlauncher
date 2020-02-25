package com.google.android.gms.internal;

/* compiled from: Utf8 */
final class zzgrp extends zzgrm {
    zzgrp() {
    }

    private static int zza(byte[] bArr, int i, long j, int i2) {
        if (i2 == 0) {
            return zzgrl.zzb(i);
        }
        if (i2 == 1) {
            return zzgrl.zzb(i, zzgrj.zza(bArr, j));
        }
        if (i2 == 2) {
            return zzgrl.zzb(i, zzgrj.zza(bArr, j), zzgrj.zza(bArr, j + 1));
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0063, code lost:
        return -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00bf, code lost:
        return -1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int zza(int r16, byte[] r17, int r18, int r19) {
        /*
            r15 = this;
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r1 | r2
            int r4 = r0.length
            int r4 = r4 - r2
            r3 = r3 | r4
            r4 = 2
            r5 = 3
            r6 = 0
            if (r3 < 0) goto L_0x00c0
            long r7 = (long) r1
            long r1 = (long) r2
            long r1 = r1 - r7
            int r2 = (int) r1
            r1 = 16
            r9 = 1
            if (r2 >= r1) goto L_0x001d
            r1 = 0
            goto L_0x002f
        L_0x001d:
            r11 = r7
            r1 = 0
        L_0x001f:
            if (r1 >= r2) goto L_0x002e
            long r13 = r11 + r9
            byte r3 = com.google.android.gms.internal.zzgrj.zza(r0, r11)
            if (r3 >= 0) goto L_0x002a
            goto L_0x002f
        L_0x002a:
            int r1 = r1 + 1
            r11 = r13
            goto L_0x001f
        L_0x002e:
            r1 = r2
        L_0x002f:
            int r2 = r2 - r1
            long r11 = (long) r1
            long r7 = r7 + r11
        L_0x0033:
            r1 = 0
        L_0x0034:
            if (r2 <= 0) goto L_0x0042
            long r11 = r7 + r9
            byte r1 = com.google.android.gms.internal.zzgrj.zza(r0, r7)
            if (r1 < 0) goto L_0x0043
            int r2 = r2 + -1
            r7 = r11
            goto L_0x0034
        L_0x0042:
            r11 = r7
        L_0x0043:
            if (r2 != 0) goto L_0x0046
            return r6
        L_0x0046:
            int r2 = r2 + -1
            r3 = -32
            r7 = -65
            r8 = -1
            if (r1 >= r3) goto L_0x0064
            if (r2 != 0) goto L_0x0052
            return r1
        L_0x0052:
            int r2 = r2 + -1
            r3 = -62
            if (r1 < r3) goto L_0x0063
            long r13 = r11 + r9
            byte r1 = com.google.android.gms.internal.zzgrj.zza(r0, r11)
            if (r1 <= r7) goto L_0x0061
            goto L_0x0063
        L_0x0061:
            r7 = r13
            goto L_0x00bd
        L_0x0063:
            return r8
        L_0x0064:
            r13 = -16
            if (r1 >= r13) goto L_0x0091
            if (r2 >= r4) goto L_0x006f
            int r0 = zza(r0, r1, r11, r2)
            return r0
        L_0x006f:
            int r2 = r2 + -2
            long r13 = r11 + r9
            byte r11 = com.google.android.gms.internal.zzgrj.zza(r0, r11)
            if (r11 > r7) goto L_0x0090
            r12 = -96
            if (r1 != r3) goto L_0x007f
            if (r11 < r12) goto L_0x0090
        L_0x007f:
            r3 = -19
            if (r1 != r3) goto L_0x0085
            if (r11 >= r12) goto L_0x0090
        L_0x0085:
            long r11 = r13 + r9
            byte r1 = com.google.android.gms.internal.zzgrj.zza(r0, r13)
            if (r1 <= r7) goto L_0x008e
            goto L_0x0090
        L_0x008e:
            r7 = r11
            goto L_0x0033
        L_0x0090:
            return r8
        L_0x0091:
            if (r2 >= r5) goto L_0x0098
            int r0 = zza(r0, r1, r11, r2)
            return r0
        L_0x0098:
            int r2 = r2 + -3
            long r13 = r11 + r9
            byte r3 = com.google.android.gms.internal.zzgrj.zza(r0, r11)
            if (r3 > r7) goto L_0x00bf
            int r1 = r1 << 28
            int r3 = r3 + 112
            int r1 = r1 + r3
            int r1 = r1 >> 30
            if (r1 != 0) goto L_0x00bf
            long r11 = r13 + r9
            byte r1 = com.google.android.gms.internal.zzgrj.zza(r0, r13)
            if (r1 > r7) goto L_0x00bf
            long r13 = r11 + r9
            byte r1 = com.google.android.gms.internal.zzgrj.zza(r0, r11)
            if (r1 <= r7) goto L_0x00bc
            goto L_0x00bf
        L_0x00bc:
            r7 = r13
        L_0x00bd:
            goto L_0x0033
        L_0x00bf:
            return r8
        L_0x00c0:
            java.lang.ArrayIndexOutOfBoundsException r3 = new java.lang.ArrayIndexOutOfBoundsException
            java.lang.Object[] r5 = new java.lang.Object[r5]
            int r0 = r0.length
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r5[r6] = r0
            java.lang.Integer r0 = java.lang.Integer.valueOf(r18)
            r1 = 1
            r5[r1] = r0
            java.lang.Integer r0 = java.lang.Integer.valueOf(r19)
            r5[r4] = r0
            java.lang.String r0 = "Array length=%d, index=%d, limit=%d"
            java.lang.String r0 = java.lang.String.format(r0, r5)
            r3.<init>(r0)
            throw r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgrp.zza(int, byte[], int, int):int");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0033 A[LOOP:1: B:13:0x0033->B:37:0x00fd, LOOP_START, PHI: r2 r3 r4 r11
      PHI: (r2v4 int) = (r2v3 int), (r2v6 int) binds: [B:10:0x002f, B:37:0x00fd] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r3v3 char) = (r3v2 char), (r3v4 char) binds: [B:10:0x002f, B:37:0x00fd] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r4v3 long) = (r4v2 long), (r4v5 long) binds: [B:10:0x002f, B:37:0x00fd] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r11v3 long) = (r11v2 long), (r11v4 long) binds: [B:10:0x002f, B:37:0x00fd] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int zza(java.lang.CharSequence r23, byte[] r24, int r25, int r26) {
        /*
            r22 = this;
            r0 = r23
            r1 = r24
            r2 = r25
            r3 = r26
            long r4 = (long) r2
            long r6 = (long) r3
            long r6 = r6 + r4
            int r8 = r23.length()
            java.lang.String r9 = " at index "
            java.lang.String r10 = "Failed writing "
            if (r8 > r3) goto L_0x0148
            int r11 = r1.length
            int r11 = r11 - r3
            if (r11 < r2) goto L_0x0148
            r2 = 0
        L_0x001a:
            r3 = 128(0x80, float:1.794E-43)
            r11 = 1
            if (r2 >= r8) goto L_0x002f
            char r13 = r0.charAt(r2)
            if (r13 >= r3) goto L_0x002f
            long r11 = r11 + r4
            byte r3 = (byte) r13
            com.google.android.gms.internal.zzgrj.zza(r1, r4, r3)
            int r2 = r2 + 1
            r4 = r11
            goto L_0x001a
        L_0x002f:
            if (r2 != r8) goto L_0x0033
            int r0 = (int) r4
            return r0
        L_0x0033:
            if (r2 >= r8) goto L_0x0146
            char r13 = r0.charAt(r2)
            if (r13 >= r3) goto L_0x004b
            int r14 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r14 >= 0) goto L_0x004b
            long r14 = r4 + r11
            byte r13 = (byte) r13
            com.google.android.gms.internal.zzgrj.zza(r1, r4, r13)
            r4 = r11
            r12 = r14
            r11 = 128(0x80, float:1.794E-43)
            goto L_0x00fd
        L_0x004b:
            r14 = 2048(0x800, float:2.87E-42)
            if (r13 >= r14) goto L_0x0075
            r14 = 2
            long r14 = r6 - r14
            int r16 = (r4 > r14 ? 1 : (r4 == r14 ? 0 : -1))
            if (r16 > 0) goto L_0x0075
            long r14 = r4 + r11
            int r3 = r13 >>> 6
            r3 = r3 | 960(0x3c0, float:1.345E-42)
            byte r3 = (byte) r3
            com.google.android.gms.internal.zzgrj.zza(r1, r4, r3)
            long r3 = r14 + r11
            r5 = r13 & 63
            r13 = 128(0x80, float:1.794E-43)
            r5 = r5 | r13
            byte r5 = (byte) r5
            com.google.android.gms.internal.zzgrj.zza(r1, r14, r5)
            r20 = r11
            r11 = 128(0x80, float:1.794E-43)
            r12 = r3
            r4 = r20
            goto L_0x00fd
        L_0x0075:
            r3 = 57343(0xdfff, float:8.0355E-41)
            r14 = 55296(0xd800, float:7.7486E-41)
            if (r13 < r14) goto L_0x007f
            if (r3 >= r13) goto L_0x00b0
        L_0x007f:
            r15 = 3
            long r15 = r6 - r15
            int r17 = (r4 > r15 ? 1 : (r4 == r15 ? 0 : -1))
            if (r17 > 0) goto L_0x00b0
            long r14 = r4 + r11
            int r3 = r13 >>> 12
            r3 = r3 | 480(0x1e0, float:6.73E-43)
            byte r3 = (byte) r3
            com.google.android.gms.internal.zzgrj.zza(r1, r4, r3)
            long r3 = r14 + r11
            int r5 = r13 >>> 6
            r5 = r5 & 63
            r11 = 128(0x80, float:1.794E-43)
            r5 = r5 | r11
            byte r5 = (byte) r5
            com.google.android.gms.internal.zzgrj.zza(r1, r14, r5)
            r14 = 1
            long r18 = r3 + r14
            r5 = r13 & 63
            r5 = r5 | r11
            byte r5 = (byte) r5
            com.google.android.gms.internal.zzgrj.zza(r1, r3, r5)
            r12 = r18
            r4 = 1
            r11 = 128(0x80, float:1.794E-43)
            goto L_0x00fd
        L_0x00b0:
            r11 = 4
            long r11 = r6 - r11
            int r15 = (r4 > r11 ? 1 : (r4 == r11 ? 0 : -1))
            if (r15 > 0) goto L_0x0111
            int r3 = r2 + 1
            if (r3 == r8) goto L_0x0109
            char r2 = r0.charAt(r3)
            boolean r11 = java.lang.Character.isSurrogatePair(r13, r2)
            if (r11 == 0) goto L_0x0108
            int r2 = java.lang.Character.toCodePoint(r13, r2)
            r11 = 1
            long r13 = r4 + r11
            int r15 = r2 >>> 18
            r15 = r15 | 240(0xf0, float:3.36E-43)
            byte r15 = (byte) r15
            com.google.android.gms.internal.zzgrj.zza(r1, r4, r15)
            long r4 = r13 + r11
            int r15 = r2 >>> 12
            r15 = r15 & 63
            r11 = 128(0x80, float:1.794E-43)
            r12 = r15 | 128(0x80, float:1.794E-43)
            byte r12 = (byte) r12
            com.google.android.gms.internal.zzgrj.zza(r1, r13, r12)
            r12 = 1
            long r14 = r4 + r12
            int r16 = r2 >>> 6
            r12 = r16 & 63
            r12 = r12 | r11
            byte r12 = (byte) r12
            com.google.android.gms.internal.zzgrj.zza(r1, r4, r12)
            r4 = 1
            long r12 = r14 + r4
            r2 = r2 & 63
            r2 = r2 | r11
            byte r2 = (byte) r2
            com.google.android.gms.internal.zzgrj.zza(r1, r14, r2)
            r2 = r3
        L_0x00fd:
            int r2 = r2 + 1
            r3 = 128(0x80, float:1.794E-43)
            r20 = r4
            r4 = r12
            r11 = r20
            goto L_0x0033
        L_0x0108:
            r2 = r3
        L_0x0109:
            com.google.android.gms.internal.zzgro r0 = new com.google.android.gms.internal.zzgro
            int r2 = r2 + -1
            r0.<init>(r2, r8)
            throw r0
        L_0x0111:
            if (r14 > r13) goto L_0x0129
            if (r13 > r3) goto L_0x0129
            int r1 = r2 + 1
            if (r1 == r8) goto L_0x0123
            char r0 = r0.charAt(r1)
            boolean r0 = java.lang.Character.isSurrogatePair(r13, r0)
            if (r0 != 0) goto L_0x0129
        L_0x0123:
            com.google.android.gms.internal.zzgro r0 = new com.google.android.gms.internal.zzgro
            r0.<init>(r2, r8)
            throw r0
        L_0x0129:
            java.lang.ArrayIndexOutOfBoundsException r0 = new java.lang.ArrayIndexOutOfBoundsException
            r1 = 46
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r1)
            r2.append(r10)
            r2.append(r13)
            r2.append(r9)
            r2.append(r4)
            java.lang.String r1 = r2.toString()
            r0.<init>(r1)
            throw r0
        L_0x0146:
            int r0 = (int) r4
            return r0
        L_0x0148:
            java.lang.ArrayIndexOutOfBoundsException r1 = new java.lang.ArrayIndexOutOfBoundsException
            int r8 = r8 + -1
            char r0 = r0.charAt(r8)
            int r2 = r2 + r3
            r3 = 37
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>(r3)
            r4.append(r10)
            r4.append(r0)
            r4.append(r9)
            r4.append(r2)
            java.lang.String r0 = r4.toString()
            r1.<init>(r0)
            throw r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgrp.zza(java.lang.CharSequence, byte[], int, int):int");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0041 A[LOOP:1: B:11:0x0041->B:35:0x0102, LOOP_START, PHI: r2 r4 r9 r10
      PHI: (r2v2 long) = (r2v0 long), (r2v3 long) binds: [B:8:0x0039, B:35:0x0102] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r4v5 long) = (r4v4 long), (r4v7 long) binds: [B:8:0x0039, B:35:0x0102] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r9v3 int) = (r9v2 int), (r9v5 int) binds: [B:8:0x0039, B:35:0x0102] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r10v1 char) = (r10v0 char), (r10v2 char) binds: [B:8:0x0039, B:35:0x0102] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x003b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(java.lang.CharSequence r22, java.nio.ByteBuffer r23) {
        /*
            r21 = this;
            r0 = r22
            r1 = r23
            long r2 = com.google.android.gms.internal.zzgrj.zza(r23)
            int r4 = r23.position()
            long r4 = (long) r4
            long r4 = r4 + r2
            int r6 = r23.limit()
            long r6 = (long) r6
            long r6 = r6 + r2
            int r8 = r22.length()
            long r9 = (long) r8
            long r11 = r6 - r4
            java.lang.String r13 = " at index "
            java.lang.String r14 = "Failed writing "
            int r15 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r15 > 0) goto L_0x0155
            r9 = 0
        L_0x0024:
            r10 = 128(0x80, float:1.794E-43)
            r11 = 1
            if (r9 >= r8) goto L_0x0039
            char r15 = r0.charAt(r9)
            if (r15 >= r10) goto L_0x0039
            long r11 = r11 + r4
            byte r10 = (byte) r15
            com.google.android.gms.internal.zzgrj.zza(r4, r10)
            int r9 = r9 + 1
            r4 = r11
            goto L_0x0024
        L_0x0039:
            if (r9 != r8) goto L_0x0041
            long r4 = r4 - r2
            int r0 = (int) r4
            r1.position(r0)
            return
        L_0x0041:
            if (r9 >= r8) goto L_0x014a
            char r15 = r0.charAt(r9)
            if (r15 >= r10) goto L_0x005b
            int r16 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r16 >= 0) goto L_0x005b
            long r16 = r4 + r11
            byte r15 = (byte) r15
            com.google.android.gms.internal.zzgrj.zza(r4, r15)
            r4 = r16
            r15 = 128(0x80, float:1.794E-43)
            r17 = r2
            goto L_0x0102
        L_0x005b:
            r10 = 2048(0x800, float:2.87E-42)
            if (r15 >= r10) goto L_0x0083
            r17 = 2
            long r17 = r6 - r17
            int r10 = (r4 > r17 ? 1 : (r4 == r17 ? 0 : -1))
            if (r10 > 0) goto L_0x0083
            r17 = r2
            long r1 = r4 + r11
            int r3 = r15 >>> 6
            r3 = r3 | 960(0x3c0, float:1.345E-42)
            byte r3 = (byte) r3
            com.google.android.gms.internal.zzgrj.zza(r4, r3)
            long r3 = r1 + r11
            r5 = r15 & 63
            r10 = 128(0x80, float:1.794E-43)
            r5 = r5 | r10
            byte r5 = (byte) r5
            com.google.android.gms.internal.zzgrj.zza(r1, r5)
            r4 = r3
            r15 = 128(0x80, float:1.794E-43)
            goto L_0x0102
        L_0x0083:
            r17 = r2
            r1 = 57343(0xdfff, float:8.0355E-41)
            r2 = 55296(0xd800, float:7.7486E-41)
            if (r15 < r2) goto L_0x008f
            if (r1 >= r15) goto L_0x00bb
        L_0x008f:
            r19 = 3
            long r19 = r6 - r19
            int r3 = (r4 > r19 ? 1 : (r4 == r19 ? 0 : -1))
            if (r3 > 0) goto L_0x00bb
            long r1 = r4 + r11
            int r3 = r15 >>> 12
            r3 = r3 | 480(0x1e0, float:6.73E-43)
            byte r3 = (byte) r3
            com.google.android.gms.internal.zzgrj.zza(r4, r3)
            long r3 = r1 + r11
            int r5 = r15 >>> 6
            r5 = r5 & 63
            r10 = 128(0x80, float:1.794E-43)
            r5 = r5 | r10
            byte r5 = (byte) r5
            com.google.android.gms.internal.zzgrj.zza(r1, r5)
            long r1 = r3 + r11
            r5 = r15 & 63
            r5 = r5 | r10
            byte r5 = (byte) r5
            com.google.android.gms.internal.zzgrj.zza(r3, r5)
            r4 = r1
            r15 = 128(0x80, float:1.794E-43)
            goto L_0x0102
        L_0x00bb:
            r19 = 4
            long r19 = r6 - r19
            int r3 = (r4 > r19 ? 1 : (r4 == r19 ? 0 : -1))
            if (r3 > 0) goto L_0x0115
            int r1 = r9 + 1
            if (r1 == r8) goto L_0x010c
            char r2 = r0.charAt(r1)
            boolean r3 = java.lang.Character.isSurrogatePair(r15, r2)
            if (r3 == 0) goto L_0x010d
            int r2 = java.lang.Character.toCodePoint(r15, r2)
            long r9 = r4 + r11
            int r3 = r2 >>> 18
            r3 = r3 | 240(0xf0, float:3.36E-43)
            byte r3 = (byte) r3
            com.google.android.gms.internal.zzgrj.zza(r4, r3)
            long r3 = r9 + r11
            int r5 = r2 >>> 12
            r5 = r5 & 63
            r15 = 128(0x80, float:1.794E-43)
            r5 = r5 | r15
            byte r5 = (byte) r5
            com.google.android.gms.internal.zzgrj.zza(r9, r5)
            long r9 = r3 + r11
            int r5 = r2 >>> 6
            r5 = r5 & 63
            r5 = r5 | r15
            byte r5 = (byte) r5
            com.google.android.gms.internal.zzgrj.zza(r3, r5)
            long r3 = r9 + r11
            r2 = r2 & 63
            r2 = r2 | r15
            byte r2 = (byte) r2
            com.google.android.gms.internal.zzgrj.zza(r9, r2)
            r9 = r1
            r4 = r3
        L_0x0102:
            int r9 = r9 + 1
            r1 = r23
            r2 = r17
            r10 = 128(0x80, float:1.794E-43)
            goto L_0x0041
        L_0x010c:
            r1 = r9
        L_0x010d:
            com.google.android.gms.internal.zzgro r0 = new com.google.android.gms.internal.zzgro
            int r1 = r1 + -1
            r0.<init>(r1, r8)
            throw r0
        L_0x0115:
            if (r2 > r15) goto L_0x012d
            if (r15 > r1) goto L_0x012d
            int r1 = r9 + 1
            if (r1 == r8) goto L_0x0127
            char r0 = r0.charAt(r1)
            boolean r0 = java.lang.Character.isSurrogatePair(r15, r0)
            if (r0 != 0) goto L_0x012d
        L_0x0127:
            com.google.android.gms.internal.zzgro r0 = new com.google.android.gms.internal.zzgro
            r0.<init>(r9, r8)
            throw r0
        L_0x012d:
            java.lang.ArrayIndexOutOfBoundsException r0 = new java.lang.ArrayIndexOutOfBoundsException
            r1 = 46
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r1)
            r2.append(r14)
            r2.append(r15)
            r2.append(r13)
            r2.append(r4)
            java.lang.String r1 = r2.toString()
            r0.<init>(r1)
            throw r0
        L_0x014a:
            r17 = r2
            long r4 = r4 - r17
            int r0 = (int) r4
            r1 = r23
            r1.position(r0)
            return
        L_0x0155:
            java.lang.ArrayIndexOutOfBoundsException r2 = new java.lang.ArrayIndexOutOfBoundsException
            int r8 = r8 + -1
            char r0 = r0.charAt(r8)
            int r1 = r23.limit()
            r3 = 37
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>(r3)
            r4.append(r14)
            r4.append(r0)
            r4.append(r13)
            r4.append(r1)
            java.lang.String r0 = r4.toString()
            r2.<init>(r0)
            throw r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgrp.zza(java.lang.CharSequence, java.nio.ByteBuffer):void");
    }
}
