package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzax;

@Hide
/* compiled from: SignInResponseCreator */
public final class zzemg implements Parcelable.Creator<zzemf> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzemf[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        ConnectionResult connectionResult = null;
        int i = 0;
        zzax zzax = null;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            int i2 = 65535 & readInt;
            if (i2 == 1) {
                i = zzbkw.zzg(parcel, readInt);
            } else if (i2 == 2) {
                connectionResult = (ConnectionResult) zzbkw.zza(parcel, readInt, ConnectionResult.CREATOR);
            } else if (i2 != 3) {
                zzbkw.zzb(parcel, readInt);
            } else {
                zzax = (zzax) zzbkw.zza(parcel, readInt, zzax.CREATOR);
            }
        }
        zzbkw.zzae(parcel, zza);
        return new zzemf(i, connectionResult, zzax);
    }
}
