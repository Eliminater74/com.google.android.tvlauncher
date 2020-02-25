package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.support.p001v4.util.ArraySet;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzau;

/* compiled from: ConnectionlessLifecycleHelper */
public class zzai extends zzp {
    private final ArraySet<zzi<?>> zze = new ArraySet<>();
    private zzbn zzf;

    private zzai(zzcf zzcf) {
        super(zzcf);
        this.zzd.zza("ConnectionlessLifecycleHelper", this);
    }

    public static void zza(Activity activity, zzbn zzbn, zzi<?> zzi) {
        zzcf zzb = zzb(activity);
        zzai zzai = (zzai) zzb.zza("ConnectionlessLifecycleHelper", zzai.class);
        if (zzai == null) {
            zzai = new zzai(zzb);
        }
        zzai.zzf = zzbn;
        zzau.zza(zzi, "ApiKey cannot be null");
        zzai.zze.add(zzi);
        zzbn.zza(zzai);
    }

    public final void zzb() {
        super.zzb();
        zzi();
    }

    public final void zze() {
        super.zze();
        zzi();
    }

    public final void zza() {
        super.zza();
        this.zzf.zzb(this);
    }

    /* access modifiers changed from: protected */
    public final void zza(ConnectionResult connectionResult, int i) {
        this.zzf.zzb(connectionResult, i);
    }

    /* access modifiers changed from: protected */
    public final void zzc() {
        this.zzf.zzd();
    }

    /* access modifiers changed from: package-private */
    public final ArraySet<zzi<?>> zzf() {
        return this.zze;
    }

    private final void zzi() {
        if (!this.zze.isEmpty()) {
            this.zzf.zza(this);
        }
    }
}
