package com.google.android.gms.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbkw;

@Hide
/* compiled from: FeatureCreator */
public final class zzc implements Parcelable.Creator<Feature> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new Feature[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        String str = null;
        int i = 0;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            int i2 = 65535 & readInt;
            if (i2 == 1) {
                str = zzbkw.zzq(parcel, readInt);
            } else if (i2 != 2) {
                zzbkw.zzb(parcel, readInt);
            } else {
                i = zzbkw.zzg(parcel, readInt);
            }
        }
        zzbkw.zzae(parcel, zza);
        return new Feature(str, i);
    }
}
