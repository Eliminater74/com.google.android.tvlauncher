package com.google.android.gms.internal;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzcj;
import com.google.android.gms.common.api.internal.zzo;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.zzl;
import com.google.android.gms.usagereporting.UsageReportingApi;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: UsageReportingClientImpl */
public final class zzffg extends zzl<zzfez> {
    private final AtomicReference<zzffn> zzc = new AtomicReference<>();

    public static boolean zza(Context context) {
        if (!context.getPackageManager().queryIntentServices(new Intent("com.google.android.gms.usagereporting.service.START").setPackage("com.google.android.gms"), 0).isEmpty()) {
            return true;
        }
        return false;
    }

    public zzffg(Context context, Looper looper, ClientSettings clientSettings, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 41, clientSettings, connectionCallbacks, onConnectionFailedListener);
    }

    public final void zza(zzcj<UsageReportingApi.OptInOptionsChangedListener> zzcj, zzo<Status> zzo) throws RemoteException {
        zzffn zzffn;
        if (zzcj == null) {
            zzffn = null;
        } else {
            zzffn = new zzffn(zzcj);
        }
        zzffm zzffm = new zzffm((zzfez) zzag(), zzo, this.zzc, zzffn);
        zzffn zzffn2 = this.zzc.get();
        if (zzffn2 != null) {
            ((zzfez) zzag()).zzb(zzffn2, zzffm);
        } else if (zzffn == null) {
            zzo.zza(Status.zza);
        } else {
            this.zzc.set(zzffn);
            ((zzfez) zzag()).zza(zzffn, zzffm);
        }
    }

    public final void disconnect() {
        try {
            zzffn andSet = this.zzc.getAndSet(null);
            if (andSet != null) {
                ((zzfez) zzag()).zzb(andSet, new zzffj());
            }
        } catch (RemoteException e) {
            Log.e("UsageReportingClientImpl", "disconnect(): Could not unregister listener from remote:", e);
        }
        super.disconnect();
    }

    /* access modifiers changed from: protected */
    public final String getStartServiceAction() {
        return "com.google.android.gms.usagereporting.service.START";
    }

    /* access modifiers changed from: protected */
    public final String getServiceDescriptor() {
        return "com.google.android.gms.usagereporting.internal.IUsageReportingService";
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface zza(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.usagereporting.internal.IUsageReportingService");
        if (queryLocalInterface instanceof zzfez) {
            return (zzfez) queryLocalInterface;
        }
        return new zzffa(iBinder);
    }
}
