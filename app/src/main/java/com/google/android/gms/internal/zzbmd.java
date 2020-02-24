package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Hide;

@Hide
/* compiled from: StringToIntConverterEntryCreator */
public final class zzbmd implements Parcelable.Creator<zzbmb> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbmb[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        int i = 0;
        String str = null;
        int i2 = 0;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            int i3 = 65535 & readInt;
            if (i3 == 1) {
                i = zzbkw.zzg(parcel, readInt);
            } else if (i3 == 2) {
                str = zzbkw.zzq(parcel, readInt);
            } else if (i3 != 3) {
                zzbkw.zzb(parcel, readInt);
            } else {
                i2 = zzbkw.zzg(parcel, readInt);
            }
        }
        zzbkw.zzae(parcel, zza);
        return new zzbmb(i, str, i2);
    }
}
