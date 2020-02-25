package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbkw;

@Hide
/* compiled from: SafeParcelResponseCreator */
public final class zzm implements Parcelable.Creator<zzl> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzl[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        Parcel parcel2 = null;
        int i = 0;
        FieldMappingDictionary fieldMappingDictionary = null;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            int i2 = 65535 & readInt;
            if (i2 == 1) {
                i = zzbkw.zzg(parcel, readInt);
            } else if (i2 == 2) {
                parcel2 = zzbkw.zzad(parcel, readInt);
            } else if (i2 != 3) {
                zzbkw.zzb(parcel, readInt);
            } else {
                fieldMappingDictionary = (FieldMappingDictionary) zzbkw.zza(parcel, readInt, FieldMappingDictionary.CREATOR);
            }
        }
        zzbkw.zzae(parcel, zza);
        return new zzl(i, parcel2, fieldMappingDictionary);
    }
}
