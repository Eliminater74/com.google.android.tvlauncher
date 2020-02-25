package com.google.android.gms.phenotype;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.internal.zzdyt;
import com.google.android.gms.internal.zzeac;

@KeepForSdk
public final class Phenotype {
    public static final String ACTION_UPDATE = "com.google.android.gms.phenotype.UPDATE";
    public static final String CONFIGURATION_VERSION_FLAG_NAME = "__phenotype_configuration_version";
    public static final String EMPTY_ALTERNATE = "com.google.EMPTY";
    public static final String EXTRA_PACKAGE_NAME = "com.google.android.gms.phenotype.PACKAGE_NAME";
    public static final String EXTRA_UPDATE_REASON = "com.google.android.gms.phenotype.UPDATE_REASON";
    public static final String EXTRA_URGENT_UPDATE = "com.google.android.gms.phenotype.URGENT";
    public static final String LOGGED_OUT_USER = "";
    @Deprecated
    public static final PhenotypeApi PhenotypeApi = new zzdyt();
    public static final String SERVER_TOKEN_FLAG_NAME = "__phenotype_server_token";
    private static final Api.ClientKey<zzeac> zza = new Api.ClientKey<>();
    private static final Api.zza<zzeac, Api.ApiOptions.NoOptions> zzb = new zzn();
    @Deprecated
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("Phenotype.API", zzb, zza);

    private Phenotype() {
    }

    public static PhenotypeClient getInstance(Activity activity) {
        return new PhenotypeClient(activity);
    }

    public static PhenotypeClient getInstance(Context context) {
        return new PhenotypeClient(context);
    }

    @KeepForSdk
    public static Uri getContentProviderUri(String str) {
        String valueOf = String.valueOf(Uri.encode(str));
        return Uri.parse(valueOf.length() != 0 ? "content://com.google.android.gms.phenotype/".concat(valueOf) : new String("content://com.google.android.gms.phenotype/"));
    }
}
