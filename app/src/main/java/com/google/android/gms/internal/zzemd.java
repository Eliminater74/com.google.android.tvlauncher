package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.zzav;

/* compiled from: SignInRequest */
public final class zzemd extends zzbkv {
    public static final Parcelable.Creator<zzemd> CREATOR = new zzeme();
    private final int zza;
    private final zzav zzb;

    zzemd(int i, zzav zzav) {
        this.zza = i;
        this.zzb = zzav;
    }

    public zzemd(zzav zzav) {
        this(1, zzav);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
     arg types: [android.os.Parcel, int, com.google.android.gms.common.internal.zzav, int, int]
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
