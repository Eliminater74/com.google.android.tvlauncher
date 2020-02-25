package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.api.Scope;
import com.google.android.gms.internal.zzbkv;
import com.google.android.gms.internal.zzbky;

/* compiled from: SignInButtonConfig */
public final class zzaz extends zzbkv {
    public static final Parcelable.Creator<zzaz> CREATOR = new zzba();
    private final int zza;
    private final int zzb;
    private final int zzc;
    @Deprecated
    private final Scope[] zzd;

    zzaz(int i, int i2, int i3, Scope[] scopeArr) {
        this.zza = i;
        this.zzb = i2;
        this.zzc = i3;
        this.zzd = scopeArr;
    }

    public zzaz(int i, int i2, Scope[] scopeArr) {
        this(1, i, i2, null);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
     arg types: [android.os.Parcel, int, com.google.android.gms.common.api.Scope[], int, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void */
    public final void writeToParcel(Parcel parcel, int i) {
        int zza2 = zzbky.zza(parcel);
        zzbky.zza(parcel, 1, this.zza);
        zzbky.zza(parcel, 2, this.zzb);
        zzbky.zza(parcel, 3, this.zzc);
        zzbky.zza(parcel, 4, (Parcelable[]) this.zzd, i, false);
        zzbky.zza(parcel, zza2);
    }
}
