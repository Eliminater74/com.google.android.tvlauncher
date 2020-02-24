package com.google.android.gms.feedback;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbkw;

@Hide
/* compiled from: FileTeleporterCreator */
public final class zzi implements Parcelable.Creator<FileTeleporter> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new FileTeleporter[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        ParcelFileDescriptor parcelFileDescriptor = null;
        String str = null;
        String str2 = null;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            int i = 65535 & readInt;
            if (i == 2) {
                parcelFileDescriptor = (ParcelFileDescriptor) zzbkw.zza(parcel, readInt, ParcelFileDescriptor.CREATOR);
            } else if (i == 3) {
                str = zzbkw.zzq(parcel, readInt);
            } else if (i != 4) {
                zzbkw.zzb(parcel, readInt);
            } else {
                str2 = zzbkw.zzq(parcel, readInt);
            }
        }
        zzbkw.zzae(parcel, zza);
        return new FileTeleporter(parcelFileDescriptor, str, str2);
    }
}
