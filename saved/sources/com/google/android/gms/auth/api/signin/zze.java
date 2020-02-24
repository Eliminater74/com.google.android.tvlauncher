package com.google.android.gms.auth.api.signin;

import android.os.Parcelable;
import com.google.android.gms.common.internal.Hide;

@Hide
/* compiled from: GoogleSignInOptionsCreator */
public final class zze implements Parcelable.Creator<GoogleSignInOptions> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new GoogleSignInOptions[i];
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [android.os.Parcelable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object createFromParcel(android.os.Parcel r14) {
        /*
            r13 = this;
            int r0 = com.google.android.gms.internal.zzbkw.zza(r14)
            r1 = 0
            r2 = 0
            r5 = r2
            r6 = r5
            r10 = r6
            r11 = r10
            r12 = r11
            r4 = 0
            r7 = 0
            r8 = 0
            r9 = 0
        L_0x0018:
            int r1 = r14.dataPosition()
            if (r1 >= r0) goto L_0x006c
            int r1 = r14.readInt()
            r2 = 65535(0xffff, float:9.1834E-41)
            r2 = r2 & r1
            switch(r2) {
                case 1: goto L_0x0066;
                case 2: goto L_0x005f;
                case 3: goto L_0x0055;
                case 4: goto L_0x004f;
                case 5: goto L_0x0049;
                case 6: goto L_0x0043;
                case 7: goto L_0x003d;
                case 8: goto L_0x0037;
                case 9: goto L_0x0030;
                default: goto L_0x002c;
            }
        L_0x002c:
            com.google.android.gms.internal.zzbkw.zzb(r14, r1)
            goto L_0x0018
        L_0x0030:
            android.os.Parcelable$Creator<com.google.android.gms.auth.api.signin.internal.zzo> r2 = com.google.android.gms.auth.api.signin.internal.zzo.CREATOR
            java.util.ArrayList r12 = com.google.android.gms.internal.zzbkw.zzc(r14, r1, r2)
            goto L_0x0018
        L_0x0037:
            java.lang.String r11 = com.google.android.gms.internal.zzbkw.zzq(r14, r1)
            goto L_0x0018
        L_0x003d:
            java.lang.String r10 = com.google.android.gms.internal.zzbkw.zzq(r14, r1)
            goto L_0x0018
        L_0x0043:
            boolean r9 = com.google.android.gms.internal.zzbkw.zzc(r14, r1)
            goto L_0x0018
        L_0x0049:
            boolean r8 = com.google.android.gms.internal.zzbkw.zzc(r14, r1)
            goto L_0x0018
        L_0x004f:
            boolean r7 = com.google.android.gms.internal.zzbkw.zzc(r14, r1)
            goto L_0x0018
        L_0x0055:
            android.os.Parcelable$Creator r2 = android.accounts.Account.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.internal.zzbkw.zza(r14, r1, r2)
            r6 = r1
            android.accounts.Account r6 = (android.accounts.Account) r6
            goto L_0x0018
        L_0x005f:
            android.os.Parcelable$Creator<com.google.android.gms.common.api.Scope> r2 = com.google.android.gms.common.api.Scope.CREATOR
            java.util.ArrayList r5 = com.google.android.gms.internal.zzbkw.zzc(r14, r1, r2)
            goto L_0x0018
        L_0x0066:
            int r4 = com.google.android.gms.internal.zzbkw.zzg(r14, r1)
            goto L_0x0018
        L_0x006c:
            com.google.android.gms.internal.zzbkw.zzae(r14, r0)
            com.google.android.gms.auth.api.signin.GoogleSignInOptions r14 = new com.google.android.gms.auth.api.signin.GoogleSignInOptions
            r3 = r14
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11, r12)
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.api.signin.zze.createFromParcel(android.os.Parcel):java.lang.Object");
    }
}
