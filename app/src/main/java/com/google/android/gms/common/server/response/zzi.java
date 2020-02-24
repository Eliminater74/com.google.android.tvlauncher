package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.server.response.FieldMappingDictionary;
import com.google.android.gms.internal.zzbkw;

@Hide
/* compiled from: FieldMapPairCreator */
public final class zzi implements Parcelable.Creator<FieldMappingDictionary.FieldMapPair> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new FieldMappingDictionary.FieldMapPair[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        String str = null;
        int i = 0;
        FastJsonResponse.Field field = null;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            int i2 = 65535 & readInt;
            if (i2 == 1) {
                i = zzbkw.zzg(parcel, readInt);
            } else if (i2 == 2) {
                str = zzbkw.zzq(parcel, readInt);
            } else if (i2 != 3) {
                zzbkw.zzb(parcel, readInt);
            } else {
                field = (FastJsonResponse.Field) zzbkw.zza(parcel, readInt, FastJsonResponse.Field.CREATOR);
            }
        }
        zzbkw.zzae(parcel, zza);
        return new FieldMappingDictionary.FieldMapPair(i, str, field);
    }
}
