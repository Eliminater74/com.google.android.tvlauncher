package com.google.android.gms.common.data;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbkw;

@Hide
/* compiled from: BitmapTeleporterCreator */
public final class zza implements Parcelable.Creator<BitmapTeleporter> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new BitmapTeleporter[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        int i = 0;
        ParcelFileDescriptor parcelFileDescriptor = null;
        int i2 = 0;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            int i3 = 65535 & readInt;
            if (i3 == 1) {
                i = zzbkw.zzg(parcel, readInt);
            } else if (i3 == 2) {
                parcelFileDescriptor = (ParcelFileDescriptor) zzbkw.zza(parcel, readInt, ParcelFileDescriptor.CREATOR);
            } else if (i3 != 3) {
                zzbkw.zzb(parcel, readInt);
            } else {
                i2 = zzbkw.zzg(parcel, readInt);
            }
        }
        zzbkw.zzae(parcel, zza);
        return new BitmapTeleporter(i, parcelFileDescriptor, i2);
    }
}
