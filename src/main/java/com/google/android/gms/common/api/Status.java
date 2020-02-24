package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzak;
import com.google.android.gms.internal.zzbkv;
import com.google.android.gms.internal.zzbky;
import java.util.Arrays;

public final class Status extends zzbkv implements Result, ReflectedParcelable {
    public static final Parcelable.Creator<Status> CREATOR = new zzf();
    @Hide
    public static final Status zza = new Status(0);
    @Hide
    public static final Status zzb = new Status(14);
    @Hide
    public static final Status zzc = new Status(8);
    @Hide
    public static final Status zzd = new Status(15);
    @Hide
    public static final Status zze = new Status(16);
    @Hide
    private static final Status zzf = new Status(17);
    @Hide
    private static final Status zzg = new Status(18);
    private final int zzh;
    private final int zzi;
    @Nullable
    private final String zzj;
    @Nullable
    private final PendingIntent zzk;

    Status(int i, int i2, @Nullable String str, @Nullable PendingIntent pendingIntent) {
        this.zzh = i;
        this.zzi = i2;
        this.zzj = str;
        this.zzk = pendingIntent;
    }

    public Status(int i) {
        this(i, null);
    }

    public Status(int i, @Nullable String str) {
        this(1, i, str, null);
    }

    public Status(int i, @Nullable String str, @Nullable PendingIntent pendingIntent) {
        this(1, i, str, pendingIntent);
    }

    public final void startResolutionForResult(Activity activity, int i) throws IntentSender.SendIntentException {
        if (hasResolution()) {
            activity.startIntentSenderForResult(this.zzk.getIntentSender(), i, null, 0, 0, 0);
        }
    }

    @Nullable
    public final String getStatusMessage() {
        return this.zzj;
    }

    public final boolean hasResolution() {
        return this.zzk != null;
    }

    public final boolean isSuccess() {
        return this.zzi <= 0;
    }

    public final boolean isCanceled() {
        return this.zzi == 16;
    }

    public final boolean isInterrupted() {
        return this.zzi == 14;
    }

    public final int getStatusCode() {
        return this.zzi;
    }

    public final PendingIntent getResolution() {
        return this.zzk;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zzh), Integer.valueOf(this.zzi), this.zzj, this.zzk});
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof Status)) {
            return false;
        }
        Status status = (Status) obj;
        if (this.zzh != status.zzh || this.zzi != status.zzi || !zzak.zza(this.zzj, status.zzj) || !zzak.zza(this.zzk, status.zzk)) {
            return false;
        }
        return true;
    }

    @Hide
    public final String zza() {
        String str = this.zzj;
        if (str != null) {
            return str;
        }
        return CommonStatusCodes.getStatusCodeString(this.zzi);
    }

    public final String toString() {
        return zzak.zza(this).zza("statusCode", zza()).zza("resolution", this.zzk).toString();
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
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
     arg types: [android.os.Parcel, int, android.app.PendingIntent, int, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void */
    public final void writeToParcel(Parcel parcel, int i) {
        int zza2 = zzbky.zza(parcel);
        zzbky.zza(parcel, 1, getStatusCode());
        zzbky.zza(parcel, 2, getStatusMessage(), false);
        zzbky.zza(parcel, 3, (Parcelable) this.zzk, i, false);
        zzbky.zza(parcel, 1000, this.zzh);
        zzbky.zza(parcel, zza2);
    }

    public final Status getStatus() {
        return this;
    }
}
