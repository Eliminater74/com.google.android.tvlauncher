package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.internal.zzbkv;
import com.google.android.gms.internal.zzbky;

/* compiled from: ResolveAccountResponse */
public final class zzax extends zzbkv {
    public static final Parcelable.Creator<zzax> CREATOR = new zzay();
    private final int zza;
    private IBinder zzb;
    private ConnectionResult zzc;
    private boolean zzd;
    private boolean zze;

    zzax(int i, IBinder iBinder, ConnectionResult connectionResult, boolean z, boolean z2) {
        this.zza = i;
        this.zzb = iBinder;
        this.zzc = connectionResult;
        this.zzd = z;
        this.zze = z2;
    }

    public final IAccountAccessor zza() {
        IBinder iBinder = this.zzb;
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IAccountAccessor");
        if (queryLocalInterface instanceof IAccountAccessor) {
            return (IAccountAccessor) queryLocalInterface;
        }
        return new zzw(iBinder);
    }

    public final ConnectionResult zzb() {
        return this.zzc;
    }

    public final boolean zzc() {
        return this.zzd;
    }

    public final boolean zzd() {
        return this.zze;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.IBinder, boolean):void
     arg types: [android.os.Parcel, int, android.os.IBinder, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Bundle, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcel, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.util.SparseArray<java.lang.String>, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Boolean, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Double, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Float, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Integer, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Long, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.util.List<java.lang.Integer>, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, double[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, float[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, int[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, long[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, boolean[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[][], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.IBinder, boolean):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
     arg types: [android.os.Parcel, int, com.google.android.gms.common.ConnectionResult, int, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void */
    public final void writeToParcel(Parcel parcel, int i) {
        int zza2 = zzbky.zza(parcel);
        zzbky.zza(parcel, 1, this.zza);
        zzbky.zza(parcel, 2, this.zzb, false);
        zzbky.zza(parcel, 3, (Parcelable) this.zzc, i, false);
        zzbky.zza(parcel, 4, this.zzd);
        zzbky.zza(parcel, 5, this.zze);
        zzbky.zza(parcel, zza2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzax)) {
            return false;
        }
        zzax zzax = (zzax) obj;
        if (!this.zzc.equals(zzax.zzc) || !zza().equals(zzax.zza())) {
            return false;
        }
        return true;
    }
}
