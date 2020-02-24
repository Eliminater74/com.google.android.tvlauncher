package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: IAdvertisingIdService */
public final class zzgi extends zzez implements zzgg {
    zzgi(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
    }

    public final String zza() throws RemoteException {
        Parcel zza = zza(1, mo17545a_());
        String readString = zza.readString();
        zza.recycle();
        return readString;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzfb.zza(android.os.Parcel, boolean):void
     arg types: [android.os.Parcel, int]
     candidates:
      com.google.android.gms.internal.zzfb.zza(android.os.Parcel, android.os.Parcelable$Creator):T
      com.google.android.gms.internal.zzfb.zza(android.os.Parcel, android.os.IInterface):void
      com.google.android.gms.internal.zzfb.zza(android.os.Parcel, android.os.Parcelable):void
      com.google.android.gms.internal.zzfb.zza(android.os.Parcel, java.lang.CharSequence):void
      com.google.android.gms.internal.zzfb.zza(android.os.Parcel, boolean):void */
    public final boolean zza(boolean z) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, true);
        Parcel zza = zza(2, a_);
        boolean zza2 = zzfb.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final boolean zzb() throws RemoteException {
        Parcel zza = zza(6, mo17545a_());
        boolean zza2 = zzfb.zza(zza);
        zza.recycle();
        return zza2;
    }
}
