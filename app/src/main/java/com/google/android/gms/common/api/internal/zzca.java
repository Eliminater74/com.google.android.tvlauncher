package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

import com.google.android.gms.common.C0952R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzaj;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.common.internal.zzbe;

@Deprecated
/* compiled from: GoogleServices */
public final class zzca {
    private static final Object zza = new Object();
    private static zzca zzb;
    private final String zzc;
    private final Status zzd;
    private final boolean zze;
    private final boolean zzf;

    private zzca(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("google_app_measurement_enable", "integer", resources.getResourcePackageName(C0952R.string.common_google_play_services_unknown_issue));
        boolean z = true;
        if (identifier != 0) {
            z = resources.getInteger(identifier) == 0 ? false : z;
            this.zzf = !z;
        } else {
            this.zzf = false;
        }
        this.zze = z;
        String zza2 = zzaj.zza(context);
        zza2 = zza2 == null ? new zzbe(context).zza("google_app_id") : zza2;
        if (TextUtils.isEmpty(zza2)) {
            this.zzd = new Status(10, "Missing google app id value from from string resources with name google_app_id.");
            this.zzc = null;
            return;
        }
        this.zzc = zza2;
        this.zzd = Status.zza;
    }

    public static Status zza(Context context) {
        Status status;
        zzau.zza(context, "Context must not be null.");
        synchronized (zza) {
            if (zzb == null) {
                zzb = new zzca(context);
            }
            status = zzb.zzd;
        }
        return status;
    }

    public static String zza() {
        return zza("getGoogleAppId").zzc;
    }

    public static boolean zzb() {
        return zza("isMeasurementExplicitlyDisabled").zzf;
    }

    private static zzca zza(String str) {
        zzca zzca;
        synchronized (zza) {
            if (zzb != null) {
                zzca = zzb;
            } else {
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 34);
                sb.append("Initialize must be called before ");
                sb.append(str);
                sb.append(".");
                throw new IllegalStateException(sb.toString());
            }
        }
        return zzca;
    }
}
