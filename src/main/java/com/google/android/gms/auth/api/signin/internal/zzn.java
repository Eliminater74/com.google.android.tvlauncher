package com.google.android.gms.auth.api.signin.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbkw;

@Hide
/* compiled from: GoogleSignInOptionsExtensionCreator */
public final class zzn implements Parcelable.Creator<zzo> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzo[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        int i = 0;
        Bundle bundle = null;
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
                bundle = zzbkw.zzs(parcel, readInt);
            }
        }
        zzbkw.zzae(parcel, zza);
        return new zzo(i, i2, bundle);
    }
}
