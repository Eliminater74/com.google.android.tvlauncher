package com.google.android.gms.dynamic;

import com.google.android.gms.internal.zzfa;

/* compiled from: IFragmentWrapper */
public abstract class zzl extends zzfa implements zzk {
    public zzl() {
        attachInterface(this, "com.google.android.gms.dynamic.IFragmentWrapper");
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [android.os.IInterface] */
    /* JADX WARN: Type inference failed for: r4v3, types: [android.os.IInterface] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTransact(int r3, android.os.Parcel r4, android.os.Parcel r5, int r6) throws android.os.RemoteException {
        /*
            r2 = this;
            boolean r6 = r2.zza(r3, r4, r5, r6)
            r0 = 1
            if (r6 == 0) goto L_0x0008
            return r0
        L_0x0008:
            r6 = 0
            java.lang.String r1 = "com.google.android.gms.dynamic.IObjectWrapper"
            switch(r3) {
                case 2: goto L_0x0169;
                case 3: goto L_0x015e;
                case 4: goto L_0x0153;
                case 5: goto L_0x0148;
                case 6: goto L_0x013d;
                case 7: goto L_0x0132;
                case 8: goto L_0x0127;
                case 9: goto L_0x011c;
                case 10: goto L_0x0111;
                case 11: goto L_0x0106;
                case 12: goto L_0x00fa;
                case 13: goto L_0x00ee;
                case 14: goto L_0x00e2;
                case 15: goto L_0x00d6;
                case 16: goto L_0x00ca;
                case 17: goto L_0x00be;
                case 18: goto L_0x00b2;
                case 19: goto L_0x00a6;
                case 20: goto L_0x0085;
                case 21: goto L_0x0079;
                case 22: goto L_0x006d;
                case 23: goto L_0x0061;
                case 24: goto L_0x0055;
                case 25: goto L_0x0045;
                case 26: goto L_0x0031;
                case 27: goto L_0x0010;
                default: goto L_0x000e;
            }
        L_0x000e:
            r3 = 0
            return r3
        L_0x0010:
            android.os.IBinder r3 = r4.readStrongBinder()
            if (r3 != 0) goto L_0x0017
            goto L_0x0028
        L_0x0017:
            android.os.IInterface r4 = r3.queryLocalInterface(r1)
            boolean r6 = r4 instanceof com.google.android.gms.dynamic.IObjectWrapper
            if (r6 == 0) goto L_0x0023
            r6 = r4
            com.google.android.gms.dynamic.IObjectWrapper r6 = (com.google.android.gms.dynamic.IObjectWrapper) r6
            goto L_0x0028
        L_0x0023:
            com.google.android.gms.dynamic.zzm r6 = new com.google.android.gms.dynamic.zzm
            r6.<init>(r3)
        L_0x0028:
            r2.zzb(r6)
            r5.writeNoException()
            goto L_0x0174
        L_0x0031:
            android.os.Parcelable$Creator r3 = android.content.Intent.CREATOR
            android.os.Parcelable r3 = com.google.android.gms.internal.zzfb.zza(r4, r3)
            android.content.Intent r3 = (android.content.Intent) r3
            int r4 = r4.readInt()
            r2.zza(r3, r4)
            r5.writeNoException()
            goto L_0x0174
        L_0x0045:
            android.os.Parcelable$Creator r3 = android.content.Intent.CREATOR
            android.os.Parcelable r3 = com.google.android.gms.internal.zzfb.zza(r4, r3)
            android.content.Intent r3 = (android.content.Intent) r3
            r2.zza(r3)
            r5.writeNoException()
            goto L_0x0174
        L_0x0055:
            boolean r3 = com.google.android.gms.internal.zzfb.zza(r4)
            r2.zzd(r3)
            r5.writeNoException()
            goto L_0x0174
        L_0x0061:
            boolean r3 = com.google.android.gms.internal.zzfb.zza(r4)
            r2.zzc(r3)
            r5.writeNoException()
            goto L_0x0174
        L_0x006d:
            boolean r3 = com.google.android.gms.internal.zzfb.zza(r4)
            r2.zzb(r3)
            r5.writeNoException()
            goto L_0x0174
        L_0x0079:
            boolean r3 = com.google.android.gms.internal.zzfb.zza(r4)
            r2.zza(r3)
            r5.writeNoException()
            goto L_0x0174
        L_0x0085:
            android.os.IBinder r3 = r4.readStrongBinder()
            if (r3 != 0) goto L_0x008c
            goto L_0x009d
        L_0x008c:
            android.os.IInterface r4 = r3.queryLocalInterface(r1)
            boolean r6 = r4 instanceof com.google.android.gms.dynamic.IObjectWrapper
            if (r6 == 0) goto L_0x0098
            r6 = r4
            com.google.android.gms.dynamic.IObjectWrapper r6 = (com.google.android.gms.dynamic.IObjectWrapper) r6
            goto L_0x009d
        L_0x0098:
            com.google.android.gms.dynamic.zzm r6 = new com.google.android.gms.dynamic.zzm
            r6.<init>(r3)
        L_0x009d:
            r2.zza(r6)
            r5.writeNoException()
            goto L_0x0174
        L_0x00a6:
            boolean r3 = r2.zzr()
            r5.writeNoException()
            com.google.android.gms.internal.zzfb.zza(r5, r3)
            goto L_0x0174
        L_0x00b2:
            boolean r3 = r2.zzq()
            r5.writeNoException()
            com.google.android.gms.internal.zzfb.zza(r5, r3)
            goto L_0x0174
        L_0x00be:
            boolean r3 = r2.zzp()
            r5.writeNoException()
            com.google.android.gms.internal.zzfb.zza(r5, r3)
            goto L_0x0174
        L_0x00ca:
            boolean r3 = r2.zzo()
            r5.writeNoException()
            com.google.android.gms.internal.zzfb.zza(r5, r3)
            goto L_0x0174
        L_0x00d6:
            boolean r3 = r2.zzn()
            r5.writeNoException()
            com.google.android.gms.internal.zzfb.zza(r5, r3)
            goto L_0x0174
        L_0x00e2:
            boolean r3 = r2.zzm()
            r5.writeNoException()
            com.google.android.gms.internal.zzfb.zza(r5, r3)
            goto L_0x0174
        L_0x00ee:
            boolean r3 = r2.zzl()
            r5.writeNoException()
            com.google.android.gms.internal.zzfb.zza(r5, r3)
            goto L_0x0174
        L_0x00fa:
            com.google.android.gms.dynamic.IObjectWrapper r3 = r2.zzk()
            r5.writeNoException()
            com.google.android.gms.internal.zzfb.zza(r5, r3)
            goto L_0x0174
        L_0x0106:
            boolean r3 = r2.zzj()
            r5.writeNoException()
            com.google.android.gms.internal.zzfb.zza(r5, r3)
            goto L_0x0174
        L_0x0111:
            int r3 = r2.zzi()
            r5.writeNoException()
            r5.writeInt(r3)
            goto L_0x0174
        L_0x011c:
            com.google.android.gms.dynamic.zzk r3 = r2.zzh()
            r5.writeNoException()
            com.google.android.gms.internal.zzfb.zza(r5, r3)
            goto L_0x0174
        L_0x0127:
            java.lang.String r3 = r2.zzg()
            r5.writeNoException()
            r5.writeString(r3)
            goto L_0x0174
        L_0x0132:
            boolean r3 = r2.zzf()
            r5.writeNoException()
            com.google.android.gms.internal.zzfb.zza(r5, r3)
            goto L_0x0174
        L_0x013d:
            com.google.android.gms.dynamic.IObjectWrapper r3 = r2.zze()
            r5.writeNoException()
            com.google.android.gms.internal.zzfb.zza(r5, r3)
            goto L_0x0174
        L_0x0148:
            com.google.android.gms.dynamic.zzk r3 = r2.zzd()
            r5.writeNoException()
            com.google.android.gms.internal.zzfb.zza(r5, r3)
            goto L_0x0174
        L_0x0153:
            int r3 = r2.zzc()
            r5.writeNoException()
            r5.writeInt(r3)
            goto L_0x0174
        L_0x015e:
            android.os.Bundle r3 = r2.zzb()
            r5.writeNoException()
            com.google.android.gms.internal.zzfb.zzb(r5, r3)
            goto L_0x0174
        L_0x0169:
            com.google.android.gms.dynamic.IObjectWrapper r3 = r2.zza()
            r5.writeNoException()
            com.google.android.gms.internal.zzfb.zza(r5, r3)
        L_0x0174:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamic.zzl.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
    }
}
