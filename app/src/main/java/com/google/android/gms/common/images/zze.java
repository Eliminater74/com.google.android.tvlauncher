package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbkw;

@Hide
/* compiled from: WebImageCreator */
public final class zze implements Parcelable.Creator<WebImage> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new WebImage[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        int i = 0;
        Uri uri = null;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            int i4 = 65535 & readInt;
            if (i4 == 1) {
                i = zzbkw.zzg(parcel, readInt);
            } else if (i4 == 2) {
                uri = (Uri) zzbkw.zza(parcel, readInt, Uri.CREATOR);
            } else if (i4 == 3) {
                i2 = zzbkw.zzg(parcel, readInt);
            } else if (i4 != 4) {
                zzbkw.zzb(parcel, readInt);
            } else {
                i3 = zzbkw.zzg(parcel, readInt);
            }
        }
        zzbkw.zzae(parcel, zza);
        return new WebImage(i, uri, i2, i3);
    }
}
