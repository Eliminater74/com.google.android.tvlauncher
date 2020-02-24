package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.usagereporting.UsageReportingOptInOptions;

/* compiled from: IUsageReportingCallbacks */
public abstract class zzfew extends zzfa implements zzfev {
    public zzfew() {
        attachInterface(this, "com.google.android.gms.usagereporting.internal.IUsageReportingCallbacks");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        if (i == 2) {
            zza((Status) zzfb.zza(parcel, Status.CREATOR), (UsageReportingOptInOptions) zzfb.zza(parcel, UsageReportingOptInOptions.CREATOR));
        } else if (i == 3) {
            zza((Status) zzfb.zza(parcel, Status.CREATOR));
        } else if (i == 4) {
            zzb((Status) zzfb.zza(parcel, Status.CREATOR));
        } else if (i != 5) {
            return false;
        } else {
            zzc((Status) zzfb.zza(parcel, Status.CREATOR));
        }
        return true;
    }
}
