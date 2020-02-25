package com.google.android.gms.clearcut;

import android.os.Parcelable;

import com.google.android.gms.common.internal.Hide;

@Hide
/* compiled from: LogEventParcelableCreator */
public final class zzq implements Parcelable.Creator<LogEventParcelable> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new LogEventParcelable[i];
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [android.os.Parcelable] */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object createFromParcel(android.os.Parcel r13) {
        /*
            r12 = this;
            int r0 = com.google.android.gms.internal.zzbkw.zza(r13)
            r1 = 0
            r2 = 1
            r4 = r1
            r5 = r4
            r6 = r5
            r7 = r6
            r8 = r7
            r9 = r8
            r11 = r9
            r10 = 1
        L_0x0016:
            int r1 = r13.dataPosition()
            if (r1 >= r0) goto L_0x0066
            int r1 = r13.readInt()
            r2 = 65535(0xffff, float:9.1834E-41)
            r2 = r2 & r1
            switch(r2) {
                case 2: goto L_0x005c;
                case 3: goto L_0x0056;
                case 4: goto L_0x0050;
                case 5: goto L_0x004a;
                case 6: goto L_0x0044;
                case 7: goto L_0x003e;
                case 8: goto L_0x0038;
                case 9: goto L_0x002e;
                default: goto L_0x002a;
            }
        L_0x002a:
            com.google.android.gms.internal.zzbkw.zzb(r13, r1)
            goto L_0x0016
        L_0x002e:
            android.os.Parcelable$Creator<com.google.android.gms.phenotype.ExperimentTokens> r2 = com.google.android.gms.phenotype.ExperimentTokens.CREATOR
            java.lang.Object[] r1 = com.google.android.gms.internal.zzbkw.zzb(r13, r1, r2)
            r11 = r1
            com.google.android.gms.phenotype.ExperimentTokens[] r11 = (com.google.android.gms.phenotype.ExperimentTokens[]) r11
            goto L_0x0016
        L_0x0038:
            boolean r10 = com.google.android.gms.internal.zzbkw.zzc(r13, r1)
            goto L_0x0016
        L_0x003e:
            byte[][] r9 = com.google.android.gms.internal.zzbkw.zzu(r13, r1)
            goto L_0x0016
        L_0x0044:
            int[] r8 = com.google.android.gms.internal.zzbkw.zzw(r13, r1)
            goto L_0x0016
        L_0x004a:
            java.lang.String[] r7 = com.google.android.gms.internal.zzbkw.zzaa(r13, r1)
            goto L_0x0016
        L_0x0050:
            int[] r6 = com.google.android.gms.internal.zzbkw.zzw(r13, r1)
            goto L_0x0016
        L_0x0056:
            byte[] r5 = com.google.android.gms.internal.zzbkw.zzt(r13, r1)
            goto L_0x0016
        L_0x005c:
            android.os.Parcelable$Creator<com.google.android.gms.clearcut.internal.PlayLoggerContext> r2 = com.google.android.gms.clearcut.internal.PlayLoggerContext.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.internal.zzbkw.zza(r13, r1, r2)
            r4 = r1
            com.google.android.gms.clearcut.internal.PlayLoggerContext r4 = (com.google.android.gms.clearcut.internal.PlayLoggerContext) r4
            goto L_0x0016
        L_0x0066:
            com.google.android.gms.internal.zzbkw.zzae(r13, r0)
            com.google.android.gms.clearcut.LogEventParcelable r13 = new com.google.android.gms.clearcut.LogEventParcelable
            r3 = r13
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11)
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.clearcut.zzq.createFromParcel(android.os.Parcel):java.lang.Object");
    }
}
