package com.google.android.gms.usagereporting;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbkw;

@Hide
/* compiled from: UsageReportingOptInOptionsCreator */
public final class zzb implements Parcelable.Creator<UsageReportingOptInOptions> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new UsageReportingOptInOptions[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        int i = 0;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            if ((65535 & readInt) != 2) {
                zzbkw.zzb(parcel, readInt);
            } else {
                i = zzbkw.zzg(parcel, readInt);
            }
        }
        zzbkw.zzae(parcel, zza);
        return new UsageReportingOptInOptions(i);
    }
}
