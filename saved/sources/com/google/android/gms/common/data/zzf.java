package com.google.android.gms.common.data;

import android.os.Parcelable;
import com.google.android.gms.common.internal.Hide;

@Hide
/* compiled from: DataHolderCreator */
public final class zzf implements Parcelable.Creator<DataHolder> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new DataHolder[i];
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object createFromParcel(android.os.Parcel r10) {
        /*
            r9 = this;
            int r0 = com.google.android.gms.internal.zzbkw.zza(r10)
            r1 = 0
            r2 = 0
            r5 = r2
            r6 = r5
            r8 = r6
            r4 = 0
            r7 = 0
        L_0x0010:
            int r1 = r10.dataPosition()
            if (r1 >= r0) goto L_0x0057
            int r1 = r10.readInt()
            r2 = 65535(0xffff, float:9.1834E-41)
            r2 = r2 & r1
            r3 = 1
            if (r2 == r3) goto L_0x0051
            r3 = 2
            if (r2 == r3) goto L_0x0047
            r3 = 3
            if (r2 == r3) goto L_0x0041
            r3 = 4
            if (r2 == r3) goto L_0x003b
            r3 = 1000(0x3e8, float:1.401E-42)
            if (r2 == r3) goto L_0x0035
            com.google.android.gms.internal.zzbkw.zzb(r10, r1)
            goto L_0x0010
        L_0x0035:
            int r4 = com.google.android.gms.internal.zzbkw.zzg(r10, r1)
            goto L_0x0010
        L_0x003b:
            android.os.Bundle r8 = com.google.android.gms.internal.zzbkw.zzs(r10, r1)
            goto L_0x0010
        L_0x0041:
            int r7 = com.google.android.gms.internal.zzbkw.zzg(r10, r1)
            goto L_0x0010
        L_0x0047:
            android.os.Parcelable$Creator r2 = android.database.CursorWindow.CREATOR
            java.lang.Object[] r1 = com.google.android.gms.internal.zzbkw.zzb(r10, r1, r2)
            r6 = r1
            android.database.CursorWindow[] r6 = (android.database.CursorWindow[]) r6
            goto L_0x0010
        L_0x0051:
            java.lang.String[] r5 = com.google.android.gms.internal.zzbkw.zzaa(r10, r1)
            goto L_0x0010
        L_0x0057:
            com.google.android.gms.internal.zzbkw.zzae(r10, r0)
            com.google.android.gms.common.data.DataHolder r10 = new com.google.android.gms.common.data.DataHolder
            r3 = r10
            r3.<init>(r4, r5, r6, r7, r8)
            r10.validateContents()
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.data.zzf.createFromParcel(android.os.Parcel):java.lang.Object");
    }
}
