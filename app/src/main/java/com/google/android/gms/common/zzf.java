package com.google.android.gms.common;

import android.os.RemoteException;
import android.util.Log;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

@Hide
/* compiled from: GoogleCertificates */
abstract class zzf extends zzab {
    private int zza;

    protected zzf(byte[] bArr) {
        zzau.zzb(bArr.length == 25);
        this.zza = Arrays.hashCode(bArr);
    }

    protected static byte[] zza(String str) {
        try {
            return str.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    /* access modifiers changed from: package-private */
    public abstract byte[] zza();

    public int hashCode() {
        return this.zza;
    }

    public boolean equals(Object obj) {
        IObjectWrapper zzb;
        if (obj == null || !(obj instanceof zzaa)) {
            return false;
        }
        try {
            zzaa zzaa = (zzaa) obj;
            if (zzaa.zzc() == hashCode() && (zzb = zzaa.zzb()) != null) {
                return Arrays.equals(zza(), (byte[]) zzn.zza(zzb));
            }
            return false;
        } catch (RemoteException e) {
            Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
            return false;
        }
    }

    public final IObjectWrapper zzb() {
        return zzn.zza(zza());
    }

    public final int zzc() {
        return hashCode();
    }
}
