package com.google.android.gms.phenotype;

import android.os.Parcelable;

import com.google.android.gms.common.internal.Hide;

@Hide
/* compiled from: ConfigurationsCreator */
public final class zzc implements Parcelable.Creator<Configurations> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new Configurations[i];
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object createFromParcel(android.os.Parcel r14) {
        /*
            r13 = this;
            int r0 = com.google.android.gms.internal.zzbkw.zza(r14)
            r1 = 0
            r2 = 0
            r3 = 0
            r6 = r1
            r7 = r6
            r8 = r7
            r10 = r8
            r11 = r3
            r9 = 0
        L_0x0014:
            int r1 = r14.dataPosition()
            if (r1 >= r0) goto L_0x0054
            int r1 = r14.readInt()
            r2 = 65535(0xffff, float:9.1834E-41)
            r2 = r2 & r1
            switch(r2) {
                case 2: goto L_0x004e;
                case 3: goto L_0x0048;
                case 4: goto L_0x003e;
                case 5: goto L_0x0038;
                case 6: goto L_0x0032;
                case 7: goto L_0x002c;
                default: goto L_0x0028;
            }
        L_0x0028:
            com.google.android.gms.internal.zzbkw.zzb(r14, r1)
            goto L_0x0014
        L_0x002c:
            long r11 = com.google.android.gms.internal.zzbkw.zzi(r14, r1)
            goto L_0x0014
        L_0x0032:
            byte[] r10 = com.google.android.gms.internal.zzbkw.zzt(r14, r1)
            goto L_0x0014
        L_0x0038:
            boolean r9 = com.google.android.gms.internal.zzbkw.zzc(r14, r1)
            goto L_0x0014
        L_0x003e:
            android.os.Parcelable$Creator<com.google.android.gms.phenotype.Configuration> r2 = com.google.android.gms.phenotype.Configuration.CREATOR
            java.lang.Object[] r1 = com.google.android.gms.internal.zzbkw.zzb(r14, r1, r2)
            r8 = r1
            com.google.android.gms.phenotype.Configuration[] r8 = (com.google.android.gms.phenotype.Configuration[]) r8
            goto L_0x0014
        L_0x0048:
            java.lang.String r7 = com.google.android.gms.internal.zzbkw.zzq(r14, r1)
            goto L_0x0014
        L_0x004e:
            java.lang.String r6 = com.google.android.gms.internal.zzbkw.zzq(r14, r1)
            goto L_0x0014
        L_0x0054:
            com.google.android.gms.internal.zzbkw.zzae(r14, r0)
            com.google.android.gms.phenotype.Configurations r14 = new com.google.android.gms.phenotype.Configurations
            r5 = r14
            r5.<init>(r6, r7, r8, r9, r10, r11)
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.phenotype.zzc.createFromParcel(android.os.Parcel):java.lang.Object");
    }
}
