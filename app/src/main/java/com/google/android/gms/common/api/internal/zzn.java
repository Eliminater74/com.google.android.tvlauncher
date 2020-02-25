package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.common.internal.zzbd;

/* compiled from: BaseImplementation */
public abstract class zzn<R extends Result, A extends Api.zzb> extends BasePendingResult<R> implements zzo<R> {
    private final Api.zzc<A> zza;
    private final Api<?> zzb;

    @Deprecated
    protected zzn(@NonNull Api.zzc zzc, @NonNull GoogleApiClient googleApiClient) {
        super((GoogleApiClient) zzau.zza(googleApiClient, "GoogleApiClient must not be null"));
        this.zza = (Api.zzc) zzau.zza(zzc);
        this.zzb = null;
    }

    protected zzn(@NonNull Api<?> api, @NonNull GoogleApiClient googleApiClient) {
        super((GoogleApiClient) zzau.zza(googleApiClient, "GoogleApiClient must not be null"));
        zzau.zza(api, "Api must not be null");
        this.zza = api.zzc();
        this.zzb = api;
    }

    /* access modifiers changed from: protected */
    @Hide
    public abstract void zza(@NonNull Api.zzb zzb2) throws RemoteException;

    @Hide
    public final Api.zzc<A> zzc() {
        return this.zza;
    }

    @Hide
    public final Api<?> zzd() {
        return this.zzb;
    }

    @Hide
    public final void zzb(@NonNull A a) throws DeadObjectException {
        if (a instanceof zzbd) {
            a = zzbd.zzc();
        }
        try {
            zza((Api.zzb) a);
        } catch (DeadObjectException e) {
            zza((RemoteException) e);
            throw e;
        } catch (RemoteException e2) {
            zza(e2);
        }
    }

    @Hide
    public final void zzc(@NonNull Status status) {
        zzau.zzb(!status.isSuccess(), "Failed result must not be success");
        zza(zza(status));
    }

    private final void zza(@NonNull RemoteException remoteException) {
        zzc(new Status(8, remoteException.getLocalizedMessage(), null));
    }

    @Hide
    public /* bridge */ /* synthetic */ void zza(Object obj) {
        super.zza((Result) obj);
    }
}
