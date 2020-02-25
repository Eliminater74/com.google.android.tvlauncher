package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamic.zzp;
import com.google.android.gms.dynamic.zzq;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

@Hide
/* compiled from: SocketFactoryCreator */
public final class zzblu extends zzp<zzblq> {
    private static zzblu zza;

    protected zzblu() {
        super("com.google.android.gms.common.net.SocketFactoryCreatorImpl");
    }

    public static zzblu zza() {
        if (zza == null) {
            zza = new zzblu();
        }
        return zza;
    }

    public final SSLSocketFactory zza(Context context, KeyManager[] keyManagerArr, TrustManager[] trustManagerArr, boolean z) {
        try {
            return (SSLSocketFactory) zzn.zza(((zzblq) zzb(context)).zza(zzn.zza(context), zzn.zza((Object) null), zzn.zza(trustManagerArr), z));
        } catch (RemoteException | zzq e) {
            throw new RuntimeException(e);
        }
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Object zza(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.net.ISocketFactoryCreator");
        if (queryLocalInterface instanceof zzblq) {
            return (zzblq) queryLocalInterface;
        }
        return new zzblr(iBinder);
    }
}
