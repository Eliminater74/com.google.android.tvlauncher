package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbkw;

@Hide
/* compiled from: ConfigurationCreator */
public final class zzb implements Parcelable.Creator<Configuration> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new Configuration[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        Flag[] flagArr = null;
        int i = 0;
        String[] strArr = null;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            int i2 = 65535 & readInt;
            if (i2 == 2) {
                i = zzbkw.zzg(parcel, readInt);
            } else if (i2 == 3) {
                flagArr = (Flag[]) zzbkw.zzb(parcel, readInt, Flag.CREATOR);
            } else if (i2 != 4) {
                zzbkw.zzb(parcel, readInt);
            } else {
                strArr = zzbkw.zzaa(parcel, readInt);
            }
        }
        zzbkw.zzae(parcel, zza);
        return new Configuration(i, flagArr, strArr);
    }
}
