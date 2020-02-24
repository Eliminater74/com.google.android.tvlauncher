package com.google.android.gms.signin;

import android.support.annotation.Nullable;
import com.google.android.gms.common.api.Api;

public final class SignInOptions implements Api.ApiOptions.Optional {
    public static final SignInOptions DEFAULT = new SignInOptions(false, false, null, false, null, false, null, null);
    private final boolean zza = false;
    private final boolean zzb = false;
    private final String zzc = null;
    private final boolean zzd = false;
    private final String zze = null;
    private final boolean zzf = false;
    private final Long zzg = null;
    private final Long zzh = null;

    public static final class zza {
    }

    private SignInOptions(boolean z, boolean z2, String str, boolean z3, String str2, boolean z4, Long l, Long l2) {
    }

    public final boolean isOfflineAccessRequested() {
        return this.zza;
    }

    public final boolean isIdTokenRequested() {
        return this.zzb;
    }

    public final String getServerClientId() {
        return this.zzc;
    }

    public final boolean isForceCodeForRefreshToken() {
        return this.zzd;
    }

    @Nullable
    public final String getHostedDomain() {
        return this.zze;
    }

    public final boolean waitForAccessTokenRefresh() {
        return this.zzf;
    }

    @Nullable
    public final Long getAuthApiSignInModuleVersion() {
        return this.zzg;
    }

    @Nullable
    public final Long getRealClientLibraryVersion() {
        return this.zzh;
    }

    static {
        new zza();
    }
}
