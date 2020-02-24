package com.google.android.gms.clearcut;

import android.content.Context;
import com.google.android.gms.clearcut.internal.zza;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.tasks.Task;

public class BootCountClient extends GoogleApi<Api.ApiOptions.NoOptions> {
    private static final Api.ClientKey<zza> zzb = new Api.ClientKey<>();
    private static final Api.zza<zza, Api.ApiOptions.NoOptions> zzc = new zza();
    private static final Api<Api.ApiOptions.NoOptions> zzd = new Api<>("BootCount.API", zzc, zzb);

    BootCountClient(Context context) {
        super(context, zzd, (Api.ApiOptions) null, GoogleApi.zza.zza);
    }

    public Task<Integer> getBootCount() {
        return zza(new zzb(this));
    }
}
