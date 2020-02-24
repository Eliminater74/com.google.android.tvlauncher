package com.google.android.gms.common;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbkw;

@Hide
/* compiled from: ConnectionResultCreator */
public final class zzb implements Parcelable.Creator<ConnectionResult> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new ConnectionResult[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        int i = 0;
        PendingIntent pendingIntent = null;
        String str = null;
        int i2 = 0;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            int i3 = 65535 & readInt;
            if (i3 == 1) {
                i = zzbkw.zzg(parcel, readInt);
            } else if (i3 == 2) {
                i2 = zzbkw.zzg(parcel, readInt);
            } else if (i3 == 3) {
                pendingIntent = (PendingIntent) zzbkw.zza(parcel, readInt, PendingIntent.CREATOR);
            } else if (i3 != 4) {
                zzbkw.zzb(parcel, readInt);
            } else {
                str = zzbkw.zzq(parcel, readInt);
            }
        }
        zzbkw.zzae(parcel, zza);
        return new ConnectionResult(i, i2, pendingIntent, str);
    }
}
