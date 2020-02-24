package com.google.android.gms.internal;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/* compiled from: HttpResponse */
public final class zzaq {
    private final int zza;
    private final List<zzl> zzb;
    private final int zzc;
    private final InputStream zzd;

    public zzaq(int i, List<zzl> list) {
        this(i, list, -1, null);
    }

    public zzaq(int i, List<zzl> list, int i2, InputStream inputStream) {
        this.zza = i;
        this.zzb = list;
        this.zzc = i2;
        this.zzd = inputStream;
    }

    public final int zza() {
        return this.zza;
    }

    public final List<zzl> zzb() {
        return Collections.unmodifiableList(this.zzb);
    }

    public final int zzc() {
        return this.zzc;
    }

    public final InputStream zzd() {
        return this.zzd;
    }
}
