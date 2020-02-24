package com.google.android.gms.internal;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

/* compiled from: AuthAccountResult */
public final class zzelv extends zzbkv implements Result {
    public static final Parcelable.Creator<zzelv> CREATOR = new zzelw();
    private final int zza;
    private int zzb;
    private Intent zzc;

    zzelv(int i, int i2, Intent intent) {
        this.zza = i;
        this.zzb = i2;
        this.zzc = intent;
    }

    public zzelv() {
        this(0, null);
    }

    private zzelv(int i, Intent intent) {
        this(2, 0, null);
    }

    public final Status getStatus() {
        if (this.zzb == 0) {
            return Status.zza;
        }
        return Status.zze;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
     arg types: [android.os.Parcel, int, android.content.Intent, int, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void */
    public final void writeToParcel(Parcel parcel, int i) {
        int zza2 = zzbky.zza(parcel);
        zzbky.zza(parcel, 1, this.zza);
        zzbky.zza(parcel, 2, this.zzb);
        zzbky.zza(parcel, 3, (Parcelable) this.zzc, i, false);
        zzbky.zza(parcel, zza2);
    }
}
