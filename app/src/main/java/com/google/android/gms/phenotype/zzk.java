package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbkw;

@Hide
/* compiled from: FlagCreator */
public final class zzk implements Parcelable.Creator<Flag> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new Flag[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int zza = zzbkw.zza(parcel);
        String str = null;
        String str2 = null;
        byte[] bArr = null;
        long j = 0;
        double d = 0.0d;
        boolean z = false;
        int i = 0;
        int i2 = 0;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str = zzbkw.zzq(parcel2, readInt);
                    break;
                case 3:
                    j = zzbkw.zzi(parcel2, readInt);
                    break;
                case 4:
                    z = zzbkw.zzc(parcel2, readInt);
                    break;
                case 5:
                    d = zzbkw.zzn(parcel2, readInt);
                    break;
                case 6:
                    str2 = zzbkw.zzq(parcel2, readInt);
                    break;
                case 7:
                    bArr = zzbkw.zzt(parcel2, readInt);
                    break;
                case 8:
                    i = zzbkw.zzg(parcel2, readInt);
                    break;
                case 9:
                    i2 = zzbkw.zzg(parcel2, readInt);
                    break;
                default:
                    zzbkw.zzb(parcel2, readInt);
                    break;
            }
        }
        zzbkw.zzae(parcel2, zza);
        return new Flag(str, j, z, d, str2, bArr, i, i2);
    }
}
