package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzak;
import com.google.android.gms.common.internal.zzam;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.internal.zzbkv;
import com.google.android.gms.internal.zzbky;
import com.google.android.tvlauncher.notifications.NotificationsContract;

import java.util.Arrays;

public class PlaceReport extends zzbkv implements ReflectedParcelable {
    public static final Parcelable.Creator<PlaceReport> CREATOR = new zzm();
    @Hide
    public static final String SOURCE_INFERRED_GEOFENCING = "inferredGeofencing";
    @Hide
    public static final String SOURCE_INFERRED_RADIO_SIGNALS = "inferredRadioSignals";
    @Hide
    public static final String SOURCE_INFERRED_REVERSE_GEOCODING = "inferredReverseGeocoding";
    @Hide
    public static final String SOURCE_INFERRED_SNAPPED_TO_ROAD = "inferredSnappedToRoad";
    @Hide
    public static final String SOURCE_UNKNOWN = "unknown";
    @Hide
    public static final String SOURCE_USER_REPORTED = "userReported";
    private final int zza;
    private final String zzb;
    private final String zzc;
    private final String zzd;

    @Hide
    PlaceReport(int i, String str, String str2, String str3) {
        this.zza = i;
        this.zzb = str;
        this.zzc = str2;
        this.zzd = str3;
    }

    public static PlaceReport create(String str, String str2) {
        return create(str, str2, "unknown");
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    @Hide
    public static PlaceReport create(String str, String str2, String str3) {
        char c;
        zzau.zza((Object) str);
        zzau.zza(str2);
        zzau.zza(str3);
        boolean z = false;
        switch (str3.hashCode()) {
            case -1436706272:
                if (str3.equals(SOURCE_INFERRED_GEOFENCING)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1194968642:
                if (str3.equals(SOURCE_USER_REPORTED)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -284840886:
                if (str3.equals("unknown")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -262743844:
                if (str3.equals(SOURCE_INFERRED_REVERSE_GEOCODING)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1164924125:
                if (str3.equals(SOURCE_INFERRED_SNAPPED_TO_ROAD)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1287171955:
                if (str3.equals(SOURCE_INFERRED_RADIO_SIGNALS)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0 || c == 1 || c == 2 || c == 3 || c == 4 || c == 5) {
            z = true;
        }
        zzau.zzb(z, "Invalid source");
        return new PlaceReport(1, str, str2, str3);
    }

    public String getPlaceId() {
        return this.zzb;
    }

    public String getTag() {
        return this.zzc;
    }

    @Hide
    public String getSource() {
        return this.zzd;
    }

    @Hide
    public String toString() {
        zzam zza2 = zzak.zza(this);
        zza2.zza("placeId", this.zzb);
        zza2.zza(NotificationsContract.COLUMN_TAG, this.zzc);
        if (!"unknown".equals(this.zzd)) {
            zza2.zza("source", this.zzd);
        }
        return zza2.toString();
    }

    @Hide
    public boolean equals(Object obj) {
        if (!(obj instanceof PlaceReport)) {
            return false;
        }
        PlaceReport placeReport = (PlaceReport) obj;
        if (!zzak.zza(this.zzb, placeReport.zzb) || !zzak.zza(this.zzc, placeReport.zzc) || !zzak.zza(this.zzd, placeReport.zzd)) {
            return false;
        }
        return true;
    }

    @Hide
    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzb, this.zzc, this.zzd});
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
    @Hide
    public void writeToParcel(Parcel parcel, int i) {
        int zza2 = zzbky.zza(parcel);
        zzbky.zza(parcel, 1, this.zza);
        zzbky.zza(parcel, 2, getPlaceId(), false);
        zzbky.zza(parcel, 3, getTag(), false);
        zzbky.zza(parcel, 4, getSource(), false);
        zzbky.zza(parcel, zza2);
    }
}
