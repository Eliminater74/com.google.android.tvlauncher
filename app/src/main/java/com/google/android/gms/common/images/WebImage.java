package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzak;
import com.google.android.gms.internal.zzbkv;
import com.google.android.gms.internal.zzbky;
import java.util.Arrays;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class WebImage extends zzbkv {
    public static final Parcelable.Creator<WebImage> CREATOR = new zze();
    private final int zza;
    private final Uri zzb;
    private final int zzc;
    private final int zzd;

    WebImage(int i, Uri uri, int i2, int i3) {
        this.zza = i;
        this.zzb = uri;
        this.zzc = i2;
        this.zzd = i3;
    }

    public WebImage(Uri uri, int i, int i2) throws IllegalArgumentException {
        this(1, uri, i, i2);
        if (uri == null) {
            throw new IllegalArgumentException("url cannot be null");
        } else if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("width and height must not be negative");
        }
    }

    public WebImage(Uri uri) throws IllegalArgumentException {
        this(uri, 0, 0);
    }

    @Hide
    public WebImage(JSONObject jSONObject) throws IllegalArgumentException {
        this(zza(jSONObject), jSONObject.optInt("width", 0), jSONObject.optInt("height", 0));
    }

    private static Uri zza(JSONObject jSONObject) {
        if (jSONObject.has("url")) {
            try {
                return Uri.parse(jSONObject.getString("url"));
            } catch (JSONException e) {
            }
        }
        return null;
    }

    public final Uri getUrl() {
        return this.zzb;
    }

    public final int getWidth() {
        return this.zzc;
    }

    public final int getHeight() {
        return this.zzd;
    }

    public final String toString() {
        return String.format(Locale.US, "Image %dx%d %s", Integer.valueOf(this.zzc), Integer.valueOf(this.zzd), this.zzb.toString());
    }

    @Hide
    public final JSONObject zza() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("url", this.zzb.toString());
            jSONObject.put("width", this.zzc);
            jSONObject.put("height", this.zzd);
        } catch (JSONException e) {
        }
        return jSONObject;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof WebImage)) {
            return false;
        }
        WebImage webImage = (WebImage) obj;
        if (zzak.zza(this.zzb, webImage.zzb) && this.zzc == webImage.zzc && this.zzd == webImage.zzd) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzb, Integer.valueOf(this.zzc), Integer.valueOf(this.zzd)});
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
     arg types: [android.os.Parcel, int, android.net.Uri, int, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void */
    public final void writeToParcel(Parcel parcel, int i) {
        int zza2 = zzbky.zza(parcel);
        zzbky.zza(parcel, 1, this.zza);
        zzbky.zza(parcel, 2, (Parcelable) getUrl(), i, false);
        zzbky.zza(parcel, 3, getWidth());
        zzbky.zza(parcel, 4, getHeight());
        zzbky.zza(parcel, zza2);
    }
}
