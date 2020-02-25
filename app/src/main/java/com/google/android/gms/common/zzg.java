package com.google.android.gms.common;

import com.google.android.gms.common.internal.Hide;

import java.util.Arrays;

@Hide
/* compiled from: GoogleCertificates */
final class zzg extends zzf {
    private final byte[] zza;

    zzg(byte[] bArr) {
        super(Arrays.copyOfRange(bArr, 0, 25));
        this.zza = bArr;
    }

    /* access modifiers changed from: package-private */
    public final byte[] zza() {
        return this.zza;
    }
}
