package com.google.android.gms.internal;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Hide;

@Hide
/* compiled from: AuthAccountResultCreator */
public final class zzelw implements Parcelable.Creator<zzelv> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzelv[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        int i = 0;
        Intent intent = null;
        int i2 = 0;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            int i3 = 65535 & readInt;
            if (i3 == 1) {
                i = zzbkw.zzg(parcel, readInt);
            } else if (i3 == 2) {
                i2 = zzbkw.zzg(parcel, readInt);
            } else if (i3 != 3) {
                zzbkw.zzb(parcel, readInt);
            } else {
                intent = (Intent) zzbkw.zza(parcel, readInt, Intent.CREATOR);
            }
        }
        zzbkw.zzae(parcel, zza);
        return new zzelv(i, i2, intent);
    }
}
