package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbkw;

import java.util.ArrayList;

@Hide
/* compiled from: FlagOverridesCreator */
public final class zzm implements Parcelable.Creator<FlagOverrides> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new FlagOverrides[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        ArrayList arrayList = null;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            if ((65535 & readInt) != 2) {
                zzbkw.zzb(parcel, readInt);
            } else {
                arrayList = zzbkw.zzc(parcel, readInt, FlagOverride.CREATOR);
            }
        }
        zzbkw.zzae(parcel, zza);
        return new FlagOverrides(arrayList);
    }
}
