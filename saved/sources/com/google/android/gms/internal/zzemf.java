package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzax;

/* compiled from: SignInResponse */
public final class zzemf extends zzbkv {
    public static final Parcelable.Creator<zzemf> CREATOR = new zzemg();
    private final int zza;
    private final ConnectionResult zzb;
    private final zzax zzc;

    zzemf(int i, ConnectionResult connectionResult, zzax zzax) {
        this.zza = i;
        this.zzb = connectionResult;
        this.zzc = zzax;
    }

    public zzemf(int i) {
        this(new ConnectionResult(8, null), null);
    }

    private zzemf(ConnectionResult connectionResult, zzax zzax) {
        this(1, connectionResult, null);
    }

    public final ConnectionResult zza() {
        return this.zzb;
    }

    public final zzax zzb() {
        return this.zzc;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
     arg types: [android.os.Parcel, int, com.google.android.gms.common.ConnectionResult, int, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
     arg types: [android.os.Parcel, int, com.google.android.gms.common.internal.zzax, int, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void */
    public final void writeToParcel(Parcel parcel, int i) {
        int zza2 = zzbky.zza(parcel);
        zzbky.zza(parcel, 1, this.zza);
        zzbky.zza(parcel, 2, (Parcelable) this.zzb, i, false);
        zzbky.zza(parcel, 3, (Parcelable) this.zzc, i, false);
        zzbky.zza(parcel, zza2);
    }
}
