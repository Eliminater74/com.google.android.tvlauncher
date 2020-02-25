package com.google.android.gms.feedback;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbkw;

@Hide
/* compiled from: LogOptionsCreator */
public final class zzj implements Parcelable.Creator<LogOptions> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new LogOptions[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        String str = null;
        boolean z = false;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            int i = 65535 & readInt;
            if (i == 2) {
                str = zzbkw.zzq(parcel, readInt);
            } else if (i != 3) {
                zzbkw.zzb(parcel, readInt);
            } else {
                z = zzbkw.zzc(parcel, readInt);
            }
        }
        zzbkw.zzae(parcel, zza);
        return new LogOptions(str, z);
    }
}
