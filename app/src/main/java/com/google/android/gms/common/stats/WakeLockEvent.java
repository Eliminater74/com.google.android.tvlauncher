package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.android.gms.internal.zzbky;

import java.util.List;

public final class WakeLockEvent extends StatsEvent {
    public static final Parcelable.Creator<WakeLockEvent> CREATOR = new zzd();
    private final int zza;
    private final long zzb;
    private final String zzd;
    private final String zze;
    private final String zzf;
    private final int zzg;
    private final List<String> zzh;
    private final String zzi;
    private final long zzj;
    private final String zzl;
    private final float zzm;
    private final long zzn;
    private int zzc;
    private int zzk;
    private long zzo;

    WakeLockEvent(int i, long j, int i2, String str, int i3, List<String> list, String str2, long j2, int i4, String str3, String str4, float f, long j3, String str5) {
        this.zza = i;
        this.zzb = j;
        this.zzc = i2;
        this.zzd = str;
        this.zze = str3;
        this.zzf = str5;
        this.zzg = i3;
        this.zzo = -1;
        this.zzh = list;
        this.zzi = str2;
        this.zzj = j2;
        this.zzk = i4;
        this.zzl = str4;
        this.zzm = f;
        this.zzn = j3;
    }

    public WakeLockEvent(long j, int i, String str, int i2, List<String> list, String str2, long j2, int i3, String str3, String str4, float f, long j3, String str5) {
        this(2, j, i, str, i2, list, str2, j2, i3, str3, str4, f, j3, str5);
    }

    public final long zza() {
        return this.zzb;
    }

    public final int zzb() {
        return this.zzc;
    }

    public final long zzc() {
        return this.zzo;
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
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zzb(android.os.Parcel, int, java.util.List<java.lang.String>, boolean):void
     arg types: [android.os.Parcel, int, java.util.List<java.lang.String>, int]
     candidates:
      com.google.android.gms.internal.zzbky.zzb(android.os.Parcel, int, android.util.SparseArray<byte[]>, boolean):void
      com.google.android.gms.internal.zzbky.zzb(android.os.Parcel, int, java.util.List<java.lang.String>, boolean):void */
    public final void writeToParcel(Parcel parcel, int i) {
        int zza2 = zzbky.zza(parcel);
        zzbky.zza(parcel, 1, this.zza);
        zzbky.zza(parcel, 2, zza());
        zzbky.zza(parcel, 4, this.zzd, false);
        zzbky.zza(parcel, 5, this.zzg);
        zzbky.zzb(parcel, 6, this.zzh, false);
        zzbky.zza(parcel, 8, this.zzj);
        zzbky.zza(parcel, 10, this.zze, false);
        zzbky.zza(parcel, 11, zzb());
        zzbky.zza(parcel, 12, this.zzi, false);
        zzbky.zza(parcel, 13, this.zzl, false);
        zzbky.zza(parcel, 14, this.zzk);
        zzbky.zza(parcel, 15, this.zzm);
        zzbky.zza(parcel, 16, this.zzn);
        zzbky.zza(parcel, 17, this.zzf, false);
        zzbky.zza(parcel, zza2);
    }

    public final String zzd() {
        String str;
        String str2 = this.zzd;
        int i = this.zzg;
        List<String> list = this.zzh;
        String str3 = "";
        if (list == null) {
            str = str3;
        } else {
            str = TextUtils.join(",", list);
        }
        int i2 = this.zzk;
        String str4 = this.zze;
        if (str4 == null) {
            str4 = str3;
        }
        String str5 = this.zzl;
        if (str5 == null) {
            str5 = str3;
        }
        float f = this.zzm;
        String str6 = this.zzf;
        if (str6 != null) {
            str3 = str6;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 45 + String.valueOf(str).length() + String.valueOf(str4).length() + String.valueOf(str5).length() + String.valueOf(str3).length());
        sb.append("\t");
        sb.append(str2);
        sb.append("\t");
        sb.append(i);
        sb.append("\t");
        sb.append(str);
        sb.append("\t");
        sb.append(i2);
        sb.append("\t");
        sb.append(str4);
        sb.append("\t");
        sb.append(str5);
        sb.append("\t");
        sb.append(f);
        sb.append("\t");
        sb.append(str3);
        return sb.toString();
    }
}
