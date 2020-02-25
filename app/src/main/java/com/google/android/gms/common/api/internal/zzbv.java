package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.IAccountAccessor;

import java.util.Set;

/* compiled from: GoogleApiManager */
final class zzbv implements zzde, BaseGmsClient.ConnectionProgressReportCallbacks {
    /* access modifiers changed from: private */
    public final Api.Client zzb;
    /* access modifiers changed from: private */
    public final zzi<?> zzc;
    final /* synthetic */ zzbn zza;
    /* access modifiers changed from: private */
    public boolean zzf = false;
    private IAccountAccessor zzd = null;
    private Set<Scope> zze = null;

    public zzbv(zzbn zzbn, Api.Client client, zzi<?> zzi) {
        this.zza = zzbn;
        this.zzb = client;
        this.zzc = zzi;
    }

    public final void onReportServiceBinding(@NonNull ConnectionResult connectionResult) {
        this.zza.zzq.post(new zzbw(this, connectionResult));
    }

    @WorkerThread
    public final void zza(ConnectionResult connectionResult) {
        ((zzbp) this.zza.zzm.get(this.zzc)).zza(connectionResult);
    }

    @WorkerThread
    public final void zza(IAccountAccessor iAccountAccessor, Set<Scope> set) {
        if (iAccountAccessor == null || set == null) {
            Log.wtf("GoogleApiManager", "Received null response from onSignInSuccess", new Exception());
            zza(new ConnectionResult(4));
            return;
        }
        this.zzd = iAccountAccessor;
        this.zze = set;
        zza();
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zza() {
        IAccountAccessor iAccountAccessor;
        if (this.zzf && (iAccountAccessor = this.zzd) != null) {
            this.zzb.getRemoteService(iAccountAccessor, this.zze);
        }
    }
}
