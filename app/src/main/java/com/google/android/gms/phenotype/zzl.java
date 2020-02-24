package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbkw;

@Hide
/* compiled from: FlagOverrideCreator */
public final class zzl implements Parcelable.Creator<FlagOverride> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new FlagOverride[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        String str = null;
        String str2 = null;
        Flag flag = null;
        boolean z = false;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            int i = 65535 & readInt;
            if (i == 2) {
                str = zzbkw.zzq(parcel, readInt);
            } else if (i == 3) {
                str2 = zzbkw.zzq(parcel, readInt);
            } else if (i == 4) {
                flag = (Flag) zzbkw.zza(parcel, readInt, Flag.CREATOR);
            } else if (i != 5) {
                zzbkw.zzb(parcel, readInt);
            } else {
                z = zzbkw.zzc(parcel, readInt);
            }
        }
        zzbkw.zzae(parcel, zza);
        return new FlagOverride(str, str2, flag, z);
    }
}
