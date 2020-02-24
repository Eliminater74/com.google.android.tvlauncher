package com.google.android.gms.internal;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/* compiled from: Cache */
public final class zzc {
    public byte[] zza;
    public String zzb;
    public long zzc;
    public long zzd;
    public long zze;
    public long zzf;
    public Map<String, String> zzg = Collections.emptyMap();
    public List<zzl> zzh;

    public final boolean zza() {
        return this.zze < System.currentTimeMillis();
    }
}
