package com.google.android.gms.clearcut.internal;

import android.os.Parcel;
import android.os.RemoteException;

import com.google.android.gms.clearcut.CollectForDebugParcelable;
import com.google.android.gms.clearcut.LogEventParcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.zzfa;
import com.google.android.gms.internal.zzfb;

/* compiled from: IClearcutLoggerCallbacks */
public abstract class zzp extends zzfa implements zzo {
    public zzp() {
        attachInterface(this, "com.google.android.gms.clearcut.internal.IClearcutLoggerCallbacks");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                zza((Status) zzfb.zza(parcel, Status.CREATOR));
                break;
            case 2:
                zzb((Status) zzfb.zza(parcel, Status.CREATOR));
                break;
            case 3:
                zza((Status) zzfb.zza(parcel, Status.CREATOR), parcel.readLong());
                break;
            case 4:
                zzc((Status) zzfb.zza(parcel, Status.CREATOR));
                break;
            case 5:
                zzb((Status) zzfb.zza(parcel, Status.CREATOR), parcel.readLong());
                break;
            case 6:
                zza((Status) zzfb.zza(parcel, Status.CREATOR), (LogEventParcelable[]) parcel.createTypedArray(LogEventParcelable.CREATOR));
                break;
            case 7:
                zza((DataHolder) zzfb.zza(parcel, DataHolder.CREATOR));
                break;
            case 8:
                zza((Status) zzfb.zza(parcel, Status.CREATOR), (CollectForDebugParcelable) zzfb.zza(parcel, CollectForDebugParcelable.CREATOR));
                break;
            case 9:
                zzb((Status) zzfb.zza(parcel, Status.CREATOR), (CollectForDebugParcelable) zzfb.zza(parcel, CollectForDebugParcelable.CREATOR));
                break;
            default:
                return false;
        }
        return true;
    }
}
