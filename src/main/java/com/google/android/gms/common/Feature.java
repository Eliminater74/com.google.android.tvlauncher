package com.google.android.gms.common;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbkv;
import com.google.android.gms.internal.zzbky;

@Hide
public class Feature extends zzbkv {
    public static final Parcelable.Creator<Feature> CREATOR = new zzc();
    public final String name;
    public final int version;

    public Feature(String str, int i) {
        this.name = str;
        this.version = i;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String, boolean):void
     arg types: [android.os.Parcel, int, java.lang.String, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Bundle, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.IBinder, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcel, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.util.SparseArray<java.lang.String>, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Boolean, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Double, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Float, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Integer, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Long, boolean):void
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
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String, boolean):void */
    public void writeToParcel(Parcel parcel, int i) {
        int zza = zzbky.zza(parcel);
        zzbky.zza(parcel, 1, this.name, false);
        zzbky.zza(parcel, 2, this.version);
        zzbky.zza(parcel, zza);
    }
}
