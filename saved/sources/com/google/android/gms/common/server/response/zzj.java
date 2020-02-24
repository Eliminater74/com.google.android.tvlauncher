package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.server.response.FieldMappingDictionary;
import com.google.android.gms.internal.zzbkw;
import java.util.ArrayList;

@Hide
/* compiled from: FieldMappingDictionaryCreator */
public final class zzj implements Parcelable.Creator<FieldMappingDictionary> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new FieldMappingDictionary[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbkw.zza(parcel);
        ArrayList arrayList = null;
        int i = 0;
        String str = null;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            int i2 = 65535 & readInt;
            if (i2 == 1) {
                i = zzbkw.zzg(parcel, readInt);
            } else if (i2 == 2) {
                arrayList = zzbkw.zzc(parcel, readInt, FieldMappingDictionary.Entry.CREATOR);
            } else if (i2 != 3) {
                zzbkw.zzb(parcel, readInt);
            } else {
                str = zzbkw.zzq(parcel, readInt);
            }
        }
        zzbkw.zzae(parcel, zza);
        return new FieldMappingDictionary(i, arrayList, str);
    }
}
