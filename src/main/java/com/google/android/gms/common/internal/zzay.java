package com.google.android.gms.common.internal;

import android.os.Parcelable;

@Hide
/* compiled from: ResolveAccountResponseCreator */
public final class zzay implements Parcelable.Creator<zzax> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzax[i];
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [android.os.Parcelable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object createFromParcel(android.os.Parcel r10) {
        /*
            r9 = this;
            int r0 = com.google.android.gms.internal.zzbkw.zza(r10)
            r1 = 0
            r2 = 0
            r5 = r1
            r6 = r5
            r4 = 0
            r7 = 0
            r8 = 0
        L_0x0010:
            int r1 = r10.dataPosition()
            if (r1 >= r0) goto L_0x0056
            int r1 = r10.readInt()
            r2 = 65535(0xffff, float:9.1834E-41)
            r2 = r2 & r1
            r3 = 1
            if (r2 == r3) goto L_0x0050
            r3 = 2
            if (r2 == r3) goto L_0x004a
            r3 = 3
            if (r2 == r3) goto L_0x0040
            r3 = 4
            if (r2 == r3) goto L_0x003a
            r3 = 5
            if (r2 == r3) goto L_0x0034
            com.google.android.gms.internal.zzbkw.zzb(r10, r1)
            goto L_0x0010
        L_0x0034:
            boolean r8 = com.google.android.gms.internal.zzbkw.zzc(r10, r1)
            goto L_0x0010
        L_0x003a:
            boolean r7 = com.google.android.gms.internal.zzbkw.zzc(r10, r1)
            goto L_0x0010
        L_0x0040:
            android.os.Parcelable$Creator<com.google.android.gms.common.ConnectionResult> r2 = com.google.android.gms.common.ConnectionResult.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.internal.zzbkw.zza(r10, r1, r2)
            r6 = r1
            com.google.android.gms.common.ConnectionResult r6 = (com.google.android.gms.common.ConnectionResult) r6
            goto L_0x0010
        L_0x004a:
            android.os.IBinder r5 = com.google.android.gms.internal.zzbkw.zzr(r10, r1)
            goto L_0x0010
        L_0x0050:
            int r4 = com.google.android.gms.internal.zzbkw.zzg(r10, r1)
            goto L_0x0010
        L_0x0056:
            com.google.android.gms.internal.zzbkw.zzae(r10, r0)
            com.google.android.gms.common.internal.zzax r10 = new com.google.android.gms.common.internal.zzax
            r3 = r10
            r3.<init>(r4, r5, r6, r7, r8)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzay.createFromParcel(android.os.Parcel):java.lang.Object");
    }
}
