package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzau;

/* compiled from: ListenerHolder */
public final class zzcj<L> {
    private final zzck zza;
    private volatile L zzb;
    private final zzcl<L> zzc;

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
     arg types: [L, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T */
    zzcj(@NonNull Looper looper, @NonNull L l, @NonNull String str) {
        this.zza = new zzck(this, looper);
        this.zzb = zzau.zza((Object) l, (Object) "Listener must not be null");
        this.zzc = new zzcl<>(l, zzau.zza(str));
    }

    public final void zza(zzcm<? super L> zzcm) {
        zzau.zza(zzcm, "Notifier must not be null");
        this.zza.sendMessage(this.zza.obtainMessage(1, zzcm));
    }

    public final boolean zza() {
        return this.zzb != null;
    }

    public final void zzb() {
        this.zzb = null;
    }

    @NonNull
    public final zzcl<L> zzc() {
        return this.zzc;
    }

    /* access modifiers changed from: package-private */
    public final void zzb(zzcm<? super L> zzcm) {
        L l = this.zzb;
        if (l == null) {
            zzcm.zza();
            return;
        }
        try {
            zzcm.zza(l);
        } catch (RuntimeException e) {
            zzcm.zza();
            throw e;
        }
    }
}
