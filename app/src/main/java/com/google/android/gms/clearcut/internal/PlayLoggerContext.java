package com.google.android.gms.clearcut.internal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzak;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.internal.zzbkv;
import com.google.android.gms.internal.zzbky;

import java.util.Arrays;

@Hide
public class PlayLoggerContext extends zzbkv {
    public static final Parcelable.Creator<PlayLoggerContext> CREATOR = new zzu();
    public final boolean isAnonymous;
    public final boolean logAndroidId;
    public final int logSource;
    public final String logSourceName;
    public final String loggingId;
    public final String packageName;
    public final int packageVersionCode;
    public final int qosTier;
    public final String uploadAccountName;

    public PlayLoggerContext(String str, int i, int i2, String str2, String str3, boolean z, String str4, boolean z2, int i3) {
        this.packageName = str;
        this.packageVersionCode = i;
        this.logSource = i2;
        this.uploadAccountName = str2;
        this.loggingId = str3;
        this.logAndroidId = z;
        this.logSourceName = str4;
        this.isAnonymous = z2;
        this.qosTier = i3;
    }

    @Deprecated
    public PlayLoggerContext(String str, int i, int i2, String str2, String str3, boolean z) {
        this.packageName = (String) zzau.zza((Object) str);
        this.packageVersionCode = i;
        this.logSource = i2;
        this.logSourceName = null;
        this.uploadAccountName = str2;
        this.loggingId = str3;
        this.logAndroidId = z;
        this.isAnonymous = false;
        this.qosTier = 0;
    }

    public PlayLoggerContext(String str, int i, int i2, String str2, String str3, String str4, boolean z, int i3) {
        this.packageName = (String) zzau.zza((Object) str);
        this.packageVersionCode = i;
        this.logSource = i2;
        this.logSourceName = str2;
        this.uploadAccountName = str3;
        this.loggingId = str4;
        this.logAndroidId = !z;
        this.isAnonymous = z;
        this.qosTier = i3;
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
        zzbky.zza(parcel, 2, this.packageName, false);
        zzbky.zza(parcel, 3, this.packageVersionCode);
        zzbky.zza(parcel, 4, this.logSource);
        zzbky.zza(parcel, 5, this.uploadAccountName, false);
        zzbky.zza(parcel, 6, this.loggingId, false);
        zzbky.zza(parcel, 7, this.logAndroidId);
        zzbky.zza(parcel, 8, this.logSourceName, false);
        zzbky.zza(parcel, 9, this.isAnonymous);
        zzbky.zza(parcel, 10, this.qosTier);
        zzbky.zza(parcel, zza);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.packageName, Integer.valueOf(this.packageVersionCode), Integer.valueOf(this.logSource), this.logSourceName, this.uploadAccountName, this.loggingId, Boolean.valueOf(this.logAndroidId), Boolean.valueOf(this.isAnonymous), Integer.valueOf(this.qosTier)});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PlayLoggerContext)) {
            return false;
        }
        PlayLoggerContext playLoggerContext = (PlayLoggerContext) obj;
        if (zzak.zza(this.packageName, playLoggerContext.packageName) && this.packageVersionCode == playLoggerContext.packageVersionCode && this.logSource == playLoggerContext.logSource && zzak.zza(this.logSourceName, playLoggerContext.logSourceName) && zzak.zza(this.uploadAccountName, playLoggerContext.uploadAccountName) && zzak.zza(this.loggingId, playLoggerContext.loggingId) && this.logAndroidId == playLoggerContext.logAndroidId && this.isAnonymous == playLoggerContext.isAnonymous && this.qosTier == playLoggerContext.qosTier) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "PlayLoggerContext[" + "package=" + this.packageName + ',' + "packageVersionCode=" + this.packageVersionCode + ',' + "logSource=" + this.logSource + ',' + "logSourceName=" + this.logSourceName + ',' + "uploadAccount=" + this.uploadAccountName + ',' + "loggingId=" + this.loggingId + ',' + "logAndroidId=" + this.logAndroidId + ',' + "isAnonymous=" + this.isAnonymous + ',' + "qosTier=" + this.qosTier + "]";
    }
}
