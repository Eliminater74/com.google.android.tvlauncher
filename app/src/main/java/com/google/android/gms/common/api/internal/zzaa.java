package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.zzd;

@Hide
/* compiled from: ConnectionlessApi */
public final class zzaa<O extends Api.ApiOptions> extends GoogleApi<O> {
    private final Api.Client zzb;
    private final zzu zzc;
    private final ClientSettings zzd;
    private final Api.zza<? extends zzd, SignInOptions> zze;

    public zzaa(@NonNull Context context, Api<O> api, Looper looper, @NonNull Api.Client client, @NonNull zzu zzu, ClientSettings clientSettings, Api.zza<? extends zzd, SignInOptions> zza) {
        super(context, api, looper);
        this.zzb = client;
        this.zzc = zzu;
        this.zzd = clientSettings;
        this.zze = zza;
        this.zza.zza(this);
    }

    public final Api.Client zza() {
        return this.zzb;
    }

    public final Api.Client zza(Looper looper, zzbp<O> zzbp) {
        this.zzc.zza(zzbp);
        return this.zzb;
    }

    public final zzdb zza(Context context, Handler handler) {
        return new zzdb(context, handler, this.zzd, this.zze);
    }
}
