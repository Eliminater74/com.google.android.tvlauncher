package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.zzak;

import java.util.Arrays;

/* compiled from: ApiKey */
public final class zzi<O extends Api.ApiOptions> {
    private final boolean zza = true;
    private final int zzb;
    private final Api<O> zzc;
    private final O zzd;

    private zzi(Api<O> api, O o) {
        this.zzc = api;
        this.zzd = o;
        this.zzb = Arrays.hashCode(new Object[]{this.zzc, this.zzd});
    }

    private zzi(Api<O> api) {
        this.zzc = api;
        this.zzd = null;
        this.zzb = System.identityHashCode(this);
    }

    public static <O extends Api.ApiOptions> zzi<O> zza(Api<O> api, O o) {
        return new zzi<>(api, o);
    }

    public static <O extends Api.ApiOptions> zzi<O> zza(Api<O> api) {
        return new zzi<>(api);
    }

    public final String zza() {
        return this.zzc.zzd();
    }

    public final int hashCode() {
        return this.zzb;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzi)) {
            return false;
        }
        zzi zzi = (zzi) obj;
        if (this.zza || zzi.zza || !zzak.zza(this.zzc, zzi.zzc) || !zzak.zza(this.zzd, zzi.zzd)) {
            return false;
        }
        return true;
    }
}
