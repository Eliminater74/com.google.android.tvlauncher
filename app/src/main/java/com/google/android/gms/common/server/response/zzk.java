package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbkw;

import java.util.ArrayList;

@Hide
/* compiled from: FieldMappingDictionaryEntryCreator */
public final class zzk implements Parcelable.Creator<FieldMappingDictionary.Entry> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new FieldMappingDictionary.Entry[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        String str = null;
        int i = 0;
        ArrayList arrayList = null;
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
                arrayList = zzbkw.zzc(parcel, readInt, FieldMappingDictionary.FieldMapPair.CREATOR);
            }
        }
        zzbkw.zzae(parcel, zza);
        return new FieldMappingDictionary.Entry(i, str, arrayList);
    }
}
