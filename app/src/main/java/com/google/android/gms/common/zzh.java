package com.google.android.gms.common;

import com.google.android.gms.common.internal.Hide;

import java.lang.ref.WeakReference;

@Hide
/* compiled from: GoogleCertificates */
abstract class zzh extends zzf {
    private static final WeakReference<byte[]> zzb = new WeakReference<>(null);
    private WeakReference<byte[]> zza = zzb;

    zzh(byte[] bArr) {
        super(bArr);
    }

    /* access modifiers changed from: protected */
    public abstract byte[] zzd();

    /* access modifiers changed from: package-private */
    public final byte[] zza() {
        byte[] bArr;
        synchronized (this) {
            bArr = this.zza.get();
            if (bArr == null) {
                bArr = zzd();
                this.zza = new WeakReference<>(bArr);
            }
        }
        return bArr;
    }
}
