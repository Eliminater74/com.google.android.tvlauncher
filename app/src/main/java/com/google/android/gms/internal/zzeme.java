package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzav;

@Hide
/* compiled from: SignInRequestCreator */
public final class zzeme implements Parcelable.Creator<zzemd> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzemd[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        int i = 0;
        zzav zzav = null;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            int i2 = 65535 & readInt;
            if (i2 == 1) {
                i = zzbkw.zzg(parcel, readInt);
            } else if (i2 != 2) {
                zzbkw.zzb(parcel, readInt);
            } else {
                zzav = (zzav) zzbkw.zza(parcel, readInt, zzav.CREATOR);
            }
        }
        zzbkw.zzae(parcel, zza);
        return new zzemd(i, zzav);
    }
}
