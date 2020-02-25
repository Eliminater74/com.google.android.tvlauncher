package com.google.android.gms.clearcut.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.zzl;

/* compiled from: ClearcutLoggerClientImpl */
public final class zzi extends zzl<zzq> {
    public zzi(Context context, Looper looper, ClientSettings clientSettings, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 40, clientSettings, connectionCallbacks, onConnectionFailedListener);
    }

    /* access modifiers changed from: protected */
    public final String getStartServiceAction() {
        return "com.google.android.gms.clearcut.service.START";
    }

    /* access modifiers changed from: protected */
    public final String getServiceDescriptor() {
        return "com.google.android.gms.clearcut.internal.IClearcutLoggerService";
    }

    public final int zza() {
        return 12525000;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface zza(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.clearcut.internal.IClearcutLoggerService");
        if (queryLocalInterface instanceof zzq) {
            return (zzq) queryLocalInterface;
        }
        return new zzr(iBinder);
    }
}
