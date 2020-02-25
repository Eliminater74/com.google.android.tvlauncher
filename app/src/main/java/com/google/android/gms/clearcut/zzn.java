package com.google.android.gms.clearcut;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbkw;

@Hide
/* compiled from: CollectForDebugParcelableCreator */
public final class zzn implements Parcelable.Creator<CollectForDebugParcelable> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new CollectForDebugParcelable[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        long j = 0;
        long j2 = 0;
        boolean z = false;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            int i = 65535 & readInt;
            if (i == 1) {
                z = zzbkw.zzc(parcel, readInt);
            } else if (i == 2) {
                j2 = zzbkw.zzi(parcel, readInt);
            } else if (i != 3) {
                zzbkw.zzb(parcel, readInt);
            } else {
                j = zzbkw.zzi(parcel, readInt);
            }
        }
        zzbkw.zzae(parcel, zza);
        return new CollectForDebugParcelable(z, j, j2);
    }
}
