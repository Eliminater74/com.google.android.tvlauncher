package com.google.android.gms.common.internal;

import android.os.Parcelable;

@Hide
/* compiled from: GetServiceRequestCreator */
public final class zzk implements Parcelable.Creator<GetServiceRequest> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new GetServiceRequest[i];
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r1v4, types: [android.os.Parcelable] */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object createFromParcel(android.os.Parcel r14) {
        /*
            r13 = this;
            int r0 = com.google.android.gms.internal.zzbkw.zza(r14)
            r1 = 0
            r2 = 0
            r7 = r2
            r8 = r7
            r9 = r8
            r10 = r9
            r11 = r10
            r12 = r11
            r4 = 0
            r5 = 0
            r6 = 0
        L_0x0018:
            int r1 = r14.dataPosition()
            if (r1 >= r0) goto L_0x0072
            int r1 = r14.readInt()
            r2 = 65535(0xffff, float:9.1834E-41)
            r2 = r2 & r1
            switch(r2) {
                case 1: goto L_0x006c;
                case 2: goto L_0x0066;
                case 3: goto L_0x0060;
                case 4: goto L_0x005a;
                case 5: goto L_0x0054;
                case 6: goto L_0x004a;
                case 7: goto L_0x0044;
                case 8: goto L_0x003a;
                case 9: goto L_0x002c;
                case 10: goto L_0x0030;
                default: goto L_0x002c;
            }
        L_0x002c:
            com.google.android.gms.internal.zzbkw.zzb(r14, r1)
            goto L_0x0018
        L_0x0030:
            android.os.Parcelable$Creator<com.google.android.gms.common.Feature> r2 = com.google.android.gms.common.Feature.CREATOR
            java.lang.Object[] r1 = com.google.android.gms.internal.zzbkw.zzb(r14, r1, r2)
            r12 = r1
            com.google.android.gms.common.Feature[] r12 = (com.google.android.gms.common.Feature[]) r12
            goto L_0x0018
        L_0x003a:
            android.os.Parcelable$Creator r2 = android.accounts.Account.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.internal.zzbkw.zza(r14, r1, r2)
            r11 = r1
            android.accounts.Account r11 = (android.accounts.Account) r11
            goto L_0x0018
        L_0x0044:
            android.os.Bundle r10 = com.google.android.gms.internal.zzbkw.zzs(r14, r1)
            goto L_0x0018
        L_0x004a:
            android.os.Parcelable$Creator<com.google.android.gms.common.api.Scope> r2 = com.google.android.gms.common.api.Scope.CREATOR
            java.lang.Object[] r1 = com.google.android.gms.internal.zzbkw.zzb(r14, r1, r2)
            r9 = r1
            com.google.android.gms.common.api.Scope[] r9 = (com.google.android.gms.common.api.Scope[]) r9
            goto L_0x0018
        L_0x0054:
            android.os.IBinder r8 = com.google.android.gms.internal.zzbkw.zzr(r14, r1)
            goto L_0x0018
        L_0x005a:
            java.lang.String r7 = com.google.android.gms.internal.zzbkw.zzq(r14, r1)
            goto L_0x0018
        L_0x0060:
            int r6 = com.google.android.gms.internal.zzbkw.zzg(r14, r1)
            goto L_0x0018
        L_0x0066:
            int r5 = com.google.android.gms.internal.zzbkw.zzg(r14, r1)
            goto L_0x0018
        L_0x006c:
            int r4 = com.google.android.gms.internal.zzbkw.zzg(r14, r1)
            goto L_0x0018
        L_0x0072:
            com.google.android.gms.internal.zzbkw.zzae(r14, r0)
            com.google.android.gms.common.internal.GetServiceRequest r14 = new com.google.android.gms.common.internal.GetServiceRequest
            r3 = r14
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11, r12)
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzk.createFromParcel(android.os.Parcel):java.lang.Object");
    }
}
