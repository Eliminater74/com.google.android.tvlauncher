package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.zzl;

/* compiled from: CommonClient */
public final class zzblk extends zzl<zzbln> {
    public zzblk(Context context, Looper looper, ClientSettings clientSettings, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 39, clientSettings, connectionCallbacks, onConnectionFailedListener);
    }

    public final String getStartServiceAction() {
        return "com.google.android.gms.common.service.START";
    }

    /* access modifiers changed from: protected */
    public final String getServiceDescriptor() {
        return "com.google.android.gms.common.internal.service.ICommonService";
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface zza(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.service.ICommonService");
        if (queryLocalInterface instanceof zzbln) {
            return (zzbln) queryLocalInterface;
        }
        return new zzblo(iBinder);
    }
}
