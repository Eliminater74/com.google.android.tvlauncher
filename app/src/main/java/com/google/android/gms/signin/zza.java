package com.google.android.gms.signin;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzemc;

/* compiled from: SignIn */
public final class zza {
    @Hide
    public static final Api.zza<zzemc, SignInOptions> zza = new zzb();
    @Hide
    private static final Api.ClientKey<zzemc> zzc = new Api.ClientKey<>();
    @Hide
    public static final Api<SignInOptions> zzb = new Api<>("SignIn.API", zza, zzc);
    @Hide
    private static final Api.ClientKey<zzemc> zzd = new Api.ClientKey<>();
    @Hide
    private static final Api.zza<zzemc, Object> zze = new zzc();
    private static final Scope zzf = new Scope("profile");
    private static final Scope zzg = new Scope("email");
    @Hide
    private static final Api<Object> zzh = new Api<>("SignIn.INTERNAL_API", zze, zzd);
}
