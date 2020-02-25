package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.server.response.FastJsonResponse;

/* compiled from: ConverterWrapper */
public final class zzbly extends zzbkv {
    public static final Parcelable.Creator<zzbly> CREATOR = new zzblz();
    private final int zza;
    private final zzbma zzb;

    zzbly(int i, zzbma zzbma) {
        this.zza = i;
        this.zzb = zzbma;
    }

    private zzbly(zzbma zzbma) {
        this.zza = 1;
        this.zzb = zzbma;
    }

    public static zzbly zza(FastJsonResponse.FieldConverter<?, ?> fieldConverter) {
        if (fieldConverter instanceof zzbma) {
            return new zzbly((zzbma) fieldConverter);
        }
        throw new IllegalArgumentException("Unsupported safe parcelable field converter class.");
    }

    public final FastJsonResponse.FieldConverter<?, ?> zza() {
        zzbma zzbma = this.zzb;
        if (zzbma != null) {
            return zzbma;
        }
        throw new IllegalStateException("There was no converter wrapped in this ConverterWrapper.");
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
     arg types: [android.os.Parcel, int, com.google.android.gms.internal.zzbma, int, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void */
    public final void writeToParcel(Parcel parcel, int i) {
        int zza2 = zzbky.zza(parcel);
        zzbky.zza(parcel, 1, this.zza);
        zzbky.zza(parcel, 2, (Parcelable) this.zzb, i, false);
        zzbky.zza(parcel, zza2);
    }
}
