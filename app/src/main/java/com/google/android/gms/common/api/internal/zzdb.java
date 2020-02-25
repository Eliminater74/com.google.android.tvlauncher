package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.BinderThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.common.internal.zzax;
import com.google.android.gms.internal.zzelx;
import com.google.android.gms.internal.zzemf;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.zzd;

import java.util.Set;

/* compiled from: SignInCoordinator */
public final class zzdb extends zzelx implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static Api.zza<? extends zzd, SignInOptions> zza = zza.zza;
    private final Context zzb;
    private final Handler zzc;
    private final Api.zza<? extends zzd, SignInOptions> zzd;
    /* access modifiers changed from: private */
    public zzde zzh;
    private Set<Scope> zze;
    private ClientSettings zzf;
    private zzd zzg;

    @WorkerThread
    public zzdb(Context context, Handler handler, @NonNull ClientSettings clientSettings) {
        this(context, handler, clientSettings, zza);
    }

    @WorkerThread
    public zzdb(Context context, Handler handler, @NonNull ClientSettings clientSettings, Api.zza<? extends zzd, SignInOptions> zza2) {
        this.zzb = context;
        this.zzc = handler;
        this.zzf = (ClientSettings) zzau.zza(clientSettings, "ClientSettings must not be null");
        this.zze = clientSettings.getRequiredScopes();
        this.zzd = zza2;
    }

    @WorkerThread
    public final void zza(zzde zzde) {
        zzd zzd2 = this.zzg;
        if (zzd2 != null) {
            zzd2.disconnect();
        }
        this.zzf.setClientSessionId(Integer.valueOf(System.identityHashCode(this)));
        Api.zza<? extends zzd, SignInOptions> zza2 = this.zzd;
        Context context = this.zzb;
        Looper looper = this.zzc.getLooper();
        ClientSettings clientSettings = this.zzf;
        this.zzg = (zzd) zza2.zza(context, looper, clientSettings, clientSettings.getSignInOptions(), this, this);
        this.zzh = zzde;
        Set<Scope> set = this.zze;
        if (set == null || set.isEmpty()) {
            this.zzc.post(new zzdc(this));
        } else {
            this.zzg.zzd();
        }
    }

    public final zzd zza() {
        return this.zzg;
    }

    public final void zzb() {
        zzd zzd2 = this.zzg;
        if (zzd2 != null) {
            zzd2.disconnect();
        }
    }

    @WorkerThread
    public final void onConnected(@Nullable Bundle bundle) {
        this.zzg.zza(this);
    }

    @WorkerThread
    public final void onConnectionSuspended(int i) {
        this.zzg.disconnect();
    }

    @WorkerThread
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zzh.zza(connectionResult);
    }

    @BinderThread
    public final void zza(zzemf zzemf) {
        this.zzc.post(new zzdd(this, zzemf));
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zzb(zzemf zzemf) {
        ConnectionResult zza2 = zzemf.zza();
        if (zza2.isSuccess()) {
            zzax zzb2 = zzemf.zzb();
            ConnectionResult zzb3 = zzb2.zzb();
            if (!zzb3.isSuccess()) {
                String valueOf = String.valueOf(zzb3);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 48);
                sb.append("Sign-in succeeded with resolve account failure: ");
                sb.append(valueOf);
                Log.wtf("SignInCoordinator", sb.toString(), new Exception());
                this.zzh.zza(zzb3);
                this.zzg.disconnect();
                return;
            }
            this.zzh.zza(zzb2.zza(), this.zze);
        } else {
            this.zzh.zza(zza2);
        }
        this.zzg.disconnect();
    }
}
