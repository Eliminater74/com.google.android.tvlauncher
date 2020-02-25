package com.google.android.gms.common.server.response;

import android.os.Parcelable;

import com.google.android.gms.common.internal.Hide;

@Hide
public class FieldCreator implements Parcelable.Creator<FastJsonResponse.Field> {
    public static final int CONTENT_DESCRIPTION = 0;

    /* JADX WARN: Type inference failed for: r1v3, types: [android.os.Parcelable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.gms.common.server.response.FastJsonResponse.Field createFromParcel(android.os.Parcel r14) {
        /*
            r13 = this;
            int r0 = com.google.android.gms.internal.zzbkw.zza(r14)
            r1 = 0
            r2 = 0
            r9 = r1
            r11 = r9
            r12 = r11
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r10 = 0
        L_0x0017:
            int r1 = r14.dataPosition()
            if (r1 >= r0) goto L_0x0069
            int r1 = r14.readInt()
            r2 = 65535(0xffff, float:9.1834E-41)
            r2 = r2 & r1
            switch(r2) {
                case 1: goto L_0x0063;
                case 2: goto L_0x005d;
                case 3: goto L_0x0057;
                case 4: goto L_0x0051;
                case 5: goto L_0x004b;
                case 6: goto L_0x0045;
                case 7: goto L_0x003f;
                case 8: goto L_0x0039;
                case 9: goto L_0x002f;
                default: goto L_0x002b;
            }
        L_0x002b:
            com.google.android.gms.internal.zzbkw.zzb(r14, r1)
            goto L_0x0017
        L_0x002f:
            android.os.Parcelable$Creator<com.google.android.gms.internal.zzbly> r2 = com.google.android.gms.internal.zzbly.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.internal.zzbkw.zza(r14, r1, r2)
            r12 = r1
            com.google.android.gms.internal.zzbly r12 = (com.google.android.gms.internal.zzbly) r12
            goto L_0x0017
        L_0x0039:
            java.lang.String r11 = com.google.android.gms.internal.zzbkw.zzq(r14, r1)
            goto L_0x0017
        L_0x003f:
            int r10 = com.google.android.gms.internal.zzbkw.zzg(r14, r1)
            goto L_0x0017
        L_0x0045:
            java.lang.String r9 = com.google.android.gms.internal.zzbkw.zzq(r14, r1)
            goto L_0x0017
        L_0x004b:
            boolean r8 = com.google.android.gms.internal.zzbkw.zzc(r14, r1)
            goto L_0x0017
        L_0x0051:
            int r7 = com.google.android.gms.internal.zzbkw.zzg(r14, r1)
            goto L_0x0017
        L_0x0057:
            boolean r6 = com.google.android.gms.internal.zzbkw.zzc(r14, r1)
            goto L_0x0017
        L_0x005d:
            int r5 = com.google.android.gms.internal.zzbkw.zzg(r14, r1)
            goto L_0x0017
        L_0x0063:
            int r4 = com.google.android.gms.internal.zzbkw.zzg(r14, r1)
            goto L_0x0017
        L_0x0069:
            com.google.android.gms.internal.zzbkw.zzae(r14, r0)
            com.google.android.gms.common.server.response.FastJsonResponse$Field r14 = new com.google.android.gms.common.server.response.FastJsonResponse$Field
            r3 = r14
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11, r12)
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.server.response.FieldCreator.createFromParcel(android.os.Parcel):com.google.android.gms.common.server.response.FastJsonResponse$Field");
    }

    public FastJsonResponse.Field[] newArray(int i) {
        return new FastJsonResponse.Field[i];
    }
}
