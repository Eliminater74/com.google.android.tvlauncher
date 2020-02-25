package com.google.android.gms.common.api;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbkw;

@Hide
/* compiled from: StatusCreator */
public final class zzf implements Parcelable.Creator<Status> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new Status[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        int i = 0;
        String str = null;
        PendingIntent pendingIntent = null;
        int i2 = 0;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            int i3 = 65535 & readInt;
            if (i3 == 1) {
                i2 = zzbkw.zzg(parcel, readInt);
            } else if (i3 == 2) {
                str = zzbkw.zzq(parcel, readInt);
            } else if (i3 == 3) {
                pendingIntent = (PendingIntent) zzbkw.zza(parcel, readInt, PendingIntent.CREATOR);
            } else if (i3 != 1000) {
                zzbkw.zzb(parcel, readInt);
            } else {
                i = zzbkw.zzg(parcel, readInt);
            }
        }
        zzbkw.zzae(parcel, zza);
        return new Status(i, i2, str, pendingIntent);
    }
}
