package com.google.android.gms.feedback;

import android.os.Parcelable;

import com.google.android.gms.common.internal.Hide;

@Hide
/* compiled from: FeedbackOptionsCreator */
public final class zzh implements Parcelable.Creator<FeedbackOptions> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new FeedbackOptions[i];
    }

    /* JADX WARN: Type inference failed for: r2v3, types: [android.os.Parcelable] */
    /* JADX WARN: Type inference failed for: r2v4, types: [android.os.Parcelable] */
    /* JADX WARN: Type inference failed for: r2v5, types: [android.os.Parcelable] */
    /* JADX WARN: Type inference failed for: r2v6, types: [android.os.Parcelable] */
    /* JADX WARN: Type inference failed for: r2v7, types: [android.os.Parcelable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object createFromParcel(android.os.Parcel r19) {
        /*
            r18 = this;
            r0 = r19
            int r1 = com.google.android.gms.internal.zzbkw.zza(r19)
            r2 = 0
            r3 = 0
            r5 = r3
            r6 = r5
            r7 = r6
            r8 = r7
            r9 = r8
            r10 = r9
            r11 = r10
            r12 = r11
            r14 = r12
            r15 = r14
            r17 = r15
            r13 = 0
            r16 = 0
        L_0x0023:
            int r2 = r19.dataPosition()
            if (r2 >= r1) goto L_0x009f
            int r2 = r19.readInt()
            r3 = 65535(0xffff, float:9.1834E-41)
            r3 = r3 & r2
            switch(r3) {
                case 2: goto L_0x0099;
                case 3: goto L_0x0093;
                case 4: goto L_0x0037;
                case 5: goto L_0x008d;
                case 6: goto L_0x0083;
                case 7: goto L_0x007d;
                case 8: goto L_0x0073;
                case 9: goto L_0x006d;
                case 10: goto L_0x0066;
                case 11: goto L_0x0060;
                case 12: goto L_0x0056;
                case 13: goto L_0x004c;
                case 14: goto L_0x0046;
                case 15: goto L_0x003b;
                default: goto L_0x0037;
            }
        L_0x0037:
            com.google.android.gms.internal.zzbkw.zzb(r0, r2)
            goto L_0x0023
        L_0x003b:
            android.os.Parcelable$Creator r3 = android.graphics.Bitmap.CREATOR
            android.os.Parcelable r2 = com.google.android.gms.internal.zzbkw.zza(r0, r2, r3)
            r17 = r2
            android.graphics.Bitmap r17 = (android.graphics.Bitmap) r17
            goto L_0x0023
        L_0x0046:
            boolean r16 = com.google.android.gms.internal.zzbkw.zzc(r0, r2)
            goto L_0x0023
        L_0x004c:
            android.os.Parcelable$Creator<com.google.android.gms.feedback.LogOptions> r3 = com.google.android.gms.feedback.LogOptions.CREATOR
            android.os.Parcelable r2 = com.google.android.gms.internal.zzbkw.zza(r0, r2, r3)
            r15 = r2
            com.google.android.gms.feedback.LogOptions r15 = (com.google.android.gms.feedback.LogOptions) r15
            goto L_0x0023
        L_0x0056:
            android.os.Parcelable$Creator<com.google.android.gms.feedback.ThemeSettings> r3 = com.google.android.gms.feedback.ThemeSettings.CREATOR
            android.os.Parcelable r2 = com.google.android.gms.internal.zzbkw.zza(r0, r2, r3)
            r14 = r2
            com.google.android.gms.feedback.ThemeSettings r14 = (com.google.android.gms.feedback.ThemeSettings) r14
            goto L_0x0023
        L_0x0060:
            boolean r13 = com.google.android.gms.internal.zzbkw.zzc(r0, r2)
            goto L_0x0023
        L_0x0066:
            android.os.Parcelable$Creator<com.google.android.gms.feedback.FileTeleporter> r3 = com.google.android.gms.feedback.FileTeleporter.CREATOR
            java.util.ArrayList r12 = com.google.android.gms.internal.zzbkw.zzc(r0, r2, r3)
            goto L_0x0023
        L_0x006d:
            java.lang.String r11 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x0023
        L_0x0073:
            android.os.Parcelable$Creator<com.google.android.gms.common.data.BitmapTeleporter> r3 = com.google.android.gms.common.data.BitmapTeleporter.CREATOR
            android.os.Parcelable r2 = com.google.android.gms.internal.zzbkw.zza(r0, r2, r3)
            r10 = r2
            com.google.android.gms.common.data.BitmapTeleporter r10 = (com.google.android.gms.common.data.BitmapTeleporter) r10
            goto L_0x0023
        L_0x007d:
            java.lang.String r9 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x0023
        L_0x0083:
            android.os.Parcelable$Creator r3 = android.app.ApplicationErrorReport.CREATOR
            android.os.Parcelable r2 = com.google.android.gms.internal.zzbkw.zza(r0, r2, r3)
            r8 = r2
            android.app.ApplicationErrorReport r8 = (android.app.ApplicationErrorReport) r8
            goto L_0x0023
        L_0x008d:
            java.lang.String r7 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x0023
        L_0x0093:
            android.os.Bundle r6 = com.google.android.gms.internal.zzbkw.zzs(r0, r2)
            goto L_0x0023
        L_0x0099:
            java.lang.String r5 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x0023
        L_0x009f:
            com.google.android.gms.internal.zzbkw.zzae(r0, r1)
            com.google.android.gms.feedback.FeedbackOptions r0 = new com.google.android.gms.feedback.FeedbackOptions
            r4 = r0
            r4.<init>(r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.feedback.zzh.createFromParcel(android.os.Parcel):java.lang.Object");
    }
}
