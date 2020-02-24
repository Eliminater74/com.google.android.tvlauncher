package com.google.android.gms.auth.api.signin;

import android.os.Parcelable;
import com.google.android.gms.common.internal.Hide;

@Hide
/* compiled from: GoogleSignInAccountCreator */
public final class zzb implements Parcelable.Creator<GoogleSignInAccount> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new GoogleSignInAccount[i];
    }

    /* JADX WARN: Type inference failed for: r2v3, types: [android.os.Parcelable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object createFromParcel(android.os.Parcel r21) {
        /*
            r20 = this;
            r0 = r21
            int r1 = com.google.android.gms.internal.zzbkw.zza(r21)
            r2 = 0
            r3 = 0
            r4 = 0
            r8 = r2
            r9 = r8
            r10 = r9
            r11 = r10
            r12 = r11
            r13 = r12
            r16 = r13
            r17 = r16
            r18 = r17
            r19 = r18
            r14 = r4
            r7 = 0
        L_0x0025:
            int r2 = r21.dataPosition()
            if (r2 >= r1) goto L_0x008a
            int r2 = r21.readInt()
            r3 = 65535(0xffff, float:9.1834E-41)
            r3 = r3 & r2
            switch(r3) {
                case 1: goto L_0x0084;
                case 2: goto L_0x007e;
                case 3: goto L_0x0078;
                case 4: goto L_0x0072;
                case 5: goto L_0x006c;
                case 6: goto L_0x0062;
                case 7: goto L_0x005c;
                case 8: goto L_0x0056;
                case 9: goto L_0x0050;
                case 10: goto L_0x0049;
                case 11: goto L_0x0043;
                case 12: goto L_0x003d;
                default: goto L_0x0039;
            }
        L_0x0039:
            com.google.android.gms.internal.zzbkw.zzb(r0, r2)
            goto L_0x0025
        L_0x003d:
            java.lang.String r19 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x0025
        L_0x0043:
            java.lang.String r18 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x0025
        L_0x0049:
            android.os.Parcelable$Creator<com.google.android.gms.common.api.Scope> r3 = com.google.android.gms.common.api.Scope.CREATOR
            java.util.ArrayList r17 = com.google.android.gms.internal.zzbkw.zzc(r0, r2, r3)
            goto L_0x0025
        L_0x0050:
            java.lang.String r16 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x0025
        L_0x0056:
            long r14 = com.google.android.gms.internal.zzbkw.zzi(r0, r2)
            goto L_0x0025
        L_0x005c:
            java.lang.String r13 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x0025
        L_0x0062:
            android.os.Parcelable$Creator r3 = android.net.Uri.CREATOR
            android.os.Parcelable r2 = com.google.android.gms.internal.zzbkw.zza(r0, r2, r3)
            r12 = r2
            android.net.Uri r12 = (android.net.Uri) r12
            goto L_0x0025
        L_0x006c:
            java.lang.String r11 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x0025
        L_0x0072:
            java.lang.String r10 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x0025
        L_0x0078:
            java.lang.String r9 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x0025
        L_0x007e:
            java.lang.String r8 = com.google.android.gms.internal.zzbkw.zzq(r0, r2)
            goto L_0x0025
        L_0x0084:
            int r7 = com.google.android.gms.internal.zzbkw.zzg(r0, r2)
            goto L_0x0025
        L_0x008a:
            com.google.android.gms.internal.zzbkw.zzae(r0, r1)
            com.google.android.gms.auth.api.signin.GoogleSignInAccount r0 = new com.google.android.gms.auth.api.signin.GoogleSignInAccount
            r6 = r0
            r6.<init>(r7, r8, r9, r10, r11, r12, r13, r14, r16, r17, r18, r19)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.api.signin.zzb.createFromParcel(android.os.Parcel):java.lang.Object");
    }
}
