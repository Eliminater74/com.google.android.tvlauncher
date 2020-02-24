package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Hide;

@Hide
/* compiled from: ConverterWrapperCreator */
public final class zzblz implements Parcelable.Creator<zzbly> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbly[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        int i = 0;
        zzbma zzbma = null;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            int i2 = 65535 & readInt;
            if (i2 == 1) {
                i = zzbkw.zzg(parcel, readInt);
            } else if (i2 != 2) {
                zzbkw.zzb(parcel, readInt);
            } else {
                zzbma = (zzbma) zzbkw.zza(parcel, readInt, zzbma.CREATOR);
            }
        }
        zzbkw.zzae(parcel, zza);
        return new zzbly(i, zzbma);
    }
}
