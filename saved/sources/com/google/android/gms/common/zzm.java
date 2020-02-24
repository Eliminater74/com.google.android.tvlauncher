package com.google.android.gms.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbkw;

@Hide
/* compiled from: GoogleCertificatesQueryCreator */
public final class zzm implements Parcelable.Creator<zzl> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzl[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        String str = null;
        IBinder iBinder = null;
        boolean z = false;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            int i = 65535 & readInt;
            if (i == 1) {
                str = zzbkw.zzq(parcel, readInt);
            } else if (i == 2) {
                iBinder = zzbkw.zzr(parcel, readInt);
            } else if (i != 3) {
                zzbkw.zzb(parcel, readInt);
            } else {
                z = zzbkw.zzc(parcel, readInt);
            }
        }
        zzbkw.zzae(parcel, zza);
        return new zzl(str, iBinder, z);
    }
}
