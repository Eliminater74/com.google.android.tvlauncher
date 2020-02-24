package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.internal.Hide;

@Hide
/* compiled from: GmscoreFlag */
public final class zzd {
    private SharedPreferences zza;

    @Hide
    public zzd(Context context) {
        SharedPreferences sharedPreferences;
        try {
            Context remoteContext = GooglePlayServicesUtilLight.getRemoteContext(context);
            if (remoteContext == null) {
                sharedPreferences = null;
            } else {
                sharedPreferences = remoteContext.getSharedPreferences("google_ads_flags", 0);
            }
            this.zza = sharedPreferences;
        } catch (Throwable th) {
            Log.w("GmscoreFlag", "Error while getting SharedPreferences ", th);
            this.zza = null;
        }
    }

    public final boolean zza(String str, boolean z) {
        try {
            if (this.zza == null) {
                return false;
            }
            return this.zza.getBoolean(str, false);
        } catch (Throwable th) {
            Log.w("GmscoreFlag", "Error while reading from SharedPreferences ", th);
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public final float zza(String str, float f) {
        try {
            if (this.zza == null) {
                return 0.0f;
            }
            return this.zza.getFloat(str, 0.0f);
        } catch (Throwable th) {
            Log.w("GmscoreFlag", "Error while reading from SharedPreferences ", th);
            return 0.0f;
        }
    }

    /* access modifiers changed from: package-private */
    public final String zza(String str, String str2) {
        try {
            if (this.zza == null) {
                return str2;
            }
            return this.zza.getString(str, str2);
        } catch (Throwable th) {
            Log.w("GmscoreFlag", "Error while reading from SharedPreferences ", th);
            return str2;
        }
    }
}
