package com.google.android.gms.common.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbkw;

@Hide
/* compiled from: ScopeCreator */
public final class zze implements Parcelable.Creator<Scope> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new Scope[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        int i = 0;
        String str = null;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            int i2 = 65535 & readInt;
            if (i2 == 1) {
                i = zzbkw.zzg(parcel, readInt);
            } else if (i2 != 2) {
                zzbkw.zzb(parcel, readInt);
            } else {
                str = zzbkw.zzq(parcel, readInt);
            }
        }
        zzbkw.zzae(parcel, zza);
        return new Scope(i, str);
    }
}
