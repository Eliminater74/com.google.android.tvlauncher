package com.google.android.gms.feedback;

import android.os.Parcelable;
import com.google.android.gms.common.internal.Hide;

@Hide
/* compiled from: ErrorReportCreator */
public final class zza implements Parcelable.Creator<ErrorReport> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new ErrorReport[i];
    }

    /* JADX WARN: Type inference failed for: r2v3, types: [android.os.Parcelable] */
    /* JADX WARN: Type inference failed for: r2v4, types: [android.os.Parcelable] */
    /* JADX WARN: Type inference failed for: r2v5, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r2v6, types: [android.os.Parcelable] */
    /* JADX WARN: Type inference failed for: r2v7, types: [android.os.Parcelable] */
    /* JADX WARN: Type inference failed for: r2v8, types: [android.os.Parcelable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object createFromParcel(android.os.Parcel r64) {
        /*
            r63 = this;
            r0 = r64
            int r1 = com.google.android.gms.internal.zzbkw.zza(r64)
            r2 = 0
            r3 = 0
            r5 = r3
            r6 = r5
            r8 = r6
            r9 = r8
            r10 = r9
            r11 = r10
            r12 = r11
            r13 = r12
            r14 = r13
            r16 = r14
            r17 = r16
            r18 = r17
            r19 = r18
            r20 = r19
            r21 = r20
            r22 = r21
            r23 = r22
            r24 = r23
            r25 = r24
            r26 = r25
            r31 = r26
            r32 = r31
            r33 = r32
            r34 = r33
            r39 = r34
            r40 = r39
            r42 = r40
            r43 = r42
            r44 = r43
            r45 = r44
            r46 = r45
            r47 = r46
            r48 = r47
            r49 = r48
            r50 = r49
            r51 = r50
            r52 = r51
            r54 = r52
            r55 = r54
            r56 = r55
            r57 = r56
            r59 = r57
            r60 = r59
            r62 = r60
            r7 = 0
            r15 = 0
            r27 = 0
            r28 = 0
            r29 = 0
            r30 = 0
            r35 = 0
            r36 = 0
            r37 = 0
            r38 = 0
            r41 = 0
            r53 = 0
            r58 = 0
            r61 = 0
        L_0x00aa:
            int r2 = r64.dataPosition()
            if (r2 >= r1) goto L_0x0269
            int r2 = r64.readInt()
            r3 = 65535(0xffff, float:9.1834E-41)
            r3 = r3 & r2
            switch(r3) {
                case 2: goto L_0x025e;
                case 3: goto L_0x0257;
                case 4: goto L_0x0250;
                case 5: goto L_0x0249;
                case 6: goto L_0x0242;
                case 7: goto L_0x023b;
                case 8: goto L_0x0234;
                case 9: goto L_0x022d;
                case 10: goto L_0x0226;
                case 11: goto L_0x021f;
                case 12: goto L_0x0218;
                case 13: goto L_0x0211;
                case 14: goto L_0x020a;
                case 15: goto L_0x0203;
                case 16: goto L_0x01fc;
                case 17: goto L_0x01f5;
                case 18: goto L_0x01ee;
                case 19: goto L_0x01e7;
                case 20: goto L_0x01e0;
                case 21: goto L_0x01d9;
                case 22: goto L_0x01d2;
                case 23: goto L_0x01cb;
                case 24: goto L_0x01c4;
                case 25: goto L_0x01bd;
                case 26: goto L_0x01b6;
                case 27: goto L_0x01af;
                case 28: goto L_0x01a8;
                case 29: goto L_0x01a1;
                case 30: goto L_0x019a;
                case 31: goto L_0x0193;
                case 32: goto L_0x018c;
                case 33: goto L_0x0185;
                case 34: goto L_0x017e;
                case 35: goto L_0x0177;
                case 36: goto L_0x0170;
                case 37: goto L_0x0169;
                case 38: goto L_0x0162;
                case 39: goto L_0x015b;
                case 40: goto L_0x0154;
                case 41: goto L_0x014d;
                case 42: goto L_0x0146;
                case 43: goto L_0x013f;
                case 44: goto L_0x0138;
                case 45: goto L_0x0131;
                case 46: goto L_0x0125;
                case 47: goto L_0x011f;
                case 48: goto L_0x0114;
                case 49: goto L_0x010e;
                case 50: goto L_0x0108;
                case 51: goto L_0x0102;
                case 52: goto L_0x00f7;
                case 53: goto L_0x00ec;
                case 54: goto L_0x00e6;
                case 55: goto L_0x00e0;
                case 56: goto L_0x00da;
                case 57: goto L_0x00d3;
                case 58: goto L_0x00cd;
                case 59: goto L_0x00c2;
                default: goto L_0x00be;
            }
        L_0x00be:
            com.google.android.gms.internal.zzbkw.zzb(r0, r2)
            goto L_0x00aa
        L_0x00c2:
            android.os.Parcelable$Creator r3 = android.graphics.Bitmap.CREATOR
            android.os.Parcelable r2 = com.google.android.gms.internal.zzbkw.zza(r0, r2, r3)
            r62 = r2
            android.graphics.Bitmap r62 = (android.graphics.Bitmap) r62
            goto L_0x00aa
        L_0x00cd:
            boolean r61 = com.google.android.gms.internal.zzbkw.zzc(r0, r2)
            goto L_0x00aa
        L_0x00d3:
            android.os.Parcelable$Creator r3 = android.graphics.RectF.CREATOR
            java.util.ArrayList r60 = com.google.android.gms.internal.zzbkw.zzc(r0, r2, r3)
            goto L_0x00aa
        L_0x00da:
            android.os.Bundle r59 = com.google.android.gms.internal.zzbkw.zzs(r0, r2)
            goto L_0x00aa
        L_0x00e0:
            boolean r58 = com.google.android.gms.internal.zzbkw.zzc(r0, r2)
            goto L_0x00aa
        L_0x00e6:
            java.lang.String r57 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x00ec:
            android.os.Parcelable$Creator<com.google.android.gms.feedback.LogOptions> r3 = com.google.android.gms.feedback.LogOptions.CREATOR
            android.os.Parcelable r2 = com.google.android.gms.internal.zzbkw.zza(r0, r2, r3)
            r56 = r2
            com.google.android.gms.feedback.LogOptions r56 = (com.google.android.gms.feedback.LogOptions) r56
            goto L_0x00aa
        L_0x00f7:
            android.os.Parcelable$Creator<com.google.android.gms.feedback.ThemeSettings> r3 = com.google.android.gms.feedback.ThemeSettings.CREATOR
            android.os.Parcelable r2 = com.google.android.gms.internal.zzbkw.zza(r0, r2, r3)
            r55 = r2
            com.google.android.gms.feedback.ThemeSettings r55 = (com.google.android.gms.feedback.ThemeSettings) r55
            goto L_0x00aa
        L_0x0102:
            java.lang.String r54 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x0108:
            boolean r53 = com.google.android.gms.internal.zzbkw.zzc(r0, r2)
            goto L_0x00aa
        L_0x010e:
            java.lang.String[] r52 = com.google.android.gms.internal.zzbkw.zzaa(r0, r2)
            goto L_0x00aa
        L_0x0114:
            android.os.Parcelable$Creator<com.google.android.gms.feedback.FileTeleporter> r3 = com.google.android.gms.feedback.FileTeleporter.CREATOR
            java.lang.Object[] r2 = com.google.android.gms.internal.zzbkw.zzb(r0, r2, r3)
            r51 = r2
            com.google.android.gms.feedback.FileTeleporter[] r51 = (com.google.android.gms.feedback.FileTeleporter[]) r51
            goto L_0x00aa
        L_0x011f:
            java.lang.String r50 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x0125:
            android.os.Parcelable$Creator<com.google.android.gms.common.data.BitmapTeleporter> r3 = com.google.android.gms.common.data.BitmapTeleporter.CREATOR
            android.os.Parcelable r2 = com.google.android.gms.internal.zzbkw.zza(r0, r2, r3)
            r49 = r2
            com.google.android.gms.common.data.BitmapTeleporter r49 = (com.google.android.gms.common.data.BitmapTeleporter) r49
            goto L_0x00aa
        L_0x0131:
            java.lang.String r48 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x0138:
            java.lang.String r47 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x013f:
            java.lang.String r46 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x0146:
            java.lang.String r45 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x014d:
            java.lang.String r44 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x0154:
            java.lang.String r43 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x015b:
            java.lang.String r42 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x0162:
            int r41 = com.google.android.gms.internal.zzbkw.zzg(r0, r2)
            goto L_0x00aa
        L_0x0169:
            java.lang.String r40 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x0170:
            java.lang.String r39 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x0177:
            boolean r38 = com.google.android.gms.internal.zzbkw.zzc(r0, r2)
            goto L_0x00aa
        L_0x017e:
            int r37 = com.google.android.gms.internal.zzbkw.zzg(r0, r2)
            goto L_0x00aa
        L_0x0185:
            int r36 = com.google.android.gms.internal.zzbkw.zzg(r0, r2)
            goto L_0x00aa
        L_0x018c:
            boolean r35 = com.google.android.gms.internal.zzbkw.zzc(r0, r2)
            goto L_0x00aa
        L_0x0193:
            android.os.Bundle r34 = com.google.android.gms.internal.zzbkw.zzs(r0, r2)
            goto L_0x00aa
        L_0x019a:
            java.lang.String r33 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x01a1:
            java.lang.String r32 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x01a8:
            java.lang.String r31 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x01af:
            int r30 = com.google.android.gms.internal.zzbkw.zzg(r0, r2)
            goto L_0x00aa
        L_0x01b6:
            int r29 = com.google.android.gms.internal.zzbkw.zzg(r0, r2)
            goto L_0x00aa
        L_0x01bd:
            int r28 = com.google.android.gms.internal.zzbkw.zzg(r0, r2)
            goto L_0x00aa
        L_0x01c4:
            int r27 = com.google.android.gms.internal.zzbkw.zzg(r0, r2)
            goto L_0x00aa
        L_0x01cb:
            byte[] r26 = com.google.android.gms.internal.zzbkw.zzt(r0, r2)
            goto L_0x00aa
        L_0x01d2:
            java.lang.String r25 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x01d9:
            java.lang.String r24 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x01e0:
            java.lang.String[] r23 = com.google.android.gms.internal.zzbkw.zzaa(r0, r2)
            goto L_0x00aa
        L_0x01e7:
            java.lang.String[] r22 = com.google.android.gms.internal.zzbkw.zzaa(r0, r2)
            goto L_0x00aa
        L_0x01ee:
            java.lang.String[] r21 = com.google.android.gms.internal.zzbkw.zzaa(r0, r2)
            goto L_0x00aa
        L_0x01f5:
            java.lang.String r20 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x01fc:
            java.lang.String r19 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x0203:
            java.lang.String r18 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x020a:
            java.lang.String r17 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x0211:
            java.lang.String r16 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x0218:
            int r15 = com.google.android.gms.internal.zzbkw.zzg(r0, r2)
            goto L_0x00aa
        L_0x021f:
            java.lang.String r14 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x0226:
            java.lang.String r13 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x022d:
            java.lang.String r12 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x0234:
            java.lang.String r11 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x023b:
            java.lang.String r10 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x0242:
            java.lang.String r9 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x0249:
            java.lang.String r8 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x0250:
            int r7 = com.google.android.gms.internal.zzbkw.zzg(r0, r2)
            goto L_0x00aa
        L_0x0257:
            java.lang.String r6 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x00aa
        L_0x025e:
            android.os.Parcelable$Creator r3 = android.app.ApplicationErrorReport.CREATOR
            android.os.Parcelable r2 = com.google.android.gms.internal.zzbkw.zza(r0, r2, r3)
            r5 = r2
            android.app.ApplicationErrorReport r5 = (android.app.ApplicationErrorReport) r5
            goto L_0x00aa
        L_0x0269:
            com.google.android.gms.internal.zzbkw.zzae(r0, r1)
            com.google.android.gms.feedback.ErrorReport r0 = new com.google.android.gms.feedback.ErrorReport
            r4 = r0
            r4.<init>(r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32, r33, r34, r35, r36, r37, r38, r39, r40, r41, r42, r43, r44, r45, r46, r47, r48, r49, r50, r51, r52, r53, r54, r55, r56, r57, r58, r59, r60, r61, r62)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.feedback.zza.createFromParcel(android.os.Parcel):java.lang.Object");
    }
}
